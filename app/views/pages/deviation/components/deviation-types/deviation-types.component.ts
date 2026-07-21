import { Component, OnInit } from "@angular/core";
import { Constants } from "src/app/core/setting/constants";
import { DeviationService } from "../../services/deviation.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { SaveDeviationTypeComponent } from "../../modal/save-deviation-type/save-deviation-type.component";

@Component({
  selector: "app-deviation-types",
  templateUrl: "./deviation-types.component.html",
  styleUrls: ["./deviation-types.component.scss"],
})
export class DeviationTypesComponent implements OnInit {
  tableColumns: any = ["Type Name", "Status", "Action"];
  types: any = [];
  status = Constants.status;
  statusConst = Constants.statusConst;
  modalRef: MDBModalRef;

  constructor(
    private readonly deviationService: DeviationService,
    public mdbModalService: MDBModalService,
  ) {}

  ngOnInit() {
    this.deviationService.getDeviationTypes().then((res: any[]) => {
      this.types = res !== null && res.length > 0 ? res : [];
    });
  }

  openAddEditModal(deviation?: any) {
    this.modalRef = this.mdbModalService.show(SaveDeviationTypeComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p",
      containerClass: "",
      animated: false,
      data: {
        deviation:
          deviation !== undefined && deviation !== null
            ? deviation
            : {
                deviationTypeId: 0,
                deviationType: "",
                status: Constants.statusConst.ACT,
              },
      },
    });

    this.modalRef.content.action.subscribe((res: any[]) => {
      if (res !== null) {
        this.types = res !== null && res.length > 0 ? res : [];
      }
    });
  }
}
