import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from 'rxjs'
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {reject} from "q";

@Injectable()

export class FacilityDetailsService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_ID)
  selectedFacilityID;

  selectedFacility: any = {};
  onSelectedFacilityChannge: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private urlEncodeService: UrlEncodeService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getFacilityUpdateDto()
      ]).then(() => {
        resolve();
      }, reject)
    })
  }

  getFacilityUpdateDto(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (this.selectedFacilityID == null) {
        this.selectedFacility = {};
        this.onSelectedFacilityChannge.next(this.selectedFacility);
      } else {
        let data = Object.assign({}, SETTINGS.ENDPOINTS.getFacilityUpdateDto);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedFacilityID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedFacility = response;
            this.onSelectedFacilityChannge.next(this.selectedFacility);
            resolve(response)
          }, reject);
      }
    });
  }
}
