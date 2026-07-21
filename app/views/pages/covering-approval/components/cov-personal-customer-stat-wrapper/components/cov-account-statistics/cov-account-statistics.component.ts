import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-cov-account-statistics',
  templateUrl: './cov-account-statistics.component.html',
  styleUrls: ['./cov-account-statistics.component.scss']
})
export class CovAccountStatisticsComponent implements OnInit {


  @Input() customerDetails: any;

  constructor(public modalRef: MDBModalRef) { }

  ngOnInit(): void {
  }

}
