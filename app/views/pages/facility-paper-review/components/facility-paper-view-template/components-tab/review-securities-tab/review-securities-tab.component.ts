import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {FacilityPaperReviewTemplateService} from "../../../../services/facility-paper-review-template.service";
import {sortBy, uniqBy} from 'lodash'
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-review-securities-tab',
  templateUrl: './review-securities-tab.component.html',
  styleUrls: ['./review-securities-tab.component.scss']
})
export class ReviewSecuritiesTabComponent implements OnInit, OnDestroy {
  @Input("tabView") tabView;
  onFPFacilitiesChangeSub = new Subscription();
  facilityPaper: any = {};
  creditFacilityList: any = [];
  commonFacilitySecurityList: any = [];
  facilitySecurityList: any = [];
  yesNoConst = Constants.yesNoConst;
  isPreviewMode = true;

  constructor(private facilityPaperReviewTemplateService: FacilityPaperReviewTemplateService,
              private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {

    this.onFPFacilitiesChangeSub = this.facilityPaperReviewTemplateService.onFPFacilitiesChange
      .subscribe((data: any) => {
        if (data) {
          this.facilityPaper = data;
        }
        this.creditFacilityList = [];
        this.creditFacilityList = this.facilityPaper.facilityDTOList || [];
        this.creditFacilityList = sortBy(this.creditFacilityList, ['displayOrder']);

        //The following implementation fro getting all common securities and other securities of each facility and get in to one array
        let commonSecurities: any[] = [];
        let allSecurities: any[] = [];

        this.creditFacilityList.forEach(facility => {
          facility.facilitySecurityDTOList.forEach(security => {
            allSecurities.push(security);
            if (security.isCommonSecurity == Constants.yesNoConst.Y) {
              commonSecurities.push(security);
            }
          });
        });
        this.commonFacilitySecurityList = uniqBy(commonSecurities, 'facilitySecurityID');
        this.facilitySecurityList = uniqBy(allSecurities, 'facilitySecurityID');
        // Above commonFacilitySecurityList has all common securities of all facilities
        // Above facilitySecurityList has all securities of all facilities
      });

  }

  ngOnDestroy(): void {
    this.onFPFacilitiesChangeSub.unsubscribe();
  }

  toContent(id) {
    document.getElementById(id).scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  moveUp() {
    document.getElementById("fp-f-prev-mod").scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }
}
