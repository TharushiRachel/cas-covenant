import {Component, OnDestroy, OnInit} from '@angular/core';
import {FacilityPaperAddEditService} from "../../../../../../services/facility-paper-add-edit.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {CacheService} from "../../../../../../../../../core/service/data/cache.service";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {FileValidator} from "../../../../../../../../../shared/validators/file.validator";
import {FileUploadError} from "../../../../../../../../../shared/dto/file-upload-error";
import {AppUtils} from "../../../../../../../../../shared/app.utils";
import {SETTINGS} from "../../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../../core/service/common/alert.service";
import * as _ from 'lodash';
import {ApplicationService} from "../../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-fp-supporting-document-upload',
  templateUrl: './fp-supporting-document-upload.component.html',
  styleUrls: ['./fp-supporting-document-upload.component.scss']
})
export class FpSupportingDocumentUploadComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any = {};

  fileToUpload: File = null;
  fileUploadError: FileUploadError = new FileUploadError();

  supportingDocs: any = [];
  result: Subject<any>;

  onSupportingDocChange: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();


  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private cacheService: CacheService,
    private alertService: AlertService
  ) {
  }

  ngOnInit() {
    this.supportingDocs = _.sortBy(this.cacheService.getData(Constants.masterDataKey.CAS_SUPPORTING_DOCs), ['documentName']);
    this.result = new BehaviorSubject(this.supportingDocs);
    this.formErrors = {
      supportingDocID: [''],
      remark: ['']
    };

    this.componentForm = this.formBuilder.group({
      supportingDocID: ['', Validators.required],
      remark: ['']
    });
    this.onSupportingDocChange = this.componentForm.controls.supportingDocID.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value))
      });


  }

  ngOnDestroy(): void {
    this.onSupportingDocChange.unsubscribe();
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

  addFacilityDocument() {
    let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, this.componentForm.value.supportingDocID);

    let data = Object.assign({},
    );

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.error);
    } else {
      let rowData = Object.assign({},
        this.componentForm.getRawValue(),
        {fpDocumentID: null},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {supportingDocID: doc.supportingDocID},
        {
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

      this.facilityPaperAddEditService.uploadFpSupportDocument(formData);
      this.mdbModalRef.hide();
    }
  }
}
