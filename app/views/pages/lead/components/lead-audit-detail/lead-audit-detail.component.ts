import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuditService} from "../../../audit/services/audit.service";
import {Constants} from "../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {Pagination} from "../../../../../core/dto/pagination";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {PageSize} from "../../../../../core/dto/page.size";
import {IMyOptions, MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {AuditContentComponent} from "../../../audit/components/audit-content/audit-content.component";

@Component({
  selector: 'app-lead-audit-detail',
  templateUrl: './lead-audit-detail.component.html',
  styleUrls: ['./lead-audit-detail.component.scss']
})
export class LeadAuditDetailComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  uniquePageName = 'LeadAuditDetailComponent-#343rta';
  pageSize = new PageSize();

  searchForm: FormGroup;
  auditList: any = [];

  onSelectAuditChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();

  constructor(
    private urlEncodeService: UrlEncodeService,
    private auditService: AuditService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    private mdbModalService: MDBModalService
  ) {
  }

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy'
  };
  tableColumns = ['User Name', 'First Name', 'Last Name', 'Audit Sub Category', 'Audited Date'];

  auditSubCategoryConst = Constants.webAuditSubCategory;

  ngOnInit() {

    this.onSelectAuditChangeSub = this.auditService.onSelectAuditChange
      .subscribe(data => {
        this.auditList = this.auditService.auditDetails;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      auditSubCategoryList: [],
      userName: [''],
      userFirstName: [''],
      userLastName: [''],
      auditFromDateStr: [''],
      auditToDateStr: ['']
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.auditService.searchAuditDetails(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }))
      });

    this.onComponentLoad();
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onSearchFormChangeSub.unsubscribe();
    this.onSelectAuditChangeSub.unsubscribe();
  }

  getSearchData() {
    let reqData = Object.assign({},
      this.searchForm.getRawValue(),
      {auditTypeID: this.urlEncodeService.decode(this.selectedLeadID)},
      {auditMainCategoryList: ['LEAD']});
    return reqData;
  }

  onComponentLoad() {
    let data = Object.assign({},
      {auditTypeID: this.urlEncodeService.decode(this.selectedLeadID)},
      {auditMainCategoryList: ['LEAD']});
    this.auditService.searchAuditDetails(data);
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.auditService.searchAuditDetails(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      auditSubCategoryList: [],
      userName: '',
      userFirstName: '',
      userLastName: '',
      auditFromDateStr: '',
      auditToDateStr: '',
    })
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  openAuditContentModal(auditDto) {
    const initialState = {
      list: [
        {"tag": 'Count', "value": auditDto}
      ]
    };

    this.modalRef = this.mdbModalService.show(AuditContentComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p audit-modal-margin-center  modal-dialog-scrollable',
      containerClass: 'right',
      animated: true,
      data: {
        heading: "comming dto",
        content: {dto: auditDto}
      }
    });
  }

}
