import { NgModule } from "@angular/core";
import { CovenantRoutingModule } from "./covenant-routing.module";
import { CovenantSharedModule } from "./covenant-shared.module";

@NgModule({
  imports: [CovenantSharedModule, CovenantRoutingModule],
  exports: [CovenantSharedModule],
})
export class CovenantModule {}
