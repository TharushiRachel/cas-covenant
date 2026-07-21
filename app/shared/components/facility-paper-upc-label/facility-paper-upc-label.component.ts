import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";


@Component({
  selector: 'app-facility-paper-upc-label',
  templateUrl: './facility-paper-upc-label.component.html',
  styleUrls: ['./facility-paper-upc-label.component.scss']
})
export class FacilityPaperUPCLabelComponent implements OnInit {

  @Input('facilityPaper') facilityPaper: any;
  @Input('tooltipContent') tooltipContent: any;
  title;
  label;
  fontColor;
  backgroundColor

  constructor() {
  }

  ngOnInit() {
      this.title = this.facilityPaper.upcTemplateName;
      this.label = this.facilityPaper.upcLabel;
      this.fontColor = this.facilityPaper.upcLabelFontColor;
      this.backgroundColor = this.facilityPaper.upcLabelBackgroundColor;

  }

}
