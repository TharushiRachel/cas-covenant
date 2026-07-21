import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { Pagination } from "src/app/core/dto/pagination";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable()
export class ACAEService implements Resolve<any> {

  onGridDataChange: BehaviorSubject<any> = new BehaviorSubject({});
  onStatusCountChange: BehaviorSubject<any> = new BehaviorSubject({});
  selectedData: any = []
  pagedData: any = []
  constructor(
    private dataService: DataService
  ) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([
      ]).then(() => {
        resolve();
      }, reject);
    });
  }
  getACAEListByStatusService(requestBody: any, _status: any, paginationData?: Pagination, isModalOpen?: boolean) {
    if (isModalOpen) {
      return new Promise((resolve, reject) => {
        this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getNoLoadingACAERecordsByStatus, requestBody, paginationData)
          .subscribe((response: any) => {
            this.selectedData[_status] = response.pageData
            this.pagedData[_status] = response;
            this.onGridDataChange.next(response);
            resolve(response);
          }, reject)
      });
      // return this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getNoLoadingACAERecordsByStatus, acaeStatus, paginationData);
    } else {
      return new Promise((resolve, reject) => {
        this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getACAERecordsByStatus, requestBody, paginationData)
          .subscribe((response: any) => {
            this.selectedData[_status] = response.pageData
            this.pagedData[_status] = response;
            this.onGridDataChange.next(response);
            resolve(response);
          }, reject)
      })
      // return this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getACAERecordsByStatus, acaeStatus, paginationData);
    }
  }


  getACAEStatusCountService(acaeStatusCountRQ, isModalOpen?: boolean) {
    if (isModalOpen) {
      return new Promise((resolve, reject) => {
        this.dataService.post(SETTINGS.ENDPOINTS.getNoLoadingACAEStatusCount, acaeStatusCountRQ)
          .subscribe((response: any) => {
            this.onStatusCountChange.next(response);
            resolve(response);
          }, reject);
      })
      // return this.dataService.post(SETTINGS.ENDPOINTS.getNoLoadingACAEStatusCount, acaeStatusCountRQ);
    } else {
      return new Promise((resolve, reject) => {
        this.dataService.post(SETTINGS.ENDPOINTS.getACAEStatusCount, acaeStatusCountRQ)
          .subscribe((response: any) => {
            this.onStatusCountChange.next(response);
            resolve(response);
          }, reject);
      })
      // return this.dataService.post(SETTINGS.ENDPOINTS.getACAEStatusCount, acaeStatusCountRQ);
    }
  }

  getAllACAEListByStatusService(acaeStatus) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getAllACAERecordsByStatus, acaeStatus);
  }

  getEligibilityForwardACAEBatchService = (acaeListDoneRQ: any) => {
    return this.dataService.post(SETTINGS.ENDPOINTS.getEligibilityForwardACAEBatch, acaeListDoneRQ);
  }

  getACAEPaperSummaryUserWiseService(acaeSearchByStatusRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAEPaperUserWiseSummary, acaeSearchByStatusRQ);
  }

  saveBulkComments(dataRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.saveACAEBulkComments, dataRQ)
  }
}
