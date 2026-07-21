import { Component, Input, OnInit } from "@angular/core";
import { CurrencyPipe } from "@angular/common";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { Subscription } from "rxjs";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-preview-three-col-facility-exposure-data",
  templateUrl: "./preview-three-col-facility-exposure-data.component.html",
  styleUrls: ["./preview-three-col-facility-exposure-data.component.scss"],
})
export class PreviewThreeColFacilityExposureDataComponent implements OnInit {
  @Input("facilityPaper") facilityPaper: any;
  onBaseFacilityPaperChangeSub = new Subscription();
  onCalculateFacilityPaperExposureChangeSub = new Subscription();

  constructor(
    private currencyPipe: CurrencyPipe,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.onBaseFacilityPaperChangeSub =
      this.facilityPaperAddEditService.onBaseFacilityPaperChange.subscribe(
        (baseFacility) => {
          if (baseFacility) {
            this.facilityPaper = baseFacility;
          }
        }
      );
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, "", "");
  }

  getFormattedThreeDecimalValues(amount: any) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }
}
