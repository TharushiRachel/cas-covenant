import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-cov-accounts-details',
  templateUrl: './cov-accounts-details.component.html',
  styleUrls: ['./cov-accounts-details.component.scss']
})
export class CovAccountsDetailsComponent implements OnInit {

  @Input() customerDetails: any;

  constructor(public modalRef: MDBModalRef) { }

  ngOnInit() {
  }

}
