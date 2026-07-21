import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { DataService } from "../../../../core/service/data/data.service";
import { SETTINGS } from "../../../../core/setting/commons.settings";
import { LocalStorage } from "ngx-webstorage";
import { UrlEncodeService } from "../../../../core/service/application/url-encode.service";
import { BoardCreditCommitteePaperDTO } from "../dto/bcc-paper-dto";
import { Constants } from "src/app/core/setting/constants";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AlertService } from "src/app/core/service/common/alert.service";

@Injectable()
export class BccReportingService implements Resolve<any> {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperIDForBccReport;
  onFacilityPaperChange = new BehaviorSubject({});
  onFPFacilitiesChange = new BehaviorSubject({});
  onCreditRiskCommentListChange = new BehaviorSubject({});
  onReviewerCommentListChange = new BehaviorSubject({});
  onFpCustomerChange = new BehaviorSubject({});
  onFpTotalExposureChange = new BehaviorSubject({});
  onFpUpcSectionChange = new BehaviorSubject({});
  onBCCPaperChange = new BehaviorSubject({});
  onBCCPaperRemoveChange = new BehaviorSubject({});
  onBCCPaperChangeUser = new BehaviorSubject({});
  onCustomerChange = new BehaviorSubject({});
  onFormDataChange = new BehaviorSubject({});
  facilityPaperDTO: any = {};
  bccPaperDTO: BoardCreditCommitteePaperDTO = new BoardCreditCommitteePaperDTO(
    {}
  );
  onBccReportDownloadContentChange = new Subject();
  onBccReportDownloadContent: any = "";
  customerDTO: any = {};
  fpCustomerList;

  constructor(
    private readonly dataService: DataService,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly cacheService: CacheService,
    private readonly alertService: AlertService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      if (this.selectedFacilityPaperIDForBccReport == null) {
        this.facilityPaperDTO = {};
        resolve({});
      } else {
        Promise.all([
          this.getFacilityPaperByID(),
          this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        ]).then(() => {
          resolve({});
        }, reject);
      }
    });
  }

  getFacilityPaperByID(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getFacilityPaperByID);
      data.url =
        data.url +
        "/" +
        this.urlEncodeService.decode(this.selectedFacilityPaperIDForBccReport);
      this.dataService.get(data).subscribe((response: any) => {
        this.facilityPaperDTO = response;
        this.onFacilityPaperChange.next(response);
        resolve(response);
      }, reject);
    });
  }

  updateBCCPaper(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.updateBCCPaper, data)
      .subscribe((response: any) => {
        this.onBCCPaperChange.next(response);
      });
  }

  updateBCCPDFReport(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.updateBCCPDFReport, data)
      .subscribe((response: any) => {
        this.onBCCPaperChange.next(response);
      });
  }

  getBCCReport(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.getBCCReport, data)
      .subscribe((response: any) => {
        this.onBccReportDownloadContent = response;
        this.onBccReportDownloadContentChange.next(response);
      });
  }

  createBCCPaper(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.createBCCPaper, data)
      .subscribe((response: any) => {
        if (response) {
          this.facilityPaperDTO = response;
          this.onFacilityPaperChange.next(response);
        }
      });
  }

  getBCCPaperByFacilityPaperByID(facilityPaperID) {
    let data = { facilityPaperID: facilityPaperID };
    this.dataService
      .post(SETTINGS.ENDPOINTS.getBCCPaperByFacilityPaperByID, data)
      .subscribe((response: any) => {
        this.bccPaperDTO = new BoardCreditCommitteePaperDTO(response);
        this.onBCCPaperChange.next(this.bccPaperDTO);
      });
  }

  saveFpDirectorDetails(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.addEditDirectorDetails, data)
      .subscribe((response: any) => {
        this.facilityPaperDTO = response;
        this.onFacilityPaperChange.next(this.facilityPaperDTO);
      });
  }

  getCustomerByID(customerID): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCustomerByID);
      data.url = data.url + "/" + customerID;
      this.dataService.get(data).subscribe((response: any) => {
        this.customerDTO = response;
        this.onCustomerChange.next(this.customerDTO);
        resolve(response);
      }, reject);
    });
  }

  saveOrUpdateCompanyDirectorDetails(companyDirector) {
    this.dataService
      .post(
        SETTINGS.ENDPOINTS.saveOrUpdateCompanyDirectorDetails,
        companyDirector
      )
      .subscribe((response: any) => {
        this.onBCCPaperChange.next(response);
      });
  }

  saveOrUpdateBccFacilities(companyDirector) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateBccFacilities, companyDirector)
      .subscribe((response: any) => {
        this.onBCCPaperChange.next(response);
      });
  }

  saveOrUpdateRiskRatingYear(riskRatingYear) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateRiskRatingYear, riskRatingYear)
      .subscribe((response: any) => {
        this.onBCCPaperChange.next(response);
      });
  }

  saveOrUpdateBCCCustomerCribDetails(customerCribDetails) {
    this.dataService
      .post(
        SETTINGS.ENDPOINTS.saveOrUpdateBCCCustomerCribDetails,
        customerCribDetails
      )
      .subscribe((response: any) => {
        this.onBCCPaperChange.next(response);
      });
  }

  deactivateBccPaper(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.deactivateBccPaper, data)
      .subscribe((response: any) => {
        this.onBCCPaperRemoveChange.next(response);
      });
  }

  changeAssignUserBCCPaper(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.changeAssignUserBCCPaper, data)
      .subscribe((response: any) => {
        this.onBCCPaperChangeUser.next(response);
      });
  }

  getComparableContent(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getComparableContent, payload)
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    });
  }

  bCCPaperSubmission(facilityPaperID: any) {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.bCCPaperSubmission);
    data.url = data.url + "/" + facilityPaperID;

    this.dataService.post(data).subscribe(
      (response: any) => {
        this.onBCCPaperChange.next(response);
        if (response && response.isSubmitted == Constants.yesNoConst.Y) {
          this.alertService.showToaster(
            "Statement has been successfully submitted.",
            SETTINGS.TOASTER_MESSAGES.success
          );
        } else {
          this.alertService.showToaster(
            "Failed to submit statement.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      },
      (err: any) => {
        this.alertService.showToaster(
          "An error occurd. please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  getCompReportData(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCompReportData, payload)
        .subscribe(
          (response: any) => {
            let dataList: any[] = response.responseData
              ? response.responseData
              : [];
            resolve(dataList);
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "An error occurd. please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  getLimitNodeData(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getLimitNodeData, payload)
        .subscribe(
          (response: any) => {
            let data: any[] = response.nodeDetails
              ? response.nodeDetails
              : null;

            resolve(data);
          },
          (err: any) => {
            resolve(null);
            this.alertService.showToaster(
              "An error occurd. please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
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

  getCommissionData(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCommissionData, payload)
        .subscribe(
          (response: any) => {
            if (response && response.status === "Success") {
              let dataList: any[] = response.reportDtls
                ? response.reportDtls
                : [];
              resolve(dataList);
            } else {
              resolve([]);
            }
          },
          (err: any) => {
            resolve([]);
          }
        );
    });
  }

  getCommissionLoanAccounts(payload: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCommissionLoanAccounts, payload)
        .subscribe(
          (response: any) => {
            let data: any[] = response.reportDtls ? response.reportDtls : [];

            resolve(data);
          },
          (err: any) => {
            resolve([]);
            this.alertService.showToaster(
              "An error occurd. please try again later.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
    });
  }

  getProductGroups(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getProductGroups).subscribe(
        (response: any) => {
          let data: any[] = response ? response : [];
          resolve(data);
        },
        (err: any) => {
          resolve([]);
        }
      );
    });
  }

  handleFormData(formData: any) {
    this.onFormDataChange.next(formData);
  }
}
