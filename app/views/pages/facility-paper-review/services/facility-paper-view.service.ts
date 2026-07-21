import {Injectable} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import {FacilityPaperReviewService} from "./facility-paper-review.service";

@Injectable()
export class FacilityPaperViewService implements Resolve<any> {

  uniquePageName = 'FacilityPaperReviewWrapperComponent-#366rta';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_ASSIGNED_USER_ID)
  selectedAssignUserID;

  facilities: any = [];
  onFacilityPapersChangeSub: Subject<any> = new BehaviorSubject({});
  facilityReviewSearchDetails;

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService,
              private searchDataCacheService: SearchDataCacheService,
              private facilityPaperReviewService: FacilityPaperReviewService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {
      if (this.selectedAssignUserID == null) {
        this.facilities = [];
        this.onFacilityPapersChangeSub.next(this.facilities);
        resolve({});
      } else {
        Promise.all([
          this.getFacilityPapersPagedSummery(),
        ]).then(() => {
          resolve();
        }, reject)
      }
    });
  }

  getFacilityPapersPagedSummery(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({})
      });
    }
    this.facilityPaperReviewService.facilityReviewSearchDetails.subscribe(response => {
      this.facilityReviewSearchDetails = response;
    });

    let searchObject = Object.assign({}, {...searchData}, {...this.facilityReviewSearchDetails}, {currentAssignedUserID: this.urlEncodeService.decode(this.selectedAssignUserID)});
    return this.searchPagedFacilityPapers({...searchObject}, paginationData);
  }

  searchPagedFacilityPapers(searchData?, paginationData?: Pagination): Promise<any> {
    //console.log(searchData);
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedFacilitiesForReview, searchData, paginationData)
        .subscribe((response: any) => {
          this.facilities = response.pageData;
          this.onFacilityPapersChangeSub.next(response);
          resolve(response);
        }, reject);
    });
  }


}
