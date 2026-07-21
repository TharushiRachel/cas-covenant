import {Component, OnDestroy, OnInit} from '@angular/core';
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CribDetailsSaveDTO} from "../../dto/CribDetailsSaveDTO";
import {AppUtils} from "../../app.utils";
import {Subject, Subscription} from "rxjs";
import {Constants} from "../../../core/setting/constants";

@Component({
  selector: 'app-show-crib-details',
  templateUrl: './show-crib-details.component.html',
  styleUrls: ['./show-crib-details.component.scss']
})
export class ShowCribDetailsComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  customerDetails: any;
  htmlContent: any;
  formErrors: any = {};
  facilityPaper: any = {};
  isEditEnabled: boolean;
  status = Constants.statusConst;
  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  cribReportSaveDto: CribDetailsSaveDTO;
  actionClickSave: Subject<CribDetailsSaveDTO> = new Subject<CribDetailsSaveDTO>();

  onFormValueChangeSub: Subscription = new Subscription();

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate() + 1,
  };

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };

  optionsCribStatusSelect = [
    {value: this.cribStatusConst.NOT_ENTERED, label: this.cribStatus.NOT_ENTERED},
    {value: this.cribStatusConst.PENDING, label: this.cribStatus.PENDING},
    {value: this.cribStatusConst.NO_IRREGULAR_ADVANCES, label: this.cribStatus.NO_IRREGULAR_ADVANCES},
    {value: this.cribStatusConst.REPORTED_AS_IRREGULAR, label: this.cribStatus.REPORTED_AS_IRREGULAR},
  ];

  constructor(
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {

    if (!this.cribReportSaveDto) {
      this.cribReportSaveDto = new CribDetailsSaveDTO({});
    }

    this.formErrors = {
      cribStatus: [''],
      cribDateStr: ['']
    };

    this.componentForm = this.createCribSaveForm();

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
  }

  createCribSaveForm() {
    return this.formBuilder.group({
      cribStatus: [this.cribReportSaveDto.cribStatus, [Validators.required]],
      cribDateStr: [this.cribReportSaveDto.cribDateStr, [Validators.required]],
      remark: [this.cribReportSaveDto.remark]
    })
  }

  onSave() {
    this.cribReportSaveDto = Object.assign(this.cribReportSaveDto, {...this.getFormValue()});
    this.cribReportSaveDto.reportContent = this.htmlContent;
    this.actionClickSave.next(this.cribReportSaveDto);
    this.mdbModalRef.hide();
  }

  getFormValue() {
    return this.componentForm.getRawValue();
  }

}
