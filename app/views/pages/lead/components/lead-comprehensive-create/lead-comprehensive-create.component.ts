import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { LeadComprehensiveIncomeTypeComponent } from "../dialogs/lead-comprehensive-income-type/lead-comprehensive-income-type.component";
import { LeadComprehensivePartiesComponent } from "../dialogs/lead-comprehensive-parties/lead-comprehensive-parties.component";
import { LeadComprehensiveFacilitieseComponent } from "../dialogs/lead-comprehensive-facilitiese/lead-comprehensive-facilitiese.component";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import {
  Address,
  AnalyticsDecision,
  BorrowerCrib,
  Identifications,
  LeadCompBorrowerDTO,
  LeadStatus,
} from "../../interfaces/Lead-comp-borrower-dto";
import { LeadCompPartiesDTO } from "../../interfaces/Lead-comp-parties-dto";
import { LeadCompIncomeTypeDTO } from "../../interfaces/Lead-comp-income-type-dto";
import { LeadCompFacilityDTO } from "../../interfaces/Lead-comp-facility-dto";
import {
  LabelValue,
  LeadCompDetailsDTO,
} from "../../interfaces/Lead-comp-lead-main-details";
import { LeadComprehensiveService } from "../../services/lead-comprehensive.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Party, SaveObj } from "../../interfaces/save-dtos/Lead-comp-save-dto";
import { CacheService } from "src/app/core/service/data/cache.service";
import { BehaviorSubject, Subject, Subscription } from "rxjs";
import { CurrencyPipe } from "@angular/common";
import { LeadAddEditService } from "../../services/lead-add-edit.service";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { ActivatedRoute, Router } from "@angular/router";
import { LeadCreateApplicationForm } from "../lead-create-application-form/lead-create-application-form.component";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { LocalStorage } from "ngx-webstorage";
import { RelatedParty } from "../../interfaces/save-dtos/Lead-comp-parties.save-dto";
import { AddCommentModalComponent } from "../dialogs/add-comment-modal/add-comment-modal.component";
import { LeadComprehensiveDocumentComponent } from "../dialogs/lead-comprehensive-document/lead-comprehensive-document.component";
import { ExternalLeadDeclineComponent } from "../dialogs/external-lead-decline/external-lead-decline.component";
import { ExternalLeadReturnComponent } from "../dialogs/external-lead-return/external-lead-return.component";
import * as moment from "moment";
import { SDConstants } from "../../interfaces/utils";
import { LeadDocumentUpdateDto } from "../../dto/lead-document-update-dto";

@Component({
  selector: "app-lead-comprehensive-create",
  templateUrl: "./lead-comprehensive-create.component.html",
  styleUrls: ["./lead-comprehensive-create.component.scss"],
})
export class LeadComprehensiveCreateComponent implements OnInit, OnDestroy {
  leadParentForm: FormGroup;

  //MDBModalRef
  addBorrowerModalRef: MDBModalRef;
  addIncomeTypeModalRef: MDBModalRef;
  addRelatedPartiesModalRef: MDBModalRef;
  addFacilitiesModalRef: MDBModalRef;
  addDocumentModalRef: MDBModalRef;

  //dropdowns
  borrowerList: LeadCompBorrowerDTO[] = [];
  relatedPartiesList: LeadCompPartiesDTO[] = [];
  incomeSourceList: LeadCompIncomeTypeDTO[] = [];
  facilitiesList: LeadCompFacilityDTO[] = [];
  documentList = [];

  selectedTabIndex: number = 0;

  borrwerData: LeadCompBorrowerDTO;

  LeadCompDetailsDTO: LeadCompDetailsDTO;

  //flags
  //check first adding comprehensive lead
  isSaveLeadFirstTime: boolean = false;
  //check related party and income type and lead facility
  isEnableAddRPITLF: boolean = false;
  // check lead name or prefered branch changed
  isLeadChanged: boolean = false;
  // is edit flag
  isEdit: boolean = false;
  isFormDisabled: boolean = false;
  leadActionConst: any = Constants.leadActionConst;
  leadStatusConst: any = Constants.leadStatusConst;

  modalRef: MDBModalRef;

  compLeadId: number = 0;
  leadId: number = 0;

  leadComprehensiveTypeConst: any = Constants.leadCompCreationTypeTypeConst;
  borrowers: BorrowerCrib[] = [];

  partyIdNameList: {
    value: number;
    label: string;
    creationType: string;
    leadPurpose: string;
  }[] = [];

  supportingDocDropdown = [];

  branchesDropdown: Subject<any>;
  allBranches: any = [];

  incomeTypeOptions: LabelValue[] = [
    { value: 1, label: "SALARY" },
    { value: 2, label: "RENTAL" },
    { value: 3, label: "BUSINESS" },
  ];

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID: any;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  onBranchNameChangeSub: Subscription = new Subscription();

  formErrors: any;
  onSelectedLeadChangeSub: Subscription = new Subscription();
  leadData: any = null;

  digitalApplications: any[] = [];
  leadStatus: LeadStatus = {
    status: Constants.leadStatusConst.PENDING,
    createdBy: "",
    assignUserId: "",
    leadRefNumber: "",
  };
  deleteModalRef: MDBModalRef;

  comments: any[] = [];
  requiredDocArray: any[] = ["CRIB", "NIC"];

  @ViewChild("downloadLink", { static: false })
  private downloadLink: ElementRef;

  isSaveLoading: boolean = false;
  tableColumnSForLeadAction = ["User", "Action", "Date", "Remark"];

  leadStatusHistory: any[] = [];
  afStatusHistory: any[] = [];
  fpStatusHistory: any[] = [];

  leadPurposeOptions: any[] = Constants.leadPuposeList;
  leadPurpose: string = "";

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly mdbModalService: MDBModalService,
    private readonly alertService: AlertService,
    private readonly leadComprehensiveService: LeadComprehensiveService,
    private readonly applicationService: ApplicationService,
    private readonly cacheService: CacheService,
    private readonly currencyPipe: CurrencyPipe,
    private readonly leadAddEditService: LeadAddEditService,
    private readonly router: Router,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef,
  ) { }

  ngOnInit() {
    this.updateLeadPurpose(Constants.leadPuposeConst.INDIVIDUAL);
    this.leadParentForm = this.loadLeadParentForm();

    this.formErrors = {
      leadName: {},
      preferredBranch: {},
      leadPurpose: {},
    };

    this.isSaveLeadFirstTime = true;

    this.leadParentForm.controls.leadName.valueChanges.subscribe(
      (value: any) => {
        this.isLeadChanged = true;
      },
    );

    this.leadParentForm.controls.preferredBranch.valueChanges.subscribe(
      (value: any) => {
        this.isLeadChanged = true;
      },
    );

    this.leadParentForm.controls.leadPurpose.valueChanges.subscribe(
      (value: any) => {
        this.isLeadChanged = true;
        this.updateLeadPurpose(value);
      },
    );

    this.activatedRoute.queryParams.subscribe((params) => {
      //get from params for edit
      if (params["selectedLeadID"]) {
        const leadId: number = parseInt(
          this.urlEncodeService.decode(params["selectedLeadID"]),
        );
        this.leadId = leadId;
        this.getLeadDataById(leadId);
        // get from localstorage for create reload
      } else if (
        this.selectedLeadID !== null &&
        this.selectedLeadID !== undefined
      ) {
        const leadId: number = parseInt(
          this.urlEncodeService.decode(this.selectedLeadID),
        );
        this.leadId = leadId;

        this.getLeadDataById(leadId);
      } else {
        this.handleAddBorower(this.leadComprehensiveTypeConst.PERSONAL);
        this.borrwerData = this.borrowerList[0];
      }
    });

    this.onBranchNameChangeSub =
      this.leadParentForm.controls.preferredBranch.valueChanges.subscribe(
        (value: any) => {
          this.branchesDropdown = new BehaviorSubject(this.allBranches);
        },
      );

    this.loadBranches();
    this.loadsupportingDocDropdown();
  }

  getLeadDataById(leadId: number) {
    this.leadComprehensiveService.getLeadById(leadId).then((leadResponse) => {
      if (leadResponse !== null) {
        this.mapLeadData(leadResponse);
        this.loadData(leadResponse);
        this.cdr.detectChanges();
      }
    });
  }

  mapLeadData(data: any) {
    this.leadData = {
      ...data.comprehensiveLeadDTO,
      leadId: data.leadId,
      leadRefNumber: data.leadRefNumber,
      createdUserDisplayName: data.createdUserDisplayName,
      assignUserId: data.assignUserID,
      branchCode: data.branchCode,
      branchName: data.branchName,
    };
    this.leadStatus = {
      status: data.leadStatus,
      createdBy: data.createdBy,
      assignUserId: data.assignUserID,
      leadRefNumber: data.leadRefNumber,
    };
  }

  borrowerMapData(data: LeadCompBorrowerDTO[]) {
    if (data && data !== undefined && data !== null) {
      let borrowerList: LeadCompBorrowerDTO[] = [];
      borrowerList = data.map((party: LeadCompBorrowerDTO) => ({
        ...party,
        compPartyId: party.compPartyId,
        address1: party.addresses[0].address1,
        address2: party.addresses[0].address2,
        identifications: party.identifications,
        city: party.addresses[0].city,
      }));

      this.borrowerList = borrowerList.sort(
        (a: LeadCompBorrowerDTO, b: LeadCompBorrowerDTO) =>
          a.compPartyId - b.compPartyId,
      );
      this.borrwerData = borrowerList[this.selectedTabIndex];

      this.partyIdNameList = data.map((party: any) => ({
        value: party.compPartyId,
        label: party.personalName,
        creationType: party.creationType,
        leadPurpose: this.leadPurpose,
      }));
    }
  }

  isLoggedInUserCanEdit(lead: any) {
    let upmGroupCode: number =
      this.applicationService.getLoggedInUserUPMGroupCode() !== null
        ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
        : 0;
    if (upmGroupCode === 10 || upmGroupCode === SETTINGS.SALES_PERSON_WC) {
      return (
        ((lead &&
          this.applicationService.getLoggedInUserUserName() ===
          lead.createdBy) ||
          (lead &&
            this.applicationService.getLoggedInUserUserName() ===
            lead.assignUserID)) &&
        (lead.leadStatus == Constants.leadStatusConst.PENDING ||
          lead.leadStatus == Constants.leadStatusConst.RETURNED)
      );
    }
    return (
      lead &&
      this.applicationService.getLoggedInUserUserName() === lead.createdBy &&
      (lead.leadStatus == Constants.leadStatusConst.PENDING ||
        lead.leadStatus == Constants.leadStatusConst.RETURNED)
    );
  }

  goBack() {
    this.router.navigate(["/leads/dashboard"]);
  }

  loadBranches() {
    let allBranches: any[] = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES,
    );

    this.branchesDropdown = new BehaviorSubject(allBranches);
    this.allBranches = allBranches;
  }

  ngOnDestroy(): void {
    this.onBranchNameChangeSub.unsubscribe();
    this.onSelectedLeadChangeSub.unsubscribe();
    this.branchesDropdown.unsubscribe();
    this.selectedLeadID = null;
    localStorage.removeItem(SETTINGS.STORAGE.SELECTED_LEAD_ID);
  }

  loadData(lead: any) {
    let isEdit = lead.leadId !== null;
    this.isEdit = isEdit;
    let editObj = lead !== null ? lead.comprehensiveLeadDTO : null;
    this.isSaveLeadFirstTime = lead.leadId === null;
    this.isEnableAddRPITLF = lead.leadId !== null;

    this.compLeadId = editObj.compLeadId;

    if (!editObj.parties || editObj.parties.length === 0) {
      this.alertService.showToaster(
        "Lead data issue!.",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      // this.router.navigate(["/leads/dashboard"]);
    }

    this.leadId = lead.leadId;

    if (this.leadId !== null && this.leadId !== 0) {
      this.getDigitalApplications();
    }

    let leadPurpose =
      editObj.leadPurpose && editObj.leadPurpose !== null
        ? editObj.leadPurpose
        : Constants.leadPuposeConst.INDIVIDUAL;

    this.updateLeadPurpose(leadPurpose);

    this.leadParentForm.patchValue({
      leadName: lead.leadName ? lead.leadName : "",
      preferredBranch:
        editObj.preferredBranch && editObj.preferredBranch !== null
          ? editObj.preferredBranch
          : "",
      leadPurpose: leadPurpose,
    });
    this.compLeadId = editObj.compLeadId;

    if (editObj.parties) {
      let tempBorr: LeadCompBorrowerDTO[] = [];
      tempBorr = editObj.parties.map((party: LeadCompBorrowerDTO) => ({
        ...party,
        address1: party.addresses[0].address1,
        address2: party.addresses[0].address2,
        identifications: party.identifications,
        city: party.addresses[0].city,
        isCreationTypeEnable: false,
      }));

      //this.borrowerList = tempBorr.sort((a:LeadCompBorrowerDTO,b:LeadCompBorrowerDTO) => a.compPartyId - b.compPartyId);
      this.borrwerData = tempBorr[0];

      this.partyIdNameList = editObj.parties.map((party: any) => ({
        value: party.compPartyId,
        label: party.personalName,
        creationType: party.creationType,
        leadPurpose: this.leadPurpose,
      }));
    }

    this.borrowerMapData(editObj.parties);

    if (editObj.relatedParties) {
      this.relatedPartiesList = editObj.relatedParties
        .map((rParty: any) => ({
          ...rParty,
          relatedPartyId: rParty.relatedPartyId,
          mainParty: rParty.mainPartnerId,
          relatedParty: rParty.relatedPartnerId,
          relationshipDescription: rParty.relationshipDescription,
        }))
        .sort((a: any, b: any) => a.relatedPartyId - b.relatedPartyId);
    }

    if (editObj.incomeSources) {
      this.incomeSourceList = editObj.incomeSources.map(
        (rIncomeSource: any) => ({
          ...rIncomeSource,
          incomeType: rIncomeSource.incomeType,
          compPartyId: rIncomeSource.compPartyId,
          party: rIncomeSource.compPartyId,
          considerForRepayment:
            rIncomeSource.considerForRepayment === "Y" ? true : false,
        }),
      );
    }

    if (editObj.facilityDTOList) {
      this.facilitiesList = editObj.facilityDTOList.map((rFacility: any) => ({
        ...rFacility,
        compLeadId: rFacility.compLeadId,
      }));
    }

    if (editObj.leadComments !== null) {
      this.comments = editObj.leadComments;
    }

    if (editObj.documentList !== null) {
      this.mapLeadDocuments(editObj.documentList, true);
    }

    let isFormDisabled =
      this.isEdit &&
      lead &&
      !(
        lead.leadStatus == Constants.leadStatusConst.PENDING ||
        lead.leadStatus == Constants.leadStatusConst.RETURNED
      ) &&
      !this.isLoggedInUserCanEdit(lead);

    this.isFormDisabled = isFormDisabled;

    if (!this.isFormEditEnabled()) {
      this.leadParentForm.controls["leadName"].disable();
    }
    if (!this.isBranchCanEdit()) {
      this.leadParentForm.controls["preferredBranch"].disable();
      this.leadParentForm.controls["leadPurpose"].disable();
    }

    if (
      lead &&
      lead.leadActionDTOList !== null &&
      lead.leadActionDTOList.length > 0
    ) {
      this.leadStatusHistory = lead.leadActionDTOList
        .sort((a: any, b: any) => b.leadActionId - a.leadActionId)
        .map((d: any) => ({
          actionedByDisplayName: d.actionedByDisplayName,
          action: d.action,
          remark: d.remark,
          actionedDateTime: d.actionedTimeStamp
            ? moment(d.actionedTimeStamp).format("Do MMMM YYYY HH:mm:ss")
            : "-",
        }));
    }

    if (
      [
        Constants.leadStatusConst.PAPER_CREATED,
        Constants.leadStatusConst.APPLICATION_CREATED,
      ].includes(lead.leadStatus)
    ) {
      this.getLeadAFAndFPHistory(this.leadId);
    }
  }

  loadsupportingDocDropdown() {
    this.leadComprehensiveService.getSupportinDocs().then((res) => {
      this.supportingDocDropdown = res;
    });
  }

  isEnableSubmit() {
    if (this.leadData !== null && this.leadStatus !== null) {
      return (
        [
          Constants.leadStatusConst.PENDING,
          Constants.leadStatusConst.RETURNED,
        ].includes(this.leadStatus.status) &&
        (this.applicationService.getLoggedInUserUserName() ===
          this.leadData.createdBy ||
          this.applicationService.getLoggedInUserUserName() ===
          this.leadData.assignUserId)
      );
    }

    return false;
  }

  getColour(status: any) {
    switch (status) {
      case this.leadStatusConst.PENDING:
        return { color: "#ffbb33a6" };
      case this.leadStatusConst.SUBMITTED:
        return { color: "#0099cc94" };
      case this.leadStatusConst.IN_PROGRESS:
        return { color: "#0099cc94" };
      case this.leadStatusConst.PAPER_CREATED:
        return { color: "#007e338a" };
      case this.leadStatusConst.RETURNED:
      case this.leadStatusConst.DECLINED:
        return { color: "#cc0000a6" };
    }
  }

  isEnableAccept() {
    if (
      this.isBranchManager() &&
      this.applicationService.getLoggedInUserUserName() !==
      this.leadData.createdBy &&
      this.applicationService.getLoggedInUserUserName() !==
      this.leadData.assignUserId &&
      this.applicationService.getLoggedInUserDivCode() ===
      this.leadData.branchCode &&
      this.leadStatus.status === Constants.leadStatusConst.SUBMITTED
    ) {
      return true;
    }
    return false;
  }

  loadLeadParentForm() {
    return this.formBuilder.group({
      leadName: [{ value: "", disabled: this.isFormDisabled }],
      preferredBranch: [{ value: "", disabled: !this.isBranchCanEdit() }],
      leadPurpose: [{ value: "INDIVIDUAL", disabled: !this.isBranchCanEdit() }],
    });
  }

  getIncomeNameById(value: number) {
    const item = this.incomeTypeOptions.find(
      (option) => option.value === value,
    );
    return item ? item.label : null;
  }

  getPartyNameById(value: number) {
    const item = this.partyIdNameList.find((option) => option.value === value);
    return item ? item.label : null;
  }

  setTabIndex($event: any) {
    let index = $event;
    this.selectedTabIndex = index;
    let borrowerList = this.borrowerList;
    let isidentificationsSaved = borrowerList[index].identifications.find(
      (rec) => rec.identificationId,
    );
    if (isidentificationsSaved === undefined) {
      borrowerList[index].identifications = [];
    }
    this.borrwerData = borrowerList[index];
  }

  addBorrowerByPurpose() {
    if (
      this.borrowerList.some(
        (b: LeadCompBorrowerDTO) =>
          b.compPartyId === null || b.compPartyId === 0,
      )
    ) {
      this.alertService.showToaster(
        "There is an empty or unsaved borrower existing.",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    let leadPurpose: string = this.leadPurpose;
    let personalCount: number = this.borrowerList.filter(
      (b: LeadCompBorrowerDTO) =>
        b.creationType === this.leadComprehensiveTypeConst.PERSONAL,
    ).length;

    let businessCount: number = this.borrowerList.filter(
      (b: LeadCompBorrowerDTO) =>
        b.creationType === this.leadComprehensiveTypeConst.BUSINESS,
    ).length;

    switch (leadPurpose) {
      case "INDIVIDUAL":
        if (this.borrowerList.length === 0) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.PERSONAL);
        } else {
          this.alertService.showToaster(
            "Borrower Count exceeded.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
        }
        break;
      case "INDIVIDUAL_JOINT":
        if (this.borrowerList.length === 1) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.PERSONAL);
        } else {
          this.alertService.showToaster(
            "Borrower Count exceeded.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
        }
        break;
      case Constants.leadPuposeConst.SOUL_PROPRITER:
        if (businessCount === 1 && personalCount === 0) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.PERSONAL);
        } else if (personalCount === 1 && businessCount === 0) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.BUSINESS);
        } else {
          this.alertService.showToaster(
            "Borrower Count exceeded.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
        }
        break;
      case Constants.leadPuposeConst.PARTNERSHIP:
        if (personalCount < 3) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.PERSONAL);
        } else if (businessCount === 0) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.BUSINESS);
        } else {
          this.alertService.showToaster(
            "Borrower Count exceeded.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
        }
        break;
      case Constants.leadPuposeConst.LIMITED_LIABILITY:
        if (personalCount < 3) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.PERSONAL);
        } else if (businessCount === 0) {
          this.handleAddBorower(this.leadComprehensiveTypeConst.BUSINESS);
        } else {
          this.alertService.showToaster(
            "Borrower Count exceeded.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
        }
        break;
      default:
        break;
    }
  }

  handleAddBorower(creationType: string) {
    this.borrowerList.push({
      compPartyId: 0,
      compLeadId: this.compLeadId,
      creationType: creationType,
      partyType: "",
      accountNumber: "",
      civilStatus: "",
      dateOfBirth: "",
      email: "",
      title:
        creationType === this.leadComprehensiveTypeConst.PERSONAL
          ? SDConstants.titleConst.MR
          : "",
      personalName: "",
      mobileNumber: "",
      address1: "",
      address2: "",
      city: "",
      identifications: [],
      gender: "",
      isBusiness: false,
      addresses: [],
      contactPerson: "",
      designation: "",
      typeOfBusiness: "",
      isCreationTypeEnable:
        creationType === this.leadComprehensiveTypeConst.PERSONAL &&
        ![
          Constants.leadPuposeConst.INDIVIDUAL,
          Constants.leadPuposeConst.INDIVIDUAL_JOINT,
        ].includes(this.leadPurpose),
    });

    setTimeout(() => {
      this.selectedTabIndex = this.borrowerList.length - 1;
      this.setTabIndex(this.selectedTabIndex);
      this.cdr.detectChanges();
    }, 100);
  }

  checkAddBorrowerCondition() {
    let isValid = false;
    let isBusinessExist = this.borrowerList.some(
      (rec) => rec.creationType === this.leadComprehensiveTypeConst.BUSINESS,
    );

    if (isBusinessExist) {
      let businessCount = 0;
      let personalCount = 0;
      this.borrowerList.map((rec) => {
        if (rec.creationType === this.leadComprehensiveTypeConst.BUSINESS) {
          businessCount++;
        } else {
          personalCount++;
        }
      });
      if (businessCount > 1) {
        this.alertService.showToaster(
          "Can't add more than 1 business borrowers!",
          SETTINGS.TOASTER_MESSAGES.warning,
        );
        return isValid;
      } else if (personalCount > 3) {
        this.alertService.showToaster(
          "Can't add more than 3 PersonalCount borrowers!",
          SETTINGS.TOASTER_MESSAGES.warning,
        );
        return isValid;
      }
      isValid = true;
      return isValid;
    } else {
      if (this.borrowerList.length >= 2) {
        this.alertService.showToaster(
          "Can't add more than 2 Persoanl borrowers!",
          SETTINGS.TOASTER_MESSAGES.warning,
        );
        return isValid;
      }
    }
    isValid = true;
    return isValid;
  }

  addRelatedParties() {
    this.addRelatedPartiesModalRef = this.mdbModalService.show(
      LeadComprehensivePartiesComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-50-p modal-dialog-scrollable",
        containerClass: "modal modal-blur-effect",
        animated: false,
        data: {
          content: {
            addRelatedPartiesModalRef: this.addRelatedPartiesModalRef,
            compLeadId: this.compLeadId,
            partyIdNameList: this.partyIdNameList,
            leadPurpose: this.leadPurpose,
            isEdit: true,
            relatedPartiesList: this.relatedPartiesList,
          },
        },
      },
    );
    this.addRelatedPartiesModalRef.content.savePartiesObj.subscribe(
      (data: any) => {
        if (data) {
          this.relatedPartiesList.push(data);
        }
      },
    );
  }

  editRelatedParty(data: LeadCompPartiesDTO, index: number) {
    this.addRelatedPartiesModalRef = this.mdbModalService.show(
      LeadComprehensivePartiesComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-50-p modal-dialog-scrollable",
        containerClass: "modal modal-blur-effect",
        animated: false,
        data: {
          content: {
            addRelatedPartiesModalRef: this.addRelatedPartiesModalRef,
            compLeadId: this.compLeadId,
            partyIdNameList: this.partyIdNameList,
            isEdit: true,
            editObj: data,
          },
        },
      },
    );
    this.addRelatedPartiesModalRef.content.savePartiesObj.subscribe(
      (data: any) => {
        if (data) {
          this.relatedPartiesList[index] = data;
        }
      },
    );
  }

  addIncomeType() {
    this.addIncomeTypeModalRef = this.mdbModalService.show(
      LeadComprehensiveIncomeTypeComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-50-p modal-dialog-scrollable",
        containerClass: "modal modal-blur-effect",
        animated: false,
        data: {
          content: {
            addIncomeTypeModalRef: this.addIncomeTypeModalRef,
            compLeadId: this.compLeadId,
            partyIdNameList: this.partyIdNameList,
          },
        },
      },
    );
    this.addIncomeTypeModalRef.content.saveIncomeSourceObj.subscribe(
      (data: any) => {
        if (data) {
          this.incomeSourceList.push(data);
        }
      },
    );
  }

  addFacilities() {
    this.addFacilitiesModalRef = this.mdbModalService.show(
      LeadComprehensiveFacilitieseComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-50-p modal-dialog-scrollable",
        containerClass: "modal modal-blur-effect",
        animated: false,
        data: {
          content: {
            addFacilitiesModalRef: this.addFacilitiesModalRef,
            compLeadId: this.compLeadId,
          },
        },
      },
    );
    this.addFacilitiesModalRef.content.saveFacilitiesObj.subscribe(
      (data: any) => {
        if (data) {
          this.facilitiesList.push(data);
        }
      },
    );
  }

  saveBorrowerObj(data: LeadCompBorrowerDTO) {
    if (data && data !== null && data !== undefined) {
      if (this.validateBorrowersByPurpose()) {
        return;
      }

      let leadName = this.leadParentForm.get("leadName")
        ? this.leadParentForm.get("leadName").value
        : "";
      let preferredBranch = this.leadParentForm.get("preferredBranch")
        ? this.leadParentForm.get("preferredBranch").value
        : "";

      if (!leadName || leadName === "") {
        this.alertService.showToaster(
          "LeadName sould not be empty!",
          SETTINGS.TOASTER_MESSAGES.warning,
        );
        return;
      }
      if (!preferredBranch || preferredBranch === "") {
        this.alertService.showToaster(
          "Preferred Branch sould not be empty!",
          SETTINGS.TOASTER_MESSAGES.warning,
        );
        return;
      }

      let borrowerList = this.borrowerList;

      borrowerList = borrowerList.map((b: any, i: number) =>
        b.compPartyId === data.compPartyId
          ? { ...data }
          : i === this.selectedTabIndex
            ? { ...data }
            : { ...b },
      );
      let compLeadId = this.compLeadId ? this.compLeadId : null;
      let leadId = this.leadId ? this.leadId : null;

      let parties: Party[] = borrowerList
        ? borrowerList.map((rec: any) => ({
          compLeadId: compLeadId,
          compPartyId: rec.compPartyId,
          creationType: rec.creationType,
          partyType: rec.partyType,
          accountNumber: rec.accountNumber,
          civilStatus: rec.civilStatus,
          dateOfBirth: rec.dateOfBirth,
          email: rec.email,
          title: rec.title,
          personalName: rec.personalName,
          mobileNumber: rec.mobileNumber,
          createdBy: this.applicationService.getLoggedInUserUserName(),
          addresses: [
            {
              address1: rec.address1,
              address2: rec.address2,
              city: rec.city,
            },
          ],
          identifications: rec.identifications,
          considerCrib: Constants.yesNoConst.Y,
          considerAA: Constants.yesNoConst.N,
          gender: rec.gender,
          contactPerson: rec.contactPerson,
          designation: rec.designation,
          typeOfBusiness: rec.typeOfBusiness,
        }))
        : [];

      if (!(borrowerList && borrowerList.length > 0)) {
        this.alertService.showToaster(
          "Can't save lead!",
          SETTINGS.TOASTER_MESSAGES.warning,
        );
        return;
      }

      this.borrowerList[this.selectedTabIndex] = data;
      this.borrwerData = data;

      let branchName: any = this.leadParentForm.get("preferredBranch").value
        ? this.leadParentForm.get("preferredBranch").value
        : this.leadData.branchName;

      let branchCode: any =
        branchName && this.getBrancCode(branchName)
          ? this.getBrancCode(branchName)
          : this.leadData.branchCode;

      let upmGroupCode: any =
        this.applicationService.getLoggedInUserUPMGroupCode();
      let leadPurpose: any = this.leadParentForm.get("leadPurpose").value;
      let firstSaveObj: SaveObj = {
        compLeadId: compLeadId,
        leadName: leadName,
        preferredBranch: preferredBranch,
        creationType:
          parties.length > 0
            ? parties[0].creationType
            : Constants.leadCompCreationTypeTypeConst.PERSONAL,
        createdBy: this.applicationService.getLoggedInUserUserName(),
        createdUserWorkClass: upmGroupCode,
        parties: parties,
        leadPurpose: leadPurpose,
        saveLeadDTO: {
          leadId: leadId,
          branchCode: branchCode,
          branchName: branchName,
          customerBankAccountNumber: data.accountNumber,
          leadType:
            parseInt(upmGroupCode) === SETTINGS.SALES_PERSON_WC
              ? Constants.leadTypeConst.EXTERNAL
              : Constants.leadTypeConst.INTERNAL,
          assignUserID: this.applicationService.getLoggedInUserUserName(),
          submitted: Constants.yesNoConst.N,
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          applicationFormID: 0,
          createdDate: new Date().toISOString(),
          createdBy: this.applicationService.getLoggedInUserUserName(),
          lastModifiedDate: new Date().toISOString(),
          modifiedBy: this.applicationService.getLoggedInUserDisplayName(),
          isCompLead: Constants.yesNoConst.Y,
          leadCreationType: this.getLeadCreationType(leadPurpose),
        },
      };

      this.isSaveLoading = true;
      // need to get lead comp id
      this.leadComprehensiveService
        .saveComprehensiveLead(firstSaveObj)
        .then((leadResponse: any) => {
          this.isSaveLoading = false;
          if (leadResponse !== null) {
            let response: any = leadResponse.comprehensiveLeadDTO;

            this.isEnableAddRPITLF = true;
            //flags off
            this.isLeadChanged = false;
            this.isSaveLeadFirstTime = false;

            data.compPartyId = response.parties[0].compPartyId
              ? response.parties[0].compPartyId
              : null;
            data.compLeadId = response.compLeadId ? response.compLeadId : null;

            this.leadId = response.leadId ? response.leadId : null;
            this.compLeadId = response.compLeadId ? response.compLeadId : null;

            this.selectedLeadID = this.urlEncodeService.encode(this.leadId);

            if (
              response.parties[0] &&
              response.parties[0] !== null &&
              response.parties[0] !== undefined
            ) {
              if (
                response.parties[0].creationType ===
                this.leadComprehensiveTypeConst.PERSONAL &&
                leadPurpose === Constants.leadPuposeConst.INDIVIDUAL
              ) {
                this.addRelatedPartyFirst({
                  compLeadId: response.compLeadId ? response.compLeadId : null,
                  compPartyId: response.parties[0].compPartyId
                    ? response.parties[0].compPartyId
                    : null,
                });
              } else {
                this.addIncomeTypeFirst({
                  compLeadId: response.compLeadId ? response.compLeadId : null,
                  compPartyId: response.parties[0].compPartyId
                    ? response.parties[0].compPartyId
                    : null,
                });
              }
            }
            this.addPartyIdNameList(
              data.compPartyId,
              data.personalName,
              data.creationType,
              this.leadPurpose,
            );
            this.getLeadDataById(this.leadId);
          }
        });
    }
  }

  getLeadCreationType(leadPurpose: string) {
    if (
      ["SOUL_PROPRITER", "PARTNERSHIP", "LIMITED_LIABILITY"].includes(
        leadPurpose,
      )
    ) {
      return Constants.leadCreationType.BUSINESS;
    }
    return Constants.leadCreationType.PERSONAL;
  }

  addRelatedPartyFirst(data: any) {
    //check related partties exist
    let index = this.relatedPartiesList.findIndex(
      (rec) => rec.mainParty === data.compPartyId,
    );
    // not found
    if (index === -1) {
      let sendObj: LeadCompPartiesDTO = {
        compLeadId: data.compLeadId,
        mainParty: data.compPartyId,
        relatedParty: null,
        relationshipDescription: "Individual",
        share: null,
        noRelationship: false,
      };
      let saveObj: RelatedParty = {
        relatedPartyId: data.relatedPartyId,
        compLeadId: data.compLeadId,
        mainPartnerId: data.compPartyId,
        relatedPartnerId: null,
        relationshipDescription: "Individual",
        share: null,
        considerCrib: "N",
        considerAdvanceAnalysis: "N",
      };
      this.isSaveLoading = true;
      this.leadComprehensiveService
        .saveRelatedPartiesLead(data.compLeadId, saveObj)
        .then((response: any) => {
          this.isSaveLoading = false;
          if (response) {
            //get related partner id
            this.relatedPartiesList.push(sendObj);
            this.cdr.detectChanges();
          }
        });
    }
  }

  addIncomeTypeFirst(data: any) { }

  addPartyIdNameList(
    compPartyId: number,
    personalName: string,
    creationType: string,
    leadPurpose: string,
  ) {
    let index = this.partyIdNameList.findIndex(
      (rec) => rec.value === compPartyId,
    );
    if (index === -1) {
      this.partyIdNameList.push({
        value: compPartyId,
        label: personalName,
        creationType: creationType,
        leadPurpose: leadPurpose,
      });
    } else {
      this.partyIdNameList[index] = {
        value: compPartyId,
        label: personalName,
        creationType: creationType,
        leadPurpose: this.leadPurpose,
      };
    }
  }

  deleteBorrowerObj(index: number) {
    let borrower: LeadCompBorrowerDTO = this.borrowerList.find(
      (d: any, i: number) => i == index,
    );
    let mainPartner = this.relatedPartiesList.find(
      (rec: LeadCompPartiesDTO) => rec.mainParty === borrower.compPartyId,
    );
    let rPartner = this.relatedPartiesList.find(
      (rec: LeadCompPartiesDTO) => rec.relatedParty === borrower.compPartyId,
    );
    if (
      (mainPartner !== undefined && mainPartner !== null) ||
      (rPartner !== undefined && rPartner !== null)
    ) {
      this.alertService.showToaster(
        "Delete the related parties related to this party and try again.",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (this.borrowerList.length > 1) {
      if (borrower !== undefined && borrower !== null && borrower.compPartyId) {
        this.handleDeleteBorrower(borrower, index);
      } else {
        this.borrowerList.splice(index, 1);
        const newIndex = index > 0 ? index - 1 : 0;
        this.setTabIndex(newIndex);
      }
    }
  }

  handleDeleteBorrower(borrower: LeadCompBorrowerDTO, index: number) {
    this.deleteModalRef = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Party deactivate",
          message: "Do you want to deactivate this record ?",
        },
      },
    );
    this.deleteModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (borrower !== undefined && borrower !== null) {
          this.leadComprehensiveService
            .deactivateParty(borrower)
            .then((response: any) => {
              if (response) {
                this.borrowerList.splice(index, 1);
                const newIndex = index > 0 ? index - 1 : 0;
                this.setTabIndex(newIndex);
                this.partyIdNameList.splice(index, 1);
                this.borrowers = this.borrowers.filter(
                  (data: BorrowerCrib) =>
                    data.personalName !== borrower.personalName,
                );
              }
            });
        }
      }
    });
  }

  getCurrencyFormat(amount: any) {
    return this.currencyPipe.transform(amount, "", "");
  }

  getBrancCode(branchName: string) {
    const item =
      this.allBranches &&
      this.allBranches !== undefined &&
      this.allBranches !== null &&
      this.allBranches.find((option) => option.branchName === branchName);
    return item ? item.branchCode : null;
  }

  getMainPartyName(value: number) {
    const item = this.partyIdNameList.find((option) => option.value === value);
    return item ? item.label : null;
  }

  getRelatedPartyName(value: number) {
    const item = this.partyIdNameList.find((option) => option.value === value);
    return item ? item.label : null;
  }

  getSupportingDocLabel(supportingDocID: number) {
    const item = this.supportingDocDropdown.find(
      (option) => option.supportingDocID === supportingDocID,
    );
    return item ? item.documentName : null;
  }

  deleteRelatedParty(data: LeadCompPartiesDTO) {
    this.deleteModalRef = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Related Party deactivate",
          message: "Do you want to deactivate this record ?",
        },
      },
    );
    this.deleteModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.deleteRelatedPartyService(data);
      }
    });
  }

  deleteRelatedPartyService(data: LeadCompPartiesDTO) {
    if (this.relatedPartiesList.length > 0) {
      const index: number = this.relatedPartiesList.findIndex(
        (rec: LeadCompPartiesDTO) => rec.relatedPartyId === data.relatedPartyId,
      );
      if (index !== -1) {
        this.leadComprehensiveService
          .deactivateRelatedParty(data)
          .then((response: any) => {
            if (response) {
              this.relatedPartiesList.splice(index, 1);
            }
          });
      }
    }
  }

  deleteIncomeType(data: LeadCompIncomeTypeDTO) {
    this.deleteModalRef = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Income Type deactivate",
          message: "Do you want to deactivate this record ?",
        },
      },
    );
    this.deleteModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (this.incomeSourceList.length > 0) {
          const index: number = this.incomeSourceList.findIndex(
            (rec: LeadCompIncomeTypeDTO) =>
              rec.incomeSourceId === data.incomeSourceId,
          );
          if (index !== -1) {
            this.leadComprehensiveService
              .deactivateIncomeSource(data)
              .then((response: any) => {
                if (response) {
                  this.incomeSourceList.splice(index, 1);
                }
              });
          }
        }
      }
    });
  }

  deleteFacility(data: LeadCompFacilityDTO) {
    this.deleteModalRef = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Facility deactivate",
          message: "Do you want to deactivate this record ?",
        },
      },
    );
    this.deleteModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (this.facilitiesList.length > 0) {
          const index: number = this.facilitiesList.findIndex(
            (rec: LeadCompFacilityDTO) =>
              rec.compFacilityId == data.compFacilityId,
          );
          if (index !== -1) {
            this.leadComprehensiveService
              .deactivateFacility(data)
              .then((response: any) => {
                if (response) {
                  this.facilitiesList.splice(index, 1);
                }
              });
          }
        }
      }
    });
  }

  openDeleteConformDialog(heading: string) {
    let status = false;
    this.deleteModalRef = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: heading + " deactivate",
          message: "Do you want to deactivate this record ?",
        },
      },
    );
    this.deleteModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        status = true;
      }
    });
    return status;
  }

  isBranchManager() {
    return (
      this.leadData !== null &&
      this.leadData.leadId !== null &&
      this.leadData.leadId !== 0 &&
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) === 50
    );
  }

  isEmptyFacilities() {
    return this.facilitiesList && this.facilitiesList.length === 0;
  }

  relatedPartyCheck() {
    const isValid = this.borrowerList.every((req) =>
      this.relatedPartiesList.some(
        (obj) =>
          this.getMainPartyName(obj.mainParty) === req.personalName ||
          this.getRelatedPartyName(obj.relatedParty) === req.personalName,
      ),
    );
    return isValid;
  }

  checkRequiredDocs() {
    const isvalid = this.requiredDocArray.every((req) =>
      this.documentList.some(
        (obj) => this.getSupportingDocLabel(obj.supportingDocId) === req,
      ),
    );

    return isvalid;
  }

  submitLead() {
    if (this.validateBorrowersForSubmit()) {
      return;
    }

    if (this.isEmptyFacilities()) {
      this.alertService.showToaster(
        "Facilities should not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (!this.relatedPartyCheck()) {
      this.alertService.showToaster(
        "All Parties should have relationship!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: false,
      data: {
        heading: "Submit Comprehensive Lead",
        message:
          "Do you want to submit the comprehensive lead to your manager?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let data = {
          leadID: this.leadId,
          leadStatus: this.leadStatusConst.SUBMITTED,
          assignUserID: "",
          assignUserDisplayName: "",
          actionedByDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          action: this.leadActionConst.SUBMIT,
        };
        this.leadAddEditService.updateLeadStatusOrAssignee(data);
        this.router.navigate(["/leads/dashboard"]);
      }
    });
  }

  acceptLead() {
    let identificationNumber = this.borrowerList[0].identifications[0]
      .identificationNumber
      ? this.borrowerList[0].identifications[0].identificationNumber
      : null;
    let identificationType = this.borrowerList[0].identifications[0]
      .identificationType
      ? this.borrowerList[0].identifications[0].identificationType
      : null;

    if (identificationNumber === null || identificationType === null) {
      this.alertService.showToaster(
        "Facilities should not be empty!.",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    let decision: AnalyticsDecision =
      this.leadData !== null ? this.leadData.analyticsDecision : null;
    if (decision !== null && decision.decision !== null) {
      this.showCreateApplicationFormModal(
        identificationNumber,
        identificationType,
      );
    } else {
      this.hanldeConfirmAccept(identificationNumber, identificationType);
    }
  }

  hanldeConfirmAccept(identificationNumber: any, identificationType: any) {
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
        heading: "Comprehensive Lead Confirmation",
        message:
          "Do you want proceed this comprehensive lead without CRIB pull and advance analytics decision?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (isYes) {
        this.showCreateApplicationFormModal(
          identificationNumber,
          identificationType,
        );
      }
    });
  }

  showCreateApplicationFormModal(
    identificationNumber: string,
    identificationType: string,
  ) {
    this.modalRef = this.mdbModalService.show(LeadCreateApplicationForm, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-dialog-scrollable",
      containerClass: "",
      animated: true,
      data: {
        leadID: this.leadId,
        identificationNumber: identificationNumber,
        identificationType: identificationType,
        leadFsType: "LEASE",
        isCompLead: true,
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.leadAddEditService
          .draftApplicationForm(data)
          .subscribe((res: any) => {
            if (res.applicationFormID) {
              let updateData = Object.assign(
                {},
                { leadID: this.leadId },
                { applicationFormID: res.applicationFormID },
                { leadStatus: this.leadStatusConst.APPLICATION_CREATED },
                { assignUserID: "" },
                {
                  actionedByDisplayName:
                    this.applicationService.getLoggedInUserDisplayName(),
                },
                { action: this.leadActionConst.APPLICATION_CREATED },
                { remark: data.remark },
              );
              this.leadAddEditService.updateLeadStatusOrAssignee(updateData);

              this.selectedApplicationFormID = this.urlEncodeService.encode(
                res.applicationFormID,
              );
              this.router.navigate(["/application-form/add-edit"]);
            }
          });
      }
    });
  }

  getDigitalApplications() {
    this.leadComprehensiveService
      .getDigitalApplications(this.leadId)
      .then((res: any[]) => {
        if (Array.isArray(res)) {
          this.digitalApplications = [...res];
        }
      });
  }

  isAADecisionEnabled() {
    return this.leadData !== null;
  }

  isAllowedUser() {
    let upmGroupCode: number =
      this.applicationService.getLoggedInUserUPMGroupCode() !== null
        ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
        : 0;
    if (upmGroupCode === 10 || upmGroupCode === SETTINGS.SALES_PERSON_WC) {
      return (
        this.applicationService.getLoggedInUserUserName() ===
        this.leadData.createdBy ||
        this.applicationService.getLoggedInUserUserName() ===
        this.leadData.assignUserId
      );
    }
    return (
      this.applicationService.getLoggedInUserUserName() ===
      this.leadData.createdBy
    );
  }

  onDigitalApplicationSaved(saved: any) {
    if (!saved || !saved.digitalApplicationID) return;
    this.digitalApplications = [saved, ...this.digitalApplications];
  }

  openAddCommentModal(comment?: any) {
    this.modalRef = this.mdbModalService.show(AddCommentModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-50-p modal-dialog-scrollable",
      containerClass: "modal modal-blur-effect",
      animated: false,
      data: {
        heading: "Add Comprehensive Lead Comment",
        comment:
          comment !== undefined &&
            comment !== null &&
            comment.comment !== null &&
            comment.comment !== ""
            ? comment
            : "",
      },
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let request: any = {
          commentID: comment ? comment.commentID : null,
          leadId: this.leadId,
          userComment: data,
          actionMessage: "Comment on this Lead",
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          isUsersOnly: Constants.yesNoConst.N,
          isDivisionOnly: Constants.yesNoConst.N,
          isPublic: Constants.yesNoConst.N,
          status: Constants.statusConst.ACT,
          // currentLeadStatus: this.leadUpdateDTO.leadStatus,
        };
        this.leadComprehensiveService
          .saveComprehensiveLeadComment(request)
          .then((res: any) => {
            if (res !== null) {
              this.comments.push(res);
              this.comments = this.comments.map((c: any) => ({ ...c }));
            }
          });
      }
    });
  }

  openAddDocumentModal() {
    this.addDocumentModalRef = this.mdbModalService.show(
      LeadComprehensiveDocumentComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-50-p modal-dialog-scrollable",
        containerClass: "modal modal-blur-effect",
        animated: false,
        data: {
          heading: "Add Comprehensive Lead Document",
          content: {
            leadId: this.leadId,
            supportingDocDropdown: this.supportingDocDropdown,
          },
        },
      },
    );
    this.addDocumentModalRef.content.action.subscribe((data: any) => {
      if (data !== null) {
        let activeList: any[] = data.filter(
          (d: any) => d.status === Constants.statusConst.ACT,
        );
        this.mapLeadDocuments(activeList, false);
      }
    });
  }

  editDocument(data: any) {
    this.addDocumentModalRef = this.mdbModalService.show(
      LeadComprehensiveDocumentComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-50-p modal-dialog-scrollable",
        containerClass: "modal modal-blur-effect",
        animated: false,
        data: {
          heading: "Edit Comprehensive Lead Document",
          content: {
            leadId: this.leadId,
            addDocumentModalRef: this.addDocumentModalRef,
            supportingDocDropdown: this.supportingDocDropdown,
            compLeadId: this.compLeadId,
            partyIdNameList: this.partyIdNameList,
            isEdit: true,
            editObj: data,
          },
        },
      },
    );
    this.addDocumentModalRef.content.action.subscribe((data: any) => {
      if (data !== null) {
        let activeList: any[] = data.filter(
          (d: any) => d.status === Constants.statusConst.ACT,
        );
        this.mapLeadDocuments(activeList, false);
      }
    });
  }

  deleteDocument(data: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: false,
      data: {
        heading: "Comprehensive Lead Document Deletion",
        message: "Do you want to delete this document ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.leadComprehensiveService
          .deleteLeadDocument(data.leadDocumentId)
          .then((data: any[]) => {
            if (data !== null) {
              this.mapLeadDocuments(data, true);
            }
          });
      }
    });
  }

  mapLeadDocuments(data: any[], isNewService: boolean) {
    if (data === undefined) {
      this.documentList = []
    }
    if (isNewService) {
      this.documentList = data.map((d: any) => ({
        leadDocumentId: d.leadDocumentId,
        supportingDocId: d.supportingDocId,
        remark: d.remark,
        status: d.status,
        createdBy: d.createdBy,
        modifiedBy: d.modifiedBy,
        docStorageDTO: d.docStorageDTO,
      }));
      this.cdr.detectChanges();
    } else {
      this.documentList = data.map((d: any) => ({
        leadDocumentId: d.leadDocumentID,
        supportingDocId:
          d.supportingDocDTO !== null ? d.supportingDocDTO.supportingDocID : 0,
        remark: d.remark,
        status: d.status,
        createdBy: d.createdBy,
        modifiedBy: d.modifiedBy,
        docStorageDTO: d.docStorageDTO,
      }));
      this.cdr.detectChanges();
    }
  }

  viewDocument(data: any) {
    if (
      data !== null &&
      data.docStorageDTO !== null &&
      data.docStorageDTO.docStorageID != null
    ) {
      this.leadAddEditService
        .downloadLeadDocument(data.docStorageDTO)
        .then((file: any) => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(file);
          downloadLink.download = data.docStorageDTO.fileName;
          downloadLink.click();
        });
    } else {
      this.alertService.showToaster(
        "Failed to view document.",
        SETTINGS.TOASTER_MESSAGES.error,
      );
    }
  }

  returnLead() {
    this.modalRef = this.mdbModalService.show(ExternalLeadReturnComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-45-p modal-margin-center ",
      containerClass: "",
      animated: false,
      data: {
        leadID: this.leadData.leadId,
        createdBy: this.leadData.createdBy,
        createdUserDisplayName: this.leadData.createdUserDisplayName,
        createdFromExternal:
          this.leadData.createdUserWorkClass &&
          parseInt(this.leadData.createdUserWorkClass) ===
          SETTINGS.SALES_PERSON_WC,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.router.navigate(["/leads/dashboard"]);
      }
    });
  }

  declineLead() {
    this.modalRef = this.mdbModalService.show(ExternalLeadDeclineComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-45-p modal-margin-center ",
      containerClass: "",
      animated: false,
      data: {
        leadID: this.leadData.leadId,
        createdBy: this.leadData.createdBy,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.router.navigate(["/leads/dashboard"]);
      }
    });
  }

  showDigitaleApplication() {
    let decision: AnalyticsDecision =
      this.leadData !== null ? this.leadData.analyticsDecision : null;
    return (
      decision !== null &&
      decision.decisionStatus &&
      decision.decisionStatus === SDConstants.analyticsDecision.APPROVED
    );
  }

  generateInPrincipleLetter(facility: LeadCompFacilityDTO) {
    let decision: AnalyticsDecision =
      this.leadData !== null ? this.leadData.analyticsDecision : null;

    let customerDetails: any[] = this.getCustomerDetailsByPurpose();

    let request: any = {
      compFacilityId: facility.compFacilityId,
      leadRefNo: this.leadData.leadRefNumber,
      inPrincipalPartDetails: customerDetails,
      decisionDate:
        decision.createdDate !== null
          ? moment(decision.createdDate).format("Do MMMM YYYY")
          : "",
    };

    if (customerDetails.length > 0) {
      this.leadAddEditService.getInprincipalLetter(request).then((res: any) => {
        if (res !== null && res !== "") {
          this.previewDocument(res);
        }
      });
    } else {
      this.alertService.showToaster(
        "Failed to generate In-Principale Letter.",
        SETTINGS.TOASTER_MESSAGES.error,
      );
    }
  }

  getCustomerDetailsByPurpose() {
    let parties: any[] =
      this.leadData.parties && this.leadData.parties.length > 0
        ? this.leadData.parties.map((d: LeadCompBorrowerDTO) => ({
          ...d,
          title: d.title ? this.toProperCase(d.title) + "." : "",
          personalName: this.toProperCase(d.personalName),
        }))
        : [];
    let result: any[] = [];
    let leadPurpose: string = this.leadPurpose;

    switch (leadPurpose) {
      case Constants.leadPuposeConst.INDIVIDUAL:
        let personalBorrower: LeadCompBorrowerDTO =
          parties.length > 0 ? parties[0] : null;

        result = this.handlePushData(personalBorrower);
        break;
      case Constants.leadPuposeConst.INDIVIDUAL_JOINT:
        let filterData: any[] = parties.filter((d: any, i: number) => i < 1);
        result = filterData.map((d: LeadCompBorrowerDTO) => ({
          title: d.title,
          customerName: d.personalName,
          ...this.prepareAddressObj(d),
        }));
        break;
      case Constants.leadPuposeConst.SOUL_PROPRITER:
        let solPropBorrower: LeadCompBorrowerDTO =
          parties.length > 0
            ? parties.find(
              (d: LeadCompBorrowerDTO) =>
                d.partyType === Constants.leadCompBorrowerTypeConst.BORROWER,
            )
            : null;
        if (solPropBorrower !== undefined && solPropBorrower !== null) {
          result = this.handlePushData(solPropBorrower);
        }

        break;
      case Constants.leadPuposeConst.PARTNERSHIP:
        let partnershipBorrower: LeadCompBorrowerDTO | any = null;
        if (
          parties.some(
            (d: LeadCompBorrowerDTO) =>
              d.creationType === Constants.leadCreationTypeConst.BUSINESS &&
              d.partyType === Constants.leadCompBorrowerTypeConst.BORROWER,
          )
        ) {
          partnershipBorrower =
            parties.length > 0
              ? parties.find(
                (d: LeadCompBorrowerDTO) =>
                  d.creationType ===
                  Constants.leadCreationTypeConst.BUSINESS &&
                  d.partyType ===
                  Constants.leadCompBorrowerTypeConst.BORROWER,
              )
              : null;
        } else {
          partnershipBorrower =
            parties.length > 0
              ? parties.find(
                (d: LeadCompBorrowerDTO) =>
                  d.creationType ===
                  Constants.leadCreationTypeConst.PERSONAL &&
                  d.partyType ===
                  Constants.leadCompBorrowerTypeConst.BORROWER,
              )
              : null;
        }
        if (partnershipBorrower !== undefined && partnershipBorrower !== null) {
          result = this.handlePushData(partnershipBorrower);
        }
        break;
      case Constants.leadPuposeConst.LIMITED_LIABILITY:
        let llcBorrower: LeadCompBorrowerDTO =
          parties.length > 0
            ? parties.find(
              (d: LeadCompBorrowerDTO) =>
                d.creationType === Constants.leadCreationTypeConst.BUSINESS &&
                d.partyType === Constants.leadCompBorrowerTypeConst.BORROWER,
            )
            : null;
        if (llcBorrower !== undefined && llcBorrower !== null) {
          result = this.handlePushData(llcBorrower);
        }

        break;
    }

    return result;
  }

  handlePushData(borrower: any) {
    let result: any[] = [];
    if (borrower !== null) {
      result.push({
        title: borrower.title,
        customerName: borrower.personalName,
        ...this.prepareAddressObj(borrower),
      });
    }

    return result;
  }

  prepareAddressObj(compBorrowerDTO: LeadCompBorrowerDTO) {
    let address: Address | any =
      compBorrowerDTO !== null &&
        compBorrowerDTO.addresses !== null &&
        compBorrowerDTO.addresses.length > 0
        ? compBorrowerDTO.addresses[0]
        : null;

    return {
      address1: this.toProperCase(address.address1),
      address2: this.toProperCase(address.address2),
      city: this.toProperCase(address.city),
    };
  }

  previewDocument(content: string) {
    let printContents, popupWin;
    let documentName: string = "In-Principle Letter";
    printContents = content;
    popupWin = window.open("", "_blank", "top=0,left=0,height=80%,width=auto");
    popupWin.document.open();

    popupWin.document.write(`
                <html>
                  <head>
                    <title>${documentName}</title>
                  </head>
                  <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                </html>`);

    popupWin.document.close();
  }

  handleDecision(data: any) {
    this.leadData = {
      ...this.leadData,
      analyticsDecision: data,
    };
  }

  isLeadCommonActionEnabled() {
    if (this.leadData !== null && this.leadStatus !== null) {
      let upmGroupCode: number =
        this.applicationService.getLoggedInUserUPMGroupCode() !== null
          ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
          : 0;
      if (upmGroupCode === 10 || upmGroupCode === SETTINGS.SALES_PERSON_WC) {
        return (
          [
            Constants.leadStatusConst.PENDING,
            Constants.leadStatusConst.RETURNED,
          ].includes(this.leadStatus.status) &&
          (this.applicationService.getLoggedInUserUserName() ===
            this.leadData.createdBy ||
            this.applicationService.getLoggedInUserUserName() ===
            this.leadData.assignUserId)
        );
      }

      if (upmGroupCode === 50) {
        return (
          this.applicationService.getLoggedInUserUserName() !==
          this.leadData.createdBy &&
          this.applicationService.getLoggedInUserDivCode() ===
          this.leadData.branchCode &&
          [Constants.leadStatusConst.SUBMITTED].includes(this.leadStatus.status)
        );
      }
    }

    return false;
  }

  isDocEditDeleteEnabled(doc: any) {
    return (
      this.isLeadCommonActionEnabled() &&
      doc.createdBy === this.applicationService.getLoggedInUserUserName()
    );
  }

  isPrincipleLetterEnabled() {
    let decision: AnalyticsDecision =
      this.leadData !== null ? this.leadData.analyticsDecision : null;
    let date: any =
      decision !== null && decision.createdDate !== null
        ? moment(decision.createdDate)
        : null;

    if (
      date !== null &&
      decision.decisionStatus &&
      decision.decisionStatus === SDConstants.analyticsDecision.APPROVED
    ) {
      const differenceInDays = moment().diff(date, "days");

      return this.leadStatus.status !== Constants.leadStatusConst.PENDING && differenceInDays <= 7;
    }
    return false;
  }

  isFormEditEnabled() {
    if (this.leadData !== null && this.leadStatus !== null) {
      return (
        [
          Constants.leadStatusConst.PENDING,
          Constants.leadStatusConst.RETURNED,
        ].includes(this.leadStatus.status) &&
        (this.applicationService.getLoggedInUserUserName() ===
          this.leadData.createdBy ||
          this.applicationService.getLoggedInUserUserName() ===
          this.leadData.assignUserId)
      );
    }

    return true;
  }

  isBranchCanEdit() {
    if (this.leadData !== null && this.leadStatus !== null) {
      return (
        this.applicationService.getLoggedInUserUserName() ===
        this.leadData.createdBy &&
        this.leadStatus.status === Constants.leadStatusConst.PENDING
      );
    }
    return true;
  }

  getRelationShipLabel(value: string): string {
    const item = Constants.allRelationShipElements.find(
      (option) => option.value === value,
    );
    return item ? item.label : null;
  }

  isLeadEditEnabled() {
    return this.leadData !== null && this.leadStatus !== null;
  }

  isDecisionExist() {
    let decision: AnalyticsDecision =
      this.leadData !== null ? this.leadData.analyticsDecision : null;

    return decision !== null && decision.decision !== null;
  }

  getLeadAFAndFPHistory(leadId: number) {
    this.leadComprehensiveService
      .getLeadStatusHistory(leadId)
      .then((res: any[]) => {
        if (res !== null && res.length > 0) {
          let historyList: any[] = res.map((d: any) => ({
            ...d,
            updateDate: d.updateDate
              ? moment(d.updateDate).format("Do MMMM YYYY HH:mm:ss")
              : "-",
          }));
          this.afStatusHistory = historyList.filter(
            (d: any) => d.sourceTable === "AF",
          );
          this.fpStatusHistory = historyList.filter(
            (d: any) => d.sourceTable === "FP",
          );
        }
      });
  }

  isAddBorrowerEnabled() {
    return (
      this.leadPurpose &&
      this.leadPurpose !== Constants.leadPuposeConst.INDIVIDUAL &&
      this.isFormEditEnabled()
    );
  }

  validateBorrowersByPurpose() {
    let leadPurpose: string = this.leadPurpose;
    let personalCount: number = this.borrowerList.filter(
      (b: LeadCompBorrowerDTO) =>
        b.creationType === this.leadComprehensiveTypeConst.PERSONAL,
    ).length;

    let businessCount: number = this.borrowerList.filter(
      (b: LeadCompBorrowerDTO) =>
        b.creationType === this.leadComprehensiveTypeConst.BUSINESS,
    ).length;

    switch (leadPurpose) {
      case Constants.leadPuposeConst.INDIVIDUAL:
        if (businessCount > 0) {
          this.alertService.showToaster(
            "Bussiness parties are not allowed for (Individual).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        } else if (personalCount > 1) {
          this.alertService.showToaster(
            "Party limit reached. Only 1 personal borrower is allowed for Individual lead.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      case Constants.leadPuposeConst.INDIVIDUAL_JOINT:
        if (businessCount > 0) {
          this.alertService.showToaster(
            "Bussiness parties are not allowed for (Individual Joint).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        } else if (personalCount > 2) {
          this.alertService.showToaster(
            "Party limit reached. Only 2 personal parties are allowed for Individual Joint lead.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      case Constants.leadPuposeConst.SOUL_PROPRITER:
        if (personalCount > 1 && businessCount < 1) {
          this.alertService.showToaster(
            "Party limit reached. Maximum allowed: 1 personal borrower and 1 business borrower (Sole Proprietorship).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      case Constants.leadPuposeConst.PARTNERSHIP:
        if (personalCount > 3 && businessCount < 1) {
          this.alertService.showToaster(
            "Party limit reached. Maximum allowed: 3 personal parties and 1 business borrower (Partnership).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      case Constants.leadPuposeConst.LIMITED_LIABILITY:
        if (personalCount > 3 && businessCount < 1) {
          this.alertService.showToaster(
            "Party limit reached. Maximum allowed: 3 personal parties and 1 business borrower (Limited Liability).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      default:
        return false;
    }
  }

  validateBorrowersForSubmit() {
    let leadPurpose: string = this.leadPurpose;
    let personalCount: number = this.borrowerList.filter(
      (b: LeadCompBorrowerDTO) =>
        b.creationType === this.leadComprehensiveTypeConst.PERSONAL,
    ).length;

    let businessCount: number = this.borrowerList.filter(
      (b: LeadCompBorrowerDTO) =>
        b.creationType === this.leadComprehensiveTypeConst.BUSINESS,
    ).length;

    switch (leadPurpose) {
      case Constants.leadPuposeConst.INDIVIDUAL:
        if (personalCount !== 1 || businessCount !== 0) {
          this.alertService.showToaster(
            "Only 1 personal borrower is required for Individual lead.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      case Constants.leadPuposeConst.INDIVIDUAL_JOINT:
        if (personalCount !== 2 || businessCount !== 0) {
          this.alertService.showToaster(
            "Only 2 personal parties are required for Individual Joint lead.",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      case Constants.leadPuposeConst.SOUL_PROPRITER:
        if (personalCount !== 1 || businessCount !== 1) {
          this.alertService.showToaster(
            "Only 1 personal and 1 business parties are required for (Sole Proprietorship).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      // case Constants.leadPuposeConst.PARTNERSHIP:
      //   if ((personalCount >= 2 && personalCount <= 3) || businessCount !== 1) {
      //     this.alertService.showToaster(
      //       "Only 2 or 3 personal and 1 business parties are required for (Partnership).",
      //       SETTINGS.TOASTER_MESSAGES.warning,
      //     );
      //     return true;
      //   }
      //   return false;
      case Constants.leadPuposeConst.PARTNERSHIP:
      case Constants.leadPuposeConst.LIMITED_LIABILITY:
        if (personalCount === 0 || personalCount > 3 || businessCount !== 1) {
          this.alertService.showToaster(
            "Minimum 1 and Maximum 3 personal and 1 business parties are required for (Limited Liability).",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
          return true;
        }
        return false;
      default:
        return false;
    }
  }

  getLeadPurpose() {
    return this.leadPurpose;
  }

  updateLeadPurpose(value: string) {
    if (value) {
      this.leadPurpose = value;
      this.leadComprehensiveService.onLeadPurposeChange.next(this.leadPurpose);
    } else {
      this.leadPurpose = "";
      this.leadComprehensiveService.onLeadPurposeChange.next(this.leadPurpose);
    }
  }

  toProperCase(value: string): string {
    if (!value) return "";

    return value
      .toLowerCase()
      .split(" ")
      .filter((v) => v.trim().length > 0)
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
      .join(" ");
  }

  isStructured(facility: LeadCompFacilityDTO) {
    return facility.repaymentMode === "Structured";
  }

  isDirectorBlock(relationship: string) {
    return relationship === "LIMITED_LIABILITY" || relationship === "Director"
  }
}
