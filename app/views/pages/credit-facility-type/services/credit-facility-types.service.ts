import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class CreditFacilityTypesService implements Resolve<any> {

  uniquePageName = "CreditFacilityTypesComponent-#343rta";
  onChangeCreditFacilityTypesChange: BehaviorSubject<any> = new BehaviorSubject({});
  creditFacilityTypes: any = [];


  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {
      Promise.all([
        this.getCreditFacilityTypes({status: 'ACT'})
      ]).then(() => {
        resolve()
      }, reject);
    });
  }

  getCreditFacilityTypes(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({})
      });
    }
    ;
    return this.searchCreditFacilityTypes(searchData, paginationData);
  }

  searchCreditFacilityTypes(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedCreditFacilityTypes, searchData, paginationData).subscribe((response: any) => {
        this.creditFacilityTypes = response.pageData;
        this.onChangeCreditFacilityTypesChange.next(response);
        resolve(response)
      }, reject);
    });
  }

}
