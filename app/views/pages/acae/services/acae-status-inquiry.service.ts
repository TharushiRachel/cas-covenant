import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { Pagination } from 'src/app/core/dto/pagination';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable()
export class ACAEStatusInquiryService implements Resolve<any> {
  onACAEStatusInquiryByDateRangeChange = new BehaviorSubject({});
  onACAEStatusInquireBySOLIdChange = new BehaviorSubject({});
  onACAEStatusInquiryByResubmittedChange = new BehaviorSubject({});
  acaeStatusInquiryByDateRangeData = [];
  acaeStatusInquireBySOLIdData = [];
  acaeStatusInquiryByResubmittedData = [];

  constructor(
    private dataService: DataService,
    private alertService: AlertService
  ) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([
      ]).then(() => {
        resolve()
      }, reject)
    })
  }

  getInquiryByDateRange(payload: any, paginationData?: Pagination) {
    return this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getInquiryByDateRange, payload, paginationData);
  }

  getInquiryByResubmittedACAE(payload: any,paginationData?: Pagination) {
    return new Promise<any>((resolve, reject) => {
      this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getInquiryByResubmittedACAE, payload,paginationData).subscribe({
        next: (response: Response) => {
          if (response) {
            resolve(response);
          }
        },
        error: (err: any) => {
          let error: Error = new Error(err);
          this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error);
        },
      });
    });
  }

  getInquiryBySolIds(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getInquiryBySolIds, payload).subscribe({
        next: (response: Response) => {
          if (response) {
            resolve(response);
          }
        },
        error: (err: any) => {
          let error: Error = new Error(err);
          this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error);
        },
      });
    });
  }

}