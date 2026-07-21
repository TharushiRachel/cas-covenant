import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {IMyOptions} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {AppUtils} from "../../../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-business-information-add-edit',
  templateUrl: './apf-business-information-add-edit.component.html',
  styleUrls: ['./apf-business-information-add-edit.component.scss']
})
export class ApfBusinessInformationAddEditComponent implements OnInit, OnDestroy {
  @Input() businessInformationForm: FormGroup;
  basicInformationConst = Constants.basicInformationTypeConst;
  basicInformation = Constants.basicInformationType;
  constitutionConst = Constants.constitutionConst;
  constitution = Constants.constitution;
  civilStatus = Constants.civilStatus;
  civilStatusConst = Constants.civilStatusConst;
  employStatus = Constants.employStatus;
  employStatusConst = Constants.employStatusConst;
  formErrors: any = {};
  isDisabled: boolean = false;

  optionsSelectConstitution = Constants.optionsBusinessSelectConstitutionOpt;

  dateOfRegistrationOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 138,
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  dateOfCommencementOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 138,
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.formErrors = {
      nameOfBusiness: {},
      registrationNo: {},
      constitution: {},
      dateOfRegistrationStr: {},
      officialAddress: {},
      telNoOfficial: {},
      emailAddress: {},
      natureOfBusiness: {},
      dateOfCommencementStr: {},
    };

  }

  ngOnDestroy(): void {
  }

  isPrivateLTD() {
    return this.businessInformationForm.controls.constitution.value == this.constitutionConst.PRIVATE_LTD;
  }

  setCurrencyFormatValue(control) {
    const amount = AppUtils.getValue(this.getFormRawData()[control]);
    this.businessInformationForm.patchValue({
      [control]: this.currencyPipe.transform(amount, '', '')
    });
  }


  getFormRawData() {
    return this.businessInformationForm.getRawValue();
  }

}
