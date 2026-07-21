import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppUtils} from "../../app.utils";
import {Constants} from "../../../core/setting/constants";

@Component({
  selector: 'app-generate-bcc-dialog',
  templateUrl: './generate-bcc-dialog.component.html',
  styleUrls: ['./generate-bcc-dialog.component.scss']
})
export class GenerateBccDialogComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  componentForm: FormGroup;
  onFormValueChangeSub: Subscription = new Subscription();
  formErrors: any;

  optionsPaperTypeSelect: any = [
    {value: Constants.BCCPaperTypeConst.BCC, label: Constants.BCCPaperType.BCC},
    {value: Constants.BCCPaperTypeConst.BCCC, label: Constants.BCCPaperType.BCCC},
    {value: Constants.BCCPaperTypeConst.EEBCC, label: Constants.BCCPaperType.EEBCC},
    {value: Constants.BCCPaperTypeConst.BRPTR, label: Constants.BCCPaperType.BRPTR},
    {value: Constants.BCCPaperTypeConst.BRPGG, label: Constants.BCCPaperType.BRPGG},
  ];

  constructor(private readonly mdbModalRef: MDBModalRef, private readonly formBuilder: FormBuilder) {
  }

  ngOnInit() {

    this.formErrors = {
      paperType: {},
    };

    this.componentForm = this.formBuilder.group({
      paperType: ['', Validators.required],
    });

    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
  }

  isValid() {
    return this.componentForm.valid;
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  generateBCCPaper(): void {
    this.action.next(this.componentForm.getRawValue());
    this.mdbModalRef.hide();
  }

}
