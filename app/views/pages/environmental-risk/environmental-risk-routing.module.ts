import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { EnvironmentalRiskComponent } from "./components/environmental-risk/environmental-risk.component";

const routes: Routes = [
  {
    path: "",
    component: EnvironmentalRiskComponent,
    data: {
      title: "Environmental Risk Tool",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EnvironmentalRiskRoutingModule {}
