import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserDelegatedAuthorityRoutingModule } from './user-delegated-authority-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import { UserDelegatedAuthoritiesComponent } from './components/user-delegated-authorities/user-delegated-authorities.component';
import {UserDelegatedAuthoritiesService} from "./services/user-delegated-authorities.service";
import { UserDelegatedAuthorityAddEditComponent } from './components/user-delegated-authority-add-edit/user-delegated-authority-add-edit.component';
import {UserDelegatedAuthorityAddEditService} from "./services/user-delegated-authority-add-edit.service";


@NgModule({
  declarations: [UserDelegatedAuthoritiesComponent, UserDelegatedAuthorityAddEditComponent],
  imports: [
    SharedModule,
    UserDelegatedAuthorityRoutingModule
  ],
  providers:[
    UserDelegatedAuthoritiesService,
    UserDelegatedAuthorityAddEditService
  ]
})
export class UserDelegatedAuthorityModule { }
