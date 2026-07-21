import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../../../services/facility-paper-add-edit.service";
import * as _ from "lodash";
import { Constants } from "../../../../../../../../../../core/setting/constants";
import { SETTINGS } from "../../../../../../../../../../core/setting/commons.settings";
import { AlertService } from "../../../../../../../../../../core/service/common/alert.service";
import { CurrencyService } from "../../../../../../../../../../core/service/common/currency.service";
import { NumberValidator } from "../../../../../../../../../../shared/validators/number.validator";
import { ConfirmationDialogComponent } from "../../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "../../../../../../../../../../core/service/application/application.service";
import { CustomerRatingsDto } from "../../../../../../../dto/customer-ratings-dto";

@Component({
  selector: "app-personal-customer-ratings",
  templateUrl: "./personal-customer-ratings.component.html",
  styleUrls: ["./personal-customer-ratings.component.scss"],
})
export class PersonalCustomerRatingsComponent implements OnInit {
  @Input("customer") customer: any = {};
  @Input("facilityPaper") facilityPaper: any = {};
  customerRatingsDTO: CustomerRatingsDto = new CustomerRatingsDto({});

  componentForm: FormGroup;
  isAbleToAddEdit = false;
  isDisableEdit = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
    public currencyService: CurrencyService,
    private applicationService: ApplicationService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    if (this.customer.customerRatingsDTOList.length > 0) {
      this.customerRatingsDTO = this.customer.customerRatingsDTOList[0];
    }
    this.getCustomerRatings();
    this.isEqualLoginAndAssignUser();
  }

  loadInitialComponentForm() {
    this.componentForm.disable();
    this.isDisableEdit = true;
  }

  createCustomerRatingsForm() {
    let customerRatingsID = this.customerRatingsDTO.customerRatingsID || "";
    let customerID = this.customerRatingsDTO.customerID || "";
    let casCustomerID = this.customerRatingsDTO.casCustomerID || "";
    let proposedFacilitiesROA =
      this.customerRatingsDTO.proposedFacilitiesROA || "";
    let existingFacilitiesROA =
      this.customerRatingsDTO.existingFacilitiesROA || "";
    let riskGrading = this.customerRatingsDTO.riskGrading || "";
    let riskScore = this.customerRatingsDTO.riskScore || "";

    return this.formBuilder.group({
      customerRatingsID: [
        { value: customerRatingsID, disabled: this.isDisableEdit },
        [],
      ],
      customerID: [{ value: customerID, disabled: this.isDisableEdit }, []],
      casCustomerID: [
        { value: casCustomerID, disabled: this.isDisableEdit },
        [],
      ],
      proposedFacilitiesROA: [
        {
          value: proposedFacilitiesROA
            ? this.currencyService.getFormattedAmount(proposedFacilitiesROA)
            : proposedFacilitiesROA,
          disabled: this.isDisableEdit,
        },
        [Validators.max(100), NumberValidator.isPercentageValueWithMinus],
      ],
      existingFacilitiesROA: [
        {
          value: existingFacilitiesROA
            ? this.currencyService.getFormattedAmount(existingFacilitiesROA)
            : existingFacilitiesROA,
          disabled: this.isDisableEdit,
        },
        [Validators.max(100), NumberValidator.isPercentageValueWithMinus],
      ],
      riskGrading: [
        { value: riskGrading, disabled: this.isDisableEdit },
        [Validators.pattern("^[A-Z][+-]?$")],
      ],
      riskScore: [
        {
          value: riskScore
            ? this.currencyService.getFormattedAmount(riskScore)
            : riskScore,
          disabled: this.isDisableEdit,
        },
        [Validators.max(100), NumberValidator.isPercentageValueWithMinus],
      ],
    });
  }

  onClickEdit() {
    this.componentForm.enable();
    this.isDisableEdit = false;
  }

  onClickCancelEdit() {
    this.componentForm.reset(
      {
        proposedFacilitiesROA: this.customerRatingsDTO.proposedFacilitiesROA,
        existingFacilitiesROA: this.customerRatingsDTO.existingFacilitiesROA,
        riskGrading: this.customerRatingsDTO.riskGrading,
        riskScore: this.customerRatingsDTO.riskScore,
      },
      { onlySelf: false, emitEvent: true }
    );
    this.componentForm.disable();
    this.isDisableEdit = true;
  }

  onClickUpdateBaseInfo() {
    this.loadInitialComponentForm();

    const {
      customerID,
      proposedFacilitiesROA,
      existingFacilitiesROA,
      riskGrading,
      riskScore,
    } = this.componentForm.getRawValue();
    let updateData = Object.assign({}, this.customerRatingsDTO);
    updateData.casCustomerID = this.customer.casCustomerID;
    updateData.customerID = this.customer.customerID;
    updateData.proposedFacilitiesROA =
      this.currencyService.getAmountFromFormattedString(proposedFacilitiesROA);
    updateData.existingFacilitiesROA =
      this.currencyService.getAmountFromFormattedString(existingFacilitiesROA);
    updateData.riskGrading = riskGrading;
    updateData.riskScore =
      this.currencyService.getAmountFromFormattedString(riskScore);

    this.facilityPaperAddEditService
      .saveOrUpdateCustomerRatings(updateData)
      .then((data: any) => {
        this.customer.customerRatingsDTOList[0] = data;
        this.customerRatingsDTO = this.customer.customerRatingsDTOList[0];
        this.componentForm = this.createCustomerRatingsForm();
      });
  }

  getCustomerRatings() {
    if (this.componentForm) {
      this.loadInitialComponentForm();
    }
    this.componentForm = this.createCustomerRatingsForm();
    this.loadInitialComponentForm();
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
        this.applicationService.getLoggedInUserUserID() &&
        this.applicationService.getLoggedInUserUPMGroupCode() < 71
    ) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isRejected() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED
    );
  }
}
