import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import * as _ from 'lodash';
import {SearchDataCacheService} from "../../../../../../../../../core/service/common/search-data-cache.service";
import {FacilityPaperAddEditService} from "../../../../../../services/facility-paper-add-edit.service";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {Pagination} from "../../../../../../../../../core/dto/pagination";
import {PageSize} from "../../../../../../../../../core/dto/page.size";
import {AppUtils} from "../../../../../../../../../shared/app.utils";
import {SETTINGS} from "../../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../../core/service/common/alert.service";


@Component({
  selector: 'app-fp-customer-search',
  templateUrl: './fp-customer-search.component.html',
  styleUrls: ['./fp-customer-search.component.scss']
})
export class FpCustomerSearchComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  displayOrder: any;
  pageSize = new PageSize();
  uniquePageName = 'fpCustomerSerch-#343rta';
  searchForm: FormGroup;
  customers = [];
  selectedCustomer: any = {};
  facilityPaperDTO: any = {};
  onCustomerChangeSub: Subscription = new Subscription();
  onFacilityPaperLoadSub: Subscription = new Subscription();
  onSelectedCustomerChangeSub: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();

  optionsSelect = Constants.customerIdentificationTypeOptionsSelect;
  statusConst = Constants.statusConst;
  status = Constants.status;
  civilStatus = Constants.civilStatus;

  tableColumns = ['Customer Name', 'CIF ID', 'Action'];

  constructor(
    private addEditService: FacilityPaperAddEditService,
    private searchDataCacheService: SearchDataCacheService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private alertService: AlertService,
  ) {
  }

  ngOnInit() {

    this.pageSize.pageSize = 5;
    this.pageSize.length = 0;
    this.pageSize.pageIndex = 0;

    this.onCustomerChangeSub = this.addEditService.onCustomersChange
      .subscribe(data => {
        this.customers = this.addEditService.customers;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      customerName: [''],
      contactNumber: [''],
      identificationType: [''],
      identificationNumber: [''],
      customerFinancialID: [''],
      bankAccountNumber: [''],
    });
    this.doSearch();

    this.onFacilityPaperLoadSub = this.facilityPaperAddEditService.onFpCustomerChange.subscribe(data => {
      this.facilityPaperDTO = data;
    });

  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onCustomerChangeSub.unsubscribe();
    this.onFacilityPaperLoadSub.unsubscribe();
    this.onSelectedCustomerChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.addEditService.searchCustomers(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      customerName: '',
      contactNumber: '',
      identificationType: '',
      identificationNumber: '',
      bankAccountNumber: ''
    }, {onlySelf: false, emitEvent: true});

    this.doSearch();
  }

  doSearch() {
    this.addEditService.getPagedCustomersForJoiningParties(AppUtils.trim(this.getSearchData()), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }))
  }

  getSelectedCustomerDetails(customer) {
    let isCustomerAlreadyAdded = false;
    this.selectedCustomer = customer;

    this.facilityPaperDTO.casCustomerDTOList.forEach(casCustomer => {
      if (customer.customerFinancialID == casCustomer.customerFinancialID) {
        isCustomerAlreadyAdded = true
      }
    });

    if (!isCustomerAlreadyAdded) {
      this.action.next(this.selectedCustomer);
      this.customers = [];
      if (!_.isEmpty(this.content.facilityPaper)) {
        let fpCustomer = Object.assign({},
          {facilityPaperID: this.content.facilityPaper.facilityPaperID},
          {
            casCustomerDTOList: [{
              facilityPaperID: this.content.facilityPaper.facilityPaperID,
              customerID: this.selectedCustomer.customerID,
              isPrimary: false,
              displayOrder: this.displayOrder,
              status: 'ACT'
            }]
          }
        );
        this.addEditService.saveCasCustomer(fpCustomer);
      }
      this.mdbModalRef.hide();
    } else {
      this.alertService.showToaster(customer.customerName + " Already Added Customer!", SETTINGS.TOASTER_MESSAGES.warning);
    }
  }

  joinNonFinacleCustomer($event) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }
    this.mdbModalRef.hide();
    this.action.next({
      isJoinNonFinacleCustomer: true,
      identificationNumber: this.searchForm.getRawValue().identificationNumber,
      identificationType: this.searchForm.getRawValue().identificationType
    });
  }
}
