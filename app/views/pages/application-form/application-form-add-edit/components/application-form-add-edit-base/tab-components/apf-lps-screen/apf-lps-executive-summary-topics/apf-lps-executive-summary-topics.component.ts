import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-apf-lps-executive-summary-topics',
  templateUrl: './apf-lps-executive-summary-topics.component.html',
  styleUrls: ['./apf-lps-executive-summary-topics.component.scss']
})
export class ApfLpsExecutiveSummaryTopicsComponent implements OnInit {
  @Input('applicationForm') applicationForm: any = {};
  visibility = false;
  constructor() {
  }

  ngOnInit() {
  }

}
