import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {FacilityPaperReviewTemplateService} from "../../services/facility-paper-review-template.service";
import {Constants} from "../../../../../core/setting/constants";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-facility-paper-view-template',
  templateUrl: './facility-paper-view-template.component.html',
  styleUrls: ['./facility-paper-view-template.component.scss']
})
export class FacilityPaperViewTemplateComponent implements OnInit, OnDestroy {

  uniquePageName = 'FacilityPaperViewTemplateComponent-#feTbtsdw';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_REVIEW)
  selectedFacilityPaperIDForReview;

  facilityStatusConst = Constants.facilityPaperStatusConst;
  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  selectedTabIndex: any = 0;
  facilityPaper: any = {};
  onFacilityPaperChangeSubs = new Subscription();
  onFPFacilitiesChangeSub = new Subscription();


  onCustomerListChangeSub = new Subscription();
  casCustomerList: any = [];
  primaryCustomer;
  primaryCustomerID;

  onRemarkListChangeSub: Subscription = new Subscription();
  resizedFPCommentList: any[] = [];

  onCreditRiskCommentListChange = new Subscription();
  riskCommentList: any[] = [];

  constructor(private facilityPaperReviewTemplateService: FacilityPaperReviewTemplateService) {

  }

  ngOnInit() {

    this.onFacilityPaperChangeSubs = this.facilityPaperReviewTemplateService.onFacilityPaperChange.subscribe(
      response => {
        this.facilityPaper = response;
        this.facilityPaper.casCustomerDTOList.forEach(
          data => {
            if (data.isPrimary == true) {
              this.primaryCustomer = data;
            }
          }
        )
      }
    );

    this.onCreditRiskCommentListChange = this.facilityPaperReviewTemplateService.onCreditRiskCommentListChange
      .subscribe((data: any) => {
        if (data) {
          this.riskCommentList = [];
          this.riskCommentList = data.fpCreditRiskCommentList;
        }
      });

    this.onRemarkListChangeSub = this.facilityPaperReviewTemplateService.onRemarkDtoListChange
      .subscribe((data: any) => {
        this.resizedFPCommentList = [];
        this.resizedFPCommentList = data;
      });

  }

  getColour(facilityStatus) {
    switch (facilityStatus) {
      case this.facilityStatusConst.DRAFT:
        return {color: '#ffbb33a6'};
      case this.facilityStatusConst.IN_PROGRESS:
        return {color: '#0099cc94'};
      case this.facilityStatusConst.APPROVED:
        return {color: '#007e338a'};
      case this.facilityStatusConst.CANCEL:
        return {color: '#cc000073'};
      case this.facilityStatusConst.REJECTED:
        return {color: '#cc0000a6'};
    }
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  ngOnDestroy(): void {
    this.onFacilityPaperChangeSubs.unsubscribe();
    this.onFPFacilitiesChangeSub.unsubscribe();
    this.onCustomerListChangeSub.unsubscribe();
    this.onCreditRiskCommentListChange.unsubscribe();
    this.onRemarkListChangeSub.unsubscribe();
  }

}
