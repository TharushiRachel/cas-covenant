import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { Pagination } from 'src/app/core/dto/pagination';
import { SearchDataCacheService } from 'src/app/core/service/common/search-data-cache.service';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable()
export class FacilityPaperSearchService {

  uniquePageName = 'FacilityPapersComponent-#ayer789656';
  selectedFacilityPapers: any = [];
  onselectedFacilityPaperChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([
        this.getFacilityPapers()
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
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getSearchedFacilityPaper, searchData, paginationData)
        .subscribe((response: any) => {
          this.selectedFacilityPapers = response.pageData;
          this.onselectedFacilityPaperChange.next(response);
          resolve(response);
        }, reject)
    })
  }
}
