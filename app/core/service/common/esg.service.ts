import { Injectable } from "@angular/core";
import { DataService } from "../data/data.service";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { SETTINGS } from "../../setting/commons.settings";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class EsgService implements Resolve<any> {
  onAnnexuresChange: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  onESGChange: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);

  constructor(private readonly dataService: DataService) {}
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    throw new Error("Method not implemented.");
  }

  //get annexure list
  getAnnexureList(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAnnexureList).subscribe(
        (response: any) => {
          if (response && response.length > 0) {
            resolve(response);
            this.onAnnexuresChange.next(response);
          } else {
            resolve([]);
          }
        },
        () => {
          resolve([]);
        }
      );
    });
  }

  //getAnnexureByAnnexureId
  getAnnexureByID(annexureID: number): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAnnexureById);
      data.url = data.url + "/" + annexureID;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

  //getAnnexureByPaperid -> getAnnexureDataByApplicaionFormOrFacilityPaperId
  getAnnexureByPaperID(payload: {
    applicationFormID: number;
    facilityPaperID: number | null;
  }): Promise<any[]> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getAnnexureByPaperID, payload)
        .subscribe(
          (res: any) => {
            const annexures = Array.isArray(res) ? res : [];

            if (!annexures.length) {
              console.warn("Warning: No annexures found in result");
            }

            this.onESGChange.next(annexures);
            resolve(annexures);
          },
          (err) => {
            this.onESGChange.next([]);
            reject(err);
          }
        );
    });
  }

  //saveAnnexureAnswer -> saveDataToAnnexure
  saveAnnexureAnswers(payload: any[]): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveEsgAnnexure, payload)
        .subscribe((res: any) => {
          resolve(res.result || res);
        }, reject);
    });
  }

  //updateDataToAnnexure
  updateAnnexureAnswers(payload: any): Promise<any> {
    const data = { ...SETTINGS.ENDPOINTS.updateDataToAnnexure };

    const queryParams = [];
    if (
      payload.applicationFormID !== null &&
      payload.applicationFormID !== undefined
    ) {
      queryParams.push(`applicationFormID=${payload.applicationFormID}`);
    }
    if (
      payload.facilityPaperID !== null &&
      payload.facilityPaperID !== undefined
    ) {
      queryParams.push(`facilityPaperID=${payload.facilityPaperID}`);
    }

    const queryString = queryParams.length ? `?${queryParams.join("&")}` : "";
    data.url += `/${payload.annexureID}${queryString}`;
    return new Promise((resolve, reject) => {
      this.dataService.post(data, payload.answers).subscribe((res: any) => {
        resolve(res.result || res);
      }, reject);
    });
  }

  //deleteAnnexure -> deleteAnnexureByAnnexureId
  deleteAnnexure(payload: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.deleteAnnexure, payload)
        .subscribe(resolve, reject);
    });
  }

  //refreshAnnexure
  refreshAnnexure(payload: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.refreshAnnexure, payload)
        .subscribe(resolve, reject);
    });
  }

  //addAttachement
  addEsgAtttachment(payload: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.addEsgAtttachment, payload)
        .subscribe((res: any) => {
          resolve(res.result || res);
        }, reject);
    });
  }

  //getEsgAttachments
  getEsgAttachments(payload: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getEsgAttachments, payload)
        .subscribe((res: any) => resolve(res.result || res), reject);
    });
  }

  //getEsgAttachmentByID
  getEsgAttachmentById(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAttachmentById);
      data.url = data.url + "/" + id;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }

  //editESGAttachment
  updateAttachment(id: number, payload: any): Promise<any> {
    const endpointConfig = SETTINGS.ENDPOINTS.updateESGAttachment;
    if (!endpointConfig || !endpointConfig.url) {
      console.error(
        "updateESGAttachment endpoint not configured properly",
        endpointConfig
      );
      return Promise.reject("Invalid endpoint configuration");
    }

    return new Promise((resolve, reject) => {
      const endpoint = {
        ...endpointConfig,
        url: `${endpointConfig.url}/${id}`,
      };

      this.dataService.post(endpoint, payload).subscribe(
        (response: any) => resolve(response),
        (error: any) => {
          console.error("Update API error", error);
          reject(error);
        }
      );
    });
  }

  //deleteESGAttachment
  deleteAttachment(id: number): Promise<any> {
    const payload = {
      esgStorageID: id,
    };
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.deleteESGAttachment, payload)
        .subscribe(resolve, reject);
    });
  }
}
