import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-apf-other-bank-details-preview',
  templateUrl: './apf-other-bank-details-preview.component.html',
  styleUrls: ['./apf-other-bank-details-preview.component.scss']
})
export class ApfOtherBankDetailsPreviewComponent implements OnInit {

  @Input() basicInformation;

  constructor() {
  }

  ngOnInit() {
  }

}
