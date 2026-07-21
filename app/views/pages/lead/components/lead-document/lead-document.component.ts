import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LeadDocumentUpdateDto} from "../../dto/lead-document-update-dto";
import {LeadAddEditService} from "../../services/lead-add-edit.service";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FileUploadError} from "../../../../../shared/dto/file-upload-error";
import {AlertService} from "../../../../../core/service/common/alert.service";
import {FileValidator} from "../../../../../shared/validators/file.validator";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {Constants} from "../../../../../core/setting/constants";
import {AppUtils} from "../../../../../shared/app.utils";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-lead-document',
  templateUrl: './lead-document.component.html',
  styleUrls: ['./lead-document.component.scss']
})
export class LeadDocumentComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;

  supportingDocs = [];
  result: Subject<any>;
  componentForm: FormGroup;
  formErrors: any;

  fileToUpload: File = null;
  selectedFile: any = {};
  uploadRequestData;

  fileUploadError: FileUploadError = new FileUploadError();
  showSpinner = false;

  leadDocumentUpdateDTO: LeadDocumentUpdateDto = new LeadDocumentUpdateDto({});
  action: Subject<any> = new Subject<any>();

  onLoadSelectedSupportingDocListChangeSub: Subscription = new Subscription();
  onFormValueChangeSub: Subscription = new Subscription();


  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(
    private leadAddEditService: LeadAddEditService,
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private cacheService: CacheService,
    private applicationService: ApplicationService,
  ) {
  }

  ngOnInit() {

    this.formErrors = {
      leadID: {},
      supportingDocID: {},
      docStorageDTO: {},
      remark: {},
    };


    this.supportingDocs = this.cacheService.getData(Constants.masterDataKey.CAS_SUPPORTING_DOCs);
    this.result = new BehaviorSubject(this.supportingDocs);

    this.componentForm = this.createLeadDocumentForm();
    this.onFormValueChangeSub.unsubscribe();

    this.onLoadSelectedSupportingDocListChangeSub = this.componentForm.controls.supportingDocID.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value))
      });

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

  }

  ngOnDestroy(): void {
    this.onLoadSelectedSupportingDocListChangeSub.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  createLeadDocumentForm() {
    return this.formBuilder.group({
      leadID: this.content.dto.leadID,
      supportingDocID: [this.leadDocumentUpdateDTO.supportingDocID, [Validators.required]],
      docStorageDTO: [this.leadDocumentUpdateDTO.docStorageDTO],
      remark: [this.leadDocumentUpdateDTO.remark, [Validators.required]],
    })
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.supportingDocs.filter((item: any) => item.documentName.toLowerCase().includes(filterValue));
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  // onAddLeadDoc(){
  //   if(this.componentForm.valid) {
  //     let data = Object.assign({},
  //       this.leadDocumentUpdateDTO,
  //       this.componentForm.getRawValue());
  //     this.action.next(data);
  //
  //   }
  // }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  isValidUpload() {
    return this.fileToUpload != null && !this.fileUploadError.hasError;
  }

  addLeadDocument() {

    let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, this.componentForm.value.supportingDocID);
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);

    if (this.componentForm.valid && this.isValidUpload()) {
      let data = Object.assign({},
        this.leadDocumentUpdateDTO,
        this.componentForm.getRawValue(),
        {supportingDocDTO: doc},
        {status: 'ACT'});
      this.action.next(data);
    }

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.warning);
    } else {
      this.showSpinner = true;

      let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, this.componentForm.value.supportingDocID)


      let rowData = Object.assign({},
        this.componentForm.getRawValue(),
        {supportingDocID: doc.supportingDocID},
        {
          status: 'ACT',
          uploadedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          uploadedDivCode: this.applicationService.getLoggedInUserDivCode()
        });

      rowData.docStorageDTO = {
        fileName: this.fileToUpload.name,
      };

      let formData: any = new FormData();
      formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
      formData.append("uploadRequestData", JSON.stringify(rowData));

      this.leadAddEditService.saveUploadedSupportDocuments(formData);
      this.mdbModalRef.hide();
    }
  }

}
