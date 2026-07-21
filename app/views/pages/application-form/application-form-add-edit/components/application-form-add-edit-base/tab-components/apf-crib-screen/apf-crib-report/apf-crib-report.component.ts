import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IMyOptions} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {FileValidator} from "../../../../../../../../../shared/validators/file.validator";
import {FileUploadError} from "../../../../../../../../../shared/dto/file-upload-error";
import * as _ from "lodash";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {Subscription} from "rxjs";
import {SETTINGS} from "../../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../../core/service/common/alert.service";

@Component({
  selector: 'app-apf-crib-report',
  templateUrl: './apf-crib-report.component.html',
  styleUrls: ['./apf-crib-report.component.scss']
})
export class ApfCribReportComponent implements OnInit, OnDestroy {

  cribForm: FormGroup;
  formErrors: any = {};
  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  fileToUpload: File = null;
  isDisabled: boolean = false;
  fileUploadError: FileUploadError = new FileUploadError();
  onApplicationFormCribChange = new Subscription();
  onDownLoadLinkChangeSub = new Subscription();
  applicationForm: any = {};
  basicInformationDTOList: any = [];
  selectedTabIndex: any = 0;
  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  optionsCribStatusSelect = [
    {value: this.cribStatusConst.NOT_ENTERED, label: this.cribStatus.NOT_ENTERED},
    {value: this.cribStatusConst.PENDING, label: this.cribStatus.PENDING},
    {value: this.cribStatusConst.NO_IRREGULAR_ADVANCES, label: this.cribStatus.NO_IRREGULAR_ADVANCES},
    {value: this.cribStatusConst.REPORTED_AS_IRREGULAR, label: this.cribStatus.REPORTED_AS_IRREGULAR},
  ];

  constructor(private formBuilder: FormBuilder,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private alertService: AlertService,) {
  }

  ngOnInit() {

    this.formErrors = {
      nic: {},
      CRIBDate: {},
      comment: {},
      cribStatus: {}
    };

    this.onApplicationFormCribChange = this.applicationFormAddEditService.onApplicationFormCribChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
        this.basicInformationDTOList = data.basicInformationDTOList;
      }
    });

    this.cribForm = this.createFrom();

    this.onDownLoadLinkChangeSub = this.applicationFormAddEditService.onDownloadLinkChangeAFCribAttachments
      .subscribe(data => {
        let downloadLink = this.downloadLink.nativeElement;
        downloadLink.href = window.URL.createObjectURL(data.data);
        downloadLink.download = data.fileName;
        downloadLink.click();
        this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      });
  }

  ngOnDestroy(): void {
    this.onApplicationFormCribChange.unsubscribe();
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  createFrom() {
    this.cribForm = this.formBuilder.group({
      cribStatus: [{value: '', disabled: this.isDisabled}, [Validators.required]],
      nic: [{value: '', disabled: this.isDisabled}, [Validators.required]],
      CRIBDate: [{value: '', disabled: this.isDisabled}, [Validators.required]],
      comment: [{value: '', disabled: this.isDisabled}, [Validators.required]],
    });
    return this.cribForm;
  }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

}
