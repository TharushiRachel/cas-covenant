import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../../core/setting/constants";

@Component({
  selector: 'application-form-count-box',
  templateUrl: './application-form-count-box.component.html',
  styleUrls: ['./application-form-count-box.component.scss']
})
export class ApplicationFormCountBoxComponent implements OnInit {

  @Input('count') count: number = 0;
  @Input('status') status = '';
  @Input('selectedStatus') selectedStatus;

  @Output('onClick') onClick = new EventEmitter();


  applicationFormDashboardStatus = Constants.applicationFormDashboardStatus;

  constructor() {
  }

  ngOnInit() {
  }

  loadPageData() {
    this.onClick.emit(this.status);
  }

}
