import {NgModule} from '@angular/core';
import {BccReportingRoutingModule} from "./bcc-reporting-routing.module";
import {BccReportingService} from "./services/bcc-reporting.service";
import {SharedModule} from "../../../shared/shared.module";
import {BccFacilityPapersComponent} from './components/bcc-facility-papers/bcc-facility-papers.component';
import {BccFacilityPaperWrapperComponent} from './components/bcc-facility-paper-wrapper/bcc-facility-paper-wrapper.component';
import {BccFacilityPaperService} from "./services/bcc-facility-paper.service";
import {NgxSortableModule} from "ngx-sortable";
import {BankCommitteePaperComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/bank-committee-paper.component";
import {BccBasicInfoComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-basic-info/bcc-basic-info.component";
import {BccDirectorDetailsComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-director-details/bcc-director-details.component";
import {BccStrengthsComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-strengths/bcc-strengths.component";
import {BccProposedFacilitiesComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-proposed-facilities/bcc-proposed-facilities.component";
import {BccExistingFacilitiesComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-existing-facilities/bcc-existing-facilities.component";
import {BccExposureTableComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-exposure-table/bcc-exposure-table.component";
import {BccCostOfFundsComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-cost-of-funds/bcc-cost-of-funds.component";
import {BccRecommendedOptionsComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-recommended-options/bcc-recommended-options.component";
import {BccJustificationComponent} from "./components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-justification/bcc-justification.component";
import {BccRiskRatingYearComponent} from './components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-risk-rating-year/bcc-risk-rating-year.component';
import {BccOfficerApprovalComponent} from './components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-officer-approval/bcc-officer-approval.component';
import {ViewBccPdfComponent} from './components/bcc-facility-paper-wrapper/bank-committee-paper/pdfPrintComponents/view-bcc-pdf/view-bcc-pdf.component';
import {BccFacilityCommonSecurityComponent} from './components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-facility-common-security/bcc-facility-common-security.component';
import {BccCribDetailsComponent} from './components/bcc-facility-paper-wrapper/bank-committee-paper/components/bcc-crib-details/bcc-crib-details.component';
import {MDBBootstrapModulesPro, MDBModalRef} from "ng-uikit-pro-standard";
import {BccUpdateContentComponent} from './components/supporting-components/bcc-update-content/bcc-update-content.component';
import { BccFacilityPaperSearchComponent } from './components/bcc-facility-paper-search/bcc-facility-paper-search.component';
import { BccFacilityPaperDraftComponent } from './components/bcc-facility-paper-draft/bcc-facility-paper-draft.component';
import { AddComparableStatementComponent } from './components/supporting-components/bcc-update-content/add-comparable-statement/add-comparable-statement.component';
import { LoanCompStatementComponent } from './components/supporting-components/bcc-update-content/add-comparable-statement/loan-comp-statement/loan-comp-statement.component';
import { CommissionCompStatementComponent } from './components/supporting-components/bcc-update-content/add-comparable-statement/commission-comp-statement/commission-comp-statement.component';
import { PreviewCompStatementComponent } from './components/supporting-components/bcc-update-content/add-comparable-statement/preview-comp-statement/preview-comp-statement.component';

import { ScrollingModule } from '@angular/cdk/scrolling';

@NgModule({
  declarations: [
    BccFacilityPapersComponent,
    BccFacilityPaperWrapperComponent,
    BankCommitteePaperComponent,
    BccBasicInfoComponent,
    BccDirectorDetailsComponent,
    BccStrengthsComponent,
    BccProposedFacilitiesComponent,
    BccExistingFacilitiesComponent,
    BccExposureTableComponent,
    BccCostOfFundsComponent,
    BccRecommendedOptionsComponent,
    BccJustificationComponent,
    BccRiskRatingYearComponent,
    BccOfficerApprovalComponent,
    ViewBccPdfComponent,
    BccFacilityCommonSecurityComponent,
    BccCribDetailsComponent,
    BccUpdateContentComponent,
    BccFacilityPaperSearchComponent,
    BccFacilityPaperDraftComponent,
    AddComparableStatementComponent,
    LoanCompStatementComponent,
    CommissionCompStatementComponent,
    PreviewCompStatementComponent,
  ],
  imports: [
    SharedModule,
    BccReportingRoutingModule,
    NgxSortableModule,
    ScrollingModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  entryComponents: [
    BccUpdateContentComponent,
    AddComparableStatementComponent
  ],
  providers: [
    BccReportingService,
    BccFacilityPaperService,
    MDBModalRef,
  ],
})
export class BccReportingModule {
}
