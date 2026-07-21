import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";

@Injectable()
export class AgentsService implements Resolve<any> {

  uniquePageName = 'AgentsViewComponent-#ffrdw';
  agents: any = [];
  onAgentsChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getAgents()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getAgents(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchAgents(searchData, paginationData);
  }

  searchAgents(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedAgents, searchData, paginationData).subscribe((response: any) => {
        this.agents = response.pageData;
        this.onAgentsChange.next(response);
        resolve(response);
      }, reject);
    });
  }


}
