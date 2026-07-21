import { Component, OnInit, OnDestroy, Input } from "@angular/core";
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from "@angular/forms";
import {
  IMyOptions,
  MDBModalRef,
  MDBModalService,
} from "ng-uikit-pro-standard";
import { Subject, Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { CommonPopupWithTinyMceEditorComponent } from "src/app/shared/components/common-popup-with-tiny-mce-editor/common-popup-with-tiny-mce-editor.component";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { CovenantService } from "src/app/views/pages/covenant/services/covenant.service";
import { AppUtils } from "src/app/shared/app.utils";
import { SessionStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { orderBy } from "lodash";
import { CurrencyPipe } from "@angular/common";
import * as _ from "lodash";
import { AlertService } from "src/app/core/service/common/alert.service";
import { ApplicationCovenant } from "../../dto/application-covenant";

@Component({
  selector: "app-account-covenant",
  templateUrl: "./account-covenant.component.html",
  styleUrls: ["./account-covenant.component.scss"],
})
export class AccountCovenantComponent implements OnInit, OnDestroy {
  @Input() type: string;
  @Input("facilityCovenantDTOList") facilityCovenantDTOList: any = [];

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
  covenantDetails: any[] = [];
  createdUserDisplayName;
  dropdownOptionsCovenantType: any;

  modalRef: MDBModalRef;
  facilityOptions: any[] = [];

  public myDatePickerOptions: IMyOptions = {
    dateFormat: "dd/mm/yyyy",
    minYear: 1900,
  };

  facilities: [
    {
      creditFacilityTemplateID: 0;
      creditFacilityName: "";
      facilityRefCode: "";
      facilityCurrency: "";
      facilityAmount: 0;
      displayOrder: 0;
      facilityID: any;
      facilityAmountMillion: any;
    }
  ];
  showTextarea: boolean = false;

  isOtherSelected: boolean = false;
  otherCovenantDescription: string = "";
  isOtherExist: boolean = false;
  action: Subject<any> = new Subject<any>();
  dueDateLabel: string = "Due Date *";

  facilityCovenantList: any;
  facilityPaper: any;
  content: any;
  facilityCovenant: ApplicationCovenant = new ApplicationCovenant();
  applicationCovenantId: number;
  isEditMode: boolean = false;

  covenantComplianceTypes = Constants.covenantComplianceTypes;
  covenantApplicableTypeConst: any = Constants.covenantApplicableTypeConst;

  descriptionBlanks: any[] = [];
  selectedCovenantDescription: string = "";
  descriptionWithBlank: string = "";
  otherDescriptionAllowedCharsPattern: RegExp =
    /^[A-Za-z0-9.,/()&%\\' ]+$/;
  private subscriptions: Subscription = new Subscription();

  constructor(
    public mdbModalRef: MDBModalRef,
    public facilityPaperAddEditService: FacilityPaperAddEditService,
    public covenantService: CovenantService,
    public fb: FormBuilder,
    private mdbModalService: MDBModalService,
    private applicationService: ApplicationService,
    private urlEncodeService: UrlEncodeService,
    private currencyPipe: CurrencyPipe,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.resolveModeAndData();
    this.getFacilityList();
    this.initForm();
    if (this.isEditMode) {
      this.loadFacilityCovenantForEdit();
    }
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  private resolveModeAndData() {
    if (
      !this.facilityCovenantList &&
      this.content &&
      this.content.facilityCovenantList
    ) {
      this.facilityCovenantList = this.content.facilityCovenantList;
    }

    this.isEditMode = !!(
      this.isEditMode ||
      (this.content && this.content.facilityCovenant) ||
      this.applicationCovenantId
    );

    if (this.isEditMode) {
      if (
        !this.applicationCovenantId &&
        this.content &&
        this.content.facilityCovenant &&
        this.content.facilityCovenant.applicationCovenantId
      ) {
        this.applicationCovenantId =
          this.content.facilityCovenant.applicationCovenantId;
      }

      if (!this.applicationCovenantId) {
        const idFromSession = sessionStorage.getItem("applicationCovenantId");
        if (idFromSession) {
          this.applicationCovenantId = parseInt(idFromSession, 10);
        }
      }
    }
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

  onSelectedValueChange($event: any) {
    if ($event !== undefined && $event !== null) {
      if (
        $event.covenant_Description === "Other" ||
        $event.covenant_Description.endsWith("_other")
      ) {
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
        this.otherCovenantDescription = "";
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

  getFacilityList() {
    this.facilityPaperAddEditService.getFacilityList().then((data: any) => {
      if (data) {
        this.facilities = data;

        data.forEach((item) => {
          item.facilityAmountMillion = this.getFormattedThreeDecimalValues(
            this.getMillionValue(item.facilityAmount)
          );
        });
      }

      if (this.isEditMode) {
        this.dropdownOptionsForFacilities = data.map((item, index) => ({
          value: item.facilityID,
          label: `${index + 1}. ${item.creditFacilityName} - ${
            item.facilityCurrency
          } ${item.facilityAmountMillion} Mn`,
        }));
      } else if ((data = orderBy(data, ["displayOrder"]))) {
        this.dropdownOptionsForFacilities = data.map((item, index) => ({
          value: item,
          label: `${index + 1}. ${item.creditFacilityName} - ${
            item.facilityCurrency
          } ${item.facilityAmountMillion} Mn`,
        }));
      }
    });
  }

  getCovenantList() {
    const RequestUUID = sessionStorage.getItem("RequestUUID");
    const type = sessionStorage.getItem("type");

    if (!RequestUUID || !type) {
      return;
    }

    const covenentList = {
      RequestUUID: RequestUUID,
      type: type,
    };

    this.covenantService
      .getCovenantList(covenentList)
      .then((response) => {
        if (response && response.executeFinacleScript_CustomData) {
          this.dropdownOptions = response.executeFinacleScript_CustomData.map(
            (item) => ({
              value: item,
              label: item.covenant_Description,
            })
          );
        } else {
          console.error("Invalid response format.");
        }
      })
      .catch((error) => {
        console.error("Error fetching covenant list:", error);
      });
  }

  initForm(): void {
    this.covenantForm = this.fb.group(
      {
        creditFacilityName: ["", [Validators.required]],
        covenantType: ["", [Validators.required]],
        covenantCode: [""],
        covenantDescription: ["", [Validators.required]],
        covenantFrequency: ["", [Validators.required]],
        covenantDueDate: ["", [Validators.required]],
        custId: this.urlEncodeService.decode(this.selectedCIFID),
        casReference: this.casReference,
        RequestUUID: this.RequestUUID,
        covenantDescription_other: [""],
        createdUserDisplayName: [""],
        preDisbursement: [false],
        postDisbursement: [false],
        applicableTypeAC: [false],
      },
      { validators: this.atLeastOneCheckboxChecked }
    );

    if (this.isEditMode) {
      this.covenantForm
        .get("covenantCode")
        .setValidators([Validators.required]);
      this.covenantForm.get("covenantCode").updateValueAndValidity();
      this.applyCovenantDescriptionValidators();
      return;
    }

    if (
      this.facilityCovenantList &&
      this.facilityCovenantList.covenants != null
    ) {
      const allCovenantNames = Object.keys(this.facilityCovenantList.covenants);

      this.dropdownOptionsCovenantType = allCovenantNames
        .filter((name) => name !== "customerLevel")
        .map((name) => ({
          label: this.formatCovenantName(name),
          value: name,
        }));

      const covenantTypeControl = this.covenantForm.get("covenantType");
      if (covenantTypeControl) {
        this.subscriptions.add(
          covenantTypeControl.valueChanges.subscribe((selectedType) => {
            this.updateCovenantDetailsDropdown(selectedType);
          })
        );
      }
    } else {
      this.alertService.showToaster(
        "Failed to load facility covenant list. Please try again later.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  private loadFacilityCovenantForEdit() {
    this.facilityPaperAddEditService
      .findFacilityCovenantByID(this.applicationCovenantId)
      .then(
        (data) => {
          this.facilityCovenant = data;

          const covenantDueDate = data.covenantDetails[0].covenant_Due_Date
            ? this.formatDateFromApi(data.covenantDetails[0].covenant_Due_Date)
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
            covenantDescription_other: "",
            createdUserDisplayName: data.createdUserDisplayName,
            preDisbursement: data.covenantDetails[0].disbursementType === "PRE",
            postDisbursement:
              data.covenantDetails[0].disbursementType === "POST",
            applicableTypeAC:
              data.covenantDetails[0].applicableType ===
              Constants.covenantApplicableTypeConst.AC,
          });
          this.isOtherSelected =
            data.covenantDetails[0].covenant_Code.endsWith("_OTH");
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
      this.facilityCovenantList &&
      this.facilityCovenantList.covenants &&
      this.facilityCovenantList.covenants[selectedType]
    ) {
      this.dropdownOptions = this.facilityCovenantList.covenants[
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

    const createdUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName();
    const facilityPaperId = this.getFacilityPaperId();
    const customerFinacleID = this.urlEncodeService.decode(this.selectedCIFID);

    const payload = this.covenantDetails.map((covenant) => {
      return {
        requestUUID: this.RequestUUID,
        customerFinacleID: customerFinacleID,
        facilityPaperRefNumber: this.casReference,
        facilityPaperId: facilityPaperId,
        covenant_Code: covenant.covenantCode,
        covenant_Description: covenant.covenantDescription,
        covenant_Frequency: covenant.covenantFrequency,
        covenant_Due_Date: covenant.covenantDueDate,
        disbursementType: covenant.disbursementType,
        applicableType: covenant.applicableType,
        createdUserDisplayName: createdUserDisplayName,
        covenantFacilities: (covenant.creditFacilityDetails || []).map(
          (facility) => ({
            facilityID: facility.facilityID,
            creditFacilityTemplateID: facility.creditFacilityTemplateID,
            creditFacilityName: facility.creditFacilityName,
            facilityRefCode: facility.facilityRefCode,
            facilityCurrency: facility.facilityCurrency,
            facilityAmount: facility.facilityAmount,
            displayOrder: facility.displayOrder,
          })
        ),
      };
    });

    this.covenantService.saveFacilityCovenants(payload).then((response: any) => {
      response = Object.assign({}, response);
      this.action.next(response);
      this.mdbModalRef.hide();
    });
  }

  private onUpdateSubmit() {
    this.isSubmitting = true;
    this.errors = [];
    const formData = this.covenantForm.value;

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

    let facobj: any = [];

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

    let applicationCovenantId = this.applicationCovenantId;
    if (!applicationCovenantId) {
      applicationCovenantId = sessionStorage.getItem("applicationCovenantId");
    }

    const payload = {
      applicationCovenantId: applicationCovenantId,
      requestUUID: this.RequestUUID,
      customerFinacleID: this.urlEncodeService.decode(this.selectedCIFID),
      facilityPaperRefNumber: this.casReference,
      facilityPaperId: this.getFacilityPaperId(),
      covenant_Code: formData.covenantCode,
      covenant_Description: formData.covenantDescription,
      covenant_Frequency: formData.covenantFrequency,
      covenant_Due_Date: covenantDueDate,
      disbursementType: disbursementType,
      applicableType: applicableType,
      createdUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      covenantFacilities: facobj,
    };

    this.covenantService
      .updateFacilityCovenant(payload)
      .then((response: any) => {
        response = Object.assign({}, response);
        this.action.next(response);
        this.mdbModalRef.hide();
      });
  }

  private getFacilityPaperId(): number {
    if (this.facilityPaper && this.facilityPaper.facilityPaperID) {
      return this.facilityPaper.facilityPaperID;
    }
    const fromSession = sessionStorage.getItem("facilityPaperID");
    return fromSession ? Number(fromSession) : null;
  }

  openAddCommentModal(formData: any) {
    const covenantDescription =
      formData.covenantDescription.covenant_Description;

    this.modalRef = this.mdbModalService.show(
      CommonPopupWithTinyMceEditorComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-lg",
        containerClass: "",
        animated: false,
        data: {
          isSaveAndCloseEnabled: true,
          content: {
            header: "Covenant",
            dataToEdit: {
              covenant_Description: covenantDescription,
            },
          },
        },
      }
    );
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

  covenantDescription: string = "";
  showTextArea: boolean = false;

  handleChange(selectedValue: string) {
    if (selectedValue === "Other") {
      this.showTextArea = true;
      this.covenantDescription = "";
    } else {
      this.showTextArea = false;
      this.covenantDescription = "";
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

  onDisbursementTypeChange(type: string) {
    const preControl = this.covenantForm.get("preDisbursement");
    const postControl = this.covenantForm.get("postDisbursement");
    if (!preControl || !postControl) {
      return;
    }

    if (type === "preDisbursement") {
      if (preControl.value) {
        postControl.patchValue(false, { emitEvent: false });
        postControl.disable({ emitEvent: false });
      } else {
        postControl.enable({ emitEvent: false });
      }
    } else if (type === "postDisbursement") {
      if (postControl.value) {
        preControl.patchValue(false, { emitEvent: false });
        preControl.disable({ emitEvent: false });
      } else {
        preControl.enable({ emitEvent: false });
      }
    }
  }

  onAdd() {
    if (this.covenantForm.valid && !this.isBlankFeildExist()) {
      const covenantDueDate = this.formatDate(
        this.covenantForm.value.covenantDueDate
      );

      const covenantDetails = {
        covenantCode: this.covenantForm.value.covenantDescription.covenant_Code,
        covenantDescription:
          this.descriptionBlanks.length > 0
            ? this.getSubmitDescription()
            : this.covenantForm.value.covenantDescription_other ||
              this.covenantForm.value.covenantDescription.covenant_Description,
        covenantFrequency: this.covenantForm.value.covenantFrequency,
        covenantDueDate: covenantDueDate,
        disbursementType: this.covenantForm.value.preDisbursement
          ? "PRE"
          : "POST",

        applicableType: this.covenantForm.value.applicableTypeAC
          ? Constants.covenantApplicableTypeConst.AC
          : Constants.covenantApplicableTypeConst.ABU,
        creditFacilityDetails: this.covenantForm.value.creditFacilityName.map(
          (facility) => ({
            creditFacilityName: facility.creditFacilityName,
            creditFacilityTemplateID: facility.creditFacilityTemplateID,
            displayOrder: facility.displayOrder,
            facilityAmount: facility.facilityAmount,
            facilityCurrency: facility.facilityCurrency,
            facilityID: facility.facilityID,
            facilityRefCode: facility.facilityRefCode,
            status: facility.status,
          })
        ),
      };

      this.covenantDetails.push(covenantDetails);

      this.descriptionWithBlank = "";
      this.descriptionBlanks = [];
      this.selectedCovenantDescription = "";
      this.covenantForm.reset();
      this.isOtherSelected = false;
    }
  }

  getCovenantFrequencyLabel(frequencyValue) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  removeCovenant(index: number) {
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
    const covenantCodeControl = this.covenantForm
      ? this.covenantForm.get("covenantCode")
      : null;
    const covenantDescriptionControl = this.covenantForm
      ? this.covenantForm.get("covenantDescription")
      : null;

    const covenantCode = covenantCodeControl
      ? covenantCodeControl.value || ""
      : "";
    const covenantDescription = covenantDescriptionControl
      ? covenantDescriptionControl.value || ""
      : "";

    if (covenantCode && covenantDescription) {
      return covenantCode + " - " + covenantDescription;
    }

    return covenantDescription || covenantCode;
  }

  getCovenantTypeForEdit(data): string {
    const list =
      this.content && this.content.facilityCovenantList
        ? this.content.facilityCovenantList
        : this.facilityCovenantList;

    if (!list || !list.covenants) {
      return "";
    }

    for (let type in list.covenants) {
      const covenantArray = list.covenants[type];

      if (
        Array.isArray(covenantArray) &&
        covenantArray.some((c) => c.covenant_Code === data.covenant_Code)
      ) {
        return this.formatCovenantName(type);
      }
    }

    return "";
  }
}
