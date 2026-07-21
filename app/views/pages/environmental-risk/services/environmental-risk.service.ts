import { Injectable } from "@angular/core";
import { AlertService } from "src/app/core/service/common/alert.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable({
  providedIn: "root",
})
export class EnvironmentalRiskService {
  constructor(
    private readonly dataService: DataService,
    private readonly alertService: AlertService
  ) {}

  getRiskCategories(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getRiskCategories).subscribe(
        (res: any) => {
          resolve(res);
        },
        (err: any) => {
          resolve(null);
        }
      );
    });
  }

  saveTempRiskCategory(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveTempRiskCategory, payload)
        .subscribe(
          (res: any) => {
            if (res) {
              resolve(res);
              this.alertService.showToaster(
                "Data has been saved successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );
            }
          },
          (err: any) => {
            resolve(null);
          }
        );
    });
  }

  approveRejectRiskCategory(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.approveRejectRiskCategories, payload)
        .subscribe(
          (res: any) => {
            if (res) {
              resolve(res);
              let message: string =
                "Data has been " +
                payload.approvedStatus.toLowerCase() +
                " successfully.";
              this.alertService.showToaster(
                message,
                SETTINGS.TOASTER_MESSAGES.success
              );
            }
          },
          (err: any) => {
            resolve(null);
          }
        );
    });
  }
}
