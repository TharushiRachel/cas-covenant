import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {DataService} from "../../../../core/service/data/data.service";
import {SearchDataCacheService} from "../../../../core/service/common/search-data-cache.service";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../core/service/application/url-encode.service";
import {LocalStorage} from "ngx-webstorage";
import {CacheService} from "../../../../core/service/data/cache.service";
import {Constants} from "../../../../core/setting/constants";
import * as _ from "lodash";
import {ApplicationService} from "../../../../core/service/application/application.service";

@Injectable()
export class FacilityPaperTransferService implements Resolve<any> {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_TRANSFER_FACILITY_PAPER_ID)
  selectedFacilityPaperToTransferID;
  defaultWorkflowUpmGroupCode = Constants.defaultWorkflowUpmGroupCode;

  onSelectedFacilityPaperChange: BehaviorSubject<any> = new BehaviorSubject({});
  onFacilityPaperChange = new BehaviorSubject({});
  onFpUpcSectionChange = new BehaviorSubject({});
  onCreditRiskCommentListChange = new BehaviorSubject({});
  onRemarkDtoListChange = new BehaviorSubject({});
  onUpcSectionDataChange = new BehaviorSubject({});
  onUpcTemplateListLoad = new BehaviorSubject({});
  onUpmGroupChange = new BehaviorSubject({});
  onUserDetailFromBranchAuthorityChange = new BehaviorSubject({});
  facilityPaperDTO: any = {};
  userDetails: any = [];

  constructor(
    private dataService: DataService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private cacheService: CacheService,
    private applicationService: ApplicationService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    let getAssistantsRQ = {
      functionCode2: this.applicationService.getLoggedInUserUserID(),
      upmGroupCode: this.defaultWorkflowUpmGroupCode.ASSISTANT
    };

    return new Promise((resolve, reject) => {
      Promise.all([
        this.getFacilityPaperByID(),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUPPORTING_DOCs),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SECTOR_DATA),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SECURITY_SUMMARY_TOPICS),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST),
        this.cacheService.loadData(Constants.masterDataKey.CAS_APPLICATION_USER_ASSISTANTS, getAssistantsRQ),
        this.getRemarkDTOList(),
        this.getActiveApprovedUpcTemplates(),
        this.getAllUpcSectionData()
      ]).then(() => {
        resolve()
      }, reject)
    })
  }

  getFacilityPaperByID(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getFacilityPaperByID);
      data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedFacilityPaperToTransferID);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.facilityPaperDTO = response;
          this.onFacilityPaperChange.next(this.facilityPaperDTO);
          this.onFpUpcSectionChange.next(this.facilityPaperDTO);
          this.onCreditRiskCommentListChange.next(this.facilityPaperDTO);
          resolve();
        }, reject);
    });
  }

  getActiveApprovedUpcTemplates(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getActiveApprovedUpcTemplateDtoList);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.onUpcTemplateListLoad.next(response);
          resolve(response);
        }, reject);
    })
  }

  getRemarkDTOList() {
    if (this.urlEncodeService.decode(this.selectedFacilityPaperToTransferID)) {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getRemarkDtoList);
      data.url = data.url + '/' + this.urlEncodeService.decode(this.selectedFacilityPaperToTransferID);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.onRemarkDtoListChange.next(response);
        })
    }
  }

  getAllUpcSectionData(): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAllUpcSectionData);
      this.dataService.get(data)
        .subscribe((response: any) => {
          this.onUpcSectionDataChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode, data)
      .subscribe((response: any) => {
        this.onUpmGroupChange.next(response);
      });
  }

  getUserUPMData(userADID): Promise<any> {
    return new Promise<any>((resolve, reject) => {

      const data = Object.assign({}, SETTINGS.ENDPOINTS.getUPMDetails);
      data.url = data.url + '/' + userADID;
      this.dataService.get(data)
        .subscribe((response: any) => {
          resolve(response);
        });
    });
  }

  getUserDetailListFormBranchAuthorityLevel(data) {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getUserDetailListFormBranchAuthorityLevel, data)
        .subscribe((response: any) => {
          resolve(response.branchAuthorityLevelResponseDTOList);
        })
    });
  }

  async getEligibleUsers(facilityPaper, groupCode) {

    let eligibleUsers = [];
    let requiredDivCodes = [];

    requiredDivCodes.push(this.applicationService.getLoggedInUserDivCode());
    requiredDivCodes.push(facilityPaper.branchCode);
    requiredDivCodes.push(facilityPaper.createdUserBranchCode);
    requiredDivCodes.push(facilityPaper.assignDepartmentCode);
    requiredDivCodes.push(facilityPaper.currentAssignUserSolID);
    requiredDivCodes.push(facilityPaper.assignDepartmentCode);

    let uniqueDivCodes = [...new Set(requiredDivCodes)];

    for (const divCode of uniqueDivCodes) {
      if (divCode) {
       // console.log("users of " + groupCode + " retrieved from " + divCode);
        let users: [] = await this.getUserDetailListFormBranchAuthorityLevel(
          {
            solId: divCode,
            roleId: groupCode,
            appCode: ''
          });
        if (users && Array.isArray(users)) {
          eligibleUsers = [...users, ...eligibleUsers];
        }
      }
    }

    eligibleUsers = _.uniqBy(eligibleUsers, (i) => i.userID);

    this.userDetails = eligibleUsers;
    this.onUserDetailFromBranchAuthorityChange.next(this.userDetails);
  }

  updateFacilityPaper(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.updateFacilityPaper, data)
      .subscribe((response: any) => {
        this.facilityPaperDTO = response;
        this.onFacilityPaperChange.next(this.facilityPaperDTO);
        this.onCreditRiskCommentListChange.next(this.facilityPaperDTO);
        this.onFpUpcSectionChange.next(this.facilityPaperDTO);
        this.getRemarkDTOList();
      })
  }

}
