import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ChangePasswordService} from "../../../core/service/authentication/change-password.service";
import {Subscription} from "rxjs";
import {AppUtils} from "../../../shared/app.utils";
import {ApplicationService} from "../../../core/service/application/application.service";
import {AuthService} from "../../../core/service/authentication/auth.service";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {PasswordPatternValidator, PasswordValidator} from "../../../views/pages/agent/validators/password.validator";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  changePasswordForm: FormGroup;
  formErrors;

  formChangeSubs = new Subscription();
  onUserPasswordChangeSubs = new Subscription();

  constructor(private changePasswordService: ChangePasswordService,
              private formBuilder: FormBuilder,
              private applicationService: ApplicationService,
              private authService: AuthService,
              public  mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {

    this.formErrors = {
      oldPassword: {},
      password: {},
      passwordConfirm: {},
    };

    this.changePasswordForm = this.formBuilder.group({
      oldPassword: ['', [Validators.required]],
      password: ['', [Validators.required]],
      passwordConfirm: ['', [Validators.required]]
    });

    this.changePasswordForm.setValidators([PasswordValidator.validateSamePassword, PasswordPatternValidator.validatePasswordPattern]);

    this.formChangeSubs = this.changePasswordForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.changePasswordForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.formChangeSubs.unsubscribe();
    this.onUserPasswordChangeSubs.unsubscribe();
  }

  changePassword() {
    let data = AppUtils.trim(this.changePasswordForm.getRawValue());
    let submitData: any = {};

    submitData.userID = this.applicationService.getLoggedInUserUserID();
    submitData.action = 'UPDATE';
    submitData.oldPassword = this.authService.getResetPassword(data.oldPassword);
    submitData.newPassword = data.password;

    this.changePasswordService.agentUpdateUserPassword(submitData);
    this.mdbModalRef.hide();
  }

  isValidForm() {
    return this.changePasswordForm.valid;
  }

}
