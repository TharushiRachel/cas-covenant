import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {Constants} from "../../../../core/setting/constants";
import {CacheService} from "../../../../core/service/data/cache.service";
import {Pagination} from "../../../../core/dto/pagination";
import * as _ from 'lodash';
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {ApplicationService} from "../../../../core/service/application/application.service";

@Injectable()
export class FacilityPaperReviewTemplateService implements Resolve<any> {

  uniquePageName = 'FacilityPaperReviewWrapperComponent-#366rta';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_REVIEW)
  selectedFacilityPaperIDForReview;
  onFacilityPaperChange = new BehaviorSubject({});
  onFPFacilitiesChange = new BehaviorSubject({});
  onCreditRiskCommentListChange = new BehaviorSubject({});
  onReviewerCommentListChange = new BehaviorSubject({});
  onCustomerListChange = new BehaviorSubject([]);
  onFpCustomerChange = new BehaviorSubject({});
  onRemarkDtoListChange = new BehaviorSubject({});
  onCustomerCribDetailsChange = new BehaviorSubject({});
  onFpTotalExposureChange = new BehaviorSubject({});
  onUpcSectionDataChange = new BehaviorSubject({});
  onFpUpcSectionChange = new BehaviorSubject({});
  onUpcTemplateListLoad = new BehaviorSubject({});
  onUpcTemplateChange = new BehaviorSubject({});
  onFPaperSecSummeryChange = new BehaviorSubject({});
  onCribReportChange = new BehaviorSubject({});

  onReviewerCommentSaveOrUpdate = new Subject();
  onOwnSavedReviewerCommentChagne = new BehaviorSubject({});
  facilityPaperDTO: any = {};
  upcSectionData: any = {};

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService,
              private cacheService: CacheService,
              private searchDataCacheService: SearchDataCacheService,
              private applicationService: ApplicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {
      if (this.selectedFacilityPaperIDForReview == null) {
        this.facilityPaperDTO = {};
        resolve({});
      } else {
        Promise.all([
          this.getFacilityPaperByID(),
          this.getRemarkDTOList(),
          this.getAllUpcSectionData(),
          this.getPagedReviewComment(),
          this.getLastOwnSavedComment(),
          this.cacheService.loadData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA),
          this.cacheService.loadData(Constants.masterDataKey.CAS_SECTOR_DATA),
          this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
          this.cacheService.loadData(Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED),
        ]).then(() => {
          resolve();
        }, reject)
      }
    });
  }

  getFacilityPaperByID(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getFacilityPaperByID);
      data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedFacilityPaperIDForReview);
      this.dataService.get(data)
        .subscribe((response: any) => {
        //  console.log(response);
          this.facilityPaperDTO = response;
          this.onFacilityPaperChange.next(response);
          this.onFPFacilitiesChange.next(response);
          this.onCreditRiskCommentListChange.next(response);
          this.onFpCustomerChange.next(response);
          this.onFpTotalExposureChange.next(response);
          this.onFpUpcSectionChange.next(response);
          this.onFPaperSecSummeryChange.next(response);

          localStorage.setItem("facilityPaperID", response.facilityPaperID);
          localStorage.setItem("facilityPaperRefID", response.fpRefNumber);

          resolve(response);
        }, reject);
    });
  }

  getRemarkDTOList() {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getRemarkDtoList);
    data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedFacilityPaperIDForReview);
    this.dataService.get(data)
      .subscribe((response: any) => {
        this.onRemarkDtoListChange.next(response);
      })
  }

  approveOrRejectFaciliptyPaper(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectFacilityPaper, data)
      .subscribe((response: any) => {
        response.needToBackRouting = true;
        this.onFacilityPaperChange.next(response);
        this.getPagedReviewComment();
      })
  }

  saveOrUpdateReviewerComment(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateReviewComment, data)
      .subscribe((response: any) => {
        this.onOwnSavedReviewerCommentChagne.next(response);
      })
  }

  getAllUpcSectionData(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAllUpcSectionData);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.upcSectionData = response;
          this.onUpcSectionDataChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getActiveApprovedUpcTemplates(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getActiveApprovedUpcTemplateDtoList);
      this.dataService.get(data)
        .subscribe((response: any) => {
          // this.activeApprovedUpcTemplates = response;
          this.onUpcTemplateListLoad.next(response);
          resolve(response);
        }, reject);
    })
  }

  getUpcTemplateDtoByID(id) {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPCTemplateUpdateDTO);
    data.url = data.url + '/' + id;
    this.dataService.get(data)
      .subscribe((response: any) => {
        // this.selectedUpcTemplate = response;
        this.onUpcTemplateChange.next(response);
      });
  }

  getPagedReviewComment(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({})
      });
    }
    if (_.isEmpty(paginationData)) {
      paginationData = new Pagination({pageIndex: 0, pageSize: 5});
    }

    let searchObject = Object.assign({}, {...searchData},
      {facilityPaperID: this.urlEncodeService.decode(this.selectedFacilityPaperIDForReview)});
    return this.getReviewComments({...searchObject}, paginationData);
  }


  getReviewComments(searchData?, paginationData?: Pagination): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getReviewCommentFromFPID, searchData, paginationData)
        .subscribe((response: any) => {
        //  console.log(response);
          this.onReviewerCommentListChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getLastOwnSavedComment(searchData?, paginationData?: Pagination) {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise<any>((resolve, reject) => {
        resolve({})
      });
    }
    if (_.isEmpty(paginationData)) {
      paginationData = new Pagination({pageIndex: 0, pageSize: 5});
    }

    let searchObject = Object.assign({}, {...searchData},
      {facilityPaperID: this.urlEncodeService.decode(this.selectedFacilityPaperIDForReview)},
      {upmID: this.applicationService.getLoggedInUserUserID()});
    return this.getOwnSavedComment({...searchObject}, paginationData);
  }

  getOwnSavedComment(searchData?, paginationData?: Pagination) {
    return new Promise((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getReviewCommentFromFPIDAndUpmID, searchData, paginationData)
        .subscribe((response: any) => {
         // console.log(response);
          this.onOwnSavedReviewerCommentChagne.next(response);
          resolve(response);
        }, reject);
    });
  }


}
