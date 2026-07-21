import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable({
  providedIn: 'root'
})
export class LeadNewService implements Resolve<any> {

  constructor(
     private dataService: DataService,
  ) { }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    throw new Error('Method not implemented.');
  }

  //  public saveLead(payload: any): Promise<any> {
  //       return new Promise<any>((resolve, reject) => {
  //         this.dataService.post(SETTINGS.ENDPOINTS.saveLead, payload).subscribe({
  //           next: (response: any) => {
  //             resolve(response);
  //           },
  //           error: (err: any) => {
  //             let error: Error = new Error(err);
  //             // this.toastService.showError(
  //             //   error.message || Constants.responseMsg.M1
  //             // );
  //           },
  //         });
  //       });
  //     }
      
      test =()=> console.log("sdsds");


      createLead = (lead)=>{
         return this.dataService.post(SETTINGS.ENDPOINTS.updateLead, lead);
      }

}
