import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../app.utils";

@Component({
  selector: 'app-comment-dialog',
  templateUrl: './comment-dialog.component.html',
  styleUrls: ['./comment-dialog.component.scss']
})
export class CommentDialogComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
  actionName: string;
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

  submitComment(): void {
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
      comment: ['', [Validators.required]],
    });
  }

}
