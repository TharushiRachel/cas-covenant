import { Injectable } from "@angular/core";
import { SETTINGS } from "../../setting/commons.settings";
import * as CryptoJS from "crypto-js";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable()
export class TokenService {
  private accessToken;

  private refreshToken;

  private jwtHelper;

  constructor() {
    this.jwtHelper = new JwtHelperService();
    this.accessToken = null;
    this.refreshToken = null;
    let accessTokenEnc = localStorage.getItem(SETTINGS.ACCESS_TOKEN);
    let refreshTokenEnc = localStorage.getItem(SETTINGS.ACCESS_TOKEN);

    if (accessTokenEnc) {
      this.accessToken = CryptoJS.AES.decrypt(
        accessTokenEnc,
        SETTINGS.KEYS.SECRET
      ).toString(CryptoJS.enc.Utf8);
    }

    if (refreshTokenEnc) {
      this.refreshToken = CryptoJS.AES.decrypt(
        refreshTokenEnc,
        SETTINGS.KEYS.SECRET
      ).toString(CryptoJS.enc.Utf8);
    }
  }

  setAccessToken(accessToken) {
    localStorage.setItem(
      SETTINGS.ACCESS_TOKEN,
      CryptoJS.AES.encrypt(accessToken, SETTINGS.KEYS.SECRET).toString()
    );
    this.accessToken = accessToken;
  }

  setRefreshToken(refreshToken) {
    localStorage.setItem(
      SETTINGS.REFRESH_TOKEN,
      CryptoJS.AES.encrypt(refreshToken, SETTINGS.KEYS.SECRET).toString()
    );
    this.refreshToken = refreshToken;
  }

  getAccessToken() {
    return this.accessToken;
  }

  getRefreshToken() {
    return this.refreshToken;
  }

  isAccessTokenExpired() {
    if (this.accessToken) {
      return this.jwtHelper.isTokenExpired(this.accessToken);
    }
    return true;
  }

  isRefreshTokenExpired() {
    if (this.refreshToken) {
      return this.jwtHelper.isTokenExpired(this.refreshToken);
    }
    return true;
  }

  getRefreshTokenExpirationDate() {
    return this.jwtHelper.getTokenExpirationDate(this.refreshToken);
  }

  getAccessTokenExpirationDate() {
    return this.jwtHelper.getTokenExpirationDate(this.accessToken);
  }

  clearTokens() {
    this.accessToken = null;
    this.refreshToken = null;
  }

  decodeSSOToken(token: any) {
    let userAD: string = "";
    if (!this.jwtHelper.isTokenExpired(token)) {
      let decodedToken: any = this.jwtHelper.decodeToken(token);
      userAD = decodedToken ? decodedToken.name : "";
    }
    return userAD;
  }
}
