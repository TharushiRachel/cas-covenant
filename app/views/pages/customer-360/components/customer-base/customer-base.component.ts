import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../core/setting/constants";
import {CustomerBaseService} from "../../services/customer-base.service";
import {AlertService} from "../../../../../core/service/common/alert.service";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {Subscription} from "rxjs";
import * as _ from 'lodash';
import {AppUtils} from "../../../../../shared/app.utils";
import {NewNonFinacleCustomerAddEditComponent} from "../../../../../shared/components/new-non-finacle-customer-add-edit/new-non-finacle-customer-add-edit.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {NewFacilityPaperDialogComponent} from "./components/support/new-facility-paper-dialog/new-facility-paper-dialog.component";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {LocalStorage} from "ngx-webstorage";

@Component({
  selector: 'app-customer-base',
  templateUrl: './customer-base.component.html',
  styleUrls: ['./customer-base.component.scss']
})
export class CustomerBaseComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;
  searchForm: FormGroup;

  optionsSelect = Constants.customerIdentificationTypeOptionsSelect;
  customerIdentificationTypeConst = Constants.customerIdentificationTypeConst;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  newCustomerModalRef: MDBModalRef;
  newPaperModalRef: MDBModalRef;

  customer360Details;
  isSearched = false;

  onCustomer360DetailsChangeSubs = new Subscription();
  onNewFacilityPaperDraftSubs = new Subscription();

  constructor(private formBuilder: FormBuilder,
              private customerBaseService: CustomerBaseService,
              private alertService: AlertService,
              private mdbModalService: MDBModalService,
              private cacheService: CacheService,
              private urlEncodeService: UrlEncodeService,
              private router: Router,
              private applicationService: ApplicationService,) {
  }

  ngOnInit() {

    this.onNewFacilityPaperDraftSubs = this.customerBaseService.onNewFacilityPaperDraft
      .subscribe((data: any) => {
        this.selectedFacilityPaperID = this.urlEncodeService.encode(data.facilityPaperID);
        this.router.navigate(['/facility-paper/edit']);
      });

    this.searchForm = this.formBuilder.group({
      customerFinancialID: [],
      identificationNumber: [],
      bankAccountNumber: [],
      identificationType: [],
    });

    this.onCustomer360DetailsChangeSubs = this.customerBaseService.onCustomer360DetailsChange
      .subscribe((data: any) => {
        this.customer360Details = data;
        if (_.isEmpty(this.searchForm.controls['bankAccountNumber'].value)) {
          if (!_.isEmpty(this.customer360Details.customerBankDetailsDTOList)) {

            this.searchForm.controls['bankAccountNumber'].setValue(this.customer360Details.customerBankDetailsDTOList[0].bankAccountNumber);
          }
        }
        if (_.isEmpty(this.searchForm.controls['customerFinancialID'].value)) {

          this.searchForm.controls['customerFinancialID'].setValue(this.customer360Details.customerFinancialID);
        }
        this.customerBaseService.getFacilityDetailResponses(this.searchForm.getRawValue());
        this.customerBaseService.getCustomerPagedLeadDTO({customerID: this.customer360Details.customerID});
      });
  }

  ngOnDestroy(): void {
    this.customerBaseService.onCustomer360DetailsChange.next({});
    this.onCustomer360DetailsChangeSubs.unsubscribe();
    this.onNewFacilityPaperDraftSubs.unsubscribe();
  }

  clearSearch() {
    this.searchForm.reset({
      customerFinancialID: null,
      identificationNumber: null,
      bankAccountNumber: null,
      identificationType: null,
    }, {onlySelf: false, emitEvent: true});

    this.customer360Details = null;
    this.isSearched = false;
  }

  validateRequest() {
    let searchData = this.searchForm.getRawValue();
    let {customerFinancialID, identificationNumber, bankAccountNumber, identificationType} = searchData;
    if (!customerFinancialID
      && !identificationNumber
      && !bankAccountNumber
      && !identificationType) {

      this.alertService.showToaster('Identification number or CIF ID is required', SETTINGS.TOASTER_MESSAGES.warning);
      return false;
    }

    if (identificationType) {
      if (!identificationNumber) {
        this.alertService.showToaster('Please fill identification number', SETTINGS.TOASTER_MESSAGES.warning);
        return false;
      }
    }

    if (identificationNumber) {
      if (!identificationType) {
        this.alertService.showToaster('Please fill identification number type', SETTINGS.TOASTER_MESSAGES.warning);
        return false;
      }
    }

    return true;
  }

  doSearch() {
    if (this.validateRequest()) {
      this.customerBaseService.searchCustomer360(this.searchForm.getRawValue());
      setTimeout(() => this.isSearched = true, 1000);
    }
  }

  refreshCustomerDetailFromBank() {
    if (this.validateRequest()) {
      let customerRefresh = {
        bankAccountNumber: null,
        customerFinancialID: null,
        identificationNumber: this.searchForm.getRawValue().identificationNumber,
        identificationType: this.searchForm.getRawValue().identificationType,
      };
      if (customerRefresh.identificationNumber && customerRefresh.identificationType) {
        this.customerBaseService.refreshCustomerDetailFromBank(AppUtils.trim(customerRefresh));
      }
    }
  }

  noData() {
    return _.isEmpty(this.customer360Details) && this.isSearched;
  }

  hasData() {
    return !_.isEmpty(this.customer360Details);
  }

  createFPWithoutFinacleCustomer($event) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }

    this.newCustomerModalRef = this.mdbModalService.show(NewNonFinacleCustomerAddEditComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable',
      containerClass: '',
      animated: false,
      data: {
        heading: 'Customer Details',
        actionMessage: 'Proceed',
        identificationNumber: this.searchForm.getRawValue().identificationNumber,
        identificationType: this.searchForm.getRawValue().identificationType,
      }
    });

    this.newCustomerModalRef.content.action.subscribe((nonFinacleCustomerData: any) => {
      if (!_.isEmpty(nonFinacleCustomerData)) {

        this.newPaperModalRef = this.mdbModalService.show(NewFacilityPaperDialogComponent, {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: 'modal-width-70-p modal-dialog-scrollable',
          containerClass: '',
          animated: false,
          data: {
            customer360Details: nonFinacleCustomerData,
          }
        });

        this.newPaperModalRef.content.action.subscribe((data: any) => {
          if (!_.isEmpty(data)) {
            let casCustomerDTO = {};
            switch (nonFinacleCustomerData.type) {
              case this.basicInformationTypeConst.PERSONAL:
                casCustomerDTO = {...nonFinacleCustomerData.personalInformationForm};
                break;
              case this.basicInformationTypeConst.CORPORATE:
                casCustomerDTO = {...nonFinacleCustomerData.corporateInformationForm};
                break;
              case this.basicInformationTypeConst.BUSINESS:
                casCustomerDTO = {...nonFinacleCustomerData.businessInformationForm};
                break;
            }

            casCustomerDTO = Object.assign(casCustomerDTO, nonFinacleCustomerData);

            this.draftFacilityPaper(data, casCustomerDTO);
          }
        });

      }
    });
  }

  draftFacilityPaper(data, casCustomerDTO) {
    let submit: any = {};
    let {branchName, cooperate, workFlow} = data;
    let branch = AppUtils.getBranchFromBranchName(this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES), branchName);
    submit.branchCode = branch.branchCode;
    submit.createdUserBranchCode = this.applicationService.getLoggedInUserDivCode();
    submit.isCooperate = cooperate ? 'Y' : 'N';
    submit.workflowTemplateID = workFlow;
    submit.currentAssignUser = this.applicationService.getLoggedInUserUserName();
    submit.currentAssignUserID = this.applicationService.getLoggedInUserUserID();
    submit.currentAssignUserDivCode = this.applicationService.getLoggedInUserDivCode();
    submit.assignUserUpmGroupCode = this.applicationService.getLoggedInUserUPMGroupCode();
    submit.assignUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
    submit.assignUserUpmID = this.applicationService.getLoggedInUserUserID();

    submit = Object.assign(submit, {casCustomerDTO: casCustomerDTO});

    this.customerBaseService.draftFacilityPaperWithNonFinacleCustomer(AppUtils.trim(submit));
  }

}
