import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../core/setting/constants";
import {BranchApplicationFormService} from "../../services/branch-application-form.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../../core/service/common/search-data-cache.service";
import {PageSize} from "../../../../../../core/dto/page.size";
import {Subscription} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {AppUtils} from "../../../../../../shared/app.utils";
import {isEmpty} from "lodash";
import {Pagination} from "../../../../../../core/dto/pagination";

@Component({
  selector: 'app-branch-application-form',
  templateUrl: './branch-application-form.component.html',
  styleUrls: ['./branch-application-form.component.scss']
})
export class BranchApplicationFormComponent implements OnInit, OnDestroy {

  uniquePageName = 'BranchApplicationFormComponent-#aardw';
  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  searchForm: FormGroup;
  pageSize = new PageSize();
  onSearchFormChangeSub = new Subscription();
  onSelectApplicationFormChangeSub = new Subscription();
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationForms: any = [];
  allBankOptions: any = {};

  optionsSelect: any = [
    {value: this.applicationFormCurrentStatusConst.IN_PROGRESS, label: this.applicationFormStatus.IN_PROGRESS},
    {value: this.applicationFormCurrentStatusConst.DECLINED, label: this.applicationFormStatus.DECLINED},
    {value: this.applicationFormCurrentStatusConst.DRAFT, label: this.applicationFormStatus.DRAFT},
    {value: this.applicationFormCurrentStatusConst.RETURNED, label: this.applicationFormStatus.RETURNED},
  ];

  constructor(private branchApplicationFormService: BranchApplicationFormService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              private urlEncodeService: UrlEncodeService,
              private router: Router,) {
  }

  ngOnInit() {
    this.onSelectApplicationFormChangeSub = this.branchApplicationFormService.onBranchApplicationFormChange
      .subscribe(data => {
        this.applicationForms = this.branchApplicationFormService.applicationForms;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      afRefNumber: [''],
      assignUserDisplayName: [''],
      identificationNumber: '',
      currentApplicationFormStatus: [''],
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.branchApplicationFormService.searchBranchApplicationForms(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;
      this.branchApplicationFormService.searchBranchApplicationForms(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex
      }))
    }
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      assignUserDisplayName: '',
      identificationNumber: '',
      currentApplicationFormStatus: ''
    }, {onlySelf: false, emitEvent: true});
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onSearchFormChangeSub.unsubscribe();
    this.onSelectApplicationFormChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  loadApplicationForm(applicationFormID) {
    this.selectedApplicationFormID = this.urlEncodeService.encode(applicationFormID);
    this.router.navigate(['/application-form/add-edit']);
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  getAssignedUser(applicationForm) {
    return applicationForm.assignUserDisplayName ? applicationForm.assignUserDisplayName : this.getBranchName(applicationForm.assignDepartmentCode);
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.branchApplicationFormService.searchBranchApplicationForms(this.getSearchData(), new Pagination(event));
  }

  getCustomerName(paper) {
    return paper.customerName ? paper.customerName : paper.nameWithInitials ? paper.nameWithInitials : paper.nameOfBusiness || '-'
  }

  getCustomerNameTooltip(paper) {
    return paper.identificationNumber ? paper.identificationType + ' : ' + paper.identificationNumber : ''
  }

}
