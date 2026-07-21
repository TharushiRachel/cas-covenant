import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { AdvanceAnalyticsService } from "../../services/advance-analytics.service";
import {
  AnalyticsDecision,
  BorrowerCrib,
  BorrowerPrincipalRequestDTO,
  BorrowerRequestDTO,
  CribIdentification,
  CRIBRequestDTO,
  FacilityRequestDTO,
  LeadCompBorrowerDTO,
  LeaseJourneyRequestDTO,
} from "../../interfaces/Lead-comp-borrower-dto";
import * as moment from "moment";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { LeadCompFacilityDTO } from "../../interfaces/Lead-comp-facility-dto";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { SDConstants } from "../../interfaces/utils";
import { LeadCompPartiesDTO } from "../../interfaces/Lead-comp-parties-dto";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-advance-analytics-view",
  templateUrl: "./advance-analytics-view.component.html",
  styleUrls: ["./advance-analytics-view.component.scss"],
})
export class AdvanceAnalyticsViewComponent implements OnInit {
  @Input() leadData: any;
  @Input() leadStatus: any;
  @Output() handleDecision: EventEmitter<any> = new EventEmitter();

  idTypeDescription: any = Constants.identificationTypeDescription;

  cribElegibilites: BorrowerCrib[] = [];
  customerCribDetails: CRIBRequestDTO[] = [];
  analyticsDecision: AnalyticsDecision = null;
  relatedParties: LeadCompPartiesDTO[] = [];
  leadFacilityAmount: number = 0;

  leadFacility: FacilityRequestDTO = {
    facilityAmount: 0,
    facilityType: "",
    tenure: "",
  };

  modalRef: MDBModalRef;

  constructor(
    private readonly advanceAnalyticsService: AdvanceAnalyticsService,
    private readonly applicationService: ApplicationService,
    private readonly mdbModalService: MDBModalService,
    private readonly alertService: AlertService,
  ) {}

  ngOnInit() {
    if (this.leadData !== null) {
      this.relatedParties =
        this.leadData.relatedParties !== null
          ? this.leadData.relatedParties
          : [];

      if (
        this.leadData.analyticsDecision !== null &&
        this.analyticsDecision === null
      ) {
        this.analyticsDecision = this.leadData.analyticsDecision;
      }

      if (this.leadData.parties !== null) {
        this.prepareBorrowers(this.leadData.parties);
      }

      if (
        this.leadData.facilityDTOList !== null &&
        this.leadData.facilityDTOList.length > 0
      ) {
        let facility: LeadCompFacilityDTO =
          this.leadData.facilityDTOList.reduce(
            (prev: LeadCompFacilityDTO, current: LeadCompFacilityDTO) =>
              prev.requestedTenure > current.requestedTenure ? prev : current,
          );

        let leadFacilityAmount: number = this.calculateFacilityAmount(
          this.leadData.facilityDTOList,
        );

        this.leadFacility = {
          facilityAmount: leadFacilityAmount,
          facilityType: facility.facilityType,
          tenure: facility.requestedTenure.toString(),
        };
      }
    }
  }

  prepareBorrowers(parties: LeadCompBorrowerDTO[]) {
    parties.forEach((element: LeadCompBorrowerDTO) => {
      const borrower: BorrowerCrib = {
        id: element.compPartyId,
        personalName: element.personalName,
        gender: element.gender,
        customerType: Constants.AACustomerType.NTB,
        creationType: element.creationType,
        borrowerType: element.partyType,
        identifications:
          element.identifications !== null
            ? element.identifications.map((e: any) => ({
                ...e,
                isCribCheck: false,
              }))
            : [],
        isAAEligible: false,
        inquiryReason: this.getInquiryReason(element),
      };
      this.cribElegibilites.push(borrower);
    });
  }

  getInquiryReason(borrower: LeadCompBorrowerDTO) {
    let type: string = "";
    let isPersonal: boolean =
      borrower.creationType ===
      Constants.leadCompCreationTypeTypeConst.PERSONAL;
    let isBorrower: boolean =
      borrower.partyType === Constants.leadCompBorrowerTypeConst.BORROWER;

    let relatedParty: LeadCompPartiesDTO = this.relatedParties.find(
      (party: LeadCompPartiesDTO) => party.mainParty === borrower.compPartyId,
    );
    let relationShip: string =
      relatedParty !== undefined && relatedParty !== null
        ? relatedParty.relationshipDescription
        : "";

    if (isBorrower) {
      type = SDConstants.inquiryReason.IR1;
    } else if (
      isPersonal &&
      [
        SDConstants.relationship.PARTNER,
        SDConstants.relationship.OWNER,
      ].includes(relationShip)
    ) {
      type = SDConstants.inquiryReason.IR2;
    } else if (
      isPersonal &&
      [SDConstants.relationship.DIRECTOR].includes(relationShip)
    ) {
      type = SDConstants.inquiryReason.IR3;
    } else {
      type = SDConstants.inquiryReason.IR1;
    }

    return type;
  }

  calculateFacilityAmount(facilities: LeadCompFacilityDTO[]) {
    let amount: number = 0;
    facilities.forEach((facility: LeadCompFacilityDTO) => {
      if (facility.leaseAmount !== null && facility.leaseAmount !== 0) {
        amount = amount + facility.leaseAmount;
      }
    });

    return amount;
  }

  getCribDetails(borrower: BorrowerCrib) {
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
        heading: "New Crib Report - Confirmation",
        message: "This service will incur charges. Do you want to proceed?",
        isCribConfirmation: true,
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.proceedReportFetching(borrower);
      }
    });
  }

  handleAddJson(identification: CribIdentification, reportJson: any) {
    if (
      !this.customerCribDetails.some(
        (data: CRIBRequestDTO) =>
          data.identityRef === identification.identificationNumber,
      )
    ) {
      this.customerCribDetails.push({
        identityDoc:
          SDConstants.identificationTypeConst[
            identification.identificationType
          ],
        identityRef: identification.identificationNumber,
        reportJson: { data: reportJson },
      });
    }
  }

  async proceedReportFetching(borrower: BorrowerCrib) {
    let basePayload: any = {
      applicationNumber: this.leadData.leadRefNumber,
      applicationDate: moment().format("YYYY-MM-DD"),
      fullName: borrower.personalName,
      gender: borrower.gender,
      creditFacilityType: "LOAN",
      creditFacilityCurrency: "LKR",
      reportDate: moment().format("YYYY-MM-DD"),
      creditFacilityAmountDTO: {
        value: this.leadFacilityAmount,
        currency: "LKR",
        localValue: this.leadFacilityAmount,
      },
      idNumberDTOList: [
        {
          idNumberType: "",
          idNumber: "",
        },
      ],
      inquiryReason: borrower.inquiryReason,
      leadId: this.leadData.leadId,
      leadRef: this.leadData.leadRef,
      userDetailsDTO: {
        userID: this.applicationService.getLoggedInUserUserID(),
        userName: this.applicationService.getLoggedInUserUserName(),
        divCode: this.applicationService.getLoggedInUserDivCode(),
        upmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
        displayName: this.applicationService.getLoggedInUserDisplayName(),
      },
    };

    const valid: CribIdentification[] = borrower.identifications.filter(
      (el) => !el.isCribCheck && !!el.identificationNumber,
    );

    const business: CribIdentification[] = valid.filter(
      (el) => el.identificationType === Constants.identificationTypeConst.BRC,
    );

    const individual: CribIdentification[] = valid.filter((el) =>
      [
        Constants.identificationTypeConst.NEW_NIC,
        Constants.identificationTypeConst.OLD_NIC,
      ].includes(el.identificationType),
    );

    if (individual.length > 0) {
      this.handleIndividualCrib(basePayload, individual, borrower);
    }

    if (business.length > 0) {
      this.handleCompanyCrib(basePayload, business, borrower);
    }
  }

  async handleIndividualCrib(
    basePayload: any,
    individual: CribIdentification[],
    borrower: BorrowerCrib,
  ) {
    for (const element of individual) {
      const payload = {
        ...basePayload,
        applicationNumber: `${element.identificationNumber}-Lead`,
        idNumberDTOList: [
          { idNumberType: "NIC", idNumber: element.identificationNumber },
        ],
      };
      try {
        const response =
          await this.advanceAnalyticsService.searchIndividualCrib(payload);
        if (response !== null && response.data !== null) {
          this.handleAddJson(element, response.data);
          this.handleBorrowerCribChange(borrower, element);
        }
      } catch (err) {
        this.alertService.showToaster(
          "Failed to retrive crib data.",
          SETTINGS.TOASTER_MESSAGES.error,
        );
        console.log("Individual CRIB search failed:", err);
      }
    }
  }

  async handleCompanyCrib(
    basePayload: any,
    company: CribIdentification[],
    borrower: BorrowerCrib,
  ) {
    for (const element of company) {
      const payload = {
        ...basePayload,
        applicationNumber: `${element.identificationNumber}-Lead`,
        idNumberDTOList: [
          {
            idNumberType: "BusinessRegistrationNumber",
            idNumber: element.identificationNumber,
          },
        ],
      };
      try {
        const response =
          await this.advanceAnalyticsService.searchCompanyCrib(payload);
        if (response !== null && response.data !== null) {
          this.handleAddJson(element, response.data);
          this.handleBorrowerCribChange(borrower, element);
        }
      } catch (err) {
        this.alertService.showToaster(
          "Failed to retrive crib data.",
          SETTINGS.TOASTER_MESSAGES.error,
        );
        console.log("Individual CRIB search failed:", err);
      }
    }
  }

  handleBorrowerCribChange(
    borrower: BorrowerCrib,
    cribIdentification: CribIdentification,
  ) {
    this.cribElegibilites = this.cribElegibilites.map((data: BorrowerCrib) =>
      data.id === borrower.id
        ? {
            ...data,
            identifications: data.identifications.map(
              (identification: CribIdentification) =>
                identification.identificationNumber ===
                cribIdentification.identificationNumber
                  ? { ...identification, isCribCheck: true }
                  : identification,
            ),
          }
        : data,
    );

    this.cribElegibilites = this.cribElegibilites.map((data: BorrowerCrib) =>
      data.id === borrower.id
        ? {
            ...data,
            isAAEligible: data.identifications.some(
              (di: CribIdentification) => di.isCribCheck === true,
            ),
          }
        : data,
    );
  }

  isCheckCribDisabled(cribIdentifications: CribIdentification[]) {
    return !cribIdentifications.some(
      (d: CribIdentification) => d.isCribCheck === false,
    );
  }

  prepareBorrowerRequest() {
    let company: BorrowerRequestDTO[] = [];
    let individuals: BorrowerRequestDTO[] = [];

    this.cribElegibilites.forEach((borrower: BorrowerCrib) => {
      let borrowerRequest: BorrowerRequestDTO = {
        name: borrower.personalName,
        type: this.getBorrowerReqType(),
        principal:
          borrower.creationType ===
          Constants.leadCompCreationTypeTypeConst.BUSINESS
            ? this.getPrincipals()
            : [],
        cribRequests: this.customerCribDetails.filter((cd: CRIBRequestDTO) =>
          borrower.identifications.some(
            (bi: CribIdentification) =>
              bi.identificationNumber === cd.identityRef,
          ),
        ),
      };

      if (
        borrower.creationType ===
        Constants.leadCompCreationTypeTypeConst.PERSONAL
      ) {
        individuals.push(borrowerRequest);
      }

      if (
        borrower.creationType ===
        Constants.leadCompCreationTypeTypeConst.BUSINESS
      ) {
        company.push(borrowerRequest);
      }
    });

    return { company: company, individuals: individuals };
  }

  getPrincipals() {
    let principals: BorrowerPrincipalRequestDTO[] = [];

    let mainParties: BorrowerCrib[] = this.cribElegibilites.filter(
      (ce: BorrowerCrib) =>
        ce.creationType === Constants.leadCompCreationTypeTypeConst.PERSONAL,
    );

    mainParties.forEach((element: BorrowerCrib) => {
      let relatedParty: any = this.relatedParties.find(
        (rp: any) => rp.mainPartnerId === element.id,
      );
      if (relatedParty !== undefined && relatedParty !== null) {
        let principal: BorrowerPrincipalRequestDTO = {
          identityDoc:
            SDConstants.identificationTypeConst[
              element.identifications[0].identificationType
            ],
          identityRef: element.identifications[0].identificationNumber,
          relationship:
            relatedParty.relationshipDescription !== null
              ? relatedParty.relationshipDescription
              : "",
          share: relatedParty.share !== null ? relatedParty.share : "",
        };
        principals.push(principal);
      }
    });

    return principals;
  }

  checkAADecision() {
    let payload: LeaseJourneyRequestDTO = {
      leadId: this.leadData.leadId,
      leadRef: this.leadData.leadRefNumber,
      journeyType: this.getJourneyType(),
      facility: this.leadFacility,
      ...this.prepareBorrowerRequest(),
    };

    this.advanceAnalyticsService
      .getLeasingJourneyValidation(payload)
      .then((response: any) => {
        if (response !== null) {
          this.analyticsDecision = response;
          this.handleDecision.next(this.analyticsDecision);
        }
      });
  }

  getBorrowerReqType() {
    if (
      ["SOUL_PROPRITER", "PARTNERSHIP", "LIMITED_LIABILITY"].includes(
        this.leadData.leadPurpose,
      )
    ) {
      return SDConstants.companyCribType[this.leadData.leadPurpose];
    }
    return "";
  }

  getJourneyType() {
    if (
      ["SOUL_PROPRITER", "PARTNERSHIP", "LIMITED_LIABILITY"].includes(
        this.leadData.leadPurpose,
      )
    ) {
      return SDConstants.companyCribType[this.leadData.leadPurpose];
    }
    return "IndividualJoint";
  }

  showDesicion() {
    return this.analyticsDecision !== null;
  }

  isBranchManager() {
    return (
      this.leadData !== null &&
      this.leadData.leadId !== null &&
      this.leadData.leadId !== 0 &&
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) === 50
    );
  }

  showAAButton() {
    let isNotElegibilitesExist: boolean = this.cribElegibilites.some(
      (ce: BorrowerCrib) => ce.isAAEligible === false,
    );
    return (
      !isNotElegibilitesExist &&
      this.isBranchManager() &&
      !this.showDesicion() &&
      this.isAllowedLead() &&
      this.isAllowedUser()
    );
  }

  showCribButton() {
    return (
      !this.showDesicion() &&
      this.isBranchManager() &&
      this.isAllowedLead() &&
      this.isAllowedUser()
    );
  }

  isAllowedUser() {
    if (this.leadData !== null) {
      return (
        this.applicationService.getLoggedInUserUserName() !==
          this.leadData.createdBy &&
        this.applicationService.getLoggedInUserDivCode() ===
          this.leadData.branchCode
      );
    }
    return false;
  }

  isAllowedLead() {
    if (this.leadStatus !== null) {
      return [Constants.leadStatusConst.SUBMITTED].includes(
        this.leadStatus.status,
      );
    }

    return false;
  }
}
