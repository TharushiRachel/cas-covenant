import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-cov-advances-details',
  templateUrl: './cov-advances-details.component.html',
  styleUrls: ['./cov-advances-details.component.scss']
})
export class CovAdvancesDetailsComponent implements OnInit {

  @Input() customerDetails: any;

  constructor(public modalRef: MDBModalRef) { }

  ngOnInit(): void {
  }

}
