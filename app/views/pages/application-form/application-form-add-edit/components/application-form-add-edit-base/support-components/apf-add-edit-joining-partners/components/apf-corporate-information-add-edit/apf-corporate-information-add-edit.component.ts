import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {IMyOptions} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {Subscription} from "rxjs";
import {AppUtils} from "../../../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-corporate-information-add-edit',
  templateUrl: './apf-corporate-information-add-edit.component.html',
  styleUrls: ['./apf-corporate-information-add-edit.component.scss']
})
export class ApfCorporateInformationAddEditComponent implements OnInit, OnDestroy {

  @Input() corporateInformationForm: FormGroup;
  onFormChangeSub = new Subscription();
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
  constitutionType;

  optionsSelectConstitution = Constants.optionsCorporateSelectConstitutionOpt;

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

    this.onFormChangeSub = this.corporateInformationForm.controls.constitution.valueChanges.subscribe(val => {
      if (val) {
        this.constitutionType = val;
      }
    });
  }

  ngOnDestroy(): void {
    this.onFormChangeSub.unsubscribe();
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
    return this.constitutionType == this.constitutionConst.PRIVATE_LTD;
  }
}
