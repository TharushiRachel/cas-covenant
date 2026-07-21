import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {Pagination} from "../../../../core/dto/pagination";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import * as _ from "lodash";
import {Constants} from "../../../../core/setting/constants";
import {ApplicationService} from "../../../../core/service/application/application.service";
import {LocalStorage} from "ngx-webstorage";

@Injectable()
export class FacilityPaperReviewService implements Resolve<any> {

  uniquePageName = 'FacilitySummeryReviewComponent-#366rta';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_ASSIGNED_USER_ID)
  selectedAssignUserID;

  paperReviewStatusConst = Constants.paperReviewStatusConst;
  facilityPapersReviewSummaryRecords: any = [];
  ownApprovedFacilityPapers: any = [];
  onOwnApprovedFPChange: BehaviorSubject<any> = new BehaviorSubject({});
  onFPSummaryReviewChange: BehaviorSubject<any> = new BehaviorSubject({});
  facilityPaperReviewDetail: BehaviorSubject<any> = new BehaviorSubject({});
  facilityReviewSearchDetails: BehaviorSubject<any> = new BehaviorSubject<any>(
    {
      dateRange: Constants.dateRangeConst.LAST_30_DAYS,
      paperReviewStatusList: [
        this.paperReviewStatusConst.ACTION_REQUIRED,
        this.paperReviewStatusConst.REPLIED]
    });

  approvedUPMGroupList: any = [];
  allowApprovedUPMGroupsForLoginUser: any = [];
  onApprovedUPMGroupListChange: Subject<any> = new Subject();
  onUserAllowedUPMGroupListChange: Subject<any> = new Subject<any>();

  onSelectedTabIndexFromPaperReviewSummaryChange: BehaviorSubject<any> = new BehaviorSubject(0);

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService,
              private searchDataCacheService: SearchDataCacheService,
              private applicationService: ApplicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      return Promise.all([
        this.getFacilityReviewSummery({
          dateRange: Constants.dateRangeConst.LAST_30_DAYS,
          loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
          paperReviewStatusList: [Constants.paperReviewStatusConst.ACTION_REQUIRED, Constants.paperReviewStatusConst.REPLIED],
        }),
      ]).then(() => {
        resolve();
      }, reject)
    });
  }

  getFacilityReviewSummery(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({})
      });
    }
    return this.searchFacilityReviewSummery(searchData, paginationData);
  }

  searchFacilityReviewSummery(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedFacilityPaperReviewSummary, searchData, paginationData)
        .subscribe((response: any) => {
          this.facilityPapersReviewSummaryRecords = response.pageData;
          this.onFPSummaryReviewChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  viewFacilities(data) {
    this.facilityPaperReviewDetail.next(data);
  }

  getAllApprovedUPMGroups(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllApprovedUPMGroups)
        .subscribe((response: any) => {
          _.forEach(_.sortBy(response, ['groupCode']), upmGroup => {
            if (!_.isNull(upmGroup.upmGroupID)) {
              this.approvedUPMGroupList = [];
              this.approvedUPMGroupList.push({
                value: upmGroup.groupCode,
                label: upmGroup.groupCode + (upmGroup.referenceName ? ' - ' + upmGroup.referenceName : '')
              });
            }
          });
          this.onApprovedUPMGroupListChange.next(this.approvedUPMGroupList);
          resolve(response);
        }, reject);
    });
  }

  getAllowApprovedUPMGroupsForLoginUser(searchData): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getAllowApprovedUPMGroupsForLoginUser, searchData)
        .subscribe((response: any) => {
          this.allowApprovedUPMGroupsForLoginUser = [];
          _.forEach(_.sortBy(response, ['groupCode']), upmGroup => {
            if (!_.isNull(upmGroup.groupCode)) {
              this.allowApprovedUPMGroupsForLoginUser.push({
                value: upmGroup.groupCode,
                label: upmGroup.groupCode + (upmGroup.referenceName ? ' - ' + upmGroup.referenceName : '')
              })
            }
          });
          this.onUserAllowedUPMGroupListChange.next(this.allowApprovedUPMGroupsForLoginUser);
          resolve(response);
        }, reject);
    });
  }

  searchOwnApprovedFacilityPapers(searchData?, paginationData?: Pagination) {
    this.getOwnApprovedFacilityPapers(searchData, paginationData);
  }

  getOwnApprovedFacilityPapers(searchData?, paginationData?: Pagination) {
    let searchObj = Object.assign({}, {...searchData}, {currentAssignedUserID: this.applicationService.getLoggedInUserUserID()});

    this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getOwnApprovedPagedFacilityPapers, searchObj, paginationData)
      .subscribe((response: any) => {
        this.ownApprovedFacilityPapers = response.pageData;
        this.onOwnApprovedFPChange.next(response);
      });
  }

}
