import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { LeadCompPartiesDTO } from "../../../interfaces/Lead-comp-parties-dto";
import { LeadComprehensiveService } from "../../../services/lead-comprehensive.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { LabelValue } from "../../../interfaces/Lead-comp-lead-main-details";
import { RelatedParty } from "../../../interfaces/save-dtos/Lead-comp-parties.save-dto";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-lead-comprehensive-parties",
  templateUrl: "./lead-comprehensive-parties.component.html",
  styleUrls: ["./lead-comprehensive-parties.component.scss"],
})
export class LeadComprehensivePartiesComponent implements OnInit {
  addRelatedPartiesForm: FormGroup;

  savePartiesObj: Subject<LeadCompPartiesDTO> =
    new Subject<LeadCompPartiesDTO>();

  mainPartyOptions: LabelValue[] = [];
  relatedPartyOptions: LabelValue[] = [];
  relationShipOptions: { value: string; label: string }[] = [];

  leadCompPartiesDTO: LeadCompPartiesDTO;
  noRelationshipSelect: boolean = false;
  content: any;
  isEnableShare: boolean;
  isEdit: boolean = false;
  editObj: LeadCompPartiesDTO;
  partyIdNameList: {
    value: number;
    label: string;
    creationType: string;
    businessType: string;
  }[] = [];

  isSaveLoading: boolean = false;
  leadPurpose: string = "";
  relatedPartiesList: LeadCompPartiesDTO[] = [];

  constructor(
    private addRelatedPartiesModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private leadComprehensiveService: LeadComprehensiveService,
    private alertService: AlertService,
  ) {}

  ngOnInit() {
    this.addRelatedPartiesForm = this.loadPartiesForm();
    this.partyIdNameList = this.content.partyIdNameList
      ? this.content.partyIdNameList
      : [];
    this.isEdit = this.content.isEdit ? this.content.isEdit : false;
    this.editObj = this.content.editObj ? this.content.editObj : {};
    this.leadPurpose = this.content.leadPurpose ? this.content.leadPurpose : "";
    this.relatedPartiesList = this.content.relatedPartiesList
      ? this.content.relatedPartiesList
      : [];
    //first time main party
    this.mainPartyOptions = this.partyIdNameList.filter(
      (option) =>
        option.creationType !==
        Constants.leadCompCreationTypeTypeConst.BUSINESS,
    );

    this.addRelatedPartiesForm.controls.mainParty.valueChanges.subscribe(
      (value: any) => {
        this.addRelatedPartiesForm.controls.relatedParty.setValue("");
        // filter partyOptions based on selected mainParty
        this.relatedPartyOptions = this.partyIdNameList.filter(
          (option) => option.value !== value,
        ); // adjust property name to your data

        //get relationShipOptions  based on selected mainParty
        let record = this.partyIdNameList.find(
          (option) => option.value === value,
        );
        if (record && record !== null && record !== undefined) {
          if (
            record.creationType ===
            Constants.leadCompCreationTypeTypeConst.BUSINESS
          ) {
            if (
              this.leadPurpose &&
              this.leadPurpose !== undefined &&
              this.leadPurpose !== null
            ) {
              this.relationShipOptions =
                Constants.businessRelationShipElements.filter(
                  (option) => option.value === this.leadPurpose,
                );
            } else {
              this.relationShipOptions = Constants.businessRelationShipElements;
            }
          } else if (
            record.creationType ===
            Constants.leadCompCreationTypeTypeConst.PERSONAL
          ) {
            this.relationShipOptions = Constants.personalRelationShipElements;
          }
        }
      },
    );

    this.addRelatedPartiesForm.controls.relatedParty.valueChanges.subscribe(
      (value: any) => {
        // filter partyOptions based on selected mainParty
        //get relationShipOptions  based on selected mainParty
        let record = this.partyIdNameList.find(
          (option) => option.value === value,
        );

        if (record && record !== null && record !== undefined) {
          if (
            record.creationType ===
            Constants.leadCompCreationTypeTypeConst.BUSINESS
          ) {
            if (
              this.leadPurpose &&
              this.leadPurpose !== undefined &&
              this.leadPurpose !== null
            ) {
              this.relationShipOptions =
                Constants.businessRelationShipElements.filter(
                  (option) => option.value === this.leadPurpose,
                );
            } else {
              this.relationShipOptions = Constants.businessRelationShipElements;
            }
          } else if (
            record.creationType ===
            Constants.leadCompCreationTypeTypeConst.PERSONAL
          ) {
            this.relationShipOptions = Constants.personalRelationShipElements;
          }
        }
      },
    );

    if (this.editObj && this.isEdit) {
      this.editPartiesForm();
    }

    this.addRelatedPartiesForm.controls.relationshipDescription.valueChanges.subscribe(
      (value: any) => {
        const share = this.addRelatedPartiesForm.get("share");
        share.setValue("");
        if (value === "LIMITED_LIABILITY") {
          this.isEnableShare = true;
          share.setValidators([
            Validators.required,
            Validators.min(0),
            Validators.max(100),
          ]);
        } else {
          this.isEnableShare = false;
          share.clearValidators();
        }
        share.updateValueAndValidity();
      },
    );
  }

  isValid() {
    return this.addRelatedPartiesForm.valid;
  }

  editPartiesForm() {
    setTimeout(() => {
      this.addRelatedPartiesForm.patchValue({
        mainParty: this.editObj.mainParty || "",
        noRelationship: this.editObj.noRelationship || "",
        relatedParty: this.editObj.relatedParty || "",
        relationshipDescription:
          this.getRelationshipDescriptionId(
            this.editObj.relationshipDescription,
          ) || "",
        share: this.editObj.share || "",
      });
    }, 100);
  }

  getRelationshipDescriptionId(label: string) {
    const item = this.relationShipOptions.find(
      (option) => option.label === label,
    );
    return item ? item.value : null;
  }

  loadPartiesForm() {
    return this.formBuilder.group({
      mainParty: [{ value: "" }, [Validators.required]],
      noRelationship: [false],
      relatedParty: [{ value: "" }],
      relationshipDescription: [{ value: "" }, [Validators.required]],
      share: [{ value: "" }],
    });
  }

  onCloseModel() {
    this.addRelatedPartiesModalRef.hide();
  }

  checkShareHolding(share: number, relatedParty: number) {
    if (relatedParty) {
      let llCSet = this.relatedPartiesList.filter(
        (rec: LeadCompPartiesDTO) => rec.relatedParty === relatedParty,
      );
      let allShare: number = 0;
      llCSet.map((rec: LeadCompPartiesDTO) => {
        if (rec.share && rec.share !== null && rec.share !== undefined) {
          allShare = parseFloat(rec.share) + allShare;
        }
      });
      allShare = allShare + share;
      if (allShare > 100) {
        return false;
      }
      return true;
    }
    return true;
  }

  save() {
    let mainParty = this.addRelatedPartiesForm.get("mainParty").value
      ? this.addRelatedPartiesForm.get("mainParty").value
      : null;
    let relationshipDescription = this.addRelatedPartiesForm.get(
      "relationshipDescription",
    ).value
      ? this.addRelatedPartiesForm.get("relationshipDescription").value
      : null;
    let relatedParty = this.addRelatedPartiesForm.get("relatedParty").value
      ? this.addRelatedPartiesForm.get("relatedParty").value
      : null;
    let share = this.addRelatedPartiesForm.get("share").value
      ? this.addRelatedPartiesForm.get("share").value
      : 0;
    if (!mainParty) {
      this.alertService.showToaster(
        "Add mainParty first!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }
    if (!relationshipDescription) {
      this.alertService.showToaster(
        "Add relationship first!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (
      relationshipDescription === "LIMITED_LIABILITY" &&
      !this.checkShareHolding(share, relatedParty)
    ) {
      this.alertService.showToaster(
        "Share holding exceed!",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    if (
      relationshipDescription === "LIMITED_LIABILITY" &&
      this.relatedPartiesList.some(
        (d: LeadCompPartiesDTO) => d.mainParty === mainParty,
      )
    ) {
      this.alertService.showToaster(
        "Selected share holder already exist.",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return;
    }

    // if (relationshipDescription === 3 && !share) {
    //   this.alertService.showToaster("Add share first!", SETTINGS.TOASTER_MESSAGES.warning)
    //   return;
    // }

    let relationship: string = this.addRelatedPartiesForm.get(
      "relationshipDescription",
    ).value;
    console.log("relationship 1: ",relationship);
    console.log("relationship 2: ",this.prepareRelationship(relationship));

    let relatedPartyId: number = this.editObj.relatedPartyId
      ? this.editObj.relatedPartyId
      : null;
    let compLeadId: number = this.content.compLeadId
      ? this.content.compLeadId
      : 0;
    let sendObj: LeadCompPartiesDTO = {
      compLeadId: compLeadId,
      mainParty: mainParty,
      relatedParty: this.addRelatedPartiesForm.get("relatedParty").value
        ? this.addRelatedPartiesForm.get("relatedParty").value
        : null,
      relationshipDescription: this.prepareRelationship(relationship),
      share: this.addRelatedPartiesForm.get("share").value,
      noRelationship: false,
    };

    let saveObj: RelatedParty = {
      relatedPartyId: relatedPartyId,
      compLeadId: compLeadId,
      mainPartnerId: mainParty,
      relatedPartnerId: this.addRelatedPartiesForm.get("relatedParty").value
        ? this.addRelatedPartiesForm.get("relatedParty").value
        : null,
      relationshipDescription: this.prepareRelationship(relationship),
      share: this.addRelatedPartiesForm.get("share").value,
      considerCrib: Constants.yesNoConst.N,
      considerAdvanceAnalysis: Constants.yesNoConst.N,
    };

    this.isSaveLoading = true;
    this.leadComprehensiveService
      .saveRelatedPartiesLead(compLeadId, saveObj)
      .then((response: any) => {
        this.isSaveLoading = false;
        if (response) {
          //get related partner id
          sendObj.relatedPartyId = response.relatedPartyId
            ? response.relatedPartyId
            : null;
          this.savePartiesObj.next(sendObj);
          this.onCloseModel();
        }
      });
  }

  getMainPartnerId(label: string): number {
    const item = this.mainPartyOptions.find((option) => option.label === label);
    return item ? item.value : null;
  }

  getRelatedPartnerId(label: string): number {
    const item = this.relatedPartyOptions.find(
      (option) => option.label === label,
    );
    return item ? item.value : null;
  }

  prepareRelationship(relationship: string) {
    switch (relationship) {
      case Constants.leadPuposeConst.SOUL_PROPRITER:
        return "Owner";
      case Constants.leadPuposeConst.PARTNERSHIP:
        return "Partner";
      case Constants.leadPuposeConst.LIMITED_LIABILITY:
        return "Director";

      default:
        return relationship;
    }
  }

  // getRelationshipDescriptionLabel(value: number) {
  //   const item = this.relationShipOptions.find(option => option.value === value);
  //   return item ? item.label : null;
  // }

  getLabelById(dropDownList: LabelValue[], value: number): string | null {
    const item = dropDownList.find((option) => option.value === value);
    return item ? item.label : null;
  }

  getFirstDropDownLable() {
    if (
      ![
        Constants.leadPuposeConst.INDIVIDUAL,
        Constants.leadPuposeConst.INDIVIDUAL_JOINT,
      ].includes(this.leadPurpose)
    ) {
      return "Related Party";
    }
    return "Main Party";
  }

  getSecondDropDownLable() {
    if (
      ![
        Constants.leadPuposeConst.INDIVIDUAL,
        Constants.leadPuposeConst.INDIVIDUAL_JOINT,
      ].includes(this.leadPurpose)
    ) {
      return "Main Party";
    }
    return "Related Party";
  }
}
