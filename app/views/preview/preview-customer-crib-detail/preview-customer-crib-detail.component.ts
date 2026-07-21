import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { Constants } from "../../../core/setting/constants";
import { ShowCribDetailsComponent } from "../../../shared/components/show-crib-details/show-crib-details.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CasDocumentStorageService } from "../../../core/service/data/cas-document-storage.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { FacilityPaperAddEditService } from "../../pages/facility-paper/services/facility-paper-add-edit.service";
import { Subscription } from "rxjs";
import { ViewCribContentComponent } from "src/app/shared/components/view-crib-content/view-crib-content.component";

@Component({
  selector: "app-preview-customer-crib-detail",
  templateUrl: "./preview-customer-crib-detail.component.html",
  styleUrls: ["./preview-customer-crib-detail.component.scss"],
})
export class PreviewCustomerCribDetailComponent implements OnInit, OnDestroy {
  @Input("customer") customer: any = {};
  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  modalRef: MDBModalRef;
  cribUploadResponse: any = {};
  cribDetailList: any[] = [];

  onUploadCribDetailChangeSub = new Subscription();

  @ViewChild("downloadLink", { static: false })
  private downloadLink: ElementRef;

  tableColumns = [
    "Full Name",
    "CRIB Status",
    "File Name",
    "Issued Date",
    "Remarks",
    "Action",
  ];

  constructor(
    private documentStorageService: CasDocumentStorageService,
    private mdbModalService: MDBModalService,
    private alertService: AlertService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.onUploadCribDetailChangeSub =
      this.facilityPaperAddEditService.onUploadCribDocumentChange.subscribe(
        (data: any) => {
          this.cribUploadResponse = data;

          this.cribDetailList =
            this.cribUploadResponse && this.cribUploadResponse.cribDetailList
              ? this.cribUploadResponse.cribDetailList.map((d: any) => ({
                  ...d,
                  isPrev: false,
                  isReport: false,
                  cribDateStr: "",
                  reportContent: "",
                }))
              : [];

          if (data.casCustomerDTOList && data.casCustomerDTOList.length > 0) {
            data.casCustomerDTOList.forEach((customer: any) => {
              let cribDetails = customer.casCustomerCribDetailDTOList.map(
                (obj: any) => ({
                  ...obj,
                  fullName: customer.customerName ? customer.customerName : "-",
                  isReport: false,
                  isPrev: true,
                  reportContent: "",
                })
              );
              let cribReports = customer.casCustomerCribReportDTOList.map(
                (obj: any) => ({
                  ...obj,
                  fullName: customer.customerName ? customer.customerName : "-",
                  isReport: true,
                  isPrev: true,
                  reportContent: obj.reportContent ? obj.reportContent : "",
                })
              );
              this.cribDetailList.push(...cribDetails);
              this.cribDetailList.push(...cribReports);
            });
          }
        }
      );
  }

  ngOnDestroy(): void {}

  openModalShowCribDetails(cribItem: any) {
    const initialState = {
      list: [{ tag: "Count", value: cribItem }],
    };
    this.modalRef = this.mdbModalService.show(ShowCribDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-100-p modal-dialog-scrollable min-height-550",
      containerClass: "right",
      animated: false,
      data: {
        cribReportSaveDto: cribItem,
        htmlContent: cribItem.reportContent,
        isEditEnabled: false,
      },
    });
  }

  viewDocument(item: any) {
    if (item.docStorageDTO.docStorageID != null) {
      this.documentStorageService
        .viewDocument(item.docStorageDTO)
        .then((data: any) => {
          this.viewInNewTab(data, item.docStorageDTO.fileName);
        });
    }
  }

  downloadDocument(item: any) {
    if (item.docStorageDTO.docStorageID != null) {
      this.documentStorageService
        .downloadDocument(item.docStorageDTO)
        .then((data: any) => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data);
          downloadLink.download = `${item.docStorageDTO.fileName}.pdf`;
          downloadLink.click();
          this.alertService.showToaster(
            "Document downloaded successfully",
            SETTINGS.TOASTER_MESSAGES.success
          );
        });
    }
  }

  viewInNewTab(data: any, fileName: string) {
    const pdfBlob = new Blob([data], { type: "application/pdf" });
    const pdfFile = new File([pdfBlob], fileName, { type: "application/pdf" });
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

  downloadPrevDocument(item: any) {
    if (item.docStorageDTO.docStorageID != null) {
      this.documentStorageService
        .downloadDocument(item.docStorageDTO)
        .then((data: any) => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data);
          downloadLink.download = item.docStorageDTO.fileName;
          downloadLink.click();
          this.alertService.showToaster(
            "Document downloaded successfully",
            SETTINGS.TOASTER_MESSAGES.success
          );
        });
    }
  }

  isCheckedPdf(item: any) {
    if (
      item.docStorageDTO.fileName.substring(
        item.docStorageDTO.fileName.lastIndexOf(".")
      ) == ".pdf"
    ) {
      return true;
    } else {
      return false;
    }
  }

  onDownLoadDoc(item: any) {
    if (item.docStorageDTO.docStorageID != null) {
      const extension = item.docStorageDTO.fileName.substring(
        item.docStorageDTO.fileName.lastIndexOf(".")
      );

      if (extension == ".pdf") {
        this.documentStorageService
          .viewDocument(item.docStorageDTO)
          .then((data: any) => {
            this.viewInNewTab(data, item.docStorageDTO.fileName);
          });
      } else {
        this.documentStorageService
          .downloadDocument(item.docStorageDTO)
          .then((data: any) => {
            let downloadLink = this.downloadLink.nativeElement;
            downloadLink.href = window.URL.createObjectURL(data);
            downloadLink.download = item.docStorageDTO.fileName;
            downloadLink.click();
            this.alertService.showToaster(
              "Document downloaded successfully",
              SETTINGS.TOASTER_MESSAGES.success
            );
          });
      }
    }
  }

  viewCribContent(item: any) {
    this.modalRef = this.mdbModalService.show(ViewCribContentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "right",
      animated: false,
      data: {
        content: item.reportContent,
      },
    });
  }
}
