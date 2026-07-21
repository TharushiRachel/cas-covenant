import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";

@Component({
  selector: 'app-trackeble-info',
  templateUrl: './trackeble-info.component.html',
  styleUrls: ['./trackeble-info.component.scss']
})
export class TrackebleInfoComponent implements OnInit {
  @Input("updateDTO")
  updateDTO;

  approveStatus = Constants.approveStatus;

  constructor() {
  }

  ngOnInit() {
  }

}
