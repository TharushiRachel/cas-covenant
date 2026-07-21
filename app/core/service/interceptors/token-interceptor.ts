import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { switchMap } from "rxjs/operators";
import { Injectable } from "@angular/core";
import { SETTINGS } from "../../setting/commons.settings";
import { TokenService } from "../authentication/token.service";
import { DataService } from "../data/data.service";
import { AlertService } from "../common/alert.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(
    private tokenService: TokenService,
    private dataService: DataService,
    private alertService: AlertService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let request;
    if (req.withCredentials) {
      if (req.url.indexOf(SETTINGS.ENDPOINTS.refreshToken.url) > -1) {
        request = req.clone({
          setHeaders: {
            Authorization: "Bearer " + this.tokenService.getRefreshToken(),
          },
        });
        return next.handle(request);
      } else {
        if (!this.tokenService.isAccessTokenExpired()) {
          request = req.clone({
            setHeaders: {
              Authorization: "Bearer " + this.tokenService.getAccessToken(),
            },
          });
          return next.handle(request);
        } else {
          // console.log(req.url);
          if (!this.tokenService.isRefreshTokenExpired()) {
            return this.dataService.post(SETTINGS.ENDPOINTS.refreshToken).pipe(
              switchMap((response: any) => {
                this.tokenService.setAccessToken(response.token);
                request = req.clone({
                  setHeaders: {
                    Authorization:
                      "Bearer " + this.tokenService.getAccessToken(),
                  },
                });
                return next.handle(request);
              })
            );
          } else {
            if (!location.href.includes("auth")) {
              this.alertService.showToaster(
                "Your session is expired",
                SETTINGS.TOASTER_MESSAGES.warning
              );
            }
          }
          return next.handle(request);
        }
      }
    } else {
      request = req;
      return next.handle(request);
    }
  }
}
