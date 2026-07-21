import { Injectable } from "@angular/core";
import { DataService } from "./data.service";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { Observable } from "rxjs";
import { SETTINGS } from "../../setting/commons.settings";
import * as _ from "lodash";
import { Constants } from "../../setting/constants";
import { Config } from "../../setting/config";

@Injectable()
export class MasterDataService implements Resolve<any> {
  systemParameters: any = {};
  constants: any = {};
  applicationProperties: any = {};

  constructor(private dataService: DataService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getConstants(),
        this.getSystemParameters(),
        this.getApplicationProperties(),
      ]).then(() => {
        resolve();
      }, reject);
    });
  }

  getConstants(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getConstants);
      this.dataService.get(data).subscribe(
        (response: any) => {
          this.constants = response;
          this.processConstants();
          resolve(response);
        },
        (error) => {
          console.log("error getting constants , {}", error);
          reject(error);
        }
      );

      resolve(true);
    });
  }

  getSystemParameters(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getSystemParams);
      this.dataService.get(data).subscribe(
        (response: any) => {
          this.systemParameters = response;
          resolve(response);
        },
        (error) => {
          console.log("error getting system parameters , {}", error);
          reject(error);
        }
      );

      resolve(true);
    });
  }

  getApplicationProperties(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getApplicationProperties
      );
      this.dataService.get(data).subscribe(
        (response: any) => {
          this.applicationProperties = response;
          this.processApplicationProperties();
          resolve(response);
        },
        (error) => {
          console.log("error getting application properties , {}", error);
          reject(error);
        }
      );

      resolve(true);
    });
  }

  reloadSystemConstants() {
    if (_.isEmpty(this.constants)) {
      Promise.all([this.getConstants()]).catch((error) => {
        console.log("error reloading constants , {}", error);
      });
    }
  }

  reloadSystemParameters() {
    if (_.isEmpty(this.systemParameters)) {
      Promise.all([this.getSystemParameters()]).catch((error) => {
        console.log("error reloading system parameters , {}", error);
      });
    }
  }

  reloadApplicationProperties() {
    if (_.isEmpty(this.constants)) {
      Promise.all([this.getApplicationProperties()]).catch((error) => {
        console.log("error reloading application properties , {}", error);
      });
    }
  }

  hasSystemParameter(key: string): boolean {
    return !_.isEmpty(this.systemParameters[key]);
  }

  updateSystemParameter(paramDTO: any) {
    this.systemParameters[paramDTO.paramKey] = paramDTO;
  }

  getSystemParameter(key: string) {
    if (this.hasSystemParameter(key)) {
      let param = this.systemParameters[key];
      let value: any = "";

      switch (param.paramType) {
        case "S":
          value = param.paramValue;
          break;

        case "D":
          value = parseFloat(param.paramValue);
          break;

        case "B":
          value = param.paramValue == "Y";
          break;
      }

      return value;
    }

    return "";
  }

  processConstants() {
    let keys = _.keys(this.constants);

    _.each(keys, (key: any) => {
      let constantKeyValueMap = this.constants[key];
      Constants[key] = constantKeyValueMap;

      let constKeys = _.keys(constantKeyValueMap);
      let constantKeyMap: any = {};
      _.each(constKeys, (constKey: any) => {
        constantKeyMap[constKey] = constKey;
      });

      Constants[key + "Const"] = constantKeyMap;
    });
  }

  processApplicationProperties() {
    let awsBaseUrl = this.applicationProperties["awsBaseUrl"];
    let imageUploadMaxSizeMB =
      this.applicationProperties["imageUploadMaxSizeMB"];
    let imageUploadAllowedExtensions =
      this.applicationProperties["imageUploadAllowedExtensions"];
    let customSizeLimits = this.applicationProperties["customFileSizeLimits"];
    let leadContextPath = this.applicationProperties["leadContextPath"];

    if (awsBaseUrl) {
      SETTINGS.BASE_IMAGE_URL = awsBaseUrl;
    }

    if (imageUploadMaxSizeMB) {
      SETTINGS.UPLOAD_IMAGE_DEFAULT_SIZE_MB = parseInt(imageUploadMaxSizeMB);
    }

    if (imageUploadAllowedExtensions) {
      SETTINGS.UPLOAD_IMAGE_ALLOWED_EXTENSIONS =
        imageUploadAllowedExtensions.split(",");
    }

    if (customSizeLimits) {
      SETTINGS.UPLOAD_IMAGE_SIZE_LIMITS_MB = customSizeLimits;
    }
    if (leadContextPath) {
      Config.CAS_LEAD_CONTEXT_PATH = leadContextPath;
    }
  }
}
