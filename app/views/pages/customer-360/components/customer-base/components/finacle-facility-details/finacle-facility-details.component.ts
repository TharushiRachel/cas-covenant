import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {CustomerBaseService} from "../../../../services/customer-base.service";
import {AppUtils} from "../../../../../../../shared/app.utils";
import {CurrencyPipe} from "@angular/common";
import * as _ from 'lodash';

@Component({
  selector: 'app-finacle-facility-details',
  templateUrl: './finacle-facility-details.component.html',
  styleUrls: ['./finacle-facility-details.component.scss']
})
export class FinacleFacilityDetailsComponent implements OnInit, OnDestroy {

  pageSize = 10;
  resizedFinacleFacilitiesList = [];

  constructor(private customerBaseService: CustomerBaseService,
              private currencyPipe: CurrencyPipe,) {
  }

  onFinacleFacilityChangeSub: Subscription = new Subscription();

  tableDataList = [];

  ngOnInit() {
    this.onFinacleFacilityChangeSub = this.customerBaseService.onFinacleFacilityDetailsChange
      .subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.tableDataList = data;
          this.resizedFinacleFacilitiesList = this.tableDataList.slice(0, this.pageSize);
        } else {
          this.tableDataList = [];
        }
      });
  }

  onLoadResizedList(listFromEvent) {
    this.resizedFinacleFacilitiesList = listFromEvent.outputArray;
  }

  ngOnDestroy(): void {
    this.onFinacleFacilityChangeSub.unsubscribe();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  startCaseFormat(value) {
    return AppUtils.startCaseFormat(value);
  }
}
