import {AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {NavigationEnd, Router} from "@angular/router";
import {CommonService} from "./core/service/common/common.service";
import {NgxUiLoaderService} from "ngx-ui-loader";
import {MasterDataService} from "./core/service/data/master-data.service";
import {ApplicationService} from "./core/service/application/application.service";
import {MyLeadCountService} from "./core/service/leed/my-lead-count.service";
import {MyFacilityPaperCountService} from "./core/service/facility-paper/my-facility-paper-count.service";
import {BnNgIdleService} from "bn-ng-idle";
import {AuthService} from "./core/service/authentication/auth.service";
import {SETTINGS} from "./core/setting/commons.settings";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit, AfterViewInit, OnDestroy {

  private unsubscribe: Subscription[] = [];
  userIdleSubs = new Subscription();

  loaderConfig = {
    "bgsColor": "#00ACC1",
    "bgsOpacity": 0.5,
    "bgsPosition": "bottom-right",
    "bgsSize": 60,
    "bgsType": "ball-spin-clockwise",
    "blur": 5,
    "fgsColor": "#3d799c",
    "fgsPosition": "center-center",
    "fgsSize": 40,
    "fgsType": "rectangle-bounce-party",
    "gap": 24,
    "logoPosition": "center-center",
    "logoSize": 60,
    // "logoUrl": "./assets/media/logos/logo__.png",
    "masterLoaderId": "master",
    "overlayBorderRadius": "0",
    "overlayColor": "rgba(100, 100, 100, 0.01)",
    "pbColor": "#00ACC1",
    "pbDirection": "ltr",
    "pbThickness": 3,
    "hasProgressBar": true,
    "text": "",
    "textColor": "#FFFFFF",
    "textPosition": "center-center",
    "threshold": 500
  };

  constructor(private router: Router,
              private commonService: CommonService,
              private ngxService: NgxUiLoaderService,
              private masterDataService: MasterDataService,
              private applicationService: ApplicationService,
              private myLeadCountService: MyLeadCountService,
              private myFacilityPaperCountService: MyFacilityPaperCountService,
              private bnIdle: BnNgIdleService,
              private authService: AuthService,
              private cdRef: ChangeDetectorRef) {

  }

  ngOnInit(): void {
    this.applicationService.loadPublicKey();
    this.masterDataService.reloadSystemConstants();
    this.masterDataService.reloadSystemParameters();
    this.masterDataService.reloadApplicationProperties();

    this.userIdleSubs = this.bnIdle.startWatching(SETTINGS.TOKEN_EXPIRATION_DURATION_IN_MINUTES * 60).subscribe((isTimedOut: boolean) => {
      if (isTimedOut) {
        if (this.authService.isLoggedIn() && !this.authService.refreshTokenPopupActivated) {
          this.authService.refreshTokenPopupActivated = true;
          this.authService.refreshTokenPopup();
        }
      }
    });

    const routerSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // scroll to top on every route change
        window.scrollTo(0, 0);
      }
    });
    this.unsubscribe.push(routerSubscription);

    const loadingStatusSubs = this.commonService.getLoadingStatus()
      .subscribe((isLoading: any) => {
        if (isLoading.status) {
          this.ngxService.start();
        } else {
          this.ngxService.stop();
        }
        this.cdRef.detectChanges();
      });

    const onApiError = this.commonService.onError.subscribe(onError => {
      if (onError) {
        this.ngxService.stop();
        this.cdRef.detectChanges();
      }
    });
    this.masterDataService.reloadSystemParameters();

    this.unsubscribe.push(loadingStatusSubs);
    this.unsubscribe.push(onApiError);
  }

  ngAfterViewInit(): void {

    let onPendingLeadCountChangeSubs = this.myLeadCountService.onPendingLeadCountChange
      .subscribe((countStr: any) => {
        setTimeout(() => {
          let countHtml = document.getElementById('branch_lead_pending_count');
          if (countHtml) {
            countHtml.innerHTML = countStr;
          }
        }, 1000);
      });

    this.unsubscribe.push(onPendingLeadCountChangeSubs);

    this.myLeadCountService.getLoggedInUserPendingLeadCount();

    let onAssignedFacilityPapers = this.myFacilityPaperCountService.onMyFacilityPaperCountChange
      .subscribe((countStr: any) => {
        setTimeout(() => {
          let countHtml = document.getElementById("assigned_facility_paper_count")
          if (countHtml) {
            countHtml.innerHTML = countStr
          }
        }, 1000)
      });

    this.unsubscribe.push(onAssignedFacilityPapers);
    this.myFacilityPaperCountService.getLoggedUserFacilityPaperCount();
  }

  ngOnDestroy() {
    this.unsubscribe.forEach(sb => sb.unsubscribe());
    this.userIdleSubs.unsubscribe();
  }

}
