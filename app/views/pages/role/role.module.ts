import {NgModule} from '@angular/core';

import {RoleRoutingModule} from './role-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import {RolesComponent} from './components/roles/roles.component';
import {RoleAddEditComponent} from './components/role-add-edit/role-add-edit.component';
import {RolesService} from "./services/roles.service";
import {RoleAddEditService} from "./services/role-add-edit.service";


@NgModule({
  declarations: [RolesComponent, RoleAddEditComponent],
  imports: [
    SharedModule,
    RoleRoutingModule
  ],
  providers: [
    RolesService,
    RoleAddEditService
  ]
})
export class RoleModule {
}
