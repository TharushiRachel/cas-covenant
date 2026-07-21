import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {AppUtils} from "../../../../../shared/app.utils";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {isEmpty} from "lodash";

@Component({
  selector: 'app-apf-status-history-preview',
  templateUrl: './apf-status-history-preview.component.html',
  styleUrls: ['./apf-status-history-preview.component.scss']
})
export class ApfStatusHistoryPreviewComponent implements OnInit {
  @Input("applicationForm") applicationForm;
  applicationFormForwardType = Constants.applicationFormForwardType;
  allBankOptions: any = {};

  constructor(private cacheService: CacheService) {
  }

  ngOnInit() {
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
