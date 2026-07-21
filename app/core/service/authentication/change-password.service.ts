import {Injectable} from '@angular/core';
import {DataService} from '../data/data.service';
import {SETTINGS} from "../../setting/commons.settings";
import {Subject} from "rxjs";

@Injectable()
export class ChangePasswordService {

  onUserPasswordReset = new Subject();
  onUserPasswordChange = new Subject();

  constructor(private dataService: DataService) {
  }

  resetUserPassword(data) {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.updateUserPassword, data)
        .subscribe((response: any) => {
          this.onUserPasswordReset.next(response);
          resolve(response);
        }, reject)
    });
  }

  changeUserPassword(data) {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.updateUserPassword, data)
        .subscribe((response: any) => {
          this.onUserPasswordChange.next(response);
          resolve(response);
        }, reject)
    });
  }


  agentUpdateUserPassword(data) {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.agentUpdateUserPassword, data)
        .subscribe((response: any) => {
          this.onUserPasswordChange.next(response);
          resolve(response);
        }, reject)
    });
  }

}
