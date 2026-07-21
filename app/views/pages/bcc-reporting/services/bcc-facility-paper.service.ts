import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {Constants} from "../../../../core/setting/constants";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import { CacheService } from 'src/app/core/service/data/cache.service';

@Injectable()
export class BccFacilityPaperService implements Resolve<any> {

  uniquePageName = 'BccFacilityPapersComponent-#343uIa';

  selectedFacilityPapers: any = [];
  onSelectedFacilityPaperChange: BehaviorSubject<any> = new BehaviorSubject({});
  onSelectedFacilityPaperChangeForDraftBCC: BehaviorSubject<any> = new BehaviorSubject({});
  onSelectedTabIndexFromBCCChange: BehaviorSubject<any> = new BehaviorSubject(0);

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService,
    private cacheService: CacheService,
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getFacilityPapers(),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
      ]).then(() => {
        resolve()
      }, reject)
    })
  }

  getFacilityPapers(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({});
      })
    }
    return this.searchFacilityPapers(searchData, paginationData)
  }

  searchFacilityPapers(searchData?, paginationData?: Pagination): Promise<any> {

    let searchObj = Object.assign({}, {...searchData}, {facilityPaperStatus: Constants.bccFacilityPaperStatusConst.APPROVED});
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedFacilityPaperDTOForBCC, searchObj, paginationData)
        .subscribe((response: any) => {
          this.selectedFacilityPapers = response.pageData;
          this.onSelectedFacilityPaperChange.next(response);
          resolve(response);
        }, reject)
    })
  }

  searchFacilityPapersByUserName(searchData?, paginationData?: Pagination): Promise<any> {

    let searchObj = Object.assign({}, {...searchData}, {facilityPaperStatus: Constants.bccFacilityPaperStatusConst.APPROVED});
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedFacilityPaperDTOForBCCForUserName, searchObj, paginationData)
        .subscribe((response: any) => {
          this.onSelectedFacilityPaperChangeForDraftBCC.next(response);
          resolve(response);
        }, reject)
    })
  }

}
