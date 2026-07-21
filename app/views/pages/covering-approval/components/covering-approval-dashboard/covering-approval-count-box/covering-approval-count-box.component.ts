import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Constants } from 'src/app/core/setting/constants';

@Component({
  selector: 'app-covering-approval-count-box',
  templateUrl: './covering-approval-count-box.component.html',
  styleUrls: ['./covering-approval-count-box.component.scss']
})
export class CoveringApprovalCountBoxComponent implements OnInit {

  @Input('count') count: number = 0;
  @Input('status') status = '';
  @Input('selectedStatus') selectedStatus;

  @Output('onClick') onClick = new EventEmitter();

  coveringApprovalDashboardStatus = Constants.coveringApprovalDashboardStatus;

  constructor() {

  }

  ngOnInit() {
  }

  loadPageData() {
    this.onClick.emit(this.status);
  }

}
