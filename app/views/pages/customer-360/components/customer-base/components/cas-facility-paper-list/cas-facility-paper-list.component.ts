import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../core/setting/constants";
import {PageSize} from "../../../../../../../core/dto/page.size";
import {Pagination} from "../../../../../../../core/dto/pagination";
import {CustomerBaseService} from "../../../../services/customer-base.service";
import {Subscription} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {AppUtils} from "../../../../../../../shared/app.utils";
import {CacheService} from "../../../../../../../core/service/data/cache.service";
import {isEmpty} from "lodash";

@Component({
  selector: 'app-cas-facility-paper-list',
  templateUrl: './cas-facility-paper-list.component.html',
  styleUrls: ['./cas-facility-paper-list.component.scss']
})
export class CasFacilityPaperListComponent implements OnInit, OnDestroy {

  @Input('customer360Details') customer360Details: any;
  tableDataList = [];
  facilityStatus = Constants.facilityPaperStatus;
  facilityStatusConst_ = Constants.facilityPaperStatusConst;
  tableColumns = ['Ref Code', 'Account Number', 'Branch', 'Created On', 'Assigned User', 'Status'];
  pageSize = new PageSize();
  onPagedFPSummaryChangeSub: Subscription = new Subscription();
  onCustomer360DetailsChangeSubs: Subscription = new Subscription();

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;

  allBankOptions: any = {};

  constructor(private customerBaseService: CustomerBaseService,
              private urlEncodeService: UrlEncodeService,
              private cacheService: CacheService,
              private router: Router) {
  }

  ngOnInit() {

    this.onPagedFPSummaryChangeSub = this.customerBaseService.onPagedFPSummaryChange
      .subscribe(data => {
        this.tableDataList = this.customerBaseService.pagedFacilityPaperSummary;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });


    this.onCustomer360DetailsChangeSubs = this.customerBaseService.onCustomer360DetailsChange
      .subscribe((data: any) => {
        let searchRQ = {
          customerID: data.customerID
        };

        this.customerBaseService.getPagedFacilityPaperSummary(searchRQ, new Pagination({
          pageSize: 10,
          pageIndex: 0
        }));
      });


  }

  ngOnDestroy(): void {
    this.onPagedFPSummaryChangeSub.unsubscribe();
    this.onCustomer360DetailsChangeSubs.unsubscribe();
  }

  loadFacilityPaper(facilityPaperID) {
    this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaperID);
    this.router.navigate(['/facility-paper/edit']);
  }

  onPageEvent(event) {
    let data = {
      customerID: this.customer360Details.customerID
    };
    this.pageSize.pageSize = event.pageSize;
    this.customerBaseService.getPagedFacilityPaperSummary(data, new Pagination(event));
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }
}
