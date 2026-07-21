import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { DeviationTypesComponent } from "./components/deviation-types/deviation-types.component";
import { DeviationsComponent } from "./components/deviations/deviations.component";

const routes: Routes = [
  {
    path: "",
    component: DeviationsComponent,
    data: {
      title: "Deviations",
    },
  },
  {
    path: "deviation-types",
    component: DeviationTypesComponent,
    data: {
      title: "Deviation Types",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeviationRoutingModule {}
