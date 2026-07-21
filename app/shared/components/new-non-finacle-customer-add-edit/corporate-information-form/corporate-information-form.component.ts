import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {IMyOptions} from "ng-uikit-pro-standard";
import {Constants} from "../../../../core/setting/constants";
import {CurrencyPipe} from "@angular/common";
import {AppUtils} from "../../../app.utils";
import {NumberValidator} from "../../../validators/number.validator";

@Component({
  selector: 'app-corporate-information-form',
  templateUrl: './corporate-information-form.component.html',
  styleUrls: ['./corporate-information-form.component.scss']
})
export class CorporateInformationFormComponent implements OnInit, OnDestroy {
  @Input() corporateInformationForm: FormGroup;
  optionsSelectConstitution = Constants.optionsCorporateSelectConstitutionOpt;
  constitutionConst = Constants.constitutionConst;
  formErrors: any = {};

  dateOfIncorporateOptions: IMyOptions = {
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
      dateOfIncorporateStr: {},
      officialAddress: {},
      telNoOfficial: {},
      emailAddress: {},
      natureOfBusiness: {},
      dateOfCommencementStr: {},
    };
  }

  ngOnDestroy(): void {
  }

  setCurrencyFormatValue(control) {
    const amount = AppUtils.getValue(this.getFormRawData()[control]);
    this.corporateInformationForm.patchValue({
      [control]: this.currencyPipe.transform(amount, '', '')
    });
  }

  getFormRawData() {
    return this.corporateInformationForm.getRawValue();
  }

  isPrivateLTD() {
    return this.corporateInformationForm.controls.constitution.value == this.constitutionConst.PRIVATE_LTD;
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

}
