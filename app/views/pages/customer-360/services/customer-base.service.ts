import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {SETTINGS} from "../../../../core/setting/commons.settings";
import {DataService} from "../../../../core/service/data/data.service";
import {Pagination} from "../../../../core/dto/pagination";
import {Constants} from "../../../../core/setting/constants";
import {CacheService} from "../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../core/service/application/application.service";
import { AlertService } from 'src/app/core/service/common/alert.service';

@Injectable()
export class CustomerBaseService implements Resolve<any> {

  customerPagedLeadDTOList: any = [];
  onCustomerPagedLeadDTOChange: any = new Subject();
  pagedFacilityPaperSummary: any = [];
  customer360Details: any = {};
  onPagedFPSummaryChange: any = new Subject();
  onCustomer360DetailsChange = new BehaviorSubject({});
  finacleFacilityDetails: any = [];
  onFinacleFacilityDetailsChange: any = new Subject();

  kalyptoData: any = {};
  onKalyptoDataChange: any = new Subject();

  onNewFacilityPaperDraft = new Subject();

  constructor(private dataService: DataService,
              private cacheService: CacheService,
              private applicationService: ApplicationService,
              private alertService: AlertService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise((resolve, reject) => {
      Promise.all([
        this.cacheService.loadData(Constants.masterDataKey.CAS_BRANCHES),
        this.cacheService.loadData(Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES)
      ]).then(() => {
        resolve();
      }, reject)
    });
  }

  searchCustomer360(searchData) {
    this.onCustomer360DetailsChange.next({});
    this.dataService.post(SETTINGS.ENDPOINTS.searchCustomerFrom360, searchData)
      .subscribe((response: any) => {
        this.customer360Details = response;
        this.onCustomer360DetailsChange.next(response);
        console.log("rrr", response)
        if(response.errorMessage != null){
          this.alertService.showToaster(
            "Failed to load customer details. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      });
  }

  getPagedFacilityPaperSummary(searchData?, paginationData?: Pagination) {
    this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getPagedFacilityPaperSummary,
      searchData, paginationData).subscribe((response: any) => {
      this.pagedFacilityPaperSummary = response.pageData;
      this.onPagedFPSummaryChange.next(response);
    });
  }

  getCustomerPagedLeadDTO(searchData?, paginationData?: Pagination) {
    this.dataService.postWithPageData(SETTINGS.ENDPOINTS.getCustomerPagedLeadDTO,
      searchData, paginationData).subscribe((response: any) => {
      this.customerPagedLeadDTOList = response.pageData;
      this.onCustomerPagedLeadDTOChange.next(response);
    });
  }

  getFacilityDetailResponses(searchData) {

    let customerDetails = Object.assign({}, {
      fromdate: null,
      todate: null,
      accno: searchData.bankAccountNumber,
      cumm: searchData.customerFinancialID,
      valType: searchData.bankAccountNumber ? 'A' : searchData.customerFinancialID ? 'C' : 'A',
      nic: searchData.identificationNumber,
      aduser: this.applicationService.getLoggedInUserUserName(),
      userId: this.applicationService.getLoggedInUserUserID(),
      refId: this.applicationService.getLoggedInUserUserName()
    });

    if (customerDetails.accno) {
      this.dataService.post(SETTINGS.ENDPOINTS.getCustomerFacilityDetailsByAccountNumber, customerDetails)
        .subscribe((response: any) => {
          this.finacleFacilityDetails = response.advancedPortfolioDTOS;
          this.onFinacleFacilityDetailsChange.next(response.advancedPortfolioDTOS);
        })
    } else {
      this.finacleFacilityDetails = [];
      this.onFinacleFacilityDetailsChange.next({});
    }
  }

  getKalyptoDetail(searchData) {
    this.dataService.post(SETTINGS.ENDPOINTS.getKalyptoDetail, searchData)
      .subscribe((response: any) => {
        this.kalyptoData = response;
        this.onKalyptoDataChange.next(response);
      })
  }

  draftFacilityPaper(searchData) {
    this.dataService.post(SETTINGS.ENDPOINTS.draftFacilityPaper, searchData)
      .subscribe((response: any) => {
        this.onNewFacilityPaperDraft.next(response);
      })
  }

  draftFacilityPaperWithNonFinacleCustomer(searchData) {
    this.dataService.post(SETTINGS.ENDPOINTS.draftFacilityPaperWithNonFinacleCustomer, searchData)
      .subscribe((response: any) => {
        this.onNewFacilityPaperDraft.next(response);
      })
  }

  updateCustomerDTO(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.saveOrUpdateCustomerDTO, data)
      .subscribe((response: any) => {
        this.customer360Details = response;
        this.onCustomer360DetailsChange.next(response);
      })
  }

  refreshCustomerDetailFromBank(searchData) {
    this.onCustomer360DetailsChange.next({});
    this.dataService.post(SETTINGS.ENDPOINTS.refreshCustomerDetailFromBank, searchData)
      .subscribe((response: any) => {
        this.customer360Details = response;
        this.onCustomer360DetailsChange.next(response);
      });
  }

}
