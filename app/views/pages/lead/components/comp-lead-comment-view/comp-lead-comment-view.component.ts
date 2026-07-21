import { Component, Input, OnInit } from "@angular/core";
import * as _ from "lodash";
import * as moment from "moment";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-comp-lead-comment-view",
  templateUrl: "./comp-lead-comment-view.component.html",
  styleUrls: ["./comp-lead-comment-view.component.scss"],
})
export class CompLeadCommentViewComponent implements OnInit {
  @Input() comments: any[] = [];
  @Input() allBranches: any[] = [];
  constructor() {}

  ngOnInit() {
    this.comments = this.comments.map((c: any) => ({
      ...c,
      createdDate:
        c.createdDate !== null
          ? moment(c.createdDate).format("Do MMMM YYYY")
          : "-",
    }));
  }

  getBranchDepartmentName(branchCode: string) {
    let branch = AppUtils.getBranchFromBranchCode(this.allBranches, branchCode);

    if (!_.isEmpty(branch)) {
      return branch.branchName;
    }
    return branchCode;
  }
}
