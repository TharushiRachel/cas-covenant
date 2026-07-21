import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FpGroupExposureComponent } from "./fp-group-exposure/fp-group-exposure.component";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { Subscription } from "rxjs";
import * as _ from "lodash";
import { AppUtils } from "src/app/shared/app.utils";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Component({
  selector: "app-facility-wrapper",
  templateUrl: "./facility-wrapper.component.html",
  styleUrls: ["./facility-wrapper.component.scss"],
})
export class FacilityWrapperComponent implements OnInit, OnChanges, OnDestroy {
  @Input("facilityPaper") facilityPaper: any = {};
  @Output("handleWalletShareUpdate") handleWalletShareUpdate =
    new EventEmitter();
  itemHasRemoved = false;
  itemHasAdded = false;
  accessLevelOfCurrentAssignUser: any;

  modalRef: MDBModalRef;
  isWalletUpdateEnabled: boolean = false;
  onFPFacilityChangeSub = new Subscription();
  onWalletShareChangeSub = new Subscription();

  facilities: any[] = [];
  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly mdbModalService: MDBModalService,
    private readonly applicationService: ApplicationService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.itemHasRemoved = false;
    this.itemHasAdded = false;

    this.onFPFacilityChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilities = data.facilityDTOList ? data.facilityDTOList : [];
            if (data.walletShares !== null && data.walletShares.length > 0) {
              this.compareFacilitiesWithWallet(
                data.facilityDTOList,
                data.walletShares
              );
            }
          }
        }
      );

    this.onWalletShareChangeSub =
      this.facilityPaperAddEditService.onWalletShareChange.subscribe(
        (shares: any[]) => {
          if (shares.length > 0) {
            this.compareFacilitiesWithWallet(this.facilities, shares);
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onFPFacilityChangeSub.unsubscribe();
    this.onWalletShareChangeSub.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["facilityPaper"]) {
      let prevFacilityCount = 0;
      let currFacilityCount = 0;
      if (changes["facilityPaper"].previousValue) {
        prevFacilityCount =
          changes["facilityPaper"].previousValue.facilityDTOList.length;
      }
      currFacilityCount =
        changes["facilityPaper"].currentValue.facilityDTOList.length;

      if (currFacilityCount < prevFacilityCount) {
        this.itemHasRemoved = true;
        this.itemHasAdded = false;
      } else if (currFacilityCount > prevFacilityCount) {
        this.itemHasRemoved = false;
        this.itemHasAdded = true;
      } else {
        // no change for the item count
      }
    }
  }

  openCalculateGroupExposure(): void {
    this.modalRef = this.mdbModalService.show(FpGroupExposureComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-90-p",
      animated: true,
      data: {},
    });
  }

  isEqualLoginAndAssignUserFiftyOrLower() {
    let fpStatus: string = this.facilityPaper
      ? this.facilityPaper.currentFacilityPaperStatus
      : "";

    let currentAssignUser: string = this.facilityPaper
      ? this.facilityPaper.currentAssignUser
      : "";

    let loggedInUser: string =
      this.applicationService.getLoggedInUserUserName();

    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (
      currentAssignUser === loggedInUser &&
      this.accessLevelOfCurrentAssignUser <= 50 &&
      this.isNotRiskUser() &&
      (fpStatus === Constants.facilityPaperStatusConst.DRAFT ||
        fpStatus === Constants.facilityPaperStatusConst.IN_PROGRESS ||
        fpStatus === Constants.facilityPaperStatusConst.CANCEL)
    ) {
      return true;
    } else {
      return false;
    }
  }

  isNotRiskUser() {
    return (
      this.applicationService.getLoggedInUserDivCode() &&
      this.applicationService.getLoggedInUserDivCode().toString() !==
        SETTINGS.ESG_DIV_CODE.toString()
    );
  }

  compareFacilitiesWithWallet(facilities: any[], walletShares: any[]) {
    let fpFacilities: any[] = [];
    let commonSecurities: any[] = [];

    facilities.forEach((facility: any) => {
      let fcSecurities: any[] = facility.facilitySecurityDTOList
        ? facility.facilitySecurityDTOList
        : [];

      let facCommonSecurities: any[] = fcSecurities
        .filter((cs: any) => cs.isCommonSecurity === Constants.yesNoConst.Y)
        .map((s: any) => s.securityDetail);

      if (commonSecurities.length > 0) {
        commonSecurities.concat(facCommonSecurities);
      } else {
        commonSecurities = facCommonSecurities;
      }

      let item: any = {
        facility: facility.creditFacilityTemplateDTO
          ? facility.creditFacilityTemplateDTO.description
          : "-",
        limitAmount: facility.facilityAmount
          ? AppUtils.getMillionValue(parseFloat(facility.facilityAmount))
          : 0,
        osAmount: facility.outstandingAmount
          ? AppUtils.getMillionValue(parseFloat(facility.outstandingAmount))
          : 0,
        facilitySecurities: fcSecurities
          .filter((cs: any) => cs.isCommonSecurity === Constants.yesNoConst.N)
          .map((s: any) => s.securityDetail),
      };
      fpFacilities.push(item);
    });

    let fpShare: any = walletShares.find((ws: any) => ws.isSystem == 1);

    let walletFPFacilities: any[] =
      fpShare && fpShare.facilities !== null
        ? fpShare.facilities.map((ws: any) => ({
            facility: ws.facility,
            limitAmount: ws.limitAmount,
            osAmount: ws.osAmount,
            facilitySecurities: ws.facilitySecurities.map(
              (s: any) => s.securityDetail
            ),
          }))
        : [];

    let walletShareSecurity: any = fpShare
      ? fpShare.commonSecurities.map((s: any) => s.securityDetail)
      : [];

    let checkFacilities: boolean = _.isEqual(
      JSON.stringify(fpFacilities),
      JSON.stringify(walletFPFacilities)
    );

    let checkSecurities: boolean = _.isEqual(
      JSON.stringify(walletShareSecurity),
      JSON.stringify(commonSecurities)
    );

    this.isWalletUpdateEnabled = !checkFacilities || !checkSecurities;
    this.cdr.detectChanges();
  }

  walletShareUpdate() {
    this.isWalletUpdateEnabled = false;
    this.handleWalletShareUpdate.emit(true);
  }
}
