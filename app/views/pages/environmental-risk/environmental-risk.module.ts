import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { EnvironmentalRiskRoutingModule } from "./environmental-risk-routing.module";
import { EnvironmentalRiskComponent } from "./components/environmental-risk/environmental-risk.component";
import { AddEditEnvironmentalRiskComponent } from "./components/add-edit-environmental-risk/add-edit-environmental-risk.component";
import { SharedModule } from "src/app/shared/shared.module";
import { MDBBootstrapModule, MDBModalRef } from "ng-uikit-pro-standard";
import { RiskTreeViewComponent } from "./components/risk-tree-view/risk-tree-view.component";
import { RiskConfirmationDialogComponent } from "./components/risk-confirmation-dialog/risk-confirmation-dialog.component";

@NgModule({
  declarations: [
    EnvironmentalRiskComponent,
    AddEditEnvironmentalRiskComponent,
    RiskTreeViewComponent,
    RiskConfirmationDialogComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    MDBBootstrapModule.forRoot(),
    EnvironmentalRiskRoutingModule,
  ],
  entryComponents: [
    AddEditEnvironmentalRiskComponent,
    RiskConfirmationDialogComponent,
  ],
  providers: [MDBModalRef],
})
export class EnvironmentalRiskModule {}
