import {Component, OnDestroy, OnInit} from '@angular/core';
import {FileUploadError} from "../../../../../shared/dto/file-upload-error";
import {FileValidator} from "../../../../../shared/validators/file.validator";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../core/service/common/alert.service";
import {ApplicationTopicConfigAddEditService} from "../../services/application-topic-config-add-edit.service";
import {FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";
import {ConfirmationDialogComponent} from "../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-application-topic-config-add-edit',
  templateUrl: './application-topic-config-add-edit.component.html',
  styleUrls: ['./application-topic-config-add-edit.component.scss']
})
export class ApplicationTopicConfigAddEditComponent implements OnInit, OnDestroy {

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  modalRef: MDBModalRef;
  fileToUpload: File = null;
  componentForm: FormGroup;
  fileUploadError: FileUploadError = new FileUploadError();
  uploadedApplicationFormConfigTopicList: any = [];
  uploadedApplicationFormConfigTopicChangeSub = new Subscription();

  constructor(
    private alertService: AlertService,
    private applicationTopicConfigAddEditService: ApplicationTopicConfigAddEditService,
    private mdbModalService: MDBModalService,) {
  }

  ngOnInit() {
    this.uploadedApplicationFormConfigTopicChangeSub = this.applicationTopicConfigAddEditService.uploadedApplicationFormConfigTopicListChange.subscribe((res: any) => {
      if (res) {
        this.uploadedApplicationFormConfigTopicList = res;
      }
    })
  }

  ngOnDestroy(): void {
    this.uploadedApplicationFormConfigTopicChangeSub.unsubscribe();
  }

  uploadFile() {

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.warning);
    } else {

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
          heading: "Confirm Configurations",
          message: `Do you want to update the topic configurations ?`,
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          let rowData = Object.assign({}, {fileName: this.fileToUpload.name});
          let formData: any = new FormData();
          formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
          formData.append("uploadRequestData", JSON.stringify(rowData));
          this.applicationTopicConfigAddEditService.uploadApplicationTopicConfigFile(formData);
        }
      });

    }
  }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  isValid() {
    return this.fileToUpload ? this.fileToUpload.name : false;
  }


}
