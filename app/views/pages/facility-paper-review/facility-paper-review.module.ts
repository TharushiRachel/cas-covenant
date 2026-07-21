import {NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {FacilityPaperReviewRoutingModule} from "./facility-paper-review-routing.module";
import {FacilityPaperReviewService} from "./services/facility-paper-review.service";
import {FacilitySummeryReviewComponent} from './components/facility-summery-review/facility-summery-review.component';
import {FacilityPaperReviewWrapperComponent} from './components/facility-paper-review-wrapper/facility-paper-review-wrapper.component';
import {FacilityPaperViewService} from "./services/facility-paper-view.service";
import {FacilityListComponent} from './components/facility-paper-review-wrapper/facility-list/facility-list.component';
import {FacilityPaperViewTemplateComponent} from './components/facility-paper-view-template/facility-paper-view-template.component';
import {FacilityPaperReviewTemplateService} from "./services/facility-paper-review-template.service";
import {ReviewTemplateUpcDocumentsComponent} from './components/facility-paper-view-template/components-tab/review-template-fp-full-view/components-full-view/review-template-upc-documents/review-template-upc-documents.component';
import {ReviewTemplateFpFullViewComponent} from './components/facility-paper-view-template/components-tab/review-template-fp-full-view/review-template-fp-full-view.component';
import {ReviewSecuritiesTabComponent} from './components/facility-paper-view-template/components-tab/review-securities-tab/review-securities-tab.component';
import {ReviewTemplateAboutComponent} from './components/facility-paper-view-template/components-tab/review-template-fp-full-view/components-full-view/review-template-about/review-template-about.component';
import {ReviewerCommentsTabComponent} from './components/facility-paper-view-template/components-tab/reviewer-comments-tab/reviewer-comments-tab.component';
import {ReviewTemplateUpcTemplateStructureComponent} from './components/facility-paper-view-template/components-tab/review-template-fp-full-view/components-full-view/review-template-upc-documents/review-template-upc-template-structure/review-template-upc-template-structure.component';
import {ReviewTemplateFullNodeHtmlComponent} from './components/facility-paper-view-template/components-tab/review-template-fp-full-view/components-full-view/review-template-upc-documents/review-template-full-node-html/review-template-full-node-html.component';
import {ReviewFacilitySecurityComponent} from './components/facility-paper-view-template/components-tab/review-securities-tab/review-facility-security/review-facility-security.component';
import {PreviewModule} from "../../preview/preview.module";
import {PaperReviewSummaryWrapperComponent} from './components/paper-review-summary-wrapper/paper-review-summary-wrapper.component';
import {OwnApprovedPaperSummaryComponent} from './components/paper-review-summary-wrapper/own-approved-paper-review-summary/own-approved-paper-summary.component/own-approved-paper-summary.component';

@NgModule({
  declarations: [
    FacilitySummeryReviewComponent,
    FacilityPaperReviewWrapperComponent,
    FacilityListComponent,
    FacilityPaperViewTemplateComponent,
    ReviewTemplateUpcDocumentsComponent,
    ReviewTemplateFpFullViewComponent,
    ReviewSecuritiesTabComponent,
    ReviewTemplateAboutComponent,
    ReviewerCommentsTabComponent,
    ReviewTemplateUpcTemplateStructureComponent,
    ReviewTemplateFullNodeHtmlComponent,
    ReviewFacilitySecurityComponent,
    PaperReviewSummaryWrapperComponent,
    OwnApprovedPaperSummaryComponent,
  ],
  imports: [
    PreviewModule,
    SharedModule,
    FacilityPaperReviewRoutingModule
  ],
  providers: [
    FacilityPaperReviewService,
    FacilityPaperViewService,
    FacilityPaperReviewTemplateService
  ],
})

export class FacilityPaperReviewModule {
}


