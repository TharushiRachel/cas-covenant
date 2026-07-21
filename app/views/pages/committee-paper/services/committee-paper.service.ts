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
export class CommitteePaperService implements Resolve<any> {


  uniquePageName = 'CommitteePaperComponent-#343rta';
  selectedFacilityPapers: any = [];
  onSelectedFacilityPaperChange: BehaviorSubject<any> = new BehaviorSubject({});

  facilityPaperCounts: any = [];
  onCommitteePaperCountsChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private cacheService: CacheService,
    private applicationService: ApplicationService,
  ) {
  }

   resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
     return new Promise((resolve, reject) => {
       Promise.all([
          this.cacheService.loadData(Constants.masterDataKey.CAS_COMMITTEE_TYPE_LIST),
          this.cacheService.loadData(Constants.masterDataKey.CAS_BCC_ENTERER_WORK_CLASS),
          this.cacheService.loadData(Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS),
          this.cacheService.loadData(Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS),
          //this.cacheService.loadData(Constants.masterDataKey.CAS_COMMITTEE_LIST)
       ]).then(
         () => {
           resolve();
         },
         reject
       );
     });
   }


  getCommitteePaperCounts(searchRQ): Promise<any> {
      return new Promise((resolve, reject) => {
        this.dataService.post(SETTINGS.ENDPOINTS.getCommitteePaperDashboardCount, searchRQ)
          .subscribe((response: any) => {
              this.onCommitteePaperCountsChange.next(response);
            resolve(response);
          }, reject)
      })
    }

  getCommitteePaperByStatus(searchData?, paginationData?: Pagination): Promise<any> {
        return this.searchCommitteePaperByStatus(searchData, paginationData)
   }


  searchCommitteePaperByStatus(searchData?, paginationData?: Pagination): Promise<any> {
     if (!searchData) {
       searchData = {};
     }
     return new Promise<any>((resolve, reject) => {
       this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedCommitteePaperDashboard, searchData, paginationData)
         .subscribe((response: any) => {
           resolve(response);
         }, reject)
     })
   }

   getBCCPaperCounts(searchRQ): Promise<any> {
       return new Promise((resolve, reject) => {
         this.dataService.post(SETTINGS.ENDPOINTS.getBCCPaperDashboardCount, searchRQ)
           .subscribe((response: any) => {
               this.onCommitteePaperCountsChange.next(response);
             resolve(response);
           }, reject)
       })
    }

    getBCCPaperByStatus(searchData?, paginationData?: Pagination): Promise<any> {
          return this.searchBCCPaperByStatus(searchData, paginationData)
     }

    searchBCCPaperByStatus(searchData?, paginationData?: Pagination): Promise<any> {
       if (!searchData) {
         searchData = {};
       }
       return new Promise<any>((resolve, reject) => {
         this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedBCCPaperDashboard, searchData, paginationData)
           .subscribe((response: any) => {
             resolve(response);
           }, reject)
       })
     }

}
