import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { CacheService } from "src/app/core/service/data/cache.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { ACAEPaperService } from "./acae-paper.service";

@Injectable()
export class ACAEEditStatusModelService {
  constructor(
    private dataService: DataService,
    private acaePaperService: ACAEPaperService,
  ) { }

  onACAEForwardUserGroupLOVChange = new BehaviorSubject({});
  onACAERejectUserGroupLOVChange = new BehaviorSubject({});

  onACAEForwardUserLOVChange = new BehaviorSubject({});
  onACAERejectUserLOVChange = new BehaviorSubject({});

  onACAEForwardChange = new BehaviorSubject({});
  onACAERejectChange = new BehaviorSubject({});

  onsaveACAECommentChange = new BehaviorSubject({});
  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }

  getACAERejectUserGroupLOVService(loggedInUserWorkFlowByStatusRQ, callback) {
    this.dataService.post(SETTINGS.ENDPOINTS.getACAELowerOrHigherUserGroupLOV, loggedInUserWorkFlowByStatusRQ)
      .subscribe((response: any) => {
        this.onACAERejectUserGroupLOVChange.next(response);

        if (callback) {
          callback(response);
        }
      }, (error: any) => {
        console.error("Error fetching covenant list:", error);

        if (callback) {
          callback(null, error);
        }
      });
  }

  getACAEForwardLOVService(branchAuthorityLevelRQ, callback) {
    this.dataService.post(SETTINGS.ENDPOINTS.getUserDetailListFormBranchAuthorityLevel, branchAuthorityLevelRQ)
      .subscribe((response: any) => {
        this.onACAEForwardUserLOVChange.next(response.branchAuthorityLevelResponseDTOList);
        if (callback) {
          callback(response.branchAuthorityLevelResponseDTOList);
        }
      }, (error: any) => {
        console.error("Error fetching covenant list:", error);
        if (callback) {
          callback(null, error);
        }
      });
  }

  getACAERejectLOVService(branchAuthorityLevelRQ, callback) {
    this.dataService.post(SETTINGS.ENDPOINTS.getUserDetailListFormBranchAuthorityLevel, branchAuthorityLevelRQ)
      .subscribe((response: any) => {
        this.onACAERejectUserLOVChange.next(response.branchAuthorityLevelResponseDTOList);

        if (callback) {
          callback(response.branchAuthorityLevelResponseDTOList);
        }
      }, (error: any) => {
        if (callback) {
          callback(null, error);
        }
      });
  }

  saveACAECommentService(acaeCommentRQ: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.saveACAEComment, acaeCommentRQ)
        .subscribe((response: any) => {
          resolve(response);
        }, (error) => {
          reject(error);
        });
    });
    // return this.dataService.post(SETTINGS.ENDPOINTS.saveACAEComment, acaeCommentRQ);
  }

  forwardACAEPaperService(acaePaperTransferRQ: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.forwardACAEPaper, acaePaperTransferRQ)
        .subscribe((response: any) => {
          resolve(response);
        }, (error) => {
          reject(error);
        });
    });
    // return this.dataService.post(SETTINGS.ENDPOINTS.forwardACAEPaper, acaePaperTransferRQ);
  }

  toBeResubmittedACAEPaperService(acaePaperTransferRQ: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.toBeResubmittedACAEPaper, acaePaperTransferRQ)
        .subscribe((response: any) => {
          resolve(response);
        }, (error) => {
          reject(error);
        });
    });
    // return this.dataService.post(SETTINGS.ENDPOINTS.toBeResubmittedACAEPaper, acaePaperTransferRQ);
  }

  updateEscalationDaysService(acaePaperTransferRQ) {
    return this.dataService.post(SETTINGS.ENDPOINTS.updateEscalationDays, acaePaperTransferRQ);
  }

  getPreviousUsersService(acaePreviousUserRQ: any) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getPreviousUsers, acaePreviousUserRQ);
  }

}