import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../core/setting/constants";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {FacilityPaperAddEditService} from "../../../../../services/facility-paper-add-edit.service";
import {Subject, Subscription} from "rxjs";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-fp-return',
  templateUrl: './fp-return.component.html',
  styleUrls: ['./fp-return.component.scss']
})
export class FpReturnComponent implements OnInit, OnDestroy {

  heading: any = '';
  users: any[] = [];
  allBranches: any[] = [];
  usersSelectOptions: any[] = [];
  formError: any = {};
  assignedUser: any = {};
  content: any = {};
  facilityPaper: any = {};
  componentForm: FormGroup;
  onFormChangeSub = new Subscription();
  onUserNameChangeSub = new Subscription();
  onFPReturnUserListChangeSub = new Subscription();
  action: Subject<any> = new Subject<any>();

  constructor(private formBuilder: FormBuilder,
              public  mdbModalRef: MDBModalRef,
              private cacheService: CacheService,
              private facilityPaperAddEditService: FacilityPaperAddEditService,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {
    this.facilityPaper = this.content.facilityPaper;
    this.allBranches = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    this.facilityPaperAddEditService.getFPDirectReturnUsersList(this.facilityPaper);

    this.formError = {
      comment: {},
      user: {}
    };


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

  ngOnDestroy(): void {
    this.onFormChangeSub.unsubscribe();
    this.onUserNameChangeSub.unsubscribe();
    this.onFPReturnUserListChangeSub.unsubscribe();
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
      fpCommentDTO: {
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
        actionMessage: actionMessage,
        currentFacilityPaperStatus: Constants.facilityPaperStatusConst.CANCEL,
        isUsersOnly: this.componentForm.controls.isUsersOnly.value ? 'Y' : 'N',
        isDivisionOnly: this.componentForm.controls.isDivisionOnly.value ? 'Y' : 'N',
        isPublic: this.componentForm.controls.isPublic.value ? 'Y' : 'N',
      },
    };
    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }


}
