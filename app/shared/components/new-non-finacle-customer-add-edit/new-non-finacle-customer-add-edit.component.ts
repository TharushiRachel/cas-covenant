import {Component, OnDestroy, OnInit} from '@angular/core';
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Constants} from "../../../core/setting/constants";
import {Subject, Subscription} from "rxjs";
import {AppUtils} from "../../app.utils";
import * as _ from "lodash"
import {NicValidator} from "../../validators/nic.validator";

@Component({
  selector: 'app-new-non-finacle-customer-add-edit',
  templateUrl: './new-non-finacle-customer-add-edit.component.html',
  styleUrls: ['./new-non-finacle-customer-add-edit.component.scss']
})
export class NewNonFinacleCustomerAddEditComponent implements OnInit, OnDestroy {

  action: Subject<any> = new Subject<any>();
  onFormValueChangeSub = new Subscription();
  onFormChangeSub = new Subscription();
  heading: string;
  actionMessage: string = 'Create Paper';
  identificationNumber;
  identificationType;
  casCustomerData: any;
  formErrors: any = {};
  informationType;
  nonFinacleCustomerForm: FormGroup;
  addressType = Constants.addressType;
  contactNumberType = Constants.contactNumberType;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  customerIdentificationTypeConst = Constants.customerIdentificationTypeConst;
  contactNumberTypeConst = Constants.contactNumberTypeConst;
  addressTypeConst = Constants.addressTypeConst;
  customerIdentificationType = Constants.customerIdentificationType;
  basicInformationType = Constants.basicInformationType;
  isDisabled = false;

  optionsSelectTitle = Constants.optionsSelectTitleOpt;
  optionsCivilStatusSelect = Constants.optionsCivilStatusSelectOpt;
  identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear() - 18,
    month: new Date().getMonth() + 1,
    day: new Date().getDate(),
  };

  myDatePickerOptionsForBirthDay: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 138,
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };

  defaultIdentificationTypes: any = [{identificationType: this.customerIdentificationTypeConst.NIC}, {identificationType: this.customerIdentificationTypeConst.BRC}, {identificationType: this.customerIdentificationTypeConst.PP}];

  constructor(public mdbModalRef: MDBModalRef, private formBuilder: FormBuilder,) {
  }

  ngOnInit() {

    this.informationType = this.casCustomerData ? this.casCustomerData.type ? this.casCustomerData.type : this.basicInformationTypeConst.PERSONAL :
      this.identificationType == this.customerIdentificationTypeConst.BRC ? this.basicInformationTypeConst.BUSINESS : this.basicInformationTypeConst.PERSONAL;

    this.nonFinacleCustomerForm = this.createForm();

    this.onFormChangeSub = this.nonFinacleCustomerForm.controls.type.valueChanges.subscribe(val => {
      if (val) {
        this.informationType = val;
        this.updateFormFormat(val, this.casCustomerData);
      }
    });

    this.formErrors = {
      title: {},
      customerName: {},
      emailAddress: {},
      secondaryEmailAddress: {},
      dateOfBirth: {},
      civilStatus: {},
    };

    this.onFormValueChangeSub = this.nonFinacleCustomerForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.nonFinacleCustomerForm, this.formErrors);
    });
  }

  createPaper() {
    let capitalAuthorized = this.nonFinacleCustomerForm.getRawValue().businessInformationForm.capitalAuthorized || AppUtils.getValue(this.nonFinacleCustomerForm.getRawValue().businessInformationForm.capitalAuthorized);
    let capitalIssued = this.nonFinacleCustomerForm.getRawValue().businessInformationForm.capitalIssued || AppUtils.getValue(this.nonFinacleCustomerForm.getRawValue().businessInformationForm.capitalIssued);
    let capitalPaidUp = this.nonFinacleCustomerForm.getRawValue().businessInformationForm.capitalPaidUp || AppUtils.getValue(this.nonFinacleCustomerForm.getRawValue().businessInformationForm.capitalPaidUp);
    let object = Object.assign({}, this.nonFinacleCustomerForm.getRawValue(),
      {
        capitalAuthorized: AppUtils.getValue(capitalAuthorized),
        capitalIssued: AppUtils.getValue(capitalIssued),
        capitalPaidUp: AppUtils.getValue(capitalPaidUp)
      });
    this.action.next(object);
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.nonFinacleCustomerForm.valid;
  }

  createForm() {
    let initData = this.casCustomerData ? this.casCustomerData : {};
    this.nonFinacleCustomerForm = this.formBuilder.group({
      type: [this.informationType],
      status: [Constants.statusConst.ACT],
      emailAddress: [initData.emailAddress, [Validators.email]],
      secondaryEmailAddress: [initData.secondaryEmailAddress, [Validators.email]],
      casCustomerAddressDTOList: this.formBuilder.array(this.createCustomerAddressFormArray(initData)),
      casCustomerTelephoneDTOList: this.formBuilder.array(this.createCustomerTelephoneFormArray(initData)),
      casCustomerIdentificationDTOList: this.formBuilder.array(this.createCustomerIdentificationsFormArray(initData)),
      personalInformationForm: this.createPersonalFormGroup(initData),
      businessInformationForm: this.createBusinessFormGroup(initData),
      corporateInformationForm: this.createCorporateFormGroup(initData),
    });

    return this.nonFinacleCustomerForm
  }

  createCustomerAddressFormArray(customerData) {
    let address = [];
    if (!_.isEmpty(customerData)) {
      customerData.casCustomerAddressDTOList.forEach(data => {
        address.push(this.createCustomerAddressFormRow(data));
      });
    } else {
      address.push(this.createCustomerAddressFormRow({addressType: this.addressTypeConst.COMMUNICATION}));
      address.push(this.createCustomerAddressFormRow({addressType: this.addressTypeConst.PERMENENT}));
    }
    return address;
  }

  createCustomerAddressFormRow(data): FormGroup {
    let formDataTemplate = Object.assign({}, data, {
      addressType: [data.addressType],
      address1: [data.address1],
      address2: [data.address2],
      city: [data.city],
      status: [Constants.statusConst.ACT],
    });
    return this.formBuilder.group(formDataTemplate);
  }

  createCustomerTelephoneFormArray(customerData) {
    let telephones = [];
    if (!_.isEmpty(customerData)) {
      customerData.casCustomerTelephoneDTOList.forEach(data => {
        telephones.push(this.createCustomerTelephone(data));
      });
    } else {
      telephones.push(this.createCustomerTelephone({description: this.contactNumberTypeConst.MOBILE}));
      telephones.push(this.createCustomerTelephone({description: this.contactNumberTypeConst.LAND}));
    }
    return telephones;
  }

  createCustomerTelephone(data): FormGroup {
    let formDataTemplate = Object.assign({}, data, {
      contactNumber: [data.contactNumber],
      description: [data.description],
      status: [Constants.statusConst.ACT],
    });
    return this.formBuilder.group(formDataTemplate);
  }

  createCustomerIdentificationsFormArray(customerData) {
    let identifications = [];
    let mergeIdentificationList = [];
    if (!_.isEmpty(customerData)) {
      mergeIdentificationList = this.defaultIdentificationTypes.map(data => {
        let returnData;
        customerData.casCustomerIdentificationDTOList.forEach(customerData => {
          if (data.identificationType == customerData.identificationType) {
            returnData = customerData;
          }
        });
        return returnData ? returnData : data;
      });

      _.sortBy(mergeIdentificationList, ['identificationType']).forEach(data => {
        identifications.push(this.createCustomerIdentification(data, this.isToBeValidatedIdentificationType(this.informationType, data.identificationType)));
      });
    } else {
      identifications.push(this.createCustomerIdentification({
        identificationType: this.customerIdentificationTypeConst.BRC,
        identificationNumber: this.customerIdentificationTypeConst.BRC === this.identificationType ? this.identificationNumber : null
      }, this.isToBeValidatedIdentificationType(this.informationType, this.customerIdentificationTypeConst.BRC)));
      identifications.push(this.createCustomerIdentification({
        identificationType: this.customerIdentificationTypeConst.NIC,
        identificationNumber: this.customerIdentificationTypeConst.NIC === this.identificationType ? this.identificationNumber : null
      }, this.isToBeValidatedIdentificationType(this.informationType, this.customerIdentificationTypeConst.NIC)));
      identifications.push(this.createCustomerIdentification({identificationType: this.customerIdentificationTypeConst.PP}, this.isToBeValidatedIdentificationType(this.informationType, this.customerIdentificationTypeConst.PP)));
    }
    return identifications;
  }

  createCustomerIdentification(data, toValidate): FormGroup {
    let validators = [];
    if (toValidate) {
      validators.push(Validators.required);
    }
    if (data.identificationType == this.customerIdentificationTypeConst.NIC && toValidate) {
      validators.push(NicValidator.isValidNICInput);
    }

    let formDataTemplate = Object.assign({}, data, {
      identificationType: [data.identificationType],
      identificationNumber: [data.identificationNumber, [...validators]],
      status: [Constants.statusConst.ACT],
    });

    return this.formBuilder.group(formDataTemplate);
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onFormChangeSub.unsubscribe();
  }

  isPersonal() {
    return this.informationType === this.basicInformationTypeConst.PERSONAL;
  }

  isBusiness() {
    return this.informationType === this.basicInformationTypeConst.BUSINESS;
  }

  isCorporate() {
    return this.informationType === this.basicInformationTypeConst.CORPORATE;
  }

  isToBeValidatedIdentificationType(informationType, identificationType) {
    switch (informationType) {
      case  this.basicInformationTypeConst.PERSONAL:
        return identificationType == Constants.customerIdentificationTypeConst.NIC;
      case this.basicInformationTypeConst.BUSINESS:
        return identificationType == Constants.customerIdentificationTypeConst.BRC
    }
  }

  updateFormFormat(informationType, initData) {
    this.nonFinacleCustomerForm.setControl('casCustomerIdentificationDTOList', this.formBuilder.array(this.createCustomerIdentificationsFormArray(initData)));
    this.nonFinacleCustomerForm.updateValueAndValidity();
  }

  createBusinessFormGroup(initData) {
    return this.formBuilder.group({
      nameOfBusiness: [{
        value: initData.nameOfBusiness,
        disabled: this.isDisabled
      }, []],
      registrationNo: [{
        value: initData.registrationNo ? initData.registrationNo : this.identificationNumber,
        disabled: this.isDisabled
      }, []],
      constitution: [{
        value: initData.constitution,
        disabled: this.isDisabled
      }, []],
      dateOfRegistrationStr: [{
        value: initData.dateOfRegistrationStr,
        disabled: this.isDisabled
      }, []],
      officialAddress: [{
        value: initData.officialAddress,
        disabled: this.isDisabled
      }, []],
      natureOfBusiness: [{
        value: initData.natureOfBusiness,
        disabled: this.isDisabled
      }, []],
      dateOfCommencementStr: [{
        value: initData.dateOfCommencementStr,
        disabled: this.isDisabled
      }, []],
      capitalAuthorized: [{
        value: initData.capitalAuthorized,
        disabled: this.isDisabled
      }, []],
      capitalIssued: [{
        value: initData.capitalIssued,
        disabled: this.isDisabled
      }, []],
      capitalPaidUp: [{
        value: initData.capitalPaidUp,
        disabled: this.isDisabled
      }, []],
      status: [{
        value: initData.status,
        disabled: this.isDisabled
      }, []],
    })

  }

  createPersonalFormGroup(initData) {
    return this.formBuilder.group({
      title: [{value: initData.title, disabled: this.isDisabled}, []],
      nameWithInitials: [{
        value: initData.nameWithInitials,
        disabled: this.isDisabled
      }, []],
      initialRepresentation: [{
        value: initData.initialRepresentation,
        disabled: this.isDisabled
      }, []],
      placeOfBirth: [{value: initData.placeOfBirth, disabled: this.isDisabled}, []],
      dateOfBirthStr: [{value: initData.dateOfBirthStr, disabled: this.isDisabled}, []],
      nationality: [{value: initData.nationality, disabled: this.isDisabled}, []],
      civilStatus: [{value: initData.civilStatus, disabled: this.isDisabled}, []],
      employment: [{value: initData.employment, disabled: this.isDisabled}, []],
      employer: [{
        value: initData.employer,
        disabled: this.isDisabled
      }, []],
      position: [{
        value: initData.position,
        disabled: this.isDisabled
      }, []],
      highestEduAchievement: [{
        value: initData.highestEduAchievement,
        disabled: this.isDisabled
      }, []],
      officialAddress: [{
        value: initData.officialAddress,
        disabled: this.isDisabled
      }, []],
      noOfYearsEmployment: [{
        value: initData.noOfYearsEmployment,
        disabled: this.isDisabled
      }, []],
      natureOfBusiness: [{
        value: initData.natureOfBusiness,
        disabled: this.isDisabled
      }, []],
      noOfBusinessYears: [{
        value: initData.noOfBusinessYears,
        disabled: this.isDisabled
      }, []],
      status: [{
        value: initData.status,
        disabled: this.isDisabled
      }, []],
    });
  }

  showAddress(addressType) {
    return !((this.isCorporate() || this.isBusiness()) && this.addressTypeConst.PERMENENT == addressType);
  }

  getLableNameForOfficialAddress() {
    if (this.isCorporate() || this.isBusiness()) {
      return 'Registered Address'
    }
    return "Official Address";
  }

  createCorporateFormGroup(initData) {
    return this.formBuilder.group({
      nameOfBusiness: [{
        value: initData.nameOfBusiness,
        disabled: this.isDisabled
      }, []],
      registrationNo: [{
        value: initData.registrationNo ? initData.registrationNo : this.identificationNumber,
        disabled: this.isDisabled
      }, []],
      constitution: [{
        value: initData.constitution,
        disabled: this.isDisabled
      }, []],
      dateOfIncorporateStr: [{
        value: initData.dateOfIncorporateStr,
        disabled: this.isDisabled
      }, []],
      citizenship: [{
        value: initData.citizenship,
        disabled: this.isDisabled
      }, []],
      officialAddress: [{
        value: initData.officialAddress,
        disabled: this.isDisabled
      }, []],
      natureOfBusiness: [{
        value: initData.natureOfBusiness,
        disabled: this.isDisabled
      }, []],
      dateOfCommencementStr: [{
        value: initData.dateOfCommencementStr,
        disabled: this.isDisabled
      }, []],
      capitalAuthorized: [{
        value: initData.capitalAuthorized,
        disabled: this.isDisabled
      }, []],
      capitalIssued: [{
        value: initData.capitalIssued,
        disabled: this.isDisabled
      }, []],
      capitalPaidUp: [{
        value: initData.capitalPaidUp,
        disabled: this.isDisabled
      }, []],
      status: [{
        value: initData.status,
        disabled: this.isDisabled
      }, []],
    });
  }
}
