import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";

@Component({
  selector: 'app-apf-return-application-form',
  templateUrl: './apf-return-application-form.component.html',
  styleUrls: ['./apf-return-application-form.component.scss']
})
export class ApfReturnApplicationFormComponent implements OnInit, OnDestroy {

  action: Subject<any> = new Subject<any>();
  content: any = {};
  heading: any = '';
  applicationForm: any = {};
  assignedUser: any = {};
  componentForm: FormGroup;
  users: any = [];
  usersSelectOptions: any = [];
  onAFReturnUserListChangeSub = new Subscription();
  onUserNameChangeSub = new Subscription();
  onFormChangeSub = new Subscription();
  formError: any = {};
  allBranches: any[] = [];

  constructor(
    public  mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private cacheService: CacheService,
    private applicationFormAddEditService: ApplicationFormAddEditService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {
    this.allBranches = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);

    this.formError = {
      comment: {},
      user: {}
    };
    this.applicationForm = this.content.applicationForm;
    this.applicationFormAddEditService.getAFReturnUsersList(this.applicationForm);

    this.onAFReturnUserListChangeSub = this.applicationFormAddEditService.onAFReturnUserListChangeSub.subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.users = [];
        this.usersSelectOptions = [];
        this.users = res;
        _.sortBy(res, ['assignUserUpmGroupCode']).forEach(user => {
          if (user.assignUserDisplayName) {
            let branch = AppUtils.getBranchFromBranchCode(this.allBranches, user.assignUserSolID);

            this.usersSelectOptions.push({
              value: user.assignUserID,
              label: user.assignUserUpmGroupCode + ' - ' + user.assignUserDisplayName + `${branch ? ' - ' + branch.branchName : ''}`,
            });
          }
        });
      }
    });

    this.componentForm = this.createComponentForm();

    this.onUserNameChangeSub = this.componentForm.controls.user.valueChanges
      .subscribe((value: any) => {
        if (value) {
          this.assignedUser = AppUtils.getApplicationFormAssignedUserFromUserID(this.users, value);
        }
      });

    this.onFormChangeSub = this.componentForm.controls.isUsersOnly.valueChanges.subscribe(data => {
      if (data) {
        this.componentForm.get('isDivisionOnly').setValue(false);
        this.componentForm.get('isPublic').setValue(false);
      } else {
        this.componentForm.get('isPublic').setValue(true);
      }
    });

    this.onFormChangeSub = this.componentForm.controls.isDivisionOnly.valueChanges.subscribe(data => {
      if (data) {
        this.componentForm.get('isUsersOnly').setValue(false);
        this.componentForm.get('isPublic').setValue(false);
      } else {
        this.componentForm.get('isPublic').setValue(true);
      }
    });
  }

  isValid() {
    return this.componentForm.valid
  }

  return() {
    let actionMessage = `${this.applicationService.getLoggedInUserDisplayName()} return to ${this.assignedUser.assignUserDisplayName}`;
    let saveObj = {
      assignUserID: this.assignedUser.assignUserID,
      assignUser: this.assignedUser.assignUser,
      assignADUserID: this.assignedUser.assignUser,
      assignUserDisplayName: this.assignedUser.assignUserDisplayName,
      assignUserUpmID: this.assignedUser.assignUserID,
      assignUserSolID: this.assignedUser.assignUserSolID,
      assignUserUpmGroupCode: this.assignedUser.assignUserUpmGroupCode,
      upmID: this.assignedUser.assignUserID,
      upmGroupCode: this.assignedUser.assignUserUpmGroupCode,
      actionMessage: actionMessage,
      afCommentDTO: {
        createdUserID: this.applicationService.getLoggedInUserUserID(),
        createdUser: this.applicationService.getLoggedInUserUserName(),
        createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
        createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
        createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
        assignedUserID: this.assignedUser.assignUserID,
        assignedUser: this.assignedUser.assignUser,
        assignedUserDisplayName: this.assignedUser.assignUserDisplayName,
        assignedUserSolID: this.assignedUser.assignUserSolID,
        comment: this.componentForm.controls.comment.value,
        isUsersOnly: this.componentForm.controls.isUsersOnly.value ? 'Y' : 'N',
        isDivisionOnly: this.componentForm.controls.isDivisionOnly.value ? 'Y' : 'N',
        isPublic: this.componentForm.controls.isPublic.value ? 'Y' : 'N',
      },
    };
    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: ['', [Validators.required, Validators.maxLength(4000)]],
      user: ['', [Validators.required]],
      isUsersOnly: [false],
      isDivisionOnly: [false],
      isPublic: [true],
    })
  }

  ngOnDestroy(): void {
    this.onAFReturnUserListChangeSub.unsubscribe();
    this.onUserNameChangeSub.unsubscribe();
    this.onFormChangeSub.unsubscribe();
  }

}
