import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BranchApplicationFormService} from "./services/branch-application-form.service";
import {BranchApplicationFormComponent} from "./components/branch-application-form/branch-application-form.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: BranchApplicationFormService
    },
    component: BranchApplicationFormComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BranchApplicationFormRoutingModule {
}
