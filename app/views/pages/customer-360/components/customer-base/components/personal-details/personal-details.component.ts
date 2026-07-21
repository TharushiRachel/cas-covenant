import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../../core/setting/constants";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PersonalUpdateDto} from "../../dto/personal-update-dto";
import * as _ from 'lodash';
import {CustomerIdentificationUpdateDto} from "../../dto/customer-identification-update-dto";
import {CustomerBaseService} from "../../../../services/customer-base.service";
import {CustomerTelephoneUpdateDto} from "../../dto/customer-telephone-update-dto";
import {CustomerAddressUpdateDto} from "../../dto/customer-address-update-dto";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {IMyDate, IMyOptions} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrls: ['./personal-details.component.scss']
})
export class PersonalDetailsComponent implements OnInit {

  @Input('customer360Details') customer360Details: any;
  @Output('refreshCustomerDetailFromBank') refreshCustomerDetailFromBank = new EventEmitter();
  civilStatus = Constants.civilStatus;
  customerIdentificationType = Constants.customerIdentificationType;
  addressType = Constants.addressType;
  contactNumberType = Constants.contactNumberType;
  IDSelect = Constants.customerIdentificationTypeOptionsSelect;
  status = Constants.status;
  statusConst = Constants.statusConst;
  addressSelect = Constants.addressSelect;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  IdentificationTableColumns = ['Identification Type', 'ID Number', 'Action'];
  tpColumns = ['Contact Number', 'Description', 'Action'];
  addressColumns = ['Address Type', 'Address 1', 'Address 2', 'City', 'Action'];

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy'
  };

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate(),
  };

  myDatePickerOptionsForBirthDay: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };


  optionsCivilStatusSelect = [
    {value: 'MARRIED', label: 'Married'},
    {value: 'SINGLE', label: 'Single'},
    {value: 'UNKNOWN', label: 'Unknown'},
  ];

  componentForm: FormGroup;
  idForm: FormGroup;
  tpForm: FormGroup;
  addressForm: FormGroup;

  resetted: any = false;
  isEnable: boolean = false;
  loadMoreClicked: boolean = false;

  personalUpdateDTO: PersonalUpdateDto = new PersonalUpdateDto({});
  addressUpdateDTO: CustomerAddressUpdateDto = new CustomerAddressUpdateDto({});
  selectedCustomer: any = {};
  idList: CustomerIdentificationUpdateDto[] = [];
  tempIdList: CustomerIdentificationUpdateDto[] = [];
  tpList: CustomerTelephoneUpdateDto[] = [];
  temptpList: CustomerTelephoneUpdateDto[] = [];
  addressList: CustomerAddressUpdateDto[] = [];
  tempAddressList: CustomerAddressUpdateDto[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private customerBaseService: CustomerBaseService
  ) {
  }

  ngOnInit() {

    if (!_.isEmpty(this.customer360Details)) {
      this.personalUpdateDTO = new PersonalUpdateDto(this.customer360Details);
      this.idList = this.customer360Details.customerIdentificationDTOList.splice(0);
      this.tempIdList = _.cloneDeep(this.idList);
      this.tpList = this.customer360Details.customerTelephoneDTOList.splice(0);
      this.temptpList = _.cloneDeep(this.tpList);
      this.addressList = this.customer360Details.customerAddressDTOList.splice(0);
      this.tempAddressList = _.cloneDeep(this.addressList);
    }

    this.componentForm = this.formBuilder.group({
      customerFinancialID: [this.personalUpdateDTO.customerFinancialID, [Validators.required]],
      customerName: [this.personalUpdateDTO.customerName, [Validators.required]],
      dateOfBirth: this.personalUpdateDTO.dateOfBirth,
      civilStatus: this.personalUpdateDTO.civilStatus,
      emailAddress: this.personalUpdateDTO.emailAddress,
      secondaryEmailAddress: this.personalUpdateDTO.secondaryEmailAddress,
    });

    this.idForm = this.formBuilder.group({
      identificationType: [Validators.required],
      identificationNumber: ['', Validators.required]
    });

    this.tpForm = this.formBuilder.group({
      contactNumber: ['', Validators.required],
      description: [''],
    });

    this.addressForm = this.formBuilder.group({
      addressType: ['', Validators.required],
      address1: ['', Validators.required],
      address2: [''],
      city: ['']
    });

    this.disableButtons();
  }

  onRemove(item) {
    if (!_.isEmpty(item)) {
      item.status = 'INA'
    }
  }


  isIDFormValid() {
    return this.idForm.valid
  }

  isTpFormValid() {
    return this.tpForm.valid
  }

  isAddressFormValid() {
    return this.addressForm.valid
  }

  isReset() {
    return this.resetted
  }

  isMainFormDirty() {
    return this.componentForm.dirty || this.idForm.dirty || this.tpForm.dirty || this.addressForm.dirty
  }

  onAddID() {
    let listValue = Object.assign({},
      {customerID: this.personalUpdateDTO.customerID},
      {identificationType: this.idForm.controls.identificationType.value},
      {identificationNumber: this.idForm.controls.identificationNumber.value},
      {status: 'ACT'});


    this.idList.push(listValue);

  }

  onAddTp() {
    let listValue = Object.assign({},
      {customerID: this.personalUpdateDTO.customerID},
      {contactNumber: this.tpForm.controls.contactNumber.value},
      {description: this.tpForm.controls.description.value},
      {status: 'ACT'});


    this.tpList.push(listValue);
  }

  onAddAddress() {
    let listValue = Object.assign({},
      {customerID: this.personalUpdateDTO.customerID},
      {addressType: this.addressForm.controls.addressType.value},
      {address1: this.addressForm.controls.address1.value},
      {address2: this.addressForm.controls.address2.value},
      {city: this.addressForm.controls.city.value},
      {status: 'ACT'});

    this.addressList.push(listValue)
  }

  updateCustomer() {
    this.componentForm.enable();
    let updateCustomer = Object.assign({},
      this.personalUpdateDTO,
      this.componentForm.getRawValue(),
      {customerIdentificationDTOList: this.idList},
      {customerTelephoneDTOList: this.tpList},
      {customerAddressDTOList: this.addressList});

    this.customerBaseService.updateCustomerDTO(updateCustomer);
  }

  resetUpdateCustomer() {
    this.resetted = true;
    this.idList = this.tempIdList;
    this.tpList = this.temptpList;
    this.addressList = this.tempAddressList;
    this.componentForm.reset({
      customerFinancialID: this.personalUpdateDTO.customerFinancialID,
      customerName: this.personalUpdateDTO.customerName,
      dateOfBirth: this.personalUpdateDTO.dateOfBirth,
      civilStatus: this.personalUpdateDTO.civilStatus,
      emailAddress: this.personalUpdateDTO.emailAddress,
      secondaryEmailAddress: this.personalUpdateDTO.secondaryEmailAddress
    });
    this.tpForm.reset({
      contactNumber: '',
      description: '',
    }, {onlySelf: true, emitEvent: false});

    this.idForm.reset({
      identificationNumber: '',
      identificationType: '',
    }, {onlySelf: true, emitEvent: false});

    this.addressForm.reset({
      addressType: '',
      address1: '',
      address2: '',
      city: ''
    }, {onlySelf: true, emitEvent: false});

  }

  enableButtons() {
    this.isEnable = true;
    this.componentForm.enable();
    this.idForm.enable();
    this.tpForm.enable();
    this.addressForm.enable();
  }

  disableButtons() {
    this.tpForm.reset({
      contactNumber: '',
      description: '',
    }, {onlySelf: true, emitEvent: false});

    this.idForm.reset({
      identificationNumber: '',
      identificationType: '',
    }, {onlySelf: true, emitEvent: false});

    this.addressForm.reset({
      addressType: '',
      address1: '',
      address2: '',
      city: ''
    }, {onlySelf: true, emitEvent: false});

    this.isEnable = false;
    this.componentForm.disable();
    this.idForm.disable();
    this.tpForm.disable();
    this.addressForm.disable();
  }

  onLoadMoreDetail() {
    this.loadMoreClicked = true;
  }

  onHideDetail() {
    this.loadMoreClicked = false;
  }

  getActiveIdList() {
    if (this.idList != null) {
      return this.idList.filter(value => value.status == 'ACT');
    }
  }

  getActiveTpList() {
    if (this.tpList != null) {
      return this.tpList.filter(value => value.status == 'ACT');
    }
  }
}
