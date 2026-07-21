import {Component} from '@angular/core';
import {Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import * as _ from 'lodash';
import {CurrencyPipe} from "@angular/common";
import {CreditCalculatorService} from "../../../core/service/creditcalculator/credit-calculator.service";

@Component({
  selector: 'app-credit-calculator',
  templateUrl: './credit-calculator.component.html',
  styleUrls: ['./credit-calculator.component.scss']
})
export class CreditCalculatorComponent {
  heading: string;
  content: any = {};

  creditCalculatorData: any = [];
  creditScheduleData: any = [];
  slvData: any = [];
  creditCalculatorResponse = new Subscription();

  otherInfoData: any[] = [];
  facilityData: any[] = [];
  rentalData: any[] = [];

  tableColumns: any = ['Rental Number',
    'Capital Outstanding',
    'VAT',
    'Interest',
    'Capital',
    'Sanction Outstanding',
    'Gross Rental Outstanding',
    'Rental Without VAT',
    'Rental With VAT',
  ];

  slvTableColumns: any = ['Term',
    'Stipulated Loss Value',
    'Interest',
    'Capital'
  ];

  selectedTabIndex: any = 0;

  constructor(
    private creditCalculatorService: CreditCalculatorService,
    public  mdbModalRef: MDBModalRef,
    private currencyPipe: CurrencyPipe
  ) {
  }

  ngOnInit() {
    this.otherInfoData = this.content.otherInfoData;
    this.facilityData = this.content.facilityData;
    this.rentalData = this.content.rentalData;
    this.getCreditCalculatorData();
  }

  getCreditCalculatorData() {
    let dataArray = [];
    _.forEach(this.otherInfoData, item => {
      let object = {
        "code": item["otherFacilityInfoCode"],
        "value": item["otherInfoData"]
      };
      if (!_.isEmpty(object.value)) {
        dataArray.push(object);
      }
    });

    let calculatorType = "Normal";
    if (this.rentalData.length > 0) {
      calculatorType = "Structured";
    }

    let data = {
      "facilityType": this.facilityData["facilityType"],
      "data": dataArray,
      "calculatorType": calculatorType,
      "rentalData": this.rentalData
    };

    this.creditCalculatorService.getCreditCalculatorData(data);

    this.creditCalculatorResponse = this.creditCalculatorService.creditCalculatorResponse
      .subscribe(res => {
        if (!_.isEmpty(res)) {
          this.creditCalculatorData = res["systemOutputResponseData"];
          this.creditScheduleData = res["creditScheduleResponseData"];
          this.slvData = res["stipulatedLossValueResponseData"]
        }
      });
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }
}
