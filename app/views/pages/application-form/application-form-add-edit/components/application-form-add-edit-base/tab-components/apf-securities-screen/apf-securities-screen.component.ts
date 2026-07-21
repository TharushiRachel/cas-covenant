import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {sortBy, uniqBy} from 'lodash'
import {BehaviorSubject, Subscription} from "rxjs";
import {PrivilegeService} from "../../../../../../../../core/service/authentication/privilege.service";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {ApfAddEditSecurityDataComponent} from "../../support-components/apf-add-edit-security-data/apf-add-edit-security-data.component";
import {Constants} from "../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-securities-screen',
  templateUrl: './apf-securities-screen.component.html',
  styleUrls: ['./apf-securities-screen.component.scss']
})
export class ApfSecuritiesScreenComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  onAFFacilitiesChangeSub = new Subscription();
  onAFFacilitiesChange = new BehaviorSubject({});
  selectedTabIndex: number = 0;

  afFacilityList: any[] = [];
  facilitySecurityList: any[] = [];
  commonFacilitySecurityList: any[] = [];
  applicationForm: any = {};
  isPreviewMode: boolean = true;


  constructor(private applicationFormAddEditService: ApplicationFormAddEditService,
              private privilegeService: PrivilegeService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService) {
  }

  ngOnInit() {

    this.onAFFacilitiesChangeSub = this.applicationFormAddEditService.onApplicationFormSecuritiesChange
      .subscribe((data: any) => {
        if (data) {
          this.applicationForm = data;
        }
        this.afFacilityList = [];
        this.afFacilityList = this.applicationForm.afFacilityDTOList || [];
        this.afFacilityList = sortBy(this.afFacilityList, ['displayOrder']);

        //The following implementation fro getting all common securities and other securities of each facility and get in to one array
        let commonSecurities: any[] = [];
        let allSecurities: any[] = [];

        this.afFacilityList.forEach(facility => {
          facility.afSecurityDTOList.forEach(security => {
            allSecurities.push(security);
            if (security.isCommonSecurity == Constants.yesNoConst.Y) {
              commonSecurities.push(security);
            }
          });
        });
        this.commonFacilitySecurityList = uniqBy(commonSecurities, 'securityID');
        this.facilitySecurityList = uniqBy(allSecurities, 'securityID');

      });

    if (this.applicationFormAddEditService.isAbleToEdit()) {
      this.isPreviewMode = false
    }
  }

  ngOnDestroy(): void {
    this.onAFFacilitiesChangeSub.unsubscribe();
  }

  setTabIndex($event) {
    this.selectedTabIndex = $event;
  }

  saveOrUpdateFPFacility(data) {
    // this.applicationFormAddEditService.saveOrUpdateFPFacility(data);
  }

  toCommonSecurityContent($event) {
    document.getElementById($event).scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }

  manageSecurity() {
    let obj = Object.assign({facilityData: {}}, {securityItem: {}}, {allSecurityItems: this.facilitySecurityList});
    this.openModalFacilitySecurity(obj);
  }

  addSecurityThroughFacility(data: any) {
    this.openModalFacilitySecurity(data);
  }

  openModalFacilitySecurity(data) {

    let facilityData = data.facilityData ? data.facilityData : {};
    let securityItem = data.securityItem ? data.securityItem : {};
    let allSecurityItems = data.allSecurityItems ? data.allSecurityItems : [];
    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityData}
      ]
    };

    this.modalRef = this.mdbModalService.show(ApfAddEditSecurityDataComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "Add/ Edit Facility",
        content: {
          facilityData: facilityData,
          securityItem: securityItem,
          allSecurityItems: allSecurityItems
        },
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      // this.onAddEditSecurityDetails.emit(result);
      // this.facilityPaperAddEditService.saveOrUpdateFPFacility(result);
    });
  }

  moveUp() {
    document.getElementById("fp-sec-cmp").scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  toggleViewMode($event) {
    this.selectedTabIndex = 0;
    this.isPreviewMode = !this.isPreviewMode
  }

}
