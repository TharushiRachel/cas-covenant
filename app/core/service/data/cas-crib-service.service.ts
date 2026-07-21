import {Injectable} from '@angular/core';
import {SETTINGS} from "../../setting/commons.settings";
import {DataService} from "./data.service";
import {Subject} from "rxjs";

@Injectable()
export class CasCribServiceService {

  onCribReportHistoryChange = new Subject();
  onCribReportChange = new Subject();

  constructor(private dataService: DataService) {
  }

  getRetailCribReportFromCasDB(data): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getRetailCribReportFromCasDB, data)
        .subscribe((response: any) => {
          this.onCribReportHistoryChange.next(response);
          resolve(response);
        }, reject);
    });
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

}
