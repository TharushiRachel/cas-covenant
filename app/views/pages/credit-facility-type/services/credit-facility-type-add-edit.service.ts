import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {LocalStorage} from "ngx-webstorage";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {DataService} from "../../../../core/service/data/data.service";

@Injectable()
export class CreditFacilityTypeAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CREDIT_FACILITY_TYPE_ID)
  selectedCreditFacilityTypeID;

  selectedFacilityType: any = {};
  onSelectedCreditFacilityTypeChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(
    private urlEncodeService: UrlEncodeService,
    private dataService: DataService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([

        this.getCreditFacilityTypeUpdateDTO()
      ]).then(() => {
        resolve();
      }, reject)
    })
  }

  getCreditFacilityTypeUpdateDTO(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (this.selectedCreditFacilityTypeID == null) {
        this.selectedFacilityType = {};
        this.onSelectedCreditFacilityTypeChange.next(this.selectedFacilityType);
        resolve({})
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getCreditFacilityTypeUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedCreditFacilityTypeID)
        this.dataService.get(data)
          .subscribe(response => {
            this.selectedFacilityType = response;
            this.onSelectedCreditFacilityTypeChange.next(this.selectedFacilityType)
            resolve(response)
          }, reject)
      }
    });
  }

  saveUpdateCreditFacilityType(creditFacilityType) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateCreditFacilityType, creditFacilityType)
      .subscribe(response => {
        this.selectedFacilityType = response;
        this.onSelectedCreditFacilityTypeChange.next(this.selectedFacilityType)
      })
  }

  approveOrRejectCreditFacilityType(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectCreditFacilityType, data)
      .subscribe((response: any) => {
        this.selectedFacilityType = response;
        this.onSelectedCreditFacilityTypeChange.next(this.selectedFacilityType);
      })
  }
}
