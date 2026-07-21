import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-acae-count-box',
  templateUrl: './acae-count-box.component.html',
  styleUrls: ['./acae-count-box.component.scss']
})
export class ACAECountBoxComponent implements OnInit {

  @Input('count') count: number = 0;
  @Input('status') status = '';
  @Input('selectedStatus') selectedStatus;
  @Output('onClick') onClick = new EventEmitter();

  acaeDashboardStatus = Constants.acaeStatusLabel;

  constructor() {
  }

  ngOnInit() {
  }

  loadPageData() {
    this.onClick.emit(this.status);
  }

}
