import {Component, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../core/setting/constants";
import {PageSize} from "../../../../../../../core/dto/page.size";
import {Pagination} from "../../../../../../../core/dto/pagination";
import {CustomerBaseService} from "../../../../services/customer-base.service";
import {Subscription} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lead-history-list',
  templateUrl: './lead-history-list.component.html',
  styleUrls: ['./lead-history-list.component.scss']
})
export class LeadHistoryListComponent implements OnInit, OnDestroy {

  tableColumns: any = ['Lead Name', 'Reference Name',
    'Identification Number', 'Account Number', 'Mobile Number', 'Preferred Branch', 'Created Date', 'Lead Status', 'Assigned User'];
  tableDataList: any = [];

  pageSize = new PageSize();
  customer360Details: any = {};

  onCustomerPagedLeadDTOChangeSub: Subscription = new Subscription();
  onCustomer360DetailsChangeSubs: Subscription = new Subscription();

  leadStatusConst = Constants.leadStatusConst;
  leadStatus = Constants.leadStatus;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  constructor(private customerBaseService: CustomerBaseService,
              private urlEncodeService: UrlEncodeService,
              private router: Router) {
  }

  ngOnInit() {

    this.onCustomerPagedLeadDTOChangeSub = this.customerBaseService.onCustomerPagedLeadDTOChange
      .subscribe(data => {
        this.tableDataList = this.customerBaseService.customerPagedLeadDTOList;
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
    this.onCustomer360DetailsChangeSubs.unsubscribe();
    this.onCustomerPagedLeadDTOChangeSub.unsubscribe();
  }

  onPageEvent(event) {
    let data = {
      customerID: this.customer360Details.customerID
    };
    this.pageSize.pageSize = event.pageSize;
    this.customerBaseService.getCustomerPagedLeadDTO(data, new Pagination(event));
  }

  loadLead(leadID) {
    if (leadID == null) {
      this.selectedLeadID = null;
    } else {
      this.selectedLeadID = this.urlEncodeService.encode(leadID);
    }

    this.router.navigate(['/leads/add-edit']);
  }
}
