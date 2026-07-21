import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class WorkflowTemplateAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_WORKFLOW_TEMPLATE_ID)
  selectedItemID;

  selectedItem: any = {};
  onSelectedItemChange: Subject<any> = new BehaviorSubject({});

  approvedUPMGroupList: any = {};
  onApprovedUPMGroupListChange: Subject<any> = new BehaviorSubject({});


  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getItemUpdateDTO(),
        this.getAllApprovedUPMGroups()
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

      if (this.selectedItemID == null) {
        this.selectedItem = {};
        this.onSelectedItemChange.next(this.selectedItem);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getWorkflowTemplate);
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
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateWorkflowTemplate, updateItemDTO)
      .subscribe((response: any) => {
        this.selectedItem = response;
        this.onSelectedItemChange.next(this.selectedItem);
      });
  }

  approveOrRejectWorkFlowTemplate(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.approveOrRejectWorkflowTemplate, data)
      .subscribe((response: any) => {
        this.selectedItem = response;
        this.onSelectedItemChange.next(this.selectedItem);
      })
  }

  getAllApprovedUPMGroups(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllApprovedUPMGroups)
        .subscribe((response: any) => {
          this.approvedUPMGroupList = response;
          this.onApprovedUPMGroupListChange.next(this.approvedUPMGroupList);
          resolve(response);
        }, reject);

    });
  }

}
