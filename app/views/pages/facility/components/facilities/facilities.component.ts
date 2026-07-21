import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {FacilitiesService} from "../../services/facilities.service";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";

@Component({
  selector: 'app-facilities',
  templateUrl: './facilities.component.html',
  styleUrls: ['./facilities.component.scss']
})
export class FacilitiesComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_ID)
  selectedFacilityID;

  uniquePageName = 'FacilitiesComponent-#343rta';
  pageSize = new PageSize();
  tableColumns: any = ['Account Number', 'Facility Amount', 'Outstanding Amount', 'Purpose of Advance', 'Facility Type', 'Condition', 'Status'];

  searchForm: FormGroup;
  selectedFacilities: any = [];
  onFacilityChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  constructor(
    private facilitiesService: FacilitiesService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {
  }

  ngOnInit() {

    this.onFacilityChangeSub = this.facilitiesService.onSelectFacility
      .subscribe(data => {
        this.selectedFacilities = this.facilitiesService.selectedFacilities;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      disbursementAccNumber: [''],
      facilityAmount: [''],
      outstandingAmount: [''],
      purposeOfAdvance: [''],
      facilityType: [''],
      condition: [''],
      status: ['']
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.facilitiesService.searchFacilities(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }))
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.facilitiesService.getFacilities(data.searchData, new Pagination({
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
    this.onSearchFormChangeSub.unsubscribe();
    this.onFacilityChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilitiesService.getFacilities(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      disbursementAccNumber: '',
      facilityAmount: '',
      outstandingAmount: '',
      purposeOfAdvance: '',
      facilityType: '',
      condition: '',
      status: ''
    })
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  loadFacility(facilityID) {
    if (facilityID == null) {
      this.selectedFacilityID = null;
    } else {
      this.selectedFacilityID = this.urlEncodeService.encode(facilityID);
    }
    this.router.navigate(['facility/details'])
  }
}
