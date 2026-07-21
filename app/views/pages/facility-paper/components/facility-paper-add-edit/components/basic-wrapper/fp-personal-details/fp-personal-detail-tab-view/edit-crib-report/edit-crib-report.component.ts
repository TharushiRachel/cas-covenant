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
import { CasDocumentStorageService } from "src/app/core/service/data/cas-document-storage.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { AppUtils } from "src/app/shared/app.utils";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { CribRequest } from "src/app/shared/interfaces/CribRequest";
import { CribResponse } from "src/app/shared/interfaces/CribResponse";
import { CribReportService } from "src/app/views/pages/facility-paper/services/crib-report.service";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-edit-crib-report",
  templateUrl: "./edit-crib-report.component.html",
  styleUrls: ["./edit-crib-report.component.scss"],
})
export class EditCribReportComponent implements OnInit, OnDestroy {
  reportItem: CribResponse = {
    cribDetailsID: 0,
    facilityPaperID: 0,
    supportingDocID: 0,
    documentName: "",
    docStorageDTO: {
      docStorageID: 0,
      description: "",
      fileName: "",
      document: null,
      lastUpdatedDateStr: "",
    },
    cribStatus: "",
    cribIssueDate: null,
    cribIssueDateStr: "",
    remark: "",
    uploadedUserDisplayName: "",
    uploadedDivCode: "",
    status: "",
    fullName: "",
    gender: "",
    identificationType: "",
    identificationNumber: "",
    createdDateStr: "",
    createdBy: "",
    isSystem: "",
    docStorageID: 0,
    modifiedDateStr: "",
    customerType: "",
    isReportUpdated: false,
    reportName: "",
    report: null,
    inquiryReason: "",
  };
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
  };
  errors: any = {
    fullName: "",
    customerType: "",
    identificationType: "",
    identificationNumber: "",
    gender: "",
    cribStatus: "",
    cribIssueDateStr: "",
    reportName: "",
    inquiryReason: "",
  };
  facilityPaper: any;

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
  isReportLoading: boolean = false;
  isMannualUploading: boolean = false;

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

  prevCustomerType: string = "";
  prevIdentificationType: string = "";
  modalRef: MDBModalRef;

  onFacilityPaperBaseDataChange = new Subscription();

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly applicationService: ApplicationService,
    public mdbModalRef: MDBModalRef,
    private readonly alertService: AlertService,
    private readonly cribReportServices: CribReportService,
    private readonly documentStorageService: CasDocumentStorageService,
    private readonly commons: CommonService,
    private readonly mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    this.reportItem = {
      ...this.reportItem,
      customerType:
        this.reportItem.identificationType ==
        Constants.customerIdentificationType.BRN
          ? this.customerType.BUSINESS
          : this.customerType.PERSONAL,
      isReportUpdated: false,
      reportName: this.reportItem.documentName,
      report: this.reportItem.docStorageDTO
        ? this.reportItem.docStorageDTO.document
        : "",
      cribIssueDateStr: moment(this.reportItem.cribIssueDateStr).format(
        "DD/MM/YYYY"
      ),
    };
    this.prevCustomerType = this.reportItem.customerType;
    this.prevIdentificationType = this.reportItem.identificationType;

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

  handleCustomerTypeChange() {
    if (this.prevCustomerType != this.reportItem.customerType) {
      this.reportItem.identificationType =
        Constants.customerIdentificationType.NIC;
      this.reportItem.identificationNumber = "";
      this.prevCustomerType = this.reportItem.customerType;
    }
  }

  handleIdentificationChange() {
    if (this.prevIdentificationType != this.reportItem.identificationType) {
      this.reportItem.identificationNumber = "";
      this.prevIdentificationType = this.reportItem.identificationType;
    }
  }

  getCribReport(item: any) {
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
          this.setItemErrors();

          setTimeout(() => {
            this.clearItemErrors();
          }, 2500);

          this.alertService.showToaster(
            "Reuired data are missing. Please check and continue.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      }

      if (item.customerType == this.customerType.BUSINESS) {
        if (item.fullName && item.identificationNumber) {
          this.handleCribConfirmation(item);
        } else {
          this.setItemErrors();

          setTimeout(() => {
            this.clearItemErrors();
          }, 2500);

          this.alertService.showToaster(
            "Reuired data are missing. Please check and continue.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
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

  setItemErrors() {
    this.reportItemErrors = {
      fullName: this.reportItem.fullName == "" ? "Full name is required." : "",
      customerType:
        this.reportItem.customerType == "" ? "Customer type is required." : "",
      identificationType:
        this.reportItem.customerType == Constants.customerType.PERSONAL &&
        this.reportItem.identificationType == ""
          ? "Identification Type is required."
          : "",
      identificationNumber: this.validateIdentification(this.reportItem),
      gender:
        this.reportItem.customerType == this.customerType.PERSONAL &&
        this.reportItem.gender == ""
          ? "Gender is required."
          : "",
      inquiryReason:
        this.reportItem.inquiryReason == ""
          ? "Inquiry Reason is required."
          : "",
    };
  }

  getIndividualReport(item: any) {
    this.isReportLoading = true;
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
      inquiryReason: "EvaluatingOfABorrowerForANewCreditFacility",
    };
    this.cribReportServices
      .searchIndividualCrib(request)
      .then((resp: any) => {
        if (resp) {
          this.isReportLoading = false;
          this.reportItem = {
            ...this.reportItem,
            report: resp.report,
            reportName: this.getReportName(item.identificationNumber),
            isReportUpdated: true,
          };

          let saveRequest: any = {
            ...item,
            report: resp.report,
            facilityAmount: this.facilityPaper.totalExposureNew
              ? this.facilityPaper.totalExposureNew
              : 0,
            customerType: this.customerType.PERSONAL,
          };
          this.saveSearch(saveRequest);
        } else {
          this.isReportLoading = false;
        }
      })
      .catch((err: any) => {
        this.alertService.showToaster(
          "There is a technical error. Please try again late.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  getCompanyReport(item: any) {
    this.isReportLoading = true;
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
      inquiryReason: "EvaluatingOfABorrowerForANewCreditFacility",
    };

    this.cribReportServices
      .searchCompanyCrib(request)
      .then((resp: any) => {
        if (resp) {
          this.isReportLoading = false;
          this.reportItem = {
            ...this.reportItem,
            report: resp.report,
            reportName: this.getReportName(item.identificationNumber),
            isReportUpdated: true,
          };

          let saveRequest: any = {
            ...item,
            report: resp.report,
            facilityAmount: this.facilityPaper.totalExposureNew
              ? this.facilityPaper.totalExposureNew
              : 0,
            customerType: this.customerType.BUSINESS,
          };
          this.saveSearch(saveRequest);
        } else {
          this.isReportLoading = false;
        }
      })
      .catch((err: any) => {
        this.alertService.showToaster(
          "There is a technical error. Please try again late.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  getReportName(id: string): string {
    return `Crib Report-${id}`;
  }

  uploadReport() {
    this.isMannualUploading = true;
  }

  handleFileUpload(event: any) {
    const file = event.target.files[0];
    if (file) {
      const fileType: any = file.type;
      if (fileType && fileType == "application/pdf") {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          let result: any = reader.result;
          this.reportItem = {
            ...this.reportItem,
            report: result.split(",")[1],
            reportName: this.getReportName(
              this.reportItem.identificationNumber
            ),
            isReportUpdated: true,
          };
          this.isMannualUploading = false;
        };
      } else {
        this.alertService.showToaster(
          "Invalid file type.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    }
  }

  closeFileUpload() {
    this.isMannualUploading = false;
  }

  removeReport() {
    this.reportItem = {
      ...this.reportItem,
      report: "",
      reportName: "",
    };
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
    this.reportItemErrors = {
      fullName: this.reportItem.fullName == "" ? "Full name is required." : "",
      customerType:
        this.reportItem.customerType == "" ? "Customer type is required." : "",
      identificationType:
        this.reportItem.customerType == Constants.customerType.PERSONAL &&
        this.reportItem.identificationType == ""
          ? "Identification Type is required."
          : "",
      identificationNumber: this.validateIdentification(this.reportItem),
      gender:
        this.reportItem.customerType == this.customerType.PERSONAL &&
        this.reportItem.gender == ""
          ? "Gender is required."
          : "",
      cribStatus:
        this.reportItem.cribStatus == "" ? "Crib status is required." : "",
      cribIssueDateStr:
        this.reportItem.cribIssueDateStr == ""
          ? "Crib issue date is required."
          : "",
      reportName: this.reportItem.reportName == "" ? "Report is required." : "",
      inquiryReason:
        this.reportItem.inquiryReason == ""
          ? "Inquiry Reason is required."
          : "",
    };

    if (JSON.stringify(this.reportItemErrors) == JSON.stringify(this.errors)) {
      this.editCribDocument();
    } else {
      console.log(this.reportItemErrors);
      setTimeout(() => {
        this.clearItemErrors();
      }, 2500);
      this.alertService.showToaster(
        "Reuired data are missing. Please check and continue.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
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
    this.reportItemErrors = {
      ...this.errors,
    };
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

  editCribDocument() {
    let formData = new FormData();
    let rowData: any = {
      ...this.reportItem,
      uploadedUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      uploadedDivCode: this.applicationService.getLoggedInUserDivCode(),
      identificationType:
        this.reportItem.customerType == Constants.customerType.BUSINESS
          ? Constants.customerIdentificationType.BRN
          : this.reportItem.identificationType,
      documentName: this.getReportName(this.reportItem.identificationNumber),
      report: "",
      docStorageDTO: {},
    };

    if (this.reportItem.isReportUpdated) {
      formData.append(
        "uploadingFile",
        this.b64toBlob(this.reportItem.report),
        this.getReportName(this.reportItem.identificationNumber)
      );
    }
    formData.append("uploadRequestData", JSON.stringify(rowData));

    this.facilityPaperAddEditService
      .updateCribReport(formData)
      .then((res: any) => {
        if (res) {
          this.alertService.showToaster(
            "Crib details has been updated successfully.",
            SETTINGS.TOASTER_MESSAGES.success
          );
          this.mdbModalRef.hide();
        }
      });
  }

  isNotSystemUser() {
    return this.reportItem.isSystem == Constants.yesNoConst.Y;
  }

  viewDocument() {
    if (this.reportItem.isReportUpdated) {
      this.viewNewReport();
    } else {
      this.viewReport();
    }
  }

  downloadDocument() {
    if (this.reportItem.isReportUpdated) {
      this.downloadNewReport();
    } else {
      this.downloadReport();
    }
  }

  viewReport() {
    if (this.reportItem.docStorageDTO.docStorageID != null) {
      this.documentStorageService
        .viewDocument(this.reportItem.docStorageDTO)
        .then((data: any) => {
          this.viewInNewTab(data, this.reportItem.docStorageDTO.fileName);
        });
    }
  }

  downloadReport() {
    if (this.reportItem.docStorageDTO.docStorageID != null) {
      this.documentStorageService
        .downloadDocument(this.reportItem.docStorageDTO)
        .then((data: any) => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data);
          downloadLink.download = `${this.reportItem.docStorageDTO.fileName}.pdf`;
          downloadLink.click();
          this.alertService.showToaster(
            "Document downloaded successfully",
            SETTINGS.TOASTER_MESSAGES.success
          );
        });
    }
  }

  viewNewReport() {
    this.viewInNewTab(
      this.b64toBlob(this.reportItem.report),
      this.getReportName(this.reportItem.identificationNumber)
    );
  }

  downloadNewReport() {
    const source = `data:application/pdf;base64,${this.reportItem.report}`;
    const link = document.createElement("a");
    link.href = source;
    link.download = `${this.getReportName(
      this.reportItem.identificationNumber
    )}.pdf`;
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
          : request.identificationType,
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
