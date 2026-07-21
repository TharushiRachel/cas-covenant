import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-apf-basic-business-info-preview',
  templateUrl: './apf-basic-business-info-preview.component.html',
  styleUrls: ['./apf-basic-business-info-preview.component.scss']
})
export class ApfBasicBusinessInfoPreviewComponent implements OnInit {

  constitutionConst = Constants.constitutionConst;
  constitution = Constants.constitution;
  basicInformationType = Constants.basicInformationType;
  addressType = Constants.addressType;
  customerDetails: any = {};
  @Input() basicInformation;

  constructor(public currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.customerDetails = this.basicInformation.afCustomerDTO ? this.basicInformation.afCustomerDTO : {};
  }

  isPrivateLTD() {
    return this.basicInformation.constitution == this.constitutionConst.PRIVATE_LTD;
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

}
