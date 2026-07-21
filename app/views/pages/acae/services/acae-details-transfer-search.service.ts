import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import { Observable} from "rxjs";
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable()
export class ACAEDetailsTransferSearchService implements Resolve<any> {
  constructor(
    private dataService: DataService
  ) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([
      ]).then(() => {
        resolve()
      }, reject)
    })
  }


  getACAERecordsForTransferService(acaeRecordsForTransferRQ){
    return this.dataService.post(SETTINGS.ENDPOINTS.getACAERecordsForTransfer, acaeRecordsForTransferRQ); 
  }

  transferOptionService(transferRQ){
    return this.dataService.post(SETTINGS.ENDPOINTS.forwardTransferOption, transferRQ); 
  }
}