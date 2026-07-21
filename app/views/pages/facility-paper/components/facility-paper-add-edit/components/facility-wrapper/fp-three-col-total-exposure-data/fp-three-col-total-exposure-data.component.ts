import { Component, OnDestroy, OnInit } from "@angular/core";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../../core/setting/constants";
import { PrivilegeService } from "../../../../../../../../core/service/authentication/privilege.service";
import { NumberValidator } from "../../../../../../../../shared/validators/number.validator";
import { CurrencyPipe } from "@angular/common";
import { FormBuilder, FormGroup } from "@angular/forms";
import { AppUtils } from "../../../../../../../../shared/app.utils";

@Component({
  selector: "app-fp-three-col-total-exposure-data",
  templateUrl: "./fp-three-col-total-exposure-data.component.html",
  styleUrls: ["./fp-three-col-total-exposure-data.component.scss"],
})
export class FpThreeColTotalExposureDataComponent implements OnInit, OnDestroy {
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  yesNoConst = Constants.yesNoConst;
  onBaseFacilityPaperChangeSub = new Subscription();
  onCalculateFacilityPaperExposureSubj = new Subscription();
  onFormValueChangeSub = new Subscription();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  componentForm: FormGroup;
  facilityPaper: any = {};
  exposureData: any = {};
  formErrors: any = {};
  hasPrivilege = false;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private currencyPipe: CurrencyPipe,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.formErrors = {
      totalDirectExposurePrevious: {},
      totalDirectExposureNew: {},
      totalIndirectExposurePrevious: {},
      totalIndirectExposureNew: {},
      totalExposurePrevious: {},
      totalExposureNew: {},
      netTotalExposureNew: {},
      netTotalExposurePrevious: {},
      netTotalExposureExisting: {},
      existingCashMargin: {},
      outstandingCashMargin: {},
      proposedCashMargin: {},
      addTotalExposureToGroup: {},
      groupTotalDirectExposurePrevious: {},
      groupTotalDirectExposureNew: {},
      groupTotalIndirectExposurePrevious: {},
      groupTotalIndirectExposureNew: {},
      groupTotalExposurePrevious: {},
      groupTotalExposureNew: {},
      totalDirectExposureExisting: {},
      totalIndirectExposureExisting: {},
      totalExposureExisting: {},
      groupTotalDirectExposureExisting: {},
      groupTotalIndirectExposureExisting: {},
      groupTotalExposureExisting: {},
      groupExistingCashMargin: {},
      groupOutstandingCashMargin: {},
      groupProposedCashMargin: {},
      groupNetTotalExposureExisting: {},
      groupNetTotalExposurePrevious: {},
      groupNetTotalExposureNew: {}
    };

    this.hasPrivilege = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT
    );

    this.componentForm = this.createForm();

    this.onBaseFacilityPaperChangeSub =
      this.facilityPaperAddEditService.onBaseFacilityPaperChange.subscribe(
        (baseFacility) => {
          if (baseFacility) {
            this.facilityPaper = baseFacility;
            this.exposureData = this.facilityPaper;
            this.componentForm = this.createForm();
            this.isEqualLoginAndAssignUser();
          }
        }
      );

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formErrors = AppUtils.getFormErrors(
          this.componentForm,
          this.formErrors
        );
      }
    );
    this.onCalculateFacilityPaperExposureSubj =
      this.facilityPaperAddEditService.onCalculateFacilityPaperExposureChange.subscribe(
        (response: any) => {
          if(this.facilityPaper.facilityPaperID == response.facilityPaperID) { 
            this.exposureData = Object.assign({}, this.exposureData, response, {
              addTotalExposureToGroup: "Y",
            });
            this.componentForm = this.createForm();
          }
         
        }
      );

    // this.onCalculateFacilityPaperExposureSubj =
    //   this.facilityPaperAddEditService.onCalculateFacilityPaperExposureSubj.subscribe(
    //     (response: any) => {
    //       this.facilityPaperAddEditService.onCalculateFacilityPaperExposureChange.next(
    //         response
    //       );
    //       this.exposureData = Object.assign({}, this.exposureData, response, {
    //         addTotalExposureToGroup: "Y",
    //       });
    //       this.componentForm = this.createForm();
    //       //this.facilityPaperAddEditService.updateFacilityPaperExposure(this.exposureData);
    //       //console.log("exposureDataL: ", this.exposureData);
    //     }
    //   );
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      return false;
    }
  }

  isAbelToEdit() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !=
        this.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !=
        this.facilityPaperStatusConst.REJECTED &&
      ((this.facilityPaper.createdBy ==
        this.applicationService.getLoggedInUserUserName() &&
        this.facilityPaper.currentFacilityPaperStatus ==
          this.facilityPaperStatusConst.DRAFT) ||
        (this.facilityPaper.currentAssignUserID ==
          this.applicationService.getLoggedInUserUserID() &&
          this.hasPrivilege))
    );
  }

  recalculate() {
    let data = {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      isCommittee: this.yesNoConst.Y,
    };
    this.facilityPaperAddEditService.calculateFacilityPaperExposure(data);
  }

  createForm() {
    let isDisabled = !this.isAbelToEdit();
    return this.formBuilder.group({
      totalDirectExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.totalDirectExposureExisting
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      totalDirectExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.totalDirectExposurePrevious
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      totalDirectExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.totalDirectExposureNew)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      totalIndirectExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.totalIndirectExposureExisting
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      totalIndirectExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.totalIndirectExposurePrevious
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      totalIndirectExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.totalIndirectExposureNew)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      totalExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.totalExposureExisting)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      totalExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.totalExposurePrevious)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      totalExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.totalExposureNew)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      existingCashMargin: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.existingCashMargin)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      outstandingCashMargin: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.outstandingCashMargin)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      proposedCashMargin: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.proposedCashMargin)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      netTotalExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.netTotalExposureNew)
          ),
          disabled: isDisabled,
        },
        [NumberValidator.maxLengthOfNumber(20)],
      ],
      netTotalExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.netTotalExposurePrevious)
          ),
          disabled: isDisabled,
        },
        [NumberValidator.maxLengthOfNumber(20)],
      ],
      netTotalExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.netTotalExposureExisting)
          ),
          disabled: isDisabled,
        },
        [NumberValidator.maxLengthOfNumber(20)],
      ],

      groupTotalDirectExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalDirectExposureExisting
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupTotalDirectExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalDirectExposurePrevious
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupTotalDirectExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalDirectExposureNew
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      groupTotalIndirectExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalIndirectExposureExisting
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupTotalIndirectExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalIndirectExposurePrevious
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupTotalIndirectExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalIndirectExposureNew
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      groupTotalExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalExposureExisting
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupTotalExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupTotalExposurePrevious
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupTotalExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.groupTotalExposureNew)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      groupExistingCashMargin: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.groupExistingCashMargin)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupOutstandingCashMargin: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupOutstandingCashMargin
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupProposedCashMargin: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.groupProposedCashMargin)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],

      groupNetTotalExposureExisting: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupNetTotalExposureExisting
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupNetTotalExposurePrevious: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(
              this.exposureData.groupNetTotalExposurePrevious
            )
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
      groupNetTotalExposureNew: [
        {
          value: this.getCurrencyFormat(
            AppUtils.getMillionValue(this.exposureData.groupNetTotalExposureNew)
          ),
          disabled: isDisabled,
        },
        [
          NumberValidator.maxLengthOfNumber(20),
          NumberValidator.greaterThanOrEqualToZero,
        ],
      ],
    });
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, "", "", "1.3-3");
  }

  setCurrencyFormatValue(control) {
    const amount = this.componentForm.getRawValue()[control]
      ? this.componentForm.getRawValue()[control].replace(/,/g, "")
      : "";
    this.componentForm.patchValue({
      [control]: this.currencyPipe.transform(amount, "", "", "1.3-3"),
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onBaseFacilityPaperChangeSub.unsubscribe();
    this.onCalculateFacilityPaperExposureSubj.unsubscribe();
  }

  updateFacilityPaperExposure() {
    let facilityPaperData = Object.assign(
      {},
      this.exposureData,
      this.facilityPaper,
      this.componentForm.getRawValue()
    );
    facilityPaperData.totalDirectExposurePrevious = this.getValue(
      facilityPaperData.totalDirectExposurePrevious,
      "totalDirectExposurePrevious"
    );
    facilityPaperData.totalDirectExposureNew = this.getValue(
      facilityPaperData.totalDirectExposureNew,
      "totalDirectExposureNew"
    );
    facilityPaperData.totalIndirectExposurePrevious = this.getValue(
      facilityPaperData.totalIndirectExposurePrevious,
      "totalIndirectExposurePrevious"
    );
    facilityPaperData.totalIndirectExposureNew = this.getValue(
      facilityPaperData.totalIndirectExposureNew,
      "totalIndirectExposureNew"
    );
    facilityPaperData.totalExposurePrevious = this.getValue(
      facilityPaperData.totalExposurePrevious,
      "totalExposurePrevious"
    );
    facilityPaperData.totalExposureNew = this.getValue(
      facilityPaperData.totalExposureNew,
      "totalExposureNew"
    );
    facilityPaperData.existingCashMargin = this.getValue(
      facilityPaperData.existingCashMargin,
      "existingCashMargin"
    );
    facilityPaperData.outstandingCashMargin = this.getValue(
      facilityPaperData.outstandingCashMargin,
      "outstandingCashMargin"
    );
    facilityPaperData.proposedCashMargin = this.getValue(
      facilityPaperData.proposedCashMargin,
      "proposedCashMargin"
    );
    facilityPaperData.groupTotalDirectExposurePrevious = this.getValue(
      facilityPaperData.groupTotalDirectExposurePrevious,
      "groupTotalDirectExposurePrevious"
    );
    facilityPaperData.groupTotalDirectExposureNew = this.getValue(
      facilityPaperData.groupTotalDirectExposureNew,
      "groupTotalDirectExposureNew"
    );
    facilityPaperData.groupTotalIndirectExposurePrevious = this.getValue(
      facilityPaperData.groupTotalIndirectExposurePrevious,
      "groupTotalIndirectExposurePrevious"
    );
    facilityPaperData.groupTotalIndirectExposureNew = this.getValue(
      facilityPaperData.groupTotalIndirectExposureNew,
      "groupTotalIndirectExposureNew"
    );
    facilityPaperData.groupTotalExposurePrevious = this.getValue(
      facilityPaperData.groupTotalExposurePrevious,
      "groupTotalExposurePrevious"
    );
    facilityPaperData.groupTotalExposureNew = this.getValue(
      facilityPaperData.groupTotalExposureNew,
      "groupTotalExposureNew"
    );
    facilityPaperData.totalDirectExposureExisting = this.getValue(
      facilityPaperData.totalDirectExposureExisting,
      "totalDirectExposureExisting"
    );
    facilityPaperData.totalIndirectExposureExisting = this.getValue(
      facilityPaperData.totalIndirectExposureExisting,
      "totalIndirectExposureExisting"
    );
    facilityPaperData.totalExposureExisting = this.getValue(
      facilityPaperData.totalExposureExisting,
      "totalExposureExisting"
    );
    facilityPaperData.groupTotalDirectExposureExisting = this.getValue(
      facilityPaperData.groupTotalDirectExposureExisting,
      "groupTotalDirectExposureExisting"
    );
    facilityPaperData.groupTotalIndirectExposureExisting = this.getValue(
      facilityPaperData.groupTotalIndirectExposureExisting,
      "groupTotalIndirectExposureExisting"
    );
    facilityPaperData.groupTotalExposureExisting = this.getValue(
      facilityPaperData.groupTotalExposureExisting,
      "groupTotalExposureExisting"
    );
    facilityPaperData.netTotalExposureNew = this.getValue(
      facilityPaperData.netTotalExposureNew,
      "netTotalExposureNew"
    );
    facilityPaperData.netTotalExposurePrevious = this.getValue(
      facilityPaperData.netTotalExposurePrevious,
      "netTotalExposurePrevious"
    );
    facilityPaperData.netTotalExposureExisting = this.getValue(
      facilityPaperData.netTotalExposureExisting,
      "netTotalExposureExisting"
    );
    facilityPaperData.groupNetTotalExposureNew = this.getValue(
      facilityPaperData.groupNetTotalExposureNew,
      "groupNetTotalExposureNew"
    );
    facilityPaperData.groupNetTotalExposurePrevious = this.getValue(
      facilityPaperData.groupNetTotalExposurePrevious,
      "groupNetTotalExposurePrevious"
    );
    facilityPaperData.groupNetTotalExposureExisting = this.getValue(
      facilityPaperData.groupNetTotalExposureExisting,
      "groupNetTotalExposureExisting"
    );
    facilityPaperData.groupExistingCashMargin = this.getValue(
      facilityPaperData.groupExistingCashMargin,
      "groupExistingCashMargin"
    );
    facilityPaperData.groupOutstandingCashMargin = this.getValue(
      facilityPaperData.groupOutstandingCashMargin,
      "groupOutstandingCashMargin"
    );
    facilityPaperData.groupProposedCashMargin = this.getValue(
      facilityPaperData.groupProposedCashMargin,
      "groupProposedCashMargin"
    );

    if (this.isValid()) {
      this.facilityPaperAddEditService.updateFacilityPaperExposure(
        facilityPaperData
      );
    }
  }

  getValue(amount:any, contorl:any) {
    if (isNaN(amount)) {
     
      let amountAsNumeric = amount.replace(/,/g, "");
      if (isNaN(amountAsNumeric)) {
        this.componentForm
          .get(contorl)
          .setErrors(NumberValidator.greaterThanOrEqualToZero);
        this.formErrors[contorl].invalidInput = true;
      } else {
        this.componentForm.get(contorl).setErrors(null);
        this.formErrors[contorl].invalidInput = false;
      }
      return amountAsNumeric * 1000000;
    }
    return amount * 1000000;
  }

  isValid = () => this.componentForm.valid;

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }
  
  calculateValueWithRate(value: any, rate: any) {
    var amount: any = value ? value : 0;
    var exchangeRate: any = rate ? rate : 0;
    if (rate > 0) {
      return amount * exchangeRate;
    } else {
      return amount;
    }
  }
}
