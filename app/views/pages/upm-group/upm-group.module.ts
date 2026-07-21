import {NgModule} from '@angular/core';

import {UpmGroupRoutingModule} from './upm-group-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import {UpmGroupComponent} from './components/upm-group/upm-group.component';
import {UpmGroupAddEditComponent} from './components/upm-group-add-edit/upm-group-add-edit.component';
import {UpmGroupService} from "./services/upm-group.service";
import {UpmGroupAddEditService} from "./services/upm-group-add-edit.service";


@NgModule({
  declarations: [UpmGroupComponent, UpmGroupAddEditComponent],
  imports: [
    SharedModule,
    UpmGroupRoutingModule
  ],
  providers: [
    UpmGroupService,
    UpmGroupAddEditService
  ]
})
export class UpmGroupModule {
}
