import { Injectable } from '@angular/core';
import { LocalStorage } from 'ngx-webstorage';
import { SETTINGS } from '../../setting/commons.settings';
import { DataService } from '../data/data.service';

@Injectable({
  providedIn: 'root'
})
export class GuaranteeVolumeService {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FINACLE_ID)
  selectedFinacleID;
  
  constructor(private dataService: DataService) { }

  getFinacaleGuaranteeVolumes(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getFinacleGuaranteeVolumes,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getFinacaleGuaranteeVolumesFinancial(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getFinacleGuaranteeVolumesFinancial,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getFinacaleGuaranteeVolumesDB(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getFinacleGuaranteeVolumesDB,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }


  saveGuaranteeVolumes(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.saveGuaranteeVolumes,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  

}
