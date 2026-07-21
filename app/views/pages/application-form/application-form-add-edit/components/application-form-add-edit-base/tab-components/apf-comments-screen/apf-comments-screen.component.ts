import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {CommentWithViewOptionsDialogComponent} from "../../../../../../../../shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {DateService} from "../../../../../../../../core/service/application/date.service";

@Component({
  selector: 'app-apf-comments-screen',
  templateUrl: './apf-comments-screen.component.html',
  styleUrls: ['./apf-comments-screen.component.scss']
})
export class ApfCommentsScreenComponent implements OnInit, OnDestroy {

  @Input() applicationForm: any = {};
  modalRef: MDBModalRef;

  onApplicationFormCommentsChangeSub = new Subscription();
  onUpmGroupChange = new Subscription();
  onUpmGroupChangeSub = new Subscription();
  applicationFormActionStatus = Constants.applicationFormActionStatus;

  comments: any = [];
  allBanksDetails = [];
  userUPMGroups = [];

  constructor(private applicationFormAddEditService: ApplicationFormAddEditService,
              private cacheService: CacheService,
              private mdbModalService: MDBModalService,
              private applicationService: ApplicationService,
              private dateService: DateService
  ) {
  }

  ngOnInit() {

    this.allBanksDetails = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);

    this.onApplicationFormCommentsChangeSub = this.applicationFormAddEditService.onAFCommentsChange.subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.applicationForm = res;
        this.comments = [];
        this.comments = res.afCommentDTOList;
      }
    });

    this.onUpmGroupChangeSub = this.applicationFormAddEditService.onUpmGroupChange
      .subscribe((upmGroupList: any) => {
        this.userUPMGroups = [];
        this.userUPMGroups = upmGroupList;
      });
  }


  ngOnDestroy(): void {
    this.onUpmGroupChange.unsubscribe();
  }

  isAbleToAddEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  isAbleToUpdateComment(comment) {
    return this.dateService.isSameOrBeforeDateTime(this.applicationForm.modifiedDateStr, comment.createdDateStr);
  }

  saveOrUpdateComment(comment) {

    this.modalRef = this.mdbModalService.show(CommentWithViewOptionsDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: '',
      animated: true,
      data: {
        showUsersOnlyOption: false,
        heading: "Add Comment",
        commentCacheKey: this.applicationForm.afRefNumber + this.applicationFormActionStatus[status] + this.applicationService.getLoggedInUserUserID() + "Commenting",
        comment: comment
      }
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let saveData = {
          commentID: comment ? comment.commentID : null,
          applicationFormID: this.applicationForm.applicationFormID,
          comment: data.comment,
          actionMessage: 'Comment on this Application Form',
          createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          isUsersOnly: data.isUsersOnly ? 'Y' : ' N',
          isDivisionOnly: data.isDivisionOnly ? 'Y' : ' N',
          isPublic: data.isPublic ? 'Y' : ' N',
          status: Constants.statusConst.ACT,
          currentApplicationFormStatus: this.applicationForm.currentApplicationFormStatus,
        };
        this.applicationFormAddEditService.saveOrUpdateAFComment(AppUtils.trim(saveData));
      }
    });
  }
}
