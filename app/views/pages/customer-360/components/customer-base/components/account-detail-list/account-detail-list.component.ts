import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  ViewChild,
} from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Constants } from "../../../../../../../core/setting/constants";
import { Subscription } from "rxjs";
import { CustomerBaseService } from "../../../../services/customer-base.service";
import { AppUtils } from "../../../../../../../shared/app.utils";
import { SETTINGS } from "../../../../../../../core/setting/commons.settings";
import * as _ from "lodash";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { MasterDataService } from "../../../../../../../core/service/data/master-data.service";
import { NewFacilityPaperDialogComponent } from "../support/new-facility-paper-dialog/new-facility-paper-dialog.component";
import { CacheService } from "../../../../../../../core/service/data/cache.service";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-account-detail-list",
  templateUrl: "./account-detail-list.component.html",
  styleUrls: ["./account-detail-list.component.scss"],
})
export class AccountDetailListComponent
  implements OnInit, OnChanges, OnDestroy
{
  @Input("customer360Details") customer360Details: any;
  @ViewChild("basicModal", { static: false }) private dialog;
  statusConst = Constants.statusConst;
  status = Constants.status;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  selectedBankAccount: any = {};
  selectedBankAccountBranch: any = {};
  upmUser: any = {};

  onApplicationServiceSub = new Subscription();

  newPaperModalRef: MDBModalRef;
  currentUserBranchCode = "";
  resizedCustomerBankDetailsDTOList = [];
  bankDetailsPageSize = 8;

  constructor(
    private modalService: MDBModalService,
    private cacheService: CacheService,
    private customerBaseService: CustomerBaseService,
    private masterDataService: MasterDataService,
    private applicationService: ApplicationService,
    private mdbModalService: MDBModalService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.initiateBankAccountList();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (
      changes["customer360Details"] &&
      changes["customer360Details"].currentValue
    ) {
      this.initiateBankAccountList();
    }
  }

  ngOnDestroy(): void {
    this.onApplicationServiceSub.unsubscribe();
  }

  initiateBankAccountList() {
    if (
      this.customer360Details &&
      this.customer360Details.customerBankDetailsDTOList
    ) {
      this.resizedCustomerBankDetailsDTOList =
        this.customer360Details.customerBankDetailsDTOList.slice(
          0,
          this.bankDetailsPageSize
        );
    }
  }

  onLoadResizedList(listFromEvent) {
    this.resizedCustomerBankDetailsDTOList = listFromEvent.outputArray;
  }

  createNewFacility(bankDetails: any) {
    this.selectedBankAccount = bankDetails;
    this.newPaperModalRef = this.mdbModalService.show(
      NewFacilityPaperDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-70-p modal-dialog-scrollable",
        containerClass: "",
        animated: false,
        data: {
          customer360Details: this.customer360Details,
          selectedBankAccount: this.selectedBankAccount,
        },
      }
    );

    this.newPaperModalRef.content.action.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.draftFacilityPaper(data);
        this.newPaperModalRef.hide();
      } else {
        this.alertService.showToaster(
          "An error occurd. Please try agian later.",
          SETTINGS.TOASTER_MESSAGES.error,
          { timeOut: 2500 }
        );
      }
    });
  }

  draftFacilityPaper(data:any) {
    let submit: any = {};
    let { branchName, committee, workFlow } = data;
    let branch = AppUtils.getBranchFromBranchName(
      this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES),
      branchName
    );

    submit.branchCode = branch.branchCode;
    submit.createdUserBranchCode =
      this.applicationService.getLoggedInUserDivCode();
    submit.createdUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName();
    submit.bankAccountID = this.selectedBankAccount.bankAccountNumber;
    submit.isCommittee = committee ? "Y" : "N";
    submit.workflowTemplateID = workFlow;
    submit.casCustomerDTOList = [
      {
        customerID: this.customer360Details.customerID,
        isPrimary: true,
        status: "ACT",
      },
    ];
    submit.currentAssignUser =
      this.applicationService.getLoggedInUserUserName();
    submit.currentAssignUserID =
      this.applicationService.getLoggedInUserUserID();
    submit.currentAssignUserDivCode =
      this.applicationService.getLoggedInUserDivCode();
    submit.assignUserUpmGroupCode =
      this.applicationService.getLoggedInUserUPMGroupCode();
    submit.assignUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName();
    submit.assignUserUpmID = this.applicationService.getLoggedInUserUserID();
    this.customerBaseService.draftFacilityPaper(AppUtils.trim(submit));
  }
}
