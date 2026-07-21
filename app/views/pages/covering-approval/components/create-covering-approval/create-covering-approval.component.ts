import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { IMyOptions, MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from 'rxjs';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { CaCreationDetailsDraftComponent } from '../ca-creation-details-draft/ca-creation-details-draft.component';
import { LocalStorage } from 'ngx-webstorage';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { AlertService } from 'src/app/core/service/common/alert.service';

@Component({
  selector: 'app-create-covering-approval',
  templateUrl: './create-covering-approval.component.html',
  styleUrls: ['./create-covering-approval.component.scss']
})
export class CreateCoveringApprovalComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID) selectedCovID;
  @Input() type: string;
  @Input() heading: string;
  @Input() message: string;
  @Input() data: any;

  @Output() action = new EventEmitter<any>();

  componentForm: FormGroup;
  buttonAction: string = "Submit";
  isSubmitDisabled: boolean = false;
  errorMessage: string;

  modalRef: MDBModalRef;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_TRANSACTION_ID) selectedTransactionId: string;
  @LocalStorage(SETTINGS.STORAGE.SELECTED_TRAN_DATE) selectedTranDate: string;

  private subscriptions: Subscription[] = [];

  public myDatePickerOptions: IMyOptions = {
    dateFormat: "dd/mm/yyyy",
    minYear: 1900,
    showTodayBtn: false,
    closeAfterSelect: true
  };

  constructor(
    public mdbModalRef: MDBModalRef,
    private fb: FormBuilder,
    private router: Router,
    private mdbModalService: MDBModalService,
    private coveringApprovalService: CoveringApprovalService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.action.emit(this.data);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  private initializeForm(): void {
    this.componentForm = this.fb.group({
      transactionId: ['', Validators.required],
      tranDate: ['', Validators.required]
    });
  }

  isValidForm(): boolean {
    return this.componentForm.valid && !this.isSubmitDisabled;
  }

  onTransactionIdInput(): void {
    const transactionIdControl = this.componentForm.get('transactionId');
    const uppercaseTransactionId = transactionIdControl.value.toUpperCase();
    transactionIdControl.setValue(uppercaseTransactionId, { emitEvent: false });
  }

  onSubmit(): void {
    this.errorMessage = '';
    const controls = this.componentForm.controls;

    if (this.componentForm.invalid) {
      Object.keys(controls).forEach(controlName => controls[controlName].markAsTouched());
      return;
    }

    this.buttonAction = 'Submitting...';
    this.isSubmitDisabled = true;

    const transactionId = this.componentForm.value.transactionId.trim();
    const tranDate = this.componentForm.value.tranDate.trim();
    const formattedTranDate = this.convertDateFormat(tranDate);

    this.selectedTransactionId = transactionId;
    this.selectedTranDate = formattedTranDate;

    this.coveringApprovalService.getTransDetails(transactionId, formattedTranDate).then(async (response: any) => {
      // Check if response is an array
      if (Array.isArray(response) && response.length > 0) {
        const firstResult = response[0];

        if (firstResult.status === 'Success' && firstResult.traninquiry) {
          const filteredTraninquiry = firstResult.traninquiry.filter(item => item.schm_type === 'ODA' && item.trn_type === 'D' && item.trnStatus !== 'DELETED');
          if (filteredTraninquiry.length > 0) {
            const combinedResponse = {
              status: firstResult.status,
              data: filteredTraninquiry,
              tranDate: formattedTranDate,
              tranId: transactionId,

            };

            this.navigateToDraft(combinedResponse);
            this.mdbModalRef.hide();
          } else {
            this.handleError('No valid transaction inquiry found.');
            this.mdbModalRef.hide();
          }
        } else {

          this.handleError('Invalid Transaction Id or Date');

          this.mdbModalRef.hide();
        }
      } else {
        this.handleError('Invalid Transaction Id or Date');
        this.mdbModalRef.hide();

      }

      this.buttonAction = 'Submit';
      this.isSubmitDisabled = false;
    }).catch(error => {
      this.mdbModalRef.hide();
      this.buttonAction = 'Submit';
      this.isSubmitDisabled = false;
    });
  }



  private convertDateFormat(date: string): string {
    // Convert from DD/MM/YYYY to DD-MM-YYYY
    return date.split('/').join('-');
  }

  private handleError(message: string): void {
    this.alertService.showToaster(message, SETTINGS.TOASTER_MESSAGES.error);
    this.errorMessage = message;
    this.isSubmitDisabled = false;
    this.buttonAction = "Submit";
    this.mdbModalRef.hide();
  }

  private navigateToDraft(data: any): void {
    this.modalRef = this.mdbModalService.show(CaCreationDetailsDraftComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-dialog-scrollable',
      containerClass: '',
      animated: false,
      data: {
        heading: "Draft Covering Approval Creation",
        transactionDetails: data
      }
    });
  }

  onBack(): void {
    this.router.navigate(['/covering-approval']);
  }
}
