import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { Constants } from "../../../../../../../../../core/setting/constants";
import { FacilityPaperAddEditService } from "../../../../../../services/facility-paper-add-edit.service";
import {
  MDBModalRef,
  MDBModalService,
  TabsetComponent,
} from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { FpCustomerOtherBankFacilityComponent } from "./fp-customer-other-bank-facility/fp-customer-other-bank-facility.component";
import * as _ from "lodash";
import { UrlEncodeService } from "../../../../../../../../../core/service/application/url-encode.service";
import { CustomerUpdateDto } from "../../../../../../dto/customer-update-dto";
import { SETTINGS } from "../../../../../../../../../core/setting/commons.settings";
import { ConfirmationDialogComponent } from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "../../../../../../../../../core/service/application/application.service";
import { AppUtils } from "../../../../../../../../../shared/app.utils";
import * as moment from "moment";
import { NewNonFinacleCustomerAddEditComponent } from "../../../../../../../../../shared/components/new-non-finacle-customer-add-edit/new-non-finacle-customer-add-edit.component";
import { PrivilegeService } from "../../../../../../../../../core/service/authentication/privilege.service";

@Component({
  selector: "app-fp-personal-detail-tab-view",
  templateUrl: "./fp-personal-detail-tab-view.component.html",
  styleUrls: ["./fp-personal-detail-tab-view.component.scss"],
})
export class FpPersonalDetailTabViewComponent implements OnInit, OnDestroy {
  facilityPaper: any = {};
  selectedCustomer: any = {};
  @ViewChild("staticTabs", { static: true }) staticTabs: TabsetComponent;

  @Output("onKaliptoDataLoad") onKaliptoDataLoad = new EventEmitter();
  @Input("mainFacilityPaper") mainFacilityPaper: any = {};
  toDate = moment().subtract(1, "months").endOf("month").format("DD-MM-YYYY");
  fromDate = moment()
    .subtract(12, "months")
    .startOf("month")
    .format("DD-MM-YYYY");

  primaryCustomerID: number;
  modalRef: MDBModalRef;
  joinNonFinacleCustomerModalRef: MDBModalRef;
  selectedCustomerList = [];
  selectedCustomerMap: any = {};
  customerDetailsMap: any = {};
  facilityDetails = [];
  sortedFpList = [];
  casCustomerList = [];
  lis: any = [];
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  selectedPersonalDetailsTabIndex: any = 0;
  isAbleToAddEdit = false;

  civilStatus = Constants.civilStatus;
  customerIdentificationType = Constants.customerIdentificationType;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  addressType = Constants.addressType;
  status = Constants.status;
  statusConst = Constants.statusConst;

  loadMoreClicked: boolean = false;

  customerUpdateDto: CustomerUpdateDto = new CustomerUpdateDto({});

  tableColumns = [
    "Facility Name",
    "Account No",
    "Sanction Limit",
    "Clear Balance Amount",
    "Account Currency Code",
    "Sub Classification User",
    "Scheme Code",
    "Scheme Desc",
    "Clean Emer Advn",
    "Acct Poa As Ewc Type",
    "Customer Reltn Code",
  ];
  tableColumnsBankDetails = ["Account No", "Status", "Action"];

  onFacilityDetailChangeSub: Subscription = new Subscription();
  onCustomerListChangeSub: Subscription = new Subscription();
  onFacilityPaperLoadSub: Subscription = new Subscription();
  onCustomerChangeSub: Subscription = new Subscription();

  selectedTabIndex: any = 0;

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  customerFinancialID: any;
  facilityPaperID: any;
  bankAccount: any;
  guaranteeVolumeDetails: any;
  insuranceRQ: any;
  isAbleToUpdate = false;
  constructor(
    public facilityPaperAddEditService: FacilityPaperAddEditService,
    private mdbModalService: MDBModalService,
    private urlEncodeService: UrlEncodeService,
    private applicationService: ApplicationService,
    private changeDetectorRef: ChangeDetectorRef,
    private privilegeService: PrivilegeService,
  ) {}

  ngOnInit() {
    this.onCustomerListChangeSub =
      this.facilityPaperAddEditService.onCustomerListChange.subscribe(
        (customerList: any) => {
          this.selectedCustomerList = customerList;
          _.forEach(this.selectedCustomerList, (customer) => {
            this.selectedCustomerMap[customer.casCustomerID] = customer;

            let customerDetails: any = {};
            let customerBankDetailsDTOList =
              customer.casCustomerBankDetailsDTOList;

            customerBankDetailsDTOList.forEach((bankDetails) => {
              if (bankDetails.bankAccountNumber) {
                customerDetails.accno = bankDetails.bankAccountNumber;
              }
            });

            customerDetails.cumm = customer.customerFinancialID;
            this.customerFinancialID = customer.customerFinancialID
              ? customer.customerFinancialID
              : null;
            if (customerDetails.cumm) {
              customerDetails.valType = "C";
            } else {
              customerDetails.valType = "A";
            }

            customerDetails.userId =
              this.applicationService.getLoggedInUserUserID();
            customerDetails.aduser =
              this.applicationService.getLoggedInUserUserName();
            customerDetails.refId =
              this.applicationService.getLoggedInUserUserName();
            customerDetails.fromdate = this.fromDate;
            customerDetails.todate = this.toDate;
            customerDetails = AppUtils.trim(customerDetails);

            this.customerDetailsMap[customer.casCustomerID] = customerDetails;
          });
          this.sortedFpList = this.casCustomerList.sort((x, y) => {
            return x.isPrimary == y.isPrimary ? 0 : x.isPrimary ? -1 : 1;
          });
        },
      );

    this.onFacilityPaperLoadSub =
      this.facilityPaperAddEditService.onFpCustomerChange.subscribe(
        (data: any) => {
          this.facilityPaper = data;

          this.casCustomerList = _.filter(
            data.casCustomerDTOList,
            (customer) => customer.status == Constants.statusConst.ACT,
          );
          if (this.casCustomerList.length > 0) {
            for (var customer of this.casCustomerList) {
              if (customer.isPrimary == true) {
                this.primaryCustomerID = customer.casCustomerID;
              }
            }
            this.setSelectedTab(0);
            this.facilityPaperAddEditService.onCustomerListChange.next(
              this.casCustomerList,
            );
          }
        },
      );

    this.onFacilityDetailChangeSub =
      this.facilityPaperAddEditService.onFacilityDetailChange.subscribe(
        (data: any) => {
          this.facilityDetails =
            this.facilityPaperAddEditService.CustomerFacilityDetailList;
          this.onLoadMoreDetail();
          this.changeDetectorRef.detectChanges();
        },
      );

    this.isAbleToAddEdit =
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT,
      ) &&
      this.isEqualLoginAndAssignUser() &&
      !this.isApproveStatus() &&
      !this.isRejected();

    this.facilityPaperID = this.facilityPaper.facilityPaperID;
    this.bankAccount = this.facilityPaper.bankAccountID;
    this.guaranteeVolumeDetails = {
      facilityPaperID: this.facilityPaperID,
      customerFinacleId: this.customerFinancialID,
    };
    this.insuranceRQ = this.guaranteeVolumeDetails;

    this.isAbleToUpdate =
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT,
      ) &&
      this.isEqualLoginAndAssignUser() &&
      !this.isApproveStatus() &&
      !this.isRejected();
  }

  loadKalyptoData(customerData) {
    this.onKaliptoDataLoad.emit(customerData);
  }

  ngOnDestroy(): void {
    this.onFacilityDetailChangeSub.unsubscribe();
    this.onCustomerListChangeSub.unsubscribe();
    this.onFacilityPaperLoadSub.unsubscribe();
    this.onCustomerChangeSub.unsubscribe();
  }

  openModalOtherBankFacility(
    facilityPaper?,
    casCustomerID?,
    otherFacilityItem?,
  ) {
    const initialState = {
      list: [{ tag: "Count", value: facilityPaper }],
    };

    this.modalRef = this.mdbModalService.show(
      FpCustomerOtherBankFacilityComponent,
      {
        initialState,
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-70-p modal-margin-center audit-modal-margin-center",
        containerClass: "",
        animated: true,
        data: {
          heading: "comming dto",
          content: {
            casCustomerID: casCustomerID,
            facilityPaper: facilityPaper,
            otherFacilityItem: otherFacilityItem,
          },
        },
      },
    );
  }

  onLoadMoreDetail() {
    this.loadMoreClicked = true;
  }

  onHideDetail() {
    this.loadMoreClicked = false;
  }

  getTabTitle(tab) {
    let title = "";
    if (this.selectedCustomerMap[tab.casCustomerID]) {
      let customer = this.selectedCustomerMap[tab.casCustomerID];
      if (customer.customerFinancialID) {
        title = customer.customerName + " : " + customer.customerFinancialID;
      } else {
        title = customer.casCustomerName
          ? customer.casCustomerName
          : "" + " : # ";
      }
    }
    return title;
  }

  onPersonalDetailsTabSelect(event) {
    this.selectedPersonalDetailsTabIndex = event;
  }

  isSelectedTab(index) {
    return this.selectedPersonalDetailsTabIndex == index;
  }

  setSelectedTab(index) {
    this.selectedPersonalDetailsTabIndex = index;
  }

  updateNonFinacleCustomer($event, casCustomerData) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }

    this.joinNonFinacleCustomerModalRef = this.mdbModalService.show(
      NewNonFinacleCustomerAddEditComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-85-p modal-dialog-scrollable",
        containerClass: "",
        animated: true,
        data: {
          heading: "Update Customer Details",
          actionMessage: "Update Customer",
          casCustomerData: casCustomerData,
        },
      },
    );

    this.joinNonFinacleCustomerModalRef.content.action.subscribe(
      (nonFinacleCustomerData: any) => {
        if (!_.isEmpty(nonFinacleCustomerData)) {
          let casCustomerDTO = {};
          switch (nonFinacleCustomerData.type) {
            case this.basicInformationTypeConst.PERSONAL:
              casCustomerDTO = {
                ...nonFinacleCustomerData.personalInformationForm,
              };
              break;
            case this.basicInformationTypeConst.CORPORATE:
              casCustomerDTO = {
                ...nonFinacleCustomerData.corporateInformationForm,
              };
              break;
            case this.basicInformationTypeConst.BUSINESS:
              casCustomerDTO = {
                ...nonFinacleCustomerData.businessInformationForm,
              };
              break;
          }
          casCustomerDTO = Object.assign(
            casCustomerDTO,
            nonFinacleCustomerData,
          );

          let updateRQ = Object.assign(
            {},
            {
              facilityPaperID: this.facilityPaper.facilityPaperID,
              casCustomerID: casCustomerData.casCustomerID,
              status: Constants.statusConst.ACT,
              isPrimary: casCustomerData.isPrimary
                ? Constants.yesNoConst.Y
                : Constants.yesNoConst.N,
              displayOrder: casCustomerData.displayOrder,
            },
            { casCustomerDTO: casCustomerDTO },
          );
          this.facilityPaperAddEditService.addEditNonFinacleCasCustomer(
            AppUtils.trim(updateRQ),
          );
        }
        this.joinNonFinacleCustomerModalRef.hide();
      },
    );
  }

  onRemovePartner(selectedPartner) {
    const customerDTOList = _.cloneDeep(this.facilityPaper.casCustomerDTOList);

    _.map(customerDTOList, (partner) => {
      if (partner.casCustomerID == selectedPartner.casCustomerID) {
        partner.status = Constants.statusConst.INA;
      }
      return partner;
    });

    let fpCustomerUpdateDTO = Object.assign(
      {},
      {
        facilityPaperID: this.facilityPaper.facilityPaperID,
        casCustomerDTOList: customerDTOList,
      },
    );

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Confirm Remove Partner",
        message: "Do you want to remove partner ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.facilityPaperAddEditService.removeFPJoningParties(
          fpCustomerUpdateDTO,
        );
      }
    });
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

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isRejected() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED
    );
  }

  isNonFinacleCustomer(customer) {
    return !customer.customerFinancialID;
  }

  isNonFinaclePrimaryCustomer(customer) {
    return !customer.customerFinancialID && customer.isPrimary;
  }

  updateCasCustomerData(data) {
    let updateRQ = {
      ...data,
      facilityPaperID: this.facilityPaper.facilityPaperID,
    };

    this.facilityPaperAddEditService.updateCasCustomerDTO(
      AppUtils.trim(updateRQ),
    );
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  // getCustomerDetails(customer){

  //   if(customer.customerFinancialID){
  //     this.insuranceRQ.customerFinacleId = customer.customerFinancialID
  //   }
  //   else{
  //     this.insuranceRQ.customerFinacleId = null
  //   }
  //   return this.insuranceRQ
  // }

  getCustomerDetails(customer) {
    let bankAccountNumbers = customer.casCustomerBankDetailsDTOList
      .filter(
        (account) =>
          account.bankAccountNumber != null ||
          account.bankAccountNumber != undefined,
      )
      .map((account) => account.bankAccountNumber);

    if (customer.customerFinancialID) {
      this.insuranceRQ.customerFinacleId = customer.customerFinancialID;
    } else {
      this.insuranceRQ.customerFinacleId = null;
    }

    this.insuranceRQ.bankAccountNumber =
      bankAccountNumbers.length > 0 ? bankAccountNumbers[0] : null;

    this.insuranceRQ.defaultAccount = this.facilityPaper
      ? this.facilityPaper.casCustomerDTOList &&
        this.facilityPaper.casCustomerDTOList.length == 1
        ? this.facilityPaper.bankAccountID
        : null
      : null;
    return this.insuranceRQ;
  }

  isPersonalBurrower(customer: any) {
    return customer.type !== Constants.basicInformationTypeConst.BUSINESS;
  }
}
