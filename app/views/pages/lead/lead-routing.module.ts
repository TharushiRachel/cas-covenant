import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LeadsComponent} from "./components/leads/leads.component";
import {LeadsService} from "./services/leads.service";
import {LeadAddEditService} from "./services/lead-add-edit.service";
import {LeadAddEditComponent} from "./components/lead-add-edit/lead-add-edit.component";
import {LeadFacilityDetailComponent} from "./components/lead-facility-detail/lead-facility-detail.component";
import {LeadAuditDetailComponent} from "./components/lead-audit-detail/lead-audit-detail.component";
import {AuditService} from "../audit/services/audit.service";
import { LeadCreateComponent } from "./components/lead-create/lead-create.component";
import { LeadSearchService } from './services/lead-search.service';
import { LeadSearchComponent } from './components/lead-search/lead-search.component';
import { LeadDashboardComponent } from './components/lead-dashboard/lead-dashboard.component';
import { LeadComprehensiveCreateComponent } from './components/lead-comprehensive-create/lead-comprehensive-create.component';
import { LeadComprehensiveService } from './services/lead-comprehensive.service';


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: LeadsService
    },
    component: LeadsComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: LeadAddEditService
    },
    component: LeadAddEditComponent,

  },
  {
    path: 'lead-facility-detail',
    resolve: {
      data: LeadAddEditService
    },
    component: LeadFacilityDetailComponent
  },
  {
    path: 'lead-audit-detail',
    resolve: {
      data: AuditService
    },
    component: LeadAuditDetailComponent
  },
  {
    path: 'create',
    resolve: {
      data: LeadAddEditService
    },
    component: LeadCreateComponent
  },
   {
    path: 'comprehensive-create',
    resolve: {
      data: LeadComprehensiveService
    },
    component: LeadComprehensiveCreateComponent
  },
  {
    path: 'dashboard',
    resolve: {
      data: LeadsService
    },
    component: LeadDashboardComponent
  },
  {
    path: 'search',
    resolve: {
      data: LeadSearchService
    },
    component: LeadSearchComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LeadRoutingModule {
}
