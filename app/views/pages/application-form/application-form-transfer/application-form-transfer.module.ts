import {NgModule} from '@angular/core';

import {ApplicationFormTransferRoutingModule} from './application-form-transfer-routing.module';
import {ApplicationFormTransferSearchComponent} from './components/application-form-transfer-search/application-form-transfer-search.component';
import {ApplicationFormTransferService} from "./services/application-form-transfer.service";
import {SharedModule} from "../../../../shared/shared.module";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import {ApplicationFormTransferComponent} from './components/application-form-transfer/application-form-transfer.component';
import {ApplicationFormPreviewModule} from "../preview-components/application-form-preview.module";
import {ApplicationFormAddEditModule} from "../application-form-add-edit/application-form-add-edit.module";
//import {ApfLpsBasicDetailsComponent} from "../application-form-add-edit/components/application-form-add-edit-base/tab-components/apf-lps-screen/apf-lps-basic-details/apf-lps-basic-details.component";
@NgModule({
  declarations: [
  ApplicationFormTransferSearchComponent,
  ApplicationFormTransferComponent,
  ],
  providers: [ApplicationFormTransferService],
  imports: [
    SharedModule,
    ApplicationFormPreviewModule,
    MDBBootstrapModulesPro.forRoot(),
    ApplicationFormTransferRoutingModule,
    ApplicationFormAddEditModule,
  ]
})

export class ApplicationFormTransferModule {
}

