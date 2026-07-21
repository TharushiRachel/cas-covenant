import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../../../services/facility-paper-add-edit.service";
import * as _ from "lodash";
import { Constants } from "../../../../../../../../../../core/setting/constants";
import { PersonalCribAddEditComponent } from "../personal-crib-add-edit/personal-crib-add-edit.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { SETTINGS } from "../../../../../../../../../../core/setting/commons.settings";
import { AlertService } from "../../../../../../../../../../core/service/common/alert.service";
import { ConfirmationDialogComponent } from "../../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "../../../../../../../../../../core/service/application/application.service";
import { ShowCribDetailsComponent } from "../../../../../../../../../../shared/components/show-crib-details/show-crib-details.component";
import { CasDocumentStorageService } from "../../../../../../../../../../core/service/data/cas-document-storage.service";
import { AddCribReportComponent } from "../add-crib-report/add-crib-report.component";
import { EditCribReportComponent } from "../edit-crib-report/edit-crib-report.component";
import { ViewCribContentComponent } from "src/app/shared/components/view-crib-content/view-crib-content.component";

@Component({
  selector: "app-personal-crib-detail",
  templateUrl: "./personal-crib-detail.component.html",
  styleUrls: ["./personal-crib-detail.component.scss"],
})
export class PersonalCribDetailComponent implements OnInit, OnDestroy {
  @Input("customer") customer: any = {};
  @Input("facilityPaper") facilityPaper: any = {};

  casCustomerID: any = null;
  cribUploadResponse: any = {};
  cribReportResponse: any = {};
  onUploadCribDetailChangeSub = new Subscription();
  onDownLoadLinkChangeSub = new Subscription();
  onSaveCribReportChangeSub: Subscription = new Subscription();

  modalRef: MDBModalRef;
  cribStatus = Constants.cribStatus;
  cribStatusConst = Constants.cribStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  statusConst = Constants.statusConst;
  customerIdentificationType = Constants.customerIdentificationType;
  cribDetailList: any[] = [];
  equalLoginUserAndAssignUser = false;

  @ViewChild("downloadLink", { static: false })
  private readonly downloadLink: ElementRef;

  tableColumns = [
    "Full Name",
    "CRIB Status",
    "File Name",
    "Issued Date",
    "Remarks",
    "Action",
  ];

  count: number = 0;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly mdbModalService: MDBModalService,
    private readonly alertService: AlertService,
    private readonly applicationService: ApplicationService,
    private readonly documentStorageService: CasDocumentStorageService
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

    this.onDownLoadLinkChangeSub =
      this.documentStorageService.onDocumentStorageChange.subscribe(
        (data: any) => {
          if (this.downloadLink) {
            let downloadLink = this.downloadLink.nativeElement;
            downloadLink.href = window.URL.createObjectURL(data.data);
            downloadLink.download = data.fileName;
            downloadLink.click();
            this.alertService.showToaster(
              "FpDocument downloaded successfully",
              SETTINGS.TOASTER_MESSAGES.success
            );
          }
        }
      );

    this.isEqualLoginAndAssignUser();
  }

  ngOnDestroy(): void {
    this.onUploadCribDetailChangeSub.unsubscribe();
    this.onSaveCribReportChangeSub.unsubscribe();
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  openModalCribDetails(facilityPaper: any, casCustomerID: any, cribItem?: any) {
    const initialState = {
      list: [{ tag: "Count", value: facilityPaper }],
    };
    this.modalRef = this.mdbModalService.show(PersonalCribAddEditComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "right",
      animated: false,
      data: {
        heading: "comming dto",
        content: {
          casCustomerID: casCustomerID,
          facilityPaper: facilityPaper,
          cribItem: cribItem,
          reportData: null,
        },
        facilityPaper: facilityPaper,
      },
    });
  }

  onRemove(cribItem: any) {
    if (!_.isEmpty(cribItem)) {
      let data = Object.assign(
        {},
        { casCustomerCribDetailsID: cribItem.casCustomerCribDetailsID },
        { facilityPaperID: cribItem.facilityPaperID },
        { casCustomerID: cribItem.casCustomerID },
        { supportingDocID: cribItem.supportingDocID },
        { docStorageDTO: cribItem.docStorageDTO },
        { status: this.statusConst.INA }
      );

      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Confirm Remove Document",
          message: "Do you want to remove this document ?",
        },
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.facilityPaperAddEditService.deactivateCribSupportDocument(data);
        }
      });
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

  isEditableAssignUser() {
    return (
      this.applicationService.getLoggedInUserUPMGroupCode() &&
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) <= 50
    );
  }

  isEqualLoginAndAssignUser() {
    return (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
  }

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isRejected() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED
    );
  }

  isUploadedDiv(data: any) {
    return (
      this.applicationService.getLoggedInUserDivCode() == data.uploadedDivCode
    );
  }

  isSavedDivCode(data: any) {
    return (
      this.applicationService.getLoggedInUserDivCode() == data.savedUserDivCode
    );
  }

  openModalShowCribDetails(cribItem: any, isEditEnabled: boolean) {
    const initialState = {
      list: [{ tag: "Count", value: this.facilityPaper }],
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

    this.modalRef.content.actionClickSave.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        let cribReportSaveDto = data;
        cribReportSaveDto.facilityPaperID = this.facilityPaper.facilityPaperID;
        cribReportSaveDto.savedUserDisplayName =
          this.applicationService.getLoggedInUserDisplayName();
        cribReportSaveDto.savedUserDivCode =
          this.applicationService.getLoggedInUserDivCode();
        this.facilityPaperAddEditService.saveOrUpdateCribReport(data);
      }
    });
  }

  openModalShowCribDetailsToEdit(cribItem: any) {
    this.openModalShowCribDetails(cribItem, true);
  }

  openModalShowCribDetailsToView(cribItem: any) {
    this.openModalShowCribDetails(cribItem, false);
  }

  onRemoveCribReport(cribItem: any) {
    if (!_.isEmpty(cribItem)) {
      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Confirm Remove Document",
          message: "Do you want to remove this document ?",
        },
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          cribItem.facilityPaperID = this.facilityPaper.facilityPaperID;
          this.facilityPaperAddEditService.deleteCribReport(cribItem);
        }
      });
    }
  }

  isAbleToEdit() {
    return (
      this.isEqualLoginAndAssignUser() &&
      !this.isApproveStatus() &&
      !this.isRejected()
    );
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

  openAddCribReport() {
    this.modalRef = this.mdbModalService.show(AddCribReportComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "right",
      animated: false,
      data: {
        heading: "Add Crib Report",
        content: {},
        facilityPaper: this.facilityPaper,
        prevCribReports: this.cribDetailList,
      },
    });
  }

  openEditCribReport(cribItem: any) {
    this.modalRef = this.mdbModalService.show(EditCribReportComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "right",
      animated: false,
      data: {
        heading: "Edit Crib Report",
        reportItem: cribItem,
        facilityPaper: this.facilityPaper,
      },
    });
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

  onRemoveCribContent(cribItem: any) {
    if (!_.isEmpty(cribItem)) {
      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Confirm Remove Document",
          message: "Do you want to remove this document ?",
        },
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          cribItem.status = this.statusConst.INA;
          cribItem.facilityPaperID = this.facilityPaper.facilityPaperID;
          this.facilityPaperAddEditService.deactiveCribReport(cribItem);
        }
      });
    }
  }
}
