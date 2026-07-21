import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFormAddEditService} from "./services/application-form-add-edit.service";
import {ApplicationFormAddEditBaseComponent} from "./components/application-form-add-edit-base/application-form-add-edit-base.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFormAddEditService
    },
    component: ApplicationFormAddEditBaseComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationFormAddEditRoutingModule {
}
