import { CurrencyPipe } from "@angular/common";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { AppUtils } from "src/app/shared/app.utils";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-fp-group-exposure",
  templateUrl: "./fp-group-exposure.component.html",
  styleUrls: ["./fp-group-exposure.component.scss"],
})
export class FpGroupExposureComponent implements OnInit, OnDestroy {
  requestId: string = "smb_1121344";
  customerFinacleId: string = "";
  exposureDetails: any[] = [];
  errorMessage: string | null;
  loading: boolean = false;
  modalRef: MDBModalRef;

  selectedCustomers: any[] = [];
  facilityPaperId: number = 0;
  facilityPaper: any = {};

  onFpCustomerChangeSub = new Subscription();
  onFacilityPaperBaseDataChangeSub = new Subscription();

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    public mdbModalRef: MDBModalRef,
    public currencyPipe: CurrencyPipe
  ) {}

  ngOnInit() {
    this.onFpCustomerChangeSub =
      this.facilityPaperAddEditService.onFpCustomerChange.subscribe(
        (data: any) => {
          if (data) {
            this.facilityPaperId = data.facilityPaperID;
            this.customerFinacleId = this.getCustomerFinacleId(data);
            if (this.customerFinacleId) {
              this.getGroupExposures();
            }
          }
        }
      );

    this.onFacilityPaperBaseDataChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperBaseDataChange.subscribe(
        (data: any) => {
          if (data) {
            this.facilityPaper = data;
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onFpCustomerChangeSub.unsubscribe();
    this.onFacilityPaperBaseDataChangeSub.unsubscribe();
  }

  getCustomerFinacleId(data: any) {
    const primaryCustomer: any = data.casCustomerDTOList.find(
      (cus: any) => cus.primary
    );

    return primaryCustomer ? primaryCustomer.customerFinancialID : "";
  }

  getGroupExposures() {
    this.loading = true;
    const payload = {
      facilityID: this.facilityPaperId,
      customerID: this.customerFinacleId,
    };
    this.facilityPaperAddEditService
      .getGroupExposureDetails(payload)
      .then((response: any) => {
        this.exposureDetails = response ? response : [];
        this.loading = false;
      })
      .catch((error: any) => {
        this.loading = false;
      });
  }

  handleRefresh() {
    const payload = {
      requestId: this.requestId,
      cifiId: this.customerFinacleId,
      facilityID: this.facilityPaperId,
    };
    this.facilityPaperAddEditService
      .refreshGroupExposureDetails(payload)
      .then((response: any) => {
        this.exposureDetails = response ? response : [];
      })
      .catch((error: any) => {})
      .finally(() => {
        this.loading = false;
      });
  }

  getFormattedMnValue(value: any) {
    if (value) {
      return this.currencyPipe.transform(
        AppUtils.getMillionValue(value),
        "",
        "",
        "1.3-3"
      );
    }

    return "0.00";
  }

  getFormattedValue(value: any) {
    if (value) {
      return this.currencyPipe.transform(value, "", "");
    }

    return "0.00";
  }

  getToolTip(value: any) {
    return `Rs. ${this.getFormattedValue(value)}`;
  }

  isSubmitDesabled() {
    return this.exposureDetails.some((c: any) => c.isSelected == 1);
  }

  handleSubmit() {
    let payload: any = {
      exposures: this.exposureDetails.map((data: any) => ({
        ...data,
        isSelected: data.isSelected == 1 || data.isSelected == true ? 1 : 0,
      })),
      facilityPaperDTO: {
        facilityPaperID: this.facilityPaper.facilityPaperID,
      },
    };

    this.facilityPaperAddEditService
      .calculateGroupExposure(payload)
      .then((res: any) => {
        if (res) {
          this.mdbModalRef.hide();
        }
      });
  }
}
