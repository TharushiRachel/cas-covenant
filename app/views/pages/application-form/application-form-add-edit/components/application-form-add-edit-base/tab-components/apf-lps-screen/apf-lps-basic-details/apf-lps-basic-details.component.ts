import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../../../shared/app.utils";
import {CacheService} from "../../../../../../../../../core/service/data/cache.service";
import {isEmpty} from "lodash";

@Component({
  selector: 'app-apf-lps-basic-details',
  templateUrl: './apf-lps-basic-details.component.html',
  styleUrls: ['./apf-lps-basic-details.component.scss']
})
export class ApfLpsBasicDetailsComponent implements OnInit {

  @Input('applicationForm') applicationForm: any = {};
  allBankOptions: any = {};
  yesNoConst = Constants.yesNoConst;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  visibility = false;
  constructor(private cacheService: CacheService,) {
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
