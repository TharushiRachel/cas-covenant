import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {LeadsService} from "../../../lead/services/leads.service";
import {PageSize} from "../../../../../core/dto/page.size";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {Constants} from "../../../../../core/setting/constants";
import {Pagination} from "../../../../../core/dto/pagination";
import {MyLeadCountService} from "../../../../../core/service/leed/my-lead-count.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {IMyOptions} from "ng-uikit-pro-standard";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-branch-leads',
  templateUrl: './my-branch-leads.component.html',
  styleUrls: ['./my-branch-leads.component.scss']
})
export class MyBranchLeadsComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  uniquePageName = 'MyBranchLeadsComponent-#343rta';

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: 1900
  };

  pageSize = new PageSize();

  searchForm: FormGroup;

  searchData: any = {};
  myBranchLeads = [];
  onSearchFormChangeSub = new Subscription();
  onMyBranchLeadChangeSub = new Subscription();

  leadStatusConst = Constants.leadStatusConst;
  leadStatus = Constants.leadStatus;
  leadCreationType = Constants.leadCreationTypeConst;
  tableColumns: any = ['Lead Name', 'Reference ID',
    'NIC Number', 'Account Number', 'Contact Number', 'Created Date', 'Lead Type', 'Lead Status', 'Assigned User'];

  optionsSelect = [
    {value: this.leadStatusConst.PENDING, label: this.leadStatus.PENDING},
    {value: this.leadStatusConst.SUBMITTED, label: this.leadStatus.SUBMITTED},
    {value: this.leadStatusConst.APPROVED, label: this.leadStatus.APPROVED},
    {value: this.leadStatusConst.ACCEPTED, label: this.leadStatus.ACCEPTED},
    {value: this.leadStatusConst.RETURNED, label: this.leadStatus.RETURNED},
    {value: this.leadStatusConst.DECLINED, label: this.leadStatus.DECLINED},
    {value: this.leadStatusConst.PAPER_CREATED, label: this.leadStatus.PAPER_CREATED},
  ];

  optionsLeadTypeSelect = [
    {value: this.leadCreationType.PERSONAL, label: this.leadCreationType.PERSONAL},
    {value: this.leadCreationType.BUSINESS, label: this.leadCreationType.BUSINESS},
  ];

  constructor(
    private leadsService: LeadsService,
    private applicationService: ApplicationService,
    private myLeadCountService: MyLeadCountService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router) {
  }

  ngOnInit() {
    this.onMyBranchLeadChangeSub = this.leadsService.onLeadsChange
      .subscribe(data => {
        this.myBranchLeads = this.leadsService.leads;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      leadName: [''],
      leadRefNumber: [''],
      identificationNumber: [''],
      accountNumber: [''],
      mobileNo: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
      leadStatusList: [],
      leadCreationTypeList: [],
      assignUserID: ['']
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.leadsService.searchLeads(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    this.getBranchLeads();
    this.myLeadCountService.getLoggedInUserPendingLeadCount();
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onMyBranchLeadChangeSub.unsubscribe();
    this.onSearchFormChangeSub.unsubscribe();
  }

  getBranchLeads() {
    this.searchData = Object.assign({},
      {
        branchCode: this.applicationService.getLoggedInUserDivCode(),
        isInMyBranchLeadPage: 'Y'
      });

    this.leadsService.searchLeads(this.searchData, new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.leadsService.searchLeads(this.searchData, new Pagination(event));
  }

  getSearchData() {
    let data = Object.assign({},
      this.searchForm.getRawValue(),
      {
        branchCode: this.applicationService.getLoggedInUserDivCode(),
        isInMyBranchLeadPage: 'Y'
      });
    return data;
  }

  clearSearch() {
    this.searchForm.reset({
      identificationNumber: '',
      accountNumber: '',
      mobileNo: '',
      createdFromDateStr: '',
      leadStatusList: [],
      assignUserID: ''
    }, {onlySelf: false, emitEvent: true})
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  loadLead(leadID) {
    if (leadID == null) {
      this.selectedLeadID = null;
    } else {
      this.selectedLeadID = this.urlEncodeService.encode(leadID);
    }

    this.router.navigate(['/leads/add-edit']);
  }

}
