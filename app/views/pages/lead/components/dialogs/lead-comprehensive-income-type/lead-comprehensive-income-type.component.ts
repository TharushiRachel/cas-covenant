import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { BehaviorSubject, Subject } from 'rxjs';
import { LeadCompIncomeTypeDTO } from '../../../interfaces/Lead-comp-income-type-dto';
import { LeadComprehensiveService } from '../../../services/lead-comprehensive.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { IncomeSource } from '../../../interfaces/save-dtos/Lead-comp-imcome-type-save-dto';
import { LabelValue } from '../../../interfaces/Lead-comp-lead-main-details';

@Component({
  selector: 'app-lead-comprehensive-income-type',
  templateUrl: './lead-comprehensive-income-type.component.html',
  styleUrls: ['./lead-comprehensive-income-type.component.scss']
})
export class LeadComprehensiveIncomeTypeComponent implements OnInit {

  addIncomeTypeForm: FormGroup;
  saveIncomeSourceObj: Subject<LeadCompIncomeTypeDTO> = new Subject<LeadCompIncomeTypeDTO>();

  //dropdowns
  partyOptions: LabelValue[] = [];
  incomeTypeOptions: LabelValue[] = [
    { value: 1, label: "Salary" },
    { value: 2, label: "Rental" },
    { value: 3, label: "Business" },
    { value: 4, label: "Self Employed" },
    { value: 5, label: "Other" }
  ]

  leadCompIncomeTypeDTO: LeadCompIncomeTypeDTO;
  content: any;
  isSaveLoading: boolean = false;

  constructor(
    private incomeTypeModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private leadComprehensiveService: LeadComprehensiveService,
    private alertService: AlertService,
  ) { }

  ngOnInit() {
    this.addIncomeTypeForm = this.loadAddIncomeTypeForm();
    this.partyOptions = this.content.partyIdNameList ? this.content.partyIdNameList : [];
    this.editIncomeTypeForm()
  }

  loadAddIncomeTypeForm() {
    return this.formBuilder.group({
      party: [{ value: "" }, [Validators.required]],
      incomeType: [{ value: "" }, [Validators.required]],
      considerForRepayment: false
    });
  }

  editIncomeTypeForm() {
    setTimeout(() => {
      this.addIncomeTypeForm.patchValue({
        party: "",
        incomeType: "",
        considerForRepayment: false
      });
    }, 100);
  }

  isValid() {
    return this.addIncomeTypeForm.valid;
  }

  onCloseModel() {
    this.incomeTypeModalRef.hide()
  }

  save() {
    let party = this.addIncomeTypeForm.get('party').value ? this.addIncomeTypeForm.get('party').value
      : null;
    let incomeType = this.addIncomeTypeForm.get('incomeType').value ? this.addIncomeTypeForm.get('incomeType').value
      : null;
    if (!party) {
      this.alertService.showToaster("Add party first!", SETTINGS.TOASTER_MESSAGES.warning)
      return;
    }
    if (!incomeType) {
      this.alertService.showToaster("Add incomeType first!", SETTINGS.TOASTER_MESSAGES.warning)
      return;
    }

    let compLeadId: number = this.content.compLeadId ? this.content.compLeadId : 0
    let sendObj: LeadCompIncomeTypeDTO = {
      party: party,
      incomeType: this.getIncomeTypeLabel(incomeType),
      considerForRepayment: this.addIncomeTypeForm.get('considerForRepayment').value ? true : false,
    }

    let saveObj: IncomeSource = {
      compPartyId: party,
      incomeType: this.getIncomeTypeLabel(incomeType),
      considerForRepayment: this.addIncomeTypeForm.get('considerForRepayment').value ? 'Y' : 'N'
    }
    this.isSaveLoading = true;
    this.leadComprehensiveService.saveIncomeSourceLead(compLeadId, [saveObj]).then((response: any) => {
      this.isSaveLoading = false;
      if (response) {
        //get incomeSourceId
        sendObj.incomeSourceId = response[0].incomeSourceId ? response[0].incomeSourceId : null;
        // emit data to parent
        this.saveIncomeSourceObj.next(sendObj);
        this.onCloseModel()
      }
    });
  }
  getIncomeTypeLabel(value: number) {
    const item = this.incomeTypeOptions.find(option => option.value === value);
    return item ? item.label : null;
  }
}

