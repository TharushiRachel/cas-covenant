import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RolesService} from "./services/roles.service";
import {RolesComponent} from "./components/roles/roles.component";
import {RoleAddEditService} from "./services/role-add-edit.service";
import {RoleAddEditComponent} from "./components/role-add-edit/role-add-edit.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: RolesService
    },
    component: RolesComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: RoleAddEditService
    },
    component: RoleAddEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RoleRoutingModule { }
