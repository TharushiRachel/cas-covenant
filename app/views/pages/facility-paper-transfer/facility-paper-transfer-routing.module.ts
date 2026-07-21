import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TransferFacilityPaperComponent} from "./components/transfer-facility-paper/transfer-facility-paper.component";
import {SearchFacilityPaperComponent} from "./components/search-facility-paper/search-facility-paper.component";
import {FacilityPaperTransferService} from "./services/facility-paper-transfer.service";
import {FacilityPaperTransferSearchService} from "./services/facility-paper-transfer-search.service";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: FacilityPaperTransferSearchService
    },
    component: SearchFacilityPaperComponent
  },
  {
    path: 'transfer',
    resolve: {
      data: FacilityPaperTransferService
    },
    component: TransferFacilityPaperComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacilityPaperTransferRoutingModule {
}
