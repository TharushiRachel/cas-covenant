import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PreviewFacilitiesComponent} from './preview-facilities/preview-facilities.component';
import {PreviewFacilityDataComponent} from './preview-facilities/preview-facility-data/preview-facility-data.component';
import {SharedModule} from "../../shared/shared.module";
import {PreviewCommonSecuritiesComponent} from './preview-common-securities/preview-common-securities.component';
import {PreviewFacilityExposureDataComponent} from './preview-facility-exposure-data/preview-facility-exposure-data.component';
import {PreviewUpcDocumentComponent} from './preview-upc-document/preview-upc-document.component';
import {PreviewUpcTemplateStructureComponent} from './preview-upc-document/preview-upc-template-structure/preview-upc-template-structure.component';
import {PreviewUpcSectionNodeComponent} from './preview-upc-document/preview-upc-section-node/preview-upc-section-node.component';
import {PreviewCommentsComponent} from './preview-comments/preview-comments.component';
import {PreviewSecuritySummaryComponent} from './preview-security-summary/preview-security-summary.component';
import {PreviewThreeColFacilityDataComponent} from './three-column/preview-three-col-facility-data/preview-three-col-facility-data.component';
import {PreviewFpAboutComponent} from './preview-fp-about/preview-fp-about.component';
import {CacheService} from "../../core/service/data/cache.service";
import {PreviewThreeColFacilityExposureDataComponent} from './three-column/preview-three-col-facility-exposure-data/preview-three-col-facility-exposure-data.component';
import {PersonalDetailBasicComponent} from "./personal-detail-basic/personal-detail-basic.component";
import {PreviewCustomerCribDetailComponent} from './preview-customer-crib-detail/preview-customer-crib-detail.component';
import {PersonalCustomerStatWrapperComponentBkp} from "./personal-detail-basic/personal-customer-stat-wrapper-bkp/personal-customer-stat-wrapper.component";
import {CustomerStatOutstandingComponentBkp} from "./personal-detail-basic/personal-customer-stat-wrapper-bkp/components/customer-stat-outstanding/customer-stat-outstanding.component";
import {CustomerCurrentAccountDetailsComponentBkp} from "./personal-detail-basic/personal-customer-stat-wrapper-bkp/components/customer-current-account-details/customer-current-account-details.component";
import {CustomerDepositsDetailsComponentBkp} from "./personal-detail-basic/personal-customer-stat-wrapper-bkp/components/customer-deposits-details/customer-deposits-details.component";
import {CustomerAdvanceDetailsComponentBkp} from "./personal-detail-basic/personal-customer-stat-wrapper-bkp/components/customer-advance-details/customer-advance-details.component";
import { PreviewRiskCommentsComponent } from './preview-risk-comments/preview-risk-comments.component';
import {PreviewPersonalCustomerRatingsComponent} from './preview-personal-customer-ratings/preview-personal-customer-ratings.component';
import { FacilityPaperAddEditService } from '../pages/facility-paper/services/facility-paper-add-edit.service';
import { PreviewScorecardComponent } from './preview-scorecard/preview-scorecard.component';
import { PreviewCustomerCovenantsComponent } from './preview-customer-covenants/preview-customer-covenants.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { PreviewCommonFacilityCovenantComponent } from './preview-common-facility-covenant/preview-common-facility-covenant.component';
import { ReportFacilityChangeModalComponent } from 'src/app/shared/components/report-facility-change-modal/report-facility-change-modal.component';
import { PreviewDirectorDetailsComponent } from './preview-director-details/preview-director-details.component';
import { PreviewExistingCovenantsComponent } from './preview-existing-covenants/preview-existing-covenants.component';

@NgModule({
  declarations: [
    PreviewFacilitiesComponent,
    PreviewFacilityDataComponent,
    PreviewCommonSecuritiesComponent,
    PreviewFacilityExposureDataComponent,
    PreviewUpcDocumentComponent,
    PreviewUpcTemplateStructureComponent,
    PreviewUpcSectionNodeComponent,
    PreviewCommentsComponent,
    PreviewSecuritySummaryComponent,
    PreviewThreeColFacilityDataComponent,
    PreviewFpAboutComponent,
    PreviewThreeColFacilityExposureDataComponent,
    PersonalDetailBasicComponent,
    PreviewCustomerCribDetailComponent,
    PersonalCustomerStatWrapperComponentBkp,
    CustomerStatOutstandingComponentBkp,
    CustomerCurrentAccountDetailsComponentBkp,
    CustomerDepositsDetailsComponentBkp,
    CustomerAdvanceDetailsComponentBkp,
    PreviewPersonalCustomerRatingsComponent,
    PreviewRiskCommentsComponent,
    PreviewScorecardComponent,
    PreviewCustomerCovenantsComponent,
    PreviewCommonFacilityCovenantComponent,
    ReportFacilityChangeModalComponent,
    PreviewDirectorDetailsComponent,
    PreviewExistingCovenantsComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    MatExpansionModule,
  ],
  exports: [
    CommonModule,
    PreviewFacilitiesComponent,
    PreviewFacilityExposureDataComponent,
    PreviewUpcDocumentComponent,
    PreviewCommentsComponent,
    PreviewSecuritySummaryComponent,
    PreviewCommonSecuritiesComponent,
    PreviewThreeColFacilityDataComponent,
    PreviewFpAboutComponent,
    PersonalDetailBasicComponent,
    PreviewCustomerCribDetailComponent,
    PersonalCustomerStatWrapperComponentBkp,
    CustomerStatOutstandingComponentBkp,
    CustomerCurrentAccountDetailsComponentBkp,
    CustomerDepositsDetailsComponentBkp,
    CustomerAdvanceDetailsComponentBkp,
    PreviewPersonalCustomerRatingsComponent,
    PreviewThreeColFacilityExposureDataComponent,
    PreviewRiskCommentsComponent,
    PreviewScorecardComponent, 
    PreviewCustomerCovenantsComponent,
    PreviewCommonFacilityCovenantComponent,
    PreviewDirectorDetailsComponent,
    PreviewExistingCovenantsComponent
  ],
  providers: [
    CacheService,
    FacilityPaperAddEditService
  ],
  entryComponents: [ReportFacilityChangeModalComponent]
})
export class PreviewModule {
}
