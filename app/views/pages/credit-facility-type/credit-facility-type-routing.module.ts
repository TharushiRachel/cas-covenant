import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CreditFacilityTypesService} from "./services/credit-facility-types.service";
import {CreditFacilityTypesComponent} from "./components/credit-facility-types/credit-facility-types.component";
import {CreditFacilityTypeAddEditService} from "./services/credit-facility-type-add-edit.service";
import {CreditFacilityTypeAddEditComponent} from "./components/credit-facility-type-add-edit/credit-facility-type-add-edit.component";


const routes: Routes = [
  {
    path: '',
    resolve:{
      data: CreditFacilityTypesService
    },
    component:CreditFacilityTypesComponent
  },
  {
    path: 'add-edit',
    resolve:{
      data: CreditFacilityTypeAddEditService
    },
    component: CreditFacilityTypeAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreditFacilityTypeRoutingModule { }
