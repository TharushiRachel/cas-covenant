import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { DataService } from "src/app/core/service/data/data.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { EndpointConfig } from "src/app/shared/interfaces/EndpointConfig";
import { COVENANT_SETTINGS } from "./endpoints";

@Injectable({
  providedIn: "root",
})
export class CovenantService implements Resolve<any> {
  onCustomerCovenantTabChange = new BehaviorSubject<any>({});
  onFacilityCovenantTabChange = new BehaviorSubject<any>({});

  constructor(
    private dataService: DataService,
    private alertService: AlertService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return Promise.resolve({});
  }

  saveCustomerCovenant(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.saveCustomerCovenant, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              this.onCustomerCovenantTabChange.next(response);
              resolve(response);
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  updateCustomerCovenant(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.updateCustomerCovenant, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              this.onCustomerCovenantTabChange.next(response);
              resolve(response);
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  getAllCustomerCovenant(facilityPaperId: number) {
    return new Promise((resolve, reject) => {
      const endpoint: EndpointConfig = Object.assign(
        {},
        COVENANT_SETTINGS.ENDPOINTS.getAllCustomerCovenant
      );
      endpoint.url = endpoint.url + "/" + facilityPaperId;

      this.dataService.get(endpoint).subscribe(
        (response: any) => {
          if (response) {
            resolve(this.unwrap(response));
          }
        },
        (err: any) => {
          this.alertService.showToaster(
            "An error occurred. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error
          );
          resolve([]);
        }
      );
    });
  }

  saveFacilityCovenants(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.saveFacilityCovenants, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              this.onFacilityCovenantTabChange.next(response);
              resolve(response);
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  updateFacilityCovenant(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.updateFacilityCovenant, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              this.onFacilityCovenantTabChange.next(response);
              resolve(response);
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  getAllFacilityCovenant(facilityPaperId: number) {
    return new Promise((resolve, reject) => {
      const endpoint: EndpointConfig = Object.assign(
        {},
        COVENANT_SETTINGS.ENDPOINTS.getAllFacilityCovenant
      );
      endpoint.url = endpoint.url + "/" + facilityPaperId;

      this.dataService.get(endpoint).subscribe(
        (response: any) => {
          if (response) {
            resolve(this.unwrap(response));
          }
        },
        (err: any) => {
          this.alertService.showToaster(
            "An error occurred. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error
          );
          resolve([]);
        }
      );
    });
  }

  getAllFacilityCovenantLegacy(facilityPaperId: number): Promise<any[]> {
    return this.getAllFacilityCovenant(facilityPaperId).then((response: any) => {
      const list = Array.isArray(response) ? response : this.unwrapList(response);
      const covValue = list.map((dto: any) => {
        return Object.assign({}, dto, {
          applicationCovenantFacilityDTOS:
            dto.covenantFacilities || dto.applicationCovenantFacilityDTOS || [],
        });
      });
      return [{ covValue: covValue }];
    });
  }

  getCovenantsDetails(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.getCovenantsDetails, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(this.unwrap(response));
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  getCovenantDetailsFromFinacle(custId: string, facilityPaperId: number) {
    const payload = {
      requestId: "CAS_0001",
      custId: custId,
      acctId: "",
      facilityPaperId: facilityPaperId,
    };
    return this.getCovenantsDetails(payload);
  }

  getCovenantList(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.getCovenantList, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(this.unwrap(response));
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  addCommentToCovenant(payload: any) {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(COVENANT_SETTINGS.ENDPOINTS.addCommentToCovenant, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
            }
          },
          (err: any) => {
            this.alertService.showToaster(
              "An error occurred. Please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
            resolve([]);
          }
        );
    });
  }

  getCovenantCommentList(facilityPaperId: number) {
    return new Promise((resolve, reject) => {
      const endpoint: EndpointConfig = Object.assign(
        {},
        COVENANT_SETTINGS.ENDPOINTS.getCovenantCommentList
      );
      endpoint.url = endpoint.url + "/" + facilityPaperId;

      this.dataService.get(endpoint).subscribe(
        (response: any) => {
          if (response) {
            resolve(this.unwrapList(response));
          }
        },
        (err: any) => {
          this.alertService.showToaster(
            "An error occurred. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error
          );
          resolve([]);
        }
      );
    });
  }

  unwrap(response: any): any {
    if (response == null) {
      return response;
    }
    if (response.response !== undefined && response.response !== null) {
      return response.response;
    }
    if (
      response.result &&
      response.result.response !== undefined &&
      response.result.response !== null
    ) {
      return response.result.response;
    }
    return response;
  }

  unwrapList(response: any): any[] {
    const data = this.unwrap(response);
    return Array.isArray(data) ? data : [];
  }
}
