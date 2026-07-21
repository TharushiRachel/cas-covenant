import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { V1DashboardComponent } from "./components/v1-dashboard/v1-dashboard.component";
import { CasV1Service } from "./services/cas-v1.service";

const routes: Routes = [
  {
    path: 'paper',
    data: {
      title: "CasV1 Paper",
    },
    component: V1DashboardComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CasV1RoutingModule { }
