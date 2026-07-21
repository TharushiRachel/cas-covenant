import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-fp-facilities-data',
  templateUrl: './fp-facilities-data.component.html',
  styleUrls: ['./fp-facilities-data.component.scss']
})
export class FpFacilitiesDataComponent implements OnInit {

  @Input("fpComment") fpComment;
  remarkStatus = Constants.remarkStatus;

  constructor() {
  }

  ngOnInit() {
  }

}
