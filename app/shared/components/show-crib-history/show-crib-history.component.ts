import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { Subject, Subscription } from "rxjs";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { CasCribServiceService } from "../../../core/service/data/cas-crib-service.service";
import { Constants } from "../../../core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { CasDocumentStorageService } from "src/app/core/service/data/cas-document-storage.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import * as moment from "moment";

@Component({
  selector: "app-show-crib-history",
  templateUrl: "./show-crib-history.component.html",
  styleUrls: ["./show-crib-history.component.scss"],
})
export class ShowCribHistoryComponent implements OnInit, OnDestroy {
  heading: string;
  message: string;
  content: any;
  customerCribReportID;
  action: Subject<any> = new Subject<any>();
  viewReportAction: Subject<any> = new Subject<any>();
  onCribReportHistoryChange = new Subscription();
  previousCribReports: any = [];
  enableActions: false;
  customerName;
  modalRef: MDBModalRef;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityPaperID: any;
  cribReportHistory: any[] = [];

  cribStatus = Constants.cribStatus;
  cribStatusConst = Constants.cribStatusConst;
  tableColumns = [
    "Full Name",
    "CRIB Status",
    "File Name",
    "Issued Date",
    "Upload On",
    "Action",
  ];
  @ViewChild("downloadLink", { static: false })
  private readonly downloadLink: ElementRef;

  constructor(
    private readonly mdbModalRef: MDBModalRef,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly alertService: AlertService,
    private readonly documentStorageService: CasDocumentStorageService
  ) {}

  ngOnInit() {
    // this.casCribServiceService.getRetailCribReportFromCasDB(this.content);

    // this.onCribReportHistoryChange =
    //   this.casCribServiceService.onCribReportHistoryChange.subscribe(
    //     (response: any) => {
    //       this.previousCribReports = response;
    //       if (this.previousCribReports.length > 0) {
    //         this.customerName = this.previousCribReports[0].customerName;
    //       }
    //     }
    //   );

    if (this.content && this.content.identificationNumber) {
      this.facilityPaperAddEditService
        .getCribHistoryByCustomer(
          this.content.identificationNumber,
          this.facilityPaperID
        )
        .then((resp: any) => {
          this.cribReportHistory = resp.map((d: any) => ({
            ...d,
            cribIssueDateStr: moment(d.cribIssueDateStr).format("YYYY-MM-DD"),
            uploadOn: d.modifiedDateStr
              ? moment(d.modifiedDateStr).format("YYYY-MM-DD")
              : moment(d.createdDateStr).format("YYYY-MM-DD"),
          }));
        });
    }
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onCribReportHistoryChange.unsubscribe();
  }

  onNoClick(): void {
    this.action.next();
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    this.action.next({
      proceedWithPreviousCribReport: false,
      proceedWithNewCribReport: true,
    });
    this.mdbModalRef.hide();
  }

  viewReport(customerCribReportID, data) {
    this.viewReportAction.next(data);
    this.mdbModalRef.hide();
  }

  proceedWithResponse(data) {
    if (this.enableActions) {
      this.action.next({
        proceedWithPreviousCribReport: true,
        proceedWithNewCribReport: false,
        pdfReport: data.pdfReport,
        previousCribDate: data.createdDateStr,
      });
      this.mdbModalRef.hide();
    }
  }

  isSelectedCribReport(report) {
    return this.customerCribReportID == report.customerCribReportID;
  }

  viewDocument(item: any) {
    if (item.docStorageID != null) {
      let request: any = { docStorageID: item.docStorageID };
      this.documentStorageService.viewDocument(request).then((data: any) => {
        this.viewInNewTab(data, item.documentName);
      });
    }
  }

  downloadDocument(item: any) {
    if (item.docStorageID != null) {
      let request: any = { docStorageID: item.docStorageID };
      this.documentStorageService
        .downloadDocument(request)
        .then((data: any) => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data);
          downloadLink.download = `${item.documentName}.pdf`;
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
                a.download = '${fileName}.pdf'; // Set the desired download name here
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
}
