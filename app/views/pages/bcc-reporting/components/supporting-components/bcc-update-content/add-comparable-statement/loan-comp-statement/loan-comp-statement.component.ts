import { CurrencyPipe } from "@angular/common";
import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  Renderer2,
  ViewChild,
} from "@angular/core";
import * as _ from "lodash";
import * as moment from "moment";
import { IMyOptions } from "ng-uikit-pro-standard";
import { PageSize } from "src/app/core/dto/page.size";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { AppUtils } from "src/app/shared/app.utils";
import { BccReportingService } from "src/app/views/pages/bcc-reporting/services/bcc-reporting.service";

@Component({
  selector: "app-loan-comp-statement",
  templateUrl: "./loan-comp-statement.component.html",
  styleUrls: ["./loan-comp-statement.component.scss"],
})
export class LoanCompStatementComponent implements OnInit {
  @ViewChild("modalBody", { static: true }) modalBody!: ElementRef;

  isLoanSubmitEnabled: boolean = false;
  isLoanSorted: boolean = false;
  isLoanLoading: boolean = true;
  isLoanSearched: boolean = false;
  isLoanCollapsed: boolean = true;
  isPGLoading: boolean = false;

  fromDate: any = "";
  toDate: any = "";
  rateCodeTypes: string[] = [];
  productGroups: any[] = [];
  rateFrom: any = "";
  rateTo: any = "";
  sector: string = "";
  riskRating: string = "";
  productGroup: string = "";
  isInterestRate: boolean = true;
  parentCodeType: string = Constants.interestRateCodeType.FIXED;
  childCodeType: string = Constants.interestRateCodeType.AWPLR;
  account: string = "";
  cifId: string = "";

  today: any = moment().format("YYYY-MM-DD");
  public myDatePickerOptions: IMyOptions = {
    dateFormat: "yyyy-mm-dd",
    minYear: new Date().getFullYear() - 5,
    maxYear: new Date().getFullYear(),
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
    editableDateField: false,
    disableSince: {
      year: this.today ? this.today.split("-")[0] : 0,
      month: this.today ? this.today.split("-")[1] : 0,
      day: this.today ? this.today.split("-")[2] : 0,
    },
  };

  facilityTypeOptions: any[] = [
    { value: "TL", label: "Term Loan" },
    { value: "WC", label: "Working Capital" },
  ];

  reportingDataStaticList: any[] = [];
  reportingDataList: any[] = [];
  selectedAccounts: any[] = [];
  @Input("sectorList") sectorList: any[] = [];
  @Input("sectorOptions") sectorOptions: any[] = [];
  @Input("riskRatingOptions") riskRatingOptions: any[];
  rateCodeOptions: any[] = Constants.interestRateCodeList;

  interestRateCodeType: any = Constants.interestRateCodeType;

  @Output("handleSubmitLoan") handleSubmitLoan = new EventEmitter();

  parentPageSize = new PageSize();

  constructor(
    private readonly bccReportingService: BccReportingService,
    private readonly alertService: AlertService,
    public currencyPipe: CurrencyPipe,
    private readonly renderer: Renderer2,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.getProductGroups();
    this.sector = this.sectorOptions[0].value;
    this.riskRating = this.riskRatingOptions[0].value;
  }

  handleCollapsed() {
    this.isLoanCollapsed = !this.isLoanCollapsed;
  }

  clearSearchData() {
    let emptyArr: any[] = [];
    this.fromDate = "";
    this.toDate = "";
    this.rateCodeTypes = emptyArr;
    this.rateFrom = "";
    this.rateTo = "";
    this.riskRating =
      this.riskRatingOptions.length > 0 ? this.riskRatingOptions[0].value : "";
    this.productGroup =
      this.productGroups.length > 0 ? this.productGroups[0].value : "";
    this.sector =
      this.sectorOptions.length > 0 ? this.sectorOptions[0].value : "";
    this.parentCodeType = Constants.interestRateCodeType.FIXED;
    this.childCodeType = Constants.interestRateCodeType.AWPLR;

    this.reportingDataList = emptyArr;
    this.reportingDataStaticList = emptyArr;
    this.selectedAccounts = emptyArr;

    this.account = "";
    this.cifId = "";

    this.isLoanSorted = false;
    this.isLoanSubmitEnabled = false;
    this.isLoanSearched = false;

    this.toggleOverflow();
  }

  handleCodeType() {
    this.rateCodeTypes = [];
    this.rateFrom = "";
    this.rateTo = "";
  }

  getCodesByType() {
    return this.rateCodeOptions.filter(
      (r: any) => r.parentType == this.childCodeType
    );
  }

  getProductGroups() {
    this.isPGLoading = true;
    this.bccReportingService.getProductGroups().then((res: any[]) => {
      this.productGroups = res.map((d: any) => ({
        value: d.RefCode,
        label: d.RefDesc1,
      }));

      this.productGroups.splice(0, 0, {
        value: "All",
        label: "All Loan Products",
      });

      this.productGroup = this.productGroups[0].value;
      this.isPGLoading = false;
    });
  }

  handleSelectCodeType(type: any) {
    if (!this.rateCodeTypes.some((d: any) => d == type)) {
      this.rateCodeTypes.push(type);
    } else {
      this.rateCodeTypes = this.rateCodeTypes.filter((d: any) => d != type);
    }
  }

  handleSearch() {
    if (
      this.isValidDateRange() &&
      this.isValidInterestRateRange() &&
      !this.isCodesEmpty()
    ) {
      let payload: any = {
        dateRanges: this.prepareDateRanges(),
        rateFrom: parseFloat(this.rateFrom),
        rateTo: parseFloat(this.rateTo),
        sector: this.sector,
        riskRating: this.riskRating,
        productGroup: this.productGroup,
        rateCodeTypes: this.rateCodeTypes,
        isInterestRate: this.parentCodeType == this.interestRateCodeType.FIXED,
        isAllSector: this.sector == "All" || this.sector == "",
        isAllRiskRating: this.riskRating == "All" || this.riskRating == "",
        isAllProductGroup:
          this.productGroup == "All" || this.productGroup == "",
      };

      this.bccReportingService.getCompReportData(payload).then((resp: any) => {
        let result: any[] = resp ? resp : [];
        let preparedResult: any[] = this.prepareReportData(result);

        if (preparedResult.length > 0) {
          this.reportingDataList = this.filterReportingData(
            this.reportingDataList,
            preparedResult
          );
          this.reportingDataStaticList = this.filterReportingData(
            this.reportingDataStaticList,
            preparedResult
          );

          this.parentPageSize.length = this.reportingDataList.length;
          this.parentPageSize.pageSizeOptions = [10];

          if (!this.isLoanSearched) {
            this.isLoanSearched = true;
            this.toggleOverflow();
          }
        } else {
          this.alertService.showToaster(
            "There no data found.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
        }
      });
    } else {
      let message: string = !this.isValidDateRange()
        ? "Check the range of dates."
        : this.isCodesEmpty()
        ? "Please Select the preferential code(s)."
        : !this.isValidInterestRateRange()
        ? "Check the iterest rate range."
        : "Please check the required data.";
      this.alertService.showToaster(message, SETTINGS.TOASTER_MESSAGES.error);
    }
  }

  filterReportingData(prevList: any[], newList: any[]) {
    if (prevList.length > 0) {
      newList.forEach((element: any) => {
        if (prevList.some((prev: any) => prev.custId == element.custId)) {
          let prevCustomer: any = prevList.find(
            (prev: any) => prev.custId == element.custId
          );
          let prevLoans: any[] = prevCustomer.loans;

          element.loans.forEach((newLoan: any) => {
            if (!prevLoans.some((pl: any) => pl.foracid == newLoan.foracid)) {
              prevLoans.push(newLoan);
            }
          });

          prevList = prevList.map((pl: any) => ({
            ...pl,
            loans: pl.custId == element.custId ? prevLoans : pl.loans,
          }));
        } else {
          prevList.push(element);
        }
      });

      return prevList;
    }
    return newList;
  }

  prepareDateRanges() {
    let result: any[] = [];

    const date1 = moment(this.fromDate);
    const date2 = moment(this.toDate);

    const numberOfDays = date2.diff(date1, "days");

    if (numberOfDays > 4) {
      let numberOfRanges: number = Math.ceil(numberOfDays / 4);

      for (let index = 0; index < numberOfRanges; index++) {
        let startDate: any;
        let endDate: any;

        if (index == 0) {
          startDate = date1.format("YYYY-MM-DD");
          endDate = date1.add(4, "days").format("YYYY-MM-DD");
        } else {
          const previousEndDate = moment(result[result.length - 1].endDate);
          startDate = previousEndDate.add(1, "days").format("YYYY-MM-DD");
          endDate = previousEndDate.add(4, "days").format("YYYY-MM-DD");
        }
        if (moment(endDate).isAfter(this.toDate)) {
          endDate = this.toDate;
        }
        result.push({
          startDate: startDate,
          endDate: endDate,
        });
      }
    } else {
      let new_range: any = {
        startDate: this.fromDate,
        endDate: this.toDate,
      };
      result.push(new_range);
    }

    result = result.map((r: any, i: number) => ({
      requestId: "SBF-12345" + i.toString(),
      startDate: moment(r.startDate).format("DD-MM-YYYY"),
      endDate: moment(r.endDate).format("DD-MM-YYYY"),
    }));

    return result;
  }

  isValidInterestRateRange() {
    let validRegex: RegExp = RegExp(/^[0-9]\d*(\.\d+)?$/);

    if (!this.rateFrom || !this.rateTo) {
      return false;
    }

    if (!validRegex.test(this.rateFrom) || !validRegex.test(this.rateTo)) {
      return false;
    }

    if (parseFloat(this.rateFrom) > 100 || parseFloat(this.rateTo) > 100) {
      return false;
    }

    if (parseFloat(this.rateFrom) > parseFloat(this.rateTo)) {
      return false;
    }

    return true;
  }

  isValidDateRange() {
    if (!this.fromDate || !this.toDate) {
      return false;
    }

    if (
      moment(this.fromDate).format("YYYY-MM-DD") >
      moment(this.toDate).format("YYYY-MM-DD")
    ) {
      return false;
    }

    // const differenceInDays = moment(this.toDate).diff(
    //   moment(this.fromDate),
    //   "days"
    // );

    // if (differenceInDays > 32) {
    //   return false;
    // }

    return true;
  }

  isCodesEmpty() {
    return (
      this.parentCodeType != this.interestRateCodeType.FIXED &&
      this.rateCodeTypes.length == 0
    );
  }

  prepareReportData(data: any[]) {
    let result: any[] = [];
    data.forEach((element: any) => {
      let emptyArr: any[] = [];
      element = {
        ...element,
        sanctLim: this.currencyPipe.transform(
          AppUtils.getMillionValue(element.sanctLim),
          "",
          ""
        ),
        prefRate: element.prefRate ? parseFloat(element.prefRate) : "0",
        interestRate: element.interestRate
          ? parseFloat(element.interestRate)
          : "0",
        isAccSelected: false,
        facilityType: "",
        collateral: emptyArr,
        covenants: emptyArr,
        limitNode: null,
        commission: 0,
        recordType: "L",
      };

      if (result.some((r: any) => r.custId == element.custId)) {
        let record: any = result.find((r: any) => r.custId == element.custId);
        let loans: any[] = record.loans;
        loans.push(element);

        result = result.map((d: any) => ({
          ...d,
          loans: d.custId == element.custId ? loans : d.loans,
        }));
      } else {
        let loans: any[] = [];

        loans.push(element);
        let new_record: any = {
          custId: element.custId,
          acctName: element.acctName,
          loans: loans,
          sector: this.getSectorByCode(element.sectorCode),
          riskRating: element.riskRating,
          commission: 0,
          recordType: "L",
        };

        result.push(new_record);
      }
    });

    return result;
  }

  getSectorByCode(code: string) {
    let sectorData: any = this.sectorList.find(
      (s: any) => s.referenceCode == code
    );
    return sectorData
      ? sectorData.referenceCode + "-" + sectorData.referenceDescription
      : "";
  }

  handleSelectChange(e: any, custId: string, foracid: string) {
    let customer: any = this.reportingDataList.find(
      (c: any) => c.custId == custId
    );

    if (customer) {
      if (this.selectedAccounts.some((c: any) => c.custId == custId)) {
        let selectedCus: any = this.selectedAccounts.find(
          (c: any) => c.custId == custId
        );
        let selectedLoans: any[] = selectedCus.loans ? selectedCus.loans : [];
        if (selectedLoans.some((l: any) => l.foracid == foracid)) {
          selectedLoans = selectedLoans.filter(
            (l: any) => l.foracid != foracid
          );
        } else {
          let new_loan: any = customer.loans.find(
            (l: any) => l.foracid == foracid
          );
          selectedLoans.push(new_loan);
        }

        this.selectedAccounts = this.selectedAccounts.map((sa: any) => ({
          ...sa,
          loans: sa.custId == custId ? selectedLoans : sa.loans,
        }));
      } else {
        let cusAccounts: any[] = customer.loans
          ? customer.loans.filter((l: any) => l.foracid == foracid)
          : [];
        let cus_record: any = {
          ...customer,
          loans: cusAccounts,
        };
        this.selectedAccounts.push(cus_record);
      }
    }

    this.selectedAccounts = this.selectedAccounts.filter(
      (sa: any) => sa.loans.length > 0
    );

    this.isLoanSubmitEnabled = this.selectedAccounts.length > 0;
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

  handleSort() {
    if (this.cifId && !this.account) {
      this.reportingDataList = this.reportingDataStaticList.filter((d: any) =>
        d.custId.includes(this.cifId.replace(/\s/g, ""))
      );
      this.isLoanSorted = true;
    } else if (this.account && !this.cifId) {
      this.reportingDataList = this.reportingDataStaticList.filter((d: any) =>
        d.loans.some((l: any) =>
          l.foracid.includes(this.account.replace(/\s/g, ""))
        )
      );
      this.isLoanSorted = true;
    } else if (this.cifId && this.account) {
      this.reportingDataList = this.reportingDataStaticList.filter(
        (d: any) =>
          d.custId.includes(this.cifId.replace(/\s/g, "")) ||
          d.loans.some((l: any) =>
            l.foracid.includes(this.account.replace(/\s/g, ""))
          )
      );
      this.isLoanSorted = true;
    } else {
      this.alertService.showToaster(
        "Please provide a CIF ID or Account.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }

    this.parentPageSize.length = this.reportingDataList.length;
    this.parentPageSize.pageIndex = 0;
    this.parentPageSize.pageSizeOptions = [10];
    this.cdr.detectChanges();
  }

  clearSort() {
    this.isLoanSorted = false;
    this.cifId = "";
    this.account = "";
    this.reportingDataList = this.reportingDataStaticList;
    this.parentPageSize.length = this.reportingDataList.length;
    this.parentPageSize.pageSizeOptions = [10];
    this.parentPageSize.pageIndex = 0;
    this.cdr.detectChanges();
  }

  handleFacilityTypeChange(
    custId: string,
    foracid: string,
    facilityType: string
  ) {
    this.selectedAccounts = this.selectedAccounts.map((sa: any) => ({
      ...sa,
      loans:
        sa.custId == custId
          ? sa.loans.map((l: any) => ({
              ...l,
              facilityType:
                l.foracid == foracid ? facilityType : l.facilityType,
            }))
          : sa.loans,
    }));
  }

  isGenerateBtnEnabled() {
    return this.isLoanSubmitEnabled && this.selectedAccounts.length > 0;
  }

  submitLoans() {
    let isEmptyType: boolean = this.selectedAccounts.some((sa: any) =>
      sa.loans.some((l: any) => l.facilityType == "")
    );

    if (!isEmptyType) {
      this.handleSubmitLoan.next(this.selectedAccounts);
    } else {
      this.alertService.showToaster(
        "There are no specific facility type in the facilities that have been chosen.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  toggleOverflow() {
    const currentOverflow = this.modalBody.nativeElement.style.overflowY;
    const newOverflow = currentOverflow === "auto" ? "initial" : "auto";
    this.renderer.setStyle(
      this.modalBody.nativeElement,
      "overflow-y",
      newOverflow
    );
  }

  handleFormData() {
    let formData: any = {
      fromDate: this.fromDate ? this.fromDate : "",
      toDate: this.toDate ? this.toDate : "",
      sector: this.sector,
      riskRating: this.riskRating,
    };
    this.bccReportingService.onFormDataChange.next(formData);
  }

  getSliceParentData() {
    return this.reportingDataList.slice(
      this.parentPageSize.pageIndex * this.parentPageSize.pageSize,
      (this.parentPageSize.pageIndex + 1) * this.parentPageSize.pageSize
    );
  }

  onParentPageEvent(event: any) {
    this.parentPageSize.pageIndex = event.pageIndex;
    this.parentPageSize.pageSize = event.pageSize;
  }
}
