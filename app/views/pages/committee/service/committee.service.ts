import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
import { LocalStorage } from "ngx-webstorage";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Injectable({
  providedIn: "root",
})
export class CommitteeService {
  onSelectedTypeChange: BehaviorSubject<any> = new BehaviorSubject({});
  onSelectedCommitteeChange: Subject<any> = new BehaviorSubject({});

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_ID)
  selectedCommitteeId;
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_FETCH_TYPE)
  selectedCommitteeFetchType;

  selectedCommittee: any;

  constructor(
    private dataService: DataService,
    private urlEncodeService: UrlEncodeService,
    private router: Router
  ) {}

  subcribeTypeData(item: any) {
    this.onSelectedTypeChange.next(item);
  }

  subcribeCommitteeData(item: any) {
    this.onSelectedCommitteeChange.next(item);
  }

  savePool(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveUserPool, data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  getUserPool(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getUserPool);
      data.url = data.url;
      this.dataService.get(data).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        }
      );
    });
  }

  savePoolUserStatus(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.savePoolUserStatus, data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  approveOrRejectPoolUser(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.approveOrRejectUser, data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  //committe type
  saveCommitteeType(data: any): Promise<any> {

    if (data.committeeTypeId && data.committeeTypeId > 0) {
      const endpointData = Object.assign(
        {},
        SETTINGS.ENDPOINTS.updateCommitteType
      );
      endpointData.url = endpointData.url + "/" + data.committeeTypeId;

      return new Promise((resolve, reject) => {
        this.dataService.post(endpointData, data).subscribe((response: any) => {
          resolve(response);
        });
      });
    } else {
      return new Promise((resolve, reject) => {
        this.dataService
          .post(SETTINGS.ENDPOINTS.saveCommitteType, data)
          .subscribe((response: any) => {
            resolve(response);
          });
      });
    }
  }

  getCommitteeType(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCommitteeType);
      data.url = data.url;
      this.dataService.get(data).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        }
      );
    });
  }

  //committee
  getCommitteeUpdateDTO(): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.selectedCommitteeId == null) {
        this.selectedCommittee = {};
        this.onSelectedCommitteeChange.next(this.selectedCommittee);
        resolve({});
      } else {
        resolve({});
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getCommitteeById);
        data.url =
          data.url +
          "/" +
          this.urlEncodeService.decode(this.selectedCommitteeId) +
          "/" +
          this.urlEncodeService.decode(this.selectedCommitteeFetchType);

        this.dataService.get(data).subscribe(
          (response: any) => {
            this.selectedCommittee = response;
            this.onSelectedCommitteeChange.next(this.selectedCommittee);
            resolve(response);
          },
          (reject) => {
            this.router.navigate(["/committee/all"]);
          }
        );
      }
    });
  }

  saveCommittee(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveCommitte, data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  getCommittees(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCommittees);
      data.url = data.url;
      this.dataService.get(data).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        }
      );
    });
  }

  getCommittee(committeeId: any, type: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCommittees);
      data.url = data.url + "/" + committeeId;
      this.dataService.get(data).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        }
      );
    });
  }

  saveApproveRejectCommittee(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveApproveRejectCommitte, data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  getCommitteeComments(committeeId: any, tableType: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCommitteComments);
      data.url = data.url + "/" + committeeId + "/" + tableType;
      this.dataService.get(data).subscribe(
        (response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        }
      );
    });
  }
}
