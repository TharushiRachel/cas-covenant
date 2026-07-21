import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { CasV1Service } from "../../services/cas-v1.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { BehaviorSubject, Subject } from "rxjs";

@Component({
  selector: "app-comments-details",
  templateUrl: "./comments-details.component.html",
  styleUrls: ["./comments-details.component.scss"],
})
export class CommentsDetailsComponent implements OnInit {
  @Input() refNo: string;
  @Input() paperDate: string;
  commentsDetails: any[] = [];

  @Input("tabLoadedData") tabLoadedData: any;
  @Input("tabLoadingSetting") tabLoadingSetting: any;
  @Output() handleTabLoadingSetting = new EventEmitter<string>();
  @Output() onCmntDataChange = new EventEmitter<any[]>();

  constructor(
    private readonly casV1Service: CasV1Service,
    private readonly alertService: AlertService
  ) {}

  ngOnInit() {
    if (!this.tabLoadingSetting.isCmntLoaded) {
      this.loadComments();
    } else {
      this.commentsDetails = this.tabLoadedData.commentsDetails;
    }
  }

  loadComments(): void {
    if (!this.refNo || !this.paperDate) {
      this.alertService.showToaster(
        "Ref No or Paper Date is missing.",
        "WARNING"
      );
      return;
    }

    this.casV1Service.getComments(this.refNo, this.paperDate).then((data) => {
      this.handleTabLoadingSetting.next("CMNT");
      this.commentsDetails = data
        ? data.sort(
            (a: any, b: any) =>
              new Date(b.remarkDate).getTime() -
              new Date(a.remarkDate).getTime()
          )
        : [];
      this.onCmntDataChange.next(this.commentsDetails);
    });
  }
}
