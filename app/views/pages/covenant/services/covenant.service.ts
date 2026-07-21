import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { DataService } from "src/app/core/service/data/data.service";
import { EndpointConfig } from "src/app/shared/interfaces/EndpointConfig";
import { COVENANT_SETTINGS } from "./endpoints";

@Injectable({
  providedIn: "root",
})
export class CovenantService implements Resolve<any> {
  onCustomerCovenantTabChange = new BehaviorSubject<any>({});
  onFacilityCovenantTabChange = new BehaviorSubject<any>({});

  constructor(private readonly dataService: DataService) {}

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
    return this.post(COVENANT_SETTINGS.ENDPOINTS.updateCustomerCovenant, data).then(
      (response) => {
        this.onCustomerCovenantTabChange.next(response);
        return response;
      }
    );
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
    return this.post(COVENANT_SETTINGS.ENDPOINTS.saveFacilityCovenants, data).then(
      (response) => {
        this.onFacilityCovenantTabChange.next(response);
        return response;
      }
    );
  }

  updateFacilityCovenant(data: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.updateFacilityCovenant, data).then(
      (response) => {
        this.onFacilityCovenantTabChange.next(response);
        return response;
      }
    );
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

  getCovenantsDetails(payload: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.getCovenantsDetails, payload);
  }

  getCovenantList(data: any): Promise<any> {
    return this.post(COVENANT_SETTINGS.ENDPOINTS.getCovenantList, data);
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
    return this.dataService.get(endpoint);
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
