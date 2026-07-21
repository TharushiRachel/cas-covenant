import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../../../../core/setting/constants";
import * as _ from "lodash";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {ApfAddEditFacilityDocumentComponent} from "../apf-add-edit-facility-document/apf-add-edit-facility-document.component";
import {Subscription} from "rxjs";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-apf-facility-document',
  templateUrl: './apf-facility-document.component.html',
  styleUrls: ['./apf-facility-document.component.scss']
})
export class ApfFacilityDocumentComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  heading: string;
  content: any = {};
  facilityData: any = {};
  applicationForm: any = {};
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;
  tableColumnsForFacilityDocument = [];
  documentList = [];
  yesNo = Constants.yesNo;
  onDownLoadLinkChangeSub = new Subscription();
  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

  constructor(public  mdbModalRef: MDBModalRef,
              private mdbModalService: MDBModalService,
              private applicationService: ApplicationService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private alertService: AlertService) {
  }

  ngOnInit() {

    this.facilityData = this.content.facilityData;
    this.applicationForm = this.content.applicationForm;
    this.documentList = this.content.facilityData.afFacilityDocumentDTOList;
    this.onDownLoadLinkChangeSub = this.applicationFormAddEditService.onDownloadLinkChangeAPSupportDoc
      .subscribe(data => {
        let downloadLink = this.downloadLink.nativeElement;
        downloadLink.href = window.URL.createObjectURL(data.data);
        downloadLink.download = data.fileName;
        downloadLink.click();
        this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      });
  }

  ngOnDestroy(): void {
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  onDownLoadDoc(item) {
    if (item.docStorageDTO.docStorageID != null) {
      this.applicationFormAddEditService.downloadAFSupportDocument(item.docStorageDTO).then((data: any) =>{
        let downloadLink = this.downloadLink.nativeElement;
         downloadLink.href = window.URL.createObjectURL(data);
         downloadLink.download = item.docStorageDTO.fileName;
         downloadLink.click();
         this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
     });
     
    }
  }

  openModalFacilityDocumentUpload(facilityData, documentItem?) {
    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityData}
      ]
    };

    this.modalRef = this.mdbModalService.show(ApfAddEditFacilityDocumentComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: 'right',
      animated: true,
      data: {
        heading: "Add/ Edit Facility",
        content: {
          facilityData: facilityData,
          applicationForm: this.applicationForm,
          documentItem: documentItem
        },
      }
    });
    this.mdbModalRef.hide();
  }

  onRemoveFacilityDocument(documentItem) {
    if (!_.isEmpty(documentItem)) {
      let data = Object.assign({}, {
          afFacilityDocumentID: documentItem.afFacilityDocumentID,
          applicationFormID: this.applicationForm.applicationFormID,
          afFacilityID: documentItem.afFacilityID
        }
      );

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
          heading: "Confirm Remove Document",
          message: "Do you want to remove this document ?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.applicationFormAddEditService.deactivateAFFacilityDocuments(data);
        }
      });
      this.mdbModalRef.hide();
    }
  }

  isAbleToEdit() {
    return this.applicationForm.assignUserID == this.applicationService.getLoggedInUserUserID()
      && (
        this.applicationForm.currentApplicationFormStatus == this.applicationFormStatusConst.DRAFT
        || this.applicationForm.currentApplicationFormStatus == this.applicationFormStatusConst.RETURNED
        || this.applicationForm.currentApplicationFormStatus == this.applicationFormStatusConst.IN_PROGRESS
      );
  }

  isUploadedDiv(data) {
    return this.applicationService.getLoggedInUserDivCode() == data.uploadedDivCode;
  }

}
