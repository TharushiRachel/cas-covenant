import { Component, Input, OnInit } from "@angular/core";
import { constants } from "../../constants";
import { CurrencyService } from "src/app/core/service/common/currency.service";

@Component({
  selector: "app-security-wrapper-v1",
  templateUrl: "./security-wrapper-v1.component.html",
  styleUrls: ["./security-wrapper-v1.component.scss"],
})
export class SecurityWrapperV1Component implements OnInit {
  @Input("facilities") facilities: any[];

  constructor() {}

  ngOnInit() {}
}
