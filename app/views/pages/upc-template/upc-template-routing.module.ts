import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UpcTemplateService} from "./services/upc-template.service";
import {UpcTemplateComponent} from "./components/upc-template/upc-template.component";
import {UpcTemplateAddEditService} from "./services/upc-template-add-edit.service";
import {UpcTemplateAddEditComponent} from "./components/upc-template-add-edit/upc-template-add-edit.component";

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: UpcTemplateService
    },
    component: UpcTemplateComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: UpcTemplateAddEditService
    },
    component: UpcTemplateAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UpcTemplateRoutingModule {
}
