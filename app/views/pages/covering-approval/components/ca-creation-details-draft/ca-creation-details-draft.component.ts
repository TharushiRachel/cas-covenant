import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { Subject, Subscription } from 'rxjs';
import { IMyOptions, MDBModalRef } from 'ng-uikit-pro-standard';
import { Router } from '@angular/router';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import { Constants } from 'src/app/core/setting/constants';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { AppUtils } from 'src/app/shared/app.utils';
import { LocalStorage } from 'ngx-webstorage';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { CurrencyPipe, DatePipe, formatDate } from '@angular/common';
import * as moment from 'moment';
import { CacheService } from 'src/app/core/service/data/cache.service';
import * as _ from 'lodash';
import { isEmpty } from 'lodash';
import { AlertService } from 'src/app/core/service/common/alert.service';

@Component({
  selector: 'app-ca-creation-details-draft',
  templateUrl: './ca-creation-details-draft.component.html',
  styleUrls: ['./ca-creation-details-draft.component.scss']
})
export class CaCreationDetailsDraftComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;
  onFormValueChangeSub = new Subscription();
  onComponentFormChangeSub = new Subscription();
  action: Subject<any>;
  results: Subject<any>;

  detailsForm: FormGroup;
  componentFormCurrentStatusConst = Constants.componentFormCurrentStatusConst;

  myDatePickerOptions: IMyOptions = {
    dateFormat: "dd/MM/yyyy",
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
  };
  formErrors: any = {};
  isDisabled: boolean = false;
  templateOption: any[] = [];
  allWorkFlowTemplates = [];
  allOptions = [];
  heading: string;
  message: string;
  htmlContent: any;
  applicationForm: any = {};
  details: any;
  traninquiry: any;
  tranDate: string;
  tranId: string;
  addressDataCom: any;
  commonDetails: any;
  customerDetails: any;
  relatedDetails: any;
  allBankOptions: any = {};
  branchName: any;
  transactionDetails: any;
  transformedDetails: any[] = []

  constructor(
    private fb: FormBuilder,
    public mdbModalRef: MDBModalRef,
    private router: Router,
    private coveringApprovalSharedService: CoveringApprovalSharedService,
    private coveringApprovalService: CoveringApprovalService,
    public applicationService: ApplicationService,
    private cacheService: CacheService,
    private currencyPipe: CurrencyPipe,
    private datePipe: DatePipe,
    private alertService: AlertService,
    private urlEncodeService: UrlEncodeService
  ) {
    this.action = new Subject<any>();

    this.detailsForm = this.fb.group({
      customerName: [{ value: '', disabled: true }],
      tranDate: [{ value: '', disabled: true }],
      //idNumber: [{ value: '', disabled: true }],
      tranAmount: [{ value: '', disabled: true }],
      accNumber: [{ value: '', disabled: true }],
      //address:[{value: '', disabled: true}],
      //branch: [{ value: '', disabled: true }],
      //accountManager: [{ value: '', disabled: true }],
      chequeNumber: [{ value: '', disabled: true }],
      comment: ['', Validators.required]
    });
  }


  ngOnInit() {
    if (this.transactionDetails && this.transactionDetails.data) {    
      this.transformedDetails = this.transactionDetails.data.map(record => {
        return {
          ...record,
          showDraftButton: (record.isExists !== "already exists" && record.currentStatus !== "DRAFT") || (record.isExists === "already exists" && record.currentStatus === "DELETE"),
          createdDate: moment(record.createdDate).format('YYYY-MM-DD h:mm a'),
          createdBy: record.createdBy,
          tranStatus: record.trnStatus
          
        };
        
      }).filter(record => record !== null);
      if (this.transactionDetails.tranDate) {
        this.transactionDetails.tranDate = moment(this.transactionDetails.tranDate, 'DD-MM-YYYY').toDate(); // Convert to a Date object
      }
      // Filter out any null records
    } else {
      this.alertService.showToaster("No transaction details found",SETTINGS.TOASTER_MESSAGES.error);
    }
  }


  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  async createApplicationForm(currentApplicationFormStatus: any, detail: any): Promise<void> {
    try {
      const response = await this.coveringApprovalService.getCustomerBankDetails(detail.accNumber);

      this.customerDetails = response;
      this.relatedDetails = this.customerDetails && this.customerDetails.relatedPartyDetails && this.customerDetails.relatedPartyDetails[0];

      const addressLine1 = typeof this.relatedDetails.addressLine1 === 'string' ? this.relatedDetails.addressLine1 : '';
      const addressLine2 = typeof this.relatedDetails.addressLine2 === 'string' ? this.relatedDetails.addressLine2 : '';
      const addressLine3 = typeof this.relatedDetails.addressLine3 === 'string' ? this.relatedDetails.addressLine3 : '';
      const addressCity = typeof this.relatedDetails.addressCity === 'string' ? this.relatedDetails.addressCity : '';
      const addressState = typeof this.relatedDetails.addressState === 'string' ? this.relatedDetails.addressState : '';
      const addressCountry = typeof this.relatedDetails.addressCountry === 'string' ? this.relatedDetails.addressCountry : '';
  
      const addressData = [
        addressLine1,
        addressLine2,
        addressLine3,
        addressCity,
        addressState,
        addressCountry
      ].filter(line => line).join(', ');
  
      this.addressDataCom = addressData;
      this.branchName = this.coveringApprovalSharedService.getBranchName(this.customerDetails.acctSolId);

      // let covAppCommentDTOList = [{
      //   userComment: detail.comment,
      //   currentStatus: currentApplicationFormStatus,
      //   createdUserID: this.applicationService.getLoggedInUserUserID(),
      //   createdUser: this.applicationService.getLoggedInUserUserName(),
      //   createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      //   createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
      //   createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      //   isUsersOnly: "N",
      //   isDivisionOnly: "N",
      //   isPublic: "Y",
      // }];

      let covAppBasicInfoDTOList = [{
        accountNumber: detail.accNumber,
        chequeNumber: detail.instNum,
        tranAmount: detail.tran_amt,
        tranCurrency: detail.trnCrncy,
        accounManager: this.customerDetails.accountManagerUserId,
        customerFinancialID: this.relatedDetails.cifId,
        customerName: detail.accName,
        address: this.addressDataCom,
        identificationType: "NIC / BR",
        identificationNumber: this.relatedDetails.idNumber,
        branchCode: this.customerDetails.acctSolId,
        branchName: this.branchName,
        tranId: this.transactionDetails.tranId,
        tranDate: moment(this.transactionDetails.tranDate, 'DD/MM/YYYY').format('YYYY-MM-DD'),
        clearBalance: this.customerDetails.clearBalance,
        partTranSRL: detail.partTrnSRL,
        floatBalance: this.customerDetails.floatBalance,
        fundsinClearing: this.customerDetails.fundsinClearing,
        tranStatus: detail.trnStatus,
        currentStatus: this.componentFormCurrentStatusConst.DRAFT
      }];

      let covFormInitData = Object.assign(
        {},
        {
          currentStatus: this.componentFormCurrentStatusConst.DRAFT,
          currentAssignUserId: this.applicationService.getLoggedInUserUserID(),
          createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          createdUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
          currentAssignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          assignUserUpmId: this.applicationService.getLoggedInUserUserID(),
          assignUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          currentAssignUser: this.applicationService.getLoggedInUserUserName(),
          branchCode: this.applicationService.getLoggedInUserDivCode(),
          accountNumber: detail.accNumber,
          isAuthorized: false,
          //covAppCommentDTOList,
          covAppBasicInfoDTOList
        }
      );

      // Reinitialize action if it is closed
      if (this.action.closed) {
        this.action = new Subject<any>();
      }

      this.action.next(AppUtils.trim(covFormInitData));
      this.mdbModalRef.hide();
      // if (covFormInitData) {
      //   this.coveringApprovalSharedService.setCovFormInitData(covFormInitData);
      //   const draftResponse = await this.coveringApprovalService.draftApplicationForm(covFormInitData);
      //   this.coveringApprovalSharedService.setCoveringApprovalForm(draftResponse);
      //   this.router.navigate(['/covering-approval/request-approval']);
      // }
      //console.log("covFormInitData",covFormInitData)
      this.coveringApprovalService.draftApplicationForm(covFormInitData).then((res: any) => {
        if(res.covAppId){
          this.selectedCovID = this.urlEncodeService.encode(res.covAppId);
          this.router.navigate(['/covering-approval/request-approval']);
        }
      })
    } catch (error) {
      console.error("Error in createApplicationForm:", error);
    }
  }

  navigateToCaCreation(detail: any) {
    // const comment = this.detailsForm.get('comment').value;
    // detail.comment = comment;
    //this.coveringApprovalSharedService.setTransactionDetails(this.details);
    this.createApplicationForm(this.componentFormCurrentStatusConst.DRAFT, detail);
    this.mdbModalRef.hide();
  }
  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      const branchName = branch.branchName + ' - ' + branch.branchCode;
      localStorage.setItem(branchCode, branchName); // Store branch name in local storage
      return branchName;
    }
    // Retrieve from local storage if branch is not found in the cache
    return localStorage.getItem(branchCode) || branchCode;
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    if (this.onFormValueChangeSub) {
      this.onFormValueChangeSub.unsubscribe();
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
