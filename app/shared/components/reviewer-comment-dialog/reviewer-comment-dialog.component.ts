import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../app.utils";
import {Constants} from "../../../core/setting/constants";

@Component({
  selector: 'app-reviewer-comment-dialog',
  templateUrl: './reviewer-comment-dialog.component.html',
  styleUrls: ['./reviewer-comment-dialog.component.scss']
})
export class ReviewerCommentDialogComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
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

    this.createDirectorDetailForm();
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
    let obj = {...this.componentForm.getRawValue(), paperReviewStatus: Constants.paperReviewStatusConst.SAVED};
    this.action.next(obj);
    this.mdbModalRef.hide();
  }

  reject(): void {
    let obj = {...this.componentForm.getRawValue(), paperReviewStatus: Constants.paperReviewStatusConst.REJECTED};
    this.action.next(obj);
    this.mdbModalRef.hide();
  }

  approve(): void {
    let obj = {...this.componentForm.getRawValue(), paperReviewStatus: Constants.paperReviewStatusConst.APPROVED};
    this.action.next(obj);
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


  createDirectorDetailForm() {
    this.componentForm = this.formBuilder.group({
      comment: ['', [Validators.required]],
    });
  }

}
