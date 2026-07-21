import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../core/setting/constants";
import {CreditFacilityTypeTemplatesService} from "../../services/credit-facility-type-templates.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {PageSize} from "../../../../../core/dto/page.size";
import {Pagination} from "../../../../../core/dto/pagination";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";

@Component({
  selector: 'app-credit-facility-type-templates',
  templateUrl: './credit-facility-type-templates.component.html',
  styleUrls: ['./credit-facility-type-templates.component.scss']
})
export class CreditFacilityTypeTemplatesComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CREDIT_FACILITY_TEMPLATE_ID)
  selectedCreditFacilityTemplateID;

  uniquePageName = 'CreditFacilityTypeTemplatesComponent-#343rta';

  pageSize = new PageSize();
  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onCreditFacilityTemplateChangeSub: Subscription = new Subscription();
  onSearchFormChangeSub: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;


  creditFacilityTemplates: any = [];

  tableColumns: any = ['Template Name', 'Facility Group', 'Description', 'Max Amount', 'Min Amount', 'Status', 'Approval Status', 'Approved Date', 'Approved By'];

  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(private creditFacilityTypeTemplatesService: CreditFacilityTypeTemplatesService,
              private searchDataCacheService: SearchDataCacheService,
              private urlEncodeService: UrlEncodeService,
              private formBuilder: FormBuilder,
              private router: Router) {
  }

  ngOnInit() {

    this.onCreditFacilityTemplateChangeSub = this.creditFacilityTypeTemplatesService.onCreditFacilityTemplateChange
      .subscribe(data => {
        this.creditFacilityTemplates = this.creditFacilityTypeTemplatesService.creditFacilityTemplates;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({

      creditFacilityName: [''],
      description: [''],
      maxFacilityAmount: [null],
      minFacilityAmount: [null],
      status: [null]
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.creditFacilityTypeTemplatesService.searchCreditFacilityTemplates(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }))
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.creditFacilityTypeTemplatesService.searchCreditFacilityTemplates(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex
      }))
    }

  }

  ngOnDestroy(): void {

    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onCreditFacilityTemplateChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.creditFacilityTypeTemplatesService.searchCreditFacilityTemplates(this.getSearchData(), new Pagination(event));
  }

  loadCreditFacilityTemplate(creditFacilityTemplateID) {
    if (creditFacilityTemplateID == null) {
      this.selectedCreditFacilityTemplateID = null
    } else {
      this.selectedCreditFacilityTemplateID = this.urlEncodeService.encode(creditFacilityTemplateID);
    }
    this.router.navigate(['/credit-facility-type-templates/add-edit']);
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true})
  }

  clearSearch() {
    this.searchForm.reset({
      creditFacilityName: '',
      description: '',
      maxFacilityAmount: '',
      minFacilityAmount: '',
      status: ''
    })
  }

}
