import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../app.utils";
import {Constants} from "../../../core/setting/constants";
import * as _ from "lodash";
import {CacheService} from "../../../core/service/data/cache.service";
import {ApplicationService} from "../../../core/service/application/application.service";

@Component({
  selector: 'app-facility-paper-copy-dialog',
  templateUrl: './facility-paper-copy-dialog.component.html',
  styleUrls: ['./facility-paper-copy-dialog.component.scss']
})
export class FacilityPaperCopyDialogComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
  facilityPaper: any;
  action: Subject<any> = new Subject<any>();
  results: Subject<any>;
  componentForm: FormGroup;
  formSubs = new Subscription();
  onFormValueChangeSub = new Subscription();
  formErrors: any;
  templateOption: any[] = [];
  allOptions = [];
  allWorkFlowTemplates = [];
  currentUserBranchCode = '';

  constructor(private mdbModalRef: MDBModalRef,
              private cacheService: CacheService,
              private applicationService: ApplicationService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {

    this.formErrors = {
      branchName: {},
      workFlow: {},
    };
    this.allOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.results = new BehaviorSubject(this.allOptions);

    this.currentUserBranchCode = this.applicationService.getLoggedInUserDivCode();
    let defaultBranch = AppUtils.getBranchFromBranchCode(this.allOptions, this.currentUserBranchCode);
    let defaultBranchName = defaultBranch ? defaultBranch.branchName : '';

    this.allWorkFlowTemplates = this.cacheService.getData(Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES);
    _.forEach(this.allWorkFlowTemplates, template => {
      if (template.status == 'ACT') {
        this.templateOption.push({
          value: template.workFlowTemplateID,
          label: template.code + "-" + template.workFlowTemplateName
        });
      }
    });

    this.componentForm = this.formBuilder.group({
      branchName: ['', [Validators.required]],
      workFlow: [this.templateOption.length > 0 ? this.templateOption[0].value : '', [Validators.required]],
    });

    this.formSubs = this.componentForm.controls.branchName.valueChanges.subscribe((value: any) => {
      this.results.next(this.filter(value));
    });

    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allOptions.filter((item: any) => item.branchName.toLowerCase().includes(filterValue));
  }

  isValid() {
    return this.componentForm.valid;
  }

  copy() {
    let data: any = {};
    let {branchName, workFlow} = this.componentForm.getRawValue();
    let branch = AppUtils.getBranchFromBranchName(this.allOptions, branchName);
    data.branchCode = branch.branchCode;
    data.workflowTemplateID = workFlow;
    data.currentAssignUser = this.applicationService.getLoggedInUserUserName();
    data.currentAssignUserID = this.applicationService.getLoggedInUserUserID();
    data.assignUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
    data.upmID = this.applicationService.getLoggedInUserUserID();

    this.action.next(data);
    this.mdbModalRef.hide();
  }

  close(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

}
