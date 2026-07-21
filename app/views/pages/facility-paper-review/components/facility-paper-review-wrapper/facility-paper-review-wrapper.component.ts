import {Component, OnInit} from '@angular/core';
import {FacilityPaperViewService} from "../../services/facility-paper-view.service";
import {FacilityPaperReviewService} from "../../services/facility-paper-review.service";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-facility-paper-review-wrapper',
  templateUrl: './facility-paper-review-wrapper.component.html',
  styleUrls: ['./facility-paper-review-wrapper.component.scss']
})
export class FacilityPaperReviewWrapperComponent implements OnInit {

  facilityPaperReviewDetail: any = {};
  tableColumns: any = [
    'User Name',
    'Total Facility Amount',
    'No of Facilities',
    'No of Days',
    'Average',
  ];

  constructor(private facilityPaperViewService: FacilityPaperViewService,
              private facilityPaperReviewService: FacilityPaperReviewService,
              private currencyPipe: CurrencyPipe,
  ) {
  }

  ngOnInit() {

    this.facilityPaperReviewDetail = this.facilityPaperReviewService.facilityPaperReviewDetail.getValue();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '');
  }

}
