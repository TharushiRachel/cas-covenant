import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {FacilitySummeryReviewComponent} from "./components/facility-summery-review/facility-summery-review.component";
import {FacilityPaperReviewService} from "./services/facility-paper-review.service";
import {FacilityPaperReviewWrapperComponent} from "./components/facility-paper-review-wrapper/facility-paper-review-wrapper.component";
import {FacilityPaperViewService} from "./services/facility-paper-view.service";
import {FacilityPaperViewTemplateComponent} from "./components/facility-paper-view-template/facility-paper-view-template.component";
import {FacilityPaperReviewTemplateService} from "./services/facility-paper-review-template.service";
import {PaperReviewSummaryWrapperComponent} from "./components/paper-review-summary-wrapper/paper-review-summary-wrapper.component";
import {OwnApprovedPaperSummaryComponent} from "./components/paper-review-summary-wrapper/own-approved-paper-review-summary/own-approved-paper-summary.component/own-approved-paper-summary.component";

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: FacilityPaperReviewService
    },
    component: PaperReviewSummaryWrapperComponent
  },

  {
    path: 'summary',
  /*  resolve: {
      data: FacilityPaperReviewService
    },*/
    component: FacilitySummeryReviewComponent
  },

  {
    path: 'paper-review',
    resolve: {
      data: FacilityPaperViewService
    },
    component: FacilityPaperReviewWrapperComponent
  },

  {
    path: 'paper-template',
    resolve: {
      data: FacilityPaperReviewTemplateService
    },
    component: FacilityPaperViewTemplateComponent
  },

  {
    path: 'own-approved-papers',
    component: OwnApprovedPaperSummaryComponent
  },



];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacilityPaperReviewRoutingModule {
}
