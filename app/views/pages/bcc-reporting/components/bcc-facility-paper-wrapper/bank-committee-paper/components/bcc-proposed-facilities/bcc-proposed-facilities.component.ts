import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {IMyOptions, MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {Constants} from "../../../../../../../../core/setting/constants";
import {BccReportingService} from "../../../../../services/bcc-reporting.service";

@Component({
  selector: 'app-bcc-proposed-facilities',
  templateUrl: './bcc-proposed-facilities.component.html',
  styleUrls: ['./bcc-proposed-facilities.component.scss']
})
export class BccProposedFacilitiesComponent implements OnInit {
  @Input() proposedFacilitiesGroup: FormGroup;
  @Output('addFacilityFormRow') addFacilityFormRow = new EventEmitter();
  @Output('removeFacilityFormRow') removeFacilityFormRow = new EventEmitter();

  modalRef: MDBModalRef;

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    firstDayOfWeek: 'mo',
    closeAfterSelect: true,
    showTodayBtn: true,
  };

  constructor(public currencyService: CurrencyService, private mdbModalService: MDBModalService, private bccReportingService: BccReportingService) {
  }

  ngOnInit() {
  }

  confirmRemoveCompanyDirector(facility) {
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
        heading: "Remove Facilities",
        message: "Do you want to remove the facility ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let removeFacility = Object.assign({}, {
          bccFacilityID: facility.bccFacilityID,
          boardCreditCommitteePaperID: facility.boardCreditCommitteePaperID,
          status: Constants.statusConst.INA
        });
        this.bccReportingService.saveOrUpdateBccFacilities(removeFacility);
      }
    });
  }

  removeBccFacility(item) {
    let bccCompanyDirector = item.value;
    bccCompanyDirector.status = Constants.statusConst.INA;
    this.confirmRemoveCompanyDirector(bccCompanyDirector);
  }

}
