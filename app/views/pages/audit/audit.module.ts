import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuditRoutingModule} from './audit-routing.module';
import {AuditComponent} from './components/audit/audit.component';
import {AuditService} from "./services/audit.service";
import {SharedModule} from "../../../shared/shared.module";
import {AuditContentComponent} from './components/audit-content/audit-content.component';
import {MDBBootstrapModulesPro, MDBSpinningPreloader} from "ng-uikit-pro-standard";


@NgModule({
  declarations: [AuditComponent, AuditContentComponent],
  imports: [
    SharedModule,
    AuditRoutingModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  exports: [AuditContentComponent],
  entryComponents: [
    AuditContentComponent
  ],
  providers: [AuditService,
    MDBSpinningPreloader]
})
export class AuditModule {
}
