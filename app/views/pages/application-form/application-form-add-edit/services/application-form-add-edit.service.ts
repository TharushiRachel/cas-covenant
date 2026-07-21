import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { Constants } from "../../../../../core/setting/constants";
import { CacheService } from "../../../../../core/service/data/cache.service";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { DataService } from "../../../../../core/service/data/data.service";
import { UrlEncodeService } from "../../../../../core/service/application/url-encode.service";
import { LocalStorage } from "ngx-webstorage";
import * as _ from "lodash";
import { ApplicationService } from "../../../../../core/service/application/application.service";
import { PrivilegeService } from "../../../../../core/service/authentication/privilege.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { AFAnnexureDTO } from "../dtos/apf-annexure-dto";

@Injectable()
export class ApplicationFormAddEditService implements Resolve<any> {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  onApplicationFormChange = new BehaviorSubject({});
  onApplicationFormCribChange = new BehaviorSubject({});
  onAFReturnUserListChangeSub = new BehaviorSubject({});
  onApplicationFormBasicDetailsChange = new BehaviorSubject({});
  onApplicationFormRiskRateChange = new BehaviorSubject({});
  onApplicationFormLiabilityScreenDataChange = new BehaviorSubject({});
  onApplicationFormTopicsChange = new BehaviorSubject({});
  onApplicationFormSecuritiesChange = new BehaviorSubject({});
  onApplicationFormDocumentChange = new BehaviorSubject({});
  onAFFacilitiesChange = new BehaviorSubject({});
  onAFCommentsChange = new BehaviorSubject({});
  onDownloadLinkChangeAPSupportDoc: Subject<any> = new Subject();
  onDownloadLinkChangeAPSupportDocSummary: Subject<any> = new Subject();
  onDownloadLinkChangeAPSupportDocUpload: Subject<any> = new Subject();
  onDownloadLinkChangeAFCribAttachments: Subject<any> = new Subject();
  onCribReportChange = new BehaviorSubject({});
  onUpmGroupChange = new BehaviorSubject({});
  applicationFormDTO: any = {};
  upmGroups: any = [];
  userDetails: any = [];
  onUserDetailFromBranchAuthorityChange = new BehaviorSubject({});
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  defaultWorkflowUpmGroupCode = Constants.defaultWorkflowUpmGroupCode;
  onViewLinkChangeAPSupportDoc: Subject<any> = new Subject();
  onViewLinkChangeAFCribAttachments: Subject<any> = new Subject();
  onViewLinkChangeAPSupportDocUpload: Subject<any> = new Subject();
  onAFESGChange = new BehaviorSubject<any[]>([]);
  onAFESGRiskScoreChange = new BehaviorSubject<any[]>([]);
  onESGRiskOpinionChange = new BehaviorSubject([]);

  constructor(
    private readonly cacheService: CacheService,
    private readonly dataService: DataService,
    private readonly applicationService: ApplicationService,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly privilegeService: PrivilegeService,
    private readonly alertService: AlertService,
    private readonly route: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    let getAssistantsRQ = {
      functionCode2: this.applicationService.getLoggedInUserUserID(),
      upmGroupCode: this.defaultWorkflowUpmGroupCode.ASSISTANT,
    };

    return new Promise((resolve, reject) => {
      Promise.all([
        this.getApplicationFormByID(),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUPPORTING_DOCs),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES
        ),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SECTOR_DATA),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_APPLICATION_FORM_TOPICS
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_APPLICATION_USER_ASSISTANTS,
          getAssistantsRQ
        ),
      ]).then(() => {
        resolve({});
      }, reject);
    });
  }

  getRetailCribReport(data): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getRetailCribReport, data)
        .subscribe((response: any) => {
          this.onCribReportChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getCorporateCribReport(data): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getCorporateCribReport, data)
        .subscribe((response: any) => {
          this.onCribReportChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  saveOrUpdateApplicationForm(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateApplicationForm, searchData)
      .subscribe((response: any) => {
        this.applicationFormDTO = response;
        this.onApplicationFormChange.next(response);
      });
  }

  draftApplicationForm(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.draftApplicationForm, searchData)
      .subscribe((response: any) => {
        this.applicationFormDTO = response;
        this.onApplicationFormChange.next(response);
        this.onApplicationFormBasicDetailsChange.next(response);
        this.onApplicationFormDocumentChange.next(response);
        this.onApplicationFormTopicsChange.next(response);
        this.onAFFacilitiesChange.next(response);
        this.onApplicationFormSecuritiesChange.next(response);
        this.onApplicationFormCribChange.next(response);
        this.onApplicationFormRiskRateChange.next(response);
        this.onApplicationFormLiabilityScreenDataChange.next(response);
        this.onAFCommentsChange.next(response);
      });
  }

  getApplicationFormByID(): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.selectedApplicationFormID == null) {
        this.applicationFormDTO = {};
        this.onApplicationFormChange.next(this.applicationFormDTO);
        resolve({});
      } else {
        const data = Object.assign(
          {},
          SETTINGS.ENDPOINTS.getApplicationFormByID
        );
        data.url =
          data.url +
          "/" +
          this.urlEncodeService.decode(this.selectedApplicationFormID);
        this.dataService.get(data).subscribe((response: any) => {
          this.applicationFormDTO = response;
          this.onApplicationFormChange.next(response);
          this.onApplicationFormBasicDetailsChange.next(response);
          this.onApplicationFormDocumentChange.next(response);
          this.onApplicationFormTopicsChange.next(response);
          this.onAFFacilitiesChange.next(response);
          this.onApplicationFormSecuritiesChange.next(response);
          this.onApplicationFormCribChange.next(response);
          this.onApplicationFormRiskRateChange.next(response);
          this.onApplicationFormLiabilityScreenDataChange.next(response);
          this.onAFCommentsChange.next(response);
          if (response.riskCategories) {
            this.onAFESGRiskScoreChange.next(response.riskCategories);
          } else {
            this.onAFESGRiskScoreChange.next([]);
          }
          this.onESGRiskOpinionChange.next([]);
          resolve(response);
        });
      }
    });
  }

  saveOrUpdateAFBasicDetails(basicDetails) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateAFBasicDetails, basicDetails)
      .subscribe((response: any) => {
        this.onApplicationFormBasicDetailsChange.next(response);
        this.onApplicationFormCribChange.next(response);
        this.onApplicationFormRiskRateChange.next(response);
        this.onApplicationFormLiabilityScreenDataChange.next(response);
      });
  }

  uploadApplicationFormDocument(DocumentDTO) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.uploadApplicationFormDocument, DocumentDTO)
      .subscribe((response: any) => {
        this.onApplicationFormDocumentChange.next(response);
      });
  }

  downloadAFSupportDocument(docStorage) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + "/" + docStorage.docStorageID;
      this.dataService.get(data).subscribe((response: any) => {
        /*this.onDownloadLinkChangeAPSupportDoc.next({
            data: response,
            fileName: docStorage.fileName
          });*/
        resolve(response);
      }, reject);
    });
  }

  viewAFSupportDocument(docStorage) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + "/" + docStorage.docStorageID;
      this.dataService.get(data).subscribe((response: any) => {
        // this.onViewLinkChangeAPSupportDoc.next({
        //   data: response,
        //   fileName: docStorage.fileName
        // });
        resolve(response);
      }, reject);
    });
  }

  deactivateAFSupportDocument(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.deactivateApplicationFormSupportingDoc, data)
      .subscribe((response: any) => {
        this.onApplicationFormDocumentChange.next(response);
      });
  }

  saveOrUpdateOwnershipDetails(basicDetails) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateOwnershipDetails, basicDetails)
      .subscribe((response: any) => {
        this.onApplicationFormBasicDetailsChange.next(response);
      });
  }

  saveOrUpdateAFFacility(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateAFFacility, searchData)
      .subscribe((response: any) => {
        this.onAFFacilitiesChange.next(response);
        this.onApplicationFormSecuritiesChange.next(response);
      });
  }

  saveOrUpdateApplicationFormTopics(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateApplicationFormTopics, data)
      .subscribe((response: any) => {
        this.onApplicationFormTopicsChange.next(response);
      });
  }

  deactivateApplicationFormTopic(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.deactivateApplicationFormTopic, data)
      .subscribe((response: any) => {
        this.onApplicationFormTopicsChange.next(response);
      });
  }

  saveUpdateFacilitySecurity(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveUpdateApplicationFormFacilitySecurity, data)
      .subscribe((response: any) => {
        this.onApplicationFormSecuritiesChange.next(response);
        this.onAFFacilitiesChange.next(response);
      });
  }

  saveOrUpdateCribReports(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateCribReports, searchData)
      .subscribe((response: any) => {
        this.onApplicationFormCribChange.next(response);
        this.onApplicationFormBasicDetailsChange.next(response);
        this.onApplicationFormRiskRateChange.next(response);
        this.onApplicationFormLiabilityScreenDataChange.next(response);
      });
  }

  saveOrUpdateCribAttachments(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateCribAttachments, searchData)
      .subscribe((response: any) => {
        this.onApplicationFormCribChange.next(response);
        this.onApplicationFormBasicDetailsChange.next(response);
      });
  }

  downloadAFCribAttachments(docStorage) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + "/" + docStorage.docStorageID;
      this.dataService.get(data).subscribe((response: any) => {
        // this.onDownloadLinkChangeAFCribAttachments.next({
        //   data: response,
        //   fileName: docStorage.fileName
        // });
        resolve(response);
      }, reject);
    });
  }

  viewAFCribAttachments(docStorage) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + "/" + docStorage.docStorageID;
      this.dataService.get(data).subscribe((response: any) => {
        // this.onViewLinkChangeAFCribAttachments.next({
        //   data: response,
        //   fileName: docStorage.fileName
        // });
        resolve(response);
      }, reject);
    });
  }

  deactivateAFCribAttachment(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.deactivateAFCribAttachment, data)
      .subscribe((response: any) => {
        this.onApplicationFormCribChange.next(response);
      });
  }

  saveOrUpdateOtherBankDetails(basicDetails) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateOtherBankDetails, basicDetails)
      .subscribe((response: any) => {
        this.onApplicationFormBasicDetailsChange.next(response);
      });
  }

  removeOtherBankDetails(basicDetails) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.removeOtherBankDetails, basicDetails)
      .subscribe((response: any) => {
        this.onApplicationFormBasicDetailsChange.next(response);
      });
  }

  saveOrUpdateOptionalCribReports(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateOptionalCribReports, searchData)
      .subscribe((response: any) => {
        this.onApplicationFormCribChange.next(response);
        this.onApplicationFormLiabilityScreenDataChange.next(response);
      });
  }

  saveOrUpdateRiskRate(riskRate) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateRiskRate, riskRate)
      .subscribe((response: any) => {
        this.onApplicationFormRiskRateChange.next(response);
      });
  }

  draftFacilityPaperByApplicationForm(riskRate) {
    return this.dataService.post(
      SETTINGS.ENDPOINTS.draftFacilityPaperByApplicationForm,
      riskRate
    );
  }

  updateApplicationFormStatus(riskRate) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.updateApplicationFormStatus, riskRate)
      .subscribe((response: any) => {
        this.onApplicationFormChange.next(response);
      });
  }

  updateAFStatus(updateData) {
    return this.dataService.post(
      SETTINGS.ENDPOINTS.updateApplicationFormStatus,
      updateData
    );
  }

  saveOrUpdateFinancialObligations(searchData) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateFinancialObligations, searchData)
      .subscribe((response: any) => {
        this.onApplicationFormLiabilityScreenDataChange.next(response);
      });
  }

  saveOrUpdateBorrowerGuarantor(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateBorrowerGuarantor, data)
      .subscribe((response: any) => {
        this.onApplicationFormCribChange.next(response);
      });
  }

  getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(data) {
    this.dataService
      .post(
        SETTINGS.ENDPOINTS
          .getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode,
        data
      )
      .subscribe((response: any) => {
        this.upmGroups = response;
        this.onUpmGroupChange.next(response);
      });
  }

  async getEligibleUsers(createdAdUserID, currentAssignUser, data) {
    let eligibleUsers = [];
    let userUpmData = await this.getUserUPMData(createdAdUserID);

    if (!userUpmData.divCode) {
      userUpmData = await this.getUserUPMData(currentAssignUser);
    }

    if (
      userUpmData &&
      userUpmData.divCode &&
      userUpmData.divCode != this.applicationService.getLoggedInUserDivCode() &&
      data.divCode != this.applicationService.getLoggedInUserDivCode()
    ) {
      let assignedUserEligibleUsers: [] =
        await this.getUserDetailListFormBranchAuthorityLevel({
          solId: this.applicationService.getLoggedInUserDivCode(),
          roleId: data.roleId,
          appCode: "",
        });
      eligibleUsers.push(...assignedUserEligibleUsers);
    }

    if (
      userUpmData &&
      userUpmData.divCode &&
      userUpmData.divCode != data.divCode
    ) {
      let createdUserEligibleUsers: [] =
        await this.getUserDetailListFormBranchAuthorityLevel({
          solId: userUpmData.divCode,
          roleId: data.roleId,
          appCode: "",
        });

      eligibleUsers.push(...createdUserEligibleUsers);
    }

    let applicationFormEligibleUsers: [] =
      await this.getUserDetailListFormBranchAuthorityLevel(data);

    eligibleUsers.push(...applicationFormEligibleUsers);

    this.userDetails = _.uniqBy(eligibleUsers, (i) => i.userID);

    this.onUserDetailFromBranchAuthorityChange.next(this.userDetails);
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

  getAFReturnUsersList(data) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.getAFReturnUsersList, data)
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    });
  }

  updateFPFacilityDisplayOrderAndStatus(searchData) {
    this.dataService
      .post(
        SETTINGS.ENDPOINTS.updateAFFacilityDisplayOrderAndStatus,
        searchData
      )
      .subscribe((response: any) => {
        this.onAFFacilitiesChange.next(response);
        this.onApplicationFormSecuritiesChange.next(response);
      });
  }

  isAbleToEdit() {
    let applicationForm: any = {};
    this.onApplicationFormChange.subscribe((response: any) => {
      applicationForm = response;
    });

    return (
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_APPLICATION_FORM_EDIT
      ) &&
      applicationForm.assignUserID ==
        this.applicationService.getLoggedInUserUserID() &&
      (applicationForm.currentApplicationFormStatus ==
        this.applicationFormStatusConst.DRAFT ||
        applicationForm.currentApplicationFormStatus ==
          this.applicationFormStatusConst.RETURNED ||
        applicationForm.currentApplicationFormStatus ==
          this.applicationFormStatusConst.IN_PROGRESS)
    );
  }

  uploadFacilityDocument(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.uploadAFFacilityDocument, data)
      .subscribe((response: any) => {
        this.onAFFacilitiesChange.next(response);
      });
  }

  deactivateAFFacilityDocuments(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.deactivateAFFacilityDocuments, data)
      .subscribe((response: any) => {
        this.onAFFacilitiesChange.next(response);
      });
  }

  replicateApplicationForm(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.replicateApplicationForm, data)
      .subscribe((response: any) => {
        this.applicationFormDTO = response;
        this.onApplicationFormChange.next(response);
        this.onApplicationFormBasicDetailsChange.next(response);
        this.onApplicationFormDocumentChange.next(response);
        this.onApplicationFormTopicsChange.next(response);
        this.onAFFacilitiesChange.next(response);
        this.onApplicationFormSecuritiesChange.next(response);
        this.onApplicationFormCribChange.next(response);
        this.onApplicationFormRiskRateChange.next(response);
        this.onApplicationFormLiabilityScreenDataChange.next(response);
        this.onAFCommentsChange.next(response);
        this.selectedApplicationFormID = this.urlEncodeService.encode(
          response.applicationFormID
        );

        if (response.riskCategories) {
          this.onAFESGRiskScoreChange.next(response.riskCategories);
        } else {
          this.onAFESGRiskScoreChange.next([]);
        }
        this.onESGRiskOpinionChange.next([]);

        this.route.navigate(["/application-forms/dashboard"]);
      });
  }

  saveOrUpdateAFComment(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateAFComment, data)
      .subscribe((response: any) => {
        this.onAFCommentsChange.next(response);
      });
  }

  getEnvironmentalRiskCategories() {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getEnvironmentalRiskTree)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
            } else {
              resolve([]);
            }
          },
          (err: any) => {
            resolve([]);
          }
        );
    });
  }

  saveEnvironmentalRiskCategory(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveEnvironmentalRiskCategory, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
              this.onAFESGRiskScoreChange.next(response);

              let afData: any;
              this.onApplicationFormChange.subscribe((data: any) => {
                if (data) {
                  afData = data;
                }
              });
              if (afData !== null) {
                this.onApplicationFormChange.next({
                  ...afData,
                  isESGApproved: Constants.yesNoConst.N,
                  isESGPaper: Constants.yesNoConst.Y,
                });
              }
              this.alertService.showToaster(
                "Data has been saved successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );
            } else {
              resolve([]);
            }
          },
          (err: any) => {
            resolve([]);
          }
        );
    });
  }

  getAnnexureById(annexureId: number): Promise<AFAnnexureDTO> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAnnexureById);
      data.url = data.url + "/" + annexureId;
      this.dataService.get(data).subscribe((response: any) => {
        resolve(response as AFAnnexureDTO);
      }, reject);
    });
  }

  saveAnnexureAnswer(payload: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveEsgAnnexure, payload)
        .subscribe((response: any) => {
          this.getAnnexureByApplicationFormID(payload.applicationFormID).then(
            (latestAnnexures) => {
              this.onAFESGChange.next(latestAnnexures);
            }
          );
          resolve(response);
        }, reject);
    });
  }

  getAnnexureByApplicationFormID(applicationFormId: number): Promise<any[]> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getAnnexureByPaperID);
      data.url += "/" + applicationFormId;

      this.dataService.get(data).subscribe(
        (res: any) => {
          const result = Array.isArray(res) ? res : res.result || [];

          this.onAFESGChange.next(result);
          resolve(result);
        },
        (err) => {
          this.onAFESGChange.next([]); // Clear annexures on error too
          reject(err);
        }
      );
    });
  }

  getAnnexureByAnnexureDataId(annexureDataId: number): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getAnnexureByAnnexureDataId
      );
      data.url += "/" + annexureDataId;

      this.dataService.get(data).subscribe((res: any) => resolve(res), reject);
    });
  }

  updateAnnexureAnswer(annexureDataId: number, payload: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.updateDataToAnnexure);
      data.url += "/" + annexureDataId;

      this.dataService.post(data, payload).subscribe((res: any) => {
        this.getAnnexureByApplicationFormID(payload.applicationFormID).then(
          (latest) => {
            this.onAFESGChange.next(latest);
          }
        );
        resolve(res);
      }, reject);
    });
  }

  getAnnexureList(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAnnexureList).subscribe(
        (response: any) => {
          resolve(response);
        },
        () => {
          reject();
        }
      );
    });
  }

  removeAFEnvironmentalRisk(applicationFormId: any) {
    return new Promise<any>((resolve, reject) => {
      let data: any = SETTINGS.ENDPOINTS.removeAFEnvironmentalRisk;
      data.url = data.url + "/" + applicationFormId;
      this.dataService.post(data).subscribe(
        (response: any) => {
          if (response) {
            resolve(response);
            this.onAFESGRiskScoreChange.next(response);

            let afData: any;
            this.onApplicationFormChange.subscribe((data: any) => {
              if (data) {
                afData = data;
              }
            });
            if (afData !== null) {
              this.onApplicationFormChange.next({
                ...afData,
                isESGApproved: Constants.yesNoConst.N,
                isESGPaper: Constants.yesNoConst.N,
                approvedESGScore: "",
              });
            }

            this.alertService.showToaster(
              "Data has been removed successfully.",
              SETTINGS.TOASTER_MESSAGES.success
            );
          } else {
            resolve([]);
          }
        },
        (err: any) => {
          resolve([]);
        }
      );
    });
  }

  approveAFEnvironmentalRisk(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.approveAFEnvironmentalRisk, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
              this.alertService.showToaster(
                "Data has been approved successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );

              let afData: any;
              this.onApplicationFormChange.subscribe((data: any) => {
                if (data) {
                  afData = data;
                }
              });
              this.onApplicationFormChange.next({
                ...afData,
                isESGApproved: response.isESGApproved,
                isESGPaper: response.isESGPaper,
                approvedESGScore: response.approvedESGScore,
              });
            } else {
              resolve(null);
            }
          },
          (err: any) => {
            resolve(null);
          }
        );
    });
  }

  getAFEnvironmentalRiskOpinion(applicationFormId: any) {
    let prevData: any[] = [];
    this.onESGRiskOpinionChange.subscribe((res: any[]) => {
      prevData = res;
    });

    if (prevData && prevData.length > 0) {
      return prevData;
    }

    return new Promise<any[]>((resolve, reject) => {
      let request: any = {
        ...SETTINGS.ENDPOINTS.getAFEnvironmentalRiskOpinion,
        url: `${SETTINGS.ENDPOINTS.getAFEnvironmentalRiskOpinion.url}/${applicationFormId}`,
      };
      this.dataService.get(request).subscribe(
        (response: any) => {
          this.onESGRiskOpinionChange.next(response);
        },
        (err: any) => {
          resolve([]);
        }
      );
    });
  }

  saveEnvironmentalRiskOpinion(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveAFEnvironmentalRiskOpinion, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
              this.onESGRiskOpinionChange.next(response);
              this.alertService.showToaster(
                "Data has been saved successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );
            } else {
              resolve([]);
            }
          },
          (err: any) => {
            resolve([]);
          }
        );
    });
  }

  saveEnvironmentalRiskReply(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.saveAFEnvironmentalRiskReply, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
              this.onESGRiskOpinionChange.next(response);
              this.alertService.showToaster(
                "Data has been saved successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );
            } else {
              resolve([]);
            }
          },
          (err: any) => {
            resolve([]);
          }
        );
    });
  }

  removeEnvironmentalRiskOpinion(payload: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(SETTINGS.ENDPOINTS.removeAFEnvironmentalRiskOpinion, payload)
        .subscribe(
          (response: any) => {
            if (response) {
              resolve(response);
              this.onESGRiskOpinionChange.next(response);
              this.alertService.showToaster(
                "Data has been removed successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );
            } else {
              resolve([]);
            }
          },
          (err: any) => {
            resolve([]);
          }
        );
    });
  }
}
