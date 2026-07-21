import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {CurrencyPipe} from "@angular/common";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import * as _ from "lodash";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfFacilityDocumentPreviewComponent} from "./apf-facility-document-preview/apf-facility-document-preview.component";

@Component({
  selector: 'app-apf-facility-data-preview',
  templateUrl: './apf-facility-data-preview.component.html',
  styleUrls: ['./apf-facility-data-preview.component.scss']
})
export class ApfFacilityDataPreviewComponent implements OnInit {
  modalRef: MDBModalRef;
  @Input("facilityData") facilityData;
  @Input("creditFacilityList") creditFacilityList;
  @Input("isPreviewMode") isPreviewMode: boolean;
  @Input("currentIndex") currentIndex;
  yesNoConst = Constants.yesNoConst;
  parentFacility: any = {};
  purposeOfAdvancedList = [];
  purposeOfAdvancedMap = {};
  sectorList: any[] = [];
  subSectorList: any[] = [];
  applicablefpInterestList: any[] = [];
  afFacilityVitalInfoDataDTOList: any[] = [];
  organizedSecurityList = [];
  totalIndividualSecuritiesCashAmount = 0;

  @Output("toCommonSecurityContent") toCommonSecurityContent: EventEmitter<number> = new EventEmitter();

  tableColumnsForFacilityInterestRate = ['Rate Code', 'Value', 'Comment'];


  constructor(private currencyPipe: CurrencyPipe, private cacheService: CacheService, private mdbModalService: MDBModalService) {
  }

  ngOnInit() {

    this.sectorList = this.cacheService.getData(Constants.masterDataKey.CAS_SECTOR_DATA) || [];
    this.subSectorList = this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA).subSectorDTOList || [];
    this.purposeOfAdvancedList = this.cacheService.getData(Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED) || [];

    _.forEach(this.purposeOfAdvancedList, purposeOfAdvanced => {
      this.purposeOfAdvancedMap[purposeOfAdvanced.referenceCode] = purposeOfAdvanced.referenceCode + " - " + purposeOfAdvanced.referenceDescription;
    });

    this.applicablefpInterestList = _.sortBy(
      _.filter(this.facilityData.facilityInterestRateList, (interestRate) => interestRate.value > 0),
      (i: any) => i.facilityInterestRateID);

    if (this.facilityData.parentFacilityID) {
      this.parentFacility = _.find(this.creditFacilityList, {facilityID: this.facilityData.parentFacilityID});
    }

    this.afFacilityVitalInfoDataDTOList = _.cloneDeep(this.facilityData.afFacilityVitalInfoDataDTOList) || [];

    let commonSecurities = [];
    let individualSecurities = [];
    this.facilityData.afSecurityDTOList.forEach(security => {
      if (security.isCommonSecurity == Constants.yesNoConst.Y) {
        commonSecurities.push(security);
      } else {
        individualSecurities.push(security);
      }
    });

    this.organizedSecurityList = _.concat(_.sortBy(_.uniqBy(individualSecurities, 'securityID'), 'securityID'), _.sortBy(_.uniqBy(commonSecurities, 'securityID'), 'securityID'));

    this.organizedSecurityList.forEach(e => {
      if (e.isCommonSecurity == 'N') {
        this.totalIndividualSecuritiesCashAmount = this.totalIndividualSecuritiesCashAmount + e.cashAmount;
      }
    });
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  getReferenceDescriptionForSectorId(sectorID) {
    let referenceDescription = "";
    if (this.sectorList.length > 0) {
      this.sectorList.forEach((sector) => {
        if (sector.sectorID == sectorID) {
          return referenceDescription = sector.referenceDescription;
        }
      })
    }
    return referenceDescription;
  }

  getReferenceDescriptionForSubSectorId(subSectorID) {
    let referenceDescription = "";
    if (this.subSectorList.length > 0) {
      this.subSectorList.forEach((subSector) => {
        if (subSector.subSectorID == subSectorID) {
          return referenceDescription = subSector.referenceDescription;
        }
      })
    }
    return referenceDescription;
  }

  getReferenceDescriptionForCashFlowGenerationSectorID(shgfID) {
    let referenceDescription = "";
    if (this.subSectorList.length > 0) {
      this.subSectorList.forEach((subSector) => {
        if (subSector.subSectorID == shgfID) {
          return referenceDescription = subSector.referenceDescription;
        }
      })
    }

    return referenceDescription;
  }

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
  }

  openModalFacilityDocument($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfFacilityDocumentPreviewComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p audit-modal-margin-center',
      containerClass: '',
      animated: true,
      data: {
        heading: "Facility Documents",
        content: {
          facilityData: this.facilityData,
        },
      }
    });
  }

  isAbleToPreviewDocuments() {
    if (!_.isEmpty(this.facilityData.creditFacilityTemplateDTO)) {
      return this.facilityData.creditFacilityTemplateDTO.cftSupportingDocDTOList.length > 0;
    } else {
      return false;
    }
  }

}
