import {NgModule} from '@angular/core';
import {ApplicationFormCreateRoutingModule} from './application-form-create-routing.module';
import {ApplicationFormCreateBaseComponent} from './components/application-form-create-base/application-form-create-base.component';
import {SharedModule} from "../../../../shared/shared.module";
import {ApplicationFormAddEditModule} from "../application-form-add-edit/application-form-add-edit.module";
import {ApfCreateApplicationFormComponent} from "./components/support-components/apf-create-application-form/apf-create-application-form.component";


@NgModule({
  declarations: [
    ApplicationFormCreateBaseComponent,
    ApfCreateApplicationFormComponent
  ],
  imports: [
    SharedModule,
    ApplicationFormCreateRoutingModule,
    ApplicationFormAddEditModule
  ],
  entryComponents: [
    ApfCreateApplicationFormComponent
  ]
})
export class ApplicationFormCreateModule {
}
