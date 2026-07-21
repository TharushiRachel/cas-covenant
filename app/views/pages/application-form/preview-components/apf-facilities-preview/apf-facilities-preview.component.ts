import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {cloneDeep, uniqBy} from 'lodash';

@Component({
  selector: 'app-apf-facilities-preview',
  templateUrl: './apf-facilities-preview.component.html',
  styleUrls: ['./apf-facilities-preview.component.scss']
})
export class ApfFacilitiesPreviewComponent implements OnInit {

  @Input("applicationForm") applicationForm;
  creditFacilityList: any[] = [];
  proposedFacilityList: any[] = [];
  existingFacilityList: any[] = [];
  commonFacilitySecurityList: any[] = [];
  yesNoConst = Constants.yesNoConst;
  visibility = false;
  constructor() {
  }

  ngOnInit() {
    this.setFacilities();
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
    document.getElementById("af-lps-f-prev-mod").scrollIntoView({
        behavior: 'smooth',
        block: 'start',
        inline: 'center'
      }
    );
  }

  ngOnDestroy(): void {
  }

  ngOnChanges(changes: SimpleChange) {
    if (changes['applicationForm']) {
      this.applicationForm = changes['applicationForm'].currentValue;
      this.setFacilities();
    }
  }

  setFacilities() {
    this.creditFacilityList = [];
    this.creditFacilityList = cloneDeep(this.applicationForm.afFacilityDTOList) || [];
    this.creditFacilityList = this.creditFacilityList.sort((a, b) => {
      return (a.displayOrder > b.displayOrder) ? 1 : ((b.displayOrder > a.displayOrder) ? -1 : 0);
    });

    this.creditFacilityList.forEach((facility: any) => {
      if (facility.isNew == this.yesNoConst.Y) {
        this.proposedFacilityList.push(facility);
      } else {
        this.existingFacilityList.push(facility);
      }
    });

    // The Following implementation is to get the common securities of each facility
    let commonSecurities: any[] = [];
    this.creditFacilityList.forEach(facility => {
      facility.afSecurityDTOList.forEach(security => {
        if (security.isCommonSecurity == Constants.yesNoConst.Y) {
          commonSecurities.push(security);
        }
      });
    });
    this.commonFacilitySecurityList = uniqBy(commonSecurities, 'securityID');
    // Above commonFacilitySecurityList has all common securities of all facilities
  }

}
