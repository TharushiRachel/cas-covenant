import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {FacilityPaperAddEditService} from "../../../../../services/facility-paper-add-edit.service";
import {FpCustomerSearchComponent} from "./fp-customer-search/fp-customer-search.component";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {NewNonFinacleCustomerAddEditComponent} from "../../../../../../../../shared/components/new-non-finacle-customer-add-edit/new-non-finacle-customer-add-edit.component";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {Subscription} from "rxjs";
import { AlertService } from 'src/app/core/service/common/alert.service';

@Component({
  selector: 'app-fp-personal-details',
  templateUrl: './fp-personal-details.component.html',
  styleUrls: ['./fp-personal-details.component.scss'],
})
export class FpPersonalDetailsComponent implements OnInit, OnDestroy {

  onCustomerListChangeSub: Subscription = new Subscription();
  facilityPaper: any = {};
  @Input('mainFacilityPaper') mainFacilityPaper: any = {};

  @Output('goToKalipto') goToKalipto = new EventEmitter();
  
  casCustomerDTOList = [];
  customerSearchModalRef: MDBModalRef;
  joinNonFinacleCustomerModalRef: MDBModalRef;
  customer: any = {};

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  equalLoginUserAndAssignUser = false;

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  watchlistStatus:any
  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private toastr: AlertService
  ) {
  }

  ngOnInit() {
    this.onCustomerListChangeSub = this.facilityPaperAddEditService.onFpCustomerChange
      .subscribe((data: any) => {
        this.facilityPaper = data;
        this.casCustomerDTOList = this.facilityPaper.casCustomerDTOList;
      });
    this.isEqualLoginAndAssignUser();
    
    this.getWatchlistStatus()
  }

  ngOnDestroy(): void {
    this.onCustomerListChangeSub.unsubscribe();
  }

  openModalAddCustomer(facilityPaper) {

    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityPaper}
      ]
    };

    this.customerSearchModalRef = this.mdbModalService.show(FpCustomerSearchComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "comming dto",
        content: {facilityPaper: facilityPaper},
        displayOrder: this.casCustomerDTOList.length + 1
      }
    });
    this.customerSearchModalRef.content.action.subscribe((result: any) => {
      if (!_.isEmpty(result)) {
        if (result.isJoinNonFinacleCustomer) {
          this.joinNonFinacleCustomer(result);
        }
      }
    });

  }

  isEqualLoginAndAssignUser() {
    if (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED;
  }

  isRejected() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.REJECTED;
  }

  onKaliptoDataLoad(customerData) {
    this.goToKalipto.emit(customerData);
  }

  joinNonFinacleCustomer(data) {
    this.joinNonFinacleCustomerModalRef = this.mdbModalService.show(NewNonFinacleCustomerAddEditComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "Customer Details",
        actionMessage: "Join Customer",
        identificationNumber: data.identificationNumber,
        identificationType: data.identificationType,
      }
    });

    this.joinNonFinacleCustomerModalRef.content.action.subscribe((nonFinacleCustomerData: any) => {
      if (!_.isEmpty(nonFinacleCustomerData)) {
        let casCustomerDTO = {};
        switch (nonFinacleCustomerData.type) {
          case this.basicInformationTypeConst.PERSONAL:
            casCustomerDTO = {...nonFinacleCustomerData.personalInformationForm};
            break;
          case this.basicInformationTypeConst.CORPORATE:
            casCustomerDTO = {...nonFinacleCustomerData.corporateInformationForm};
            break;
          case this.basicInformationTypeConst.BUSINESS:
            casCustomerDTO = {...nonFinacleCustomerData.businessInformationForm};
            break;
        }
        casCustomerDTO = Object.assign(casCustomerDTO, nonFinacleCustomerData);

        let updateData = Object.assign({},
          {
            facilityPaperID: this.facilityPaper.facilityPaperID,
            casCustomerID: null,
            isPrimary: Constants.yesNoConst.N,
            displayOrder: this.casCustomerDTOList.length + 1,
            status: 'ACT'
          },
          {casCustomerDTO: casCustomerDTO}
        );
        this.facilityPaperAddEditService.addEditNonFinacleCasCustomer(AppUtils.trim(updateData));
      }
      this.joinNonFinacleCustomerModalRef.hide();
    });
  }

  getWatchlistStatus(){
    
   
    let cusID= this.facilityPaper.casCustomerDTOList.find(customer => customer.isPrimary ===true).customerFinancialID ? this.facilityPaper.casCustomerDTOList.find(customer => customer.isPrimary ===true).customerFinancialID : null;
    if(cusID){
    let cusIDRQ = {cusId:`${cusID}`}
    this.facilityPaperAddEditService.getWatchlistStatus(cusIDRQ).then((response)=>{
      this.watchlistStatus=  response && response.result != undefined
      ? response.result
      : response;  
    }).catch((err)=>{
      this.toastr.showToaster("could not load watch list status", SETTINGS.TOASTER_MESSAGES.error)
    })
  }
  else{
    this.toastr.showToaster("Could not find CIF to get watch list status", SETTINGS.TOASTER_MESSAGES.warning)
  }
  }
}
