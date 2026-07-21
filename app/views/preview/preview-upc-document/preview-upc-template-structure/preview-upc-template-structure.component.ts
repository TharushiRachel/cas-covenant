import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-preview-upc-template-structure',
  templateUrl: './preview-upc-template-structure.component.html',
  styleUrls: ['./preview-upc-template-structure.component.scss']
})
export class PreviewUpcTemplateStructureComponent implements OnInit {

  @Input('initNodeRowData') initNodeRowData: any = [];
  @Input('isCommentEnable') isCommentEnable:Boolean;
  @Input('facilityPaperId') facilityPaperId:any;
  @Input('currentAssignUser') currentAssignUser:any;
  @Input('currentFPStatus') currentFPStatus:any;
  @Input('currentFPWC') currentFPWC:any;
  @Input('assignDepartmentCode') assignDepartmentCode:any = "";
  
  constructor() {
  }

  ngOnInit() {
    //console.log("isCommentEnable",this.isCommentEnable)
  }

}
