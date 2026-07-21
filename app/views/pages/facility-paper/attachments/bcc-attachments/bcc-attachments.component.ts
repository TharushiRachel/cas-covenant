import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { BehaviorSubject, Subject, Subscription } from "rxjs";
import { FileUploadError } from "src/app/shared/dto/file-upload-error";
import { FacilityPaperAddEditService } from "../../services/facility-paper-add-edit.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { Constants } from "src/app/core/setting/constants";
import * as _ from "lodash";
import { FileValidator } from "src/app/shared/validators/file.validator";
import { AppUtils } from "src/app/shared/app.utils";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { PrivilegeService } from "src/app/core/service/authentication/privilege.service";
import { FpSupportingDocumentUploadComponent } from "../../components/facility-paper-add-edit/components/other-details-wrapper/fp-supporting-documents/fp-supporting-document-upload/fp-supporting-document-upload.component";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { BccDocumentUploadComponent } from "../bcc-document-upload/bcc-document-upload.component";
import { Router } from "@angular/router";
import * as moment from "moment";

@Component({
  selector: "app-bcc-attachments",
  templateUrl: "./bcc-attachments.component.html",
  styleUrls: ["./bcc-attachments.component.scss"],
})
export class BccAttachmentsComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  @Input("facilityPaper") facilityPaper: any = {};

  updatedFacilityPaper: any = {};
  onDocumentUploadChangeSub: Subscription = new Subscription();
  onDownLoadLinkChangeSub: Subscription = new Subscription();

  fpDocumentDTOList: any = {};
  docs: any[] = [];

  docList = [];
  showUploadForm: boolean = true;
  bccEntererWorkClass = "";
  bccAuthorizerWorkClass = "";
  loggedInUserWorkClass = "";

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  @ViewChild("downloadLink", { static: false })
  private downloadLink: ElementRef;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private cacheService: CacheService,
    private router: Router
  ) {}

  ngOnInit() {

    this.loggedInUserWorkClass =
      this.applicationService.getLoggedInUserUPMGroupCode();
    this.bccEntererWorkClass = this.cacheService.getData(
      Constants.masterDataKey.CAS_BCC_ENTERER_WORK_CLASS
    );
    this.bccAuthorizerWorkClass = this.cacheService.getData(
      Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS
    );

    if (
      this.facilityPaper.fpBccList[0] != null &&
      this.facilityPaper.fpBccList[0].fpBccDocumentList != null
    ) {
      if (
        this.bccAuthorizerWorkClass == this.loggedInUserWorkClass &&
        this.facilityPaper.fpBccList[0].docsApproveStatus != "PENDING"
      ) {
        this.facilityPaper.fpBccList[0].fpBccDocumentList =
          this.facilityPaper.fpBccList[0].fpBccDocumentList.filter(
            (item) =>
              item.approveStatus === "APPROVED" ||
              item.approveStatus === "REJECTED"
          );
      } else if (
        this.bccAuthorizerWorkClass != this.loggedInUserWorkClass &&
        this.bccEntererWorkClass != this.loggedInUserWorkClass
      ) {
        this.facilityPaper.fpBccList[0].fpBccDocumentList =
          this.facilityPaper.fpBccList[0].fpBccDocumentList.filter(
            (item) => item.approveStatus === "APPROVED"
          );
      }
      this.facilityPaper.fpBccList[0].fpBccDocumentList = this.sortDocuments(
        this.facilityPaper.fpBccList[0].fpBccDocumentList
      );
    }

    if (this.bccEntererWorkClass == this.loggedInUserWorkClass) {
      if (
        this.facilityPaper.fpBccList[0] != null &&
        this.facilityPaper.fpBccList[0].approveStatus == "PENDING"
      ) {
        this.showUploadForm = false;
      } else {
        if (
          this.facilityPaper.fpBccList[0] != null &&
          this.facilityPaper.fpBccList[0].docsApproveStatus == "PENDING"
        ) {
          this.showUploadForm = false;
        } else {
          this.showUploadForm = true;
        }
      }
    } else {
      this.showUploadForm = false;
    }

    if (this.bccAuthorizerWorkClass == this.loggedInUserWorkClass) {
      this.showUploadForm = false;
    }

    this.onDocumentUploadChangeSub =
      this.facilityPaperAddEditService.onFPUploadDocumentChange.subscribe(
        (data: any) => {
          if (
            !_.isEmpty(data) &&
            !_.isEmpty(data.fpBccList[0]) &&
            !_.isEmpty(data.fpBccList[0].fpBccDocumentList)
          ) {
            const filteredObjects = data.fpBccList[0].fpBccDocumentList.filter(
              (itemDoc) => {
                return itemDoc.status === "Active";
              }
            );
            this.facilityPaper.fpBccList[0].fpBccDocumentList = filteredObjects;
            this.facilityPaper.fpBccList[0].fpBccDocumentList =
              this.sortDocuments(
                this.facilityPaper.fpBccList[0].fpBccDocumentList
              );
          }
        }
      );

    this.onDownLoadLinkChangeSub =
      this.facilityPaperAddEditService.onDownloadLinkChageFPSupportDoc.subscribe(
        (data) => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data.data);
          downloadLink.download = data.fileName;
          downloadLink.click();
          this.alertService.showToaster(
            "Document downloaded successfully",
            SETTINGS.TOASTER_MESSAGES.success
          );
        }
      );
  }

  sortDocuments(documents: any[]): any[] {
    return documents.sort((a, b) => {
      return this.getOrder(a.documentName) - this.getOrder(b.documentName);
    });
  }

  getOrder(documentName: string): number {
    if (documentName === "Meeting Minute") {
      return 2;
    } else if (documentName === "Cover Page") {
      return 1;
    } else {
      return 3;
    }
  }

  ngOnDestroy(): void {
    this.onDownLoadLinkChangeSub.unsubscribe();
    this.onDocumentUploadChangeSub.unsubscribe();
  }

  openModalSupportDocumentUpload(formName) {
    const initialState = {
      list: [{ tag: "Count", value: this.facilityPaper }],
    };

    this.modalRef = this.mdbModalService.show(BccDocumentUploadComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p audit-modal-margin-center",
      containerClass: "right",
      animated: false,
      data: {
        heading: "comming dto",
        content: { facilityPaper: this.facilityPaper },
        formName: formName,
      },
    });
  }

  remove(item) {
    if (!_.isEmpty(item)) {
      let data = Object.assign(
        {},
        { facilityPaperID: this.facilityPaper.facilityPaperID },
        { fpBccDocumentID: item.fpBccDocumentID },
        { status: "INA" }
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
          this.facilityPaperAddEditService.deactivateFpBccDocument(data);
        }
      });
    }
  }

  // checkDocs(item) {
  //   if (!_.isEmpty(item)) {
  //     console.log("delete-item", item);
  //     let data = Object.assign({}, { fpOtherBankFacilityID: item.fpOtherBankFacilityID },
  //       { facilityPaperID: item.facilityPaperID }, //
  //       { fpBccDocumentID: item.supportingDocID },
  //       { status: 'INA' });

  //     this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
  //       backdrop: true,
  //       keyboard: true,
  //       focus: true,
  //       show: false,
  //       ignoreBackdropClick: true,
  //       class: 'modal-width-30-p modal-margin-center ',
  //       containerClass: '',
  //       animated: true,
  //       data: {
  //         heading: "Confirm Document",
  //         message: "Do you want to upload this document ?",
  //       }
  //     });
  //     this.modalRef.content.action.subscribe((isYes: any) => {
  //       if (isYes) {
  //         this.facilityPaperAddEditService.deactivateFpBccDocument(data);
  //       }
  //     });

  //   }
  // }

  onDownloadDoc(item: any) {
    if (item.docStorageDTO.docStorageID != null) {
      let docNameWithoutExtension;
      const extension = item.docStorageDTO.fileName.substring(
        item.docStorageDTO.fileName.lastIndexOf(".")
      );
      item.documentName = item.documentName.trim();

      if (
        item.documentName.endsWith(".pdf") ||
        item.documentName.endsWith(".PDF")
      ) {
        const fileNameWithoutExtension = item.documentName.substring(
          0,
          item.documentName.lastIndexOf(".")
        );
        docNameWithoutExtension = fileNameWithoutExtension;
      } else {
        docNameWithoutExtension = item.documentName;
      }

      let downloadFileName = `${docNameWithoutExtension}_${this.facilityPaper.fpRefNumber}`;

      if (extension == ".pdf" || ".PDF") {
        this.facilityPaperAddEditService
          .viewFpSupportDocument(item.docStorageDTO)
          .then((data: any) => {
            this.viewInNewTab(data, downloadFileName);
          });
      } else {
        this.facilityPaperAddEditService
          .downloadFpSupportDocument(item.docStorageDTO)
          .then((data: any) => {
            if (this.downloadLink) {
              let downloadLink = this.downloadLink.nativeElement;
              downloadLink.href = window.URL.createObjectURL(data);
              downloadLink.download = downloadFileName;
              downloadLink.click();
              this.alertService.showToaster(
                "Document downloaded successfully",
                SETTINGS.TOASTER_MESSAGES.success
              );
            } else {
              console.error("Download link element not found.");
            }
          });
      }
    }
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      return false;
    }
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

  isUploadedDiv(data) {
    return (
      this.applicationService.getLoggedInUserDivCode() == data.uploadedDivCode
    );
  }

  downloadDocument(item) {
    if (item.docStorageDTO.docStorageID != null) {
      this.facilityPaperAddEditService
        .downloadFpSupportDocument(item.docStorageDTO)
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

  viewInNewTab(data, fileName) {
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

  isCheckedPdf(document: any) {
    if (
      document.docStorageDTO.fileName.substring(
        document.docStorageDTO.fileName.lastIndexOf(".")
      ) == ".pdf" ||
      ".PDF"
    ) {
      return true;
    } else {
      return false;
    }
  }

  showForwardDocsButton(): boolean {
    if (
      this.bccEntererWorkClass == this.loggedInUserWorkClass &&
      this.facilityPaper.fpBccList[0] != null &&
      this.facilityPaper.fpBccList[0].docsApproveStatus != "PENDING"
    ) {
      if (this.facilityPaper.fpBccList[0].fpBccDocumentList != null) {
        const hasPendingDocs =
          this.facilityPaper.fpBccList[0].fpBccDocumentList.some(
            (doc) => doc.approveStatus != "APPROVED"
          );
        if (
          this.facilityPaper.fpBccList[0].bccStatus != "" &&
          hasPendingDocs &&
          this.facilityPaper.fpBccList[0].approveStatus != "PENDING" &&
          this.facilityPaper.fpBccList[0].approveStatus != "REJECTED"
        ) {
          return true;
        }
      }
    }
    return false;
  }

  ForwardBCCDocs() {
    let request = { fpBccId: this.facilityPaper.fpBccList[0].fpBccId };

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
        heading: "Confirm Forward Document",
        message: "Do you want to forward this document ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.facilityPaperAddEditService.forwardBCCDocs(request).subscribe(
          (data: any) => {
            if (!_.isEmpty(data)) {
              this.facilityPaper.fpBccList[0] = data;
              this.alertService.showToaster(
                "Document Forwarded successfully",
                SETTINGS.TOASTER_MESSAGES.success
              );
              this.router.navigate(["/committee-paper/dashboard"]);
            }
          },
          (error) => {
            this.alertService.showToaster(
              "Please contact system administrator",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
      }
    });
  }

  showAuthorizeDocsButton(): boolean {
    if (
      this.bccAuthorizerWorkClass == this.loggedInUserWorkClass &&
      this.facilityPaper.fpBccList[0] != null &&
      this.facilityPaper.fpBccList[0].docsApproveStatus == "PENDING" &&
      this.facilityPaper.fpBccList[0].approveStatus != "PENDING"
    ) {
      if (this.facilityPaper.fpBccList[0].fpBccDocumentList != null) {
        return true;
      }
    }
    return false;
  }

  authorizeDocs() {
    let request = {
      fpBccID: this.facilityPaper.fpBccList[0].fpBccId,
      approveStatus: "APPROVED",
    };

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
        heading: "Confirm Authorize Document",
        message: "Do you want to authorize this document ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.facilityPaperAddEditService.authorizeDocs(request).subscribe(
          (data: any) => {
            if (!_.isEmpty(data)) {
              this.facilityPaper.fpBccList[0] = data;
              this.alertService.showToaster(
                "Document Authorized Successfully",
                SETTINGS.TOASTER_MESSAGES.success
              );
              this.router.navigate(["/committee-paper/dashboard"]);
            }
          },
          (error) => {
            this.alertService.showToaster(
              "Please contact system administrator",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
      }
    });
  }

  rejectDocs() {
    let request = {
      fpBccID: this.facilityPaper.fpBccList[0].fpBccId,
      approveStatus: "REJECTED",
    };

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
        heading: "Confirm Reject Document",
        message: "Do you want to reject this document ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.facilityPaperAddEditService.authorizeDocs(request).subscribe(
          (data: any) => {
            if (!_.isEmpty(data)) {
              this.facilityPaper.fpBccList[0] = data;
              this.alertService.showToaster(
                "Document Reject Successfully",
                SETTINGS.TOASTER_MESSAGES.success
              );
              this.router.navigate(["/committee-paper/dashboard"]);
            }
          },
          (error) => {
            this.alertService.showToaster(
              "Please contact system administrator",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
      }
    });
  }

  isExistsCoverPage(): boolean {
    if (
      this.facilityPaper.fpBccList[0] != null &&
      this.facilityPaper.fpBccList[0].fpBccDocumentList != null
    ) {
      let cc =
        this.facilityPaper.fpBccList[0].fpBccDocumentList.find(
          (document) =>
            document.documentName === "Cover Page" &&
            document.status === "Active" &&
            document.approveStatus === "APPROVED"
        ) !== undefined;
      if (cc) {
        return true;
      }
    }
    return false;
  }

  isExistsMeetingMinute(): boolean {
    if (
      this.facilityPaper.fpBccList[0] != null &&
      this.facilityPaper.fpBccList[0].fpBccDocumentList != null
    ) {
      let mm =
        this.facilityPaper.fpBccList[0].fpBccDocumentList.find(
          (document) =>
            document.documentName === "Meeting Minute" &&
            document.status === "Active" &&
            document.approveStatus === "APPROVED"
        ) !== undefined;
      if (mm) {
        return true;
      }
    }
    return false;
  }
}
