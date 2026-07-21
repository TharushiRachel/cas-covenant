import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { DeviationService } from "../../services/deviation.service";

@Component({
  selector: "app-save-deviation",
  templateUrl: "./save-deviation.component.html",
  styleUrls: ["./save-deviation.component.scss"],
})
export class SaveDeviationComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  deviation: any;
  types: any[] = [];
  formData: any = {
    deviationId: 0,
    deviationType: "",
    description: "",
    status: Constants.statusConst.ACT,
  };

  errors: any = {
    deviationType: "",
    description: "",
  };

  statusOptions: any[] = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];
  typeOptions: any[] = [];
  constructor(
    private readonly deviationService: DeviationService,
    private readonly mdbModalRef: MDBModalRef,
  ) {}

  ngOnInit() {
    this.deviationService.getDeviationTypes().then((res: any[]) => {
      this.types =
        res !== null && res.length > 0
          ? res.filter((r: any) => r.status === Constants.statusConst.ACT)
          : [];
      this.typeOptions = this.types.map((d: any) => ({
        value: d.deviationType,
        label: d.deviationType,
      }));
    });

    if (this.deviation !== null) {
      this.formData = {
        deviationId: this.deviation.deviationId
          ? this.deviation.deviationId
          : 0,
        deviationType: this.deviation.deviationType
          ? this.deviation.deviationType
          : "",
        description: this.deviation.description
          ? this.deviation.description
          : "",
        status: this.deviation.status
          ? this.deviation.status
          : Constants.statusConst.ACT,
      };
    }
  }

  onClose() {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  handleSave() {
    if (
      this.formData.deviationType !== "" &&
      this.formData.description !== ""
    ) {
      let request: any = {
        ...this.formData,
        parentId: this.deviation.deviationId ? this.deviation.deviationId : 0,
      };

      this.deviationService.saveDeviation(request).then((res: any[]) => {
        this.action.next(res);
        this.onClose();
      });
    } else {
      this.errors = {
        deviationType:
          this.formData.deviationType === ""
            ? "Type description is required."
            : "",
        description:
          this.formData.description === "" ? "Description is required." : "",
      };

      setTimeout(() => {
        this.errors = {
          deviationType: "",
        };
      }, 1500);
    }
  }

  isSaveEnabled() {
    return (
      this.formData.deviationType !== null &&
      this.formData.deviationType !== "" &&
      this.formData.description !== null &&
      this.formData.description !== ""
    );
  }
}
