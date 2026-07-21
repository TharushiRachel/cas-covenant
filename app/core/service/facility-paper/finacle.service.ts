import { Injectable } from '@angular/core';
import { DataService } from '../data/data.service';
import { SETTINGS } from '../../setting/commons.settings';

@Injectable({
  providedIn: 'root'
})
export class FinacleService {

  constructor(private dataService: DataService) { }

  getInsuranceDetails(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getInsuranceDetails,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getInsuranceDetailsDB(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getInsuranceDetailsDB,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }



  refreshInsuranceDetails(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.refreshInsuranceDetails,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }
  
  getExportDataFinancial(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getExportTOFinancial,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getExportDataCalender(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getExportTOCalender,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }
  saveExportData(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.saveExportTO,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }
  
  getExportDataDB(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getExportTODB,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getImportDataDB(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getImportTODB,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getImportDataFinancial(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getImportTOFinancial,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getImportDataCalender(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getImportTOCalender,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  saveImportData(cusID: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.saveImportTO,cusID).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }
}
