import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFormService} from "./services/application-form.service";
import {ApplicationFormComponent} from "./components/application-form/application-form.component";
import { ApplicationFormDashboardComponent } from './components/application-form-dashboard/application-form-dashboard.component';

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFormService
    },
    component: ApplicationFormComponent
  },
  {
      path: 'dashboard',
      resolve: {
        data: ApplicationFormService
      },
      component: ApplicationFormDashboardComponent
    },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationFormRoutingModule {
}
