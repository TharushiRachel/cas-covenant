import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageSize} from "../../../../../../core/dto/page.size";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {Subscription} from "rxjs";
import {ApplicationFromInboxService} from "../../services/application-from-inbox.service";
import {Constants} from "../../../../../../core/setting/constants";
import {Pagination} from "../../../../../../core/dto/pagination";
import {CacheSearchData, SearchDataCacheService} from "../../../../../../core/service/common/search-data-cache.service";
import {AppUtils} from "../../../../../../shared/app.utils";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import * as _ from "lodash";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-apf-application-forms',
  templateUrl: './apf-application-forms.component.html',
  styleUrls: ['./apf-application-forms.component.scss']
})
export class ApfApplicationFormsComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  uniquePageName = 'ApplicationFormComponent-#layer789656';

  searchForm: FormGroup;
  pageSize = new PageSize();
  onSelectApplicationFormChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  selectedApplicationForms: any = [];
  allBankOptions: any = {};
  workflows: any = {};

  optionsSelect: any = [
    {value: this.applicationFormCurrentStatusConst.IN_PROGRESS, label: this.applicationFormStatus.IN_PROGRESS},
    {value: this.applicationFormCurrentStatusConst.DECLINED, label: this.applicationFormStatus.DECLINED},
    {value: this.applicationFormCurrentStatusConst.DRAFT, label: this.applicationFormStatus.DRAFT},
    {value: this.applicationFormCurrentStatusConst.RETURNED, label: this.applicationFormStatus.RETURNED},
  ];

  constructor(private applicationFromInboxService: ApplicationFromInboxService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              private urlEncodeService: UrlEncodeService,
              private router: Router,
  ) {
  }

  ngOnInit() {

    this.onSelectApplicationFormChangeSub = this.applicationFromInboxService.onSelectedApplicationFormChange
      .subscribe(data => {
        this.selectedApplicationForms = this.applicationFromInboxService.selectedApplicationForms;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      afRefNumber: [''],
      identificationNumber: [''],
      currentApplicationFormStatus: [''],
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.applicationFromInboxService.searchApplicationForms(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;
      this.applicationFromInboxService.searchApplicationForms(data.searchData, new Pagination({
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
    this.applicationFromInboxService.searchApplicationForms(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      afRefNumber: '',
      identificationNumber: '',
      currentApplicationFormStatus: '',
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

    if (!_.isEmpty(branch)) {
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

  getCustomerName(paper) {
    return paper.customerName ? paper.customerName : paper.nameWithInitials ? paper.nameWithInitials : paper.nameOfBusiness || '-'
  }

  getCustomerNameTooltip(paper) {
    return paper.identificationNumber ? paper.identificationType + ' : ' + paper.identificationNumber : ''
  }
}
