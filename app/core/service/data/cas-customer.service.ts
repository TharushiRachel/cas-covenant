import {Injectable} from '@angular/core';
import {DataService} from "./data.service";
import {SETTINGS} from "../../setting/commons.settings";

@Injectable()
export class CasCustomerService {

  constructor(private dataService: DataService) {
  }

  getCustomerAccountStatistic(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getCustomerAccountStatisticResponse, data);
  }

  getCasStat(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getCasStatResponse, data);
  }

  getCustomerDepositDetails(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getCustomerDepositDetails, data);
  }

  getCustomerFacilityDetailsByAccountNumber(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getCustomerFacilityDetailsByAccountNumber, data);
  }

  getTranDetForCashFlow(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.getTranDetForCashFlow, data);
  }

 

  getTTTunroverAccounts(account: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getTTTurnoverAccounts
      );
      data.url =
        data.url +
        "/" +account
      this.dataService.get(data).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }


 


  // getAllTTTunrover(data: any): Promise<any> {
  //   return new Promise((resolve, reject) => {
  //     this.dataService.post(SETTINGS.ENDPOINTS.getAllTTTurnover,data).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
  //   });
  // }
  // Kalypto service
  
  saveKalyptoService(data) {
    return this.dataService.post(SETTINGS.ENDPOINTS.saveKalyptoService, data);
  }

  getKalyptoFromIntegrationService(data){
    return this.dataService.post(SETTINGS.ENDPOINTS.getKalyptoFromIntegrationService, data);
  }

  getKalyptoValueService(data){
    return this.dataService.post(SETTINGS.ENDPOINTS.getKalyptoValueService, data);
  }

  saveNewKalyptoService(data){
    return this.dataService.post(SETTINGS.ENDPOINTS.saveNewKalyptoService, data);
  }

  refreshKalyptoValueService(data){
    return this.dataService.post(SETTINGS.ENDPOINTS.refreshKalyptoValues, data);
  }

  saveEditedKalyptoService(data){
    return this.dataService.post(SETTINGS.ENDPOINTS.saveEditedKalypto, data);
  }

  isAddedNewKalyptoService(data){
    return this.dataService.post(SETTINGS.ENDPOINTS.isAddedNewKalypto, data);
  }
}
