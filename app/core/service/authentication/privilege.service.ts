import {Injectable} from "@angular/core";
import {SETTINGS} from "../../setting/commons.settings";
import {EncryptionService} from "../application/encryption.service";
import {LocalStorage} from "ngx-webstorage";
import * as _ from 'lodash';

@Injectable()
export class PrivilegeService {

  @LocalStorage(SETTINGS.USER_PRIVILEGES)
  private userPrivilegesEncStr: string;

  private userPrivileges: Array<string> = [];

  constructor(private encryptionService: EncryptionService) {
    if (!_.isEmpty(this.userPrivilegesEncStr)) {
      this.decryptPrivileges();
    }
  }

  setUserPrivileges(userPrivileges: Array<string>) {
    this.userPrivilegesEncStr = this.encryptionService.encrypt(JSON.stringify(userPrivileges));
    this.decryptPrivileges();
  }

  hasPrivilege(privilegeCode: string): boolean {
    return _.indexOf(this.userPrivileges, privilegeCode) !== -1;
  }

  hasAnyPrivilege(privilegeCodes: Array<string>): boolean {
    let hasAnyPrivilege = false;

    for (let i = 0; i < privilegeCodes.length; i++) {
      if (this.hasPrivilege(privilegeCodes[i].trim())) {
        hasAnyPrivilege = true;
        break;
      }
    }

    return hasAnyPrivilege;
  }

  private decryptPrivileges() {
    this.userPrivileges = JSON.parse(this.encryptionService.decrypt(this.userPrivilegesEncStr))
  }
}
