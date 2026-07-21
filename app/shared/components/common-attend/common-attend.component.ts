import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../core/setting/constants";
import {AppUtils} from "../../app.utils";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../core/service/application/application.service";

@Component({
  selector: 'app-common-attend',
  templateUrl: './common-attend.component.html',
  styleUrls: ['./common-attend.component.scss']
})
export class CommonAttendComponent implements OnInit, OnDestroy {
  action: Subject<any> = new Subject<any>();
  heading: any = '';
  message: any = '';
  componentForm: FormGroup;
  onFormChangeSub = new Subscription();

  constructor(private formBuilder: FormBuilder, public  mdbModalRef: MDBModalRef, private applicationService: ApplicationService) {
  }

  ngOnInit() {
    this.componentForm = this.createComponentForm();
    this.onFormChangeSub = this.componentForm.controls.isDivisionOnly.valueChanges.subscribe(data => {
      if (data) {
        this.componentForm.get('isUsersOnly').setValue(false);
        this.componentForm.get('isPublic').setValue(false);
      } else {
        this.componentForm.get('isPublic').setValue(true);
      }
    });
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: [null],//as a request no need to comment on attend
      isUsersOnly: [false],
      isDivisionOnly: [false],
      isPublic: [true],
    })
  }


  attend() {
    let remarkData = {
      comment: this.componentForm.controls.comment.value,
      createdUserID: this.applicationService.getLoggedInUserUserID(),
      createdUser: this.applicationService.getLoggedInUserUserName(),
      createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
      createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      isUsersOnly: this.componentForm.controls.isUsersOnly.value ? 'Y' : 'N',
      isDivisionOnly: this.componentForm.controls.isDivisionOnly.value ? 'Y' : 'N',
      isPublic: this.componentForm.controls.isPublic.value ? 'Y' : 'N',
    };

    let saveObj = Object.assign({}, {
        assignedUser: {
          userID: this.applicationService.getLoggedInUserUserID(),
          adUserID: this.applicationService.getLoggedInUserUserName(),
          assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          assignUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          divCode: this.applicationService.getLoggedInUserDivCode(),
        }
      },
      {
        assignDepartmentCode: null,
        forwardType: Constants.ForwardTypeConst.DIRECT_USER,
      },
      {assignDepartmentDTOList: null},
      {
        remarkData: {
          ...remarkData
        }
      });

    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.componentForm.valid
  }

  ngOnDestroy(): void {
    this.onFormChangeSub.unsubscribe();
  }

}
