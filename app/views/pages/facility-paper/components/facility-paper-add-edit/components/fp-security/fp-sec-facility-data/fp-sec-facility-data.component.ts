import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import * as _ from "lodash";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { ConfirmationDialogComponent } from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../../core/setting/constants";
import { CurrencyPipe } from "@angular/common";
import { AppUtils } from "../../../../../../../../shared/app.utils";
import { FacilityChangesModalComponent } from "src/app/shared/components/facility-changes-modal/facility-changes-modal.component";

@Component({
  selector: "app-fp-sec-facility-data",
  templateUrl: "./fp-sec-facility-data.component.html",
  styleUrls: ["./fp-sec-facility-data.component.scss"],
})
export class FpSecFacilityDataComponent implements OnInit {
  @Input("facilityData") facilityData: any = {};
  @Input("facilityPaper") facilityPaper: any = {};
  @Input("lastIndex") lastIndex;
  @Input("currentIndex") currentIndex;
  @Output("setTabIndex") setTabIndex: EventEmitter<number> = new EventEmitter();
  @Output("onAddEditSecurityDetails") onAddEditSecurityDetails =
    new EventEmitter();
  @Output("toCommonSecurityContent")
  toCommonSecurityContent: EventEmitter<number> = new EventEmitter();
  @Output("addSecurityThroughFacility")
  addSecurityThroughFacility: EventEmitter<any> = new EventEmitter();
  @Input("isPreviewMode") isPreviewMode: boolean;

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  modalRef: MDBModalRef;
  resizedfpSecurityList: any[] = [];
  activeSecurityList: any[] = [];
  count: number = 0;
  organizedSecurityList: any[] = [];
  fpSecurity = "fpSecurity";
  yesNoConst = Constants.yesNoConst;
  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  currencyViseTotalIndividualCashAmount = {};
  individualCashAmounts: any[] = [];

  isCommittee: boolean = false;
  currentFPStatus: any = "";

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private currencyPipe: CurrencyPipe
  ) {}

  ngOnInit() {
    this.isCommittee =
      this.facilityPaper &&
      this.facilityPaper.isCommittee == Constants.yesNoConst.Y;

    this.currentFPStatus = this.facilityPaper
      ? this.facilityPaper.currentFacilityPaperStatus
      : "";

    this.activeSecurityList = _.filter(
      this.facilityData.facilitySecurityDTOList,
      (security) => security.status == Constants.statusConst.ACT
    );

    let commonSecurities = [];
    let individualSecurities = [];
    this.activeSecurityList.forEach((security) => {
      if (security.isCommonSecurity == Constants.yesNoConst.Y) {
        commonSecurities.push(security);
      } else {
        individualSecurities.push(security);
      }
    });
    this.organizedSecurityList = _.concat(
      _.sortBy(
        _.uniqBy(individualSecurities, "facilitySecurityID"),
        "facilitySecurityID"
      ),
      _.sortBy(
        _.uniqBy(commonSecurities, "facilitySecurityID"),
        "facilitySecurityID"
      )
    );
    _.uniqBy(individualSecurities, "facilitySecurityID").forEach((e) => {
      if (
        e.isCommonSecurity == Constants.yesNoConst.N &&
        e.isCashSecurity == Constants.yesNoConst.Y
      ) {
        if (
          _.isNumber(
            this.currencyViseTotalIndividualCashAmount[e.securityCurrency]
          )
        ) {
          this.currencyViseTotalIndividualCashAmount[e.securityCurrency] =
            this.currencyViseTotalIndividualCashAmount[e.securityCurrency] +
            e.cashAmount;
        } else {
          this.currencyViseTotalIndividualCashAmount[e.securityCurrency] =
            e.cashAmount ? e.cashAmount : 0;
        }
      }
    });

    this.individualCashAmounts = [];
    Object.entries(this.currencyViseTotalIndividualCashAmount).forEach(
      ([key, value]) => {
        this.individualCashAmounts.push({ currency: key, amount: value });
      }
    );

    if (this.activeSecurityList.length > 3) {
      for (this.count = 0; this.count < 3; this.count++) {
        this.resizedfpSecurityList.push(this.activeSecurityList[this.count]);
      }
    } else {
      for (
        this.count = 0;
        this.count < this.activeSecurityList.length;
        this.count++
      ) {
        this.resizedfpSecurityList.push(this.activeSecurityList[this.count]);
      }
    }

    this.isEqualLoginAndAssignUser();
  }

  onRemoveFacilitySecurity(facilityData, securityItem) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        message:
          "Do you want to remove security " +
          securityItem.securityCode +
          " from facility " +
          facilityData.facilityRefCode +
          " ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let facilitySecuritySecurityID = null;

        securityItem.facilityFacilitySecurityDTOS.forEach(
          (facilityFacilitySecurity: any) => {
            if (
              facilityFacilitySecurity.facilityID == facilityData.facilityID
            ) {
              facilitySecuritySecurityID =
                facilityFacilitySecurity.facilitySecuritySecurityID;
            }
          }
        );

        let facilityFacilitySecurityDTO = {
          facilitySecuritySecurityID: facilitySecuritySecurityID,
          facilityID: facilityData.facilityID,
          facilitySecurityID: securityItem.facilitySecurityID
            ? securityItem.facilitySecurityID
            : null,
          isAddedFacility: "N",
          isCashSecurity: securityItem.isCashSecurity == "Y" ? "Y" : "N",
          status: "INA",
        };

        let removeObj = Object.assign(
          {},
          {
            facilitySecurityID: securityItem.facilitySecurityID
              ? securityItem.facilitySecurityID
              : null,
            securityDetail: securityItem.securityDetail
              ? securityItem.securityDetail
              : null,
            facilityPaperID: facilityData.facilityPaperID
              ? facilityData.facilityPaperID
              : null,
            securityCode: securityItem.securityCode
              ? securityItem.securityCode
              : null,
            securityAmount: this.getValue(securityItem.securityAmount),
            cashAmount: this.getValue(securityItem.cashAmount),
            isCashSecurity: securityItem.isCashSecurity == "Y" ? "Y" : "N",
            securityCurrency: securityItem.securityCurrency,
            status:
              securityItem.isCommonSecurity == "Y"
                ? Constants.statusConst.ACT
                : Constants.statusConst.INA,
            isCommonSecurity:
              _.filter(
                securityItem.facilityFacilitySecurityDTOS,
                (facilityFacilitySecurityDTO) =>
                  facilityFacilitySecurityDTO.status ==
                  Constants.statusConst.ACT
              ).length >= 3
                ? "Y"
                : "N",
            facilityID: facilityData.facilityID,
            facilityFacilitySecurityDTOS: [facilityFacilitySecurityDTO],
          }
        );
        if (facilitySecuritySecurityID) {
          this.facilityPaperAddEditService.saveUpdateFacilitySecurity(
            removeObj,
            this.facilityPaper
          );
        }
      }
    });
  }

  onLoadResizedList(listFromEvent) {
    if (listFromEvent.outputArrayType === this.fpSecurity) {
      this.resizedfpSecurityList = [];
      _.forEach(listFromEvent.outputArray, (item) => {
        this.resizedfpSecurityList.push(item);
      });
    }
  }

  showPreviousTab($event) {
    let index = this.currentIndex;
    if (index != 0) {
      this.setTabIndex.emit(--index);
    }
  }

  showNextTab($event) {
    let index = this.currentIndex;
    if (index != this.lastIndex) {
      this.setTabIndex.emit(++index);
    }
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "");
    }
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isRejected() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED
    );
  }

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
  }

  addSecurity(facilityData, securityItem?, allSecurityItems?) {
    let emitObject = Object.assign(
      {},
      { facilityData: facilityData },
      { securityItem: securityItem },
      { allSecurityItems: allSecurityItems }
    );
    this.addSecurityThroughFacility.emit(emitObject);
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }

  getAllCommentsCount(facility: any): number {
    var preCmnts: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    return preCmnts.filter(
      (cmnt: any) => cmnt.flag == Constants.fusTraceFlag.FACSE
    ).length;
  }

  getUnReadCommentsCount(facility: any): number {
    var preCmnts: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    return preCmnts.filter(
      (cmnt: any) =>
        cmnt.flag == Constants.fusTraceFlag.FACSE &&
        cmnt.isView == 0 &&
        cmnt.createdBy != this.applicationService.getLoggedInUserUserName()
    ).length;
  }

  viewChangeModal(creditFacility: any) {
    this.modalRef = this.mdbModalService.show(FacilityChangesModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-50-p modal-dialog-scrollable",
      containerClass: "",
      animated: true,
      data: {
        heading: "Facility Security Changes",
        isSecurity: true,
        facility: creditFacility,
        facilityPaper: this.facilityPaper,
      },
    });
  }

  isShow(): boolean {
    var isShow: boolean = false;
    var wc: any = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    if (this.isPreviewMode) {
      if (
        wc == Constants.applicationSecurityWorkClass.ENTERER ||
        wc == Constants.applicationSecurityWorkClass.SUPERVISOR ||
        wc == Constants.applicationSecurityWorkClass.MANAGER
      ) {
        isShow =
          this.currentFPStatus ==
            Constants.facilityPaperStatusConst.IN_PROGRESS ||
          this.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL ||
          this.currentFPStatus == Constants.facilityPaperStatusConst.APPROVED;
      }
    } else {
      if (
        wc == Constants.applicationSecurityWorkClass.ENTERER ||
        wc == Constants.applicationSecurityWorkClass.SUPERVISOR ||
        wc == Constants.applicationSecurityWorkClass.MANAGER
      ) {
        isShow =
          this.currentFPStatus ==
            Constants.facilityPaperStatusConst.IN_PROGRESS ||
          this.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL;
      } else {
        isShow = false;
      }
    }

    return isShow;
  }
}
