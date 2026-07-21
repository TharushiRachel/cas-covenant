import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LeadsService} from "../lead/services/leads.service";
import {MyBranchLeadsComponent} from "./components/my-branch-leads/my-branch-leads.component";


const routes: Routes = [
  {
    path: '',
    resolve:{
      data: LeadsService
    },
    component: MyBranchLeadsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyBranchLeadsRoutingModule { }
