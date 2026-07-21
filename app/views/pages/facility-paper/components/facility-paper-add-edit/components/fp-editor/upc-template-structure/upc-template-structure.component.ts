import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core";
import { ITreeOptions, KEYS, TREE_ACTIONS } from "angular-tree-component";
import * as _ from "lodash";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { UpcTemplateAddDataComponent } from "./upc-template-add-data/upc-template-add-data.component";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { PrivilegeService } from "../../../../../../../../core/service/authentication/privilege.service";
import { ConfirmationDialogComponent } from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../../core/setting/constants";

@Component({
  selector: "app-upc-template-structure",
  templateUrl: "./upc-template-structure.component.html",
  styleUrls: ["./upc-template-structure.component.scss"],
})
export class UpcTemplateStructureComponent implements OnInit {
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  isPreviewMode = true;

  @Input("allUpcSectionData") allUpcSectionData: any = [];
  @Input("initNodeRowData") initNodeRowData: any = [];
  @Input("initNodeData") initNodeData: any = [];
  @Input("alreadyAddedUpcSectionIDs") alreadyAddedUpcSectionIDs: any = [];
  @Input("hasFptemplates") hasFptemplates: boolean;
  @Input("upcTemplateName") upcTemplateName;
  @Input("upcTemplateId") upcTemplateId: any = 0;
  @Input("facilityPaper") facilityPaper;

  @Output("onUpdate") onUpdate = new EventEmitter();
  @Output("onAddData") onAddData = new EventEmitter();
  @Output("onRemove") onRemove = new EventEmitter();
  @Output("onDeleteTemplate") onDeleteTemplate = new EventEmitter();

  nodes = [];
  addedIDs: any = [];
  modalRef: MDBModalRef;
  data: any = {};
  dataMap: any = {};
  addedToDataMap: boolean = false;
  upcSectionMode: boolean = false;
  previewMode: boolean = false;
  readMode: boolean = true;
  htmlMode: boolean = false;
  clickedNode: number;
  isCommittee: boolean = false;

  equalLoginUserAndAssignUser = false;

  options: ITreeOptions = {
    actionMapping: {
      mouse: {
        click: TREE_ACTIONS.TOGGLE_ACTIVE,
        dblClick: null,
        contextMenu: null,
        expanderClick: TREE_ACTIONS.TOGGLE_EXPANDED,
        checkboxClick: TREE_ACTIONS.TOGGLE_SELECTED,
        drop: (tree, node, $event, { from, to }) => {
          if (!from.hasOwnProperty("treeModel")) {
            this.nodes.push({
              name: from.name,
              item: from.item,
              isExpanded: true,
              children: [],
            });

            this.addToAddedIDs(from.item.upcSectionID);
            tree.update();
          } else {
            TREE_ACTIONS.MOVE_NODE(tree, node, $event, { from, to });
          }

          this.onUpdate.emit(this.nodes);
        },
      },
      keys: {
        [KEYS.ENTER]: (tree, node, $event) => {
          node.expandAll();
        },
      },
    },
    nodeHeight: 23,
    allowDrag: (node) => {
      return true;
    },
    allowDrop: (node) => {
      return true;
    },
    allowDragoverStyling: true,
    levelPadding: 20,
    useVirtualScroll: true,
    animateExpand: true,
    scrollOnActivate: true,
    animateSpeed: 30,
    animateAcceleration: 1.2,
    scrollContainer: document.documentElement, // HTML
  };

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  hasPrivilegeToEditFacilityPaperAsDefault: boolean = false;
  hasPrivilegeToEditUPCAsDefault: boolean = false;

  constructor(
    private mdbModalService: MDBModalService,
    private privilegeService: PrivilegeService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.nodes = this.initNodeData;
    this.addedIDs = this.alreadyAddedUpcSectionIDs;
    this.hasPrivilegeToEditFacilityPaperAsDefault =
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT
      );
    this.hasPrivilegeToEditUPCAsDefault = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_UPC_EDIT
    );
    if (
      this.hasPrivilegeToEditFacilityPaperAsDefault ||
      this.hasPrivilegeToEditUPCAsDefault
    ) {
      this.isPreviewMode = false;
    }

    if (this.facilityPaper) {
      this.isCommittee =
        this.facilityPaper &&
        this.facilityPaper.isCommittee == Constants.yesNoConst.Y
          ? true
          : false;
    }

    this.isEqualLoginAndAssignUser();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["initNodeData"]) {
      this.nodes = this.initNodeData;
      this.addedIDs = this.alreadyAddedUpcSectionIDs;
      this.setDataMap(this.nodes);
    }
  }

  addToAddedIDs(id) {
    this.addedIDs = [...this.addedIDs, id];
  }

  removeFromAddedID(ids) {
    this.addedIDs = _.without(this.addedIDs, ...ids);
  }

  addEligible(item) {
    return _.indexOf(this.addedIDs, item.upcSectionID) === -1;
  }

  removeItem($event, treeModel, node) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let removedIDs = [];

    this.getRrmovedItemIDs(removedIDs, node.data);

    _.remove(node.parent.data.children, node.data);
    treeModel.update();

    this.removeFromAddedID(removedIDs);

    this.onRemove.emit(node.data);
  }

  getRrmovedItemIDs(dataArray, node) {
    dataArray.push(node.item.upcSectionID);

    if (node.children && node.children.length > 0) {
      for (let i = 0; i < node.children.length; i++) {
        this.getRrmovedItemIDs(dataArray, node.children[i]);
      }
    }
  }

  openModalTemplateData($event, treeModel, node) {
    // let alreadyAddedData:any;
    // if(this.data[node.data.id]){
    //   alreadyAddedData = ;
    // }
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

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
        heading: "comming dto",
        content: {
          treeModel: treeModel,
          node: node,
          dataToEdit: this.dataMap[node.data.item.upcSectionID],
        },
      },
    });
    this.modalRef.content.action.subscribe((result: any) => {
      this.data[node.data.id] = result;
      this.dataMap[node.data.item.upcSectionID] = result;
      this.addedToDataMap = true;
      this.onAddData.emit(this.dataMap);
      // let tree:any = this.setNodeData(this.nodes,node.data.id,result.data);
      // console.log("map "+JSON.stringify(this.data))
    });
  }

  addData() {
    this.onUpdate.emit(this.nodes);
  }

  removeData() {
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
        heading: "Confirm Remove Template",
        message: "Do you want to remove this UPC template ?",
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.onDeleteTemplate.emit(this.nodes);
      }
    });
  }

  // onViewHtmlForSingleNode($event, nodeData) {
  //   if ($event) {
  //     $event.stopPropagation();
  //     $event.preventDefault();
  //   }
  //
  //   this.modalRef = this.mdbModalService.show(ShowHtmlModalComponent, {
  //     backdrop: true,
  //     keyboard: true,
  //     focus: true,
  //     show: false,
  //     ignoreBackdropClick: false,
  //     class: 'modal-lg',
  //     containerClass: '',
  //     animated: false,
  //     data: {
  //       content: {htmlStr: nodeData.data},
  //     }
  //   });
  // }

  setDataMap(nodes) {
    for (var item of nodes) {
      if (item.children.length == 0) {
        this.dataMap[item.item.upcSectionID] = item.item.data
          ? item.item.data
          : "";
      } else {
        this.dataMap[item.item.upcSectionID] = item.item.data
          ? item.item.data
          : "";
        this.setDataMap(item.children);
      }
    }
  }

  addNewSector() {
    this.upcSectionMode = true;
    this.previewMode = false;
    this.readMode = false;
  }

  onPreviewMode() {
    this.previewMode = true;
    this.upcSectionMode = false;
    this.readMode = false;
  }

  onReadMode() {
    this.readMode = true;
    this.previewMode = false;
    this.upcSectionMode = false;
  }

  getData(node) {
    this.previewMode = true;
    this.clickedNode = node.data.item.upcSectionID;
    this.upcSectionMode = false;
  }

  getDataForPreview() {
    return this.dataMap[this.clickedNode];
  }

  showFullTree($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.htmlMode = true;
  }

  toggleViewMode() {
    this.isPreviewMode = !this.isPreviewMode;
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      this.isPreviewMode = true;
      return false;
    }
  }

  isApproveStatus() {
    let isApproved = false;
    isApproved =
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED;

    if (isApproved) {
      this.isPreviewMode = true;
    }
    return isApproved;
  }

  isRejected() {
    let isRejected = false;
    isRejected =
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED;

    if (isRejected) {
      this.isPreviewMode = true;
    }
    return isRejected;
  }
}
