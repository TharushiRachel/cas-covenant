import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import * as moment from "moment";
import {
  IMyDate,
  IMyOptions,
  MDBModalRef,
  MDBModalService,
} from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { CommonService } from "src/app/core/service/common/common.service";
import { CacheService } from "src/app/core/service/data/cache.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { AppUtils } from "src/app/shared/app.utils";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { CribRequest } from "src/app/shared/interfaces/CribRequest";
import { CribReportService } from "src/app/views/pages/facility-paper/services/crib-report.service";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-add-crib-report",
  templateUrl: "./add-crib-report.component.html",
  styleUrls: ["./add-crib-report.component.scss"],
})
export class AddCribReportComponent implements OnInit, OnDestroy {
  content: any;
  facilityPaper: any = null;
  cribUsers: any[] = [];
  isEdit: boolean = false;

  @ViewChild("downloadLink", { static: false })
  private readonly downloadLink: ElementRef;

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate() + 1,
  };

  myDatePickerOptions: IMyOptions = {
    dateFormat: "dd/mm/yyyy",
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
    disableSince: this.disableSinceDate,
  };

  optionscribStatusSelect: any[] = [
    // { value: "NOT_ENTERED", label: "Not Entered" },
    // { value: "PENDING", label: "Pending" },
    { value: "NO_IRREGULAR_ADVANCES", label: "No Irregular Advances" },
    {
      value: "REPORTED_AS_IRREGULAR",
      label: "Reported as Irregular (Refer Comments)",
    },
  ];

  inquiryReasonSelect: any[] = [
    {
      value: "EvaluatingOfABorrowerForANewCreditFacility",
      label: "Evaluating of a borrower for a new credit facility",
    },
    {
      value: "ReviewAsAGuarantorForANewCreditFacility",
      label: "Review as a guarantor for a new credit facility",
    },
    {
      value: "MonitoringAndReviewingOfAnExistingBorrower",
      label: "Monitoring and reviewing of an existing borrower",
    },
    {
      value: "OpeningOfACurrentAccount",
      label: "Opening of a current account",
    },
    {
      value: "ReviewAsAPartnerProprietorForANewCreditFacility",
      label: "Review as a partner/proprietor for a new credit facility",
    },
    {
      value: "ReviewAsADirectorForANewCreditFacility",
      label: "Review as a director for a new credit facility",
    },
  ];

  customerTypeList: any[] = Constants.customerTypeList;
  customerType: any = Constants.customerType;
  genderList: any[] = [
    { value: "Male", label: "Male" },
    { value: "Female", label: "Female" },
    { value: "Other", label: "Other" },
  ];

  personalIDOptions: any[] = [
    { value: "NIC", label: "NIC" },
    { value: "PP", label: "Passport" },
  ];

  supportingDocs: any[] = [];
  reportItemErrors: any = {
    fullName: "",
    customerType: "",
    identificationType: "",
    identificationNumber: "",
    gender: "",
    cribStatus: "",
    cribIssueDateStr: "",
    reportName: "",
    inquiryReason: "",
    identifications: "",
  };
  modalRef: MDBModalRef;

  onFacilityPaperBaseDataChange = new Subscription();

  @ViewChild("modalBody", { static: true })
  private readonly modalContainer: ElementRef;

  prevCribReports: any[] = [];

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly applicationService: ApplicationService,
    public mdbModalRef: MDBModalRef,
    private readonly cacheService: CacheService,
    private readonly alertService: AlertService,
    private readonly cribReportServices: CribReportService,
    private readonly commons: CommonService,
    private readonly mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    this.supportingDocs = this.cacheService.getData(
      Constants.masterDataKey.CAS_SUPPORTING_DOCs
    );
    this.setReportEligibleUsers();

    this.onFacilityPaperBaseDataChange =
      this.facilityPaperAddEditService.onFacilityPaperBaseDataChange.subscribe(
        (data: any) => {
          this.facilityPaper = {
            ...this.facilityPaper,
            totalExposureNew:
              data && data.totalExposureNew
                ? data.totalExposureNew
                : this.facilityPaper.totalExposureNew,
          };
        }
      );
  }

  ngOnDestroy(): void {
    this.onFacilityPaperBaseDataChange.unsubscribe();
  }

  handleCustomerTypeChange(index: number) {
    this.cribUsers = this.cribUsers.map((element: any, i: number) =>
      i === index
        ? {
            ...element,
            identificationType: Constants.customerIdentificationType.NIC,
            identificationNumber: "",
            fullName: "",
          }
        : element
    );
  }

  handleIdentificationChange(index: number) {
    this.cribUsers = this.cribUsers.map((element: any, i: number) =>
      i === index
        ? {
            ...element,
            identificationNumber: "",
          }
        : element
    );
  }

  setReportEligibleUsers() {
    if (this.facilityPaper) {
      if (
        this.facilityPaper.casCustomerDTOList &&
        this.facilityPaper.casCustomerDTOList.length > 0
      ) {
        this.facilityPaper.casCustomerDTOList.forEach((cus: any) => {
          let identifications: any[] = cus.casCustomerIdentificationDTOList
            .filter(
              (data: any) =>
                data.identificationType && data.identificationNumber
            )
            .map((d: any) => ({
              identificationType: d.identificationType,
              identificationNumber: d.identificationNumber,
            }));

          if (
            identifications.length > 0 &&
            !this.isReportExistByList(identifications)
          ) {
            let cus_record: any = {
              fullName: cus.casCustomerName,
              identificationType:
                identifications.length == 1
                  ? identifications[0].identificationType
                  : "",
              identificationNumber:
                identifications.length == 1
                  ? identifications[0].identificationNumber
                  : "",
              customerType:
                identifications.length == 1
                  ? this.getCustomerType(identifications[0].identificationType)
                  : "",
              report: "",
              reportName: "",
              isLoading: false,
              createdDate: "",
              cribStatus: "",
              cribIssueDateStr: "",
              remark: "",
              inquiryReason: "",
              gender: "",
              isNew: false,
              isRequired: true,
              isMannualUpload: false,
              identifications:
                identifications.length > 1 ? identifications : [],
              errors: this.reportItemErrors,
            };

            this.cribUsers.push(cus_record);
          }
        });
      }

      if (
        this.facilityPaper.fpDirectorDetailDTOList &&
        this.facilityPaper.fpDirectorDetailDTOList.length > 0
      ) {
        this.facilityPaper.fpDirectorDetailDTOList.forEach((director: any) => {
          if (director.nic && !this.isReportExist(director.nic)) {
            let director_record: any = {
              fullName: director.directorName + " " + director.fullName,
              identificationType: Constants.customerIdentificationType.NIC,
              identificationNumber: director.nic,
              customerType: Constants.customerType.PERSONAL,
              report: "",
              reportName: "",
              isLoading: false,
              createdDate: "",
              cribStatus: "",
              cribIssueDateStr: "",
              remark: "",
              inquiryReason: "",
              gender: "",
              isNew: false,
              isRequired: true,
              isMannualUpload: false,
              identifications: [],
              errors: this.reportItemErrors,
            };

            this.cribUsers.push(director_record);
          }
        });
      }

      if (this.cribUsers.length <= 0) {
        this.addNewUser();
      }
    }
  }

  getCustomerType(idType: string) {
    switch (idType) {
      case "NIC":
        return Constants.customerType.PERSONAL;
      case "PP":
        return Constants.customerType.PERSONAL;
      case "BRN":
        return Constants.customerType.BUSINESS;
      case "BRC":
        return Constants.customerType.BUSINESS;
    }
  }

  isReportExist(identificationNumber: string) {
    return this.prevCribReports.some(
      (pr: any) => pr.identificationNumber == identificationNumber
    );
  }

  isReportExistByList(dataList: any[]) {
    return this.prevCribReports.some((pr: any) =>
      dataList.some(
        (d: any) => d.identificationNumber == pr.identificationNumber
      )
    );
  }

  addNewUser() {
    let new_record: any = {
      fullName: "",
      identificationType: Constants.customerIdentificationType.NIC,
      identificationNumber: "",
      customerType: this.customerType.PERSONAL,
      report: "",
      reportName: "",
      isLoading: false,
      createdDate: "",
      cribStatus: "",
      cribIssueDateStr: "",
      remark: "",
      inquiryReason: "",
      gender: "",
      isNew: true,
      isRequired: true,
      isMannualUpload: false,
      identifications: [],
      errors: this.reportItemErrors,
    };

    this.cribUsers.push(new_record);

    setTimeout(() => {
      this.modalContainer.nativeElement.scroll({
        top: this.modalContainer.nativeElement.scrollHeight,
        left: 0,
        behavior: "smooth",
      });
    }, 100);
  }

  handleIdentificationType(index: number, item: any) {
    this.cribUsers = this.cribUsers.map((element: any, i: number) =>
      i === index
        ? {
            ...element,
            identificationType: item.identificationType,
            identificationNumber: item.identificationNumber,
            customerType: this.getCustomerType(item.identificationType),
          }
        : element
    );
  }

  getCribReport(item: any, index: number) {
    if (this.facilityPaper.totalExposureNew) {
      if (item.customerType == this.customerType.PERSONAL) {
        if (
          item.fullName &&
          item.gender &&
          item.identificationType &&
          this.validateIdentification(item) == ""
        ) {
          this.handleCribConfirmation(item);
        } else {
          this.setItemErrors(index);

          setTimeout(() => {
            this.clearItemErrors();
          }, 2500);
          this.alertService.showToaster(
            "Reuired data are missing. Please check and continue.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      } else if (item.customerType == this.customerType.BUSINESS) {
        if (item.fullName && item.identificationNumber) {
          this.handleCribConfirmation(item);
        } else {
          this.setItemErrors(index);

          setTimeout(() => {
            this.clearItemErrors();
          }, 2500);

          this.alertService.showToaster(
            "Reuired data are missing. Please check and continue.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      } else {
        this.cribUsers = this.cribUsers.map((element: any, i: number) =>
          i === index
            ? {
                ...element,
                errors: {
                  ...element.errors,
                  identifications:
                    element.identifications.length > 0 &&
                    element.customerType == ""
                      ? "Identification Type is required."
                      : "",
                },
              }
            : element
        );
        setTimeout(() => {
          this.clearItemErrors();
        }, 2500);

        this.alertService.showToaster(
          "Reuired data are missing. Please check and continue.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    } else {
      this.alertService.showToaster(
        "Facilities are required to retrive crib report.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  handleCribConfirmation(item: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "New Crib Report - Confirmation",
        message: "This service will incur charges. Do you want to proceed?",
        isCribConfirmation: true,
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (item.customerType == this.customerType.PERSONAL) {
          this.getIndividualReport(item);
        }

        if (item.customerType == this.customerType.BUSINESS) {
          this.getCompanyReport(item);
        }
      }
    });
  }

  setItemErrors(index: number) {
    this.cribUsers = this.cribUsers.map((element: any, i: number) =>
      i === index
        ? {
            ...element,
            errors: {
              ...element.errors,
              fullName: element.fullName == "" ? "Full name is required." : "",
              customerType:
                element.customerType == "" ? "Customer type is required." : "",
              identificationType:
                element.customerType == Constants.customerType.PERSONAL &&
                element.identificationType == ""
                  ? "Identification Type is required."
                  : "",
              identificationNumber: this.validateIdentification(element),
              gender:
                element.customerType == this.customerType.PERSONAL &&
                element.gender == ""
                  ? "Gender is required."
                  : "",
              inquiryReason:
                element.inquiryReason == ""
                  ? "Inquiry Reason is required."
                  : "",
              identifications:
                element.identifications.length > 0 && element.customerType == ""
                  ? "Identification Type is required."
                  : "",
            },
          }
        : element
    );
  }

  getIndividualReport(item: any) {
    this.hanldeReportLoading(item.identificationNumber);
    let request: CribRequest = {
      applicationNumber: `${this.facilityPaper.fpRefNumber}-${item.identificationNumber}`,
      applicationDate: moment().format("YYYY-MM-DD"),
      fullName: item.fullName,
      gender: item.gender,
      creditFacilityType: "LOAN",
      creditFacilityCurrency: "LKR",
      reportDate: moment().format("YYYY-MM-DD"),
      creditFacilityAmountDTO: {
        value: this.facilityPaper.totalExposureNew
          ? this.facilityPaper.totalExposureNew
          : 0,
        currency: "LKR",
        localValue: "",
      },
      idNumberDTOList: [
        {
          idNumberType:
            item.identificationType == "PP" ? "PassportNumber" : "NIC",
          idNumber: item.identificationNumber,
        },
      ],
      inquiryReason: item.inquiryReason,
    };

    this.cribReportServices
      .searchIndividualCrib(request)
      .then((resp: any) => {
        if (resp) {
          this.cribUsers = this.cribUsers.map((r: any) => ({
            ...r,
            report:
              r.identificationNumber == item.identificationNumber
                ? resp.report
                : r.report,
            reportName:
              r.identificationNumber == item.identificationNumber
                ? this.getReportName(item.identificationNumber)
                : r.reportName,
          }));

          let saveRequest: any = {
            ...item,
            report: resp.report,
            facilityAmount: this.facilityPaper.totalExposureNew
              ? this.facilityPaper.totalExposureNew
              : 0,
            customerType: this.customerType.PERSONAL,
          };
          this.saveSearch(saveRequest);

          this.hanldeReportLoading(item.identificationNumber);
        } else {
          this.hanldeReportLoading(item.identificationNumber);
        }
      })
      .catch((err: any) => {
        this.alertService.showToaster(
          "There is a technical error. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
        this.hanldeReportLoading(item.identificationNumber);
      });
  }

  getCompanyReport(item: any) {
    this.hanldeReportLoading(item.identificationNumber);
    let request: CribRequest = {
      applicationNumber: `${this.facilityPaper.fpRefNumber}-${item.identificationNumber}`,
      applicationDate: moment().format("YYYY-MM-DD"),
      fullName: item.fullName,
      gender: "",
      creditFacilityType: "LOAN",
      creditFacilityCurrency: "LKR",
      reportDate: moment().format("YYYY-MM-DD"),
      creditFacilityAmountDTO: {
        value: this.facilityPaper.totalExposureNew
          ? this.facilityPaper.totalExposureNew
          : 0,
        currency: "LKR",
        localValue: "",
      },
      idNumberDTOList: [
        {
          idNumberType: "BusinessRegistrationNumber",
          idNumber: item.identificationNumber,
        },
      ],
      inquiryReason: item.inquiryReason,
    };

    this.cribReportServices
      .searchCompanyCrib(request)
      .then((resp: any) => {
        if (resp) {
          this.cribUsers = this.cribUsers.map((r: any) => ({
            ...r,
            report:
              r.identificationNumber == item.identificationNumber
                ? resp.report
                : r.report,
            reportName:
              r.identificationNumber == item.identificationNumber
                ? this.getReportName(item.identificationNumber)
                : r.reportName,
          }));

          let saveRequest: any = {
            ...item,
            report: resp.report,
            facilityAmount: this.facilityPaper.totalExposureNew
              ? this.facilityPaper.totalExposureNew
              : 0,
            customerType: this.customerType.BUSINESS,
          };
          this.saveSearch(saveRequest);

          this.hanldeReportLoading(item.identificationNumber);
        } else {
          this.hanldeReportLoading(item.identificationNumber);
        }
      })
      .catch((err: any) => {
        this.alertService.showToaster(
          "There is a technical error. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
        this.hanldeReportLoading(item.identificationNumber);
      });
  }

  hanldeReportLoading(id: any) {
    this.cribUsers = this.cribUsers.map((r: any) => ({
      ...r,
      isLoading: r.identificationNumber == id ? !r.isLoading : r.isLoading,
    }));
  }

  getReportName(id: string): string {
    return `Crib Report-${id}`;
  }

  checkValidReports() {
    return this.cribUsers.filter((r: any) => r.reportName != "").length > 0;
  }

  uploadReport(item: any) {
    this.cribUsers = this.cribUsers.map((r: any) => ({
      ...r,
      isMannualUpload:
        r.identificationNumber == item.identificationNumber
          ? !r.isMannualUpload
          : r.isMannualUpload,
    }));
  }

  handleFileUpload(event: any, item: any) {
    const file = event.target.files[0];
    if (file) {
      const fileType: any = file.type;
      if (fileType && fileType == "application/pdf") {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          let result: any = reader.result;
          this.cribUsers = this.cribUsers.map((r: any) => ({
            ...r,
            report:
              r.identificationNumber == item.identificationNumber
                ? result.split(",")[1]
                : r.report,
            reportName:
              r.identificationNumber == item.identificationNumber
                ? this.getReportName(item.identificationNumber)
                : r.reportName,
            isMannualUpload:
              r.identificationNumber == item.identificationNumber
                ? false
                : r.isMannualUpload,
          }));
        };
      } else {
        this.alertService.showToaster(
          "Invalid file type.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    }
  }

  closeFileUpload(item: any) {
    this.cribUsers = this.cribUsers.map((r: any) => ({
      ...r,
      isMannualUpload:
        r.identificationNumber == item.identificationNumber
          ? false
          : r.isMannualUpload,
    }));
  }

  viewNewReport(item: any) {
    this.viewInNewTab(
      this.b64toBlob(item.report),
      this.getReportName(item.identificationNumber)
    );
  }

  downloadNewReport(item: any) {
    const source = `data:application/pdf;base64,${item.report}`;
    const link = document.createElement("a");
    link.href = source;
    link.download = `${this.getReportName(item.identificationNumber)}.pdf`;
    link.click();
  }

  viewInNewTab(data: any, fileName: string) {
    const pdfBlob = new Blob([data], { type: "application/pdf" });
    const pdfFile = new File([pdfBlob], fileName, { type: "application/pdf" });
    const fileUrl = URL.createObjectURL(pdfFile);

    const newWindow = window.open();
    newWindow.document.write(
      `<html>
          <head>
            <title>${fileName}</title>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
            <style>
              .menu-bar {
                position: absolute;
                top: 8px;
                right: 80px;
                
                padding: 10px;
                display: flex;
                align-items: center;
              }

              .menu-bar button {
                margin-right: 10px;
              }

              .menu-bar .download-button {
                margin-left: auto;
                background-color: transparent;
                color: white;
                border : none;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                border-radius: 500px;
                cursor: pointer;
                
              }


            </style>
          </head>
          <body>
            <div style="position: relative;">
              <embed src="${fileUrl}" id="pdfViewer" class="pdfjs-viewer" height="100%" width="100%" type="application/pdf" />
              <div class="menu-bar">
                <button id="downloadButton" class = "download-button" title="Download PDF">
                  <i class='fas fa-download' style='color: white'></i> 
                </button>
              </div>
            </div>

            <script>
              const downloadButton = document.getElementById('downloadButton');
              downloadButton.addEventListener('click', function() {
                const pdfViewer = document.getElementById('pdfViewer');
                const a = document.createElement('a');
                a.href = pdfViewer.src;
                a.download = '${fileName}'; // Set the desired download name here
                a.style.display = 'none';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
              });
            </script>
          </body>
        </html>`
    );
    newWindow.document.close();
  }

  removeReport(item: any) {
    this.cribUsers = this.cribUsers.map((r: any) => ({
      ...r,
      report:
        r.identificationNumber == item.identificationNumber ? "" : r.report,
      reportName:
        r.identificationNumber == item.identificationNumber ? "" : r.reportName,
      cribStatus:
        r.identificationNumber == item.identificationNumber ? "" : r.cribStatus,
      cribIssueDateStr:
        r.identificationNumber == item.identificationNumber
          ? ""
          : r.cribIssueDateStr,
    }));
  }

  b64toBlob(b64Data: any) {
    const byteCharacters = atob(b64Data);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += 512) {
      const slice = byteCharacters.slice(offset, offset + 512);

      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    const blob = new Blob(byteArrays, { type: "application/pdf" });
    return blob;
  }

  submit() {
    let errors: any[] = [];
    this.cribUsers.forEach((element: any) => {
      element.errors = this.validateItem(element);
      if (
        JSON.stringify(element.errors) != JSON.stringify(this.reportItemErrors)
      ) {
        errors.push(element.errors);
      }
    });
    console.log("e:", errors);
    if (errors.length == 0) {
      this.addCribDocument();
    } else {
      setTimeout(() => {
        this.clearItemErrors();
      }, 2500);

      this.alertService.showToaster(
        "Reuired data are missing. Please check and continue.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  validateItem(item: any) {
    if (item.isRequired) {
      item.errors = {
        ...item.errors,
        fullName: item.fullName == "" ? "Full name is required." : "",
        customerType:
          item.customerType == "" ? "Customer type is required." : "",
        identificationType:
          item.customerType == Constants.customerType.PERSONAL &&
          item.identificationType == ""
            ? "Identification Type is required."
            : "",
        identificationNumber: this.validateIdentification(item),
        gender:
          item.customerType == this.customerType.PERSONAL && item.gender == ""
            ? "Gender is required."
            : "",
        cribStatus: item.cribStatus == "" ? "Crib status is required." : "",
        cribIssueDateStr:
          item.cribIssueDateStr == "" ? "Crib issue date is required." : "",
        reportName: item.reportName == "" ? "Report is required." : "",
        inquiryReason:
          item.inquiryReason == "" ? "Inquiry Reason is required." : "",
      };
    } else {
      item.errors = this.reportItemErrors;
    }

    return item.errors;
  }

  validateIdentification(item: any) {
    let msg: string = "";
    if (
      item.customerType == this.customerType.PERSONAL &&
      item.identificationType == Constants.customerIdentificationType.NIC
    ) {
      if (item.identificationNumber) {
        if (!AppUtils.isNic(item.identificationNumber)) {
          msg = "Invalid NIC format.";
        }
      } else {
        msg = "NIC is required.";
      }
    } else {
      msg = item.identificationNumber == "" ? "Passport is required." : "";
    }

    if (
      item.customerType == this.customerType.BUSINESS &&
      item.identificationNumber == ""
    ) {
      msg = "BRN is required.";
    }

    return msg;
  }

  clearItemErrors() {
    this.cribUsers = this.cribUsers.map((cu: any) => ({
      ...cu,
      errors: this.reportItemErrors,
    }));
  }

  getIdentificationType(type: string, idType: string): any {
    switch (type) {
      case Constants.customerType.PERSONAL:
        return idType == "PP"
          ? Constants.customerIdentificationType.PP
          : idType == "NIC"
          ? Constants.customerIdentificationType.NIC
          : "Identification Number";

      case Constants.customerType.BUSINESS:
        return Constants.customerIdentificationType.BRN;

      default:
        return "Identification Number";
    }
  }

  addCribDocument() {
    let doc = AppUtils.getSupportingDocFromDocumentName(
      this.supportingDocs,
      "CRIB"
    );

    let filteredList: any[] = this.cribUsers.filter((r: any) => r.isRequired);

    let formData = new FormData();
    let requestList: any[] = [];
    filteredList.forEach((element: any, i: number) => {
      formData.append(
        "uploadingFiles",
        this.b64toBlob(element.report),
        this.getReportName(element.identificationNumber)
      );

      let rowData: any = {
        cribDetailsID: 0,
        docStorageDTO: {},
        facilityPaperID: this.facilityPaper.facilityPaperID,
        supportingDocID: doc.supportingDocID,
        status: Constants.statusConst.ACT,
        uploadedUserDisplayName:
          this.applicationService.getLoggedInUserDisplayName(),
        uploadedDivCode: this.applicationService.getLoggedInUserDivCode(),
        cribStatus: element.cribStatus,
        cribIssueDateStr: element.cribIssueDateStr,
        remark: element.remark,
        inquiryReason: element.inquiryReason,
        fullName: element.fullName,
        gender: element.gender,
        identificationType:
          element.customerType == Constants.customerType.BUSINESS
            ? Constants.customerIdentificationType.BRN
            : element.identificationType,
        identificationNumber: element.identificationNumber,
        documentName: this.getReportName(element.identificationNumber),
        isSystem: element.isNew
          ? Constants.yesNoConst.N
          : Constants.yesNoConst.Y,
      };
      requestList.push(rowData);
    });

    formData.append("uploadRequestData", JSON.stringify(requestList));

    this.facilityPaperAddEditService
      .uploadFpCribDocumentList(formData)
      .then((res: any) => {
        if (res) {
          this.alertService.showToaster(
            "Crib details has been saved successfully.",
            SETTINGS.TOASTER_MESSAGES.success
          );
          this.mdbModalRef.hide();
        }
      });
  }

  isReportLoaing() {
    return this.cribUsers.some((d: any) => d.isLoading);
  }

  checkRequiredReport() {
    return this.cribUsers.some((d: any) => d.isRequired);
  }

  saveSearch(request: any) {
    let formData = new FormData();

    formData.append(
      "uploadingFile",
      this.b64toBlob(request.report),
      this.getReportName(request.identificationNumber)
    );
    let rowData: any = {
      cribSearchHistoryId: 0,
      docStorageDTO: {},
      uploadedUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      uploadedDivCode: this.applicationService.getLoggedInUserDivCode(),
      remark: request.remark,
      fullName: request.fullName,
      gender: request.gender,
      identificationType:
        request.customerType == Constants.customerType.BUSINESS
          ? Constants.customerIdentificationType.BRN
          : Constants.customerIdentificationType.NIC,
      identificationNumber: request.identificationNumber,
      facilityAmount: request.facilityAmount,
      inquiryReason: request.inquiryReason,
    };
    formData.append("uploadRequestData", JSON.stringify(rowData));

    this.commons.showLoading();
    this.cribReportServices.saveCribSearch(formData).then((res: any) => {
      this.commons.hideLoading();
    });
  }
}
