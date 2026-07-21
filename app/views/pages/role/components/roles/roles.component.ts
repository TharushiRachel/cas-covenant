import {Component, OnDestroy, OnInit} from '@angular/core';
import {PageSize} from "../../../../../core/dto/page.size";
import {Subscription} from "rxjs/Rx";
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {RolesService} from "../../services/roles.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss']
})
export class RolesComponent implements OnInit, OnDestroy {
  uniquePageName = 'RolesComponent-#343rta';
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onCentresChangeSubs = new Subscription();
  searchFormChangeSubs = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_ROLE_ID)
  selectedRoleID;

  roles: any = [];

  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;
  tableColumns: any = ['Role Name','UPM Privilege Code', 'Status', 'Approval Status', 'Approved Date', 'Approved By'];
  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(public roleService: RolesService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private urlEncodeService: UrlEncodeService,
              private router: Router) {
  }

  ngOnInit() {

    this.onCentresChangeSubs = this.roleService.onRolesChange
      .subscribe(data => {
        this.roles = this.roleService.roles;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      roleName: [''],
      upmPrivilegeCode: [''],
      status: [null]
    });

    this.searchFormChangeSubs = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.roleService.searchRoles(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.roleService.searchRoles(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex
      }));
    }

  }


  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onCentresChangeSubs.unsubscribe();
    this.searchFormChangeSubs.unsubscribe();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.roleService.searchRoles(this.getSearchData(), new Pagination(event));
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  loadRole(roleID) {
    if (roleID == null) {
      this.selectedRoleID = null;
    } else {
      this.selectedRoleID = this.urlEncodeService.encode(roleID);
    }

    this.router.navigate(['/roles/add-edit']);
  }

  clearSearch() {
    this.searchForm.reset({
      roleName: '',
      upmPrivilegeCode: '',
      status: ''
    }, {onlySelf: false, emitEvent: true});
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

}
