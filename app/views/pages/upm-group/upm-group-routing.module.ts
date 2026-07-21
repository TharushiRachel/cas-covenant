import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UpmGroupService} from "./services/upm-group.service";
import {UpmGroupAddEditService} from "./services/upm-group-add-edit.service";
import {UpmGroupAddEditComponent} from "./components/upm-group-add-edit/upm-group-add-edit.component";
import {UpmGroupComponent} from "./components/upm-group/upm-group.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: UpmGroupService
    },
    component: UpmGroupComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: UpmGroupAddEditService
    },
    component: UpmGroupAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UpmGroupRoutingModule {
}
