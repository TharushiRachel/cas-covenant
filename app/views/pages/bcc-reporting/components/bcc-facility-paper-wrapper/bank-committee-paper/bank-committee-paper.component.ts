import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../core/setting/constants";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BoardCreditCommitteePaperDTO} from "../../../dto/bcc-paper-dto";
import {CurrencyPipe} from "@angular/common";
import {BccReportingService} from "../../../services/bcc-reporting.service";
import {NumberValidator} from "../../../../../../shared/validators/number.validator";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../shared/app.utils";

@Component({
  selector: 'app-bank-committee-paper',
  templateUrl: './bank-committee-paper.component.html',
  styleUrls: ['./bank-committee-paper.component.scss']
})
export class BankCommitteePaperComponent implements OnInit, OnDestroy {

  inboxCountInterval;
  onBCCPaperChangeSub = new Subscription();
  bccFormChangeSub = new Subscription();
  bccPaper = new BoardCreditCommitteePaperDTO({});
  facilityPaper: any = {};
  bccPaperTypeConst = Constants.BCCPaperTypeConst;
  bccForm: FormGroup;
  strengthFormGroup: FormGroup;
  basicInfoFormGroup: FormGroup;
  companyDirectorFromGroup: FormGroup;
  riskRatingYearFromGroup: FormGroup;
  costOfFundsFromGroup: FormGroup;
  recommendedOptionsFromGroup: FormGroup;
  justificationFormGroup: FormGroup;
  proposedFacilitiesFromGroup: FormGroup;
  existingFacilitiesFormGroup: FormGroup;
  commonSecurityTextFromGroup: FormGroup;
  exposureFromGroup: FormGroup;
  officerApprovalFromGroup: FormGroup;
  formErrors: any;
  bccCompanyDirectorDTOS: FormArray;
  bccRiskRatingYearDTOS: FormArray;
  proposedFacilities: FormArray;
  existingFacilities: FormArray;
  bccCustomerCribDetailDTOs: FormArray;
  primaryCustomer: any = {};
  monthOptionSelect = Constants.monthOptionSelect;
  financialStabilityOptionSelect = Constants.financialStabilityOptionSelect;
  toDay = new Date();
  currentYear = ((this.toDay.getFullYear() - 1).toString() + '/' + this.toDay.getFullYear().toString()).trim();


  constructor(private bccReportingService: BccReportingService,
              private formBuilder: FormBuilder,
              public currencyPipe: CurrencyPipe) {
    this.bccPaper = bccReportingService.bccPaperDTO;
    this.bccForm = this.createForm();
    // this.setCountIntervals()
  }

  ngOnInit() {

    this.onBCCPaperChangeSub = this.bccReportingService.onBCCPaperChange
      .subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.bccPaper = new BoardCreditCommitteePaperDTO(data);
          this.bccForm = this.createForm();
        }
      });

  }

  ngOnDestroy(): void {
    this.onBCCPaperChangeSub.unsubscribe();
    this.bccFormChangeSub.unsubscribe();
  }

  saveBCCPaper() {
    this.isValid();

    let saveObj = {
      ...this.bccPaper,
      ...this.bccForm.getRawValue().basicInfo,
      bccCustomerCribDetailDTOS: this.bccForm.getRawValue().cribDetailsGroup.bccCustomerCribDetailDTOs,

      ...this.bccForm.getRawValue().riskRatingYearGroup,

      bccCompanyDirectorDTOS: [...this.createCompanyDirectorsSaveObject(this.bccForm.getRawValue().companyDirectorGroup.bccCompanyDirectorDTOS)],

      ...this.bccForm.getRawValue().strengths,
      incomeAmountYearOne: this.getValue(this.bccForm.getRawValue().strengths.incomeAmountYearOne),
      incomeAmountYearTwo: this.getValue(this.bccForm.getRawValue().strengths.incomeAmountYearTwo),
      incomeGrowthYearOne: this.getValue(this.bccForm.getRawValue().strengths.incomeGrowthYearOne),
      incomeGrowthYearTwo: this.getValue(this.bccForm.getRawValue().strengths.incomeGrowthYearTwo),
      profitAfterTaxAmountYearOne: this.getValue(this.bccForm.getRawValue().strengths.profitAfterTaxAmountYearOne),
      profitAfterTaxGrowthYearOne: this.getValue(this.bccForm.getRawValue().strengths.profitAfterTaxGrowthYearOne),
      profitAfterTaxAmountYearTwo: this.getValue(this.bccForm.getRawValue().strengths.profitAfterTaxAmountYearTwo),
      profitAfterTaxGrowthYearTwo: this.getValue(this.bccForm.getRawValue().strengths.profitAfterTaxGrowthYearTwo),


      ...this.bccForm.getRawValue().costOfFunds,
      monthlyCostOfFundsLkr: this.getValue(this.bccForm.getRawValue().costOfFunds.monthlyCostOfFundsLkr),
      monthlyCostOfFundsFcy: this.getValue(this.bccForm.getRawValue().costOfFunds.monthlyCostOfFundsFcy),
      cumulativeCostOfFundsFcy: this.getValue(this.bccForm.getRawValue().costOfFunds.cumulativeCostOfFundsFcy),
      cumulativeCostOfFundsLkr: this.getValue(this.bccForm.getRawValue().costOfFunds.cumulativeCostOfFundsLkr),
      incrementalCostOfFundsLkr: this.getValue(this.bccForm.getRawValue().costOfFunds.incrementalCostOfFundsLkr),
      incrementalCostOfFundsFcy: this.getValue(this.bccForm.getRawValue().costOfFunds.incrementalCostOfFundsFcy),

      ...this.bccForm.getRawValue().recommendedOptions,
      ...this.bccForm.getRawValue().justificationGroup,

      bccFacilityDTOS: [
        ...this.createBccFacilitiesSaveObject(this.bccForm.getRawValue().proposedFacilitiesGroup.proposedFacilities),
        ...this.createBccFacilitiesSaveObject(this.bccForm.getRawValue().existingFacilitiesGroup.existingFacilities)
      ],

      ...this.bccForm.getRawValue().commonSecurityTextGroup,

      proposedOutstandingAtDate: this.bccForm.getRawValue().proposedFacilitiesGroup.proposedOutstandingAtDate,
      proposedFacilityTotal: this.getValue(this.bccForm.getRawValue().proposedFacilitiesGroup.proposedFacilityTotal),
      facilityDateOfApproval: this.bccForm.getRawValue().proposedFacilitiesGroup.facilityDateOfApproval,
      existingOutstandingAtDate: this.bccForm.getRawValue().existingFacilitiesGroup.existingOutstandingAtDate,
      existingFacilityTotal: this.getValue(this.bccForm.getRawValue().existingFacilitiesGroup.existingFacilityTotal),
      existingPlusProposedTotal: this.getValue(this.bccForm.getRawValue().existingFacilitiesGroup.existingPlusProposedTotal),

      bccExposureCompanyDTO: this.createBccExposureCompanySaveObject(this.bccForm.getRawValue().exposureGroup.bccExposureCompanyDTO),
      bccExposureGroupDTO: this.createBccExposureGroupSaveObject(this.bccForm.getRawValue().exposureGroup.bccExposureGroupDTO),

      ...this.bccForm.getRawValue().officerApprovalGroup,

    };
    this.bccReportingService.updateBCCPaper(saveObj);
  };

  createForm() {

    this.bccForm = this.formBuilder.group({
      basicInfo: this.formBuilder.group({
        branchCode: [this.bccPaper.branchCode],
        businessProfile: [this.bccPaper.businessProfile],
        customerName: [this.bccPaper.customerName],
        startedCapital: [this.bccPaper.startedCapital],
        marketPosition: [this.bccPaper.marketPosition],
        businessManagementStrength: [this.bccPaper.businessManagementStrength],
        securityCover: [this.bccPaper.securityCover],
      }),

      companyDirectorGroup: this.formBuilder.group({
        bccCompanyDirectorDTOS: this.formBuilder.array(this.createCompanyDirectorFormArray()),
      }),

      riskRatingYearGroup: this.formBuilder.group({
        bccRiskRatingYearDTOS: this.formBuilder.array(this.createRiskRatingYearFormArray()),
      }),

      cribDetailsGroup: this.formBuilder.group({
        bccCustomerCribDetailDTOs: this.formBuilder.array(this.createBccCustomerCribDetailDTOSFormArray()),
      }),

      strengths: this.formBuilder.group({
        financialStability: [this.bccPaper.financialStability],
        auditorName: [this.bccPaper.auditorName],

        financialYearOne: [this.bccPaper.financialYearOne ? this.bccPaper.financialYearOne : this.currentYear],
        financialYearOneFinancial: [this.bccPaper.financialYearOneFinancial ? this.bccPaper.financialYearOneFinancial : this.financialStabilityOptionSelect[0].value],
        financialYearTwo: [this.bccPaper.financialYearTwo ? this.bccPaper.financialYearTwo : this.currentYear],
        financialYearTwoFinancial: [this.bccPaper.financialYearTwoFinancial ? this.bccPaper.financialYearTwoFinancial : this.financialStabilityOptionSelect[0].value],

        incomeAmountYearOne: [this.getFormattedValue(this.bccPaper.incomeAmountYearOne), [NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
        incomeGrowthYearOne: [this.getFormattedValue(this.bccPaper.incomeGrowthYearOne), [NumberValidator.isPercentageValue]],
        incomeAmountYearTwo: [this.getFormattedValue(this.bccPaper.incomeAmountYearTwo), [NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
        incomeGrowthYearTwo: [this.getFormattedValue(this.bccPaper.incomeGrowthYearTwo), [NumberValidator.isPercentageValue]],
        profitAfterTaxAmountYearOne: [this.getFormattedValue(this.bccPaper.profitAfterTaxAmountYearOne), [NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
        profitAfterTaxGrowthYearOne: [this.getFormattedValue(this.bccPaper.profitAfterTaxGrowthYearOne), [NumberValidator.isPercentageValue]],
        profitAfterTaxAmountYearTwo: [this.getFormattedValue(this.bccPaper.profitAfterTaxAmountYearTwo), [NumberValidator.maxLengthOfNumber(18), NumberValidator.isCommaSeparatedValue]],
        profitAfterTaxGrowthYearTwo: [this.getFormattedValue(this.bccPaper.profitAfterTaxGrowthYearTwo), [NumberValidator.isPercentageValue]],

        interestCover: [this.bccPaper.interestCover],
        gearing: [this.bccPaper.gearing],
        netInterestMargin: [this.bccPaper.netInterestMargin],
        grossNPL: [this.bccPaper.grossNPL],

      }),

      costOfFunds: this.formBuilder.group({
        costOfFundMonth: [this.bccPaper.costOfFundMonth ? this.bccPaper.costOfFundMonth : this.monthOptionSelect[0].value],
        costOfFundYear: [this.bccPaper.costOfFundYear ? this.bccPaper.costOfFundYear : new Date().getFullYear()],
        monthlyCostOfFundsLkr: [this.getFormattedValue(this.bccPaper.monthlyCostOfFundsLkr), [NumberValidator.isPercentageValue]],
        monthlyCostOfFundsFcy: [this.getFormattedValue(this.bccPaper.monthlyCostOfFundsFcy), [NumberValidator.isPercentageValue]],
        cumulativeCostOfFundsLkr: [this.getFormattedValue(this.bccPaper.cumulativeCostOfFundsLkr), [NumberValidator.isPercentageValue]],
        cumulativeCostOfFundsFcy: [this.getFormattedValue(this.bccPaper.cumulativeCostOfFundsFcy), [NumberValidator.isPercentageValue]],
        incrementalCostOfFundsLkr: [this.getFormattedValue(this.bccPaper.incrementalCostOfFundsLkr), [NumberValidator.isPercentageValue]],
        incrementalCostOfFundsFcy: [this.getFormattedValue(this.bccPaper.incrementalCostOfFundsFcy), [NumberValidator.isPercentageValue]],
      }),

      recommendedOptions: this.formBuilder.group({
        recommendation: [this.bccPaper.recommendation],
        recommendationRemark: [this.bccPaper.recommendationRemark]
      }),

      justificationGroup: this.formBuilder.group({
        justification: [this.bccPaper.justification],
        riskBasedPricing: [this.bccPaper.riskBasedPricing]
      }),

      proposedFacilitiesGroup: this.formBuilder.group({
        proposedFacilities: this.formBuilder.array(this.createProposedFacilityFormArray()),
        proposedOutstandingAtDate: [this.bccPaper.proposedOutstandingAtDate],
        proposedFacilityTotal: [this.getFormattedValue(AppUtils.getMillionValue(this.bccPaper.proposedFacilityTotal))],
        facilityDateOfApproval: [this.bccPaper.facilityDateOfApproval]
      }),

      existingFacilitiesGroup: this.formBuilder.group({
        existingFacilities: this.formBuilder.array(this.createExistingFacilityFormArray()),
        existingOutstandingAtDate: [this.bccPaper.existingOutstandingAtDate],
        existingFacilityTotal: [this.getFormattedValue(AppUtils.getMillionValue(this.bccPaper.existingFacilityTotal))],
        existingPlusProposedTotal: [this.getFormattedValue(AppUtils.getMillionValue(this.bccPaper.existingPlusProposedTotal))]
      }),

      commonSecurityTextGroup: this.formBuilder.group({
        commonSecurityText: [this.bccPaper.commonSecurityText],
        note: [this.bccPaper.note],
      }),

      exposureGroup: this.formBuilder.group({
        bccExposureCompanyDTO: this.createBccExposureCompanyFormGroup(this.bccPaper.bccExposureCompanyDTO),
        bccExposureGroupDTO: this.createBccExposureGroupFormGroup(this.bccPaper.bccExposureGroupDTO)
      }),

      officerApprovalGroup: this.formBuilder.group({
        exceptions: [this.bccPaper.exceptions],
        officerOne: [this.bccPaper.officerOne],
        officerTwo: [this.bccPaper.officerTwo],
        officerThree: [this.bccPaper.officerThree],
        officerFour: [this.bccPaper.officerFour],
        officerOneDesignation: [this.bccPaper.officerOneDesignation],
        officerTwoDesignation: [this.bccPaper.officerTwoDesignation],
        officerThreeDesignation: [this.bccPaper.officerThreeDesignation],
        officerFourDesignation: [this.bccPaper.officerFourDesignation],
        eacMemberOne: [this.bccPaper.eacMemberOne],
        eacMemberTwo: [this.bccPaper.eacMemberTwo],
        eacMemberThree: [this.bccPaper.eacMemberThree],
        eacMemberFour: [this.bccPaper.eacMemberFour],
        eacMemberOneDesignation: [this.bccPaper.eacMemberOneDesignation],
        eacMemberTwoDesignation: [this.bccPaper.eacMemberTwoDesignation],
        eacMemberThreeDesignation: [this.bccPaper.eacMemberThreeDesignation],
        eacMemberFourDesignation: [this.bccPaper.eacMemberFourDesignation],
      }),

    });
    return this.bccForm;
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  isValid() {
    return this.bccForm.valid;
  }

  createCompanyDirector(companyDirector): FormGroup {
    return this.formBuilder.group({
      bccCompanyDirectorID: [companyDirector.bccCompanyDirectorID],
      boardCreditCommitteePaperID: [companyDirector.boardCreditCommitteePaperID],
      companyDirectorName: [companyDirector.companyDirectorName, Validators.required],
      constitutionType: [companyDirector.constitutionType],
      nicOrBRN: [companyDirector.nicOrBRN],
      creditCard: [companyDirector.creditCard],
      shareHolding: [this.getFormattedValue(companyDirector.shareHolding), [NumberValidator.isPercentageValue]],
      status: [companyDirector.status],
    });
  }

  createRiskRatingYear(riskRatingYear): FormGroup {
    return this.formBuilder.group({
      bccRiskRatingYearID: [riskRatingYear.bccRiskRatingYearID],
      boardCreditCommitteePaperID: [riskRatingYear.boardCreditCommitteePaperID],
      riskGrading: [riskRatingYear.riskGrading],
      riskScore: [riskRatingYear.riskScore, [NumberValidator.isPercentageValue]],
      riskYear: [riskRatingYear.riskYear],
      status: [riskRatingYear.status],
    });
  }

  createRiskRatingYearFormArray() {
    let riskRatingYears = [];
    this.bccPaper.bccRiskRatingYearDTOS.forEach(riskRatingYear => {
      riskRatingYears.push(this.createRiskRatingYear(riskRatingYear))
    });
    return riskRatingYears;
  }

  createEmptyRiskRatingYearFormRecord() {
    let boardCreditCommitteePaperID = this.bccPaper.boardCreditCommitteePaperID;
    let status = Constants.statusConst.ACT;
    return this.createRiskRatingYear({boardCreditCommitteePaperID, status});
  }

  addRiskRatingYearFormRow() {
    this.bccRiskRatingYearDTOS = this.bccForm.get('riskRatingYearGroup').get('bccRiskRatingYearDTOS') as FormArray;
    this.bccRiskRatingYearDTOS.push(this.createEmptyRiskRatingYearFormRecord());
  }

  removeRiskRatingYearFromRow(index) {
    this.bccRiskRatingYearDTOS = this.bccForm.get('riskRatingYearGroup').get('bccRiskRatingYearDTOS') as FormArray;
    this.bccRiskRatingYearDTOS.removeAt(index);
  }

  createCompanyDirectorFormArray() {
    let companyManagers = [];
    this.bccPaper.bccCompanyDirectorDTOS.forEach(companyManager => {
      companyManagers.push(this.createCompanyDirector(companyManager))
    });
    return companyManagers;
  }

  createEmptyCompanyManagerFormRecord() {
    let boardCreditCommitteePaperID = this.bccPaper.boardCreditCommitteePaperID;
    let status = Constants.statusConst.ACT;
    return this.createCompanyDirector({boardCreditCommitteePaperID, status});
  }

  addCompanyManagerFromRow() {
    this.bccCompanyDirectorDTOS = this.bccForm.get('companyDirectorGroup').get('bccCompanyDirectorDTOS') as FormArray;
    this.bccCompanyDirectorDTOS.push(this.createEmptyCompanyManagerFormRecord());
  }

  removeCompanyManagerFormRow(index) {
    this.bccCompanyDirectorDTOS = this.bccForm.get('companyDirectorGroup').get('bccCompanyDirectorDTOS') as FormArray;
    this.bccCompanyDirectorDTOS.removeAt(index);
  }

  addProposedFacilityFromRow() {
    let boardCreditCommitteePaperID = this.bccPaper.boardCreditCommitteePaperID;
    let status = Constants.statusConst.ACT;
    let bccFacilityType = Constants.BCCFacilityTypeConst.PROPOSED;

    this.proposedFacilities = this.bccForm.get('proposedFacilitiesGroup').get('proposedFacilities') as FormArray;
    this.proposedFacilities.push(this.createFacilities({boardCreditCommitteePaperID, bccFacilityType, status}));
  }

  addRemoveProposedFacilityFromRow(index) {
    this.proposedFacilities = this.bccForm.get('proposedFacilitiesGroup').get('proposedFacilities') as FormArray;
    this.proposedFacilities.removeAt(index);
  }

  addExistingFacilityFromRow() {
    let boardCreditCommitteePaperID = this.bccPaper.boardCreditCommitteePaperID;
    let status = Constants.statusConst.ACT;
    let bccFacilityType = Constants.BCCFacilityTypeConst.EXISTING;

    this.existingFacilities = this.bccForm.get('existingFacilitiesGroup').get('existingFacilities') as FormArray;
    this.existingFacilities.push(this.createFacilities({boardCreditCommitteePaperID, bccFacilityType, status}));
  }

  addRemoveExistingFacilityFromRow(index) {
    this.existingFacilities = this.bccForm.get('existingFacilitiesGroup').get('existingFacilities') as FormArray;
    this.existingFacilities.removeAt(index);
  }

  createFacilities(facility): FormGroup {
    return this.formBuilder.group({
      boardCreditCommitteePaperID: [facility.boardCreditCommitteePaperID],
      bccFacilityID: [facility.bccFacilityID],
      type: [facility.type],
      dateGranted: [facility.dateGranted],
      amount: [Number(facility.amount) ? this.getFormattedValue(AppUtils.getMillionValue(Number(facility.amount))) : facility.amount],
      interestRate: [facility.interestRate],
      purpose: [facility.purpose],
      outstandingAsAt: [Number(facility.outstandingAsAt) ? this.getFormattedValue(AppUtils.getMillionValue(Number(facility.outstandingAsAt))) : facility.outstandingAsAt],
      settlementDate: [facility.settlementDate],
      settlementPlan: [facility.settlementPlan],
      security: [facility.security],
      status: [facility.status],
      bccFacilityType: [facility.bccFacilityType]
    });
  }

  createProposedFacilityFormArray() {
    let proposedFacilities = [];
    this.bccPaper.bccFacilityDTOS.forEach(facility => {
      if (facility.bccFacilityType == Constants.BCCFacilityTypeConst.PROPOSED) {
        proposedFacilities.push(this.createFacilities(facility));
      }
    });
    return proposedFacilities;
  }


  createExistingFacilityFormArray() {
    let existingFacilities = [];
    this.bccPaper.bccFacilityDTOS.forEach(facility => {
      if (facility.bccFacilityType == Constants.BCCFacilityTypeConst.EXISTING) {
        existingFacilities.push(this.createFacilities(facility));
      }
    });
    return existingFacilities;
  }

  createBccCustomerCribDetailDTOSFormArray() {
    let bccCustomerCribDetails = [];
    this.bccPaper.bccCustomerCribDetailDTOS.forEach(customerCribDetail => {
      bccCustomerCribDetails.push(this.createCustomerCribDetails(customerCribDetail));
    });
    return bccCustomerCribDetails;
  }

  createCustomerCribDetails(data): FormGroup {
    return this.formBuilder.group({
      bccCustomerCribDetailsID: [data.bccCustomerCribDetailsID],
      boardCreditCommitteePaperID: [data.boardCreditCommitteePaperID],
      borrower: [data.borrower],
      cribStatus: [data.cribStatus],
      remark: [data.remark],
      cribIssueDateStr: [data.cribIssueDateStr],
      reportDateStr: [data.reportDateStr],
      status: [data.status],
    });
  }


  addCribDetailFromRow() {
    this.bccCustomerCribDetailDTOs = this.bccForm.get('cribDetailsGroup').get('bccCustomerCribDetailDTOs') as FormArray;
    this.bccCustomerCribDetailDTOs.push(this.createCustomerCribDetails(this.bccPaper));
  }

  removeCribDetailFromRow(index) {
    this.bccCustomerCribDetailDTOs = this.bccForm.get('cribDetailsGroup').get('bccCustomerCribDetailDTOs') as FormArray;
    this.bccCustomerCribDetailDTOs.removeAt(index);
  }


  createBccExposureCompanyFormGroup(dto): FormGroup {
    return this.formBuilder.group({
      bccExposureCompanyID: [dto.bccExposureCompanyID],
      boardCreditCommitteePaperID: [dto.boardCreditCommitteePaperID],
      existingExposureDirect: [dto.existingExposureDirect],
      existingExposureIndirect: [dto.existingExposureIndirect],
      existingExposureTotal: [dto.existingExposureTotal],
      additionalExposureDirect: [dto.additionalExposureDirect],
      additionalExposureIndirect: [dto.additionalExposureIndirect],
      additionalExposureTotal: [dto.additionalExposureTotal],
      totalExposureDirect: [dto.totalExposureDirect],
      totalExposureIndirect: [dto.totalExposureIndirect],
      totalExposureTotal: [dto.totalExposureTotal],
      type: [dto.type],
      exposureSecuredBy: [dto.exposureSecuredBy],
      approvedFSV: [dto.approvedFSV],
      againstApprovedFSV: [dto.againstApprovedFSV],
      againstTotalExposure: [dto.againstTotalExposure],
      status: [dto.status],
    });
  }

  createBccExposureGroupFormGroup(dto): FormGroup {
    return this.formBuilder.group({
      bccExposureGroupID: [dto.bccExposureGroupID],
      boardCreditCommitteePaperID: [dto.boardCreditCommitteePaperID],
      existingExposureDirect: [dto.existingExposureDirect],
      existingExposureIndirect: [dto.existingExposureIndirect],
      existingExposureTotal: [dto.existingExposureTotal],
      additionalExposureDirect: [dto.additionalExposureDirect],
      additionalExposureIndirect: [dto.additionalExposureIndirect],
      additionalExposureTotal: [dto.additionalExposureTotal],
      totalExposureDirect: [dto.totalExposureDirect],
      totalExposureIndirect: [dto.totalExposureIndirect],
      totalExposureTotal: [dto.totalExposureTotal],
      type: [dto.type],
      exposureSecuredBy: [dto.exposureSecuredBy],
      approvedFSV: [dto.approvedFSV],
      againstApprovedFSV: [dto.againstApprovedFSV],
      againstTotalExposure: [dto.againstTotalExposure],
      status: [dto.status],
    });
  }

  createBccExposureGroupSaveObject(dto) {
    return {
      bccExposureGroupID: dto.bccExposureGroupID,
      boardCreditCommitteePaperID: dto.boardCreditCommitteePaperID,
      existingExposureDirect: this.getValue(dto.existingExposureDirect),
      existingExposureIndirect: this.getValue(dto.existingExposureIndirect),
      existingExposureTotal: this.getValue(dto.existingExposureTotal),
      additionalExposureDirect: this.getValue(dto.additionalExposureDirect),
      additionalExposureIndirect: this.getValue(dto.additionalExposureIndirect),
      additionalExposureTotal: this.getValue(dto.additionalExposureTotal),
      totalExposureDirect: this.getValue(dto.totalExposureDirect),
      totalExposureIndirect: this.getValue(dto.totalExposureIndirect),
      totalExposureTotal: this.getValue(dto.totalExposureTotal),
      type: dto.type,
      exposureSecuredBy: this.getValue(dto.exposureSecuredBy),
      approvedFSV: this.getValue(dto.approvedFSV),
      againstApprovedFSV: this.getValue(dto.againstApprovedFSV),
      againstTotalExposure: this.getValue(dto.againstTotalExposure),
      status: dto.status,
    }
  }

  createBccExposureCompanySaveObject(dto) {
    return {
      bccExposureCompanyID: dto.bccExposureCompanyID,
      boardCreditCommitteePaperID: dto.boardCreditCommitteePaperID,
      existingExposureDirect: this.getValue(dto.existingExposureDirect),
      existingExposureIndirect: this.getValue(dto.existingExposureIndirect),
      existingExposureTotal: this.getValue(dto.existingExposureTotal),
      additionalExposureDirect: this.getValue(dto.additionalExposureDirect),
      additionalExposureIndirect: this.getValue(dto.additionalExposureIndirect),
      additionalExposureTotal: this.getValue(dto.additionalExposureTotal),
      totalExposureDirect: this.getValue(dto.totalExposureDirect),
      totalExposureIndirect: this.getValue(dto.totalExposureIndirect),
      totalExposureTotal: this.getValue(dto.totalExposureTotal),
      type: dto.type,
      exposureSecuredBy: this.getValue(dto.exposureSecuredBy),
      approvedFSV: this.getValue(dto.approvedFSV),
      againstApprovedFSV: this.getValue(dto.againstApprovedFSV),
      againstTotalExposure: this.getValue(dto.againstTotalExposure),
      status: dto.status,
    }
  }

  createCompanyDirectorsSaveObject(companyDirectorsArray) {
    let companyDirectors = [];
    companyDirectorsArray.forEach(companyDirector => {
      companyDirectors.push({...companyDirector, shareHolding: this.getValue(companyDirector.shareHolding)});
    });
    return companyDirectors;
  }


  createBccFacilitiesSaveObject(facilityArray) {
    let facilities = [];
    facilityArray.forEach(facility => {
      facilities.push({...facility, amount: this.getValue(facility.amount)});
    });
    return facilities;
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  printPDF() {
    this.bccReportingService.getBCCReport({boardCreditCommitteePaperID: this.bccPaper.boardCreditCommitteePaperID});
  }

  setCountIntervals() {
    this.clearCountInterval();
    this.inboxCountInterval = setInterval(() => {
     // console.log("save BCC");
    }, 600);
  }

  clearCountInterval() {
    clearInterval(this.inboxCountInterval);
  }

}

