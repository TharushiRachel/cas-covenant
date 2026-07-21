import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import * as _ from 'lodash';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { CurrencyPipe } from '@angular/common';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { LocalStorage } from 'ngx-webstorage';
import * as moment from 'moment';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { AppUtils } from 'src/app/shared/app.utils';

@Component({
  selector: 'app-ca-details-about',
  templateUrl: './ca-details-about.component.html',
  styleUrls: ['./ca-details-about.component.scss']
})
export class CaDetailsAboutComponent implements OnInit {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;
  toDate = moment().subtract(1, "months").endOf("month").format("DD-MM-YYYY");
  fromDate = moment()
    .subtract(12, "months")
    .startOf("month")
    .format("DD-MM-YYYY");
  customerDetails: any = {};
  details: any;
  traninquiry: any;
  tranDate: string;
  relatedDetails: any;
  addressDataCom: any;
  componentForm: FormGroup;
  branchName: any;
  transactionDetails: any;
  commentDetails: any;
  basicInfoDetails: any;
  covAppStatusHistory: any;
  commonDetails: any;
  @Input() coveringApproval: any;
  @Input() upmDetails: any;
  allBranches = [];
  results: Subject<any>;
  onCOVFormChangeSub = new Subscription();
  onCOVFormChangeSubs = new Subscription();
  onCoveringApprovalChangeSub = new Subscription();
  onCoveringApprovalChangesSub = new Subscription();
  coveringApprovalForm: any = {};
  accessLevelOfCurrentAssignUser: any;


  constructor(
    private fb: FormBuilder,
    private coveringApprovalSharedService: CoveringApprovalSharedService,
    private coveringApprovalService: CoveringApprovalService,
    private currencyPipe: CurrencyPipe,
    private applicationService: ApplicationService,
    private cacheService: CacheService
  ) {
    this.componentForm = this.fb.group({
      assignBMRMAM: new FormControl('')
    });
  }

  ngOnInit() {
    this.allBranches = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    this.results = new BehaviorSubject(this.allBranches);

    this.onCoveringApprovalChangeSub =
      this.coveringApprovalService.onCoveringApprovalChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.coveringApproval = data;
            if (
              this.coveringApproval &&
              this.coveringApproval.covAppBasicInfoDTOList &&
              this.coveringApproval.covAppBasicInfoDTOList.length > 0
            ) {
              this.basicInfoDetails =
                this.coveringApproval.covAppBasicInfoDTOList[0];

              this.customerDetails.cumm =
                this.basicInfoDetails.customerFinancialID;
              this.customerDetails.accno = this.basicInfoDetails.accountNumber;
              this.customerDetails.userId =
                this.applicationService.getLoggedInUserUserID();
              this.customerDetails.aduser =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.refId =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.fromdate = this.fromDate;
              this.customerDetails.todate = this.toDate;
              this.customerDetails = AppUtils.trim(this.customerDetails);
            } else {
              console.warn("covAppBasicInfoDTOList is empty or undefined");
            }
          } else {
            this.coveringApproval =
              this.coveringApprovalSharedService.getCoveringApprovalForm();
            if (
              this.coveringApproval && 
              this.coveringApproval.covAppBasicInfoDTOList &&
              this.coveringApproval.covAppBasicInfoDTOList.length > 0
            ) {
              this.basicInfoDetails =
                this.coveringApproval.covAppBasicInfoDTOList[0];
              this.customerDetails.cumm =
                this.basicInfoDetails.customerFinancialID;
              this.customerDetails.accno = this.basicInfoDetails.accountNumber;
              this.customerDetails.userId =
                this.applicationService.getLoggedInUserUserID();
              this.customerDetails.aduser =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.refId =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.fromdate = this.fromDate;
              this.customerDetails.todate = this.toDate;
              this.customerDetails = AppUtils.trim(this.customerDetails);
            } else {
              console.warn("covAppBasicInfoDTOList is empty or undefined");
            }
          }
        }
      );

    if (this.isEqualLoginAndAssignUser()) {
      this.onCoveringApprovalChangesSub =
        this.coveringApprovalService.onCoveringApprovalPendingChange.subscribe(
          (data: any) => {
            if (!_.isEmpty(data)) {
              this.coveringApprovalForm = data;
            }
          }
        );
    }


    let testAccountNumber = Object.assign({
      accountNumber: this.basicInfoDetails.accountNumber,
    });
    this.basicInfoDetails = this.coveringApproval && this.coveringApproval.covAppBasicInfoDTOList
    ? this.coveringApproval.covAppBasicInfoDTOList[0]
    : null;

  this.commentDetails = this.coveringApproval && this.coveringApproval.covAppCommentDTOList
    ? this.coveringApproval.covAppCommentDTOList[0]
    : null;

  
  }
  isEqualLoginAndAssignUser() {
    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= 50) {
      return true;
    } else {
      return false;
    }
  }
  getFormattedValue(amount: any): string | null {
    if (amount != null) {
      // If the amount is a string and contains commas, remove the commas and parse it to a float
      const numericAmount = typeof amount === 'string' ? parseFloat(amount.replace(/,/g, '')) : amount;
  
      // Ensure that the resulting value is a valid number
      if (!isNaN(numericAmount)) {
        // Use the CurrencyPipe to format the number
        return this.currencyPipe.transform(numericAmount, '', ''); // Customize as needed for currency type
      }
    }
    return null; // Return null if the amount is not valid or empty
  }
  
  

}
