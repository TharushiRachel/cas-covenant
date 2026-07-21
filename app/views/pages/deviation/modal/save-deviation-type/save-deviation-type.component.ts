import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { DeviationService } from "../../services/deviation.service";

@Component({
  selector: "app-save-deviation-type",
  templateUrl: "./save-deviation-type.component.html",
  styleUrls: ["./save-deviation-type.component.scss"],
})
export class SaveDeviationTypeComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  deviation: any;
  formData: any = {
    deviationTypeId: 0,
    deviationType: "",
    status: Constants.statusConst.ACT,
  };

  errors: any = {
    deviationType: "",
  };

  statusOptions: any[] = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];
  constructor(
    private readonly deviationService: DeviationService,
    private readonly mdbModalRef: MDBModalRef,
  ) {}

  ngOnInit() {
    if (this.deviation !== null) {
      this.formData = {
        deviationTypeId: this.deviation.deviationTypeId
          ? this.deviation.deviationTypeId
          : 0,
        deviationType: this.deviation.deviationType
          ? this.deviation.deviationType
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
    if (this.formData.deviationType !== "") {
      this.deviationService
        .saveDeviationType(this.formData)
        .then((res: any[]) => {
          this.action.next(res);
          this.onClose();
        });
    } else {
      this.errors = {
        deviationType: "Type description is required.",
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
      this.formData.deviationType !== null && this.formData.deviationType !== ""
    );
  }
}
