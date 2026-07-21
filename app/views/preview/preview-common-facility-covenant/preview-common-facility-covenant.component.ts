import { Component, Input, OnInit } from "@angular/core";
import { FacilityPaperAddEditService } from "../../pages/facility-paper/services/facility-paper-add-edit.service";
import { AppUtils } from "src/app/shared/app.utils";
import { CurrencyPipe } from "@angular/common";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-preview-common-facility-covenant",
  templateUrl: "./preview-common-facility-covenant.component.html",
  styleUrls: ["./preview-common-facility-covenant.component.scss"],
})
export class PreviewCommonFacilityCovenantComponent implements OnInit {
  @Input("facilityData") facilityData;
  covenantsWithSingleFacility: any[] = [];
  covenantsWithMultipleFacilities: any[] = [];
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  covenantVal: any[] = [];

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private currencyPipe: CurrencyPipe
  ) {}

  ngOnInit() {
    this.getFacilityCovenants();
  }

  getFacilityCovenants() {
    this.facilityPaperAddEditService.getFacilityCovenantList().then((data) => {
      this.covenantsWithSingleFacility = [];
      this.covenantsWithMultipleFacilities = [];

      data.forEach((result) => {
        const activeCovValues = result.covValue.filter(
          (covValue) => covValue.status === "Active"
        );
        activeCovValues.forEach((covValue) => {
          if (covValue.applicationCovenantFacilityDTOS.length === 1) {
            this.covenantsWithSingleFacility.push({
              ...result,
              covValue: [covValue],
            });
          } else if (covValue.applicationCovenantFacilityDTOS.length > 1) {
            this.covenantsWithMultipleFacilities.push({
              ...result,
              covValue: [covValue],
            });
          }
        });
      });
    });
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  getCovenantFrequencyLabel(frequencyValue) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  computeCovenantCounters() {
    return this.covenantsWithMultipleFacilities.filter(
      (covenant) => covenant.status === "Active"
    );
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

  getApplicableTypeTxt(type: string) {
    if (type !== null) {
      return Constants.covenantApplicableType[type];
    }
    return "-";
  }
}
