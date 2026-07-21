import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class UpcTemplateAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPC_TEMPLATE_ID)
  selectedItemID;

  selectedItem: any = {};
  onSelectedItemChange: Subject<any> = new BehaviorSubject({});

  upcSectionData: any = [];
  onUpcSectionDataChange = new BehaviorSubject([]);

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getAllUpcSectionData(),
        this.getItemUpdateDTO(),
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getAllUpcSectionData(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAllUpcSectionData);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.upcSectionData = response;
          this.onUpcSectionDataChange.next(this.upcSectionData);
          resolve(response);
        }, reject);
    });
  }


  getItemUpdateDTO(): Promise<any> {
    return new Promise((resolve, reject) => {

      if (this.selectedItemID == null) {
        this.selectedItem = {};
        this.onSelectedItemChange.next(this.selectedItem);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPCTemplateUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedItemID);
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
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateUPCTemplate, updateItemDTO)
      .subscribe((response: any) => {
        this.selectedItem = response;
        this.onSelectedItemChange.next(this.selectedItem);
      });
  }

  approveOrRejectUpcTemplate(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectUPCTemplate, data)
      .subscribe((response: any) => {
        this.selectedItem = response;
        this.onSelectedItemChange.next(this.selectedItem);
      })
  }
}
