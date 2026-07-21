import {Component, Input, OnInit} from '@angular/core';
import {CurrencyPipe} from "@angular/common";
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-financial-obligations-preview',
  templateUrl: './apf-financial-obligations-preview.component.html',
  styleUrls: ['./apf-financial-obligations-preview.component.scss']
})
export class ApfFinancialObligationsPreviewComponent implements OnInit {

  @Input() basicInformation;
  financialObligations = Constants.financialObligations;
  identificationTypeViceFinancialObligationMap = {};
  liabilities: any = [];
  identificationGroups = [];
  identificationTypeViceTotalValuesMap = {};

  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.liabilities = this.basicInformation.afFinancialObligationDTOList;
    this.identificationTypeViceFinancialObligationMap = {};
    this.liabilities.forEach((financialObligation: any) => {
      if (this.identificationTypeViceFinancialObligationMap[financialObligation.identificationNumber] == undefined) {
        this.identificationTypeViceFinancialObligationMap[financialObligation.identificationNumber] = [];
        this.identificationGroups.push({
          identificationNumber: financialObligation.identificationNumber,
          identificationType: financialObligation.identificationType
        });
      }
      this.identificationTypeViceFinancialObligationMap[financialObligation.identificationNumber].push(financialObligation);
    });

    this.identificationGroups.forEach((data: any) => {
      let originalAmountTotal = 0;
      let presentOutstandingTotal = 0;
      let arrearsTotal = 0;
      this.identificationTypeViceFinancialObligationMap[data.identificationNumber].forEach((financialObligations: any) => {
        originalAmountTotal = originalAmountTotal + financialObligations.originalAmount;
        presentOutstandingTotal = presentOutstandingTotal + financialObligations.presentOutstanding;
        arrearsTotal = arrearsTotal + financialObligations.arrears;
        if (this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber] == undefined) {
          this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber] = {
            originalAmountTotal: 0,
            presentOutstandingTotal: 0,
            arrearsTotal: 0
          }
        }
        this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber].originalAmountTotal = originalAmountTotal;
        this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber].presentOutstandingTotal = presentOutstandingTotal;
        this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber].arrearsTotal = arrearsTotal;
      })
    });
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

}
