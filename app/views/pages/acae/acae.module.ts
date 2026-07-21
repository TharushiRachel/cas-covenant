import { NgModule } from '@angular/core';
import { ACAERoutingModule } from './acae-routing.module';
import { SharedModule } from "../../../shared/shared.module";
import { MDBBootstrapModulesPro } from "ng-uikit-pro-standard";
import { ACAEService } from "./services/acae-base.service";
import { ACAEDetailsComponent } from './components/acae-details/acae-details.component';
import { ACAEDetailsSearchComponent } from './components/acae-details-search/acae-details-search.component';
import { ACAESearchService } from './services/acae-search.service';
import { ACAEDetailsDateRangeInquiryComponent } from './components/acae-details-date-range-inquiry/acae-details-date-range-inquiry.component';
import { ACAEDateRangeInquiryService } from './services/acae-date-range-inquiry.service';
import { ACAEDetailsStatusInquiryComponent } from './components/acae-details-status-inquiry/acae-details-status-inquiry.component';
import { AcaeDetailsTransferSearchComponent } from './components/acae-details-transfer-search/acae-details-transfer-search.component';
import { ACAEStatusInquiryService } from './services/acae-status-inquiry.service';
import { ACAEDetailsMonitorComponent } from './components/acae-details-monitor/acae-details-monitor.component';
import { ACAEMonitorService } from './services/acae-monitor.service';
import { ACAEPaperDetailsComponent } from './components/acae-paper-details/acae-paper-details.component';
import { ACAEPaperService } from './services/acae-paper.service';
import { ACAECountBoxComponent } from './components/acae-details/acae-count-box/acae-count-box.component';
import { ACAEPaperTransferModelComponent } from './components/acae-paper-details/acae-paper-transfer-model/acae-paper-transfer-model.component';
import { ACAEEditStatusModelService } from './services/acae-edit-status-model.service';
import { ACAECustomerOutstandingComponentBkp } from './components/acae-paper-details/acae-customer-outstanding-details/acae-customer-outstanding-details.component';
import { CasCustomerService } from 'src/app/core/service/data/cas-customer.service';
import { ACAECustomerRealatedAccountComponentBkp } from './components/acae-paper-details/acae-customer-related-account-details/acae-customer-related-account-details.component';
import { ACAECustomerLoanAccountComponentBkp } from './components/acae-paper-details/acae-customer-loan-accounts-details/acae-customer-loan-accounts-details.component';
import { ACAESharedService } from './services/acae-shared.service';
import { MatCheckboxModule, MatExpansionModule, MatSelectModule, MatTableModule } from '@angular/material';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ACAEDetailsTransferSearchService } from './services/acae-details-transfer-search.service';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSortModule } from '@angular/material/sort';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AcaePaperRangeInquiryDetailsComponent } from './components/acae-paper-details/acae-paper-range-inquiry-details/acae-paper-range-inquiry-details.component';
import {ChipsModule} from 'primeng/chips';
import { AcaeBulkCommentModalComponent } from './components/acae-details/acae-bulk-comment-modal/acae-bulk-comment-modal.component';



@NgModule({
  declarations: [
    ACAEDetailsComponent,
    ACAEDetailsSearchComponent,
    ACAEDetailsDateRangeInquiryComponent,
    ACAEDetailsStatusInquiryComponent,
    ACAEDetailsMonitorComponent,
    ACAEPaperDetailsComponent,
    ACAECountBoxComponent,
    ACAEPaperTransferModelComponent,
    ACAECustomerOutstandingComponentBkp,
    ACAECustomerRealatedAccountComponentBkp,
    ACAECustomerLoanAccountComponentBkp,
    AcaeDetailsTransferSearchComponent,
    AcaePaperRangeInquiryDetailsComponent,
    AcaeBulkCommentModalComponent,
  ],
  imports: [
    SharedModule,
    ACAERoutingModule,
    MDBBootstrapModulesPro.forRoot(),
    MatExpansionModule,
    MatSelectModule,
    MatCheckboxModule,
    MatAutocompleteModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    MatNativeDateModule,
    MatTableModule,
    ReactiveFormsModule,
    MatSortModule,
    MatAutocompleteModule,
    ChipsModule
  ],
  providers: [
    ACAEService,
    ACAESearchService,
    ACAEDateRangeInquiryService,
    ACAEStatusInquiryService,
    ACAEMonitorService,
    ACAEPaperService,
    ACAEEditStatusModelService,
    CasCustomerService,
    ACAESharedService,
    ACAEDetailsTransferSearchService,
  ],
  entryComponents: [
    ACAEDetailsComponent,
    ACAEDetailsSearchComponent,
    ACAEDetailsDateRangeInquiryComponent,
    ACAEDetailsStatusInquiryComponent,
    ACAEDetailsMonitorComponent,
    ACAEPaperDetailsComponent,
    ACAEPaperTransferModelComponent,
    ACAECustomerOutstandingComponentBkp,
    ACAECustomerRealatedAccountComponentBkp,
    ACAECustomerLoanAccountComponentBkp,
    AcaeDetailsTransferSearchComponent,
    AcaePaperRangeInquiryDetailsComponent,
    AcaeBulkCommentModalComponent,
  ],
})
export class ACAEModule {
}
