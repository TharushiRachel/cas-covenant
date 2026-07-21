import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import {FpCustomerOtherBankFacilityComponent} from "../fp-customer-other-bank-facility/fp-customer-other-bank-facility.component";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-personal-detail-ob-facility',
  templateUrl: './personal-detail-ob-facility.component.html',
  styleUrls: ['./personal-detail-ob-facility.component.scss']
})
export class PersonalDetailObFacilityComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  tableColumns = ['Bank Name', 'Branch Name', 'Facility Amount', 'Outstanding Amount', 'Facility Type', 'Disbursement Date', 'Maturity Date', 'Securities', 'Action'];
  @Input('customer') customer: any = {};
  @Input('facilityPaper') facilityPaper: any = {};

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  otherBankFacilityResponse: any = {};
  onOtherBankFacilityChangeSub = new Subscription();
  casCustomerOtherBankFacilityDTOList = [];

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private mdbModalService: MDBModalService,
    private applicationService: ApplicationService,
  ) {
  }

  ngOnInit() {

    this.onOtherBankFacilityChangeSub = this.facilityPaperAddEditService.onOtherBankDetailsChange
      .subscribe((data: any) => {
        this.otherBankFacilityResponse = data;
        let customer = data.casCustomerDTOList.find(data => data.casCustomerID == this.customer.casCustomerID);
        if (customer) {
          this.casCustomerOtherBankFacilityDTOList = customer.casCustomerOtherBankFacilityDTOList;
        }
      });
  }

  ngOnDestroy(): void {
    this.onOtherBankFacilityChangeSub.unsubscribe();
  }

  openModalOtherBankFacility(facilityPaper, otherFacilityItem?) {

    const initialState = {
      list: [
        {"tag": 'Count', "value": otherFacilityItem}
      ]
    };

    this.modalRef = this.mdbModalService.show(FpCustomerOtherBankFacilityComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: 'right',
      animated: true,
      data: {
        heading: "comming dto",
        content: {
          casCustomerID: this.customer.casCustomerID,
          facilityPaper: facilityPaper,
          otherFacilityItem: otherFacilityItem
        },
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

}
