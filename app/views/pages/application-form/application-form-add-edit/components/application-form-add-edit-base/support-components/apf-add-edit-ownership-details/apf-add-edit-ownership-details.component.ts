import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import * as _ from "lodash";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-apf-add-edit-ownership-details',
  templateUrl: './apf-add-edit-ownership-details.component.html',
  styleUrls: ['./apf-add-edit-ownership-details.component.scss']
})
export class ApfAddEditOwnershipDetailsComponent implements OnInit, OnDestroy {

  ownershipDetailsForm: FormGroup;
  onApplicationFormChangeSub = new Subscription();
  ownershipDetails: any = {};
  basicInformation: any = {};
  applicationFrom: any = {};
  isDisabled: boolean = false;
  content: any = {};
  heading: string;

  civilStatusConst = Constants.civilStatusConst;
  civilStatus = Constants.civilStatus;
  identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;

  creditCardOptionsYesNoSelect: any = [
    {value: Constants.yesNoConst.Y, label: Constants.yesNo.Y},
    {value: Constants.yesNoConst.N, label: Constants.yesNo.N},
  ];

  optionsConstitutionTypeSelect: any = [
    {value: Constants.ConstitutionTypeConst.CHAIRMAN, label: Constants.ConstitutionType.CHAIRMAN},
    {value: Constants.ConstitutionTypeConst.DIRECTOR, label: Constants.ConstitutionType.DIRECTOR},
    {value: Constants.ConstitutionTypeConst.MANAGING_DIRECTOR, label: Constants.ConstitutionType.MANAGING_DIRECTOR},
    {value: Constants.ConstitutionTypeConst.PARTNER, label: Constants.ConstitutionType.PARTNER},
  ];

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

  optionsCivilStatusSelect: any = [
    {value: '', label: ''},
    {value: this.civilStatusConst.MARRIED, label: this.civilStatus.MARRIED},
    {value: this.civilStatusConst.SINGLE, label: this.civilStatus.SINGLE},
    {value: this.civilStatusConst.UNKNOWN, label: this.civilStatus.UNKNOWN},
  ];

  constructor(private formBuilder: FormBuilder,
              public  mdbModalRef: MDBModalRef,
              private applicationFormCreateService: ApplicationFormAddEditService) {
  }

  ngOnInit() {

    this.onApplicationFormChangeSub = this.applicationFormCreateService.onApplicationFormChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationFrom = data;
      }
    });
    this.basicInformation = this.content.basicInformation;
    this.ownershipDetails = this.content.ownershipDetails;

    this.ownershipDetailsForm = this.createFrom();
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  createFrom() {
    this.ownershipDetailsForm = this.formBuilder.group({
      applicationFormID: [this.ownershipDetails.applicationFormID ? this.ownershipDetails.applicationFormID : this.applicationFrom.applicationFormID],
      ownershipDetailsID: [this.ownershipDetails.ownershipDetailsID],
      basicInformationID: [this.ownershipDetails.basicInformationID ? this.ownershipDetails.basicInformationID : this.basicInformation.basicInformationID],
      name: [{value: this.ownershipDetails.name, disabled: this.isDisabled}, [Validators.required]],
      address: [{value: this.ownershipDetails.address, disabled: this.isDisabled}, [Validators.required]],
      contactNo: [{value: this.ownershipDetails.contactNo, disabled: this.isDisabled}, [Validators.required]],
      identificationType: [{
        value: this.ownershipDetails.identificationType,
        disabled: this.isDisabled
      }, [Validators.required]],
      identificationNumber: [{
        value: this.ownershipDetails.identificationNumber,
        disabled: this.isDisabled
      }, [Validators.required]],
      shareHolding: [{value: this.ownershipDetails.shareHolding, disabled: this.isDisabled}, [Validators.required]],
      constitutionType: [{
        value: this.ownershipDetails.constitutionType,
        disabled: this.isDisabled
      }, [Validators.required]],
      civilStatus: [{
        value: this.ownershipDetails.civilStatus,
        disabled: this.isDisabled
      }, [Validators.required]],
      dateOfBirthStr: [{
        value: this.ownershipDetails.dateOfBirthStr,
        disabled: this.isDisabled
      }, [Validators.required]],
      creditCard: [{
        value: this.ownershipDetails.creditCard,
        disabled: this.isDisabled
      }, [Validators.required]],
      status: [{
        value: this.ownershipDetails.status,
        disabled: this.isDisabled
      }, [Validators.required]],
    });
    return this.ownershipDetailsForm
  }

  saveOwnerShipDetails() {
    let saveData = {...this.ownershipDetailsForm.getRawValue()};
    this.applicationFormCreateService.saveOrUpdateOwnershipDetails(AppUtils.trim(saveData));
    this.mdbModalRef.hide();
  }

}
