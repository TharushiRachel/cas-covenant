import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LeadFacilityDetailUpdateDto} from "../../dto/lead-facility-detail-update-dto";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {LeadAddEditService} from "../../services/lead-add-edit.service";
import {LeadsService} from "../../services/leads.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import * as _ from 'lodash';
import {MDBModalRef} from 'ng-uikit-pro-standard';
import {CacheService} from "../../../../../core/service/data/cache.service";
import {Constants} from "../../../../../core/setting/constants";
import {CurrencyPipe} from "@angular/common";
import {AppUtils} from "../../../../../shared/app.utils";
import {NumberValidator} from "../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-lead-facility-detail',
  templateUrl: './lead-facility-detail.component.html',
  styleUrls: ['./lead-facility-detail.component.scss']
})
export class LeadFacilityDetailComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  facilityTemplateID: any = null;
  facilityTemplateTypeID: any = null;
  creditFacilityTemplates = [];
  leadFacilityDetailUpdateDto: LeadFacilityDetailUpdateDto = new LeadFacilityDetailUpdateDto({});
  onSelectedLeadFacilityDetailChangeSub: Subscription = new Subscription();
  onFormValueChangeSub: Subscription = new Subscription();
  onFacilityNameChange: Subscription = new Subscription();
  onLoadCreditFacilityTemplateListChangeSub: Subscription = new Subscription();
  result: Subject<any>;
  facility: any = {};
  facilityTypeList = [];
  creditFacilityGroups = [];
  creditFacilityGroupWiseTemplateMap = {};
  creditFacilityTemplateList: any = [];
  creditFacilityTemplateOptionList: any[] = [];
  currencyTypesConst = Constants.currencyTypesConst;
  creditFacilityTemplatesApproveStatusConst = Constants.creditFacilityTemplatesApproveStatusConst;
  statusConst = Constants.statusConst;

  action: Subject<any> = new Subject<any>();

  pageType: string = 'new';

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  currencyTypesOpt = Constants.currencyTypesOpt;

  constructor(private leadAddEditService: LeadAddEditService,
              private leadsService: LeadsService,
              private urlEncodeService: UrlEncodeService,
              private cacheService: CacheService,
              private formBuilder: FormBuilder,
              private currencyPipe: CurrencyPipe,
              public  mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {

    this.formErrors = {
      leadID: {},
      facilityTemplateName: {},
      creditFacilityType: {},
      facilityCurrency: {},
      amount: {},
      description: {},
      status: {}
    };

    this.creditFacilityTemplates = this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES);
    this.result = new BehaviorSubject(this.creditFacilityTemplates);


    this.componentForm = this.createLeadFacilityDetailForm();
    this.onFormValueChangeSub.unsubscribe();

    this.onFacilityNameChange = this.componentForm.controls.facilityTemplateName.valueChanges
      .subscribe(templateName => {

        let find = _.find(this.creditFacilityTemplates, (template) => {
          return template.creditFacilityName === templateName
            && template.approveStatus == this.creditFacilityTemplatesApproveStatusConst.APPROVED
            && template.status == this.statusConst.ACT;
        });
        if (find) {
          this.facilityTemplateID = find.creditFacilityTemplateID;
        }
      });

    this.onFacilityNameChange = this.componentForm.controls.creditFacilityType.valueChanges
      .subscribe(typeName => {

        let find = _.find(this.facilityTypeList, (facilityType) => {
          if (facilityType.status == Constants.statusConst.ACT) {
            return facilityType.facilityTypeName === typeName;
          }
        });
        if (find) {
          this.facilityTemplateTypeID = find.creditFacilityTypeID;
        }
      });

    this.onLoadCreditFacilityTemplateListChangeSub = this.componentForm.controls.facilityTemplateName.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value));
      });

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

    this.facilityTypeList = this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES) || [];
    _.forEach(this.facilityTypeList, cfType => {
      if (cfType.status == 'ACT')
        this.creditFacilityGroups.push(cfType);
    });

    _.forEach(this.creditFacilityTemplates, cfTemplate => {
      if (cfTemplate.status == 'ACT') {
        if (this.creditFacilityGroupWiseTemplateMap[cfTemplate.facilityTypeName] == undefined) {
          this.creditFacilityGroupWiseTemplateMap[cfTemplate.facilityTypeName] = [];
        }
        this.creditFacilityGroupWiseTemplateMap[cfTemplate.facilityTypeName] = [
          ...this.creditFacilityGroupWiseTemplateMap[cfTemplate.facilityTypeName],
          cfTemplate
        ]
      }
    });


    this.onFormValueChangeSub = this.componentForm.get('creditFacilityType').valueChanges.subscribe((creditFacilityType) => {
      if (creditFacilityType) {
        this.creditFacilityTemplateOptionList = [];
        this.creditFacilityTemplateOptionList = this.creditFacilityGroupWiseTemplateMap[creditFacilityType];
      }
    });

    this.onFormValueChangeSub = this.componentForm.get('creditFacilityType').valueChanges.subscribe(creditFacilityType => {
      let templateList = this.creditFacilityGroupWiseTemplateMap[creditFacilityType] || [];
      if (creditFacilityType && templateList && templateList.length > 0) {
        this.componentForm.get('facilityTemplateName').setValue(templateList[0].creditFacilityName);
      }
    });

  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onSelectedLeadFacilityDetailChangeSub.unsubscribe();
    this.onLoadCreditFacilityTemplateListChangeSub.unsubscribe();
  }

  createLeadFacilityDetailForm() {
    return this.formBuilder.group({
      leadID: [this.leadFacilityDetailUpdateDto.leadID],
      creditFacilityType: [this.leadFacilityDetailUpdateDto.creditFacilityType, [Validators.required]],
      facilityTemplateName: [this.leadFacilityDetailUpdateDto.facilityTemplateName, [Validators.required]],
      amount: [this.leadFacilityDetailUpdateDto.amount, [Validators.required, NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
      facilityCurrency: [this.leadFacilityDetailUpdateDto.facilityCurrency ? this.leadFacilityDetailUpdateDto.facilityCurrency : this.currencyTypesConst.LKR, [Validators.required]],
      description: [this.leadFacilityDetailUpdateDto.description, Validators.required],
    })
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

  getFormRawData() {
    let rawData = this.componentForm.getRawValue();
    return rawData;
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.creditFacilityTemplates.filter((item: any) => item.creditFacilityName.toLowerCase().includes(filterValue));
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  onAdd() {
    if (this.componentForm.valid) {
      let data = Object.assign({},
        this.leadFacilityDetailUpdateDto,
        this.componentForm.getRawValue(),
        {facilityTemplateID: this.facilityTemplateID},
        {creditFacilityTypeID: this.facilityTemplateTypeID},
        {status: 'ACT'}
      );
      data.amount = this.getValue(data.amount);

      this.action.next(data);
      this.mdbModalRef.hide();

    }
  }

  saveUpdate() {
  }
}
