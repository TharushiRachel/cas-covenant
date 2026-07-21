import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {CurrencyPipe} from "@angular/common";
import * as _ from "lodash";
import {FacilityPaperReviewTemplateService} from "../../../../../services/facility-paper-review-template.service";

@Component({
  selector: 'app-review-facility-security',
  templateUrl: './review-facility-security.component.html',
  styleUrls: ['./review-facility-security.component.scss']
})
export class ReviewFacilitySecurityComponent implements OnInit {
  @Input('facilityData') facilityData: any = {};
  @Input('facilityPaper') facilityPaper: any = {};
  @Input("isPreviewMode") isPreviewMode: boolean;
  @Output("toCommonSecurityContent") toCommonSecurityContent: EventEmitter<number> = new EventEmitter();

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  modalRef: MDBModalRef;
  resizedfpSecurityList: any[] = [];
  activeSecurityList: any[] = [];
  organizedSecurityList: any[] = [];
  count: number = 0;
  fpSecurity = "fpSecurity";
  yesNoConst = Constants.yesNoConst;
  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  totalIndividualSecuritiesCashAmount = 0;
  currency = '';

  constructor(private mdbModalService: MDBModalService,
              private facilityPaperReviewTemplateService: FacilityPaperReviewTemplateService,
              private applicationService: ApplicationService,
              private currencyPipe: CurrencyPipe,) {
  }

  ngOnInit() {

    this.activeSecurityList = _.filter(this.facilityData.facilitySecurityDTOList, (security) => security.status == Constants.statusConst.ACT);


    let commonSecurities = [];
    let individualSecurities = [];
    this.activeSecurityList.forEach(security => {
      if (security.isCommonSecurity == Constants.yesNoConst.Y) {
        commonSecurities.push(security);
      } else {
        individualSecurities.push(security);
      }
    });
    this.organizedSecurityList = _.concat(_.sortBy(_.uniqBy(individualSecurities, 'facilitySecurityID'), 'facilitySecurityID'), _.sortBy(_.uniqBy(commonSecurities, 'facilitySecurityID'), 'facilitySecurityID'));

    _.uniqBy(individualSecurities, 'facilitySecurityID').forEach(e => {
      this.totalIndividualSecuritiesCashAmount = this.totalIndividualSecuritiesCashAmount + e.cashAmount;
      this.currency = e.securityCurrency;
    });

    if (this.activeSecurityList.length > 3) {
      for (this.count = 0; this.count < 3; this.count++) {
        this.resizedfpSecurityList.push(this.activeSecurityList[this.count])
      }
    } else {
      for (this.count = 0; this.count < this.activeSecurityList.length; this.count++) {
        this.resizedfpSecurityList.push(this.activeSecurityList[this.count])
      }
    }

    this.isEqualLoginAndAssignUser();
  }


  onLoadResizedList(listFromEvent) {

    if (listFromEvent.outputArrayType === this.fpSecurity) {
      this.resizedfpSecurityList = [];
      _.forEach(listFromEvent.outputArray, item => {
        this.resizedfpSecurityList.push(item)
      })
    }
  }


  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  isEqualLoginAndAssignUser() {
    if (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED;
  }

  isRejected() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.REJECTED;
  }

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

}
