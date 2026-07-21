import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {LeadAddEditService} from "../../services/lead-add-edit.service";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {PageSize} from "../../../../../core/dto/page.size";
import {Pagination} from "../../../../../core/dto/pagination";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../core/setting/constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lead-customer-detail',
  templateUrl: './lead-customer-detail.component.html',
  styleUrls: ['./lead-customer-detail.component.scss']
})
export class LeadCustomerDetailComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  pageSize = new PageSize();
  uniquePageName = 'LeadCustomerSearch-#343rta';
  searchForm: FormGroup;
  customers = [];
  selectedCustomer: any = {};
  onCustomerChangeSub: Subscription = new Subscription();
  onSelectedCustomerChangeSub: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();

  optionsSelect = Constants.customerIdentificationTypeOptionsSelect;
  statusConst = Constants.statusConst;
  status = Constants.status;
  civilStatus = Constants.civilStatus;

  tableColumns = ['Customer Name', 'CIF ID', 'Civil Status', 'Action'];

  constructor(
    private leadAddEditService: LeadAddEditService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private router: Router,
  ) {
  }

  ngOnInit() {

    this.pageSize.pageSize = 5;
    this.pageSize.length = 0;
    this.pageSize.pageIndex = 0;


    this.onCustomerChangeSub = this.leadAddEditService.onCustomersChange
      .subscribe(data => {
        this.customers = this.leadAddEditService.customers;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      customerName: [''],
      contactNumber: [''],
      identificationType: [''],
      identificationNumber: [''],
      customerFinancialID: [''],
    });
    this.doSearch();
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onCustomerChangeSub.unsubscribe();
    this.onSelectedCustomerChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.leadAddEditService.searchCustomers(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      customerName: '',
      contactNumber: '',
      identificationType: '',
      identificationNumber: ''
    }, {onlySelf: false, emitEvent: true});

    this.doSearch();
  }

  doSearch() {
    this.leadAddEditService.searchCustomers(this.getSearchData(), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }))
  }

  getSelectedCustomerDetails(customer) {
    this.selectedCustomer = customer;
    this.action.next(this.selectedCustomer);
    this.customers = [];
    this.leadAddEditService.getCustomerById(this.selectedCustomer.customerID);
    this.mdbModalRef.hide();
  }
}
