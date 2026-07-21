import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, RouterStateSnapshot, Resolve} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class SupportDocumentsService implements Resolve<any> {

  uniquePageName = "SupportDocumentsComponent-#343rta";
  onSupportingDocChange: BehaviorSubject<any> = new BehaviorSubject({});
  supportingDocs: any = [];

  constructor(
    private dataServive: DataService,
    private searchDataCacheService: SearchDataCacheService
  ) {
  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getSupportingDocs()
      ]).then(() => {
        resolve();
      }, reject);
    });
  }

  getSupportingDocs(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({})
      });
    }
    return this.searchSupportingDocs()
  }

  searchSupportingDocs(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataServive.postWithPageData(SETTINGS.ENDPOINTS.getPagedSupportingDoc, searchData, paginationData).subscribe((reponse: any) => {
        this.supportingDocs = reponse.pageData;
        this.onSupportingDocChange.next(reponse);
        resolve(reponse)
      }, reject)
    })
  }
}
