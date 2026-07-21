import { NgModule } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { CoveringApprovalRoutingModule } from './covering-approval-routing.module';
import { MDBBootstrapModulePro, MDBSpinningPreloader, ModalModule } from 'ng-uikit-pro-standard';
import { SharedModule } from 'src/app/shared/shared.module';
import { CreateCoveringApprovalComponent } from './components/create-covering-approval/create-covering-approval.component';
import { CaCreationDetailsComponent } from './components/ca-creation-details/ca-creation-details.component';
import { CoveringApprovalDashboardComponent } from './components/covering-approval-dashboard/covering-approval-dashboard.component';
import { CoveringApprovalService } from './services/covering-approval.service';
import { CoveringApprovalCountBoxComponent } from './components/covering-approval-dashboard/covering-approval-count-box/covering-approval-count-box.component';
import { CaRequestApprovalComponent } from './components/ca-request-approval/ca-request-approval.component';
import { CaCreationDetailsDraftComponent } from './components/ca-creation-details-draft/ca-creation-details-draft.component';
import { CaDetailsAboutComponent } from './components/ca-details-about/ca-details-about.component';
import { CaDetailsCommentComponent } from './components/ca-details-comment/ca-details-comment.component';
import { CovPendingDashboardComponent } from './components/cov-pending-dashboard/cov-pending-dashboard.component';
import { CovAccountStatisticsComponent } from './components/cov-personal-customer-stat-wrapper/components/cov-account-statistics/cov-account-statistics.component';
import { CovAdvancesDetailsComponent } from './components/cov-personal-customer-stat-wrapper/components/cov-advances-details/cov-advances-details.component';
import { CovDepositsDetailsComponent } from './components/cov-personal-customer-stat-wrapper/components/cov-deposits-details/cov-deposits-details.component';
import { CovAccountsDetailsComponent } from './components/cov-personal-customer-stat-wrapper/components/cov-accounts-details/cov-accounts-details.component';
import { CovCreationDetailsComponent } from './components/cov-creation-details/cov-creation-details.component';
import { CovPendingCommentComponent } from './components/cov-pending-comment/cov-pending-comment.component';
import { CovSubmitComponent } from './components/cov-submit/cov-submit.component';
import { CovReturnUserSelectionComponent } from './components/cov-return-user-selection/cov-return-user-selection.component';


@NgModule({
  declarations: [
    CoveringApprovalDashboardComponent,
    CoveringApprovalCountBoxComponent,
    CreateCoveringApprovalComponent,
    CaCreationDetailsComponent,
    CaRequestApprovalComponent,
    CaCreationDetailsDraftComponent,
    CaDetailsAboutComponent,
    CaDetailsCommentComponent,
    CovPendingDashboardComponent,
    CovAccountStatisticsComponent,
    CovAdvancesDetailsComponent,
    CovDepositsDetailsComponent,
    CovAccountsDetailsComponent,
    CovCreationDetailsComponent, 
    CovPendingCommentComponent, CovSubmitComponent, CovReturnUserSelectionComponent,

  ],
  imports: [
    CommonModule,
    SharedModule,
    CoveringApprovalRoutingModule,
    ModalModule.forRoot(),
    MDBBootstrapModulePro.forRoot(),

  ],
  entryComponents: [
    CreateCoveringApprovalComponent,
    CaCreationDetailsDraftComponent,
    CovAccountStatisticsComponent,
    CovAdvancesDetailsComponent,
    CovDepositsDetailsComponent,
    CovAccountsDetailsComponent,
    CovPendingCommentComponent,
    CovSubmitComponent,
    CovReturnUserSelectionComponent
  ],
  providers: [
    CoveringApprovalService,
    DatePipe,
    MDBSpinningPreloader,
    CurrencyPipe,

  ]
})
export class CoveringApprovalModule { }
