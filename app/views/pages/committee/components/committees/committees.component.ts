import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Constants } from "src/app/core/setting/constants";
import { CommitteeService } from "../../service/committee.service";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { PageSize } from "src/app/core/dto/page.size";

@Component({
  selector: "app-committees",
  templateUrl: "./committees.component.html",
  styleUrls: ["./committees.component.scss"],
})
export class CommitteesComponent implements OnInit {
  tableColumns: any = [
    "Committee Name",
    "Committee Type",
    "Status",
    "Approve Status",
    "Committee Status",
  ];
  committes: any[] = [];
  pendingCommittes: any[] = [];
  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;
  committeeStatus = Constants.committeeStatus;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_ID)
  selectedCommitteeId;
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_FETCH_TYPE)
  selectedCommitteeFetchType;

  pageSize = new PageSize();
  
  constructor(
    private router: Router,
    private urlEncodeService: UrlEncodeService,
    private committeeService: CommitteeService
  ) {}

  ngOnInit() {
    this.getAllCommitte();
  }

  loadSaveCommittee(committeeId: any, type: any) {
    if (committeeId) {
      this.selectedCommitteeId = this.urlEncodeService.encode(committeeId);
      this.selectedCommitteeFetchType = this.urlEncodeService.encode(type);
    } else {
      this.selectedCommitteeId = null;
      this.selectedCommitteeFetchType = null;
    }

    this.router.navigate(["/committee/add-edit"]);
  }

  getAllCommitte() {
    this.committeeService.getCommittees().then((data:any) => {
      if (data) {
        this.committes = data.filter((d:any)=> d.approveStatus == Constants.approveStatusConst.APPROVED);
        this.pendingCommittes = data.filter((d:any)=> d.approveStatus != Constants.approveStatusConst.APPROVED);
        
        this.pageSize.length = this.committes.length;
        this.pageSize.pageSizeOptions = [10];
      }
    });
  }
  
  getSliceUsers() {
    return this.committes.slice(
      this.pageSize.pageIndex * this.pageSize.pageSize,
      (this.pageSize.pageIndex + 1) * this.pageSize.pageSize
    );
  }

  onPageEvent(event: any) {
    this.pageSize.pageIndex = event.pageIndex;
    this.pageSize.pageSize = event.pageSize;
  }
}
