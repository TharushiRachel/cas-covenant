import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { FileUploadError } from 'src/app/shared/dto/file-upload-error';
import { FacilityPaperAddEditComponent } from '../../components/facility-paper-add-edit/facility-paper-add-edit.component';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { Constants } from 'src/app/core/setting/constants';
import { FileValidator } from 'src/app/shared/validators/file.validator';
import { AppUtils } from 'src/app/shared/app.utils';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import * as _ from 'lodash';
import { FacilityPaperAddEditService } from '../../services/facility-paper-add-edit.service';
import { Router } from "@angular/router";
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-bcc-document-upload',
  templateUrl: './bcc-document-upload.component.html',
  styleUrls: ['./bcc-document-upload.component.scss']
})
export class BccDocumentUploadComponent implements OnInit, OnDestroy {

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

  formName: string = "";
  facilityPaper: any = {};

  modalRef: MDBModalRef;

  //@Input("facilityPaper") facilityPaper: any = {};

  onDocumentUploadChangeSub: Subscription = new Subscription();

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private formBuilder: FormBuilder,
    public mdbModalRef: MDBModalRef,
    //private cacheService: CacheService,
    private alertService: AlertService,
    //private router: Router,
    private mdbModalService: MDBModalService,
  ) {
  }

  ngOnInit() {
    this.componentForm = this.formBuilder.group({
      remark: ['']
    });

    //console.log("facilityPaper-upload", this.content.facilityPaper);
  }

  ngOnDestroy(): void {
    this.onSupportingDocChange.unsubscribe();
    this.onDocumentUploadChangeSub.unsubscribe();
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

    if (!this.fileUploadError.hasError && this.fileToUpload && this.fileToUpload.type != "application/pdf") {
      this.fileUploadError.errorMessage = `please upload a valid pdf`;
      this.fileUploadError.hasError = true;
    }

    //console.log("this.fileUploadError", this.fileUploadError);
    //console.log("this.fileToUpload.type", this.fileToUpload.type);
  }

  getFileType(formName : string){
    if(formName == "Meeting Minute"){
      return "M"
    }else if(formName =="Cover Page"){
      return "C"
    }else{
      return "O"
    }

  }

  getIsEmail(formName : string){
    if(formName == "Meeting Minute"){
      return "Y"
    }else if(formName =="Cover Page"){
      return "Y"
    }else{
      return "N"
    }

  }

  uploadBccDocument(formName) {
    //console.log("this.content.facilityPaper", this.content.facilityPaper);
    //let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, formName);
    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.error);
    } else {
      let rowData = Object.assign({},
        this.componentForm.getRawValue(),
        { facilityPaperID: this.content.facilityPaper.facilityPaperID },
        {
          status: 'PENDING',
          uploadedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          documentName: this.getDocName(formName, this.fileToUpload.name),
          fpBccID: (this.content.facilityPaper.fpBccList.length > 0) ? this.content.facilityPaper.fpBccList[0].fpBccId : 0,
          isSendEmail: this.getIsEmail(formName),
          fileSize: this.fileToUpload.size,
          fileType : this.getFileType(formName)
        });

      rowData.docStorageDTO = {
        fileName: this.fileToUpload.name,
      };

      //console.log("this.fileToUpload", this.fileToUpload);
      //console.log("formName", formName);
      //console.log("content", this.content);
      //console.log("doc", doc);
      //console.log("rowData", rowData);

      if (this.content.facilityPaper.fpBccList.length > 0) {
        const coverPgNMeetingMinDocExists = this.documentExists(this.content.facilityPaper.fpBccList[0].fpBccDocumentList, formName);

        if (coverPgNMeetingMinDocExists) {
          this.checkCoverPgNMeetingMinDocs(this.content.facilityPaper.fpBccList[0], rowData)
        } else {
          let formData = new FormData();
          formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
          formData.append("uploadRequestData", JSON.stringify(rowData));

          this.facilityPaperAddEditService.uploadFpBccDocument(formData);
        }

      }
      else {
        let formData = new FormData();
        formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
        formData.append("uploadRequestData", JSON.stringify(rowData));

        this.facilityPaperAddEditService.uploadFpBccDocument(formData);
      }
      this.mdbModalRef.hide();
    }
  }

  //check if a document with the given name exists
  documentExists(documentList: any[], documentName: string): boolean {
    return documentList.find(document => document.documentName === documentName && document.status ===
      "Active") !== undefined;
  }

  checkCoverPgNMeetingMinDocs(item, rowData) {
    if (!_.isEmpty(item)) {
      //console.log("delete-item", item);
      let data = Object.assign({}, { fpOtherBankFacilityID: item.fpOtherBankFacilityID },
        { facilityPaperID: item.facilityPaperID }, //
        { fpBccDocumentID: item.supportingDocID },
        { status: 'INA' });

      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-30-p modal-margin-center ',
        containerClass: '',
        animated: true,
        data: {
          heading: "Confirm re-upload document",
          message: "Do you wish to proceed with the re-upload?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          let formData = new FormData();
          formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
          formData.append("uploadRequestData", JSON.stringify(rowData));

          this.facilityPaperAddEditService.uploadFpBccDocument(formData);
        }
      });
    }
  }

  getDocName(formName, docName): string {
    let displayDocName: string
    if (formName === "Other") {
      const fileNameWithoutExtension = docName.substring(0, docName.lastIndexOf("."));
      displayDocName = `Other - ${fileNameWithoutExtension}`
    } else {
      displayDocName = formName
    }

    return displayDocName;
  }


}
