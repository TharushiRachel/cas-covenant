import { Component, Input, OnInit } from "@angular/core";
import { isNumber } from "lodash";
import {
  AnalyticsDecision,
  FacilityRequestDTO,
} from "src/app/views/pages/lead/interfaces/Lead-comp-borrower-dto";

@Component({
  selector: "app-view-analytics-decision",
  templateUrl: "./view-analytics-decision.component.html",
  styleUrls: ["./view-analytics-decision.component.scss"],
})
export class ViewAnalyticsDecisionComponent implements OnInit {
  @Input() analyticsDecision: AnalyticsDecision = null;
  facilityData: any = "";
  analyticsDecisionPreview: any = "";
  constructor() {}

  ngOnInit() {
    if (
      this.analyticsDecision !== null &&
      this.analyticsDecision.decision !== null
    ) {
      let desicion = this.transformKeysToPascalCase(
        JSON.parse(this.analyticsDecision.decision),
      );
      this.analyticsDecisionPreview = this.objectToString(desicion);
      this.facilityData = this.analyticsDecision.facilityData
        ? this.analyticsDecision.facilityData
        : "";
    }
  }

  transformKeysToPascalCase<T>(input: T): any {
    if (Array.isArray(input)) {
      return input.map((item) => this.transformKeysToPascalCase(item));
    }

    if (input !== null && typeof input === "object") {
      const result: Record<string, any> = {};
      for (const [key, value] of Object.entries(input as Record<string, any>)) {
        const newKey = this.toPascalCaseKey(key);
        result[newKey] = this.transformKeysToPascalCase(value);
      }
      return result;
    }
    return input;
  }

  toPascalCaseKey(key: string): string {
    return key
      .split("_")
      .filter(Boolean)
      .map((segment) => segment.charAt(0).toUpperCase() + segment.slice(1))
      .join(" ");
  }

  objectToString(obj: Record<string, any>): string {
    return Object.entries(obj)
      .map(
        ([key, value]) =>
          `<strong>${key}:</strong>   ${value !== "" ? this.toCapitalFirst(value) : "-"}`,
      )
      .join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
  }

  toCapitalFirst(value: string) {
    if (value && !isNumber(value)) {
      value = value.charAt(0).toUpperCase() + value.substring(1);
    }
    return value;
  }

  showDesicion() {
    return (
      this.analyticsDecisionPreview !== null &&
      this.analyticsDecisionPreview !== ""
    );
  }
}
