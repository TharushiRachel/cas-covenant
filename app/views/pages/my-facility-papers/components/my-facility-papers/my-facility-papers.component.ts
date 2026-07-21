import {
  Component,
  OnDestroy,
  OnInit,
  ElementRef,
  HostListener,
  Renderer2,
  ViewChild,
  ChangeDetectorRef,
} from "@angular/core";
import { Constants } from "../../../../../core/setting/constants";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { PageSize } from "../../../../../core/dto/page.size";
import { FormBuilder } from "@angular/forms";
import { Subscription } from "rxjs/Rx";
import { SearchDataCacheService } from "../../../../../core/service/common/search-data-cache.service";
import { UrlEncodeService } from "../../../../../core/service/application/url-encode.service";
import { Router } from "@angular/router";
import { Pagination } from "../../../../../core/dto/pagination";
import { ApplicationService } from "../../../../../core/service/application/application.service";
import { MyFacilityPaperCountService } from "../../../../../core/service/facility-paper/my-facility-paper-count.service";
import { MyFacilityPapersService } from "../../services/my-facility-paper.service";
import { AppUtils } from "../../../../../shared/app.utils";
import { isEmpty } from "lodash";
import { CacheService } from "../../../../../core/service/data/cache.service";
import { FacilityPaperAddEditService } from "../../../facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-my-facility-papers",
  templateUrl: "./my-facility-papers.component.html",
  styleUrls: ["./my-facility-papers.component.scss"],
})
export class MyFacilityPapersComponent implements OnInit, OnDestroy {
  @ViewChild("dynamicScrollDiv", { read: ElementRef, static: false })
  elementRef!: ElementRef;

  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutigStatus = Constants.facilityRoutingStatus;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityPaperReviewStatusConst = Constants.paperReviewStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_INQUIRY_TYPE)
  selectedCommitteeInquiryType;

  @LocalStorage(SETTINGS.STORAGE.FACILITY_PAPER_BY_ID)
  facilityPaperByIDT;

  @LocalStorage(SETTINGS.STORAGE.RISK_DIV_CODE)
  riskDivCode;

  uniquePageName = "MyFacilityPapersComponent-#ayergses656";

  pageSize = new PageSize({ pageSize: 25 });

  isSearchTriggered;
  selectedFacilityPapers: any = [];
  searchData: any = {};
  onSelectFacilityPaperChangeSub = new Subscription();
  onFacilityPaperCountsChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();
  userName = "";
  status: String = this.facilityPaperStatusConst.DRAFT;
  dateHeading: String = "Received On";
  isAgent = true;
  exHeight = 321;
  currentHeight: number = 0;
  @HostListener("window:resize", ["$event"])
  onResize(event: Event): void {
    this.updateMaxHeight();
  }

  facilityPaperCounts: any = {
    draftFacilityPaper: 0,
    inProgressFacilityPaper: 0,
    pendingFacilityPaper: 0,
    approvedFacilityPaper: 0,
    rejectedFacilityPaper: 0,
    cancelFacilityPaper: 0,
    reviewRejectedPaper: 0,
  };

  allBankOptions: any = {};
  optionsSelect: any = [
    {
      value: this.facilityPaperStatusConst.IN_PROGRESS,
      label: this.facilityPaperStatus.IN_PROGRESS,
    },
    {
      value: this.facilityPaperStatusConst.REJECTED,
      label: this.facilityPaperStatus.REJECTED,
    },
    {
      value: this.facilityPaperStatusConst.APPROVED,
      label: this.facilityPaperStatus.APPROVED,
    },
    { value: this.facilityPaperStatusConst.DRAFT, label: "Inbox" },
    {
      value: this.facilityPaperStatusConst.CANCEL,
      label: this.facilityPaperStatus.CANCEL,
    },
  ];

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  constructor(
    private facilityPapersService: MyFacilityPapersService,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private applicationService: ApplicationService,
    private myFacilityPaperCountService: MyFacilityPaperCountService,
    private cacheService: CacheService,
    private el: ElementRef,
    private renderer: Renderer2,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.myFacilityPaperCountService.getLoggedUserFacilityPaperCount();

    this.userName = this.applicationService.getLoggedInUserUserName() || "";
    this.isAgent = this.applicationService.isAgent();
    this.onSelectFacilityPaperChangeSub =
      this.facilityPapersService.onSelectedFacilityPaperChange.subscribe(
        (data) => {
          this.isSearchTriggered = true;
          this.selectedFacilityPapers =
            this.facilityPapersService.selectedFacilityPapers;
          this.pageSize.length = data.totalNoOfRecords;
          this.pageSize.pageIndex = data.currentPageNo - 1;
        }
      );

    this.onFacilityPaperCountsChangeSub =
      this.facilityPapersService.onFacilityPaperCountsChange.subscribe(
        (data) => {
          this.facilityPaperCounts = data;
          this.loadInitiateDashboardPage();
        }
      );
  }

  ngOnDestroy(): void {
    this.onSearchFormChangeSub.unsubscribe();
    this.onSelectFacilityPaperChangeSub.unsubscribe();
    this.onFacilityPaperCountsChangeSub.unsubscribe();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPapersService.searchFacilityPapers(
      {
        currentAssignUser: this.applicationService.getLoggedInUserUserName(),
        intiatedUserName: this.applicationService.getLoggedInUserUserName(),
        loggedInUserBranchCode:
          this.applicationService.getLoggedInUserDivCode(),
        loginUpmAccessLevel:
          this.applicationService.getLoggedInUserUPMGroupCode(),
        facilityPaperStatus: this.status,
      },
      new Pagination(event)
    );
  }

  loadFacilityPaper(facilityPaperID, committeeType) {
    this.selectedFacilityPaperID =
      this.urlEncodeService.encode(facilityPaperID);
    this.selectedCommitteeInquiryType =
      this.urlEncodeService.encode(committeeType);
    // this.facilityPaperAddEditService.getFacilityPaperByIDT().then((data1: any) =>{
    //   // this.facilityPaperByIDT = this.urlEncodeService.encode(data1);
    //   this.facilityPaperByIDT = data1;
    // });

    // this.facilityPaperAddEditService.getRiskDivCode().then((data2: any) =>{
    //   // this.riskDivCode = this.urlEncodeService.encode(data2);
    //   this.riskDivCode = data2;
    // });
    //console.log("triggered")
    this.router.navigate(["/facility-paper/edit"]);
  }

  getColor(facilityStatus) {
    switch (facilityStatus) {
      case this.facilityPaperStatusConst.IN_PROGRESS:
        return "light-blue";
      case this.facilityPaperStatusConst.APPROVED:
        return "pink";
      case this.facilityPaperStatusConst.CANCEL:
        return "orange";
      case this.facilityPaperStatusConst.REJECTED:
        return "amber";
      case this.facilityPaperStatusConst.DRAFT:
        return "mdb-color";
      default:
        return "cyan";
    }
  }

  loadInitiateDashboardPage() {
    this.facilityPapersService
      .getFacilityPapers(
        {
          currentAssignUser: this.userName,
          intiatedUserName: this.userName,
          loggedInUserBranchCode:
            this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          facilityPaperStatus: this.facilityPaperStatusConst.DRAFT,
        },
        new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0,
        })
      )
      .then((data: any) => {
        this.cdr.detectChanges();
        this.updateMaxHeight();
      });
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    let branch = AppUtils.getBranchFromBranchCode(
      this.allBankOptions,
      branchCode
    );

    if (!isEmpty(branch)) {
      return branch.branchName + " - " + branch.branchCode;
    }
    return branchCode;
  }

  loadPageData(fpStatus) {
    let UserName = this.applicationService.getLoggedInUserUserName();

    switch (fpStatus) {
      case this.facilityPaperStatusConst.DRAFT:
      case this.facilityPaperStatusConst.IN_PROGRESS: {
        this.dateHeading = "Received On";
        break;
      }

      case this.facilityPaperStatusConst.APPROVED: {
        this.dateHeading = "Approved On";
        break;
      }

      case this.facilityPaperStatusConst.CANCEL: {
        this.dateHeading = "Returned On";
        break;
      }

      case this.facilityPaperStatusConst.REJECTED: {
        this.dateHeading = "Declined On";
        break;
      }

      default: {
        this.dateHeading = "Received On";
      }
    }

    this.status = fpStatus;

    this.facilityPapersService
      .getFacilityPapers(
        {
          currentAssignUser: UserName,
          intiatedUserName: UserName,
          loggedInUserBranchCode:
            this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          facilityPaperStatus: fpStatus,
        },
        new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0,
        })
      )
      .then((data: any) => {
        this.cdr.detectChanges();
        this.updateMaxHeight();
      });
  }

  loadCustomer360() {
    this.router.navigate(["/customer-360"]);
  }

  private removeScrollStyles() {
    this.renderer.removeStyle(
      this.el.nativeElement.querySelector(".scroll-view"),
      "overflow-y"
    );
    this.renderer.removeStyle(
      this.el.nativeElement.querySelector(".scroll-view"),
      "overflow-x"
    );
    this.renderer.removeStyle(
      this.el.nativeElement.querySelector(".scroll-view"),
      "overflow"
    );
    this.renderer.removeStyle(
      this.el.nativeElement.querySelector(".scroll-view"),
      "max-height"
    );
  }

  private updateMaxHeight() {
    const scrHeight = window.innerHeight - this.exHeight;

    if (this.elementRef) {
      this.removeScrollStyles();
      this.currentHeight = this.elementRef.nativeElement.offsetHeight;
      this.renderer.setStyle(
        this.el.nativeElement.querySelector(".scroll-view"),
        "max-height",
        `${scrHeight}px`
      );
      if (this.currentHeight < scrHeight) {
        this.renderer.setStyle(
          this.el.nativeElement.querySelector(".scroll-view"),
          "overflow-y",
          "auto"
        );
      } else {
        this.renderer.setStyle(
          this.el.nativeElement.querySelector(".scroll-view"),
          "overflow",
          "scroll"
        );
        this.renderer.setStyle(
          this.el.nativeElement.querySelector(".scroll-view"),
          "overflow-x",
          "hidden"
        );
      }
    }
  }

  isESGPaper(item: any) {
    return item && item.isESGPaper === Constants.yesNoConst.Y;
  }
}
