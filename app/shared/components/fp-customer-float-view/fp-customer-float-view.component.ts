import { Component, HostListener, Input, OnInit } from "@angular/core";

@Component({
  selector: "app-fp-customer-float-view",
  templateUrl: "./fp-customer-float-view.component.html",
  styleUrls: ["./fp-customer-float-view.component.scss"],
})
export class FpCustomerFloatViewComponent implements OnInit {
  @Input("facilityPaper") facilityPaper: any;

  isFloatViewEnabled: boolean = false;
  customerName: string = "";

  constructor() {}

  ngOnInit() {
    if (this.facilityPaper && this.facilityPaper.casCustomerDTOList) {
      let primaryCustomer: any = this.facilityPaper.casCustomerDTOList.find(
        (cus: any) => cus.isPrimary === true
      );
      this.customerName =
        primaryCustomer && primaryCustomer.customerName
          ? primaryCustomer.customerName
          : "";
    }
  }

  @HostListener("window:scroll", [])
  onWindowScroll(): void {
    const scrollTop =
      window.pageYOffset ||
      document.documentElement.scrollTop ||
      document.body.scrollTop ||
      0;
    const scrollHeight =
      document.documentElement.scrollHeight -
      document.documentElement.clientHeight;
    const scrollPercentage = (scrollTop / scrollHeight) * 100;

    this.isFloatViewEnabled = scrollPercentage >= 2;
  }
}
