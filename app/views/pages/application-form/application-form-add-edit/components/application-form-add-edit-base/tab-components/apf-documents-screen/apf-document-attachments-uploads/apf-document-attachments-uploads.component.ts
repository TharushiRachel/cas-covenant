import {Component, ElementRef, EventEmitter, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import * as _ from "lodash";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {ConfirmationDialogComponent} from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {SETTINGS} from "../../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../../core/service/common/alert.service";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {ApplicationService} from "../../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-apf-document-attachments-uploads',
  templateUrl: './apf-document-attachments-uploads.component.html',
  styleUrls: ['./apf-document-attachments-uploads.component.scss']
})
export class ApfDocumentAttachmentsUploadsComponent implements OnInit, OnDestroy {

  onApplicationFormDocumentChange = new Subscription();
  onDownloadLinkChangeAPSupportDocUpload = new Subscription();
  applicationFrom: any = {};
  modalRef: MDBModalRef;
  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;
  @Output('openModalDocumentDetailsDetails') openModalDocumentDetailsDetails = new EventEmitter();
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;


  constructor(private applicationFormAddEditService: ApplicationFormAddEditService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService,
              private alertService: AlertService,
  ) {
  }

  ngOnInit() {
    this.onApplicationFormDocumentChange = this.applicationFormAddEditService.onApplicationFormDocumentChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationFrom = data;
      }
    });

    this.onDownloadLinkChangeAPSupportDocUpload = this.applicationFormAddEditService.onDownloadLinkChangeAPSupportDocUpload
      .subscribe(data => {
        let downloadLink = this.downloadLink.nativeElement;
        downloadLink.href = window.URL.createObjectURL(data.data);
        downloadLink.download = data.fileName;
        downloadLink.click();
        this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      });

  }

  ngOnDestroy(): void {
    this.onApplicationFormDocumentChange.unsubscribe();
    this.onDownloadLinkChangeAPSupportDocUpload.unsubscribe();

  }

  remove(item) {

    if (!_.isEmpty(item)) {
      let data = Object.assign({}, {...item},
        {
          applicationFormID: item.applicationFormID,
          docStorageDTO:
          item.docStorageDTO,
          supportingDocID:
          item.supportingDocID,
          status:
            'INA'
        });

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
          this.applicationFormAddEditService.deactivateAFSupportDocument(data);
        }
      });
    }
  }

  onDownloadDoc(item) {
    if (item.docStorageDTO.docStorageID != null) {
      const extension = item.docStorageDTO.fileName.substring(item.docStorageDTO.fileName.lastIndexOf("."));

      if (extension == '.pdf') {
        this.applicationFormAddEditService.viewAFSupportDocument(item.docStorageDTO).then((data: any) =>{
          this.viewInNewTab(data, item.docStorageDTO.fileName);
        });


      } else {
        this.applicationFormAddEditService.downloadAFSupportDocument(item.docStorageDTO).then((data: any) =>{
          let downloadLink = this.downloadLink.nativeElement;
           downloadLink.href = window.URL.createObjectURL(data);
           downloadLink.download = item.docStorageDTO.fileName;
           downloadLink.click();
           this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
       });
      }

    }
  }

  update(documentDTO) {
    this.openModalDocumentDetailsDetails.emit(documentDTO);
  }

  isAbleToEdit() {
    return this.applicationFrom.assignUserID == this.applicationService.getLoggedInUserUserID()
      && (
        this.applicationFrom.currentApplicationFormStatus == this.applicationFormStatusConst.DRAFT
        || this.applicationFrom.currentApplicationFormStatus == this.applicationFormStatusConst.RETURNED
        || this.applicationFrom.currentApplicationFormStatus == this.applicationFormStatusConst.IN_PROGRESS
      );
  }

  isUploadedDiv(data) {
    return this.applicationService.getLoggedInUserDivCode() == data.uploadedDivCode;
  }

  downloadDocument(item) {
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
  
}
