import { Injectable } from "@angular/core";
import { SETTINGS } from "../../setting/commons.settings";
import { DataService } from "../data/data.service";
import { LocalStorage } from "ngx-webstorage";
import * as _ from "lodash";
import { EncryptionService } from "./encryption.service";
import { BehaviorSubject } from "rxjs";

@Injectable()
export class ApplicationService {
  @LocalStorage(SETTINGS.LOGGED_USER_ENC)
  private loggedInUserEncrypted: string;

  private loggedInUserDecrypted: any;

  @LocalStorage(SETTINGS.LOGGED_AGENT_ENC)
  private loggedInAgentEncrypted: string;

  private loggedInAgentDecrypted: any;

  @LocalStorage(SETTINGS.PUBLIC_KEY)
  private publicKey: any;

  private externalSiteDetails: string;

  userDa: any = {};
  userUPMDetails: any = {};
  onUserDaChange = new BehaviorSubject({});
  onUserUPMDetailsChange = new BehaviorSubject({});

  constructor(
    private dataService: DataService,
    private encryptionService: EncryptionService
  ) {
    if (!_.isEmpty(this.loggedInUserEncrypted)) {
      this.loggedInUserDecrypted = this.getDecryptedDTO(
        this.loggedInUserEncrypted
      );
      this.getUserDaByUserName(this.loggedInUserDecrypted.userName);
    } else {
      this.loggedInUserDecrypted = {};
    }

    if (!_.isEmpty(this.loggedInAgentEncrypted)) {
      this.loggedInAgentDecrypted = this.getDecryptedDTO(
        this.loggedInAgentEncrypted
      );
    } else {
      this.loggedInAgentDecrypted = {};
    }
  }

  loadPublicKey() {
    let loadPublicKeyObserving = this.dataService.get(
      SETTINGS.ENDPOINTS.loadPublicKey
    );
    loadPublicKeyObserving.subscribe((response: any) => {
      this.publicKey = response.publicKey;
    });
    return loadPublicKeyObserving;
  }

  setLoggedInUser(user) {
    if (user != null) {
      this.loggedInUserEncrypted = this.getEncryptedDTO(user);
      this.loggedInUserDecrypted = this.getDecryptedDTO(
        this.loggedInUserEncrypted
      );
    } else {
      this.loggedInUserEncrypted = "";
      this.loggedInUserDecrypted = {};
    }
  }

  setLoggedInAgent(agentDTO) {
    if (agentDTO != null) {
      this.loggedInAgentEncrypted = this.getEncryptedDTO(agentDTO);
      this.loggedInAgentDecrypted = this.getDecryptedDTO(
        this.loggedInAgentEncrypted
      );
    } else {
      this.loggedInAgentEncrypted = "";
      this.loggedInAgentDecrypted = {};
    }
  }

  getUserDisplayName(): string {
    return this.loggedInUserDecrypted.displayName;
  }

  getLoggedInUserUserID() {
    return this.loggedInUserDecrypted.userID;
  }

  getLoggedInUserUserName() {
    return this.loggedInUserDecrypted.userName;
  }

  getLoggedInUserFullName() {
    return this.loggedInUserDecrypted.fullName;
  }

  getLoggedInUserCombinedName() {
    return this.loggedInUserDecrypted.combinedName;
  }

  getLoggedInUser() {
    return this.loggedInUserDecrypted;
  }

  getLoggedInUserDivCode() {
    return this.loggedInUserDecrypted.divCode;
  }

  getLoggedInUserSolID() {
    return this.loggedInUserDecrypted.solID;
  }

  getLoggedInUserUPMGroupCode() {
    return this.loggedInUserDecrypted.upmGroupCode;
  }

  getLoggedInUserSupervisorDetails() {
    return this.loggedInUserDecrypted.supervisor;
  }

  getLoggedInCASUserSupervisorAdUserID() {
    return this.loggedInUserDecrypted.supervisor
      ? this.loggedInUserDecrypted.supervisor.adUserID
      : null;
  }

  getLoggedInCASUserSupervisorUserID() {
    return this.loggedInUserDecrypted.supervisor
      ? this.loggedInUserDecrypted.supervisor.userID
      : null;
  }

  getLoggedInCASUserSupervisorDivCode() {
    return this.loggedInUserDecrypted.supervisor
      ? this.loggedInUserDecrypted.supervisor.divCode
      : null;
  }

  getLoggedInCASUserSupervisorUPMGroupCode() {
    return this.loggedInUserDecrypted.supervisor
      ? this.loggedInUserDecrypted.supervisor.upmGroupCode
      : null;
  }

  getLoggedInCASUserSupervisorDisplayName() {
    return this.loggedInUserDecrypted.supervisor
      ? this.loggedInUserDecrypted.supervisor.displayName
      : null;
  }

  // getDesignationDescription(){
  //   return this.loggedInUserDecrypted.supervisor? this.loggedInUserDecrypted.supervisor.displayDescription : null;
  // }

  getLoggedInUserUPMID() {
    return this.loggedInUserDecrypted.upmID;
  }

  isAgent() {
    return this.loggedInUserDecrypted.agent;
  }

  getIsAssistant() {
    return this.loggedInUserDecrypted.isAssistantUser;
  }

  getAgentSupervisorADUserID() {
    return this.loggedInAgentDecrypted.supervisorADUserID;
  }

  private getEncryptedDTO(dto): string {
    return this.encryptionService.encrypt(JSON.stringify(dto));
  }

  private getDecryptedDTO(user) {
    return JSON.parse(this.encryptionService.decrypt(user));
  }

  isUserTypeAllowed(allowedUserTypes: [any]) {
    return (
      _.indexOf(allowedUserTypes, this.loggedInUserDecrypted.userType) !== -1
    );
  }

  getLoggedInUserDisplayName() {
    return this.getLoggedInUserCombinedName()
      ? this.getLoggedInUserCombinedName()
      : this.getUserDisplayName()
      ? this.getUserDisplayName()
      : null;
  }

  getUserDaByUserName(userName): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getUserDAByLoggedInUser
      );
      data.url = data.url + "/" + userName;
      this.dataService.get(data).subscribe((response: any) => {
        this.userDa = response;
        this.onUserDaChange.next(this.userDa);
        resolve(response);
      }, reject);
    });
  }

  getUserUPMDetailsList(userUPMDetailsRQ) {
    return this.dataService.post(
      SETTINGS.ENDPOINTS.getUserUPMDetailList,
      userUPMDetailsRQ
    );
  }

  getUpmDetailsByAdUserIdAndAppCode(userID) {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPMDetails);
    data.url = data.url + "/" + userID;
    return this.dataService.get(data);
  }

  getUpmDetailsById(userID) {
    const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPMDetailsById);
    data.url = data.url + "/" + userID;
    return this.dataService.get(data);
  }

  getUserDetailListFormBranchAuthorityLevel(data) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(
          SETTINGS.ENDPOINTS.getUserDetailListFormBranchAuthorityLevel,
          data
        )
        .subscribe((response: any) => {
          resolve(response.branchAuthorityLevelResponseDTOList);
        });
    });
  }

  getUserUPMData(userADID): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPMDetails);
      data.url = data.url + "/" + userADID;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response);
      });
    });
  }

  getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(data) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(
          SETTINGS.ENDPOINTS
            .getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode,
          data
        )
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    });
  }

  getLoggedInUserDesignation() {
    return this.loggedInUserDecrypted.designation;
  }
}
