import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class ApplicationFormTopicAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_TOPIC_ID)
  selectedApplicationFormTopicID;

  selectedItem: any = {};
  onSelectedItemChange: Subject<any> = new BehaviorSubject({});
  onSelectedTemplateUPCSections: Subject<any> = new Subject<any>();
  onUpcTemplateList = new BehaviorSubject({});


  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getItemUpdateDTO(),
        this.getActiveApprovedUpcTemplates()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getItemUpdateDTO(): Promise<any> {
    return new Promise((resolve, reject) => {

      if (this.selectedApplicationFormTopicID == null) {
        this.selectedItem = {};
        this.onSelectedItemChange.next(this.selectedItem);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getAFTopicByID);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedApplicationFormTopicID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedItem = response;
            this.onSelectedItemChange.next(this.selectedItem);
            resolve(response);
          }, reject);
      }
    });
  }

  saveUpdateItem(updateItemDTO) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateAFTopic, updateItemDTO)
      .subscribe((response: any) => {
        this.selectedItem = response;
        this.onSelectedItemChange.next(this.selectedItem);
      });
  }

  approveOrReject(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveAFTopic, data)
      .subscribe((response: any) => {
        this.selectedItem = response;
        this.onSelectedItemChange.next(this.selectedItem);
      })
  }

  getActiveApprovedUpcTemplates(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getActiveApprovedUpcTemplateDtoList);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.onUpcTemplateList.next(response);
          resolve(response);
        }, reject);
    })
  }

  getActiveApprovedUpcSectionListByTemplateID(UpcTemplateID) {
    this.dataService.post(SETTINGS.ENDPOINTS.getActiveApprovedUpcSectionListByTemplateID, UpcTemplateID)
      .subscribe((response: any) => {
        this.onSelectedTemplateUPCSections.next(response);
      });
  }
}
