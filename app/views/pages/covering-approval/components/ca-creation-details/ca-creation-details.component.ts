import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { NavigationStart, Router } from '@angular/router';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { Constants } from 'src/app/core/setting/constants';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { CommonForwardComponent } from 'src/app/shared/components/common-forward/common-forward.component';
import { Subject, Subscription } from 'rxjs';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import * as _ from 'lodash';
import { AppUtils } from 'src/app/shared/app.utils';
import { LocalStorage } from 'ngx-webstorage';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Component({
  selector: 'app-ca-creation-details',
  templateUrl: './ca-creation-details.component.html',
  styleUrls: ['./ca-creation-details.component.scss']
})
export class CaCreationDetailsComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;
  //@Input() coveringApprovals: any;

  action: Subject<any> = new Subject<any>();
  covStatusConst = Constants.covCurrentStatusConst;
  covStatusChangeHeading = Constants.covStatusChangeHeading;
  covActionStatus = Constants.covActionStatus;
  componentFormCurrentStatusConst = Constants.componentFormCurrentStatusConst;
  details: any;
  commonDetails: any;
  traninquiry: any;
  tranDate: string;
  customerDetails: any;
  relatedDetails: any;
  addressDataCom: any;
  componentForm: FormGroup;
  coveringApprovals: any = {};
  onCovFormChangeSub = new Subscription();
  selectedTabIndex: any = 0;
  aboutTabIndex = 0;
  commentTabIndex = 1;

  commentDetails: any;
  basicDetails: any;

  private routerEventsSubscription: Subscription;
  private isNavigationInProgress: boolean = false;

  modalRef: MDBModalRef;
  covFormInitData: any;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private coveringApprovalSharedService: CoveringApprovalSharedService,
    private mdbModalService: MDBModalService,
    public applicationService: ApplicationService,
    private coveringApprovalService: CoveringApprovalService
  ) {
    this.componentForm = this.fb.group({
      assignBMRMAM: new FormControl('')
    });

  }


  ngOnInit() {

    this.covFormInitData = this.coveringApprovalSharedService.getCovFormInitData();
    this.coveringApprovalService.draftApplicationForm(this.covFormInitData);

    this.onCovFormChangeSub = this.coveringApprovalService.onCoveringApprovalChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.coveringApprovals = data;
        this.basicDetails = this.coveringApprovals.covAppBasicInfoDTOList[0];
        this.commentDetails = this.coveringApprovals.covAppCommentDTOList[0];
      }

    })
    this.routerEventsSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.isNavigationInProgress = true;
      }
    });

    // Handle beforeunload event to detect page refresh
    window.addEventListener('beforeunload', this.handleBeforeUnload.bind(this));

  }
  ngOnDestroy(): void {
    window.removeEventListener('beforeunload', this.handleBeforeUnload.bind(this));

    // Unsubscribe from router events
    if (this.routerEventsSubscription) {
      this.routerEventsSubscription.unsubscribe();
    }

    // Remove data from local storage if navigation is in progress
    if (this.isNavigationInProgress) {
      this.coveringApprovalSharedService.clearCovFormInitData();
    }
  }
  handleBeforeUnload(event: Event) {
    // If navigation is not in progress, retain the data

  }

  async changeStatusCOV($event, status) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let returnUserList: any;
    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: '',
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading: `${this.covStatusChangeHeading[status]}` + " Covering Approval",
        actionMessage: `${this.covStatusChangeHeading[status]}`,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.RETURNED,
        commentCacheKey: this.coveringApprovals.covAppRefNumber + this.covActionStatus[status] + this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: this.coveringApprovals.branchCode,
          createdUser: this.coveringApprovals.createdBy,
          currentAssignUser: this.coveringApprovals.assignUser,
          //workflowTemplateID: this.coveringApprovalForm.workflowTemplateID,
          relatedDivCodes: [this.coveringApprovals.branchCode]
        }
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let covStatusChangeRQ = {
          // covAppId: this.coveringApprovalForm.covAppId,
          // covAppRefNumber: this.coveringApprovalForm.covAppRefNumber,
          // assignDepartmentCode:data.assignDepartmentCode,
          // updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName()
          covAppId: this.coveringApprovals.covAppId,
          currentStatus: "IN_PROGRESS",
          currentAssignUserId: data.assignedUser.userID,
          covAppRefNumber: this.coveringApprovals.covAppRefNumber,
          actionMessage: data.actionMessage,
          forwardType: data.forwardType,
          covAppCommentDTO:
          {
            userComment: data.remarkData.comment,
            createdUserID: data.remarkData.createdUserID,
            createdUser: data.remarkData.createdUser,
            createdUserDisplayName: data.remarkData.createdUserDisplayName,
            createdUserDivCode: data.remarkData.createdUserDivCode,
            createdUserUpmCode: data.remarkData.createdUserUpmCode,
            isUsersOnly: "N",
            isDivisionOnly: "N",
            isPublic: "Y"
          }


        };
        if (data.assignedUser) {
          covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
            assignUserDisplayName: data.assignedUser.assignUserDisplayName,
            currentAssignUserAD: data.assignedUser.userID,
            currentAssignUserDivCode: data.assignedUser.divCode,
            assignUserUpmId: data.assignedUser.userID,
            assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
            currentAssignUser: data.assignedUser.assignUserDisplayName
          });
        } else {
          covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
            assignUserID: null,
            assignADUserID: null,
            assignUser: null,
            assignUserDisplayName: null,
            assignUserUpmID: null,
            assignedUserDivCode: null,
            assignUserUpmGroupCode: null,
          })
        }

        this.coveringApprovalService.updateCOVStatus(AppUtils.trim(covStatusChangeRQ)).subscribe((res: any) => {
          this.router.navigate(['/covering-approval/dashboard']);
        })
      }

    });

  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }
  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

}
