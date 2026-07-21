import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Subscription } from "rxjs";
import { SETTINGS } from "../../../../../../../core/setting/commons.settings";
import { AlertService } from "../../../../../../../core/service/common/alert.service";
import { CasDocumentStorageService } from "../../../../../../../core/service/data/cas-document-storage.service";
import jsPDF from "jspdf";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-fp-default-full-view",
  templateUrl: "./fp-default-full-view.component.html",
  styleUrls: ["./fp-default-full-view.component.scss"],
})
export class FpDefaultFullViewComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper;
  @Input("isCommentEnable") isCommentEnable: Boolean;

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  upcTemplateList = [];
  customerList = [];
  fpUpcSectionData: any = [];
  creditScheduleESBResponseStatusList: any = [];
  onTreeUpdateChangeSub = new Subscription();
  onUpcTemplateLoadChangeSub = new Subscription();
  onCreditCalculatedFacilitiesESBResponseStatusChangeSub = new Subscription();
  onDownLoadLinkChangeSub = new Subscription();
  @ViewChild("downloadLink", { static: false })
  private downloadLink: ElementRef;

  customerObj: any = {};
  onBaseFacilityPaperChangeSub: Subscription = new Subscription();
  isCommitteePaper: boolean = false;
  isESGPaper: boolean = false;
  isDeviationTableEditable:boolean = false;
  analyticsDecision:any = null;

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly documentStorageService: CasDocumentStorageService,
    private readonly alertService: AlertService
  ) {}

  ngOnInit() {
    this.customerList = this.facilityPaper.casCustomerDTOList.sort((x, y) => {
      return x.isPrimary == y.isPrimary ? 0 : x.isPrimary ? -1 : 1;
    });

    this.onUpcTemplateLoadChangeSub =
      this.facilityPaperAddEditService.onUpcTemplateListLoad.subscribe(
        (data: any) => {
          this.upcTemplateList = data;
        }
      );

    this.onTreeUpdateChangeSub =
      this.facilityPaperAddEditService.onFpUpcSectionChange.subscribe(
        (data: any) => {
          this.fpUpcSectionData = data.fpUpcSectionDataDTOList;
        }
      );

    this.onDownLoadLinkChangeSub =
      this.documentStorageService.onDocumentStorageChange.subscribe(
        (data: any) => {
          if (this.downloadLink) {
            let downloadLink = this.downloadLink.nativeElement;
            downloadLink.href = window.URL.createObjectURL(data.data);
            downloadLink.download = data.fileName;
            downloadLink.click();
            this.alertService.showToaster(
              "FpDocument downloaded successfully",
              SETTINGS.TOASTER_MESSAGES.success
            );
          }
        }
      );

    this.onCreditCalculatedFacilitiesESBResponseStatusChangeSub =
      this.facilityPaperAddEditService.onCreditCalculatedFacilitiesESBResponseStatusChange.subscribe(
        (data: any) => {
          this.creditScheduleESBResponseStatusList = data;
        }
      );

    this.onBaseFacilityPaperChangeSub =
      this.facilityPaperAddEditService.onBaseFacilityPaperChange.subscribe(
        (data: any) => {
          this.isCommitteePaper = data.isCommittee === Constants.yesNoConst.Y;
          this.isESGPaper = data.isESGPaper === Constants.yesNoConst.Y;
          if(this.analyticsDecision === null){
            this.analyticsDecision = data.analyticsDecision;
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onUpcTemplateLoadChangeSub.unsubscribe();
    this.onDownLoadLinkChangeSub.unsubscribe();
    this.onTreeUpdateChangeSub.unsubscribe();
    this.onCreditCalculatedFacilitiesESBResponseStatusChangeSub.unsubscribe();
    this.onBaseFacilityPaperChangeSub.unsubscribe();
  }

  fullPaperWindow() {
    var pageContent = document.documentElement.outerHTML;

    var tempContainer = document.createElement("div");
    tempContainer.innerHTML = pageContent;

    var elementsToRemove = tempContainer.querySelectorAll(
      "#sidenav, #navbar, #remove-view-section"
    );
    if (elementsToRemove) {
      elementsToRemove.forEach(function (element) {
        element.remove();
      });
    }

    var elementToRead = document.getElementById(
      "filter-full-paper-view-section"
    );
    if (elementToRead) {
      var elementContent = elementToRead.outerHTML;
      tempContainer.innerHTML += elementContent;
    }

    var elementsToRemoveFromElementContent = tempContainer.querySelectorAll(
      "#app-personal-customer-stat-wrapper, #view-crib-details-btn"
    );
    if (elementsToRemoveFromElementContent) {
      elementsToRemoveFromElementContent.forEach(function (element) {
        element.remove();
      });
    }

    var updatedContent = tempContainer.innerHTML;

    var inputFields = document.querySelectorAll("input");

    inputFields.forEach(function (input) {
      var regex = new RegExp('id="' + input.id + '"');
      updatedContent = updatedContent.replace(
        regex,
        'value="' + input.value + '"'
      );
    });

    updatedContent = updatedContent.replace(
      /"table simple-table padding-left-unset"/g,
      '"table simple-table padding-left-unset" style="font-size : 13px"'
    );

    updatedContent = updatedContent.replace(
      /mdb-card-body/g,
      'mdb-card-body style="margin-bottom: 30px"'
    );

    updatedContent = updatedContent.replace(
      /style="height: 30px;"/g,
      'style="height: 30px; font-size : 14px;"'
    );

    updatedContent = updatedContent.replace(
      /"width-100 preview-mode"/g,
      '"width-100 preview-mode" style="font-size : 14px"'
    );

    updatedContent = updatedContent.replace(
      /"width-100 mt-10p"/g,
      '"width-100 mt-10p" style="font-size : 14px"'
    );

    updatedContent = updatedContent.replace(
      /"w-100-p table"/g,
      '"w-100-p table" style="font-size : 14px"'
    );

    updatedContent = updatedContent.replace(
      /"custom-bordered-table width-100 mt-5p"/g,
      '"custom-bordered-table width-100 mt-5p" style="font-size : 14px"'
    );

    var newWindow = window.open();

    newWindow.document.write(
      "<html>" +
        "<head>" +
        "<title>" +
        this.facilityPaper.fpRefNumber +
        "</title>" +
        "</head>" +
        "<body>" +
        '<div class="d-flex justify-content-center m-bottom-20px">' +
        '<div class="width-90 m-top-20px " style="border: 3px solid #000;">' +
        updatedContent +
        "<div>" +
        "<div>" +
        "</body>" +
        "</html>"
    );
  }

  // generatePdf() {
  //   const element = document.getElementById("filter-full-paper-view-section");

  //   if (!element) {
  //     this.alertService.showToaster(
  //       "An error occurd. please try again later.",
  //       SETTINGS.TOASTER_MESSAGES.error
  //     );
  //     return;
  //   }

  //   var pdfContainer = document.createElement("div");
  //   pdfContainer.className = "d-flex justify-content-center m-0 p-0";
  //   pdfContainer.innerHTML = element.innerHTML;

  //   const updatedContent = pdfContainer;

  //   updatedContent.querySelectorAll(".btn").forEach((item: Element) => {
  //     item.remove();
  //   });

  //   var pdf = new jsPDF("p", "mm", [800, element.offsetHeight], true);
  //   let fileName: string = `${this.facilityPaper.fpRefNumber}.pdf`;
  //   pdf.html(updatedContent, {
  //     callback: function (pdf) {
  //       pdf.save(fileName);
  //     },
  //     windowWidth: element.offsetWidth,
  //     width: pdf.internal.pageSize.getWidth(),
  //     autoPaging: false,
  //     margin: 0,
  //     html2canvas: { useCORS: true, svgRendering: true },
  //   });
  // }
}
