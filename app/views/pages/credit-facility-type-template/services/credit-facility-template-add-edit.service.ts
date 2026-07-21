import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {BehaviorSubject, Observable} from "rxjs";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {DataService} from "../../../../core/service/data/data.service";
import {CacheService} from "../../../../core/service/data/cache.service";
import {Constants} from "../../../../core/setting/constants";

@Injectable()
export class CreditFacilityTemplateAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CREDIT_FACILITY_TEMPLATE_ID)
  selectedCreditFacilityTemplateID;

  supportingDocList: any = [];
  selectedCreditFacilityTemplate: any = {};
  creditFacilityTypeList = [];
  onLoadSupportingDocListChange: BehaviorSubject<any> = new BehaviorSubject({});
  onloadCreditFacilityTypeListChange: BehaviorSubject<any> = new BehaviorSubject<any>({});
  onSelectCreditFacilityTemplateChange: BehaviorSubject<any> = new BehaviorSubject<any>({});


  constructor(
    private urlEncodeService: UrlEncodeService,
    private dataService: DataService,
    private cacheService: CacheService
  ) {
  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    new Promise((resolve, reject) => {
      Promise.all([
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUPPORTING_DOCs),
        this.getCreditFacilityTemplateUpdateDTO(),
        this.getApprovedSupportingDoc(),
      ]).then(() => {
        resolve();
      }, reject)
    })
  }

  getCreditFacilityTemplateUpdateDTO(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (this.selectedCreditFacilityTemplateID == null) {
        this.selectedCreditFacilityTemplate = {};
        this.onSelectCreditFacilityTemplateChange.next(this.selectedCreditFacilityTemplate)
        resolve({})
      } else {
        let data = Object.assign({}, SETTINGS.ENDPOINTS.getCreditFacilityTemplateUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedCreditFacilityTemplateID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedCreditFacilityTemplate = response;
            this.onSelectCreditFacilityTemplateChange.next(this.selectedCreditFacilityTemplate);
            resolve(response)
          }, reject);
      }
    })
  }

  getApprovedSupportingDoc(): Promise<any> {
    return new Promise((resolve, reject) => {
      let data = Object.assign({}, SETTINGS.ENDPOINTS.getApprovedSupportingDocList);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.supportingDocList = response;
          this.onLoadSupportingDocListChange.next(response);
          resolve(response)
        }, reject)
    })
  }

  saveUpdateCreditFacilityTemplate(template) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateCreditFacilityTemplate, template)
      .subscribe((reponse: any) => {
        this.selectedCreditFacilityTemplate = reponse;
        this.onSelectCreditFacilityTemplateChange.next(this.selectedCreditFacilityTemplate);
      })
  }

  approveOrRejectCreditFacilityTemplate(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectCreditFacilityTemplate, data)
      .subscribe((response: any) => {
        this.selectedCreditFacilityTemplate = response;
        this.onSelectCreditFacilityTemplateChange.next(this.selectedCreditFacilityTemplate);
      })
  }
}
