import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class SectionSubSectionAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPC_SECTION_ID)
  selectedUPCSectionID;

  selectedUPCSection: any = {};
  onSelectedUPCSectionChange: Subject<any> = new BehaviorSubject({});


  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getUPCSectionUpdateDTO()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getUPCSectionUpdateDTO(): Promise<any> {
    return new Promise((resolve, reject) => {

      if (this.selectedUPCSectionID == null) {
        this.selectedUPCSection = {};
        this.onSelectedUPCSectionChange.next(this.selectedUPCSection);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPCSectionUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedUPCSectionID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedUPCSection = response;
            this.onSelectedUPCSectionChange.next(this.selectedUPCSection);
            resolve(response);
          }, reject);
      }

    });
  }

  saveUpdateUPCSection(updatedDTO) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateUPCSection, updatedDTO)
      .subscribe((response: any) => {
        this.selectedUPCSection = response;
        this.onSelectedUPCSectionChange.next(this.selectedUPCSection);
      });
  }

  approveOrRejectUPCSection(data){
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectUPCSection,data)
      .subscribe((response:any)=>{
        this.selectedUPCSection = response;
        this.onSelectedUPCSectionChange.next(this.selectedUPCSection);
      })
  }


}
