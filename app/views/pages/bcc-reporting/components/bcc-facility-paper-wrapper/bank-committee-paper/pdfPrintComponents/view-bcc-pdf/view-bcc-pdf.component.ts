import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { BccReportingService } from "../../../../../services/bcc-reporting.service";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { BccUpdateContentComponent } from "../../../../supporting-components/bcc-update-content/bcc-update-content.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { UrlEncodeService } from "../../../../../../../../core/service/application/url-encode.service";
import { BoardCreditCommitteePaperDTO } from "../../../../../dto/bcc-paper-dto";
import { ConfirmationDialogComponent } from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationService } from "src/app/core/service/application/application.service";
import * as _ from "lodash";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AppUtils } from "src/app/shared/app.utils";
import { AddComparableStatementComponent } from "../../../../supporting-components/bcc-update-content/add-comparable-statement/add-comparable-statement.component";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-view-bcc-pdf",
  templateUrl: "./view-bcc-pdf.component.html",
  styleUrls: ["./view-bcc-pdf.component.scss"],
})
export class ViewBccPdfComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperID;

  onBCCPaperChangeSub = new Subscription();
  onBCCPaperChangeUserSub = new Subscription();
  onBCCReportDownloadContentChangeSubs = new Subscription();
  htmlContent: any = "";
  modalRef: MDBModalRef;
  bccPaper: BoardCreditCommitteePaperDTO;
  yesNoConst: any = Constants.yesNoConst;
  isSubmitted: string = "";

  constructor(
    private readonly bccReportingService: BccReportingService,
    private readonly mdbModalService: MDBModalService,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly applicationService: ApplicationService,
    private readonly cacheService: CacheService,
    public mdbModalRef: MDBModalRef,
    private readonly alertService: AlertService
  ) {}

  ngOnInit() {
    this.bccReportingService.getBCCPaperByFacilityPaperByID(
      this.urlEncodeService.decode(this.selectedFacilityPaperID)
    );

    this.onBCCPaperChangeSub =
      this.bccReportingService.onBCCPaperChange.subscribe((data: any) => {
        if (data != null) {
          this.bccPaper = new BoardCreditCommitteePaperDTO(data);
          this.isSubmitted = this.bccPaper.isSubmitted;
          this.htmlContent = data.pdfReport ? data.pdfReport : "";
        }
      });

    this.onBCCPaperChangeUserSub =
      this.bccReportingService.onBCCPaperChangeUser.subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.bccPaper = new BoardCreditCommitteePaperDTO(data);
          this.htmlContent = data.pdfReport ? data.pdfReport : "";
        }
      });
  }

  ngOnDestroy(): void {
    this.onBCCReportDownloadContentChangeSubs.unsubscribe();
    this.onBCCPaperChangeSub.unsubscribe();
    this.onBCCPaperChangeUserSub.unsubscribe();
  }

  update($event: any) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }

    this.modalRef = this.mdbModalService.show(BccUpdateContentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: true,
      data: {
        header: "Update",
        content: {
          dataToEdit: this.htmlContent,
        },
        retortType: this.bccPaper.paperType,
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        this.showWarningMessage(result);
      }
    });
  }

  showWarningMessage(data: any) {
    if (data.missingTopics) {
      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Default Topic Removed !",
          message:
            "Do you want to save without content of " +
            data.missingTopics +
            " ?",
        },
      });
      this.modalRef.content.action.subscribe((result: any) => {
        if (result) {
          this.updateBCCPDFReport(data.pdfReport);
        } else {
          // this.htmlContent = data.pdfReport;
        }
      });
    } else {
      this.updateBCCPDFReport(data.pdfReport);
    }
  }

  updateBCCPDFReport(pdfReport: any) {
    let data = Object.assign({}, this.bccPaper, { pdfReport: pdfReport });
    this.bccReportingService.updateBCCPDFReport(data);
  }

  print(): void {
    let printContents, popupWin;
    printContents = document.getElementById("printContentDiv").innerHTML;
    popupWin = window.open("", "_blank", "top=0,left=0,height=100%,width=auto");
    popupWin.document.open();
    popupWin.document.write(`
      <html>
        <head>
                </head>

    <body onload="window.print()" onafterprint="window.close()">${this.htmlContent}</body>
      </html>`);
    popupWin.document.close();
  }

  onRemoveBccPaper() {
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
        message: "Do you want to Delete this report?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let obj = {
          facilityPaperID: this.bccPaper.facilityPaperID,
          status: Constants.statusConst.INA,
        };
        this.bccReportingService.deactivateBccPaper(obj);
      }
    });
  }

  isEqualLoginAndAssignUser() {
    if (!_.isEmpty(this.bccPaper)) {
      if (
        this.bccPaper.currentAssignUser ==
        this.applicationService.getLoggedInUserUserName()
      ) {
        return true;
      } else {
        return false;
      }
    }
  }

  getAssign($event: any) {
    let heading = "Confirm Assign this Report";
    let displayName: String = null;
    let branch: String = null;

    if (this.bccPaper.currentAssignUserDivCode != null) {
      branch = this.getBranchName(this.bccPaper.currentAssignUserDivCode);
    }
    if (this.bccPaper.assignUserDisplayName != null) {
      displayName = this.bccPaper.assignUserDisplayName
        .toLowerCase()
        .split(" ")
        .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
        .join(" ");
    }

    let message = `This report is ${
      this.bccPaper.assignUserDisplayName ? "" : "not"
    } assigned to ${displayName ? displayName : "any user"} ${
      branch ? "(" + branch + ")" : ""
    }. Do you want to get this assigned ?`;

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
        heading: heading,
        message: message,
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let obj = {
          facilityPaperID: this.bccPaper.facilityPaperID,
          status: Constants.statusConst.ACT,
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
        };
        this.bccReportingService.changeAssignUserBCCPaper(obj);
      }
    });
    this.mdbModalRef.hide();
  }

  getBranchName(branchCode) {
    let allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    let branch = AppUtils.getBranchFromBranchCode(allBankOptions, branchCode);

    if (!_.isEmpty(branch)) {
      return branch.branchName + " - " + branch.branchCode;
    }
    return branchCode;
  }

  addComparableStatemnet() {
    this.modalRef = this.mdbModalService.show(AddComparableStatementComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-95-p",
      containerClass: "",
      animated: true,
      data: {},
    });
    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        let payload: any = {
          boardCreditCommitteePaperID:
            this.bccPaper.boardCreditCommitteePaperID,
          compReportingData: result,
        };

        this.bccReportingService
          .getComparableContent(payload)
          .then((contentResp: any) => {
            if (contentResp) {
              const parser = new DOMParser();
              const html = parser.parseFromString(
                this.htmlContent,
                "text/html"
              );

              html.body
                .querySelectorAll(".comareableSatement")
                .forEach((item: Element) => {
                  item.remove();
                });

              const newElement = document.createElement('div');
              newElement.className = "comareableSatement";
              newElement.style.margin = "20px 0px 0px 0px"
              newElement.innerHTML = contentResp;
              html.body.appendChild(newElement);
              
              this.htmlContent = html.body.innerHTML;

              this.updateBCCPDFReport(this.htmlContent);
              window.scrollTo({
                top: window.innerHeight / 2,
                behavior: "smooth",
              });
            } else {
              this.alertService.showToaster(
                "An error occurred.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          });
      }

      this.modalRef.hide();
    });
  }

  handleSubmission() {
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
        heading: "Confirm Statement Submission",
        message: "Do you want to submit this statement?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.bccReportingService.bCCPaperSubmission(
          this.bccPaper.facilityPaperID
        );
      }
    });
  }
}
