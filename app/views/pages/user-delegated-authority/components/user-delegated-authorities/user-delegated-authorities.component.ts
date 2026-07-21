import {Component, OnDestroy, OnInit} from '@angular/core';
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs/Rx";
import {Constants} from "../../../../../core/setting/constants";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Router} from "@angular/router";
import {UserDelegatedAuthoritiesService} from "../../services/user-delegated-authorities.service";
import {Pagination} from "../../../../../core/dto/pagination";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {CurrencyService} from "../../../../../core/service/common/currency.service";

@Component({
  selector: 'app-user-delegated-authorities',
  templateUrl: './user-delegated-authorities.component.html',
  styleUrls: ['./user-delegated-authorities.component.scss']
})
export class UserDelegatedAuthoritiesComponent implements OnInit, OnDestroy {

  uniquePageName = 'UserDelegatedAuthoritiesComponent-#343rta';
  pageSize = new PageSize();

  searchForm: FormGroup;

  onUserDAChangeSub: Subscription = new Subscription();
  onSearchFormChangesub: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_USER_DA_ID)
  selectedUserDaID;

  userDAs: any = [];

  tableColumns: any = ['User Name', 'Max amount','Clean amount', 'Description', 'Status', 'Approved Status', 'Approved Date', 'Approved By']

  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];


  constructor(private userDelegatedAuthoritiesService: UserDelegatedAuthoritiesService,
              private searchDataCacheService: SearchDataCacheService,
              private urlEncodeService: UrlEncodeService,
              public currencyService : CurrencyService,
              private formBuilder: FormBuilder,
              private router: Router) {
  }

  ngOnInit() {

    this.onSearchFormChangesub = this.userDelegatedAuthoritiesService.onUserDAsChange
      .subscribe(data => {
        this.userDAs = this.userDelegatedAuthoritiesService.userDAs;
        //console.log("this.userDelegatedAuthoritiesService.userDAs", this.userDelegatedAuthoritiesService.userDAs)
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      userName: [''],
      maxAmount: [null],
      cleanAmount:[null],
      description: [''],
      status: [null]
    });

    this.onSearchFormChangesub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.userDelegatedAuthoritiesService.searchUserDAs(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.userDelegatedAuthoritiesService.searchUserDAs(data.searchData, new Pagination({
        pageSize: this.pageSize.pageSize,
        pageIndex: 0
      }))
    }


  }

  ngOnDestroy(): void {

    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onSearchFormChangesub.unsubscribe();
    this.onUserDAChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(Event) {
    this.pageSize.pageSize = Event.pageSize;
    this.userDelegatedAuthoritiesService.searchUserDAs(this.getSearchData(), new Pagination(Event))
  }

  loadUserDA(userDaID) {
    if (userDaID == null) {
      this.selectedUserDaID = null
    } else {
      this.selectedUserDaID = this.urlEncodeService.encode(userDaID);
    }
    this.router.navigate(['/user-delegated-authorities/add-edit']);
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  clearSearch() {
    this.searchForm.reset({
      userName: '',
      maxAmount: '',
      description: '',
      status: ''
    }, {onlySelf: false, emitEvent: true});
  }
}
