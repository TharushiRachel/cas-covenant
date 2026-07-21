import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Constants} from "../../../core/setting/constants";
import {CacheService} from "../../../core/service/data/cache.service";
import {AppUtils} from "../../../shared/app.utils";
import {isEmpty} from "lodash";

@Component({
  selector: 'app-preview-fp-about',
  templateUrl: './preview-fp-about.component.html',
  styleUrls: ['./preview-fp-about.component.scss']
})
export class PreviewFpAboutComponent implements OnInit {
  @Input('facilityPaper') facilityPaper: any = {};
  primaryCustomer: any = {};
  allBankOptions = [];
  branch: any = {};
  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;

  constructor(private cacheService: CacheService) {
  }

  ngOnChanges(changes: SimpleChange) {
    if (changes['facilityPaper']) {
      this.facilityPaper = changes['facilityPaper'].currentValue;
    }
  }

  ngOnInit() {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, this.facilityPaper.branchCode);
    this.facilityPaper.casCustomerDTOList.forEach(customer => {
      if (customer.isPrimary) {
        this.primaryCustomer = customer
      }
    })
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

}
