import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";

@Injectable()
export class ApplicationFormTopicService implements Resolve<any> {

  uniquePageName = 'ApplicationTopicComponent-#ggrdw';
  applicationTopicsPagedData: any = [];
  onApplicationTopicChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getAFTopics()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getAFTopics(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchAFTopics(searchData, paginationData);
  }

  searchAFTopics(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedAFTopics, searchData, paginationData).subscribe((response: any) => {
        this.applicationTopicsPagedData = response.pageData;
        this.onApplicationTopicChange.next(response);
        resolve(response);
      }, reject);
    });
  }


}
