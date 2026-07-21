import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {PrivilegeService} from "../../../../../../../../core/service/authentication/privilege.service";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {DateService} from "../../../../../../../../core/service/application/date.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {CurrencyPipe} from "@angular/common";
import {FormGroup} from "@angular/forms";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {BccReportingService} from "../../../../../services/bcc-reporting.service";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";
import {Subscription} from "rxjs";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-bcc-director-details',
  templateUrl: './bcc-director-details.component.html',
  styleUrls: ['./bcc-director-details.component.scss']
})
export class BccDirectorDetailsComponent implements OnInit, OnDestroy {

  @Input('facilityPaper') facilityPaper: any = {};
  @Input() companyDirectorGroup: FormGroup;
  @Output('addCompanyManagerFormRow') addCompanyManagerFormRow = new EventEmitter();
  @Output('removeCompanyManagerFormRow') removeCompanyManagerFormRow = new EventEmitter();

  modalRef: MDBModalRef;
  formErrors: any [];
  formChangeSub = new Subscription();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  civilStatus = Constants.civilStatus;
  civilStatusConst = Constants.civilStatusConst;

  optionsConstitutionTypeSelect: any = [
    {value: Constants.ConstitutionTypeConst.CHAIRMAN, label: Constants.ConstitutionType.CHAIRMAN},
    {value: Constants.ConstitutionTypeConst.DIRECTOR, label: Constants.ConstitutionType.DIRECTOR},
    {value: Constants.ConstitutionTypeConst.MANAGING_DIRECTOR, label: Constants.ConstitutionType.MANAGING_DIRECTOR},
    {value: Constants.ConstitutionTypeConst.PARTNER, label: Constants.ConstitutionType.PARTNER},
  ];

  optionsYesNoSelect: any = [
    {value: Constants.yesNoConst.Y, label: Constants.yesNo.Y},
    {value: Constants.yesNoConst.N, label: Constants.yesNo.N},
  ];

  constructor(private privilegeService: PrivilegeService,
              private dateService: DateService,
              private mdbModalService: MDBModalService,
              private currencyPipe: CurrencyPipe,
              private bccReportingService: BccReportingService,
              public currencyService: CurrencyService
  ) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.formChangeSub.unsubscribe();
  }

  getCalculatedAge(dob) {
    return this.dateService.getDateDifference(dob, 'years');
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  confirmRemoveCompanyDirector(bccCompanyDirector) {

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
        heading: "Remove Ownership / Directors",
        message: "Do you want to remove " + bccCompanyDirector.companyDirectorName + " with "
          + bccCompanyDirector.shareHolding + " (%) share holding ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.bccReportingService.saveOrUpdateCompanyDirectorDetails(bccCompanyDirector);
      }
    });
  }

  removeCompanyDirector(item) {
    let bccCompanyDirector = item.value;
    bccCompanyDirector.status = Constants.statusConst.INA;
    this.confirmRemoveCompanyDirector(bccCompanyDirector);
  }

  addCompanyManagerRow() {
    this.addCompanyManagerFormRow.emit()
  }

  removeCompanyManagerRow(index) {
    this.removeCompanyManagerFormRow.emit(index)
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }


}
