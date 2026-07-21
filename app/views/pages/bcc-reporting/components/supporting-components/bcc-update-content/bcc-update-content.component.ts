import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Subject } from "rxjs";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { MasterDataService } from "../../../../../../core/service/data/master-data.service";
import { Constants } from "../../../../../../core/setting/constants";

@Component({
  selector: "app-bcc-update-content",
  templateUrl: "./bcc-update-content.component.html",
  styleUrls: ["./bcc-update-content.component.scss"],
})
export class BccUpdateContentComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  action: Subject<any> = new Subject<any>();
  dataUpdateDTO: Data = new Data({});
  content: any;
  givenData: any;
  header: any = "";
  retortType: any = "";

  isTinyMCEEnabled: boolean = false;
  isScrollEnabled: boolean = true;
  constructor(
    private mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private masterDataService: MasterDataService
  ) {}

  ngOnInit() {
    this.givenData = this.content.dataToEdit ? this.content.dataToEdit : "";
    this.header = this.header ? this.header : "";
    this.isTinyMCEEnabled = this.masterDataService.getSystemParameter(
      Constants.systemParamKey.TINYMCE_ENABLED
    );
  }

  update(event: any) {
    let missingTopics =
      this.retortType != Constants.BCCPaperTypeConst.BRPTR && this.retortType != Constants.BCCPaperTypeConst.BRPGG
        ? this.contentValidator(event)
        : "";
    let data = { pdfReport: event, missingTopics: missingTopics };
    this.action.next(data);
    this.mdbModalRef.hide();
  }

  close() {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  contentValidator(content: any) {
    let topics = {
      _recommendedOfficers: "Recommended Officers",
      _exposureTable: "Exposure Values",
      _commonSecurities: "Common Securities",
      _existingFacilities: "Existing Facilities",
      _proposedFacilities: "Proposed Facilities",
      _riskBasePricing: "Risk Base Pricing",
      _financialCostOfFundTable: "Financial Const of Fund Table",
      _financialCostOfFund: "Financial Cost of Fund",
      _recommendation: "Recommendation",
      _securityCover: "Security Cover",
      _businessManagementStrength: "Business Management Strength",
      _marketPosition: "Market Position",
      _cribStatus: "Crib Status",
      _financialYearDetails: "Financial Year Details",
      _strength: "Strength",
      _statedCapital: "Stated Capital",
      _ownershipDirectors: "Ownership Details",
      _businessProfile: "Business Profile",
      _riskRatingTable: "Risk Rating Table",
      _customerName: "Customer Name",
      _branchName: "Branch Code",
      _summaryOfSecurities: "Summary Of Securities",
    };

    let ids = [
      "_recommendedOfficers",
      "_exposureTable",
      "_commonSecurities",
      "_existingFacilities",
      "_proposedFacilities",
      "_riskBasePricing",
      "_financialCostOfFundTable",
      "_financialCostOfFund",
      "_recommendation",
      "_securityCover",
      "_businessManagementStrength",
      "_marketPosition",
      "_cribStatus",
      "_financialYearDetails",
      "_strength",
      "_statedCapital",
      "_ownershipDirectors",
      "_businessProfile",
      "_riskRatingTable",
      "_customerName",
      "_branchName",
      "_summaryOfSecurities",
    ];

    let missedTopics = [];

    for (let i = 0; i < ids.length; i++) {
      if (!content.includes(ids[i])) {
        // console.log(ids[i]);
        missedTopics.push(topics[ids[i]]);
      }
    }
    let topicString = missedTopics.join(" ,");
    topicString.slice(0, -2);

    return topicString;
  }
}

export class Data {
  data;

  constructor(dto) {
    dto = dto || {};
    this.data = dto.data || "";
  }
}
