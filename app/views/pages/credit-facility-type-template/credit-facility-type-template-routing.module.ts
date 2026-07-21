import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CreditFacilityTypeTemplatesService} from "./services/credit-facility-type-templates.service";
import {CreditFacilityTypeTemplatesComponent} from "./components/credit-facility-type-templates/credit-facility-type-templates.component";
import {CreditFacilityTemplateAddEditService} from "./services/credit-facility-template-add-edit.service";
import {CreditFacilityTemplateAddEditComponent} from "./components/credit-facility-template-add-edit/credit-facility-template-add-edit.component";
import {CftSupportingDocComponent} from "./components/cft-supporting-doc/cft-supporting-doc.component";


const routes: Routes = [
  {
    path:'',
    resolve:{
      data: CreditFacilityTypeTemplatesService
    },
    component: CreditFacilityTypeTemplatesComponent
  },
  {
    path: 'add-edit',
    resolve:{
      data: CreditFacilityTemplateAddEditService,
    },
    component: CreditFacilityTemplateAddEditComponent,

  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreditFacilityTypeTemplateRoutingModule { }
