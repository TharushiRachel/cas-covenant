import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from "@angular/forms";
import { IMyDate, IMyOptions } from "ng-uikit-pro-standard";
import { Constants } from "src/app/core/setting/constants";
import {
  AnalyticsDecision,
  LeadCompBorrowerDTO,
} from "../../../interfaces/Lead-comp-borrower-dto";
import * as _ from "lodash";
import { Subject, Subscription } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { ApplicationService } from "src/app/core/service/application/application.service";
import * as moment from "moment";
import { LeadCompPartiesDTO } from "../../../interfaces/Lead-comp-parties-dto";
import { SDConstants } from "../../../interfaces/utils";
import { LeadComprehensiveService } from "../../../services/lead-comprehensive.service";

@Component({
  selector: "app-lead-comprehensive-borrower",
  templateUrl: "./lead-comprehensive-borrower.component.html",
  styleUrls: ["./lead-comprehensive-borrower.component.scss"],
})
export class LeadComprehensiveBorrowerComponent implements OnInit, OnDestroy {
  @Input() leadData: any = null;
  @Input() leadStatus: any = null;

  leadCompBorrowerDTO: LeadCompBorrowerDTO;
  leadFsTypeConst: any = Constants.leadFsTypeConst;

  identificationType: boolean = false;
  addBorrwerForm: FormGroup;
  leadComprehensiveTypeConst: any = Constants.leadCompCreationTypeTypeConst;
  leadCompBorrowerTypeConst: any = Constants.leadCompBorrowerTypeConst;
  branches: string[] = ["Branch A", "Branch B", "Branch C"];
  identificationNumberLabel: string = "Identification Number";
  nameLabel = "Personal Name";
  results: Subject<any>;

  @Input("relatedPartiesList") relatedPartiesList: LeadCompPartiesDTO[] = [];

  @Output("saveBorrowerObj") saveBorrowerObj =
    new EventEmitter<LeadCompBorrowerDTO>();
  @Output("deleteBorrowerObj") deleteBorrowerObj = new EventEmitter<number>();

  @Input("borrwerData") initBorrwerData: LeadCompBorrowerDTO;

  @Input("isFormDisabled") isFormDisabled: boolean = false;
  @Input("isSaveLoading") isSaveLoading: boolean = false;

  @Input("currentIndex") currentIndex: number;
  @Input("selectedTabIndex") selectedTabIndex: number;

  @Input("borrowerList") borrowerList: LeadCompBorrowerDTO[];

  @Input("isEdit") isEdit: boolean = false;
  leadPurpose: string = "";
  optionsCivilStatusSelect: any[] = Constants.optionsCivilStatusSelectOpt;
  genderSelect: any[] = Constants.genderSelect;

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear() - 18,
    month: new Date().getMonth() + 1,
    day: new Date().getDate(),
  };
  disableUntilDate: IMyDate = {
    year: new Date().getFullYear() - 60,
    month: new Date().getMonth() + 1,
    day: new Date().getDate(),
  };
  myDatePickerOptionsForBirthDay: IMyOptions = {
    dateFormat: "yyyy-mm-dd",
    minYear: new Date().getFullYear() - 138,
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
    disableSince: this.disableSinceDate,
    disableUntil: this.disableUntilDate,
  };

  identityOptionSelect: any[] = Constants.leadCompPersoanlIdSelect;
  staticIdentityOption: any[] = Constants.leadCompPersoanlIdSelect;

  leadCompConst: any = Constants.leadCompConst;
  compPartyId: number = 0;

  formErrors: any;
  identifications: any[] = [];
  isBusiness: boolean = false;

  isBussinesBorrowerExist: boolean = false;

  titleConstList: any[] = SDConstants.titleConstList;
  onLeadPurposeChangeSub: Subscription = new Subscription();

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly alertService: AlertService,
    private readonly applicationService: ApplicationService,
    private readonly leadComprehensiveService: LeadComprehensiveService,
  ) { }

  ngOnInit() {
    if (this.borrowerList.length > 0) {
      this.isBussinesBorrowerExist = this.borrowerList.some(
        (b: LeadCompBorrowerDTO) =>
          b.creationType === Constants.leadCompCreationTypeTypeConst.BUSINESS,
      );
    }

    this.formErrors = {
      creationType: {},
      partyType: {},
      title: {},
      personalName: {},
      address1: {},
      city: {},
      accountNumber: {},
      dateOfBirth: {},
      designation: {},
      email: {},
      mobileNumber: {},
      gender: {},
      civilStatus: {},
      typeOfBusiness: {},
      contactPerson: {},
    };

    this.addBorrwerForm = this.loadAddBorrwerForm();

    this.addBorrwerForm.reset({
      creationType: this.initBorrwerData.creationType,
    });

    this.addBorrwerForm
      .get("identificationType")
      .valueChanges.subscribe((value: any) => {
        this.addBorrwerForm.controls.identificationNumber.setValue("");
        const idNumberControl =
          this.addBorrwerForm.controls["identificationNumber"];
        idNumberControl.clearValidators();
        const dateOfBirth = this.addBorrwerForm.get("dateOfBirth").value;
        if (value === this.leadCompConst.OLD_NIC) {
          idNumberControl.setValidators([
            Validators.pattern(/^[0-9]{9}[VX]$/),
            this.nicDobValidator(dateOfBirth, "OLD"),
          ]);
        } else if (value === this.leadCompConst.NEW_NIC) {
          idNumberControl.setValidators([
            Validators.pattern(/^[0-9]{12}$/),
            this.nicDobValidator(dateOfBirth, "NEW"),
          ]);
        } else if (value === this.leadCompConst.BRC) {
          idNumberControl.setValidators([Validators.pattern("")]);
        }
        idNumberControl.updateValueAndValidity();
        idNumberControl.setValue("");
      });

    this.addBorrwerForm
      .get("creationType")
      .valueChanges.subscribe((value: any) => {
        const personalName = this.addBorrwerForm.get("personalName");
        const civilStatus = this.addBorrwerForm.get("civilStatus");
        const gender = this.addBorrwerForm.get("gender");
        const dateOfBirth = this.addBorrwerForm.get("dateOfBirth");
        const typeOfBusiness = this.addBorrwerForm.get("typeOfBusiness");
        const contactPerson = this.addBorrwerForm.get("contactPerson");
        const title = this.addBorrwerForm.get("title");

        if (value == this.leadComprehensiveTypeConst.BUSINESS) {
          this.identityOptionSelect = Constants.leadCompBusinessIdSelect;
          this.staticIdentityOption = Constants.leadCompBusinessIdSelect;

          this.nameLabel = "Business Name";
          this.addBorrwerForm.get("identificationType").setValue("BRC");
          this.isBusiness = true;

          personalName.setValidators([
            Validators.required,
            Validators.pattern(/^[A-Za-z()]+(?: [A-Za-z()]+)*$/),
          ]);

          typeOfBusiness.setValidators([Validators.required]);
          contactPerson.setValidators([Validators.required]);
          civilStatus.clearValidators();
          gender.clearValidators();
          dateOfBirth.clearValidators();
          title.clearValidators();
        } else {
          this.identityOptionSelect = Constants.leadCompPersoanlIdSelect;
          this.staticIdentityOption = Constants.leadCompPersoanlIdSelect;
          this.nameLabel = "Person Name";
          this.addBorrwerForm.get("identificationType").setValue("OLD_NIC");
          this.isBusiness = false;

          personalName.setValidators([
            Validators.required,
            Validators.pattern(/^[A-Za-z]+(?: [A-Za-z]+)*$/),
          ]);
          typeOfBusiness.clearValidators();
          contactPerson.clearValidators();
          civilStatus.setValidators([Validators.required]);
          gender.setValidators([Validators.required]);
          dateOfBirth.setValidators([Validators.required]);
          title.setValidators([Validators.required]);
        }
        personalName.updateValueAndValidity();
        typeOfBusiness.updateValueAndValidity();
        contactPerson.updateValueAndValidity();
        civilStatus.updateValueAndValidity();
        dateOfBirth.updateValueAndValidity();
        gender.updateValueAndValidity();
        title.updateValueAndValidity();

        //this.initBorrwerData.creationType = value;
      });

    this.addBorrwerForm
      .get("dateOfBirth")
      .valueChanges.subscribe((value: any) => {
        this.addBorrwerForm.controls.identificationNumber.setValue("");
        this.maxAgeValidator(value);
        const identificationType =
          this.addBorrwerForm.get("identificationType").value;
        if (
          identificationType &&
          identificationType !== undefined &&
          identificationType !== null &&
          value &&
          value !== undefined &&
          value !== null
        ) {
          if (identificationType === this.leadCompConst.OLD_NIC) {
            this.addBorrwerForm
              .get("identificationNumber")
              .setValidators([
                Validators.pattern(/^[0-9]{9}[VX]$/),
                this.nicDobValidator(value, "OLD"),
              ]);
          } else if (identificationType === this.leadCompConst.NEW_NIC) {
            this.addBorrwerForm
              .get("identificationNumber")
              .setValidators([
                Validators.pattern(/^[0-9]{12}$/),
                this.nicDobValidator(value, "NEW"),
              ]);
          } else if (identificationType === this.leadCompConst.BRC) {
            this.addBorrwerForm
              .get("identificationNumber")
              .setValidators([Validators.pattern("")]);
          }
        }
      });

    this.addBorrwerForm
      .get("partyType")
      .valueChanges.subscribe((value: any) => {
        // this.initBorrwerData.partyType = value;
      });

    let leadCompBorrowerDTO: LeadCompBorrowerDTO = {
      // ids set
      compLeadId: this.initBorrwerData.compLeadId
        ? this.initBorrwerData.compLeadId
        : null,
      compPartyId: this.initBorrwerData.compPartyId
        ? this.initBorrwerData.compPartyId
        : null,

      creationType: this.initBorrwerData.creationType
        ? this.initBorrwerData.creationType
        : "",
      partyType: this.initBorrwerData.partyType
        ? this.initBorrwerData.partyType
        : "",
      accountNumber: this.initBorrwerData.accountNumber
        ? this.initBorrwerData.accountNumber
        : "",
      civilStatus: this.initBorrwerData.civilStatus
        ? this.initBorrwerData.civilStatus
        : "",
      dateOfBirth: this.initBorrwerData.dateOfBirth
        ? this.initBorrwerData.dateOfBirth
        : "",
      title: this.initBorrwerData.title
        ? this.initBorrwerData.title
        : SDConstants.titleConst.MR,
      personalName: this.initBorrwerData.personalName
        ? this.initBorrwerData.personalName
        : "",
      email: this.initBorrwerData.email ? this.initBorrwerData.email : "",
      mobileNumber: this.initBorrwerData.mobileNumber
        ? this.initBorrwerData.mobileNumber
        : "",
      identifications: this.initBorrwerData.identifications,
      address1: this.initBorrwerData.address1 || "",
      address2: this.initBorrwerData.address2 || "",
      city: this.initBorrwerData.city || "",
      gender: this.initBorrwerData.gender ? this.initBorrwerData.gender : "",
      isBusiness: this.initBorrwerData.isBusiness,
      addresses: [],
      contactPerson: this.initBorrwerData.contactPerson || "",
      designation: this.initBorrwerData.designation || "",
      typeOfBusiness: this.initBorrwerData.typeOfBusiness || "",
      isCreationTypeEnable: this.initBorrwerData.isCreationTypeEnable || false,
    };
    this.isBusiness = this.initBorrwerData.isBusiness;
    this.identifications = this.initBorrwerData.identifications;
    this.leadCompBorrowerDTO = leadCompBorrowerDTO;
    this.compPartyId = this.initBorrwerData.compPartyId
      ? this.initBorrwerData.compPartyId
      : 0;

    this.identityOptionSelect = this.staticIdentityOption.filter(
      (item) =>
        !this.initBorrwerData.identifications.some(
          (s2Item) => s2Item.identificationType === item.value,
        ),
    );

    this.onLeadPurposeChangeSub =
      this.leadComprehensiveService.onLeadPurposeChange.subscribe(
        (value: string) => {
          this.leadPurpose = value;
          this.handleLeadPurpsoe(value, leadCompBorrowerDTO);
        },
      );
  }

  ngOnDestroy(): void {
    this.onLeadPurposeChangeSub.unsubscribe();
  }

  handleLeadPurpsoe(purpose: string, leadCompBorrowerDTO: LeadCompBorrowerDTO) {
    const isBorrowerExist: boolean = this.borrowerList.some(
      (b: any) => b.compPartyId && b.partyType === "BORROWER",
    );

    switch (purpose) {
      case Constants.leadPuposeConst.INDIVIDUAL:
        this.addBorrwerForm.get("partyType").disable();
        if (!isBorrowerExist) {
          this.addBorrwerForm.get("creationType").disable();
          leadCompBorrowerDTO = {
            ...leadCompBorrowerDTO,
            partyType: this.leadCompBorrowerTypeConst.BORROWER,
          };
        }
        this.updateFormWithValues(leadCompBorrowerDTO);
        break;
      case Constants.leadPuposeConst.INDIVIDUAL_JOINT:
        this.addBorrwerForm.get("creationType").disable();
        if (
          leadCompBorrowerDTO.compPartyId !== null &&
          leadCompBorrowerDTO.compPartyId !== 0
        ) {
          this.addBorrwerForm.get("partyType").disable();
        } else {
          this.addBorrwerForm.get("partyType").enable();
        }
        if (!isBorrowerExist) {
          leadCompBorrowerDTO = {
            ...leadCompBorrowerDTO,
            partyType: this.leadCompBorrowerTypeConst.BORROWER,
          };
        } else {
          leadCompBorrowerDTO = {
            ...leadCompBorrowerDTO,
            partyType: leadCompBorrowerDTO.partyType || "",
          };
        }

        this.updateFormWithValues(leadCompBorrowerDTO);
        break;
      case Constants.leadPuposeConst.SOUL_PROPRITER:
        this.addBorrwerForm.get("partyType").disable();

        if (isBorrowerExist) {
          this.addBorrwerForm.get("creationType").disable();
          leadCompBorrowerDTO = {
            ...leadCompBorrowerDTO,
            partyType:
              leadCompBorrowerDTO.partyType ||
              this.leadCompBorrowerTypeConst.NON_BORROWER,
          };
        } else {
          this.addBorrwerForm.get("creationType").enable();
          leadCompBorrowerDTO = {
            ...leadCompBorrowerDTO,
            partyType:
              leadCompBorrowerDTO.partyType ||
              this.leadCompBorrowerTypeConst.BORROWER,
          };
        }
        this.updateFormWithValues(leadCompBorrowerDTO);
        break;

      default:
        this.addBorrwerForm.get("creationType").enable();
        this.addBorrwerForm.get("partyType").enable();
        if (this.currentIndex === 0) {
          leadCompBorrowerDTO = {
            ...leadCompBorrowerDTO,
            partyType:
              leadCompBorrowerDTO.partyType ||
              this.leadCompBorrowerTypeConst.BORROWER,
          };
        }
        this.updateFormWithValues(leadCompBorrowerDTO);
        break;
    }
    this.dobRangeCheck(purpose);
  }

  dobRangeCheck(purpose: string) {
    if (purpose === Constants.leadPuposeConst.INDIVIDUAL ||
      purpose === Constants.leadPuposeConst.INDIVIDUAL_JOINT) {
      this.myDatePickerOptionsForBirthDay = {
        dateFormat: "yyyy-mm-dd",
        minYear: new Date().getFullYear() - 138,
        showTodayBtn: false,
        closeAfterSelect: true,
        firstDayOfWeek: "mo",
        disableUntil: {
          year: new Date().getFullYear() - 60,
          month: new Date().getMonth() + 1,
          day: new Date().getDate(),
        },
        disableSince:
        {
          year: new Date().getFullYear() - 18,
          month: new Date().getMonth() + 1,
          day: new Date().getDate(),
        },
      }
    } else {
      this.myDatePickerOptionsForBirthDay = {
        dateFormat: "yyyy-mm-dd",
        minYear: new Date().getFullYear() - 138,
        showTodayBtn: false,
        closeAfterSelect: true,
        firstDayOfWeek: "mo",
        disableSince: {
          year: new Date().getFullYear() - 18,
          month: new Date().getMonth() + 1,
          day: new Date().getDate(),
        },
      }
    }
  }

  updateFormWithValues(leadCompBorrowerDTO: LeadCompBorrowerDTO) {
    setTimeout(() => {
      this.addBorrwerForm.patchValue({
        creationType:
          leadCompBorrowerDTO.creationType ||
          this.leadComprehensiveTypeConst.PERSONAL,
        partyType: leadCompBorrowerDTO.partyType || "",
        title: !this.isBusiness ? leadCompBorrowerDTO.title : "",
        accountNumber: leadCompBorrowerDTO.accountNumber || "",
        civilStatus: leadCompBorrowerDTO.civilStatus || "",
        dateOfBirth: leadCompBorrowerDTO.dateOfBirth || "",
        personalName: leadCompBorrowerDTO.personalName || "",
        email: leadCompBorrowerDTO.email || "",
        mobileNumber: leadCompBorrowerDTO.mobileNumber || "",
        address1: leadCompBorrowerDTO.address1 || "",
        address2: leadCompBorrowerDTO.address2 || "",
        city: leadCompBorrowerDTO.city || "",
        gender: leadCompBorrowerDTO.gender || "",
        contactPerson: leadCompBorrowerDTO.contactPerson || "",
        designation: leadCompBorrowerDTO.designation || "",
        typeOfBusiness: leadCompBorrowerDTO.typeOfBusiness || "",
      });
      if (!this.isFormEditEnabled()) {
        this.addBorrwerForm.disable();
      }
    }, 100);
  }

  nicDobValidator(dateOfBirth: string, type: "OLD" | "NEW"): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!dateOfBirth || !control.value) {
        return null;
      }
      const dob = new Date(dateOfBirth);
      const year = dob.getFullYear().toString();
      if (type === "NEW") {
        if (!control.value.startsWith(year)) {
          return { dobMismatch: true };
        }
      }
      if (type === "OLD") {
        const lastTwo = year.slice(-2);
        if (!control.value.startsWith(lastTwo)) {
          return { dobMismatch: true };
        }
      }
      return null;
    };
  }

  nicDobVaildateAddId(dateOfBirth: string, nic: string, type: "OLD_NIC" | "NEW_NIC") {
    if (!dateOfBirth || !nic) {
      return false;
    }

    const dob = new Date(dateOfBirth);
    const year = dob.getFullYear().toString();

    if (type === "NEW_NIC") {
      if (!nic.startsWith(year)) {
        return false;
      }
    }
    if (type === "OLD_NIC") {
      const lastTwo = year.slice(-2);
      if (!nic.startsWith(lastTwo)) {
        return false;
      }
    }
    return true;
  }

  maxAgeValidator(inputAge: any) {
    if (inputAge === null || inputAge === undefined || inputAge === "") {
      return null;
    }

    let maxDOB: any = moment().diff(60, "years");
    let minDOB: any = moment().diff(18, "years");

    return (
      moment(maxDOB).format("YYYY-MM-DD") <
      moment(inputAge).format("YYYY-MM-DD") &&
      moment(minDOB).format("YYYY-MM-DD") >
      moment(inputAge).format("YYYY-MM-DD")
    );
  }

  onIDTypeChange(value: string) { }

  getIdentificationLabelByForm() {
    let value: any = this.addBorrwerForm.controls.identificationType.value;

    if (
      value !== undefined &&
      value !== null &&
      Constants.identificationTypeDescription[value]
    ) {
      return Constants.identificationTypeDescription[value];
    }
    return "Identification Number";
  }

  getIdentificationLabel(value: string) {
    const item = this.staticIdentityOption.find(
      (option) => option.value === value,
    );
    return item ? item.label : null;
  }

  loadAddBorrwerForm() {
    return this.formBuilder.group({
      creationType: [{ value: "" }, [Validators.required]],
      partyType: [{ value: "" }, [Validators.required]],
      personalName: [{ value: "" }],
      title: [{ value: SDConstants.titleConst.MR }, [Validators.required]],
      accountNumber: [{ value: "" }, [Validators.pattern("^[0-9]{12}$")]],
      dateOfBirth: [{ value: "" }],
      email: [{ value: "" }, [Validators.email]],
      civilStatus: [{ value: "" }],
      mobileNumber: [
        { value: "" },
        [Validators.required, Validators.pattern(/^(?:\+94|0)?7\d{8}$/)],
      ],
      identificationType: [Constants.identificationTypeOptionsSelect[0].value],
      identificationNumber: [{ value: "" }],
      address1: [{ value: "" }, [Validators.required]],
      address2: [{ value: "" }, []],
      city: [{ value: "" }, [Validators.required]],
      gender: [{ value: "" }],
      designation: [{ value: "" }],
      typeOfBusiness: [{ value: "" }],
      contactPerson: [{ value: "" }],
    });
  }

  clearBorrowerForm() { }

  onBranchChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
  }

  onBorrowerTypeChange(event: Event) { }

  onCreationTypeChange(event: Event) {
    // this.identifications = [];
  }

  validateValues() {
    let creationType = this.addBorrwerForm.get("creationType").value;
    let partyType = this.addBorrwerForm.get("partyType").value;

    if (!creationType) {
      this.alertService.showToaster(
        "creationType sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    if (!partyType) {
      this.alertService.showToaster(
        "Borrower Type sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
  }

  isValid() {
    return this.addBorrwerForm.valid;
  }

  save() {
    let creationType = this.addBorrwerForm.get("creationType")
      ? this.addBorrwerForm.get("creationType").value
      : "";
    let partyType = this.addBorrwerForm.get("partyType")
      ? this.addBorrwerForm.get("partyType").value
      : "";
    let dateOfBirth = this.addBorrwerForm.get("dateOfBirth")
      ? this.addBorrwerForm.get("dateOfBirth").value
      : "";

    if (!creationType) {
      this.alertService.showToaster(
        "creationType sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    if (!partyType) {
      this.alertService.showToaster(
        "Borrower Type sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    if (this.identifications.length === 0) {
      this.alertService.showToaster(
        "Identifications sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (this.maxAgeValidator(dateOfBirth)) {
      this.alertService.showToaster(
        "Age should be between 18 - 60 years old!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    let mobileNumber: string = this.addBorrwerForm.get("mobileNumber").value;
    let saveObj: LeadCompBorrowerDTO = {
      compPartyId: this.leadCompBorrowerDTO.compPartyId
        ? this.leadCompBorrowerDTO.compPartyId
        : null,
      compLeadId: this.leadCompBorrowerDTO.compLeadId
        ? this.leadCompBorrowerDTO.compLeadId
        : null,
      creationType: this.addBorrwerForm.get("creationType").value,
      partyType: this.addBorrwerForm.get("partyType").value,
      accountNumber: this.addBorrwerForm.get("accountNumber").value,
      //business properties also there
      civilStatus: this.addBorrwerForm.get("civilStatus").value
        ? this.addBorrwerForm.get("civilStatus").value
        : null,
      dateOfBirth: this.addBorrwerForm.get("dateOfBirth").value
        ? this.addBorrwerForm.get("dateOfBirth").value
        : null,
      title:
        !this.isBusiness && this.addBorrwerForm.get("title").value
          ? this.addBorrwerForm.get("title").value
          : "",
      personalName: this.addBorrwerForm.get("personalName").value,
      email: this.addBorrwerForm.get("email").value,
      mobileNumber: mobileNumber ? mobileNumber.toString() : "",
      identifications: this.identifications,
      address1: this.addBorrwerForm.get("address1").value,
      address2: this.addBorrwerForm.get("address2").value,
      city: this.addBorrwerForm.get("city").value,
      gender: this.addBorrwerForm.get("gender")
        ? this.addBorrwerForm.get("gender").value
        : "",
      isBusiness: this.isBusiness,
      addresses: [],
      contactPerson: this.addBorrwerForm.get("contactPerson")
        ? this.addBorrwerForm.get("contactPerson").value
        : "",
      designation: this.addBorrwerForm.get("designation")
        ? this.addBorrwerForm.get("designation").value
        : "",
      typeOfBusiness: this.addBorrwerForm.get("typeOfBusiness").value
        ? this.addBorrwerForm.get("typeOfBusiness").value
        : "",

      isCreationTypeEnable: false,
    };

    this.saveBorrowerObj.emit(saveObj); // emit data to parent
  }

  isValidOldNIC(value: any) {
    const oldNicRegex = /^[0-9]{9}[VvXx]$/;
    return oldNicRegex.test(value);
  }

  isValidNewNIC(value: any) {
    const oldNicRegex = /^[0-9]{12}$/;
    return oldNicRegex.test(value);
  }

  isDuplicateIdentification(newIdNumber: string) {
    return this.borrowerList.some((party) =>
      party.identifications.some(
        (rec) => rec.identificationNumber === newIdNumber,
      ),
    );
  }

  nicDobValidateAction() {
    const dateOfBirth = this.addBorrwerForm.get("dateOfBirth");
    const creationType = this.addBorrwerForm.get("creationType").value
      ? this.addBorrwerForm.get("creationType").value
      : null;
    if (
      creationType !== null &&
      creationType === this.leadComprehensiveTypeConst.PERSONAL
    ) {
      if (this.identifications.length > 0) {
        dateOfBirth.disable();
      } else {
        dateOfBirth.enable();
      }
    }
  }

  addIdentificationNumber = () => {

    let identificationType = this.addBorrwerForm.get("identificationType").value
      ? this.addBorrwerForm.get("identificationType").value
      : "";
    let identificationNumber = this.addBorrwerForm.get("identificationNumber")
      .value
      ? this.addBorrwerForm.get("identificationNumber").value
      : "";

    let dob = this.addBorrwerForm.get("dateOfBirth").value
      ? this.addBorrwerForm.get("dateOfBirth").value
      : null;

    let creationType = this.addBorrwerForm.get("creationType").value
      ? this.addBorrwerForm.get("creationType").value
      : null;

    if (
      creationType === this.leadComprehensiveTypeConst.PERSONAL &&
      (!dob || dob === null)
    ) {
      this.alertService.showToaster(
        "Date of birth sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (
      creationType === this.leadComprehensiveTypeConst.PERSONAL &&
      !this.nicDobVaildateAddId(dob, identificationNumber, identificationType)
    ) {
      this.alertService.showToaster(
        "NIC is Invalid!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (!identificationType || identificationType === "") {
      this.alertService.showToaster(
        "Identification Type sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    if (!identificationNumber || identificationNumber === "") {
      this.alertService.showToaster(
        "Identification Number sould not be empty!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (
      identificationType === "OLD_NIC" &&
      !this.isValidOldNIC(identificationNumber)
    ) {
      this.alertService.showToaster(
        "Invalid identification number!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (
      identificationType === "NEW_NIC" &&
      !this.isValidNewNIC(identificationNumber)
    ) {
      this.alertService.showToaster(
        "Invalid identification number!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    const item = this.identifications.find(
      (option) => option.identificationType === identificationType,
    );

    if (item) {
      this.alertService.showToaster(
        "Same Identification!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    if (this.isDuplicateIdentification(identificationNumber)) {
      this.alertService.showToaster(
        "Identification number already exist!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    const idNumberControl =
      this.addBorrwerForm.controls["identificationNumber"];
    idNumberControl.clearValidators();
    idNumberControl.updateValueAndValidity();

    this.addBorrwerForm.controls.identificationNumber.setValue("");
    this.addBorrwerForm.controls.identificationType.setValue("");

    this.identifications.push({
      identificationType: identificationType,
      identificationNumber: identificationNumber,
    });

    let identityOptionSelect = this.identityOptionSelect.filter(
      (opt) => opt.value !== identificationType,
    );
    this.identityOptionSelect = identityOptionSelect;
    this.nicDobValidateAction();
  };

  deleteIdentification(identificationType: string) {
    const index = this.identifications.findIndex(
      (id) => id.identificationType === identificationType,
    );
    if (index === -1) {
      console.warn(`Identification not found: ${identificationType}`);
      return;
    }
    this.identityOptionSelect = [
      ...this.identityOptionSelect,
      {
        value: identificationType,
        label: this.getIdentificationLabel(identificationType),
      },
    ];
    this.identifications.splice(index, 1);
    this.nicDobValidateAction();
  }

  deleteBorrower() {
    this.deleteBorrowerObj.emit(this.currentIndex);
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

  isDecisionExist() {
    let decision: AnalyticsDecision =
      this.leadData !== null ? this.leadData.analyticsDecision : null;

    return decision !== null && decision.decision !== null;
  }
}
