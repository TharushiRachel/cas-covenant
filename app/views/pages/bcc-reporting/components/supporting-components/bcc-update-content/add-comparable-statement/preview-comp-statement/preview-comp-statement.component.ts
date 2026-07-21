import { CurrencyPipe } from "@angular/common";
import { Component, Input, OnInit } from "@angular/core";
import { AppUtils } from "src/app/shared/app.utils";
import { BccReportingService } from "src/app/views/pages/bcc-reporting/services/bcc-reporting.service";

@Component({
  selector: "app-preview-comp-statement",
  templateUrl: "./preview-comp-statement.component.html",
  styleUrls: ["./preview-comp-statement.component.scss"],
})
export class PreviewCompStatementComponent implements OnInit {
  @Input("reportingDataList") reportingDataList: any[] = [];

  constructor(
    private readonly bccReportingService: BccReportingService,
    public currencyPipe: CurrencyPipe,
  ) {}

  ngOnInit() {
  }

  getLimitNodeDetails(iimitB2kId: string, custId: string, foracid: string) {
    let payload: any = {
      reqId: "Req_SBF-123456",
      limitB2kId: iimitB2kId,
    };

    this.bccReportingService.getLimitNodeData(payload).then((res: any) => {
      let node: any = {
        ...res,
        nodeSanction: this.currencyPipe.transform(
          AppUtils.getMillionValue(res.nodeSanction),
          "",
          ""
        ),
        drawingPower: this.currencyPipe.transform(
          AppUtils.getMillionValue(res.drawingPower),
          "",
          ""
        ),
        liab: this.currencyPipe.transform(
          AppUtils.getMillionValue(res.liab),
          "",
          ""
        ),
      };
      this.reportingDataList = this.reportingDataList.map((rd: any) => ({
        ...rd,
        loans:
          rd.custId == custId
            ? rd.loans.map((l: any) => ({
                ...l,
                limitNode: l.foracid == foracid ? node : l.limitNode,
              }))
            : rd.loans,
      }));
    });
  }

  getFacilityType(type: string) {
    switch (type) {
      case "TL":
        return "Term Loan";

      case "WC":
        return "Working Capital";
    }
  }
}
