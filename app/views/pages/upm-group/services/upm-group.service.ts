import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class UpmGroupService implements Resolve<any> {

  uniquePageName = 'upmGroup-#ad12355';
  pageDataList: any = [];
  onDataChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getPageData({status: 'ACT'})
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getPageData(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchPageData(searchData, paginationData);
  }

  searchPageData(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedUPMGroup, searchData, paginationData).subscribe((response: any) => {
        this.pageDataList = response.pageData;
        this.onDataChange.next(response);
        resolve(response);
      }, reject);
    });
  }
}
