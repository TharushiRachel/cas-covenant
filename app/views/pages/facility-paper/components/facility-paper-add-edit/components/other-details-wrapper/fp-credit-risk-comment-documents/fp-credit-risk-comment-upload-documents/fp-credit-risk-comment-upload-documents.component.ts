import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as _ from 'lodash';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { AppUtils } from 'src/app/shared/app.utils';
import { FileUploadError } from 'src/app/shared/dto/file-upload-error';
import { FileValidator } from 'src/app/shared/validators/file.validator';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-fp-credit-risk-comment-upload-documents',
  templateUrl: './fp-credit-risk-comment-upload-documents.component.html',
  styleUrls: ['./fp-credit-risk-comment-upload-documents.component.scss']
})
export class FpCreditRiskCommentUploadDocumentsComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any = {};

  fileToUpload: File = null;
  fileUploadError: FileUploadError = new FileUploadError();

  globalSupportingDocs: any = [];
  result: Subject<any>;

  onCreditRiskDocChange: Subscription = new Subscription();
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
    
    // this.globalSupportingDocs = _.sortBy(this.cacheService.getData(Constants.masterDataKey.CAS_GLOBAL_SUPPORTING_DOCs).filter((item)=>item.categoryID == Constants.documentTypes.CREDIT_RISK_DOCUMENT), ['documentName']);

    this.result = new BehaviorSubject(this.globalSupportingDocs);
    this.formErrors = {
      supportingDocID: [''],
      remark: ['']
    };

    this.componentForm = this.formBuilder.group({
      supportingDocID: ['', Validators.required],
      remark: ['']
    });
    this.onCreditRiskDocChange = this.componentForm.controls.supportingDocID.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value))
      });


  }

  ngOnDestroy(): void {
    this.onCreditRiskDocChange.unsubscribe();
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.globalSupportingDocs.filter((item: any) => item.documentName.toLowerCase().includes(filterValue));
  }

  isValidUpload() {
    return this.fileToUpload != null && !this.fileUploadError.hasError;
  }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  addRiskDocument() {
    let doc = AppUtils.getGlobalSupportingDocFromDocumentName(this.globalSupportingDocs, this.componentForm.value.supportingDocID);

    let data = Object.assign({},
    );

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.error);
    } else {
      let rowData = Object.assign({},
        this.componentForm.getRawValue(),
        {fpCreditRiskDocumentID: null},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {supportingDocID: doc.supportingDocID},
        {isLocked : Constants.yesNoConst.N},
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

      this.facilityPaperAddEditService.uploadFPCreditRiskDocument(formData);
      this.mdbModalRef.hide();
    }
  }

}
