import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as _ from "lodash";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import {FileUploadError} from "../../../../../../../../../../shared/dto/file-upload-error";
import {FileValidator} from "../../../../../../../../../../shared/validators/file.validator";
import {AlertService} from "../../../../../../../../../../core/service/common/alert.service";
import {AppUtils} from "../../../../../../../../../../shared/app.utils";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";

@Component({
  selector: 'app-facility-data-doc-upload',
  templateUrl: './facility-data-doc-upload.component.html',
  styleUrls: ['./facility-data-doc-upload.component.scss']
})
export class FacilityDataDocUploadComponent implements OnInit, OnDestroy {

  fileToUpload: File = null;
  fileUploadError: FileUploadError = new FileUploadError();
  content: any = {};
  formErrors: any = {};
  componentForm: FormGroup;
  cftSupportingDocListForFacility = [];
  onSupportindDocNameChangeSub = new Subscription();
  result: Subject<any>;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    public  mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private alertService: AlertService
  ) {
  }

  ngOnInit() {
    this.formErrors = {
      documentName: [''],
    };

    if (!_.isEmpty(this.content.facilityData.creditFacilityTemplateDTO)) {

      for (var item of this.content.facilityData.creditFacilityTemplateDTO.cftSupportingDocDTOList) {
        this.cftSupportingDocListForFacility.push(item);
      }
    }

    this.result = new BehaviorSubject(this.cftSupportingDocListForFacility);

    this.componentForm = this.formBuilder.group({
      documentName: ['', Validators.required],
      remark: ['',]
    });

    this.onSupportindDocNameChangeSub = this.componentForm.controls.documentName.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value))
      })
  }

  ngOnDestroy(): void {
    this.onSupportindDocNameChangeSub.unsubscribe();
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.cftSupportingDocListForFacility.filter((item: any) => item.documentName.toLowerCase().includes(filterValue));
  }

  isValidUpload() {
    return this.fileToUpload != null && !this.fileUploadError.hasError;
  }

  selectFile(event) {
    if (event) {
      this.fileToUpload = event.target.files[0];
      this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
    }
  }

  addFacilityDocument() {
    let doc = AppUtils.getSupportingDocFromDocumentName(this.cftSupportingDocListForFacility, this.componentForm.value.documentName);
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.error);
    } else {
      let rowData = Object.assign({},
        {facilityDocumentID: null},
        {facilityPaperID: this.content.facilityPaperID},
        {facilityID: this.content.facilityData.facilityID},
        {supportingDocID: doc.supportingDocID},
        {cftSupportingDocID: doc.cftSupportingDocID},
        {mandatory: doc.mandatory == 'Y' ? 'Y' : 'N'},
        {docStorageDTO: {}},
        {remark: this.componentForm.controls.remark.value},
        {status: 'ACT'});

      rowData.docStorageDTO = {
        fileName: this.fileToUpload.name,
      };

      let formData = new FormData();
      formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
      formData.append("uploadRequestData", JSON.stringify(rowData));
      this.facilityPaperAddEditService.uploadFacilityDocument(formData);
      // this.alertService.showToaster("Facility document uploaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      this.mdbModalRef.hide();
    }
  }
}
