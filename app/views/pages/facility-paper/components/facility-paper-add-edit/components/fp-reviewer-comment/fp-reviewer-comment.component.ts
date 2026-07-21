import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {FacilityPaperAddEditService} from "../../../../services/facility-paper-add-edit.service";
import {CommentDialogComponent} from "../../../../../../../shared/components/comment-dialog/comment-dialog.component";

@Component({
  selector: 'app-fp-reviewer-comment',
  templateUrl: './fp-reviewer-comment.component.html',
  styleUrls: ['./fp-reviewer-comment.component.scss']
})
export class FpReviewerCommentComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  tableColumns = ["Name", "Commented On", "Status", "Comment"];
  onReviewerCommentListChange = new Subscription();
  onFacilityPaperChange = new Subscription();
  fpReviewerComments: any[] = [];
  facilityPaper;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  paperReviewStatusConst = Constants.paperReviewStatusConst;
  paperReviewStatus = Constants.paperReviewStatus;

  constructor(private facilityPaperAddEditService: FacilityPaperAddEditService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService) {
  }

  ngOnInit() {
    this.onReviewerCommentListChange = this.facilityPaperAddEditService.onReviewerCommentListChange
      .subscribe((data: any) => {
        if (data) {
          this.fpReviewerComments = [];
          this.fpReviewerComments = data.fpReviewerCommentList;
        }
      });

    this.onFacilityPaperChange = this.facilityPaperAddEditService.onReviewerCommentListChange.subscribe(response => {
      this.facilityPaper = response;
    })
  }

  ngOnDestroy(): void {
    this.onReviewerCommentListChange.unsubscribe();
    this.onFacilityPaperChange.unsubscribe();
  }

  isButtonEnabled() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED
      && this.facilityPaper.paperReviewStatus == this.paperReviewStatusConst.REJECTED
      && this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID();
  }

  openCommentModal(facilityPaper) {

    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityPaper}
      ]
    };

    this.modalRef = this.mdbModalService.show(CommentDialogComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-70-p modal-dialog-scrollable',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "Reply",
        content: {facilityPaper: facilityPaper},
      }
    });

    this.modalRef.content.action.subscribe((response: any) => {
      if (response) {
        let displayName = this.applicationService.getLoggedInUserDisplayName();
        let upmID = this.applicationService.getLoggedInUserUserID() ? this.applicationService.getLoggedInUserUserID() : null;
        let userName = this.applicationService.getLoggedInUser() ? this.applicationService.getLoggedInUser().userName : null;

        let data = Object.assign({},
          {facilityPaperID: this.facilityPaper.facilityPaperID},
          {createdUserDisplayName: displayName},
          {createdUserName: userName},
          {...response},
          {paperReviewStatus: this.paperReviewStatusConst.REPLIED},
          {upmID: upmID}
        );
        this.facilityPaperAddEditService.saveOrUpdateFpReviewerComment(data);
      }
    });

  }


  isEqualLoginAndAssignUser() {
    if (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED;
  }

  isRejected() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.REJECTED;
  }

  getButtonLabel(reviewStatus) {
    switch (reviewStatus) {
      case this.paperReviewStatusConst.REJECTED:
        return 'Reply';
      case this.paperReviewStatusConst.ACTION_REQUIRED:
      case this.paperReviewStatusConst.SAVED:
      case this.paperReviewStatusConst.REPLIED:
        return 'Review Pending';
      case this.paperReviewStatusConst.APPROVED:
        return 'Review Approved';
    }
  }

  getContent(data) {
    return `<pre class="custom-pre-tag">${data || '-'}</pre>`;
  }

}
