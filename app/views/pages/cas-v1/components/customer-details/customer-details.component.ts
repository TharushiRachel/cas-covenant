import { Component, Input, OnInit } from "@angular/core";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-customer-details",
  templateUrl: "./customer-details.component.html",
  styleUrls: ["./customer-details.component.scss"],
})
export class CustomerDetailsComponent implements OnInit {
  @Input("customer") customer: any;
  formattedPaperDate: string;

  constructor(private alertService: AlertService) {}

  ngOnInit() {
    if (
      !this.customer.otherFacilities ||
      this.customer.otherFacilities.length === 0
    ) {
      // this.alertService.showToaster('No facilities found in other banks.', 'WARNING')
    }

    // Format the addDate to "dd/MM/yy"
    this.formattedPaperDate = this.formatDate(this.customer.addDate);
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);

    const day = date.getDate().toString().padStart(2, "0"); // Ensure two digits for the day
    const month = (date.getMonth() + 1).toString().padStart(2, "0"); // Ensure two digits for the month
    const year = date.getFullYear().toString().slice(2); // Extract last two digits of the year

    return `${day}/${month}/${year}`;
  }
}
