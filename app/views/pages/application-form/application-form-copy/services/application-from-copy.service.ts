import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {Pagination} from "../../../../../core/dto/pagination";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {DataService} from "../../../../../core/service/data/data.service";
import {Constants} from "../../../../../core/setting/constants";
import {CacheService} from "../../../../../core/service/data/cache.service";

@Injectable()
export class ApplicationFromCopyService implements Resolve<any> {

  uniquePageName = 'ApplicationFormCopyBaseComponent-#aardw';
  applicationForms: any = [];
  onApplicationFormsChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private cacheService: CacheService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {
      Promise.all([
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUPPORTING_DOCs),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SECTOR_DATA),
        this.cacheService.loadData(Constants.masterDataKey.CAS_APPLICATION_FORM_TOPICS),
      ]).then(() => {
        resolve();
      }, reject)
    });
  }

  searchApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {

    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getCopyPagedApplicationForms, searchData, paginationData).subscribe((response: any) => {
        this.applicationForms = response.pageData;
        this.onApplicationFormsChange.next(response);
        resolve(response);
      }, reject);
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

  replicateApplicationForm(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.replicateApplicationForm, data);
  }

}
