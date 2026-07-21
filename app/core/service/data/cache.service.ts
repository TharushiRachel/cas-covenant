import { Injectable } from "@angular/core";
import { DataService } from "./data.service";
import { Constants } from "../../setting/constants";
import { SETTINGS } from "../../setting/commons.settings";
import { BehaviorSubject } from "rxjs";

@Injectable()
export class CacheService {
  masterDataCache: any = {};
  getBrnachesChange = new BehaviorSubject({});

  constructor(private dataService: DataService) { }

  expireCache() {
    this.masterDataCache = {};
  }

  expireCacheData(key: string) {
    this.masterDataCache[key] = null;
  }

  loadData(key: string, postData?): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.masterDataCache[key]) {
        resolve();
      } else {
        switch (key) {
          case Constants.masterDataKey.CAS_CONSTANTS:
            return this.getAllConstants(resolve, reject);
          case Constants.masterDataKey.CAS_BRANCHES:

            return this.getAllBranches(resolve, reject);
          case Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED:
            return this.getAllPurposeOfAdvanced(resolve, reject);
          case Constants.masterDataKey.CAS_SECTOR_DATA:
            return this.getAllSectorData(resolve, reject);
          case Constants.masterDataKey.CAS_SUB_SECTOR_DATA:
            return this.getAllSubSectorData(resolve, reject);
          case Constants.masterDataKey.CAS_CUSTOMERS:
            return this.getAllActiveCustomers(resolve, reject);
          case Constants.masterDataKey.CAS_SUPPORTING_DOCs:
            return this.getSupportinDocs(resolve, reject);
          // case Constants.masterDataKey.CAS_GLOBAL_SUPPORTING_DOCs:
          //   return this.getGlobalSupportingDocs(resolve, reject);
          case Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES:
            return this.getCreditFacilityTemplates(resolve, reject);
          case Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES:
            return this.getApproveFacilityTypes(resolve, reject);
          case Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES:
            return this.getAllCftInterestRateDTOS(resolve, reject);
          case Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES:
            return this.getApprovedWorkFlowTemplates(resolve, reject);
          case Constants.masterDataKey.CAS_SECURITY_SUMMARY_TOPICS:
            return this.getActiveSecuritySummaryTopics(resolve, reject);
          case Constants.masterDataKey.CAS_APPLICATION_FORM_TOPICS:
            return this.getApprovedApplicationFormTopics(resolve, reject);
          case Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST:
            return this.getCasActiveBranchDepartmentList(resolve, reject);
          case Constants.masterDataKey.CAS_APPLICATION_USER_ASSISTANTS:
            return this.getAssistants(resolve, reject, postData);
          case Constants.masterDataKey.CAS_COMMITTEE_TYPE_LIST:
            return this.getCommitteeTypeList(resolve, reject);
          case Constants.masterDataKey.CAS_COMMITTEE_LIST:
            return this.getCommitteeList(resolve, reject);
          case Constants.masterDataKey.CAS_COMMITTEE_LEVEL_LIST:
            return this.getCommitteeLevelList(resolve, reject);
          /* case Constants.masterDataKey.CAS_COMMITTEE_USER_LIST:
              return this.getCommitteeUserList(resolve, reject);*/
          case Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_DIV:
            return this.getSecurityDocumentSubmitDiv(resolve, reject);
          case Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_WORK_CLASS:
            return this.getSecurityDocumentSubmitWorkClass(resolve, reject);
          case Constants.masterDataKey.CAS_BCC_ENTERER_WORK_CLASS:
            return this.getBCCEntererWorkClass(resolve, reject);
          case Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS:
            return this.getBCCAuthorizerWorkClass(resolve, reject);
          case Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS:
            return this.getBCCInquirerWorkClass(resolve, reject);
          default:
            return;
        }
      }
    });
  }

  getData(key: string): any {
    return this.masterDataCache[key];
  }

  private getAllConstants(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getConstants).subscribe(
        (response: any) => {
          this.masterDataCache[Constants.masterDataKey.CAS_CONSTANTS] =
            response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getAllBranches(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getBranchList).subscribe(
        (response: any) => {
          this.masterDataCache[Constants.masterDataKey.CAS_BRANCHES] =
            response.branchLoadResponseDTOArrayList;
          this.getBrnachesChange.next(response.branchLoadResponseDTOArrayList);
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getAllPurposeOfAdvanced(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getAllPurposeOfAdvanced)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getAllSectorData(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllSectorData).subscribe(
        (response: any) => {
          this.masterDataCache[Constants.masterDataKey.CAS_SECTOR_DATA] =
            response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getAllSubSectorData(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getAllSubSectorData).subscribe(
        (response: any) => {
          this.masterDataCache[Constants.masterDataKey.CAS_SUB_SECTOR_DATA] =
            response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getAllActiveCustomers(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getActiveCustomersList).subscribe(
        (response: any) => {
          this.masterDataCache[Constants.masterDataKey.CAS_CUSTOMERS] =
            response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getSupportinDocs(resolveParent, rejectParent): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getApprovedSupportingDocList)
        .subscribe(
          (response: any) => {
            this.masterDataCache[Constants.masterDataKey.CAS_SUPPORTING_DOCs] =
              response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  // private getGlobalSupportingDocs(resolveParent, rejectParent): Promise<any> {

  //   return new Promise((resolve, reject) => {
  //     this.dataService.get(SETTINGS.ENDPOINTS.getApprovedGlobalSupportingDocList)
  //       .subscribe((response: any) => {
  //         this.masterDataCache[Constants.masterDataKey.CAS_GLOBAL_SUPPORTING_DOCs] = response;
  //         resolve(response);
  //         resolveParent(response)
  //       }, () => {
  //         reject();
  //         rejectParent();
  //       });
  //   })
  // }

  private getCreditFacilityTemplates(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getApprovedCreditFacilityTemplateList)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getApproveFacilityTypes(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getApprovedFacilityTypeList)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getAllCftInterestRateDTOS(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getAllCftInterestRateDTOS)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getApprovedWorkFlowTemplates(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getAllWorkFlowTemplates)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getActiveSecuritySummaryTopics(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getActiveSecuritySummaryTopics)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_SECURITY_SUMMARY_TOPICS
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getApprovedApplicationFormTopics(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getApprovedApplicationFormTopics)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_APPLICATION_FORM_TOPICS
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getCasActiveBranchDepartmentList(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getCasActiveBranchDepartmentList)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getAssistants(resolveParent, rejectParent, data): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.post(SETTINGS.ENDPOINTS.getAssistants, data).subscribe(
        (response: any) => {
          this.masterDataCache[
            Constants.masterDataKey.CAS_APPLICATION_USER_ASSISTANTS
          ] = response.branchAuthorityLevelResponseDTOList;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getSecurityDocumentSubmitDiv(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getSecurityDocumentSubmitDiv)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_DIV
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getSecurityDocumentSubmitWorkClass(
    resolveParent,
    rejectParent
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getSecurityDocumentSubmitWorkClass)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_WORK_CLASS
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getCommitteeTypeList(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getCommitteeType).subscribe(
        (response: any) => {
          this.masterDataCache[
            Constants.masterDataKey.CAS_COMMITTEE_TYPE_LIST
          ] = response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getCommitteeList(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getCommittees).subscribe(
        (response: any) => {
          this.masterDataCache[Constants.masterDataKey.CAS_COMMITTEE_LIST] =
            response
              ? response.filter(
                (r: any) =>
                  r.status == Constants.statusConst.ACT &&
                  r.approveStatus == Constants.approveStatusConst.APPROVED
              )
              : [];
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getCommitteeLevelList(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getCommitteeLevels).subscribe(
        (response: any) => {
          this.masterDataCache[
            Constants.masterDataKey.CAS_COMMITTEE_LEVEL_LIST
          ] = response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getBCCEntererWorkClass(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService.get(SETTINGS.ENDPOINTS.getBCCEntererWorkClass).subscribe(
        (response: any) => {
          this.masterDataCache[
            Constants.masterDataKey.CAS_BCC_ENTERER_WORK_CLASS
          ] = response;
          resolve(response);
          resolveParent(response);
        },
        () => {
          reject();
          rejectParent();
        }
      );
    });
  }

  private getBCCAuthorizerWorkClass(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getBCCAuthorizerWorkClass)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  private getBCCInquirerWorkClass(resolveParent, rejectParent): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getBCCInquirerWorkClass)
        .subscribe(
          (response: any) => {
            this.masterDataCache[
              Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS
            ] = response;
            resolve(response);
            resolveParent(response);
          },
          () => {
            reject();
            rejectParent();
          }
        );
    });
  }

  /* private getCommitteeUserList(resolveParent, rejectParent): Promise<any> {
        return new Promise<any>((resolve, reject) => {
          this.dataService.get(SETTINGS.ENDPOINTS.getCommitteeUsers)
            .subscribe((response: any) => {
               console.log("response-3",response);
              this.masterDataCache[Constants.masterDataKey.CAS_COMMITTEE_USER_LIST] = response;
              resolve(response);
              resolveParent(response)
            }, () => {
              reject();
              rejectParent();
            });
        });
      }*/
}
