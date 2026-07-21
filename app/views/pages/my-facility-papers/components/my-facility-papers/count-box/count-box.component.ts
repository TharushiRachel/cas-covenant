import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-count-box',
  templateUrl: './count-box.component.html',
  styleUrls: ['./count-box.component.scss']
})
export class CountBoxComponent implements OnInit {

  @Input('count') count: number = 0;
  @Input('status') status = '';
  @Input('selectedStatus') selectedStatus;

  @Output('onClick') onClick = new EventEmitter();


  facilityPaperStatus = Constants.facilityPaperDashboardStatus;

  constructor() {
  }

  ngOnInit() {
  }

  loadPageData() {
    this.onClick.emit(this.status);
  }

}
