import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {MasterDataService} from "../../../../../../../../core/service/data/master-data.service";

@Component({
  selector: 'app-new-facility-paper-dialog',
  templateUrl: './new-facility-paper-dialog.component.html',
  styleUrls: ['./new-facility-paper-dialog.component.scss']
})
export class NewFacilityPaperDialogComponent implements OnInit, OnDestroy {

  action: Subject<any> = new Subject<any>();
  results: Subject<any>;
  formSubs = new Subscription();
  corporateModalRef: MDBModalRef;

  heading: string;
  customer360Details: any;
  selectedBankAccount: any;
  currentUserBranchCode: any;
  searchForm: FormGroup;
  allWorkFlowTemplates = [];
  templateOption: any[] = [];
  allOptions = [];

  constructor(private mdbModalRef: MDBModalRef,
              private cacheService: CacheService,
              private applicationService: ApplicationService,
              private masterDataService: MasterDataService,
              private formBuilder: FormBuilder,
              private  mdbModalService: MDBModalService,) {
  }

  ngOnInit() {

    this.currentUserBranchCode = this.applicationService.getLoggedInUserDivCode();
    this.allOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.results = new BehaviorSubject(this.allOptions);
    let defaultBranch = AppUtils.getBranchFromBranchCode(this.allOptions, this.currentUserBranchCode);
    let defaultBranchName = '';

    if (parseInt(this.applicationService.getLoggedInUserDivCode()) <= parseInt(this.masterDataService.getSystemParameter(Constants.systemParamKey.BRANCH_SOL_ID_LIMIT))) {
      defaultBranchName = defaultBranch ? defaultBranch.branchName : '';
    }

    this.allWorkFlowTemplates = this.cacheService.getData(Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES);
    _.forEach(this.allWorkFlowTemplates, template => {
      if (template.status == 'ACT') {
        this.templateOption.push({
          value: template.workFlowTemplateID,
          label: template.code + "-" + template.workFlowTemplateName
        });
      }
    });

    this.searchForm = this.formBuilder.group({
      branchName: [defaultBranchName, [Validators.required]],
      workFlow: [this.templateOption.length > 0 ? this.templateOption[0].value : '', [Validators.required]],
      committee: [{value: false, disabled: false}],
    });

    this.formSubs = this.searchForm.controls.branchName.valueChanges.subscribe((value: any) => {
      this.results.next(this.filter(value));
    });
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  draft() {
    // if (this.searchForm.controls.committee.value) {
    //   this.corporateModalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
    //     backdrop: true,
    //     keyboard: true,
    //     focus: true,
    //     show: false,
    //     ignoreBackdropClick: true,
    //     class: 'modal-width-30-p modal-margin-center ',
    //     containerClass: '',
    //     animated: false,
    //     data: {
    //       heading: "",
    //       message: `"Include Board Paper Limit Summary" the user cannot change it into general paper limit summary later. 
    //                  Do you want to continue ? `,
    //     }
    //   });
    //   this.corporateModalRef.content.action.subscribe((isYes: any) => {
    //     if (isYes) {
    //       this.corporateModalRef.hide();
    //       this.action.next({...this.searchForm.getRawValue()});
    //     } else {
    //       this.corporateModalRef.hide();
    //     }
    //   });
    // } else {
    //   this.action.next({...this.searchForm.getRawValue()});
    // }

    this.action.next({...this.searchForm.getRawValue()});
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.searchForm.valid;
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allOptions.filter((item: any) => item.branchName.toLowerCase().includes(filterValue));
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.formSubs.unsubscribe();
  }

}
