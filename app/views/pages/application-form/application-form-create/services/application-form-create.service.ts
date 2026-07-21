import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {DataService} from "../../../../../core/service/data/data.service";

@Injectable()
export class ApplicationFormCreateService implements Resolve<any> {
  onCribReportChange = new BehaviorSubject({});

  constructor(private dataService: DataService,) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
  }

  getRetailCribReport(data): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getRetailCribReport, data)
        .subscribe((response: any) => {
          this.onCribReportChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getCorporateCribReport(data): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getCorporateCribReport, data)
        .subscribe((response: any) => {
          this.onCribReportChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  draftApplicationForm(searchData) {
    return this.dataService.post(SETTINGS.ENDPOINTS.draftApplicationForm, searchData);
  }

}
