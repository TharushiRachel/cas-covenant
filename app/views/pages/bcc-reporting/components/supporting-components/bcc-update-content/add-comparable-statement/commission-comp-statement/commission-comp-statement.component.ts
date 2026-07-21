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
import { Subscription } from "rxjs";
import { PageSize } from "src/app/core/dto/page.size";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { AppUtils } from "src/app/shared/app.utils";
import { BccReportingService } from "src/app/views/pages/bcc-reporting/services/bcc-reporting.service";

@Component({
  selector: "app-commission-comp-statement",
  templateUrl: "./commission-comp-statement.component.html",
  styleUrls: ["./commission-comp-statement.component.scss"],
})
export class CommissionCompStatementComponent implements OnInit {
  @ViewChild("modalBody", { static: true }) modalBody!: ElementRef;

  @Output("handleSubmitCommission") handleSubmitCommission = new EventEmitter();

  isGenerateEnabled: boolean = false;
  isSorted: boolean = false;
  isLoading: boolean = true;
  isSearched: boolean = false;
  isCollapsed: boolean = true;
  isSectorLoading: boolean = true;

  fromDate: any;
  toDate: any;
  sector: string = "";
  riskRating: string = "";
  productId: string;
  chargeFrom: any;
  chargeTo: any;

  facilityTypeOptions: any[] = [
    { value: "TL", label: "Term Loan" },
    { value: "WC", label: "Working Capital" },
  ];

  commissionTypeOptions: any[] = [
    { value: "ODCM", label: "LC" },
    { value: "HORM", label: "Outward TT" },
    { value: "HIRM", label: "Inward TT" },
    { value: "MIIB-DA", label: "Import Bills - DA" },
    { value: "MIIB-DP", label: "Import Bills - DP" },
    { value: "MEOB", label: "Export Bills" },
  ];
  account: string = "";
  cifId: string = "";
  commissionDataList: any[] = [];
  commissionReportingDataList: any[] = [];
  commissionReportingStaticList: any[] = [];
  selectedAccounts: any[] = [];
  @Input("sectorList") sectorList: any[] = [];
  @Input("sectorOptions") sectorOptions: any[] = [];
  @Input("riskRatingOptions") riskRatingOptions: any[] = [];
  isSubmitEnabled: boolean = false;

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

  pageSize = new PageSize();

  onFormDataChange: Subscription = new Subscription();

  constructor(
    private readonly bccReportingService: BccReportingService,
    private readonly alertService: AlertService,
    public currencyPipe: CurrencyPipe,
    private readonly renderer: Renderer2,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.onFormDataChange = this.bccReportingService.onFormDataChange.subscribe(
      (loanFormData: any) => {
        this.handleUpdateFormData(loanFormData);
      }
    );
  }

  ngOnDestroy(): void {
    this.onFormDataChange.unsubscribe();
  }

  handleUpdateFormData(loanFormData: any) {
    this.fromDate = this.fromDate ? this.fromDate : loanFormData.fromDate;
    this.toDate = this.toDate ? this.toDate : loanFormData.toDate;
    this.riskRating = this.riskRating
      ? this.riskRating
      : loanFormData.riskRating
      ? loanFormData.riskRating
      : this.riskRatingOptions[0].value;
    this.sector = this.sector
      ? this.sector
      : loanFormData.sector
      ? loanFormData.sector
      : this.sectorOptions[0].value;

    this.cdr.detectChanges();
  }

  handleCollapsed() {
    this.isCollapsed = !this.isCollapsed;
  }

  handleSearchClear() {
    this.chargeFrom = "";
    this.chargeTo = "";
    this.productId = "";
    this.riskRating =
      this.riskRatingOptions.length > 0 ? this.riskRatingOptions[0].value : "";
    this.sector =
      this.sectorOptions.length > 0 ? this.sectorOptions[0].value : "";
    this.commissionDataList = [];
    this.isSearched = false;
    this.isSorted = false;
    this.isGenerateEnabled = false;
    this.isCollapsed = true;
    this.fromDate = "";
    this.toDate = "";
    this.account = "";
    this.cifId = "";
    this.commissionDataList = [];
    this.commissionReportingDataList = [];
    this.commissionReportingStaticList = [];
    this.selectedAccounts = [];
  }

  handleSearch() {
    if (this.isValidDateRange() && this.productId && this.isValidRateRange()) {
      let payload: any = {
        chargeFrom: this.chargeFrom,
        chargeTo: this.chargeTo,
        productId:
          this.productId.split("-").length > 1
            ? this.productId.split("-")[0]
            : this.productId,
        chargeSubType:
          this.productId.split("-").length > 1
            ? this.productId.split("-")[1]
            : "",
      };

      this.bccReportingService.getCommissionData(payload).then((data: any) => {
        if (data && data.length > 0) {
          this.commissionDataList = data.map((d: any) => ({
            ...d,
            pcntAmt: d.pcntAmt ? parseFloat(d.pcntAmt) : "0",
            isSearch: false,
            isAddStatement: false,
          }));

          this.pageSize.length = this.commissionDataList.length;
          this.pageSize.pageSizeOptions = [10];

          this.isSearched = true;
        } else {
          this.alertService.showToaster(
            "There are no data found.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
        }
      });
    } else {
      this.isSearched = false;
      let message: string = !this.isValidDateRange()
        ? "Check the range of dates."
        : !this.productId
        ? "Please Select the product type."
        : !this.isValidRateRange()
        ? "Check the charge rate range."
        : "Please check the required data.";
      this.alertService.showToaster(message, SETTINGS.TOASTER_MESSAGES.error);
    }
  }

  isValidRateRange() {
    let validRegex: RegExp = RegExp(/^[0-9]\d*(\.\d+)?$/);

    if (!this.chargeFrom || !this.chargeTo) {
      return false;
    }

    if (!validRegex.test(this.chargeFrom) || !validRegex.test(this.chargeTo)) {
      return false;
    }

    if (parseFloat(this.chargeFrom) > 100 || parseFloat(this.chargeTo) > 100) {
      return false;
    }

    if (parseFloat(this.chargeFrom) > parseFloat(this.chargeTo)) {
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

  getLoanAccounts(cifId: any, commission: any) {
    let payload: any = {
      dateRanges: this.prepareDateRanges(cifId),
      customerId: cifId,
      sector: this.sector,
      riskRating: this.riskRating,
      isAllSector: this.sector == "All" || this.sector == "",
      isAllRiskRating: this.riskRating == "All" || this.riskRating == "",
    };

    this.bccReportingService
      .getCommissionLoanAccounts(payload)
      .then((data: any) => {
        this.commissionDataList = this.commissionDataList.map((d: any) => ({
          ...d,
          isSearch: d.cif == cifId ? true : d.isSearch,
        }));

        if (data) {
          let preparedResult: any[] = this.prepareReportData(data, commission);

          this.commissionReportingDataList =
            this.commissionReportingDataList.concat(preparedResult);

          this.commissionReportingStaticList =
            this.commissionReportingStaticList.concat(preparedResult);
        } else {
          this.alertService.showToaster(
            "There no data found.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
        }
      });
  }

  prepareDateRanges(cifId: string) {
    let result: any[] = [];

    const date1 = moment(this.fromDate);
    const date2 = moment(this.toDate);

    const numberOfDays = date2.diff(date1, "days");

    if (numberOfDays > 90) {
      let numberOfRanges: number = Math.ceil(numberOfDays / 90);

      for (let index = 0; index < numberOfRanges; index++) {
        let startDate: any;
        let endDate: any;

        if (index == 0) {
          startDate = date1.format("YYYY-MM-DD");
          endDate = date1.add(90, "days").format("YYYY-MM-DD");
        } else {
          const previousEndDate = moment(result[result.length - 1].endDate);
          startDate = previousEndDate.add(1, "days").format("YYYY-MM-DD");
          endDate = previousEndDate.add(90, "days").format("YYYY-MM-DD");
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
      customerId: cifId,
      startDate: moment(r.startDate).format("DD-MM-YYYY"),
      endDate: moment(r.endDate).format("DD-MM-YYYY"),
    }));

    return result;
  }

  prepareReportData(data: any[], commission: any) {
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
        isAccSelected: false,
        facilityType: "",
        collateral: emptyArr,
        covenants: emptyArr,
        limitNode: null,
        commission: commission,
        recordType: "C",
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
          sector: element.sectorCode + "-" + element.sectorDesc,
          riskRating: element.riskRating,
          commission: commission,
          recordType: "C",
        };

        result.push(new_record);
      }
    });

    return result;
  }

  handleSelectChange(e: any, custId: string, foracid: string) {
    let customer: any = this.commissionReportingDataList.find(
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

    this.isSubmitEnabled = this.selectedAccounts.length > 0;
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
      this.commissionReportingDataList = this.commissionReportingDataList.map(
        (rd: any) => ({
          ...rd,
          loans:
            rd.custId == custId
              ? rd.loans.map((l: any) => ({
                  ...l,
                  limitNode: l.foracid == foracid ? node : l.limitNode,
                }))
              : rd.loans,
        })
      );
    });
  }

  handleSort() {
    if (this.cifId && !this.account) {
      this.commissionReportingDataList =
        this.commissionReportingStaticList.filter((d: any) =>
          d.custId.includes(this.cifId.replace(/\s/g, ""))
        );
      this.isSorted = true;
    } else if (this.account && !this.cifId) {
      this.commissionReportingDataList =
        this.commissionReportingStaticList.filter((d: any) =>
          d.loans.some((l: any) =>
            l.foracid.includes(this.account.replace(/\s/g, ""))
          )
        );
      this.isSorted = true;
    } else if (this.cifId && this.account) {
      this.commissionReportingDataList =
        this.commissionReportingStaticList.filter(
          (d: any) =>
            d.custId.includes(this.cifId.replace(/\s/g, "")) ||
            d.loans.some((l: any) =>
              l.foracid.includes(this.account.replace(/\s/g, ""))
            )
        );
      this.isSorted = true;
    } else {
      this.alertService.showToaster(
        "Please provide a CIF ID or Account.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  clearSort() {
    this.isSorted = false;
    this.cifId = "";
    this.account = "";
    this.commissionReportingDataList = this.commissionReportingStaticList;
  }

  isGenerateBtnEnabled() {
    return this.isSubmitEnabled && this.selectedAccounts.length > 0;
  }

  submitLoans() {
    let isEmptyType: boolean = this.selectedAccounts.some((sa: any) =>
      sa.loans.some((l: any) => l.facilityType == "")
    );
    if (!isEmptyType) {
      this.handleSubmitCommission.next(this.selectedAccounts);
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

  getSliceData() {
    return this.commissionDataList.slice(
      this.pageSize.pageIndex * this.pageSize.pageSize,
      (this.pageSize.pageIndex + 1) * this.pageSize.pageSize
    );
  }

  onPageEvent(event: any) {
    this.pageSize.pageIndex = event.pageIndex;
    this.pageSize.pageSize = event.pageSize;
  }

  isSearchAndNoLoanAcc(custId: string) {
    let isCommissionSearched: boolean = this.commissionDataList.some(
      (d: any) => d.cif == custId && d.isSearch
    );
    let isAccAvialable: boolean = this.commissionReportingDataList.some(
      (d: any) => d.custId == custId
    );

    return isCommissionSearched && !isAccAvialable;
  }

  addWithoutLoanAcc(commission: any) {
    let data: any[] = [];
    data.push({
      acid: commission.acid,
      solId: "",
      foracid: commission.acct_id,
      acctName: commission.acctName,
      custId: commission.cif,
      glSubhead: "",
      schmCode: "",
      schmDesc: "",
      productGroup: "",
      acctOpnDate: "",
      acctClsFlg: "",
      acctClsDate: "",
      acctCrncyCode: "",
      sanctLim: "",
      clrBalAmt: "",
      interestRate: "",
      intTbleCode: "",
      prefRate: "",
      sectorCode: "",
      sectorDesc: "",
      riskRating: "",
      limitB2kId: "",
    });
    let preparedResult: any[] = this.prepareReportData(
      data,
      commission.pcntAmt
    );

    this.commissionReportingDataList =
      this.commissionReportingDataList.concat(preparedResult);

    this.commissionReportingStaticList =
      this.commissionReportingStaticList.concat(preparedResult);

    this.commissionDataList = this.commissionDataList.map((d: any) => ({
      ...d,
      isAddStatement: d.cif == commission.cif ? true : d.isAddStatement,
    }));
  }
}
