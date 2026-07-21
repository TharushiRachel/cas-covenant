import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-basic-information-preview',
  templateUrl: './apf-basic-information-preview.component.html',
  styleUrls: ['./apf-basic-information-preview.component.scss']
})
export class ApfBasicInformationPreviewComponent implements OnInit {

  @Input() basicInformation;
  basicInformationTypeConst = Constants.basicInformationTypeConst;

  constructor() {
  }

  ngOnInit() {
  }

  isPersonal() {
    return this.basicInformation.type === this.basicInformationTypeConst.PERSONAL;
  }

  isBusiness() {
    return this.basicInformation.type === this.basicInformationTypeConst.BUSINESS;
  }

  isCorporate() {
    return this.basicInformation.type === this.basicInformationTypeConst.CORPORATE;
  }

}
