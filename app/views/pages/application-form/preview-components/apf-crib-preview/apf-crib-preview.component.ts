import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {HtmlContentViewerComponent} from "../../../../../shared/components/html-content-viewer/html-content-viewer.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../application-form-add-edit/services/application-form-add-edit.service";
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Component({
  selector: 'app-apf-crib-preview',
  templateUrl: './apf-crib-preview.component.html',
  styleUrls: ['./apf-crib-preview.component.scss']
})
export class ApfCribPreviewComponent implements OnInit, OnDestroy {

  @Input() basicInformation;
  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  modalRef: MDBModalRef;

  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;


  constructor(private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private alertService: AlertService,) {
  }

  ngOnInit() {

  }

  viewInitiateCribReport(data) {
    this.modalRef = this.mdbModalService.show(HtmlContentViewerComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        header: 'CRIB Details',
        htmlContent: data,
      }
    });
  }

  onDownloadDoc($event?, item?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    if (item.docStorageDTO.docStorageID != null) {
      const extension = item.docStorageDTO.fileName.substring(item.docStorageDTO.fileName.lastIndexOf("."));

      if (extension == '.pdf') {
        this.applicationFormAddEditService.viewAFCribAttachments(item.docStorageDTO).then((data: any) =>{
          this.viewInNewTab(data, item.docStorageDTO.fileName);
        });

      } else {
        this.applicationFormAddEditService.downloadAFCribAttachments(item.docStorageDTO).then((data: any) =>{
          let downloadLink = this.downloadLink.nativeElement;
           downloadLink.href = window.URL.createObjectURL(data);
           downloadLink.download = item.docStorageDTO.fileName;
           downloadLink.click();
           this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
       });
      }

    }
  }


  downloadDocument($event?, item?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    if (item.docStorageDTO.docStorageID != null) {

        this.applicationFormAddEditService.downloadAFCribAttachments(item.docStorageDTO).then((data: any) =>{
          let downloadLink = this.downloadLink.nativeElement;
           downloadLink.href = window.URL.createObjectURL(data);
           downloadLink.download = item.docStorageDTO.fileName;
           downloadLink.click();
           this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
       });

    }
  }

  ngOnDestroy(): void {

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
