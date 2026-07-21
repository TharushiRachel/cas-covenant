import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PageSize} from "../../../../../core/dto/page.size";
import {FacilityPapersService} from "../../services/facility-papers.service";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {MyFacilityPaperCountService} from "../../../../../core/service/facility-paper/my-facility-paper-count.service";
import {AppUtils} from "../../../../../shared/app.utils";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {isEmpty} from "lodash";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import { FacilityPaperSearchService } from '../../services/facility-paper-search.service';
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from '../../services/facility-paper-add-edit.service';
import { CommitteeService } from '../../../committee/service/committee.service';
import { log } from 'console';

@Component({
  selector: 'app-facility-paper-search',
  templateUrl: './facility-paper-search.component.html',
  styleUrls: ['./facility-paper-search.component.scss']
})
export class FacilityPaperSearchComponent implements OnInit, OnDestroy {

  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutigStatus = Constants.facilityRoutingStatus;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityPaperReviewStatusConst = Constants.paperReviewStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;

  @LocalStorage(SETTINGS.STORAGE.FACILITY_PAPER_BY_ID)
  facilityPaperByIDT;

  @LocalStorage(SETTINGS.STORAGE.RISK_DIV_CODE)
  riskDivCode;

  uniquePageName = 'FacilityPapersComponent-#ayer789656';

  tableColumns = ['Customer Name', 'Ref Number', 'Account Number', 'Branch', 'Created Date', 'Assigned User', 'Status'];

  pageSize = new PageSize();

  searchForm: FormGroup;
  selectedFacilityPapers: any = [];

   myDatePickerOptions: IMyOptions = {
      dateFormat: 'dd/mm/yyyy',
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
    };

  //facilityPaperStatus = Constants.facilityPaperStatus;

  allBankOptions: any = {};
  optionsSelect: any = [
    {value: this.facilityPaperStatusConst.IN_PROGRESS, label: this.facilityPaperStatus.IN_PROGRESS},
    {value: this.facilityPaperStatusConst.REJECTED, label: this.facilityPaperStatus.REJECTED},
    {value: this.facilityPaperStatusConst.APPROVED, label: this.facilityPaperStatus.APPROVED},
    {value: this.facilityPaperStatusConst.DRAFT, label: this.facilityPaperStatus.DRAFT},
    // {value: this.facilityPaperStatusConst.PENDING, label: this.facilityPaperStatus.PENDING},
    // {value: this.facilityPaperStatusConst.REMOVED, label: this.facilityPaperStatus.REMOVED},
    {value: this.facilityPaperStatusConst.CANCEL, label: this.facilityPaperStatus.CANCEL},
  ];

  isAgent;

  paperTypeOptions = [
    { value: 'normal', label: 'General Paper' },
    { value: 'committee', label: 'Committee Paper' },
  ];

  committeeTypeOptions : any = [];

  isCommitteePaperSelected = false;
  isCommittee = Constants.yesNo;


  constructor(
    private facilityPaperSearchService: FacilityPaperSearchService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private myFacilityPaperCountService: MyFacilityPaperCountService,
    private cacheService: CacheService,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private committeeService: CommitteeService
  ) {
  }

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      fpRefNumber: [null],
      assignUserDisplayName: [null],
      bankAccountID: [null],
      leadRefNumber: [null],
      customerName: [null],
      facilityPaperStatus: [null],
      createdFromDateStr: [null],
      createdToDateStr: [null],
      branchCode: [null, [Validators.pattern('^[0-9]{3}$')]],
      isCommittee: [null],
      committeeType: [null],
      paperType: [null],
    });

    // Subscribe to paperType value changes
    this.searchForm.get('paperType').valueChanges.subscribe((selectedValue: string) => {
      console.log('Selected Paper Type:', selectedValue);
      this.isCommitteePaperSelected = selectedValue === 'committee';

      const isCommitteeControl = this.searchForm.get('isCommittee');
      if (this.isCommitteePaperSelected) {
        isCommitteeControl.setValue(Constants.yesNoConst.Y); // Set isCommittee to 'Y'
        this.getCommitteeTypes(); // Fetch committee types dynamically
      } else {
        isCommitteeControl.setValue(Constants.yesNoConst.N); // Clear isCommittee value
        this.committeeTypeOptions = []; // Clear committee type options
      }

      const committeeControl = this.searchForm.get('committeeType');
      if (this.isCommitteePaperSelected) {
        committeeControl.setValidators([Validators.required]);
      } else {
        committeeControl.clearValidators();
        committeeControl.setValue(null); // Clear committeeType value
      }
      committeeControl.updateValueAndValidity();
    });
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

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperSearchService.searchFacilityPapers(AppUtils.trim(this.getSearchData()), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      assignUserDisplayName: '',
      bankAccountID: '',
      leadRefNumber: '',
      customerName: '',
      facilityPaperStatus: '',
      createdFromDateStr: '',
      createdToDateStr: '',
      branchCode: '',
    }, {onlySelf: false, emitEvent: true});

    this.myDatePickerOptions = {
      dateFormat: 'dd/mm/yyyy',
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
    }; 

    this.selectedFacilityPapers = [];
  }

  doSearch() {

    this.facilityPaperSearchService.onselectedFacilityPaperChange
    .subscribe(data => {
      this.selectedFacilityPapers = this.facilityPaperSearchService.selectedFacilityPapers;
      this.pageSize.length = data.totalNoOfRecords;
      this.pageSize.pageIndex = data.currentPageNo - 1;

      //this.myFacilityPaperCountService.getLoggedUserFacilityPaperCount();
    });

    this.facilityPaperSearchService.searchFacilityPapers(AppUtils.trim(this.getSearchData()), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));

    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  loadFacilityPaper(facilityPaperID) {
    this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaperID);
    // this.facilityPaperAddEditService.getFacilityPaperByIDT().then((data1: any) =>{
    //   // this.facilityPaperByIDT = this.urlEncodeService.encode(data1);
    //   this.facilityPaperByIDT = data1;
    // });

    // this.facilityPaperAddEditService.getRiskDivCode().then((data2: any) =>{
    //   // this.riskDivCode = this.urlEncodeService.encode(data2);
    //   this.riskDivCode = data2;
    // });
    this.router.navigate(['/facility-paper/edit']);
  }

  getColor(facilityStatus) {
    switch (facilityStatus) {
      // case this.facilityPaperStatusConst.REMOVED:
      //   return 'purple';
      case this.facilityPaperStatusConst.IN_PROGRESS:
        return 'light-blue';
      // case this.facilityPaperStatusConst.PENDING:
      //   return 'teal';
      case this.facilityPaperStatusConst.APPROVED:
        return 'pink';
      case this.facilityPaperStatusConst.CANCEL:
        return 'orange';
      case this.facilityPaperStatusConst.REJECTED:
        return 'amber';
      case this.facilityPaperStatusConst.DRAFT:
        return 'mdb-color';
      default:
        return 'cyan';
    }
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
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

  isSearchDataEntered(): boolean {

    if ((this.searchForm.value.assignUserDisplayName) || (this.searchForm.value.bankAccountID) ||
        (this.searchForm.value.branchCode) || (this.searchForm.value.createdFromDateStr) ||
        (this.searchForm.value.createdToDateStr) || (this.searchForm.value.customerName)||
        (this.searchForm.value.facilityPaperStatus) || (this.searchForm.value.fpRefNumber) ||
        (this.searchForm.value.leadRefNumber) || (this.searchForm.value.paperType) || (this.searchForm.value.isCommittee))  {

      return false
    } else {

      return true
    }

  }

  onPaperTypeChange(selectedValue: string) {
    console.log('Selected paper type:', selectedValue);

    this.isCommitteePaperSelected = selectedValue === 'committee';

    // Update the isCommittee form control value
    const isCommitteeControl = this.searchForm.get('isCommittee');
    if (this.isCommitteePaperSelected) {
      isCommitteeControl.setValue('Y'); // Set isCommittee to 'Y'

      // Fetch committee types dynamically
      this.getCommitteeTypes();
    } else {
      isCommitteeControl.setValue(''); // Clear isCommittee value
      this.committeeTypeOptions = []; // Clear committee type options
    }

    // Update committeeType validators
    const committeeControl = this.searchForm.get('committeeType');
    if (this.isCommitteePaperSelected) {
      committeeControl.setValidators([Validators.required]);
    } else {
      committeeControl.clearValidators();
      committeeControl.setValue('');
    }
    committeeControl.updateValueAndValidity();
  }
  
  getCommitteeTypes() {
    this.committeeService.getCommitteeType().then((data: any) => {
      console.log('Fetched Committee Types:', data);

      // Map the fetched data to dropdown options
      this.committeeTypeOptions = data.map(item => ({
        value: item.committeeTypeName,
        label: item.committeeTypeName,
      }));
      console.log('Committee Type Options:', this.committeeTypeOptions);
    }).catch(error => {
      console.error('Error fetching committee types:', error);
    });
  }
}
