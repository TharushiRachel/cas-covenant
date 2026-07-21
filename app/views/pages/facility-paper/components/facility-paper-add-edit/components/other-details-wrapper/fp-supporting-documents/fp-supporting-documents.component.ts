import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FpSupportingDocumentUploadComponent} from "./fp-supporting-document-upload/fp-supporting-document-upload.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {FacilityPaperAddEditService} from "../../../../../services/facility-paper-add-edit.service";
import * as _ from 'lodash';
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {PrivilegeService} from "../../../../../../../../core/service/authentication/privilege.service";
import { DataService } from 'src/app/core/service/data/data.service';

@Component({
  selector: 'app-fp-supporting-documents',
  templateUrl: './fp-supporting-documents.component.html',
  styleUrls: ['./fp-supporting-documents.component.scss']
})
export class FpSupportingDocumentsComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  @Input('facilityPaper') facilityPaper: any = {};

  updatedFacilityPaper: any = {};
  onDocumentUploadChangeSub: Subscription = new Subscription();
  onDownLoadLinkChangeSub: Subscription = new Subscription();

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
    private privilegeService: PrivilegeService,
    private dataService: DataService,
  ) {
  }

  ngOnInit() {

    this.onDocumentUploadChangeSub = this.facilityPaperAddEditService.onFPUploadDocumentChange
      .subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.fpDocumentDTOList = data.fpDocumentDTOList;
        }
      });

    this.onDownLoadLinkChangeSub = this.facilityPaperAddEditService.onDownloadLinkChageFPSupportDoc
      .subscribe(data => {
        let downloadLink = this.downloadLink.nativeElement;
        downloadLink.href = window.URL.createObjectURL(data.data);
        downloadLink.download = data.fileName;
        downloadLink.click();
        this.alertService.showToaster("FpDocument downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      });


  }

  ngOnDestroy(): void {
    this.onDownLoadLinkChangeSub.unsubscribe();
    this.onDocumentUploadChangeSub.unsubscribe();

  }


  openModalSupportDocumentUpload(facilityPaper) {

    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityPaper}
      ]
    };

    this.modalRef = this.mdbModalService.show(FpSupportingDocumentUploadComponent, {
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
      let data = Object.assign({}, {fpOtherBankFacilityID: item.fpOtherBankFacilityID},
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
          this.facilityPaperAddEditService.deactivateFpSupportDocument(data);
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

  showDownloadDocsBtn() : boolean {
    if (this.facilityPaper.fpDocumentDTOList[0] != null){
      return true;
    } 
    return false;
  }

  downloadSupportingDocsZip() {
    const fpId = this.facilityPaper.facilityPaperID;
    const primaryCustomer = this.facilityPaper.casCustomerDTOList.find(c => c.isPrimary === true);

    let customerName = primaryCustomer.customerName;

    // If longer than 30, truncate and then add underscore
    if (customerName.length > 30) {
      customerName = customerName.substring(0, 30) + '_';
    }

    const fileName = `${customerName} - ${this.facilityPaper.fpRefNumber}`
    this.facilityPaperAddEditService.downloadSupportingDocsZipfile(fpId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `${fileName}.zip`;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    }, error => {
      console.error('Download failed', error);
    });
  }


}

