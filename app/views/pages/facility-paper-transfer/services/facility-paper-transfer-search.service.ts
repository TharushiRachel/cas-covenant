import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable, Subject} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../core/service/application/application.service";
import {AppUtils} from "../../../../shared/app.utils";

@Injectable()
export class FacilityPaperTransferSearchService implements Resolve<any> {

  uniquePageName = 'SearchFacilityPaperComponent-#ayer789777';
  onSelectedFacilityPaperChange: Subject<any> = new Subject();

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService,
    private applicationService: ApplicationService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve()
      }, reject)
    })
  }

  getFacilityPapers(searchData?): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({});
      })
    }
    let searchObj = Object.assign({}, searchData, {
      loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode()
    });
    return this.searchFacilityPapers(searchObj)
  }

  searchFacilityPapers(searchData?): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getPagedFacilityPaperForTransfer, AppUtils.trim(searchData))
        .subscribe((response: any) => {
          this.onSelectedFacilityPaperChange.next(response);
          resolve(response);
        }, reject)
    })
  }
}
