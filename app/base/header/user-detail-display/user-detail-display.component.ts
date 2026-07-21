import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../core/service/application/application.service";
import {Subscription} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import {SETTINGS} from "../../../core/setting/commons.settings";
import {DataService} from "../../../core/service/data/data.service";
import {Constants} from "../../../core/setting/constants";
import {AppUtils} from "../../../shared/app.utils";
import {CacheService} from "../../../core/service/data/cache.service";
import {isEmpty} from "lodash";
import {AlertService} from "../../../core/service/common/alert.service";

@Component({
  selector: 'app-user-detail-display',
  templateUrl: './user-detail-display.component.html',
  styleUrls: ['./user-detail-display.component.scss']
})
export class UserDetailDisplayComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  onUserDaChangeSubs = new Subscription();
  allBankOptions: any = {};

  daUserData: any = {};
  upmDetailOfADUser: any = {};
  designationName =" - "

  constructor(public  mdbModalRef: MDBModalRef,
              private applicationService: ApplicationService,
              private currencyPipe: CurrencyPipe,
              private alertService: AlertService,
              private cacheService: CacheService,
              private dataService: DataService) {
  }

  ngOnInit() {
    this.onUserDaChangeSubs = this.applicationService.onUserDaChange
      .subscribe((daUserData: any) => {
        //console.log("daUserData", daUserData)
        this.daUserData = daUserData;
      });

      let userId = this.applicationService.getLoggedInUserUserID();
      this.getUserUPMData(userId);
      this.getUserUPMDetailsList()
      
    if (this.applicationService.isAgent()) {
      this.getUpmDetailsByAdUserIdAndAppCode(this.applicationService.getAgentSupervisorADUserID());
    }
  }

  ngOnDestroy(): void {
    this.onUserDaChangeSubs.unsubscribe();
  }

  getUserDisplayName() {
    return this.applicationService.getLoggedInUserCombinedName() ? this.applicationService.getLoggedInUserCombinedName() : this.applicationService.getUserDisplayName();
  }

  getLoggedInUserDivCode() {
    return this.applicationService.getLoggedInUserDivCode();
  }

  getUserSolID() {
    return this.applicationService.getLoggedInUserSolID();
  }

  getUserName() {
    return this.applicationService.getLoggedInUserUserName();
  }

  getUPMAccessLevel() {
    return this.applicationService.getLoggedInUserUPMGroupCode();
  }

  getUPMID() {
    return this.applicationService.getLoggedInUserUserID();
  }

  getUserUPMDetailsList() {
    this.applicationService.getUserUPMDetailsList({
      "userID" : this.applicationService.getLoggedInUserUserID(),
      "appCode":  'CAS'
   }).subscribe((response: any) => {
      if (response) {
        this.designationName = response.designationDescription;
      }
    }, (error) => {
      console.error(error);
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  getDaMaxAmount() {
    return this.daUserData.maxAmount ? this.currencyPipe.transform(this.daUserData.maxAmount, '', '') : 'No DA available for the user ' + this.applicationService.getLoggedInUserUserName();
  }

  isAgent() {
    return this.applicationService.isAgent();
  }

  getUpmDetailsByAdUserIdAndAppCode(userID) {
    console.log("this.upmDetailOfADUser")
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPMDetails);
    data.url = data.url + '/' + userID;
    this.dataService.get(data)
      .subscribe((response: any) => {
        //console.log("response", response)
        this.upmDetailOfADUser = response;
        console.log("this.upmDetailOfADUser", response)
      });
  }

  getUserUPMData(userADID): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPMDetails);
      data.url = data.url + '/' + userADID;
      this.dataService.get(data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  getBranchName(branchCode) {
    //console.log("div", branchCode)
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);
    //console.log("branch", branch)
    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

}
