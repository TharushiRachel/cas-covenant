import {Component, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-audit-content',
  templateUrl: './audit-content.component.html',
  styleUrls: ['./audit-content.component.scss']
})
export class AuditContentComponent implements OnInit {

  heading: string;
  content: any;
  datatList: any;
  previousDataList: any;


  constructor(
    public  mdbModalRef: MDBModalRef
  ) {
  }

  ngOnInit() {
    this.datatList = JSON.parse(this.content.dto.updateContent);
    this.previousDataList = JSON.parse(this.content.dto.previousContent);
  }

}
