import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NgxPrintModule} from "ngx-print";
import {CapitalizeDirective} from "./directives/capitalize.directive";
import {FocusInputDirective} from "./directives/focus-input.directive";
import {GoBackDirective} from "./directives/go-back.directive";
import {HasAnyPrivilegeDirective} from "./directives/has-any-privilege.directive";
import {StickyDirective} from "./directives/sticky.directive";
import {JoinPipe} from "./pipes/join.pipe";
import {GetObjectPipe} from "./pipes/get-object.pipe";
import {SafePipe} from "./pipes/safe.pipe";
import {FirstLetterPipe} from "./pipes/first-letter.pipe";
import {SafeUrlPipe} from "./pipes/safe-url.pipe";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {MdbImports} from "./mdb.imports";
import {PaginatorComponent} from './components/paginator/paginator.component';
import {TrackebleInfoComponent} from './components/trackeble-info/trackeble-info.component';
import {ConfirmationDialogComponent} from './components/confirmation-dialog/confirmation-dialog.component';
import {TreeModule} from "angular-tree-component";
import {TabsComponent} from './components/tabs/tabs.component';
import {TabComponent} from './components/tabs/tab/tab.component';
import {RichTextEditorComponent} from './components/rich-text-editor/rich-text-editor.component';
import {ListToPaginatorComponent} from './components/list-to-paginator/list-to-paginator.component';
import {TreeViewNodesComponent} from './components/tree-view-nodes/tree-view-nodes.component';
import {ViewNodeComponent} from './components/tree-view-nodes/view-node/view-node.component';
import {AnimatedCounterComponent} from './components/animated-counter/animated-counter.component';
import {InformationDialogComponent} from './components/information-dialog/information-dialog.component';
import {TextAreaAutoHeightDirective} from './directives/text-area-auto-height.directive';
import {JsonViewerComponent} from './components/json-viewer/json-viewer.component';
import {FromToDateDialogComponent} from './components/from-to-date-dialog/from-to-date-dialog.component';
import {CommentDialogComponent} from './components/comment-dialog/comment-dialog.component';
import {ReviewerCommentDialogComponent} from './components/reviewer-comment-dialog/reviewer-comment-dialog.component';
import {GenerateBccDialogComponent} from "./components/generate-bcc-dialog/generate-bcc-dialog.component";
import {FacilityPaperCopyDialogComponent} from "./components/facility-paper-copy-dialog/facility-paper-copy-dialog.component";
import {SafeHtmlPipe} from "./pipes/safe-html.pipe";
import {TinyMceEditorComponent} from './components/tiny-mce-editor/tiny-mce-editor.component';
import {HtmlContentViewerComponent} from './components/html-content-viewer/html-content-viewer.component';
import {FacilityPaperReviewerLabelComponent} from './components/facility-paper-reviewer-label/facility-paper-reviewer-label.component';
import {FacilityPaperDateCountLabelComponent} from './components/facility-paper-date-count-label/facility-paper-date-count-label.component';
import {ShowCribHistoryComponent} from './components/show-crib-history/show-crib-history.component';
import {BlockedByUPMsDirective} from './directives/blocked-by-upms.directive';
import {ApplicationFormCopyDialogComponent} from './components/application-form-copy-dialog/application-form-copy-dialog.component';
import {CommonForwardComponent} from './components/common-forward/common-forward.component';
import {CommonReleaseComponent} from "./components/common-release/common-release.component";
import {CommonAttendComponent} from './components/common-attend/common-attend.component';
import {CommentWithViewOptionsDialogComponent} from './components/comment-with-view-options-dialog/comment-with-view-options-dialog.component';
import {CommonUserCommentComponent} from './components/common-user-comment/common-user-comment.component';
import {RiskOpinionReplyViewerComponent} from './components/risk-opinion-reply-viewer/risk-opinion-reply-viewer.component';
import {NewNonFinacleCustomerAddEditComponent} from "./components/new-non-finacle-customer-add-edit/new-non-finacle-customer-add-edit.component";
import {PersonalInformationFormComponent} from './components/new-non-finacle-customer-add-edit/personal-information-form/personal-information-form.component';
import {BusinessInformationFormComponent} from './components/new-non-finacle-customer-add-edit/business-information-form/business-information-form.component';
import {CorporateInformationFormComponent} from './components/new-non-finacle-customer-add-edit/corporate-information-form/corporate-information-form.component';
import {CasPrintButtonComponent} from './components/cas-print-button/cas-print-button.component';
import {CommentDetailedViewComponent} from "./components/comment-detailed-view-modal/comment-detailed-view.component";
import {TableCommentViewerComponent} from './components/table-comment-viewer/table-comment-viewer.component';
import {ShowCribDetailsComponent} from './components/show-crib-details/show-crib-details.component';
import {TinyMceInlineEditorComponent} from "./components/tiny-mce-inline-editor/tiny-mce-inline-editor.component";
import {KalyptoDataViewComponent} from './components/kalypto-data-view/kalypto-data-view.component';
import {NgxSortableModule} from "ngx-sortable";
import { ChangeOrderComponent } from './components/change-order/change-order.component';
import {CommonPopupWithTinyMceEditorComponent} from "./components/common-popup-with-tiny-mce-editor/common-popup-with-tiny-mce-editor.component";
import { ScrollToTopComponent } from './components/scroll-to-top/scroll-to-top.component';
import {CreditCalculatorComponent} from "./components/credit-calculator/credit-calculator.component";
import {PersonalDetailBankComponent} from  "./components/personal-detail-bank/personal-detail-bank.component"
import {PersonalDetailFacilityComponent} from  "./components/personal-detail-facility/personal-detail-facility.component"
import {PersonalCustomerStatWrapperComponent } from './components/personal-customer-stat-wrapper/personal-customer-stat-wrapper.component';
import {CustomerStatOutstandingComponent} from "./components/personal-customer-stat-wrapper/components/customer-stat-outstanding/customer-stat-outstanding.component";
import {CustomerCurrentAccountDetailsComponent} from "./components/personal-customer-stat-wrapper/components/customer-current-account-details/customer-current-account-details.component";
import {CustomerDepositsDetailsComponent} from "./components/personal-customer-stat-wrapper/components/customer-deposits-details/customer-deposits-details.component";
import {CustomerAdvanceDetailsComponent} from "./components/personal-customer-stat-wrapper/components/customer-advance-details/customer-advance-details.component";
import { CustomerComprehensiveStatisticsComponent } from './components/personal-customer-stat-wrapper/components/customer-comprehensive-statistics/customer-comprehensive-statistics.component';
import { CalculateTotalPipe } from './pipes/calculate-total.pipe';
import { CalculateAveragePipe } from './pipes/calculate-average.pipe';
import {NumberToWordsPipe} from "./pipes/number-to-words.pipe";
import { NumberCommaDirective } from './directives/number-comma.directive';
import { RiskOpinionHistoryViewerComponent } from './components/risk-opinion-history-viewer/risk-opinion-history-viewer.component';
import { GenerateCrcDialogComponent } from './components/generate-crc-dialog/generate-crc-dialog.component';
import {FacilityPaperUPCLabelComponent} from './components/facility-paper-upc-label/facility-paper-upc-label.component';
import { SaveStatusModalComponent } from './components/save-status-modal/save-status-modal.component';
import { CommitteeApproveRejectModalComponent } from './components/committee-approve-reject-modal/committee-approve-reject-modal.component';
// import { CommonAutoCompleteTextAreaComponent } from './components/common-autocomplete-textarea/common-autocomplete-textarea.component';
import { FacilityChangesModalComponent } from './components/facility-changes-modal/facility-changes-modal.component';
import { DaTableStructureComponent } from './components/da-table-structure/da-table-structure.component';
import { DaTableAddEditComponent } from '../views/pages/delegated-credit-authority/components/da-table-add-edit/da-table-add-edit.component';
import { DaTableChangeOrderComponent } from './components/da-table-structure/da-table-change-order/da-table-change-order.component';
import { CustomerGuaranteeVolumesComponent } from './components/personal-customer-stat-wrapper/components/customer-guarantee-volumes/customer-guarantee-volumes.component';
import { CustomerInsuranceValuationComponent } from './components/personal-customer-stat-wrapper/components/customer-insurance-valuation/customer-insurance-valuation.component';
import { CustomerKalyptoDetailsComponent } from './components/personal-customer-stat-wrapper/components/customer-kalypto-details/customer-kalypto-details.component';
import { CustomerExportTurnoverComponent } from './components/personal-customer-stat-wrapper/components/customer-export-turnover/customer-export-turnover.component';
import {CustomerImportTurnoverComponent} from './components/personal-customer-stat-wrapper/components/customer-import-turnover/customer-import-turnover.component';
import { ViewCribContentComponent } from './components/view-crib-content/view-crib-content.component';
import { SmeQuestionsComponent } from '../views/pages/sme/components/sme-questions/sme-questions.component';
import { SmeServiceService } from '../views/pages/sme/service/sme-service.service';
import { SmeAnswersComponent } from '../views/pages/sme/components/sme-answers/sme-answers.component';
import { FpCustomerFloatViewComponent } from './components/fp-customer-float-view/fp-customer-float-view.component';

import { EsgAnnexuresCommonComponent } from './components/esg/esg-annexures-common/esg-annexures-common.component';
import { EsgAnnexureSelectorsCommonComponent } from './components/esg/esg-annexure-selectors-common/esg-annexure-selectors-common.component';
import { ShowEsgInstructionsComponent } from './components/show-esg-instructions/show-esg-instructions.component';
import { EsgAnnexureAttachmentComponent } from './components/esg/esg-annexure-attachment/esg-annexure-attachment.component';
import { EsgViewAttachmentComponent } from './components/esg/esg-view-attachment/esg-view-attachment.component';
import { SaveRiskOpinionReplyComponent } from './components/esg/save-risk-opinion-reply/save-risk-opinion-reply.component';
import { EsgConfirmScoreComponent } from './components/esg/esg-confirm-score/esg-confirm-score.component';
import { EsgAnnexuresViewComponent } from './components/esg/esg-annexures-view/esg-annexures-view.component';
import { EsgAnnexureViewComponent } from './components/esg/esg-annexure-view/esg-annexure-view.component';
import { EsgSummaryViewComponent } from './components/esg/esg-summary-view/esg-summary-view.component';
import { FieldsetModule } from "primeng/fieldset";
import { SdConfirmationDialogComponent } from './components/sd-confirmation-dialog/sd-confirmation-dialog.component';
import { ViewAnalyticsDecisionComponent } from './components/view-analytics-decision/view-analytics-decision.component';

@NgModule({
  declarations: [
    CapitalizeDirective,
    FocusInputDirective,
    GoBackDirective,
    HasAnyPrivilegeDirective,
    StickyDirective,
    JoinPipe,
    GetObjectPipe,
    SafePipe,
    FirstLetterPipe,
    SafeUrlPipe,
    SafeHtmlPipe,
    PaginatorComponent,
    TrackebleInfoComponent,
    ConfirmationDialogComponent,
    TabsComponent,
    TabComponent,
    RichTextEditorComponent,
    ListToPaginatorComponent,
    TreeViewNodesComponent,
    ViewNodeComponent,
    AnimatedCounterComponent,
    InformationDialogComponent,
    TextAreaAutoHeightDirective,
    JsonViewerComponent,
    FromToDateDialogComponent,
    CommentDialogComponent,
    ReviewerCommentDialogComponent,
    GenerateBccDialogComponent,
    FacilityPaperCopyDialogComponent,
    TinyMceEditorComponent,
    HtmlContentViewerComponent,
    FacilityPaperReviewerLabelComponent,
    FacilityPaperDateCountLabelComponent,
    ShowCribHistoryComponent,
    BlockedByUPMsDirective,
    ApplicationFormCopyDialogComponent,
    CommonForwardComponent,
    CommonReleaseComponent,
    CommonAttendComponent,
    CommentWithViewOptionsDialogComponent,
    CommonUserCommentComponent,
    RiskOpinionReplyViewerComponent,
    NewNonFinacleCustomerAddEditComponent,
    PersonalInformationFormComponent,
    BusinessInformationFormComponent,
    CorporateInformationFormComponent,
    CasPrintButtonComponent,
    CommentDetailedViewComponent,
    TableCommentViewerComponent,
    ShowCribDetailsComponent,
    TinyMceInlineEditorComponent,
    KalyptoDataViewComponent,
    ChangeOrderComponent,
    CommonPopupWithTinyMceEditorComponent,
    ScrollToTopComponent,
    CreditCalculatorComponent,
    PersonalDetailBankComponent,
    PersonalDetailFacilityComponent,
    PersonalCustomerStatWrapperComponent,
    CustomerStatOutstandingComponent,
    CustomerCurrentAccountDetailsComponent,
    CustomerDepositsDetailsComponent,
    CustomerAdvanceDetailsComponent,
    CustomerComprehensiveStatisticsComponent,
    CalculateTotalPipe,
    CalculateAveragePipe,
    NumberToWordsPipe,
    NumberCommaDirective,
    RiskOpinionHistoryViewerComponent,
    GenerateCrcDialogComponent,
    FacilityPaperUPCLabelComponent,
    FacilityChangesModalComponent,
    FacilityPaperUPCLabelComponent,
    DaTableStructureComponent,
    DaTableAddEditComponent,
    SaveStatusModalComponent,
    CommitteeApproveRejectModalComponent,
    DaTableChangeOrderComponent,
    CustomerExportTurnoverComponent,
    CustomerInsuranceValuationComponent,
    CustomerGuaranteeVolumesComponent,
    CustomerKalyptoDetailsComponent,
    CustomerImportTurnoverComponent,
    ViewCribContentComponent,
    SmeQuestionsComponent,
    SmeAnswersComponent,
    FpCustomerFloatViewComponent,
    FpCustomerFloatViewComponent,
    EsgAnnexuresCommonComponent,
    EsgAnnexureSelectorsCommonComponent,
    ShowEsgInstructionsComponent,
    EsgAnnexureAttachmentComponent,
    EsgViewAttachmentComponent,
    SaveRiskOpinionReplyComponent,
    EsgConfirmScoreComponent,
    EsgAnnexuresViewComponent,
    EsgAnnexureViewComponent,
    EsgSummaryViewComponent,
    SdConfirmationDialogComponent,
    ViewAnalyticsDecisionComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MdbImports,
    NgxPrintModule,
    NgxSortableModule,
    FieldsetModule,
    TreeModule.forRoot()
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    CapitalizeDirective,
    FocusInputDirective,
    GoBackDirective,
    StickyDirective,
    HasAnyPrivilegeDirective,
    BlockedByUPMsDirective,
    TextAreaAutoHeightDirective,
    JoinPipe,
    GetObjectPipe,
    SafePipe,
    SafeHtmlPipe,
    FirstLetterPipe,
    SafeUrlPipe,
    MdbImports,
    PaginatorComponent,
    TrackebleInfoComponent,
    TreeModule,
    TabsComponent,
    TabComponent,
    RichTextEditorComponent,
    ListToPaginatorComponent,
    TreeViewNodesComponent,
    AnimatedCounterComponent,
    JsonViewerComponent,
    TinyMceEditorComponent,
    FacilityPaperReviewerLabelComponent,
    FacilityPaperDateCountLabelComponent,
    CommonUserCommentComponent,
    RiskOpinionReplyViewerComponent,
    NewNonFinacleCustomerAddEditComponent,
    CasPrintButtonComponent,
    NgxPrintModule,
    NgxSortableModule,
    CommentDetailedViewComponent,
    TableCommentViewerComponent,
    TinyMceInlineEditorComponent,
    KalyptoDataViewComponent,
    CommonPopupWithTinyMceEditorComponent,
    ScrollToTopComponent,
    CreditCalculatorComponent,
    PersonalDetailBankComponent,
    PersonalDetailFacilityComponent,
    PersonalCustomerStatWrapperComponent,
    NumberToWordsPipe,
    NumberCommaDirective,
    CustomerCurrentAccountDetailsComponent,
    CustomerComprehensiveStatisticsComponent,
    CustomerStatOutstandingComponent,
    CustomerAdvanceDetailsComponent,
    CustomerDepositsDetailsComponent,
    RiskOpinionHistoryViewerComponent,
    FacilityPaperUPCLabelComponent,
    DaTableStructureComponent,
    DaTableAddEditComponent,
    DaTableChangeOrderComponent,
    CustomerInsuranceValuationComponent,
    CustomerGuaranteeVolumesComponent,
    CustomerKalyptoDetailsComponent,
    CustomerExportTurnoverComponent,
    CustomerImportTurnoverComponent,
    SmeQuestionsComponent,
    SmeAnswersComponent,
    FpCustomerFloatViewComponent,
    EsgAnnexuresCommonComponent,
    EsgViewAttachmentComponent,
    EsgAnnexuresViewComponent,
    EsgAnnexureViewComponent,
    EsgSummaryViewComponent,
    FieldsetModule,
    ViewAnalyticsDecisionComponent
  ],
  providers: [NumberToWordsPipe, NumberCommaDirective, SmeServiceService],
  entryComponents: [
    ConfirmationDialogComponent,
    ViewNodeComponent,
    InformationDialogComponent,
    JsonViewerComponent,
    FromToDateDialogComponent,
    CommentDialogComponent,
    ReviewerCommentDialogComponent,
    GenerateBccDialogComponent,
    FacilityPaperCopyDialogComponent,
    HtmlContentViewerComponent,
    ShowCribHistoryComponent,
    ApplicationFormCopyDialogComponent,
    CommonForwardComponent,
    CommonReleaseComponent,
    CommonAttendComponent,
    CommentWithViewOptionsDialogComponent,
    RiskOpinionReplyViewerComponent,
    NewNonFinacleCustomerAddEditComponent,
    CommentDetailedViewComponent,
    ShowCribDetailsComponent,
    ChangeOrderComponent,
    CommonPopupWithTinyMceEditorComponent,
    CreditCalculatorComponent,
    RiskOpinionHistoryViewerComponent,
    GenerateCrcDialogComponent,
    DaTableAddEditComponent,
    DaTableChangeOrderComponent,
    CustomerKalyptoDetailsComponent,
    EsgAnnexureSelectorsCommonComponent,
    ShowEsgInstructionsComponent,
    SaveRiskOpinionReplyComponent,
    EsgConfirmScoreComponent,
    SdConfirmationDialogComponent
  ]
})
export class SharedModule {
}
