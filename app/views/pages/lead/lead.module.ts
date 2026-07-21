import { NgModule } from "@angular/core";

import {
  MDBBootstrapModulesPro,
  MDBSpinningPreloader,
} from "ng-uikit-pro-standard";

import { LeadRoutingModule } from "./lead-routing.module";
import { LeadsComponent } from "./components/leads/leads.component";
import { SharedModule } from "../../../shared/shared.module";
import { LeadsService } from "./services/leads.service";
import { LeadAddEditComponent } from "./components/lead-add-edit/lead-add-edit.component";
import { LeadAddEditService } from "./services/lead-add-edit.service";
import { LeadFacilityDetailComponent } from "./components/lead-facility-detail/lead-facility-detail.component";
import { LeadDocumentComponent } from "./components/lead-document/lead-document.component";
import { LeadAuditDetailComponent } from "./components/lead-audit-detail/lead-audit-detail.component";
import { AuditService } from "../audit/services/audit.service";
import { AuditModule } from "../audit/audit.module";
import { LeadCustomerDetailComponent } from "./components/lead-customer-detail/lead-customer-detail.component";
import { CurrencyPipe } from "@angular/common";
import { ExternalLeadApproveComponent } from "./components/dialogs/external-lead-approve/external-lead-approve.component";
import { ExternalLeadCloseComponent } from "./components/dialogs/external-lead-close/external-lead-close.component";
import { ExternalLeadDeclineComponent } from "./components/dialogs/external-lead-decline/external-lead-decline.component";
import { ExternalLeadReturnComponent } from "./components/dialogs/external-lead-return/external-lead-return.component";
import { InternalLeadApproveComponent } from "./components/dialogs/internal-lead-approve/internal-lead-approve.component";
import { LeadExistingMessageComponent } from "./components/lead-existing-message/lead-existing-message.component";
import { InternalLeadAcceptComponent } from "./components/dialogs/internal-lead-accept/internal-lead-accept.component";
import { InternalLeadStartPaperComponent } from "./components/dialogs/internal-lead-start-paper/internal-lead-start-paper.component";
import { LeadCreateComponent } from "./components/lead-create/lead-create.component";
import { LeadSearchComponent } from "./components/lead-search/lead-search.component";
import { LeadSearchService } from "./services/lead-search.service";
import { LeadDashboardComponent } from "./components/lead-dashboard/lead-dashboard.component";
import { LeadCountBoxComponent } from "./components/lead-dashboard/lead-count-box/lead-count-box.component";
import { LeadCreateApplicationForm } from "./components/lead-create-application-form/lead-create-application-form.component";
import { TinyMceInlineEditorComponent } from "src/app/shared/components/tiny-mce-inline-editor/tiny-mce-inline-editor.component";
import { LeadComprehensiveCreateComponent } from "./components/lead-comprehensive-create/lead-comprehensive-create.component";
import { MatExpansionModule } from "@angular/material";
import { LeadComprehensiveBorrowerComponent } from "./components/dialogs/lead-comprehensive-borrower/lead-comprehensive-borrower.component";
import { LeadComprehensivePartiesComponent } from "./components/dialogs/lead-comprehensive-parties/lead-comprehensive-parties.component";
import { LeadComprehensiveIncomeTypeComponent } from "./components/dialogs/lead-comprehensive-income-type/lead-comprehensive-income-type.component";
import { LeadComprehensiveFacilitieseComponent } from "./components/dialogs/lead-comprehensive-facilitiese/lead-comprehensive-facilitiese.component";
import { AdvanceAnalyticsViewComponent } from "./components/advance-analytics-view/advance-analytics-view.component";
import { AdvanceAnalyticsService } from "./services/advance-analytics.service";
import { DigitalApplicationModalComponent } from './components/digital-application-modal/digital-application-modal.component';
import { DigitalApplicationViewComponent } from './components/digital-application-view/digital-application-view.component';
import { CompLeadCommentViewComponent } from './components/comp-lead-comment-view/comp-lead-comment-view.component';
import { AddCommentModalComponent } from './components/dialogs/add-comment-modal/add-comment-modal.component';
import { LeadComprehensiveDocumentComponent } from "./components/dialogs/lead-comprehensive-document/lead-comprehensive-document.component";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { DigitalApplicantPickerModalComponent } from './components/digital-applicant-picker-modal/digital-applicant-picker-modal.component';


@NgModule({
  declarations: [
    LeadsComponent,
    LeadAddEditComponent,
    LeadFacilityDetailComponent,
    LeadDocumentComponent,
    LeadAuditDetailComponent,
    LeadCustomerDetailComponent,
    ExternalLeadApproveComponent,
    ExternalLeadCloseComponent,
    ExternalLeadDeclineComponent,
    ExternalLeadReturnComponent,
    InternalLeadApproveComponent,
    LeadExistingMessageComponent,
    InternalLeadAcceptComponent,
    InternalLeadStartPaperComponent,
    LeadCreateComponent,
    LeadSearchComponent,
    LeadDashboardComponent,
    LeadCountBoxComponent,
    LeadCreateApplicationForm,
    LeadComprehensiveCreateComponent,
    LeadComprehensiveBorrowerComponent,
    LeadComprehensivePartiesComponent,
    LeadComprehensiveIncomeTypeComponent,
    LeadComprehensiveFacilitieseComponent,
    AdvanceAnalyticsViewComponent,
    DigitalApplicationModalComponent,
    DigitalApplicationViewComponent,
    CompLeadCommentViewComponent,
    AddCommentModalComponent,
    LeadComprehensiveDocumentComponent,
    DigitalApplicantPickerModalComponent,
  ],
  imports: [
    SharedModule,
    LeadRoutingModule,
    AuditModule,
    MatExpansionModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  exports: [LeadCustomerDetailComponent],
  entryComponents: [
    LeadFacilityDetailComponent,
    LeadDocumentComponent,
    LeadCustomerDetailComponent,
    ExternalLeadReturnComponent,
    ExternalLeadApproveComponent,
    ExternalLeadCloseComponent,
    ExternalLeadDeclineComponent,
    InternalLeadApproveComponent,
    LeadExistingMessageComponent,
    InternalLeadAcceptComponent,
    InternalLeadStartPaperComponent,
    LeadCreateApplicationForm,
    TinyMceInlineEditorComponent,
    LeadComprehensiveCreateComponent,
    LeadComprehensiveBorrowerComponent,
    LeadComprehensiveIncomeTypeComponent,
    LeadComprehensivePartiesComponent,
    LeadComprehensiveFacilitieseComponent,
    DigitalApplicationModalComponent,
    AddCommentModalComponent,
    LeadComprehensiveDocumentComponent,
    ConfirmationDialogComponent,
    DigitalApplicantPickerModalComponent,
  ],
  providers: [
    LeadsService,
    LeadAddEditService,
    AuditService,
    MDBSpinningPreloader,
    CurrencyPipe,
    LeadSearchService,
    AdvanceAnalyticsService,
  ],
})
export class LeadModule {}
