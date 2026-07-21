import {NgModule} from '@angular/core';

import {ApplicationFormInboxRoutingModule} from './application-form-inbox-routing.module';
import {ApfApplicationFormsComponent} from './components/apf-application-forms/apf-application-forms.component';
import {ApplicationFromInboxService} from "./services/application-from-inbox.service";
import {SharedModule} from "../../../../shared/shared.module";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";


@NgModule({
  declarations: [ApfApplicationFormsComponent],
  providers: [
    ApplicationFromInboxService
  ],
  imports: [
    SharedModule,
    ApplicationFormInboxRoutingModule,
    MDBBootstrapModulesPro.forRoot(),
  ]
})
export class ApplicationFormInboxModule {
}
