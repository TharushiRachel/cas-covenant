import {
  Component,
  OnInit,
  OnChanges,
  OnDestroy,
  SimpleChanges,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
} from "@angular/forms";
import { IMyOptions, MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subject, Subscription } from "rxjs";
import { PageSize } from "src/app/core/dto/page.size";
import * as _ from "lodash";
import { ACAEPaperService } from "../../services/acae-paper.service";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { CacheService } from "src/app/core/service/data/cache.service";
import { CurrencyPipe } from "@angular/common";
import { ACAEPaperTransferModelComponent } from "./acae-paper-transfer-model/acae-paper-transfer-model.component";
import * as moment from "moment";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { ACAESharedService } from "../../services/acae-shared.service";
import { ACAEService } from "../../services/acae-base.service";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { Pagination } from "src/app/core/dto/pagination";
import { AcaePaperRangeInquiryDetailsComponent } from "./acae-paper-range-inquiry-details/acae-paper-range-inquiry-details.component";
import { IACAECurrentUserDTO, IAcaePaperApproveRQ } from "../../interfaces/acae-interface";

@Component({
  selector: "app-acae-paper-details",
  templateUrl: "./acae-paper-details.component.html",
  styleUrls: ["./acae-paper-details.component.scss"]
})
export class ACAEPaperDetailsComponent implements OnInit, OnChanges, OnDestroy {
  constructor(
    private mdbModalService: MDBModalService,
    private formBuilder: FormBuilder,
    public acaeDetailsEditmodalRef: MDBModalRef,
    private acaePaperService: ACAEPaperService,
    private applicationService: ApplicationService,
    private cacheService: CacheService,
    private currencyPipe: CurrencyPipe,
    private alertService: AlertService,
    private acaeSharedService: ACAESharedService,
    private acaeBaseService: ACAEService
  ) {
    this.formErrors = {
      directorName: {},
      nic: {},
      dateOfBirthStr: {},
    };
  }
  commonDetails = {} = {};
  suggestCommentArr: string[];
  toDate = moment().subtract(1, "months").endOf('month').format('DD-MM-YYYY');
  fromDate = moment().subtract(12, "months").startOf('month').format('DD-MM-YYYY');

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo'
  };
  paginationData: Pagination;
  approveModalRef: MDBModalRef;
  viewOnly: boolean;
  customerDetails: any = {}
  refreshAction: Subject<any> = new Subject<any>();
  acaeAllowedApproveLimit: string = null;
  activeAnticipatedDateStr = "";
  activeComment: String = "";
  remark: String = "";
  tempUserLOV: any[];
  acaeTransferModelRef: MDBModalRef;
  rejectACAEForm: FormGroup;
  forwardACAEForm: FormGroup;
  remarkForm: FormGroup;
  formErrors: any;
  allBranches: any[] = [];
  content: any;
  acaeStatusNo: number = 0;
  onRejectUserGroupChangeSub: Subscription = new Subscription();
  onForwardUserGroupChangeSub: Subscription = new Subscription();
  accountBalanceDetails = {}
  advanceCusDetails = {}
  pageSize = new PageSize();

  acaeTabStatus: string = "";
  balancesAfterPaymentData: any = [];
  userCommentList: any = [];
  acaeAllData: any[] = []
  forwardUserLOV: { value: String; label: String }[] = [];
  rejectUserLOV: { value: String; label: String }[] = [];
  isCommentSave: Boolean = false;
  isCommentEdited: Boolean = false;
  condition: any;
  advancerOutstandingData: [] = []
  relatedAccountData: [] = [];
  firstOutstandingData: {} = {}
  outstandingData: any = [];
  isOutstandingLoading: boolean = false;
  isBasicOutstandingAvailable: boolean = false;
  isAdvanceOutstandingAvailable: boolean = false;
  loanDetails = [] = []

  isLoanDetailLoading: boolean = false;
  isRelatedDetailLoading: boolean = false;
  isLoadingUserComment: boolean = false;
  isBasicOutstandingLoading: boolean = false;
  isActiveCommentLoading: boolean = false;
  isLoadingAccountBalance: boolean = false;
  isLoadingBalancesAfterPayment: boolean = false;
  isLoadingAllACAEPapers: boolean;
  isLoadingApproveButton: boolean = false;

  comment: String = "";
  numberOfClassifiedDays: number = 0;

  availableTagsManager = [
    "Since Regularized",
    "Paid against leeway of depositsof Rs..................../- ( Leeway - Rs.............../-)",
    "Paid against float Balance of Rs................./-",
    "Paid against Post dated cheques ( Date of realization of PDs.....................)",
    "Rs....................../- deposited to the account during .............................",
    "Excess reduced to Rs...................../-"
  ];

  availableTagsRMOnly = [
    "Approved. Please regularize the account and report",
    "Approved. Please arrange a regular limit",
    "Approved",
    "Recommended"
  ];

  availableTagsRM = [
    "Approved. Please regularize the account and report",
    "Please Regularize the account & report",
    "Casual excesses will not entertain until settlement of loan arrears",
    "Please recover loan arrears before ...........",
    "Please arrange a regular limit",
    "Let us have the settlement arragements of loan arrears",
    "Late submission - Please submit on stipulated time frame",
    "Approved",
    "Recommended"
  ];
  isFirstOutstanding: boolean = false;
  isAdvanceOutstandingLoading: boolean = false;
  executed: boolean = false;
  anticipatedDateStr = "";
  noBasicData = ""
  noData = ''
  anticipatedDate = "";
  clearDABalance: number = 0.00;

  currentUserInfo: IACAECurrentUserDTO = {
    userName: "",
    userLevel: "",
    branch: ""
  };

  ngOnInit() {
    //get clicked initial papper detaiils
    this.loadInitailPaper()
    this.forwardACAEForm = this.loadInitailforwardACAEForm();
    this.rejectACAEForm = this.loadInitailRejectACAEForm();
    this.remarkForm = this.loadInitailRemarkForm();
    this.loadSuggestComment();
    this.getDAClearBalance();
    this.acaeSharedService.triggerRefreshSpecificACAEPaperSource$.subscribe(() => {
      this.getSpecificACAEPaper("next", 'normal');
    });
    this.acaeSharedService.triggerAddACAECommentSource$.subscribe(() => {
      this.commentSaveService("")
    });
    //screen height  ajestment related to screen size
    // this.screenHeightControl()
  }

  screenHeightControl() {
    let screenHeight: number = window.innerHeight
    let child: any = document.getElementById('overflow-edit-status-model')
    child.style.scrollBehavior = "smooth";
    if (this.isCommentSectionEnable()) {
      //small desktop
      if (300 <= screenHeight && screenHeight < 500) {
        child.style.maxHeight = '10vh'
        //small desktop
      } else if (500 <= screenHeight && screenHeight < 700) {
        child.style.maxHeight = '29vh'
        //small desktop
      } else if (700 <= screenHeight && screenHeight < 900) {
        child.style.maxHeight = '45vh'
        // isNormalDesktop
      } else if (900 <= screenHeight && screenHeight < 1364) {
        child.style.maxHeight = '56vh'
        //Ipad
      } else if (1364 <= screenHeight && screenHeight < 2080) {
        child.style.maxHeight = '65vh'
      } else if (2080 <= screenHeight) {
        child.style.maxHeight = '88vh'
      }
    } else {
      //small desktop
      if (300 <= screenHeight && screenHeight < 500) {
        child.style.maxHeight = '40vh'
        //small desktop
      } else if (500 <= screenHeight && screenHeight < 700) {
        child.style.maxHeight = '60vh'
        //small desktop
      } if (700 <= screenHeight && screenHeight < 900) {
        child.style.maxHeight = '74vh'
        // isNormalDesktop
      } else if (900 <= screenHeight && screenHeight < 1364) {
        child.style.maxHeight = '79vh'
        //Ipad
      } else if (1364 <= screenHeight && screenHeight < 2080) {
        child.style.maxHeight = '90vh'
      } else if (2080 <= screenHeight) {
        child.style.maxHeight = '90vh'
      }
    }
  }

  loadSuggestComment() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.RM) {
      this.suggestCommentArr = this.availableTagsRMOnly;
    } else if (this.applicationService.getLoggedInUserUPMGroupCode() > Constants.applicationSecurityWorkClass.MANAGER) {
      this.suggestCommentArr = this.availableTagsRM;
    } else {
      this.suggestCommentArr = this.availableTagsManager;
    }
  }

  onRangeInquiryOpenModal(toDate: String) {
    const initialState = {
      "gridData": this.commonDetails,
      "toDate": toDate,
      "paperInfo": this.commonDetails
    };
    this.mdbModalService.show(AcaePaperRangeInquiryDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Director Details",
        content: {
          initialState: initialState,
        },
      },
    });
  }

  loadInitailRemarkForm() {
    return this.formBuilder.group({
      remark: "",
      anticipatedDateStr: [""]
    });
  }

  loadInitailRejectACAEForm() {
    return this.formBuilder.group({
      rejectUserGroup: [""],
      rejectUser: [""],
    });
  }

  loadInitailforwardACAEForm() {
    return this.formBuilder.group({
      forwardUserGroup: [""],
      forwardUser: [""],
    });
  }

  //get clicked initial papper details
  loadInitailPaper() {
    this.commonDetails = {
      "refNumber": this.content.initialState.gridData.refNumber,
      "accountNumber": this.content.initialState.gridData.accountNumber,
      "accountName": this.content.initialState.gridData.accountName,
      "receivedDate": this.content.initialState.gridData.receivedDate,
      "viewOnly": this.content.initialState.gridData.viewOnly,
      "currentUserName": this.content.initialState.gridData.currentUserName,
      "loadUserName": this.content.initialState.gridData.loadUserName,
    }

    this.condition = this.content.initialState.condition

    this.acaeSharedService.setAllPaperSize(this.content.initialState.recordSize);

    this.acaeStatusNo = this.content.initialState.acaeStatusNo;
    this.pageSize = this.content.initialState.pageSize;
    this.viewOnly = this.content.initialState.gridData.viewOnly;
    this.acaeTabStatus = this.content.initialState.acaeTabStatus
    this.allBranches = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.paginationData = this.content.initialState.paginationData

    this.loadActiveComment(this.content.initialState.gridData);
    this.getCurrentUser(this.content.initialState.gridData);
    this.loadCustomerInformationDetails(this.content.initialState.gridData);
    this.loadAccountBalanceDetails(this.content.initialState.gridData);
    this.getBasicOutstandingData(this.content.initialState.gridData);
    this.getRelatedAccountData(this.content.initialState.gridData);
    this.getACAELoanAccountData(this.content.initialState.gridData);
    this.loadBalancesAfterPaymentDetails(this.content.initialState.gridData);
    this.loadUserCommentDetails(this.content.initialState.gridData);
    if (this.condition !== "inquiry" && this.condition !== "transferOption") {
      this.getAllACAEPapers(this.content.initialState.acaeStatusNo)
    }
  }

  async getDAClearBalance() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() >= Constants.applicationSecurityWorkClass.RM
      && (this.acaeTabStatus == Constants.acaeStatusConst.NEW)) {
      this.isLoadingApproveButton = true;
      await this.acaePaperService.getDAClearBalance().then((response: any) => {
        this.isLoadingApproveButton = false;
        if (response) {
          this.acaeAllowedApproveLimit = response
        }
        this.canbeApprovedforDA()
      }, (error) => {
        this.isLoadingApproveButton = false;
        // this.alertService.showToaster("Please contact system administrator",
        //   SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      });
    }
  }

  async getCurrentUser(gridData: any) {
    this.currentUserInfo = {
      userName: "",
      userLevel: "",
      branch: ""
    };
    if (this.acaeTabStatus !== Constants.acaeStatusConst.NEW
      && this.acaeTabStatus !== Constants.acaeStatusConst.APPROVED) {
      if (gridData.loadUserName) {
        //if username  in db loading in db
        this.currentUserInfo = {
          userName: gridData.currentUserName,
          userLevel: "",
          branch: ""
        };
      } else {
        //if username not in db loading in upm
        let dataRQ = {
          "refNumber": gridData.refNumber,
          "accountNumber": gridData.accountNumber,
        }
        await this.acaePaperService.getCurrentUserService(dataRQ).then((response: any) => {
          if (response) {
            this.currentUserInfo = response
          }
        }, (error) => {
          this.isLoadingApproveButton = false;
          this.alertService.showToaster("Please contact system administrator",
            SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
        });
      }


    }
  }

  getACAELoanAccountData(gridData: any) {
    this.loanDetails = [];
    let dataRQ = {
      "requestId": gridData.refNumber,
      "accountId": gridData.accountNumber,
    }
    this.isLoanDetailLoading = true;
    this.acaePaperService.getACAELoanAccountsService(dataRQ).subscribe((response: any) => {
      this.isLoanDetailLoading = false;
      if (response) {
        this.loanDetails = response["successResponse"] ? response["successResponse"] : [];
      }
    }, (error) => {
      this.isLoanDetailLoading = false;
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    });
  }

  //get all acae papers for page navigation
  getAllACAEPapers(acaeStatusNo: any) {
    let dataRQ = {
      userId: this.applicationService.getLoggedInUserUserID(),
      acaeStatus: acaeStatusNo,
      solId: this.applicationService.getLoggedInUserSolID(),
    }
    //check still loading all acae papers
    this.isLoadingAllACAEPapers = true;
    this.acaeBaseService.getAllACAEListByStatusService(dataRQ).subscribe((res: any) => {
      this.isLoadingAllACAEPapers = false;
      this.acaeAllData = res
      this.getSpesificPaperIndex(res)
      this.acaeSharedService.setACAEAllData(res);
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  //get specific index number in grid when acae paper clicked
  getSpesificPaperIndex(acaeAllData: any) {
    let data = acaeAllData.find((data: any) => {
      return data.refNumber === this.commonDetails["refNumber"] && data.accountNumber === this.commonDetails["accountNumber"]
    });
    this.acaeSharedService.setPaperIndex(data.recordIndex);
  }

  //get specific acae paper grid details
  loadSpesificPaper(gridData: any) {
    this.loadActiveComment(gridData);
    this.loadCustomerInformationDetails(gridData);
    this.getCurrentUser(gridData);
    this.loadAccountBalanceDetails(gridData);
    this.getBasicOutstandingData(gridData);
    this.getRelatedAccountData(gridData);
    this.getACAELoanAccountData(gridData);
    this.loadBalancesAfterPaymentDetails(gridData);
    this.loadUserCommentDetails(gridData);
  }

  async loadCustomerInformationDetails(item: any) {
    await this.acaePaperService.getACAECustomerDetailService(item).subscribe((response: any) => {
      if (_.isEmpty(response)) {
        this.advanceCusDetails = {}
        this.activeAnticipatedDateStr = null;
        this.remarkForm.reset({
          anticipatedDateStr: [""],
        });
        this.remarkForm.reset({
          anticipatedDateStr: '',
        }, { onlySelf: false, emitEvent: true });
        this.numberOfClassifiedDays = 0;
      } else {
        this.advanceCusDetails = response;
        this.activeAnticipatedDateStr = response["negDate"];

        //get numberOfClassifiedDays
        try {
          if (response["reviewingDate"] && response["classificationDate"]) {
            const acaeDate = new Date(response["reviewingDate"]);
            const classificationDate = new Date(response["classificationDate"]);
            const diffInMillis = acaeDate.getTime() - classificationDate.getTime();
            const diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
            this.numberOfClassifiedDays = diffInDays;
          }
        } catch (error) {
          console.error("Error calculating date difference:", error);
          this.numberOfClassifiedDays = null;
        }

        this.remarkForm.get('anticipatedDateStr').setValue(response["negDate"]);
      }
    });
  }

  async loadAccountBalanceDetails(gridData: any) {
    this.accountBalanceDetails = {}
    this.isLoadingAccountBalance = true;
    this.isLoadingApproveButton = true;
    await this.acaePaperService.getAccountBalanceDetailsService(gridData).subscribe((response: any) => {
      this.isLoadingAccountBalance = false;
      this.isLoadingApproveButton = false;
      if (_.isEmpty(response)) {
        this.accountBalanceDetails = {}
      } else {
        this.accountBalanceDetails = response
        this.canbeApprovedforDA()
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      this.isLoadingAccountBalance = false;
      this.isLoadingApproveButton = false;
    });
  }

  loadActiveComment(gridData: any) {
    let dataRQ = {
      "referenceNumber": gridData.refNumber,
      "accountNumber": gridData.accountNumber,
    }
    this.isActiveCommentLoading = true;
    this.acaePaperService.getActiveCommentService(dataRQ).subscribe((response: any) => {
      this.isActiveCommentLoading = false;
      if (response == "-") {
        this.activeComment = "";
        return this.remarkForm.reset({
          remark: "",
        });
      } else {
        this.activeComment = response;
        this.isCommentSave = true;
        this.isCommentEdited = true;
        return this.formBuilder.group({
          remark: [response],
        });
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    });
  }

  //get own account OutstandingData
  getBasicOutstandingData = (gridData: any) => {
    let outstandingRQ = {
      "refNumber": gridData.refNumber,
      "accountNumber": gridData.accountNumber,
    }
    this.isBasicOutstandingLoading = true;
    this.acaePaperService.getACAEBasicOutstandingService(outstandingRQ).subscribe((response: any) => {
      this.isBasicOutstandingLoading = false;
      if (response && response.length > 0) {
        this.isBasicOutstandingAvailable = true;
        let tempOutstandingData: any = [];

        if (response[0]["isSameAcct"] === true) {
          this.isFirstOutstanding = true;
          this.firstOutstandingData = response[0];
          for (var i = 1; i < response.length; i++) {
            tempOutstandingData.push(response[i])
          }
          this.outstandingData = tempOutstandingData;
        } else {
          this.isFirstOutstanding = false;
          this.firstOutstandingData = {};
          for (var i = 0; i < response.length; i++) {
            tempOutstandingData.push(response[i])
          }
          this.outstandingData = tempOutstandingData;
        }
      } else {
        this.isBasicOutstandingAvailable = false;
        this.isFirstOutstanding = false;
        this.firstOutstandingData = {};
        this.outstandingData = [];
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    });
  }


  getAdvanceOutstandingData = (gridData: any) => {
    this.isOutstandingLoading = true;
    let outstandingRQ = {
      "refNumber": this.commonDetails["refNumber"],
      "accountNumber": gridData.acctNum,
    }
    this.isAdvanceOutstandingLoading = true;
    this.acaePaperService.getACAEAdvanceOutstandingService(outstandingRQ).subscribe((response: any) => {
      this.isAdvanceOutstandingLoading = false;
      if (response) {
        this.advancerOutstandingData = response;
        this.outstandingData[gridData.index]['statByAcctData'] = response
        this.isAdvanceOutstandingAvailable = true;
      } else {
        this.advancerOutstandingData = [];
        this.isAdvanceOutstandingAvailable = false;
      }
    }, (error) => {
      this.isAdvanceOutstandingLoading = false;
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    });
  }

  getRelatedAccountData = (gridData: any) => {
    this.relatedAccountData = [];
    let relatedRQ = {
      "refNumber": gridData.refNumber,
      "accountNumber": gridData.accountNumber,
    }
    this.isRelatedDetailLoading = true;
    this.acaePaperService.getACAERelatedAccountService(relatedRQ).subscribe((response: any) => {
      this.isRelatedDetailLoading = false;
      if (response) {
        this.relatedAccountData = response;
      } else {
        this.relatedAccountData = [];
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
    });
  }

  loadBalancesAfterPaymentDetails(item: { accountNumber: any; refNumber: any; }) {
    this.balancesAfterPaymentData = []
    this.isLoadingBalancesAfterPayment = true;
    this.isLoadingApproveButton = true;
    this.acaePaperService.getBalancesAfterPaymentService(
      {
        "accountNumber": item.accountNumber,
        "referenceNumber": item.refNumber
      }).subscribe((response: any) => {
        this.isLoadingBalancesAfterPayment = false;
        this.isLoadingApproveButton = false;
        if (_.isEmpty(response)) {
          this.balancesAfterPaymentData = []
        } else {
          this.balancesAfterPaymentData = response[0]
        }
      }, (error) => {
        this.alertService.showToaster("Please contact system administrator",
          SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
        this.isLoadingBalancesAfterPayment = false;
        this.isLoadingApproveButton = false;
      });
  }

  // Function to get the first date of the week
  getFirstDateOfWeek() {
    const today = new Date();
    const firstDayOfWeek = new Date(today.setDate(today.getDate() - today.getDay()));
    return this.formatDate(firstDayOfWeek);
  }

  // Function to get the end date of the week
  getEndDateOfWeek() {
    const today = new Date();
    const lastDayOfWeek = new Date(today.setDate(today.getDate() - today.getDay() + 6));
    return this.formatDate(lastDayOfWeek);
  }

  // Function to format date as "YYYY-MM-DD"
  formatDate(date: Date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  async loadUserCommentDetails(item: any) {
    this.userCommentList = []
    // Get the first and end date of the week
    const firstDateOfWeek = this.getFirstDateOfWeek();
    const endDateOfWeek = this.getEndDateOfWeek();

    let dataRQ = {
      "referenceNumber": item.refNumber,
      "accountNumber": item.accountNumber,
      "fromDate": firstDateOfWeek,
      "toDate": endDateOfWeek
    }
    this.isLoadingUserComment = true;
    await this.acaePaperService.getUserCommentService(dataRQ).subscribe((response: any) => {
      this.isLoadingUserComment = false;
      if (_.isEmpty(response)) {
        this.userCommentList = []
      } else {
        var result: any[] = response.map((r: any, i: number) => ({
          ...r,
          remark: r.remark,
          remardDate: r.remardDate,
          // remardDate: moment(r.remardDate).format("Do MMMM YYYY, h:mm:ss a"),
          regDate: r.regDate,
          remStatus: r.remStatus,
          firstName: r.firstName,
          lastName: r.lastName,
        }));
        this.userCommentList = result
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  getforwardUserGroupValue = async (value: any) => {
    this.forwardUserLOV = [];
    this.forwardUserLOV = await this.loadForwardOrRejectedLOV(value)
  }

  getRejectUserGroupValue = async (value: any) => {
    this.rejectUserLOV = [];
    this.rejectUserLOV = await this.loadForwardOrRejectedLOV(value)
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
        userList.push({
          value: user.userID,
          label: user.userID ? user.firstName + '  ' + user.lastName : "No Users"
        });

      }
    });
    return userList;
  }

  addComment() {
    if (this.isCommentEdited) {
      this.commentSaveService("");
    }
  }

  commentSaveService(status: string): string {
    let { anticipatedDateStr } = this.remarkForm.getRawValue();
    this.anticipatedDate = anticipatedDateStr ? anticipatedDateStr : this.activeAnticipatedDateStr;

    if (this.remark == "" && this.activeComment == "" && status !== "approve") {
      this.alertService.showToaster("Please add a comment!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      return;
    } if (this.anticipatedDate == null && this.acaeStatusNo === Constants.acaeStatusNo.DRAFT) {
      this.alertService.showToaster("Please specify the date!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      return;
    } else {
      let dataRQ = {}
      if (status !== "approve") {
        dataRQ = {
          referenceNumber: this.commonDetails["refNumber"],
          accountNumber: this.commonDetails["accountNumber"],
          activeComment: this.remark ? this.remark : this.activeComment,
          negDate: this.anticipatedDate,
          previousNegDate: this.commonDetails["receivedDate"],
        }
        this.comment = this.remark ? this.remark : this.activeComment;
      } else {
        //save comment when approve paper
        dataRQ = {
          referenceNumber: this.commonDetails["refNumber"],
          accountNumber: this.commonDetails["accountNumber"],
          activeComment: this.remark !== "" ? this.remark : this.activeComment !== "" ? this.activeComment : "Approved",
          negDate: this.anticipatedDate,
          previousNegDate: this.commonDetails["receivedDate"],
        }
        this.comment = this.remark !== "" ? this.remark : this.activeComment !== "" ? this.activeComment : "Approved";
      }
      this.acaePaperService.saveACAECommentService(dataRQ).then((response: any) => {
        if (response) {
          this.alertService.showToaster("Comment added successfully",
            SETTINGS.TOASTER_MESSAGES.success, { timeOut: Constants.toastMessageTimeout.MEDIUM })
          this.isCommentSave = true;
          //status approve
          if (status === "approve") {
            this.doApproveACAEPaper();
          }
          //get the next paper when manager batch process doing.
          if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER) {
            this.getSpecificACAEPaper("next", "mgrbatchproc");
          }
          //get count data and grid  data loading background
          this.refreshAction.next(true);
        }
      }).catch((error) => {
        this.alertService.showToaster("Comment Added Failed!",
          SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      });
    }
  }

  openCloseConformDialog() {
    this.approveModalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Information",
        message: "Do you need to close the Information ?",
      }
    });
    this.approveModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.onCloseModel();
        window.location.reload();
      }
    });
  }

  //automatically scrolling top to get details
  scrollToTop() {
    let child: any = document.getElementById('overflow-edit-status-model')
    child.scrollTop = 0;
    child.style.scrollBehavior = "smooth";
  }

  doApproveACAEPaper() {
    let dataRQ: IAcaePaperApproveRQ = {
      referenceNumber: this.commonDetails["refNumber"],
      accountNumber: this.commonDetails["accountNumber"],
      sanctionLimit: this.accountBalanceDetails["sanctionLimit"],
      currentUsername: this.applicationService.getLoggedInUserDisplayName()
    }
    this.isLoadingApproveButton = true;
    this.acaePaperService.approveACAEPaperService(dataRQ).then((response: any) => {
      if (response) {
        this.isLoadingApproveButton = false;
        this.refreshAction.next(true);
        //get next paper after approving
        this.getSpecificACAEPaper("next", 'approve')
        this.alertService.showToaster("Approved successfully!", SETTINGS.TOASTER_MESSAGES.success,
          { timeOut: Constants.toastMessageTimeout.MEDIUM })
      }
    }, (error) => {
      this.isLoadingApproveButton = false;
      this.alertService.showToaster("Approved Failed!", SETTINGS.TOASTER_MESSAGES.error,
        { timeOut: Constants.toastMessageTimeout.MEDIUM })
    });
  }

  openApproveConformDialog() {
    this.approveModalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "ACAE Paper Approval",
        message: "Do you want to approve this " + this.commonDetails["accountNumber"] + " Paper ?",
      }
    });
    this.approveModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.commentSaveService("approve");
      }
    });
  }

  ngOnDestroy(): void {
    this.onForwardUserGroupChangeSub.unsubscribe();
    this.onRejectUserGroupChangeSub.unsubscribe();
  }

  getCurrencyFormat(amount: any) {
    return this.currencyPipe.transform(amount, '', '')
  }

  openModalACAETransferDetails(status: any) {
    if (this.remark == "" && this.activeComment == "") {
      this.alertService.showToaster("Add Comment First!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    } else {
      this.openTransferModel(status)
    }
  }

  openTransferModel(status: any) {
    const initialState = {
      "status": status,
      "anticipatedDate": this.remarkForm.getRawValue().anticipatedDateStr ?
        this.remarkForm.getRawValue().anticipatedDateStr : this.activeAnticipatedDateStr,
      "comment": this.remark !== "" ? this.remark : this.activeComment,
      "gridData": this.commonDetails,
      "getSpecificACAEPaper": this.getSpecificACAEPaper,
      "scrollToTop": this.scrollToTop,
      "isCommentEdited": this.isCommentEdited,
    };
    this.acaeTransferModelRef = this.mdbModalService.show(ACAEPaperTransferModelComponent, {
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
          acaeDetailsEditmodalRef: this.acaeDetailsEditmodalRef,
          initialState: initialState,
        },
      },
    });
    this.acaeTransferModelRef.content.specificACAEPaperAction.subscribe((data: any) => {
      if (data) {
        this.getSpecificACAEPaper("next", 'transfer')
      }
    })
    this.acaeTransferModelRef.content.refreshGridAction.subscribe((data: any) => {
      if (data) {
        this.refreshAction.next(true);
      }
    })
  }

  //get DA LImit condition for approve 
  canbeApprovedforDA() {
    let sanctionLimit: number = 0
    let levelDA: number = 0;
    let accountClearBalance: number = 0;
    let canbeApproved = false;
    try {

      sanctionLimit = parseFloat(this.accountBalanceDetails["sanctionLimit"]);
      levelDA = parseFloat(this.acaeAllowedApproveLimit)

      accountClearBalance = Math.min(accountClearBalance, this.balancesAfterPaymentData.vbal_1);
      accountClearBalance = Math.min(accountClearBalance, this.balancesAfterPaymentData.vbal_2);
      accountClearBalance = Math.min(accountClearBalance, this.balancesAfterPaymentData.vbal_3);
      accountClearBalance = Math.min(accountClearBalance, this.balancesAfterPaymentData.vbal_4);
      accountClearBalance = Math.min(accountClearBalance, this.balancesAfterPaymentData.vbal_5);
      accountClearBalance = Math.min(accountClearBalance, this.balancesAfterPaymentData.vbal_6);

      canbeApproved = ((accountClearBalance + sanctionLimit + levelDA) >= 0);

    } catch (error) {
      console.error("Error calculating date difference:", error);
      canbeApproved = false;
    }

    if (canbeApproved) {
      return true;
    } else {
      return false;
    }
  }

  //Condition for AnticipatedDate Visible
  isAnticipatedDateVisible() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() >= Constants.applicationSecurityWorkClass.MANAGER
      && this.acaeStatusNo != Constants.acaeStatusNo.DRAFT) {
      return true;
    } else {
      return false;
    }
  }

  //Condition for AnticipatedDate Editable
  isAnticipatedDateEditable() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER
      && this.acaeStatusNo === Constants.acaeStatusNo.DRAFT) {
      return true;
    } else {
      return false;
    }
  }

  //Condition for Comment Section Enable
  isCommentSectionEnable() {
    if (this.acaeTabStatus == Constants.acaeStatusConst.NEW) {
      return true;
    } else {
      return false;
    }
  }

  isACAECommentGridEnable() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.RM) {
      return true;
    } if (this.applicationService.getLoggedInUserUPMGroupCode() > Constants.applicationSecurityWorkClass.MANAGER) {
      return true;
    } else {
      return false;
    }
  }

  isACAEForwardPaperEnable() {
    if (this.acaeStatusNo == Constants.acaeStatusNo.DRAFT &&
      this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.MANAGER) {
      return false;
    }
    if (this.acaeTabStatus == Constants.acaeStatusConst.NEW) {
      return true;
    } else {
      return false;
    }
  }

  isACAEToBeResubmittedEnable() {
    if (
      (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.RM) &&
      (this.acaeTabStatus == Constants.acaeStatusConst.NEW)) {
      return true;
    } else {
      return false;
    }
  }

  isACAERejectEnable() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() != Constants.applicationSecurityWorkClass.RM
      && this.applicationService.getLoggedInUserUPMGroupCode() != Constants.applicationSecurityWorkClass.MANAGER
      && (this.acaeTabStatus == Constants.acaeStatusConst.NEW)) {
      return true;
    } else {
      return false;
    }
  }

  isACAEApproveEnable() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() >= Constants.applicationSecurityWorkClass.RM
      && (this.acaeTabStatus == Constants.acaeStatusConst.NEW)
      && this.canbeApprovedforDA()
    ) {
      return true;
    } else {
      return false;
    }
  }

  //check navigation enable
  isPaperNavigationEnable() {
    if (this.condition == "transferOption" || this.condition == "inquiry") {
      return false;
    } else {
      return true;
    }
  }

  isCommentNotAdded() {
    if (this.activeComment == "" && this.remark == "") {
      return true
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

  getRefreshComment($event: String) {
    if ($event.length > 998) {
      this.alertService.showToaster("Comment is too long!", SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM })
      return;
    } else {
      this.remark = $event
      this.isCommentSave = false;
      this.isCommentEdited = true;
      if ($event.length === 1) {
        this.autocomplete(document.getElementById('myInput'), this.suggestCommentArr, "firstSug");
      } else if ($event.length > 1 && $event.length <= 4) {
        this.autocomplete(document.getElementById('myInput'), this.suggestCommentArr, "moreSug");
      } else {
        this.clearAllSuggestion();
      }
    }
  }

  clearAllSuggestion() {
    document.getElementById("myInputautocomplete-list") ? document.getElementById("myInputautocomplete-list").remove() : "";
  }

  autocomplete(inp: any, arr: any, condition: string) {
    var currentFocus: number = 0;

    inp.addEventListener("input", (e: any) => {
      var a, b, i, val = e.data;
      this.closeAllLists();
      if (!val) {
        return false;
      }
      a = document.createElement("DIV");

      a.style.cursor = "pointer";
      a.style.position = "absolute";

      a.style.boxShadow = "0px 8px 16px 0px rgba(0, 0, 0, 0.2)";
      a.style.width = "100%";
      a.style.backdrop = 'static'
      a.style.ignoreBackdropClick = true;
      a.style.zIndex = 2;

      a.setAttribute("id", inp.id + "autocomplete-list");
      a.setAttribute("class", "autocomplete-items");

      let parent = (<HTMLElement>(<HTMLElement>e.target).parentNode);
      parent.appendChild(a);

      if (condition == "firstSug") {
        for (i = 0; i < arr.length; i++) {

          b = document.createElement("DIV");

          b.style.border = "1px solid #d4d4d4"
          b.style.borderRadius = "0 0 2px 2px";
          b.style.backgroundColor = "white"
          b.style.backdrop = 'static'
          b.style.ignoreBackdropClick = true;
          b.style.zIndex = 2;

          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
          b.innerHTML += arr[i].substr(val.length);
          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";

          b.addEventListener("click", (e: any) => {

            let elementTarget = (<HTMLElement>(<HTMLElement>e.target));
            inp.value = elementTarget.getElementsByTagName("input")[0].value;
            this.remark = elementTarget.getElementsByTagName("input")[0].value;
            this.closeAllLists();
            currentFocus = 0;

          });
          a.appendChild(b);
        }
      } else {
        for (i = 0; i < arr.length; i++) {

          if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {

            b = document.createElement("DIV");

            b.style.border = "1px solid #d4d4d4"
            b.style.borderRadius = "0 0 2px 2px";
            b.style.backgroundColor = "white"
            b.style.backdrop = 'static'
            b.style.ignoreBackdropClick = true;
            b.style.zIndex = 2;

            b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
            b.innerHTML += arr[i].substr(val.length);
            b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";

            b.addEventListener("click", (e) => {

              let elementTarget = (<HTMLElement>(<HTMLElement>e.target));
              inp.value = elementTarget.getElementsByTagName("input")[0].value;
              this.remark = elementTarget.getElementsByTagName("input")[0].value;
              this.closeAllLists();
              currentFocus = 0;

            });
            a.appendChild(b);
          }
        }
      }
    });

    inp.addEventListener("keydown", (e: any) => {
      var x: any = document.getElementById(inp.id + "autocomplete-list");

      if (x) x = x.getElementsByTagName("div");

      if (e.keyCode == 40) {
        currentFocus++;
        this.addActive(x, currentFocus);
      } else if (e.keyCode == 38) {
        currentFocus--;
        this.addActive(x, currentFocus);
      } else if (e.keyCode == 13) {
        e.preventDefault();
        if (currentFocus > -1) {
          if (x) {
            inp.value = arr[currentFocus];
            this.remark = arr[currentFocus];
            this.closeAllLists();
            currentFocus = 0;
          }
        }
      }
    });
  }

  closeAllLists(elmnt?: any) {
    var x = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < x.length; i++) {
      if (elmnt != x[i] && elmnt != document.getElementById('myInput')) {
        x[i].parentNode.removeChild(x[i]);
      }
    }
  }

  addActive(x: any, currentFocus: any) {
    if (!x) return false;
    this.removeActive(x);
    if (currentFocus >= x.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = (x.length - 1);

    x[currentFocus].classList.add("autocomplete-active");
    x[currentFocus].style.backgroundColor = "DodgerBlue"
  }

  removeActive(x: any) {
    for (var i = 0; i < x.length; i++) {
      x[i].classList.remove("autocomplete-active");
      x[i].style.backgroundColor = "white"
    }
  }

  ngOnChanges(_changes: SimpleChanges): void {
  }

  onCloseModel() {
    this.acaeDetailsEditmodalRef.hide();
  }

  clearComment() {
    this.remarkForm.reset({
      anticipatedDateStr: [{}],
    });
    this.remark = "";
    return this.remarkForm.reset({
      remark: [""],
    });
  }

  //get acae paper grid details when paper navigation
  getSpecificACAEPaper(status: String, type: string) {
    let acaeAllData = this.acaeSharedService.getACAEAllData();
    let paperIndex = this.acaeSharedService.getPaperIndex();
    let allPaperSize = this.acaeSharedService.getAllPaperSize();
    if (acaeAllData.length > 0) {
      //get first paper in batch
      if (status == 'first') {
        if (paperIndex < 0) {
          this.alertService.showToaster("No ACAE Paper Found!",
            SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM })
          return;
        } else {
          this.commonDetails = {
            "accountName": acaeAllData[0].customerName,
            "accountNumber": acaeAllData[0].accountNumber,
            "receivedDate": acaeAllData[0].recievedDate,
            "refNumber": acaeAllData[0].refNumber,
            "viewOnly": this.viewOnly,
          }
          this.loadSpesificPaper(this.commonDetails);
          this.scrollToTop();
          this.acaeSharedService.setPaperIndex(0);
          this.alertService.showToaster("First paper was Loaded!",
            SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM })
        }
        //get previous paper in batch
      } else if (status == 'previous') {
        if (paperIndex - 1 < 0) {
          this.alertService.showToaster("No ACAE Paper Found!",
            SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM })
          return;
        } else {
          this.commonDetails = {
            "accountName": acaeAllData[paperIndex - 1].customerName,
            "accountNumber": acaeAllData[paperIndex - 1].accountNumber,
            "receivedDate": acaeAllData[paperIndex - 1].recievedDate,
            "refNumber": acaeAllData[paperIndex - 1].refNumber,
            "viewOnly": this.viewOnly,
          }
          this.loadSpesificPaper(this.commonDetails);
          this.scrollToTop();
          this.acaeSharedService.setPaperIndex(paperIndex - 1);
          this.alertService.showToaster("Previous paper was Loaded!",
            SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM });
        }
        //get next paper in batch
      } else if (status == 'next') {
        if (paperIndex + 1 >= allPaperSize) {
          this.openCloseConformDialog();
          if (type == "mgrbatchproc") {
            this.alertService.showToaster("All Records Completed.",
              SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM });
            this.alertService.showToaster("Ready to forward to the next authority !",
              SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM });
          }
          return;
        } else {
          this.commonDetails = {
            "accountName": acaeAllData[paperIndex + 1].customerName,
            "accountNumber": acaeAllData[paperIndex + 1].accountNumber,
            "receivedDate": acaeAllData[paperIndex + 1].recievedDate,
            "refNumber": acaeAllData[paperIndex + 1].refNumber,
            "viewOnly": this.viewOnly,
          }
          this.loadSpesificPaper(this.commonDetails);
          this.scrollToTop();
          this.acaeSharedService.setPaperIndex(paperIndex + 1);
          this.alertService.showToaster("Next paper was Loaded!",
            SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM });
        }
        //get last paper in batch
      } else {
        this.commonDetails = {
          "accountName": acaeAllData[allPaperSize - 1].customerName,
          "accountNumber": acaeAllData[allPaperSize - 1].accountNumber,
          "receivedDate": acaeAllData[allPaperSize - 1].recievedDate,
          "refNumber": acaeAllData[allPaperSize - 1].refNumber,
          "viewOnly": this.viewOnly,
        }
        this.loadSpesificPaper(this.commonDetails);
        this.scrollToTop();
        this.acaeSharedService.setPaperIndex(allPaperSize - 1);
        this.alertService.showToaster("Last paper was Loaded!",
          SETTINGS.TOASTER_MESSAGES.info, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      }
      this.activeComment = "";
      this.remark = "";
      this.isCommentEdited = false;
      this.isCommentSave = false;
    }
  }
}