import {NgModule} from '@angular/core';
import {BranchApplicationFormRoutingModule} from './branch-application-form-routing.module';
import {BranchApplicationFormComponent} from './components/branch-application-form/branch-application-form.component';
import {SharedModule} from "../../../../shared/shared.module";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import {BranchApplicationFormService} from "./services/branch-application-form.service";


@NgModule({
  declarations: [BranchApplicationFormComponent],
  providers: [
    BranchApplicationFormService
  ],
  imports: [
    SharedModule,
    MDBBootstrapModulesPro.forRoot(),
    BranchApplicationFormRoutingModule
  ]
})
export class BranchApplicationFormModule {
}
