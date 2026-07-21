import {NgModule} from '@angular/core';
import {ConfigService} from './service/application/config.service';
import {CommonService} from './service/common/common.service';
import {NavigationService} from './service/navigation/navigation.service';
import {SharedModule} from "../shared/shared.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {Ng2Webstorage} from "ngx-webstorage";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {DataService} from "./service/data/data.service";
import {AlertService} from "./service/common/alert.service";
import {ApplicationService} from "./service/application/application.service";
import {DateService} from "./service/application/date.service";
import {EncryptionService} from "./service/application/encryption.service";
import {MasterDataService} from "./service/data/master-data.service";
import {UrlEncodeService} from "./service/application/url-encode.service";
import {DataResetService} from "./service/application/data-reset.service";
import {SearchDataCacheService} from "./service/common/search-data-cache.service";
import {CacheService} from "./service/data/cache.service";
import {CommentCacheService} from "./service/data/comment-cache.service";
import {AuthService} from "./service/authentication/auth.service";
import {TokenService} from "./service/authentication/token.service";
import {AuthGuard} from "./guard/auth.guard";
import {ChangePasswordService} from "./service/authentication/change-password.service";
import {ImageUploadService} from "./service/common/image-upload.service";
import {PrivilegeService} from "./service/authentication/privilege.service";
import {TokenInterceptor} from "./service/interceptors/token-interceptor";
import {MyLeadCountService} from "./service/leed/my-lead-count.service";
import {ToastrModule} from "ngx-toastr";
import {MyFacilityPaperCountService} from "./service/facility-paper/my-facility-paper-count.service";
import {CurrencyPipe} from "@angular/common";
import {CurrencyService} from "./service/common/currency.service";
import {MnCurrencyService} from "./service/common/mn-currency.service";
import {CasCribServiceService} from "./service/data/cas-crib-service.service";
import {CasDocumentStorageService} from "./service/data/cas-document-storage.service";
import {CasCustomerService} from "./service/data/cas-customer.service";


@NgModule({
  declarations: [],
  imports: [
    SharedModule,
    BrowserAnimationsModule,
    Ng2Webstorage.forRoot({prefix: '', separator: '', caseSensitive: true}),
    HttpClientModule,
    ToastrModule.forRoot({
      preventDuplicates: true,
    })
  ],
  providers: [
    ConfigService,
    CommonService,
    NavigationService,
    DataService,
    CommentCacheService,
    AlertService,
    ApplicationService,
    DateService,
    EncryptionService,
    MasterDataService,
    UrlEncodeService,
    DataResetService,
    SearchDataCacheService,
    CacheService,
    AuthService,
    TokenService,
    AuthGuard,
    ChangePasswordService,
    ImageUploadService,
    PrivilegeService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    MyLeadCountService,
    MyFacilityPaperCountService,
    CurrencyPipe,
    CurrencyService,
    MnCurrencyService,
    CasCribServiceService,
    CasDocumentStorageService,
    CasCustomerService
  ]
})
export class CoreModule {
}
