import { Component, OnInit } from "@angular/core";
import { CasV1Service } from "../../services/cas-v1.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ViewPaperModalComponent } from "../view-paper-modal/view-paper-modal.component";

@Component({
  selector: "app-v1-dashboard",
  templateUrl: "./v1-dashboard.component.html",
  styleUrls: ["./v1-dashboard.component.scss"],
})
export class V1DashboardComponent implements OnInit {
  accNo: any;
  facilityPapers: any[] = [];
  customerLoans: any[] = [];
  customer: any = null;
  facilityPaper: any = null;

  isPaperOpened: boolean = false;
  isDataLoaded: boolean = false;
  selectedDate: string | null = null;
  loggedInUser: any;

  tabs: any[] = [];
  currentIndex: number = 0;

  modalRef: MDBModalRef;
  constructor(
    private readonly casV1Service: CasV1Service,
    private readonly mdbModalService: MDBModalService,
    public mdbModalRef: MDBModalRef
  ) {}

  ngOnInit() {}

  handleSearch() {
    this.casV1Service.getCustomersByAcc(this.accNo).then((res: any[]) => {
      this.customerLoans = res ? res : [];
      this.isDataLoaded = res !== null && res.length > 0;
      if (res && res.length > 0) {
        for (let index = 0; index < res.length; index++) {
          let refNo: string = this.customerLoans[index].refNo;
          this.tabs.push({ index: index, title: "Ref No: " + refNo });
        }

        this.handleCustomer(0);
      }
    });
  }

  handleCustomer(index: number) {
    this.currentIndex = index;
    if (this.customerLoans.length > 0 && index < this.customerLoans.length) {
      let customer: any = this.customerLoans[index];
      this.customer = customer ? customer : null;
      this.facilityPapers = customer.facilityPapers
        ? customer.facilityPapers.reverse().map((fp: any) => ({
            ...fp,
            date: fp.facilityDate,
            statusText: fp.statusText
          }))
        : [];
    }
  }

  viewPaper(date: string) {
    let formatedDate: any = date.split("/").join("-");
    this.casV1Service
      .getFacilityPaperDetails(this.customer.refNo, formatedDate)
      .then((res: any) => {
        this.facilityPaper = {
          ...res,
          basicData: this.facilityPapers.find((fp: any) => fp.date == date),
        };
        this.selectedDate = formatedDate;
        if (res !== null) {
          this.modalRef = this.mdbModalService.show(ViewPaperModalComponent, {
            backdrop: true,
            keyboard: true,
            focus: true,
            show: false,
            ignoreBackdropClick: true,
            class: "modal-width-95-p",
            containerClass: "",
            animated: true,
            data: {
              facilityPaper: this.facilityPaper,
              customer: {
                accNo: this.customer.accNo,
                fullName: this.customer.title + " " + this.customer.lastName,
                refNo: this.customer.refNo,
                facilityDate: this.selectedDate,
                status: this.facilityPaper.basicData.statusText
              },
            },
          });
        }

        this.modalRef.content.action.subscribe((result: any) => {
          this.facilityPaper = null;
          this.modalRef.hide();
        });
      });
  }

  clearData() {
    this.isDataLoaded = false;
    this.tabs = [];
    this.customerLoans = [];
    this.customer = null;
    this.facilityPapers = [];
    this.accNo = "";
    this.facilityPaper = null;
  }
}
