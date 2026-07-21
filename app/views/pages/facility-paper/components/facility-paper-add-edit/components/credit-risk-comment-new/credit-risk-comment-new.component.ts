import { Component, Input, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { RiskOpinionHistoryComponent } from "src/app/views/pages/risk-opinion-history/risk-opinion-history.component";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AppUtils } from "src/app/shared/app.utils";
import { CommonPopupWithTinyMceEditorComponent } from "src/app/shared/components/common-popup-with-tiny-mce-editor/common-popup-with-tiny-mce-editor.component";
import { RiskOpinionReplyViewerComponent } from "src/app/shared/components/risk-opinion-reply-viewer/risk-opinion-reply-viewer.component";
import * as _ from "lodash";
import { GenerateCrcDialogComponent } from "src/app/shared/components/generate-crc-dialog/generate-crc-dialog.component";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: "app-credit-risk-comment-new",
  templateUrl: "./credit-risk-comment-new.component.html",
  styleUrls: ["./credit-risk-comment-new.component.scss"],
})
export class CreditRiskCommentNewComponent implements OnInit, OnDestroy {
  @ViewChild("model,", { static: false }) model: RiskOpinionHistoryComponent;

  @Input("facilityPaper") facilityPaper: any = {};
  modalRef: MDBModalRef;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  selectedTabIndex: any = 0;

  currentRiskOpinionReplyTabIndex = 0;
  previousRiskOpinionReplyTabIndex = 1;
  historyTabIndex = 2;

  listType = "fpCreditRiskComment";
  activeRiskComment;
  creditRiskReply;

  createdUserName;
  createdDateStr;

  modifiedUserName;
  modifiedDateStr;

  createdRiskReplyUserName;
  createdDate;

  modifiedRiskReplyUserName;
  modifiedDate;

  divCode;

  remarkList = [];
  previousLockedComments = [];
  casBranchDepartments = [];
  riskDepartment: any = {};
  isLoading = false;
  isLoad: boolean = false;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  onCreditRiskCommentListChange = new Subscription();
  onSaveOrUpdateFpCreditRiskCommentListChange = new Subscription();
  onAddEditCreditRiskReplyChange = new Subscription();
  onRefreshFacilityPaperByID = new Subscription();

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private cacheService: CacheService
  ) {}

  ngOnInit() {
    this.casBranchDepartments = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST
    );
    this.riskDepartment = AppUtils.getCasBranchDepartment(
      this.casBranchDepartments,
      SETTINGS.BRANCH_DEPARTMENT_LIST.RISK_MANAGEMENT_AND_COMPLIENCE
    );

    this.onRefreshFacilityPaperByID =
      this.facilityPaperAddEditService.onRefreshFacilityPaperByID.subscribe(
        (data: any) => {
          if (data) {
            if (!_.isEmpty(data)) {
              this.previousLockedComments =
                data.fpCreditRiskCommentFilterDTO.previousLockedComments;
              this.activeRiskComment =
                data.fpCreditRiskCommentFilterDTO.activeRiskComment;
              if (
                !_.isEmpty(data.fpCreditRiskCommentFilterDTO.activeRiskComment)
              ) {
                this.creditRiskReply =
                  data.fpCreditRiskCommentFilterDTO.activeRiskComment.fpCreditRiskReplyDTO;
              } else {
                this.creditRiskReply = null;
              }
              this.isLoad = data.fpCreditRiskCommentFilterDTO.load;
              this.isLoading = data.fpCreditRiskCommentFilterDTO.loadHistory;

              if (this.previousLockedComments.length > 0) {
                this.currentRiskOpinionReplyTabIndex = 0;
                this.previousRiskOpinionReplyTabIndex = 1;
                this.historyTabIndex = 2;
              } else {
                this.currentRiskOpinionReplyTabIndex = 0;
                this.historyTabIndex = 1;
              }
            }
          }
        }
      );

    if (this.isEqualLoginAndAssignUser()) {
      this.onAddEditCreditRiskReplyChange =
        this.facilityPaperAddEditService.onAddEditCreditRiskReplyChange.subscribe(
          (data: any) => {
            if (!_.isEmpty(data)) {
              this.previousLockedComments =
                data.fpCreditRiskCommentFilterDTO.previousLockedComments;
              this.activeRiskComment =
                data.fpCreditRiskCommentFilterDTO.activeRiskComment;
              if (
                !_.isEmpty(data.fpCreditRiskCommentFilterDTO.activeRiskComment)
              ) {
                this.creditRiskReply =
                  data.fpCreditRiskCommentFilterDTO.activeRiskComment.fpCreditRiskReplyDTO;
              } else {
                this.creditRiskReply = null;
              }
              this.isLoad = data.fpCreditRiskCommentFilterDTO.load;
              this.isLoading = data.fpCreditRiskCommentFilterDTO.loadHistory;

              if (this.previousLockedComments.length > 0) {
                this.currentRiskOpinionReplyTabIndex = 0;
                this.previousRiskOpinionReplyTabIndex = 1;
                this.historyTabIndex = 2;
              } else {
                this.currentRiskOpinionReplyTabIndex = 0;
                this.historyTabIndex = 1;
              }
            }
          }
        );
    }

    this.onSaveOrUpdateFpCreditRiskCommentListChange =
      this.facilityPaperAddEditService.onSaveOrUpdateFpCreditRiskCommentListChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            if (!_.isEmpty(data.fpCreditRiskCommentFilterDTO)) {
              this.previousLockedComments =
                data.fpCreditRiskCommentFilterDTO.previousLockedComments;
              this.activeRiskComment =
                data.fpCreditRiskCommentFilterDTO.activeRiskComment;

              if (
                !_.isEmpty(data.fpCreditRiskCommentFilterDTO.activeRiskComment)
              ) {
                this.creditRiskReply =
                  data.fpCreditRiskCommentFilterDTO.activeRiskComment.fpCreditRiskReplyDTO;
              } else {
                this.creditRiskReply = null;
              }
              this.isLoad = data.fpCreditRiskCommentFilterDTO.load;
              this.isLoading = data.fpCreditRiskCommentFilterDTO.loadHistory;
            } else {
              this.previousLockedComments = [];
              this.activeRiskComment = null;
              this.creditRiskReply = null;
            }

            if (this.previousLockedComments.length > 0) {
              this.currentRiskOpinionReplyTabIndex = 0;
              this.previousRiskOpinionReplyTabIndex = 1;
              this.historyTabIndex = 2;
            } else {
              this.currentRiskOpinionReplyTabIndex = 0;
              this.historyTabIndex = 1;
            }
          }
        }
      );

    this.onCreditRiskCommentListChange =
      this.facilityPaperAddEditService.onCreditRiskCommentListChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            if (!_.isEmpty(data.fpCreditRiskCommentFilterDTO)) {
              this.previousLockedComments =
                data.fpCreditRiskCommentFilterDTO.previousLockedComments;
              this.activeRiskComment =
                data.fpCreditRiskCommentFilterDTO.activeRiskComment;

              if (
                !_.isEmpty(data.fpCreditRiskCommentFilterDTO.activeRiskComment)
              ) {
                this.creditRiskReply =
                  data.fpCreditRiskCommentFilterDTO.activeRiskComment.fpCreditRiskReplyDTO;
              } else {
                this.creditRiskReply = null;
              }
              this.isLoad = data.fpCreditRiskCommentFilterDTO.load;
              this.isLoading = data.fpCreditRiskCommentFilterDTO.loadHistory;
            } else {
              this.previousLockedComments = [];
              this.activeRiskComment = null;
              this.creditRiskReply = null;
            }

            if (this.previousLockedComments.length > 0) {
              this.currentRiskOpinionReplyTabIndex = 0;
              this.previousRiskOpinionReplyTabIndex = 1;
              this.historyTabIndex = 2;
            } else {
              this.currentRiskOpinionReplyTabIndex = 0;
              this.historyTabIndex = 1;
            }
          }
        }
      );

    this.facilityPaperAddEditService.refreshFacilityPaperByID();
  }

  ngOnDestroy(): void {
    this.onCreditRiskCommentListChange.unsubscribe();
    this.onSaveOrUpdateFpCreditRiskCommentListChange.unsubscribe();
    this.onAddEditCreditRiskReplyChange.unsubscribe();
  }

  openAddCommentModal($event, riskComment, content?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      CommonPopupWithTinyMceEditorComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-lg",
        containerClass: "",
        animated: false,
        data: {
          isSaveAndCloseEnabled: true,
          content: {
            header: "Risk Opinion",
            dataToEdit: content ? content : "",
            facilityPaper: this.facilityPaper,
          },
        },
      }
    );

    this.modalRef.content.action.subscribe((commentContent: any) => {
      const parser = new DOMParser();
      const html = parser.parseFromString(commentContent, "text/html");

      html.body.querySelectorAll("table").forEach((item: HTMLElement) => {
        item.style.maxWidth = "100%";
        item.style.overflowX = "hidden";
        item.style.margin = "0px";
      });

      commentContent = html.body.innerHTML;

      if (this.activeRiskComment) {
        if (this.activeRiskComment.isLocked) {
          if (this.activeRiskComment.isLocked == Constants.yesNoConst.N) {
            riskComment.fpCreditRiskCommentID =
              this.activeRiskComment.fpCreditRiskCommentID;
          }
        }
      }
      this.saveOrUpdateFpRiskComment(riskComment, commentContent);
    });
  }

  openAddCreditRiskComment($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(GenerateCrcDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-45-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Generate Credit Risk Comment Template",
        message: "",
      },
    });

    this.modalRef.content.action.subscribe((response: any) => {
      if (response) {
        let paperType = response.paperType;

        let data = Object.assign(
          {},
          {
            facilityPaperID: this.facilityPaper.facilityPaperID,
            userName: this.applicationService.getLoggedInUserDisplayName(),
            upmID: this.applicationService.getLoggedInUserUserID(),
            paperType: paperType,
            fpCreditRiskCommentDTOS: [
              Object.assign(
                {},
                {
                  fpCreditRiskCommentID: null,
                  facilityPaperID: this.facilityPaper.facilityPaperID,
                  upmprivilegeCode:
                    this.applicationService.getLoggedInUserUPMGroupCode(),
                  creditRiskComment: null,
                  commentedBy:
                    this.applicationService.getLoggedInUserDisplayName(),
                  status: Constants.statusConst.ACT,
                  isLocked: "N",
                }
              ),
            ],
          }
        );
        this.facilityPaperAddEditService.addNewFpRiskComment(
          AppUtils.trim(data)
        );
      }
    });
  }

  openAddReplyModal($event, riskReply, content?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      CommonPopupWithTinyMceEditorComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-lg",
        containerClass: "",
        animated: false,
        data: {
          isSaveAndCloseEnabled: true,
          content: {
            header: "Reply For Opinion",
            dataToEdit: content ? content : "",
            facilityPaper: this.facilityPaper,
          },
        },
      }
    );

    this.modalRef.content.action.subscribe((replyContent: any) => {
      if (this.creditRiskReply) {
        riskReply.fpCreditRiskReplyID =
          this.creditRiskReply.fpCreditRiskReplyID;
      }
      this.saveOrUpdateFpRiskReply(riskReply, replyContent);
    });
  }

  view(data) {
    this.modalRef = this.mdbModalService.show(RiskOpinionReplyViewerComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p modal-dialog-scrollable  modal-lg",
      containerClass: "",
      animated: true,
      data: {
        riskComment: data.creditRiskComment,
        riskReply: data.fpCreditRiskReplyDTO
          ? data.fpCreditRiskReplyDTO.replyComment
          : "",

        createdUserName: data.createdUserName,
        createdRiskReplyUserName: data.fpCreditRiskReplyDTO
          ? data.fpCreditRiskReplyDTO.createdUserName
          : "",

        createdDateStr: data.createdDateStr,
        createdDate: data.fpCreditRiskReplyDTO
          ? data.fpCreditRiskReplyDTO.createdDateStr
          : "",

        modifiedUserName: data.modifiedUserName,
        modifiedDateStr: data.modifiedDateStr,

        modifiedRiskReplyUserName: data.fpCreditRiskReplyDTO
          ? data.fpCreditRiskReplyDTO.modifiedUserName
          : "",
        modifiedDate: data.fpCreditRiskReplyDTO
          ? data.fpCreditRiskReplyDTO.modifiedDateStr
          : "",

        divCode: data.fpCreditRiskReplyDTO
          ? data.fpCreditRiskReplyDTO.createdDivCode
          : "",
      },
    });
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

  isAbleToEditRiskComment(data) {
    return (
      data &&
      data.isLocked == Constants.yesNoConst.N &&
      this.riskDepartment.branchDepartmentDivCode ==
        this.applicationService.getLoggedInUserDivCode()
    );
  }

  isAbleToAddReplyOnRiskComment(data) {
    return (
      data.isLocked == Constants.yesNoConst.Y &&
      _.isEmpty(this.creditRiskReply) &&
      !this.isRiskUserLoggedIn()
    );
  }

  isAbleToEditRiskReply(data) {
    return (
      data.createdDivCode == this.applicationService.getLoggedInUserDivCode()
    );
  }

  isRiskUserLoggedIn() {
    return this.riskDepartment.branchDepartmentDivCode
      ? this.riskDepartment.branchDepartmentDivCode ==
          this.applicationService.getLoggedInUserDivCode()
      : false;
  }

  saveOrUpdateFpRiskComment(riskComment, commentContent) {
    let data = Object.assign(
      {},
      {
        facilityPaperID: this.facilityPaper.facilityPaperID,
        userName: this.applicationService.getLoggedInUserDisplayName(),
        upmID: this.applicationService.getLoggedInUserUserID(),
        fpCreditRiskCommentDTOS: [
          Object.assign(
            {},
            {
              fpCreditRiskCommentID: riskComment.fpCreditRiskCommentID,
              facilityPaperID: this.facilityPaper.facilityPaperID,
              upmprivilegeCode:
                this.applicationService.getLoggedInUserUPMGroupCode(),
              creditRiskComment: commentContent,
              commentedBy: this.applicationService.getLoggedInUserDisplayName(),
              status: "ACT",
              isLocked: "N",
            }
          ),
        ],
      }
    );
    this.facilityPaperAddEditService.saveOrUpdateFpRiskComment(
      AppUtils.trim(data)
    );
  }

  saveOrUpdateFpRiskReply(riskReply, replyContent) {
    let data = Object.assign(
      {},
      {
        facilityPaperID: this.facilityPaper.facilityPaperID,
        fpCreditRiskCommentID: this.activeRiskComment.fpCreditRiskCommentID,
        fpCreditRiskReplyID: riskReply.fpCreditRiskReplyID,
        createdUserName: this.applicationService.getLoggedInUserDisplayName(),
        modifiedUserName: this.applicationService.getLoggedInUserDisplayName(),
        createdDivCode: this.applicationService.getLoggedInUserDivCode(),
        replyComment: replyContent,
        status: Constants.statusConst.ACT,
      }
    );
    this.facilityPaperAddEditService.addEditCreditRiskReply(
      AppUtils.trim(data)
    );
  }

  getCommentWithPreTag(comment) {
    return `<pre class="credit-risk-comment-pre-tag">${comment || "-"}</pre>`;
  }

  openDialog() {
    // this.router.navigate(['history']);
    this.modalRef = this.mdbModalService.show(RiskOpinionHistoryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p modal-dialog-scrollable  modal-lg",
      containerClass: "",
      animated: true,
      // data: {
      //   riskComment: data.creditRiskComment,
      //   riskReply: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.replyComment : '',
      // }
    });
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  printCreditRiskComment() {
    if (this.activeRiskComment) {
      const printWindow = window.open("", "_blank");
      printWindow.document.open();
      printWindow.document.write(`
        <html>
        <head>
          <title>Print</title>
        </head>
        <body>
          ${this.activeRiskComment.creditRiskComment}
        </body>
        </html>
      `);
      printWindow.document.close();
      printWindow.print();
      printWindow.close();
    }
  }

  remove(activeRiskComment) {
    if (!_.isEmpty(activeRiskComment)) {
      let data = Object.assign(
        {},
        { fpCreditRiskCommentID: activeRiskComment.fpCreditRiskCommentID },
        { facilityPaperID: activeRiskComment.facilityPaperID },
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
          heading: "Confirm delete Template",
          message: "Do you want to delete this template ?",
        },
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.facilityPaperAddEditService.deactivateFpCreditRiskComment(data);
        }
      });
    }
  }
}
