import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {SETTINGS} from "../../../core/setting/commons.settings";
import {Constants} from "../../../core/setting/constants";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PrivilegeService} from "../../../core/service/authentication/privilege.service";
import {CurrencyPipe} from "@angular/common";
import {ApplicationService} from "../../../core/service/application/application.service";
import {CacheService} from "../../../core/service/data/cache.service";
import * as _ from "lodash";
import {NumberValidator} from "../../../shared/validators/number.validator";
import {AppUtils} from "../../../shared/app.utils";
import {MnCurrencyService} from "../../../core/service/common/mn-currency.service";

@Component({
  selector: 'app-preview-security-summary',
  templateUrl: './preview-security-summary.component.html',
  styleUrls: ['./preview-security-summary.component.scss']
})
export class PreviewSecuritySummaryComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  @Input('facilityPaper') facilityPaper: any = {};
  @Input('isView') isView: boolean = false;
  @Input('isCommitteeTab') isCommitteeTab: boolean = false;
  isPreviewMode: boolean = true;

  onFPaperSecSummeryChangeSub = new Subscription();
  onSecuritySummeryFormChangeSub = new Subscription();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilitySecuritySummaryType = Constants.facilitySecuritySummaryTypeConst.INDIVIDUAL;
  hasPrivilege = true;
  isDisabled = true;

  componentForm: FormGroup;
  firstClassSecuritySummaryTopicsDTOs: FormArray;
  otherSecuritySummaryTopicsDTOs: FormArray;
  defaultSecuritySummaryTopicsDTOs: FormArray;
  fpSecuritySummeryDTO: any = {};

  securitySummaryTopics: any[] = [];
  firstClassSecuritySummaryTopics: any[] = [];
  optionsFirstClassSecuritySummaryTopics: any[] = [];
  otherSecuritySummaryTopics: any[] = [];
  optionsOtherSecuritySummaryTopics: any[] = [];
  defaultSecuritySummaryTopics: any[] = [];
  optionsDefaultSecuritySummaryTopics: any[] = [];

  subTotalFirstClassCompany: number = 0;
  subTotalFirstClassCompanyPercentage;
  subTotalFirstClassGroup: number = 0;
  subTotalFirstClassGroupPercentage;
  subTotalFirstClassPlusOthersCompany: number = 0;
  subTotalFirstClassPlusOthersCompanyPercentage;
  subTotalFirstClassPlusOthersGroup: number = 0;
  subTotalFirstClassPlusOthersGroupPercentage;
  totalCompany: number = 0;
  totalCompanyPercentage;
  totalGroup: number = 0;
  totalGroupPercentage;

  subTotalOtherCompany: number = 0;
  subTotalOtherCompanyPercentage;
  subTotalOtherCompanyGroup: number = 0;
  subTotalOtherCompanyGroupPercentage;
  subTotalClean: number = 0;
  subTotalCleanPercentage;
  subTotalCleanGroup: number = 0;
  subTotalCleanGroupPercentage;


  constructor(private privilegeService: PrivilegeService,
              private formBuilder: FormBuilder,
              private currencyPipe: CurrencyPipe,
              public mnCurrencyService: MnCurrencyService,
              private applicationService: ApplicationService,
              private cacheService: CacheService,
  ) {
    this.componentForm = this.createForm();

  }


  ngOnInit() {

    this.securitySummaryTopics = this.cacheService.getData(Constants.masterDataKey.CAS_SECURITY_SUMMARY_TOPICS) || [];
    this.securitySummaryTopics.forEach(data => {
      switch (data.securityTypeGroup) {
        case  'FIRST_CLASS':
          this.firstClassSecuritySummaryTopics.push(data);
          break;
        case  'OTHER':
          this.otherSecuritySummaryTopics.push(data);
          break;
        case  'DEFAULT':
          this.defaultSecuritySummaryTopics.push(data);
          break;
      }
    });

    this.hasPrivilege = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT);
    this.isDisabled = !this.isAbleToUpdate();


    this.fpSecuritySummeryDTO = this.facilityPaper.fpSecuritySummeryDTO ? this.facilityPaper.fpSecuritySummeryDTO : {facilityPaperID: this.facilityPaper.facilityPaperID};
    this.facilitySecuritySummaryType = this.fpSecuritySummeryDTO.facilitySecuritySummaryType ? this.fpSecuritySummeryDTO.facilitySecuritySummaryType : Constants.facilitySecuritySummaryTypeConst.INDIVIDUAL;
    this.fpSecuritySummeryDTO.facilityPaperID = this.facilityPaper.facilityPaperID;

    this.subTotalFirstClassCompany = this.fpSecuritySummeryDTO.companySubTotalOne;
    this.subTotalFirstClassPlusOthersCompany = this.fpSecuritySummeryDTO.companySubTotalTwo;
    this.totalCompany = this.fpSecuritySummeryDTO.companyTotal;
    this.subTotalOtherCompany = this.fpSecuritySummeryDTO.companySubTotalThree;
    this.subTotalClean = this.fpSecuritySummeryDTO.companySubTotalFour;

    this.subTotalFirstClassGroup = this.fpSecuritySummeryDTO.groupSubTotalOne;
    this.subTotalFirstClassPlusOthersGroup = this.fpSecuritySummeryDTO.groupSubTotalTwo;
    this.totalGroup = this.fpSecuritySummeryDTO.groupTotal;
    this.subTotalOtherCompanyGroup = this.fpSecuritySummeryDTO.groupSubTotalThree;
        this.subTotalCleanGroup = this.fpSecuritySummeryDTO.groupSubTotalFour;

    this.calculateTotalPercentages();
    this.componentForm = this.createForm();

    this.setInitialAddedSecurityTopics(this.fpSecuritySummeryDTO);
    this.updateFormSubscription();

  }

  calculateTotalPercentages() {
    if (this.totalCompany > 0) {
      this.subTotalFirstClassCompanyPercentage = ((this.getFloatValue(this.subTotalFirstClassCompany) / this.getFloatValue(this.totalCompany)) * 100).toFixed(2) + '%';
      this.subTotalOtherCompanyPercentage = AppUtils.roundUp(((this.getFloatValue(this.subTotalOtherCompany) / this.getFloatValue(this.totalCompany)) * 100), 2).toFixed(2) + '%';
      this.subTotalCleanPercentage = AppUtils.roundUp(((this.getFloatValue(this.subTotalClean) / this.getFloatValue(this.totalCompany)) * 100), 2).toFixed(2) + '%';
      this.subTotalFirstClassPlusOthersCompanyPercentage = ((this.getFloatValue(this.subTotalFirstClassPlusOthersCompany) / this.getFloatValue(this.totalCompany)) * 100).toFixed(2) + '%';
      this.totalCompanyPercentage = ((this.getFloatValue(this.totalCompany) / this.getFloatValue(this.totalCompany)) * 100).toFixed(2) + '%';
    }

    if (this.totalGroup > 0) {
      this.subTotalFirstClassGroupPercentage = ((this.getFloatValue(this.subTotalFirstClassGroup) / this.getFloatValue(this.totalGroup)) * 100).toFixed(2) + '%';
      this.subTotalOtherCompanyGroupPercentage = AppUtils.roundUp(((this.getFloatValue(this.subTotalOtherCompanyGroup) / this.getFloatValue(this.totalGroup)) * 100), 2).toFixed(2) + '%';
      this.subTotalCleanGroupPercentage = AppUtils.roundUp(((this.getFloatValue(this.subTotalCleanGroup) / this.getFloatValue(this.totalGroup)) * 100), 2).toFixed(2) + '%';
      this.subTotalFirstClassPlusOthersGroupPercentage = ((this.getFloatValue(this.subTotalFirstClassPlusOthersGroup) / this.getFloatValue(this.totalGroup)) * 100).toFixed(2) + '%';
      this.totalGroupPercentage = ((this.getFloatValue(this.totalGroup) / this.getFloatValue(this.totalGroup)) * 100).toFixed(2) + '%';
    }
  }

  ngOnDestroy(): void {
    this.onFPaperSecSummeryChangeSub.unsubscribe();
    this.onSecuritySummeryFormChangeSub.unsubscribe();
  }

  createSecuritySummaryRow(securityTopic, facilityPaper, index?): FormGroup {
    return this.formBuilder.group({
      facilityPaperID: [facilityPaper.facilityPaperID],
      fpSecuritySummaryID: [facilityPaper.fpSecuritySummaryID],
      fpSecuritySummaryTopicID: [securityTopic.fpSecuritySummaryTopicID],
      displayOrder: [securityTopic.displayOrder ? securityTopic.displayOrder : index],
      securityType: [securityTopic.securityType],
      securityTypeGroup: [securityTopic.securityTypeGroup],
      companyValue: [{value: this.getCurrencyFormat(securityTopic.companyValue), disabled: this.isDisabled}],
      companyPercentage: [{value: securityTopic.companyPercentage, disabled: true}],
      groupValue: [{value: this.getCurrencyFormat(securityTopic.groupValue), disabled: this.isDisabled}],
      groupPercentage: [securityTopic.groupPercentage],
      status: [securityTopic.status],
    });
  }

  createForm() {
    this.componentForm = this.formBuilder.group({
      firstClassSecuritySummaryTopicsDTOs: this.formBuilder.array(this.createSecurityTypeFormArray('FIRST_CLASS')),
      otherSecuritySummaryTopicsDTOs: this.formBuilder.array(this.createSecurityTypeFormArray('OTHER')),
      defaultSecuritySummaryTopicsDTOs: this.formBuilder.array(this.createSecurityTypeFormArray('DEFAULT')),
      firstClassSecurityType: [''],
      otherSecurityType: [''],
      defaultSecurityType: [''],
      limitSummery: [{
        value: this.fpSecuritySummeryDTO.limitSummery,
        disabled: !this.hasPrivilege || this.isDisabled
      }, [Validators.maxLength(2000)]],

    });

    return this.componentForm;
  }

  createSecurityTypeFormArray(securityTypeGroup) {
    let securityTopics = [];
    if (this.fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS) {
      this.fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS.forEach((securityTopic, index) => {
        if (securityTopic.securityTypeGroup == securityTypeGroup) {
          securityTopics.push(this.createSecuritySummaryRow(securityTopic, this.facilityPaper, index));
        }
      });
    }
    return securityTopics;
  }

  addFirstClassSecuritySummaryTopicFormRow(securityType) {
    this.firstClassSecuritySummaryTopicsDTOs = this.componentForm.get('firstClassSecuritySummaryTopicsDTOs') as FormArray;
    let securitySummaryTopic = {
      securityType: securityType,
      securityTypeGroup: 'FIRST_CLASS',
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpSecuritySummaryID: this.facilityPaper.fpSecuritySummaryID,
      companyValue: 0,
      groupValue: 0
    };
    this.firstClassSecuritySummaryTopicsDTOs.push(this.createSecuritySummaryRow(securitySummaryTopic, this.facilityPaper, this.firstClassSecuritySummaryTopicsDTOs.length + 1));
  }

  removeFirstClassSecuritySummaryTopicFormRow(item, index) {
    if (!_.isEmpty(item.value)) {
      let value = item.value.securityType;
      let option = _.find(this.firstClassSecuritySummaryTopics, {'securityType': value});
      this.optionsFirstClassSecuritySummaryTopics.push(option);
    }
    this.firstClassSecuritySummaryTopicsDTOs = this.componentForm.get('firstClassSecuritySummaryTopicsDTOs') as FormArray;
    this.firstClassSecuritySummaryTopicsDTOs.removeAt(index);
  }

  addOtherSecuritySummaryTopicFormRow(securityType) {
    this.otherSecuritySummaryTopicsDTOs = this.componentForm.get('otherSecuritySummaryTopicsDTOs') as FormArray;
    let securitySummaryTopic = {
      securityType: securityType,
      securityTypeGroup: 'OTHER',
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpSecuritySummaryID: this.facilityPaper.fpSecuritySummaryID,
      companyValue: 0,
      groupValue: 0
    };
    this.otherSecuritySummaryTopicsDTOs.push(this.createSecuritySummaryRow(securitySummaryTopic, this.facilityPaper, this.otherSecuritySummaryTopicsDTOs.length + 1));
  }


  addDefaultSecuritySummaryTopicFormRow(securityType) {
    this.defaultSecuritySummaryTopicsDTOs = this.componentForm.get('defaultSecuritySummaryTopicsDTOs') as FormArray;
    let securitySummaryTopic = {
      securityType: securityType,
      securityTypeGroup: 'DEFAULT',
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpSecuritySummaryID: this.facilityPaper.fpSecuritySummaryID,
      companyValue: 0,
      groupValue: 0,
    };
    this.defaultSecuritySummaryTopicsDTOs.push(this.createSecuritySummaryRow(securitySummaryTopic, this.facilityPaper, this.defaultSecuritySummaryTopicsDTOs.length + 1));
  }


  isValid() {
    return this.componentForm.valid
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  getFloatValue(value): number {
    let formattedValue = this.getValue(value ? value : '');
    return AppUtils.getFloatValue(formattedValue);
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '', '1.3-3');
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  createSecuritySummaryTopicsSaveObject(securitySummaryTopics) {
    let topics = [];
    securitySummaryTopics.forEach(data => {
      topics.push({
        ...data,
        companyValue: this.getValue(data.companyValue),
        companyPercentage: data.companyPercentage,
        groupValue: this.getValue(data.groupValue),
        groupPercentage: data.groupPercentage
      });
    });
    return topics;
  }

  createTopicSaveObject(securitySummaryTopic) {
    return {
      ...securitySummaryTopic,
      companyValue: this.getValue(securitySummaryTopic.companyValue),
      companyPercentage: securitySummaryTopic.companyPercentage,
      groupValue: this.getValue(securitySummaryTopic.groupValue),
      groupPercentage: securitySummaryTopic.groupPercentage
    };
  }

  setFacilityGroupSecurity() {
    this.facilitySecuritySummaryType = Constants.facilitySecuritySummaryTypeConst.GROUP;
  }

  setFacilityIndividualSecurity() {
    this.facilitySecuritySummaryType = Constants.facilitySecuritySummaryTypeConst.INDIVIDUAL;
  }

  isGroupSecuritySummary() {
    return this.facilitySecuritySummaryType === Constants.facilitySecuritySummaryTypeConst.GROUP;
  }

  updateFormSubscription() {

    this.onSecuritySummeryFormChangeSub.unsubscribe();
    this.onSecuritySummeryFormChangeSub = this.componentForm.valueChanges.subscribe(res => {

      let firstClassCompanyTotal: number = 0;
      let firstClassGroupTotal: number = 0;

      let otherCompanyTotal: number = 0;
      let otherGroupTotal: number = 0;

      let defaultCompanyTotal: number = 0;
      let defaultGroupTotal: number = 0;

      res['firstClassSecuritySummaryTopicsDTOs'].forEach((data: any) => {

        if (this.getFloatValue(data.companyValue)) {
          firstClassCompanyTotal = firstClassCompanyTotal + this.getFloatValue(data.companyValue);
        }
        if (this.getFloatValue(data.groupValue)) {
          firstClassGroupTotal = firstClassGroupTotal + this.getFloatValue(data.groupValue);
        }
      });

      res['otherSecuritySummaryTopicsDTOs'].forEach((data: any) => {
        if (this.getFloatValue(data.companyValue)) {
          otherCompanyTotal = otherCompanyTotal + this.getFloatValue(data.companyValue);
        }
        if (this.getFloatValue(data.groupValue)) {
          otherGroupTotal = otherGroupTotal + this.getFloatValue(data.groupValue);
        }
      });


      res['defaultSecuritySummaryTopicsDTOs'].forEach((data: any) => {
        if (this.getFloatValue(data.companyValue)) {
          defaultCompanyTotal = defaultCompanyTotal + this.getFloatValue(data.companyValue);
        }
        if (this.getFloatValue(data.groupValue)) {
          defaultGroupTotal = defaultGroupTotal + this.getFloatValue(data.groupValue);
        }
      });

      this.subTotalFirstClassCompany = this.getFloatValue(firstClassCompanyTotal);
      this.subTotalOtherCompany = this.getFloatValue(otherCompanyTotal);
      this.subTotalClean = this.getFloatValue(defaultCompanyTotal);
      this.subTotalFirstClassPlusOthersCompany =
        this.getFloatValue(firstClassCompanyTotal)
        + this.getFloatValue(otherCompanyTotal);
      this.totalCompany =
        this.getFloatValue(firstClassCompanyTotal)
        + this.getFloatValue(otherCompanyTotal)
        + this.getFloatValue(defaultCompanyTotal);


      this.subTotalFirstClassGroup = this.getFloatValue(firstClassGroupTotal);
      this.subTotalOtherCompanyGroup = this.getFloatValue(otherGroupTotal);
      this.subTotalCleanGroup = this.getFloatValue(defaultGroupTotal);
      this.subTotalFirstClassPlusOthersGroup =
        this.getFloatValue(firstClassGroupTotal)
        + this.getFloatValue(otherGroupTotal);
      this.totalGroup =
        this.getFloatValue(firstClassGroupTotal)
        + this.getFloatValue(otherGroupTotal)
        + this.getFloatValue(defaultGroupTotal);

      this.calculateTotalPercentages();

    });

    this.onSecuritySummeryFormChangeSub = this.componentForm.get('firstClassSecurityType').valueChanges.subscribe(res => {
      if (!_.isEmpty(res)) {
        this.addFirstClassSecuritySummaryTopicFormRow(res);
        this.optionsFirstClassSecuritySummaryTopics = this.getSecuritySummaryOptions(this.optionsFirstClassSecuritySummaryTopics, res);
      }
    });

    this.onSecuritySummeryFormChangeSub = this.componentForm.get('otherSecurityType').valueChanges.subscribe(res => {
      if (!_.isEmpty(res)) {
        this.addOtherSecuritySummaryTopicFormRow(res);
        this.optionsOtherSecuritySummaryTopics = this.getSecuritySummaryOptions(this.optionsOtherSecuritySummaryTopics, res);
      }
    });

    this.onSecuritySummeryFormChangeSub = this.componentForm.get('defaultSecurityType').valueChanges.subscribe(res => {
      if (!_.isEmpty(res)) {
        this.addDefaultSecuritySummaryTopicFormRow(res);
        this.optionsDefaultSecuritySummaryTopics = this.getSecuritySummaryOptions(this.optionsDefaultSecuritySummaryTopics, res);
      }
    });

  }

  getGroupPercentage(form, controlName, type) {
    const amount = this.mnCurrencyService.getAmountFromFormattedString(form.getRawValue()[controlName]);
    let percentage;
    if (type == 'COMPANY' && this.totalCompany > 0) {
      percentage = ((this.getFloatValue(amount) / this.getFloatValue(this.totalCompany)) * 100).toFixed(2) + '%';
    } else if (type == 'GROUP' && this.totalGroup > 0) {
      percentage = ((this.getFloatValue(amount) / this.getFloatValue(this.totalGroup)) * 100).toFixed(2) + '%';
    }
    return percentage
  }

  isAbleToUpdate() {
    return false;
  }

  setInitialAddedSecurityTopics(fpSecuritySummeryDTO) {
    this.optionsFirstClassSecuritySummaryTopics = this.firstClassSecuritySummaryTopics;
    this.optionsOtherSecuritySummaryTopics = this.otherSecuritySummaryTopics;
    this.optionsDefaultSecuritySummaryTopics = this.defaultSecuritySummaryTopics;

    if (!_.isEmpty(fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS)) {
      fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS.forEach((securityTopic, index) => {
        switch (securityTopic.securityTypeGroup) {
          case  'FIRST_CLASS':
            this.optionsFirstClassSecuritySummaryTopics = this.getSecuritySummaryOptions(this.optionsFirstClassSecuritySummaryTopics, securityTopic.securityType);
            break;
          case  'OTHER':
            this.optionsOtherSecuritySummaryTopics = this.getSecuritySummaryOptions(this.optionsOtherSecuritySummaryTopics, securityTopic.securityType);
            break;
          case  'DEFAULT':
            this.optionsDefaultSecuritySummaryTopics = this.getSecuritySummaryOptions(this.optionsDefaultSecuritySummaryTopics, securityTopic.securityType);
            break;
        }
      });
    }
  }

  getSecuritySummaryOptions(securitySummaryTopics, topic) {
    return _.reject(securitySummaryTopics, ['securityType', topic]);
  }

  getFirstClassOptions() {
    return this.optionsFirstClassSecuritySummaryTopics;
  }

  getOtherOptions() {
    return this.optionsOtherSecuritySummaryTopics;
  }

  getDefaultOptions() {
    return this.optionsDefaultSecuritySummaryTopics;
  }

  getDataWithPreTag(data: string) {
    return `<pre class="data-with-pre-tag pdf-font-text">${data || '-'}</pre>`
  }


}
