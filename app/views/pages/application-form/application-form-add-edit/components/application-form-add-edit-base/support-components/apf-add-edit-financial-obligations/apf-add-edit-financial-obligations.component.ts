import {Component, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject} from "rxjs";
import {Constants} from "../../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {CurrencyPipe} from "@angular/common";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-apf-add-edit-financial-obligations',
  templateUrl: './apf-add-edit-financial-obligations.component.html',
  styleUrls: ['./apf-add-edit-financial-obligations.component.scss']
})
export class ApfAddEditFinancialObligationsComponent implements OnInit {

  heading: string;
  content: any;
  applicationForm: any = {};
  basicInformation: any = {};
  financialObligation: any = {};
  action: Subject<any> = new Subject<any>();
  financialObligationForm: FormGroup;
  formErrors: any;

  optionsSignedAsSelect = [
    {value: Constants.financialObligationsConst.BORROWER, label: Constants.financialObligations.BORROWER},
    {value: Constants.financialObligationsConst.GUARANTOR, label: Constants.financialObligations.GUARANTOR},
    {value: Constants.financialObligationsConst.INDEMNITOR, label: Constants.financialObligations.INDEMNITOR},
  ];

  constructor(
    public mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private currencyPipe: CurrencyPipe,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {
    this.financialObligationForm = this.createForm();
  }

  createForm() {

    this.financialObligationForm = this.formBuilder.group({
      applicationFormID: [this.applicationForm.applicationFormID, Validators.required],
      basicInformationID: [this.basicInformation.basicInformationID, Validators.required],
      financialObligationID: [this.financialObligation.financialObligationID],
      originalAmount: [this.getCurrencyFormat(this.financialObligation.originalAmount), Validators.required],
      presentOutstanding: [this.getCurrencyFormat(this.financialObligation.presentOutstanding), Validators.required],
      arrears: [this.getCurrencyFormat(this.financialObligation.arrears), Validators.required],
      securities: [this.financialObligation.securities, Validators.required],
      signedAs: [this.financialObligation.signedAs],
      financialInstitution: [this.financialObligation.financialInstitution, Validators.required],
      identificationNumber: [{
        value: this.financialObligation.identificationNumber ? this.financialObligation.identificationNumber : this.basicInformation.identificationNo,
        disabled: true
      }, Validators.required],
      identificationType: [{
        value: this.financialObligation.identificationType ? this.financialObligation.identificationType : this.basicInformation.identificationType,
        disabled: true
      }, Validators.required],
    });

    return this.financialObligationForm;
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.getFormRawData()[control]);
    this.financialObligationForm.patchValue({
      [control]: this.currencyPipe.transform(amount, '', '')
    });
  }

  getFormRawData() {
    return this.financialObligationForm.getRawValue();
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  save(): void {
    let saveObj = {
      ...this.financialObligationForm.getRawValue(),
      originalAmount: this.getValue(this.financialObligationForm.getRawValue().originalAmount),
      presentOutstanding: this.getValue(this.financialObligationForm.getRawValue().presentOutstanding),
      arrears: this.getValue(this.financialObligationForm.getRawValue().arrears),
      modifiedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      status: Constants.statusConst.ACT
    };
    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

}
