import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {Constants} from "../../../../../../core/setting/constants";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../../../../../shared/app.utils";

@Component({
  selector: 'app-internal-lead-start-paper',
  templateUrl: './internal-lead-start-paper.component.html',
  styleUrls: ['./internal-lead-start-paper.component.scss']
})
export class InternalLeadStartPaperComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  formErrors: any = {};
  action: Subject<any> = new Subject<any>();

  onFormValueChangeSub = new Subscription();
  onLeadStatusUpdatedSub = new Subscription();

  leadStatusConst = Constants.leadStatusConst;
  leadActionConst = Constants.leadActionConst;

  leadID;
  createdBy;
  accountNumber;
  customerID;

  constructor(public  mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder) {

    this.formErrors = {
      remark: {},
      customerBankAccountNumber: {},
    };
  }

  ngOnInit() {
    this.componentForm = this.createCommentForm();

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onLeadStatusUpdatedSub.unsubscribe();
    this.action.unsubscribe();
  }

  createCommentForm() {
    return this.formBuilder.group({
      customerBankAccountNumber: [
        {value: this.accountNumber ? this.accountNumber : '', disabled: true}
        , [Validators.required, Validators.maxLength(50)]],
      remark: ['', [Validators.required, Validators.maxLength(4000)]],
    })
  }

  onStartPaper() {

    let formData = this.componentForm.getRawValue();

    let data = {
      ...formData
    };

    this.action.next(data);
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.componentForm.valid
  }

}
