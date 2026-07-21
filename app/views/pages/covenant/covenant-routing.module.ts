import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CustomerCovenantComponent } from "./components/customer-covenant/customer-covenant.component";
import { CovenantService } from "./services/covenant.service";

const routes: Routes = [
  {
    path: "",
    resolve: {
      data: CovenantService,
    },
    component: CustomerCovenantComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CovenantRoutingModule {}
