import {NgModule} from '@angular/core';

import {ApplicationFormRoutingModule} from './application-form-routing.module';
import {ApplicationFormComponent} from './components/application-form/application-form.component';
import {ApplicationFormService} from "./services/application-form.service";
import {SharedModule} from "../../../../shared/shared.module";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import { ApplicationFormDashboardComponent } from './components/application-form-dashboard/application-form-dashboard.component';
import { ApplicationFormCountBoxComponent } from './components/application-form-dashboard/application-form-count-box/application-form-count-box.component';


@NgModule({
  declarations: [
      ApplicationFormComponent,
      ApplicationFormDashboardComponent,
      ApplicationFormCountBoxComponent],
  imports: [
    SharedModule,
    MDBBootstrapModulesPro.forRoot(),
    ApplicationFormRoutingModule
  ],
  providers: [
    ApplicationFormService
  ],
})
export class ApplicationFormModule {
}
