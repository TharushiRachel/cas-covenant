import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Subscription } from "rxjs/Rx";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../core/setting/constants";
import { CacheService } from "../../../../../../../core/service/data/cache.service";
import { CurrencyPipe } from "@angular/common";
import { AddEditDocumentComponent } from "./add-edit-document/add-edit-document.component";
import { InformationDialogComponent } from "../../../../../../../shared/components/information-dialog/information-dialog.component";
import { SETTINGS } from "../../../../../../../core/setting/commons.settings";
import { TreeNode } from "primeng/api";
import { CommentWithViewOptionsDialogComponent } from "../../../../../../../shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import * as _ from "lodash";

@Component({
  selector: "app-fp-documentation",
  templateUrl: "./fp-documentation.component.html",
  styleUrls: ["./fp-documentation.component.scss"],
})
export class FpDocumentationComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any = {};
  @Input("fpDocumentElementDTOListAll") fpDocumentElementDTOListAll: any = [];
  modalRef: MDBModalRef;
  initialNodes: TreeNode[];
  documentNodes: TreeNode[];
  selectedFiles: TreeNode[] = [];
  fpDocumentElementDTOList = [];
  fpSecurityDocumentDTOList = [];
  fpSecurityDocumentDTOListTemp = [];
  facilityDocumentList = [];
  selectedDocumentElement: any = {};
  componentForm: FormGroup;
  documentContent: string = "";
  showDocPreview: boolean = false;
  enableEditDraftDoc: boolean = false;
  facilityDTOList = [];
  facilityDocumentElementList: any = {};
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  updatedDocumentContent: string = "";
  selectedNode;
  selectedInitialNode;
  securityDocumentID: any;
  showUpdatedDocPreview: boolean = false;
  documentSubmitWorkClass = "";
  loggedInUserWorkClass = "";
  loggedInUserDivCode = "";
  loggedInUserDisplayName = "";
  documentSubmitDivCode = "";
  visibleToEnterer = false;
  visibleToAuthorizer = false;
  visibleToViewer = false;
  filteredFpSecurityDocumentDTOList = [];
  onDocumentationDataChange = new Subscription();

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly cacheService: CacheService,
    private readonly currencyPipe: CurrencyPipe,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.initialNodes = [];
    this.documentNodes = [];
    this.documentSubmitDivCode = this.cacheService.getData(
      Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_DIV
    );
    this.documentSubmitWorkClass = this.cacheService.getData(
      Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_WORK_CLASS
    );
    this.loggedInUserWorkClass =
      this.applicationService.getLoggedInUserUPMGroupCode();
    this.loggedInUserDivCode = this.applicationService.getLoggedInUserDivCode();
    this.loggedInUserDisplayName =
      this.applicationService.getLoggedInUserDisplayName();

    if (this.loggedInUserDivCode == this.facilityPaper.branchCode) {
      this.visibleToViewer = true;
    } else {
      if (this.loggedInUserDivCode == this.documentSubmitDivCode) {
        if (this.loggedInUserWorkClass == this.documentSubmitWorkClass) {
          this.visibleToEnterer = true;
        } else {
          this.visibleToAuthorizer = true;
        }
      }
    }

    this.constructInitialTreeStructure();
  }

  constructInitialTreeStructure() {
    this.fpDocumentElementDTOList = _.uniqBy(
      this.fpDocumentElementDTOListAll,
      "creditFacilityName"
    );
    if (this.fpDocumentElementDTOList.length > 0) {
      _.forEach(this.fpDocumentElementDTOList, (docList) => {
        _.forEach(docList.fpDocumentElementDTOList, (doc) => {
          if (doc.fpSecurityDocumentDTOList.length != 0) {
            _.remove(
              doc.fpSecurityDocumentDTOList,
              (i: any) =>
                i.facilityPaperID != this.facilityPaper.facilityPaperID
            );
            this.fpSecurityDocumentDTOList.push(
              ...doc.fpSecurityDocumentDTOList
            );
          }
        });
      });
    }

    if (this.fpDocumentElementDTOList.length > 0) {
      _.forEach(this.fpDocumentElementDTOList, (docList) => {
        _.sortBy(docList.fpDocumentElementDTOList, ["elementID"]).forEach(
          (fe) => {
            _.remove(
              fe.fpSecurityDocumentDTOList,
              (i: any) => i.documentStatus === "DELETE"
            );
            this.facilityDocumentList.push({
              elementName: fe.elementName,
              parentID: fe.parentID,
              elementID: fe.elementID,
              key: fe.key,
              documentContent: fe.documentContent,
              elementType: fe.elementType,
              fpSecurityDocumentDTOList: fe.fpSecurityDocumentDTOList,
              creditFacilityTemplateID: fe.creditFacilityTemplateID,
              creditFacilityName: fe.creditFacilityName,
            });
          }
        );
      });
    }

    this.initialNodes = this.treeConstruct(this.facilityDocumentList);
    this.documentNodes = this.treeDocumentConstruct(
      this.fpSecurityDocumentDTOList
    );
  }

  ngOnDestroy(): void {
    //
  }

  nodeSelect(event: any) {
    this.selectedNode = {};
    this.enableEditDraftDoc = false;
    this.selectedInitialNode = {
      ...this.selectedInitialNode,
      documentContent: this.selectedInitialNode.documentContent,
    };
    if (event.node.documentContent != null) {
      this.selectedDocumentElement = this.selectedInitialNode;
      this.documentContent = this.selectedDocumentElement.documentContent;
      this.showDocPreview = true;
    } else {
      this.showDocPreview = false;
    }
  }

  docNodeSelect(event: any) {
    this.selectedInitialNode = {};
    this.selectedNode = {
      ...this.selectedNode,
      documentContent: this.selectedNode.documentContent,
    };
    this.selectedDocumentElement = this.selectedNode;
    this.documentContent = this.selectedDocumentElement.documentContent;
    if (event.node.documentContent != null) {
      this.showDocPreview = true;
      this.enableEditDraftDoc = true;
    } else {
      this.showDocPreview = false;
      this.enableEditDraftDoc = false;
    }
  }

  treeDocumentConstruct(treeDocumentData) {
    let constructedDocumentTree = [];
    let draftChildrenNode = [];
    let approvedChildrenNode = [];
    let submittedChildrenNode = [];
    let deletedChildrenNode = [];
    let returnedChildrenNode = [];

    _.sortBy(_.sortBy(treeDocumentData, ["documentStatus"]), [
      "securityDocumentID",
    ]).forEach((doc) => {
      if (doc.documentStatus == "DRAFT") {
        draftChildrenNode.push({
          label: doc.documentName,
          expanded: true,
          icon: "pi pi-file",
          ...doc,
        });
      }
      if (doc.documentStatus == "SUBMIT") {
        submittedChildrenNode.push({
          label: doc.documentName,
          expanded: false,
          icon: "pi pi-file",
          ...doc,
        });
      }
      if (doc.documentStatus == "APPROVE") {
        approvedChildrenNode.push({
          label: doc.documentName,
          expanded: false,
          icon: "pi pi-file",
          ...doc,
        });
      }
      /*if (doc.documentStatus == "DELETE"){
           deletedChildrenNode.push({label: doc.documentName, expanded: false, icon: 'pi pi-file', ... doc});
         }*/
      if (doc.documentStatus == "RETURN") {
        if (
          doc.authByDiv == this.loggedInUserDivCode ||
          doc.savedByDiv == this.loggedInUserDivCode
        ) {
          returnedChildrenNode.push({
            label: doc.documentName,
            expanded: false,
            icon: "pi pi-file",
            ...doc,
          });
        }
      }
    });

    if (this.visibleToEnterer) {
      constructedDocumentTree.push(
        {
          label: "Draft",
          documentStatus: "DRAFT",
          icon: "pi pi-folder",
          expanded: true,
          children: draftChildrenNode,
        },
        {
          label: "Returned",
          documentStatus: "RETURN",
          icon: "pi pi-folder",
          expanded: true,
          children: returnedChildrenNode,
        },
        {
          label: "Submitted",
          documentStatus: "SUBMIT",
          icon: "pi pi-folder",
          expanded: true,
          children: submittedChildrenNode,
        },
        {
          label: "Recommended & Forwarded",
          documentStatus: "APPROVE",
          icon: "pi pi-folder",
          expanded: false,
          children: approvedChildrenNode,
        }
      );
      //  {label : 'Deleted', documentStatus: 'DELETE', icon: 'pi pi-folder', expanded: true, children : deletedChildrenNode});
    }

    if (this.visibleToAuthorizer) {
      constructedDocumentTree.push(
        {
          label: "Submitted",
          documentStatus: "SUBMIT",
          icon: "pi pi-folder",
          expanded: true,
          children: submittedChildrenNode,
        },
        {
          label: "Returned",
          documentStatus: "RETURN",
          icon: "pi pi-folder",
          expanded: true,
          children: returnedChildrenNode,
        },
        {
          label: "Recommended & Forwarded",
          documentStatus: "APPROVE",
          icon: "pi pi-folder",
          expanded: true,
          children: approvedChildrenNode,
        }
      );
    }

    if (this.visibleToViewer) {
      constructedDocumentTree.push(
        {
          label: "Returned",
          documentStatus: "RETURN",
          icon: "pi pi-folder",
          expanded: true,
          children: returnedChildrenNode,
        },
        {
          label: "Recommended & Forwarded",
          documentStatus: "APPROVE",
          icon: "pi pi-folder",
          expanded: true,
          children: approvedChildrenNode,
        }
      );
    }
    return constructedDocumentTree;
  }

  treeConstruct(treeData) {
    let constructedTree = [];
    for (let i of treeData) {
      let treeObj = i;
      let assigned = false;
      this.constructTree(constructedTree, treeObj, assigned);
    }
    return constructedTree;
  }

  constructTree(constructedTree, treeObj, assigned) {
    treeObj.expandedIcon = "pi pi-folder-open";
    treeObj.collapsedIcon = "pi pi-folder";
    treeObj.expanded = false;
    if (treeObj.parentID == 0) {
      treeObj.label = treeObj.elementName;
      treeObj.children = [];
      constructedTree.push(treeObj);
      return true;
    } else if (treeObj.parentID == constructedTree.elementID) {
      treeObj.label = treeObj.elementName;
      if (treeObj.elementType === "D") {
        treeObj.icon = "pi pi-file";
        treeObj.expanded = true;
      }
      treeObj.children = [];
      constructedTree.children.push(treeObj);
      return true;
    } else {
      if (constructedTree.children != undefined) {
        for (let index = 0; index < constructedTree.children.length; index++) {
          let constructedObj = constructedTree.children[index];
          if (assigned == false) {
            assigned = this.constructTree(constructedObj, treeObj, assigned);
          }
        }
      } else {
        for (let index = 0; index < constructedTree.length; index++) {
          let constructedObj = constructedTree[index];
          if (assigned == false) {
            assigned = this.constructTree(constructedObj, treeObj, assigned);
          }
        }
      }
      return false;
    }
  }

  showAddEditDocumentComponent(facilityTypeOptionList) {
    this.modalRef = this.mdbModalService.show(AddEditDocumentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "model-height-80-p model-width-55-p modal-dialog-scrollable",
      containerClass: "",
      animated: true,
      data: {
        content: {
          enableEditDraftDoc: this.enableEditDraftDoc,
          facilityPaper: this.facilityPaper,
          facilityTypeOptionList: facilityTypeOptionList,
          loggedInUserDisplayName: this.loggedInUserDisplayName,
          selectedDocumentElement: this.selectedDocumentElement,
          draftedFpSecurityDocumentDTOList: this.fpSecurityDocumentDTOList,
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      this.modifyDocumentTreeStructure(data);
    });
  }

  showInformationDialogComponent() {
    this.modalRef = this.mdbModalService.show(InformationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Edit Security Documents",
        message: "This document has been drafted for submission",
      },
    });
    this.modalRef.content.action.subscribe((response: any) => {});
  }

  editInitialDocument($event) {
    let facilityTypeOptionList = [];
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    facilityTypeOptionList = this.findDocumentsToBeDraft();

    if (facilityTypeOptionList.length > 0) {
      this.showAddEditDocumentComponent(facilityTypeOptionList);
    } else {
      this.showInformationDialogComponent();
    }
  }

  findDocumentsToBeDraft() {
    let facilityTypeOptionList = [];

    _.forEach(this.fpDocumentElementDTOListAll, (element) => {
      let addElementToList = false;
      //if (element.creditFacilityTemplateID == this.selectedDocumentElement.creditFacilityTemplateID){
      if (
        element.creditFacilityName ==
        this.selectedDocumentElement.creditFacilityName
      ) {
        if (this.selectedDocumentElement.fpSecurityDocumentDTOList.length > 0) {
          addElementToList = true;
          _.forEach(
            this.selectedDocumentElement.fpSecurityDocumentDTOList,
            (doc) => {
              if (doc.facilityID == element.facilityID) {
                if (doc.documentStatus == "DELETE") {
                  addElementToList = true;
                } else {
                  addElementToList = false;
                }
                return false;
              }
            }
          );
        } else {
          addElementToList = true;
        }
      }

      if (addElementToList) {
        facilityTypeOptionList.push({
          documentName: this.selectedDocumentElement.elementName,
          facilityName: element.facilityName,
          facilityAmount: element.facilityAmount,
          facilityID: element.facilityID,
          displayOrder: element.displayOrder,
          creditFacilityName: element.creditFacilityName,
          value: element.displayOrder,
          label:
            "Facility " +
            element.displayOrder +
            " - " +
            element.creditFacilityName +
            " - Rs " +
            this.currencyPipe.transform(element.facilityAmount, "", ""),
        });
      }
    });

    return facilityTypeOptionList;
  }

  submitDocument() {
    let securityDocumentData = Object.assign(
      {},
      { facilityPaperID: this.selectedNode.facilityPaperID },
      { creditFacilityTemplateID: this.selectedNode.creditFacilityTemplateID },
      { creditFacilityName: this.selectedNode.creditFacilityName },
      { elementID: this.selectedNode.elementID },
      { securityDocumentID: this.selectedNode.securityDocumentID },
      { facilityID: this.selectedNode.facilityID },
      { documentName: this.selectedNode.label },
      { savedByDisplayName: this.loggedInUserDisplayName },
      { documentStatus: "SUBMIT" },
      { returnComment: null },
      { documentContent: this.selectedNode.documentContent }
    );

    this.facilityPaperAddEditService
      .saveOrUpdateSecurityDocument(securityDocumentData)
      .then((data: any) => {
        _.remove(
          this.fpSecurityDocumentDTOList,
          (i) => i.securityDocumentID === data.securityDocumentID
        );
        this.modifyDocumentTreeStructure(data);
      });
  }

  deleteDocument() {
    let securityDocumentData = Object.assign(
      {},
      { facilityPaperID: this.selectedNode.facilityPaperID },
      { creditFacilityTemplateID: this.selectedNode.creditFacilityTemplateID },
      { creditFacilityName: this.selectedNode.creditFacilityName },
      { elementID: this.selectedNode.elementID },
      { securityDocumentID: this.selectedNode.securityDocumentID },
      { facilityID: this.selectedNode.facilityID },
      { documentName: this.selectedNode.label },
      { savedByDisplayName: this.loggedInUserDisplayName },
      { documentStatus: "DELETE" },
      { returnComment: null },
      { documentContent: this.selectedNode.documentContent }
    );

    this.facilityPaperAddEditService
      .saveOrUpdateSecurityDocument(securityDocumentData)
      .then((data: any) => {
        _.remove(
          this.fpSecurityDocumentDTOList,
          (i) => i.securityDocumentID === data.securityDocumentID
        );
        this.modifyDocumentTreeStructure(data);
      });
  }

  approveDocument() {
    let securityDocumentData = Object.assign(
      {},
      { facilityPaperID: this.selectedNode.facilityPaperID },
      { creditFacilityTemplateID: this.selectedNode.creditFacilityTemplateID },
      { creditFacilityName: this.selectedNode.creditFacilityName },
      { elementID: this.selectedNode.elementID },
      { securityDocumentID: this.selectedNode.securityDocumentID },
      { facilityID: this.selectedNode.facilityID },
      { authByDisplayName: this.loggedInUserDisplayName },
      { documentName: this.selectedNode.label },
      { documentStatus: "APPROVE" },
      { returnComment: null },
      { documentContent: this.selectedNode.documentContent }
    );

    this.facilityPaperAddEditService
      .saveOrUpdateSecurityDocument(securityDocumentData)
      .then((data: any) => {
        _.remove(
          this.fpSecurityDocumentDTOList,
          (i) => i.securityDocumentID === data.securityDocumentID
        );
        this.modifyDocumentTreeStructure(data);
      });
  }

  returnDocument(comment) {
    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          heading: "Add Return Comment",
          comment: comment,
        },
      }
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let securityDocumentData = Object.assign(
          {},
          { facilityPaperID: this.selectedNode.facilityPaperID },
          {
            creditFacilityTemplateID:
              this.selectedNode.creditFacilityTemplateID,
          },
          { creditFacilityName: this.selectedNode.creditFacilityName },
          { elementID: this.selectedNode.elementID },
          { securityDocumentID: this.selectedNode.securityDocumentID },
          { facilityID: this.selectedNode.facilityID },
          { authByDisplayName: this.loggedInUserDisplayName },
          { documentName: this.selectedNode.label },
          { returnComment: data.comment },
          { documentStatus: "RETURN" },
          { documentContent: this.selectedNode.documentContent }
        );

        this.facilityPaperAddEditService
          .saveOrUpdateSecurityDocument(securityDocumentData)
          .then((data: any) => {
            _.remove(
              this.fpSecurityDocumentDTOList,
              (i) => i.securityDocumentID === data.securityDocumentID
            );
            this.modifyDocumentTreeStructure(data);
          });
      }
    });
  }

  previewDocument() {
    let matchDocName = this.selectedNode.label.match(/Offer Letter/gi);
    let printContents, popupWin;
    printContents = this.selectedNode.documentContent;
    popupWin = window.open("", "_blank", "top=0,left=0,height=80%,width=auto");
    popupWin.document.open();
    if (matchDocName != null) {
      popupWin.document.write(`
                 <html>
                    <head>
                      <script defer src="assets/js/polyfill.js"></script>
                      <script>
                        window.PagedConfig = {auto:false}
                        window.addEventListener('load', () => {window.PagedPolyfill.preview().then(() => {window.print();});});
                      </script>
                      <style type="text/css">
                           @page {size: A4;  margin-bottom: 1.5cm; margin-top: 3.5cm;
                           <!-- @bottom-right { content: "CCDU | Page "counter(page)" of " counter(pages); }} -->
                           .template-footer {position: fixed; bottom:4.5cm; width: 100%; display:table;}
                           .template-footer p {page-break-before: always;font-size: 10pt;}
                           .template-footer p::before {content: "CCDU | Page "counter(page)" of " counter(pages);display: block;text-align: right; margin-right: 2.5cm;}
                           .pagebreak {page-break-before: always; }
                      </style>
                    </head>
                    <body onafterprint="window.close()">${printContents}</body>
                  </html>`);
    } else {
      popupWin.document.write(`
                <html>
                  <head></head>
                  <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                </html>`);
    }

    popupWin.document.close();
  }

  printDocument() {
    let securityDocumentData = Object.assign(
      {},
      { facilityPaperID: this.selectedNode.facilityPaperID },
      { creditFacilityTemplateID: this.selectedNode.creditFacilityTemplateID },
      { creditFacilityName: this.selectedNode.creditFacilityName },
      { elementID: this.selectedNode.elementID },
      { securityDocumentID: this.selectedNode.securityDocumentID },
      { facilityID: this.selectedNode.facilityID },
      { printedByDisplayName: this.loggedInUserDisplayName },
      { documentName: this.selectedNode.label },
      { documentStatus: "PRINT" },
      { returnComment: null },
      { documentContent: this.selectedNode.documentContent }
    );

    this.facilityPaperAddEditService
      .saveOrUpdateSecurityDocument(securityDocumentData)
      .then((data: any) => {
        let matchDocName = data.documentName.match(/Offer Letter/gi);
        let printContents, popupWin;
        printContents = this.selectedNode.documentContent;
        popupWin = window.open(
          "",
          "_blank",
          "top=0,left=0,height=80%,width=auto"
        );
        popupWin.document.open();
        if (matchDocName != null) {
          popupWin.document.write(`
                       <html>
                           <head>
                            <script defer src="assets/js/polyfill.js"></script>
                             <script>
                               window.PagedConfig = {auto:false}
                               window.addEventListener('load', () => {window.PagedPolyfill.preview().then(() => {window.print();});});
                             </script>
                             <style type="text/css">
                                  @page {size: A4;  margin-bottom: 1.5cm; margin-top: 3.5cm;
                                  <!-- @bottom-right { content: "CCDU | Page "counter(page)" of " counter(pages); }} -->
                                  .template-footer {position: fixed; bottom:4.5cm; width: 100%; display:table;}
                                  .template-footer p {page-break-before: always;font-size: 10pt;}
                                  .template-footer p::before {content: "CCDU | Page "counter(page)" of " counter(pages);display: block;text-align: right; margin-right: 2.5cm;}
                                  .pagebreak {page-break-before: always; }
                             </style>
                           </head>
                           <body onafterprint="window.close()">${printContents}</body>
                         </html>`);
        } else {
          popupWin.document.write(`
                      <html>
                        <head></head>
                        <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                      </html>`);
        }

        popupWin.document.close();
      });
  }

  viewActionLog() {
    let securityDocumentID = this.selectedNode.securityDocumentID;

    this.facilityPaperAddEditService
      .getSecurityDocumentHistory(securityDocumentID)
      .then((data: any) => {
        this.modalRef = this.mdbModalService.show(AddEditDocumentComponent, {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: "model-height-60-p model-width-100-p modal-dialog-scrollable",
          containerClass: "",
          animated: true,
          data: {
            content: {
              securityDocumentHistoryDTOList: data,
              showHistoryTable: true,
            },
          },
        });

        this.modalRef.content.action.subscribe((data: any) => {});
      });
  }

  showReturnComment() {
    let returnCommentMessage =
      "Comment Added By : " +
      this.selectedDocumentElement.authBy +
      "\n\nComment Added On : " +
      this.selectedDocumentElement.authDateStr +
      "\n\nComment : " +
      this.selectedDocumentElement.returnComment;

    this.modalRef = this.mdbModalService.show(InformationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Return Comment",
        message: returnCommentMessage,
      },
    });

    this.modalRef.content.action.subscribe((response: any) => {});
  }

  modifyDocumentTreeStructure(data: any) {
    let isFound = false;

    if (this.fpSecurityDocumentDTOList.length > 0) {
      if (data.documentStatus == "DRAFT") {
        _.remove(
          this.fpSecurityDocumentDTOList,
          (i) => i.documentStatus === "RETURN" && i.elementID === data.elementID
        );
      }
    }

    this.fpSecurityDocumentDTOListTemp = this.fpSecurityDocumentDTOList;
    if (data) {
      if (this.fpSecurityDocumentDTOListTemp.length == 0) {
        this.fpSecurityDocumentDTOListTemp.push(data);
      } else {
        _.forEach(this.fpSecurityDocumentDTOListTemp, (doc) => {
          if (
            doc.securityDocumentID == data.securityDocumentID &&
            doc.documentName == data.documentName &&
            doc.documentStatus == data.documentStatus
          ) {
            doc.documentContent = data.documentContent;
            doc.documentStatus = data.documentStatus;
            doc.facilityID = data.facilityID;
            doc.savedDateStr = data.savedDateStr;
            doc.savedBy = data.savedBy;
            doc.authBy = data.authBy;
            doc.authByDiv = data.authByDiv;
            doc.savedByDiv = data.savedByDiv;
            doc.authDateStr = data.authDateStr;
            doc.fpSecurityDocumentTagDataDTOList =
              data.fpSecurityDocumentTagDataDTOList;
            isFound = true;
          }
        });
        if (!isFound) {
          this.fpSecurityDocumentDTOListTemp.push(data);
        }
      }

      _.forEach(this.facilityDocumentList, (list) => {
        if (list.elementID == data.elementID) {
          _.forEach(list.fpSecurityDocumentDTOList, (doc) => {
            if (
              doc.documentName == data.documentName &&
              doc.documentStatus == data.documentStatus
            ) {
              isFound = true;
            }
          });
          if (!isFound) {
            _.remove(
              list.fpSecurityDocumentDTOList,
              (i: any) => i.securityDocumentID === data.securityDocumentID
            );
            list.fpSecurityDocumentDTOList.push(data);
          }
        }
      });

      this.enableEditDraftDoc = true;
      this.selectUpdatedDocumentNode(data);
    }

    // console.log("this.fpSecurityDocumentDTOList",this.fpSecurityDocumentDTOList);
    //  console.log("this.fpSecurityDocumentDTOListTemp",this.fpSecurityDocumentDTOListTemp);
  }

  editDraftDocument($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.selectedDocumentElement = this.selectedNode;

    this.modalRef = this.mdbModalService.show(AddEditDocumentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "model-height-80-p model-width-55-p modal-dialog-scrollable",
      containerClass: "",
      animated: true,
      data: {
        content: {
          enableEditDraftDoc: this.enableEditDraftDoc,
          facilityPaper: this.facilityPaper,
          loggedInUserDisplayName: this.loggedInUserDisplayName,
          selectedDocumentElement: this.selectedDocumentElement,
          facilityDocumentList: this.facilityDocumentList,
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      this.modifyDocumentTreeStructure(data);
    });
  }

  openModalAddEdit($event?) {
    if (this.enableEditDraftDoc) {
      this.editDraftDocument($event);
    } else {
      this.editInitialDocument($event);
    }
  }

  selectUpdatedDocumentNode(updatedDocumentNode) {
    this.documentContent = updatedDocumentNode.documentContent;
    //this.initialNodes = this.treeConstruct(this.facilityDocumentList);
    this.documentNodes = this.treeDocumentConstruct(
      this.fpSecurityDocumentDTOListTemp
    );

    _.forEach(this.documentNodes, (node: any) => {
      if (node.documentStatus == updatedDocumentNode.documentStatus) {
        _.forEach(node.children, (children: any) => {
          if (
            children.elementID == updatedDocumentNode.elementID &&
            children.documentName == updatedDocumentNode.documentName
          ) {
            this.selectedNode = children;
          }
        });
      }
    });

    // this.setExpandFalseAllInitialNodes();
    //    this.setExpandTrueAllInitialNodes();
  }

  setExpandFalseAllInitialNodes() {
    this.selectedInitialNode = {};
    _.forEach(this.initialNodes, (node) => {
      node.expanded = false;
    });
  }

  setExpandTrueAllInitialNodes() {
    this.selectedInitialNode.expanded = true;
  }

  setExpandFalseAllDocumentNodes() {
    this.selectedNode = {};
    _.forEach(this.documentNodes, (node) => {
      node.expanded = false;
    });
  }

  showEditDocumentButton() {
    return (
      this.visibleToEnterer &&
      ((this.showDocPreview &&
        (this.selectedNode.documentStatus == "RETURN" ||
          this.selectedNode.documentStatus == "DRAFT")) ||
        (this.showDocPreview && _.isEmpty(this.selectedNode)))
    );
  }

  showSubmitDocumentButton() {
    return (
      this.visibleToEnterer &&
      this.showDocPreview &&
      this.enableEditDraftDoc &&
      this.selectedNode.documentStatus == "DRAFT"
    );
  }

  showDeleteDocumentButton() {
    return (
      this.visibleToEnterer &&
      this.showDocPreview &&
      this.enableEditDraftDoc &&
      this.selectedNode.documentStatus == "DRAFT"
    );
  }

  showApproveDocumentButton() {
    return (
      this.visibleToAuthorizer &&
      this.showDocPreview &&
      this.selectedNode.documentStatus == "SUBMIT"
    );
  }

  showReturnDocumentButton() {
    return (
      this.showDocPreview &&
      ((this.visibleToAuthorizer &&
        this.selectedNode.documentStatus == "SUBMIT") ||
        (this.visibleToViewer && this.selectedNode.documentStatus == "APPROVE"))
    );
  }

  showPrintDocumentButton() {
    return (
      this.showDocPreview &&
      this.selectedNode.documentStatus == "APPROVE" &&
      !this.visibleToEnterer
    );
  }

  showDocumentTreeNavigation() {
    return (
      this.visibleToEnterer || this.visibleToAuthorizer || this.visibleToViewer
    );
  }

  showInitialTreeNavigation() {
    return this.visibleToEnterer;
  }

  showReturnCommentButton() {
    return (
      this.visibleToEnterer &&
      this.showDocPreview &&
      this.selectedNode.documentStatus == "RETURN"
    );
  }

  showActionLogButton() {
    return this.showDocPreview && Object.keys(this.selectedNode).length != 0;
  }

  showPreviewDocumentButton() {
    return (
      (this.visibleToEnterer || this.visibleToAuthorizer) &&
      !_.isEmpty(this.selectedNode)
    );
  }
}
