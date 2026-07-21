import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {DataService} from "../../../../core/service/data/data.service";
import {BehaviorSubject, Observable} from "rxjs";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {CacheService} from "../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../core/service/application/application.service";

@Injectable()
export class LeadSearchService implements Resolve<any> {

   uniquePageName = 'LeadSearchComponent-#343rta';
    leads: any = [];
    onLeadsChange: BehaviorSubject<any> = new BehaviorSubject({});

    pendingLeadCount = 0;
    onPendingLeadCountChange = new BehaviorSubject('00');


    constructor(
      private searchDataCacheService: SearchDataCacheService,
      private dataService: DataService,
      private cacheService: CacheService,
      private applicationService: ApplicationService
    ) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
      return new Promise((resolve, reject) => {
        Promise.all([
          this.getLeads(),
        ]).then(() => {
          resolve();
        }, reject)
      });
    }

    getLeads(searchData?, paginationData?: Pagination): Promise<any> {
      if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
        return new Promise<any>((resolve, reject) => {
          resolve({})
        });
      }
      return this.searchLeads(searchData, paginationData);
    }

    searchLeads(searchData?, paginationData?: Pagination): Promise<any> {

      return new Promise((resolve, reject) => {
        this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedLeads, searchData, paginationData)
          .subscribe((respose: any) => {
            this.leads = respose.pageData;
            this.onLeadsChange.next(respose);
            resolve(respose)
          }, reject);
      });
    }
}
