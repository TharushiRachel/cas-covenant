import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
//import {ApplicationFormService} from "./services/application-form.service";
//import {ApplicationFormComponent} from "./components/application-form/application-form.component";
import {CommitteePaperService} from "./services/committee-paper.service";
import { CommitteePaperDashboardComponent } from './components/committee-paper-dashboard/committee-paper-dashboard.component';

const routes: Routes = [
  {
   path: '',
     resolve: {
       data: CommitteePaperService
     },
     component: CommitteePaperDashboardComponent
  },
   {
     path: 'dashboard',
       resolve: {
         data: CommitteePaperService
       },
       component: CommitteePaperDashboardComponent
    },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommitteePaperRoutingModule {
}
