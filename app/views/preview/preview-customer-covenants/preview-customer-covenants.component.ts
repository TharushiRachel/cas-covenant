import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subject, Subscription } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationCovenant } from "../../pages/customer-360/components/customer-base/dto/application-covenant";
import { FacilityPaperAddEditService } from "../../pages/facility-paper/services/facility-paper-add-edit.service";
import { Router } from "@angular/router";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { ApplicationFormAddEditService } from "../../pages/application-form/application-form-add-edit/services/application-form-add-edit.service";
import { CustomerCovenantListComponent } from "../../pages/customer-360/components/customer-base/components/customer-covenant/add-customer-covenant/customer-covenant-list.component";
import { EditCustomerCovenantComponent } from "../../pages/customer-360/components/customer-base/components/customer-covenant/edit-customer-covenant/edit-customer-covenant.component";
import { AccountCovenantComponent } from "../../pages/customer-360/components/customer-base/components/add-account-covenant/account-covenant.component";
import { EditAccountCovenantComponent } from "../../pages/customer-360/components/customer-base/components/add-account-covenant/edit-account-covenant/edit-account-covenant.component";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { AppUtils } from "src/app/shared/app.utils";
import * as _ from "lodash";

@Component({
  selector: "app-preview-customer-covenants",
  templateUrl: "./preview-customer-covenants.component.html",
  styleUrls: ["./preview-customer-covenants.component.scss"],
})
export class PreviewCustomerCovenantsComponent implements OnInit {
  @Input("facilityPaper") facilityPaper: any = {};
  modalRef: MDBModalRef;
  type: string;
  covenants: any[] = [];
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  covenantDetail: any = {};
  onCustomerCovenant = new Subscription();

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private router: Router,
    private applicationService: ApplicationService
  ) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit() {
    // this.onCustomerCovenant =
    //   this.facilityPaperAddEditService.onCustomerCovenant.subscribe(
    //     (data: any) => {
    //       if (data) {
    //         this.reloadPage();
    //       }
    //     }
    //   );

    this.reloadPage();
  }

  getCovenantFrequencyLabel(frequencyValue) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  reloadPage() {
    this.facilityPaperAddEditService
      .getCovenantListByFpRefNumber()
      .then((data: any) => {
        if (data) {
          this.covenants = data;
          console.log("data", data);
        } else if (this.covenantDetail) {
          this.covenants = this.covenantDetail;
          this.covenantDetail = null;
        }
      });
  }

  computeCovenantCounters() {
    return this.covenants.filter((covenant) => covenant.status === "Active");
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      return false;
    }
  }

  getDisbursementTypeClass(disbursementType: string): string {
    switch (disbursementType) {
      case "PRE":
        return "disbursement-pre";
      case "POST":
        return "disbursement-post";
      default:
        return "disbursement-default";
    }
  }

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }
}
