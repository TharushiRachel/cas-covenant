import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFormTransferSearchComponent} from "./components/application-form-transfer-search/application-form-transfer-search.component";
import {ApplicationFormTransferService} from "./services/application-form-transfer.service";
import {ApplicationFormTransferComponent} from "./components/application-form-transfer/application-form-transfer.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFormTransferService
    },
    component: ApplicationFormTransferSearchComponent
  },
  {
    path: 'transfer-paper',
    resolve: {
      data: ApplicationFormTransferService
    },
    component: ApplicationFormTransferComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationFormTransferRoutingModule {
}
