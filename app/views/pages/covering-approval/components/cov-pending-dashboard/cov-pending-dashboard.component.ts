import { Component, Input, OnInit } from '@angular/core';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { AppUtils } from 'src/app/shared/app.utils';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import { Pagination } from 'src/app/core/dto/pagination';
import { Constants } from 'src/app/core/setting/constants';
import { CommonForwardComponent } from 'src/app/shared/components/common-forward/common-forward.component';
import { Router } from '@angular/router';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { CommentWithViewOptionsDialogComponent } from 'src/app/shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component';
import { Subscription } from 'rxjs';
import * as _ from 'lodash';
import { LocalStorage } from 'ngx-webstorage';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { PageSize } from 'src/app/core/dto/page.size';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { CaDetailsCommentComponent } from '../ca-details-comment/ca-details-comment.component';
import { CovPendingCommentComponent } from '../cov-pending-comment/cov-pending-comment.component';
import { CovReturnUserSelectionComponent } from '../cov-return-user-selection/cov-return-user-selection.component';

@Component({
  selector: 'app-cov-pending-dashboard',
  templateUrl: './cov-pending-dashboard.component.html',
  styleUrls: ['./cov-pending-dashboard.component.scss']
})
export class CovPendingDashboardComponent implements OnInit {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;

  tableColumns: any = ['Date', 'Ref No', 'Transaction Date', 'Branch', 'Cheque Number', 'Transaction Amount', 'Status', 'Balance as at Initiation', 'EOD balance of Transaction Date', 'Comments'];
  details: any = {};
  @Input("coveringApproval") coveringApproval: any;

  pageSize = new PageSize();

  totalRecords = 0;
  covStatusConst = Constants.covCurrentStatusConst;
  covStatusChangeHeading = Constants.covStatusChangeHeading;
  modalRef: MDBModalRef;
  transactionDetails: any;
  //commentDetails: any;
  basicInfoDetails: any;
  covAppStatusHistory: any;
  covActionStatus = Constants.covActionStatus;
  selectAll: boolean = false;
  userDa: any = {};
  coveringApprovalStatusConst = Constants.coveringApprovalStatusConst;
  coveringApprovalForm: any = [];
  coveringApprovalFormPage: any = [];
  selectedRecords = [];
  isReturnButtonEnabled = false;
  onCOVFormChangeSub = new Subscription();
  onCoveringApprovalChangeSub = new Subscription();
  onCoveringApprovalChangesSub = new Subscription();
  disableApproveButton: boolean = false;

  accessLevelOfCurrentAssignUser: any;

  covForm: any = {};



  constructor(
    private applicationService: ApplicationService,
    private coveringApprovalService: CoveringApprovalService,
    private router: Router,
    private mdbModalService: MDBModalService,
    private datePipe: DatePipe,
    private alertService: AlertService,
    private currencyPipe: CurrencyPipe,

  ) { }

  ngOnInit() {
    let testAccountNumber = Object.assign({
      accountNumber: this.coveringApproval.accountNumber
    });

    if (this.isEqualLoginAndAssignUser()) {
      this.coveringApprovalService.getPendingCoveringApprovals(testAccountNumber).then(data => {
        // Get the latest covering approvals sorted by modifiedDate
        this.coveringApprovalForm = data
          .sort((a, b) => new Date(b.modifiedDate).getTime() - new Date(a.modifiedDate).getTime())
          .map(item => {
            // Create a unique, sorted list of createdUserIds from covAppCommentDTOList
            const useridList = Array.from(
              new Set(
                item.covAppCommentDTOList
                  .map(comment => comment.createdUserId)
                  .filter(id => id !== null) // Filter out any null IDs
              )
            ).sort((a, b) => Number(a) - Number(b)); // Sort in ascending order

            // Add the unique, sorted user ID list as a hidden field in each item
            return {
              ...item,
              useridList // Add new field to item object
            };
          });

        this.totalRecords = this.coveringApprovalForm.length;
      });
    }

    // Fetch user data for the logged-in user
    this.coveringApprovalService.getUserDaByUserName(this.applicationService.getLoggedInUserUserName());

    // Subscribe to changes in logged user data
    this.onCOVFormChangeSub = this.coveringApprovalService.onChangeLoggedUserName.subscribe(
      (data: any) => {
        if (data != null) {
          this.userDa = data;
        }
      }
    );
  }


  // Checks if all selected records have the same useridList
  checkUseridListConsistency(): boolean {
    if (this.selectedRecords.length < 2) {
      return true; // No need to compare if fewer than two records are selected
    }

    const firstUseridList = JSON.stringify(this.selectedRecords[0].useridList);

    return this.selectedRecords.every(
      record => JSON.stringify(record.useridList) === firstUseridList
    );
  }

  // Handles "Return" button click
  handleReturnClick(event: Event) {
    if (!this.isReturnButtonEnabled) {
      this.alertService.showToaster("User ID lists do not match for selected records", SETTINGS.TOASTER_MESSAGES.error);
      event.preventDefault();
    } else {
      this.changeStatusCOVSelectedReturn(event, this.covStatusConst.CANCEL);
    }
  }

  isAuthorized() {
    this.accessLevelOfCurrentAssignUser = this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser > '70') {
      return true;
    }

    // If the user is not in group 70, check the isAuthorized condition for selected records
    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);

    // Check if all selected records have isAuthorized set to true
    const allAuthorized = selectedDetails.every(detail => detail.isAuthorized === true);
    return allAuthorized;

  }

  selectAllRecords() {
    this.coveringApprovalForm.forEach(detail => detail.selected = this.selectAll);
    this.onSelectionChange();
  }

  onSelectionChange() {
    this.selectedRecords = this.coveringApprovalForm.filter(item => item.selected);
    this.isReturnButtonEnabled = this.checkUseridListConsistency();

    this.calculateSumOfSelectedTranAmount();

    this.accessLevelOfCurrentAssignUser = this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= '50') {
      this.disableApproveButton = false;
      return;
    }

    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);

    const allAuthorized = selectedDetails.every(detail => detail.isAuthorized === true);

    this.disableApproveButton = !allAuthorized;
  }



  calculateSumOfSelectedTranAmount() {
    const sum = this.coveringApprovalForm
      .filter(detail => detail.selected)
      .reduce((acc, curr) => acc + parseFloat(curr.covAppBasicInfoDTOList[0].tranAmount || '0'), 0);

    this.disableApproveButton = sum > this.userDa.maxAmount;

  }

  //forward
  async changeStatusCOVSelectedForward($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let returnUserList: any;
    let returnUser: any;
    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);
    if (selectedDetails.length === 0) {
      this.alertService.showToaster("No records selected", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }

    if (status === this.covStatusConst.CANCEL) {
      returnUserList = await this.coveringApprovalService.getCOVReturnUsersList(selectedDetails[0].covAppId);

    }

    const covAppRefNumbers = Array.from(new Set(selectedDetails.map(detail => detail.covAppRefNumber))).join(',');
    const branchCode = Array.from(new Set(selectedDetails.map(detail => detail.branchCode))).join(',');

    let heading = `${this.covStatusChangeHeading[status]} Covering Approval`;
    let actionMessage = `${this.covStatusChangeHeading[status]}`;
    let buttonLabel = "Cancel";


    if (status === this.covStatusConst.CANCEL) {
      heading = "Return Covering Approval";
      actionMessage = "Return";
      buttonLabel = "Return";

    }

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
        heading: heading,
        actionMessage: actionMessage,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.CANCEL,
        commentCacheKey: covAppRefNumbers + this.covActionStatus[status] + this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: [],
          branchCode: branchCode,
          // createdUser: createdUser,
          // currentAssignUser: currentAssignUser,
          relatedDivCodes: [branchCode],
          //covAppId:covAppId,
          buttonLabel: buttonLabel
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        if (status === this.covStatusConst.CANCEL) {
          heading = "Return Covering Approval";
          actionMessage = `Return to ${data.assignedUser.assignUserDisplayName}`;
          buttonLabel = "Return";
        }
        else {
          actionMessage = `Forward to ${data.assignedUser.assignUserDisplayName}`;
        }
        selectedDetails.forEach(detail => {
          let covStatusChangeRQ = {
            covAppId: detail.covAppId,
            currentStatus: status,
            currentAssignUserId: data.assignedUser.userID,
            covAppRefNumber: detail.covAppRefNumber,
            actionMessage: actionMessage,
            forwardType: data.forwardType,
            isAuthorized: false,
            covAppCommentDTO: {
              userComment: data.remarkData.comment,
              createdUserId: data.remarkData.createdUserID,
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
              currentAssignUserAD: data.assignedUser.adUserID,
              currentAssignUserDivCode: data.assignedUser.divCode,
              assignUserUpmId: data.assignedUser.userID,
              assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
              currentAssignUser: data.assignedUser.adUserID,
              updatedUserId: this.applicationService.getLoggedInUserUserID()
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
            });
          }
          this.coveringApprovalService.updateCOVStatus(AppUtils.trim(covStatusChangeRQ)).subscribe((res: any) => {
            this.router.navigate(['/covering-approval/dashboard']);
          });
        });

        
      }
    });
  }

  //return 
  async changeStatusCOVSelectedReturn($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let returnUserList: any;
    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);
    if (selectedDetails.length === 0) {
      this.alertService.showToaster("No records selected", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }

    if (status === this.covStatusConst.CANCEL) {
      let response: any;
      response = await this.coveringApprovalService.getCOVReturnUsersList(selectedDetails[0].covAppId);

      if (response) {
        returnUserList = response || [];
      }
    }

    let heading = `${this.covStatusChangeHeading[status]} Covering Approval`;
    let actionMessage = `${this.covStatusChangeHeading[status]}`;
    let buttonLabel = "Cancel";

    if (status === this.covStatusConst.CANCEL) {
      heading = "Return Covering Approval";
      actionMessage = "Return";
      buttonLabel = "Return";
    }

    this.modalRef = this.mdbModalService.show(CovReturnUserSelectionComponent, {
      data: {
        users: returnUserList
      },
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
    });


    this.modalRef.content.action.subscribe((selectedData: any) => {
      if (selectedData) {
        // const covAppRefNumbers = Array.from(new Set(selectedDetails.map(detail => detail.covAppRefNumber))).join(',');
        // const branchCode = Array.from(new Set(selectedDetails.map(detail => detail.branchCode))).join(',');
        const selectedUser = selectedData.assignedUser;
        const comment = selectedData.remarkData.comment;
        if (status === this.covStatusConst.CANCEL) {
          actionMessage = `Return to ${selectedUser.assignUserDisplayName}`;

        }
        selectedDetails.forEach(detail => {
          let covStatusChangeRQ = {
            covAppId: detail.covAppId,
            currentStatus: status,
            currentAssignUserId: selectedUser.assignUserID,
            covAppRefNumber: detail.covAppRefNumber,
            actionMessage: actionMessage,
            forwardType: "DIRECT_USER",
            isAuthorized: false,
            covAppCommentDTO: {
              userComment: comment,
              createdUserId: this.applicationService.getLoggedInUserUserID(),
              createdUser: this.applicationService.getLoggedInUserUserName(),
              createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
              createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
              createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
              isUsersOnly: "N",
              isDivisionOnly: "N",
              isPublic: "Y"
            }
          };

          if (selectedData.assignedUser) {
            covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
              assignUserDisplayName: selectedUser.assignUserDisplayName,
              currentAssignUserAD: selectedUser.assignUser,
              currentAssignUserDivCode: selectedUser.assignUserDivCode,
              assignUserUpmId: selectedUser.assignUserUpmID,
              assignUserUpmGroupCode: selectedUser.assignUserUpmGroupCode,
              currentAssignUser: selectedUser.assignUser,
              updatedUserId: this.applicationService.getLoggedInUserUserID()
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
            });
          }
          //console.log("AppUtils.trim(covStatusChangeRQ))",AppUtils.trim(covStatusChangeRQ));
          this.coveringApprovalService.updateCOVStatus(AppUtils.trim(covStatusChangeRQ)).subscribe((res: any) => {
            this.router.navigate(['/covering-approval/dashboard']);
          });
        });
        this.modalRef.hide();
        
      }
    });

  }

  //Unauthorized
  async UnauthCOV($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    // Filter only selected records
    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);
    if (selectedDetails.length === 0) {
      this.alertService.showToaster("No records selected", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }

    let returnUserList: any;
    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
    };

    // Get return user list based on logged-in user info
    await this.applicationService
      .getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(loggedInUserWorkFlowRQ)
      .then((response) => {
        returnUserList = response;
      });

    const covAppRefNumbers = Array.from(new Set(selectedDetails.map(detail => detail.covAppRefNumber))).join(',');
    const branchCodes = Array.from(new Set(selectedDetails.map(detail => detail.branchCode))).join(',');

    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading: "Unauthorized Covering Approval",
        actionMessage: `${this.covStatusChangeHeading[status]}`,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.CANCEL,
        commentCacheKey: covAppRefNumbers + this.covActionStatus[status] + this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          relatedDivCodes: [branchCodes]
        }
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        selectedDetails.forEach(detail => {
          let covStatusChangeRQ = {
            covAppId: detail.covAppId,
            currentStatus: status,
            currentAssignUserId: data.assignedUser.userID,
            covAppRefNumber: detail.covAppRefNumber,
            actionMessage: `Forward to ${data.assignedUser.assignUserDisplayName}`,
            forwardType: data.forwardType,
            isAuthorized: true,
            covAppCommentDTO: {
              userComment: data.remarkData.comment,
              createdUserId: data.remarkData.createdUserId,
              createdUser: data.remarkData.createdUser,
              createdUserDisplayName: data.remarkData.createdUserDisplayName,
              createdUserDivCode: data.remarkData.createdUserDivCode,
              createdUserUpmCode: data.remarkData.createdUserUpmCode,
              isUsersOnly: "N",
              isDivisionOnly: "N",
              isPublic: "Y",
            }
          };

          if (data.assignedUser) {
            covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
              assignUserDisplayName: data.assignedUser.assignUserDisplayName,
              currentAssignUserAD: data.assignedUser.adUserID,
              currentAssignUserDivCode: data.assignedUser.divCode,
              assignUserUpmId: data.assignedUser.userID,
              assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
              currentAssignUser: data.assignedUser.adUserID,
              updatedUserId: this.applicationService.getLoggedInUserUserID()
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
            });
          }

          // Call the service for each selected detail
          this.coveringApprovalService.updateCOVStatus(AppUtils.trim(covStatusChangeRQ)).subscribe((res: any) => {
            // handle the response if needed
          });
        });

        // Redirect to the dashboard
        this.router.navigate(["/covering-approval/dashboard"]);
      }
    });
  }

  //decline
  async changeStatusCOVSelectedDecline($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);
    if (selectedDetails.length === 0) {
      this.alertService.showToaster("No records selected", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }
    let returnUserList: any;
    const covAppRefNumbers = Array.from(new Set(selectedDetails.map(detail => detail.covAppRefNumber))).join(',');
    const branchCode = Array.from(new Set(selectedDetails.map(detail => detail.branchCode))).join(',');

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
        heading: "Decline Covering Approval",
        actionName: this.covActionStatus[status],
        //commentCacheKey: this.coveringApprovalForm.covAppRefNumber + this.covActionStatus[status] + this.applicationService.getLoggedInUserUserID(),
        commentCacheKey: covAppRefNumbers + this.applicationService.getLoggedInUserUserID(),
        showUsersOnlyOption: false,
        showDivisionOnlyOption: false,
        actionMessage: `${this.covStatusChangeHeading[status]}`,

        message: "Do you want to  this Application Form ?`",
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          //branchCode: this.transactionDetails.branchCode,
          //createdUser: this.transactionDetails.createdBy,
          //currentAssignUser: this.transactionDetails.assignUser,
          relatedDivCodes: [branchCode]
        },
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        selectedDetails.forEach(detail => {
          let actionMessage = 'Rejected by ' + this.applicationService.getLoggedInUserDisplayName();
          let coveringApprovalUpdateDTO = {
            actionMessage: actionMessage,
            assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
            assignUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
            assignUserUpmId: this.applicationService.getLoggedInUserUserID(),
            covAppCommentDTO: {
              userComment: data.comment,
              isUsersOnly: data.isUsersOnly ? 'Y' : 'N',
              isDivisionOnly: data.isDivisionOnly ? 'Y' : 'N',
              isPublic: data.isPublic ? 'Y' : 'N',
              createdUserId: this.applicationService.getLoggedInUserUserID(),
              createdUser: this.applicationService.getLoggedInUserUserName(),
              createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
              createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
              createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
            },
            covAppId: detail.covAppId,
            covAppRefNumber: detail.covAppRefNumber,
            currentAssignUser: this.applicationService.getLoggedInUserUserName(),
            currentAssignUserAD: this.applicationService.getLoggedInUserUserName(),
            currentAssignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
            currentAssignUserId: this.applicationService.getLoggedInUserUserID(),
            updatedUserId: this.applicationService.getLoggedInUserUserID(),
            currentStatus: status,
            forwardType: "DIRECT_USER",
            isAuthorized: false,

            //updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          };
          this.coveringApprovalService.updateCOVStatus(AppUtils.trim(coveringApprovalUpdateDTO)).subscribe((res: any) => {
          });

        });
        this.router.navigate(['/covering-approval/dashboard']);
      }
    });
  }

  //approved
  async changeStatusCOVSelectedApprove($event, status) {
    if (this.disableApproveButton) {
      // Handle the case where button is disabled, if necessary
      return;
    }
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);
    if (selectedDetails.length === 0) {
      this.alertService.showToaster("No records selected", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }

    let returnUserList: any;
    const covAppRefNumbers = Array.from(new Set(selectedDetails.map(detail => detail.covAppRefNumber))).join(',');
    const branchCode = Array.from(new Set(selectedDetails.map(detail => detail.branchCode))).join(',');

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
        heading: "Approved Covering Approval",
        actionName: this.covActionStatus[status],
        commentCacheKey: covAppRefNumbers + this.covActionStatus[status] + this.applicationService.getLoggedInUserUserID(),
        showUsersOnlyOption: false,
        showDivisionOnlyOption: false,
        actionMessage: `${this.covStatusChangeHeading[status]}`,

        message: "Do you want to  this Application Form ?`",
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          //branchCode: this.transactionDetails.branchCode,
          //createdUser: this.transactionDetails.createdBy,
          //currentAssignUser: this.transactionDetails.assignUser,
          relatedDivCodes: [branchCode]
        },
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        selectedDetails.forEach(detail => {
          let actionMessage = 'Approved by ' + this.applicationService.getLoggedInUserDisplayName();
          let coveringApprovalUpdateDTO = {
            //...this.coveringApprovalForm,
            actionMessage: actionMessage,
            assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
            assignUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
            assignUserUpmId: this.applicationService.getLoggedInUserUserID(),
            covAppCommentDTO: {
              userComment: data.comment,
              isUsersOnly: data.isUsersOnly ? 'Y' : 'N',
              isDivisionOnly: data.isDivisionOnly ? 'Y' : 'N',
              isPublic: data.isPublic ? 'Y' : 'N',
              createdUserId: this.applicationService.getLoggedInUserUserID(),
              createdUser: this.applicationService.getLoggedInUserUserName(),
              createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
              createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
              createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
            },
            covAppId: detail.covAppId,
            covAppRefNumber: detail.covAppRefNumber,
            currentAssignUser: this.applicationService.getLoggedInUserUserName(),
            currentAssignUserAD: this.applicationService.getLoggedInUserUserName(),
            currentAssignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
            currentAssignUserId: this.applicationService.getLoggedInUserUserID(),
            updatedUserId: this.applicationService.getLoggedInUserUserID(),
            currentStatus: status,
            forwardType: "DIRECT_USER",
            isAuthorized: false,

            //updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          };

          this.coveringApprovalService.updateCOVStatus(AppUtils.trim(coveringApprovalUpdateDTO)).subscribe((res: any) => {
            this.router.navigate(['/covering-approval/dashboard']);
          });

        });
        
      }
    });
  }

  //cancel
  onCancelClicked() {
    this.router.navigate(['/covering-approval/dashboard']);
  }

  // isAuthorized($event){
  //   const selectedDetails = this.coveringApprovalForm.filter(detail => detail.selected);
  //   console.log("selectedDetails",selectedDetails)
  // }

  // onPageEvent(event) {
  //   this.currentPage = event.pageIndex;
  //   this.pageSize.pageSize = event.pageSize;
  //   // Call your service to fetch data for the new page here
  //   let testAccountNumber = Object.assign({
  //     accountNumber : this.coveringApproval.accountNumber
  //  }); 
  //   this.coveringApprovalService.getPendingCoveringApprovals({accountNumber:this.coveringApproval.accountNumber},
  //     new Pagination(event)
  //   ).then((data: any) => {
  //     this.coveringApproval = data;
  //     this.coveringApprovalFormPage[this.coveringApproval] = data.pageData;

  //     console.log("data.pageData",data.pageData);
  //     console.log("data",data);

  //   });
  // }

  openComment(coveringApprovalForm: any): void {
    const commentDetails = coveringApprovalForm.covAppCommentDTOList[0];
    this.modalRef = this.mdbModalService.show(CovPendingCommentComponent, {
      data: { commentDetails: commentDetails }

    })
  }
  isEqualLoginAndAssignUser() {
    // if (
    //   this.coveringApproval.assignUserUpmGroupCode ==
    //   this.applicationService.getLoggedInUserUPMGroupCode()
    // ) 
    this.accessLevelOfCurrentAssignUser = this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= 50) {
      return true;
    } else {
      return false;
    }

  }

  isEqualLoginAndAssignUserCheck(coveringApprovalForm: any[]): any[] {
    // Fetch the logged-in user ID and Manager ID
    const loggedInUserId = this.applicationService.getLoggedInUserUserID();
    const loggedInUserIdMGR = this.applicationService.getLoggedInUserSolID() + "MGR";

    // Filter the forms by checking if the currentAssignUserId matches either the logged-in user ID or the manager ID
    const filteredForms = coveringApprovalForm.filter(form => {
        // Compare both values as strings to ensure type safety
        const assignUserId = String(form.currentAssignUserId);
        const isMatch = assignUserId === String(loggedInUserId) || assignUserId === String(loggedInUserIdMGR);
        
        //console.log(`Comparing form currentAssignUserId: ${assignUserId} with loggedInUserId: ${loggedInUserId} and loggedInUserIdMGR: ${loggedInUserIdMGR} => Match: ${isMatch}`);
        
        return isMatch;
    });

    return filteredForms;
}

 

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }

  // isEqualLoginAndAssignUsers() {
  //   if (
  //     this.coveringApproval.currentAssignUserId ==
  //     this.applicationService.getLoggedInUserUserID()
  //   ) {
  //     return true;
  //   } else {
  //     return false;
  //   }
  // }

  getFormattedValue(amount: any): string | null {
    if (amount != null) {
      // If the amount is a string and contains commas, remove the commas and parse it to a float
      const numericAmount = typeof amount === 'string' ? parseFloat(amount.replace(/,/g, '')) : amount;

      // Check the resulting value is a valid number
      if (!isNaN(numericAmount)) {
        // Use the CurrencyPipe to format the number
        return this.currencyPipe.transform(numericAmount, '', '');
      }
    }
    return null; // Return null if the amount is not valid or empty
  }

  toggleSelection(coveringApprovalForm: any): void {
    coveringApprovalForm.selected = !coveringApprovalForm.selected;
  }
  isEqualLoginAndAssignUsers() {
    if (this.coveringApproval.currentAssignUserId == this.applicationService.getLoggedInUserUserID() || this.coveringApproval.currentAssignUserId == this.applicationService.getLoggedInUserSolID() + "MGR") {
      return true;
    } else {
      return false;
    }
  }
  isEqualLoginAndAssignUserFiftyAbove() {
    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= 50) {
      return true;
    } else {
      return false;
    }
  }

  isEqualLoginAndAssignUserFiftyLower() {
    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser < 50) {
      return true;
    } else {
      return false;
    }
  }

  showForwardButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ==
        this.coveringApprovalStatusConst.APPROVED) ||
      coveringApproval.currentStatus ==
      this.coveringApprovalStatusConst.REJECTED
    ) {
      return false;
    } else {
      return true;
    }
  }
  showDeleteButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ===
        this.coveringApprovalStatusConst.DRAFT)
    ) {
      return false;
    } else {
      return true;
    }
  }
  showReturnButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ==
        this.coveringApprovalStatusConst.APPROVED) ||
      coveringApproval.currentStatus ==
      this.coveringApprovalStatusConst.REJECTED
    ) {
      return false;
    } else {
      return true;
    }
  }


}
