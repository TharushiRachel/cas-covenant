import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { CustomerLimitsOutstandingDataComponent } from "../customer-limits-outstanding-data.component";

@Component({
  selector: "app-finacle-data-structure",
  templateUrl: "./finacle-data-structure.component.html",
  styleUrls: ["./finacle-data-structure.component.scss"],
})
export class FinacleDataStructureComponent implements OnInit {
  @Input("facility") facility;
  @Input("facilityPaper") facilityPaper = null;
  @Output("updateExOutstandingDate")
  updateExOutstandingDate: EventEmitter<any> = new EventEmitter();

  selectedLoanData: any;
  finacleData: any = null;
  accountNumberOfFacility: string | null = null;
  loanLimitsSubscription: Subscription = new Subscription();
  isOpendLoanLimiModal = false;
  customerFinancialID: string | null;
  facilityDTOList: any;
  modalRef: MDBModalRef;
  facilityPaperSub: Subscription = new Subscription();
  loanLimitSub: Subscription = new Subscription();
  auxFacilityPaper: any = null;
  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private toastr: AlertService,
    public mdbModalRef: MDBModalRef,
    private mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
   
    this.accountNumberOfFacility = this.facility.loanLimitId;
    // this.facilityPaperSub = this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(data => {
    //   this.auxFacilityPaper = data;
    // })

    this.facilityPaperAddEditService.onFpCustomerChange.subscribe((data) => {
      this.auxFacilityPaper = data;
    } );
  }

  ngOnDestroy(): void {
    this.facilityPaperSub.unsubscribe();
    this.loanLimitSub.unsubscribe();
    this.loanLimitsSubscription.unsubscribe();
  }

  loadFinacleData() {
    let facilityPaper: any = this.facilityPaper;
    let auxFacilityPaper: any = this.auxFacilityPaper;
    this.isOpendLoanLimiModal = false;
    if (facilityPaper) {
      const primaryCustomer = Array.isArray(auxFacilityPaper.casCustomerDTOList)
        ? auxFacilityPaper.casCustomerDTOList.find(
            (customer) => customer.isPrimary === true
          )
        : null;
       
      if (primaryCustomer) {
        this.customerFinancialID = primaryCustomer.customerFinancialID || null;
      } else {
        this.customerFinancialID = null;
      }

      this.facilityDTOList = Array.isArray(facilityPaper.facilityDTOList)
        ? facilityPaper.facilityDTOList
        : [];

      
      if (this.customerFinancialID) {
        this.handleFinacleData(this.customerFinancialID);
      } else {
        this.toastr.showToaster(
          "Primary Customer Finacle ID Not Found",
          "ERROR"
        );
      }

      //   this.customerFinancialID = facilityPaper.casCustomerDTOList.find(customer => customer.isPrimary ===true).customerFinancialID ? facilityPaper.casCustomerDTOList.find(customer => customer.isPrimary ===true).customerFinancialID : null;
      //   this.facilityDTOList = facilityPaper.facilityDTOList ?facilityPaper.facilityDTOList :[]
      //   if (this.customerFinancialID) {

      //   this.handleFinacleData(this.customerFinancialID)
      // }
      // else {
      //   this.toastr.showToaster("Customer Finacle ID Not Found", "ERROR")
      //  }
    } else {
      this.toastr.showToaster("Facility Paper Not Found", "ERROR");
    }
  }

  handleFinacleData(cusID) {
    let cusIDRQ = { cusId: `${cusID}` };

    this.facilityPaperAddEditService
      .getFinacaleData(cusIDRQ)
      .then((res) => {
        this.finacleData = res;

        this.finacleData = this.updateSelectedBy(
          this.finacleData,
          this.facilityDTOList
        );

        this.openLoanLimitModal();
      })
      .catch((err) => {
        this.toastr.showToaster("please contact administrator", "ERROR");
      });

    //  this.loanLimitSub = this.facilityPaperAddEditService.loanLimitsList.subscribe(data => {
    //   this.finacleData = data;
    //   if (this.finacleData === null) {

    //     this.facilityPaperAddEditService.getFinacaleData(cusIDRQ).then((res) => {
    //       // console.log("get finacle data",data)
    //       this.finacleData = res;
    //       this.finacleData = this.updateSelectedBy(this.finacleData, this.facilityDTOList)

    //       this.openLoanLimitModal()

    //     }).catch((err) => {
    //       this.toastr.showToaster("please contact administrator", "ERROR")
    //     })
    //   }
    //   else {

    //     if (!this.isOpendLoanLimiModal)
    //       this.openLoanLimitModal();
    //   }
    // })
    // this.loanLimitsSubscription.add(loanLimitSub)
  }

  openLoanLimitModal() {
    this.loanLimitSub.unsubscribe();
    this.isOpendLoanLimiModal = true;
    this.modalRef = this.mdbModalService.show(
      CustomerLimitsOutstandingDataComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: true,
        ignoreBackdropClick: true,
        class: "modal-dialog-scrollable",
        containerClass: "right",
        animated: true,
        data: {
          heading: "Existing and Outstanding Facilities",
          content: {
            finacleData: this.finacleData,
            facilityId: this.facility.facilityID,
            accountNumberOfFacility: this.accountNumberOfFacility,
            facilityDTOList: this.facilityDTOList,
          },
        },
      }
    );

    this.modalRef.content.action.subscribe((loandData: any) => {
      let data;

      if (!(loandData == null)) {
        this.selectedLoanData = loandData;
        this.accountNumberOfFacility = loandData.id;

        let outstandingAmount = loandData.outstandingAmount
          ? Math.abs(loandData.outstandingAmount)
          : 0;

        let grantedAmount = loandData.grantedAmount
          ? Math.abs(loandData.grantedAmount)
          : 0;

        data = {
          outstandingAmount: outstandingAmount,
          grantedAmount: grantedAmount,
          id: loandData.id,
          currencyCode: loandData.currencyType,
          isOpendLoanLimiModal: this.isOpendLoanLimiModal,
          finacaleData: this.finacleData,
          loanType: loandData.loanType ? loandData.loanType : "limit",
        };
      } else {
        this.accountNumberOfFacility = null;
        data = {
          id: this.accountNumberOfFacility,
          isOpendLoanLimiModal: this.isOpendLoanLimiModal,
          finacaleData: this.finacleData,
        };
      }
      this.updateExOutstandingDate.emit(data);
    });
  }

  updateSelectedBy(data, facilityDTOList) {
    const limitsMap = new Map<string, number>();
    facilityDTOList.forEach((facility) => {
      if (facility.loanLimitId)
        limitsMap.set(facility.loanLimitId, facility.facilityID);
    });

    if (data.limits.length === 0) {
      data.limits = [];
    } else {
      data.limits.forEach((loan) => {
        const facilityId = limitsMap.get(loan.id) || null;
        loan.selectedBy = facilityId;
      });
    }
    if (data.loans.length === 0) {
      data.loans = [];
    } else {
      data.loans.forEach((loan) => {
        const facilityId = limitsMap.get(loan.id) || null;
        loan.selectedBy = facilityId;
      });
    }

    return data;
  }
}
