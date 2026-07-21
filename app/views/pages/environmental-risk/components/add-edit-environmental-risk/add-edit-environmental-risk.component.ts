import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { EnvironmentalRiskService } from "../../services/environmental-risk.service";

@Component({
  selector: "app-add-edit-environmental-risk",
  templateUrl: "./add-edit-environmental-risk.component.html",
  styleUrls: ["./add-edit-environmental-risk.component.scss"],
})
export class AddEditEnvironmentalRiskComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  isChildNode: boolean = false;
  isEdit: boolean = false;
  node: any = {};
  parentDesc: string = "";
  formData: any = {
    riskCategoryId: 0,
    parentCategoryId: 0,
    parentId: 0,
    description: "",
    score: "",
    type: Constants.riskRecordTypeConst.P,
    status: Constants.statusConst.ACT,
  };
  staticFormData: any = {
    description: "",
    score: "",
  };

  errors: any = {
    description: "",
    score: "",
  };

  typeOptions: any[] = [
    {
      value: Constants.riskRecordTypeConst.P,
      label: Constants.riskRecordType.P,
    },
    {
      value: Constants.riskRecordTypeConst.C,
      label: Constants.riskRecordType.C,
    },
  ];
  riskRatingOptions: any[] = Constants.esgRiskRatingList;

  constructor(
    private readonly mdbModalRef: MDBModalRef,
    private readonly environmentalRiskService: EnvironmentalRiskService
  ) {}

  ngOnInit() {
    this.riskRatingOptions = Constants.esgRiskRatingList.concat([
      {
        value: "N/A",
        label: "N/A",
      },
    ]);
    this.prepareFormData();
  }

  prepareFormData() {
    if (this.isEdit) {
      this.formData = {
        ...this.formData,
        riskCategoryId: 0,
        parentCategoryId: this.node.riskCategoryId,
        parentId: this.node.parentId,
        description: this.node.description,
        score: this.node.score,
        type: this.node.type,
        status: this.node.status,
      };
      this.staticFormData = {
        description: this.node.description,
        score: this.node.score,
      };
    } else {
      if (this.isChildNode && this.node) {
        this.parentDesc = this.node.description;
        this.formData = {
          ...this.formData,
          parentId: this.node.riskCategoryId,
        };
      }
    }
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  handleType() {}

  isChild() {
    return this.formData.type && this.formData.type == "C";
  }

  isValidScore() {
    if (this.isChild() && !this.formData.score) {
      return false;
    }

    return true;
  }

  handleSave() {
    if (this.formData.description && this.isValidScore()) {
      this.environmentalRiskService
        .saveTempRiskCategory(this.formData)
        .then((res: any) => {
          this.action.next(res);
          this.mdbModalRef.hide();
        });
    } else {
      this.errors = {
        description: !this.formData.description
          ? "Description is required"
          : "",
        score: !this.isValidScore() ? "Score is required" : "",
      };

      setTimeout(() => {
        this.errors = {
          description: "",
          score: "",
        };
      }, 2500);
    }
  }

  isSaveEnabled() {
    let formData: any = {
      description: this.formData.description,
      score: this.formData.score,
    };

    return JSON.stringify(formData) !== JSON.stringify(this.staticFormData);
  }

  clearData() {
    this.formData = {
      riskCategoryId: 0,
      parentId: 0,
      description: "",
      score: "",
      type: Constants.riskRecordType.P,
    };

    this.errors = {
      description: "",
      score: "",
    };
  }
}
