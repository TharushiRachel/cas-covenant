import { Component, OnInit, Input } from "@angular/core";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { ActivatedRoute } from "@angular/router";
import { CustomerEvaluationSaveDTO } from "src/app/shared/dto/CustomerEvaluationSaveDTO";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Component({
  selector: "app-customer-evaluation-form",
  templateUrl: "./customer-evaluation-form.component.html",
  styleUrls: ["./customer-evaluation-form.component.scss"],
})
export class CustomerEvaluationFormComponent implements OnInit {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;
  
  evaluations = [];
  facilityPaper: any;
  facilityPaperID: any;
  customerFinancialId: any;
  score: any;
  dataLoaded: boolean = false;

  @Input() customerEvaluationId: number;

  saveCustomerEvaluation: CustomerEvaluationSaveDTO =
    new CustomerEvaluationSaveDTO();

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private route: ActivatedRoute,
    private urlEncodeService: UrlEncodeService,
  ) {}

  ngOnInit() {
    this.getCustomerEvaluationForm();
  }

  getCustomerEvaluationForm() {
    this.facilityPaperAddEditService
      .getCustomerEvaluationByCIFId(this.customerEvaluationId)
      .then((data: any) => {
        this.evaluations = data;

        // this.facilityPaperAddEditService
        //   .getEvaluationScore(this.customerEvaluationId)
        //   .then((data1: any) => {
        //     this.score = data1;
        const val = localStorage.getItem("id");

        //   });
        if(this.customerEvaluationId != null){
          this.facilityPaperAddEditService
          .getEvaluationScore(this.customerEvaluationId)
          .then((data1: any) => {
            this.score = data1;
          });
        }

        this.saveOrUpdateCIFIDFromLocalStorage();

        const storedCustomerEvaluationId = localStorage.getItem(
          "customerEvaluationId"
        );
        this.customerEvaluationId = parseInt(storedCustomerEvaluationId);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  saveOrUpdateCIFIDFromLocalStorage() {
    const facilityPaperID = localStorage.getItem("facilityPaperID");
    //const customerId = localStorage.getItem("myKey");
    const customerId = this.urlEncodeService.decode(this.selectedCIFID);
    const customerEvaluationId = localStorage.getItem("customerEvaluationId");

    const data: CustomerEvaluationSaveDTO = new CustomerEvaluationSaveDTO();
    data.facilityPaperID = parseInt(facilityPaperID);

    data.customerId = customerId;
    data.customerEvaluationId = parseInt(customerEvaluationId);

    this.facilityPaperAddEditService.saveOrUpdateCIFID(data);
  }
}
