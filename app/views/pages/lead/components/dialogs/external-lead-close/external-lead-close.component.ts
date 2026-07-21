import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {Constants} from "../../../../../../core/setting/constants";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {LeadAddEditService} from "../../../services/lead-add-edit.service";
import {AppUtils} from "../../../../../../shared/app.utils";
import {ApplicationService} from "../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-external-lead-close',
  templateUrl: './external-lead-close.component.html',
  styleUrls: ['./external-lead-close.component.scss']
})
export class ExternalLeadCloseComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  formErrors: any = {};
  action: Subject<any> = new Subject<any>();

  onFormValueChangeSub = new Subscription();
  onLeadStatusUpdatedSub = new Subscription();

  leadStatusConst = Constants.leadStatusConst;
  leadActionConst = Constants.leadActionConst;

  leadID;
  createdBy;

  constructor(public  mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder,
              private leadAddEditService: LeadAddEditService,
              private applicationService: ApplicationService) {

    this.formErrors = {
      remark: {}
    };
  }

  ngOnInit() {
    this.componentForm = this.createCommentForm();

    this.onLeadStatusUpdatedSub = this.leadAddEditService.onLeadStatusUpdated
      .subscribe(() => {
        this.mdbModalRef.hide();
      });

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
      remark: ['', [Validators.required, Validators.maxLength(4000)]],
    })
  }

  onClose() {

    let data = {
      leadID: this.leadID,
      leadStatus: this.leadStatusConst.CLOSED,
      remark: this.componentForm.controls.remark.value,
      assignUserID: this.createdBy,
      actionedByDisplayName: this.applicationService.getUserDisplayName,
      action: this.leadActionConst.CLOSED,
    };

    this.leadAddEditService.updateLeadStatusOrAssignee(data);
    this.action.next(true);
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.componentForm.valid
  }

}
