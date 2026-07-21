import { Injectable } from "@angular/core";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Party, SaveObj } from "../interfaces/save-dtos/Lead-comp-save-dto";
import { Response } from "../../../../shared/interfaces/Response";
import { MicroDataService } from "src/app/core/service/data/micro-data.service";
import { LEAD_SETTINGS } from "./endpoints";
import { AlertService } from "src/app/core/service/common/alert.service";
import { EndpointConfig } from "src/app/shared/interfaces/EndpointConfig";
import { IncomeSource } from "../interfaces/save-dtos/Lead-comp-imcome-type-save-dto";
import { RelatedParty } from "../interfaces/save-dtos/Lead-comp-parties.save-dto";
import { BehaviorSubject, Observable } from "rxjs";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import {
  ActivatedRoute,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";
import { HttpParams } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class LeadComprehensiveService {
  selectedLead: any = {};
  onLeadPurposeChange: BehaviorSubject<string> = new BehaviorSubject('');
  selectedLeadID: number = 0;
  constructor(
    private readonly dataService: MicroDataService,
    private readonly alertService: AlertService,
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        // this.getLeadById()
      ]).then(() => {
        resolve({});
      }, reject);
    });
  }

  public getLeadById(leadId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.getLeadById,
        url: LEAD_SETTINGS.ENDPOINTS.getLeadById.url + "/" + leadId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.response !== undefined && response.response !== null) {
              let res = response.response;
              res.leadId = leadId;
              resolve(res);
            } else {
              this.alertService.showToaster(
                "Failed to fetch data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to fetch data.",
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

  public saveComprehensiveLead(payload: SaveObj): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(LEAD_SETTINGS.ENDPOINTS.saveComprehensiveLead, payload)
        .subscribe({
          next: (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response.success) {
                this.alertService.showToaster(
                  "Data has been save successfully.",
                  SETTINGS.TOASTER_MESSAGES.success,
                );
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to save data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          },
          error: (err: any) => {
            let error: Error = new Error(err);
            resolve(null);
            this.alertService.showToaster(
              "An error occured. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        });
    });
  }

  public saveRelatedPartiesLead(
    compLeadId: number,
    payload: RelatedParty,
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.saveRelatedPartiesLead,
        url:
          LEAD_SETTINGS.ENDPOINTS.saveRelatedPartiesLead.url + "/" + compLeadId,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been save successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to save data.",
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

  public saveIncomeSourceLead(
    compLeadId: number,
    payload: IncomeSource[],
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.saveIncomeSourceLead,
        url:
          LEAD_SETTINGS.ENDPOINTS.saveIncomeSourceLead.url + "/" + compLeadId,
      };

      let request: any = {
        incomeSourceRequestDTOs: payload,
      };
      this.dataService.post<any>(updated_endpoint, request).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been save successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to save data.",
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

  public savePartiesLead(compLeadId: number, payload: Party): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.savePartiesLead,
        url: LEAD_SETTINGS.ENDPOINTS.savePartiesLead.url + "/" + compLeadId,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been save successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to save data.",
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

  public saveFacilitiesLead(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.saveFacilitiesLead,
        url: LEAD_SETTINGS.ENDPOINTS.saveFacilitiesLead.url,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been save successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to save data.",
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

  public deactivateParty(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.deactivateParty,
        url: LEAD_SETTINGS.ENDPOINTS.deactivateParty.url,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been removed successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to remove data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to remove data.",
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

  public deactivateIncomeSource(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.deactivateIncomeSource,
        url: LEAD_SETTINGS.ENDPOINTS.deactivateIncomeSource.url,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been removed successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to remove data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to remove data.",
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

  public deactivateRelatedParty(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.deactivateRelatedParty,
        url: LEAD_SETTINGS.ENDPOINTS.deactivateRelatedParty.url,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been removed successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to remove data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to remove data.",
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

  public deactivateFacility(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.deactivateFacility,
        url: LEAD_SETTINGS.ENDPOINTS.deactivateFacility.url,
      };
      this.dataService.post<any>(updated_endpoint, payload).subscribe({
        next: (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response.success) {
              this.alertService.showToaster(
                "Data has been removed successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
              resolve(response.response);
            } else {
              this.alertService.showToaster(
                "Failed to remove data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
            this.alertService.showToaster(
              "Failed to remove data.",
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

  getDigitalApplicationContent(compLeadId: number): Promise<string> {
    return new Promise((resolve) => {
      const baseEndpoint = LEAD_SETTINGS.ENDPOINTS.getDigitalApplication;

      // Clone EndpointConfig and override url
      const endpointWithId = {
        ...baseEndpoint,
        url: baseEndpoint.url + "/" + compLeadId,
      };

      // Second arg must be HttpParams (based on your error)
      const params = new HttpParams();

      this.dataService.get(endpointWithId, params).subscribe(
        (res: any) => {
          // Your wrapper might return either raw string OR { result: string }
          if (typeof res === "string" && res) return resolve(res);
          if (res && typeof res.result === "string" && res.result)
            return resolve(res.result);
          resolve("");
        },
        () => resolve(""),
      );
    });
  }

  saveDigitalApplication(digitalApplicationData: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(
          LEAD_SETTINGS.ENDPOINTS.saveDigitalApplication,
          digitalApplicationData,
        )
        .subscribe(
          (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response.success) {
                this.alertService.showToaster(
                  "Digital Application detail has been saved.",
                  SETTINGS.TOASTER_MESSAGES.success,
                );
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to save data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "Failed to save digital application details.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          },
        );
    });
  }

  public getDigitalApplications(leadId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.getDigitalApplications,
        url: LEAD_SETTINGS.ENDPOINTS.getDigitalApplications.url + "/" + leadId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe({
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
                "Failed to retrive digital applications.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
          } else {
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

  public getDigitalApplicationById(applicationId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.getDigitalApplicationById,
        url:
          LEAD_SETTINGS.ENDPOINTS.getDigitalApplicationById.url +
          "/" +
          applicationId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe({
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
                "Failed to retrive digital application",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve(null);
            }
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

  public getApproveFacilityTypes(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getApprovedFacilityTypeList)
        .subscribe(
          (response: any) => {
            resolve(response);
          },
          () => {
            reject();
          },
        );
    });
  }

  public getLeadStatus(leadId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.getLeadStatus,
        url: LEAD_SETTINGS.ENDPOINTS.getLeadStatus.url + "/" + leadId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe({
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
              resolve(null);
            }
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

  public saveComprehensiveLeadComment(payload: SaveObj): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(
          LEAD_SETTINGS.ENDPOINTS.saveComprehensiveLeadComment,
          payload,
        )
        .subscribe({
          next: (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response.success) {
                this.alertService.showToaster(
                  "Data has been save successfully.",
                  SETTINGS.TOASTER_MESSAGES.success,
                );
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to save data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
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

  public saveComprehensiveLeadDocument(payload: SaveObj): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post<any>(
          LEAD_SETTINGS.ENDPOINTS.saveComprehensiveLeadDocument,
          payload,
        )
        .subscribe({
          next: (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response.success) {
                this.alertService.showToaster(
                  "Data has been save successfully.",
                  SETTINGS.TOASTER_MESSAGES.success,
                );
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to save data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
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

  public uploadLeadDocument(supportDocumentDTO: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(LEAD_SETTINGS.ENDPOINTS.uploadLeadDocument, supportDocumentDTO)
        .subscribe({
          next: (result: any) => {
            if (
              result !== null &&
              result.result !== undefined &&
              result.result !== null
            ) {
              let response: Response | any = result.result;
              if (response && response.response) {
                this.alertService.showToaster(
                  "Data has been save successfully.",
                  SETTINGS.TOASTER_MESSAGES.success,
                );
                resolve(response.response);
              } else {
                this.alertService.showToaster(
                  "Failed to save data.",
                  SETTINGS.TOASTER_MESSAGES.error,
                );
                resolve(null);
              }
            } else {
              this.alertService.showToaster(
                "Failed to save data.",
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

  public getSupportinDocs(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getApprovedSupportingDocList)
        .subscribe(
          (response: any) => {
            if (response !== null && response.result !== null) {
              resolve(response.result);
            } else {
              resolve([]);
            }
          },
          () => {
            resolve([]);
          },
        );
    });
  }

  public deleteLeadDocument(documentId: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.deleteLeadDocument,
        url: LEAD_SETTINGS.ENDPOINTS.deleteLeadDocument.url + "/" + documentId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe(
        (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response !== null && response.response !== null) {
              resolve(response.response);
              this.alertService.showToaster(
                "Document has been delete successfully.",
                SETTINGS.TOASTER_MESSAGES.success,
              );
            } else {
              this.alertService.showToaster(
                "Failed to delete document.",
                SETTINGS.TOASTER_MESSAGES.error,
              );
              resolve([]);
            }
          } else {
            this.alertService.showToaster(
              "Failed to delete document.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
            resolve([]);
          }
        },
        () => {
          this.alertService.showToaster(
            "An error occured. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error,
          );
          resolve([]);
        },
      );
    });
  }

  public viewLeadDocument(documentId: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.viewLeadDocument,
        url: LEAD_SETTINGS.ENDPOINTS.viewLeadDocument.url + "/" + documentId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe(
        (result: any) => {
          if (
            result !== null &&
            result.result !== undefined &&
            result.result !== null
          ) {
            let response: Response | any = result.result;
            if (response !== null && response.response !== null) {
              resolve(response.response);
            } else {
              resolve(null);
            }
          } else {
            resolve(null);
          }
        },
        () => {
          resolve(null);
        },
      );
    });
  }

  public saveApplication(
    compLeadId: number,
    applicationDTO: any,
  ): Promise<any> {
    return new Promise<any>((resolve) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.saveApplication,
        url: LEAD_SETTINGS.ENDPOINTS.saveApplication.url + "/" + compLeadId,
      };

      this.dataService
        .post<Response>(updated_endpoint, applicationDTO)
        .subscribe({
          next: (response: any) => {
            if (response && typeof response.success === "boolean") {
              if (response.success) resolve(response.response);
              else resolve(null);
            } else {
              resolve(response);
            }
          },
          error: () => {
            this.alertService.showToaster(
              "An error occured. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
            resolve(null);
          },
        });
    });
  }

  public deleteDigitalApplication(appId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.deleteDigitalApplication,
        url: LEAD_SETTINGS.ENDPOINTS.deleteDigitalApplication.url + "/" + appId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe(
        (result: any) => {
          console.log("we", result);
          const list =
            result.result.response || result.response || result.result || [];
          resolve(Array.isArray(list) ? list : []);
        },
        () => resolve([]),
      );
    });
  }

  getDigitalApplicationContentWithApplicants(
    compLeadId: number,
    compPartyIds: number[],
  ): Promise<string> {
    const ep: EndpointConfig = {
      ...LEAD_SETTINGS.ENDPOINTS.getDigitalApplicationContentWithApplicants,
      url: `${LEAD_SETTINGS.ENDPOINTS.getDigitalApplicationContentWithApplicants.url}`,
      type: "POST",
    };

    const body = { compLeadId, compPartyIds };
    return new Promise((resolve) => {
      this.dataService.post<any>(ep, body).subscribe(
        (res) => resolve(typeof res === "string" ? res : res.result || ""),
        () => resolve(""),
      );
    });
  }

  public getLeadStatusHistory(leadId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const updated_endpoint: EndpointConfig = {
        ...LEAD_SETTINGS.ENDPOINTS.leadStatusHistory,
        url: LEAD_SETTINGS.ENDPOINTS.leadStatusHistory.url + "/" + leadId,
      };
      this.dataService.get<any>(updated_endpoint).subscribe({
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
              resolve([]);
            }
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
