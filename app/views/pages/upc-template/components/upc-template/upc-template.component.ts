import {Component, OnDestroy, OnInit} from '@angular/core';
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs/Rx";
import {Constants} from "../../../../../core/setting/constants";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {LocalStorage} from "ngx-webstorage";
import {UpcTemplateService} from "../../services/upc-template.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";

@Component({
  selector: 'app-upc-template',
  templateUrl: './upc-template.component.html',
  styleUrls: ['./upc-template.component.scss']
})
export class UpcTemplateComponent implements OnInit, OnDestroy {
  uniquePageName = 'upcTemplate-#389Njka';
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onCentresChangeSubs = new Subscription();
  searchFormChangeSubs = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;

  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPC_TEMPLATE_ID)
  selectedItemID;

  upcTemplateDTOs: any = [];
  tableColumns: any = ['Template Name', 'Description', 'Status', 'Approval Status', 'Approved Date', 'Approved By'];

  optionsSelect = Constants.statusOptionsSelect;
  approveStatusOptionsSelect = Constants.approveStatusOptionsSelect;

  constructor(public upcTemplateService: UpcTemplateService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private urlEncodeService: UrlEncodeService,
              private router: Router) {
  }

  ngOnInit() {

    this.onCentresChangeSubs = this.upcTemplateService.onTemplateChange
      .subscribe(data => {
        this.upcTemplateDTOs = this.upcTemplateService.pageDataList;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      templateName: [''],
      approveStatusList: [],
      status: [null]
    });

    this.searchFormChangeSubs = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.upcTemplateService.searchUPCTemplate(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.upcTemplateService.searchUPCTemplate(data.searchData, new Pagination({
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
    this.upcTemplateService.searchUPCTemplate(this.getSearchData(), new Pagination(event));
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  loadSelectedItem(itemID) {
    if (itemID == null) {
      this.selectedItemID = null;
    } else {
      this.selectedItemID = this.urlEncodeService.encode(itemID);
    }

    this.router.navigate(['/upc-template/add-edit']);
  }

  clearSearch() {
    this.searchForm.reset({
      templateName: '',
      approveStatusList: [],
      status: ''
    }, {onlySelf: false, emitEvent: true});
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

}
