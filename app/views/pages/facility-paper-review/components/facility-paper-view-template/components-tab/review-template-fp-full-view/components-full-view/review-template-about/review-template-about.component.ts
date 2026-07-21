import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {AppUtils} from "../../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-review-template-about',
  templateUrl: './review-template-about.component.html',
  styleUrls: ['./review-template-about.component.scss']
})
export class ReviewTemplateAboutComponent implements OnInit, OnDestroy {
  @Input('primaryCustomer') primaryCustomer: any = {};
  @Input('facilityPaper') facilityPaper: any = {};
  allBankOptions = [];
  branch: any = {};

  constructor() {
    //Deprecated Component
  }

  ngOnInit() {
    this.branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, this.facilityPaper.branchCode);
  }

  ngOnDestroy(): void {
  }

}
