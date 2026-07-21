import { NgModule } from "@angular/core";

import { ApplicationFormAddEditRoutingModule } from "./application-form-add-edit-routing.module";
import { ApplicationFormAddEditBaseComponent } from "./components/application-form-add-edit-base/application-form-add-edit-base.component";
import { SharedModule } from "../../../../shared/shared.module";
import { ApplicationFormAddEditService } from "./services/application-form-add-edit.service";
import { ApfCribReportComponent } from "./components/application-form-add-edit-base/tab-components/apf-crib-screen/apf-crib-report/apf-crib-report.component";
import { ApfAddEditRiskRateComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-risk-rate/apf-add-edit-risk-rate.component";
import { ApfFinancialObligationsComponent } from "./components/application-form-add-edit-base/tab-components/apf-liability-screen/apf-financial-obligations/apf-financial-obligations.component";
import { ApfCribScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-crib-screen/apf-crib-screen.component";
import { ApfRiskRateScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-risk-rate-screen/apf-risk-rate-screen.component";
import { ApfBasicScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-basic-screen/apf-basic-screen.component";
import { ApfBasicInformationComponent } from "./components/application-form-add-edit-base/tab-components/apf-basic-screen/apf-basic-information/apf-basic-information.component";
import { ApfOtherBankAccountDetailsAddEditComponent } from "./components/application-form-add-edit-base/tab-components/apf-basic-screen/apf-other-bank-account-details-add-edit/apf-other-bank-account-details-add-edit.component";
import { ApfAssetsScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-assets-screen/apf-assets-screen.component";
import { ApfRepaymentScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-repayment-screen/apf-repayment-screen.component";
import { ApfDocumentsScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-documents-screen/apf-documents-screen.component";
import { ApfDocumentAttachmentsUploadsComponent } from "./components/application-form-add-edit-base/tab-components/apf-documents-screen/apf-document-attachments-uploads/apf-document-attachments-uploads.component";
import { ApfDeclarationScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-declaration-screen/apf-declaration-screen.component";
import { ApfDeclarationAddEditComponent } from "./components/application-form-add-edit-base/tab-components/apf-declaration-screen/apf-declaration-add-edit/apf-declaration-add-edit.component";
import { ApfSearchCribComponent } from "./components/application-form-add-edit-base/tab-components/apf-crib-screen/apf-search-crib/apf-search-crib.component";
import { MDBBootstrapModulesPro } from "ng-uikit-pro-standard";
import { ApfPersonalInformationAddEditComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/components/apf-personal-information-add-edit/apf-personal-information-add-edit.component";
import { ApfBusinessInformationAddEditComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/components/apf-business-information-add-edit/apf-business-information-add-edit.component";
import { ApfCorporateInformationAddEditComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/components/apf-corporate-information-add-edit/apf-corporate-information-add-edit.component";
import { ApfAddEditJoiningPartnersComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/apf-add-edit-joining-partners.component";
import { ApfBasicOwnershipDetailsComponent } from "./components/application-form-add-edit-base/tab-components/apf-basic-screen/apf-basic-information/apf-basic-ownership-details/apf-basic-ownership-details.component";
import { ApfAddEditDocumentsComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-documents/apf-add-edit-documents.component";
import { ApfAddEditOwnershipDetailsComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-ownership-details/apf-add-edit-ownership-details.component";
import { ApfFacilitiesScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-facilities-screen/apf-facilities-screen.component";
import { ApfAddEditFacilitiesComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-facilities/apf-add-edit-facilities.component";
import { ApfFacilityComponent } from "./components/application-form-add-edit-base/tab-components/apf-facilities-screen/apf-facility/apf-facility.component";
import { ApfAddTopicsComponent } from "./components/application-form-add-edit-base/support-components/apf-add-topics/apf-add-topics.component";
import { ApfAddEditTopicDataComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-topic-data/apf-add-edit-topic-data.component";
import { ApfTopicViewComponent } from "./components/application-form-add-edit-base/support-components/apf-topic-view/apf-topic-view.component";
import { ApfSecuritiesScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-securities-screen/apf-securities-screen.component";
import { ApfFacilitySecurityDataComponent } from "./components/application-form-add-edit-base/tab-components/apf-securities-screen/apf-facility-security-data/apf-facility-security-data.component";
import { ApfFacilityCommonSecurityDataComponent } from "./components/application-form-add-edit-base/tab-components/apf-securities-screen/apf-facility-common-security-data/apf-facility-common-security-data.component";
import { ApfAddEditSecurityDataComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-security-data/apf-add-edit-security-data.component";
import { ApfExecutiveSummaryScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-executive-summary-screen/apf-executive-summary-screen.component";
import { ApfAddEditCribReportsComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-crib-reports/apf-add-edit-crib-reports.component";
import { ApfCribReportRecordComponent } from "./components/application-form-add-edit-base/tab-components/apf-crib-screen/apf-crib-report/apf-crib-report-record/apf-crib-report-record.component";
import { ApfCribAttachmentsComponent } from "./components/application-form-add-edit-base/tab-components/apf-crib-screen/apf-crib-report/apf-crib-attachments/apf-crib-attachments.component";
import { ApfAddEditCribAttachmentComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-crib-attachment/apf-add-edit-crib-attachment.component";
import { ApfAddEditOtherBankDetailsComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-other-bank-details/apf-add-edit-other-bank-details.component";
import { ApfManualRiskRatesComponent } from "./components/application-form-add-edit-base/tab-components/apf-risk-rate-screen/apf-manual-risk-rates/apf-manual-risk-rates.component";
import { ApfNextPrevButtonComponent } from "./components/application-form-add-edit-base/support-components/apf-next-prev-button/apf-next-prev-button.component";
import { ApfAddEditFinancialObligationsComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-financial-obligations/apf-add-edit-financial-obligations.component";
import { ApfForwardApplicationFormComponent } from "./components/application-form-add-edit-base/support-components/apf-forward-application-form/apf-forward-application-form.component";
import { ApfReturnApplicationFormComponent } from "./components/application-form-add-edit-base/support-components/apf-return-application-form/apf-return-application-form.component";
import { ApfUpdateFacilityOrderComponent } from "./components/application-form-add-edit-base/support-components/apf-update-facility-order/apf-update-facility-order.component";
import { NgxSortableModule } from "ngx-sortable";
import { ApfLpsScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-lps-screen/apf-lps-screen.component";
import { ApfLpsBasicDetailsComponent } from "./components/application-form-add-edit-base/tab-components/apf-lps-screen/apf-lps-basic-details/apf-lps-basic-details.component";
import { ApfLpsExecutiveSummaryTopicsComponent } from "./components/application-form-add-edit-base/tab-components/apf-lps-screen/apf-lps-executive-summary-topics/apf-lps-executive-summary-topics.component";
import { ApfBorrowerGuarantorComponent } from "./components/application-form-add-edit-base/tab-components/apf-crib-screen/apf-borrower-guarantor/apf-borrower-guarantor.component";
import { ApfAddEditBorrowerGuarantorComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-borrower-guarantor/apf-add-edit-borrower-guarantor.component";
import { ApplicationFormPreviewModule } from "../preview-components/application-form-preview.module";
import { ApfFacilityDocumentComponent } from "./components/application-form-add-edit-base/support-components/apf-facility-document/apf-facility-document.component";
import { ApfAddEditFacilityDocumentComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-facility-document/apf-add-edit-facility-document.component";
import { ApfFinacleCustomerUpdateComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/components/apf-finacle-customer-update/apf-finacle-customer-update.component";
import { ApfFinacleCustomerAddressAddEditComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/components/apf-finacle-customer-update/apf-finacle-customer-address-add-edit/apf-finacle-customer-address-add-edit.component";
import { ApfFinacleCustomerContactAddEditComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-joining-partners/components/apf-finacle-customer-update/apf-finacle-customer-contact-add-edit/apf-finacle-customer-contact-add-edit.component";
import { ApfLiabilityScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-liability-screen/apf-liability-screen.component";
import { ApfCommentsScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-comments-screen/apf-comments-screen.component";
import { ApfEsgScreenComponent } from "./components/application-form-add-edit-base/tab-components/apf-esg-screen/apf-esg-screen.component";
import { ApfAddEditRiskScoreComponent } from "./components/application-form-add-edit-base/support-components/apf-add-edit-risk-score/apf-add-edit-risk-score.component";
import { EsgAnnexuresCommonComponent } from "src/app/shared/components/esg/esg-annexures-common/esg-annexures-common.component";
import { EsgAnnexureSelectorsCommonComponent } from "src/app/shared/components/esg/esg-annexure-selectors-common/esg-annexure-selectors-common.component";
import { EsgAnnexureAttachmentComponent } from "src/app/shared/components/esg/esg-annexure-attachment/esg-annexure-attachment.component";
import { AfEsgCommentWrapperComponent } from './components/application-form-add-edit-base/support-components/af-esg-comment-wrapper/af-esg-comment-wrapper.component';

@NgModule({
  declarations: [
    ApplicationFormAddEditBaseComponent,
    ApfCribReportComponent,
    ApfAddEditRiskRateComponent,
    ApfFinancialObligationsComponent,
    ApfCribScreenComponent,
    ApfRiskRateScreenComponent,
    ApfBasicScreenComponent,
    ApfBasicInformationComponent,
    ApfOtherBankAccountDetailsAddEditComponent,
    ApfAssetsScreenComponent,
    ApfRepaymentScreenComponent,
    ApfDocumentsScreenComponent,
    ApfDocumentAttachmentsUploadsComponent,
    ApfDeclarationScreenComponent,
    ApfDeclarationAddEditComponent,
    ApfSearchCribComponent,
    ApfPersonalInformationAddEditComponent,
    ApfBusinessInformationAddEditComponent,
    ApfCorporateInformationAddEditComponent,
    ApfAddEditJoiningPartnersComponent,
    ApfBasicOwnershipDetailsComponent,
    ApfAddEditDocumentsComponent,
    ApfAddEditOwnershipDetailsComponent,
    ApfFacilitiesScreenComponent,
    ApfAddEditFacilitiesComponent,
    ApfFacilityComponent,
    ApfAddTopicsComponent,
    ApfAddEditTopicDataComponent,
    ApfTopicViewComponent,
    ApfSecuritiesScreenComponent,
    ApfFacilitySecurityDataComponent,
    ApfFacilityCommonSecurityDataComponent,
    ApfAddEditSecurityDataComponent,
    ApfExecutiveSummaryScreenComponent,
    ApfAddEditCribReportsComponent,
    ApfCribReportRecordComponent,
    ApfCribAttachmentsComponent,
    ApfAddEditCribAttachmentComponent,
    ApfAddEditOtherBankDetailsComponent,
    ApfManualRiskRatesComponent,
    ApfNextPrevButtonComponent,
    ApfAddEditFinancialObligationsComponent,
    ApfForwardApplicationFormComponent,
    ApfReturnApplicationFormComponent,
    ApfUpdateFacilityOrderComponent,
    ApfLpsScreenComponent,
    ApfLpsBasicDetailsComponent,
    ApfLpsExecutiveSummaryTopicsComponent,
    ApfBorrowerGuarantorComponent,
    ApfAddEditBorrowerGuarantorComponent,
    ApfFacilityDocumentComponent,
    ApfAddEditFacilityDocumentComponent,
    ApfFinacleCustomerUpdateComponent,
    ApfFinacleCustomerAddressAddEditComponent,
    ApfFinacleCustomerContactAddEditComponent,
    ApfLiabilityScreenComponent,
    ApfCommentsScreenComponent,
    ApfEsgScreenComponent,
    ApfAddEditRiskScoreComponent,
    AfEsgCommentWrapperComponent,
  ],
  imports: [
    SharedModule,
    ApplicationFormPreviewModule,
    ApplicationFormAddEditRoutingModule,
    NgxSortableModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  providers: [ApplicationFormAddEditService],
  entryComponents: [
    ApfAddEditOwnershipDetailsComponent,
    ApfAddEditJoiningPartnersComponent,
    ApfAddEditCribReportsComponent,
    ApfAddEditFacilitiesComponent,
    ApfAddEditTopicDataComponent,
    ApfAddEditDocumentsComponent,
    ApfAddTopicsComponent,
    ApfAddEditSecurityDataComponent,
    ApfAddEditCribAttachmentComponent,
    ApfAddEditOtherBankDetailsComponent,
    ApfAddEditRiskRateComponent,
    ApfAddEditFinancialObligationsComponent,
    ApfForwardApplicationFormComponent,
    ApfReturnApplicationFormComponent,
    ApfUpdateFacilityOrderComponent,
    ApfAddEditBorrowerGuarantorComponent,
    ApfFacilityDocumentComponent,
    ApfAddEditFacilityDocumentComponent,
    ApfLpsBasicDetailsComponent,
    EsgAnnexureSelectorsCommonComponent,
    EsgAnnexureAttachmentComponent
  ],
  exports: [ApfLpsBasicDetailsComponent, ApfLpsExecutiveSummaryTopicsComponent],
})
export class ApplicationFormAddEditModule {}
