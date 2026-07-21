import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Constants } from "../../../../core/setting/constants";
import { CurrencyPipe } from "@angular/common";
import { CacheService } from "../../../../core/service/data/cache.service";
import * as _ from "lodash";
import { CreditCalculatorComponent } from "../../../../shared/components/credit-calculator/credit-calculator.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { sortBy, orderBy } from "lodash";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-preview-facility-data",
  templateUrl: "./preview-facility-data.component.html",
  styleUrls: ["./preview-facility-data.component.scss"],
})
export class PreviewFacilityDataComponent implements OnInit {
  @Input("facilityData") facilityData;
  @Input("facilityPaper") facilityPaper;
  @Input("creditFacilityList") creditFacilityList;
  @Input("isPreviewMode") isPreviewMode: boolean;
  @Input("currentIndex") currentIndex;
  @Input("creditScheduleESBResponseStatusList")
  creditScheduleESBResponseStatusList: []; // TODO
  yesNoConst = Constants.yesNoConst;
  parentFacility: any = {};
  purposeOfAdvancedList = [];
  purposeOfAdvancedMap = {};
  sectorList: any[] = [];
  subSectorList: any[] = [];
  applicablefpInterestList: any[] = [];
  fpVitalInfoDataDTOList: any[] = [];
  fpOtherInfoDataDTOList: any[] = [];
  fpRentalDataDTOList: any[] = [];
  fpCustomInfoDataDTOList: any[] = [];
  organizedSecurityList = [];
  currencyViseTotalIndividualCashAmount = {};
  individualCashAmounts: any[] = [];
  covenants: any[] = [];
  ESBCreditScheduleServiceResponse: any = {};
  modalRef: MDBModalRef;
  esbCreditCalculatorResponseStatusConst =
    Constants.esbCreditCalculatorResponseStatusConst;
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  covenantVal: any[] = [];

  facilityCovenants: any[] = [];
  facilityDetails: any[] = [];

  covenantsWithSingleFacility: any[] = [];
  covenantsWithMultipleFacilities: any[] = [];

  @Output("toCommonSecurityContent")
  toCommonSecurityContent: EventEmitter<number> = new EventEmitter();

  tableColumnsForFacilityInterestRate = ["Rate Code", "Value", "Comment"];
  tableColumnsForFacilityCovenants = ["Description", "Freaquency", "Due Date", "Applicable Type"];

  constructor(
    private currencyPipe: CurrencyPipe,
    private cacheService: CacheService,
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.getFacilityCovenants();

    this.sectorList =
      this.cacheService.getData(Constants.masterDataKey.CAS_SECTOR_DATA) || [];
    this.subSectorList =
      this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA)
        .subSectorDTOList || [];
    this.purposeOfAdvancedList =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED
      ) || [];

    _.forEach(this.purposeOfAdvancedList, (purposeOfAdvanced) => {
      this.purposeOfAdvancedMap[purposeOfAdvanced.referenceCode] =
        purposeOfAdvanced.referenceCode +
        " - " +
        purposeOfAdvanced.referenceDescription;
    });

    this.applicablefpInterestList = _.sortBy(
      _.filter(
        this.facilityData.facilityInterestRateList,
        (interestRate) => interestRate.value >= 0
      ),
      (i: any) => i.facilityInterestRateID
    );

    if (this.facilityData.parentFacilityID) {
      this.parentFacility = _.find(this.creditFacilityList, {
        facilityID: this.facilityData.parentFacilityID,
      });
    }

    this.fpVitalInfoDataDTOList =
      _.cloneDeep(this.facilityData.facilityVitalInfoDataDTOList) || [];
    this.fpOtherInfoDataDTOList =
      _.sortBy(
        _.cloneDeep(this.facilityData.facilityOtherFacilityInformationDTOList),
        ["displayOrder"]
      ) || [];
    this.fpRentalDataDTOList =
      _.cloneDeep(this.facilityData.facilityRentalInformationDTOList) || [];
    this.fpCustomInfoDataDTOList =
      _.sortBy(_.cloneDeep(this.facilityData.facilityCustomInfoDataDTOList), [
        "displayOrder",
      ]) || [];
    //this.fpCustomInfoDataDTOList = _.cloneDeep(this.facilityData.facilityCustomInfoDataDTOList) || [];
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

    this.ESBCreditScheduleServiceResponse = _.find(
      this.creditScheduleESBResponseStatusList,
      (data: any) => {
        return data.facilityId == this.facilityData.facilityID;
      }
    );
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "");
    }
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

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
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

  // getFacilityCovenants() {
  //   this.facilityPaperAddEditService.getFacilityCovenantList().then((data) => {
  //     console.log("data", data);
  //     this.covenants = data

  //     .map(result => {
  //       const activeCovValues = result.covValue
  //         .filter(covValue => covValue.status === 'Active')
  //         .map(covValue => ({
  //           ...covValue,
  //           applicationCovenantFacilityDTOS: covValue.applicationCovenantFacilityDTOS
  //             .sort((a, b) => a.displayOrder - b.displayOrder)
  //         }))
  //         .filter(covValue => covValue.applicationCovenantFacilityDTOS.length > 0);

  //       if (activeCovValues.length > 0) {
  //         return {
  //           ...result,
  //           covValue: activeCovValues.sort((a, b) => a.applicationCovenantFacilityDTOS[0].displayOrder - b.applicationCovenantFacilityDTOS[0].displayOrder)
  //         };
  //       } else {
  //         return null; // Return null for items with no active covValues
  //       }
  //     })
  //     .filter(result => result !== null)
  //     .sort((a, b) => a.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder - b.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder);
  //   });
  // }

  getFacilityCovenants() {
    this.facilityPaperAddEditService.getFacilityCovenantList().then((data) => {
      this.covenantsWithSingleFacility = [];
      this.covenantsWithMultipleFacilities = [];

      data.forEach((result) => {
        const activeCovValues = result.covValue.filter(
          (covValue) => covValue.status === "Active"
        );

        activeCovValues.forEach((covValue) => {
          if (
            covValue.applicationCovenantFacilityDTOS.length === 1 &&
            covValue.applicationCovenantFacilityDTOS[0].facilityID ===
              this.facilityData.facilityID
          ) {
            this.covenantsWithSingleFacility.push({
              ...result,
              covValue: [covValue],
            });
          } else if (covValue.applicationCovenantFacilityDTOS.length > 1) {
            this.covenantsWithMultipleFacilities.push({
              ...result,
              covValue: [covValue],
            });
          }
        });
      });
    });
  }

  getCovenantFrequencyLabel(frequencyValue) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  computeCovenantCounters() {
    return this.covenants.filter((covenant) => covenant.status === "Active");
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  getDisbursementTypeClass(disbursementType: string): string {
    switch (disbursementType) {
      case "PRE":
        return "disbursement-pre";
      case "POST":
        return "disbursement-post";
      default:
        return "disbursement-default";
    }
  }

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }
}
