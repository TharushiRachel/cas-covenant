import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {TabsetComponent} from "ng-uikit-pro-standard";
import * as _ from "lodash";
import {FacilityPaperAddEditService} from "../../../../services/facility-paper-add-edit.service";

@Component({
  selector: 'app-fp-kalypto-detail',
  templateUrl: './fp-kalypto-detail.component.html',
  styleUrls: ['./fp-kalypto-detail.component.scss']
})
export class FpKalyptoDetailComponent implements OnInit, OnDestroy {

  @Input('selectedCustomer') selectedCustomer: any;
  onKalyptoDataChangeSub: Subscription = new Subscription();
  fpKalyptoData: any = {};

  isDataEmpty = true;
  message = "No Kalypto Data found.";
  isNotAuthMessage;

  @ViewChild('staticTabs', {static: true}) staticTabs: TabsetComponent;


  constructor(private facilityPaperAddEditService: FacilityPaperAddEditService) {
  }

  ngOnInit() {
    this.loadKalyptoData();

    this.onKalyptoDataChangeSub = this.facilityPaperAddEditService.onFpKalyptoDataChange
      .subscribe(data => {
        if (!_.isEmpty(data)) {
          if (!data.successfullyParse) {
            this.isDataEmpty = true;
            this.fpKalyptoData = {};
            this.message = this.facilityPaperAddEditService.fpKalyptoData.message;
          } else {
            this.isDataEmpty = false;
            this.fpKalyptoData = this.facilityPaperAddEditService.fpKalyptoData.kalyptoResponseDTO;
          }
        } else {
          this.isDataEmpty = true;
          this.fpKalyptoData = {};
        }
      });
  }

  loadKalyptoData() {
    if (this.facilityPaperAddEditService.allowKalypto()) {
      this.message = "Processing..";
      if (!_.isEmpty(this.selectedCustomer.customerFinancialID)) {
        this.facilityPaperAddEditService.getFpKalyptoDetail({uniqueIdentifier: this.selectedCustomer.customerFinancialID})
      }
    } else {
      this.message = "You are not authorized to view the Kalypto Details";
      this.isNotAuthMessage = true;
    }
  }

  ngOnDestroy() {
    this.onKalyptoDataChangeSub.unsubscribe();
  }

}
