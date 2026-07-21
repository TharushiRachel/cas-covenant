import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {PrivilegeService} from "../../../../../../../../core/service/authentication/privilege.service";
import {DateService} from "../../../../../../../../core/service/application/date.service";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {BccReportingService} from "../../../../../services/bcc-reporting.service";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {Constants} from "../../../../../../../../core/setting/constants";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-bcc-risk-rating-year',
  templateUrl: './bcc-risk-rating-year.component.html',
  styleUrls: ['./bcc-risk-rating-year.component.scss']
})
export class BccRiskRatingYearComponent implements OnInit {

  modalRef: MDBModalRef;
  @Input() riskRatingYearGroup: FormGroup;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  bccRiskRatingScoreOptionSelect = Constants.bccRiskRatingScoreOptionSelect;
  @Output('addRiskRatingYearFormRow') addRiskRatingYearFormRow = new EventEmitter();
  @Output('removeRiskRatingYearFormRow') removeRiskRatingYearFormRow = new EventEmitter();
  riskRatingYearsOptionSelect;

  constructor(private privilegeService: PrivilegeService,
              private dateService: DateService,
              private mdbModalService: MDBModalService,
              public currencyPipe: CurrencyPipe,
              private bccReportingService: BccReportingService) {
  }

  ngOnInit() {
    this.riskRatingYearsOptionSelect = this.generateRiskRatingYears();
  }

  confirmRemoveRiskRatingYear(riskRatingYear) {

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
        heading: "Remove Risk Ratting",
        message: "Do you want to remove " + riskRatingYear.riskGrading + " ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.bccReportingService.saveOrUpdateRiskRatingYear(riskRatingYear);
      }
    });
  }


  addRiskRatingYearRow() {
    this.addRiskRatingYearFormRow.emit();
  }

  removeRiskRatingYear(item) {
    let bccRiskRatingYear = item.value;
    bccRiskRatingYear.status = Constants.statusConst.INA;
    this.confirmRemoveRiskRatingYear(bccRiskRatingYear);

  }

  removeRiskRatingYearRow(index) {
    this.removeRiskRatingYearFormRow.emit(index);
  }

  generateRiskRatingYears() {
    let list = [];
    let startingYear = new Date().getFullYear() - 15;
    for (let i = 0; i < 30; i++) {
      startingYear = startingYear + 1;
      let value = startingYear.toString() + "/" + (startingYear + 1).toString();
      list.push({value: value, label: value});
    }
    return list;
  }

  validateNumber(event){
    NumberValidator.validateNumber(event);
  }

}
