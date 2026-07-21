import {NgModule} from '@angular/core';

import {Customer360RoutingModule} from './customer-360-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import {CustomerBaseComponent} from './components/customer-base/customer-base.component';
import {CustomerBaseService} from "./services/customer-base.service";
import {PersonalDetailsComponent} from './components/customer-base/components/personal-details/personal-details.component';
import {AccountDetailListComponent} from './components/customer-base/components/account-detail-list/account-detail-list.component';
import {FinacleFacilityDetailsComponent} from './components/customer-base/components/finacle-facility-details/finacle-facility-details.component';
import {CasFacilityPaperListComponent} from './components/customer-base/components/cas-facility-paper-list/cas-facility-paper-list.component';
import {LeadHistoryListComponent} from './components/customer-base/components/lead-history-list/lead-history-list.component';
import {KaliptoDetailComponent} from './components/customer-base/components/kalipto-detail/kalipto-detail.component';
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import {NewFacilityPaperDialogComponent} from './components/customer-base/components/support/new-facility-paper-dialog/new-facility-paper-dialog.component';
import { CribReportComponent } from './components/customer-base/components/crib-report/crib-report.component';
@NgModule({
  declarations: [
    CustomerBaseComponent,
    PersonalDetailsComponent,
    AccountDetailListComponent,
    FinacleFacilityDetailsComponent,
    CasFacilityPaperListComponent,
    LeadHistoryListComponent,
    KaliptoDetailComponent,
    NewFacilityPaperDialogComponent,
    CribReportComponent,
  ],
  imports: [
    SharedModule,
    Customer360RoutingModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  providers: [
    CustomerBaseService
  ],
  entryComponents: [
    NewFacilityPaperDialogComponent,
  ]
})
export class Customer360Module {
}
