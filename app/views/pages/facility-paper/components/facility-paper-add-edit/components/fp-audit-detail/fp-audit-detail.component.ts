import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../../../core/setting/constants";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";
import {AuditService} from "../../../../../audit/services/audit.service";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {PageSize} from "../../../../../../../core/dto/page.size";
import {Pagination} from "../../../../../../../core/dto/pagination";
import {UrlEncodeService} from "../../../../../../../core/service/application/url-encode.service";
import {AuditContentComponent} from "../../../../../audit/components/audit-content/audit-content.component";

@Component({
  selector: 'app-fp-audit-detail',
  templateUrl: './fp-audit-detail.component.html',
  styleUrls: ['./fp-audit-detail.component.scss']
})
export class FpAuditDetailComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_CUSTOMER_ID)
  selectedFacilityPaperID;
  auditSubCategoryConst = Constants.webAuditSubCategory;

  modalRef: MDBModalRef;
  uniquePageName = 'FpAuditDetailComponent-#343rta';
  pageSize = new PageSize();
  auditList = [];

  tableColumns = ['User Name', 'First Name', 'Last Name', 'Audit Sub Category', 'Audited Date'];
  searchForm: FormGroup;

  onSelectAuditChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();

  constructor(
    private auditService: AuditService,
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private mdbModalService: MDBModalService
  ) {
  }

  ngOnInit() {

    this.onSelectAuditChangeSub = this.auditService.onSelectAuditChange
      .subscribe((data: any) => {
        this.auditList = data.pageData;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
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
    this.onSelectAuditChangeSub.unsubscribe();
  }

  getSearchData() {
    let requestData = Object.assign({},
      this.searchForm.getRawValue(),
      {auditTypeID: this.urlEncodeService.decode(this.selectedFacilityPaperID)},
      {auditMainCategoryList: ['FACILITY_PAPER']}
    );
    return requestData
  }

  onComponentLoad() {
    let data = Object.assign({},
      {auditTypeID: this.urlEncodeService.decode(this.selectedFacilityPaperID)},
      {auditMainCategoryList: ['FACILITY_PAPER']});
    this.auditService.searchAuditDetails(data);
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.auditService.searchAuditDetails(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
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
