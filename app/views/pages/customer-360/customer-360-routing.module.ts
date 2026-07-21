import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CustomerBaseComponent} from "./components/customer-base/customer-base.component";
import {CustomerBaseService} from "./services/customer-base.service";

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: CustomerBaseService
    },
    component: CustomerBaseComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Customer360RoutingModule {
}
