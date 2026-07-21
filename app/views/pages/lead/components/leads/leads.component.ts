import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../core/setting/constants";
import {LeadsService} from "../../services/leads.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {MyLeadCountService} from "../../../../../core/service/leed/my-lead-count.service";
import {IMyOptions} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-leads',
  templateUrl: './leads.component.html',
  styleUrls: ['./leads.component.scss']
})
export class LeadsComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: 1900
  };

  uniquePageName = 'LeadsComponent-#343rta';

  leads: any = [];
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onLeadesChangeSub: Subscription = new Subscription();
  onSearchFormChangeSub: Subscription = new Subscription();

  leadStatusConst = Constants.leadStatusConst;
  leadCreationType = Constants.leadCreationTypeConst;
  leadStatus = Constants.leadStatus;
  loggedInUserName;

  tableColumns: any = ['Lead Name', 'Reference ID',
    'NIC Number', 'Account Number', 'Contact Number', 'Preferred Branch', 'Created Date', 'Lead Type', 'Lead Status', 'Assigned User'];

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
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router,
    private myLeadCountService: MyLeadCountService,
    private applicationService: ApplicationService) {
  }

  ngOnInit() {


    this.loggedInUserName = this.applicationService.getLoggedInUser().userName;
    this.onLeadesChangeSub = this.leadsService.onLeadsChange
      .subscribe(data => {
        this.leads = this.leadsService.leads;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;

        this.myLeadCountService.getLoggedInUserPendingLeadCount();
      });

    this.searchForm = this.formBuilder.group({
      leadName: [''],
      leadRefNumber: [''],
      identificationNumber: [''],
      accountNumber: [''],
      mobileNo: [''],
      branchCode: [''],
      branchName: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
      leadStatusList: [],
      leadCreationTypeList: [],
      assignedUserID: [''],
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

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.leadsService.searchLeads(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex,
      }))
    }
  }

  ngOnDestroy(): void {

    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onLeadesChangeSub.unsubscribe();
    this.onSearchFormChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.leadsService.searchLeads(this.getSearchData(), new Pagination(event));
  }

  loadLead(leadID) {
    if (leadID == null) {
      this.selectedLeadID = null;
    } else {
      this.selectedLeadID = this.urlEncodeService.encode(leadID);
    }

    this.router.navigate(['/leads/add-edit']);
  }

  clearSearch() {
    this.searchForm.reset({
      identificationNumber: '',
      accountNumber: '',
      mobileNo: '',
      branchCode: '',
      branchName: '',
      createdFromDateStr: '',
      leadStatusList: [],
      assignedUserID: '',
      myLeads: true,
    }, {onlySelf: false, emitEvent: true})
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

}
