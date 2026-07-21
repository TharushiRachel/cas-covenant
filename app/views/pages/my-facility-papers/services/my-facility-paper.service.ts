import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {Constants} from "../../../../core/setting/constants";
import {CacheService} from "../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../core/service/application/application.service";

@Injectable()
export class MyFacilityPapersService implements Resolve<any> {

  uniquePageName = 'MyFacilityPapersComponent-#ayergses656';
  selectedFacilityPapers: any = [];
  onSelectedFacilityPaperChange: BehaviorSubject<any> = new BehaviorSubject({});

  facilityPaperCounts: any = [];
  facilityPaperDashboardCounts: any = {
      draftFacilityPaper: 0,
      inProgressFacilityPaper: 0,
      approvedFacilityPaper: 0,
      rejectedFacilityPaper: 0,
      cancelFacilityPaper: 0,
    };

  onFacilityPaperCountsChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private cacheService: CacheService,
    private applicationService: ApplicationService,
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      let userName = this.applicationService.getLoggedInUserUserName();
      Promise.all([
        this.getFacilityPaperDashboardCounts({
          currentAssignUser: userName,
          intiatedUserName: userName,
          loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
          loggedInUserUPMGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
          assignUsers: this.applicationService.getIsAssistant() ? [this.applicationService.getLoggedInCASUserSupervisorAdUserID()] : null
        }),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
      ]).then(() => {
        resolve()
      }, reject)
    })
  }

  getFacilityPapers(searchData?, paginationData?: Pagination): Promise<any> {
    return this.searchFacilityPapers(searchData, paginationData)
  }

  searchFacilityPapers(searchData?, paginationData?: Pagination): Promise<any> {

    if (!searchData) {
      searchData = {};
    }

    searchData.inboxRequest = 'N';
    searchData.inprogressRequest = 'N';
    searchData.loggedInUserBranchCode = this.applicationService.getLoggedInUserDivCode();
    searchData.loginUpmAccessLevel = this.applicationService.getLoggedInUserUPMGroupCode();

    if (searchData.facilityPaperStatus === Constants.facilityPaperStatusConst.DRAFT) {
      searchData.facilityPaperStatus = null;
      searchData.inboxRequest = 'Y';
    } else if (searchData.facilityPaperStatus === Constants.facilityPaperStatusConst.IN_PROGRESS) {
      searchData.facilityPaperStatus = null;
      searchData.inprogressRequest = 'Y';
    } else if (searchData.facilityPaperStatus === Constants.facilityPaperStatusConst.CANCEL) {
      searchData.returnRequest = 'Y';
    }
    searchData.assignUsers = this.applicationService.getIsAssistant() ? [this.applicationService.getLoggedInCASUserSupervisorAdUserID()] : null;

    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedMyFacilityPaper, searchData, paginationData)
        .subscribe((response: any) => {
          this.selectedFacilityPapers = response.pageData;
          this.onSelectedFacilityPaperChange.next(response);
          resolve(response);
        }, reject)
    })
  }

  getFacilityPaperCounts(searchRQ) {
    this.dataService.post(SETTINGS.ENDPOINTS.getDashboardCount, searchRQ)
      .subscribe(response => {
        this.facilityPaperCounts = response;
        this.onFacilityPaperCountsChange.next(this.facilityPaperCounts);
      });
  }


   getFacilityPaperDashboardCounts(searchRQ) {
       //GET INBOX COUNT
       let searchRQTemp = {};
       searchRQTemp = Object.assign({},searchRQ, {
             facilityPaperStatus: Constants.facilityPaperStatusConst.DRAFT,
             isInboxOnly: 1
       });
       this.searchFacilityPaperForCount(searchRQTemp, new Pagination(event)).then((data: any) =>{
             this.facilityPaperDashboardCounts.draftFacilityPaper = data.totalNoOfRecords;
       });

       //GET IN_PROGRESS COUNT
       searchRQTemp = Object.assign({},searchRQ, {
          facilityPaperStatus: Constants.facilityPaperStatusConst.IN_PROGRESS,
          isInboxOnly: 1
       });
       this.searchFacilityPaperForCount(searchRQTemp, new Pagination(event)).then((data: any) =>{
          this.facilityPaperDashboardCounts.inProgressFacilityPaper = data.totalNoOfRecords;
       });

       //GET RETURNED COUNT
       searchRQTemp = Object.assign({},searchRQ, {
          facilityPaperStatus: Constants.facilityPaperStatusConst.CANCEL,
          isInboxOnly: 1
       });
       this.searchFacilityPaperForCount(searchRQTemp, new Pagination(event)).then((data: any) =>{
          this.facilityPaperDashboardCounts.cancelFacilityPaper = data.totalNoOfRecords;
       });

       //GET APPROVED COUNT
       searchRQTemp = Object.assign({},searchRQ, {
          facilityPaperStatus: Constants.facilityPaperStatusConst.APPROVED,
          isInboxOnly: 1
       });
       this.searchFacilityPaperForCount(searchRQTemp, new Pagination(event)).then((data: any) =>{
          this.facilityPaperDashboardCounts.approvedFacilityPaper = data.totalNoOfRecords;
       });

       //GET DECLINED COUNT
       searchRQTemp = Object.assign({},searchRQ, {
          facilityPaperStatus: Constants.facilityPaperStatusConst.REJECTED,
          isInboxOnly: 1
       });
       this.searchFacilityPaperForCount(searchRQTemp, new Pagination(event)).then((data: any) =>{
          this.facilityPaperDashboardCounts.rejectedFacilityPaper = data.totalNoOfRecords;
       });

       this.onFacilityPaperCountsChange.next(this.facilityPaperDashboardCounts);
   }

   searchFacilityPaperForCount(searchData?, paginationData?: Pagination): Promise<any> {
       if (!searchData) {
         searchData = {};
       }
       searchData.inboxRequest = 'N';
       searchData.inprogressRequest = 'N';
       searchData.loggedInUserBranchCode = this.applicationService.getLoggedInUserDivCode();
       searchData.loginUpmAccessLevel = this.applicationService.getLoggedInUserUPMGroupCode();

       if (searchData.facilityPaperStatus === Constants.facilityPaperStatusConst.DRAFT) {
         searchData.facilityPaperStatus = null;
         searchData.inboxRequest = 'Y';
       } else if (searchData.facilityPaperStatus === Constants.facilityPaperStatusConst.IN_PROGRESS) {
         searchData.facilityPaperStatus = null;
         searchData.inprogressRequest = 'Y';
       } else if (searchData.facilityPaperStatus === Constants.facilityPaperStatusConst.CANCEL) {
         searchData.returnRequest = 'Y';
       }
       searchData.assignUsers = this.applicationService.getIsAssistant() ? [this.applicationService.getLoggedInCASUserSupervisorAdUserID()] : null;

       return new Promise<any>((resolve, reject) => {
         this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedMyFacilityPaper, searchData, paginationData)
           .subscribe((response: any) => {
             resolve(response);
           }, reject)
       })
   }

}
