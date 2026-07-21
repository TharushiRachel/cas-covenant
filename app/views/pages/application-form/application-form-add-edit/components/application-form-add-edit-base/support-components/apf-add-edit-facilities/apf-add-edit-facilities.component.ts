import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Constants} from "../../../../../../../../core/setting/constants";
import {Subject, Subscription} from "rxjs";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {MasterDataService} from "../../../../../../../../core/service/data/master-data.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";

@Component({
  selector: 'app-apf-add-edit-facilities',
  templateUrl: './apf-add-edit-facilities.component.html',
  styleUrls: ['./apf-add-edit-facilities.component.scss']
})
export class ApfAddEditFacilitiesComponent implements OnInit, OnDestroy {
  currencyTypesOpt = Constants.currencyTypesOpt;

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any;
  facility: any = {};
  applicationForm: any = {};

  facilityTypeList = [];
  creditFacilityGroups = [];
  creditFacilityGroupWiseTemplateMap = {};
  creditFacilityTemplateList: any = [];
  creditFacilityTemplateOptionList: any[] = [];
  creditFacilityInterestRates: any[] = [];
  creditFacilityInterestRateMap = {};
  purposeOfAdvancedList: any = [];
  purposeOfAdvancedOptionList: any = [];
  purposeOfAdvancedMap: any = {};
  parentFacility: any = {};

  sectorList: any[] = [];
  subSectorList: any[] = [];
  sectorOptionList: any[] = [];
  subSectorOptionList: any[] = [];
  cashFlowGenerationSectorList: any[] = [];
  subSectorMap: any = {};
  selectedSubSectorMap: any = {};
  creditFacilityList: any[] = [];
  creditFacilityOptionList: any[] = [];

  yesNo = Constants.yesNo;
  currencyTypesConst = Constants.currencyTypesConst;
  afInterestList: any[] = [];
  fpVitalInfoDataDTOList: any[] = [];
  vitalInfoContolNames: any[] = [];
  editField: string;
  isValueUpdated: boolean = false;
  showCondition: boolean = false;
  showPurpose: boolean = false;
  showRemark: boolean = false;
  showRepayment: boolean = false;
  isFacilityOutstandingAmountValidationEnabled: boolean = false;

  tableColumnsForFacilityInterestRate = ['Rate Name', 'Rate Code', 'Value (%)', 'Comment'];

  action: Subject<any> = new Subject<any>();
  onFPFacilityChangeSub = new Subscription();
  onFormValueChangeSub = new Subscription();
  onFormChangeSub = new Subscription();
  onFormCFTChangeSub = new Subscription();
  isNewValueChangeSubs = new Subscription();
  onApplicationFormChangeSub = new Subscription();

  constructor(private applicationFormCreateService: ApplicationFormAddEditService,
              private cacheService: CacheService,
              private formBuilder: FormBuilder,
              private alertService: AlertService,
              public  mdbModalRef: MDBModalRef,
              private currencyPipe: CurrencyPipe,
              private masterDataService: MasterDataService,
  ) {
    this.formErrors = {
      creditFacilityTypeID: {},
      creditFacilityTemplateID: {},
      facilityCurrency: {},
      disbursementAccNumber: {},
      facilityAmount: {},
      outstandingAmount: {},
      sectorID: {},
      subSectorID: {},
      cashFlowGenerationSectorID: {},
      purposeOfAdvance: {},
      parentFacility: {},
      purpose: {},
      isOneOff: {},
      repayment: {},
      facilityRefCode: {},
      condition: {},
      isNew: {},
      remark: {},
      status: {}
    }
  }


  isNewFacility() {
    return _.isEmpty(this.facility);
  }

  ngOnInit() {


    this.facility = this.content.facility ? this.content.facility : {};

    this.onApplicationFormChangeSub = this.applicationFormCreateService.onAFFacilitiesChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
      }
    });

    this.creditFacilityList = this.content.creditFacilityList;
    this.isFacilityOutstandingAmountValidationEnabled = this.masterDataService.getSystemParameter(Constants.systemParamKey.FACILITY_OUTSTANDING_AMOUNT_VALIDATION_ENABLED);


    this.facilityTypeList = this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES) || [];
    _.forEach(this.facilityTypeList, cfType => {
      if (cfType.status == 'ACT' || this.facility.creditFacilityTypeID == cfType.creditFacilityTypeID)
        this.creditFacilityGroups.push({
          value: cfType.creditFacilityTypeID,
          label: cfType.facilityTypeName
        });
    });

    this.creditFacilityTemplateList = this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES) || [];
    _.forEach(this.creditFacilityTemplateList, cfTemplate => {

      if (cfTemplate.status == 'ACT' || this.facility.creditFacilityTemplateID == cfTemplate.creditFacilityTemplateID) {
        if (this.creditFacilityGroupWiseTemplateMap[cfTemplate.creditFacilityTypeID] == undefined) {
          this.creditFacilityGroupWiseTemplateMap[cfTemplate.creditFacilityTypeID] = [];
        }

        this.creditFacilityGroupWiseTemplateMap[cfTemplate.creditFacilityTypeID] = [
          ...this.creditFacilityGroupWiseTemplateMap[cfTemplate.creditFacilityTypeID],
          cfTemplate
        ]
      }
    });

    this.creditFacilityTemplateOptionList = [];
    let templateList = this.creditFacilityGroupWiseTemplateMap[this.facility.creditFacilityTypeID];

    _.forEach(templateList, (cfTemplate) => {
      this.creditFacilityTemplateOptionList.push({
        value: cfTemplate.creditFacilityTemplateID,
        label: cfTemplate.creditFacilityName
      });
    });

    let creditFacilityGroupCopy = _.cloneDeep(this.creditFacilityGroups);
    this.creditFacilityGroups.forEach((creditFacilityGroup) => {
      let templateList = this.creditFacilityGroupWiseTemplateMap[creditFacilityGroup.value];

      if (!templateList || templateList.length === 0) {
        _.remove(creditFacilityGroupCopy, (i) => i.value === creditFacilityGroup.value);
      }
    });

    this.creditFacilityGroups = creditFacilityGroupCopy;

    this.sectorList = this.cacheService.getData(Constants.masterDataKey.CAS_SECTOR_DATA) || [];
    _.forEach(this.sectorList, sectorData => {
      this.sectorOptionList.push({
        value: sectorData.sectorID,
        label: sectorData.referenceCode + "-" + sectorData.referenceDescription
      });
    });
    if (this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA)) {
      this.subSectorList = this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA).subSectorDTOList || [];
      this.subSectorMap = this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA).sectorWiseSubSectorMap || {};
    }

    _.forEach(this.subSectorList, data => {
      this.subSectorOptionList.push({
        value: data.subSectorID,
        label: data.referenceDescription
      });
    });

    this.cashFlowGenerationSectorList = this.subSectorOptionList;

    this.purposeOfAdvancedList = this.cacheService.getData(Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED) || [];
    _.forEach(this.purposeOfAdvancedList, pusposeOfAdvanced => {
      this.purposeOfAdvancedOptionList.push({
        value: pusposeOfAdvanced.referenceCode,
        label: pusposeOfAdvanced.referenceCode + "-" + pusposeOfAdvanced.referenceDescription
      });
      this.purposeOfAdvancedMap[pusposeOfAdvanced.referenceCode] = pusposeOfAdvanced;
    });

    this.creditFacilityInterestRates = this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES) || [];
    _.forEach(this.creditFacilityInterestRates, cftInterestRate => {
      this.creditFacilityInterestRateMap[cftInterestRate.cftInterestRateID] = cftInterestRate.rateName;
    });
    this.afInterestList = _.cloneDeep(this.facility.afFacilityInterestRateDTOList) || [];
    this.fpVitalInfoDataDTOList = _.cloneDeep(this.facility.afFacilityVitalInfoDataDTOList) || [];

    _.forEach(this.creditFacilityList, data => {
      if (data.facilityID != this.facility.facilityID) {
        this.creditFacilityOptionList.push(
          {
            value: data.facilityID,
            label: data.creditFacilityTemplateDTO.creditFacilityName + (data.facilityRefCode ? " - " + data.facilityRefCode : '')
          });
      }
    });

    this.componentForm = this.createForm();

    this.onFormChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.subSectorOptionList = [];
      let subSectorList = this.subSectorMap[form.sectorID] || [];
      _.forEach(subSectorList, data => {
        this.subSectorOptionList.push({
          value: data.subSectorID,
          label: data.referenceDescription
        });
      });

      this.creditFacilityTemplateOptionList = [];
      let creditFacilityTypeID = form.creditFacilityTypeID ? form.creditFacilityTypeID : this.facility.creditFacilityTypeID;
      let templateList = this.creditFacilityGroupWiseTemplateMap[creditFacilityTypeID];
      _.forEach(templateList, (cfTemplate) => {
        this.creditFacilityTemplateOptionList.push({
          value: cfTemplate.creditFacilityTemplateID,
          label: cfTemplate.creditFacilityName
        });
      });

      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

    this.onFormChangeSub = this.componentForm.get('sectorID').valueChanges.subscribe(sectorID => {
      let subSectorList = this.subSectorMap[sectorID] || [];
      if (sectorID && subSectorList && subSectorList.length > 0) {
        this.componentForm.get('subSectorID').setValue(subSectorList[0].subSectorID);
      }
    });

    this.onFormChangeSub = this.componentForm.get('creditFacilityTypeID').valueChanges.subscribe(creditFacilityTypeID => {
      let templateList = this.creditFacilityGroupWiseTemplateMap[creditFacilityTypeID] || [];
      if (creditFacilityTypeID && templateList && templateList.length > 0) {
        this.componentForm.get('creditFacilityTemplateID').setValue(templateList[0].creditFacilityTemplateID);
      }
    });

    this.isNewValueChangeSubs = this.componentForm.controls.isNew.valueChanges.subscribe((isNew: boolean) => {
      if (isNew) {
        this.componentForm.controls.outstandingAmount.clearValidators();
      } else {
        this.componentForm.controls.outstandingAmount.setValidators([
          Validators.required, NumberValidator.maxLengthOfNumber(18)
        ]);
      }

      this.componentForm.controls.outstandingAmount.updateValueAndValidity({onlySelf: false, emitEvent: true});
    });

    this.onFormCFTChangeSub = this.componentForm.controls['creditFacilityTemplateID'].valueChanges.subscribe(creditFacilityTemplateID => {
      this.componentForm = this.createDynamicFormFields(creditFacilityTemplateID, true);
    });


    this.onFormChangeSub = this.componentForm.controls.enhancement.valueChanges.subscribe(enhancement => {
      if (enhancement) {
        this.componentForm.get('reduction').setValue(false);
      }
    });

    this.onFormChangeSub = this.componentForm.controls.reduction.valueChanges.subscribe(reduction => {
      if (reduction) {
        this.componentForm.get('enhancement').setValue(false);
      }
    });

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

  }

  ngOnDestroy(): void {
    this.onFPFacilityChangeSub.unsubscribe();
    this.onFormChangeSub.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
    this.onFormCFTChangeSub.unsubscribe();
    this.isNewValueChangeSubs.unsubscribe();
    this.onApplicationFormChangeSub.unsubscribe();
  }

  createForm() {
    let status = this.facility.status || 'ACT';

    let isNew = this.facility.isNew == 'Y';

    let controlConfig = {
      creditFacilityTypeID: [{
        value: this.facility.creditFacilityTypeID,
        disabled: !!this.facility.facilityID
      }, [Validators.required]],
      creditFacilityTemplateID: [{
        value: this.facility.creditFacilityTemplateID,
        disabled: !!this.facility.facilityID
      }, [Validators.required]],
      parentFacilityID: [this.facility.parentFacilityID],
      facilityRefCode: [this.facility.facilityRefCode, [Validators.required]],
      isNew: [isNew, []],
      directFacility: [this.facility.directFacility == 'Y', []],
      isOneOff: [this.facility.isOneOff == 'Y', []],
      seriesOfLoans: [this.facility.seriesOfLoans == 'Y', []],
      revolving: [this.facility.revolving == 'Y', []],
      reduction: [this.facility.reduction == 'Y', []],
      enhancement: [this.facility.enhancement == 'Y', []],
      facilityCurrency: [this.facility.facilityCurrency ? this.facility.facilityCurrency : this.currencyTypesConst.LKR, Validators.required],
      facilityAmount: [this.getCurrencyFormat(this.facility.facilityAmount), [Validators.required, NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
      outstandingAmount: [this.getCurrencyFormat(this.facility.outstandingAmount), [
        !isNew ? Validators.required : NumberValidator.maxLengthOfNumber(18),
        NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
      disbursementAccNumber: [this.facility.disbursementAccNumber, [Validators.maxLength(100), NumberValidator.isCommaSeparatedValue]],
      sectorID: [this.facility.sectorID, [Validators.required]],
      subSectorID: [this.facility.subSectorID, [Validators.required]],
      cashFlowGenerationSectorID: [this.facility.cashFlowGenerationSectorID, [Validators.required]],
      purposeOfAdvance: [this.facility.purposeOfAdvance],
      status: [status, [Validators.required]]
    };

    if (this.facility && !_.isEmpty(this.facility.afFacilityVitalInfoDataDTOList)) {
      let vitalInfoDataControlConfig = {};
      _.forEach(this.facility.afFacilityVitalInfoDataDTOList, (vitalInfoData) => {
        let isMandatory = (Constants.yesNoConst.Y == vitalInfoData.mandatory) ? [Validators.required] : [];
        vitalInfoDataControlConfig["vitalInfo-" + vitalInfoData.cftVitalInfoID] = [vitalInfoData.vitalInfoData, [...isMandatory]]
      });

      controlConfig = Object.assign({}, controlConfig, vitalInfoDataControlConfig)
    }

    this.componentForm = this.formBuilder.group(controlConfig);
    this.componentForm = this.createDynamicFormFields(this.facility.creditFacilityTemplateID, false);

    return this.componentForm;
  }

  updateList(id: number, property: string, event: any) {
    const newValue = _.toNumber(event.target.textContent);
    const preValue = this.afInterestList[id][property];
    this.isValueUpdated = false;

    if (_.isFinite(newValue) && preValue != newValue) {
      this.afInterestList[id][property] = newValue;
      this.isValueUpdated = true;
    }
  }

  updateUserComment(id: number, property: string, event: any) {
    const newValue = event.target.textContent;
    this.afInterestList[id][property] = newValue;
    this.isValueUpdated = true;
  }

  changeValue(id: number, property: string, event: any) {
    this.editField = event.target.textContent;
  }

  getFormRawData() {
    let rawData = this.componentForm.getRawValue();

    if (!_.isEmpty(this.fpVitalInfoDataDTOList)) {
      _.map(this.fpVitalInfoDataDTOList, (vitalInfoData) => {
        vitalInfoData.vitalInfoData = rawData["vitalInfo-" + vitalInfoData.cftVitalInfoID];
        delete rawData["vitalInfo-" + vitalInfoData.cftVitalInfoID];
      });
    }

    return rawData;
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.getFormRawData()[control]);
    this.componentForm.patchValue({
      [control]: this.currencyPipe.transform(amount, '', '')
    });
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  onAddEdit() {
    let facilityList = this.applicationForm.afFacilityDTOList || [];
    let displayOrder = this.facility.displayOrder || facilityList.length + 1;
    let formData = this.getFormRawData();
    let fpFacility = Object.assign({},
      this.facility,
      {displayOrder: displayOrder},
      formData,
      {applicationFormID: this.applicationForm.applicationFormID});

    fpFacility.facilityAmount = this.getValue(formData.facilityAmount);

    if (this.markedAsNewFacility()) {
      fpFacility.outstandingAmount = 0;
    } else {
      fpFacility.outstandingAmount = this.getValue(formData.outstandingAmount);

      if (this.isFacilityOutstandingAmountValidationEnabled) {
        if (parseFloat(fpFacility.outstandingAmount) < parseFloat(fpFacility.facilityAmount)) {
          this.alertService.showToaster("Outstanding Amount should be lesser than the Previous Amount", SETTINGS.TOASTER_MESSAGES.warning);
          return;
        }
      }
    }

    fpFacility.isNew = formData.isNew ? 'Y' : 'N';
    fpFacility.isOneOff = formData.isOneOff ? 'Y' : 'N';
    fpFacility.directFacility = formData.directFacility ? 'Y' : 'N';
    fpFacility.seriesOfLoans = formData.seriesOfLoans ? 'Y' : 'N';
    fpFacility.revolving = formData.revolving ? 'Y' : 'N';
    fpFacility.reduction = formData.reduction ? 'Y' : 'N';
    fpFacility.enhancement = formData.enhancement ? 'Y' : 'N';
    fpFacility.isCooperate = this.applicationForm.isCooperate;

    fpFacility.afFacilityInterestRateDTOList = this.afInterestList;
    fpFacility.afFacilityVitalInfoDataDTOList = this.fpVitalInfoDataDTOList;

    this.action.next(fpFacility);
    this.applicationFormCreateService.saveOrUpdateAFFacility(fpFacility);
    this.mdbModalRef.hide();
  }

  onRemove() {
    let fpFacility = Object.assign({},
      this.facility,
      {status: 'INA'});
    this.action.next(fpFacility);
    this.applicationFormCreateService.saveOrUpdateAFFacility(fpFacility);
  }

  markedAsNewFacility() {
    return this.componentForm && this.componentForm.controls.isNew.value;
  }

  isValid() {
    return this.componentForm.valid;
  }

  createDynamicFormFields(creditFacilityTemplateID, isNew) {
    if (creditFacilityTemplateID) {
      let cfTemplate = _.find(this.creditFacilityTemplateList, (cfTemplate) => cfTemplate.creditFacilityTemplateID == creditFacilityTemplateID);

      if (cfTemplate.showCondition == 'Y') {
        this.componentForm.addControl("condition", new FormControl(this.facility.condition, [Validators.required]));
        this.showCondition = true;
      } else {
        this.componentForm.removeControl("condition");
        this.showCondition = false;
      }

      if (cfTemplate.showPurpose == 'Y') {
        this.componentForm.addControl("purpose", new FormControl(this.facility.purpose, [Validators.required, Validators.maxLength(2000)]));
        this.showPurpose = true;
      } else {
        this.componentForm.removeControl("purpose");
        this.showPurpose = false;
      }

      if (cfTemplate.showRemark == 'Y') {
        this.componentForm.addControl("remark", new FormControl(this.facility.remark, [Validators.required, Validators.maxLength(2000)]));
        this.showRemark = true;
      } else {
        this.componentForm.removeControl("remark");
        this.showRemark = false;
      }

      if (cfTemplate.showRepayment == 'Y') {
        this.componentForm.addControl("repayment", new FormControl(this.facility.repayment, [Validators.required, Validators.maxLength(2000)]));
        this.showRepayment = true;
      } else {
        this.componentForm.removeControl("repayment");
        this.showRepayment = false;
      }


      if (isNew) {
        // This is fpInterest and fpVitalInfoDataDTOList List is loaded when only adding new Facility
        this.afInterestList = _.cloneDeep(cfTemplate.cftInterestRateDTOList) || [];
        this.fpVitalInfoDataDTOList = _.cloneDeep(cfTemplate.cftVitalInfoDTOList) || [];

        _.forEach(this.vitalInfoContolNames, (controlName) => {
          this.componentForm.removeControl(controlName);
        });
        this.vitalInfoContolNames = [];

        _.forEach(this.fpVitalInfoDataDTOList, (vitalInfoData) => {
          let isMandatory = (Constants.yesNoConst.Y == vitalInfoData.mandatory) ? Validators.required : null;
          this.componentForm.addControl("vitalInfo-" + vitalInfoData.cftVitalInfoID, new FormControl('', isMandatory));
          this.vitalInfoContolNames.push("vitalInfo-" + vitalInfoData.cftVitalInfoID);
        });
      }

    } else {
      this.fpVitalInfoDataDTOList = [];
      this.afInterestList = [];
    }
    return this.componentForm;
  }
}
