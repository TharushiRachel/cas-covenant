import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Constants } from "../../../../core/setting/constants";
import { CurrencyPipe } from "@angular/common";
import { CacheService } from "../../../../core/service/data/cache.service";
import * as _ from "lodash";
import { AppUtils } from "../../../../shared/app.utils";

@Component({
  selector: "app-preview-three-col-facility-data",
  templateUrl: "./preview-three-col-facility-data.component.html",
  styleUrls: ["./preview-three-col-facility-data.component.scss"],
})
export class PreviewThreeColFacilityDataComponent implements OnInit {
  @Input("facilityData") facilityData;
  @Input("facilityPaper") facilityPaper;
  @Input("creditFacilityList") creditFacilityList;
  @Input("currentIndex") currentIndex;
  yesNoConst = Constants.yesNoConst;
  parentFacility: any = {};
  purposeOfAdvancedList = [];
  purposeOfAdvancedMap = {};
  sectorList: any[] = [];
  subSectorList: any[] = [];
  applicablefpInterestList: any[] = [];
  fpVitalInfoDataDTOList: any[] = [];
  organizedSecurityList = [];
  totalIndividualSecuritiesCashAmount = 0;

  @Output("toCommonSecurityContent")
  toCommonSecurityContent: EventEmitter<number> = new EventEmitter();

  tableColumnsForFacilityInterestRate = ["Rate Code", "Value", "Comment"];
  isCommittee: boolean = false;
  constructor(
    private currencyPipe: CurrencyPipe,
    private cacheService: CacheService
  ) {}

  ngOnInit() {
    if (this.facilityPaper) {
      this.isCommittee =
        this.facilityPaper.isCommittee == Constants.yesNoConst.Y;
    }

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
      _.filter(this.facilityData.facilityInterestRateList, (interestRate) => interestRate.value >= 0),
      (i: any) => i.facilityInterestRateID);

    if (this.facilityData.parentFacilityID) {
      this.parentFacility = _.find(this.creditFacilityList, {
        facilityID: this.facilityData.parentFacilityID,
      });
    }

    this.fpVitalInfoDataDTOList =
      _.cloneDeep(this.facilityData.facilityVitalInfoDataDTOList) || [];

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
      if (e.isCommonSecurity == "N") {
        this.totalIndividualSecuritiesCashAmount =
          this.totalIndividualSecuritiesCashAmount + e.cashAmount;
      }
    });
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

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
  }

  getFormattedThreeDecimalValues(amount:any) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }
}
