import { Component, Input, OnInit } from "@angular/core";
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from "@angular/forms";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { Subject, Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { ApplicationCovenant } from "../../../dto/application-covenant";
import { AppUtils } from "src/app/shared/app.utils";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { LocalStorage, SessionStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { orderBy } from "lodash";
import { CurrencyPipe } from "@angular/common";

@Component({
  selector: "app-edit-account-covenant",
  templateUrl: "./edit-account-covenant.component.html",
  styleUrls: ["./edit-account-covenant.component.scss"],
})
export class EditAccountCovenantComponent implements OnInit {
  @Input() type: string;

  @SessionStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;

  dropdownOptions: { value: string; label: string }[] = [];
  dropdownOptionsForFacilities: { value: string; label: string }[] = [];
  selectedFacilityNames: any[] = [];
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  covenantForm: FormGroup;
  errors = [];
  isSubmitting = false;
  custId: string | null = sessionStorage.getItem("myKey");
  casReference: string | null = sessionStorage.getItem("facilityPaperRefID");
  RequestUUID: string | null = sessionStorage.getItem("RequestUUID");
  covenantDetails: any = [];
  createdUserDisplayName;
  content: any;
  facilityCovenant: ApplicationCovenant = new ApplicationCovenant();
  applicationCovenantId: number;
  action: Subject<any> = new Subject<any>();
  modalRef: MDBModalRef;
  @Input("facilityCovenantList") facilityCovenantList: any = [];

  covenantComplianceTypes = Constants.covenantComplianceTypes;
  facilities: [
    {
      creditFacilityTemplateID: any;
      creditFacilityName: any;
      facilityRefCode: any;
      facilityCurrency: any;
      facilityAmount: any;
      facilityID: any;
      facilityAmountMillion: any;
      displayOrder: any;
    }
  ];

  creditFacilityName: any[];
  isOtherSelected: boolean = false;
  otherDescriptionAllowedCharsPattern: RegExp =
    /^[A-Za-z0-9.,/()&%\\' ]+$/;
  dueDateLabel: string = "Due Date *";
  covenantApplicableTypeConst: any = Constants.covenantApplicableTypeConst;

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    public fb: FormBuilder,
    public mdbModalRef: MDBModalRef,
    private readonly applicationService: ApplicationService,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly currencyPipe: CurrencyPipe
  ) {}

  async ngOnInit() {
    this.initForm();

    //await this.getCovenantList();

    this.getData();

    this.getFacilityList();
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
        creditFacilityName: ["", [Validators.required]],
        covenantCode: ["", [Validators.required]],
        covenantDescription: ["", [Validators.required]],
        covenantFrequency: ["", [Validators.required]],
        covenantDueDate: ["", [Validators.required]],
        custId: this.urlEncodeService.decode(this.selectedCIFID),
        casReference: this.casReference,
        RequestUUID: this.RequestUUID,
        createdUserDisplayName: [""],
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

  getData() {
    this.facilityPaperAddEditService
      .findFacilityCovenantByID(this.applicationCovenantId)
      .then(
        (data) => {
          this.facilityCovenant = data;

          console.log("data ***************", data);
          

          const covenantDueDate = data.covenantDetails[0].covenant_Due_Date
            ? this.formatDate(data.covenantDetails[0].covenant_Due_Date)
            : null;

          this.selectedFacilityNames =
            data.covenantDetails[0].applicationCovenantFacilityDTOS.map(
              (facilityDTO) => ({
                value: facilityDTO,
                label: `${facilityDTO.creditFacilityName}`,
              })
            );

          const selfac = this.selectedFacilityNames.map(
            (item) => item.value.facilityID
          );

          this.covenantForm.setValue({
            covenantType: this.getCovenantTypeForEdit(data.covenantDetails[0]),
            creditFacilityName: selfac,
            covenantCode: data.covenantDetails[0].covenant_Code,
            covenantDescription: data.covenantDetails[0].covenant_Description,
            covenantFrequency: data.covenantDetails[0].covenant_Frequency,
            covenantDueDate: covenantDueDate,
            custId: this.custId,
            casReference: this.casReference,
            RequestUUID: this.RequestUUID,
            createdUserDisplayName: data.createdUserDisplayName,
            preDisbursement: data.covenantDetails[0].disbursementType === "PRE",
            postDisbursement:
              data.covenantDetails[0].disbursementType === "POST",
            applicableTypeAC:
              data.covenantDetails[0].applicableType ===
              Constants.covenantApplicableTypeConst.AC,
          });
          this.isOtherSelected = data.covenantDetails[0].covenant_Code.endsWith("_OTH");
          this.applyCovenantDescriptionValidators();
        },
        (error) => console.log(error)
      );
  }

  getCovenantList() {
    const RequestUUID = localStorage.getItem("RequestUUID");
    const type = localStorage.getItem("type");

    if (!RequestUUID || !type) {
      return;
    }

    const covenentList = {
      RequestUUID: RequestUUID,
      type: type,
    };
    this.facilityPaperAddEditService.getCovenantList(
      covenentList,
      (response, error) => {
        if (error) {
          console.error("Error fetching covenant list:", error);
        } else if (response && response.executeFinacleScript_CustomData) {
          this.dropdownOptions = response.executeFinacleScript_CustomData.map(
            (item) => ({
              value: item,
              label: item.covenant_Description,
            })
          );
        } else {
          console.error("Invalid response format.");
        }
      }
    );
  }

  getFacilityList() {
    this.facilityPaperAddEditService.getFacilityList().then((data: any) => {
      if (data) {
        this.facilities = data;

        data.forEach((item) => {
          item.facilityAmountMillion = this.getFormattedThreeDecimalValues(
            this.getMillionValue(item.facilityAmount)
          );
        });

        const filteredFacility = data.filter((item) => item.facilityID);

        this.dropdownOptionsForFacilities = data.map((item, index) => ({
          value: item.facilityID,
          label: `${index + 1}. ${item.creditFacilityName} - ${
            item.facilityCurrency
          } ${item.facilityAmountMillion} Mn`,
        }));
      }
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

    let applicableType: string = "";

    if (formData.applicableTypeAC) {
      applicableType = Constants.covenantApplicableTypeConst.AC;
    } else {
      applicableType = Constants.covenantApplicableTypeConst.ABU;
    }

    const covenantDueDate = this.formatDateForInput(formData.covenantDueDate);

    let facobj: any = [];

    data.covenantDetails = [];

    for (
      let i = 0;
      i < this.covenantForm.getRawValue().creditFacilityName.length;
      i++
    ) {
      const filteredFacility = this.facilities.filter(
        (item) =>
          item.facilityID ===
          this.covenantForm.getRawValue().creditFacilityName[i]
      );

      const facilityData: any = {};
      facilityData.creditFacilityName = filteredFacility[0].creditFacilityName;
      facilityData.creditFacilityTemplateID =
        filteredFacility[0].creditFacilityTemplateID;
      facilityData.facilityAmount = filteredFacility[0].facilityAmount;
      facilityData.facilityAmountMillion =
        filteredFacility[0].facilityAmountMillion;
      facilityData.facilityCurrency = filteredFacility[0].facilityCurrency;
      facilityData.facilityID = filteredFacility[0].facilityID;
      facilityData.facilityRefCode = filteredFacility[0].facilityRefCode;
      facilityData.displayOrder = filteredFacility[0].displayOrder;

      facobj.push(facilityData);
    }

    covenantDetails.covenant_Code = formData.covenantCode;
    covenantDetails.covenant_Description = formData.covenantDescription;
    covenantDetails.covenant_Frequency = formData.covenantFrequency;
    covenantDetails.covenant_Due_Date = covenantDueDate;
    covenantDetails.applicationCovenantFacilityDTOS = facobj;

    covenantDetails.push({
      covenant_Code: formData.covenantCode,
      covenant_Description: formData.covenantDescription,
      covenant_Frequency: formData.covenantFrequency,
      covenant_Due_Date: covenantDueDate,
      disbursementType: disbursementType,
      applicationCovenantFacilityDTOS: facobj,
      applicableType: applicableType,
    });

    data.applicationCovenantId = sessionStorage.getItem(
      "applicationCovenantId"
    );
    data.RequestUUID = this.RequestUUID;
    data.custId = this.urlEncodeService.decode(this.selectedCIFID);
    data.casReference = this.casReference;
    data.covenantDetails = covenantDetails;
    (data.createdUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName()),
      (data.createdDate = Date.now());

    this.facilityPaperAddEditService
      .saveApplicationCovenantDetails(data)
      .then((response: any) => {
        response = Object.assign({}, response);
        this.action.next(response);
      });
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  onFacilitiesSelectionChange(selectedFacilities: any) {
    this.selectedFacilityNames = selectedFacilities;
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
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
    this.covenantForm.value.covenantDueDate = "";
  }

  getDisplayCovenantDescription(): string {
    const covenantCodeControl = this.covenantForm
      ? this.covenantForm.get("covenantCode")
      : null;
    const covenantDescriptionControl = this.covenantForm
      ? this.covenantForm.get("covenantDescription")
      : null;

    const covenantCode = covenantCodeControl ? covenantCodeControl.value || "" : "";
    const covenantDescription = covenantDescriptionControl
      ? covenantDescriptionControl.value || ""
      : "";

    if (covenantCode && covenantDescription) {
      return covenantCode + " - " + covenantDescription;
    }

    return covenantDescription || covenantCode;
  }

  getCovenantTypeForEdit(data): string {
    for (let type in this.content.facilityCovenantList.covenants) {
      const covenantArray = this.content.facilityCovenantList.covenants[type];

      if (
        Array.isArray(covenantArray) &&
        covenantArray.some((c) => c.covenant_Code === data.covenant_Code)
      ) {
        return this.formatCovenantName(type);
      }
    }

    return "";
  }

  formatCovenantName(name: string): string {
    return name
      .replace(/([A-Z])/g, " $1")
      .replace(/^./, (str) => str.toUpperCase())
      .trim();
  }

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }
}
