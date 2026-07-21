import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FacilityPaperReviewService} from "../../services/facility-paper-review.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-paper-review-summary-wrapper',
  templateUrl: './paper-review-summary-wrapper.component.html',
  styleUrls: ['./paper-review-summary-wrapper.component.scss']
})
export class PaperReviewSummaryWrapperComponent implements OnInit, OnDestroy, AfterViewInit {

  selectedTabIndex: number;
  onSelectedTabIndexChange = new Subscription();

  constructor(private facilityPaperReviewService: FacilityPaperReviewService) {
  }

  ngOnInit() {

    /* in the code, OwnApprovedFacility paper is reffering to higher level reviews in UI view.
     after the coded those names ate give to view in front end*/

    this.onSelectedTabIndexChange = this.facilityPaperReviewService.onSelectedTabIndexFromPaperReviewSummaryChange.subscribe(res => {
      this.selectedTabIndex = res;
    });
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect(event) {
    this.selectedTabIndex = event;
    this.facilityPaperReviewService.onSelectedTabIndexFromPaperReviewSummaryChange.next(event);
  }

  ngOnDestroy(): void {
    this.onSelectedTabIndexChange.unsubscribe();
  }

  ngAfterViewInit(): void {
    this.onSelectedTabIndexChange = this.facilityPaperReviewService.onSelectedTabIndexFromPaperReviewSummaryChange.subscribe(res => {
      this.selectedTabIndex = res;
    });
  }

}
