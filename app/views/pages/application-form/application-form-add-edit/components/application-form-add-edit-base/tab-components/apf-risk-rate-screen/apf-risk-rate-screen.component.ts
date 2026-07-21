import {Component, OnDestroy, OnInit} from '@angular/core';
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-apf-risk-rate-screen',
  templateUrl: './apf-risk-rate-screen.component.html',
  styleUrls: ['./apf-risk-rate-screen.component.scss']
})
export class ApfRiskRateScreenComponent implements OnInit, OnDestroy {

  onApplicationFormChangeSub = new Subscription();
  onApplicationFormRiskRateChangeSub = new Subscription();
  selectedTabIndex: number = 0;
  basicDetails = [];
  applicationForm: any = {};

  constructor(private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
    this.onApplicationFormRiskRateChangeSub = this.applicationFormAddEditService.onApplicationFormRiskRateChange.subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.applicationForm = res;
        this.basicDetails = [];
        this.basicDetails = res.basicInformationDTOList;
      }
    });
  }

  setTabIndex($event) {
    this.selectedTabIndex = $event;
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
    this.onApplicationFormRiskRateChangeSub.unsubscribe();
  }

}
