import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {DataService} from "../../../../core/service/data/data.service";
import {BehaviorSubject, Observable} from "rxjs";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {CacheService} from "../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../core/service/application/application.service";
import { Constants } from 'src/app/core/setting/constants';

@Injectable()
export class LeadsService implements Resolve<any> {

  uniquePageName = 'LeadsComponent-#343rta';
  leads: any = [];
  onLeadsChange: BehaviorSubject<any> = new BehaviorSubject({});

  onLeadCountsChange: BehaviorSubject<any> = new BehaviorSubject({});
  leadCounts: any = [];
  pendingLeadCount = 0;
  onPendingLeadCountChange = new BehaviorSubject('00');


  constructor(
    private searchDataCacheService: SearchDataCacheService,
    private dataService: DataService,
    private cacheService: CacheService,
    private applicationService: ApplicationService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        //this.getLeads(),
        this.getLeadCounts({
            loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
            loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
            loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode()
          }),
          this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
      ]).then(() => {
        resolve();
      }, reject)
    });
  }



  getLeads(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({})
      });
    }
    return this.searchLeads(searchData, paginationData);
  }

  searchLeads(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedLeads, searchData, paginationData)
        .subscribe((response: any) => {
          this.leads = response.pageData;
          this.onLeadsChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getLeadByStatus(searchData?, paginationData?: Pagination): Promise<any> {
      return this.searchLeadByStatus(searchData, paginationData)
    }

  searchLeadByStatus(searchData?, paginationData?: Pagination): Promise<any> {
    if (!searchData) {
      searchData = {};
    }
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedLeadDashboard, searchData, paginationData)
        .subscribe((response: any) => {
         /* this.leads = response.pageData;
          this.onLeadsChange.next(response);*/
          resolve(response);
        }, reject)
    })
  }

  getLeadCounts(searchRQ) {
    this.dataService.post(SETTINGS.ENDPOINTS.getLeadDashboardCount, searchRQ)
      .subscribe(response => {
        this.leadCounts = response;
        this.onLeadCountsChange.next(this.leadCounts);
      });
  }

}
