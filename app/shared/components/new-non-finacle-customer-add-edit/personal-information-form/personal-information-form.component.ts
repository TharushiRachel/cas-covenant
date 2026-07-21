import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Constants} from "../../../../core/setting/constants";
import {NumberValidator} from "../../../validators/number.validator";
import {IMyDate, IMyOptions} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-personal-information-form',
  templateUrl: './personal-information-form.component.html',
  styleUrls: ['./personal-information-form.component.scss']
})
export class PersonalInformationFormComponent implements OnInit, OnDestroy {
  @Input() personalInformationForm: FormGroup;
  employStatusConst = Constants.employStatusConst;
  optionsSelectTitle = Constants.optionsSelectTitleOpt;
  optionsEmploymentSelect = Constants.optionsEmploymentSelectOpt;
  optionsCivilStatusSelect = Constants.optionsCivilStatusSelectOpt;
  identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;
  formErrors: any = {};

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


  constructor() {
  }

  ngOnInit() {

    this.formErrors = {
      title: {},
      type: {},
      nameWithInitials: {},
      initialRepresentation: {},
      privateAddress: {},
      officialAddress: {},
      telNoOfficial: {},
      emailAddress: {},
      dateOfBirthStr: {},
      placeOfBirth: {},
      civilStatus: {},
      nationality: {},
      identificationType: {},
      identificationNo: {},
      employment: {},
      highestEduAchievement: {},
      natureOfBusiness: {},
      noOfBusinessYears: {},
    };

  }

  isEmployed() {
    return this.personalInformationForm.controls.employment.value == this.employStatusConst.EMPLOYED;
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  isSelfEmployed() {
    return this.personalInformationForm.controls.employment.value == this.employStatusConst.SELF_EMPLOYED;
  }

  ngOnDestroy(): void {
  }

}
