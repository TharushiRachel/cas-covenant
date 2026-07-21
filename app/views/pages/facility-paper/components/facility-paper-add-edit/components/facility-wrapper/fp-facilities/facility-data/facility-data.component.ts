import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { Subscription } from "rxjs";
import { Constants } from "../../../../../../../../../core/setting/constants";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "../../../../../../services/facility-paper-add-edit.service";
import { SETTINGS } from "../../../../../../../../../core/setting/commons.settings";
import { AddEditFacilityComponent } from "../add-edit-facility/add-edit-facility.component";
import * as _ from "lodash";
import { FpFacilityCopyComponent } from "../facility-data-helper/fp-facility-copy/fp-facility-copy.component";
import { CacheService } from "../../../../../../../../../core/service/data/cache.service";
import { FpFacilityRepaymentComponent } from "../facility-data-helper/fp-facility-repayment/fp-facility-repayment.component";
import { FacilityDataDocumentComponent } from "../facility-data-helper/facility-data-document/facility-data-document.component";
import { CurrencyPipe } from "@angular/common";
import { ApplicationService } from "../../../../../../../../../core/service/application/application.service";
import { CreditCalculatorComponent } from "../../../../../../../../../shared/components/credit-calculator/credit-calculator.component";
import { sortBy, orderBy } from "lodash";
import { FacilityChangesModalComponent } from "src/app/shared/components/facility-changes-modal/facility-changes-modal.component";

@Component({
  selector: "app-facility-data",
  templateUrl: "./facility-data.component.html",
  styleUrls: ["./facility-data.component.scss"],
})
export class FacilityDataComponent implements OnInit, OnDestroy {
  @Input("facilityData") facilityData;
  @Input("facilityPaper") facilityPaper;
  @Input("creditFacilityList") creditFacilityList;
  @Input("otherInfoData") otherInfoData;
  @Input("isPreviewMode") isPreviewMode: boolean;
  @Input("lastIndex") lastIndex;
  @Input("currentIndex") currentIndex;
  @Input("creditScheduleESBResponseStatusList")
  creditScheduleESBResponseStatusList: [];

  @Output("onUpdateFacility") onUpdateFacility: EventEmitter<any> =
    new EventEmitter();
  @Output("setTabIndex") setTabIndex: EventEmitter<number> = new EventEmitter();
  @Output("toCommonSecurityContent")
  toCommonSecurityContent: EventEmitter<number> = new EventEmitter();

  onDownLoadLinkChangeSub = new Subscription();

  tableColumnsForFacilityInterestRate = ["Rate Name", "Value", "Comment"];
  tableColumnsForFacilityPurposeofAdvance = [
    "Purpose of Advance",
    "Reference Description",
    "Reference Code",
    "Status",
  ];
  purposeOfAdvancedList = [];
  purposeOfAdvancedMap = {};
  status = Constants.status;
  yesNo = Constants.yesNo;
  yesNoConst = Constants.yesNoConst;
  approveStatus = Constants.approveStatus;
  modalRef: MDBModalRef;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  esbCreditCalculatorResponseStatusConst =
    Constants.esbCreditCalculatorResponseStatusConst;

  resizedfpInterestList: any[] = [];
  count: number = 0;
  fpInterest = "fpInterest";
  applicablefpInterestList: any[] = [];

  resizedFpSecuritiesList: any[] = [];
  securityCount: number = 0;
  fpSecurity = "fpSecurity";

  fpVitalInfoDataDTOList: any[] = [];
  fpCustomInfoDataDTOList: any[] = [];
  fpOtherInfoDataDTOList: any[] = [];
  fpRentalDataDTOList: any[] = [];
  fpShowCalculator: any;

  loadMoreClicked: boolean = false;

  sectorList: any[] = [];
  subSectorList: any[] = [];

  equalLoginUserAndAssignUser = false;

  organizedSecurityList = [];
  parentFacility: any = {};
  currencyViseTotalIndividualCashAmount = {};
  ESBCreditScheduleServiceResponse: any = {};
  individualCashAmounts: any[] = [];

  currentFPStatus: any = "";

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly cacheService: CacheService,
    private readonly currencyPipe: CurrencyPipe,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.currentFPStatus = this.facilityPaper
      ? this.facilityPaper.currentFacilityPaperStatus
      : "";

    this.sectorList =
      this.cacheService.getData(Constants.masterDataKey.CAS_SECTOR_DATA) || [];
    this.subSectorList =
      this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA)
        .subSectorDTOList || [];
    this.purposeOfAdvancedList =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED
      ) || [];

    _.forEach(this.purposeOfAdvancedList, (pusposeOfAdvanced) => {
      this.purposeOfAdvancedMap[pusposeOfAdvanced.referenceCode] =
        pusposeOfAdvanced.referenceCode +
        " - " +
        pusposeOfAdvanced.referenceDescription;
    });

    this.applicablefpInterestList = _.sortBy(
      this.facilityData.facilityInterestRateList,
      (i: any) => i.facilityInterestRateID
    );

    if (this.applicablefpInterestList.length > 3) {
      for (this.count = 0; this.count < 3; this.count++) {
        this.resizedfpInterestList.push(
          this.applicablefpInterestList[this.count]
        );
      }
    } else {
      for (
        this.count = 0;
        this.count < this.applicablefpInterestList.length;
        this.count++
      ) {
        this.resizedfpInterestList.push(
          this.applicablefpInterestList[this.count]
        );
      }
    }

    if (this.facilityData.facilitySecurityDTOList.length > 3) {
      for (
        this.securityCount = 0;
        this.securityCount < 3;
        this.securityCount++
      ) {
        this.resizedFpSecuritiesList.push(
          this.facilityData.facilitySecurityDTOList[this.securityCount]
        );
      }
    } else {
      for (
        this.securityCount = 0;
        this.securityCount < this.facilityData.facilitySecurityDTOList.length;
        this.securityCount++
      ) {
        this.resizedFpSecuritiesList.push(
          this.facilityData.facilitySecurityDTOList[this.securityCount]
        );
      }
    }

    this.fpVitalInfoDataDTOList =
      _.cloneDeep(this.facilityData.facilityVitalInfoDataDTOList) || [];
    this.fpCustomInfoDataDTOList =
      _.sortBy(_.cloneDeep(this.facilityData.facilityCustomInfoDataDTOList), [
        "displayOrder",
      ]) || [];
    this.fpCustomInfoDataDTOList = this.fpCustomInfoDataDTOList.filter(
      (item) => item.customInfoData !== null && item.customInfoData !== ""
    );
    this.fpCustomInfoDataDTOList = orderBy(
      this.fpCustomInfoDataDTOList,
      [
        "mandatory", // Sorting by mandatory first
        // 'customInfoData', // Then sorting by customInfoData (null comes last)
        // 'customFacilityInfoName' // Finally, sorting by customFacilityInfoName
      ],
      ["desc"]
    );

    this.fpOtherInfoDataDTOList =
      _.sortBy(
        _.cloneDeep(this.facilityData.facilityOtherFacilityInformationDTOList),
        ["displayOrder"]
      ) || [];
    this.fpRentalDataDTOList =
      _.cloneDeep(this.facilityData.facilityRentalInformationDTOList) || [];
      if(this.facilityData.creditFacilityTemplateDTO !== null && this.facilityData.creditFacilityTemplateDTO.showCalculator){
    this.fpShowCalculator =  _.cloneDeep(
      this.facilityData.creditFacilityTemplateDTO.showCalculator
    );

      }
    this.isEqualLoginAndAssignUser();

    let commonSecurities = [];
    let individualSecurities = [];
    this.facilityData.facilitySecurityDTOList.forEach((security) => {
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

    this.organizedSecurityList.forEach((e) => {
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

    if (this.facilityData.parentFacilityID) {
      this.parentFacility = _.find(this.creditFacilityList, {
        facilityID: this.facilityData.parentFacilityID,
      });
    }

    this.ESBCreditScheduleServiceResponse = _.find(
      this.creditScheduleESBResponseStatusList,
      (data: any) => {
        return data.facilityId == this.facilityData.facilityID;
      }
    );
  }

  ngOnDestroy(): void {
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  openModalAddEdit(facility?) {
    facility = facility || {};
    const initialState = {
      list: [{ tag: "Count", value: facility }],
    };

    this.modalRef = this.mdbModalService.show(AddEditFacilityComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "right",
      animated: true,
      data: {
        heading: "Add/ Edit Facility",
        content: {
          facility: facility,
          facilityPaper: this.facilityPaper,
        },
      },
    });
    this.modalRef.content.action.subscribe((result: any) => {});
  }

  onLoadMoreDetail() {
    this.loadMoreClicked = true;
  }

  onHideDetail() {
    this.loadMoreClicked = false;
  }

  onLoadResizedList(listFromEvent) {
    if (listFromEvent.outputArrayType === this.fpInterest) {
      this.resizedfpInterestList = [];
      _.forEach(listFromEvent.outputArray, (item) => {
        this.resizedfpInterestList.push(item);
      });
    }
  }

  onLoadResizedSecurityList(listFromEvent) {
    if (listFromEvent.outputArrayType === this.fpSecurity) {
      this.resizedFpSecuritiesList = [];
      _.forEach(listFromEvent.outputArray, (item) => {
        this.resizedFpSecuritiesList.push(item);
      });
    }
  }

  onUpdate($event) {
    this.onUpdateFacility.emit($event);
  }

  getReferenceDescriptionForSectorId(sectorID) {
    let referenceDescription = "";

    if (this.sectorList.length > 0) {
      this.sectorList.forEach((sector) => {
        if (sector.sectorID == sectorID) {
          return (referenceDescription = sector.referenceDescription);
        }
      });
    }

    return referenceDescription;
  }

  getReferenceDescriptionForSubSectorId(subSectorID) {
    let referenceDescription = "";

    if (this.subSectorList.length > 0) {
      this.subSectorList.forEach((subSector) => {
        if (subSector.subSectorID == subSectorID) {
          return (referenceDescription = subSector.referenceDescription);
        }
      });
    }

    return referenceDescription;
  }

  getReferenceDescriptionForCashFlowGenerationSectorID(shgfID) {
    let referenceDescription = "";
    if (this.subSectorList.length > 0) {
      this.subSectorList.forEach((subSector) => {
        if (subSector.subSectorID == shgfID) {
          return (referenceDescription = subSector.referenceDescription);
        }
      });
    }

    return referenceDescription;
  }

  openModalFacilityRepayment($event) {
    this.modalRef = this.mdbModalService.show(FpFacilityRepaymentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p audit-modal-margin-center",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Facility Repayment",
        content: {
          facilityData: this.facilityData,
          facilityPaper: this.facilityPaper,
          isPreviewMode: this.isPreviewMode,
        },
      },
    });
  }

  openModalFacilityDocument($event) {
    this.modalRef = this.mdbModalService.show(FacilityDataDocumentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p audit-modal-margin-center",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Facility Document",
        content: {
          facilityData: this.facilityData,
          facilityPaper: this.facilityPaper,
          isPreviewMode: this.isPreviewMode,
        },
      },
    });
  }

  openModalCreditCalculator(
    fpOtherInfoDataDTOList,
    facilityData,
    fpRentalDataDTOList
  ) {
    this.modalRef = this.mdbModalService.show(CreditCalculatorComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-dialog-scrollable modal-width-85-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Credit Calculator",
        content: {
          otherInfoData: fpOtherInfoDataDTOList,
          facilityData: facilityData,
          rentalData: fpRentalDataDTOList,
        },
      },
    });
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

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
  }

  isAbleToAddDocuments() {
    if (!_.isEmpty(this.facilityData.creditFacilityTemplateDTO)) {
      return (
        this.facilityData.creditFacilityTemplateDTO.cftSupportingDocDTOList
          .length > 0
      );
    } else {
      return false;
    }
  }

  isESBCreditScheduleResponseSuccess() {}

  openCopyFacilityWindow(facilityData: any) {
    this.modalRef = this.mdbModalService.show(FpFacilityCopyComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: false,
      data: {
        heading: "Copy Facilities",
        content: {
          facilityData: facilityData,
        },
      },
    });
  }

  getAllCommentsCount(facility: any): number {
    var preCmnts: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    return preCmnts.filter(
      (cmnt: any) => cmnt.flag == Constants.fusTraceFlag.FAC
    ).length;
  }

  getUnReadCommentsCount(facility: any): number {
    var preCmnts: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    return preCmnts.filter(
      (cmnt: any) =>
        cmnt.flag == Constants.fusTraceFlag.FAC &&
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
        isSecurity: false,
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
          this.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL;
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

  showCusApplicableNotify(facility: any) {
    return (
      facility.facilityType === "Lease" &&
      this.currentFPStatus !== Constants.facilityPaperStatusConst.APPROVED &&
      this.currentFPStatus !== Constants.facilityPaperStatusConst.REJECTED
    );
  }
}
