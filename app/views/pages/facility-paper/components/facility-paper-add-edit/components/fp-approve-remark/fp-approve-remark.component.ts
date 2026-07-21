import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Subscription } from "rxjs/Rx";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../core/setting/constants";
import { CacheService } from "../../../../../../../core/service/data/cache.service";
import { CommentWithViewOptionsDialogComponent } from "../../../../../../../shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import { AppUtils } from "../../../../../../../shared/app.utils";
import { PrivilegeService } from "src/app/core/service/authentication/privilege.service";
import { MasterDataService } from "src/app/core/service/data/master-data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { CommentDialogComponent } from "src/app/shared/components/comment-dialog/comment-dialog.component";

@Component({
  selector: "app-fp-approve-remark",
  templateUrl: "./fp-approve-remark.component.html",
  styleUrls: ["./fp-approve-remark.component.scss"],
})
export class FpApproveRemarkComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  @Input("facilityPaper") facilityPaper: any = {};

  onFPCommentsChangeSub: Subscription = new Subscription();
  onUpmGroupChangeSub = new Subscription();
  comments: any = [];
  allBanksDetails = [];
  userUPMGroups = [];

  tableColumns = ["User", "Date", "Action", "Comment"];
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  hasPrivilegeToViewBCCPapers: boolean = false;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  isLegalOfficer: boolean = false;

  constructor(
    private mdbModalService: MDBModalService,
    private cacheService: CacheService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
  ) {}

  ngOnInit() {
    this.allBanksDetails = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES,
    );

    this.hasPrivilegeToViewBCCPapers = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_VIEW_BCC_PAPER,
    );

    this.isLegalOfficer =
      this.applicationService.getLoggedInUserUPMGroupCode() &&
      this.applicationService.getLoggedInUserUPMGroupCode() ==
        Constants.committeeSignatureUsers.LO;

    this.onFPCommentsChangeSub =
      this.facilityPaperAddEditService.onFPCommentsChange.subscribe(
        (data: any) => {
          this.comments = [];
          this.comments = data.fpCommentDTOList;
          this.facilityPaper.fpCommentDTOList = this.comments;
        },
      );

    this.onUpmGroupChangeSub =
      this.facilityPaperAddEditService.onUpmGroupChange.subscribe(
        (upmGroupList: any) => {
          this.userUPMGroups = [];
          this.userUPMGroups = upmGroupList;
        },
      );
  }

  ngOnDestroy(): void {
    this.onFPCommentsChangeSub.unsubscribe();
  }

  saveOrUpdateComment(comment) {
    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          showUsersOnlyOption: false,
          commentCacheKey:
            this.facilityPaper.fpRefNumber +
            this.facilityPaperStatusConst[
              this.facilityPaper.currentFacilityPaperStatus
            ] +
            this.applicationService.getLoggedInUserUserID() +
            "Commenting",
          heading: "Add Comment",
          comment: comment,
        },
      },
    );
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let saveData = {
          fpCommentID: comment ? comment.fpCommentID : null,
          facilityPaperID: this.facilityPaper.facilityPaperID,
          comment: data.comment,
          actionMessage: "Comment on this Facility Paper",
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          isUsersOnly: data.isUsersOnly ? "Y" : " N",
          isDivisionOnly: data.isDivisionOnly ? "Y" : " N",
          isPublic: data.isPublic ? "Y" : " N",
          status: Constants.statusConst.ACT,
          currentFacilityPaperStatus:
            this.facilityPaper.currentFacilityPaperStatus,
        };
        this.facilityPaperAddEditService.addEditComment(
          AppUtils.trim(saveData),
        );
      }
    });
  }

  isAbleToAddEditComment() {
    return (
      this.facilityPaper.currentAssignUserID != null &&
      (this.facilityPaper.currentAssignUserID ==
        this.applicationService.getLoggedInUserUserID() ||
        this.facilityPaper.currentAssignUserID ==
          this.applicationService.getLoggedInCASUserSupervisorUserID()) &&
      (this.facilityPaper.currentFacilityPaperStatus ==
        this.facilityPaperStatusConst.DRAFT ||
        this.facilityPaper.currentFacilityPaperStatus ==
          this.facilityPaperStatusConst.CANCEL ||
        this.facilityPaper.currentFacilityPaperStatus ==
          this.facilityPaperStatusConst.IN_PROGRESS)
    );
  }

  isMDReviewCmnViewEnabled() {
    let upmGroupCode: any =
      this.applicationService.getLoggedInUserUPMGroupCode();
    return [79, 80].includes(parseInt(upmGroupCode));
  }

  isMDReviewCmntAddEnabled() {
    return this.isAllowedPaper() && this.isAllowedUser();
  }

  isAllowedPaper() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.REJECTED
    );
  }

  isAllowedUser() {
    let upmGroupCode: any =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (parseInt(upmGroupCode) === 79) {
      return true;
    }
    if (parseInt(upmGroupCode) === 80) {
      return (
        this.facilityPaper.currentAssignUserID ===
        this.applicationService.getLoggedInUserUserID()
      );
    }
    return false;
  }

  saveOrUpdateMDComment(comment?: any) {
    this.modalRef = this.mdbModalService.show(CommentDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "MD's Review Comment",
      },
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let saveData: any = {
          fpCommentID: comment ? comment.fpCommentID : null,
          facilityPaperId: this.facilityPaper.facilityPaperID,
          comment: data.comment,
        };

        this.facilityPaperAddEditService
          .saveMdAssistanceComment(saveData)
          .then((comments: any[]) => {});
      }
    });
  }
}
