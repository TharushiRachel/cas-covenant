import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'app-preview-upc-section-node',
  templateUrl: './preview-upc-section-node.component.html',
  styleUrls: ['./preview-upc-section-node.component.scss']
})
export class PreviewUpcSectionNodeComponent implements OnInit {

  @Input('initNodeRowData') initNodeRowData: any = [];
  @Input('isCommentEnable') isCommentEnable:Boolean;
  treeData = [];
  previewData = [];
  @Input('facilityPaperId') facilityPaperId:any;
  @Input('currentAssignUser') currentAssignUser:any;
  @Input('currentFPStatus') currentFPStatus:any;
  @Input('currentFPWC') currentFPWC:any;
  @Input('assignDepartmentCode') assignDepartmentCode:any = "";

  constructor() { }

  ngOnInit() {
   // console.log("isCommentEnable zdsds",this.isCommentEnable)
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
          fpUpcSectionDataID: node.fpUpcSectionDataID,
          upcSectionID: node.upcSectionID,
          sectionLevel: node.sectionLevel,
          title: node.upcSectionName ? node.upcSectionName : '',
          modifiedUserDisplayName: node.modifiedUserDisplayName ? node.modifiedUserDisplayName : '',
          modifiedDateStr: node.modifiedDateStr ? node.modifiedDateStr : '',
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
          modifiedUserDisplayName: node.modifiedUserDisplayName ? node.modifiedUserDisplayName : '',
          modifiedDateStr: node.modifiedDateStr ? node.modifiedDateStr : '',
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
