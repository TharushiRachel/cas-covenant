import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {ApplicationService} from "../application/application.service";
import {DataService} from "../data/data.service";
import {SETTINGS} from "../../setting/commons.settings";

@Injectable()
export class MyFacilityPaperCountService {

  inboxCountInterval;
  myFacilityPaperCount: any = "00";
  onMyFacilityPaperCountChange: BehaviorSubject<any> = new BehaviorSubject({})

  constructor(
    private applicationService: ApplicationService,
    private dataService: DataService
  ) {
    // this.setCountIntervals()
  }

  setCountIntervals() {
    this.clearCountInterval();
    // this.inboxCountInterval = setInterval(() => {
    //   // this.getLoggedUserFacilityPaperCount();
    // }, 60000);
  }

  getLoggedUserFacilityPaperCount() {
    this.dataService.post(SETTINGS.ENDPOINTS.getAssignedFacilityPaperCount, {
      currentAssignUser: this.applicationService.getLoggedInUserUserName()
    })
      .subscribe((response: any) => {
        this.myFacilityPaperCount = response;
        this.onMyFacilityPaperCountChange.next(this.getAssignedUserFacilityPaperCountString(this.myFacilityPaperCount));
      })
  }

  clearCountInterval() {
    clearInterval(this.inboxCountInterval);
  }

  private getAssignedUserFacilityPaperCountString(count: any) {
    let countHtml = '';
    if (count < 10) {
      countHtml = '0' + count;
    } else {
      countHtml = count;
    }
    return countHtml;
  }
}
