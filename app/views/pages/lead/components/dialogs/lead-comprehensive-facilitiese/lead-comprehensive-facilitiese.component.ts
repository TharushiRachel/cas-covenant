import { ChangeDetectorRef, Component, OnDestroy, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from "@angular/forms";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { NumberValidator } from "src/app/shared/validators/number.validator";
import { CurrencyPipe } from "@angular/common";
import { LeadCompFacilityDTO } from "../../../interfaces/Lead-comp-facility-dto";
import { BehaviorSubject, Subject, Subscription } from "rxjs";
import { LeadComprehensiveService } from "../../../services/lead-comprehensive.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { LabelValue } from "../../../interfaces/Lead-comp-lead-main-details";
import * as _ from "lodash";
import { Constants } from "src/app/core/setting/constants";
import { CacheService } from "src/app/core/service/data/cache.service";
import { LeadAddEditService } from "../../../services/lead-add-edit.service";
import * as Utils from "../../../interfaces/utils";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-lead-comprehensive-facilitiese",
  templateUrl: "./lead-comprehensive-facilitiese.component.html",
  styleUrls: ["./lead-comprehensive-facilitiese.component.scss"],
})
export class LeadComprehensiveFacilitieseComponent
  implements OnInit, OnDestroy
{
  isFormLoaded: boolean = false;

  addFacilitiesForm: FormGroup = null;
  creditFacilityGroups = [];

  creditFacilityTemplateOptionList: any[] = [];
  leadCompFacilityDTO: LeadCompFacilityDTO;
  isCalculate: boolean = false;
  content: any;
  saveFacilitiesObj: Subject<LeadCompFacilityDTO> =
    new Subject<LeadCompFacilityDTO>();

  myDatePickerOptions: IMyOptions = {
    dateFormat: "yyyy-mm-dd",
    minYear: new Date().getFullYear() - 138,
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
  };

  repaymentDropDown: LabelValue[] = [
    { value: 1, label: "General" },
    { value: 2, label: "Structured" },
  ];

  formErrors: any;

  // Lease calculation inputs (copied from lease-calculator component)
  capital: number = 0;
  interestRate: number = 0;
  numberOfPeriods: number = 0;
  upfronts: number = 0;

  // Lease calculation outputs
  installmentValue: number = 0;
  totalAmount: number = 0;
  totalInterest: number = 0;

  // RATE calculation inputs/outputs
  rateCalculationMode: boolean = false;
  rateInstallment: number = 0;
  calculatedRate: number = 0;
  calculatedAnnualRate: number = 0;

  // Validation state for calculations
  isCalculationValid: boolean = true;
  calculationErrorMessage: string = "";
  isLeaseRentalVisible = false;

  facilityTypeList: any[] = [];
  facilityGroupDropDown: any[] = [];
  facilityTemplateDropDown: any[] = [];

  facilityDescriptionDD: any[] = [
    { value: 1, label: "Purchase of a brand new vehicle / machinery" },
    {
      value: 2,
      label:
        "Purchase of an un-registered / re-conditioned vehicle / machinery",
    },
    { value: 3, label: "Purchase of a registered vehicle" },
    {
      value: 4,
      label:
        "Financing against own vehicle / machinery (by way of Re-lease or Lease-back)",
    },
    {
      value: 5,
      label:
        "Financing the settlement of existing lease facilities with other Financial Institutions",
    },
  ];

  onFormChangeSub = new Subscription();

  processingFee: string = "As per bank prevailing tariff";
  insurance: string =
    "Vehicle to be insured to market value together with necessary covers";
  validityOffer: string = "7 Days from the date of issue";

  LEASE_GEN_VEHICLE: string = "Lease - General - Vehicle";
  LEASE_GEN_MACHINARY: string = "Lease - General - Machinery";

  isSaveLoading: boolean = false;
  isStructured: boolean = false;

  constructor(
    private readonly facilitiesModalRef: MDBModalRef,
    private readonly formBuilder: FormBuilder,
    private readonly currencyPipe: CurrencyPipe,
    private readonly leadComprehensiveService: LeadComprehensiveService,
    private readonly applicationService: ApplicationService,
    private readonly cacheService: CacheService,
    private readonly leadAddEditService: LeadAddEditService,
  ) {}

  ngOnInit() {
    this.addFacilitiesForm = this.loadAddFacilitiesForm();
    this.loadFacilityDropdown();

    const upfront = this.addFacilitiesForm.get("upfront");
    upfront.setValidators([
      Validators.required,
      Validators.min(0),
      Validators.max(10),
    ]);
    upfront.updateValueAndValidity();

    const effectiveRate = this.addFacilitiesForm.get("effectiveRate");
    effectiveRate.setValidators([
      Validators.required,
      Validators.min(0),
      Validators.max(100),
    ]);
    effectiveRate.updateValueAndValidity();

    this.addFacilitiesForm.controls.facilityTemplateId.valueChanges.subscribe(
      (value: any) => {
        this.addFacilitiesForm.controls["requestedTenure"].setValue("");
        if (
          this.facilityTemplateDropDown &&
          this.facilityTemplateDropDown.length > 0
        ) {
          let template: any = this.facilityTemplateDropDown.find(
            (d: any) => d.value === value,
          );

          if (template && template.label.includes("Vehicle")) {
            this.addFacilitiesForm.controls["requestedTenure"].setValidators([
              Validators.required,
              Validators.min(12),
              Validators.max(84),
            ]);
            this.addFacilitiesForm.controls[
              "requestedTenure"
            ].updateValueAndValidity();
          } else if (template && template.label.includes("Machinery")) {
            this.addFacilitiesForm.controls["requestedTenure"].setValidators([
              Validators.required,
              Validators.min(12),
              Validators.max(60),
            ]);
            this.addFacilitiesForm.controls[
              "requestedTenure"
            ].updateValueAndValidity();
          } else {
            this.addFacilitiesForm.controls["requestedTenure"].setValidators([
              Validators.required,
              Validators.min(12),
              Validators.max(84),
            ]);
            this.addFacilitiesForm.controls[
              "requestedTenure"
            ].updateValueAndValidity();
          }
        }
      },
    );

    this.addFacilitiesForm.controls.facilityGroupId.valueChanges.subscribe(
      (value: any) => {
        this.facilityTemplateDropDown = [];
        this.addFacilitiesForm.controls.facilityTemplateId.setValue("");

        let creditFacilityTypeID = value ? value : 0;

        let creditFacilityType: any = this.facilityTypeList.find(
          (d: any) => d.facilityTypeID === creditFacilityTypeID,
        );

        if (
          creditFacilityType !== undefined &&
          creditFacilityType !== null &&
          creditFacilityType.facilityTemplates !== null
        ) {
          creditFacilityType.facilityTemplates.forEach((element: any) => {
            if (
              !this.facilityTemplateDropDown.some(
                (d: any) => d.value === element.facilityTemplateID,
              )
            ) {
              this.facilityTemplateDropDown.push({
                value: element.facilityTemplateID,
                label: element.templateName,
              });
            }
          });
        }
      },
    );

    this.addFacilitiesForm.controls.repaymentMode.valueChanges.subscribe(
      (value: any) => {
        this.isStructured = value === "Structured";
        if (value && value !== "General") {
          this.isCalculate = false;
          this.installmentValue = 0;
        }
      },
    );

    this.formErrors = {
      facilityGroupId: {},
      facilityTemplateId: {},
      facilityDescription: {},
      leaseAmount: {},
      model: {},
      make: {},
      effectiveRate: {},
      requestedTenure: {},
      repaymentMode: {},
      upfront: {},
    };
  }

  ngOnDestroy(): void {
    this.onFormChangeSub.unsubscribe();
  }

  loadFacilityDropdown() {
    this.leadAddEditService.getFacilityTypes().then((res: any[]) => {
      this.facilityTypeList = res !== null ? res : [];
      this.facilityTypeList.forEach((element: any) => {
        if (element.description === "Lease") {
          this.creditFacilityGroups.push({
            value: element.facilityTypeID,
            label: element.description,
          });
        }
      });

      this.isFormLoaded = true;
    });
  }

  loadAddFacilitiesForm() {
    return this.formBuilder.group({
      facilityGroupId: ["", [Validators.required]],
      facilityTemplateId: ["", [Validators.required]],
      facilityDescription: ["", [Validators.required]],
      requestedTenure: ["", [Validators.required, Validators.min(12)]],
      leaseAmount: ["", [Validators.required]],
      repaymentMode: ["", [Validators.required]],
      make: ["", [Validators.required]],
      model: ["", [Validators.required]],
      effectiveRate: ["", [Validators.required]],
      upfront: ["", [Validators.required]],
    });
  }

  ensureValueAbsolute(field: string) {
    const ctrl = this.addFacilitiesForm.get("upfront");
    if (!ctrl) return;
    const raw = ctrl.value;
    if (raw === null || raw === undefined || raw === "") return;

    const parsed = Math.floor(Math.abs(Number(raw)));
    if (Number.isNaN(parsed)) {
      ctrl.patchValue("", { emitEvent: false });
    } else {
      ctrl.patchValue(parsed, { emitEvent: false });
    }
  }

  setCurrencyFormatValue(control: any) {
    const amount = this.getValue(this.getFormRawData()[control]);

    if (this.isValidAmount(amount)) {
      this.addFacilitiesForm.patchValue({
        [control]: this.currencyPipe.transform(amount, "", ""),
      });
    } else {
      this.addFacilitiesForm.patchValue({
        [control]: this.currencyPipe.transform("", "", ""),
      });
    }
  }

  isValidAmount(value: any) {
    let validRegex: RegExp = RegExp(/^[0-9]\d*(\.\d+)?$/);
    return validRegex.test(value);
  }

  getCurrencyFormat(amount: any) {
    return this.currencyPipe.transform(amount, "", "");
  }

  getValue(amount: any) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  getFacilityDescriptionName(value: number) {
    const item = this.facilityDescriptionDD.find(
      (option) => option.value === value,
    );
    return item ? item.label : null;
  }

  getFormRawData() {
    let rawData = this.addFacilitiesForm.getRawValue();
    return rawData;
  }

  onCloseModel() {
    this.facilitiesModalRef.hide();
  }

  isValid() {
    return this.addFacilitiesForm.valid;
  }

  save() {
    let compLeadId: number = this.content.compLeadId
      ? this.content.compLeadId
      : 0;
    let saveObj: LeadCompFacilityDTO = {
      compFacilityId: 0,
      compLeadId: compLeadId,
      facilityGroupId: this.addFacilitiesForm.get("facilityGroupId").value,
      facilityTemplateId:
        this.addFacilitiesForm.get("facilityTemplateId").value,
      facilityDescription: this.getFacilityDescriptionName(
        this.addFacilitiesForm.get("facilityDescription").value,
      ),
      leaseAmount: this.getValue(
        this.addFacilitiesForm.get("leaseAmount").value,
      ),
      model: this.addFacilitiesForm.get("model").value,
      make: this.addFacilitiesForm.get("make").value,
      requestedTenure: this.addFacilitiesForm.get("requestedTenure").value,
      repaymentMode: this.addFacilitiesForm.get("repaymentMode").value,
      effectiveRate: this.addFacilitiesForm.get("effectiveRate").value,
      leaseRental: this.installmentValue,
      upfront: this.addFacilitiesForm.get("upfront").value,
      processingFee: 0,
      validityOfOffer: "",
      createdBy: this.applicationService.getLoggedInUserUserName(),
      facilityType: "",
    };
    this.isSaveLoading = true;
    this.leadComprehensiveService
      .saveFacilitiesLead(saveObj)
      .then((response: any) => {
        this.isSaveLoading = false;
        if (response) {
          saveObj.compFacilityId = response.compFacilityId
            ? response.compFacilityId
            : null;
          this.saveFacilitiesObj.next(saveObj); // emit data to parent
          this.onCloseModel();
        }
      });
  }

  getTwoDecimal(control: any) {
    const rate = this.getValue(this.getFormRawData()[control]);
    if (rate) {
      const formatted = parseFloat(rate).toFixed(2);

      this.addFacilitiesForm.patchValue({
        [control]: formatted,
      });
    }
    this.calculate();
  }

  formatTenure(event: any) {
    let value = event.target.value;
    if (value) {
      if (value.includes(".")) {
        const [int, dec] = value.split(".");
        value = int + "." + dec.substring(0, 2);
      }
      event.target.value = value;
      this.addFacilitiesForm
        .get("requestedTenure")
        .setValue(value, { emitEvent: false });
    }
    this.calculate();
  }

  /**   * Calculate rate using Newton-Raphson method
   * Given: present value (pv), payment (pmt), future value (fv), number of periods (nper), and type
   */
  calculate() {
    const repaymentMode = this.addFacilitiesForm.get("repaymentMode").value;

    if (repaymentMode && repaymentMode === "General") {
      const leaseAmountControl = this.addFacilitiesForm.get("leaseAmount");
      const effectiveRateControl = this.addFacilitiesForm.get("effectiveRate");
      const requestedTenureControl =
        this.addFacilitiesForm.get("requestedTenure");
      const upfrontControl = this.addFacilitiesForm.get("upfront");

      const leaseAmount = leaseAmountControl
        ? this.getValue(leaseAmountControl.value)
        : null;
      const effectiveRate = effectiveRateControl
        ? effectiveRateControl.value
        : null;
      const requestedTenure = requestedTenureControl
        ? requestedTenureControl.value
        : null;
      const upfront = upfrontControl ? upfrontControl.value : null;

      this.capital = Number(leaseAmount);
      this.interestRate = Number(effectiveRate);
      this.numberOfPeriods = Number(requestedTenure);
      this.upfronts = Number(upfront);

      this.calculateInstallment();
      this.calculateRate();
      this.isCalculate = this.isCalculationValid;
    } else {
      this.isCalculate = false;
      this.installmentValue = 0;
    }
  }

  /**
   * Calculate the rental monthly installment using the formula:
   * installmentValue = (capital * (rate/1200)) /
   *   ((upfronts * (rate/1200)) + (1 - (1 + (rate/1200)) ^ (-(period - upfronts))))
   */
  calculateInstallment(): void {
    // Reset validation
    this.isCalculationValid = true;
    this.calculationErrorMessage = "";

    // Validate inputs
    if (this.capital <= 0) {
      this.isCalculationValid = false;
      this.calculationErrorMessage = "Capital must be greater than 0";
      return;
    }

    if (this.interestRate <= 0) {
      this.isCalculationValid = false;
      this.calculationErrorMessage = "Interest rate must be greater than 0";
      return;
    }

    if (this.numberOfPeriods <= 0 || !Number.isInteger(this.numberOfPeriods)) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        "Number of periods must be a positive integer (months)";
      return;
    }

    if (this.upfronts < 0) {
      this.isCalculationValid = false;
      this.calculationErrorMessage = "Upfronts cannot be negative";
      return;
    }

    if (!Number.isInteger(this.upfronts)) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        "Upfronts must be a whole number of months";
      return;
    }

    if (this.upfronts >= this.numberOfPeriods) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        "Upfronts must be less than the number of periods";
      return;
    }

    // Convert annual interest rate to monthly rate (as decimal)
    const monthlyRate = this.interestRate / 1200;
    const totalMonths = this.numberOfPeriods;
    const remainingMonths = totalMonths - this.upfronts;

    // Calculate installment using the provided formula
    const numerator = this.capital * monthlyRate;
    const denominator =
      this.upfronts * monthlyRate +
      (1 - Math.pow(1 + monthlyRate, -remainingMonths));
    this.installmentValue = numerator / denominator;

    // Calculate total amount and interest
    this.totalAmount = this.installmentValue * totalMonths;
    this.totalInterest = this.totalAmount - this.capital;

    // Round to 2 decimal places
    this.installmentValue = Math.round(this.installmentValue * 100) / 100;
    this.totalAmount = Math.round(this.totalAmount * 100) / 100;
    this.totalInterest = Math.round(this.totalInterest * 100) / 100;
  }

  /**
   * Excel RATE function equivalent
   */
  rate(
    nper: number,
    pmt: number,
    pv: number,
    fv: number = 0,
    type: number = 0,
    guess: number = 0.1,
  ): number {
    if (nper <= 0) {
      throw new Error("Number of periods must be greater than 0");
    }

    const FINANCIAL_MAX_ITERATIONS = 100;
    const FINANCIAL_PRECISION = 1.0e-8;

    let rate = guess;
    let y: number;
    let x0: number = guess;
    let x1: number = 0;
    let i: number = 0;
    let close: boolean = false;

    while (i < FINANCIAL_MAX_ITERATIONS && !close) {
      rate = x0;

      if (Math.abs(rate) < FINANCIAL_PRECISION) {
        y = pv * (1 + nper * rate) + pmt * (1 + rate * type) * nper + fv;
      } else {
        const fvif = Math.pow(1 + rate, nper);
        y = pv * fvif + pmt * (1 / rate + type) * (fvif - 1) + fv;
      }

      let df: number;
      if (Math.abs(rate) < FINANCIAL_PRECISION) {
        df = pv * nper + pmt * type * nper;
      } else {
        const fvif = Math.pow(1 + rate, nper);
        const term1 = pv * nper * Math.pow(1 + rate, nper - 1);
        const term2 =
          pmt * (1 / rate + type) * nper * Math.pow(1 + rate, nper - 1);
        const term3 = (pmt * (fvif - 1)) / (rate * rate);
        df = term1 + term2 - term3;
      }

      if (Math.abs(df) < FINANCIAL_PRECISION) {
        throw new Error("Cannot calculate rate: derivative is zero");
      }

      x1 = x0 - y / df;

      close = Math.abs(x1 - x0) < FINANCIAL_PRECISION;

      if (!isFinite(x1)) {
        throw new Error("Rate calculation resulted in invalid value");
      }

      x0 = x1;
      i++;
    }

    if (!close) {
      throw new Error(
        "Rate calculation did not converge after " +
          FINANCIAL_MAX_ITERATIONS +
          " iterations",
      );
    }

    return x1;
  }

  /**
   * Calculate reducing rate using RATE function
   */
  calculateRate(): void {
    // Reset validation
    this.isCalculationValid = true;
    this.calculationErrorMessage = "";
    this.calculatedRate = 0;
    this.calculatedAnnualRate = 0;

    // Validate inputs
    if (this.capital <= 0) {
      this.isCalculationValid = false;
      this.calculationErrorMessage = "Capital must be greater than 0";
      return;
    }

    if (this.interestRate <= 0) {
      this.isCalculationValid = false;
      this.calculationErrorMessage = "Effective rate must be greater than 0";
      return;
    }

    if (this.numberOfPeriods <= 0 || !Number.isInteger(this.numberOfPeriods)) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        "Number of periods must be a positive integer";
      return;
    }

    if (this.upfronts < 0) {
      this.isCalculationValid = false;
      this.calculationErrorMessage = "Upfronts cannot be negative";
      return;
    }

    if (!Number.isInteger(this.upfronts)) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        "Upfronts must be a whole number of months";
      return;
    }

    if (this.upfronts >= this.numberOfPeriods) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        "Upfronts must be less than the number of periods";
      return;
    }

    try {
      const monthlyEffectiveRate = this.interestRate / 1200;
      const remainingMonths = this.numberOfPeriods - this.upfronts;
      const effectiveInstallment =
        (this.capital * monthlyEffectiveRate) /
        (this.upfronts * monthlyEffectiveRate +
          (1 - Math.pow(1 + monthlyEffectiveRate, -remainingMonths)));

      this.rateInstallment = Math.round(effectiveInstallment * 100) / 100;

      const monthlyRate = this.rate(
        this.numberOfPeriods,
        -effectiveInstallment,
        this.capital,
        0,
        0,
        0.01,
      );

      this.calculatedRate = monthlyRate * 100;
      this.calculatedAnnualRate = monthlyRate * 12 * 100;

      this.calculatedRate = Math.round(this.calculatedRate * 10000) / 10000;
      this.calculatedAnnualRate =
        Math.round(this.calculatedAnnualRate * 100) / 100;
    } catch (error) {
      this.isCalculationValid = false;
      this.calculationErrorMessage =
        error.message || "Error calculating interest rate";
    }
  }

  /**
   * Toggle between installment calculation and rate calculation mode
   */
  toggleCalculationMode(): void {
    this.rateCalculationMode = !this.rateCalculationMode;
    if (this.rateCalculationMode) {
      this.rateInstallment =
        this.installmentValue > 0 ? this.installmentValue : 0;
    }
  }

  /**
   * Reset calculation values to default
   */
  resetCalculation(): void {
    this.capital = 0;
    this.interestRate = 0;
    this.numberOfPeriods = 0;
    this.upfronts = 0;
    this.installmentValue = 0;
    this.totalAmount = 0;
    this.totalInterest = 0;
    this.rateInstallment = 0;
    this.calculatedRate = 0;
    this.calculatedAnnualRate = 0;
    this.isCalculationValid = true;
    this.calculationErrorMessage = "";
  }
}
