import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core";
import * as _ from "lodash";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { UpcTemplateAddDataComponent } from "../../upc-template-add-data/upc-template-add-data.component";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import * as moment from "moment";
import { Constants } from "src/app/core/setting/constants";
import { AnalyticsDecision } from "src/app/views/pages/lead/interfaces/Lead-comp-borrower-dto";

@Component({
  selector: "app-show-full-node-html",
  templateUrl: "./show-full-node-html.component.html",
  styleUrls: ["./show-full-node-html.component.scss"],
})
export class ShowFullNodeHtmlComponent implements OnInit, OnChanges {
  content: any;
  dataMap: any = {};

  modalRef: MDBModalRef;

  @Input("isCommittee") isCommittee: boolean = false;
  @Input("initNodeRowData") initNodeRowData: any = [];
  @Input("isPreviewMode") isPreviewMode: boolean;
  @Output("onAddEditData") onAddEditData = new EventEmitter();
  @Input("upcTemplateId") upcTemplateId: any = 0;
  @Input("upcTemplateName") upcTemplateName: string = "";
  @Input("facilityPaper") facilityPaper: any;
  facilityPaperId: any = 0;
  currentAssignUser: any = "";
  currentFPStatus: any = "";
  currentFPWC: number = 0;

  nodeRowData: any = [];
  treeData = [];
  previewData = [];
  currentEvent: any;
  currentNode: any;
  currentFPId: number = 0;
  templateSectionList: any[] = [];
  isCommentEnable: boolean = true;
  assignDepartmentCode: any = "";

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
  ) {}

  ngOnInit() {
    this.templateSectionList = Constants.templateSectionList;
    this.nodeRowData = this.initNodeRowData ? this.initNodeRowData : [];

    this.facilityPaperId = this.facilityPaper
      ? this.facilityPaper.facilityPaperID
      : 0;

    this.currentAssignUser = this.facilityPaper
      ? this.facilityPaper.currentAssignUser
      : "";

    this.currentFPStatus = this.facilityPaper
      ? this.facilityPaper.currentFacilityPaperStatus
      : "";
    this.currentFPWC =
      this.facilityPaper && this.facilityPaper.assignUserUpmGroupCode
        ? parseInt(this.facilityPaper.assignUserUpmGroupCode)
        : 0;
    this.assignDepartmentCode =
      this.facilityPaper && this.facilityPaper.assignDepartmentCode
        ? this.facilityPaper.assignDepartmentCode
        : "";

    _.each(this.nodeRowData, (upcSection) => {
      this.dataMap[upcSection.upcSectionID] = upcSection.data
        ? upcSection.data
        : "";
    });
    this.setTreeData(_.cloneDeep(this.nodeRowData));
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["initNodeRowData"]) {
      this.setTreeData(_.cloneDeep(this.initNodeRowData));
    }
  }

  setTreeData(rowData: any) {
    let id = 0;
    this.currentFPId = rowData[0].facilityPaperID;

    this.treeData = [];
    this.previewData = [];

    _.each(rowData, (node) => {
      if (node.parentSectionID == null) {
        const nodeObj = Object.assign(
          {},
          {
            dataLabel: (++id).toString(),
            fpUpcSectionDataID: node.fpUpcSectionDataID,
            parentSectionID: node.parentSectionID,
            upcSectionID: node.upcSectionID,
            sectionLevel: node.sectionLevel,
            title: node.upcSectionName ? node.upcSectionName : "",
            content: node.data ? node.data : "",
            modifiedUserDisplayName: node.modifiedUserDisplayName
              ? node.modifiedUserDisplayName
              : "",
            modifiedDateStr: node.modifiedDateStr ? node.modifiedDateStr : "",
            children: [],
          },
        );

        this.treeData.push(nodeObj);
        this.previewData.push(nodeObj);
      } else {
        this.setTreeDataChild(this.treeData, node);
      }
    });
  }

  setTreeDataChild(treeData, node) {
    _.each(treeData, (treeNode) => {
      if (treeNode.upcSectionID == node.parentSectionID) {
        const nodeObj = Object.assign(
          {},
          {
            dataLabel:
              treeNode.dataLabel +
              "." +
              (treeNode.children.length + 1).toString(),
            parentSectionID: node.parentSectionID,
            upcSectionID: node.upcSectionID,
            sectionLevel: node.sectionLevel,
            title: node.upcSectionName ? node.upcSectionName : "",
            modifiedUserDisplayName: node.modifiedUserDisplayName
              ? node.modifiedUserDisplayName
              : "",
            modifiedDateStr: node.modifiedDateStr ? node.modifiedDateStr : "",
            content: node.data ? node.data : "",
            children: [],
          },
        );

        treeNode.children.push(nodeObj);
        this.previewData.push(nodeObj);
        return;
      } else if (treeNode.children.length > 0) {
        this.setTreeDataChild(treeNode.children, node);
      }
    });
  }

  openModalTemplateData1($event: any, node: any) {
    this.currentEvent = $event;
    this.currentNode = node;

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let analyticsDecision: AnalyticsDecision =
      this.facilityPaper.analyticsDecision;
    if (
      node &&
      !node.content &&
      Constants.compTemplateSectionList.includes(node.title) &&
      analyticsDecision !== null &&
      analyticsDecision.decisionStatus === "Approved"
    ) {
      let request: any = {
        leadID: this.facilityPaper.leadID,
        section: node.title,
      };

      this.facilityPaperAddEditService
        .getDigitalFormApplicationContent(request)
        .then((response: any) => {
          this.openTemplateDataModal(response);
        });
    } else {
      if (node && !this.isTemplateLoad(node.content, node.title)) {
        this.openTemplateDataModal(node.content);
      } else {
        this.facilityPaperAddEditService
          .getDocumentContent(this.currentFPId, node.upcSectionID)
          .subscribe((response: any) => {
            this.openTemplateDataModal(response);
          });
      }
    }
  }

  openModalTemplateData($event: any, node: any) {
    this.currentEvent = $event;
    this.currentNode = node;

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    if (!node) return;

    const isCompTemplateSection =
      !node.content &&
      Constants.compTemplateSectionList.includes(node.title) &&
      this.facilityPaper.analyticsDecision !== null;

    if (isCompTemplateSection) {
      this.loadDigitalFormTemplate(node);
      return;
    }

    if (this.isTemplateLoad(node.content, node.title)) {
      this.loadDocumentContent(node.upcSectionID);
      return;
    }

    this.openTemplateDataModal(node.content);
  }

  loadDigitalFormTemplate(node: any) {
    const request = {
      leadID: this.facilityPaper.leadID,
      section: node.title,
    };

    this.facilityPaperAddEditService
      .getDigitalFormApplicationContent(request)
      .then((response: any) => {
        if (response !== null && response.documentContent !== null) {
          this.openTemplateDataModal(response.documentContent);
        }
      });
  }

  loadDocumentContent(sectionID: number) {
    this.facilityPaperAddEditService
      .getDocumentContent(this.currentFPId, sectionID)
      .subscribe((content: any) => {
        this.openTemplateDataModal(content);
      });
  }

  openTemplateDataModal(data: any) {
    localStorage.setItem(
      "mdbmlopn",
      JSON.stringify(moment().format("YYYY-MM-DD H:mm:ss")),
    );

    this.modalRef = this.mdbModalService.show(UpcTemplateAddDataComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-lg",
      containerClass: "",
      animated: false,
      data: {
        heading: "",
        content: {
          dataToEdit: data,
          header: this.currentNode.title,
        },
        isCommittee: this.isCommittee,
        isRefreshEnabled:
          (this.upcTemplateName.trim() == "Board Paper" ||
            this.upcTemplateName.includes("Review")) &&
          this.templateSectionList.some(
            (s: any) => s.sectionName == this.currentNode.title.trim(),
          ),
        templateSectionList: this.templateSectionList,
        nodeData: {
          facilityPaperID: this.facilityPaperId,
          currentAssignUser: this.currentAssignUser,
          currentFPStatus: this.currentFPStatus,
          currentFPWC: this.currentFPWC,
          ...this.currentNode,
        },
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result == 1) {
        this.modalRef.hide();
        this.refreshContent();
      } else {
        this.dataMap[this.currentNode.upcSectionID] = result;
        this.onAddEditData.emit(this.dataMap);
      }
      localStorage.removeItem("mdbmlopn");
    });
  }

  isTemplateLoad(prevContent: any, section: any) {
    let result: boolean = false;

    if (
      !prevContent &&
      this.templateSectionList.some(
        (tempSection: any) => tempSection.sectionName == section,
      )
    ) {
      result = true;
    }

    return result;
  }

  refreshContent() {
    this.facilityPaperAddEditService
      .getDocumentContent(this.currentFPId, this.currentNode.upcSectionID)
      .subscribe((response: any) => {
        this.openTemplateDataModal(response);
      });
  }
}
