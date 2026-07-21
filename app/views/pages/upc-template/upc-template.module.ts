import {NgModule} from '@angular/core';

import {UpcTemplateRoutingModule} from './upc-template-routing.module';
import {UpcTemplateComponent} from './components/upc-template/upc-template.component';
import {UpcTemplateAddEditComponent} from './components/upc-template-add-edit/upc-template-add-edit.component';
import {SharedModule} from "../../../shared/shared.module";
import {UpcTemplateService} from "./services/upc-template.service";
import {UpcTemplateAddEditService} from "./services/upc-template-add-edit.service";
import { TemplateStructureComponent } from './components/upc-template-add-edit/template-structure/template-structure.component';


@NgModule({
  declarations: [UpcTemplateComponent, UpcTemplateAddEditComponent, TemplateStructureComponent],
  imports: [
    SharedModule,
    UpcTemplateRoutingModule
  ],
  providers: [
    UpcTemplateService,
    UpcTemplateAddEditService]
})
export class UpcTemplateModule {
}
