import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Constants } from "src/app/core/setting/constants";
import { CommitteeService } from "../../service/committee.service";
import { BehaviorSubject } from "rxjs";

@Component({
  selector: "app-committee-type",
  templateUrl: "./committee-type.component.html",
  styleUrls: ["./committee-type.component.scss"],
})
export class CommitteeTypeComponent implements OnInit {
  tableColumns: any = ["Type Name", "Description", "Status"];
  types: any = [];
  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  constructor(
    private router: Router,
    private committeeService: CommitteeService
  ) {}

  ngOnInit() {
    this.getAllTypes();
  }

  loadSaveType(item: any) {
  if(item == null){
  this.router.navigate(["/committee/type-add-edit"]);
  }else{
   if (item.isSystem == 0) {
        this.committeeService.subcribeTypeData(item);

        this.router.navigate(["/committee/type-add-edit"]);
      }
  }

  }

  getAllTypes() {
    this.committeeService.getCommitteeType().then((data: any) => {
      if (data) {
        this.types = data;
      }
    });
  }
}
