import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AlertService } from "src/app/core/service/common/alert.service";
import { MicroDataService } from "src/app/core/service/data/micro-data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { LEAD_SETTINGS } from "./endpoints";
import { Response } from "../../../../shared/interfaces/Response";
import { LeaseJourneyRequestDTO } from "../interfaces/Lead-comp-borrower-dto";

@Injectable({
  providedIn: "root",
})
export class AdvanceAnalyticsService {
  constructor(
    private readonly dataService: MicroDataService,
    private readonly alertService: AlertService,
  ) {}

  public searchIndividualCrib(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(LEAD_SETTINGS.ENDPOINTS.getIndividualCribDetails, payload)
        .subscribe({
          next: (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response.success) {
                resolve(response.response);
              } else {
                if (response.message !== null && response.message !== "Error") {
                  this.alertService.showToaster(
                    response.message,
                    SETTINGS.TOASTER_MESSAGES.error,
                  );
                } else {
                  this.alertService.showToaster(
                    "Failed to retrieve crib data.",
                    SETTINGS.TOASTER_MESSAGES.error,
                  );
                }

                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to retrieve crib data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          },
          error: (err: any) => {
            let error: Error = new Error(err);
            this.alertService.showToaster(
              "An error occured. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        });
    });
  }

  public searchCompanyCrib(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(LEAD_SETTINGS.ENDPOINTS.getCompanyCribDetails, payload)
        .subscribe({
          next: (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response.success) {
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to retrieve crib data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to retrieve crib data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          },
          error: (err: any) => {
            let error: Error = new Error(err);
            this.alertService.showToaster(
              "An error occured. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        });
    });
  }

  public getIndividualLeasingAA(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(LEAD_SETTINGS.ENDPOINTS.getIndividualLeasingAA, payload)
        .subscribe({
          next: (result: any) => {
            if (result !== null) {
              let response: Response | any = result;
              if (response.success) {
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to retrieve advance analytics data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to retrieve advance analytics data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          },
          error: (err: any) => {
            let error: Error = new Error(err);
            this.alertService.showToaster(
              "An error occured. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        });
    });
  }

  public getLeasingJourneyValidation(
    payload: LeaseJourneyRequestDTO,
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(LEAD_SETTINGS.ENDPOINTS.getLeasingJourneyValidation, payload)
        .subscribe({
          next: (result: any) => {
            if (result !== null) {
              let response: Response | any = result;
              if (response.success) {
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to retrieve advance analytics data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to retrieve advance analytics data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          },
          error: (err: any) => {
            let error: Error = new Error(err);
            this.alertService.showToaster(
              "An error occured. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        });
    });
  }
}
