import {Component, OnDestroy, OnInit} from '@angular/core';
import {FileUploadError} from "../../../../../../../../shared/dto/file-upload-error";
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {FileValidator} from "../../../../../../../../shared/validators/file.validator";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-apf-add-edit-crib-attachment',
  templateUrl: './apf-add-edit-crib-attachment.component.html',
  styleUrls: ['./apf-add-edit-crib-attachment.component.scss']
})
export class ApfAddEditCribAttachmentComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  basicInformation: any = {};
  applicationForm: any = {};
  fileToUpload: File = null;
  fileUploadError: FileUploadError = new FileUploadError();
  action: Subject<any> = new Subject<any>();
  cribStatus = Constants.cribStatus;
  cribStatusConst = Constants.cribStatusConst;

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

  componentForm: FormGroup;
  formErrors: any = {};
  supportingDocs = [];

  result: Subject<any>;
  onDocumentNameChangeSub: Subscription = new Subscription();
  onFormValueChangeSub: Subscription = new Subscription();


  constructor(
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private cacheService: CacheService,
    private alertService: AlertService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {
    this.formErrors = {
      cribStatus: {},
      cribDateStr: {},
      remark: {}
    };
    this.supportingDocs = this.cacheService.getData(Constants.masterDataKey.CAS_SUPPORTING_DOCs);

    this.componentForm = this.createCribForm();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

  }

  ngOnDestroy(): void {
    this.onDocumentNameChangeSub.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  createCribForm() {
    let cribAttachment = this.content.cribAttachment ? this.content.cribAttachment : {};
    return this.formBuilder.group({
      cribAttachmentID: [cribAttachment.cribAttachmentID,],
      cribStatus: [cribAttachment.cribStatus, [Validators.required]],
      cribDateStr: [cribAttachment.cribDateStr, [Validators.required]],
      remark: [cribAttachment.remark]
    })
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.supportingDocs.filter((item: any) => item.documentName.toLowerCase().includes(filterValue));
  }

  isValidUpload() {
    return this.fileToUpload != null && !this.fileUploadError.hasError;
  }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  addCribDocument() {
    let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, "CRIB");
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.error);
    } else {
      let rowData = Object.assign({},
        this.componentForm.getRawValue(),
        {
          applicationFormID: this.applicationForm.applicationFormID,
          basicInformationID: this.basicInformation.basicInformationID,
          identificationNo: this.basicInformation.identificationNo,
          identificationType: this.basicInformation.identificationType,
          supportingDocID: doc.supportingDocID,
          status: 'ACT',
          uploadedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          uploadedDivCode: this.applicationService.getLoggedInUserDivCode()
        });
      rowData.docStorageDTO = {
        fileName: this.fileToUpload.name,
      };

      let formData = new FormData();
      formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
      formData.append("uploadRequestData", JSON.stringify(rowData));

      this.action.next(AppUtils.trim(formData));
      this.mdbModalRef.hide();
    }
  }

}

