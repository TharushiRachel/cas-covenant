import { Component, OnDestroy, OnInit } from "@angular/core";
import { ApplicationService } from "../../core/service/application/application.service";
import { AuthService } from "../../core/service/authentication/auth.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { UserDetailDisplayComponent } from "./user-detail-display/user-detail-display.component";
import { Subscription } from "rxjs";
import { ChangePasswordComponent } from "./change-password/change-password.component";
import { MasterDataService } from "src/app/core/service/data/master-data.service";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"],
})
export class HeaderComponent implements OnInit, OnDestroy {
  loginSubs = new Subscription();

  modalRef: MDBModalRef;

  constructor(
    private applicationService: ApplicationService,
    private mdbModalService: MDBModalService,
    private authService: AuthService,
    private readonly masterDataService: MasterDataService
  ) {}

  ngOnInit() {
    // this.loginSubs = this.authService
    //   .loginStatusBehavi
    //   .subscribe((isLoggedIn: boolean) => {
    //     if (isLoggedIn) {
    //       this.viewInfo(null);
    //     }
    //   });
    //  Login Initial Pop up Disabled // request from Menaka Weerasighne
  }

  ngOnDestroy(): void {
    this.loginSubs.unsubscribe();
  }

  getUserName() {
    return this.applicationService.getLoggedInUserCombinedName()
      ? this.applicationService.getLoggedInUserCombinedName()
      : this.applicationService.getUserDisplayName();
  }

  viewInfo(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }

    this.modalRef = this.mdbModalService.show(UserDetailDisplayComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: false,
      class: "modal-width-30-p modal-margin-12",
      containerClass: "",
      animated: true,
      data: {
        heading: "User Details",
        content: {},
      },
    });
  }

  onChangePassword(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }
    this.modalRef = this.mdbModalService.show(ChangePasswordComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "",
      animated: true,
      data: {
        heading: "Change Password",
        content: {},
      },
    });
  }

  onLogOut() {
    this.authService.getSSOConfigData().then((data: any) => {
      this.authService.setLoggedOut(data);
    });

  }

  isAgent() {
    return this.applicationService.isAgent();
  }
}
