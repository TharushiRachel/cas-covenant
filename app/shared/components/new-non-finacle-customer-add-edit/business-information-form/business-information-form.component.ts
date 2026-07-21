import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {IMyOptions} from "ng-uikit-pro-standard";
import {Constants} from "../../../../core/setting/constants";
import {CurrencyPipe} from "@angular/common";
import {NumberValidator} from "../../../validators/number.validator";
import {AppUtils} from "../../../app.utils";

@Component({
  selector: 'app-business-information-form',
  templateUrl: './business-information-form.component.html',
  styleUrls: ['./business-information-form.component.scss']
})
export class BusinessInformationFormComponent implements OnInit, OnDestroy {
  @Input() businessInformationForm: FormGroup;
  optionsSelectConstitution = Constants.optionsBusinessSelectConstitutionOpt;
  constitutionConst = Constants.constitutionConst;
  formErrors: any = {};

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

  ngOnDestroy(): void {
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }
}
