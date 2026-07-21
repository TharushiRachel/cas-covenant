import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import * as _ from "lodash";
import {AppUtils} from "../../app.utils";


@Component({
  selector: 'app-from-to-date-dialog',
  templateUrl: './from-to-date-dialog.component.html',
  styleUrls: ['./from-to-date-dialog.component.scss']
})
export class FromToDateDialogComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  componentForm: FormGroup;
  onFormValueChangeSub = new Subscription();
  formErrors: any;

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy/mm/dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  constructor(private mdbModalRef: MDBModalRef, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.formErrors = {
      fromDate: {},
      toDate: {},
    };

    this.componentForm = this.createDirectorDetailForm();
    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    this.action.next(this.componentForm.getRawValue());
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm && this.componentForm.dirty;
  }


  createDirectorDetailForm() {
    this.componentForm = this.formBuilder.group({
      fromDate: ['', [Validators.required]],
      toDate: ['', [Validators.required]],
    });
    this.componentForm.setValidators(DateValidator.dateRangeValidator);
    return this.componentForm;
  }
}

class DateValidator {
  public static dateRangeValidator(control: FormGroup): ValidationErrors | null {
    const fromDate = control.get('fromDate').value;
    const toDate = control.get('toDate').value;

    if (!_.isEmpty(fromDate) && !_.isEmpty(fromDate)) {
      if (toDate < fromDate) {
        control.get('toDate').setErrors({invalidDateRange: true}, {emitEvent: true});
        return {
          invalidDateRange: true
        };
      } else {
        control.get('toDate').setErrors(null, {emitEvent: true});
      }
    }
    return null;
  }
}
