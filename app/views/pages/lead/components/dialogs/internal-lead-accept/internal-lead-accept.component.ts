import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {Constants} from "../../../../../../core/setting/constants";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {LeadAddEditService} from "../../../services/lead-add-edit.service";
import {ApplicationService} from "../../../../../../core/service/application/application.service";
import {AppUtils} from "../../../../../../shared/app.utils";

@Component({
  selector: 'app-internal-lead-accept',
  templateUrl: './internal-lead-accept.component.html',
  styleUrls: ['./internal-lead-accept.component.scss']
})
export class InternalLeadAcceptComponent implements OnInit, OnDestroy {

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

  constructor(public  mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder,
              private leadAddEditService: LeadAddEditService,
              private applicationService: ApplicationService,) {

    this.formErrors = {
      customerBankAccountNumber: {},
      remark: {},
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
      customerBankAccountNumber: [this.accountNumber ? this.accountNumber : '', [Validators.maxLength(50)]],
      remark: ['', [Validators.required, Validators.maxLength(4000)]],
    })
  }

  onAccept() {

    let formData = this.componentForm.getRawValue();

    let data = {
      leadID: this.leadID,
      leadStatus: this.leadStatusConst.ACCEPTED,
      assignUserID: this.createdBy,
      actionedByDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      action: this.leadActionConst.ACCEPTED,
      allowFinacleData: true,
      allowCrib: true,
      allowKalypto: true,
      ...formData
    };

    this.leadAddEditService.updateLeadStatusOrAssignee(data);
    this.action.next(true);
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.componentForm.valid
  }

}
