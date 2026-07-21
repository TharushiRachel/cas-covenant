import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { AppUtils } from "src/app/shared/app.utils";
import { FileUploadError } from "src/app/shared/dto/file-upload-error";
import { FileValidator } from "src/app/shared/validators/file.validator";
import { LeadDocumentUpdateDto } from "../../../dto/lead-document-update-dto";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { LeadComprehensiveService } from "../../../services/lead-comprehensive.service";
import { Constants } from "src/app/core/setting/constants";
import { LeadAddEditService } from "../../../services/lead-add-edit.service";

@Component({
  selector: "app-lead-comprehensive-document",
  templateUrl: "./lead-comprehensive-document.component.html",
  styleUrls: ["./lead-comprehensive-document.component.scss"],
})
export class LeadComprehensiveDocumentComponent implements OnInit {
  heading: string;
  message: string;
  actionName: string;
  action: Subject<any> = new Subject<any>();
  supportingDocs: any[] = [];
  documentForm: FormGroup;
  fileToUpload: File = null;
  leadDocumentUpdateDTO: LeadDocumentUpdateDto = new LeadDocumentUpdateDto({});
  showSpinner = false;
  formErrors: any;
  fileUploadError: FileUploadError = new FileUploadError();
  content: any;
  leadId: number = 0;
  result: Subject<any>;
  isFormLoaded: boolean = false;
  supportingDocDropdown: any[] = [];
  isEdit: boolean = false;
  editObj: any;
  existingFileName: string;
  isSaveLoading: boolean = false;
  isFileUploaded: boolean = false;
  constructor(
    public readonly mdbModalRef: MDBModalRef,
    private readonly formBuilder: FormBuilder,
    private readonly alertService: AlertService,
    private readonly applicationService: ApplicationService,
    private readonly leadComprehensiveService: LeadComprehensiveService,
    private readonly leadAddEditService: LeadAddEditService,
  ) { }

  ngOnInit() {
    this.documentForm = this.loadDocumentForm();
    this.leadId = this.content.leadId ? this.content.leadId : 0;
    this.supportingDocDropdown = this.content.supportingDocDropdown
      ? this.content.supportingDocDropdown
      : [];
    console.log("this.content.editObj------->", this.content.editObj)
    this.isEdit = this.content.isEdit ? this.content.isEdit : false;
    this.editObj = this.content.editObj ? this.content.editObj : {};


    this.formErrors = {
      leadID: {},
      supportingDocID: {},
      docStorageDTO: {},
      remark: {},
    };

    if (this.editObj && this.isEdit) {
      this.editDocumentForm();
    }
  }

  loadDocumentForm() {
    return this.formBuilder.group({
      leadID: this.leadId,
      supportingDocID: ["", [Validators.required]],
      remark: ["", [Validators.required]],
    });
  }

  editDocumentForm() {
    setTimeout(() => {
      this.documentForm.patchValue({
        leadID: this.leadId,
        supportingDocID:
          this.getSupportingDocumentName(this.editObj.supportingDocId) || "",
        remark: this.editObj.remark || "",
      });

      this.existingFileName = this.editObj.docStorageDTO.fileName || null;
    }, 100);
  }

  getSupportingDocumentName(supportingDocID: number) {
    let data = this.supportingDocDropdown.find(
      (rec) => rec.supportingDocID === supportingDocID,
    );
    return data ? data.documentName : null;
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  handleSave() {
    this.action.next();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  selectFile(event: any) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
    this.isFileUploaded = true;
  }

  isValid() {
    return this.documentForm.valid;
  }

  isValidUpload() {
    if (!this.isEdit) {
      return this.fileToUpload != null && !this.fileUploadError.hasError;
    }

    if (this.fileToUpload) {
      return !this.fileUploadError.hasError;
    }
    return true;
  }

  addEditLeadDocument() {
    let doc = AppUtils.getSupportingDocFromDocumentName(
      this.supportingDocDropdown,
      this.documentForm.value.supportingDocID,
    );

    if (this.isFileUploaded) {
      this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
    }

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(
        this.fileUploadError.errorMessage,
        SETTINGS.TOASTER_MESSAGES.warning,
      );
    } else {
      this.showSpinner = true;

      let doc = AppUtils.getSupportingDocFromDocumentName(
        this.supportingDocDropdown,
        this.documentForm.value.supportingDocID,
      );

      let rowData = Object.assign(
        {},
        this.documentForm.getRawValue(),
        {
          leadDocumentID: this.editObj.leadDocumentId
            ? this.editObj.leadDocumentId
            : null,
        },
        { leadID: this.leadId },
        { supportingDocID: doc.supportingDocID },
        {
          status: Constants.statusConst.ACT,
          uploadedUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          uploadedDivCode: this.applicationService.getLoggedInUserDivCode(),
        },
      );

      if (this.isFileUploaded) {
        rowData.docStorageDTO = {
          fileName: this.fileToUpload.name,
        };
      } else {
        rowData.docStorageDTO = {
          fileName: this.editObj.docStorageDTO.fileName,
          docStorageID: this.editObj.docStorageDTO.docStorageID,
        };
      }
    
      let formData: any = new FormData();
      if (this.isFileUploaded) {
        formData.append(
          "uploadingFile",
          this.fileToUpload,
          this.fileToUpload["Name"],
        );
      }
      formData.append("uploadRequestData", JSON.stringify(rowData));
      this.isSaveLoading = true;
      this.leadAddEditService.saveUploadedSupportDocuments(formData).then(
        (res: any) => {
          this.isSaveLoading = false;
          this.isFileUploaded = false;
          if (res !== null && res.leadDocumentDTOList !== null) {
            this.action.next(res.leadDocumentDTOList);
            this.mdbModalRef.hide();
          }
        },
        (err: any) => {
          this.isSaveLoading = false;
          this.isFileUploaded = false;
        },
      );
    }
  }
}
