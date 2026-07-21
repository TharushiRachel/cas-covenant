import {NgModule} from '@angular/core';

import {ApplicationFormCopyRoutingModule} from './application-form-copy-routing.module';
import {SharedModule} from "../../../../shared/shared.module";
import {ApplicationFormPreviewModule} from "../preview-components/application-form-preview.module";
import {NgxSortableModule} from "ngx-sortable";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import {ApplicationFormCopyBaseComponent} from './components/application-form-copy-base/application-form-copy-base.component';
import {AuditService} from "../../audit/services/audit.service";
import {CurrencyPipe} from "@angular/common";
import {ApplicationFromCopyService} from "./services/application-from-copy.service";


@NgModule({
  declarations: [ApplicationFormCopyBaseComponent],
  imports: [
    SharedModule,
    ApplicationFormPreviewModule,
    ApplicationFormCopyRoutingModule,
    NgxSortableModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  providers: [
    ApplicationFromCopyService,
    AuditService,
    CurrencyPipe
  ]
})
export class ApplicationFormCopyModule {
}
