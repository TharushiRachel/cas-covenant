import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {IMyDate, IMyOptions, MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {BccReportingService} from "../../../../../services/bcc-reporting.service";

@Component({
  selector: 'app-bcc-crib-details',
  templateUrl: './bcc-crib-details.component.html',
  styleUrls: ['./bcc-crib-details.component.scss']
})
export class BccCribDetailsComponent implements OnInit {
  modalRef: MDBModalRef;
  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  @Input() cribDetailsGroup: FormGroup;
  @Output('addCribDetailFromRow') addCribDetailFromRow = new EventEmitter();
  @Output('removeCribDetailRow') removeCribDetailRow = new EventEmitter();


  optionsCribStatusSelect = [
    {value: this.cribStatusConst.NOT_ENTERED, label: this.cribStatus.NOT_ENTERED},
    {value: this.cribStatusConst.PENDING, label: this.cribStatus.PENDING},
    {value: this.cribStatusConst.NO_IRREGULAR_ADVANCES, label: this.cribStatus.NO_IRREGULAR_ADVANCES},
    {value: this.cribStatusConst.REPORTED_AS_IRREGULAR, label: this.cribStatus.REPORTED_AS_IRREGULAR},
  ];

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate() + 1,
  };

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };

  constructor(
    private mdbModalService: MDBModalService,
    private bccReportingService: BccReportingService
  ) {
  }

  ngOnInit() {
  }

  removeCribDetail(item) {
    let bccCustomerCribDetailDTO = item.value;
    bccCustomerCribDetailDTO.status = Constants.statusConst.INA;
    this.confirmRemoveCribDetails(bccCustomerCribDetailDTO);

  }

  confirmRemoveCribDetails(CustomerCribDetails) {

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
        heading: "Remove Crib Details",
        message: "Do you want to remove ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.bccReportingService.saveOrUpdateBCCCustomerCribDetails(CustomerCribDetails);
      }
    });
  }


}
