import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class FacilitiesService implements Resolve<any> {

  uniquePageName = 'FacilitiesComponent-#343rta';

  selectedFacilities: any = [];
  onSelectFacility: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getFacilities()
      ]).then(() => {
        resolve()
      }, reject)
    })
  }

  getFacilities(searchData?, paginationData?): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({})
      })
    }
    return this.searchFacilities(searchData, paginationData);
  }

  searchFacilities(searchData?, paginationData?): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedFacility, searchData, paginationData).subscribe((response: any) => {
        this.selectedFacilities = response.pageData;
        this.onSelectFacility.next(response);
        resolve(response);
      }, reject)
    })
  }
}
