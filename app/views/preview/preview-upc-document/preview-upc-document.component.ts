import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import { UpcTemplateDto } from "../../pages/upc-template/dto/upc-template-dto";
import { Subscription } from "rxjs";
import * as _ from "lodash";

@Component({
  selector: "app-preview-upc-document",
  templateUrl: "./preview-upc-document.component.html",
  styleUrls: ["./preview-upc-document.component.scss"],
})
export class PreviewUpcDocumentComponent implements OnInit {
  @Input("facilityPaper") facilityPaper: any;
  @Input("upcSectionData") upcSectionData;
  @Input("upcTemplateList") upcTemplateList = [];
  @Input("isCommentEnable") isCommentEnable: Boolean;
  facilityPaperId: any;
  currentAssignUser: any = "";
  currentFPStatus: any = "";
  currentFPWC: number = 0;
  assignDepartmentCode: any = "";

  selectedUpcTemplateDTO: UpcTemplateDto = new UpcTemplateDto([]);

  allUpcSectionData: any = [];
  fpUpcSectionData: any = [];

  treeUpdatedContent: any = null;
  isTreeUpdated: boolean;
  hasFptemplates: boolean = false;
  initNodeRowData: any = [];
  initNodeData: any = [];
  alreadyAddedUpcSectionIDs = [];
  upcTemplateName: any = [];

  onFacilityPaperChangeSubs = new Subscription();
  onUpcTemplateLoadChangeSub = new Subscription();
  onUpcTemplateIDChangeSub = new Subscription();
  onSelectedUpcTemplateChangeSub = new Subscription();
  onAllUpcSectionDataChangeSub = new Subscription();
  onLoadFacilityPaperLoadChangeSub = new Subscription();
  onTreeUpdateChangeSub = new Subscription();

  constructor(private changeDetectorRef: ChangeDetectorRef) {}

  ngOnInit() {
    this.upcTemplateName = this.facilityPaper.upcTemplateName;

    this.initNodeData = [];

    this.isTreeUpdated = false;
    this.treeUpdatedContent = null;
    this.initNodeRowData = [];

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

    this.fpUpcSectionData = this.facilityPaper.fpUpcSectionDataDTOList;
    this.allUpcSectionData = this.upcSectionData;

    if (this.fpUpcSectionData && this.fpUpcSectionData.length > 0) {
      this.hasFptemplates = true;
      this.allUpcSectionData = this.upcSectionData;
    } else {
      this.hasFptemplates = false;
    }

    this.generateTreeFeedUsingExistingFpSections(this.fpUpcSectionData);

    if (this.fpUpcSectionData && this.fpUpcSectionData.length > 0) {
      this.hasFptemplates = true;
      this.generateTreeFeedUsingExistingFpSections(this.fpUpcSectionData);
    }

    setTimeout(() => {
      this.changeDetectorRef.detectChanges();
    }, 250);
  }

  ngOnDestroy(): void {
    this.onAllUpcSectionDataChangeSub.unsubscribe();
    this.onSelectedUpcTemplateChangeSub.unsubscribe();
    this.onUpcTemplateLoadChangeSub.unsubscribe();
    this.onUpcTemplateIDChangeSub.unsubscribe();
    this.onTreeUpdateChangeSub.unsubscribe();
    this.onLoadFacilityPaperLoadChangeSub.unsubscribe();
    this.onFacilityPaperChangeSubs.unsubscribe();
  }

  getUPCSectionByID(upcSectionID) {
    return _.find(
      this.allUpcSectionData,
      (d: any) => d.upcSectionID == upcSectionID
    );
  }

  getUPCSectionByIDInFpSectionList(upcSectionID) {
    return _.find(
      this.fpUpcSectionData,
      (d: any) => d.upcSectionID == upcSectionID
    );
  }

  generateTreeFeed(upcTemplateList) {
    let data = _.sortBy(upcTemplateList, (i: any) => i.displayOrder);
    this.initNodeRowData = data;
    let nodeData = [];
    let addedIDs = [];

    let levelWiseParents = {};

    for (let i = 0; i < data.length; i++) {
      let dataItem = data[i];
      addedIDs.push(dataItem.upcSectionID);
      let upcSectionData = this.getUPCSectionByID(dataItem.upcSectionID);
      if (!dataItem.parentSectionID) {
        let mainParent = {
          name: upcSectionData.upcSectionName
            ? upcSectionData.upcSectionName
            : "",
          item: upcSectionData,
          isExpanded: true,
          children: [],
        };

        nodeData.push(mainParent);
        levelWiseParents = {};
        levelWiseParents[0] = mainParent;
      } else {
        let upcSectionData = this.getUPCSectionByID(dataItem.upcSectionID);
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
    this.initNodeData = nodeData;
    this.alreadyAddedUpcSectionIDs = addedIDs;
  }

  setFpUpcTemplateID(treeData) {
    if (this.fpUpcSectionData && this.fpUpcSectionData.length > 0) {
      for (var item of treeData) {
        _.forEach(this.fpUpcSectionData, (fpItem) => {
          if (item.upcSectionID == fpItem.upcSectionID) {
            item["fpUpcSectionDataID"] = fpItem.fpUpcSectionDataID;
            item["facilityPaperID"] = fpItem.facilityPaperID;
          }
        });
      }
      return treeData;
    } else {
      for (var item of treeData) {
        item["fpUpcSectionDataID"] = null;
        item["facilityPaperID"] = this.facilityPaper.facilityPaperID;
      }
    }
    return treeData;
  }

  composeTree(treeUpdatedContent) {
    let data = [];
    let tree = treeUpdatedContent;

    if (!tree) {
      return this.allUpcSectionData;
    }

    for (let i = 0; i < tree.length; i++) {
      this.buildTree(data, tree[i], 0, null);
    }
    data.forEach((item: any, index) => {
      item.displayOrder = index + 1;
    });

    return data;
  }

  buildTree(dataArray, node, level, parentSectionID) {
    dataArray.push(
      Object.assign({}, node.item, {
        parentSectionID: parentSectionID,
        sectionLevel: level,
      })
    );

    if (node.children && node.children.length > 0) {
      for (let i = 0; i < node.children.length; i++) {
        this.buildTree(
          dataArray,
          node.children[i],
          level + 1,
          node.item.upcSectionID
        );
      }
    }
  }

  generateTreeFeedUsingExistingFpSections(upcTemplateList) {
    let data = _.sortBy(upcTemplateList, (i: any) => i.displayOrder);
    this.initNodeRowData = data;
    let nodeData = [];
    let addedIDs = [];

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

    this.initNodeData = nodeData;
    this.alreadyAddedUpcSectionIDs = addedIDs;
  }
}
