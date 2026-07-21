import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-cov-deposits-details',
  templateUrl: './cov-deposits-details.component.html',
  styleUrls: ['./cov-deposits-details.component.scss']
})
export class CovDepositsDetailsComponent implements OnInit {

  @Input() customerDetails: any;

  constructor(public modalRef: MDBModalRef) { }

  ngOnInit(): void {
  }

}
