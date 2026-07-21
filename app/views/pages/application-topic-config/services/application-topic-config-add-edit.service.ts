import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {DataService} from "../../../../core/service/data/data.service";

@Injectable()
export class ApplicationTopicConfigAddEditService implements Resolve<any> {
  uploadedApplicationFormConfigTopicListChange = new BehaviorSubject({});

  constructor(private dataService: DataService,) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getApplicationTopicConfig(),
      ]).then(() => {
        resolve()
      }, reject)
    })
  }

  uploadApplicationTopicConfigFile(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.uploadApplicationTopicConfigFile, data)
      .subscribe((response: any) => {
        if (response) {
          this.uploadedApplicationFormConfigTopicListChange.next(response);
        }
      })
  }

  getApplicationTopicConfig() {
    this.dataService.get(SETTINGS.ENDPOINTS.getApplicationTopicConfigs)
      .subscribe((response: any) => {
        if (response) {
          this.uploadedApplicationFormConfigTopicListChange.next(response);
        }
      })
  }


}
