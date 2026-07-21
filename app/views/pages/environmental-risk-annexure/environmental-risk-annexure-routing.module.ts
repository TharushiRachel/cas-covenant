import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { EnvironmentalRiskAnnexureComponent } from "./components/environmental-risk-annexure/environmental-risk-annexure.component";
import { AddEditEnvironmentalRiskAnnexureComponent } from "./components/add-edit-environmental-risk-annexure/add-edit-environmental-risk-annexure.component";

const routes: Routes = [
  {
    path: "",
    component: EnvironmentalRiskAnnexureComponent,
    data: {
      title: "Environmental Risk Annexure",
    },
  },
  {
    path: "add-edit-annexure",
    component: AddEditEnvironmentalRiskAnnexureComponent,
    data: {
      title: "Add/Edit Environmental Risk Annexure",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EnvironmentalRiskAnnexureRoutingModule {}
