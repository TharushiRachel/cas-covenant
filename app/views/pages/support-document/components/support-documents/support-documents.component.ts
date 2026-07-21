import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../core/setting/constants";
import {SupportDocumentsService} from "../../services/support-documents.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Pagination} from "../../../../../core/dto/pagination";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";

@Component({
  selector: 'app-support-documents',
  templateUrl: './support-documents.component.html',
  styleUrls: ['./support-documents.component.scss']
})
export class SupportDocumentsComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_SUPPORTING_DOC_ID)
  selectedSupporingDocID;

  uniquePageName = "SupportDocumentsComponent-#343rta";
  pageSize = new PageSize();

  searchForm: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onSupportingDocChangeSub: Subscription = new Subscription();
  onSearchFormChange: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  supportingDocs: any = [];

  tableColumns: any = ['Document Name', 'Description', 'Document Type', 'Status', 'Approval Status', 'Approved Date', 'Approved By']

  optionsSelect = [
    {value: null, label: 'All'},
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];


  constructor(
    private supportiDocumentsService: SupportDocumentsService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
  }

  ngOnInit() {

    this.onSupportingDocChangeSub = this.supportiDocumentsService.onSupportingDocChange
      .subscribe(data => {
        this.supportingDocs = this.supportiDocumentsService.supportingDocs;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      documentName: [''],
      description: [''],
      supportDocumentType: [''],
      status: [null]
    })

    this.onSearchFormChange = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.supportiDocumentsService.searchSupportingDocs(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName)
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.supportiDocumentsService.searchSupportingDocs(data.searchData, new Pagination({
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
    this.onSupportingDocChangeSub.unsubscribe();
    this.onSearchFormChange.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(Event) {
    this.pageSize.pageSize = Event.pageSize;
    this.supportiDocumentsService.searchSupportingDocs(this.getSearchData(), new Pagination(Event))
  }

  loadSupportingDoc(supportingDocID) {
    if (supportingDocID == null) {
      this.selectedSupporingDocID = null;
    } else {
      this.selectedSupporingDocID = this.urlEncodeService.encode(supportingDocID);
    }
    this.router.navigate(['/support-documents/add-edit']);
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  clearSearch() {
    this.searchForm.reset({
      documentName: '',
      description: '',
      supportDocumentType: '',
      status: null
    }, {onlySelf: false, emitEvent: true})
  }
}
