import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { sortBy, uniqBy } from "lodash";
import { SETTINGS } from "../../../../../../../core/setting/commons.settings";
import { PrivilegeService } from "../../../../../../../core/service/authentication/privilege.service";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../core/setting/constants";
import { FpFacilityDataSecurityComponent } from "./fp-sec-facility-data/fp-facility-data-security/fp-facility-data-security.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { InsuranceValuationModalComponent } from "./insurance-valuation-modal/insurance-valuation-modal.component";
import * as _ from "lodash";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-fp-security",
  templateUrl: "./fp-security.component.html",
  styleUrls: ["./fp-security.component.scss"],
})
export class FpSecurityComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any = {};
  @Output("handleWalletShareUpdate") handleWalletShareUpdate =
    new EventEmitter();

  onFPFacilitiesChangeSub = new Subscription();

  creditFacilityList: any[] = [];
  facilitySecurityList: any[] = [];
  commonFacilitySecurityList: any[] = [];
  updatedFacilityPaper: any = {};

  selectedTabIndex: number = 0;

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  isPreviewMode: boolean = true;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  modalRef: MDBModalRef;
  isWalletUpdateEnabled: boolean = false;
  onWalletShareChangeSub = new Subscription();

  facilities: any[] = [];

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private privilegeService: PrivilegeService,
    private applicationService: ApplicationService,
    private mdbModalService: MDBModalService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    if (
      this.facilityPaper &&
      (this.facilityPaper.currentFacilityPaperStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
        this.facilityPaper.currentFacilityPaperStatus ==
          Constants.facilityPaperStatusConst.CANCEL)
    ) {
      this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
        this.facilityPaper,
        Constants.fusTraceFlag.FACSE
      );
    }

    this.onFPFacilitiesChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (data) {
            this.updatedFacilityPaper = data;
          }
          this.creditFacilityList = [];
          this.creditFacilityList =
            this.updatedFacilityPaper.facilityDTOList || [];
          this.creditFacilityList = sortBy(this.creditFacilityList, [
            "displayOrder",
          ]);

          //The following implementation fro getting all common securities and other securities of each facility and get in to one array
          let commonSecurities: any[] = [];
          let allSecurities: any[] = [];

          this.creditFacilityList.forEach((facility) => {
            facility.facilitySecurityDTOList.forEach((security) => {
              allSecurities.push(security);
              if (security.isCommonSecurity == Constants.yesNoConst.Y) {
                commonSecurities.push(security);
              }
            });
          });
          this.commonFacilitySecurityList = uniqBy(
            commonSecurities,
            "facilitySecurityID"
          );
          this.facilitySecurityList = uniqBy(
            allSecurities,
            "facilitySecurityID"
          );
          // Above commonFacilitySecurityList has all common securities of all facilities
          // Above facilitySecurityList has all securities of all facilities

          this.facilities = data.facilityDTOList ? data.facilityDTOList : [];
          if (data.walletShares !== null && data.walletShares.length > 0) {
            this.compareFacilitiesWithWallet(
              data.facilityDTOList,
              data.walletShares
            );
          }
        }
      );

    if (
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT
      )
    ) {
      this.isPreviewMode = false;
    }

    this.onWalletShareChangeSub =
      this.facilityPaperAddEditService.onWalletShareChange.subscribe(
        (shares: any[]) => {
          if (shares.length > 0) {
            this.compareFacilitiesWithWallet(this.facilities, shares);
          }
        }
      );

    this.isEqualLoginAndAssignUser();
  }

  ngOnDestroy(): void {
    this.onFPFacilitiesChangeSub.unsubscribe();
  }

  setTabIndex($event) {
    this.selectedTabIndex = $event;
  }

  toggleViewMode($event) {
    this.selectedTabIndex = 0;
    this.isPreviewMode = !this.isPreviewMode;
  }

  saveOrUpdateFPFacility(data) {
    this.facilityPaperAddEditService.saveOrUpdateFPFacility(
      data,
      this.facilityPaper
    );
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      this.isPreviewMode = true;
      return false;
    }
  }

  isApproveStatus() {
    let isApproved = false;
    isApproved =
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED;

    if (isApproved) {
      this.isPreviewMode = true;
    }
    return isApproved;
  }

  isRejected() {
    let isRejected = false;
    isRejected =
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED;

    if (isRejected) {
      this.isPreviewMode = true;
    }
    return isRejected;
  }

  toCommonSecurityContent($event) {
    document.getElementById($event).scrollIntoView({
      behavior: "smooth",
      block: "center",
      inline: "center",
    });
  }

  manageSecurity() {
    let obj = Object.assign(
      { facilityData: {} },
      { securityItem: {} },
      { allSecurityItems: this.facilitySecurityList }
    );
    this.openModalFacilitySecurity(obj);
  }

  addSecurityThroughFacility(data: any) {
    this.openModalFacilitySecurity(data);
  }

  openModalFacilitySecurity(data) {
    let facilityData = data.facilityData ? data.facilityData : {};
    let securityItem = data.securityItem ? data.securityItem : {};
    let allSecurityItems = data.allSecurityItems ? data.allSecurityItems : [];
    const initialState = {
      list: [{ tag: "Count", value: facilityData }],
    };

    this.modalRef = this.mdbModalService.show(FpFacilityDataSecurityComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p modal-dialog-scrollable",
      containerClass: "right",
      animated: false,
      data: {
        heading: "Add/ Edit Facility",
        content: {
          facilityData: facilityData,
          securityItem: securityItem,
          allSecurityItems: allSecurityItems,
        },
      },
    });
    this.modalRef.content.action.subscribe((result: any) => {
      // this.onAddEditSecurityDetails.emit(result);
      // this.facilityPaperAddEditService.saveOrUpdateFPFacility(result);
    });
  }

  moveUp() {
    document.getElementById("fp-sec-cmp").scrollIntoView({
      behavior: "smooth",
      block: "center",
      inline: "center",
    });
  }

  showInsuranceValuation() {
    const insuranceRQ = {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      customerFinacleId: null,
    };

    this.openModalInsuranceValuation(insuranceRQ);
  }

  openModalInsuranceValuation(data) {
    this.modalRef = this.mdbModalService.show(
      InsuranceValuationModalComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-85-p modal-dialog-scrollable",
        containerClass: "right",
        animated: true,
        data: {
          content: {
            insuranceRQ: data,
            customerDTOList: this.facilityPaper.casCustomerDTOList,
          },
        },
      }
    );
    this.modalRef.content.action.subscribe((result: any) => {
      // this.onAddEditSecurityDetails.emit(result);
      // this.facilityPaperAddEditService.saveOrUpdateFPFacility(result);
    });
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
