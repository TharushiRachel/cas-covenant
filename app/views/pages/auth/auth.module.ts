import {ModuleWithProviders, NgModule} from '@angular/core';

import {AuthRoutingModule} from './auth-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import {AuthComponent} from './auth.component';
import {LoginComponent} from './login/login.component';
import {ButtonsModule, CardsModule, CheckboxModule, IconsModule, WavesModule} from "ng-uikit-pro-standard";
import { SsoInitComponent } from './sso-init/sso-init.component';


@NgModule({
  declarations: [AuthComponent, LoginComponent, SsoInitComponent],
  imports: [
    SharedModule,
    AuthRoutingModule,
  ],
  exports: [
    AuthComponent
  ]
})
export class AuthModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: AuthModule
    };
  }
}
