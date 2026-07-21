import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class UserDelegatedAuthoritiesService implements Resolve<any> {

  uniquePageName = 'UserDelegatedAuthoritiesComponent-#343rta';
  onUserDAsChange: BehaviorSubject<any> = new BehaviorSubject({});
  userDAs: any = [];


  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getUserDAs()
      ]).then(() => {
        resolve();
      }, reject);
    });
  }


  getUserDAs(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({})
      });
    }
    return this.searchUserDAs(searchData, paginationData);
  }

  searchUserDAs(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedUserDAs, searchData, paginationData).subscribe((response: any) => {
        this.userDAs = response.pageData;
        this.onUserDAsChange.next(response);
        resolve(response)
      }, reject)
    });
  }
}
