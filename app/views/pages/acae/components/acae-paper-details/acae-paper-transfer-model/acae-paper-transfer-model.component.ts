import {
  Component,
  OnInit,
  OnDestroy,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";
import * as _ from "lodash";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { ACAEEditStatusModelService } from "../../../services/acae-edit-status-model.service";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { CurrencyPipe } from "@angular/common";
import { Subject, Subscription } from "rxjs";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { AlertService } from "src/app/core/service/common/alert.service";
import { ACAESharedService } from "../../../services/acae-shared.service";
import { ACAEDetailsTransferSearchService } from "../../../services/acae-details-transfer-search.service";
import { ACAEPaperService } from "../../../services/acae-paper.service";
import { error } from "console";

@Component({
  selector: "app-acae-paper-transfer-model",
  templateUrl: "./acae-paper-transfer-model.component.html",
  styleUrls: ["./acae-paper-transfer-model.component.scss"],
})
export class ACAEPaperTransferModelComponent implements OnInit, OnDestroy {
  content: any;

  constructor(
    private formBuilder: FormBuilder,
    private acaeEditService: ACAEEditStatusModelService,
    private applicationService: ApplicationService,
    public acaeDetailsEditStatusModelRef: MDBModalRef,
    private acaePaperService: ACAEPaperService,
    private currencyPipe: CurrencyPipe,
    private alertService: AlertService,
    public acaeSharedService: ACAESharedService,
    private acaeDetailsTransferSearchService: ACAEDetailsTransferSearchService,
  ) { }

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo'
  };

  status: any;
  remarkForm: FormGroup;
  forwardACAEForm: FormGroup;
  rejectACAEForm: FormGroup;
  toBeResubmitACAEForm: FormGroup;
  previouseUserForm: FormGroup;
  transferOptionForm: FormGroup;
  basicCusDetails: {} = {};
  acaeCustomerDetails: {} = {};
  forwardUserGroupLOV: any[] = [];
  forwardUserLOV: any[] = [];
  rejectUserGroupLOV: any[] = [];
  rejectUserLOV: any[] = [];
  transferUserGroupLOV: any[] = [];
  transferUserLOV: any[] = [];
  tempUserLOV: any[];
  previousUsersLOV: any[];
  escalateWithinLOV: any[];
  anticipatedDateStr = "";
  transferUserSolId: string = "";

  onRejectUserGroupChangeSub: Subscription = new Subscription();
  onForwardUserGroupChangeSub: Subscription = new Subscription();
  onTransferUserGroupChangeSub: Subscription = new Subscription();
  isCommentEdited: boolean = false;

  specificACAEPaperAction: Subject<any> = new Subject<any>();
  refreshGridAction: Subject<any> = new Subject<any>();
  clearGridAction: Subject<any> = new Subject<any>();

  ngOnInit(): void {
    this.status = this.content.initialState.status;
    this.isCommentEdited = this.content.initialState.isCommentEdited;

    if (this.status === 'forward') {
      this.forwardACAEForm = this.loadInitailforwardACAEForm();
      this.forwardACAEForm.controls.forwardUserGroup.enable();
      this.loadCAEForwardUserGroupLOV();
      this.onForwardUserGroupChangeSub = this.forwardACAEForm.controls.forwardUserGroup.valueChanges
        .subscribe((value: any) => {
          this.getforwardUserGroupValue(value)
          this.forwardACAEForm.controls.forwardUser.setValidators([Validators.required]);
          this.forwardACAEForm.controls.forwardUser.reset();
          this.forwardACAEForm.updateValueAndValidity();
        });
    } else if (this.status === 'reject') {
      //changed this requirement 2025-01-01
      // this.rejectACAEForm = this.loadInitailRejectACAEForm();
      // this.rejectACAEForm.controls.rejectUserGroup.enable();
      // this.loadCAERejectUserGroupLOV();
      // this.onRejectUserGroupChangeSub = this.rejectACAEForm.controls.rejectUserGroup.valueChanges
      //   .subscribe((value: any) => {
      //     this.getRejectUserGroupValue(value)
      //     this.rejectACAEForm.controls.rejectUser.setValidators([Validators.required]);
      //     this.rejectACAEForm.controls.rejectUser.reset();
      //     this.rejectACAEForm.updateValueAndValidity();
      //   });
      this.rejectACAEForm = this.loadPerviousUserDropDown();
      this.rejectACAEForm.controls.previousUsers.enable();
      this.loadPreviousUsersLOV();

    } else if (this.status === 'toBeResubmitted') {
      this.toBeResubmitACAEForm = this.loadInitailToBeResubmitACAEForm();
      this.toBeResubmitACAEForm.controls.previousUsers.enable();
      this.toBeResubmitACAEForm.controls.escalateWithin.enable();
      this.loadPreviousUsersLOV();
      this.loadEscalateWithinLOV();
    } else if (this.status === 'transferOption') {
      this.transferOptionForm = this.loadInitailTransferOptionForm();
      this.transferOptionForm.controls.transferUserGroup.enable();
      this.loadTransferUserGroupLOV();

      this.transferOptionForm.controls.transferUserSolId.valueChanges.subscribe((value: any) => {
        this.transferOptionForm.controls.transferUserGroup.reset();
        this.transferOptionForm.controls.rejectUser.reset();
        this.transferOptionForm.updateValueAndValidity();
      });
      this.onTransferUserGroupChangeSub = this.transferOptionForm.controls.transferUserGroup.valueChanges
        .subscribe((value: any) => {
          this.getTransferUserList(value)
          this.transferOptionForm.controls.rejectUser.reset();
          this.transferOptionForm.updateValueAndValidity();
        });
    }
  }

  ngOnDestroy(): void {
  }

  isValid() {
    return this.transferOptionForm.valid
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  loadInitailRemarkForm() {
    return this.formBuilder.group({
      remark: [""],
    });
  }

  // loadInitailRejectACAEForm() {
  //   return this.formBuilder.group({
  //     rejectUserGroup: [""],
  //     rejectUser: [""],
  //   });
  // }

  loadInitailforwardACAEForm() {
    return this.formBuilder.group({
      forwardUserGroup: [""],
      forwardUser: [""]
    });
  }

  loadInitailToBeResubmitACAEForm() {
    return this.formBuilder.group({
      previousUsers: [""],
      escalateWithin: ["7"]
    });
  }

  loadInitailTransferOptionForm() {
    return this.formBuilder.group({
      transferUserGroup: [""],
      transferUserSolId: [""],
      rejectUser: [""],
      comment: [""],
    });
  }

  loadPerviousUserDropDown() {
    return this.formBuilder.group({
      previousUsers: [""]
    });
  }

  loadCAEForwardUserGroupLOV() {
    this.forwardUserLOV = [];
    var loggedInUserWorkFlowByHighLevelRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      level: "HIGH",
    }
    this.acaePaperService.getACAEForwardUserGroupLOVService(loggedInUserWorkFlowByHighLevelRQ).subscribe((response: any) => {
      if (response) {
        this.forwardUserGroupLOV = response.filter((rec: { groupCode: number; referenceName: any }) =>
          rec.groupCode != 64 && rec.groupCode != 81
        )
          .map((item: { groupCode: number; referenceName: string }) => ({
            value: item.groupCode,
            label: item.referenceName,
          }));
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  loadCAERejectUserGroupLOV() {
    this.rejectUserLOV = [];
    var loggedInUserWorkFlowByLowLevelRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      level: "LOW",
    }
    this.acaePaperService.getACAEForwardUserGroupLOVService(loggedInUserWorkFlowByLowLevelRQ).subscribe((response: any) => {
      if (response) {
        this.rejectUserGroupLOV =
          response.filter((rec: { groupCode: number }) => rec.groupCode >= Constants.applicationSecurityWorkClass.MANAGER).map(
            (item: { groupCode: any; referenceName: any; }) => ({
              value: item.groupCode,
              label: item.referenceName,
            })
          );
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  loadPreviousUsersLOV() {
    let acaePreviousUserRQ = {
      "searchReference": this.content.initialState.gridData.refNumber,
      "accountNumber": this.content.initialState.gridData.accountNumber,
      "thisUser": this.applicationService.getLoggedInUserUserID(),
    }
    this.acaeEditService.getPreviousUsersService(acaePreviousUserRQ).subscribe((response: any) => {
      if (response) {
        this.previousUsersLOV = response.map(
          (item: { id: any; firstName: any; lastName: any; }) => ({
            value: item.id,
            label: item.firstName + " " + item.lastName,
          })
        );
      }
    }, (error) => {
      this.alertService.showToaster("Forward Failed!", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  loadTransferUserGroupLOV() {
    this.transferUserLOV = [];
    var loggedInUserWorkFlowByLowLevelRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.content.initialState.solId,
      level: "LOW",
    }
    this.acaePaperService.getACAETransferUserListService(loggedInUserWorkFlowByLowLevelRQ).subscribe((response: any) => {
      if (response) {
        this.transferUserGroupLOV =
          response.filter((rec: { groupCode: number }) => rec.groupCode >= Constants.applicationSecurityWorkClass.MANAGER).map(
            (item: { groupCode: any; referenceName: any; }) => ({
              value: item.groupCode,
              label: item.referenceName,
            })
          );
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  loadEscalateWithinLOV() {
    this.escalateWithinLOV = Constants.escalateDates
  }

  getforwardUserGroupValue = async (value: any) => {
    this.forwardUserLOV = [];
    this.forwardUserLOV = await this.loadForwardOrRejectedLOV(value)
  }

  getRejectUserGroupValue = async (value: any) => {
    this.rejectUserLOV = [];
    this.rejectUserLOV = await this.loadForwardOrRejectedLOV(value)
  }

  getTransferUserList = async (value: any) => {
    this.transferUserLOV = [];
    this.transferUserLOV = await this.loadTransferUserList(value)
  }

  async loadTransferUserList(value: any) {
    let eligibleUsers = [];
    let { transferUserSolId } = this.transferOptionForm.getRawValue();

    let users: [] = await this.applicationService.getUserDetailListFormBranchAuthorityLevel(
      {
        solId: transferUserSolId,
        roleId: value,
        appCode: 'CAS'
      });
    if (users && Array.isArray(users)) {
      eligibleUsers = [...users, ...eligibleUsers];
    }

    //get unique records
    this.tempUserLOV = _.uniqBy(eligibleUsers, (i) => i.userID);

    let userList = [];
    _.forEach(_.sortBy(this.tempUserLOV, ['firstName']), async user => {
      if (!_.isNull(user.userID)) {
        // let branch = await AppUtils.getBranchFromBranchCode(this.allBranches, user.divCode);
        // get user details that except login user
        if (this.applicationService.getLoggedInUserUserID() != user.userID) {
          userList.push({
            value: user.userID,
            label: user.userID ? user.firstName + '  ' + user.lastName : "No Users"
          });
        }
      }
    });
    return userList;
  }

  async loadForwardOrRejectedLOV(value: any) {
    let eligibleUsers = [];
    let requiredDivCodes = [];

    requiredDivCodes.push(this.applicationService.getLoggedInUserDivCode());

    let uniqueDivCodes = [...new Set(requiredDivCodes)];

    for (const divCode of uniqueDivCodes) {

      let users: [] = await this.applicationService.getUserDetailListFormBranchAuthorityLevel(
        {
          solId: divCode,
          roleId: value,
          appCode: 'CAS'
        });
      if (users && Array.isArray(users)) {
        eligibleUsers = [...users, ...eligibleUsers];
      }
    }
    //get unique records
    this.tempUserLOV = _.uniqBy(eligibleUsers, (i) => i.userID);

    let userList = [];
    _.forEach(_.sortBy(this.tempUserLOV, ['firstName']), async user => {
      if (!_.isNull(user.userID)) {
        // let branch = await AppUtils.getBranchFromBranchCode(this.allBranches, user.divCode);
        // get user details that except login user
        if (this.applicationService.getLoggedInUserUserID() != user.userID) {
          userList.push({
            value: user.userID,
            label: user.userID ? user.firstName + '  ' + user.lastName : "No Users"
          });
        }
      }
    });
    return userList;
  }

  doForwardACAEPaper = async () => {
    let { forwardUser } = this.forwardACAEForm.getRawValue();
    if (forwardUser == null || forwardUser == "") {
      this.alertService.showToaster("Please select a user!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    if (this.isCommentEdited) {
      await this.commentSaveService()
      await this.doSomethingAsync();
      await this.forwardACAEPaperService();
    }
  }

  doRejectACAEPaper = async () => {
    let { previousUsers } = this.rejectACAEForm.getRawValue();
    if (previousUsers == null || previousUsers == "") {
      this.alertService.showToaster("Please select user!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    if (this.isCommentEdited) {
      await this.commentSaveService()
      await this.doSomethingAsync();
      await this.rejectACAEPaperService();
    }
  }

  doResubmitACAEPaper = async () => {
    let { previousUsers, escalateWithin } = this.toBeResubmitACAEForm.getRawValue();
    if (previousUsers == null || previousUsers == "") {
      this.alertService.showToaster("Please select user!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    if (escalateWithin == null || escalateWithin == "") {
      this.alertService.showToaster("Please escalateWithin Days!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    if (this.isCommentEdited) {
      await this.commentSaveService()
      await this.doSomethingAsync();
      await this.resubmitACAEPaperService();
    }
  }

  doSomethingAsync() {
    return this.timeoutPromise(500);
  }

  timeoutPromise = (time) => {
    return new Promise(function (resolve) {
      setTimeout(function () {
        resolve(Date.now());
      }, time)
    })
  }

  commentSaveService = async (): Promise<any> => {
    if (this.content.initialState.comment == null || this.content.initialState.comment == "") {
      this.alertService.showToaster("Please add comment!", SETTINGS.TOASTER_MESSAGES.error)
      return false;
    }
    let dataRQ = {
      referenceNumber: this.content.initialState.gridData.refNumber,
      accountNumber: this.content.initialState.gridData.accountNumber,
      activeComment: this.content.initialState.comment,
      negDate: this.content.initialState.anticipatedDate,
      previousNegDate: this.content.initialState.gridData.receivedDate,
    }
    return await this.acaePaperService.saveACAECommentService(dataRQ).then((response: any) => {
      if (response) {
        this.alertService.showToaster("Comment added successfully",
          SETTINGS.TOASTER_MESSAGES.success, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      }
    }).catch((error) => {
      this.alertService.showToaster("Comment Added Failed!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  forwardACAEPaperService = async (): Promise<any> => {
    try {
      let { forwardUser } = this.forwardACAEForm.getRawValue();
      if (forwardUser == null || forwardUser == "") {
        this.alertService.showToaster("Please select a user!", SETTINGS.TOASTER_MESSAGES.error)
        return;
      }
      let forwardUserName = "";
      if (this.forwardUserLOV && this.forwardUserLOV.length > 0) {
        const result = this.forwardUserLOV.filter(item => item.value === forwardUser);
        forwardUserName = result[0].label;
      }
      let acaePaperTransferRQ = {
        searchReference: this.content.initialState.gridData.refNumber,
        accountNumber: this.content.initialState.gridData.accountNumber,
        nextUser: forwardUser,
        thisUser: this.applicationService.getLoggedInUserUserID().toString(),
        status: Constants.acaeStatusNo.FORWARDED,
        currentUsername: forwardUserName,
      }
      return await this.acaeEditService.forwardACAEPaperService(acaePaperTransferRQ).then((response: any) => {
        if (response) {
          this.refreshGridAction.next(true);
          this.specificACAEPaperAction.next(true);
          this.onCloseModel()
          this.alertService.showToaster("Forwarded Successfully!",
            SETTINGS.TOASTER_MESSAGES.success, { timeOut: Constants.toastMessageTimeout.MEDIUM })
        }
      }).catch((error) => {
        this.alertService.showToaster("Forwarded Failed!",
          SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      });;
    } catch (e) {
      this.alertService.showToaster("Forwarded Failed!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    }
  }

  async rejectACAEPaperService() {
    try {
      console.log("this.rejectACAEForm.getRawValue()", this.rejectACAEForm.getRawValue())
      let { previousUsers } = this.rejectACAEForm.getRawValue();
      if (previousUsers == null || previousUsers == "") {
        this.alertService.showToaster("Please select user!", SETTINGS.TOASTER_MESSAGES.error)
        return;
      }
      let previousUserName = "";
      if (this.previousUsersLOV && this.previousUsersLOV.length > 0) {
        const result = this.previousUsersLOV.filter(item => item.value === previousUsers);
        previousUserName = result[0].label;
      }
      let acaePaperTransferRQ = {
        searchReference: this.content.initialState.gridData.refNumber,
        accountNumber: this.content.initialState.gridData.accountNumber,
        nextUser: previousUsers,
        thisUser: this.applicationService.getLoggedInUserUserID().toString(),
        status: Constants.acaeStatusNo.RETURNED,
        currentUsername: previousUserName,
      }
      await this.acaeEditService.forwardACAEPaperService(acaePaperTransferRQ).then((response: any) => {
        if (response) {
          this.refreshGridAction.next(true);
          this.specificACAEPaperAction.next(true);
          this.onCloseModel()
          this.alertService.showToaster("Rejected Successfully!",
            SETTINGS.TOASTER_MESSAGES.success, { timeOut: Constants.toastMessageTimeout.MEDIUM });
        }
      }).catch((error) => {
        this.alertService.showToaster("Rejected Failed!",
          SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      });
    } catch (e) {
      this.alertService.showToaster("Forwarded Failed!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    }
  }

  async resubmitACAEPaperService() {
    try {
      let { previousUsers, escalateWithin } = this.toBeResubmitACAEForm.getRawValue();
      if (previousUsers == null || previousUsers == "") {
        this.alertService.showToaster("Please select user!", SETTINGS.TOASTER_MESSAGES.error)
        return;
      }
      if (escalateWithin == null || escalateWithin == "") {
        this.alertService.showToaster("Please escalateWithin Days!", SETTINGS.TOASTER_MESSAGES.error)
        return;
      }
      let previousUserName = "";
      if (this.previousUsersLOV && this.previousUsersLOV.length > 0) {
        const result = this.previousUsersLOV.filter(item => item.value === previousUsers);
        previousUserName = result[0].label;
      }
      let acaePaperTransferRQ = {
        searchReference: this.content.initialState.gridData.refNumber,
        accountNumber: this.content.initialState.gridData.accountNumber,
        nextUser: previousUsers,
        thisUser: this.applicationService.getLoggedInUserUserID().toString(),
        status: Constants.acaeStatusNo.TO_BE_RESUBMIT,
        numOfDays: escalateWithin,
        currentUsername: previousUserName
      }
      await this.acaeEditService.toBeResubmittedACAEPaperService(acaePaperTransferRQ).then((response: any) => {
        if (response) {
          this.refreshGridAction.next(true);
          this.specificACAEPaperAction.next(true);
          this.onCloseModel()
          this.alertService.showToaster("Resubmitted Successfully!",
            SETTINGS.TOASTER_MESSAGES.success, { timeOut: Constants.toastMessageTimeout.MEDIUM })
        }
      }).catch((error) => {
        this.alertService.showToaster("Resubmitted Failed!",
          SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      });
    } catch (e) {
      this.alertService.showToaster("Forwarded Failed!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    }
  }

  // this method used for transfer option
  doTransfer() {
    const today = new Date();
    // Add 7 days
    const futureDate = new Date();
    futureDate.setDate(today.getDate() + 7);

    // Format as yyyy-mm-dd
    const formattedDate = futureDate.toISOString().split('T')[0];

    let { rejectUser, comment } = this.transferOptionForm.getRawValue();
    if (rejectUser == null || rejectUser == "") {
      this.alertService.showToaster("Please select user!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    if (comment == null || comment == "") {
      this.alertService.showToaster("Please add comment!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    let nextUserName = "";
    if (this.transferUserLOV && this.transferUserLOV.length > 0) {
      const result = this.transferUserLOV.filter(item => item.value === rejectUser);
      nextUserName = result[0].label;
    }
    let transferRQ = {
      acaeRecordList: this.content.initialState.acaeRecordList,
      nextUser: rejectUser,
      comment: comment,
      acaeStatus: Constants.acaeStatusNo.TRANSFER_TO_ME,
      thisUser: this.applicationService.getLoggedInUserUserID().toString(),
      solId: this.content.initialState.solId,
      negDate: formattedDate,
      nextUserName: nextUserName
    }
    this.acaeDetailsTransferSearchService.transferOptionService(transferRQ).subscribe((response: any) => {
      if (response) {
        this.alertService.showToaster("Transfer Successfully!", SETTINGS.TOASTER_MESSAGES.success,
          { timeOut: Constants.toastMessageTimeout.MEDIUM })
        this.onCloseModel();
        // this.acaeSharedService.triggerRefreshACAETransferGrid();
        this.clearGridAction.next(true);
      }
    }, (error) => {
      this.alertService.showToaster("Transfer Failed!", SETTINGS.TOASTER_MESSAGES.error,
        { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  onCloseModel() {
    this.acaeDetailsEditStatusModelRef.hide();
  }
}
