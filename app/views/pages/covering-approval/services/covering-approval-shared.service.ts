import { Injectable } from '@angular/core';
import { isEmpty } from 'lodash';
import { BehaviorSubject } from 'rxjs';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { Constants } from 'src/app/core/setting/constants';
import { AppUtils } from 'src/app/shared/app.utils';

@Injectable({
  providedIn: 'root'
})
export class CoveringApprovalSharedService {

  private covFormInitData: any;
  private covFormInitDataKey = "kihabws7523"
  private coveringApprovalFormKey = 'coveringApprovalForm';
  private coveringApprovalForm: any;

  setCovFormInitData(details: any) {
    sessionStorage.setItem(this.covFormInitDataKey, JSON.stringify(details));
    this.covFormInitData = details;
  }
  clearCovFormInitData() {
    sessionStorage.removeItem(this.covFormInitDataKey);
  }

  getCovFormInitData() {
    const details = sessionStorage.getItem(this.covFormInitDataKey);
    this.covFormInitData = details ? JSON.parse(details) : null;
    return this.covFormInitData;
  }

  private pendingDashboardDetailsSubject = new BehaviorSubject<any>(null);
  pendingDashboardDetails$ = this.pendingDashboardDetailsSubject.asObservable();

  setCoveringApprovalForm(details: any): void {
    sessionStorage.setItem(this.coveringApprovalFormKey, JSON.stringify(details));
    this.coveringApprovalForm = details;
  }

  getCoveringApprovalForm(): any {
    const details = sessionStorage.getItem(this.coveringApprovalFormKey);
    this.coveringApprovalForm = details ? JSON.parse(details) : null;
    return this.coveringApprovalForm;
  }

  clearCoveringApprovalForm(): void {
    sessionStorage.removeItem(this.coveringApprovalFormKey);
  }

  getBranchName(branchCode: string): string {
    const allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      const branchName = branch.branchName + ' - ' + branch.branchCode;
      localStorage.setItem(branchCode, branchName); // Store branch name in local storage
      return branchName;
    }

    // Retrieve from local storage if branch is not found in the cache
    return localStorage.getItem(branchCode) || branchCode;
  }
  constructor(private cacheService: CacheService) { }
}
