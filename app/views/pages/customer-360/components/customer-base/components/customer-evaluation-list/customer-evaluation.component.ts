import {
  Component,
  ComponentFactoryResolver,
  OnInit,
  ViewContainerRef,
  ViewChild,
  Renderer2,
} from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { CustomerEvaluationFormComponent } from "../customer-evaluation-form/customer-evaluation-form.component";
import { Router } from "@angular/router";
import { Constants } from "src/app/core/setting/constants";
import { Subscription } from "rxjs";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
// import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: "app-customer-evaluation",
  templateUrl: "./customer-evaluation.component.html",
  styleUrls: ["./customer-evaluation.component.scss"],
})
export class CustomerEvaluationComponent implements OnInit {
  
  @LocalStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;

  evaluations = [
    {
      customerId: "",
      score: "",
      filledDate: "",
    },
  ];

  data : any;

  showComponentB = false;
  customerEvaluationId: number;

  modalRef: MDBModalRef;

  facilityStatusConst = Constants.facilityPaperStatusConst;
  onFacilityPaperChangeSubs = new Subscription();

  @ViewChild("dynamicComponentContainer", {
    read: ViewContainerRef,
    static: true,
  })
  dynamicComponentContainer: ViewContainerRef;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private mdbModalService: MDBModalService,
    private router: Router,
    private componentFactoryResolver: ComponentFactoryResolver,
    private renderer: Renderer2,
    private urlEncodeService: UrlEncodeService,
  ) {}

  ngOnInit() {
    this.getEvaluationList();
  }

  getEvaluationList() {
    this.facilityPaperAddEditService
      .getCustomerEvaluationListById()
      .then((data: any) => {
        this.evaluations = data;
      });

    this.onFacilityPaperChangeSubs =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (fp: any) => {
          if (
            fp.currentFacilityPaperStatus ==
            this.facilityStatusConst.IN_PROGRESS
          ) {
            const element = document.getElementById("data");

            //this.renderer.setStyle(element, "font-weight", "bold");
          }
        }
      );
  }

  openDialog(customerEvaluationId: number) {
    this.showComponentB = true;
    this.customerEvaluationId = customerEvaluationId;
    localStorage.setItem(
      "customerEvaluationId",
      customerEvaluationId.toString()
    );
  }

  closeDataComponent() {
    this.showComponentB = false;
    //this.facilityPaperAddEditService.deleteEvaluation();

    // const storedValue = localStorage.getItem("myKey");
    const storedValue = this.urlEncodeService.decode(this.selectedCIFID);
    const customerEvaluationId = localStorage.getItem("customerEvaluationId");
    const id = localStorage.getItem("id");

    this.data = {
      storedValue, customerEvaluationId, id
    }

    this.facilityPaperAddEditService.deleteEvaluation(this.data);
  }
}
