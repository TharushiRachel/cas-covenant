import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import { Constants } from 'src/app/core/setting/constants';
import { CacheService } from 'src/app/core/service/data/cache.service';

@Injectable()
export class ApplicationFormService implements Resolve<any> {

  uniquePageName = 'ApplicationFormComponent-#aardw';
  applicationForms: any = [];
  onApplicationFormChange: BehaviorSubject<any> = new BehaviorSubject({});
  onApplicationFormCountsChange: BehaviorSubject<any> = new BehaviorSubject({});
 // applicationFormCounts: any = [];



  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService,
              private cacheService: CacheService,
              public applicationService: ApplicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
      //  this.getApplicationForms(),
        /*this.getApplicationFormCounts({
          loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
          loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode()
        }),*/
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchApplicationForms(searchData, paginationData);
  }

  searchApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {

    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedApplicationForm, searchData, paginationData).subscribe((response: any) => {
        this.applicationForms = response.pageData;
        this.onApplicationFormChange.next(response);
        resolve(response);
      }, reject);
    });
  }

  getApplicationFormByStatus(searchData?, paginationData?: Pagination): Promise<any> {
        return this.searchApplicationFormByStatus(searchData, paginationData)
      }

  searchApplicationFormByStatus(searchData?, paginationData?: Pagination): Promise<any> {
    if (!searchData) {
      searchData = {};
    }
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedApplicationFormDashboard, searchData, paginationData)
        .subscribe((response: any) => {
         /* this.applicationForms = response.pageData;
          this.onApplicationFormChange.next(response);*/
          resolve(response);
        }, reject)
    })
  }

 /*getApplicationFormCounts(searchRQ) {
    this.dataService.post(SETTINGS.ENDPOINTS.getApplicationFormDashboardCount, searchRQ)
      .subscribe(response => {
        console.log("getApplicationFormCounts");
        console.log(response);
        this.onApplicationFormCountsChange.next(response);
      });
  }*/

  getApplicationFormCounts(searchRQ): Promise<any> {
      return new Promise((resolve, reject) => {
        this.dataService.post(SETTINGS.ENDPOINTS.getApplicationFormDashboardCount, searchRQ)
          .subscribe((response: any) => {
              this.onApplicationFormCountsChange.next(response);
            resolve(response);
          }, reject)
      })
    }
}
