import { ChangeDetectorRef, Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../../../core/service/authentication/auth.service";
import { AlertService } from "../../../../core/service/common/alert.service";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { SETTINGS } from "../../../../core/setting/commons.settings";
import * as CryptoJS from "crypto-js";
import * as JSEncryptModule from "jsencrypt";
import { LocalStorage } from "ngx-webstorage";
import { Constants } from "../../../../core/setting/constants";
import { MasterDataService } from "../../../../core/service/data/master-data.service";
import { ApplicationService } from "../../../../core/service/application/application.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.PUBLIC_KEY)
  private publicKey: any;

  loginForm: FormGroup;
  buttonAction: String = "Sign up";
  isSignUpDisabled: boolean = false;
  isPasswordVisible: boolean = false;

  passwordInputType: string = "password";

  loginStatusBehaviourSub = new Subscription();
  loginSubscription = new Subscription();

  ssoConfig: any = null;
  isLoading: boolean = false;

  constructor(
    private authService: AuthService,
    private alertService: AlertService,
    private masterDataService: MasterDataService,
    private router: Router,
    private fb: FormBuilder,
    private route: Router,
    private applicationService: ApplicationService,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.prepareSSOConfig();

    this.initLoginForm();

    this.loginSubscription = this.authService
      .getLoggedInStatus()
      .subscribe((results) => {
        if (results.status) {
          if (
            this.applicationService.getLoggedInUserUPMGroupCode() ==
              Constants.applicationSecurityWorkClass.SDA ||
            this.applicationService.getLoggedInUserUPMGroupCode() ==
              Constants.applicationSecurityWorkClass.SDE
          ) {
            this.route.navigate([SETTINGS.PAGES.committeePaperDashboard]);
          } else {
            this.route.navigate([SETTINGS.PAGES.home]);
          }
        } else {
          let message = results.message
            ? results.message
            : "Login error : Incorrect username or password entered. Please try again.";
          this.alertService.showToaster(`${message}`, "ERROR");
        }
      });
  }

  ngOnDestroy(): void {
    this.loginSubscription.unsubscribe();
    this.loginStatusBehaviourSub.unsubscribe();
  }

  prepareSSOConfig() {
    this.isLoading = true;
    this.authService.getSSOConfigData().then((data: any) => {
      this.ssoConfig = data;
    }).finally(()=>{
      this.isLoading = false;
      this.changeDetectorRef.detectChanges();
    });
  }

  isSSOLogin(): boolean {
    return this.ssoConfig != null && this.ssoConfig.isSSOLogin;
  }

  initLoginForm() {
    this.loginForm = this.fb.group({
      userName: ["", [Validators.required]],
      password: ["", [Validators.required]],
    });
  }

  isValidForm() {
    return this.loginForm.valid && !this.isSignUpDisabled;
  }

  submit() {
    const controls = this.loginForm.controls;
    /** check form */
    this.buttonAction = "Verifying...";
    this.isSignUpDisabled = true;
    if (this.loginForm.invalid) {
      Object.keys(controls).forEach((controlName) =>
        controls[controlName].markAsTouched()
      );
      return;
    }

    const userName = controls["userName"].value.trim();
    const password = this.getEncryptedPassword(controls["password"].value);

    this.authService.userLogin({ username: userName, password });
    this.loginStatusBehaviourSub = this.authService.loginStatusBehavi.subscribe(
      (data: any) => {
        if (data) {
          this.isSignUpDisabled = true;
        } else {
          this.isSignUpDisabled = false;
          this.buttonAction = "Sign up";
        }
      }
    );
  }

  isControlHasError(controlName: string, validationType: string): boolean {
    const control = this.loginForm.controls[controlName];
    if (!control) {
      return false;
    }

    const result =
      control.hasError(validationType) && (control.dirty || control.touched);
    return result;
  }

  // private getEncryptedPassword(password) {
  //
  //   password = CryptoJS.SHA1(password);
  //   password = CryptoJS.enc.Base64.stringify(password);
  //
  //   let crypt = new JSEncryptModule.JSEncrypt();
  //   crypt.setPublicKey(this.publicKey || SETTINGS.KEYS.PUBLIC_KEY);
  //   password = crypt.encrypt(password);
  //   return password;
  // }

  private getEncryptedPassword(password) {
    let activeDirectoryEnabled = this.masterDataService.getSystemParameter(
      Constants.systemParamKey.ACTIVE_DIRECTORY_ENABLED
    );
    if (!activeDirectoryEnabled) {
      password = CryptoJS.SHA1(password);
      password = CryptoJS.enc.Base64.stringify(password);
    }

    let crypt = new JSEncryptModule.JSEncrypt();
    crypt.setPublicKey(this.publicKey || SETTINGS.KEYS.PUBLIC_KEY);
    password = crypt.encrypt(password);
    return password;
  }

  getButtonAction() {
    return this.buttonAction;
  }

  togglePassword() {
    if (this.passwordInputType == "password") {
      this.passwordInputType = "text";
      this.isPasswordVisible = true;
    } else {
      this.passwordInputType = "password";
      this.isPasswordVisible = false;
    }
  }
}
