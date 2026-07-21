import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { CommonService } from "src/app/core/service/common/common.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { AppUtils } from "src/app/shared/app.utils";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { CribRequest } from "src/app/shared/interfaces/CribRequest";
import { CribReportService } from "src/app/views/pages/facility-paper/services/crib-report.service";

@Component({
  selector: "app-crib-report",
  templateUrl: "./crib-report.component.html",
  styleUrls: ["./crib-report.component.scss"],
})
export class CribReportComponent implements OnInit {
  genderOptions: any[] = [
    { value: "Male", label: "Male" },
    { value: "Female", label: "Female" },
    { value: "Other", label: "Other" },
  ];

  personalIDOptions: any[] = [
    { value: "NIC", label: "NIC" },
    { value: "PP", label: "Passport" },
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

  selectedTabIndex: any = 0;
  reportList: any[] = [];
  customerType: any = Constants.customerType;

  formData: any = {
    identificationType: Constants.customerIdentificationType.NIC,
    identificationNumber: "",
    fullName: "",
    gender: "",
    facilityAmount: "",
    inquiryReason: "",
    remark: "",
  };

  formErrors: any = {
    identificationType: "",
    identificationNumber: "",
    fullName: "",
    gender: "",
    facilityAmount: "",
    inquiryReason: "",
  };

  emptyFormErrors: any = {
    identificationType: "",
    identificationNumber: "",
    fullName: "",
    gender: "",
    facilityAmount: "",
    inquiryReason: "",
  };

  modalRef: MDBModalRef;

  constructor(
    private readonly cribReportServices: CribReportService,
    private readonly alertService: AlertService,
    private readonly commons: CommonService,
    private readonly applicationService: ApplicationService,
    private readonly mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    //
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event: any) {
    if (this.selectedTabIndex != $event) {
      this.clearSearch();
    }
    this.selectedTabIndex = $event;
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

  doSearch(type: string) {
    this.formErrors = {
      identificationType:
        type == this.customerType.PERSONAL &&
        this.formData.identificationType == ""
          ? "Identification Type is required."
          : "",
      identificationNumber: this.validateIdentification(type),
      fullName: this.formData.fullName == "" ? "Full Name is required." : "",
      gender:
        type == this.customerType.PERSONAL && this.formData.gender == ""
          ? "Gender is required."
          : "",
      facilityAmount:
        this.formData.facilityAmount == ""
          ? "Facility Amount is required."
          : "",
      inquiryReason:
        this.formData.inquiryReason == "" ? "Inquiry Reason is required." : "",
    };

    if (
      JSON.stringify(this.formErrors) == JSON.stringify(this.emptyFormErrors)
    ) {
      let identificationNumber: string = this.formData.identificationNumber;
      this.formData = {
        ...this.formData,
        identificationNumber: identificationNumber.replace(/\s/g, ""),
      };

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
          message: 'This service will incur charges. Do you want to proceed?',
          isCribConfirmation: true
        },
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          if (type == this.customerType.PERSONAL) {
            this.getIndividualReport(this.formData);
          }

          if (type == this.customerType.BUSINESS) {
            this.getCompanyReport(this.formData);
          }
        }
      });
    } else {
      setTimeout(() => {
        this.formErrors = {
          ...this.emptyFormErrors,
        };
      }, 2500);
      this.alertService.showToaster(
        "Reuired data are missing. Please check and continue.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  validateIdentification(type: string) {
    let msg: string = "";
    if (
      type == this.customerType.PERSONAL &&
      this.formData.identificationType ==
        Constants.customerIdentificationType.NIC
    ) {
      if (this.formData.identificationNumber) {
        if (!AppUtils.isNic(this.formData.identificationNumber)) {
          msg = "Invalid NIC format.";
        }
      } else {
        msg = "NIC is required.";
      }
    } else {
      msg =
        this.formData.identificationNumber == "" ? "Passport is required." : "";
    }

    if (
      type == this.customerType.BUSINESS &&
      this.formData.identificationNumber == ""
    ) {
      msg = "BRN is required.";
    }

    return msg;
  }

  getIndividualReport(payload: any) {
    let request: CribRequest = {
      applicationNumber: `${
        payload.identificationNumber
      }-${this.generateRandomNumber()}`,
      applicationDate: moment().format("YYYY-MM-DD"),
      fullName: payload.fullName,
      gender: payload.gender,
      creditFacilityType: "LOAN",
      creditFacilityCurrency: "LKR",
      reportDate: moment().format("YYYY-MM-DD"),
      creditFacilityAmountDTO: {
        value: payload.facilityAmount,
        currency: "LKR",
        localValue: "",
      },
      idNumberDTOList: [
        {
          idNumberType:
            payload.identificationType == "PP" ? "PassportNumber" : "NIC",
          idNumber: payload.identificationNumber,
        },
      ],
      inquiryReason: payload.inquiryReason,
    };
    this.commons.showLoading();

    this.cribReportServices
      .searchIndividualCrib(request)
      .then((resp: any) => {
        if (resp) {
          let report: any = {
            fullName: payload.fullName,
            createdDate: moment().format("MMMM Do YYYY, h:mm:ss a"),
            report: resp.report,
            token: resp.token,
            identificationNumber: payload.identificationNumber,
          };
          this.reportList.push(report);

          let saveRequest: any = {
            ...this.formData,
            report: resp.report,
            customerType: this.customerType.PERSONAL,
          };
          this.saveSearch(saveRequest);
          this.commons.hideLoading();
        } else {
          this.commons.hideLoading();
        }
      })
      .catch((err: any) => {
        this.commons.hideLoading();
        this.alertService.showToaster(
          "There is a technical error. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  getCompanyReport(payload: any) {
    let request: CribRequest = {
      applicationNumber: `${
        payload.identificationNumber
      }-${this.generateRandomNumber()}`,
      applicationDate: moment().format("YYYY-MM-DD"),
      fullName: payload.fullName,
      gender: "",
      creditFacilityType: "LOAN",
      creditFacilityCurrency: "LKR",
      reportDate: moment().format("YYYY-MM-DD"),
      creditFacilityAmountDTO: {
        value: payload.facilityAmount,
        currency: "LKR",
        localValue: "",
      },
      idNumberDTOList: [
        {
          idNumberType: "BusinessRegistrationNumber",
          idNumber: payload.identificationNumber,
        },
      ],
      inquiryReason: payload.inquiryReason,
    };
    this.commons.showLoading();

    this.cribReportServices
      .searchCompanyCrib(request)
      .then((resp: any) => {
        if (resp) {
          let report: any = {
            fullName: payload.fullName,
            createdDate: moment().format("MMMM Do YYYY, h:mm:ss a"),
            report: resp.report,
            token: resp.token,
            identificationNumber: payload.identificationNumber,
          };
          this.reportList.push(report);

          let saveRequest: any = {
            ...this.formData,
            report: resp.report,
            customerType: this.customerType.BUSINESS,
          };
          this.saveSearch(saveRequest);
          this.commons.hideLoading();
        } else {
          this.commons.hideLoading();
        }
      })
      .catch((err: any) => {
        this.commons.hideLoading();
        this.alertService.showToaster(
          "There is a technical error. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  viewReport(item: any) {
    this.viewInNewTab(
      this.b64toBlob(item.report),
      this.getReportName(item.identificationNumber)
    );
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
                a.download = '${fileName}.pdf'; // Set the desired download name here
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

  getReportName(id: string): string {
    return `Crib Report-${id}`;
  }

  downloadReport(item: any) {
    const source = `data:application/pdf;base64,${item.report}`;
    const link = document.createElement("a");
    link.href = source;
    link.download = `${this.getReportName(item.identificationNumber)}.pdf`;
    link.click();
  }

  clearSearch() {
    this.formData = {
      identificationType: "NIC",
      identificationNumber: "",
      fullName: "",
      gender: "",
      facilityAmount: "",
      inquiryReason: "",
    };
    this.formErrors = this.emptyFormErrors;
    this.reportList = [];
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

  generateRandomNumber() {
    let epoch = new Date().getTime();
    return epoch;
  }
}
