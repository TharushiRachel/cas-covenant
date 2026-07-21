import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFormCreateBaseComponent} from "./components/application-form-create-base/application-form-create-base.component";
import {ApplicationFormCreateService} from "./services/application-form-create.service";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFormCreateService
    },
    component: ApplicationFormCreateBaseComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    ApplicationFormCreateService
  ]
})
export class ApplicationFormCreateRoutingModule {
}
