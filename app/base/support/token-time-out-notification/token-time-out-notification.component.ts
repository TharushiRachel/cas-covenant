import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {SETTINGS} from "../../../core/setting/commons.settings";

@Component({
  selector: 'app-token-time-out-notification',
  templateUrl: './token-time-out-notification.component.html',
  styleUrls: ['./token-time-out-notification.component.scss']
})
export class TokenTimeOutNotificationComponent implements OnInit, OnDestroy {

  sessionTimeOutCounter;
  heading: string;
  message: string;
  expireDate: any;
  systemDate;
  timeDif: any;
  minutes: number;
  seconds: number;
  action: Subject<any> = new Subject<any>();

  constructor(private mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {
    this.systemDate = new Date();
    this.timeDif = SETTINGS.TOKEN_EXPIRATION_DURATION_IN_MINUTES - 15;
    this.setSessionTimeOutCounter();
  }

  setSessionTimeOutCounter() {
    this.clearSessionTimeOutCounter();
    this.seconds = this.timeDif * 60;
    this.sessionTimeOutCounter = setInterval(() => {
        this.seconds = this.seconds - 1;
        this.minutes = Math.floor(this.seconds / 60);
        this.message = `${this.minutes > 0 ? this.minutes + " minutes and " : ""}  ${this.seconds % 60} seconds`;
        if (this.seconds === 0) {
          this.loggedOut();
        }
      },
      1000
    );
  }

  clearSessionTimeOutCounter() {
    clearInterval(this.sessionTimeOutCounter);
  }

  onNoClick(): void {
    this.clearSessionTimeOutCounter();
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  loggedOut(): void {
    this.clearSessionTimeOutCounter();
    this.action.next({logout: true});
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    this.action.next({refreshToken: true});
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.clearSessionTimeOutCounter();
    this.action.unsubscribe();
  }

}
