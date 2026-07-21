import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CustomerBaseService} from "../../../../services/customer-base.service";
import {Subscription} from "rxjs";
import {TabsetComponent} from "ng-uikit-pro-standard";
import * as _ from 'lodash';
import {Kalipto} from "./kalipto";


@Component({
  selector: 'app-kalipto-detail',
  templateUrl: './kalipto-detail.component.html',
  styleUrls: ['./kalipto-detail.component.scss']
})
export class KaliptoDetailComponent implements OnInit, OnDestroy {

  onKalyptoDataChangeSub: Subscription = new Subscription();
  onCustomer360DetailsChangeSubs: Subscription = new Subscription();

  kalyptoData: any = Kalipto.kalipto;

  isDataEmpty = true;
  message = "No Kalypto Data found.";
  customer360Details: any = {};

  @ViewChild('staticTabs', {static: true}) staticTabs: TabsetComponent;


  constructor(private customerBaseService: CustomerBaseService) {
  }

  ngOnInit() {

    this.onCustomer360DetailsChangeSubs = this.customerBaseService.onCustomer360DetailsChange
      .subscribe((data: any) => {
        this.customer360Details = data;
      });

    this.onKalyptoDataChangeSub = this.customerBaseService.onKalyptoDataChange
      .subscribe(data => {
        if (!_.isEmpty(data)) {
          if (!data.successfullyParse) {
            this.isDataEmpty = true;
            this.kalyptoData = {};
            this.message = this.customerBaseService.kalyptoData.message;
          } else {
            this.isDataEmpty = false;
            this.kalyptoData = this.customerBaseService.kalyptoData.kalyptoResponseDTO;
          }
        } else {
          this.isDataEmpty = true;
          this.kalyptoData = {};
        }
      });
  }

  loadKalyptoData() {
    if (!_.isEmpty(this.customer360Details)) {
      this.customerBaseService.getKalyptoDetail({uniqueIdentifier: this.customer360Details.customerFinancialID});
    }
  }

  ngOnDestroy() {
    this.onKalyptoDataChangeSub.unsubscribe();
    this.onCustomer360DetailsChangeSubs.unsubscribe();
  }


}
