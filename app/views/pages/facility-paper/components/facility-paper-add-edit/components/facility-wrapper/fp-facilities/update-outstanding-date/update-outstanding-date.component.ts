import {Component, OnDestroy, OnInit} from '@angular/core';
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {Subject} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppUtils} from "../../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-update-outstanding-date',
  templateUrl: './update-outstanding-date.component.html',
  styleUrls: ['./update-outstanding-date.component.scss']
})
export class UpdateOutstandingDateComponent implements OnInit, OnDestroy {

  heading: string;
  content: any = {};
  facilityPaper: any = {};
  componentForm: FormGroup;
  action: Subject<any> = new Subject<any>();

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  constructor(public mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit() {
    this.facilityPaper = this.content.facilityPaper ? this.content.facilityPaper : {};
    this.createForm();
  }

  update() {
    let data = Object.assign({}, this.facilityPaper, {outstandingDateStr: this.componentForm.getRawValue().outstandingDateStr});
    this.action.next(AppUtils.trim(data));
    this.mdbModalRef.hide();
  }

  close() {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
  }

  createForm() {
    this.componentForm = this.formBuilder.group({
      outstandingDateStr: ['', [Validators.required]],
    });
    return this.componentForm;
  }

  isValid() {
    return this.componentForm.valid;
  }

}
