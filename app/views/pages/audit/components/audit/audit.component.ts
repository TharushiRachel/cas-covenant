import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {AuditService} from "../../services/audit.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageSize} from "../../../../../core/dto/page.size";
import {Pagination} from "../../../../../core/dto/pagination";
import {Constants} from "../../../../../core/setting/constants";
import {IMyOptions, MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {AuditContentComponent} from "../audit-content/audit-content.component";
import * as _ from 'lodash';

@Component({
  selector: 'app-audit',
  templateUrl: './audit.component.html',
  styleUrls: ['./audit.component.scss']
})
export class AuditComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;

  optionSelectForAuditMainCategory = Constants.webAuditMainCategoryOptionSelect;
  //optionSelectForAuditSubCategory = Constants.webAuditSubCategoryOptionSelect;

  auditMainCategoryConst = Constants.webAuditMainCategory;
  auditSubCategoryConst = Constants.webAuditSubCategory;

  auditMap: any = {};
  subCategoryList: any[] = [];

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy'
  };

  tableColumns = ['User Name', 'User First Name', 'User Last Name', 'Audit Main Category', 'Audit Sub Category', 'Audited Date']

  pageSize = new PageSize();
  uniquePageName = 'AuditComponent-#343rta';
  searchForm: FormGroup;
  auditDetails: any = [];
  onSelectAuditDetailChangeSub: Subscription = new Subscription();
  onSearchFormValueChange: Subscription = new Subscription();
  onMainCategoryChangeSub: Subscription = new Subscription();

  constructor(
    private auditService: AuditService,
    private searchDataCacheService: SearchDataCacheService,
    private router: Router,
    private formBuilder: FormBuilder,
    private mdbModalService: MDBModalService
  ) {
  }

  ngOnInit() {

    this.auditMap['LEAD'] = Constants.leadSubCategotyoptionSelect;
    this.auditMap['FACILITY_PAPER'] = Constants.facilityPaperSubCategorySelect;
    this.auditMap['SUPPORTING_DOC'] = Constants.supportingDocSubCategorySelect;
    this.auditMap['CREDIT_FACILITY_GROUP'] = Constants.creditFacilityGroupSubCategorySelect;
    this.auditMap['USER_DA'] = Constants.userDaSubCategorySelect;
    this.auditMap['CREDIT_FACILITY_TEMPLATE'] = Constants.creditFacilityTemplateSubCategorySelect;
    this.auditMap['UPM_GROUP'] = Constants.upmGroupSubCategorySelect;
    this.auditMap['WORK_FLOW_TEMPLATE'] = Constants.workFlowSubCategorySelect;
    this.auditMap['UPC_SECTION'] = Constants.upcSectionSubCategorySelect;
    this.auditMap['UPC_TEMPLATE'] = Constants.upcTemplateSubCategorySelect;
    this.auditMap['CFT_INTEREST_RATE'] = Constants.cftInterestRateSubCategorySelect;
    this.auditMap['CFT_SUPPORTING_DOC'] = Constants.cftSupportingDocSubCategorySelect;
    this.auditMap['FACILITY'] = Constants.facilitySubCategorySelect;

    this.onSelectAuditDetailChangeSub = this.auditService.onSelectAuditChange
      .subscribe(data => {
        this.auditDetails = this.auditService.auditDetails;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      auditMainCategoryList: [],
      auditSubCategoryList: [],
      userName: [''],
      userFirstName: [''],
      userLastName: [''],
      auditFromDateStr: [''],
      auditToDateStr: ['']
    });

    this.onMainCategoryChangeSub = this.searchForm.controls.auditMainCategoryList.valueChanges
      .subscribe((values: any) => {
        if (this.subCategoryList.length > 0) {
          while (this.subCategoryList.length > 0) {
            this.subCategoryList.pop();
          }
        }
        this.subCategoryList = [];
        _.forEach(values, value => {
          _.forEach(this.auditMap[value], item => {
            this.subCategoryList.push(item);
          })

        })
      });

    this.onSearchFormValueChange = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.auditService.searchAuditDetails(this.getSearchData(), new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        }))
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;

      this.auditService.searchAuditDetails(data.searchData, new Pagination({
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
    this.onSearchFormValueChange.unsubscribe();
    this.onSelectAuditDetailChangeSub.unsubscribe();
    this.onMainCategoryChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }


  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.auditService.searchAuditDetails(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      auditMainCategoryList: [],
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
