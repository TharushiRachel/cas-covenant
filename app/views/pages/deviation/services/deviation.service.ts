import { Injectable } from "@angular/core";
import { AlertService } from "src/app/core/service/common/alert.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable({
  providedIn: "root",
})
export class DeviationService {
  constructor(
    private dataService: DataService,
    private alertService: AlertService,
  ) {}

  getDeviationTypes(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getDeviationTypes).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          resolve(null);
        },
      );
    });
  }

  saveDeviationType(request: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveDeviationType, request)
        .subscribe(
          (response: any) => {
            resolve(response);
            this.alertService.showToaster(
              "Data has been saved successfully.",
              SETTINGS.TOASTER_MESSAGES.success,
            );
          },
          (error: any) => {
            resolve([]);
            this.alertService.showToaster(
              "Failed to save data.",
              SETTINGS.TOASTER_MESSAGES.success,
            );
          },
        );
    });
  }

  getDeviations(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllDeviations).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          resolve(null);
        },
      );
    });
  }

  saveDeviation(request: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveDeviations, request)
        .subscribe(
          (response: any) => {
            resolve(response);
            this.alertService.showToaster(
              "Data has been saved successfully.",
              SETTINGS.TOASTER_MESSAGES.success,
            );
          },
          (error: any) => {
            resolve(null);
            this.alertService.showToaster(
              "Failed to save data.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        );
    });
  }

  authorizeDeviation(request: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.authorizeDeviation, request)
        .subscribe(
          (response: any) => {
            resolve(response);
            this.alertService.showToaster(
              "Data has been saved successfully.",
              SETTINGS.TOASTER_MESSAGES.success,
            );
          },
          (error: any) => {
            resolve(null);
            this.alertService.showToaster(
              "Failed to save data.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        );
    });
  }
}
