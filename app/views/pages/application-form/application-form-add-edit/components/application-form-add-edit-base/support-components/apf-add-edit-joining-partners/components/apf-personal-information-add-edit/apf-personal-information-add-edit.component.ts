import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {IMyDate, IMyOptions} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {NumberValidator} from "../../../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-apf-personal-information-add-edit',
  templateUrl: './apf-personal-information-add-edit.component.html',
  styleUrls: ['./apf-personal-information-add-edit.component.scss']
})
export class ApfPersonalInformationAddEditComponent implements OnInit, OnDestroy {
  @Input() personalInformationForm: FormGroup;
  onFormChangeSub = new Subscription();
  basicInformationConst = Constants.basicInformationTypeConst;
  basicInformation = Constants.basicInformationType;
  identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;
  titleConst = Constants.titleConst;
  title = Constants.title;
  civilStatus = Constants.civilStatus;
  civilStatusConst = Constants.civilStatusConst;
  employStatus = Constants.employStatus;
  employStatusConst = Constants.employStatusConst;
  formErrors: any = {};
  isDisabled: boolean = false;

  optionsSelectTitle = Constants.optionsSelectTitleOpt;
  optionsEmploymentSelect = Constants.optionsEmploymentSelectOpt;
  optionsCivilStatusSelect = Constants.optionsCivilStatusSelectOpt;

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

  ngOnDestroy(): void {
    this.onFormChangeSub.unsubscribe();
  }

  isSelfEmployed() {
    return this.personalInformationForm.controls.employment.value == this.employStatusConst.SELF_EMPLOYED;
  }

  isEmployed() {
    return this.personalInformationForm.controls.employment.value == this.employStatusConst.EMPLOYED;
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

}
