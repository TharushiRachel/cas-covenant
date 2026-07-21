import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-fp-credit-risk-comment-data',
  templateUrl: './fp-credit-risk-comment-data.component.html',
  styleUrls: ['./fp-credit-risk-comment-data.component.scss']
})
export class FpCreditRiskCommentDataComponent implements OnInit {

  @Input("fpRiskComment") fpRiskComment;

  constructor() { }

  ngOnInit() {
  }

}
