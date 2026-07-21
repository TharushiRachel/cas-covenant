import {Component, OnDestroy, OnInit} from '@angular/core';
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../core/setting/constants";
import {CreditFacilityTypesService} from "../../services/credit-facility-types.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";

@Component({
  selector: 'app-credit-facility-types',
  templateUrl: './credit-facility-types.component.html',
  styleUrls: ['./credit-facility-types.component.scss']
})
export class CreditFacilityTypesComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CREDIT_FACILITY_TYPE_ID)
  selectedCreditFacilityTypeID;

  uniquePageName = "CreditFacilityTypesComponent-#343rta";
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onCreditFacilityTypesChangeSub: Subscription = new Subscription();
  searchFormChangeSub: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  creditFacilityTypes: any = [];
  tableColumns: any = ['Facility Type', 'Description', 'Status', 'Approval Status', 'Approved Date', 'Approved By'];

  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(
    private creditFacilityTypesService: CreditFacilityTypesService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
  }

  ngOnInit() {

    this.onCreditFacilityTypesChangeSub = this.creditFacilityTypesService.onChangeCreditFacilityTypesChange.subscribe(data => {
      this.creditFacilityTypes = this.creditFacilityTypesService.creditFacilityTypes;
      this.pageSize.length = data.totalNoOfRecords;
      this.pageSize.pageIndex = data.currentPageNo - 1;
    });

    this.searchForm = this.formBuilder.group({
      facilityTypeName: [''],
      description: [''],
      status: ['ACT']
    });

    this.searchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.creditFacilityTypesService.searchCreditFacilityTypes(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });


    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false})
      this.pageSize = data.pageSize;

      this.creditFacilityTypesService.searchCreditFacilityTypes(data.searchData, new Pagination({
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

    this.onCreditFacilityTypesChangeSub.unsubscribe();
    this.searchFormChangeSub.unsubscribe()
  }

  onPageEvent(Event) {
    this.pageSize.pageSize = Event.pageSize;
    this.creditFacilityTypesService.searchCreditFacilityTypes(this.getSearchData(), new Pagination(Event))
  }

  loadCreditfacilityType(creditFacilityTypeID) {
    if (creditFacilityTypeID == null) {
      this.selectedCreditFacilityTypeID = null
    } else {
      this.selectedCreditFacilityTypeID = this.urlEncodeService.encode(creditFacilityTypeID);
    }
    this.router.navigate(["/credit-facility-types/add-edit"])
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: true, emitEvent: true})
  }

  clearSearch() {
    this.searchForm.reset({
      facilityTypeName: '',
      description: '',
      status: 'ACT'
    }, {onlySelf: true, emitEvent: true});
  }


  getSearchData() {
    return this.searchForm.getRawValue();
  }


}
