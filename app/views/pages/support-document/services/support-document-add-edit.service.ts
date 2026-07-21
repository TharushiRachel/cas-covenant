import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class SupportDocumentAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_SUPPORTING_DOC_ID)
  selectedSupporingDocID;

  selectedSupportingDoc: any = {};
  onSupportDocChange: BehaviorSubject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {

      Promise.all([
        this.getSupportingDocUpdateDTO()
      ]).then(() => {
        resolve();
      }, reject)
    })

  }

  getSupportingDocUpdateDTO(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (this.selectedSupporingDocID == null) {
        this.selectedSupportingDoc = {};
        this.onSupportDocChange.next(this.selectedSupportingDoc);
        resolve({})
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getSupportingDocUpdateDTO)
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedSupporingDocID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedSupportingDoc = response;
            this.onSupportDocChange.next(this.selectedSupportingDoc);
            resolve(response)
          }, reject)
      }
    })
  }

  saveUpdateSupportingDoc(supportingDoc) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateSupportingDoc, supportingDoc)
      .subscribe((response: any) => {
        this.selectedSupportingDoc = response;
        this.onSupportDocChange.next(this.selectedSupportingDoc)
      })
  }

  approveOrRejectSupportingDoc(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectSupportingDoc, data)
      .subscribe((response: any) => {
        this.selectedSupportingDoc = response;
        this.onSupportDocChange.next(this.selectedSupportingDoc);
      })
  }
}
