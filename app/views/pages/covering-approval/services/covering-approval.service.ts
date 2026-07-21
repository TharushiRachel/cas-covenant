import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { values } from 'lodash';
import { LocalStorage } from 'ngx-webstorage';
import { BehaviorSubject, Observable } from 'rxjs';
import { Pagination } from 'src/app/core/dto/pagination';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { PrivilegeService } from 'src/app/core/service/authentication/privilege.service';
import { SearchDataCacheService } from 'src/app/core/service/common/search-data-cache.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';

@Injectable()
export class CoveringApprovalService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;

  uniquePageName = 'CoveringApprovalDashboardComponent-#aardw';
  coveringApprovals: any = [];
  onApplicationFormChange = new BehaviorSubject({});
  onApplicationFormsChange = new BehaviorSubject({});
  onApplicationFormDraftChange = new BehaviorSubject({});
  onCoveringApprovalPendingChange = new BehaviorSubject({});
  onCoveringApprovalChange = new BehaviorSubject({});
  onChangeLoggedUserName = new BehaviorSubject({});

  //onCoveringApprovalChange: BehaviorSubject<any> = new BehaviorSubject({});
  onCoveringApprovalDetailsChange = new BehaviorSubject({});
  onCoveringApprovalCountsChange: BehaviorSubject<any> = new BehaviorSubject({});
  coveringApprovalCounts: any = [];
  pendingCovCount = 0;
  pendingCoveringApprovalCountChange = new BehaviorSubject('00');

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  coveringApprovalTrandetails = Constants.coveringApprovalTrandetails;

  onUpmGroupChange = new BehaviorSubject({});
  onCACommentsChange = new BehaviorSubject({});

  upmGroups: any = [];
  userDa: any = {};
  covFormDTO: any = {};

  constructor(
    private searchDataCacheService: SearchDataCacheService,
    private dataService: DataService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private urlEncodeService: UrlEncodeService,
    private cacheService: CacheService,
  ) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getCoveringApprovalByID(),
        this.getCoveringApprovalCounts({
          loggedInUserId: this.applicationService.getLoggedInUserUserID(),
          loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode()
        }),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
      ]).then(() => {
        resolve({});
      }, reject)
    });
  }

  getCoveringApprovals(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchCoveringApprovals(searchData, paginationData);
  }


  searchCoveringApprovals(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedCoveringApprovals, searchData, paginationData)
        .subscribe((response: any) => {

          this.coveringApprovals = response.pageData;
          this.onCoveringApprovalChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getCoveringApprovalByStatus(searchData?, paginationData?: Pagination): Promise<any> {
    return this.searchCoveringApprovalByStatus(searchData, paginationData);
  }

  searchCoveringApprovalByStatus(searchData?, paginationData?: Pagination): Promise<any> {
    if (!searchData) {
      searchData = {};
    }

    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedCoveringApprovalDashboard, searchData, paginationData)
        .subscribe((response: any) => {
          //this.onCoveringApprovalCountsChange.next(response);
          resolve(response);
        }, reject)
    });
  }

  getCoveringApprovalCounts(searchRQ): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getCoveringApprovalDashboardCount, searchRQ)
        .subscribe((response: any) => {
          // this.coveringApprovalCounts = response;
          // this.onCoveringApprovalChange.next(this.coveringApprovalCounts);
          this.onCoveringApprovalCountsChange.next(response);
          resolve(response);
        }, reject)
    })

  }

  getTransDetails(transactionId: string, tranDate: string): Promise<any> {
    const payload = {
      reqID: "CAS_0001",
      tranId: transactionId,
      trnDate: tranDate,
      prtsrlS: "1",
      prtsrlE: "2"
    };

    return this.dataService.post(SETTINGS.ENDPOINTS.getTransDetails, payload).toPromise();

  }


  getCustomerBankDetails(accountNo: string): Promise<any> {
    const payload = {
      AccountNo: accountNo
    };

    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getCustomerBankDetails, payload)
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    });
  }


  getPendingCoveringApprovals(searchData, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPendingCoveringApprovals, searchData, paginationData)
        .subscribe((response: any) => {
          //this.onApplicationFormsChange.next(response);
          this.coveringApprovals = response.pageData;
          this.onCoveringApprovalPendingChange.next(response)
          resolve(response);
        }, error => {
          console.error("Error in getPendingCoveringApprovals service:", error);
          reject(error); // Reject promise with the error
        });
    });
  }
  draftApplicationForm(searchData): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.draftApplicationFormCA, searchData)
        .subscribe((response: any) => {
          this.covFormDTO = response;
          this.onCoveringApprovalChange.next(response);
          //this.onCoveringApprovalDetailsChange.next(response);
          resolve(response);
        }, reject)
    })
  }

  getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode, data)
      .subscribe((response: any) => {
        this.upmGroups = response;
        this.onUpmGroupChange.next(response);
      });
  }

  getCOVCommentById(covAppId): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCOVCommentById);
      data.url = data.url + '/' + covAppId;
      this.dataService.get(data)
        .subscribe((response: any) => {
          //this.onApplicationFormChange.next(response);

          resolve(response);

        }, reject);
    })
  }
  getCOVReturnUsersList(covAppId): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCOVReturnUsersList);
      data.url = data.url + '/' + covAppId;
      this.dataService.get(data)
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    })
  }

  getCoveringApprovalByID(): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.selectedCovID == null) {
        this.covFormDTO = {};
        this.onCoveringApprovalChange.next(this.covFormDTO); //6.27
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getCoveringApprovalByID);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedCovID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.covFormDTO = response; //6.27
            this.onCoveringApprovalChange.next(response); //6.27
            //this.onCoveringApprovalPendingChange.next(response)
            resolve(response);

          });
      }
    });
  }
  updateCOVStatus(updateData) {
    return this.dataService.post(SETTINGS.ENDPOINTS.updateCOVStatus, updateData)
  }
  getUserDaByUserName(userName) {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getUserDAByLoggedInUser);
    data.url = data.url + "/" + userName;
    this.dataService.get(data).subscribe((response: any) => {
      this.userDa = response;
      this.onChangeLoggedUserName.next(this.userDa);
    });
  }



}
