import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {SETTINGS} from "../../../../../core/setting/commons.settings";

@Injectable()
export class BranchApplicationFormService implements Resolve<any> {
  uniquePageName = 'BranchApplicationFormComponent-#aardw';
  applicationForms: any = [];
  onBranchApplicationFormChange: BehaviorSubject<any> = new BehaviorSubject({});


  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService,
              public applicationService: ApplicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getBranchApplicationForms()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getBranchApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchBranchApplicationForms(searchData, paginationData);
  }

  searchBranchApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {

    let search = Object.assign({}, searchData, {
      branchCode: this.applicationService.getLoggedInUserDivCode(),
    });

    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(
        SETTINGS.ENDPOINTS.getPagedBranchApplicationForm, search, paginationData).subscribe((response: any) => {
        this.applicationForms = response.pageData;
        this.onBranchApplicationFormChange.next(response);
        resolve(response);
      }, reject);
    });
  }

}
