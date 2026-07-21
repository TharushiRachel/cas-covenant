import { NgModule } from '@angular/core';


import { CreditFacilityTypeRoutingModule } from './credit-facility-type-routing.module';
import { CreditFacilityTypesComponent } from './components/credit-facility-types/credit-facility-types.component';
import {CreditFacilityTypesService} from "./services/credit-facility-types.service";
import {SharedModule} from "../../../shared/shared.module";
import { CreditFacilityTypeAddEditComponent } from './components/credit-facility-type-add-edit/credit-facility-type-add-edit.component';
import {CreditFacilityTypeAddEditService} from "./services/credit-facility-type-add-edit.service";


@NgModule({
  declarations: [CreditFacilityTypesComponent, CreditFacilityTypeAddEditComponent],
  imports: [
    SharedModule,
    CreditFacilityTypeRoutingModule
  ],
  providers:[
    CreditFacilityTypesService,
    CreditFacilityTypeAddEditService
  ]
})
export class CreditFacilityTypeModule { }
