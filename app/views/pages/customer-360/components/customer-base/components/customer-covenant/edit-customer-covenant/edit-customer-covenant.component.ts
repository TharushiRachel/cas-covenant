import { Component, Input, OnInit } from "@angular/core";
import { CustomerCovenant } from "../../../dto/customer-covenant";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { Constants } from "src/app/core/setting/constants";
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from "@angular/forms";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Subject } from "rxjs";
import { DatePipe } from "@angular/common";
import { LocalStorage, SessionStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";

@Component({
  selector: "app-edit-customer-covenant",
  templateUrl: "./edit-customer-covenant.component.html",
  styleUrls: ["./edit-customer-covenant.component.scss"],
  providers: [DatePipe],
})
export class EditCustomerCovenantComponent implements OnInit {
  @SessionStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;
  content: any;
  customerCovenantId: number;
  dropdownOptions: { value: string; label: string }[] = [];
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  covenantForm: FormGroup;
  custId: string | null = sessionStorage.getItem("myKey");
  casReference: string | null = sessionStorage.getItem("facilityPaperRefID");
  RequestUUID: string | null = sessionStorage.getItem("RequestUUID");

  errors = [];
  isSubmitting = false;
  covenantData: any = {};
  action: Subject<any> = new Subject<any>();

  isOtherSelected: boolean = false;
  otherDescriptionAllowedCharsPattern: RegExp =
    /^[A-Za-z0-9.,/()&%\\' ]+$/;
  myDate = new Date();
  dueDateLabel: string = "Due Date *";
  @Input("customerCovenantList") customerCovenantList: any = [];
  covenantApplicableTypeConst: any = Constants.covenantApplicableTypeConst;

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    public fb: FormBuilder,
    public mdbModalRef: MDBModalRef,
    private readonly applicationService: ApplicationService,
    private readonly urlEncodeService: UrlEncodeService
  ) {}

  ngOnInit() {
    this.content.customerCovenant;

    this.initForm();
    this.facilityPaperAddEditService
      .findCustomerCovenantByID(this.customerCovenantId)
      .then(
        (data) => {
          const covenantDueDate = data.covenant_Due_Date
            ? this.formatDate(data.covenant_Due_Date)
            : null;

          this.covenantForm.patchValue({
            covenantType: this.getCovenantTypeForEdit(data),
            covenantDescription: data.covenant_Description,
            covenantFrequency: data.covenant_Frequency,
            covenantDueDate: covenantDueDate,
            custId: this.custId,
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

  initForm(): void {
    this.covenantForm = this.fb.group(
      {
        covenantType: ["", [Validators.required]],
        covenantDescription: ["", [Validators.required]],
        covenantFrequency: ["", [Validators.required]],
        covenantDueDate: ["", [Validators.required]],
        custId: this.urlEncodeService.decode(this.selectedCIFID),
        casReference: this.casReference,
        RequestUUID: this.RequestUUID,
        createdUserDisplayName: [""],
        createdDate: [""],
        preDisbursement: [false],
        postDisbursement: [false],
        applicableTypeAC: [false],
      },
      { validators: this.atLeastOneCheckboxChecked }
    );

    this.covenantForm
      .get("covenantFrequency")
      .valueChanges.subscribe((value) => {
        this.onFrequencyChange(value);
      });

    this.applyCovenantDescriptionValidators();
  }

  private applyCovenantDescriptionValidators(): void {
    const covenantDescriptionControl = this.covenantForm.get("covenantDescription");
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

  atLeastOneCheckboxChecked(control: AbstractControl): ValidationErrors | null {
    const preDisbursement = control.get("preDisbursement")
      ? control.get("preDisbursement").value
      : false;
    const postDisbursement = control.get("postDisbursement")
      ? control.get("postDisbursement").value
      : false;

    return preDisbursement || postDisbursement ? null : { required: true };
  }

  onSubmit() {
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

    let applicableTpe: string = "";

    if (formData.applicableTypeAC) {
      applicableTpe = Constants.covenantApplicableTypeConst.AC;
    } else {
      applicableTpe = Constants.covenantApplicableTypeConst.ABU;
    }

    const covenantDueDate = this.formatDateForInput(formData.covenantDueDate);

    covenantDetails.push({
      covenant_Code: this.content.customerCovenant.covenant_Code,
      covenant_Description: formData.covenantDescription,
      covenant_Frequency: formData.covenantFrequency,
      disbursementType: disbursementType,
      covenant_Due_Date: covenantDueDate,
      applicableType: applicableTpe,
    });

    const customerCovenantIdString =
      sessionStorage.getItem("customerCovenantId");
    if (customerCovenantIdString) {
      data.customerCovenantId = parseInt(customerCovenantIdString, 10); // Use parseInt with base 10
    }
    data.RequestUUID = this.RequestUUID;
    data.custId = this.urlEncodeService.decode(this.selectedCIFID);
    data.casReference = this.casReference;
    data.covenantDetails = covenantDetails;
    (data.createdUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName()),
      (data.createdDate = Date.now());

    this.facilityPaperAddEditService
      .updateCustomerCovenant(data)
      .then((response: any) => {
        response = Object.assign({}, response);
        this.action.next(response);
        this.covenantData = response;
      });
  }

  formatDate(inputDate: string): string {
    const parts = inputDate.split("-");
    const day = parts[2].slice(0, 2);
    const month = parts[1];
    const year = parts[0];

    const formattedDate = `${day}/${month}/${year}`;

    return formattedDate;
  }

  formatDateForInput(inputDate: string): string {
    const parts = inputDate.split("/");
    const day = parts[0];
    const month = parts[1];
    const year = parts[2];

    const formattedDate = `${year}-${month}-${day}`;

    return formattedDate;
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

  onDateChange(event: any) {
    this.covenantForm.controls["covenantDueDate"].setValue(event.value);
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

  clearDateField() {
    this.covenantForm.value.covenantDueDate = null;
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
    // Iterate over customerCovenantList and find the matching covenant
    for (let type in this.content.customerCovenantList.covenants) {
      if (
        this.content.customerCovenantList.covenants[type].some(
          (c) => c.covenant_Code === data.covenant_Code
        )
      ) {
        return this.formatCovenantName(type); // Return the matched covenant type
      }
    }
    return ""; // Default empty value if not found
  }

  formatCovenantName(name: string): string {
    return name
      .replace(/([A-Z])/g, " $1") // Add space before uppercase letters
      .replace(/^./, (str) => str.toUpperCase()) // Capitalize first letter
      .trim(); // Remove leading spaces if any
  }

  onApplicableTypeChange(type: string) {}
}
