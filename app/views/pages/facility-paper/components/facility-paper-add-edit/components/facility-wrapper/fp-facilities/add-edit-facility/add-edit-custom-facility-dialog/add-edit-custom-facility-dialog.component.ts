import { Component, OnInit } from "@angular/core";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import * as _ from "lodash";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-add-edit-custom-facility-dialog",
  templateUrl: "./add-edit-custom-facility-dialog.component.html",
  styleUrls: ["./add-edit-custom-facility-dialog.component.scss"],
})
export class AddEditCustomFacilityDialogComponent implements OnInit {
  heading: string;
  dropdownOptionsForNotMandatoryFeilds: any[] = [];
  content: any[] = [];
  action: Subject<any> = new Subject<any>();
  customFacilityForm: FormGroup;
  isSubmitting = false;
  errors = [];
  checkedItems: any[] = [];
  customInfoCheckedItems: any[] = [];
  checkedNewItems: any[] = [];

  constructor(private mdbModalRef: MDBModalRef, public fb: FormBuilder) {}

  ngOnInit() {
    this.initForm();
  }

  onNoClick(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
  }

  initForm() {
    this.customFacilityForm = this.fb.group({
      isAddedCustomInfo: [""],
      customFacilityInfoName: [""],
    });
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  setCheckboxValues() {
    this.content.forEach((option) => {
      this.customFacilityForm
        .get("isAddedCustomInfo")
        .setValue(option.isChecked, { emitEvent: false });
    });
  }

  addCheckedItemsToTable() {
    this.action.next(this.checkedItems);
    this.mdbModalRef.hide();
  }

  isCustomInfoChecked(cftCustomFacilityInfoID: any) {
    if (_.isEmpty(this.checkedItems)) {
      if (this.checkedNewItems) {
        this.checkedNewItems = this.checkedNewItems;
        this.customInfoCheckedItems = this.checkedNewItems;

        for (let i = 0; i < this.checkedNewItems.length; i++) {
          let x = this.content.filter(
            (c) => c.cftCustomFacilityInfoID == this.checkedNewItems[i]
          );

          if (_.isEmpty(this.content.map((r) => r.facilityID))) {
          }

          if (
            _.indexOf(
              this.checkedItems.map((t) => t.cftCustomFacilityInfoID),
              x[0].cftCustomFacilityInfoID
            ) == -1
          ) {
            this.checkedItems.push(x[0]);
          }
        }
      } else {
        this.checkedNewItems = [];
      }
    }
    return _.indexOf(this.checkedNewItems, cftCustomFacilityInfoID) !== -1;
  }

  onCheckboxChange($event, option) {
    if (
      this.isCustomInfoUnChecked(
        option.cftCustomFacilityInfoID,
        this.customInfoCheckedItems
      )
    ) {
      let y = this.checkedItems.indexOf(option);

      if (y != -1) {
        this.checkedItems.splice(y, 1);
      }
    } else {
      // this.checkedItems = [this.checkedItems, option];
      if (
        _.indexOf(
          this.checkedItems.map((t) => t.cftCustomFacilityInfoID),
          option.cftCustomFacilityInfoID
        ) == -1
      ) {
        this.checkedItems.push(option);
      }
    }
  }

  isCustomInfoUnChecked(cftCustomFacilityInfoID: any, checkedNewItems: any) {
    return _.indexOf(checkedNewItems, cftCustomFacilityInfoID) !== -1;
  }
}
