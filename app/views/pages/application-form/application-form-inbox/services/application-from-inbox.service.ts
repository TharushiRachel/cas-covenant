import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {Pagination} from "../../../../../core/dto/pagination";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {DataService} from "../../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Constants} from "../../../../../core/setting/constants";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Injectable()
export class ApplicationFromInboxService implements Resolve<any> {

  uniquePageName = 'ApplicationFormComponent-#layer789656';
  selectedApplicationForms: any = [];
  onSelectedApplicationFormChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService,
    private cacheService: CacheService,
    public applicationService: ApplicationService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES),
        this.getApplicationForms(),
      ]).then(() => {
        resolve();
      }, reject)
    });
  }

  getApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({});
      })
    }
    return this.searchApplicationForms(searchData, paginationData)
  }

  searchApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {
    let search = Object.assign({}, searchData, {
      // branchCode: this.applicationService.getLoggedInUserBranchcode(),
      assignUser: this.applicationService.getLoggedInUserUserName(),
      assignDepartmentCode: this.applicationService.getLoggedInUserDivCode(),
      assignGroupUPMGroupCode: this.applicationService.getLoggedInUserUPMGroupCode()
    });
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getInboxPagedApplicationForms, search, paginationData)
        .subscribe((response: any) => {
          this.selectedApplicationForms = response.pageData;
          this.onSelectedApplicationFormChange.next(response);
          resolve(response);
        }, reject)
    })
  }

}
