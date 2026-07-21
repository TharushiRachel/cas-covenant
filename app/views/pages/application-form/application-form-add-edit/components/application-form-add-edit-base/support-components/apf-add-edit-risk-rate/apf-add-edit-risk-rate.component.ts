import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-add-edit-risk-rate',
  templateUrl: './apf-add-edit-risk-rate.component.html',
  styleUrls: ['./apf-add-edit-risk-rate.component.scss']
})
export class ApfAddEditRiskRateComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  action: Subject<any> = new Subject<any>();
  riskForm: FormGroup;
  allBranches = [];
  formErrors: any;
  onBranchNameChangeSub: Subscription = new Subscription();
  applicationForm: any = {};
  basicInformation: any = {};
  riskRate: any = {};
  isFormDisabled: any = false;
  results: Subject<any>;
  bccRiskRatingScoreOptionSelect = Constants.bccRiskRatingScoreOptionSelect;

  public myTime: Date = new Date();
  currentYear: any = this.myTime.getUTCFullYear();
  currentDate: any = this.myTime.getUTCDate() + 1;
  currentMonth: any = this.myTime.getUTCMonth() + 1;

  optionsRatingModelSelect = [
    {value: 'LARGE_CORPORATE', label: 'Large Corporate (LC)'},
    {value: 'MIDDLE_CORPORATE', label: 'Middle Corporate (MC)'},
    {value: 'SMALL_AND_MEDIUM_ENTERPRISES', label: 'Small and Medium Enterprises (SME)'},
    {value: 'FINANCIAL_INSTITUTION', label: 'Financial Institutions (FI) – (Banks/ NBFC)'},
    {value: 'PROJECT_FINANCE', label: 'Project Finance (SPF) – (SPF/ RE)'},
    {value: 'PERSONAL_ADVANCES_RISK_RATING_SCORE_CARD', label: 'Personal Advances Risk Rating Score Card (PA)'},
    {value: 'CASH_MARGIN', label: 'Cash Margin ( CM )'},
    {value: 'STAFF_LOANS', label: 'Staff Loans (SL)'},
    {value: 'CREDIT_CARDS_RATING', label: 'Credit Cards rating (CC)'}
  ];

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: {year: this.currentYear, month: this.currentMonth, day: this.currentDate},

  };

  constructor(
    public mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private cacheService: CacheService,
  ) {
  }

  ngOnInit() {
    this.riskForm = this.createRiskForm();
    this.allBranches = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.results = new BehaviorSubject(this.allBranches);

    this.onBranchNameChangeSub.unsubscribe();
    this.onBranchNameChangeSub = this.riskForm.controls.initiatedBranch.valueChanges
      .subscribe((value: any) => {
        this.results.next(this.filterBranchNameValueChange(value));
      });
  }

  ngOnDestroy(): void {
    this.onBranchNameChangeSub.unsubscribe();
  }

  createRiskForm() {
    this.riskForm = this.formBuilder.group({
      applicationFormID: [this.applicationForm.applicationFormID, Validators.required],
      basicInformationID: [this.basicInformation.basicInformationID, Validators.required],
      riskRateID: [this.riskRate.riskRateID, Validators.required],
      scoring: [this.riskRate.scoring, Validators.required],
      riskGrading: [this.riskRate.riskGrading ? this.riskRate.riskGrading.length == 1 ? this.riskRate.riskGrading.concat(' ') : this.riskRate.riskGrading : '', Validators.required],
      lastRatedStr: [this.riskRate.lastRatedStr, Validators.required],
      model: [this.riskRate.model, Validators.required],
      assetClassification: [this.riskRate.assetClassification, Validators.required],
      riskConfirmed: [this.riskRate.riskConfirmed ? this.riskRate.riskConfirmed == 'Y' : false],
      initiatedBranch: [this.riskRate.initiatedBranch, Validators.required],
    });
    return this.riskForm;
  }

  filterBranchNameValueChange(value: string): string[] {
    if (value) {
      const filterValue = value.toLowerCase();
      return this.allBranches.filter((item: any) => item.branchName.toLowerCase().includes(filterValue));
    } else {
      return this.allBranches;
    }
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  save(): void {
    let saveObj = {
      ...this.riskForm.getRawValue(),
      riskConfirmed: this.riskForm.controls.riskConfirmed.value ? 'Y' : 'N',
      status: Constants.statusConst.ACT
    };
    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

}
