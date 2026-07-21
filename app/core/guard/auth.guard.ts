// Angular
import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
// RxJS
import { Observable } from "rxjs";
// NGRX
import { AuthService } from "../service/authentication/auth.service";
import { AlertService } from "../service/common/alert.service";
import { SETTINGS } from "../setting/commons.settings";

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    if (!this.authService.isLoggedIn()) {
      if (localStorage.getItem(SETTINGS.ACCESS_TOKEN) != null) {
        this.alertService.showToaster(
          "Your session is expired",
          SETTINGS.TOASTER_MESSAGES.warning
        );
      }
      let config_data: any = {
        ssoRedirectUri: "",
        ssoLogoutRedirectUri: "",
        isSSOLogin: false,
      };
      this.authService.setLoggedOut(config_data);

      return false;
    }
    return true;
  }
}
