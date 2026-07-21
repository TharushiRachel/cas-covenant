import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Subject, Subscription} from "rxjs";
import {cloneDeep} from 'lodash';
import {FacilityPaperAddEditService} from "../../../../../../services/facility-paper-add-edit.service";
import {AppUtils} from "../../../../../../../../../shared/app.utils";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-update-facility-order',
  templateUrl: './update-facility-order.component.html',
  styleUrls: ['./update-facility-order.component.scss']
})
export class UpdateFacilityOrderComponent implements OnInit, OnDestroy {

  heading: string;
  content: any = {};

  creditFacilityList: any[] = [];
  action: Subject<any> = new Subject<any>();
  onFPFacilityChangeSub = new Subscription();

  listStyle = {
    width: '100%', //width of the list defaults to 300,
    height: '100%', //height of the list defaults to 250,
    dropZoneHeight: '60px' // height of the dropzone indicator defaults to 50
  };

  constructor(public  mdbModalRef: MDBModalRef,
              private facilityPaperAddEditService: FacilityPaperAddEditService,
              private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.creditFacilityList = cloneDeep(this.content.creditFacilityList);

    this.onFPFacilityChangeSub = this.facilityPaperAddEditService.onFPFacilityChange.subscribe(
      data => {
        if (data) {
          this.mdbModalRef.hide();
        }
      }
    );
  }

  listOrderChanged(event: any) {
    // console.log(event);
  }

  removeFpFacility(event) {
    event.status = "INA"
  }

  removeData(event) {
    event.status = "INA"
  }

  updateOrderAndStatus() {
    this.creditFacilityList.map((fpFacility, index) => {
      return fpFacility.displayOrder = index + 1;
    });

    let fpFacilities = Object.assign({}, {
      facilityPaperID: this.content.facilityPaper.facilityPaperID,
      facilityDTOS: this.creditFacilityList
    });

    this.action.next(fpFacilities);
    this.facilityPaperAddEditService.updateFPFacilityDisplayOrderAndStatus(fpFacilities);
  }

  clearDeletedItem() {
    this.creditFacilityList.forEach(value => {
      value.status = 'ACT';
    });
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '', '1.3-3')
    }
  }

  ngOnDestroy(): void {
    this.onFPFacilityChangeSub.unsubscribe();
  }
}
