import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-lead-count-box',
  templateUrl: './lead-count-box.component.html',
  styleUrls: ['./lead-count-box.component.scss']
})
export class LeadCountBoxComponent implements OnInit {

  @Input('count') count: number = 0;
  @Input('status') status = '';
  @Input('selectedStatus') selectedStatus;


  @Output('onClick') onClick = new EventEmitter();


  leadDashboardStatus = Constants.leadDashboardStatus;

  constructor() {
  }

  ngOnInit() {
  }

  loadPageData() {
    this.onClick.emit(this.status);
  }

}
