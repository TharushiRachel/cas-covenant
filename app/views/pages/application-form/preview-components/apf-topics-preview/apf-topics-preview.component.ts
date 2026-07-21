import {Component, Input, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-apf-topics-preview',
  templateUrl: './apf-topics-preview.component.html',
  styleUrls: ['./apf-topics-preview.component.scss']
})
export class ApfTopicsPreviewComponent implements OnInit {
  @Input('selectedTopic') selectedTopic: any = {};
  modalRef: MDBModalRef;

  constructor() {
  }

  ngOnInit() {
  }


}
