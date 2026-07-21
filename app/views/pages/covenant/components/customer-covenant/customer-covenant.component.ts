import { Component, OnInit, OnDestroy, Input } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CustomerCovenantListComponent } from "./add-customer-covenant/customer-covenant-list.component";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { CovenantService } from "src/app/views/pages/covenant/services/covenant.service";
import { Constants } from "src/app/core/setting/constants";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { AccountCovenantComponent } from "../add-account-covenant/account-covenant.component";
import { Router } from "@angular/router";
import { Subject, Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { ApplicationCovenant } from "../../dto/application-covenant";
import { AppUtils } from "src/app/shared/app.utils";
import * as _ from "lodash";
import { CurrencyPipe } from "@angular/common";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { SessionStorage } from "ngx-webstorage";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { ViewCovenantAccountDetailsComponent } from "./view-covenant-account-details/view-covenant-account-details.component";
import { AddCovenantCommentComponent } from "./add-covenant-comment/add-covenant-comment.component";
import { FacilitySelectModalComponent } from "../facility-select-modal/facility-select-modal.component";

@Component({
  selector: "app-customer-covenant",
  templateUrl: "./customer-covenant.component.html",
  styleUrls: ["./customer-covenant.component.scss"],
})
export class CustomerCovenantComponent implements OnInit, OnDestroy {
  private toSentenceCase(value: any): any {
    if (typeof value !== "string") {
      return value;
    }

    const lower = value.toLowerCase();
    return lower.charAt(0).toUpperCase() + lower.slice(1);
  }

  getGroupByAcctId(acctId: string) {
    return this.groupedCovTypACompStY.find((cov) => cov.acctId === acctId);
  }
  selectedFacilities: { [acctId: string]: any } = {};
  existingFacilityCovenants: { [acctId: string]: any } = {};
  facilityDisplayOrderByAcctId: { [acctId: string]: number } = {};
  existingCovenantRecordIdByAcctId: { [acctId: string]: any } = {};
  @Input("facilityPaper") facilityPaper: any = {};
  @Input("covenantList") covenantList: any = [];
  @Input("facilityCovenantDTOList") facilityCovenantDTOList: any = [];
  @Input("customerCovenantList") customerCovenantList: any = [];
  @Input("facilityCovenantList") facilityCovenantList: any = [];
  @Input() isAbleToAddEdit = false;

  modalRef: MDBModalRef;
  accessLevelOfCurrentAssignUser: any;
  isLegalOfficer: boolean = false;
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  action: Subject<any> = new Subject<any>();
  content: any;

  isPreviewMode: boolean = true;
  facilityStatusConst = Constants.facilityPaperStatusConst;

  covenantDetail: any = {};
  covenantDetailUpdate: any = {};
  applicationCovenantDetail: any = {};
  customerCovenantId: number;
  applicationCovenantId: number;
  facilityCovenant: ApplicationCovenant = new ApplicationCovenant();
  isLoad: boolean = true;
  activeCovenantCounter: number;
  deactivatedCovenantCounter: number;
  activeCustomerCovenantCounter: number;
  deactivatedCustomerCovenantCounter: number;
  riskDivCode: any;
  createdUserDisplayName: any;
  covenantVal: any[] = [];
  deactivateCovenantVal: any[] = [];
  approvedCovValues: any[] = [];

  covTypCCompStN: any[] = [];
  covTypCCompStY: any[] = [];
  covTypACompStN: any[] = [];
  covTypACompStY: any[] = [];
  covenantComplianceTypes = Constants.covenantComplianceTypes;

  @SessionStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;

  groupedCovTypACompStN: any[] = [];
  groupedCovTypACompStY: any[] = [];
  groupedApprovedFacility: any[] = [];
  approvedFacilityCovComplied: any[] = [];
  approvedFacilityCovNoneComplied: any[] = [];
  approvedCustomerCovComplied: any[] = [];
  approvedCustomerCovNoneComplied: any[] = [];
  covenantComments: any[] = [];
  mappedCovenants: any[] = [];
  sortedExistingFacilityCovenants: any[] = [];
  specialComment: any;
  private subscriptions: Subscription = new Subscription();

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private covenantService: CovenantService,
    private router: Router,
    private applicationService: ApplicationService,
    private currencyPipe: CurrencyPipe,
    private urlEncodeService: UrlEncodeService
  ) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit() {
    this.isLegalOfficer =
      this.applicationService.getLoggedInUserUPMGroupCode() &&
      this.applicationService.getLoggedInUserUPMGroupCode() ==
        Constants.committeeSignatureUsers.LO;

    this.isAbleToDeleteCovenant();

    this.facilityPaperAddEditService.getRiskDivCode().then((data) => {
      this.riskDivCode = data;

      if (
        this.riskDivCode == this.applicationService.getLoggedInUserDivCode()
      ) {
        this.isLoad = false;
      }
    });

    this.getCovenantDetailsFromFinacle();

    this.getApprovedFacilityCovenantList();

    this.subscriptions.add(
      this.facilityPaperAddEditService.onFacilityCovenantTabChange.subscribe(
        () => {
          this.getFacilityCovenantList();
          this.getDeactiaveFacilityCovenantList();
        }
      )
    );

    this.subscriptions.add(
      this.covenantService.onFacilityCovenantTabChange.subscribe(() => {
        this.getFacilityCovenantList();
        this.getDeactiaveFacilityCovenantList();
      })
    );

    this.covenantService
      .getAllFacilityCovenantLegacy(this.facilityPaper.facilityPaperID)
      .then((data) => {
        this.groupedApprovedFacility =
          this.getGroupedSortedApprovedCovenants(data);
      });

    this.getGroupedSortedApprovedCovenantsNew();
    this.computeApprovedCovenantCounters();

    this.subscriptions.add(
      this.covenantService
        .getCovenantCommentList(this.facilityPaper.facilityPaperID)
        .subscribe((comments: any[]) => {
          this.covenantComments = comments;
        })
    );

    this.checkMatch();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  computeCovenantCounters() {
    return (this.activeCustomerCovenantCounter = this.covenantList.filter(
      (covenant) =>
        covenant.status === "Active" &&
        (covenant.isExists === "N" || covenant.isExists === null),
    ));
  }

  computeCovenantCountersINA() {
    return this.covenantList.filter(
      (covenant) =>
        covenant.status === "Inactive" &&
        (covenant.isExists === "N" || covenant.isExists === null),
    );
  }

  isAssignedToRisk() {
    if (
      this.facilityPaperAddEditService.getRiskDivCode() ==
      this.applicationService.getLoggedInUserDivCode()
    ) {
      this.isLoad = false;
    }
  }

  showAddDeleteButton(facilityPaper) {
    if (
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.IN_PROGRESS ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.DRAFT ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.CANCEL
    ) {
      return true;
    } else {
      return false;
    }
  }

  openDialog(response?) {
    this.modalRef = this.mdbModalService.show(CustomerCovenantListComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class:
        "modal-width-85-p modal-dialog-scrollable  modal-lg modal-height-covenant",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Customer Covenant Details",
        customerCovenantList: this.customerCovenantList,
        covenantList: this.covenantList,
        content: {
          response: response,
        },
      },
    });

    this.modalRef.content.action.subscribe((response: any) => {
      this.covenantDetail = response;
      this.ngOnInit();
    });
  }

  openDialogForUpdateCustomerCovenant(customerCovenant: any, response?) {
    if (!this.customerCovenantList) {
      console.error("customerCovenantList is not available yet!");
      return;
    }
    this.customerCovenantId = customerCovenant.customerCovenantId;
    sessionStorage.setItem(
      "customerCovenantId",
      `${customerCovenant.customerCovenantId}`,
    );

    this.modalRef = this.mdbModalService.show(CustomerCovenantListComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class:
        "modal-width-85-p modal-dialog-scrollable  modal-lg modal-height-covenant-edit",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Covenat Details",
        isEditMode: true,
        customerCovenantId: customerCovenant.customerCovenantId,
        customerCovenantList: this.customerCovenantList,
        content: {
          customerCovenantList: this.customerCovenantList,
          customerCovenant: customerCovenant,
          customerCovenantId: customerCovenant.customerCovenantId,
          response: response,
        },
      },
    });

    this.modalRef.content.action.subscribe((response: any) => {
      this.covenantDetailUpdate = response;
    });
  }

  OpenDialogForAccountCovenant() {
    this.modalRef = this.mdbModalService.show(AccountCovenantComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class:
        "modal-width-85-p modal-dialog-scrollable  modal-lg modal-height-covenant",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Covenat Details",
        facilityCovenantList: this.facilityCovenantList,
        facilityPaper: this.facilityPaper,
      },
    });

    this.modalRef.content.action.subscribe((response: any) => {
      this.applicationCovenantDetail = response;
    });
  }

  openDialogForUpdateAccountCovenant(facilityCovenant: any, response?) {
    this.applicationCovenantId = facilityCovenant.applicationCovenantId;
    sessionStorage.setItem(
      "applicationCovenantId",
      `${facilityCovenant.applicationCovenantId}`,
    );

    this.modalRef = this.mdbModalService.show(AccountCovenantComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class:
        "modal-width-85-p modal-dialog-scrollable  modal-lg modal-height-covenant-edit",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Covenat Details",
        isEditMode: true,
        applicationCovenantId: facilityCovenant.applicationCovenantId,
        facilityCovenantList: this.facilityCovenantList,
        facilityPaper: this.facilityPaper,
        content: {
          facilityCovenantList: this.facilityCovenantList,
          facilityCovenant: facilityCovenant,
          applicationCovenantId: facilityCovenant.applicationCovenantId,
          applicationCovenantDetail: response,
          response: response,
        },
      },
    });

    this.modalRef.content.action.subscribe((response: any) => {
      this.covenantDetail = response;
      this.getFacilityCovenantList();
    });
  }

  getCovenantFrequencyLabel(frequencyValue) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue,
    );
    return frequency ? frequency.label : "Unknown";
  }

  removeData(customerCovenantId: number) {
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
        heading: "Confirm Remove Covenant",
        message: "Do you want to deactivate this Covenant ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        const createdUserDisplayName =
          this.applicationService.getLoggedInUserDisplayName();
        this.facilityPaperAddEditService.deleteCovenant(
          customerCovenantId,
          createdUserDisplayName,
        );
        this.getFacilityCovenantList();
      }
    });
  }

  removeApplicationCovenants(applicationCovenantId: number) {
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
        heading: "Confirm Remove Covenant",
        message: "Do you want to deactivate this Covenant ?",
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        const createdUserDisplayName =
          this.applicationService.getLoggedInUserDisplayName();
        this.facilityPaperAddEditService.deleteApplicationCovenants(
          applicationCovenantId,
          createdUserDisplayName,
        );
        this.getFacilityCovenantList();
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

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  isAbleToDeleteCovenant() {
    let ob1 = this.facilityPaper.currentAssignADUserID;

    this.subscriptions.add(
      this.applicationService
        .getUpmDetailsByAdUserIdAndAppCode(ob1)
        .subscribe((response: any) => {
          this.accessLevelOfCurrentAssignUser = response.applicationSecurityClass;
        })
    );
  }

  getFacilityCovenantList() {
    this.covenantService
      .getAllFacilityCovenantLegacy(this.facilityPaper.facilityPaperID)
      .then((data) => {
        this.covenantVal = data
          .map((result) => {
            const facilities =
              result.covValue && Array.isArray(result.covValue)
                ? result.covValue
                : [];
            const activeCovValues = facilities
              .filter(
                (covValue) =>
                  covValue.status === "Active" &&
                  (covValue.isExists === "N" || covValue.isExists === null),
              )
              .map((covValue) => {
                const facilityDtos =
                  covValue.applicationCovenantFacilityDTOS || [];
                return Object.assign({}, covValue, {
                  applicationCovenantFacilityDTOS:
                    facilityDtos.length > 0
                      ? facilityDtos.sort(
                          (a, b) => a.displayOrder - b.displayOrder,
                        )
                      : [],
                });
              });

            if (activeCovValues.length > 0) {
              return Object.assign({}, result, {
                covValue: activeCovValues.sort((a, b) => {
                  const aOrder =
                    a.applicationCovenantFacilityDTOS.length > 0
                      ? a.applicationCovenantFacilityDTOS[0].displayOrder
                      : Number.MAX_VALUE;
                  const bOrder =
                    b.applicationCovenantFacilityDTOS.length > 0
                      ? b.applicationCovenantFacilityDTOS[0].displayOrder
                      : Number.MAX_VALUE;
                  return aOrder - bOrder;
                }),
              });
            } else {
              return null;
            }
          })
          .filter((result) => result !== null)
          .sort((a, b) => {
            const aOrder =
              a.covValue[0].applicationCovenantFacilityDTOS.length > 0
                ? a.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder
                : Number.MAX_VALUE;
            const bOrder =
              b.covValue[0].applicationCovenantFacilityDTOS.length > 0
                ? b.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder
                : Number.MAX_VALUE;
            return aOrder - bOrder;
          });
      });
  }

  computeFacilityCovenantCounters() {
    return this.covenantVal.filter(
      (covenant) => covenant.covValue.status === "Active",
    );
  }

  computeFacilityCovenantCountersINA() {
    return this.covenantVal.filter(
      (covenant) => covenant.covValue.status === "Inactive",
    );
  }

  getDeactiaveFacilityCovenantList() {
    this.covenantService
      .getAllFacilityCovenantLegacy(this.facilityPaper.facilityPaperID)
      .then((data) => {
        this.deactivateCovenantVal = data
          .map((result) => {
            const facilities =
              result.covValue && Array.isArray(result.covValue)
                ? result.covValue
                : [];
            return Object.assign({}, result, {
              covValue: facilities.filter(
                (covValue) => covValue.status === "Inactive",
              ),
            });
          })
          .filter((result) => result.covValue.length > 0);
      });
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

  getCovenantDetailsFromFinacle() {
    let custId = this.urlEncodeService.decode(this.selectedCIFID);
    let facilityPaperId = this.facilityPaper.facilityPaperID;

    this.covenantService
      .getCovenantDetailsFromFinacle(custId, facilityPaperId)
      .then((response) => {
        if (response && Array.isArray(response.covenant)) {
          let allCovenants: any[] = [];

          response.covenant.forEach((covenantItem: any) => {
            if (
              covenantItem.covenantInq &&
              Array.isArray(covenantItem.covenantInq)
            ) {
              allCovenants.push(
                ...covenantItem.covenantInq.map((item: any) => ({
                  ...item,
                  covRem: this.toSentenceCase(item.covRem),
                })),
              );
            }
          });

          this.covTypCCompStN = allCovenants.filter(
            (item) => item.covTyp === "C" && item.compSt === "N",
          );
          this.covTypCCompStY = allCovenants.filter(
            (item) => item.covTyp === "C" && item.compSt === "Y",
          );
          this.covTypACompStN = allCovenants.filter(
            (item) => item.covTyp === "A" && item.compSt === "N",
          );
          this.covTypACompStY = allCovenants.filter(
            (item) => item.covTyp === "A" && item.compSt === "Y",
          );

          this.groupCovTypACompStNByAcctId();
          this.groupCovTypACompStYByAcctId();          
        }

        this.specialComment = response.specialComment;
      })
      .catch((error) => {
        console.error("Error fetching covenant details:", error);
      });
  }

  computeApprovedCovenantCounters() {
    this.approvedCustomerCovComplied = this.covenantList.filter(
      (covenant) =>
        covenant.status === "Active" &&
        covenant.isExists === "Y" &&
        covenant.complianceStatus === "Y",
    );

    this.approvedCustomerCovNoneComplied = this.covenantList.filter(
      (covenant) =>
        covenant.status === "Active" &&
        covenant.isExists === "Y" &&
        covenant.complianceStatus === "N",
    );
  }

  getApprovedFacilityCovenantList() {
    this.covenantService
      .getAllFacilityCovenantLegacy(this.facilityPaper.facilityPaperID)
      .then((data) => {
      if (!data || !Array.isArray(data)) {
        console.warn("No valid 'covenant' array found in response:", data);
        this.approvedCovValues = [];
        return;
      }

      this.approvedCovValues = data
        .map((result) => {
          if (!result.covValue || !Array.isArray(result.covValue)) {
            return null;
          }

          const activeCovValues = result.covValue
            .filter(
              (covValue) =>
                covValue.status === "Active" && covValue.isExists === "Y",
            )
            .map((covValue) => ({
              ...covValue,
              applicationCovenantFacilityDTOS:
                covValue.applicationCovenantFacilityDTOS &&
                covValue.applicationCovenantFacilityDTOS.length > 0
                  ? covValue.applicationCovenantFacilityDTOS
                      .slice()
                      .sort((a, b) => a.displayOrder - b.displayOrder)
                  : [],
            }))
            .sort((a, b) => {
              const statusOrder = (status: string) => (status === "Y" ? 0 : 1);
              const compStatusDiff =
                statusOrder(a.complianceStatus) -
                statusOrder(b.complianceStatus);
              if (compStatusDiff !== 0) return compStatusDiff;

              const aOrder =
                a.applicationCovenantFacilityDTOS.length > 0
                  ? a.applicationCovenantFacilityDTOS[0].displayOrder
                  : Number.MAX_VALUE;
              const bOrder =
                b.applicationCovenantFacilityDTOS.length > 0
                  ? b.applicationCovenantFacilityDTOS[0].displayOrder
                  : Number.MAX_VALUE;
              return aOrder - bOrder;
            });

          if (activeCovValues.length > 0) {
            return {
              ...result,
              covValue: activeCovValues,
            };
          } else {
            return null;
          }
        })
        .filter((result) => result !== null)
        .sort((a, b) => {
          const aOrder =
            a.covValue[0].applicationCovenantFacilityDTOS.length > 0
              ? a.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder
              : Number.MAX_VALUE;
          const bOrder =
            b.covValue[0].applicationCovenantFacilityDTOS.length > 0
              ? b.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder
              : Number.MAX_VALUE;
          return aOrder - bOrder;
        });
    });
  }

  getCovenantComplianceLabel(complianceStatus) {
    const compStatus = this.covenantComplianceTypes.find(
      (item) => item.value === complianceStatus,
    );
    return compStatus ? compStatus.label : "Unknown";
  }

  openBankDetails(acctId: string): void {
    const modalRef = this.mdbModalService.show(
      ViewCovenantAccountDetailsComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center",
        containerClass: "",
        animated: true,
        data: {
          content: {
            acctId: acctId,
          },
        },
      },
    );
    //modalRef.componentInstance.acctId = acctId;
  }

  // groupCovTypACompStNByAcctId() {
  //   const map = new Map<string, any>();

  //   this.covTypACompStN.forEach((item) => {
  //     const acctId = item.acctId;
  //     if (!map.has(acctId)) {
  //       map.set(acctId, {
  //         acctId,
  //         items: []
  //       });
  //     }
  //     map.get(acctId).items.push({
  //       covRem: item.covRem,
  //       covFrq: item.covFrq,
  //       covDue: item.covDue
  //     });
  //   });

  //   this.groupedCovTypACompStN = Array.from(map.values());
  // }
  groupCovTypACompStNByAcctId() {
    const map = new Map<string, any>();

    this.covTypACompStN.forEach((item) => {
      const acctId = item.acctId;

      const selectedFacility = this.existingFacilityCovenants[acctId];

      const displayOrder =
        selectedFacility && selectedFacility.displayOrder != null
          ? selectedFacility.displayOrder
          : 9999;

      if (!map.has(acctId)) {
        map.set(acctId, {
          acctId,
          facility: selectedFacility
            ? `Facility ${selectedFacility.displayOrder}`
            : null,
          displayOrder,
          items: [],
        });
      }

      map.get(acctId).items.push({
        covRem: item.covRem,
        covFrq: item.covFrq,
        covDue: item.covDue,
        srlNum: item.srlNum,
        nonComplianceCovenantDTO: item.nonComplianceCovenantDTO,
      });
    });

    this.groupedCovTypACompStN = Array.from(map.values()).sort(
      (a, b) => a.displayOrder - b.displayOrder,
    ); // IMPORTANT FOR ANGULAR 8 CHANGE DETECTION

    this.groupedCovTypACompStN = [...this.groupedCovTypACompStN];
  }

  groupCovTypACompStYByAcctId() {
    const map = new Map<string, any>();

    this.covTypACompStY.forEach((item) => {
      const acctId = item.acctId;

      const selectedFacility = this.existingFacilityCovenants[acctId];

      const displayOrder =
        selectedFacility && selectedFacility.displayOrder != null
          ? selectedFacility.displayOrder
          : 9999;

      if (!map.has(acctId)) {
        map.set(acctId, {
          acctId,
          facility: selectedFacility
            ? `Facility ${selectedFacility.displayOrder}`
            : null,
          displayOrder,
          items: [],
        });
      }

      map.get(acctId).items.push({
        covRem: item.covRem,
        covFrq: item.covFrq,
        covDue: item.covDue,
      });
    });

    this.groupedCovTypACompStY = Array.from(map.values()).sort(
      (a, b) => a.displayOrder - b.displayOrder,
    ); // IMPORTANT FOR ANGULAR 8 CHANGE DETECTION

    this.groupedCovTypACompStY = [...this.groupedCovTypACompStY];
  }

  getGroupedSortedApprovedCovenants(data: any[]): any[] {
    const allCovValues = data
      .reduce((acc, group) => acc.concat(group.covValue), [])
      .filter((cov) => cov.isExists === "Y");

    allCovValues.sort((a, b) => {
      const statusOrder = (status: string | null) => {
        if (status === "Y") return 0;
        if (status === "N") return 1;
        return 2; // fallback for null/undefined
      };
      return statusOrder(a.complianceStatus) - statusOrder(b.complianceStatus);
    });

    // Step 3: Group by accountId
    const groupedByAccount: { [accountId: string]: any[] } = {};
    allCovValues.forEach((cov) => {
      const key = cov.accountId || "UNKNOWN";
      if (!groupedByAccount[key]) {
        groupedByAccount[key] = [];
      }
      groupedByAccount[key].push(cov);
    });

    // Step 4: Convert grouped data into an array
    const result = Object.entries(groupedByAccount).map(
      ([accountId, items]) => ({
        acctId: accountId,
        items: items.map((item) => ({
          covRem: item.covenant_Description,
          covFrq: item.covenant_Frequency,
          covDue: item.covenant_Due_Date,
          disbursementType: item.disbursementType,
          complianceStatus: item.complianceStatus,
        })),
      }),
    );

    return result;
  }

  getGroupedSortedApprovedCovenantsNew() {
    this.covenantService
      .getAllFacilityCovenantLegacy(this.facilityPaper.facilityPaperID)
      .then((data) => {
        const allCovenants: any[] = [];

        if (data) {
          data.forEach((result: any) => {
            if (result.covValue && Array.isArray(result.covValue)) {
              result.covValue.forEach((covenantItem: any) => {
                if (
                  covenantItem.status === "Active" &&
                  covenantItem.isExists === "Y"
                ) {
                  allCovenants.push(covenantItem);
                }
              });
            }
          });
        } else {
          console.warn("No valid 'covenant' array found in response:", data);
        }

        const groupByAccountId = (items: any[]) => {
          const map = new Map<string, any[]>();
          for (const item of items) {
            const accountId = item.accountId;
            if (!map.has(accountId)) {
              map.set(accountId, []);
            }
            const accountItems = map.get(accountId);
            if (accountItems) {
              accountItems.push(item);
            }
          }

          return Array.from(map.entries())
            .map(([accountId, covenants]) => {
              if (this.existingCovenantRecordIdByAcctId[accountId] == null) {
                const recordRow = covenants.find((c: any) => c.id != null);
                this.existingCovenantRecordIdByAcctId[accountId] =
                  recordRow && recordRow.id != null ? recordRow.id : null;
              }

              const dtoOrders = covenants
                .reduce(
                  (acc: any[], c: any) =>
                    acc.concat(c.applicationCovenantFacilityDTOS || []),
                  [],
                )
                .map((f: any) => f.displayOrder)
                .filter((o: any) => o != null);

              const displayOrder =
                dtoOrders.length > 0
                  ? Math.min(...dtoOrders)
                  : this.facilityDisplayOrderByAcctId[accountId] != null
                    ? this.facilityDisplayOrderByAcctId[accountId]
                    : 9999;

              return {
                accountId,
                displayOrder,
                covenants: covenants.sort((a, b) => {
                  const dateA = new Date(a.covenant_Due_Date || 0).getTime();
                  const dateB = new Date(b.covenant_Due_Date || 0).getTime();
                  return dateA - dateB;
                }),
              };
            })
            .sort((a, b) => a.displayOrder - b.displayOrder)
            .map(({ accountId, covenants }) => ({ accountId, covenants }));
        };

        const complied = allCovenants.filter(
          (item) => item.complianceStatus === "Y",
        );
        const nonComplied = allCovenants.filter(
          (item) => item.complianceStatus === "N",
        );

        this.approvedFacilityCovComplied = groupByAccountId(complied);
        this.approvedFacilityCovNoneComplied = groupByAccountId(nonComplied);

        // this.approvedFacilityCovComplied = allCovenants.filter(
        //   (item) => item.complianceStatus === "Y"
        // );

        // this.approvedFacilityCovNoneComplied = allCovenants.filter(
        //   (item) => item.complianceStatus === "N"
        // );
      })
      .catch((error) => {
        console.error("Error fetching covenant details:", error);
      });
  }

  addComment(srlNum: number) {
    this.modalRef = this.mdbModalService.show(AddCovenantCommentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-dialog-centered modal-lg",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add Covenant Comment",
        covenantSerialNumber: srlNum,
      },
    });

    this.modalRef.content.action.subscribe(() => {
      this.getCovenantDetailsFromFinacle();
    });
  }

  editComment(event: Event, nonComplianceCovenantDTO: any) {
    event.stopPropagation();

    if (!nonComplianceCovenantDTO) {
      console.warn("No nonComplianceCovenantDTO found");
      return;
    }

    this.modalRef = this.mdbModalService.show(AddCovenantCommentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-dialog-centered modal-lg",
      containerClass: "",
      animated: true,
      data: {
        heading: "Edit Covenant Comment",
        covenantSerialNumber: nonComplianceCovenantDTO.serialNumber,
        comment: nonComplianceCovenantDTO.comment,
        nonComplianceCovenantId:
          nonComplianceCovenantDTO.nonComplianceCovenantId,
        facilityPaperId: nonComplianceCovenantDTO.facilityPaperId,
        addedBy: nonComplianceCovenantDTO.addedBy,
        addedUserDisplayName: nonComplianceCovenantDTO.addedUserDisplayName,
        addedDate: nonComplianceCovenantDTO.addedDate,
      },
    });

    if (
      this.modalRef &&
      this.modalRef.content &&
      this.modalRef.content.action
    ) {
      this.modalRef.content.action.subscribe(() => {
        this.getCovenantDetailsFromFinacle();
      });
    }
  }

  isEnabelEdit(nonComplianceCovenantDTO: any): boolean {
    if (!nonComplianceCovenantDTO) {
      return false;
    }
    const currentUserId = this.applicationService.getLoggedInUserUserID();
    return (
      this.facilityPaper.currentAssignUserID === currentUserId &&
      currentUserId === nonComplianceCovenantDTO.addedUserId
    );
  }

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }

  // Facility selection modal logic
  openFacilitySelectPopup(accountId: string, existingRecordId?: any) {
    this.facilityPaperAddEditService.getFacilityList().then((data: any) => {
      const facilities = (data || []).map((item, index) => {
        const amountMillion = this.currencyPipe.transform(
          AppUtils.getMillionValue(item.facilityAmount),
          "",
          "",
          "1.3-3",
        );
        return {
          ...item,
          facilityAmountMillion: amountMillion,
          label: `${index + 1}. ${item.creditFacilityName} - ${item.facilityCurrency} ${amountMillion} Mn`,
        };
      });
      this.modalRef = this.mdbModalService.show(FacilitySelectModalComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-lg",
        containerClass: "",
        animated: false,
        data: {
          facilities: facilities,
          accountId: accountId,
          facilityPaperId: this.facilityPaper.facilityPaperID,
          recordId: existingRecordId,
        },
      });
      // Listen for facility selection
      if (this.modalRef.content && this.modalRef.content.facilitySelected) {
        this.modalRef.content.facilitySelected.subscribe(
          ({ acctId, facility }) => {
            this.selectedFacilities[acctId] = facility;
            this.existingFacilityCovenants[acctId] = facility;
            this.facilityDisplayOrderByAcctId[acctId] = facility.displayOrder;
            console.log(
              "Facility selected for acctId:",
              acctId,
              "Facility:",
              facility,
            );
          },
        );
      }
    });
  }

  checkMatch() {
    if (this.facilityPaper && this.facilityPaper.facilityPaperID) {
      this.facilityPaperAddEditService
        .getAllExistingFacilityCovenants(this.facilityPaper.facilityPaperID)
        .then((covenantData: any[]) => {
          if (Array.isArray(covenantData)) {
            this.facilityPaperAddEditService
              .getFacilityList()
              .then((facilityList: any[]) => {
                if (Array.isArray(facilityList)) {
                  covenantData.forEach((item) => {
                    const key = item.acctId || item.accountId;

                    if (key) {
                      this.existingCovenantRecordIdByAcctId[key] = item.id;
                    }

                    if (key && item.facilityId) {
                      const matchedFacility = facilityList.find(
                        (fac) => fac.facilityID === item.facilityId,
                      );

                      if (matchedFacility) {
                        const displayOrder = matchedFacility.displayOrder;
                        const amountMillion =
                          matchedFacility.facilityAmountMillion ||
                          (matchedFacility.facilityAmount
                            ? typeof matchedFacility.facilityAmount === "number"
                              ? (
                                  matchedFacility.facilityAmount / 1000000
                                ).toFixed(3)
                              : matchedFacility.facilityAmount
                            : "");

                        this.existingFacilityCovenants[key] = {
                          ...matchedFacility,
                          facilityAmountMillion: amountMillion,
                          label:
                            `${matchedFacility.creditFacilityName} - ` +
                            `${matchedFacility.facilityCurrency} ` +
                            `${amountMillion} Mn`,
                        };
                        this.facilityDisplayOrderByAcctId[key] = displayOrder;
                      } else {
                        this.existingFacilityCovenants[key] = item;
                      }
                    }
                  }); 

                  this.groupCovTypACompStYByAcctId();
                  this.groupCovTypACompStNByAcctId();
                }
              });
          }
        });
    }
  }
}
