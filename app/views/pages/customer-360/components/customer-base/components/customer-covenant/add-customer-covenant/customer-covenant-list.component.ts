import { Component, OnInit, Input } from "@angular/core";
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from "@angular/forms";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { Subject } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import * as _ from "lodash";
import { SessionStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-customer-covenant-list",
  templateUrl: "./customer-covenant-list.component.html",
  styleUrls: ["./customer-covenant-list.component.scss"],
})
export class CustomerCovenantListComponent implements OnInit {
  @Input() type: string;
  @Input("facilityPaper") facilityPaper;
  covenantList: any = [];

  @SessionStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;
  dropdownOptions: { value: string; label: string }[] = [];
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  covenantForm: FormGroup;
  errors = [];
  isSubmitting = false;
  custId: string | null;
  casReference: string | null = sessionStorage.getItem("facilityPaperRefID");
  RequestUUID: string | null = sessionStorage.getItem("facilityPaperRefID");
  covenantDetails: any[] = [];
  isOtherSelected: boolean = false;
  createdUserDisplayName;
  covenantData: any = {};
  action: Subject<any> = new Subject<any>();
  dueDateLabel: string = "Due Date *";

  customerCovenantList: any;
  dropdownOptionsCovenantType: any;

  public myDatePickerOptions: IMyOptions = {
    dateFormat: "dd/mm/yyyy",
    minYear: 1900,
  };

  covenantDesc: any[] = [];
  covenantApplicableTypeConst: any = Constants.covenantApplicableTypeConst;

  descriptionBlanks: any[] = [];
  descriptionWithBlank: string = "";
  selectedCovenantDescription: string = "";
  otherDescriptionAllowedCharsPattern: RegExp =
    /^[A-Za-z0-9.,/()&%\\' ]+$/;

  constructor(
    public mdbModalRef: MDBModalRef,
    public facilityPaperAddEditService: FacilityPaperAddEditService,
    public fb: FormBuilder,
    private readonly applicationService: ApplicationService,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly alertService: AlertService
  ) {}

  ngOnInit() {
    this.initForm();
  }

  handelDropdown() {
    this.dropdownOptions = this.covenantDesc.filter((d: any) => !d.isSelected);
  }



  myDatePickerOptionsForBirthDay: IMyOptions = {
    dateFormat: "dd/mm/yyyy",
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
    // Disable past dates
    disableUntil: this.getYesterdayDateObj(),
  };

  /**
   * Returns yesterday's date as {year, month, day} for mdb-date-picker disableUntil
   */
  getYesterdayDateObj() {
    const today = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);
    return {
      year: yesterday.getFullYear(),
      month: yesterday.getMonth() + 1, // JS months are 0-based
      day: yesterday.getDate(),
    };
  }

  /**
   * Returns today's date as {year, month, day} for mdb-date-picker minDate
   */
  getTodayDateObj() {
    const today = new Date();
    return {
      year: today.getFullYear(),
      month: today.getMonth() + 1, // JS months are 0-based
      day: today.getDate(),
    };
  }

  onSelectedValueChange($event: any) {
    if ($event !== undefined && $event !== null) {
      if ($event.covenant_Description === "Other") {
        this.isOtherSelected = true;
        this.covenantForm
          .get("covenantDescription_other")
          .setValidators([
            Validators.required,
            Validators.pattern(this.otherDescriptionAllowedCharsPattern),
          ]);
      } else {
        this.handleCheckDescription($event ? $event.covenant_Description : "");
        this.isOtherSelected = false;
        this.covenantForm.get("covenantDescription_other").clearValidators();
      }
      this.covenantForm
        .get("covenantDescription_other")
        .updateValueAndValidity();
    } else {
      this.descriptionWithBlank = "";
      this.descriptionBlanks = [];
      this.selectedCovenantDescription = "";
    }
  }

  initForm(): void {
    // Ensure covenants exist before proceeding
    const covenantKeys =
      this.customerCovenantList && this.customerCovenantList.covenants
        ? Object.keys(this.customerCovenantList.covenants)
        : [];

    const defaultCovenantType = covenantKeys.length > 0 ? covenantKeys[0] : ""; // Get first available type dynamically

    this.covenantForm = this.fb.group(
      {
        covenantType: [defaultCovenantType, [Validators.required]], // Set first available type dynamically
        covenantDescription: ["", [Validators.required]],
        covenantFrequency: ["", [Validators.required]],
        covenantDueDate: ["", [Validators.required]],
        custId: this.urlEncodeService.decode(this.selectedCIFID),
        casReference: this.casReference,
        RequestUUID: this.RequestUUID,
        covenantDescription_other: [""],
        preDisbursement: [false],
        postDisbursement: [false],
        applicableTypeAC: [false],
      },
      {
        validators: this.atLeastOneCheckboxChecked,
      }
    );

    // Handle frequency change
    this.covenantForm
      .get("covenantFrequency")
      .valueChanges.subscribe((value) => {
        this.onFrequencyChange(value);
      });

    if (covenantKeys.length > 0) {
      this.dropdownOptionsCovenantType = covenantKeys
        .filter((name) => name == "customerLevel")
        .map((name) => ({
          label: this.formatCovenantName(name),
          value: name,
        }));

      // Dynamically set dropdown options based on default selection
      this.updateCovenantDetailsDropdown(defaultCovenantType);

      // Listen for changes in covenantType dropdown
      this.covenantForm
        .get("covenantType")
        .valueChanges.subscribe((selectedType) => {
          this.updateCovenantDetailsDropdown(selectedType);
        });
    } else {
      this.alertService.showToaster(
        "Failed to load facility covenant list. Please try again later.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  formatCovenantName(name: string): string {
    return name
      .replace(/([A-Z])/g, " $1") // Add space before uppercase letters
      .replace(/^./, (str) => str.toUpperCase()) // Capitalize first letter
      .trim(); // Remove leading spaces if any
  }

  updateCovenantDetailsDropdown(selectedType: string) {
    if (selectedType && this.customerCovenantList.covenants[selectedType]) {
      this.dropdownOptions = this.customerCovenantList.covenants[
        selectedType
      ].map((item) => ({
        label: `${item.covenant_Code} - ${item.covenant_Description}`,
        value: item,
      }));
    } else {
      this.dropdownOptions = [];
    }

    // Reset the selected covenantDescription when covenantType changes
    //this.covenantForm.get('covenantDescription')?.setValue(null);
  }

  onSubmit() {
    this.isSubmitting = true;
    this.errors = [];

    const facilityPaperId =
      this.facilityPaper && this.facilityPaper.facilityPaperID
        ? this.facilityPaper.facilityPaperID
        : Number(sessionStorage.getItem("facilityPaperID"));

    // Backend expects List<CustomerCovenantDTO>
    const payload = this.covenantDetails.map((covenant) => {
      return {
        requestUUID: this.RequestUUID,
        customerFinancialID: this.urlEncodeService.decode(this.selectedCIFID),
        facilityPaperRefNumber: this.casReference,
        facilityPaperId: facilityPaperId,
        covenant_Code: covenant.covenantCode,
        covenant_Description: covenant.covenantDescription,
        covenant_Frequency: covenant.covenantFrequency,
        covenant_Due_Date: covenant.covenantDueDate,
        disbursementType: covenant.disbursementType,
        applicableType: covenant.applicableType,
        createdUserDisplayName:
          this.applicationService.getLoggedInUserDisplayName(),
      };
    });

    this.facilityPaperAddEditService
      .saveCustomerCovenantDetails(payload)
      .then((response: any) => {
        response = Object.assign({}, response);
        this.action.next(response);
        this.covenantData = response;
        this.mdbModalRef.hide();
      });
  }

  formatDate(inputDate: string): string {
    const parts = inputDate.split("/");
    const day = parts[0];
    const month = parts[1];
    const year = parts[2];

    const formattedDate = `${year}-${month}-${day}`;

    return formattedDate;
  }

  isNewCovenant() {
    return _.isEmpty(this.covenantData);
  }

  onDisbursementTypeChange(type: string) {
    if (type === "preDisbursement") {
      if (this.covenantForm.get("preDisbursement").value) {
        this.covenantForm.patchValue({ postDisbursement: false });
      }
    } else if (type === "postDisbursement") {
      if (this.covenantForm.get("postDisbursement").value) {
        this.covenantForm.patchValue({ preDisbursement: false });
      }
    }
  }

  getCovenantFrequencyLabel(frequencyValue) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  onAdd() {
    if (this.covenantForm.valid) {
      const covenantDueDate = this.formatDate(
        this.covenantForm.value.covenantDueDate
      );

      const covenantDetails = {
        covenantCode: this.covenantForm.value.covenantDescription.covenant_Code,
        covenantDescription:
          this.covenantForm.value.covenantDescription_other ||
          this.covenantForm.value.covenantDescription.covenant_Description,
        covenantFrequency: this.covenantForm.value.covenantFrequency,
        covenantDueDate: covenantDueDate,
        disbursementType: this.covenantForm.value.preDisbursement
          ? "PRE"
          : "POST",
        applicableType: this.covenantForm.value.applicableTypeAC
          ? Constants.covenantApplicableTypeConst.AC
          : Constants.covenantApplicableTypeConst.ABU,
      };

      this.covenantDetails.push(covenantDetails);

      const selectedDescription =
        this.covenantForm.value.covenantDescription.covenant_Description;
      if (selectedDescription !== "Other") {
        this.covenantDesc = this.covenantDesc.map((data: any) => ({
          ...data,
          isSelected:
            data.label == selectedDescription ? true : data.isSelected,
        }));
        this.handelDropdown();
      }

      this.covenantForm.reset();
      this.descriptionWithBlank = "";
      this.descriptionBlanks = [];
      this.selectedCovenantDescription = "";
      this.isOtherSelected = false;
    }
  }

  filterDescription() {
    return this.dropdownOptions.filter((data: any) => data.isSelected == false);
  }

  removeCovenant(index: number) {
    let removedDesc: string = this.covenantDetails[index].covenantDescription;
    this.covenantDesc = this.covenantDesc.map((data: any) => ({
      ...data,
      isSelected: data.label == removedDesc ? false : data.isSelected,
    }));
    this.handelDropdown();

    this.covenantDetails.splice(index, 1);
  }

  atLeastOneCheckboxChecked(control: AbstractControl): ValidationErrors | null {
    const preDisbursement = control.get("preDisbursement")
      ? control.get("preDisbursement").value
      : false;
    const postDisbursement = control.get("postDisbursement")
      ? control.get("postDisbursement").value
      : false;

    return preDisbursement || postDisbursement ? null : { required: true };
  }

  onFrequencyChange(_value: string): void {
    const dueDateControl = this.covenantForm.get("covenantDueDate");
    if (!dueDateControl) {
      return;
    }

    this.dueDateLabel = "Due Date *";
    dueDateControl.setValidators([Validators.required]);

    if (dueDateControl.disabled) {
      dueDateControl.enable();
    }

    dueDateControl.updateValueAndValidity({ onlySelf: true, emitEvent: false });
  }

  onApplicableTypeChange(type: string) {
  }

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }

  handleCheckDescription(description: any) {
    if (description !== null) {
      this.selectedCovenantDescription = description;
      this.descriptionWithBlank = description.replace(
        /~\[(.*?)\]~/g,
        (_, content: string) => `<strong>~[${content}]~</strong>`
      );
      const matches = [...description.matchAll(/~\[(.*?)\]~/g)].map(
        (match) => match[1]
      );

      this.descriptionBlanks =
        matches.length > 0
          ? matches.map((match: any) => ({
              label: match,
              value: "",
            }))
          : [];
    }
  }

  handleBlankChange($event: any, item: any) {
    let value: string =
      $event !== null && $event.target !== null ? $event.target.value : "";
    this.descriptionBlanks = this.descriptionBlanks.map((d: any) => ({
      ...d,
      value: d.label === item.label ? value : d.value,
    }));

    this.descriptionWithBlank = this.getPreparedDescription();
  }

  isBlankFeildExist() {
    return this.descriptionBlanks.some(
      (d: any) => d.value === null || d.value === ""
    );
  }

  getPreparedDescription() {
    let result: string = "";
    if (
      this.selectedCovenantDescription !== null &&
      this.selectedCovenantDescription !== ""
    ) {
      result = this.selectedCovenantDescription;
      this.descriptionBlanks.forEach((element: any) => {
        if (element.value !== "") {
          result = result.replace(
            `~[${element.label}]~`,
            `<strong>${element.value}</strong>`
          );
        } else {
          result = result.replace(
            /~\[(.*?)\]~/g,
            (_, content) => `<strong>~[${content}]~</strong>`
          );
        }
      });
    }

    return result;
  }

  getSubmitDescription() {
    return this.descriptionWithBlank.replace(/<\/?strong>/g, "");
  }
}
