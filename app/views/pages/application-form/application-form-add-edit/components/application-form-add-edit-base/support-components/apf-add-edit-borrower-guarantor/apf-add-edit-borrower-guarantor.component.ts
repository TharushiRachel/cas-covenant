import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {Subject} from "rxjs";
import {Constants} from "../../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-add-edit-borrower-guarantor',
  templateUrl: './apf-add-edit-borrower-guarantor.component.html',
  styleUrls: ['./apf-add-edit-borrower-guarantor.component.scss']
})
export class ApfAddEditBorrowerGuarantorComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  borrowerGuarantorForm: FormGroup;
  borrowerGuarantor: any = {};
  applicationForm: any = {};
  basicInformation: any = {};
  action: Subject<any> = new Subject<any>();
  yesNoConst = Constants.yesNoConst;

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  constructor(public mdbModalRef: MDBModalRef,
              private currencyPipe: CurrencyPipe,
              private formBuilder: FormBuilder,) {
  }

  ngOnInit() {
    this.borrowerGuarantorForm = this.createForm();
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.getFormRawData()[control]);
    this.borrowerGuarantorForm.patchValue({
      [control]: this.currencyPipe.transform(amount, '', '')
    });
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  getFormRawData() {
    return this.borrowerGuarantorForm.getRawValue();
  }

  save() {
    let saveObj = {
      ...this.borrowerGuarantorForm.getRawValue(),
      amount: this.getValue(this.borrowerGuarantorForm.getRawValue().amount),
      status: Constants.statusConst.ACT,
      isBorrowerOrGuarantor: this.yesNoConst.Y
    };
    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  createForm() {
    this.borrowerGuarantorForm = this.formBuilder.group({
      borrowerGuarantorID: [this.borrowerGuarantor.borrowerGuarantorID, Validators.required],
      applicationFormID: [this.applicationForm.applicationFormID, Validators.required],
      basicInformationID: [this.basicInformation.basicInformationID, Validators.required],
      financialObligationID: [this.borrowerGuarantor.financialObligationID],
      amount: [this.getCurrencyFormat(this.borrowerGuarantor.amount), Validators.required],
      bankAndBranch: [this.borrowerGuarantor.bankAndBranch, Validators.required],
      dateGrantedStr: [this.borrowerGuarantor.dateGrantedStr, Validators.required],
      borrowerName: [this.borrowerGuarantor.borrowerName]
    });

    return this.borrowerGuarantorForm;
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

}
