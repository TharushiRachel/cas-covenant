import { Component, Input, OnInit } from "@angular/core";
import { CurrencyService } from "src/app/core/service/common/currency.service";

@Component({
  selector: "app-facility-wrapper-v1",
  templateUrl: "./facility-wrapper-v1.component.html",
  styleUrls: ["./facility-wrapper-v1.component.scss"],
})
export class FacilityWrapperV1Component implements OnInit {
  @Input("facilities") facilities: any[];
  @Input("basicData") basicData: any;

  constructor(private readonly currencyService: CurrencyService) {}

  ngOnInit() {}

  getFormattedAmount(amount: any) {
    if (amount) {
      return this.currencyService.getFormattedAmount(amount);
    }
    return "0.00";
  }
}
