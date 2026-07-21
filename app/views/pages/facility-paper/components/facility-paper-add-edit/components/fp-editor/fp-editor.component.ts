import {
  ChangeDetectorRef,
  Component,
  Input,
  OnDestroy,
  OnInit,
} from "@angular/core";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Subscription } from "rxjs";
import * as _ from "lodash";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { UpcTemplateDto } from "../../../../../upc-template/dto/upc-template-dto";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../core/setting/constants";
import { PrivilegeService } from "../../../../../../../core/service/authentication/privilege.service";
import { SETTINGS } from "../../../../../../../core/setting/commons.settings";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FpCustomerPaperUpcHistoryComponent } from "../support-components/fp-customer-paper-upc-history/fp-customer-paper-upc-history.component";
import { ActivatedRoute } from "@angular/router";
import { CustomerEvaluationComponent } from "src/app/views/pages/customer-360/components/customer-base/components/customer-evaluation-list/customer-evaluation.component";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { LocalStorage } from "ngx-webstorage";

@Component({
  selector: "app-fp-editor",
  templateUrl: "./fp-editor.component.html",
  styleUrls: ["./fp-editor.component.scss"],
})
export class FpEditorComponent implements OnInit, OnDestroy {
  @Input("facilityPaper")
  facilityPaper;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;

  modalRef: MDBModalRef;

  upcTemplateList:any[] = [];
  upcTemplateListOpt:any[] = [];
  formErrors = {};
  selectedUpcTemplateDTO: UpcTemplateDto = new UpcTemplateDto([]);
  searchForm: FormGroup;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  allUpcSectionData: any = [];
  fpUpcSectionData: any = [];

  treeUpdatedContent: any = null;
  isTreeUpdated: boolean;
  hasFptemplates: boolean = false;
  initNodeRowData: any = [];
  initNodeData: any = [];
  alreadyAddedUpcSectionIDs = [];
  upcTemplateName: any = [];
  upcTemplateId: any = 0;

  onUpcTemplateLoadChangeSub = new Subscription();
  onUpcTemplateIDChangeSub = new Subscription();
  onSelectedUpcTemplateChangeSub = new Subscription();
  onAllUpcSectionDataChangeSub = new Subscription();
  onLoadFacilityPaperLoadChangeSub = new Subscription();
  onTreeUpdateChangeSub = new Subscription();
  hasPrivilege = false;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  customerEvaluationId: number;
  evaluations = [];
  showDataComponent: boolean = false;
  showDataComponent2: boolean = false;
  showComponentB = false;
  isSelected = false;
  data: any;
  defualtTemplate: any = null;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private mdbModalService: MDBModalService,
    private route: ActivatedRoute,
    private urlEncodeService: UrlEncodeService,
  ) {}

  ngOnInit() {
    this.hasPrivilege =
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT,
      ) ||
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_UPC_EDIT,
      );
    this.searchForm = this.createFormUpcTemplateSearchForm();
    this.upcTemplateName = this.facilityPaper.upcTemplateName;
    this.upcTemplateId = this.facilityPaper
      ? this.facilityPaper.upcTemplateID
      : 0;

    this.initNodeData = [];

    this.isTreeUpdated = false;
    this.treeUpdatedContent = null;
    this.initNodeRowData = [];

    this.formErrors = {
      upcTemplateID: [""],
    };

    this.showComponentB = true;
    //this.showComponentBInTab()

    if (
      this.facilityPaper &&
      (this.facilityPaper.currentFacilityPaperStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
        this.facilityPaper.currentFacilityPaperStatus ==
          Constants.facilityPaperStatusConst.CANCEL)
    ) {
      this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
        this.facilityPaper,
        Constants.fusTraceFlag.UPCT,
      );
    }

    this.onLoadFacilityPaperLoadChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (data: any) => {
          this.fpUpcSectionData = data.fpUpcSectionDataDTOList;
          this.allUpcSectionData =
            this.facilityPaperAddEditService.upcSectionData;
        },
      );

    this.onTreeUpdateChangeSub =
      this.facilityPaperAddEditService.onFpUpcSectionChange.subscribe(
        (data: any) => {
          this.fpUpcSectionData = data.fpUpcSectionDataDTOList;
          if (this.fpUpcSectionData && this.fpUpcSectionData.length > 0) {
            this.upcTemplateName = data.upcTemplateName;
            this.hasFptemplates = true;
            this.allUpcSectionData =
              this.facilityPaperAddEditService.upcSectionData;
          } else {
            this.hasFptemplates = false;
          }
          this.generateTreeFeedUsingExistingFpSections(this.fpUpcSectionData);
        },
      );

    this.onUpcTemplateLoadChangeSub =
      this.facilityPaperAddEditService.onUpcTemplateListLoad.subscribe(
        (data: any) => {
          this.upcTemplateList = data;
          _.forEach(this.upcTemplateList, (template) => {
            this.upcTemplateListOpt.push({
              value: template.upcTemplateID,
              label: template.templateName,
            });
          });
        },
      );

    if (this.fpUpcSectionData && this.fpUpcSectionData.length > 0) {
      this.hasFptemplates = true;
      this.generateTreeFeedUsingExistingFpSections(this.fpUpcSectionData);
    } else {
      this.hasFptemplates = false;
      this.onUpcTemplateIDChangeSub =
        this.searchForm.controls.upcTemplateID.valueChanges.subscribe(
          (value: any) => {
            this.facilityPaperAddEditService.getUpcTemplateDtoByID(value);
          },
        );

      this.onSelectedUpcTemplateChangeSub =
        this.facilityPaperAddEditService.onUpcTemplateChange.subscribe(
          (response: any) => {
            this.selectedUpcTemplateDTO = new UpcTemplateDto(response);
            this.generateTreeFeed(
              this.selectedUpcTemplateDTO.upcTemplateDataDTOList,
            );
          },
        );

      this.onAllUpcSectionDataChangeSub =
        this.facilityPaperAddEditService.onUpcSectionDataChange.subscribe(
          (data: any) => {
            this.allUpcSectionData = data;
          },
        );
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
  }

  createFormUpcTemplateSearchForm() {
    return this.formBuilder.group({
      upcTemplateID: [
        { value: "", disabled: !this.isAbelToEdit() },
        Validators.required,
      ],
    });
  }

  getUPCSectionByID(upcSectionID: any) {
    return _.find(
      this.allUpcSectionData,
      (d: any) => d.upcSectionID == upcSectionID,
    );
  }

  getUPCSectionByIDInFpSectionList(upcSectionID) {
    return _.find(
      this.fpUpcSectionData,
      (d: any) => d.upcSectionID == upcSectionID,
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

  onUpdateData($event) {
    this.isTreeUpdated = true;
    this.treeUpdatedContent = $event;
    this.composeData();
  }

  onUpdateTree($event: any) {
    var selectedTemplate: any = $event;

    this.isTreeUpdated = true;
    this.treeUpdatedContent = selectedTemplate;
    let treeData = this.composeTree(this.treeUpdatedContent);
    let structuredData = this.setFpUpcTemplateID(treeData);

    let data = Object.assign(
      {},
      { facilityPaperID: this.facilityPaper.facilityPaperID },
      { applicationFormID: this.facilityPaper.applicationFormID },
      { upcTemplateID: selectedTemplate ? selectedTemplate.value : 0 },
      { addedUpcSectionDTOs: structuredData },
    );
    this.upcTemplateId = selectedTemplate ? selectedTemplate.value : 0;
    this.facilityPaperAddEditService.addEditUPCSectionData(data);
  }

  onRemoveNode($event) {
    let romovedTreeNode = $event;
    let find = _.find(this.fpUpcSectionData, (fpItem) => {
      return fpItem.upcSectionID == romovedTreeNode.item.upcSectionID;
    });

    if (find) {
      find.status = "INA";
    }

    let data = Object.assign(
      {},
      { facilityPaperID: this.facilityPaper.facilityPaperID },
      { upcTemplateID: this.searchForm.controls.upcTemplateID.value },
      { addedUpcSectionDTOs: [find] },
    );

    this.facilityPaperAddEditService.addEditUPCSectionData(data);
  }

  onDeleteTemplate($event) {
    this.isTreeUpdated = true;
    this.treeUpdatedContent = $event;
    let treeData = this.composeTree(this.treeUpdatedContent);
    let structuredData = this.setFpUpcTemplateID(treeData);
    this.defualtTemplate = null;

    let data = Object.assign(
      {},
      { facilityPaperID: this.facilityPaper.facilityPaperID },
      { upcTemplateID: this.searchForm.controls.upcTemplateID.value },
      { addedUpcSectionDTOs: structuredData },
    );

    this.facilityPaperAddEditService.removeUpcSectionData(data);
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
    // if (this.fpUpcSectionData.length > 0) {
    //   for (var item of treeData) {
    //     if (item.children.length == 0) {
    //       _.forEach(this.fpUpcSectionData, fpItem => {
    //         if (item.upcSectionID == fpItem.upcSectionID) {
    //           item["fpUpcSectionDataID"] = fpItem.fpUpcSectionDataID;
    //           item["facilityPaperID"] = fpItem.facilityPaperID;
    //         }
    //       })
    //     } else {
    //       _.forEach(this.fpUpcSectionData, fpItem => {
    //         if (item.upcSectionID == fpItem.upcSectionID) {
    //           item["fpUpcSectionDataID"] = fpItem.fpUpcSectionDataID;
    //           item["facilityPaperID"] = fpItem.facilityPaperID;
    //         }
    //       });
    //       this.setFpUpcTemplateID(item.children)
    //     }
    //   }
    //   return treeData;
    //
    // } else {
    //   for (var item of treeData) {
    //     if (item.children.length == 0) {
    //       item["fpUpcSectionDataID"] = null;
    //       item["facilityPaperID"] = this.facilityPaper.facilityPaperID;
    //
    //     } else {
    //       item["fpUpcSectionDataID"] = null;
    //       item["facilityPaperID"] = this.facilityPaper.facilityPaperID;
    //       this.setFpUpcTemplateID(item.children)
    //     }
    //   }
    // }
    // return treeData;
  }

  composeData() {
    let upcTemplateID: number;
    let dataMap = this.treeUpdatedContent;
    let upcTemplateList =
      this.fpUpcSectionData.length > 0
        ? this.fpUpcSectionData
        : this.allUpcSectionData;

    for (var item of upcTemplateList) {
      upcTemplateID = item.upcTemplateID;
      item["facilityPaperID"] = this.facilityPaper.facilityPaperID;
      item["fpUpcSectionDataID"] = item.fpUpcSectionDataID
        ? item.fpUpcSectionDataID
        : null;
      item["modifiedUserDisplayName"] =
        this.applicationService.getLoggedInUserDisplayName();
      if (dataMap[item.upcSectionID]) {
        item["data"] = dataMap[item.upcSectionID];
      }
    }

    let data = Object.assign(
      {},
      { facilityPaperID: this.facilityPaper.facilityPaperID },
      { upcTemplateID: this.searchForm.controls.upcTemplateID.value },
      { addedUpcSectionDTOs: upcTemplateList },
    );

    this.facilityPaperAddEditService.addEditUPCSectionData(data);
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
      }),
    );

    if (node.children && node.children.length > 0) {
      for (let i = 0; i < node.children.length; i++) {
        this.buildTree(
          dataArray,
          node.children[i],
          level + 1,
          node.item.upcSectionID,
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
        dataItem.upcSectionID,
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
          dataItem.upcSectionID,
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

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isEqualLoginAndAssignUser() {
    return (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
  }

  isAbelToEdit() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !=
        this.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !=
        this.facilityPaperStatusConst.REJECTED &&
      ((this.facilityPaper.createdBy ==
        this.applicationService.getLoggedInUserUserName() &&
        this.facilityPaper.currentFacilityPaperStatus ==
          this.facilityPaperStatusConst.DRAFT) ||
        (this.facilityPaper.currentAssignUserID ==
          this.applicationService.getLoggedInUserUserID() &&
          this.hasPrivilege))
    );
  }

  onViewPreviousPaperTemplates($event) {
    this.modalRef = this.mdbModalService.show(
      FpCustomerPaperUpcHistoryComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-85-p modal-dialog-scrollable",
        animated: false,
        data: {
          heading: "Previous UPC Templates",
          message: "",
          facilityPaper: this.facilityPaper,
        },
      },
    );
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
      }
    });
  }

  openDialog() {
    this.modalRef = this.mdbModalService.show(CustomerEvaluationComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-85-p modal-dialog-scrollable  modal-lg",
      containerClass: "",
      animated: true,
    });
  }

  loadDataComponent() {
    this.showDataComponent = true;
    this.hasFptemplates = true;
    // const val = localStorage.getItem("customerEvaluationId");
    // if(val != null){
    //   this.showDataComponent = true;
    // }
  }

  closeDataComponent() {
    this.showDataComponent = false;
    this.showComponentB = false;

    //const storedValue = localStorage.getItem("myKey");
    const storedValue = this.urlEncodeService.decode(this.selectedCIFID);
    const customerEvaluationId = localStorage.getItem("customerEvaluationId");
    const id = localStorage.getItem("id");

    this.data = {
      storedValue,
      customerEvaluationId,
      id,
    };

    this.facilityPaperAddEditService.deleteEvaluation(this.data);
    // const val = localStorage.getItem("customerEvaluationId");
    // if(val != null){
    //   this.facilityPaperAddEditService.deleteEvaluation();
    // }
  }

  // showComponentBInTab(){
  //   const val = localStorage.getItem("customerEvaluationId");
  //   if(val != null){
  //     this.showComponentB = true;
  //   }
  // }
}
