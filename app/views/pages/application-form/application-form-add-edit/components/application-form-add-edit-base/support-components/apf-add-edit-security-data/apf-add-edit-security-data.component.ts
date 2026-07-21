import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Constants} from "../../../../../../../../core/setting/constants";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {AFSecurityUpdateDto} from "../../../../dtos/apf-security-dto";

@Component({
  selector: 'app-apf-add-edit-security-data',
  templateUrl: './apf-add-edit-security-data.component.html',
  styleUrls: ['./apf-add-edit-security-data.component.scss']
})
export class ApfAddEditSecurityDataComponent implements OnInit, OnDestroy {

  action: Subject<any> = new Subject<any>();
  isEmpty: boolean = false;
  forRemove: boolean = false;
  content: any = {};
  componentForm: FormGroup;
  onFormValueChangeSub = new Subscription();
  formErrors: any = {};
  afSecurityUpdateDto: AFSecurityUpdateDto = new AFSecurityUpdateDto({});
  facilityList: any[] = [];
  onFPFacilitiesChangeSub = new Subscription();
  applicationForm: any = {};
  isCommonSecurity: boolean = false;
  facilitySecurities: any[] = [];
  currencyTypesOpt = Constants.currencyTypesOpt;
  afFacilitySecurityDTOS: FormArray;

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(
    private formBuilder: FormBuilder,
    private applicationFormCreateService: ApplicationFormAddEditService,
    public  mdbModalRef: MDBModalRef,
    private currencyPipe: CurrencyPipe,
    public currencyService: CurrencyService,
    private alertService: AlertService
  ) {
  }

  ngOnInit() {

    if (!_.isEmpty(this.content.securityItem)) {
      this.afSecurityUpdateDto = new AFSecurityUpdateDto(this.content.securityItem);
      this.isEmpty = false;
    } else if (!_.isEmpty(this.content.facilityData)) {
      this.isEmpty = true;
    } else {
      this.isEmpty = true;
    }

    if (!_.isEmpty(this.content.allSecurityItems)) {
      _.sortBy(this.content.allSecurityItems, ['securityID']).forEach(
        security => {
          this.facilitySecurities.push({
            value: security.securityID,
            label: security.securityCode
          });
        }
      );
    }

    this.formErrors = {
      securityCode: {},
      cashAmount: {},
      securityDetail: {},
      securityAmount: {},
      securityCurrency: {},
      facilitySecurityType: {},
    };

    this.onFPFacilitiesChangeSub = this.applicationFormCreateService.onAFFacilitiesChange
      .subscribe((data: any) => {
        if (data) {
          this.applicationForm = data;
        }
        this.facilityList = [];
        this.facilityList = _.sortBy(this.applicationForm.afFacilityDTOList || [], ['displayOrder']);
        this.createForm();
      });

    this.onFormValueChangeSub.unsubscribe();

    this.onFormValueChangeSub = this.componentForm.get('securityID').valueChanges.subscribe(securityID => {
      if (securityID) {
        this.content.allSecurityItems.forEach(security => {
          if (security.securityID == securityID) {
            this.afSecurityUpdateDto = new AFSecurityUpdateDto(security);
            this.componentForm.get('securityCode').setValue(security.securityCode);
            this.componentForm.get('isCashSecurity').setValue(security.isCashSecurity == 'Y');
            this.componentForm.get('cashAmount').setValue(this.currencyPipe.transform(security.cashAmount, '', ''));
            this.componentForm.get('securityDetail').setValue(security.securityDetail);
            this.componentForm.get('securityAmount').setValue(this.currencyPipe.transform(security.securityAmount, '', ''));
            this.componentForm.get('securityCurrency').setValue(security.securityCurrency);
            this.removeThenRecreateFacilities();
            this.isEmpty = false;
          }
        });
      } else {
        this.afSecurityUpdateDto = new AFSecurityUpdateDto({});
        this.componentForm.get('securityCode').reset('');
        this.componentForm.get('isCashSecurity').reset('');
        this.componentForm.get('cashAmount').reset('');
        this.componentForm.get('securityDetail').reset('');
        this.componentForm.get('securityAmount').reset('');
        this.componentForm.get('securityCurrency').reset(this.currencyTypesOpt[0].value);
        let facilitySecurityFormArray = this.componentForm.get('afFacilitySecurityDTOS') as FormArray;
        facilitySecurityFormArray.clear();
        this.removeThenRecreateFacilities();
        this.isEmpty = true;
      }
    });

    this.onFormValueChangeSub = this.componentForm.get('isCashSecurity').valueChanges.subscribe((value: any) => {
        if (value) {
          this.componentForm.controls.cashAmount.setValidators([Validators.required, Validators.min(1)]);
          this.componentForm.controls.cashAmount.reset();
        } else {
          this.componentForm.controls.cashAmount.setValidators(null);
          this.componentForm.controls.cashAmount.reset();
        }
      }
    );

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  createForm() {
    let isSelectingSecurityEnabled = !_.isEmpty(this.content.allSecurityItems) && this.content.allSecurityItems.length > 0;
    this.componentForm = this.formBuilder.group({
      securityID: [{value: '', disabled: !isSelectingSecurityEnabled}],
      securityCode: [this.afSecurityUpdateDto.securityCode, [Validators.required, Validators.maxLength(4000)]],
      securityDetail: [this.afSecurityUpdateDto.securityDetail, [Validators.required, Validators.maxLength(4000)]],
      securityAmount: [this.currencyPipe.transform(this.afSecurityUpdateDto.securityAmount, '', ''), [Validators.required, NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue, Validators.min(1)]],
      cashAmount: [this.currencyPipe.transform(this.afSecurityUpdateDto.cashAmount, '', '')],
      isCashSecurity: [this.afSecurityUpdateDto.isCashSecurity ? this.afSecurityUpdateDto.isCashSecurity == 'Y' : null],
      securityCurrency: [
        this.afSecurityUpdateDto.securityCurrency ? this.afSecurityUpdateDto.securityCurrency :
          this.content.facilityData.facilityCurrency ? this.content.facilityData.facilityCurrency :
            this.currencyTypesOpt[0].value],
      afFacilitySecurityDTOS: this.formBuilder.array(this.createFacilitySecurityGroup()),
    });

    if (this.afSecurityUpdateDto.isCashSecurity == 'Y') {
      this.componentForm.controls.cashAmount.setValidators([Validators.required, Validators.min(1)]);
    }

  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onFPFacilitiesChangeSub.unsubscribe();
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  isDisabled(form) {
    return form.getRawValue().isAddedFacility;
  }

  isCashSecurity() {
    return this.componentForm.controls.isCashSecurity.value;
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.componentForm.getRawValue()[control]);
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

  removeThenRecreateFacilities() {
    this.componentForm.removeControl('afFacilitySecurityDTOS');
    this.componentForm.addControl('afFacilitySecurityDTOS', this.formBuilder.array(this.createFacilitySecurityGroup()));
  }

  createFacilitySecurityGroup() {
    let facilitySecurities = [];
    this.facilityList.forEach(facility => {
      let facilitySecurity = Object.assign({}, _.find(this.afSecurityUpdateDto.afFacilitySecurityDTOS, {facilityID: facility.facilityID}));
      facilitySecurities.push(this.createFacilitySecurity(facilitySecurity, facility))
    });
    return facilitySecurities;
  }

  createFacilitySecurity(facilitySecurity, facility): FormGroup {

    return this.formBuilder.group({
      facilitySecurityID: [facilitySecurity.facilitySecurityID ? facilitySecurity.facilitySecurityID : null],
      facilityRefCode: [{
        value: facility.facilityRefCode ? facility.creditFacilityTemplateDTO.creditFacilityName + " - " + facility.facilityRefCode : '',
        disabled: true
      }],
      isAddedFacility: [facilitySecurity.facilitySecurityID ? facilitySecurity.status == 'ACT' : facility.facilityID == this.content.facilityData.facilityID],
      isCashSecurity: [facilitySecurity.isCashSecurity ? 'Y' : 'N'],
      facilitySecurityAmount: [facilitySecurity.facilitySecurityAmount ? this.getCurrencyFormat(facilitySecurity.facilitySecurityAmount) : null],
      status: [facilitySecurity.status ? facilitySecurity.status : Constants.statusConst.ACT],
      facilityID: [facilitySecurity.facilityID ? facilitySecurity.facilityID : facility.facilityID],
      securityID: [facilitySecurity.securityID ? facilitySecurity.securityID : this.afSecurityUpdateDto.securityID],
    });
  }

  saveUpdateFacilitySecurity() {
    let facilitySecurities: [] = this.componentForm.getRawValue().afFacilitySecurityDTOS;
    let afFacilitySecurityDTOS = [];
    let selectedFacilities = 0;

    facilitySecurities.forEach((security: any) => {
      if (security.isAddedFacility) {
        selectedFacilities += 1;
      }

      let facilitySecurityData = Object.assign({}, security,
        {isAddedFacility: security.isAddedFacility ? 'Y' : 'N'},
        {status: security.isAddedFacility ? 'ACT' : 'INA'},
        // {facilitySecurityAmount: this.getValue(security.facilitySecurityAmount)},
        {facilitySecurityAmount: this.componentForm.controls.isCashSecurity.value ? this.getValue(this.componentForm.controls.cashAmount.value) : this.getValue(this.componentForm.controls.securityAmount.value)},
        {isCashSecurity: this.componentForm.controls.isCashSecurity.value ? 'Y' : 'N'},
      );
      afFacilitySecurityDTOS.push(facilitySecurityData);
    });

    let data = Object.assign({},
      {
        securityID: this.afSecurityUpdateDto.securityID ? this.afSecurityUpdateDto.securityID : null,
        securityDetail: this.componentForm.controls.securityDetail.value,
        applicationFormID: this.applicationForm.applicationFormID ? this.applicationForm.applicationFormID : null,
        securityCode: this.componentForm.controls.securityCode.value,
        isCashSecurity: this.componentForm.controls.isCashSecurity.value ? 'Y' : 'N',
        securityAmount: this.getValue(this.componentForm.controls.securityAmount.value),
        cashAmount: this.getValue(this.componentForm.controls.cashAmount.value),
        securityCurrency: this.getValue(this.componentForm.controls.securityCurrency.value),
        status: 'ACT',
        isCommonSecurity: selectedFacilities > 1 ? 'Y' : 'N',
        afFacilitySecurityDTOS: afFacilitySecurityDTOS
      }
    );

    if (parseFloat(this.getValue(this.componentForm.controls.cashAmount.value)) > parseFloat(this.getValue(this.componentForm.controls.securityAmount.value))) {
      this.alertService.showToaster("Security Amount Exceeded!", SETTINGS.TOASTER_MESSAGES.warning, {timeOut: 5000});
    } else if (selectedFacilities == 0) {
      this.alertService.showToaster("No Facilities Included", SETTINGS.TOASTER_MESSAGES.warning, {timeOut: 5000});
    } else {
      this.applicationFormCreateService.saveUpdateFacilitySecurity(data);
      this.mdbModalRef.hide();
    }
  }

}
