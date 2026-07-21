import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {BehaviorSubject, Observable} from "rxjs";
import {Pagination} from "../../../../core/dto/pagination";
import {DataService} from "../../../../core/service/data/data.service";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {CacheService} from "../../../../core/service/data/cache.service";
import {Constants} from "../../../../core/setting/constants";

@Injectable()
export class CreditFacilityTypeTemplatesService implements Resolve<any> {

  uniquePageName = 'CreditFacilityTypeTemplatesComponent-#343rta';
  onCreditFacilityTemplateChange: BehaviorSubject<any> = new BehaviorSubject({});
  creditFacilityTemplates: any = [];

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService,
    private cacheService: CacheService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getCreditFacilityTemplates(),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES)
      ]).then(() => {
        resolve();
      }, reject)
    })
  }

  getCreditFacilityTemplates(searchData?, paginationdData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchCreditFacilityTemplates(searchData, paginationdData);
  }

  searchCreditFacilityTemplates(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedCreditFacilityTemplates, searchData, paginationData).subscribe((response: any) => {
        this.creditFacilityTemplates = response.pageData
        this.onCreditFacilityTemplateChange.next(response)
        resolve(response);
      }, reject)

    })
  }
}
