import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";

@Component({
  selector: 'app-apf-liability-screen',
  templateUrl: './apf-liability-screen.component.html',
  styleUrls: ['./apf-liability-screen.component.scss']
})
export class ApfLiabilityScreenComponent implements OnInit, OnDestroy {

  onApplicationFormChangeSub = new Subscription();
  onApplicationFormLiabilityScreenDataChange = new Subscription();
  selectedTabIndex: number = 0;
  basicDetails = [];
  applicationForm: any = {};

  constructor(private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
    this.onApplicationFormLiabilityScreenDataChange = this.applicationFormAddEditService.onApplicationFormLiabilityScreenDataChange.subscribe((res: any) => {
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
    this.onApplicationFormLiabilityScreenDataChange.unsubscribe();
  }

}
