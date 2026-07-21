import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs/Rx";
import {Constants} from "../../../../../core/setting/constants";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {AgentsService} from "../../services/agents.service";

@Component({
  selector: 'app-agents-view',
  templateUrl: './agents-view.component.html',
  styleUrls: ['./agents-view.component.scss']
})
export class AgentsViewComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_AGENT_ID)
  selectedAgentID;

  uniquePageName = 'AgentsViewComponent-#ffrdw';

  agents: any = [];
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onAgentssChangeSub: Subscription = new Subscription();
  onSearchFormChangeSub: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;

  tableColumns: any = [
    'User name',
    'Name',
    'Email',
    'NIC',
    'Mobile No',
    'Supervisor UPM ID',
    'Status'
  ];

  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(
    private agentsService: AgentsService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router,) {
  }

  ngOnInit() {

    this.onAgentssChangeSub = this.agentsService.onAgentsChange
      .subscribe(data => {
        this.agents = this.agentsService.agents;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      userName: [''],
      firstName: [''],
      lastName: [''],
      email: [''],
      mobileNumber: [''],
      nic: [''],
      supervisorADUserID: [''],
      status: [],
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.agentsService.searchAgents(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.agentsService.searchAgents(data.searchData, new Pagination({
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
    this.onAgentssChangeSub.unsubscribe();
    this.onSearchFormChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.agentsService.searchAgents(this.getSearchData(), new Pagination(event));
  }

  loadAgent(agentID) {
    if (agentID == null) {
      this.selectedAgentID = null;
    } else {
      this.selectedAgentID = this.urlEncodeService.encode(agentID);
    }

    this.router.navigate(['/agents/add-edit']);
  }

  clearSearch() {
    this.searchForm.reset({
      userName: '',
      firstName: '',
      lastName: '',
      email: '',
      mobileNumber: '',
      nic: '',
      supervisorADUserID: '',
      status: this.statusConst.ACT,
    }, {onlySelf: false, emitEvent: true})
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

}
