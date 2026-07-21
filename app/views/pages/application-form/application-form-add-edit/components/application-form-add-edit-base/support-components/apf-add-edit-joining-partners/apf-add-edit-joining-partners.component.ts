import {Component, OnDestroy, OnInit} from '@angular/core';
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-add-edit-joining-partners',
  templateUrl: './apf-add-edit-joining-partners.component.html',
  styleUrls: ['./apf-add-edit-joining-partners.component.scss']
})
export class ApfAddEditJoiningPartnersComponent implements OnInit, OnDestroy {

  basicInformationTypeConst = Constants.basicInformationTypeConst;
  basicInformationType = Constants.basicInformationType;
  titleConst = Constants.titleConst;
  title = Constants.title;
  civilStatus = Constants.civilStatus;
  civilStatusConst = Constants.civilStatusConst;
  employStatus = Constants.employStatus;
  employStatusConst = Constants.employStatusConst;
  constitutionConst = Constants.constitutionConst;
  constitution = Constants.constitution;
  yesNo = Constants.yesNoConst;

  basicInformationForm: FormGroup;
  afCustomerAddressDTOList: FormArray;
  afCustomerTelephoneDTOList: FormArray;
  onFormChangeSub = new Subscription();
  formErrors: any = {};
  isDisabled: boolean = false;
  informationType;
  employmentType = this.employStatus.EMPLOYED;
  applicationForm: any = {};
  basicInformation: any = {};
  content: any = {};
  heading: string;

  optionsSelectTitle = Constants.optionsSelectTitleOpt;

  optionsCivilStatusSelect = Constants.optionsCivilStatusSelectOpt;

  optionsEmploymentSelect: any = [
    {value: '', label: ''},
    {value: this.employStatusConst.EMPLOYED, label: this.employStatus.EMPLOYED},
    {value: this.employStatusConst.SELF_EMPLOYED, label: this.employStatus.SELF_EMPLOYED},
  ];

  optionsSelectConstitution = [
    {value: this.constitutionConst.PUBLIC_LTD, label: this.constitution.PUBLIC_LTD},
    {value: this.constitutionConst.CORPORATION, label: this.constitution.CORPORATION},
    {value: this.constitutionConst.OTHER, label: this.constitution.OTHER},
  ];

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

  constructor(private formBuilder: FormBuilder,
              public  mdbModalRef: MDBModalRef,
              private applicationFormCreateService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
    this.formErrors = {
      title: {},
      type: {},
      nameWithInitials: {},
      initialRepresentation: {},
      nameOfBusiness: {},
      registrationNo: {},
      constitution: {},
      dateOfIncorporate: {},
      dateOfCommencement: {},
      dateOfRegistrationStr: {},
      natureOfBusiness: {},
      noOfBusinessYears: {},
      citizenship: {},
      privateAddress: {},
      officialAddress: {},
      businessAddress: {},
      telNumber: {},
      emailAddress: {},
      dateOfBirthStr: {},
      placeOfBirth: {},
      civilStatus: {},
      nationality: {},
      identificationNo: {},
      employment: {},
      highestEduAchievement: {},
      position: {},
      noOfYearsEmployment: {},
      capitalAuthorized: {},
      capitalIssued: {},
      capitalPaidUp: {},
      identificationType: {},
    };

    this.applicationForm = this.content.applicationForm;
    this.basicInformation = this.content.basicInformation;
    this.informationType = this.basicInformation.type ? this.basicInformation.type : this.applicationForm.formType ? this.applicationForm.formType : this.basicInformationTypeConst.PERSONAL;

    this.basicInformationForm = this.createFrom();

    this.onFormChangeSub = this.basicInformationForm.controls.type.valueChanges.subscribe(val => {
      if (val) {
        this.informationType = val;
      }
    });

  }

  ngOnDestroy(): void {
    this.onFormChangeSub.unsubscribe();
  }

  createFrom() {

    this.basicInformationForm = this.formBuilder.group({
      type: [{
        value: this.basicInformation.type ? this.basicInformation.type : this.applicationForm.formType ? this.applicationForm.formType : this.basicInformationTypeConst.PERSONAL,
        disabled: this.isDisabled
      }, [Validators.required]],

      personalInformationForm: this.formBuilder.group({
        basicInformationID: [{
          value: this.basicInformation.basicInformationID,
          disabled: this.isDisabled
        }, [Validators.required]],
        title: [{value: this.basicInformation.title, disabled: this.isDisabled}, [Validators.required]],
        nameWithInitials: [{
          value: this.basicInformation.nameWithInitials,
          disabled: this.isDisabled
        }, [Validators.required]],
        initialRepresentation: [{
          value: this.basicInformation.initialRepresentation,
          disabled: this.isDisabled
        }, [Validators.required]],
        placeOfBirth: [{value: this.basicInformation.placeOfBirth, disabled: this.isDisabled}, [Validators.required]],
        dateOfBirthStr: [{
          value: this.basicInformation.dateOfBirthStr,
          disabled: this.isDisabled
        }, [Validators.required]],
        nationality: [{value: this.basicInformation.nationality, disabled: this.isDisabled}, [Validators.required]],
        civilStatus: [{value: this.basicInformation.civilStatus, disabled: this.isDisabled}, [Validators.required]],
        employment: [{value: this.basicInformation.employment, disabled: this.isDisabled}, [Validators.required]],
        employer: [{
          value: this.basicInformation.employer,
          disabled: this.isDisabled
        }, [Validators.required]],
        position: [{
          value: this.basicInformation.position,
          disabled: this.isDisabled
        }, [Validators.required]],
        highestEduAchievement: [{
          value: this.basicInformation.highestEduAchievement,
          disabled: this.isDisabled
        }, [Validators.required]],
        officialAddress: [{
          value: this.basicInformation.officialAddress,
          disabled: this.isDisabled
        }, [Validators.required]],
        noOfYearsEmployment: [{
          value: this.basicInformation.noOfYearsEmployment,
          disabled: this.isDisabled
        }, [Validators.required]],
        natureOfBusiness: [{
          value: this.basicInformation.natureOfBusiness,
          disabled: this.isDisabled
        }, [Validators.required]],
        noOfBusinessYears: [{
          value: this.basicInformation.noOfBusinessYears,
          disabled: this.isDisabled
        }, [Validators.required]],
        status: [{
          value: this.basicInformation.status,
          disabled: this.isDisabled
        }, []],
      }),

      businessInformationForm: this.formBuilder.group({
        basicInformationID: [{
          value: this.basicInformation.basicInformationID,
          disabled: this.isDisabled
        }, [Validators.required]],
        identificationType: [{
          value: this.basicInformation.identificationType,
          disabled: this.isDisabled
        }, [Validators.required]],
        identificationNo: [{
          value: this.basicInformation.identificationNo,
          disabled: this.isDisabled
        }, [Validators.required]],
        nameOfBusiness: [{
          value: this.basicInformation.nameOfBusiness,
          disabled: this.isDisabled
        }, [Validators.required]],
        registrationNo: [{
          value: this.basicInformation.registrationNo,
          disabled: this.isDisabled
        }, [Validators.required]],
        constitution: [{
          value: this.basicInformation.constitution,
          disabled: this.isDisabled
        }, [Validators.required]],
        dateOfRegistrationStr: [{
          value: this.basicInformation.dateOfRegistrationStr,
          disabled: this.isDisabled
        }, [Validators.required]],
        privateAddress: [{
          value: this.basicInformation.privateAddress,
          disabled: this.isDisabled
        }, [Validators.required]],
        officialAddress: [{
          value: this.basicInformation.officialAddress,
          disabled: this.isDisabled
        }, [Validators.required]],
        telNumber: [{
          value: this.basicInformation.telNumber,
          disabled: this.isDisabled
        }, [Validators.required]],
        emailAddress: [{
          value: this.basicInformation.emailAddress,
          disabled: this.isDisabled
        }, [Validators.required]],
        natureOfBusiness: [{
          value: this.basicInformation.natureOfBusiness,
          disabled: this.isDisabled
        }, [Validators.required]],
        dateOfCommencementStr: [{
          value: this.basicInformation.dateOfCommencementStr,
          disabled: this.isDisabled
        }, [Validators.required]],
        capitalAuthorized: [{
          value: this.basicInformation.capitalAuthorized,
          disabled: this.isDisabled
        }, [Validators.required]],
        capitalIssued: [{
          value: this.basicInformation.capitalIssued,
          disabled: this.isDisabled
        }, [Validators.required]],
        capitalPaidUp: [{
          value: this.basicInformation.capitalPaidUp,
          disabled: this.isDisabled
        }, [Validators.required]],
        status: [{
          value: this.basicInformation.status,
          disabled: this.isDisabled
        }, []],
      }),

      corporateInformationForm: this.formBuilder.group({
        basicInformationID: [{
          value: this.basicInformation.basicInformationID,
          disabled: this.isDisabled
        }, [Validators.required]],
        identificationType: [{
          value: this.basicInformation.identificationType,
          disabled: this.isDisabled
        }, [Validators.required]],
        identificationNo: [{
          value: this.basicInformation.identificationNo,
          disabled: this.isDisabled
        }, [Validators.required]],
        nameOfBusiness: [{
          value: this.basicInformation.nameOfBusiness,
          disabled: this.isDisabled
        }, [Validators.required]],
        registrationNo: [{
          value: this.basicInformation.registrationNo,
          disabled: this.isDisabled
        }, [Validators.required]],
        constitution: [{
          value: this.basicInformation.constitution,
          disabled: this.isDisabled
        }, [Validators.required]],
        dateOfIncorporateStr: [{
          value: this.basicInformation.dateOfIncorporateStr,
          disabled: this.isDisabled
        }, [Validators.required]],
        citizenship: [{
          value: this.basicInformation.citizenship,
          disabled: this.isDisabled
        }, [Validators.required]],
        officialAddress: [{
          value: this.basicInformation.officialAddress,
          disabled: this.isDisabled
        }, [Validators.required]],
        telNumber: [{
          value: this.basicInformation.telNumber,
          disabled: this.isDisabled
        }, [Validators.required]],
        emailAddress: [{
          value: this.basicInformation.emailAddress,
          disabled: this.isDisabled
        }, [Validators.required]],
        natureOfBusiness: [{
          value: this.basicInformation.natureOfBusiness,
          disabled: this.isDisabled
        }, [Validators.required]],
        dateOfCommencementStr: [{
          value: this.basicInformation.dateOfCommencementStr,
          disabled: this.isDisabled
        }, [Validators.required]],
        capitalAuthorized: [{
          value: this.basicInformation.capitalAuthorized,
          disabled: this.isDisabled
        }, [Validators.required]],
        capitalIssued: [{
          value: this.basicInformation.capitalIssued,
          disabled: this.isDisabled
        }, [Validators.required]],
        capitalPaidUp: [{
          value: this.basicInformation.capitalPaidUp,
          disabled: this.isDisabled
        }, [Validators.required]],
        status: [{
          value: this.basicInformation.status,
          disabled: this.isDisabled
        }, []],
      }),

      finacleCustomerUpdateForm: this.formBuilder.group({
        afCustomerAddressDTOList: this.formBuilder.array(this.createCustomerAddressFormArray()),
        afCustomerTelephoneDTOList: this.formBuilder.array(this.createCustomerTelephoneFormArray()),
        emailAddress: [{
          value: this.basicInformation.afCustomerDTO.emailAddress,
          disabled: this.isDisabled
        }, [Validators.email]],
      })
    });

    return this.basicInformationForm;
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

  saveBasicDetails() {
    let basicInformation = {
      ...this.basicInformation,
      applicationFormID: this.applicationForm.applicationFormID,
      type: this.informationType,
      primaryInformation: this.basicInformation.primaryInformation ? this.basicInformation.primaryInformation : this.applicationForm.basicInformationDTOList ? (this.applicationForm.basicInformationDTOList.length == 0 || this.applicationForm.basicInformationDTOList.length == 1 && this.basicInformation.basicInformationID) ? 'Y' : 'N' : 'Y',
    };

    switch (this.informationType) {
      case this.basicInformationTypeConst.PERSONAL:
        basicInformation = {
          ...basicInformation,
          ...this.basicInformationForm.getRawValue().personalInformationForm,
        };
        break;
      case this.basicInformationTypeConst.BUSINESS:
        basicInformation = {
          ...basicInformation,
          ...this.basicInformationForm.getRawValue().businessInformationForm,
          capitalAuthorized: this.getValue(this.basicInformationForm.getRawValue().businessInformationForm.capitalAuthorized),
          capitalIssued: this.getValue(this.basicInformationForm.getRawValue().businessInformationForm.capitalIssued),
          capitalPaidUp: this.getValue(this.basicInformationForm.getRawValue().businessInformationForm.capitalPaidUp),

        };
        break;
      case this.basicInformationTypeConst.CORPORATE:
        basicInformation = {
          ...basicInformation,
          ...this.basicInformationForm.getRawValue().corporateInformationForm,
          capitalAuthorized: this.getValue(this.basicInformationForm.getRawValue().corporateInformationForm.capitalAuthorized),
          capitalIssued: this.getValue(this.basicInformationForm.getRawValue().corporateInformationForm.capitalIssued),
          capitalPaidUp: this.getValue(this.basicInformationForm.getRawValue().corporateInformationForm.capitalPaidUp),
        };
        break;
      default:
    }

    let afCustomerDTO = {...this.basicInformationForm.getRawValue().finacleCustomerUpdateForm};
    let saveObj = Object.assign({}, basicInformation, {afCustomerDTO: afCustomerDTO});

    this.applicationFormCreateService.saveOrUpdateAFBasicDetails(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  getValue(amount) {
    if (amount) {
      if (isNaN(amount)) {
        return amount.replace(/,/g, '');
      }
    }
    return amount;
  }

  showRadioButton(type) {
    // this The primary party should enable all the radio buttons and other joining parties should behave according to the primary party type
    if (this.applicationForm.basicInformationDTOList.length == 0) {
      return true;
    } else if (this.applicationForm.formType == type || this.basicInformation.primaryInformation == this.yesNo.Y) {
      return true;
    }
    return false;
  }


  createCustomerAddressFormArray() {
    let address = [];
    this.basicInformation.afCustomerDTO.afCustomerAddressDTOList.forEach(customerAddress => {
      address.push(this.createCustomerAddress(customerAddress))
    });
    return address;
  }

  createCustomerAddress(customerAddress): FormGroup {
    return this.formBuilder.group({
      afCustomerAddressID: [customerAddress.afCustomerAddressID],
      address1: [customerAddress.address1],
      address2: [customerAddress.address2],
      city: [customerAddress.city],
      addressType: [customerAddress.addressType],
      status: [customerAddress.status],
    });
  }

  createCustomerTelephoneFormArray() {
    let telephones = [];
    this.basicInformation.afCustomerDTO.afCustomerTelephoneDTOList.forEach(customerTelephone => {
      telephones.push(this.createCustomerTelephone(customerTelephone))
    });
    return telephones;
  }

  createCustomerTelephone(customerTelephone): FormGroup {
    return this.formBuilder.group({
      afCustomerTelephoneID: [customerTelephone.afCustomerTelephoneID],
      contactNumber: [customerTelephone.contactNumber],
      description: [customerTelephone.description],
      status: [customerTelephone.status],
    });
  }

}
