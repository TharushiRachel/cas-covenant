import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";
import { DataService } from "src/app/core/service/data/data.service";
import { EndpointConfig } from "src/app/shared/interfaces/EndpointConfig";
import { COVENANT_SETTINGS } from "./endpoints";

@Injectable({
  providedIn: "root",
})
export class CovenantService implements Resolve<any> {
  onCustomerCovenantTabChange = new BehaviorSubject<any>({});
  onFacilityCovenantTabChange = new BehaviorSubject<any>({});

  constructor(private dataService: DataService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return Promise.resolve({});
  }

  saveCustomerCovenant(data: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.saveCustomerCovenant, data).then(
      (response) => {
        this.onCustomerCovenantTabChange.next(response);
        return response;
      }
    );
  }

  updateCustomerCovenant(data: any): Promise<any> {
    return this.post(
      COVENANT_SETTINGS.ENDPOINTS.updateCustomerCovenant,
      data
    ).then((response) => {
      this.onCustomerCovenantTabChange.next(response);
      return response;
    });
  }

  getAllCustomerCovenant(facilityPaperId: number): Promise<any> {
    const endpoint: EndpointConfig = {
      ...COVENANT_SETTINGS.ENDPOINTS.getAllCustomerCovenant,
      url:
        COVENANT_SETTINGS.ENDPOINTS.getAllCustomerCovenant.url +
        "/" +
        facilityPaperId,
    };
    return this.get(endpoint);
  }

  saveFacilityCovenants(data: any): Promise<any> {
    return this.post(
      COVENANT_SETTINGS.ENDPOINTS.saveFacilityCovenants,
      data
    ).then((response) => {
      this.onFacilityCovenantTabChange.next(response);
      return response;
    });
  }

  updateFacilityCovenant(data: any): Promise<any> {
    return this.post(
      COVENANT_SETTINGS.ENDPOINTS.updateFacilityCovenant,
      data
    ).then((response) => {
      this.onFacilityCovenantTabChange.next(response);
      return response;
    });
  }

  getAllFacilityCovenant(facilityPaperId: number): Promise<any> {
    const endpoint: EndpointConfig = {
      ...COVENANT_SETTINGS.ENDPOINTS.getAllFacilityCovenant,
      url:
        COVENANT_SETTINGS.ENDPOINTS.getAllFacilityCovenant.url +
        "/" +
        facilityPaperId,
    };
    return this.get(endpoint);
  }

  /**
   * Maps getAllFacilityCovenant result into the legacy [{ covValue: [...] }] shape
   * used by existing covenant UI grouping logic.
   */
  getAllFacilityCovenantLegacy(facilityPaperId: number): Promise<any[]> {
    return this.getAllFacilityCovenant(facilityPaperId).then((response) => {
      const list = this.unwrapList(response);
      const covValue = list.map((dto: any) => {
        return Object.assign({}, dto, {
          applicationCovenantFacilityDTOS:
            dto.covenantFacilities || dto.applicationCovenantFacilityDTOS || [],
        });
      });
      return [{ covValue: covValue }];
    });
  }

  getCovenantsDetails(payload: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.getCovenantsDetails, payload);
  }

  getCovenantDetailsFromFinacle(
    custId: string,
    facilityPaperId: number
  ): Promise<any> {
    const payload = {
      requestId: "CAS_0001",
      custId: custId,
      acctId: "",
      facilityPaperId: facilityPaperId,
    };
    return this.getCovenantsDetails(payload).then((response) => {
      return this.unwrap(response);
    });
  }

  getCovenantList(data: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.getCovenantList, data).then(
      (response) => this.unwrap(response)
    );
  }

  addCommentToCovenant(data: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.addCommentToCovenant, data);
  }

  getCovenantCommentList(facilityPaperId: number): Observable<any> {
    const endpoint: EndpointConfig = {
      ...COVENANT_SETTINGS.ENDPOINTS.getCovenantCommentList,
      url:
        COVENANT_SETTINGS.ENDPOINTS.getCovenantCommentList.url +
        "/" +
        facilityPaperId,
    };
    return this.dataService.get(endpoint).pipe(
      map((response: any) => this.unwrapList(response))
    );
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

  private post(endpoint: EndpointConfig, data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(endpoint, data).subscribe(
        (response: any) => resolve(response),
        (error: any) => reject(error)
      );
    });
  }

  private get(endpoint: EndpointConfig): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(endpoint).subscribe(
        (response: any) => resolve(response),
        (error: any) => reject(error)
      );
    });
  }
}
