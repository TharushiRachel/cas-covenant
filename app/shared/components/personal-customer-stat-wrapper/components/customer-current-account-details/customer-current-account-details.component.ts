import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import * as moment from 'moment';
import {CasCustomerService} from "../../../../../core/service/data/cas-customer.service";

@Component({
  selector: 'app-customer-current-account-details',
  templateUrl: './customer-current-account-details.component.html',
  styleUrls: ['./customer-current-account-details.component.scss']
})
export class CustomerCurrentAccountDetailsComponent implements OnInit, OnDestroy {

  @Input('customerDetails') customerDetails: any = {};
  onCustomerStatisticChangeSub = new Subscription();
  toDate = moment().subtract(1, "months").endOf('month').format('DD-MM-YYYY');
  fromDate = moment().subtract(12, "months").startOf('month').format('DD-MM-YYYY');

  guaranteeSummary: any = [];
  detailGuarantees: any = [];
  casStat: any[] = [];
  casChqReturn: any = [];
  accountViceCasStatMap = {};
  accountViceCasChqReturnMap = {};

  accountViceAverageBalanceTotalMap = {};
  accountViceAverageBalanceAverageMap = {};

  accountViceTotalDebitTotalMap = {};
  accountViceTotalDebitAverageMap = {};

  accountViceTotalCreditTotalMap = {};
  accountViceTotalCreditAverageMap = {};

  accountViceChequeReturnTotalMap = {};
  accountViceChequeReturnAverageMap = {};


  accountNumbers: any[] = [];
  isDataAvailable: boolean = true;

  constructor(private currencyPipe: CurrencyPipe,
              private casCustomerService: CasCustomerService) {
  }

  ngOnInit() {

    let request = Object.assign({}, this.customerDetails);
    this.casCustomerService.getCasStat(request).subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.casStat = res.casStats;
        this.casChqReturn = res.casChqReturns;

        if (this.casStat) {
          this.accountNumbers = _.uniq(this.casStat.map(data => data.accNo));

          this.casStat.forEach(statData => {
            if (this.accountViceCasStatMap[statData.accNo] == undefined) {
              this.accountViceCasStatMap[statData.accNo] = [];
            }
            this.accountViceCasStatMap[statData.accNo] = [
              ...this.accountViceCasStatMap[statData.accNo],
              statData
            ]
          });
        }

        if (this.accountNumbers ? !this.accountNumbers.length : !this.accountNumbers) {
          this.isDataAvailable = false;
        }

        if (this.accountNumbers) {
          this.accountNumbers.forEach(accNo => {
            let x: any[] = this.accountViceCasStatMap[accNo];

            let averageBalanceTotal: number = 0;
            let totalDebitTotal: number = 0;
            let totalCreditTotal: number = 0;
            let chequeReturnTotal: number = 0;

            let count: number = 0;

            x.forEach((data, index) => {
              averageBalanceTotal = averageBalanceTotal + parseFloat(data.averageBalance);
              totalDebitTotal = totalDebitTotal + parseFloat(data.totalDebt);
              totalCreditTotal = totalCreditTotal + parseFloat(data.totalCredit);
              chequeReturnTotal = chequeReturnTotal + parseFloat(data.chequeReturnRd);
              count = count + 1;
            });

            let averageBalanceAverage = averageBalanceTotal / count;
            let totalDebitAverage = totalDebitTotal / count;
            let totalCreditAverage = totalCreditTotal / count;
            let chequeReturnAverage = chequeReturnTotal / count;

            this.accountViceAverageBalanceTotalMap[accNo] = averageBalanceTotal;
            this.accountViceAverageBalanceAverageMap[accNo] = averageBalanceAverage;

            this.accountViceTotalDebitTotalMap[accNo] = totalDebitTotal;
            this.accountViceTotalDebitAverageMap[accNo] = totalDebitAverage;

            this.accountViceTotalCreditTotalMap[accNo] = totalCreditTotal;
            this.accountViceTotalCreditAverageMap[accNo] = totalCreditAverage;

            this.accountViceChequeReturnTotalMap[accNo] = chequeReturnTotal;
            this.accountViceChequeReturnAverageMap[accNo] = chequeReturnAverage;

          });

          this.accountNumbers.forEach(accNo => {
            let x: any[] = this.accountViceCasStatMap[accNo];

            let averageBalanceTotal: number = 0;
            let totalDebitTotal: number = 0;
            let totalCreditTotal: number = 0;
            let chequeReturnTotal: number = 0;

            let count: number = 0;

            x.forEach((data, index) => {
              averageBalanceTotal = averageBalanceTotal + parseFloat(data.averageBalance);
              totalDebitTotal = totalDebitTotal + parseFloat(data.totalDebt);
              totalCreditTotal = totalCreditTotal + parseFloat(data.totalCredit);
              chequeReturnTotal = chequeReturnTotal + parseFloat(data.chequeReturnRd);
              count = count + 1;
            });

            let averageBalanceAverage = averageBalanceTotal / count;
            let totalDebitAverage = totalDebitTotal / count;
            let totalCreditAverage = totalCreditTotal / count;
            let chequeReturnAverage = chequeReturnTotal / count;

            this.accountViceAverageBalanceTotalMap[accNo] = averageBalanceTotal;
            this.accountViceAverageBalanceAverageMap[accNo] = averageBalanceAverage;

            this.accountViceTotalDebitTotalMap[accNo] = totalDebitTotal;
            this.accountViceTotalDebitAverageMap[accNo] = totalDebitAverage;

            this.accountViceTotalCreditTotalMap[accNo] = totalCreditTotal;
            this.accountViceTotalCreditAverageMap[accNo] = totalCreditAverage;

            this.accountViceChequeReturnTotalMap[accNo] = chequeReturnTotal;
            this.accountViceChequeReturnAverageMap[accNo] = chequeReturnAverage;
          });

        }

        if (this.casChqReturn) {
          this.casChqReturn.forEach(chqData => {
            if (this.accountViceCasChqReturnMap[chqData.accNo] == undefined) {
              this.accountViceCasChqReturnMap[chqData.accNo] = [];
            }
            this.accountViceCasChqReturnMap[chqData.accNo] = [
              ...this.accountViceCasChqReturnMap[chqData.accNo],
              chqData
            ]
          });
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.onCustomerStatisticChangeSub.unsubscribe();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

}
