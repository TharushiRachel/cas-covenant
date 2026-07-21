import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PageSize} from "../../../../../../core/dto/page.size";
import {Pagination} from "../../../../../../core/dto/pagination";
import {ApplicationFromCopyService} from "../../services/application-from-copy.service";
import {Constants} from "../../../../../../core/setting/constants";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {AppUtils} from "../../../../../../shared/app.utils";
import {Router} from "@angular/router";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import {isEmpty} from "lodash";
import {CacheSearchData, SearchDataCacheService} from "../../../../../../core/service/common/search-data-cache.service";
import {Subscription} from "rxjs";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormCopyDialogComponent} from "../../../../../../shared/components/application-form-copy-dialog/application-form-copy-dialog.component";
import {ApplicationService} from "../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-application-form-copy-base',
  templateUrl: './application-form-copy-base.component.html',
  styleUrls: ['./application-form-copy-base.component.scss']
})
export class ApplicationFormCopyBaseComponent implements OnInit, OnDestroy {

  uniquePageName = 'ApplicationFormCopyBaseComponent-#aardw';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  modalRef: MDBModalRef;
  searchForm: FormGroup;
  pageSize = new PageSize();
  applicationForms: any = [];
  response: any;
  allBankOptions: any = {};
  onSelectApplicationFormChangeSub = new Subscription();
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  customerIdentificationType = Constants.customerIdentificationType;
  identityOptionSelect = Constants.customerCribIdentificationTypeOptionsSelect;
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;

  optionsSelect: any = [
    {value: this.applicationFormCurrentStatusConst.IN_PROGRESS, label: this.applicationFormStatus.IN_PROGRESS},
    {value: this.applicationFormCurrentStatusConst.DECLINED, label: this.applicationFormStatus.DECLINED},
    {value: this.applicationFormCurrentStatusConst.DRAFT, label: this.applicationFormStatus.DRAFT},
    {value: this.applicationFormCurrentStatusConst.RETURNED, label: this.applicationFormStatus.RETURNED},
  ];

  constructor(private applicationFromCopyService: ApplicationFromCopyService,
              private applicationService: ApplicationService,
              private formBuilder: FormBuilder,
              private urlEncodeService: UrlEncodeService,
              private router: Router,
              private cacheService: CacheService,
              private mdbModalService: MDBModalService,
              private searchDataCacheService: SearchDataCacheService,) {
  }

  ngOnInit() {

    this.onSelectApplicationFormChangeSub = this.applicationFromCopyService.onApplicationFormsChange
      .subscribe(data => {
        this.response = data;
        this.applicationForms = data.pageData ? data.pageData : [];
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    this.searchForm = this.formBuilder.group({
      afRefNumber: ['', [Validators.required]],
      identificationType: [this.customerIdentificationType.NIC, [Validators.required]],
      identificationNumber: ['', [Validators.required]],
    });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(this.uniquePageName);
      this.searchForm.setValue(data.searchData, {onlySelf: true, emitEvent: false});
      this.pageSize = data.pageSize;
      this.applicationFromCopyService.searchApplicationForms(data.searchData, new Pagination({
        pageSize: data.pageSize.pageSize,
        pageIndex: data.pageSize.pageIndex
      }))
    }
  }

  doSearch() {
    this.applicationFromCopyService.searchApplicationForms(this.getSearchData(), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      identificationType: this.customerIdentificationType.NIC,
      identificationNumber: ''
    }, {onlySelf: false, emitEvent: true});
    this.applicationFromCopyService.onApplicationFormsChange.next({});
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.applicationFromCopyService.searchApplicationForms(this.getSearchData(), new Pagination(event));
  }

  loadApplicationForm(applicationFormID) {
    this.selectedApplicationFormID = this.urlEncodeService.encode(applicationFormID);
    this.router.navigate(['/application-form/add-edit']);
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  ngOnDestroy(): void {
    this.onSelectApplicationFormChangeSub.unsubscribe();
  }

  copy($event, applicationForm) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApplicationFormCopyDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-margin-center modal-width-40-p',
      containerClass: '',
      animated: true,
      data: {
        applicationForm: applicationForm,
        heading: "Copy Application Form From - ",
        message: "Do you want to copy this Application Form ?",
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let applicationFormReplicateRQ = {
          ...applicationForm,
          originalApplicationFormID: applicationForm.applicationFormID,
          workflowTemplateID: data.workflowTemplateID,
          branchCode: data.branchCode,
          assignUserID: this.applicationService.getLoggedInUserUserID(),
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          assignADUserID: this.applicationService.getLoggedInUserUserName(),
          assignUser: this.applicationService.getLoggedInUserUserName(),
          assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          assignUserUpmID: this.applicationService.getLoggedInUserUserID(),
          upmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          applicationFormStatus: this.applicationFormStatusConst.DRAFT,
        };
        this.applicationFromCopyService.replicateApplicationForm(AppUtils.trim(applicationFormReplicateRQ)).subscribe((response: any) => {
          this.selectedApplicationFormID = this.urlEncodeService.encode(response.applicationFormID);
          this.router.navigate(['/application-form/add-edit']);
        }, (error) => {
          console.log(error);
        });
      }
    });
  }

  noRecords() {
    return (this.searchForm.getRawValue().afRefNumber || this.searchForm.getRawValue().identificationNumber) && this.response && this.response.pageData && this.response.pageData.length == 0;
  }

  isValid() {
    if (this.searchForm.get('afRefNumber').valid || this.searchForm.get('identificationNumber').valid) {
      return true;
    } else {
      return false;
    }
  }

}
