import { Component, OnInit } from "@angular/core";
import { MenuConfig } from "../../core/setting/menu.config";
import { DataService } from "src/app/core/service/data/data.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { BehaviorSubject } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { ApplicationService } from "src/app/core/service/application/application.service";

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.scss"],
})
export class SidebarComponent implements OnInit {
  navItems: any = [];
  onExternalDataChange: BehaviorSubject<any> = new BehaviorSubject({});
  onUserApplicationDataChange: BehaviorSubject<any> = new BehaviorSubject({});
  loggedInWC: number = 0;
  userActiveApplications: any = [];
  constructor(
    private dataService: DataService,
    private alertService: AlertService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.loggedInWC = this.applicationService.getLoggedInUserUPMGroupCode()
      ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
      : 0;
    this.navItems = new MenuConfig().model;
    this.getExternalSiteDetails().then((res: any) => {
      if (
        this.loggedInWC != 81 &&
        this.loggedInWC != 82 &&
        this.loggedInWC != 64
      ) {
        this.getUserApplicationDetails().then((res: any[]) => {
          const activeApplications =
            res !== null && res.length > 0
              ? res.filter((app: any) => app.applicationShortName !== "CAS")
              : [];

          const externalSitesNavItem = {
            id: "external-sites",
            title: "External Sites",
            type: "group",
            icon: "fas fa-window-restore",
            isExternal: true,
            children: [
              {
                id: "external-sites-risk-web",
                title: "Risk Web",
                type: "item",
                icon: "fas fa-th-list",
                onClick: () => this.onClickRiskWeb(),
              },
            ],
          };

          activeApplications.forEach((app: any) => {
            externalSitesNavItem.children.push({
              id: app.applicationId,
              title: app.applicationCodeWithStatus,
              type: "item",
              icon: "fas fa-th-list",
              onClick: () =>
                this.onClickUserApplicationDetails(app.url, app.activeStatus),
            });
          });
          this.navItems.push(externalSitesNavItem);
        });
      }

      if (this.loggedInWC == 81 || this.loggedInWC == 82) {
        this.navItems.push({
          id: "facility-paper-search",
          title: "Facility Paper Search",
          type: "item",
          icon: "fas fa-search",
          url: "/facility-paper/search",
        });
      }
    });
  }

  onClickRiskWeb() {
    this.onExternalDataChange.subscribe((response: any) => {
      if (response && response["enable"]) {
        window.open(response["url"], "_blank");
      } else {
        this.alertService.showToaster(
          "Please contact system administrator",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    });
  }

  onClickUserApplicationDetails(url: string, activeStatus: string) {
    if (url && activeStatus === "Active") {
      window.open(url, "_blank");
    } else if (activeStatus === "Not Available") {
      this.alertService.showToaster(
        "You do not have permission to view this application. Please contact the system administrator.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    } else {
      this.alertService.showToaster(
        "URL is not available. Please contact the system administrator.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  getExternalSiteDetails() {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getExternalSiteDetails)
        .subscribe((response: any) => {
          this.onExternalDataChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  getUserApplicationDetails() {
    return new Promise<any>((resolve, reject) => {
      this.dataService
        .get(SETTINGS.ENDPOINTS.getUserApplicationDetails)
        .subscribe((response: any) => {
          this.onUserApplicationDataChange.next(response);
          resolve(response);
        }, reject);
    });
  }

  isGroup(item: any) {
    return item.type === "group";
  }

  isItem(item: any) {
    return item.type === "item";
  }
}
