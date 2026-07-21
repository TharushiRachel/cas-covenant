import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { EsgService } from 'src/app/core/service/common/esg.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { FileUploadError } from 'src/app/shared/dto/file-upload-error';
import { FileValidator } from 'src/app/shared/validators/file.validator';

@Component({
  selector: 'app-esg-annexure-attachment',
  templateUrl: './esg-annexure-attachment.component.html',
  styleUrls: ['./esg-annexure-attachment.component.scss']
})
export class EsgAnnexureAttachmentComponent implements OnInit {

  @Output() action = new EventEmitter<number>();
  @Output() uploaded = new EventEmitter<void>();
  mode: 'add' | 'edit';
  attachment: any;
  applicationFormID: number | null;
  facilityPaperID: number | null;

  attachmentForm: FormGroup;
  form: FormGroup;
  fileToUpload: File | null = null;
  base64Document: string = '';
  fileUploadError: FileUploadError = new FileUploadError();

  constructor(public mdbModalRef: MDBModalRef,
    private readonly esgService: EsgService,
    private readonly alertService: AlertService,
    private readonly fb: FormBuilder,
  ) {
  }

  ngOnInit(): void {
    this.mode = this.mode || 'add';
    this.attachment = this.attachment || null;

    this.attachmentForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      file: [''] // only required for add
    });

    if (this.mode === 'edit' && this.attachment) {
      this.attachmentForm.patchValue({
        name: this.attachment.name,
        description: this.attachment.description,
        file: this.attachment.fileName
      });
    }
  }

  onFileSelected(event: any): void {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(
        this.fileUploadError.errorMessage,
        SETTINGS.TOASTER_MESSAGES.warning
      );
      this.attachmentForm.patchValue({ file: '' });
      this.base64Document = '';
      return;
    }

    if (this.fileToUpload) {
      this.attachmentForm.patchValue({ file: this.fileToUpload.name }); // mark file control as filled
      this.attachmentForm.get('file').updateValueAndValidity();

      const reader = new FileReader();
      reader.onload = () => {
        const result = reader.result as string;
        this.base64Document = result.split(',')[1]; //  strip prefix
      };
      reader.readAsDataURL(this.fileToUpload);
    }
  }

  canUpload(): boolean {
    if (this.attachmentForm.invalid) return false;
    if (this.mode === 'add') {
      return this.fileToUpload != null && !this.fileUploadError.hasError;
    }
    // in edit mode allow saving with or without file
    return true;
  }

  isValidUpload() {
    return this.fileToUpload != null && !this.fileUploadError.hasError;
  }

  saveAttachment(): void {
    const isEdit = this.mode === 'edit';
    const hasNewFile = !!this.fileToUpload;

    if (this.attachmentForm.invalid || (!isEdit && !hasNewFile)) {
      this.alertService.showToaster(
        'Please fill all the required fields',
        SETTINGS.TOASTER_MESSAGES.warning
      );
      return;
    }

    if (hasNewFile) {
      this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
      if (this.fileUploadError.hasError) {
        this.alertService.showToaster(
          this.fileUploadError.errorMessage,
          SETTINGS.TOASTER_MESSAGES.warning
        );
        return;
      }
    }

    const payload: any = {
      facilityPaperID: this.facilityPaperID || null,
      applicationFormID: this.applicationFormID || null,
      name: this.attachmentForm.value.name,
      description: this.attachmentForm.value.description,
      fileName: hasNewFile
        ? this.fileToUpload.name
        : (this.attachment.fileName || null)
    };

    // If no new file, preserve the old base64 document
    if (hasNewFile) {
      payload.document = this.base64Document;
    } 

    const serviceCall = isEdit
      ? this.esgService.updateAttachment(this.attachment.esgStorageID, payload)
      : this.esgService.addEsgAtttachment(payload);

    serviceCall
      .then(() => {
        this.alertService.showToaster(
          isEdit
            ? 'Attachment updated successfully'
            : 'Attachment added successfully',
          SETTINGS.TOASTER_MESSAGES.success
        );
        this.action.emit(); // triggers refresh in parent
      })
      .catch(err => {
        this.alertService.showToaster(
          'Failed to save attachment',
          SETTINGS.TOASTER_MESSAGES.error
        );
      })
      .finally(() => this.mdbModalRef.hide());
  }

}
