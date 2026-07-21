import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FileUploadError} from "../../../../../../../../shared/dto/file-upload-error";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import * as _ from "lodash";
import {FileValidator} from "../../../../../../../../shared/validators/file.validator";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-apf-add-edit-documents',
  templateUrl: './apf-add-edit-documents.component.html',
  styleUrls: ['./apf-add-edit-documents.component.scss']
})
export class ApfAddEditDocumentsComponent implements OnInit, OnDestroy {

  onApplicationFormChangeSub = new Subscription();
  componentForm: FormGroup;
  formErrors: any = {};
  documentDTO: any = {};
  applicationForm: any = {};
  fileToUpload: File = null;
  fileUploadError: FileUploadError = new FileUploadError();
  selectedFile: any = {};
  uploadRequestData;
  isDisabled: boolean = false;
  supportingDocs: any = [];
  result: Subject<any>;
  content: any;

  constructor(private formBuilder: FormBuilder,
              private cacheService: CacheService,
              public  mdbModalRef: MDBModalRef,
              private applicationService: ApplicationService,
              private alertService: AlertService,
              private applicationFormCreateService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
    if (!_.isEmpty(this.content.documentDTO)) {
      this.documentDTO = this.content.documentDTO
    }

    this.supportingDocs = this.cacheService.getData(Constants.masterDataKey.CAS_SUPPORTING_DOCs);
    this.result = new BehaviorSubject(this.supportingDocs);
    this.formErrors = {
      supportingDocID: {},
      remark: {}
    };

    this.onApplicationFormChangeSub = this.applicationFormCreateService.onApplicationFormChange.subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.applicationForm = res;
      }
    });

    this.componentForm = this.createForm();
  }

  createForm() {
    let supportingDocName: any = {};
    if (this.documentDTO.supportingDocID) {
      supportingDocName = AppUtils.getSupportingDocFromDocumentID(this.supportingDocs, this.documentDTO.supportingDocID);
    }

    return this.formBuilder.group({
      afDocumentID: [this.documentDTO.afDocumentID],
      applicationFormID: [this.documentDTO.applicationFormID],
      supportingDocID: [supportingDocName.documentName ? supportingDocName.documentName : this.documentDTO.supportingDocID, [Validators.required]],
      docStorageDTO: [this.documentDTO.docStorageDTO],
      remark: [this.documentDTO.remark, [Validators.required]],
    })
  }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  uploadDocument() {

    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.warning);
    } else {
      let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, this.componentForm.value.supportingDocID);
      let rowData = Object.assign({},
        this.componentForm.getRawValue(),
        {
          supportingDocID: doc.supportingDocID,
          status: 'ACT',
          applicationFormID: this.applicationForm.applicationFormID,
          uploadedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          uploadedDivCode: this.applicationService.getLoggedInUserDivCode()
        });

      rowData.docStorageDTO = {
        fileName: this.fileToUpload.name,
      };

      let formData: any = new FormData();
      formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
      formData.append("uploadRequestData", JSON.stringify(rowData));

      this.applicationFormCreateService.uploadApplicationFormDocument(formData);
      this.mdbModalRef.hide();
    }
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

}
