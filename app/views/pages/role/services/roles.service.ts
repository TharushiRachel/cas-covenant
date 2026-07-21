import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class RolesService implements Resolve<any> {

  uniquePageName = 'RolesComponent-#343rta';
  roles: any = [];
  onRolesChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getRoles()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getRoles(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchRoles(searchData, paginationData);
  }

  searchRoles(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedRoles, searchData, paginationData).subscribe((response: any) => {
        this.roles = response.pageData;
        this.onRolesChange.next(response);
        resolve(response);
      }, reject);
    });
  }
}
