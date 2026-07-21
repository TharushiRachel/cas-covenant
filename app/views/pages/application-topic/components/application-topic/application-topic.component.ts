import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageSize} from "../../../../../core/dto/page.size";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Pagination} from "../../../../../core/dto/pagination";
import {ApplicationFormTopicService} from "../../services/application-form-topic.service";

@Component({
  selector: 'app-application-topic',
  templateUrl: './application-topic.component.html',
  styleUrls: ['./application-topic.component.scss']
})
export class ApplicationTopicComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_TOPIC_ID)
  selectedApplicationFormTopicID;

  uniquePageName = 'ApplicationTopicComponent-#ggrdw';
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  optionsSelect = Constants.statusOptionsSelect;
  approveStatusOptionsSelect = Constants.approveStatusOptionsSelect;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;
  status = Constants.status;
  statusConst = Constants.statusConst;
  applicationFormTopicPage = Constants.applicationFormTopicPage;
  applicationFormTopicPageConst = Constants.applicationFormTopicPageConst;

  pageOptionSelect = [
    {value: this.applicationFormTopicPageConst.REPAYMENT, label: this.applicationFormTopicPage.REPAYMENT},
    {value: this.applicationFormTopicPageConst.ASSETS, label: this.applicationFormTopicPage.ASSETS},
    {value: this.applicationFormTopicPageConst.EXECUTIVE_SUMMARY, label: this.applicationFormTopicPage.EXECUTIVE_SUMMARY},
  ];

  onCentresChangeSubs = new Subscription();
  searchFormChangeSubs = new Subscription();

  searchForm: FormGroup;
  pageSize = new PageSize();
  applicationSubTopicsPagedData: any = [];

  constructor(private urlEncodeService: UrlEncodeService,
              private router: Router,
              private formBuilder: FormBuilder,
              private applicationFormTopicService: ApplicationFormTopicService,
              private searchDataCacheService: SearchDataCacheService) {
  }

  ngOnInit() {
    this.onCentresChangeSubs = this.applicationFormTopicService.onApplicationTopicChange
      .subscribe(data => {
        this.applicationSubTopicsPagedData = this.applicationFormTopicService.applicationTopicsPagedData;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      applicationFormPage: '',
      topic: '',
      topicCode: '',
      approveStatusList: [],
      status: [null]
    });

    this.searchFormChangeSubs = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.applicationFormTopicService.searchAFTopics(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }));
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.applicationFormTopicService.searchAFTopics(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex
      }));
    }
  }

  ngOnDestroy(): void {
    this.onCentresChangeSubs.unsubscribe();
    this.searchFormChangeSubs.unsubscribe();
  }

  loadSelectedItem(itemID) {
    if (itemID == null) {
      this.selectedApplicationFormTopicID = null;
    } else {
      this.selectedApplicationFormTopicID = this.urlEncodeService.encode(itemID);
    }

    this.router.navigate(['/application-form-topics/add-edit']);
  }

  clearSearch() {
    this.searchForm.reset({
      applicationFormPage: '',
      topic: '',
      topicCode: '',
      approveStatusList: [],
      status: ''
    }, {onlySelf: false, emitEvent: true});
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.applicationFormTopicService.searchAFTopics(this.getSearchData(), new Pagination(event));
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

}
