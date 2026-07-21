import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PageSize} from "../../../../../core/dto/page.size";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import { IMyOptions } from 'ng-uikit-pro-standard';
import { MyLeadCountService } from 'src/app/core/service/leed/my-lead-count.service';
import { LeadSearchService } from '../../services/lead-search.service';

@Component({
  selector: 'app-lead-search',
  templateUrl: './lead-search.component.html',
  styleUrls: ['./lead-search.component.scss']
})
export class LeadSearchComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  uniquePageName = 'MyBranchLeadsComponent-#343rta';

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: 1900,
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  pageSize = new PageSize();

  searchForm: FormGroup;

  searchData: any = {};
  leadSearchData = [];

  leadStatusConst = Constants.leadStatusConst;
  leadStatus = Constants.leadStatus;
  leadCreationType = Constants.leadCreationTypeConst;
  leadCreationTypeLabel = Constants.leadCreationType;
  tableColumns: any = ['Lead Name' , 'Ref Number', 'Account Number', 'NIC or BR Number',
  'Branch Name' , 'Branch Code', 'Assigned User', //'Lead Type',
  'Lead Status' , 'Created Date'];

  optionsSelect = [
    {value: this.leadStatusConst.PENDING, label: this.leadStatus.PENDING},
    {value: this.leadStatusConst.SUBMITTED, label: this.leadStatus.SUBMITTED},
    {value: this.leadStatusConst.APPROVED, label: this.leadStatus.APPROVED},
    {value: this.leadStatusConst.ACCEPTED, label: this.leadStatus.ACCEPTED},
    {value: this.leadStatusConst.RETURNED, label: this.leadStatus.RETURNED},
    {value: this.leadStatusConst.DECLINED, label: this.leadStatus.DECLINED},
    {value: this.leadStatusConst.PAPER_CREATED, label: this.leadStatus.PAPER_CREATED},
  ];

  optionsLeadTypeSelect = [
    {value: this.leadCreationType.PERSONAL, label: this.leadCreationTypeLabel.PERSONAL},
    {value: this.leadCreationType.BUSINESS, label: this.leadCreationTypeLabel.BUSINESS},
    {value: this.leadCreationType.CORPORATE, label: this.leadCreationTypeLabel.CORPORATE},
  ];

  constructor(
    private leadSearchService: LeadSearchService,
    private myLeadCountService: MyLeadCountService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {

    this.searchForm = this.formBuilder.group({
      leadRefNumber: [''],
      identificationNumber: [''],
      accountNumber: [ '',[Validators.pattern('^[0-9]{0,12}$'),Validators.maxLength(12)]],
      mobileNo: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
      leadStatusList: [],
      leadCreationTypeList: [],
      assignedUserID: [],
      branchCode:['',[Validators.required,Validators.pattern('^[0-9]{3}$')]],
    });


  }

  ngOnDestroy(): void {

    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });

  }

  getBranchLeads() {

    this.searchData = Object.assign({},
      {
        isInMyBranchLeadPage: 'Y'
      });

    this.leadSearchService.searchLeads(this.searchData, new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));
  }

  onPageEvent(event) {

    this.pageSize.pageSize = event.pageSize;
    this.leadSearchService.searchLeads(this.getSearchData(), new Pagination(event));
  }

  getSearchData() {

    let data = Object.assign({},
      this.searchForm.getRawValue(),
      {

         isInMyBranchLeadPage: 'Y'
      });

    return data;
  }

  clearSearch() {

    this.searchForm.reset({
      identificationNumber: '',
      accountNumber: '',
      mobileNo: '',
      createdFromDateStr: '',
      leadStatusList: [],
      assignUserID: '',
      branchCode: '',
    }, {onlySelf: false, emitEvent: true})

    this.myDatePickerOptions = {
      dateFormat: 'dd/mm/yyyy',
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
    };
    
    this.leadSearchData = [];

  }

  doSearch() {

    this.leadSearchService.onLeadsChange
    .subscribe(data => {
      this.leadSearchData = this.leadSearchService.leads;
      this.pageSize.length = data.totalNoOfRecords;
      this.pageSize.pageIndex = data.currentPageNo - 1;

    });


    this.leadSearchService.searchLeads(this.getSearchData(), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));

    this.myLeadCountService.getLoggedInUserPendingLeadCount();
    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  isComprehensive(lead: any) {
    return (
      lead !== null &&
      lead.isCompLead !== null &&
      lead.isCompLead === Constants.yesNoConst.Y
    );
  }

  // loadLead(leadID) {
  //   if (leadID == null) {
  //     this.selectedLeadID = null;
  //   } else {
  //     this.selectedLeadID = this.urlEncodeService.encode(leadID);
  //   }
  //   this.router.navigate(['/leads/add-edit']);
  // }

    loadLead(lead: any) {
    if (lead.leadID == null) {
      this.selectedLeadID = null;
    } else {
      this.selectedLeadID = this.urlEncodeService.encode(lead.leadID);
    }
    if (this.isComprehensive(lead)) {
      this.router.navigate(["/leads/comprehensive-create"], {
        queryParams: {
          selectedLeadID: this.urlEncodeService.encode(lead.leadID),
        }, replaceUrl: true
      });
    } else {
      this.router.navigate(["/leads/add-edit"]);
    }
  }

  keyPressNumbers(event) {

    var charCode = (event.which) ? event.which : event.keyCode;
    // Only Numbers 0-9
    if ((charCode < 48 || charCode > 57)) {

      return false;
    } else {
      return true;
    }
  }


}
