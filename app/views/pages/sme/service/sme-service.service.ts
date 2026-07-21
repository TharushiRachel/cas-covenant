import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { Observable } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable()
export class SmeServiceService implements Resolve<any> {
  constructor(
    private dataService: DataService,
    private alertService: AlertService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }

  getAllQuestionsAndAnswers() {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getAllQuestionsAndAnswers
      );
      data.url = data.url;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

  saveOrUpdateAnswer(data): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveOrUpdateAnswer, data)
        .subscribe(
          (response: any) => {
            console.log("response", response);
            resolve(response);
          },
          (error) => {
            console.error("API error", error);
              // this.alertService.showToaster(
              //   "Please answer all questions before submitting.",
              //   SETTINGS.TOASTER_MESSAGES.error
              // );
            //reject(error);
          }
        );
    });
  }

  getSmeAnswers(facilityPaperID) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getSmeAnswer);
      data.url = data.url + "/" + facilityPaperID;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

    getAnswerList(facilityPaperID) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAnswerList);
      data.url = data.url + "/" + facilityPaperID;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

  smeCustomerTurnover(data): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.smeCustomerTurnover, data)
        .subscribe(
          (response: any) => {
            console.log("response", response);
            resolve(response);
          },
          (error) => {
            console.error("API error", error);
          }
        );
    });
  }
}
