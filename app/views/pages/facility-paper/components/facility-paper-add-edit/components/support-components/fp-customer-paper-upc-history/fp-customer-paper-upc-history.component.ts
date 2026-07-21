import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../../core/setting/constants";
import * as _ from 'lodash';
import {PageSize} from "../../../../../../../../core/dto/page.size";
import {Pagination} from "../../../../../../../../core/dto/pagination";
import {FacilityPaperAddEditService} from "../../../../../services/facility-paper-add-edit.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-fp-customer-paper-upc-history',
  templateUrl: './fp-customer-paper-upc-history.component.html',
  styleUrls: ['./fp-customer-paper-upc-history.component.scss']
})
export class FpCustomerPaperUpcHistoryComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  results: Subject<any>;
  onPagedFPHistoryWithUPCTemplateDetailsChangeSub = new Subscription();
  formSubs = new Subscription();
  facilityPaper;
  primaryCustomer;
  pageSize = new PageSize();
  records = [];
  allOptions = [];

  searchForm: FormGroup;
  optionsSelect = Constants.optionsFacilityPaperStatusSelectOpt;
  facilityPaperStatus = Constants.facilityPaperDashboardStatus;

  constructor(private mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              private applicationService: ApplicationService,
              private facilityPaperAddEditService: FacilityPaperAddEditService) {
  }

  ngOnInit() {

    this.allOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.results = new BehaviorSubject(this.allOptions);

    this.pageSize.pageSize = 5;
    this.pageSize.length = 0;
    this.pageSize.pageIndex = 0;

    this.primaryCustomer = _.find(this.facilityPaper.casCustomerDTOList, data => data.isPrimary);

    this.onPagedFPHistoryWithUPCTemplateDetailsChangeSub = this.facilityPaperAddEditService.onPagedFacilityPaperHistoryWithUPCTemplateDetails.subscribe(
      (data: any) => {
        if (!_.isEmpty(data)) {
          this.records = data.pageData;
          this.pageSize.length = data.totalNoOfRecords;
          this.pageSize.pageIndex = data.currentPageNo - 1;
        }
      }
    );

    this.searchForm = this.formBuilder.group({
      customerID: [this.primaryCustomer ? this.primaryCustomer.customerID : ''],
      fpRefNumber: [''],
      templateName: [''],
      facilityPaperStatus: [''],
      createdBranchCode: [''],
      assignUserDisplayName: [''],
    });

    this.formSubs = this.searchForm.controls.createdBranchCode.valueChanges.subscribe((value: any) => {
      this.results.next(this.filter(value));
    });

    this.doSearch();
  }

  getSearchData() {
    let branch = AppUtils.getBranchFromBranchName(this.allOptions, this.searchForm.controls.createdBranchCode.value);
    return Object.assign({}, this.searchForm.getRawValue(), {createdBranchCode: branch ? branch.branchCode : ''});
  }

  close(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  doSearch() {
    this.facilityPaperAddEditService.getPagedFacilityPaperHistoryWithUPCTemplateDetails(AppUtils.trim(this.getSearchData()), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));
  }

  clearSearch() {
    this.searchForm.reset({
      customerID: this.primaryCustomer ? this.primaryCustomer.customerID : '',
      fpRefNumber: '',
      templateName: '',
      facilityPaperStatus: '',
      createdBranchCode: '',
      assignUserDisplayName: '',
    }, {onlySelf: false, emitEvent: true});
    this.doSearch();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperAddEditService.getPagedFacilityPaperHistoryWithUPCTemplateDetails(AppUtils.trim(this.getSearchData()), new Pagination(event));
  }

  ngOnDestroy(): void {
    this.onPagedFPHistoryWithUPCTemplateDetailsChangeSub.unsubscribe();
  }

  getBranchName(branchCode) {
    this.allOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allOptions, branchCode);

    if (!_.isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allOptions.filter((item: any) => item.branchName.toLowerCase().includes(filterValue));
  }

  copyUPCData(data) {
    let updateData = {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      updatedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      ...data
    };
    this.facilityPaperAddEditService.copyUPCSectionData(AppUtils.trim(updateData));
    this.action.next(null);
    this.mdbModalRef.hide();
  }

}
