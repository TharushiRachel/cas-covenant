import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { DeviationRoutingModule } from "./deviation-routing.module";
import { DeviationTypesComponent } from "./components/deviation-types/deviation-types.component";
import { DeviationsComponent } from "./components/deviations/deviations.component";
import { SaveDeviationTypeComponent } from "./modal/save-deviation-type/save-deviation-type.component";
import { SaveDeviationComponent } from "./modal/save-deviation/save-deviation.component";
import { SharedModule } from "src/app/shared/shared.module";
import { MDBBootstrapModule } from "ng-uikit-pro-standard";

@NgModule({
  declarations: [
    DeviationTypesComponent,
    DeviationsComponent,
    SaveDeviationTypeComponent,
    SaveDeviationComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    DeviationRoutingModule,
    MDBBootstrapModule.forRoot(),
  ],
  entryComponents: [SaveDeviationTypeComponent, SaveDeviationComponent],
})
export class DeviationModule {}
