import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-cov-creation-details',
  templateUrl: './cov-creation-details.component.html',
  styleUrls: ['./cov-creation-details.component.scss']
})
export class CovCreationDetailsComponent implements OnInit {

  @Input() coveringApproval: any;
  commentDetails: any;
  basicInfoDetails: any;
  @Input() upmDetails: any;

  selectedTabIndex: any = 0;
  aboutTabIndex = 0;
  commentTabIndex = 1;

  constructor() { }

  ngOnInit() {
    this.basicInfoDetails = this.coveringApproval.covAppBasicInfoDTOList[0];
    this.commentDetails = this.coveringApproval.covAppCommentDTOList[0];
  

  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }
  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

}
