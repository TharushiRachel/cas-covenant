import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable({
  providedIn: "root",
})
export class CasV1Service {
  onCasV1FPDataChange = new BehaviorSubject({});

  constructor(
    private readonly dataService: DataService,
    private readonly alertService: AlertService
  ) {}

  getCustomersByAcc(accNo: string): Promise<any[]> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCasV1CustomerByAcc);
      data.url = data.url + "/" + accNo;
      this.dataService.get(data).subscribe(
        (response: any) => {
          if (response !== null && response.length > 0) {
            resolve(response);
          } else {
            this.alertService.showToaster(
              "There are no data found.",
              SETTINGS.TOASTER_MESSAGES.warning
            );
          }
        },
        (err: any) => {
          resolve([]);
        }
      );
    });
  }

  getFacilityPaperDetails(refNo: string, date: any): Promise<any> {
    return new Promise((resolve, reject) => {
      let payload: any = {
        refNo: refNo,
        facilityDate: date,
      };
      this.dataService
        .post(SETTINGS.ENDPOINTS.getFacilityPaperDetails, payload)
        .subscribe(
          (response: any) => {
            this.onCasV1FPDataChange.next(response);
            resolve(response);
          },
          (err: any) => {
            this.onCasV1FPDataChange.next(null);
            resolve(null);
          }
        );
    });
  }

  getAllPurposeOfAdvanced(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getAllPurposeOfAdvanced)
        .subscribe(
          (response: any) => {
            resolve(response);
          },
          () => {
            reject();
          }
        );
    });
  }

  getAllSectorData(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllSectorData).subscribe(
        (response: any) => {
          resolve(response);
        },
        () => {
          reject();
        }
      );
    });
  }

  getAllSubSectorData(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllSubSectorData).subscribe(
        (response: any) => {
          resolve(response);
        },
        () => {
          reject();
        }
      );
    });
  }

  getAttachments(
    refNo: string,
    paperDate: string,
    upcFormat: string
  ): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getCasV1AttachmentByRef
      );
      this.dataService.post(data, { refNo, paperDate, upcFormat }).subscribe(
        (response: any) => {
          let resultData = response.result ? response.result : response;
          if (Array.isArray(resultData)) {
            resolve(
              resultData.map((item) => ({
                label: item.codeDesc,
                sectionID: item.sectionID,
                lastModDate: item.lastModifiedDate,
                fileName: item.fileName,
                fileContent: item.fileContent ? atob(item.fileContent) : "",
              }))
            );
          } else {
            resolve([]);
          }
        },
        (error) => {
          this.alertService.showToaster('API Error', 'ERROR')
          reject(error);
        }
      );
    });
  }

  getDropdownSections(upcFormatNum: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getSections);
      this.dataService.post(data, { upcFormatNum }).subscribe(resolve, reject);
    });
  }

  getComments(refNo: string, paperDate: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCasV1CommentsByRef);
      this.dataService.post(data, { refNo, paperDate }).subscribe(
        (response: any) => {
          let resultData = response.result ? response.result : response;
          if (Array.isArray(resultData)) {
            resolve(
              resultData.map((item) => ({
                userID: item.userID,
                remarkDate: item.remarkDate || "No Record",
                remark1: item.remark1 || "No Comments",
                codeDesc: item.codeDesc || "No Type",
              }))
            );
          } else {
            resolve([]);
          }
        },
        (error) => {
          this.alertService.showToaster('API Error', 'ERROR')
          reject(error);
        }
      );
    });
  }
}
