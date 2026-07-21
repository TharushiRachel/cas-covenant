import { Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { PrivilegeService } from 'src/app/core/service/authentication/privilege.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';
import { FpSupportingDocumentUploadComponent } from '../fp-supporting-documents/fp-supporting-document-upload/fp-supporting-document-upload.component';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import * as _ from 'lodash';
import { FpCreditRiskCommentUploadDocumentsComponent } from './fp-credit-risk-comment-upload-documents/fp-credit-risk-comment-upload-documents.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-fp-credit-risk-comment-documents',
  templateUrl: './fp-credit-risk-comment-documents.component.html',
  styleUrls: ['./fp-credit-risk-comment-documents.component.scss']
})
export class FpCreditRiskCommentDocumentsComponent implements OnInit, OnDestroy {


  modalRef: MDBModalRef;
  @Input('facilityPaper') facilityPaper: any = {};
  @Input('isLoading') isLoading: any = {};
  @Input('isAbleToEditRiskComment') isAbleToEditRiskComment: any;

  data1: any;
  data2: any;

  isCheckedToDisplayDocument: boolean= false;
  facilityPaperByIDT: any;
  updatedFacilityPaper: any = {};
  onDocumentUploadChangeSub: Subscription = new Subscription();
  fpDocumentDTOList = [];

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService
  ) {
  }

  ngOnInit() {

    // this.isCheckedToDisplayDocument = this.IsCheckedToDisplay()
    this.facilityPaperByIDT = this.facilityPaperAddEditService.getFacilityPaperByIDT();

    this.onDocumentUploadChangeSub = this.facilityPaperAddEditService.onFPCreditRiskDocument
      .subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.fpDocumentDTOList = data.fpCreditRiskDocumentDTOList;
        }
      });


  }

  ngOnDestroy(): void {
    this.onDocumentUploadChangeSub.unsubscribe();
  }


  openModalSupportDocumentUpload(facilityPaper) {

    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityPaper}
      ]
    };

    this.modalRef = this.mdbModalService.show(FpCreditRiskCommentUploadDocumentsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p audit-modal-margin-center',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "comming dto",
        content: {facilityPaper: facilityPaper},
      }
    });
  }

  remove(item) {

    if (!_.isEmpty(item)) {
      let data = Object.assign({}, {creditRiskDocumentID: item.creditRiskDocumentID},
        {facilityPaperID: item.facilityPaperID},
        {docStorageDTO: item.docStorageDTO},
        {supportingDocID: item.supportingDocID},
        {status: 'INA'});

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
          this.facilityPaperAddEditService.deactivateFpCreditRiskDocument(data);
        }
      });

    }
  }

  onDownloadDoc(item) {
    if (item.docStorageDTO.docStorageID != null) {
      const extension = item.docStorageDTO.fileName.substring(item.docStorageDTO.fileName.lastIndexOf("."));

      if (extension == '.pdf') {
        this.facilityPaperAddEditService.viewFpSupportDocument(item.docStorageDTO).then((data: any) =>{
          this.viewInNewTab(data, item.docStorageDTO.fileName);
        });

      } else {
        this.facilityPaperAddEditService.downloadFpSupportDocument(item.docStorageDTO).then((data: any) =>{
          let downloadLink = this.downloadLink.nativeElement;
           downloadLink.href = window.URL.createObjectURL(data);
           downloadLink.download = item.docStorageDTO.fileName;
           downloadLink.click();
           this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
       });
      }

    }
  }

  isEqualLoginAndAssignUser() {
    if (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED;
  }

  isRejected() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.REJECTED;
  }


  // IsCheckedToDisplay() {
  //   let getLoggedInUserDivCode: String = this.applicationService.getLoggedInUserDivCode();
  //   this.facilityPaperByIDT = this.facilityPaperAddEditService.getFacilityPaperByIDT();

  //   if (localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE)){
  //     this.data1 = localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE).replace(/[^\w\s]/gi, "")
  //   } else {
  //     this.data1 = "0"
  //   }

  //   if (getLoggedInUserDivCode == this.data1) {
  //     return true;
  //   } else if ((this.facilityPaperByIDT !== getLoggedInUserDivCode) && (this.facilityPaperByIDT == this.data1)) {

  //       return false;

  //   } else if (this.facilityPaper.currentAssignUserDivCode != this.data1) {

  //     return true;

  // }

  //   else {
  //     return false;
  //   }
  // }

  isUploadedDiv(data) {
    return this.applicationService.getLoggedInUserDivCode() == data.uploadedDivCode;
  }


  downloadDocument(item) {
    if (item.docStorageDTO.docStorageID != null) {

        this.facilityPaperAddEditService.downloadFpSupportDocument(item.docStorageDTO).then((data: any) =>{
          let downloadLink = this.downloadLink.nativeElement;
           downloadLink.href = window.URL.createObjectURL(data);
           downloadLink.download = item.docStorageDTO.fileName;
           downloadLink.click();
           this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
       });

    }
  }


  viewInNewTab(data, fileName){

    const pdfBlob = new Blob([data], { type: 'application/pdf' });
    const pdfFile = new File([pdfBlob], fileName, { type: 'application/pdf' });
    const fileUrl = URL.createObjectURL(pdfFile);

    const newWindow = window.open();
        newWindow.document.write(
        `<html>
          <head>
            <title>${fileName}</title>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
            <style>
              .menu-bar {
                position: absolute;
                top: 8px;
                right: 80px;

                padding: 10px;
                display: flex;
                align-items: center;
              }

              .menu-bar button {
                margin-right: 10px;
              }

              .menu-bar .download-button {
                margin-left: auto;
                background-color: transparent;
                color: white;
                border : none;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                border-radius: 500px;
                cursor: pointer;

              }


            </style>
          </head>
          <body>
            <div style="position: relative;">
              <embed src="${fileUrl}" id="pdfViewer" class="pdfjs-viewer" height="100%" width="100%" type="application/pdf" />
              <div class="menu-bar">
                <button id="downloadButton" class = "download-button" title="Download PDF">
                  <i class='fas fa-download' style='color: white'></i>
                </button>
              </div>
            </div>

            <script>
              const downloadButton = document.getElementById('downloadButton');
              downloadButton.addEventListener('click', function() {
                const pdfViewer = document.getElementById('pdfViewer');
                const a = document.createElement('a');
                a.href = pdfViewer.src;
                a.download = '${fileName}'; // Set the desired download name here
                a.style.display = 'none';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
              });
            </script>
          </body>
        </html>`
        );
        newWindow.document.close();

  }


  isCheckedPdf(item: any) {

    if (item.docStorageDTO.fileName.substring(item.docStorageDTO.fileName.lastIndexOf(".")) == '.pdf') {
      return true;
    } else{
      return false;
    }

  }

  isLockedPdf(item: any) {
    if (item.isLocked == Constants.yesNoConst.N) {
      return true;
    } else {
      return false;
    }
    

  }

  isCheckedToDisplayDoc (item: any) {
    
    let getLoggedInUserDivCode: String = this.applicationService.getLoggedInUserDivCode();
    
    if (localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE)){
      this.data1 = localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE).replace(/[^\w\s]/gi, "")
    } else {
      this.data1 = "0"
    }


    if (item == Constants.yesNoConst.Y) {
      return true;
    }
    else if (getLoggedInUserDivCode == this.data1) {
      return true;
    } 
    else if ((this.facilityPaperByIDT !== getLoggedInUserDivCode) && (this.facilityPaperByIDT == this.data1)) {

        return false;

    } 
    else if (this.facilityPaper.currentAssignUserDivCode != this.data1) {

      return true;

  }
    else {
      return false;
    }
    
  }

}
