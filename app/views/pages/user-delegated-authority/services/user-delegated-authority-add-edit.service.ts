import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {LocalStorage} from "ngx-webstorage";

@Injectable()
export class UserDelegatedAuthorityAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_USER_DA_ID)
  selectedUserDaID;

  selectedUserDA: any = {};
  onSelectedUserDaChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private  urlEncodeService: UrlEncodeService) {

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([

        this.getUserDelegatedAuthorityDTO()
      ]).then(() => {
        resolve();
      }, reject)
    })
  }

  getUserDelegatedAuthorityDTO(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (this.selectedUserDaID == null) {
        this.selectedUserDA = {};
        this.onSelectedUserDaChange.next(this.selectedUserDA);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getUserDaUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedUserDaID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedUserDA = response;
            this.onSelectedUserDaChange.next(this.selectedUserDA);
            resolve(response)
          }, reject)
      }
    })
  }

  saveUpdateUserDa(userDa) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateUserDa, userDa)
      .subscribe(response => {
        this.selectedUserDA = response;
        this.onSelectedUserDaChange.next(this.selectedUserDA);
      })
  }

  approveOrRejectUserDa(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectUserDa, data)
      .subscribe((response: any) => {
        this.selectedUserDA = response;
        this.onSelectedUserDaChange.next(this.selectedUserDA);
      })
  }

}
