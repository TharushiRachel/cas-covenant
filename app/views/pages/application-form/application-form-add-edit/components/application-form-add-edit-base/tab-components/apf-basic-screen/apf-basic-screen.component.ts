import {Component, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";

@Component({
  selector: 'app-apf-basic-screen',
  templateUrl: './apf-basic-screen.component.html',
  styleUrls: ['./apf-basic-screen.component.scss']
})
export class ApfBasicScreenComponent implements OnInit, OnDestroy {

  onApplicationFormChangeSub = new Subscription();
  basicDetails = [];
  applicationForm: any = {};

  constructor(private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {

    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormBasicDetailsChange.subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.basicDetails = res.basicInformationDTOList;
        this.applicationForm = res;
      }
    });

  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

}
