import {Injectable} from '@angular/core';
import {ApplicationService} from "../application/application.service";
import {DataService} from "../data/data.service";
import {SETTINGS} from "../../setting/commons.settings";
import {BehaviorSubject} from "rxjs";

@Injectable()
export class MyLeadCountService {

  inboxCountInterval;

  pendingLeadCount: any = '00';
  onPendingLeadCountChange = new BehaviorSubject(this.pendingLeadCount);

  constructor(private applicationService: ApplicationService,
              private dataService: DataService) {
    // this.setCountIntervals();
  }

  setCountIntervals() {
    this.clearCountInterval();
    // this.inboxCountInterval = setInterval(() => {
    //   this.getLoggedInUserPendingLeadCount();
    // }, 60000);
  }

  getLoggedInUserPendingLeadCount() {
    this.dataService.post(SETTINGS.ENDPOINTS.getBranchPendingLeadCount, {
      branchCode: this.applicationService.getLoggedInUserDivCode()
    })
      .subscribe((response: any) => {
        this.pendingLeadCount = response;
        this.onPendingLeadCountChange.next(this.getPendingLeadCountString(this.pendingLeadCount));
      });
  }

  clearCountInterval() {
    clearInterval(this.inboxCountInterval);
  }

  private getPendingLeadCountString(count: any) {
    let countHtml = '';
    if (count < 10) {
      countHtml = '0' + count;
    } else {
      countHtml = count;
    }
    return countHtml;
  }
}
