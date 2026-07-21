import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FacilityPaperReviewTemplateService} from "../../../../services/facility-paper-review-template.service";
import {ApplicationService} from "../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../core/setting/constants";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {MasterDataService} from "../../../../../../../core/service/data/master-data.service";
import {FormGroup} from "@angular/forms";
import {FacilityPaperViewService} from "../../../../services/facility-paper-view.service";
import {Router} from "@angular/router";
import {PageSize} from "../../../../../../../core/dto/page.size";
import {Pagination} from "../../../../../../../core/dto/pagination";
import {AlertService} from "../../../../../../../core/service/common/alert.service";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {FpReviewerCommentDto} from "../../../../dtos/fp-reviewer-comment-dto";
import {Location} from "@angular/common";
import {FacilityPaperReviewService} from "../../../../services/facility-paper-review.service";


@Component({
  selector: 'app-reviewer-comments-tab',
  templateUrl: './reviewer-comments-tab.component.html',
  styleUrls: ['./reviewer-comments-tab.component.scss']
})
export class ReviewerCommentsTabComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  tableColumns = ["Name", "Commented On", "Status", "Comment", ''];
  onReviewerCommentListChange = new Subscription();
  onFacilityPaperChange = new Subscription();
  onFacilityPapersChangeSub = new Subscription();
  onOwnApprovedFacilityPaperChangeSub = new Subscription();
  onReviewercommentSaveOrUpdateSub = new Subscription();
  onOwnSavedCommentChange = new Subscription();
  pageSize = new PageSize({
    pageIndex: 0,
    length: 0,
    pageSize: 5,
    pageSizeOptions: [2, 5, 10, 50]
  });

  fpReviewerComments: any[] = [];
  fpReviewCommentsNotInSavedStatus: any[] = [];

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  paperReviewStatusConst = Constants.paperReviewStatusConst;
  paperReviewStatus = Constants.paperReviewStatus;
  statusConst = Constants.statusConst;

  isTinyMCEEnabled: boolean = false;
  hasCommentToSave: boolean = false;
  hasDefaultValueInEditor: boolean = false;
  isTakeActionToOwnAppFP: boolean = false;
  componentForm: FormGroup;
  action: Subject<any> = new Subject<any>();

  facilityPapers = [];
  ownApprovedFacilityPapers = [];
  reviewComment: string;
  ownSavedComment: FpReviewerCommentDto = new FpReviewerCommentDto();
  editorHTML = '';
  facilityPaper;
  header: any = '';

  constructor(private facilityPaperReviewTemplateService: FacilityPaperReviewTemplateService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService,
              private masterDataService: MasterDataService,
              private facilityPaperViewService: FacilityPaperViewService,
              private router: Router,
              private alertService: AlertService,
              private facilityPaperReviewService: FacilityPaperReviewService
  ) {
  }

  ngOnInit() {

    this.onReviewerCommentListChange = this.facilityPaperReviewTemplateService.onReviewerCommentListChange
      .subscribe((data: any) => {
        if (data) {
          this.fpReviewCommentsNotInSavedStatus = [];
          this.fpReviewCommentsNotInSavedStatus = data.pageData;
          this.pageSize.length = data.totalNoOfRecords;
          this.pageSize.pageIndex = data.currentPageNo - 1;
        }
      });

    this.onOwnSavedCommentChange = this.facilityPaperReviewTemplateService.onOwnSavedReviewerCommentChagne
      .subscribe((data: FpReviewerCommentDto) => {
        if (data) {
          this.ownSavedComment = data;
          this.editorHTML = this.ownSavedComment.comment;
        } else {
          this.ownSavedComment = new FpReviewerCommentDto();
          this.editorHTML = '';
        }
      });

    this.onFacilityPaperChange = this.facilityPaperReviewTemplateService.onFacilityPaperChange.subscribe(response => {
      this.facilityPaper = response;
      if (this.facilityPaper.needToBackRouting) {
        this.checkAndRoute();
      }
    });

    this.onOwnApprovedFacilityPaperChangeSub = this.facilityPaperReviewService.onOwnApprovedFPChange.subscribe(response => {
      if (response.pageData) {
        this.ownApprovedFacilityPapers = response.pageData;
      }
    });

    this.onFacilityPapersChangeSub = this.facilityPaperViewService.onFacilityPapersChangeSub.subscribe(response => {
      if (response.pageData) {
        this.facilityPapers = response.pageData;
      }
    });

    this.onReviewercommentSaveOrUpdateSub = this.facilityPaperReviewTemplateService.onReviewerCommentSaveOrUpdate.subscribe((data: FpReviewerCommentDto) => {
      this.ownSavedComment = data;
      this.editorHTML = this.ownSavedComment.comment;
    });

    this.isTinyMCEEnabled = this.masterDataService.getSystemParameter(Constants.systemParamKey.TINYMCE_ENABLED);

  }

  ngOnDestroy(): void {
    this.onReviewerCommentListChange.unsubscribe();
    this.onFacilityPaperChange.unsubscribe();
    this.onReviewercommentSaveOrUpdateSub.unsubscribe();
    this.onOwnSavedCommentChange.unsubscribe();
    this.onOwnApprovedFacilityPaperChangeSub.unsubscribe();
  }

  //this no need
  /*
    isButtonEnabled() {
      return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED
        && (this.facilityPaper.paperReviewStatus == this.paperReviewStatusConst.SAVED
          || this.facilityPaper.paperReviewStatus == this.paperReviewStatusConst.ACTION_REQUIRED
          || (this.facilityPaper.paperReviewStatus == this.paperReviewStatusConst.REPLIED && this.facilityPaper.reviewUserName == this.applicationService.getLoggedInUserUserName()))
    }
  */

  /*
    openCommentModal(facilityPaper) {

      const initialState = {
        list: [
          {"tag": 'Count', "value": facilityPaper}
        ]
      };

      this.modalRef = this.mdbModalService.show(ReviewerCommentDialogComponent, {
        initialState,
        backdrop: true,
        keyboarofd: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-70-p modal-dialog-scrollable',
        containerClass: 'right',
        animated: false,
        data: {
          heading: "Add Comment",
          content: {facilityPaper: facilityPaper},
        }
      });

      this.modalRef.content.action.subscribe((response: any) => {
          if (response) {
          let displayName = this.applicationService.getLoggedInUserDisplayName();
          let upmID = this.applicationService.getLoggedInUserUserID() ? this.applicationService.getLoggedInUserUserID() : null;
          let userName = this.applicationService.getLoggedInUser() ? this.applicationService.getLoggedInUser().usoferName : null;

          let data = Object.assign({},
            {facilityPaperID: this.facilityPaper.facilityPaperID},
            {createdUserDisplayName: displayName},
            {createdUserName: userName},
            {...response},
            {upmID: upmID}
          );
                    +// this.facilityPaperReviewTemplateService.approveOrRejectFaciliptyPaper(data);
        }
      });
    }
  */

  isUserActionEnable() {
    switch (this.facilityPaper.paperReviewStatus) {
      case this.paperReviewStatusConst.ACTION_REQUIRED:
        return this.facilityPaper.currentAssignUserID != this.applicationService.getLoggedInUserUserID();
      case this.paperReviewStatusConst.REJECTED:
        return this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID();
      case this.paperReviewStatusConst.REPLIED:
        return this.facilityPaper.reviewUserID == this.applicationService.getLoggedInUserUserID();
      case this.paperReviewStatusConst.APPROVED:
        return false;
      case this.paperReviewStatusConst.SAVED:
        return this.facilityPaper.reviewUserID == this.applicationService.getLoggedInUserUserID();
      default:
        return false;
    }
  }

  isOwnApprovedFacilityPaper(): boolean {
    return this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID();
  }

  clickBtnSave(): void {
    if (this.hasCommentToSave) {
      let data: FpReviewerCommentDto;
      data = this.getReviwerCommentData();
      data.paperReviewStatus = this.paperReviewStatusConst.SAVED;
      this.facilityPaperReviewTemplateService.saveOrUpdateReviewerComment(data);
    } else {
      this.alertService.showToaster("Please add a comment to save", SETTINGS.TOASTER_MESSAGES.error);
    }

  }

  clickBtnReject(): void {
    if (this.hasCommentToSave) {
      let data: FpReviewerCommentDto;
      data = this.getReviwerCommentData();
      data.paperReviewStatus = this.paperReviewStatusConst.REJECTED;
      this.facilityPaperReviewTemplateService.approveOrRejectFaciliptyPaper(data);
    } else {
      this.alertService.showToaster("Please add a comment to reject", SETTINGS.TOASTER_MESSAGES.error);
    }
  }

  clickBtnApprove(): void {
    if (this.hasCommentToSave) {
      let data: FpReviewerCommentDto;
      data = this.getReviwerCommentData();
      data.paperReviewStatus = this.paperReviewStatusConst.APPROVED;
      this.facilityPaperReviewTemplateService.approveOrRejectFaciliptyPaper(data);
    } else {
      this.alertService.showToaster("Please add a comment to approve", SETTINGS.TOASTER_MESSAGES.error);
    }
  }

  clickBtnReplied() {
    if (this.hasCommentToSave) {
      let data: FpReviewerCommentDto;
      data = this.getReviwerCommentData();
      data.paperReviewStatus = this.paperReviewStatusConst.REPLIED;
      this.isTakeActionToOwnAppFP = true;
      this.facilityPaperReviewTemplateService.approveOrRejectFaciliptyPaper(data);
    } else {
      this.alertService.showToaster("Please add a comment to reply", SETTINGS.TOASTER_MESSAGES.error);
    }
  }

  onEditorTyping(event: any) {
    this.reviewComment = event;
    this.hasCommentToSave = !!this.reviewComment;
  }

  truncateString(str, num) {
    if (str.length <= num) {
      return str;
    }
    return str.slice(0, num) + '...';
  }

  getContent(data) {
    return `<pre class="custom-pre-tag">${data || '-'}</pre>`;
  }

  checkAndRoute() {
    if (this.isTakeActionToOwnAppFP) {
      this.router.navigateByUrl('/facility-review', {replaceUrl: true});
    } else {
      this.backRouting(this.facilityPapers);
    }
  }

  backRouting(papers) {
    if (papers.length <= 1) {
      // this.router.navigate(['/facility-review'], {replaceUrl: true});
      this.router.navigateByUrl('/facility-review', {replaceUrl: true});
    } else {
      // this.router.navigateByUrl('/facility-review/paper-review', {replaceUrl: true});
      this.router.navigateByUrl('/facility-review/paper-review', {replaceUrl: true});
    }

  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperReviewTemplateService.getPagedReviewComment({}, new Pagination(event));
  }

  onEditorHasDefaultVal(event: any) {
    this.hasDefaultValueInEditor = event;
  }

  getReviwerCommentData() {

    let data: FpReviewerCommentDto;
    if (this.ownSavedComment.fpReviewerCommentID) {
      data = this.ownSavedComment;
      data.comment = this.reviewComment;
    } else {
      data = new FpReviewerCommentDto();
      data.comment = this.reviewComment;
      data.status = this.statusConst.ACT;
      data.facilityPaperID = this.facilityPaper.facilityPaperID;
      data.upmID = this.applicationService.getLoggedInUserUserID();
      data.createdUserUpmCode = this.applicationService.getLoggedInUserUPMGroupCode();
      data.createdUserDivCode = this.applicationService.getLoggedInUserDivCode();
      data.createdUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
    }
    return data;
  }

}
