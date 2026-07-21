import { Component, Input, OnInit } from "@angular/core";
import { AlertService } from "src/app/core/service/common/alert.service";
import { EsgService } from "src/app/core/service/common/esg.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { EsgAnnexureAttachmentComponent } from "../esg-annexure-attachment/esg-annexure-attachment.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ConfirmationDialogComponent } from "../../confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-esg-view-attachment",
  templateUrl: "./esg-view-attachment.component.html",
  styleUrls: ["./esg-view-attachment.component.scss"],
})
export class EsgViewAttachmentComponent implements OnInit {
  @Input() facilityPaperID: number;
  @Input() applicationFormID: number;
  @Input() applicationForm: any;
  @Input() facilityPaper: any;
  @Input() attachments: any[] = [];
  @Input() isFacilityPaper: boolean = false;
  modalRef: MDBModalRef;

  loading = false;

  tableColumns = [
    "Annexure Name",
    "Annexure Description",
    "File Name",
    "Created By",
    "Action",
  ];

  constructor(
    private readonly esgService: EsgService,
    private readonly alertService: AlertService,
    private readonly mdbModalService: MDBModalService,
    private readonly applicationService: ApplicationService
  ) { }

  ngOnInit() {
    const { applicationFormID, facilityPaperID } = this.resolveIds();
    this.refreshAttachments();
  }

  ngOnChanges(): void {
    if (this.attachments && this.attachments.length > 0) {

      this.attachments = this.attachments
        .filter((a) => a.status === Constants.statusConst.ACT)
        .sort((a, b) => {
          const dateA = a.createdDate ? new Date(a.createdDate).getTime() : 0;
          const dateB = b.createdDate ? new Date(b.createdDate).getTime() : 0;
          return dateB - dateA; // latest first
        });
    }
  }  

  refreshAttachments(): void {
    const { applicationFormID, facilityPaperID } = this.resolveIds();
    if (!applicationFormID && !facilityPaperID) {
      this.attachments = [];
      return;
    }

    this.loading = true;
    this.esgService
      .getEsgAttachments({ applicationFormID, facilityPaperID })
      .then((rows) => {
        this.attachments = (rows || [])
        .filter((r) => r.status === Constants.statusConst.ACT)
        .sort((a, b) => {
          const dateA = a.createdDate ? new Date(a.createdDate).getTime() : 0;
          const dateB = b.createdDate ? new Date(b.createdDate).getTime() : 0;
          return dateB - dateA; // latest first
        });
        this.loading = false;
      })
      .catch((err) => {
        this.alertService.showToaster(
          "Failed to load attachments",
          SETTINGS.TOASTER_MESSAGES.error
        );
        this.loading = false;
      });
  }

  setAttachments(list: any[]): void {
    this.attachments = list;
  }

  private resolveIds(): {
    applicationFormID: number | null;
    facilityPaperID: number | null;
  } {
    const afId =
      this.applicationForm && this.applicationForm.applicationFormID
        ? this.applicationForm.applicationFormID
        : this.applicationFormID
          ? this.applicationFormID
          : null;

    const fpId =
      this.facilityPaper && this.facilityPaper.facilityPaperID
        ? this.facilityPaper.facilityPaperID
        : this.facilityPaperID
          ? this.facilityPaperID
          : null;

    return { applicationFormID: afId, facilityPaperID: fpId };
  }

  load(): void {
    const { applicationFormID, facilityPaperID } = this.resolveIds();
    if (!applicationFormID && !facilityPaperID) return; // nothing to load for

    this.loading = true;
    this.esgService
      .getEsgAttachments({ applicationFormID, facilityPaperID })
      .then((rows) => {
        // normalize a bit to avoid UI errors if BE omits some fields
        this.attachments = (rows || [])
          .filter((r) => r.status === Constants.statusConst.ACT) // <--- filter here
          .map((r: any) => ({
            esgStoragerID: r.esgStorageID,
            name: r.name || "-",
            description: r.description || "-",
            fileName: r.fileName || "-",
            createdDate: r.createdDate || r.createdDateStr || null,
            createdBy: r.createdBy || null
          }))
          .sort((a, b) => {
            const dateA = a.createdDate ? new Date(a.createdDate).getTime() : 0;
            const dateB = b.createdDate ? new Date(b.createdDate).getTime() : 0;
            return dateB - dateA; // latest first
          }); 
        this.loading = false;
      })
      .catch(() => {
        this.alertService.showToaster(
          "Failed to load ESG attachments",
          SETTINGS.TOASTER_MESSAGES.error
        );
        this.loading = false;
      });
  }

  onPreview(attachment: any): void {
    if (!attachment || !attachment.esgStorageID) {
      this.alertService.showToaster(
        "Attachment data is missing",
        SETTINGS.TOASTER_MESSAGES.error
      );
      return;
    }

    this.esgService
      .getEsgAttachmentById(attachment.esgStorageID)
      .then((res) => {
        const base64Doc = typeof res === "string" ? res : res.result;
        if (!base64Doc) {
          throw new Error("No document found");
        }

        // detect MIME type based on file extension
        const fileExt = (attachment.fileName || "")
          .split(".")
          .pop()
          .toLowerCase();
        let mimeType = "application/octet-stream"; // default fallback

        switch (fileExt) {
          case "pdf":
            mimeType = "application/pdf";
            break;
          case "jpg":
          case "jpeg":
            mimeType = "image/jpeg";
            break;
          case "png":
            mimeType = "image/png";
            break;
          case "doc":
            mimeType = "application/msword";
            break;
          case "docx":
            mimeType =
              "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            break;
          case "xls":
            mimeType = "application/vnd.ms-excel";
            break;
          case "xlsx":
            mimeType =
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            break;
        }

        const blob = this.base64ToBlob(base64Doc, mimeType);
        const url = URL.createObjectURL(blob);

        // PDF or images can be previewed in browser
        if (
          mimeType.startsWith("application/pdf") ||
          mimeType.startsWith("image/")
        ) {
          window.open(url);
        } else {
          // for docx/xlsx, force download
          const a = document.createElement("a");
          a.href = url;
          a.download = attachment.fileName || "file";
          a.click();
        }
      })
      .catch((err) => {
        this.alertService.showToaster(
          "Failed to load document",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  private base64ToBlob(base64: string, contentType: string): Blob {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: contentType });
  }

  openAttachmentModal(mode: "add" | "edit", attachment?: any): void {
    const ids = this.resolveIds();
    this.modalRef = this.mdbModalService.show(EsgAnnexureAttachmentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-45-p modal-margin-center",
      data: {
        mode,
        attachment: attachment || null,
        applicationFormID: ids.applicationFormID,
        facilityPaperID: ids.facilityPaperID,
      },
    });

    this.modalRef.content.action.subscribe(() => {
      this.refreshAttachments();
    });
  }

  openEditAttachmentModal(attachment: any): void {
    this.openAttachmentModal("edit", attachment);
  }

  confirmDeleteAttachment(attachment: any): void {
    if (!attachment.esgStorageID) return;

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {
        heading: "Confirm Delete Attachment",
        message: `Do you really want to delete "${attachment.name || attachment.fileName || "this attachment"
          }"?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (!isYes) return;

      this.esgService
        .deleteAttachment(attachment.esgStorageID)
        .then(() => {
          this.alertService.showToaster(
            "Attachment deleted successfully",
            SETTINGS.TOASTER_MESSAGES.success
          );
          this.refreshAttachments(); // reload list
        })
        .catch(() => {
          this.alertService.showToaster(
            "Failed to delete attachment",
            SETTINGS.TOASTER_MESSAGES.error
          );
        });
    });
  }

  isEditEnabled(): boolean {
    if (this.isFacilityPaper) {
      return (
        this.facilityPaper.currentAssignUser ===
        this.applicationService.getLoggedInUserUserName() &&
        this.isAllowedFPPaper() &&
        this.facilityPaper.isESGApproved === Constants.yesNoConst.N
      );
    }
    return (
      this.applicationForm.assignUserID ===
      this.applicationService.getLoggedInUserUserID() &&
      this.isAllowedAFPaper()
    );
  }

  isAllowedAFPaper() {
    return (
      this.applicationForm.currentApplicationFormStatus !==
      Constants.applicationFormCurrentStatusConst.PAPER_CREATED &&
      this.applicationForm.currentApplicationFormStatus !==
      Constants.applicationFormCurrentStatusConst.DECLINED
    );
  }

  isAllowedFPPaper() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !==
      Constants.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !==
      Constants.facilityPaperStatusConst.REJECTED
    );
  }

  isNotRiskUser() {
    return (
      this.applicationService.getLoggedInUserDivCode() &&
      this.applicationService.getLoggedInUserDivCode().toString() !==
      SETTINGS.ESG_DIV_CODE.toString()
    );
  }

  isCreatedUser(attachment: any): boolean {
    return (
      attachment && attachment.createdBy === this.applicationService.getLoggedInUserUserName()
    )
  }

  isPreviewable(attachment: any): boolean {
    if (!attachment.fileName) return false;
    const ext = attachment.fileName.split('.').pop().toLowerCase();
    return ['pdf', 'jpg', 'jpeg', 'png'].includes(ext);
  }
  
  isDownloadOnly(attachment: any): boolean {
    if (!attachment.fileName) return false;
    const ext = attachment.fileName.split('.').pop().toLowerCase();
    return ['xls', 'xlsx'].includes(ext);
  }
  
  downloadAttachment(attachment: any): void {
    this.esgService.getEsgAttachmentById(attachment.esgStorageID)
      .then((res) => {
        const base64Doc = typeof res === "string" ? res : res.result;
        if (!base64Doc) throw new Error("No document found");
  
        const blob = this.base64ToBlob(base64Doc, "application/octet-stream");
        const url = URL.createObjectURL(blob);
  
        const a = document.createElement("a");
        a.href = url;
        a.download = attachment.fileName || "file";
        a.click();
      })
      .catch(() => {
        this.alertService.showToaster("Failed to download document", SETTINGS.TOASTER_MESSAGES.error);
      });
  }
  
}
