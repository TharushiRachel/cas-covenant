import { Component, Input, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { CurrencyPipe } from "@angular/common";
import { AppUtils } from "src/app/shared/app.utils";
import { isEmpty } from "lodash";
import { Constants } from "src/app/core/setting/constants";
import * as _ from "lodash";
import jsPDF from "jspdf";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { CacheService } from "src/app/core/service/data/cache.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import * as moment from "moment";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ReportFacilityChangeModalComponent } from "src/app/shared/components/report-facility-change-modal/report-facility-change-modal.component";
import { UPCTemplateComparisonComponent } from "../fp-editor/upc-template-structure/upc-template-comparison/upc-template-comparison.component";
import html2pdf from "html2pdf.js";

@Component({
  selector: "app-fp-bcc-full-view",
  templateUrl: "./fp-bcc-full-view.component.html",
  styleUrls: ["./fp-bcc-full-view.component.scss"],
})
export class FpBccFullViewComponent implements OnInit {
  onCalculateFacilityPaperExposureSubj = new Subscription();
  onFacilityPaperBaseDataChangeSub = new Subscription();
  onFPaperSecSummeryChangeSub = new Subscription();

  @Input("facilityPaper") facilityPaper: any;
  facilityPaperData: any;
  facilityList: any[] = [];
  upcTemplateList: any[] = [];
  fpUpcSectionData: any[] = [];
  facilityCommonSecurities: any[] = [];
  covenantVal: any[] = [];
  yesNoConst = Constants.yesNoConst;
  facilitiesCovenants: any[] = [];
  customerCovenants: any[] = [];
  creditFacilityName: any[] = [];
  applicationCovenantDetail: any = {};
  onUpcTemplateLoadChangeSub = new Subscription();
  onTreeUpdateChangeSub = new Subscription();
  onCreditCalculatedFacilitiesESBResponseStatusChangeSub = new Subscription();
  onUPCFustaceChangeSub = new Subscription();
  onAddEditCreditRiskReplyChangeSub = new Subscription();

  covenantsWithSingleFacility: any[] = [];
  covenantsWithMultipleFacilities: any[] = [];
  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  allBankOptions: any = {};

  signatures: any[] = [];
  upcSectionsFusTraces: any[] = [];

  committeeSignatureWC: any = Constants.committeeSignatureUsers;
  committeeSignatureDES: any = Constants.committeeSignatureDesignation;

  modalRef: MDBModalRef;
  creditScheduleESBResponseStatusList: [];
  upcTemplateComparisonModelRef: MDBModalRef;
  initNodeRowData: any[] = [];
  upcTemplateFormat: any[] = [];

  fpFinalStatusData: any = {
    isShow: false,
    src: "",
    description: "",
    color: "",
  };

  fpFinalStatusData_Reccomond: any = {
    isShow: false,
    src: "",
    description_1: "",
    description_2: "",
    date: "",
    status: "",
    fontColor: "",
  };

  statusConst: any = Constants.statusConst;
  isESGPaper: boolean = false;

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly currencyPipe: CurrencyPipe,
    private readonly cacheService: CacheService,
    private readonly alertService: AlertService,
    private readonly applicationService: ApplicationService,
    private readonly mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    this.facilityPaperData = Object.assign({}, this.facilityPaper);
    this.isESGPaper =
      this.facilityPaperData !== null
        ? this.facilityPaperData.isESGPaper === Constants.yesNoConst.Y
        : false;
    if (this.isHigherWCUser()) {
      this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
        this.facilityPaper,
        Constants.fusTraceFlag.UPCT
      );

      this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
        this.facilityPaper,
        Constants.fusTraceFlag.FAC
      );
    }

    if (this.facilityPaper.facilityDTOList) {
      this.facilityCommonSecurities = this.setCommonSecurities(
        this.facilityPaper.facilityDTOList
      );

      if (
        this.facilityPaper.currentFacilityPaperStatus &&
        this.facilityPaper.currentFacilityPaperStatus !=
          Constants.facilityPaperStatusConst.DRAFT
      ) {
        this.getFacilityPpaerSignatures(this.facilityPaper.facilityPaperID);
      }
    }

    this.getCustomerCovenants();
    this.getFacilityCovenants();

    this.onUpcTemplateLoadChangeSub =
      this.facilityPaperAddEditService.onUpcTemplateListLoad.subscribe(
        (data: any) => {
          this.upcTemplateList = data !== null ? data : [];
        }
      );

    this.onTreeUpdateChangeSub =
      this.facilityPaperAddEditService.onFpUpcSectionChange.subscribe(
        (data: any) => {
          this.fpUpcSectionData =
            data !== null && data.fpUpcSectionDataDTOList
              ? data.fpUpcSectionDataDTOList
              : [];
          this.generateTreeFeedUsingExistingFpSections(this.fpUpcSectionData);
        }
      );

    this.onCreditCalculatedFacilitiesESBResponseStatusChangeSub =
      this.facilityPaperAddEditService.onCreditCalculatedFacilitiesESBResponseStatusChange.subscribe(
        (data: any) => {
          this.facilityList = data !== null ? data : [];
          this.creditScheduleESBResponseStatusList = data !== null ? data : [];
        }
      );

    this.onCalculateFacilityPaperExposureSubj =
      this.facilityPaperAddEditService.onCalculateFacilityPaperExposureChange.subscribe(
        (response: any) => {
          if (
            this.facilityPaperData.facilityPaperID == response.facilityPaperID
          ) {
            this.facilityPaperData = Object.assign(
              {},
              this.facilityPaper,
              response
            );
          }
        }
      );

    this.onFacilityPaperBaseDataChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperBaseDataChange.subscribe(
        (fpBase: any) => {
          if (fpBase) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, fpBase);

            if (this.facilityPaper.facilityDTOList) {
              this.facilityCommonSecurities = this.setCommonSecurities(
                this.facilityPaper.facilityDTOList
              );
            }

            if (
              this.facilityPaper.currentFacilityPaperStatus ==
              Constants.facilityPaperStatusConst.APPROVED
            ) {
              this.setApprovedStatusData();
            }

            if (
              this.facilityPaper.currentFacilityPaperStatus ==
              Constants.facilityPaperStatusConst.REJECTED
            ) {
              this.setRejectedStatusData();
            }
          }
        }
      );

    this.onFPaperSecSummeryChangeSub =
      this.facilityPaperAddEditService.onFPaperSecSummeryChange.subscribe(
        (facilityPaper: any) => {
          if (!_.isEmpty(facilityPaper) && facilityPaper.fpSecuritySummeryDTO) {
            this.facilityPaper = {
              ...this.facilityPaper,
              fpSecuritySummeryDTO: facilityPaper.fpSecuritySummeryDTO,
            };
          }
        }
      );

    this.onUPCFustaceChangeSub =
      this.facilityPaperAddEditService.onUPCFustaceChange.subscribe(
        (data: any) => {
          this.upcSectionsFusTraces = !_.isEmpty(data) ? data : [];
          if (this.fpUpcSectionData) {
            this.fpUpcSectionData = this.fpUpcSectionData.map((d: any) => ({
              ...d,
              commentCount: this.upcSectionsFusTraces.filter(
                (f: any) => f.mainKey == d.fpUpcSectionDataID
              ).length,
            }));
          }
        }
      );

    this.onAddEditCreditRiskReplyChangeSub =
      this.facilityPaperAddEditService.onAddEditCreditRiskReplyChange.subscribe(
        (res: any) => {
          if (
            res !== null &&
            res.fpCreditRiskCommentList &&
            res.fpCreditRiskCommentList.length > 0
          ) {
            this.facilityPaper = {
              ...this.facilityPaper,
              fpCreditRiskCommentList: res.fpCreditRiskCommentList,
              fpCreditRiskCommentFilterDTO: res.fpCreditRiskCommentFilterDTO,
            };
            this.fpUpcSectionData =
              this.facilityPaper !== null &&
              this.facilityPaper.fpUpcSectionDataDTOList
                ? this.facilityPaper.fpUpcSectionDataDTOList
                : [];
            this.generateTreeFeedUsingExistingFpSections(this.fpUpcSectionData);
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onUpcTemplateLoadChangeSub.unsubscribe();
    this.onTreeUpdateChangeSub.unsubscribe();
    this.onCreditCalculatedFacilitiesESBResponseStatusChangeSub.unsubscribe();
    this.onFacilityPaperBaseDataChangeSub.unsubscribe();
    this.onCalculateFacilityPaperExposureSubj.unsubscribe();
    this.onFPaperSecSummeryChangeSub.unsubscribe();
    this.onUPCFustaceChangeSub.unsubscribe();
    this.onAddEditCreditRiskReplyChangeSub.unsubscribe();
  }

  getUPCSectionByIDInFpSectionList(upcSectionID: any) {
    return _.find(
      this.fpUpcSectionData,
      (d: any) => d.upcSectionID == upcSectionID
    );
  }

  generateTreeFeedUsingExistingFpSections(upcTemplateList: any[]) {
    let data = _.sortBy(upcTemplateList, (i: any) => i.displayOrder);
    this.initNodeRowData = data;
    let nodeData: any[] = [];
    let addedIDs: any[] = [];

    let levelWiseParents = {};

    for (let i = 0; i < data.length; i++) {
      let dataItem = data[i];
      addedIDs.push(dataItem.upcSectionID);
      let upcSectionData = this.getUPCSectionByIDInFpSectionList(
        dataItem.upcSectionID
      );
      if (!dataItem.parentSectionID) {
        let mainParent = {
          name: upcSectionData.upcSectionName,
          item: upcSectionData,
          isExpanded: true,
          children: [],
        };

        nodeData.push(mainParent);
        levelWiseParents = {};
        levelWiseParents[0] = mainParent;
      } else {
        let upcSectionData = this.getUPCSectionByIDInFpSectionList(
          dataItem.upcSectionID
        );
        let itemData = {
          name: upcSectionData.upcSectionName,
          item: upcSectionData,
          isExpanded: true,
          children: [],
        };

        let currentLevel = dataItem.sectionLevel;
        let parentLevel = currentLevel - 1;

        if (parentLevel == 0) {
          let parent = levelWiseParents[parentLevel];
          parent.children.push(itemData);
        } else {
          let parents = levelWiseParents[parentLevel];
          let parent;
          for (let x = 0; x < parents.length; x++) {
            if (parents[x].item.upcSectionID == dataItem.parentSectionID) {
              parent = parents[x];
              break;
            }
          }

          parent.children.push(itemData);
        }
        if (!levelWiseParents[currentLevel]) {
          levelWiseParents[currentLevel] = [];
        }
        levelWiseParents[currentLevel].push(itemData);
      }
    }

    this.upcTemplateFormat = this.updateFormatWithExternalTemplate(nodeData);
  }

  updateFormatWithExternalTemplate(data: any[]) {
    let result: any[] = data.length > 0 ? data : [];

    if (result && result.length > 0) {
      let riskOpinion: any = this.prepareRiskOpinion();

      let mainParent1 = {
        name: "RISK OPINION",
        item: {
          upcSectionID: 0,
          upcSectionName: "RISK OPINION",
          data: riskOpinion.comment,
        },
        isExpanded: true,
        children: [
          {
            name: "RESPONSES TO RISK OPINION",
            item: {
              upcSectionID: 0,
              upcSectionName: "RESPONSES TO RISK OPINION",
              data: riskOpinion.reply,
            },
          },
        ],
      };

      if (this.isESGPaper) {
        mainParent1.children.push({
          name: "ENVIRONMENTAL AND SOCIAL GOVERNANCE",
          item: {
            upcSectionID: 0,
            upcSectionName: "ESG SUMMARY",
            data: "",
          },
        });
      }

      if (data.length > 3) {
        result.splice(3, 0, mainParent1);
      }

      result = result.map((r: any) => ({
        ...r,
        isFacility: r.name == "FACILITIES",
        isAnnexes: r.name == "ANNEXES",
      }));
    }

    return result;
  }

  prepareRiskOpinion() {
    let comments: any[] =
      this.facilityPaper.fpCreditRiskCommentList !== null
        ? this.facilityPaper.fpCreditRiskCommentList
        : [];

    let preparedOpinion: any = {
      comment: "",
      reply: "",
    };

    if (comments.length > 1) {
      comments.forEach((element: any, i: number) => {
        let response: string =
          element.fpCreditRiskReplyDTO !== null &&
          element.fpCreditRiskReplyDTO.replyComment
            ? element.fpCreditRiskReplyDTO.replyComment
            : "";

        preparedOpinion.comment =
          preparedOpinion.comment +
          this.prepareRiskSubHeading("Risk Opinion", i) +
          element.creditRiskComment +
          (i < comments.length - 1
            ? `<div style="border-bottom: 0.5px solid rgba(0, 0, 0, 0.5);"></div>`
            : "");

        preparedOpinion.reply =
          preparedOpinion.reply +
          this.prepareRiskSubHeading("Risk Response", i) +
          response +
          (i < comments.length - 1
            ? `<div style="border-bottom: 0.5px solid rgba(0, 0, 0, 0.5);"></div>`
            : "");
      });
    } else {
      let activeRiskComment: any =
        this.facilityPaper.fpCreditRiskCommentFilterDTO !== null &&
        this.facilityPaper.fpCreditRiskCommentFilterDTO.activeRiskComment
          ? this.facilityPaper.fpCreditRiskCommentFilterDTO.activeRiskComment
          : null;

      preparedOpinion.comment = activeRiskComment
        ? activeRiskComment.creditRiskComment
        : "";
      preparedOpinion.reply =
        activeRiskComment !== null && activeRiskComment.fpCreditRiskReplyDTO
          ? activeRiskComment.fpCreditRiskReplyDTO.replyComment
          : "";
    }

    return preparedOpinion;
  }

  prepareRiskSubHeading(title: string, index: number) {
    return `<br/><div class="sub-topic"><h6 class="text-start pdf-font-bold"
    style="font-weight: 600 !important; font-family: Arial, Helvetica, sans-serif !important;word-spacing: normal;">
    ${title} - ${index + 1}
    </h6></div><br/>`;
  }

  getCustomerCovenants() {
    this.facilityPaperAddEditService
      .getCovenantListByFpRefNumber()
      .then((data: any) => {
        if (data) {
          this.customerCovenants = data
            ? data.filter((cov: any) => cov.status == Constants.status.ACT)
            : [];
        }
      });
  }

  getFacilityCovenants() {
    this.facilityPaperAddEditService.getFacilityCovenantList().then((data) => {
      this.covenantsWithSingleFacility = [];
      this.covenantsWithMultipleFacilities = [];

      if (data) {
        this.covenantsWithSingleFacility = data
          .map((result: any) => {
            const activeCovValues1 = result.covValue
              .filter((covValue: any) => covValue.status === "Active")
              .map((covValue: any) => ({
                ...covValue,
                applicationCovenantFacilityDTOS:
                  covValue.applicationCovenantFacilityDTOS.sort(
                    (a: any, b: any) => a.displayOrder - b.displayOrder
                  ),
              }))
              .filter(
                (covValue: any) =>
                  covValue.applicationCovenantFacilityDTOS.length === 1
              );

            if (activeCovValues1.length > 0) {
              return {
                ...result,
                covValue: activeCovValues1.sort(
                  (a: any, b: any) =>
                    a.applicationCovenantFacilityDTOS[0].displayOrder -
                    b.applicationCovenantFacilityDTOS[0].displayOrder
                ),
              };
            } else {
              return null; // Return null for items with no active covValues
            }
          })
          .filter((result: any) => result !== null)
          .sort(
            (a: any, b: any) =>
              a.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder -
              b.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder
          );

        this.covenantsWithMultipleFacilities = data
          .map((result: any) => {
            const activeCovValues2 = result.covValue
              .filter((covValue: any) => covValue.status === "Active")
              .map((covValue: any) => ({
                ...covValue,
                applicationCovenantFacilityDTOS:
                  covValue.applicationCovenantFacilityDTOS.sort(
                    (a: any, b: any) => a.displayOrder - b.displayOrder
                  ),
              }))
              .filter(
                (covValue: any) =>
                  covValue.applicationCovenantFacilityDTOS.length > 1
              );

            if (activeCovValues2.length > 0) {
              return {
                ...result,
                covValue: activeCovValues2.sort(
                  (a: any, b: any) =>
                    a.applicationCovenantFacilityDTOS[0].displayOrder -
                    b.applicationCovenantFacilityDTOS[0].displayOrder
                ),
              };
            } else {
              return null; // Return null for items with no active covValues
            }
          })
          .filter((result: any) => result !== null)
          .sort(
            (a: any, b: any) =>
              a.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder -
              b.covValue[0].applicationCovenantFacilityDTOS[0].displayOrder
          );
      }
    });
  }

  getCmntCountById(id: any): number {
    let result: number = 0;

    let data_row: any = this.fpUpcSectionData.find(
      (d: any) => d.fpUpcSectionDataID == id
    );
    result =
      data_row !== null && data_row.commentCount ? data_row.commentCount : 0;

    return result;
  }

  getFormatedContent(value: any) {
    let content: any = "";
    if (value !== null && value.includes("<p>&nbsp;</p>")) {
      content = value.replace("<p>&nbsp;</p>", "");
    } else {
      content = value;
    }

    const contentDiv = document.createElement("div");
    contentDiv.innerHTML = content;

    contentDiv
      .querySelectorAll("p, strong, span")
      .forEach((element: HTMLElement) => {
        element.style.setProperty("font-size", "14px", "important");
        element.style.lineHeight = "1.4";
      });

    return contentDiv.innerHTML;
  }

  getFilterFacilites(facilities: any[], type: any) {
    let filterRecs: any[] = [];
    filterRecs = facilities.filter((d: any) => d.directFacility == type);

    let mapRecs: any[] = filterRecs.map((f: any) => ({
      ...f,
      index: this.getFacilityIndex(facilities, f.facilityID),
      parentFacility: f.parentFacilityID
        ? this.getParentFacilityIndex(facilities, f.parentFacilityID)
        : null,
      parentIndex: f.parentFacilityID
        ? this.getParentFacilityIndex(facilities, f.parentFacilityID)
            .parentIndex
        : "",
    }));

    return mapRecs;
  }

  setCommonSecurities(facilities: any[]) {
    let commonSecurities: any[] = [];

    facilities.forEach((element: any) => {
      let securities: any[] =
        element.facilitySecurityDTOList !== null
          ? element.facilitySecurityDTOList
          : [];

      if (securities.length > 0) {
        let elementCommonSecurities: any[] = securities.filter(
          (sec: any) => sec.isCommonSecurity == Constants.yesNoConst.Y
        );

        elementCommonSecurities.forEach((ecomsec: any) => {
          if (
            !commonSecurities.some(
              (csec: any) =>
                csec.facilitySecurityID == ecomsec.facilitySecurityID
            )
          ) {
            commonSecurities.push(ecomsec);
          }
        });
      }
    });

    commonSecurities.sort(
      (a: any, b: any) => a.facilitySecurityID - b.facilitySecurityID
    );
    return commonSecurities;
  }

  getFormattedValue(amount: any) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "");
    }
  }

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }

  getFacilityIndex(facilities: any[], id: any): any {
    let currentIndex: any = 0;

    if (facilities.length > 0) {
      currentIndex = facilities
        .map((f: any, index: number) => {
          if (f.facilityID == id) {
            return index + 1;
          }
        })
        .filter((f) => f)[0];
    }

    return currentIndex !== null ? currentIndex : 0;
  }

  getParentFacilityIndex(facilities: any[], parentId: any): any {
    let parentFacilty: any;

    if (facilities.length > 0) {
      parentFacilty = facilities
        .map((f: any, index: number) => {
          if (f.facilityID == parentId) {
            return { ...f, parentIndex: index + 1 };
          }
        })
        .filter((f) => f)[0];
    }
    return parentFacilty !== null ? parentFacilty : null;
  }

  filterFacilitySecurities(securities: any[]) {
    return securities.filter(
      (s: any) => s.isCommonSecurity == Constants.yesNoConst.N
    );
  }

  getFormattedThreeDecimalValues(amount: any) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  getCovenantFrequencyLabel(frequencyValue: any) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  getCovenantByFacility(facilityId: any): any[] {
    let covenants: any[] = [];
    if (this.covenantsWithSingleFacility.length > 0) {
      this.covenantsWithSingleFacility.forEach((element: any) => {
        if (element.covValue && element.covValue.length > 0) {
          element.covValue.forEach((covValueElement: any) => {
            if (
              covValueElement.applicationCovenantFacilityDTOS &&
              covValueElement.applicationCovenantFacilityDTOS.length > 0
            ) {
              if (
                covValueElement.applicationCovenantFacilityDTOS.some(
                  (s: any) => s.facilityID == facilityId
                )
              ) {
                covenants.push({
                  description: covValueElement.covenant_Description,
                  date:
                    covValueElement.covenant_Due_Date !== null
                      ? covValueElement.covenant_Due_Date
                      : "",
                  frequency:
                    covValueElement.covenant_Frequency !== null
                      ? covValueElement.covenant_Frequency
                      : "",
                });
              }
            }
          });
        }
      });
    }

    return covenants;
  }

  getFacilityVitalInfoData(vitalInfoDataList: any[], lable: any) {
    let vitalInfoData: any = "";
    if (vitalInfoDataList && vitalInfoDataList.length > 0) {
      let data_row: any = vitalInfoDataList.find(
        (d: any) => d.vitalInfoName == lable
      );
      vitalInfoData = data_row ? data_row.vitalInfoData : "";
    }

    return vitalInfoData;
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
        '<div class="d-flex justify-content-center m-bottom-14px">' +
        '<div class="width-90 m-top-14px " style="border: 3px solid #000;">' +
        updatedContent +
        "<div>" +
        "<div>" +
        "</body>" +
        "</html>"
    );
  }

  generatePdfOld() {
    const element = document.getElementById("filter-full-paper-view-section");

    if (!element) {
      this.alertService.showToaster(
        "An error occurd. please try again later.",
        SETTINGS.TOASTER_MESSAGES.error
      );
      return;
    }

    var pdfContainer = document.createElement("div");
    pdfContainer.className = "d-flex justify-content-center m-0 p-0";

    pdfContainer.innerHTML = element.innerHTML;

    const updatedContent = pdfContainer;

    updatedContent.querySelectorAll(".btn").forEach((item: Element) => {
      item.remove();
    });

    var pdf = new jsPDF({
      orientation: "p",
      unit: "px",
      format: "a4",
    });

    let fileName: string = `${this.facilityPaper.fpRefNumber}.pdf`;
    pdf.html(this.prepareStyleProp(updatedContent), {
      callback: function (pdf: any) {
        // Add page numbers
        const totalPages = pdf.internal.getNumberOfPages();
        for (let i = 1; i <= totalPages; i++) {
          pdf.setPage(i);
          pdf.setFontSize(10);
          pdf.text(
            `Page ${i} of ${totalPages}`,
            pdf.internal.pageSize.getWidth() - 70,
            pdf.internal.pageSize.getHeight() - 10
          );
        }
        pdf.save(fileName);
      },
      windowWidth: element.offsetWidth,
      width: pdf.internal.pageSize.getWidth(),
      autoPaging: "text",
      margin: [30, 0, 30, 0],
      fontFaces: [],
      html2canvas: {
        useCORS: true,
        svgRendering: true,
        letterRendering: true,
      },
    });
  }

  generatePdf() {
    const element = document.getElementById("filter-full-paper-view-section");
    const clonedElement = element.cloneNode(true) as HTMLElement;
    if (!element) {
      this.alertService.showToaster(
        "An error occurd. please try again later.",
        SETTINGS.TOASTER_MESSAGES.error
      );
      return;
    }

    clonedElement.querySelectorAll(".btn").forEach((item: Element) => {
      item.remove();
    });

    let fileName: string = `${this.facilityPaper.fpRefNumber}.pdf`;
    const preparedContent: string = this.prepareStyleProp(clonedElement);
    const options = {
      margin: [15, 0, 15, 0],
      filename: fileName,
      image: { type: "jpeg", quality: 0.98 },
      html2canvas: {
        scale: 1,
        useCORS: true,
        letterRendering: true,
      },
      jsPDF: {
        unit: "mm",
        format: "a4",
        orientation: "l",
        floatPrecision: "smart",
        hotfixes: ["px_scaling"],
      },
      pagebreak: {
        mode: ["avoid-all", "css", "legacy"],
      },
    };

    const worker = html2pdf().from(preparedContent).set(options).toPdf();

    worker
      .get("pdf")
      .then((pdf: any) => {
        const totalPages = pdf.internal.getNumberOfPages();

        for (let i = 1; i <= totalPages; i++) {
          pdf.setPage(i);
          pdf.setFontSize(10);
          pdf.text(
            `Page ${i} of ${totalPages}`,
            pdf.internal.pageSize.getWidth() - 30,
            pdf.internal.pageSize.getHeight() - 5
          );
        }
      })
      .then(() => {
        worker.save();
      });
  }

  prepareStyleProp(updatedContent: any) {
    let classes: FontSizeProp[] = [
      { prop: ".pdf-content-heading", size: "18px" },
      {
        prop: ".pdf-font-heading",
        size: "16px",
      },
      {
        prop: ".pdf-font-bold",
        size: "16px",
      },
      {
        prop: ".pdf-font-text",
        size: "16px",
      },
      { prop: ".text-1", size: "16px" },
      { prop: ".text-2", size: "16px" },
      { prop: ".text-3", size: "16px" },
      { prop: "li", size: "16px" },
      { prop: "strong", size: "16px" },
      { prop: "td", size: "16px" },
      { prop: "th", size: "16px" },
      { prop: "span", size: "16px" },
      { prop: "p", size: "16px" },
      {
        prop: ".custom-pre-tag-with-spaces",
        size: "16px",
      },
    ];

    classes.forEach((item: FontSizeProp) => {
      updatedContent
        .querySelectorAll(item.prop)
        .forEach((element: HTMLElement) => {
          element.style.setProperty("font-size", item.size, "important");
          element.style.lineHeight = "1.4";
        });
    });

    updatedContent.querySelectorAll("th").forEach((element: HTMLElement) => {
      let propPadding = element.style.getPropertyValue("padding");
      if (!propPadding) {
        element.style.padding = "6px";
      }
    });

    updatedContent.querySelectorAll("td").forEach((element: HTMLElement) => {
      let propPadding = element.style.getPropertyValue("padding");
      let propTextAlign = element.style.getPropertyValue("text-align");
      if (!propPadding) {
        element.style.padding = "6px";
      }
      if (!propTextAlign) {
        element.style.textAlign = "left";
      }
    });

    updatedContent
      .querySelectorAll(".topic")
      .forEach((element: HTMLElement) => {
        element.style.marginTop = "0px";
        element.style.marginBottom = "15px";
      });

    return updatedContent;
  }

  getExistingFacilities(facilities: any[]) {
    let existingFacilities: any[] = [];

    if (facilities.length > 0) {
      existingFacilities = facilities.filter(
        (f: any) => f.isNew == Constants.yesNoConst.N
      );
    }

    return existingFacilities;
  }

  getBranchName(branchCode: any) {
    this.allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    let branch = AppUtils.getBranchFromBranchCode(
      this.allBankOptions,
      branchCode
    );

    if (!isEmpty(branch)) {
      return branch.branchName.toUpperCase();
    }
    return branchCode;
  }

  getFacilityPpaerSignatures(facilityPaperId: any) {
    this.facilityPaperAddEditService
      .getFPSignatures(facilityPaperId)
      .then((data: any) => {
        if (data) {
          this.signatures = data;
        }
      });
  }

  getSpecificSignature(wc: any, divCode: any) {
    let result: any = { designation: "", name: "" };
    if (divCode) {
      let data_list: any[] = this.signatures.filter(
        (s: any) => s.assignUserDivCode == divCode
      );
      let sort_max_wc: any[] = data_list.sort(
        (a: any, b: any) =>
          parseInt(b.assignUserUpmGroupCode) -
          parseInt(a.assignUserUpmGroupCode)
      );
      result =
        sort_max_wc.length > 0
          ? {
              designation: sort_max_wc[0].assignUserDesignation.toUpperCase(),
              name: sort_max_wc[0].assignUserDisplayName,
            }
          : "";
    } else {
      let data_row: any = this.signatures.find(
        (s: any) =>
          parseInt(s.assignUserUpmGroupCode) == wc &&
          s.assignUserDivCode != "874"
      );
      result = data_row
        ? {
            designation: data_row.assignUserDesignation.toUpperCase(),
            name: data_row.assignUserDisplayName,
            units:
              data_row.assignUserFunctionalUnits !== null
                ? data_row.assignUserFunctionalUnits
                : "",
          }
        : "";
    }

    return result;
  }

  showSignatures() {
    let currentWC: any = this.applicationService.getLoggedInUserUPMGroupCode();

    return parseInt(currentWC) >= 79;
  }

  isHide(): boolean {
    let wc: any = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    return (
      wc == Constants.applicationSecurityWorkClass.ENTERER ||
      wc == Constants.applicationSecurityWorkClass.SUPERVISOR ||
      wc == Constants.applicationSecurityWorkClass.MANAGER
    );
  }

  getUnReadFacilityCommentsCount(facility: any): number {
    let preCmnts: any[] =
      facility.fusTraceList !== null ? facility.fusTraceList : [];

    return preCmnts.filter(
      (cmnt: any) =>
        cmnt.flag == Constants.fusTraceFlag.FAC &&
        cmnt.isView == 0 &&
        cmnt.createdBy != this.applicationService.getLoggedInUserUserName()
    ).length;
  }

  openReportModal(creditFacility: any) {
    this.modalRef = this.mdbModalService.show(
      ReportFacilityChangeModalComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-dialog-scrollable modal-width-95-m",
        containerClass: "",
        animated: true,
        data: {
          heading: "Report Facility Changes",
          facility: creditFacility,
          componentData: {
            facilityPaper: this.facilityPaper,
            creditFacilityList: this.facilityPaper.facilityDTOList,
            creditFacility: creditFacility,
            creditScheduleESBResponseStatusList:
              this.creditScheduleESBResponseStatusList,
            commonFacilitySecurityList: this.setCommonSecurities(
              this.facilityPaper.facilityDTOList
            ),
            isCommittee:
              this.facilityPaper.isCommittee == Constants.yesNoConst.Y,
          },
          content: {},
        },
      }
    );
  }

  openModalUPCTemplateComment(section: any) {
    
    const initialState = {
      node: {
        ...section,
        content: section.data ? section.data : "",
        facilityPaperID: this.facilityPaper.facilityPaperID,
        currentAssignUser:
          this.facilityPaper.currentAssignUser !== null
            ? this.facilityPaper.currentAssignUser
            : "",
        currentFPStatus:
          this.facilityPaper.currentFacilityPaperStatus !== null
            ? this.facilityPaper.currentFacilityPaperStatus
            : "",
        currentFPWC:
          this.facilityPaper &&
          this.facilityPaper.assignUserUpmGroupCode !== null
            ? parseInt(this.facilityPaper.assignUserUpmGroupCode)
            : 0,
        assignDepartmentCode:
          this.facilityPaper && this.facilityPaper.assignDepartmentCode !== null
            ? this.facilityPaper.assignDepartmentCode
            : "",
      },
    };

    this.upcTemplateComparisonModelRef = this.mdbModalService.show(
      UPCTemplateComparisonComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        ignoreBackdropClick: true,
        class: "modal-width-95-p",
        containerClass: "",
        animated: true,
        data: {
          heading: "12",
          content: {
            initialState: initialState,
            upcTemplateComparisonModelRef: this.upcTemplateComparisonModelRef,
          },
        },
      }
    );
  }

  isDocumentDataExist(currentItem: any, children: any[]) {
    let isExist: boolean = false;

    if (currentItem.data && !isEmpty(currentItem.data)) {
      isExist = true;
    } else {
      isExist = children.some(
        (c: any) => c.item.data != null && !isEmpty(c.item.data)
      );
    }

    return isExist;
  }

  showAnnexesEndBorder(childs: any[], currentIndex: any) {
    let result: boolean = false;
    // var activeAnnexes: any[] = childs.filter(
    //   (c: any) => c.item.data != null && !isEmpty(c.item.data)
    // );
    result = currentIndex != childs.length - 1;

    return result;
  }

  setApprovedStatusData() {
    let convertedDate: any = moment(this.facilityPaper.approvedDate)
      .format("Do MMMM YYYY")
      .toString();

    let description: string =
      "Approved by " +
      (this.facilityPaper.currentAssignUser == "BCC"
        ? this.facilityPaper.currentAssignUser
        : this.facilityPaper.assignUserDisplayName.toUpperCase()) +
      " on " +
      convertedDate;

    this.fpFinalStatusData = {
      isShow: true,
      src: "assets/media/images/fpstatusicon/approved.png",
      description: description,
      color: "#007e338a",
    };

    this.fpFinalStatusData_Reccomond = {
      isShow: true,
      src: "assets/media/images/fpstatusicon/approved.png",
      description_1:
        "APPROVED " + this.facilityPaper.assignUserDisplayName.toUpperCase(),
      description_2:
        "The above detailed facilities are approved subject to any conditions recorded overleaf.",
      date: convertedDate,
      status: Constants.facilityPaperStatusConst.APPROVED,
      fontColor: "#007e338a",
    };
  }

  setRejectedStatusData() {
    var convertedDate: any = moment(this.facilityPaper.rejectedDate)
      .format("Do MMMM YYYY")
      .toString();

    var description: string =
      "Rejected by " +
      (this.facilityPaper.currentAssignUser == "BCC"
        ? this.facilityPaper.currentAssignUser
        : this.facilityPaper.assignUserDisplayName.toUpperCase()) +
      " on " +
      convertedDate;

    this.fpFinalStatusData = {
      isShow: true,
      src: "assets/media/images/fpstatusicon/rejected.png",
      description: description,
      color: "#cc0000a6",
    };

    this.fpFinalStatusData_Reccomond = {
      isShow: true,
      src: "assets/media/images/fpstatusicon/rejected.png",
      description_1:
        "REJECTED BY " + this.facilityPaper.assignUserDisplayName.toUpperCase(),
      description_2:
        "The above detailed facilities are rejected subject to any conditions recorded overleaf.",
      date: convertedDate,
      status: Constants.facilityPaperStatusConst.REJECTED,
      fontColor: "#cc0000a6",
    };
  }

  isCommentable() {
    let isEnabled: boolean = false;

    isEnabled =
      this.facilityPaper.currentAssignUser ==
      this.applicationService.getLoggedInUserUserName();

    return isEnabled;
  }

  isHigherWCUser() {
    let wc: any = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    return wc >= 71 && wc <= 80;
  }

  isForeignCrny(value: any) {
    return value != Constants.currencyTypesConst.LKR;
  }

  isFacilityConditionExists(facility: any) {
    return (
      facility.condition !== undefined &&
      facility.condition !== null &&
      facility.condition !== ""
    );
  }
}

export interface FontSizeProp {
  prop: string;
  size: string;
}
