import {Component, OnDestroy, OnInit} from '@angular/core';
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs/Rx";
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {SectionSubSectionService} from "../../services/section-sub-section.service";


@Component({
  selector: 'app-section-sub-section',
  templateUrl: './section-sub-section.component.html',
  styleUrls: ['./section-sub-section.component.scss']
})
export class SectionSubSectionComponent implements OnInit, OnDestroy {
  uniquePageName = 'sectionSubSectionComponent-#343rta';
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onCentresChangeSubs = new Subscription();
  searchFormChangeSubs = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;

  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPC_SECTION_ID)
  selectedUPCSectionID;

  upcSectionDTOs: any = [];
  tableColumns: any = ['Section Name', 'Description', 'Status', 'Approval Status', 'Approved Date', 'Approved By'];

  optionsSelect = Constants.statusOptionsSelect;
  approveStatusOptionsSelect = Constants.approveStatusOptionsSelect;

  constructor(public sectionSubSectionService: SectionSubSectionService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private urlEncodeService: UrlEncodeService,
              private router: Router) {
  }

  ngOnInit() {

    this.onCentresChangeSubs = this.sectionSubSectionService.onSectionsChange
      .subscribe(data => {
        this.upcSectionDTOs = this.sectionSubSectionService.sectionSubSections;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });
    this.upcSectionDTOs.sort((a,b) => (a.upcSectionName>b.upcSectionName)?1:((b.upcSectionName>a.upcSectionName)?-1:0));

    this.searchForm = this.formBuilder.group({
      upcSectionName: [''],
      approveStatusList: [],
      status: [null]
    });

    this.searchFormChangeSubs = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.sectionSubSectionService.searchUPCSection(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.sectionSubSectionService.searchUPCSection(data.searchData, new Pagination({
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
    this.sectionSubSectionService.searchUPCSection(this.getSearchData(), new Pagination(event));
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  loadUpcSection(upcSectionID) {
    if (upcSectionID == null) {
      this.selectedUPCSectionID = null;
    } else {
      this.selectedUPCSectionID = this.urlEncodeService.encode(upcSectionID);
    }

    this.router.navigate(['/section-sub-section/add-edit']);
  }

  clearSearch() {
    this.searchForm.reset({
      upcSectionName: '',
      approveStatusList: [],
      status: ''
    }, {onlySelf: false, emitEvent: true});
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }
}
