import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {cloneDeep} from 'lodash';
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";

@Component({
  selector: 'app-apf-update-facility-order',
  templateUrl: './apf-update-facility-order.component.html',
  styleUrls: ['./apf-update-facility-order.component.scss']
})
export class ApfUpdateFacilityOrderComponent implements OnInit, OnDestroy {

  heading: string;
  content: any = {};

  creditFacilityList: any[] = [];
  action: Subject<any> = new Subject<any>();
  onAFFacilityChangeSub = new Subscription();

  listStyle = {
    width: '100%', //width of the list defaults to 300,
    height: '100%', //height of the list defaults to 250,
    dropZoneHeight: '60px' // height of the dropzone indicator defaults to 50
  };

  constructor(public mdbModalRef: MDBModalRef,
              private applicationFormAddEditService: ApplicationFormAddEditService,) {
  }

  ngOnInit() {
    this.creditFacilityList = cloneDeep(this.content.creditFacilityList);

    this.onAFFacilityChangeSub = this.applicationFormAddEditService.onAFFacilitiesChange.subscribe(
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
    this.creditFacilityList.map((afFacility, index) => {
      return afFacility.displayOrder = index + 1;
    });

    let afFacilities = Object.assign({}, {
      applicationFormID: this.content.applicationForm.applicationFormID,
      facilityDTOS: this.creditFacilityList
    });

    this.action.next(afFacilities);
    this.applicationFormAddEditService.updateFPFacilityDisplayOrderAndStatus(afFacilities);
  }

  clearDeletedItem() {
    this.creditFacilityList.forEach(value => {
      value.status = 'ACT';
    });
  }

  ngOnDestroy(): void {
    this.onAFFacilityChangeSub.unsubscribe();
  }
}
