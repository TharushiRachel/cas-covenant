import { Injectable } from "@angular/core";
import { DataService } from "../data/data.service";
import { SETTINGS } from "../../setting/commons.settings";
import * as CryptoJS from "crypto-js";
import * as _ from "lodash";
import { LocalStorage } from "ngx-webstorage";
import { ApplicationService } from "../application/application.service";
import { PrivilegeService } from "./privilege.service";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { Router } from "@angular/router";
import { DataResetService } from "../application/data-reset.service";
import { MasterDataService } from "../data/master-data.service";
import { CacheService } from "../data/cache.service";
import { SearchDataCacheService } from "../common/search-data-cache.service";
import { TokenService } from "./token.service";
import { MyLeadCountService } from "../leed/my-lead-count.service";
import { MyFacilityPaperCountService } from "../facility-paper/my-facility-paper-count.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CommentCacheService } from "../data/comment-cache.service";

export class LoginRQ {
  username: string;
  password: string;
}

@Injectable()
export class AuthService {
  private loginStatus = new Subject();
  loginStatusBehavi = new BehaviorSubject(false);

  @LocalStorage(SETTINGS.PUBLIC_KEY)
  private publicKey: any;

  refreshTokenPopupActivated = false;

  jwtHelper;
  modalRef: MDBModalRef;

  constructor(
    private dataService: DataService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private masterDataService: MasterDataService,
    private dataResetService: DataResetService,
    private cacheService: CacheService,
    private commentCacheService: CommentCacheService,
    private searchDataCacheService: SearchDataCacheService,
    private tokenService: TokenService,
    private myLeadCountService: MyLeadCountService,
    private router: Router
  ) {}

  userLogin(logInRequest: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.login, logInRequest).subscribe(
        (res: any) => {
          this.tokenService.setAccessToken(res.accessToken);
          this.tokenService.setRefreshToken(res.refreshToken);

          const user = res.user;
          this.applicationService.setLoggedInUser(user);
          let privileges = [];

          if (user.isAssistantUser) {
            privileges = _.remove(user.privileges, function (n) {
              for (
                let i = 0;
                i < SETTINGS.ASSISTANT_RESTRICTED_PRIVILEGES.length;
                i++
              ) {
                if (n != SETTINGS.ASSISTANT_RESTRICTED_PRIVILEGES[i]) {
                  return true;
                }
              }
            });
          } else {
            privileges = user.privileges;
          }

          this.privilegeService.setUserPrivileges(privileges);

          // this.myLeadCountService.setCountIntervals();
          // this.myLeadCountService.getLoggedInUserPendingLeadCount();
          //
          // this.myFacilityPaperCountService.setCountIntervals();
          // this.myFacilityPaperCountService.getLoggedUserFacilityPaperCount();

          Promise.all([
            this.getAgentDetails(user.userRefID),
            this.applicationService.getUserDaByUserName(user.userName),
            this.masterDataService.getSystemParameters(),
            this.masterDataService.getConstants(),
            this.masterDataService.getApplicationProperties(),
          ]).then(
            () => {
              this.loginStatus.next({ status: true });
              this.loginStatusBehavi.next(true);
              resolve(true);
            },
            (error) => {
              resolve(false);
              console.log("error loading meta data , ", error);
            }
          );

          this.searchDataCacheService.resetSearchDataCache();
        },
        (error) => {
          resolve(false);
          this.loginStatus.next({ status: false, message: error });
          this.loginStatusBehavi.next(false);
          this.applicationService.setLoggedInUser(null);
          this.applicationService.setLoggedInAgent(null);
        }
      );
    });
  }

  getAgentDetails(agentID): Promise<any> {
    return new Promise((resolve, reject) => {
      if (!agentID) {
        resolve();
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getAgentUpdateDTO);
        data.url = data.url + "/" + agentID;
        this.dataService.get(data).subscribe((response: any) => {
          this.applicationService.setLoggedInAgent(response);
          resolve(response);
        }, reject);
      }
    });
  }

  getResetPassword(password) {
    password = CryptoJS.SHA1(password);
    return CryptoJS.enc.Base64.stringify(password);
  }

  getLoggedInStatus(): Observable<any> {
    return this.loginStatus.asObservable();
  }

  setLoggedOut(config: any) {
    if (!this.tokenService.isAccessTokenExpired()) {
      this.dataService.post(SETTINGS.ENDPOINTS.expireUserCache).subscribe(
        (data) => {},
        (error) => {}
      );
    }

    this.applicationService.setLoggedInUser(null);
    this.applicationService.setLoggedInAgent(null);
    this.privilegeService.setUserPrivileges([]);
    this.myLeadCountService.clearCountInterval();

    this.dataResetService.resetData();
    localStorage.clear();
    sessionStorage.clear();
    this.cacheService.expireCache();
    this.commentCacheService.expireCommentCache();
    this.tokenService.clearTokens();

    if (config.isSSOLogin) {
      window.open(config.ssoLogoutRedirectUri, "_self");
    } else {
      this.router.navigate(["/auth/login/"]).then(() => {
        location.reload();
      });
    }
  }

  isLoggedIn(): boolean {
    return (
      !this.tokenService.isAccessTokenExpired() ||
      !this.tokenService.isRefreshTokenExpired()
    );
  }

  refreshTokenPopup() {
    this.refreshTokenPopupActivated = false;
    let config_data: any = {
      ssoRedirectUri: "",
      ssoLogoutRedirectUri: "",
      isSSOLogin: false,
    };
    this.setLoggedOut(config_data);

    /*this.modalRef = this.mdbModalService.show(TokenTimeOutNotificationComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-margin-center modal-width-40-p',
      containerClass: '',
      animated: true,
      data: {
        expireDate: this.tokenService.getAccessTokenExpirationDate(),
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      this.refreshTokenPopupActivated = false;
      if (data) {
        if (data.logout) {
          this.setLoggedOut();
        } else if (data.refreshToken) {
          this.refreshToken();
        }
      }
    });*/
  }

  refreshToken() {
    this.dataService
      .post(SETTINGS.ENDPOINTS.refreshToken, {})
      .subscribe((res: any) => {
        this.tokenService.setAccessToken(res.token);
        this.tokenService.setRefreshToken(res.refreshToken);
      });
  }

  getSSOConfigData(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getSSOProperties).subscribe(
        (res: any) => {
          let config_data: any = {
            ssoRedirectUri: res && res.ssoRedirectUri ? res.ssoRedirectUri : "",
            ssoLogoutRedirectUri:
              res && res.ssoLogoutRedirectUri ? res.ssoLogoutRedirectUri : "",
            isSSOLogin:
              res && res.isSSOLogin ? JSON.parse(res.isSSOLogin) : false,
          };
          resolve(config_data);
        },
        (err: any) => {
          let config_data: any = {
            ssoRedirectUri: "",
            ssoLogoutRedirectUri: "",
            isSSOLogin: false,
          };
          resolve(config_data);
        }
      );
    });
  }
}
