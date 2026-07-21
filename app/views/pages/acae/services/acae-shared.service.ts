// shared.service.ts
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { PageSize } from 'src/app/core/dto/page.size';
import { Pagination } from 'src/app/core/dto/pagination';
import { Constants } from 'src/app/core/setting/constants';

@Injectable({
  providedIn: 'root',
})
export class ACAESharedService {

  pageSize: PageSize = new PageSize();
  // Observable to notify subscribers when to trigger ngOnInit
  private triggerNgOnInitSource = new Subject<void>();
  private triggerRefreshACAEGridSource = new Subject<void>();
  private triggerRefreshACAECountSource = new Subject<void>();
  private RefreshSpecificACAEPaperSource = new Subject<void>();
  private triggerACAECommentSource = new Subject<void>();

  private triggerRefreshSpecificACAEPaperSource = new Subject<void>();

  private triggerRefreshACAETransferGridSource = new Subject<void>();
  private triggerAddACAECommentSource= new Subject<void>()
  

  triggerNgOnInit$ = this.triggerNgOnInitSource.asObservable();
  triggerRefreshACAEGrid$ = this.triggerRefreshACAEGridSource.asObservable();
  triggerRefreshACAECount$ = this.triggerRefreshACAECountSource.asObservable();
  triggerRefreshACAETransferGrid$ = this.triggerRefreshACAETransferGridSource.asObservable();
  triggerRefreshSpecificACAEPaperSource$ = this.triggerRefreshSpecificACAEPaperSource.asObservable();
  triggerAddACAECommentSource$ = this.triggerAddACAECommentSource.asObservable();

  paginationData = new Pagination({
    pageSize: 10,
    pageIndex: 0
  })

  acaeAllData: any = []
  paperIndex: number = 0;
  allPaperSize: number = 0;

  acaeDashboardStatus: string;

  triggerNgOnInit() {
    this.triggerNgOnInitSource.next();
  }

  triggerRefreshACAEGrid() {

    this.triggerRefreshACAEGridSource.next();
  }

  triggerRefreshACAECount() {
    this.triggerRefreshACAECountSource.next();
  }

  triggerRefreshACAETransferGrid() {
    this.triggerRefreshACAETransferGridSource.next();
  }

  setACAEDashboardStatus(status: string) {
    this.acaeDashboardStatus = status;
  }

  getACAEDashboardStatus() {
    return this.acaeDashboardStatus;
  }

  triggerSpecificACAEPaper() {
    this.triggerRefreshSpecificACAEPaperSource.next();
  }

  triggerACAEAddComment() {
    this.triggerAddACAECommentSource.next();
  }

  setACAEAllData(data: any) {
    this.acaeAllData = data;
  }

  getACAEAllData() {
    return this.acaeAllData;
  }

  setPaperIndex(index: number) {
    this.paperIndex = index;
  }

  getPaperIndex() {
    return this.paperIndex;
  }

  setAllPaperSize(size: number) {
    this.allPaperSize = size
  }

  getAllPaperSize() {
    return this.allPaperSize;
  }

  setPageData(event: Pagination) {
    this.paginationData = event;
  }

  getPageData(): Pagination {
    return new Pagination(this.paginationData);
  }

  getACAEStatusNo(status: String): Number {
    let acaeNo = 0
    switch (status) {
      case Constants.acaeStatusConst.DRAFT: {
        acaeNo = 14;
        break;
      }
      case Constants.acaeStatusConst.FORWARDED_TO_ME: {
        acaeNo = 2;
        break;
      }
      case Constants.acaeStatusConst.RETURNED_TO_ME: {
        acaeNo = 9;
        break;
      }
      case Constants.acaeStatusConst.RESUBMITED_TO_ME: {
        acaeNo = 5;
        break;
      }
      case Constants.acaeStatusConst.FORWARDED: {
        acaeNo = 1;
        break;
      }
      case Constants.acaeStatusConst.RETURNED: {
        acaeNo = 3;
        break;
      }
      case Constants.acaeStatusConst.APPROVED: {
        acaeNo = 4;
        break;
      }
      case Constants.acaeStatusConst.TO_BE_RESUBMIT: {
        acaeNo = 7;
        break;
      }
      case Constants.acaeStatusConst.TRANSFERED_TO_ME: {
        acaeNo = 6;
        break;
      }
    }
    return acaeNo;
  }
}