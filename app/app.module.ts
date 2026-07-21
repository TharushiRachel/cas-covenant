import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AgmCoreModule} from '@agm/core';
import {AppComponent} from './app.component';

import {MDBBootstrapModulesPro, MDBSpinningPreloader, ToastModule} from 'ng-uikit-pro-standard';
import {MainComponent} from './base/main/main.component';
import {SidebarComponent} from './base/sidebar/sidebar.component';
import {HeaderComponent} from './base/header/header.component';
import {LoadingComponent} from './base/loading/loading.component';
import {ContentComponent} from './base/content/content.component';
import {HomeComponent} from './base/home/home.component';
import {AppRouter} from './app.router';
import {SharedModule} from './shared/shared.module';
import {CurrencyPipe, DecimalPipe, HashLocationStrategy, LocationStrategy} from "@angular/common";
import {NgxUiLoaderModule} from "ngx-ui-loader";
import {NgxSortableModule} from "ngx-sortable";
import {CoreModule} from "./core/core.module";
import {NavGroupComponent} from './base/sidebar/nav-group/nav-group.component';
import {NavItemComponent} from './base/sidebar/nav-item/nav-item.component';
import {UserDetailDisplayComponent} from './base/header/user-detail-display/user-detail-display.component';
import {ChangePasswordComponent} from './base/header/change-password/change-password.component';
import {TokenTimeOutNotificationComponent} from './base/support/token-time-out-notification/token-time-out-notification.component';
import {BnNgIdleService} from "bn-ng-idle";
import { RiskOpinionHistoryComponent } from './views/pages/risk-opinion-history/risk-opinion-history.component';
import { CookieService } from 'ngx-cookie-service';


@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    SidebarComponent,
    HeaderComponent,
    LoadingComponent,
    ContentComponent,
    HomeComponent,
    NavGroupComponent,
    NavItemComponent,
    UserDetailDisplayComponent,
    ChangePasswordComponent,
    TokenTimeOutNotificationComponent,
    RiskOpinionHistoryComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRouter,
    BrowserAnimationsModule,
    SharedModule,
    CoreModule,
    FormsModule,
    HttpClientModule,
    ToastModule.forRoot(),
    MDBBootstrapModulesPro.forRoot(),
    AgmCoreModule.forRoot({
      apiKey: 'Your_api_key'
    }),
    NgxUiLoaderModule,
    NgxSortableModule,
    
  ],
  entryComponents: [
    UserDetailDisplayComponent,
    ChangePasswordComponent,
    TokenTimeOutNotificationComponent
  ],
  providers: [
    MDBSpinningPreloader,
    CurrencyPipe,
    DecimalPipe,
    BnNgIdleService,
    CookieService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
  ],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule {
}
