import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {DataService} from "../../../../core/service/data/data.service";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";

@Injectable()
export class AgentAddEditService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_AGENT_ID)
  selectedAgentID;

  selectedAgent: any = {};
  onSelectedAgentChange: Subject<any> = new BehaviorSubject({});

  constructor(private dataService: DataService,
              private urlEncodeService: UrlEncodeService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return new Promise((resolve, reject) => {

      Promise.all([
        this.getRoleUpdateDTO()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getRoleUpdateDTO(): Promise<any> {
    return new Promise((resolve, reject) => {

      if (this.selectedAgentID == null) {
        this.selectedAgent = {};
        this.onSelectedAgentChange.next(this.selectedAgent);
        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getAgentUpdateDTO);
        data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedAgentID);
        this.dataService.get(data)
          .subscribe((response: any) => {
            this.selectedAgent = response;
            this.onSelectedAgentChange.next(this.selectedAgent);
            resolve(response);
          }, reject);
      }

    });
  }

  addAgent(agent) {
    this.dataService.post(SETTINGS.ENDPOINTS.addAgent, agent)
      .subscribe((response: any) => {
        this.selectedAgent = response;
        this.onSelectedAgentChange.next(this.selectedAgent);
      });
  }

  updateAgent(agent) {
    this.dataService.post(SETTINGS.ENDPOINTS.updateAgent, agent)
      .subscribe((response: any) => {
        this.selectedAgent = response;
        this.onSelectedAgentChange.next(this.selectedAgent);
      });
  }

}
