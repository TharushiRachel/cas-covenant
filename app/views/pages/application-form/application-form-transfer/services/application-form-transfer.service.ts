import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {DataService} from "../../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {LocalStorage} from "ngx-webstorage";
import {AppUtils} from "../../../../../shared/app.utils";


@Injectable()
export class ApplicationFormTransferService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;


  onSelectedApplicationFormChange: Subject<any> = new Subject();

  onApplicationFormTransferChange: BehaviorSubject<any> = new BehaviorSubject(null);
  onApplicationFormChange: BehaviorSubject<any> = new BehaviorSubject({});


  constructor(private dataService: DataService, private urlEncodeService: UrlEncodeService,) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return null;
  }

  /*searchApplicationForms(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.getApplicationFormsForTransfer, data)
      .subscribe((response: any) => {
        this.onApplicationFormTransferChange.next(response);
      });
  }*/


  searchApplicationForms(searchData?): Promise<any> {
      return new Promise<any>((resolve, reject) => {
        this.dataService.post(SETTINGS.ENDPOINTS.getApplicationFormsForTransfer, AppUtils.trim(searchData))
          .subscribe((response: any) => {
            this.onSelectedApplicationFormChange.next(response);
            resolve(response);
          }, reject)
      })
    }

  getApplicationFormByID() {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getApplicationFormByID);
    data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedApplicationFormID);
    this.dataService.get(data)
      .subscribe((response: any) => {
        this.onApplicationFormChange.next(response);
      })
  }

   updateApplicationForm(searchData) {
       this.dataService.post(SETTINGS.ENDPOINTS.updateApplicationFormStatus, searchData)
         .subscribe((response: any) => {
           this.onApplicationFormChange.next(response);
         })
     }



}
