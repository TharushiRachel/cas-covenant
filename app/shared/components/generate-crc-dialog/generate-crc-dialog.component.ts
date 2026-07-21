import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { Subject, Subscription } from 'rxjs';
import { Constants } from 'src/app/core/setting/constants';
import { AppUtils } from '../../app.utils';

@Component({
  selector: 'app-generate-crc-dialog',
  templateUrl: './generate-crc-dialog.component.html',
  styleUrls: ['./generate-crc-dialog.component.scss']
})
export class GenerateCrcDialogComponent implements OnInit {

  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  componentForm: FormGroup;
  onFormValueChangeSub: Subscription = new Subscription();
  formErrors: any;

  optionsPaperTypeSelect: any = [
    {value: Constants.CRCPaperTypeConst.ROF, label: Constants.CRCPaperType.ROF},
    {value: Constants.CRCPaperTypeConst.DF, label: Constants.CRCPaperType.DF},
    {value: Constants.CRCPaperTypeConst.RF1, label: Constants.CRCPaperType.RF1},
    {value: Constants.CRCPaperTypeConst.RF2, label: Constants.CRCPaperType.RF2},
  ];

  constructor(private mdbModalRef: MDBModalRef, private formBuilder: FormBuilder) {
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

  generateCRCPaper(): void {
    this.action.next(this.componentForm.getRawValue());
    this.mdbModalRef.hide();
  }


}
