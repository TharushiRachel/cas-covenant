import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserDelegatedAuthoritiesService} from "./services/user-delegated-authorities.service";
import {UserDelegatedAuthoritiesComponent} from "./components/user-delegated-authorities/user-delegated-authorities.component";
import {UserDelegatedAuthorityAddEditService} from "./services/user-delegated-authority-add-edit.service";
import {UserDelegatedAuthorityAddEditComponent} from "./components/user-delegated-authority-add-edit/user-delegated-authority-add-edit.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: UserDelegatedAuthoritiesService
    },
    component: UserDelegatedAuthoritiesComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: UserDelegatedAuthorityAddEditService
    },
    component: UserDelegatedAuthorityAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserDelegatedAuthorityRoutingModule { }
