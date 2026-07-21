import {
  ChangeDetectorRef,
  Component, OnChanges, OnDestroy, OnInit, SimpleChanges,
} from "@angular/core";
import { Constants } from "../../../../../core/setting/constants";
import { Subject, Subscription } from "rxjs";
import { PageSize } from "src/app/core/dto/page.size";
import { ACAEPaperDetailsComponent } from "../acae-paper-details/acae-paper-details.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import * as _ from "lodash";
import { ACAEService } from "../../services/acae-base.service";
import { ACAEPaperService } from "../../services/acae-paper.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Pagination } from "src/app/core/dto/pagination";
import { AlertService } from "src/app/core/service/common/alert.service";
import { ACAESharedService } from "../../services/acae-shared.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AppUtils } from "src/app/shared/app.utils";
import { isEmpty } from "lodash";
import { AcaeBulkCommentModalComponent } from "./acae-bulk-comment-modal/acae-bulk-comment-modal.component";
import { CurrencyPipe } from "@angular/common";

@Component({
  selector: "app-acae-details",
  templateUrl: "./acae-details.component.html",
  styleUrls: ["./acae-details.component.scss"],
})
export class ACAEDetailsComponent implements OnInit, OnChanges, OnDestroy {

  constructor(
    private mdbModalService: MDBModalService,
    private formBuilder: FormBuilder,
    private acaeBaseService: ACAEService,
    private acaePaperService: ACAEPaperService,
    private applicationService: ApplicationService,
    private alertService: AlertService,
    private acaeSharedService: ACAESharedService,
    private cacheService: CacheService,
    private currencyPipe: CurrencyPipe,
    private cdr: ChangeDetectorRef
  ) { }

  acaeStatusConst = Constants.acaeStatusConst;
  acaeStatus = Constants.acaeStatusLabel;
  acaeStatusNo: Number = Constants.acaeStatusNo.NEW;
  status: string = this.acaeStatusConst.NEW;
  acaeTabStatus = this.acaeStatusConst.NEW;
  acaeCounts: any = Constants.acaeCounts;
  summaryPageArr = [
    {
      pageSize: {
        pageIndex: 1,
        length: 10,
        pageSize: 3,
        pageSizeOptions: 2
      },
    }
  ];
  acaeDetailAction: Subject<any> = new Subject<any>();
  acaeDetailEditModelRef: MDBModalRef;
  acaeSearchPageData = []
  pageSize = new PageSize({ pageSize: 10 });
  pageIndex = 0;
  forwardBatchWiseACAEForm: FormGroup;
  acaeBulkCommentModalRef: MDBModalRef;
  acaeSearchData: any = []

  viewOnly: boolean;
  initailRefNum: number = 0;
  initialSolUserName: string = ""
  acaePaperSummaryDetails: any[] = []
  forwardUserGroupLOV: any[] = []
  forwardUserLOV: any[] = []
  tempUserLOV: any[] = [];
  isACAEBatchComplete: string = "N";

  paginationData = new Pagination({
    pageSize: 10,
    pageIndex: 0
  })
  newPaginationData = new Pagination({
    pageSize: 10,
    pageIndex: 0
  })
  allBankOptions: any = {};
  onForwardUserGroupChangeSub: Subscription = new Subscription();
  onGridDataChangeSub = new Subscription();

  //load initail data
  ngOnInit() {
    //load count data in initail
    this.loadACAECountData(true);
    this.forwardBatchWiseACAEForm = this.loadInitailForwardBatchWiseACAEForm();

    this.paginationData = new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: this.pageSize.pageIndex
    })
    this.loadPageData(this.acaeStatusConst.NEW, this.newPaginationData, false);
    this.onGridDataChangeSub = this.acaeBaseService.onGridDataChange.subscribe(data => {
      this.acaeSearchData = this.acaeBaseService.selectedData;
      this.acaeSearchPageData = this.acaeBaseService.pagedData
    });
  }

  ngOnDestroy(): void {
    this.onGridDataChangeSub.unsubscribe();
    this.onForwardUserGroupChangeSub.unsubscribe();
  }

  //load ACAE Count Data
  loadACAECountData(isModalOpen: boolean) {
    let dataRQ = {
      userId: this.applicationService.getLoggedInUserUserID(),
      solId: this.applicationService.getLoggedInUserSolID(),
    }
    this.acaeBaseService.getACAEStatusCountService(dataRQ, isModalOpen).then((response: any) => {
      if (response) {
        this.acaeCounts = response;
      }
      this.cdr.detectChanges();
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  getCurrencyFormat(amount: any) {
    return this.currencyPipe.transform(amount, '', '')
  }

  loadACAEStatusForDashboard(acaeStatusNo: Number, _status: any, paginationData?: Pagination, isModalOpen?: boolean) {
    let requestBody = {
      solName: this.getBranchName(this.applicationService.getLoggedInUserDivCode()),
      userName: this.applicationService.getLoggedInUserCombinedName() ?
        this.applicationService.getLoggedInUserCombinedName() : this.applicationService.getUserDisplayName(),
      userId: this.applicationService.getLoggedInUserUserID(),
      acaeStatus: acaeStatusNo,
      solId: this.applicationService.getLoggedInUserSolID(),
    }
    this.acaeBaseService.getACAEListByStatusService(requestBody, _status, paginationData, isModalOpen).then((response: any) => {
      if (response) {
        // get Eligibility for Forward ACAE Batch
        if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER
          && acaeStatusNo == Constants.acaeStatusNo.DRAFT && response.pageData.length > 0) {
          this.getEligibilityForwardACAEBatch(response.pageData)
        } else {
          this.isACAEBatchComplete = "N";
          this.resetDropDownField();
        }
        this.cdr.detectChanges();
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  //get upm branch name for display in grid
  getBranchName(branchCode: string) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);
    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  //get Eligibility for forward
  getEligibilityForwardACAEBatch(acaeSearchData: any[]) {
    if (acaeSearchData.length > 0) {
      this.initailRefNum = acaeSearchData[0].refNumber ? acaeSearchData[0].refNumber : 0;
      this.initialSolUserName = acaeSearchData[0].solUserName ? acaeSearchData[0].solUserName : '';
    }
    let acaeListDoneRQ = {
      "solId": this.applicationService.getLoggedInUserSolID(),
      "referenceNumber": acaeSearchData[0].refNumber ? acaeSearchData[0].refNumber : 0,
      "userId": this.applicationService.getLoggedInUserUserID(),
    }
    this.acaeBaseService.getEligibilityForwardACAEBatchService(acaeListDoneRQ).subscribe((response: any) => {
      this.isACAEBatchComplete = response
      //get batch forward upm group list
      if (response === "Y") {
        this.loadBatchForwardData();
        this.cdr.detectChanges();
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  resetDropDownField() {
    this.forwardBatchWiseACAEForm.controls.forwardUserGroup.enable();
    this.forwardBatchWiseACAEForm.controls.forwardUserGroup.reset();
    this.forwardBatchWiseACAEForm.controls.forwardUser.enable();
    this.forwardBatchWiseACAEForm.controls.forwardUser.reset();
  }

  loadBatchForwardData() {
    this.resetDropDownField()
    if (this.isEnableACAEBatchForward()) {
      this.loadCAEForwardUserGroupLOV();
      this.onForwardUserGroupChangeSub = this.forwardBatchWiseACAEForm.controls.forwardUserGroup.valueChanges
        .subscribe((value: any) => {
          if (value) {
            this.getforwardUserGroupValue(value)
            this.forwardBatchWiseACAEForm.controls.forwardUser.setValidators([Validators.required]);
            this.forwardBatchWiseACAEForm.controls.forwardUser.reset();
            this.forwardBatchWiseACAEForm.updateValueAndValidity();
          }
        });
    }
  }

  loadInitailForwardBatchWiseACAEForm(): FormGroup {
    return this.formBuilder.group({
      forwardUserGroup: [""],
      forwardUser: [""]
    });
  }

  getforwardUserGroupValue = async (value: any) => {
    this.forwardUserLOV = [];
    this.forwardUserLOV = await this.loadForwardOrRejectedLOV(value)
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
        ).map((item: { groupCode: number; referenceName: string }) => ({
          value: item.groupCode,
          label: item.referenceName,
        }));
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
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
          appCode: ''
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

  ngOnChanges(changes: SimpleChanges): void {
  }

  //open acae paper modal
  openModalACAEDetails(item: any, attended: string, acaeStatus: string): void {
    let hasAttended = this.loadIsAttended(item);
    if (hasAttended) {
      this.getAcaePaperDetails(item, attended, acaeStatus)
    } else {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    }
  }

  async loadIsAttended(gridData: any) {
    let isSucess: Boolean = false;
    let dataRQ = {
      "referenceNumber": gridData.refNumber,
      "accountNumber": gridData.accountNumber,
    }
    await this.acaePaperService.getIsAttendedService(dataRQ).subscribe((response: any) => {
      if (response) {
        isSucess = response;
      }
    }, (error) => {
      isSucess = false;
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
    return isSucess;
  }

  getAcaePaperDetails(item: any, attended: string, acaeStatus: string): void {
    this.viewOnly = attended == 'Y' ? true : false;
    let dataRQ = {
      "accountName": item.customerName,
      "accountNumber": item.accountNumber,
      "receivedDate": item.recievedDate,
      "refNumber": item.refNumber,
      "viewOnly": this.viewOnly,
      "currentUserName": item.currentUserName,
      "loadUserName": item.loadUserName,
    }
    const initialState = {
      recordSize: this.acaeSearchPageData[acaeStatus].totalNoOfRecords,
      recordIndex: item.recordIndex,
      list: [{ tag: "Count", value: item }],
      gridData: dataRQ,
      acaeStatusNo: this.acaeSharedService.getACAEStatusNo(acaeStatus),

      acaeTabStatus: this.acaeTabStatus,
      pageSize: this.pageSize,
      acaeSearchData: this.acaeSearchData,
      paginationData: this.paginationData,
      condition: "acaeOptions",
    };
    this.acaeDetailEditModelRef = this.mdbModalService.show(ACAEPaperDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      ignoreBackdropClick: true,
      class: "modal-width-90-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "12",
        content: {
          initialState: initialState,
          acaeDetailEditModelRef: this.acaeDetailEditModelRef
        },
      },
    });
    this.acaeDetailEditModelRef.content.refreshAction.subscribe((data: any) => {
      if (data) {
        this.loadPageData(this.acaeSharedService.getACAEDashboardStatus(), this.paginationData, false);
        this.loadACAECountData(true);
      }
    })
  }

  // handle coundbox click event
  loadChanges(acaeDashboardStatus: string) {
    this.loadPageData(acaeDashboardStatus, new Pagination({
      pageSize: this.acaeSharedService.getPageData().pageSize ? this.acaeSharedService.getPageData().pageSize : 10,
      pageIndex: 0
    }), false)
  }

  loadPageData(acaeDashboardStatus: string, paginationData: Pagination, isModalOpen: boolean) {
    switch (acaeDashboardStatus) {
      case this.acaeStatusConst.NEW: {
        this.acaeStatusNo = Constants.acaeStatusNo.NEW;
        this.acaeBaseService.selectedData = [];
        this.acaeBaseService.pagedData = [];
        if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER) {
          this.loadACAEStatusForDashboard(Constants.acaeStatusNo.DRAFT, Constants.acaeStatusConst.DRAFT, paginationData, isModalOpen);
          this.loadACAEStatusForDashboard(Constants.acaeStatusNo.RESUBMIT_TO_ME, Constants.acaeStatusConst.RESUBMITED_TO_ME, paginationData, isModalOpen);
        }
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.FORWARD_TO_ME, Constants.acaeStatusConst.FORWARDED_TO_ME, paginationData, isModalOpen);
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.RETURN_TO_ME, Constants.acaeStatusConst.RETURNED_TO_ME, paginationData, isModalOpen);
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.TRANSFER_TO_ME, Constants.acaeStatusConst.TRANSFERED_TO_ME, paginationData, isModalOpen);
        this.acaePaperSummaryDetails = []
        break;
      }
      case this.acaeStatusConst.FORWARDED: {
        this.acaeStatusNo = Constants.acaeStatusNo.FORWARDED;
        this.acaeBaseService.selectedData = [];
        this.acaeBaseService.pagedData = [];
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.FORWARDED, Constants.acaeStatusConst.FORWARDED, paginationData, isModalOpen);
        break;
      }
      case this.acaeStatusConst.TO_BE_RESUBMIT: {
        this.acaeStatusNo = Constants.acaeStatusNo.TO_BE_RESUBMIT;
        this.acaeBaseService.selectedData = [];
        this.acaeBaseService.pagedData = [];
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.TO_BE_RESUBMIT, Constants.acaeStatusConst.TO_BE_RESUBMIT, paginationData, isModalOpen);
        break;
      }
      case this.acaeStatusConst.APPROVED: {
        this.acaeStatusNo = Constants.acaeStatusNo.APPROVED;
        this.acaeBaseService.selectedData = [];
        this.acaeBaseService.pagedData = [];
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.APPROVED, Constants.acaeStatusConst.APPROVED, paginationData, isModalOpen);
        break;
      }
      case this.acaeStatusConst.RETURNED: {
        this.acaeStatusNo = Constants.acaeStatusNo.RETURNED;
        this.acaeBaseService.selectedData = [];
        this.acaeBaseService.pagedData = [];
        this.loadACAEStatusForDashboard(Constants.acaeStatusNo.RETURNED, Constants.acaeStatusConst.RETURNED, paginationData, isModalOpen);
        break;
      }
    }
    this.acaeTabStatus = acaeDashboardStatus;
    this.acaeSharedService.setACAEDashboardStatus(acaeDashboardStatus)
  }

  //page navigation acae grid
  onPageEvent(event: any) {
    this.paginationData.pageSize = event.pageSize;
    this.paginationData.pageIndex = event.pageIndex;
    this.acaeSharedService.setPageData(event)
    this.loadPageData(this.acaeTabStatus, this.acaeSharedService.getPageData(), false);

  }

  onSummaryPageEvent(event, i) {
    this.summaryPageArr[i].pageSize.pageSize = event.pageSize;
  }

  // batch forwarding api call
  doForwardACAEBatch() {
    let { forwardUser } = this.forwardBatchWiseACAEForm.getRawValue();
    if (forwardUser == null) {
      this.alertService.showToaster("Please Select User!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      return;
    }
    let forwardUserName = "";
    if (this.forwardUserLOV && this.forwardUserLOV.length > 0) {
      const result = this.forwardUserLOV.filter(item => item.value === forwardUser);
      forwardUserName = result[0].label;
    }
    let dataRQ = {
      referenceId: this.initailRefNum,
      thisUser: this.applicationService.getLoggedInUserUserID(),
      nextUser: forwardUser,
      status: Constants.acaeStatusNo.FORWARDED,
      solUserName: this.initialSolUserName,
      nextUserName: forwardUserName
    }
    this.acaePaperService.forwardACAEBatchService(dataRQ).subscribe((response: any) => {
      if (response) {
        this.alertService.showToaster("Batch Forward Successfully",
          SETTINGS.TOASTER_MESSAGES.success, { timeOut: Constants.toastMessageTimeout.MEDIUM })
        this.loadPageData(this.acaeSharedService.getACAEDashboardStatus(), this.paginationData, false);
        this.loadACAECountData(true);
      } else {
        this.alertService.showToaster("Batch Forward Failed",
          SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
        this.loadPageData(this.acaeSharedService.getACAEDashboardStatus(), this.paginationData, false);
        this.loadACAECountData(true);
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  //select for acae batch comment
  onSelectACAERecords($event: any, item: any, acaeItem: any, key: string) {
    if (item['selected']) {
      this.acaeSearchData[key][item.recordIndex]['selected'] = false
    } else {
      this.acaeSearchData[key][item.recordIndex]['selected'] = true
    }
  }

  //return selected records for batch frowarding
  doSelectedACAERecords() {
    let selectedData = []
    this.acaeSearchData["DRAFT"].map((data: any, index: number) => {
      if (data.selected === true) {
        let rec = {
          "accNumber": data["accountNumber"],
          "refNumber": data["refNumber"],
        }
        selectedData.push(rec)
      }
    });
    return selectedData;
  }

  //condition to enable batchforward dropdowns
  isEnableACAEBatchForward() {
    // return true;
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER
      && this.acaeTabStatus == Constants.acaeStatusConst.NEW
      && this.isACAEBatchComplete == "Y") {
      return true;
    } else {
      return false;
    }
  }

  //condition to enable BulkComment button
  isEnableBulkComment() {
    // return true;
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER
      && this.acaeTabStatus == Constants.acaeStatusConst.NEW) {
      return true;
    } else {
      return false;
    }
  }

  //modal open for bulk comment add
  modalOpenBulkComment(sdsd: string) {
    if (this.doSelectedACAERecords().length > 0) {
      const initialState = {
        "recordSet": this.doSelectedACAERecords(),
      };
      this.acaeBulkCommentModalRef = this.mdbModalService.show(AcaeBulkCommentModalComponent, {
        initialState,
        backdrop: true,
        keyboard: true,
        focus: true,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          heading: "Add/Edit Director Details",
          content: {
            acaeBulkCommentModalRef: this.acaeBulkCommentModalRef,
            initialState: initialState,
          },
        },
      });
      this.acaeBulkCommentModalRef.content.refreshGridAction.subscribe((data: any) => {
        if (data) {
          //grid refresing actions after events
          this.loadACAECountData(true);
          this.loadPageData(this.acaeSharedService.getACAEDashboardStatus(), new Pagination({
            pageSize: this.acaeSharedService.getPageData().pageSize ? this.acaeSharedService.getPageData().pageSize : 10,
            pageIndex: 0
          }), false);
        }
      })
    } else {
      this.alertService.showToaster("Please select records to comment", SETTINGS.TOASTER_MESSAGES.error)
    }
  }


  isACAEPaperSummaryEnable() {
    // if (this.acaeTabStatus != Constants.acaeStatusConst.NEW &&
    //   this.acaeTabStatus != Constants.acaeStatusConst.TOBERESUBMITTED) {
    //   return true;
    // } else {
    //   return false;
    // }
    return false;
  }

  //condition to enable ToBeResubmitCountBox
  isEnableToBeResubmitCountBox() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER) {
      return true;
    } else {
      return false;
    }
  }

  isEnableToBeResubmittedCountBox() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.RM) {
      return true;
    } else {
      return false;
    }
  }

  isCurrentUserEnable() {
    if (this.acaeTabStatus !== Constants.acaeStatusConst.NEW
      && this.acaeTabStatus !== Constants.acaeStatusConst.APPROVED) {
      return true
    } else {
      return false;
    }
  }

  OnDestroy(): void {
    this.forwardBatchWiseACAEForm.controls.forwardUserGroup.reset();
    this.forwardBatchWiseACAEForm.controls.forwardUser.reset();
  }

  onCloseModel = () => {
    this.acaeDetailEditModelRef.hide();
    this.acaeDetailAction.next(null);
  }
}