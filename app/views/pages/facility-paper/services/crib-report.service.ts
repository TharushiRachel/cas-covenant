import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable({
  providedIn: "root",
})
export class CribReportService {
  onCribDetailsChange = new BehaviorSubject({});

  constructor(
    private readonly dataService: DataService,
    private readonly alertService: AlertService
  ) {}

  searchIndividualCrib(request: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.searchIndividualCrib, request)
        .subscribe(
          (response: any) => {
            if (response && response.data && response.data.workflowId) {
              let payload: any = {
                correlationId: response.data.workflowId,
                action: "Check",
                data: true,
              };

              setTimeout(() => {
                this.searchIndividualCribContinue(payload)
                  .then((resp: any) => {
                    resolve(resp);
                  })
                  .catch((err: any) => {
                    resolve(null);
                  });
              }, 4500);
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "The report is unavailable or there is a technical issue.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  searchIndividualCribContinue(request: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.searchIndividualCribContinue, request)
        .subscribe(
          (response: any) => {
            if (
              response &&
              response.data &&
              response.data.individualRecords.length > 0
            ) {
              let payload: any = {
                sectionsList: ["CreditinfoReportPlus"],
                subjectToken: response.data.individualRecords[0].subjectToken,
                timeout: 0,
              };

              setTimeout(() => {
                this.getCustomReport(payload)
                  .then((resp: any) => {
                    resolve(resp);
                  })
                  .catch((err: any) => {
                    resolve(null);
                  });
              }, 4500);
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "The report is unavailable or there is a technical issue.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  searchCompanyCrib(request: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.searchCompanyCrib, request)
        .subscribe(
          (response: any) => {
            if (response && response.data && response.data.workflowId) {
              let payload: any = {
                correlationId: response.data.workflowId,
                action: "Check",
                data: true,
              };
              setTimeout(() => {
                this.searchCompanyCribContinue(payload)
                  .then((resp: any) => {
                    resolve(resp);
                  })
                  .catch((err: any) => {
                    resolve(null);
                  });
              }, 4500);
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "The report is unavailable or there is a technical issue.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  searchCompanyCribContinue(request: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.searchCompanyCribContinue, request)
        .subscribe(
          (response: any) => {
            if (
              response &&
              response.data &&
              response.data.companyRecords.length > 0
            ) {
              let payload: any = {
                sectionsList: ["CreditinfoReportPlus"],
                subjectToken: response.data.companyRecords[0].subjectToken,
              };
              setTimeout(() => {
                this.getCustomReport(payload)
                  .then((resp: any) => {
                    resolve(resp);
                  })
                  .catch((err: any) => {
                    resolve(null);
                  });
              }, 4500);
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "The report is unavailable or there is a technical issue.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  getCustomReport(request: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCustomReport, request)
        .subscribe(
          (response: any) => {
            if (response && response.data && response.data.requestId) {
              let payload: any = {
                reportToken: response.data.requestId,
                languageCode: "en-GB",
              };
              setTimeout(() => {
                this.getCribReportPDF(payload)
                  .then((resp: any) => {
                    resolve(resp);
                  })
                  .catch((err: any) => {
                    resolve(null);
                  });
              }, 4500);
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "The report is unavailable or there is a technical issue.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  getCribReportPDF(request: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCribReportPDF, request)
        .subscribe(
          (response: any) => {
            if (response && response.data) {
              if (response && response.data && response.data.report) {
                let result: any = {
                  report: response.data.report,
                  token: response.data.token,
                };
                resolve(result);
              } else {
                setTimeout(() => {
                  this.getCustomReportByToken(response.data.token)
                    .then((resp: any) => {
                      resolve(resp);
                    })
                    .catch((err: any) => {
                      resolve(null);
                    });
                }, 4500);
              }
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "The report is unavailable or there is a technical issue.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  getCustomReportByToken(token: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      let payload: any = {
        reportToken: token,
      };
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCustomReportByToken, payload)
        .subscribe(
          (response: any) => {
            if (response && response.data) {
              let result: any = {
                report: response.data.report,
                token: response.data.token,
              };
              resolve(result);
            } else {
              resolve(null);
              this.alertService.showToaster(
                "The report is unavailable or there is a technical issue.",
                SETTINGS.TOASTER_MESSAGES.error
              );
            }
          },
          (err: any) => {
            resolve(null);
          }
        );
    });
  }

  saveCribSearch(formData: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveCribSearch, formData)
        .subscribe(
          (res: any) => {
            resolve(true);
          },
          (err: any) => {
            resolve(false);
          }
        );
    });
  }
}
