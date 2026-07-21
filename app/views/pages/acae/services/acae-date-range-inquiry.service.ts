import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable } from "rxjs";
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable()
export class ACAEDateRangeInquiryService {
  constructor(
    private dataService: DataService,
    private cacheService: CacheService,
    private applicationService: ApplicationService,
    private alertService: AlertService,
  ) { }

  onACAEListInquiryByDateRangeChange = new BehaviorSubject({});
  acaeInquiryByDateRangeDetail: any = [];

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([
        //implement method
      ]).then(() => {
        resolve()
      }, reject)
    })
  }


  getInquiryByDateRange(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getACAEDateRangeInquiry, payload).subscribe({
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