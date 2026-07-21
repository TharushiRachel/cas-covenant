import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable({
  providedIn: 'root'
})
export class DaService {
  onDADataChange = new BehaviorSubject({});
  DAData: any = {};
  DAMasterData: any = {};
  onDADesignationChange= new BehaviorSubject({});
  DADesignation: any = {};

  constructor(private dataService: DataService) { }

  saveDAUser(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveDAUser, data)
        .subscribe((response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        });
    });
  }


  updateDAUser(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.updateDAUser, data)
        .subscribe((response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        });
    });
  }
  addDataDATable1(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.addDataDaTable, data)
      .subscribe((response: any) => {
        if (response) {
          console.log(response)
        }
      })
  }
  addDataDATable(data): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.addDataDaTable,data).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getApprovedDesignations(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getApprovedDesignations).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }
  getApprovedDAData(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getApprovedDAData).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }
  
  getPendingDAData(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getPendingDAData).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  approveRejectRiskValues(data): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.approveRejectRiskValues,data).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  saveDADesignation(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveDAUser, data)
        .subscribe((response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        });
    });
  }

  getAllIndividualDesignationCode(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllIndividualDesignationCode).subscribe((response: any) => { 
        this.DADesignation = response;
        this.onDADesignationChange.next(this.DAData);
        resolve(response); }, (error: any) => { reject(error); });
   
    });
    
  }

  getAllCommitteeDesignationsWithCode(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllCommitteeDesignationsWithCode).subscribe((response: any) => { 
        this.DADesignation = response;
        this.onDADesignationChange.next(this.DAData);
        resolve(response); }, (error: any) => { reject(error); });
   
    });
    
  }

  addNewDesignation(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.addNewDesignation, data)
        .subscribe((response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        });
    });
  }

  getApprovedAndTempData(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getApprovedPendingDAData).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  getAllDAUsers(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllDAUsers).subscribe((response: any) => { resolve(response); }, (error: any) => { reject(error); });
    });
  }

  changeDAOrder(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.changeDAOrder, data)
        .subscribe((response: any) => {
          resolve(response);
        },
        (error: any) => {
          reject(error);
        });
    });
  }

}

