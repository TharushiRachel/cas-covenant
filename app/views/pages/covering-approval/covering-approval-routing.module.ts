import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoveringApprovalDashboardComponent } from './components/covering-approval-dashboard/covering-approval-dashboard.component';
import { CaCreationDetailsComponent } from './components/ca-creation-details/ca-creation-details.component';
import { CoveringApprovalService } from './services/covering-approval.service';
import { CaRequestApprovalComponent } from './components/ca-request-approval/ca-request-approval.component';


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: CoveringApprovalService
    },
    component: CoveringApprovalDashboardComponent
  },
  {
    path: 'creation',
    component: CaCreationDetailsComponent
  },
  {
    path: 'dashboard',
    resolve: {
      data: CoveringApprovalService
    },
    component: CoveringApprovalDashboardComponent
  },
  {
    path: 'request-approval',
    resolve: {
      data: CoveringApprovalService
    },
    component: CaRequestApprovalComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoveringApprovalRoutingModule { }
