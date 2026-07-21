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
  content: any;
  customerCovenantId: number;
  isEditMode: boolean = false;

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
    private applicationService: ApplicationService,
    private urlEncodeService: UrlEncodeService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.resolveModeAndData();
    this.initForm();
    if (this.isEditMode) {
      this.loadCustomerCovenantForEdit();
    }
  }

  private resolveModeAndData() {
    if (
      !this.customerCovenantList &&
      this.content &&
      this.content.customerCovenantList
    ) {
      this.customerCovenantList = this.content.customerCovenantList;
    }

    this.isEditMode = !!(
      this.isEditMode ||
      (this.content && this.content.customerCovenant) ||
      this.customerCovenantId
    );

    if (this.isEditMode) {
      if (
        !this.customerCovenantId &&
        this.content &&
        this.content.customerCovenant &&
        this.content.customerCovenant.customerCovenantId
      ) {
        this.customerCovenantId =
          this.content.customerCovenant.customerCovenantId;
      }

      if (!this.customerCovenantId) {
        const idFromSession = sessionStorage.getItem("customerCovenantId");
        if (idFromSession) {
          this.customerCovenantId = parseInt(idFromSession, 10);
        }
      }

      if (!this.RequestUUID) {
        this.RequestUUID = sessionStorage.getItem("RequestUUID");
      }
    }
  }

  handelDropdown() {
    this.dropdownOptions = this.covenantDesc.filter((d: any) => !d.isSelected);
  }

  myDatePickerOptionsForBirthDay: IMyOptions = {
    dateFormat: "dd/mm/yyyy",
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
    disableUntil: this.getYesterdayDateObj(),
  };

  getYesterdayDateObj() {
    const today = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);
    return {
      year: yesterday.getFullYear(),
      month: yesterday.getMonth() + 1,
      day: yesterday.getDate(),
    };
  }

  getTodayDateObj() {
    const today = new Date();
    return {
      year: today.getFullYear(),
      month: today.getMonth() + 1,
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
    const covenantKeys =
      this.customerCovenantList && this.customerCovenantList.covenants
        ? Object.keys(this.customerCovenantList.covenants)
        : [];

    const defaultCovenantType = covenantKeys.length > 0 ? covenantKeys[0] : "";

    this.covenantForm = this.fb.group(
      {
        covenantType: [
          this.isEditMode ? "" : defaultCovenantType,
          [Validators.required],
        ],
        covenantDescription: ["", [Validators.required]],
        covenantFrequency: ["", [Validators.required]],
        covenantDueDate: ["", [Validators.required]],
        custId: this.urlEncodeService.decode(this.selectedCIFID),
        casReference: this.casReference,
        RequestUUID: this.RequestUUID,
        covenantDescription_other: [""],
        createdUserDisplayName: [""],
        createdDate: [""],
        preDisbursement: [false],
        postDisbursement: [false],
        applicableTypeAC: [false],
      },
      {
        validators: this.atLeastOneCheckboxChecked,
      }
    );

    this.covenantForm
      .get("covenantFrequency")
      .valueChanges.subscribe((value) => {
        this.onFrequencyChange(value);
      });

    if (this.isEditMode) {
      this.applyCovenantDescriptionValidators();
      return;
    }

    if (covenantKeys.length > 0) {
      this.dropdownOptionsCovenantType = covenantKeys
        .filter((name) => name == "customerLevel")
        .map((name) => ({
          label: this.formatCovenantName(name),
          value: name,
        }));

      this.updateCovenantDetailsDropdown(defaultCovenantType);

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

  private loadCustomerCovenantForEdit() {
    this.facilityPaperAddEditService
      .findCustomerCovenantByID(this.customerCovenantId)
      .then(
        (data) => {
          const covenantDueDate = data.covenant_Due_Date
            ? this.formatDateFromApi(data.covenant_Due_Date)
            : null;

          this.covenantForm.patchValue({
            covenantType: this.getCovenantTypeForEdit(data),
            covenantDescription: data.covenant_Description,
            covenantFrequency: data.covenant_Frequency,
            covenantDueDate: covenantDueDate,
            custId: this.urlEncodeService.decode(this.selectedCIFID),
            casReference: this.casReference,
            RequestUUID: this.RequestUUID,
            createdUserDisplayName: data.createdUserDisplayName,
            createdDate: data.createdDate,
            preDisbursement: data.disbursementType === "PRE",
            postDisbursement: data.disbursementType === "POST",
            applicableTypeAC:
              data.applicableType === Constants.covenantApplicableTypeConst.AC,
          });

          this.isOtherSelected = data.covenant_Code === "C_OTH";
          this.applyCovenantDescriptionValidators();
        },
        (error) => console.log(error)
      );
  }

  private applyCovenantDescriptionValidators(): void {
    const covenantDescriptionControl = this.covenantForm.get(
      "covenantDescription"
    );
    if (!covenantDescriptionControl) {
      return;
    }

    if (this.isOtherSelected) {
      covenantDescriptionControl.setValidators([
        Validators.required,
        Validators.pattern(this.otherDescriptionAllowedCharsPattern),
      ]);
    } else {
      covenantDescriptionControl.setValidators([Validators.required]);
    }

    covenantDescriptionControl.updateValueAndValidity({ emitEvent: false });
  }

  formatCovenantName(name: string): string {
    return name
      .replace(/([A-Z])/g, " $1")
      .replace(/^./, (str) => str.toUpperCase())
      .trim();
  }

  updateCovenantDetailsDropdown(selectedType: string) {
    if (
      selectedType &&
      this.customerCovenantList &&
      this.customerCovenantList.covenants &&
      this.customerCovenantList.covenants[selectedType]
    ) {
      this.dropdownOptions = this.customerCovenantList.covenants[
        selectedType
      ].map((item) => ({
        label: `${item.covenant_Code} - ${item.covenant_Description}`,
        value: item,
      }));
    } else {
      this.dropdownOptions = [];
    }
  }

  onSubmit() {
    if (this.isEditMode) {
      this.onUpdateSubmit();
    } else {
      this.onAddBatchSubmit();
    }
  }

  private onAddBatchSubmit() {
    this.isSubmitting = true;
    this.errors = [];
    let data: any = {};

    data.RequestUUID = this.RequestUUID;
    data.custId = this.urlEncodeService.decode(this.selectedCIFID);
    data.casReference = this.casReference;

    data.covenantDetails = this.covenantDetails.map((covenant) => {
      return {
        covenant_Code: covenant.covenantCode,
        covenant_Description: covenant.covenantDescription,
        covenant_Frequency: covenant.covenantFrequency,
        covenant_Due_Date: covenant.covenantDueDate,
        disbursementType: covenant.disbursementType,
        applicableType: covenant.applicableType,
      };
    });

    data.createdUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName();
    this.facilityPaperAddEditService
      .saveCustomerCovenantDetails(data)
      .then((response: any) => {
        response = Object.assign({}, response);
        this.action.next(response);
        this.covenantData = response;
        this.mdbModalRef.hide();
      });
  }

  private onUpdateSubmit() {
    this.isSubmitting = true;
    this.errors = [];
    const formData = this.covenantForm.value;

    let data: any = {};
    let covenantDetails: any = [];

    let disbursementType = "";
    if (formData.preDisbursement) {
      disbursementType = "PRE";
    } else if (formData.postDisbursement) {
      disbursementType = "POST";
    }

    let applicableType = "";
    if (formData.applicableTypeAC) {
      applicableType = Constants.covenantApplicableTypeConst.AC;
    } else {
      applicableType = Constants.covenantApplicableTypeConst.ABU;
    }

    const covenantDueDate = this.formatDate(formData.covenantDueDate);

    const covenantCode =
      this.content &&
      this.content.customerCovenant &&
      this.content.customerCovenant.covenant_Code
        ? this.content.customerCovenant.covenant_Code
        : "";

    covenantDetails.push({
      covenant_Code: covenantCode,
      covenant_Description: formData.covenantDescription,
      covenant_Frequency: formData.covenantFrequency,
      disbursementType: disbursementType,
      covenant_Due_Date: covenantDueDate,
      applicableType: applicableType,
    });

    if (this.customerCovenantId) {
      data.customerCovenantId = this.customerCovenantId;
    } else {
      const customerCovenantIdString =
        sessionStorage.getItem("customerCovenantId");
      if (customerCovenantIdString) {
        data.customerCovenantId = parseInt(customerCovenantIdString, 10);
      }
    }

    data.RequestUUID = this.RequestUUID;
    data.custId = this.urlEncodeService.decode(this.selectedCIFID);
    data.casReference = this.casReference;
    data.covenantDetails = covenantDetails;
    data.createdUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName();
    data.createdDate = Date.now();

    this.facilityPaperAddEditService
      .updateCustomerCovenant(data)
      .then((response: any) => {
        response = Object.assign({}, response);
        this.action.next(response);
        this.covenantData = response;
        this.mdbModalRef.hide();
      });
  }

  /** Converts dd/mm/yyyy to yyyy-mm-dd */
  formatDate(inputDate: string): string {
    const parts = inputDate.split("/");
    const day = parts[0];
    const month = parts[1];
    const year = parts[2];
    return `${year}-${month}-${day}`;
  }

  /** Converts API yyyy-mm-dd to dd/mm/yyyy */
  formatDateFromApi(inputDate: string): string {
    const parts = inputDate.split("-");
    const day = parts[2].slice(0, 2);
    const month = parts[1];
    const year = parts[0];
    return `${day}/${month}/${year}`;
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

  onApplicableTypeChange(type: string) {}

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }

  private extractBlankLabels(description: string): string[] {
    const labels = [];
    if (!description) {
      return labels;
    }
    const regex = /~\[(.*?)\]~/g;
    let match;
    while ((match = regex.exec(description)) !== null) {
      labels.push(match[1]);
    }
    return labels;
  }

  handleCheckDescription(description: any) {
    if (description !== null) {
      this.selectedCovenantDescription = description;
      this.descriptionWithBlank = description.replace(
        /~\[(.*?)\]~/g,
        (_, content: string) => `<strong>~[${content}]~</strong>`
      );
      const matches = this.extractBlankLabels(description);

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

  getDisplayCovenantDescription(): string {
    const covenantCode =
      this.content &&
      this.content.customerCovenant &&
      this.content.customerCovenant.covenant_Code
        ? this.content.customerCovenant.covenant_Code
        : "";

    const covenantDescriptionControl = this.covenantForm
      ? this.covenantForm.get("covenantDescription")
      : null;
    const covenantDescription = covenantDescriptionControl
      ? covenantDescriptionControl.value || ""
      : "";

    if (covenantCode && covenantDescription) {
      return `${covenantCode} - ${covenantDescription}`;
    }

    return covenantDescription || covenantCode;
  }

  getCovenantTypeForEdit(data): string {
    const list =
      this.content && this.content.customerCovenantList
        ? this.content.customerCovenantList
        : this.customerCovenantList;

    if (!list || !list.covenants) {
      return "";
    }

    for (let type in list.covenants) {
      if (
        list.covenants[type].some(
          (c) => c.covenant_Code === data.covenant_Code
        )
      ) {
        return this.formatCovenantName(type);
      }
    }
    return "";
  }
}
