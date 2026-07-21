import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IMyOptions} from "ng-uikit-pro-standard";
import {BccReportingService} from "../../../../../services/bcc-reporting.service";

@Component({
  selector: 'app-bcc-basic-info',
  templateUrl: './bcc-basic-info.component.html',
  styleUrls: ['./bcc-basic-info.component.scss']
})
export class BccBasicInfoComponent implements OnInit, OnDestroy {

  @Input() strengths: FormGroup;
  @Input() basicInfo: FormGroup;
  @Input() cribDetailsGroup: FormGroup;
  @Input() companyDirectorGroup: FormGroup;
  @Input() riskRatingYearGroup: FormGroup;

  @Output() addCompanyManagerFromRow = new EventEmitter();
  @Output() removeCompanyManagerFormRow = new EventEmitter();

  @Output() addRiskRatingYearFromRow = new EventEmitter();
  @Output() removeRiskRatingYearFormRow = new EventEmitter();

  @Output('addCribDetailFromRow') addCribDetailFromRow = new EventEmitter();
  @Output('removeCribDetailFromRow') removeCribDetailFromRow = new EventEmitter();


  facilityPaper: any = {};
  onFacilityPaperChangeSubs = new Subscription();


  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    firstDayOfWeek: 'mo',
    closeAfterSelect: true,
    showTodayBtn: true,
  };

  constructor(private bccReportingService: BccReportingService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.onFacilityPaperChangeSubs = this.bccReportingService.onFacilityPaperChange
      .subscribe((fp: any) => {
        this.facilityPaper = fp;
      });
  }

  ngOnDestroy(): void {
    this.onFacilityPaperChangeSubs.unsubscribe();
  }


  createUpcJustificationForm() {
    return this.formBuilder.group({
      justification: ['', [Validators.required]],
    })
  }

  addCompanyManagerRow() {
    this.addCompanyManagerFromRow.emit();
  }

  removeCompanyManagerRow($event) {
    this.removeCompanyManagerFormRow.emit($event)
  }

  addRiskRatingYearFormRow() {
    this.addRiskRatingYearFromRow.emit();
  }

  removeRiskRatingYearRow($event) {
    this.removeRiskRatingYearFormRow.emit($event)
  }

  addCribDetailRow() {
    this.addCribDetailFromRow.emit();
  }

  removeCribDetailRow($event) {
    this.removeCribDetailFromRow.emit($event)
  }

}
