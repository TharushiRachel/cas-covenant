import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {DataService} from "../../../../core/service/data/data.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";


@Injectable()
export class AuditService implements Resolve<any> {

  uniquePageName = 'AuditComponent-#343rta';
  auditDetails: any = [];
  onSelectAuditChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private searchDataCacheService: SearchDataCacheService,
    private dataService: DataService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getAuditDetails()
      ]).then(() => {
        resolve();
      }, resolve)
    })
  }

  getAuditDetails(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      })
    }
    return this.searchAuditDetails(searchData, paginationData);
  }

  searchAuditDetails(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedAuditDetails, searchData, paginationData).subscribe((response: any) => {
        this.auditDetails = response.pageData;
        this.onSelectAuditChange.next(response)
        resolve(response)
      }, reject);
    });
  }

}
