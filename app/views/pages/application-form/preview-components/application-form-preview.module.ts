import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from "../../../../shared/shared.module";
import {CacheService} from "../../../../core/service/data/cache.service";
import {ApfBasicInformationPreviewComponent} from "./apf-basic-information-preview/apf-basic-information-preview.component";
import {ApfFinancialObligationsPreviewComponent} from "./apf-basic-information-preview/apf-financial-obligations-preview/apf-financial-obligations-preview.component";
import {ApfBasicPersonalInfoPreviewComponent} from "./apf-basic-information-preview/apf-basic-personal-info-preview/apf-basic-personal-info-preview.component";
import {ApfBasicCorporateInfoPreviewComponent} from "./apf-basic-information-preview/apf-basic-corporate-info-preview/apf-basic-corporate-info-preview.component";
import {ApfBasicBusinessInfoPreviewComponent} from "./apf-basic-information-preview/apf-basic-business-info-preview/apf-basic-business-info-preview.component";
import {ApfCribPreviewComponent} from "./apf-crib-preview/apf-crib-preview.component";
import {ApfDocumentsPreviewComponent} from "./apf-documents-preview/apf-documents-preview.component";
import {ApfFacilitiesPreviewComponent} from "./apf-facilities-preview/apf-facilities-preview.component";
import {ApfFacilityDataPreviewComponent} from "./apf-facilities-preview/apf-facility-data-preview/apf-facility-data-preview.component";
import {ApfFacilitiesCommonSecuritiesComponent} from "./apf-facilities-preview/apf-facilities-common-securities/apf-facilities-common-securities.component";
import {ApfRiskRatingPreviewComponent} from "./apf-basic-information-preview/apf-risk-rating-preview/apf-risk-rating-preview.component";
import {ApfSecuritiesPreviewComponent} from "./apf-securities-preview/apf-securities-preview.component";
import {ApfTopicsPreviewComponent} from "./apf-topics-preview/apf-topics-preview.component";
import {ApfSecuritiesRowPreviewComponent} from './apf-securities-preview/apf-securities-row-preview/apf-securities-row-preview.component';
import {ApfDirectorDetailsPreviewComponent} from './apf-basic-information-preview/apf-director-details-preview/apf-director-details-preview.component';
import {ApfOtherBankDetailsPreviewComponent} from './apf-basic-information-preview/apf-other-bank-details-preview/apf-other-bank-details-preview.component';
import {ApfFacilityDocumentPreviewComponent} from './apf-facilities-preview/apf-facility-data-preview/apf-facility-document-preview/apf-facility-document-preview.component';
import {ApfStatusHistoryPreviewComponent} from './apf-status-history-preview/apf-status-history-preview.component';

@NgModule({
  declarations: [
    ApfBasicInformationPreviewComponent,
    ApfFinancialObligationsPreviewComponent,
    ApfBasicPersonalInfoPreviewComponent,
    ApfBasicCorporateInfoPreviewComponent,
    ApfBasicBusinessInfoPreviewComponent,
    ApfFacilitiesCommonSecuritiesComponent,
    ApfCribPreviewComponent,
    ApfDocumentsPreviewComponent,
    ApfFacilitiesPreviewComponent,
    ApfFacilityDataPreviewComponent,
    ApfRiskRatingPreviewComponent,
    ApfSecuritiesPreviewComponent,
    ApfTopicsPreviewComponent,
    ApfSecuritiesRowPreviewComponent,
    ApfDirectorDetailsPreviewComponent,
    ApfOtherBankDetailsPreviewComponent,
    ApfFacilityDocumentPreviewComponent,
    ApfStatusHistoryPreviewComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
  ],
  entryComponents: [
    ApfFacilityDocumentPreviewComponent
  ],
  exports: [
    ApfBasicInformationPreviewComponent,
    ApfFinancialObligationsPreviewComponent,
    ApfBasicPersonalInfoPreviewComponent,
    ApfBasicCorporateInfoPreviewComponent,
    ApfBasicBusinessInfoPreviewComponent,
    ApfFacilitiesCommonSecuritiesComponent,
    ApfCribPreviewComponent,
    ApfDocumentsPreviewComponent,
    ApfFacilitiesPreviewComponent,
    ApfFacilityDataPreviewComponent,
    ApfRiskRatingPreviewComponent,
    ApfSecuritiesPreviewComponent,
    ApfTopicsPreviewComponent,
    ApfDirectorDetailsPreviewComponent,
    ApfOtherBankDetailsPreviewComponent,
    ApfFacilityDocumentPreviewComponent,
    ApfStatusHistoryPreviewComponent
  ],
  providers: [
    CacheService
  ],
})
export class ApplicationFormPreviewModule {
}
