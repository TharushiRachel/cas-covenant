import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class RoleAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_ROLE_ID)
  selectedRoleID;

  selectedRole: any = {};
  onSelectedRoleChange: Subject<any> = new BehaviorSubject({});

  systemPrivileges: any = {};
  onSystemPrivilegesChange: Subject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getSystemPrivileges(),
        this.getRoleUpdateDTO()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getSystemPrivileges(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getSystemPrivileges);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.systemPrivileges = response;
          this.onSystemPrivilegesChange.next(this.systemPrivileges);
          resolve(response);
        }, reject);
    });
  }

  getRoleUpdateDTO(): Promise<any> {
    return new Promise((resolve, reject) => {

      if (this.selectedRoleID == null) {
        this.selectedRole = {};
        this.onSelectedRoleChange.next(this.selectedRole);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getRoleUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedRoleID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedRole = response;
            this.onSelectedRoleChange.next(this.selectedRole);
            resolve(response);
          }, reject);
      }

    });
  }

  saveUpdateRole(role) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateRole, role)
      .subscribe((response: any) => {
        this.selectedRole = response;
        this.onSelectedRoleChange.next(this.selectedRole);
      });
  }


  approveOrRejectRole(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectRole, data)
      .subscribe((response: any) => {
        this.selectedRole = response;
        this.onSelectedRoleChange.next(this.selectedRole);
      })
  }

}
