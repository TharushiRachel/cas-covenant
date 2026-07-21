import { CurrencyPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import * as _ from 'lodash';
import { CasCustomerService } from 'src/app/core/service/data/cas-customer.service';

@Component({
  selector: 'app-customer-comprehensive-statistics',
  templateUrl: './customer-comprehensive-statistics.component.html',
  styleUrls: ['./customer-comprehensive-statistics.component.scss']
})
export class CustomerComprehensiveStatisticsComponent implements OnInit {

  @Input('customerDetails') customerDetails: any = {};
  customerDepositDetails: any[] = [];
  customerComprehensiveDetails: any[] = [];
  isDataAvailable: boolean = true;

  constructor(private currencyPipe: CurrencyPipe,
              private casCustomerService: CasCustomerService) { }

  ngOnInit() {
    this.casCustomerService.getTranDetForCashFlow(this.customerDetails).subscribe((response: any) => {
      if (!_.isEmpty(response)) {

        this.customerDepositDetails = response.executeFinacleScriptCustomDataDTOS;
        this.customerComprehensiveDetails = response.executeFinacleScriptCustomDataTreemapDTO;
        if (this.customerComprehensiveDetails ? !this.customerComprehensiveDetails.length : !this.customerComprehensiveDetails) {
          this.isDataAvailable = false
        }
    
    };

  })
}

getCurrencyFormat(amount) {
  return this.currencyPipe.transform(amount, '', '')
}

}
