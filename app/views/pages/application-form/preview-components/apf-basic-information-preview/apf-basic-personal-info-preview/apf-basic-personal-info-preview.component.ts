import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {CacheService} from "../../../../../../core/service/data/cache.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-basic-personal-info-preview',
  templateUrl: './apf-basic-personal-info-preview.component.html',
  styleUrls: ['./apf-basic-personal-info-preview.component.scss']
})
export class ApfBasicPersonalInfoPreviewComponent implements OnInit {

  employStatus = Constants.employStatus;
  civilStatus = Constants.civilStatus;
  identificationType = Constants.customerIdentificationType;
  employmentConst = Constants.employStatusConst;
  basicInformationType = Constants.basicInformationType;
  customerIdentificationType = Constants.customerIdentificationType;
  addressType = Constants.addressType;
  customerDetails: any = {};
  allBankOptions: any = {};
  @Input() basicInformation;

  constructor(private cacheService: CacheService) {
  }

  ngOnInit() {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.customerDetails = this.basicInformation.afCustomerDTO ? this.basicInformation.afCustomerDTO : {};
  }

  isSelfEmployed() {
    return this.basicInformation.employment == this.employmentConst.SELF_EMPLOYED;
  }

  getBranchName(branchCode) {

    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!_.isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

}
