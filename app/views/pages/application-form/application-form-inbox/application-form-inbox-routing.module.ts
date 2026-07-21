import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFromInboxService} from "./services/application-from-inbox.service";
import {ApfApplicationFormsComponent} from "./components/apf-application-forms/apf-application-forms.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFromInboxService
    },
    component: ApfApplicationFormsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationFormInboxRoutingModule {
}
