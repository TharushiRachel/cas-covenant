import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-add-edit-other-bank-details',
  templateUrl: './apf-add-edit-other-bank-details.component.html',
  styleUrls: ['./apf-add-edit-other-bank-details.component.scss']
})
export class ApfAddEditOtherBankDetailsComponent implements OnInit, OnDestroy {
  heading: string;
  message: string;
  basicInformation: any = {};
  applicationForm: any = {};
  otherBankDetail: any = {};
  action: Subject<any> = new Subject<any>();
  componentForm: FormGroup;
  onFormValueChangeSub = new Subscription();
  formErrors: any;

  constructor(private mdbModalRef: MDBModalRef, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.formErrors = {
      comment: {},
    };

    this.createForm();
    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  save(): void {
    this.action.next(this.componentForm.getRawValue());
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  isValid() {
    return this.componentForm && this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm && this.componentForm.dirty;
  }

  createForm() {
    this.componentForm = this.formBuilder.group({
      otherBankDetailsID: [this.otherBankDetail.otherBankDetailsID],
      basicInformationID: [this.basicInformation.basicInformationID],
      applicationFormID: [this.applicationForm.applicationFormID],
      nameOfBank: [this.otherBankDetail.nameOfBank, [Validators.required]],
      nameOfBranch: [this.otherBankDetail.nameOfBranch, [Validators.required]],
      accountNo: [this.otherBankDetail.accountNo, [Validators.required]],
      typeOfAccount: [this.otherBankDetail.typeOfAccount, [Validators.required]],
      status: [this.otherBankDetail.status ? this.otherBankDetail.status : 'ACT']
    });
  }

}
