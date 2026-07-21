import {NgModule} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {FacilityPaperRoutingModule} from './facility-paper-routing.module';
import {FacilityPapersComponent} from './components/facility-papers/facility-papers.component';
import {SharedModule} from "../../../shared/shared.module";
import {FacilityPapersService} from "./services/facility-papers.service";
import {FacilityPaperAddEditComponent} from './components/facility-paper-add-edit/facility-paper-add-edit.component';
import {FacilityPaperAddEditService} from "./services/facility-paper-add-edit.service";
import {FpPersonalDetailsComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-details.component';
import {FpEditorComponent} from './components/facility-paper-add-edit/components/fp-editor/fp-editor.component';
import {LeadModule} from "../lead/lead.module";
import {FpDirectorDetailsComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/fp-director-details/fp-director-details.component';
import {FpCustomerSearchComponent} from "./components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-customer-search/fp-customer-search.component";
import {FpAddDirectorComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/fp-director-details/fp-add-director/fp-add-director.component';
import {FpCompanyRoaComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/fp-company-roa/fp-company-roa.component';
import {FpAddEditCompanyRoaComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/fp-company-roa/fp-add-edit-company-roa/fp-add-edit-company-roa.component';
import {BasicFpInfoComponent} from './components/facility-paper-add-edit/components/basic-wrapper/basic-fp-info/basic-fp-info.component';
import {FpSupportingDocumentUploadComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/fp-supporting-documents/fp-supporting-document-upload/fp-supporting-document-upload.component';
import {FpSupportingDocumentsComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/fp-supporting-documents/fp-supporting-documents.component';
import {FpPersonalDetailTabViewComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/fp-personal-detail-tab-view.component';
import {FpCustomerOtherBankFacilityComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/fp-customer-other-bank-facility/fp-customer-other-bank-facility.component';
import {PersonalDetailBankComponentBkp} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/personal-detail-bank-bkp/personal-detail-bank.component';
import {PersonalDetailFacilityComponentBkp} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/personal-detail-facility-bkp/personal-detail-facility.component';
import {PersonalDetailObFacilityComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/personal-detail-ob-facility/personal-detail-ob-facility.component';
import {AddEditFacilityComponent} from "./components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/add-edit-facility/add-edit-facility.component";
import {PersonalCribAddEditComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/personal-crib-add-edit/personal-crib-add-edit.component';
import {PersonalCribDetailComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/personal-crib-detail/personal-crib-detail.component';
import {PersonalCustomerRatingsComponent} from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/personal-customer-ratings/personal-customer-ratings.component';
import {FpFacilitiesComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/fp-facilities.component';
import {FpFacilityDataSecurityComponent} from './components/facility-paper-add-edit/components/fp-security/fp-sec-facility-data/fp-facility-data-security/fp-facility-data-security.component';
import {FacilityDataDocUploadComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/facility-data-helper/facility-data-doc-upload/facility-data-doc-upload.component';
import {FacilityDataComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/facility-data/facility-data.component';
import {FpFacilityRepaymentComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/facility-data-helper/fp-facility-repayment/fp-facility-repayment.component';
import {FpTotalExposureDataComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-total-exposure-data/fp-total-exposure-data.component';
import {FpApproveComponent} from './components/facility-paper-add-edit/components/fp-approve/fp-approve.component';
import {FpApproveRemarkComponent} from './components/facility-paper-add-edit/components/fp-approve-remark/fp-approve-remark.component';
import {FpCommentComponent} from './components/facility-paper-add-edit/components/fp-comment/fp-comment.component';
import {FpAuditDetailComponent} from './components/facility-paper-add-edit/components/fp-audit-detail/fp-audit-detail.component';
import {AuditModule} from "../audit/audit.module";
import {AuditService} from "../audit/services/audit.service";
import {UpcTemplateStructureComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-structure.component';
import {UpcTemplateAddDataComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-add-data/upc-template-add-data.component';
import {UpcTemplateReadViewComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-read-view/upc-template-read-view.component';
import {FpKalyptoDetailComponent} from './components/facility-paper-add-edit/components/fp-kalypto-detail/fp-kalypto-detail.component';
import {SingleNodeHtmlComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/html-view/single-node-html/single-node-html.component';
import {ShowHtmlModalComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/html-view/show-html-modal/show-html-modal.component';
import {ShowFullNodeHtmlComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/html-view/show-full-node-html/show-full-node-html.component';
import {BasicWrapperComponent} from './components/facility-paper-add-edit/components/basic-wrapper/basic-wrapper.component';
import {OtherDetailsWrapperComponent} from './components/facility-paper-add-edit/components/other-details-wrapper/other-details-wrapper.component';
import {FacilityWrapperComponent} from './components/facility-paper-add-edit/components/facility-wrapper/facility-wrapper.component';
import {FpSecurityComponent} from './components/facility-paper-add-edit/components/fp-security/fp-security.component';
import {FpSecFacilityDataComponent} from './components/facility-paper-add-edit/components/fp-security/fp-sec-facility-data/fp-sec-facility-data.component';
import {FacilityDataDocumentComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/facility-data-helper/facility-data-document/facility-data-document.component';
import {UpdateFacilityOrderComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/update-facility-order/update-facility-order.component';
import {CreditRiskCommentComponent} from './components/facility-paper-add-edit/components/credit-risk-comment/credit-risk-comment.component';
import {CommentListToPaginationComponent} from './components/facility-paper-add-edit/components/fp-approve-remark/comment-list-to-pagination/comment-list-to-pagination.component';
import {FpFacilitiesDataComponent} from './components/facility-paper-add-edit/components/fp-approve-remark/fp-facilities-data/fp-facilities-data.component';
import {FpCreditRiskCommentDataComponent} from './components/facility-paper-add-edit/components/credit-risk-comment/fp-credit-risk-comment-data/fp-credit-risk-comment-data.component';
import {AgentFpForwardComponent} from './components/facility-paper-add-edit/components/agent-fp-forward/agent-fp-forward.component';
import {FpReturnToAgentComponent} from './components/facility-paper-add-edit/components/fp-return-to-agent/fp-return-to-agent.component';
import {HtmlEditorComponent} from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/html-editor/html-editor.component';
import {FpDefaultFullViewComponent} from './components/facility-paper-add-edit/components/fp-default-full-view/fp-default-full-view.component';
import {FpSecuritySummeryComponent} from "./components/facility-paper-add-edit/components/fp-security/fp-security-summery/fp-security-summery.component";
import {FpReviewerCommentComponent} from './components/facility-paper-add-edit/components/fp-reviewer-comment/fp-reviewer-comment.component';
import {PreviewModule} from "../../preview/preview.module";
import {AddEditThreeColumnFacilityComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/three-column/add-edit-three-column-facility/add-edit-three-column-facility.component';
import {ThreeColumnFacilityDataComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/three-column/three-column-facility-data/three-column-facility-data.component';
import {FpThreeColTotalExposureDataComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-three-col-total-exposure-data/fp-three-col-total-exposure-data.component';
import {UpdateOutstandingDateComponent} from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/update-outstanding-date/update-outstanding-date.component';
import {FpReturnComponent} from './components/facility-paper-add-edit/components/support-components/fp-return/fp-return.component';
import {FpCustomerPaperUpcHistoryComponent} from './components/facility-paper-add-edit/components/support-components/fp-customer-paper-upc-history/fp-customer-paper-upc-history.component';
import {FpShowCribDetailsButtonComponent} from './components/facility-paper-add-edit/components/common/fp-show-crib-details-button/fp-show-crib-details-button.component';
import { FacilityPaperSearchComponent } from './components/facility-paper-search/facility-paper-search.component';
import { FacilityPaperSearchService } from './services/facility-paper-search.service';
import { CustomerEvaluationComponent } from '../customer-360/components/customer-base/components/customer-evaluation-list/customer-evaluation.component';
import { CustomerEvaluationFormComponent } from '../customer-360/components/customer-base/components/customer-evaluation-form/customer-evaluation-form.component';
import { MDBBootstrapModule, MDBModalRef, ModalModule } from 'ng-uikit-pro-standard';

import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { FpCreditRiskCommentDocumentsComponent } from './components/facility-paper-add-edit/components/other-details-wrapper/fp-credit-risk-comment-documents/fp-credit-risk-comment-documents.component';
import { FpCreditRiskCommentUploadDocumentsComponent } from './components/facility-paper-add-edit/components/other-details-wrapper/fp-credit-risk-comment-documents/fp-credit-risk-comment-upload-documents/fp-credit-risk-comment-upload-documents.component';
import { ScorecardTemplateComponent } from './components/facility-paper-add-edit/components/fp-editor/scorecard-template/scorecard-template.component';
import { ApplicationFormAddEditService } from '../application-form/application-form-add-edit/services/application-form-add-edit.service';
import { MatButton, MatButtonModule, MatCheckbox, MatCheckboxModule, MatExpansionModule, MatIconModule, MatSelectModule } from '@angular/material';
import { AddEditCustomFacilityDialogComponent } from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/add-edit-facility/add-edit-custom-facility-dialog/add-edit-custom-facility-dialog.component';

import {FpDocumentationComponent} from './components/facility-paper-add-edit/components/fp-documentation/fp-documentation.component';
import {AddEditDocumentComponent} from './components/facility-paper-add-edit/components/fp-documentation/add-edit-document/add-edit-document.component';
import {TreeModule} from 'primeng/tree';
//import {jsPDF} from "jspdf";
//import {ToastModule} from 'primeng/toast';
//import {ButtonModule} from 'primeng/button';
import { CreditRiskCommentNewComponent } from './components/facility-paper-add-edit/components/credit-risk-comment-new/credit-risk-comment-new.component';
import { CreditRiskOpinionHistoryNewComponent } from './components/facility-paper-add-edit/components/credit-risk-comment-new/credit-risk-opinion-history-new/credit-risk-opinion-history-new.component';
import { FpFacilityCopyComponent } from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/facility-data-helper/fp-facility-copy/fp-facility-copy.component';
import { UpcNotifyComponentComponent } from './components/facility-paper-add-edit/components/fp-editor/upc-notify-component/upc-notify-component.component';
import { FpBccFullViewComponent } from './components/facility-paper-add-edit/components/fp-bcc-full-view/fp-bcc-full-view.component';
import { BccAttachmentsComponent } from './attachments/bcc-attachments/bcc-attachments.component';
import { BccDocumentUploadComponent } from './attachments/bcc-document-upload/bcc-document-upload.component';
import { CustomerLimitsOutstandingDataComponent } from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/finacle-data/customer-limits-outstanding-data/customer-limits-outstanding-data.component';
import { FinacleDataStructureComponent } from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/finacle-data/customer-limits-outstanding-data/finacle-data-structure/finacle-data-structure.component';

import { UPCTemplateComparisonComponent } from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-comparison/upc-template-comparison.component';
import {MatToolbarModule,MatSidenavModule,MatListModule} from '@angular/material';
import { FacilityChangesModalComponent } from 'src/app/shared/components/facility-changes-modal/facility-changes-modal.component';
import { UpcTemplateHistoryCommentViewComponent } from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-comparison/upc-template-history-comment-view/upc-template-history-comment-view.component';
import { UpcTemplateViewCommentComponent } from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-add-data/upc-template-view-comment/upc-template-view-comment.component';
import { UPCTemplateCommentSectionComponent } from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-comparison/upc-template-comment-section/upc-template-comment-section.component';
import { UpcTemplateHistorySectionComponent } from './components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-comparison/upc-template-history-section/upc-template-history-section.component';
import { FpViewDasComponent } from './components/facility-paper-add-edit/components/fp-das/fp-view-das/fp-view-das.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { CustomerInsuranceValuationComponent } from 'src/app/shared/components/personal-customer-stat-wrapper/components/customer-insurance-valuation/customer-insurance-valuation.component';
import { InsuranceValuationModalComponent } from './components/facility-paper-add-edit/components/fp-security/insurance-valuation-modal/insurance-valuation-modal.component';
import { FpGroupExposureComponent } from './components/facility-paper-add-edit/components/facility-wrapper/fp-group-exposure/fp-group-exposure.component';

import { AddCribReportComponent } from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/add-crib-report/add-crib-report.component';
import { EditCribReportComponent } from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/edit-crib-report/edit-crib-report.component';
import { ViewCribContentComponent } from 'src/app/shared/components/view-crib-content/view-crib-content.component';
import { CoveringApprovalService } from '../covering-approval/services/covering-approval.service';
import { FpWalletShareComponent } from './components/facility-paper-add-edit/components/fp-wallet-share/fp-wallet-share.component';
import { AddEditWalletShareComponent } from './components/facility-paper-add-edit/components/fp-wallet-share/add-edit-wallet-share/add-edit-wallet-share.component';
import { FpEsgWrapperComponent } from './components/facility-paper-add-edit/components/fp-esg-wrapper/fp-esg-wrapper.component';
import { FpAddEditRiskScoreComponent } from './components/facility-paper-add-edit/components/fp-esg-wrapper/fp-add-edit-risk-score/fp-add-edit-risk-score.component';
import { EsgAnnexureSelectorsCommonComponent } from 'src/app/shared/components/esg/esg-annexure-selectors-common/esg-annexure-selectors-common.component';
import { EsgAnnexureAttachmentComponent } from 'src/app/shared/components/esg/esg-annexure-attachment/esg-annexure-attachment.component';
import { FpEsgCommentWrapperComponent } from './components/facility-paper-add-edit/components/fp-esg-wrapper/fp-esg-comment-wrapper/fp-esg-comment-wrapper.component';

import { FpCommitteInquiriesComponent } from './components/facility-paper-add-edit/components/fp-committe-inquiries/fp-committe-inquiries.component';
import { FpAddCommitteeInquiryComponent } from './components/facility-paper-add-edit/components/fp-committe-inquiries/fp-add-committee-inquiry/fp-add-committee-inquiry.component';
import { FpInquiryRichEditorComponent } from './components/facility-paper-add-edit/components/fp-committe-inquiries/fp-inquiry-rich-editor/fp-inquiry-rich-editor.component';
import { FpAddInquiryResponseComponent } from './components/facility-paper-add-edit/components/fp-committe-inquiries/fp-add-inquiry-response/fp-add-inquiry-response.component';
import { FpAddCommitteInquryButtonComponent } from './components/facility-paper-add-edit/components/fp-committe-inquiries/fp-add-committe-inqury-button/fp-add-committe-inqury-button.component';
import { FpDeviationComponent } from './components/facility-paper-add-edit/components/fp-deviation/fp-deviation.component';
import { FpDocumentationNewComponent } from './components/facility-paper-add-edit/components/fp-documentation-new/fp-documentation-new.component';
import { AddEditContentFeildComponent } from './components/facility-paper-add-edit/components/fp-documentation-new/add-edit-content-feild/add-edit-content-feild.component';
import { ActionLogViewComponent } from './components/facility-paper-add-edit/components/fp-documentation-new/action-log-view/action-log-view.component';
import { SdInfoDialogComponent } from './components/facility-paper-add-edit/components/fp-documentation-new/sd-info-dialog/sd-info-dialog.component';
import { SdCovenantModalComponent } from './components/facility-paper-add-edit/components/fp-documentation-new/sd-covenant-modal/sd-covenant-modal.component';
import { CustomerClarificationViewComponent } from './components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/customer-clarification-view/customer-clarification-view.component';
import { FpMdAssistanceCmntViewComponent } from './components/facility-paper-add-edit/components/fp-md-assistance-cmnt-view/fp-md-assistance-cmnt-view.component';
import { CovenantSharedModule } from '../covenant/covenant-shared.module';

@NgModule({
  declarations: [FacilityPapersComponent,
    FacilityPaperAddEditComponent,
    FpPersonalDetailsComponent,
    FpEditorComponent,
    FpDirectorDetailsComponent,
    FpCustomerSearchComponent,
    FpAddDirectorComponent,
    FpCompanyRoaComponent,
    FpAddEditCompanyRoaComponent,
    BasicFpInfoComponent,
    FpSupportingDocumentUploadComponent,
    FpSupportingDocumentsComponent,
    FpPersonalDetailTabViewComponent,
    AddEditFacilityComponent,
    FpCustomerOtherBankFacilityComponent,
    PersonalDetailBankComponentBkp,
    PersonalDetailFacilityComponentBkp,
    PersonalDetailObFacilityComponent,
    PersonalCribAddEditComponent,
    PersonalCribDetailComponent,
    PersonalCustomerRatingsComponent,
    FpFacilityDataSecurityComponent,
    FacilityDataDocUploadComponent,
    FpFacilitiesComponent,
    FacilityDataComponent,
    FpFacilityRepaymentComponent,
    FpTotalExposureDataComponent,
    FpApproveComponent,
    FpApproveRemarkComponent,
    FpCommentComponent,
    FpAuditDetailComponent,
    UpcTemplateStructureComponent,
    UpcTemplateAddDataComponent,
    UpcTemplateReadViewComponent,
    FpKalyptoDetailComponent,
    SingleNodeHtmlComponent,
    ShowHtmlModalComponent,
    ShowFullNodeHtmlComponent,
    BasicWrapperComponent,
    OtherDetailsWrapperComponent,
    FacilityWrapperComponent,
    FpSecurityComponent,
    FpSecFacilityDataComponent,
    FacilityDataDocumentComponent,
    UpdateFacilityOrderComponent,
    CreditRiskCommentComponent,
    CommentListToPaginationComponent,
    FpFacilitiesDataComponent,
    FpCreditRiskCommentDataComponent,
    AgentFpForwardComponent,
    FpReturnToAgentComponent,
    HtmlEditorComponent,
    FpDefaultFullViewComponent,
    FpSecuritySummeryComponent,
    FpReviewerCommentComponent,
    AddEditThreeColumnFacilityComponent,
    ThreeColumnFacilityDataComponent,
    FpThreeColTotalExposureDataComponent,
    UpdateOutstandingDateComponent,
    FpReturnComponent,
    FpCustomerPaperUpcHistoryComponent,
    FpShowCribDetailsButtonComponent,
    FacilityPaperSearchComponent,
    CustomerEvaluationComponent,
    CustomerEvaluationFormComponent,
    FpCreditRiskCommentDocumentsComponent,
    FpCreditRiskCommentUploadDocumentsComponent,
    ScorecardTemplateComponent,
    FpDocumentationComponent,
    AddEditDocumentComponent,
    AddEditCustomFacilityDialogComponent,
    CreditRiskCommentNewComponent,
    CreditRiskOpinionHistoryNewComponent,
    FpViewDasComponent,
    FpFacilityCopyComponent,
    UpcNotifyComponentComponent,
    FpBccFullViewComponent,
    BccAttachmentsComponent,
    BccDocumentUploadComponent,
    CustomerLimitsOutstandingDataComponent,
    FinacleDataStructureComponent,
    UPCTemplateComparisonComponent,
    UpcTemplateHistoryCommentViewComponent,
    UpcTemplateViewCommentComponent,
    UPCTemplateCommentSectionComponent,
    UpcTemplateHistorySectionComponent,
    InsuranceValuationModalComponent,
    FpGroupExposureComponent,
    AddCribReportComponent,
    EditCribReportComponent,
    FpWalletShareComponent,
    AddEditWalletShareComponent,
    FpEsgWrapperComponent,
    FpAddEditRiskScoreComponent,
    FpEsgCommentWrapperComponent,
    FpCommitteInquiriesComponent,
    FpAddCommitteeInquiryComponent,
    FpInquiryRichEditorComponent,
    FpAddInquiryResponseComponent,
    FpAddCommitteInquryButtonComponent,
    FpDeviationComponent,
    FpDocumentationNewComponent,
    AddEditContentFeildComponent,
    ActionLogViewComponent,
    SdInfoDialogComponent,
    SdCovenantModalComponent,
    CustomerClarificationViewComponent,
    FpMdAssistanceCmntViewComponent
  ],
  imports: [
    PreviewModule,
    SharedModule,
    FacilityPaperRoutingModule,
    LeadModule,
    AuditModule,
    CovenantSharedModule,
    TreeModule,
   // jsPDF,
   // ToastModule,
   // ButtonModule,
    ModalModule.forRoot(),
    MDBBootstrapModule.forRoot(),
    MatExpansionModule,
    MatSelectModule,
    MatCheckboxModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatButtonModule,
    MatToolbarModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
  ],
  entryComponents: [
    FpAddDirectorComponent,
    FpCustomerSearchComponent,
    FpAddEditCompanyRoaComponent,
    FpSupportingDocumentUploadComponent,
    AddEditFacilityComponent,
    FpSupportingDocumentUploadComponent,
    FpCustomerOtherBankFacilityComponent,
    PersonalCribAddEditComponent,
    FpFacilityDataSecurityComponent,
    FacilityDataDocUploadComponent,
    FpApproveComponent,
    FpCommentComponent,
    UpcTemplateAddDataComponent,
    ShowHtmlModalComponent,
    FpFacilityRepaymentComponent,
    FacilityDataDocumentComponent,
    UpdateFacilityOrderComponent,
    AgentFpForwardComponent,
    FpReturnToAgentComponent,
    AddEditThreeColumnFacilityComponent,
    UpdateOutstandingDateComponent,
    FpReturnComponent,
    FpCustomerPaperUpcHistoryComponent,
    CustomerEvaluationFormComponent,
    FpCreditRiskCommentUploadDocumentsComponent,
    FpDocumentationComponent,
    AddEditDocumentComponent,
    AddEditCustomFacilityDialogComponent,
    FpFacilityCopyComponent,
    UpcNotifyComponentComponent,
    UPCTemplateComparisonComponent,
    FacilityChangesModalComponent,
    UpcTemplateHistoryCommentViewComponent,
    FpViewDasComponent,InsuranceValuationModalComponent,
    FpGroupExposureComponent,
    FpViewDasComponent,
    InsuranceValuationModalComponent,
    AddCribReportComponent,
    EditCribReportComponent,
    ViewCribContentComponent,
    FpAddCommitteeInquiryComponent,
    FpInquiryRichEditorComponent,
    FpAddInquiryResponseComponent,
    AddEditWalletShareComponent,
    EsgAnnexureSelectorsCommonComponent,
    EsgAnnexureAttachmentComponent,
    FpAddCommitteeInquiryComponent,
    FpInquiryRichEditorComponent,
    FpAddInquiryResponseComponent,
    AddEditContentFeildComponent,
    ActionLogViewComponent,
    SdInfoDialogComponent,
    SdCovenantModalComponent
  ],
  providers: [
    FacilityPapersService,
    AuditService,
    CurrencyPipe,
    FacilityPaperSearchService,
    MDBModalRef,
    UrlEncodeService,
    ApplicationFormAddEditService,
    CoveringApprovalService,
    DatePipe
  ]
})
export class FacilityPaperModule {
}
