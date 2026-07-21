import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfAddEditFacilitiesComponent} from "../../support-components/apf-add-edit-facilities/apf-add-edit-facilities.component";
import * as _ from "lodash";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {Subscription} from "rxjs";
import {ApfUpdateFacilityOrderComponent} from "../../support-components/apf-update-facility-order/apf-update-facility-order.component";
import {Constants} from "../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-facilities-screen',
  templateUrl: './apf-facilities-screen.component.html',
  styleUrls: ['./apf-facilities-screen.component.scss']
})
export class ApfFacilitiesScreenComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  applicationFormFacilitiesList: any[] = [];
  commonFacilitySecurityList: any[] = [];
  onApplicationFormChangeSub = new Subscription();
  onAFFacilitiesChange = new Subscription();
  applicationForm: any = {};
  selectedTabIndex: number = 0;
  isPreviewMode: boolean = true;

  constructor(private mdbModalService: MDBModalService, private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {

    let commonSecurities: any[] = [];

    this.onAFFacilitiesChange = this.applicationFormAddEditService.onAFFacilitiesChange.subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.applicationForm = res;
        this.applicationFormFacilitiesList = res.afFacilityDTOList.sort((a, b) => {
          return (a.displayOrder > b.displayOrder) ? 1 : ((b.displayOrder > a.displayOrder) ? -1 : 0);
        });
        this.applicationFormFacilitiesList.forEach(facility => {
          facility.afSecurityDTOList.forEach(security => {
            if (security.isCommonSecurity == Constants.yesNoConst.Y) {
              commonSecurities.push(security);
            }
          });
        });
        this.commonFacilitySecurityList = _.uniqBy(commonSecurities, 'securityID');
      }
    });

    if (this.applicationFormAddEditService.isAbleToEdit()) {
      this.isPreviewMode = false
    }

  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
    this.onAFFacilitiesChange.unsubscribe();
  }

  openModalAddEdit($event?, facility?, selectedTabIndex?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfAddEditFacilitiesComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "Add/ Edit Facility",
        content: {
          facility: facility,
          creditFacilityList: this.applicationFormFacilitiesList
        },
      }
    });
  }

  setTabIndex($event) {
    this.selectedTabIndex = $event;
  }

  updateFacilityOrder($event?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfUpdateFacilityOrderComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-80-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "Update Facility Order",
        content: {
          applicationForm: this.applicationForm,
          creditFacilityList: this.applicationFormFacilitiesList
        },
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      // console.log(result);
    });

  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  toggleViewMode($event) {
    this.selectedTabIndex = 0;
    this.isPreviewMode = !this.isPreviewMode
  }

  toCommonSecurityContent($event) {
    document.getElementById($event).scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }

  moveUp() {
    document.getElementById("af-f-prev-mod").scrollIntoView({
        behavior: 'smooth',
        block: 'start',
        inline: 'center'
      }
    );
  }


}
