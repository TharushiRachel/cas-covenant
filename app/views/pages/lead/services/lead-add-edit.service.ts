import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../core/setting/commons.settings";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { DataService } from "../../../../core/service/data/data.service";
import { UrlEncodeService } from "../../../../core/service/application/url-encode.service";
import { CacheService } from "../../../../core/service/data/cache.service";
import { Constants } from "../../../../core/setting/constants";
import { Pagination } from "../../../../core/dto/pagination";
import { SearchDataCacheService } from "../../../../core/service/common/search-data-cache.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { log } from "console";

@Injectable()
export class LeadAddEditService implements Resolve<any> {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  uniquePageName = "LeadAddEditComponent-#343rta";

  customers = [];
  selectedCustomer: any = {};
  supportingDocs = [];
  selectedLead: any = {};
  uploadedLeadDocument: any = {};
  onSelectedLeadChange: BehaviorSubject<any> = new BehaviorSubject({});
  onLeadStatusUpdated = new Subject();

  onLeadBasicDetailsChange = new BehaviorSubject({});
  onCustomersChange: BehaviorSubject<any> = new BehaviorSubject({});
  onSelectedCustomerChange = new Subject();
  onLeadFacilityPaperStatusHistoryChange = new BehaviorSubject({});
  onDownloadLinkChanged: Subject<any> = new Subject();
  onViewLinkChanged: Subject<any> = new Subject();

  onAgentFacilityPaperDrafted: Subject<any> = new Subject();
  onLeadCommentsChange = new BehaviorSubject({});
  leadStatusConst = Constants.leadStatusConst;

  constructor(
    private dataService: DataService,
    private urlEncodeService: UrlEncodeService,
    private cacheService: CacheService,
    private searchDataCacheService: SearchDataCacheService,
    private applicationService: ApplicationService,
    private http: HttpClient,
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.getLeadUpdateDto(),
        // this.getCustomers(),
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        //this.cacheService.loadData(Constants.masterDataKey.CAS_CUSTOMERS),
        this.cacheService.loadData(Constants.masterDataKey.CAS_SUPPORTING_DOCs),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES,
        ),
        this.cacheService.loadData(
          Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES,
        ),
      ]).then(() => {
        resolve();
      }, reject);
    });
  }

  getLeadUpdateDto(): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.selectedLeadID == null) {
        this.selectedLead = {};
        this.onSelectedLeadChange.next(this.selectedLead);

        resolve({});
      } else {
        const data = Object.assign({}, SETTINGS.ENDPOINTS.getLeadUpdateDTO);
        data.url =
          data.url + "/" + this.urlEncodeService.decode(this.selectedLeadID);
        this.dataService.get(data).subscribe((response: any) => {
          this.selectedLead = response;
          this.onSelectedLeadChange.next(this.selectedLead);
          this.onLeadCommentsChange.next(this.selectedLead);
          resolve(response);
        }, reject);
      }
    });
  }

  // saveUpdateLead(lead) {
  //   this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateLead, lead)
  //     .subscribe((response: any) => {
  //       this.selectedLead = response;
  //       this.onSelectedLeadChange.next(response);
  //     })
  // }

  saveUpdateLead(lead) {
    return this.dataService.post(SETTINGS.ENDPOINTS.updateLead, lead);
  }

  submitLead(lead) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.submitLead, lead)
      .subscribe((response: any) => {
        this.selectedLead = response;
        this.onSelectedLeadChange.next(response);
      });
  }

  saveUploadedSupportDocuments(supportDocumentDTO: any) {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .post(
          SETTINGS.ENDPOINTS.saveUploadedSupportingDocuments,
          supportDocumentDTO,
        )
        .subscribe((response: any) => {
          this.uploadedLeadDocument = response;
          resolve(response);
        });
    });
  }

  updateLeadStatusOrAssignee(assigneeOrStatusUpdateDto) {
    this.dataService
      .post(
        SETTINGS.ENDPOINTS.updateLeadStatusOrAssignee,
        assigneeOrStatusUpdateDto,
      )
      .subscribe((response: any) => {
        this.selectedLead = response;
        this.onSelectedLeadChange.next(this.selectedLead);
        this.onLeadStatusUpdated.next(true);
      });
  }

  updateLeadStatus(updateData) {
    return this.dataService.post(
      SETTINGS.ENDPOINTS.updateLeadStatusOrAssignee,
      updateData,
    );
  }

  downloadLeadDocument(docStorage) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + "/" + docStorage.docStorageID;
      this.dataService.get(data).subscribe((response: any) => {
        // this.onDownloadLinkChanged.next({
        //   data: response,
        //   fileName: docStorage.fileName
        // });
        resolve(response);
      }, reject);
    });
  }

  viewLeadDocument(docStorage): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + "/" + docStorage.docStorageID;
      this.dataService.get(data).subscribe((response: any) => {
        // this.onViewLinkChanged.next({
        //   data: response,
        //   fileName: docStorage.fileName
        // });
        resolve(response);
      }, reject);
    });
  }

  getFacilityTypes(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getFacilityTypes)
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    });
  }

  getCustomers(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchCustomers(searchData, paginationData);
  }

  searchCustomers(searchData?, paginationData?: Pagination): Promise<any> {
    if (!searchData) {
      searchData = {};
    }

    searchData.status = Constants.statusConst.ACT;

    return new Promise((resolve, reject) => {
      this.dataService
        .postWithPageData(
          SETTINGS.ENDPOINTS.getPagedCustomers,
          searchData,
          paginationData,
        )
        .subscribe((response: any) => {
          this.customers = response.pageData;
          this.onCustomersChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getCustomerById(customerID): Promise<any> {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.getCustomerByID);
      data.url = data.url + "/" + customerID;
      this.dataService.get(data).subscribe((response: any) => {
        this.selectedCustomer = response.customerName;
        this.onSelectedCustomerChange.next(response);
        resolve(this.selectedCustomer);
      }, reject);
    });
  }

  draftFacilityPaperByLead(data) {
    return this.dataService
      .post(SETTINGS.ENDPOINTS.draftFacilityPaperByLead, data)
      .subscribe((response: any) => {
        this.onAgentFacilityPaperDrafted.next(response);
      });
  }

  updateLead(lead) {
    return this.dataService.post(SETTINGS.ENDPOINTS.updateLead, lead);
  }

  getFacilityPaperHistoryForLead(leadDto) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.getFacilityPaperHistoryForLead, leadDto)
      .subscribe((response: any) => {
        this.onLeadFacilityPaperStatusHistoryChange.next(response);
      });
  }

  draftApplicationForm(searchData) {
    return this.dataService.post(
      SETTINGS.ENDPOINTS.draftApplicationForm,
      searchData,
    );
  }

  saveOrUpdateLeadComment(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.saveOrUpdateLeadComment, data)
      .subscribe((response: any) => {
        this.onLeadCommentsChange.next(response);
      });
  }

  /* isAbleToEdit(leadUpdateDTO) {
      return (leadUpdateDTO.leadStatus == this.leadStatusConst.PENDING
            || leadUpdateDTO.leadStatus == this.leadStatusConst.RETURNED)
          ;
    }*/

  deactivateLeadSupportDocument(data) {
    this.dataService
      .post(SETTINGS.ENDPOINTS.deactivateLeadSupportingDoc, data)
      .subscribe((response: any) => {
        this.onLeadBasicDetailsChange.next(response);
      });
  }

  getInprincipalLetter(payload: any) {
    return new Promise((resolve, reject) => {
      const data = Object.assign(
        {},
        SETTINGS.ENDPOINTS.getInPrincipleSanctionLetterContent,
      );
      this.dataService.post(data, payload).subscribe((response: any) => {
        resolve(response);
      }, reject);
    });
  }
}
