import {NgModule} from '@angular/core';

import {FacilityPaperTransferRoutingModule} from './facility-paper-transfer-routing.module';
import {SearchFacilityPaperComponent} from './components/search-facility-paper/search-facility-paper.component';
import {TransferFacilityPaperComponent} from './components/transfer-facility-paper/transfer-facility-paper.component';
import {FacilityPaperTransferService} from "./services/facility-paper-transfer.service";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import {PreviewModule} from "../../preview/preview.module";
import {SharedModule} from "../../../shared/shared.module";
import {FacilityPaperTransferSearchService} from "./services/facility-paper-transfer-search.service";
import {CurrencyPipe} from "@angular/common";
import {FpTransferToUserComponent} from './components/transfer-facility-paper/support-components/fp-transfer-to-user/fp-transfer-to-user.component';


@NgModule({
  declarations: [
    SearchFacilityPaperComponent,
    TransferFacilityPaperComponent,
    FpTransferToUserComponent],
  imports: [
    PreviewModule,
    SharedModule,
    FacilityPaperTransferRoutingModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  entryComponents: [
    FpTransferToUserComponent,
  ],
  providers: [
    FacilityPaperTransferService,
    FacilityPaperTransferSearchService,
    CurrencyPipe
  ]
})
export class FacilityPaperTransferModule {
}
