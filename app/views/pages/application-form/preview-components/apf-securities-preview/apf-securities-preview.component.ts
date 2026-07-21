import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {cloneDeep, uniqBy} from 'lodash';
import {AppUtils} from "../../../../../shared/app.utils";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-apf-securities-preview',
  templateUrl: './apf-securities-preview.component.html',
  styleUrls: ['./apf-securities-preview.component.scss']
})
export class ApfSecuritiesPreviewComponent implements OnInit {

  @Input("applicationForm") applicationForm;
  creditFacilityList: any[] = [];
  commonFacilitySecurityList: any[] = [];
  yesNoConst = Constants.yesNoConst;

  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.setFacilities();
  }

  ngOnChanges(changes: SimpleChange) {
    if (changes['applicationForm']) {
      this.applicationForm = changes['applicationForm'].currentValue;
      this.setFacilities();
    }
  }

  setFacilities() {
    this.creditFacilityList = [];
    let facilities = cloneDeep(this.applicationForm.afFacilityDTOList) || [];
    this.creditFacilityList = facilities.sort((a, b) => {
      return (a.displayOrder > b.displayOrder) ? 1 : ((b.displayOrder > a.displayOrder) ? -1 : 0);
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

  toContent(id) {
    document.getElementById(id).scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '', '1.3-3')
    }
  }

  moveUp() {
    document.getElementById("af-sec-prev-mod").scrollIntoView({
        behavior: 'smooth',
        block: 'start',
        inline: 'center'
      }
    );
  }
}
