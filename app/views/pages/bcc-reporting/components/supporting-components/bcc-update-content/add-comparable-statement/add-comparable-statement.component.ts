import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import * as _ from "lodash";
import { Constants } from "src/app/core/setting/constants";
import { BccReportingService } from "../../../../services/bcc-reporting.service";

@Component({
  selector: "app-add-comparable-statement",
  templateUrl: "./add-comparable-statement.component.html",
  styleUrls: ["./add-comparable-statement.component.scss"],
})
export class AddComparableStatementComponent implements OnInit {
  action: Subject<any> = new Subject<any>();

  selectedTabIndex: number = 0;
  reportingDataList: any[] = [];

  formData: any = {
    fromDate: "",
    toDate: "",
    sector: "",
    riskRating: "",
  };

  sectorList: any[] = [];
  sectorOptions: any[] = [];
  riskRatingOptions: any[] = [];
  isSectorLoading: boolean = true;

  loadedTabs = new Set<number>();

  constructor(
    private mdbModalRef: MDBModalRef,
    private readonly bccReportingService: BccReportingService
  ) {}

  ngOnInit() {
    let firstIndex:any[] = [{ value: "All", label: "All Risk Rating"}];
    this.riskRatingOptions = firstIndex.concat(Constants.riskRatingList);

    this.bccReportingService.getAllSectorData().then((res: any) => {
      this.sectorList = res ? res : [];
      this.sectorOptions.push({ value: "All", label: "All Sectors" });
      _.forEach(this.sectorList, (sectorData) => {
        this.sectorOptions.push({
          value: sectorData.referenceCode,
          label:
            sectorData.referenceCode + "-" + sectorData.referenceDescription,
        });
        this.isSectorLoading = false;
      });
    });
  }

  onClose(): void {
    this.action.next(null);
    this.bccReportingService.onFormDataChange.next({});
    this.mdbModalRef.hide();
  }

  onTabSelect(index:number) {
    this.selectedTabIndex = index;
    this.loadedTabs.add(index);
  }

  handleSubmitCommission(data: any[]) {
    this.reportingDataList = this.reportingDataList.filter(
      (d: any) => d.recordType != "C"
    );

    data.forEach((element: any) => {
      if (
        !this.reportingDataList.some(
          (d: any) => d.custId == element.custId && d.recordType == "C"
        )
      ) {
        this.reportingDataList.push(element);
      }
    });

    this.onTabSelect(2);
  }

  handleSubmitLoan(data: any[]) {
    this.reportingDataList = this.reportingDataList.filter(
      (d: any) => d.recordType != "L"
    );
    data.forEach((element: any) => {
      if (
        !this.reportingDataList.some(
          (d: any) => d.custId == element.custId && d.recordType == "L"
        )
      ) {
        this.reportingDataList.push(element);
      }
    });
   
    this.onTabSelect(2);
  }

  isGenerateBtnEnabled() {
    return this.reportingDataList.length > 0 && this.selectedTabIndex == 2;
  }

  generateReport() {
    this.action.next(this.reportingDataList);
  }
}
