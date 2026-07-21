import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Constants} from "../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../shared/app.utils";
import * as _ from "lodash";
import {isEmpty} from "lodash";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageSize} from "../../../../../../core/dto/page.size";
import {ApplicationFormTransferService} from "../../services/application-form-transfer.service";
import {Subscription} from "rxjs";
import {DataService} from "../../../../../../core/service/data/data.service";
import {ApplicationService} from "../../../../../../core/service/application/application.service";
import {SearchDataCacheService} from "../../../../../../core/service/common/search-data-cache.service";

@Component({
  selector: 'app-application-form-transfer-search',
  templateUrl: './application-form-transfer-search.component.html',
  styleUrls: ['./application-form-transfer-search.component.scss']
})
export class ApplicationFormTransferSearchComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  onSelectApplicationFormChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();
  searchForm: FormGroup;
  pageSize = new PageSize();
  selectedApplicationForms;
  allBankOptions: any = {};

  constructor(private urlEncodeService: UrlEncodeService,
              private router: Router,
              private cacheService: CacheService,
              private formBuilder: FormBuilder,
              private applicationFormTransferService: ApplicationFormTransferService,
              private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {

      this.onSelectApplicationFormChangeSub = this.applicationFormTransferService.onSelectedApplicationFormChange
        .subscribe(data => {
         if (data) {
            this.selectedApplicationForms = data;
          }

        });


     this.searchForm = this.formBuilder.group({
          afRefNumber: [''],
          loginUpmAccessLevel: [this.applicationService.getLoggedInUserUPMGroupCode()],
          loggedInUserBranchCode: [this.applicationService.getLoggedInUserDivCode()],
        });


  }

  loadApplicationForm(applicationFormID) {
    this.selectedApplicationFormID = this.urlEncodeService.encode(applicationFormID);
    this.router.navigate(['/application-form-transfer/transfer-paper']);
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!_.isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  getAssignedUser(applicationForm) {
    return applicationForm.assignUserDisplayName ? applicationForm.assignUserDisplayName : this.getBranchName(applicationForm.assignDepartmentCode);
  }

  getCustomerName(paper) {
    return paper.customerName ? paper.customerName : paper.nameWithInitials ? paper.nameWithInitials : paper.nameOfBusiness || '-'
  }

  getCustomerNameTooltip(paper) {
    return paper.identificationNumber ? paper.identificationType + ' : ' + paper.identificationNumber : ''
  }

  clearSearch() {
    this.searchForm.reset({
      afRefNumber: '',
    }, {onlySelf: false, emitEvent: true});

    this.selectedApplicationForms = {};
  }

  ngOnDestroy(): void {
    this.clearSearch();
    this.onSelectApplicationFormChangeSub.unsubscribe();
    }

 getSearchData() {
    return this.searchForm.getRawValue();
  }

  search() {
   this.selectedApplicationForms = null;
    let searchData = Object.assign({}, AppUtils.trim(this.searchForm.getRawValue()),
      {
        loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
        loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode()
      }
    );
    this.applicationFormTransferService.searchApplicationForms(AppUtils.trim(searchData));
    /*this.onSelectApplicationFormChangeSub = this.applicationFormTransferService.onApplicationFormTransferChange
          .subscribe(data => {
            this.selectedApplicationForms = data;
          });*/



  }

  isNotEmpty(data) {
    return !isEmpty(data);
  }

}
