import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../../../core/setting/constants";
import {CacheService} from "../../../../../../../../../core/service/data/cache.service";
import {ApfAddEditRiskRateComponent} from "../../../support-components/apf-add-edit-risk-rate/apf-add-edit-risk-rate.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {ConfirmationDialogComponent} from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import * as _ from "lodash";

@Component({
  selector: 'app-apf-manual-risk-rates',
  templateUrl: './apf-manual-risk-rates.component.html',
  styleUrls: ['./apf-manual-risk-rates.component.scss']
})
export class ApfManualRiskRatesComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  @Input() basicInformation: any = {};
  @Input() applicationForm: any = {};
  allBranches = [];
  yesNoConst = Constants.yesNo;
  ratingModel = Constants.ratingModel;

  constructor(private cacheService: CacheService,
              private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
    this.allBranches = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
  }


  save($event, riskRate?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.modalRef = this.mdbModalService.show(ApfAddEditRiskRateComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: '',
      animated: false,
      data: {
        heading: "",
        applicationForm: this.applicationForm,
        basicInformation: this.basicInformation,
        riskRate: riskRate ? riskRate : {},
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.applicationFormAddEditService.saveOrUpdateRiskRate(data);
      }
    });
  }

  ngOnDestroy(): void {
  }

  remove($event, cribAttachment) {

    if (!_.isEmpty(cribAttachment)) {
      cribAttachment.status = Constants.statusConst.INA;
      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-30-p modal-margin-center ',
        containerClass: '',
        animated: true,
        data: {
          heading: "Confirm Remove Risk Rating",
          message: "Do you want to remove the Risk Rating Record ?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.applicationFormAddEditService.saveOrUpdateRiskRate(cribAttachment);
        }
      });
    }
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

}
