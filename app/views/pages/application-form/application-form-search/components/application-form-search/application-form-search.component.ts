import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LocalStorage } from 'ngx-webstorage';
import { Subscription } from 'rxjs';
import { PageSize } from 'src/app/core/dto/page.size';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { CacheSearchData, SearchDataCacheService } from 'src/app/core/service/common/search-data-cache.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { Router } from '@angular/router';
import { Pagination } from 'src/app/core/dto/pagination';
import { AppUtils } from 'src/app/shared/app.utils';
import { isEmpty } from 'lodash';
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import { ApplicationFormSearchService } from '../../services/application-form-search.service';

@Component({
  selector: 'app-application-form-search',
  templateUrl: './application-form-search.component.html',
  styleUrls: ['./application-form-search.component.scss']
})
export class ApplicationFormSearchComponent implements OnInit, OnDestroy {

  uniquePageName = 'BranchApplicationFormComponent-#aardw';
  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  searchForm: FormGroup;
  pageSize = new PageSize();

  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationFormData: any = [];
  allBankOptions: any = {};

   myDatePickerOptions: IMyOptions = {
      dateFormat: 'dd/mm/yyyy',
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
    };

  optionsSelect: any = [
    {value: this.applicationFormCurrentStatusConst.IN_PROGRESS, label: this.applicationFormStatus.IN_PROGRESS},
    {value: this.applicationFormCurrentStatusConst.DECLINED, label: this.applicationFormStatus.DECLINED},
    {value: this.applicationFormCurrentStatusConst.DRAFT, label: this.applicationFormStatus.DRAFT},
    {value: this.applicationFormCurrentStatusConst.RETURNED, label: this.applicationFormStatus.RETURNED},
  ];

  constructor(private applicationFormSearchService: ApplicationFormSearchService,
              private searchDataCacheService: SearchDataCacheService,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              private urlEncodeService: UrlEncodeService,
              private router: Router,) {
  }

  ngOnInit() {

    this.searchForm = this.formBuilder.group({
      afRefNumber: ['',Validators.required],
      assignUserDisplayName: [''],
      identificationNumber: '',
      currentApplicationFormStatus: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
    });

  }

  doSearch() {

    this.applicationFormSearchService.onSearchApplicationFormChange
    .subscribe(data => {
      this.applicationFormData = this.applicationFormSearchService.applicationForms;
      this.pageSize.length = data.totalNoOfRecords;
      this.pageSize.pageIndex = data.currentPageNo - 1;
    });

    this.applicationFormSearchService.searchApplicationForms(this.getSearchData(), new Pagination({
            pageSize: this.pageSize.pageSize,
            pageIndex: 0
          }));

    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});

  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      assignUserDisplayName: '',
      identificationNumber:'',
      currentApplicationFormStatus: '',
      createdFromDateStr: '',
      createdToDateStr: '',
    }, {onlySelf: false, emitEvent: true});

    this.myDatePickerOptions = {
      dateFormat: 'dd/mm/yyyy',
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
    };

    this.applicationFormData = [];
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()

    });



  }

  getSearchData() {
    return this.searchForm.getRawValue();
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

  getAssignedUser(applicationForm) {
    return applicationForm.assignUserDisplayName ? applicationForm.assignUserDisplayName : this.getBranchName(applicationForm.assignDepartmentCode);
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.applicationFormSearchService.searchApplicationForms(this.getSearchData(), new Pagination(event));
  }

  getCustomerName(paper) {
    return paper.customerName ? paper.customerName : paper.nameWithInitials ? paper.nameWithInitials : paper.nameOfBusiness || '-'
  }

  getCustomerNameTooltip(paper) {
    return paper.identificationNumber ? paper.identificationType + ' : ' + paper.identificationNumber : ''
  }


  isSearchDataEntered(): boolean {

    if ((this.searchForm.value.afRefNumber) || (this.searchForm.value.assignUserDisplayName) ||
        (this.searchForm.value.createdFromDateStr) || (this.searchForm.value.createdToDateStr) ||
        (this.searchForm.value.currentApplicationFormStatus) || (this.searchForm.value.identificationNumber))  {

      return false
    } else {
      return true
    }

  }

}
