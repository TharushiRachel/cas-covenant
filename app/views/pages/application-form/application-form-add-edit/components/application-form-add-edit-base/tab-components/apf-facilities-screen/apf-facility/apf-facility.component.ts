import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CurrencyPipe} from "@angular/common";
import {Constants} from "../../../../../../../../../core/setting/constants";
import * as _ from "lodash";
import {CacheService} from "../../../../../../../../../core/service/data/cache.service";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfFacilityDocumentComponent} from "../../../support-components/apf-facility-document/apf-facility-document.component";

@Component({
  selector: 'app-apf-facility',
  templateUrl: './apf-facility.component.html',
  styleUrls: ['./apf-facility.component.scss']
})
export class ApfFacilityComponent implements OnInit {
  @Input("facilityData") facilityData;
  @Input("applicationForm") applicationForm;
  @Input("facilityList") facilityList;
  @Input("lastIndex") lastIndex;
  @Input("currentIndex") currentIndex;
  @Output("setTabIndex") setTabIndex: EventEmitter<number> = new EventEmitter();
  @Output("onUpdateFacility") onUpdateFacility: EventEmitter<any> = new EventEmitter();
  purposeOfAdvancedMap = {};
  parentFacility: any = {};
  sectorList = [];
  purposeOfAdvancedList = [];
  subSectorList = [];
  interestList = [];
  vitalInfoDataDTOList = [];
  modalRef: MDBModalRef;

  yesNoConst = Constants.yesNoConst;

  constructor(private currencyPipe: CurrencyPipe,
              private cacheService: CacheService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private mdbModalService: MDBModalService) {
  }

  ngOnInit() {
    this.sectorList = this.cacheService.getData(Constants.masterDataKey.CAS_SECTOR_DATA) || [];
    this.subSectorList = this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA).subSectorDTOList || [];
    this.purposeOfAdvancedList = this.cacheService.getData(Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED) || [];

    this.interestList = _.sortBy(
      _.filter(this.facilityData.afFacilityInterestRateDTOList, (interestRate) => interestRate.value > 0),
      (i: any) => i.facilityInterestRateID);

    this.vitalInfoDataDTOList = _.cloneDeep(this.facilityData.afFacilityVitalInfoDataDTOList) || [];

    _.forEach(this.purposeOfAdvancedList, pusposeOfAdvanced => {
      this.purposeOfAdvancedMap[pusposeOfAdvanced.referenceCode] = pusposeOfAdvanced.referenceCode + " - " + pusposeOfAdvanced.referenceDescription;
    });

    if (this.facilityData.parentFacilityID) {
      this.parentFacility = _.find(this.facilityList, {facilityID: this.facilityData.parentFacilityID});
    }
  }

  showPreviousTab($event) {
    let index = this.currentIndex;
    if (index != 0) {
      this.setTabIndex.emit(--index)
    }
  }

  showNextTab($event) {
    let index = this.currentIndex;
    if (index != this.lastIndex) {
      this.setTabIndex.emit(++index)
    }
  }

  onUpdate($event) {
    this.onUpdateFacility.emit($event);
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

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  openModalFacilityDocument($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfFacilityDocumentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p audit-modal-margin-center',
      containerClass: '',
      animated: true,
      data: {
        heading: "Add/Edit Facility Document",
        content: {
          facilityData: this.facilityData,
          applicationForm: this.applicationForm,
        },
      }
    });
  }

  isAbleToAddDocuments() {
    if (!_.isEmpty(this.facilityData.creditFacilityTemplateDTO)) {
      return this.facilityData.creditFacilityTemplateDTO.cftSupportingDocDTOList.length > 0;
    } else {
      return false;
    }
  }

}
