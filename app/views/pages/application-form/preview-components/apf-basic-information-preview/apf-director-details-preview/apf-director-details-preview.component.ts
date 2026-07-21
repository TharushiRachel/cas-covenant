import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-director-details-preview',
  templateUrl: './apf-director-details-preview.component.html',
  styleUrls: ['./apf-director-details-preview.component.scss']
})
export class ApfDirectorDetailsPreviewComponent implements OnInit {
  @Input() basicInformation;
  customerIdentificationType = Constants.customerIdentificationType;
  ConstitutionType = Constants.ConstitutionType;
  yesNo = Constants.yesNo;
  civilStatus = Constants.civilStatus;

  constructor() {
  }

  ngOnInit() {
  }

}
