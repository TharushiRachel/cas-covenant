import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import * as _ from "lodash";

@Component({
  selector: 'app-review-template-full-node-html',
  templateUrl: './review-template-full-node-html.component.html',
  styleUrls: ['./review-template-full-node-html.component.scss']
})
export class ReviewTemplateFullNodeHtmlComponent implements OnInit {

  content: any;
  dataMap: any = {};

  modalRef: MDBModalRef;

  @Input('initNodeRowData') initNodeRowData: any = [];
  isPreviewMode: boolean = true;

  nodeRowData: any = [];
  treeData = [];
  previewData = [];
  isCommentEnable:boolean = false;

  constructor(private mdbModalService: MDBModalService) {
  }

  ngOnInit() {
    this.nodeRowData = this.initNodeRowData ? this.initNodeRowData : [];

    _.each(this.nodeRowData, (upcSection) => {
      this.dataMap[upcSection.upcSectionID] = upcSection.data ? upcSection.data : '';
    });
    this.setTreeData(_.cloneDeep(this.nodeRowData));
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['initNodeRowData']) {
      this.setTreeData(_.cloneDeep(this.initNodeRowData));
    }
  }

  setTreeData(rowData) {
    let id = 0;

    this.treeData = [];
    this.previewData = [];

    _.each(rowData, (node) => {

      if (node.parentSectionID == null) {
        const nodeObj = Object.assign({}, {
          dataLabel: (++id).toString(),
          parentSectionID: node.parentSectionID,
          upcSectionID: node.upcSectionID,
          sectionLevel: node.sectionLevel,
          title: node.upcSectionName ? node.upcSectionName : '',
          content: node.data ? node.data : '',
          children: []
        });

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
        const nodeObj = Object.assign({}, {
          dataLabel: treeNode.dataLabel + '.' + (treeNode.children.length + 1).toString(),
          parentSectionID: node.parentSectionID,
          upcSectionID: node.upcSectionID,
          sectionLevel: node.sectionLevel,
          title: node.upcSectionName ? node.upcSectionName : '',
          content: node.data ? node.data : '',
          children: []
        });

        treeNode.children.push(nodeObj);
        this.previewData.push(nodeObj);
        return;

      } else if (treeNode.children.length > 0) {
        this.setTreeDataChild(treeNode.children, node);
      }
    })
  }


}
