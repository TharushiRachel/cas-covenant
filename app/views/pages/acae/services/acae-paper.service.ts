import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { CacheService } from "src/app/core/service/data/cache.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { IACAEAccountTypeRQ, IAcaePaperApproveRQ } from "../interfaces/acae-interface";


@Injectable()
export class ACAEPaperService {

  constructor(
    private dataService: DataService,
  ) { }

  onACAECustomerDetailChange = new BehaviorSubject({});
  onACAEOutstandingDetailChange = new BehaviorSubject({});
  onACAERelatedAccountDetailChange = new BehaviorSubject({});
  onACAELoanAccountDetailChange = new BehaviorSubject({});
  onACAEUserCommentChange = new BehaviorSubject({});
  onACAEBalancesAfterPaymentChange = new BehaviorSubject({});

  onACAEForwardUserGroupLOVChange = new BehaviorSubject({});
  onACAERejectUserGroupLOVChange = new BehaviorSubject({});

  onACAEForwardUserLOVChange = new BehaviorSubject({});
  onACAERejectUserLOVChange = new BehaviorSubject({});

  onsaveACAECommentChange = new BehaviorSubject({});
  onACAEActiveCommentChange = new BehaviorSubject({});
  onACAEApproveChange = new BehaviorSubject({});
  onACAEForwardChange = new BehaviorSubject({});
  onACAERejectChange = new BehaviorSubject({});
  onACAEAllowedLimitChange = new BehaviorSubject({});

  onFinacleIdFromAccountNumberChange = new BehaviorSubject({});

  onACAEBatchForwardChange = new BehaviorSubject({});
  onDAClearBalanceChange = new BehaviorSubject({});

  acaeCustomerDetails: any = [];
  acaeOutstandingDetail: any = [];
  acaeRelatedAccountDetail: any = [];
  acaeLoanAccountDetail: any = [];
  acaeUserCommentDetail: any = [];
  acaeForwardLOVDetail: any = [];
  acaeRejectLOVDetail: any = [];
  acaeBalanceAfterPaymentDetail: any = []
  acaeActiveCommentDetail: any = {};
  finacleIdFromAccountNumberDetail: any = {};
  allowedLimit: number = 0

  acaeApproveDetail: any = {};

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }

  getACAELoanAccountsDetails(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAELoanAccountsDetails, data);
  }

  getACAELoanAccountsService(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAELoanAccounts, data);
  }

  getACAECustomerDetailService(acaeCustomerDetailsRQ) {
    let data = {
      refId: acaeCustomerDetailsRQ.refNumber,
      AccountNo: acaeCustomerDetailsRQ.accountNumber,
    }
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAECustomerDetails, data);
  }

  getAccountBalanceDetailsService(acaeCustomerDetailsRQ) {
    let data = {
      refId: acaeCustomerDetailsRQ.refNumber,
      AccountNo: acaeCustomerDetailsRQ.accountNumber,
    }
    return this.dataService.post(SETTINGS.ENDPOINTS.getAccountBalanceDetails, data);
  }

  getFinacleIdFromAccountNumberService(finacleIdRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getFinacleIdFromAccountNumber, finacleIdRQ);
  }

  getBalancesAfterPaymentService(acaeBalanceAfterPaymentRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAEBalanceAfterPayment, acaeBalanceAfterPaymentRQ);
  }

  getUserCommentService(acaeUserCommentsRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAEUserComments, acaeUserCommentsRQ);
  }

  getActiveCommentService(acaeUserCommentsRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAEActiveComment, acaeUserCommentsRQ);
  }

  getACAEForwardUserGroupLOVService(loggedInUserWorkFlowByStatusRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAELowerOrHigherUserGroupLOV, loggedInUserWorkFlowByStatusRQ);
  }

  getACAERejectUserGroupLOVService(loggedInUserWorkFlowByStatusRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAELowerOrHigherUserGroupLOV, loggedInUserWorkFlowByStatusRQ);
  }

  getACAETransferUserListService(loggedInUserWorkFlowByStatusRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAETransferUserList, loggedInUserWorkFlowByStatusRQ);
  }

  getACAEForwardLOVService(branchAuthorityLevelRQ, callback) {
    this.dataService.post(SETTINGS.ENDPOINTS.getUserDetailListFormBranchAuthorityLevel, branchAuthorityLevelRQ)
      .subscribe((response: any) => {
        this.onACAEForwardUserLOVChange.next(response.branchAuthorityLevelResponseDTOList);

        if (callback) {
          callback(response.branchAuthorityLevelResponseDTOList);
        }
      }, (error: any) => {
        console.error("Error fetching covenant list:", error);

        if (callback) {
          callback(null, error);
        }
      });
  }

  getACAERejectLOVService(branchAuthorityLevelRQ, callback) {
    this.dataService.post(SETTINGS.ENDPOINTS.getUserDetailListFormBranchAuthorityLevel, branchAuthorityLevelRQ)
      .subscribe((response: any) => {
        this.onACAERejectUserLOVChange.next(response.branchAuthorityLevelResponseDTOList);

        if (callback) {
          callback(response.branchAuthorityLevelResponseDTOList);
        }
      }, (error: any) => {
        console.error("Error fetching covenant list:", error);

        if (callback) {
          callback(null, error);
        }
      });
  }

  getDAClearBalance(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getDAClearBalance
      );
      data.url = data.url;
      this.dataService.get(data).subscribe((response: any) => {
        console.log("da limit", response)
        this.onDAClearBalanceChange.next(response);
        resolve(response);
      }, reject);
    });
  }

  approveACAEPaperService(acaePaperApproveRQ: IAcaePaperApproveRQ): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.approveACAEPaper, acaePaperApproveRQ).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

  getCurrentUserService(dataRQ: IACAEAccountTypeRQ): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getCurrentUser, dataRQ).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

  forwardACAEPaperService(acaePaperTransferRQ) {
    try {
      this.dataService.post(SETTINGS.ENDPOINTS.forwardACAEPaper, acaePaperTransferRQ)
        .subscribe((response: any) => {
          this.onACAEForwardChange.next(response);
        })
    } catch (e) {
      console.error(`Invalid acae reference ${acaePaperTransferRQ}`);
    }
  }

  saveACAECommentService(acaeCommentRQ: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.saveACAEComment, acaeCommentRQ)
        .subscribe((response: any) => {
          resolve(response);
        }, (error) => {
          reject(error);
        });
    });
    // return this.dataService.post(SETTINGS.ENDPOINTS.saveACAEComment, acaeCommentRQ);
  }

  forwardACAEBatchService(acaeBatchForwardRQ: { referenceId: number; thisUser: string; nextUser: any; status: any; }) {
    return this.dataService.post(SETTINGS.ENDPOINTS.forwardACAEBatch, acaeBatchForwardRQ);
  }

  getIsAttendedService(attendedRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.isACAEAttended, attendedRQ);
  }

  getACAERangeInquiryService(rangeRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAERangeInquiry, rangeRQ);
  }

  getACAEBasicOutstandingService(outstandingRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getBasicACAEOutstanding, outstandingRQ);
  }

  getACAEAdvanceOutstandingService(outstandingRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getAdvanceACAEOutstanding, outstandingRQ);
  }

  getACAERelatedAccountService(relatedRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAERelatedAccounts, relatedRQ);
  }
}

