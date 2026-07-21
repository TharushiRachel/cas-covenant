import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageSize} from "../../../../../../core/dto/page.size";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../core/setting/constants";
import {CacheSearchData, SearchDataCacheService} from "../../../../../../core/service/common/search-data-cache.service";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../../core/dto/pagination";
import {AppUtils} from "../../../../../../shared/app.utils";
import {ApplicationFormService} from "../../services/application-form.service";
import {isEmpty} from "lodash";

@Component({
  selector: 'app-application-form',
  templateUrl: './application-form.component.html',
  styleUrls: ['./application-form.component.scss']
})
export class ApplicationFormComponent implements OnInit, OnDestroy {

  uniquePageName = 'ApplicationFormComponent-#aardw';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  searchForm: FormGroup;
  pageSize = new PageSize();
  onSelectApplicationFormChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationForms: any = [];
  allBankOptions: any = {};

  optionsSelect: any = [
    {value: this.applicationFormCurrentStatusConst.IN_PROGRESS, label: this.applicationFormStatus.IN_PROGRESS},
    {value: this.applicationFormCurrentStatusConst.DECLINED, label: this.applicationFormStatus.DECLINED},
    {value: this.applicationFormCurrentStatusConst.DRAFT, label: this.applicationFormStatus.DRAFT},
    {value: this.applicationFormCurrentStatusConst.RETURNED, label: this.applicationFormStatus.RETURNED},
  ];

  constructor(private applicationFormService: ApplicationFormService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              private urlEncodeService: UrlEncodeService,
              private router: Router,
  ) {
  }

  ngOnInit() {

    this.onSelectApplicationFormChangeSub = this.applicationFormService.onApplicationFormChange
      .subscribe(data => {
        this.applicationForms = this.applicationFormService.applicationForms;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      afRefNumber: [''],
      assignUserDisplayName: [''],
      currentApplicationFormStatus: [''],
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.applicationFormService.searchApplicationForms(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;
      this.applicationFormService.searchApplicationForms(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex
      }))
    }
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.applicationFormService.searchApplicationForms(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      assignUserDisplayName: '',
      currentApplicationFormStatus: ''
    }, {onlySelf: false, emitEvent: true});
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onSearchFormChangeSub.unsubscribe();
    this.onSelectApplicationFormChangeSub.unsubscribe();
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  loadApplicationForm(applicationFormID) {
    this.selectedApplicationFormID = this.urlEncodeService.encode(applicationFormID);
    this.router.navigate(['/application-form/add-edit']);
  }

  getAssignedUser(applicationForm) {
    return applicationForm.assignUserDisplayName ? applicationForm.assignUserDisplayName : this.getBranchName(applicationForm.assignDepartmentCode);
  }
}
