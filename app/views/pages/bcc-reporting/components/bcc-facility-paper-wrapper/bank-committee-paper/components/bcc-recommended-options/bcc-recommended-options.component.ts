import {Component, Input, OnDestroy, OnInit, SimpleChange} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import * as _ from "lodash";
import {BccReportingService} from "../../../../../services/bcc-reporting.service";

@Component({
  selector: 'app-bcc-recommended-options',
  templateUrl: './bcc-recommended-options.component.html',
  styleUrls: ['./bcc-recommended-options.component.scss']
})
export class BccRecommendedOptionsComponent implements OnInit, OnDestroy {

  @Input('recommendedOptions') recommendedOptions: FormGroup;
  bccRecommendedOptionsChangeSub = new Subscription();
  BCCPaperRecommendationConst = Constants.BCCPaperRecommendationConst;
  showRecommendationRemark = false;
  onBCCPaperChangeSub = new Subscription();

  constructor(private bccReportingService: BccReportingService,) {
  }

  ngOnInit() {
    this.updateFormSubscription();

    this.onBCCPaperChangeSub = this.bccReportingService.onBCCPaperChange
      .subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          if (data.recommendation != this.BCCPaperRecommendationConst.NOT_RECOMMENDED_BY_GRO) {
            this.showRecommendationRemark = false;
          } else {
            this.showRecommendationRemark = true;
          }
        }
      });
  }

  ngOnChanges(changes: SimpleChange) {
    if (changes['recommendedOptions']) {
      this.recommendedOptions = changes['recommendedOptions'].currentValue;
      this.updateFormSubscription();
    }
  }

  ngOnDestroy(): void {
    this.bccRecommendedOptionsChangeSub.unsubscribe();
  }

  updateFormSubscription() {
    this.bccRecommendedOptionsChangeSub.unsubscribe();
    this.bccRecommendedOptionsChangeSub = this.recommendedOptions.valueChanges.subscribe((res: any) => {
      if (res.recommendation == this.BCCPaperRecommendationConst.NOT_RECOMMENDED_BY_GRO) {
        this.showRecommendationRemark = true;
      } else {
        this.showRecommendationRemark = false;
      }
    });
  }

}
