import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuditService} from "./services/audit.service";
import {AuditComponent} from "./components/audit/audit.component";


const routes: Routes = [
  {
    path:'',
    resolve: {
      data: AuditService
    },
    component: AuditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuditRoutingModule { }
