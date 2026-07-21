import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Constants} from "../../../../../../../../core/setting/constants";
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import * as moment from 'moment';

@Component({
  selector: 'app-apf-add-edit-crib-reports',
  templateUrl: './apf-add-edit-crib-reports.component.html',
  styleUrls: ['./apf-add-edit-crib-reports.component.scss']
})
export class ApfAddEditCribReportsComponent implements OnInit, OnDestroy {

  onFormValueChangeSub = new Subscription();
  onApplicationFormChangeSub = new Subscription();
  cribDate;
  action: Subject<any> = new Subject<any>();
  results: Subject<any>;

  cribReportForm: FormGroup;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormCurrentStatus = Constants.applicationFormCurrentStatus;
  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  basicInformationConst = Constants.basicInformationTypeConst;
  basicInformation = Constants.basicInformationType;
  titleConst = Constants.titleConst;
  identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  basicInformationType = Constants.basicInformationType;

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  formErrors: any = {};
  isDisabled: boolean = false;
  templateOption: any[] = [];
  allWorkFlowTemplates = [];
  allOptions = [];
  heading: string;
  message: string;
  htmlContent: any = {};
  applicationForm: any = {};
  identificationNumber: string;
  identificationType: string;

  optionsCribStatusSelect = [
    {value: this.cribStatusConst.NOT_ENTERED, label: this.cribStatus.NOT_ENTERED},
    {value: this.cribStatusConst.PENDING, label: this.cribStatus.PENDING},
    {value: this.cribStatusConst.NO_IRREGULAR_ADVANCES, label: this.cribStatus.NO_IRREGULAR_ADVANCES},
    {value: this.cribStatusConst.REPORTED_AS_IRREGULAR, label: this.cribStatus.REPORTED_AS_IRREGULAR},
    {value: this.cribStatusConst.SERVICE_NOT_AVAILABLE, label: this.cribStatus.SERVICE_NOT_AVAILABLE},
  ];

  constructor(private mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              public applicationService: ApplicationService,
              private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {

    this.formErrors = {
      comment: {},
      cribStatus: {},
      CRIBDate: {},
      identificationType: {},
      type: {},
    };

    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
      }
    });

    this.createForm();

    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.cribReportForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.cribReportForm, this.formErrors);
    });
  }


  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  saveCribReport(): void {

    let afCribReportUpdateRQ = {
      applicationFormID: this.applicationForm.applicationFormID,
      identificationNo: this.cribReportForm.getRawValue().identificationNumber,
      identificationType: this.cribReportForm.getRawValue().identificationType,
      cribDateStr: this.cribReportForm.getRawValue().CRIBDate,
      reportContent: this.htmlContent,
      cribStatus: this.cribReportForm.getRawValue().cribStatus,
      remark: this.cribReportForm.getRawValue().comment,
      status: Constants.statusConst.ACT
    };

    this.action.next(AppUtils.trim(afCribReportUpdateRQ));
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
    this.onApplicationFormChangeSub.unsubscribe();
  }

  isValid() {
    return this.cribReportForm && this.cribReportForm.valid;
  }

  isDirty() {
    return this.cribReportForm && this.cribReportForm.dirty;
  }

  createForm() {

    this.cribReportForm = this.formBuilder.group({
      identificationType: [{
        value: this.identificationType ? this.identificationType : 'NIC',
        disabled: true
      }, [Validators.required]],
      identificationNumber: [{value: this.identificationNumber, disabled: true}, [Validators.required]],
      comment: ['', [Validators.required]],
      cribStatus: ['', [Validators.required]],
      CRIBDate: [{
        value: this.cribDate ? this.cribDate : moment().format('DD/MM/YYYY'),
        disabled: true
      }, [Validators.required]],
    });
  }

  moveUp() {
    document.getElementById("form-div").scrollIntoView({
        behavior: 'smooth',
        block: 'start',
        inline: 'start'
      }
    );
  }
}
