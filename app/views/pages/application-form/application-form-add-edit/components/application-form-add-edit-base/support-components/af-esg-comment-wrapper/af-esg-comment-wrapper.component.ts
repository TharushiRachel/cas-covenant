import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationFormAddEditService } from "../../../../services/application-form-add-edit.service";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import * as moment from "moment";

@Component({
  selector: "app-af-esg-comment-wrapper",
  templateUrl: "./af-esg-comment-wrapper.component.html",
  styleUrls: ["./af-esg-comment-wrapper.component.scss"],
})
export class AfEsgCommentWrapperComponent implements OnInit, OnDestroy {
  applicationForm: any;
  opinions: any[] = [];
  onESGRiskOpinionChangeSub = new Subscription();
  onApplicationFormChangeSub = new Subscription();
  selectedTabIndex: number = 0;

  @Output("addEditOpinion") addEditOpinion = new EventEmitter();
  @Output("addEditReply") addEditReply = new EventEmitter();
  @Output("handleDeleteOpinion") handleDeleteOpinion = new EventEmitter();

  constructor(
    private readonly applicationService: ApplicationService,
    private readonly applicationFormAddEditService: ApplicationFormAddEditService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.onApplicationFormChangeSub =
      this.applicationFormAddEditService.onApplicationFormChange.subscribe(
        (data: any) => {
          if (data) {
            this.applicationForm = data;
          }
        }
      );

    this.onESGRiskOpinionChangeSub =
      this.applicationFormAddEditService.onESGRiskOpinionChange.subscribe(
        (res: any[]) => {
          this.opinions = this.sortOpinions(res);
          this.selectedTabIndex = 0;
          this.cdr.detectChanges();
        }
      );
  }

  ngOnDestroy(): void {
    this.onESGRiskOpinionChangeSub.unsubscribe();
    this.onApplicationFormChangeSub.unsubscribe();
  }

  sortOpinions(dataList: any[]) {
    dataList.sort(
      (a: { riskOpinionId: number }, b: { riskOpinionId: number }) =>
        b.riskOpinionId - a.riskOpinionId
    );
    return dataList.map((d: any, i: number) => ({
      ...d,
      tabTitle:
        i == 0
          ? "Current ESG Risk Opinion and Reply"
          : `History ESG Risk Opinion by ${moment(d.createdDate).format(
              "MMMM Do YYYY"
            )}`,
      isActive: i == 0 && d.riskOpinionReply == null,
      isReplyEditable: i == 0 && d.riskOpinionReply !== null,
    }));
  }

  onTabSelect($event: any) {
    this.selectedTabIndex = $event;
  }

  editOpinion() {
    let selectedItem: any = this.opinions.find(
      (d: any, i: number) => i === this.selectedTabIndex
    );
    if (selectedItem && this.selectedTabIndex === 0) {
      this.addEditOpinion.emit(selectedItem);
    }
  }

  handleReply(item?: any) {
    if (item) {
      this.addEditReply.emit(item);
    } else {
      let selectedItem: any = this.opinions.find(
        (d: any, i: number) => i === this.selectedTabIndex
      );
      if (selectedItem) {
        let riskOpinionReply: any = selectedItem.riskOpinionReply;
        let reply: any = {
          riskReplyId:
            riskOpinionReply && riskOpinionReply.riskReplyId
              ? riskOpinionReply.riskReplyId
              : 0,
          riskOpinionId: selectedItem.riskOpinionId
            ? selectedItem.riskOpinionId
            : 0,
          reply:
            riskOpinionReply && riskOpinionReply.reply
              ? riskOpinionReply.reply
              : "",
        };
        this.addEditReply.emit(reply);
      }
    }
  }

  showEditOpinionButton(item: any) {
    return (
      this.isAllowedPaper() &&
      this.isAllowedUser() &&
      this.isRiskUser() &&
      item.isActive &&
      this.applicationForm.isESGApproved === Constants.yesNoConst.N
    );
  }

  showAddReplyButton(item: any) {
    return (
      this.isAllowedPaper() &&
      this.isAllowedUser() &&
      !this.isRiskUser() &&
      item.isActive &&
      this.applicationForm.isESGApproved === Constants.yesNoConst.N
    );
  }

  showEditReplyButton(item: any) {
    return (
      this.isAllowedPaper() &&
      this.isAllowedUser() &&
      !this.isRiskUser() &&
      item.isReplyEditable &&
      this.applicationForm.isESGApproved === Constants.yesNoConst.N
    );
  }

  isRiskUser() {
    let loggedInUserDiv: string = this.applicationService
      .getLoggedInUserDivCode()
      .toString();
    return loggedInUserDiv === SETTINGS.ESG_DIV_CODE.toString();
  }

  isAllowedPaper() {
    return (
      this.applicationForm.currentApplicationFormStatus !==
        Constants.applicationFormCurrentStatusConst.PAPER_CREATED &&
      this.applicationForm.currentApplicationFormStatus !==
        Constants.applicationFormCurrentStatusConst.DECLINED
    );
  }

  isAllowedUser() {
    return (
      this.applicationForm.assignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
  }

  handleDelete(item: any) {
    if (item !== null) {
      this.handleDeleteOpinion.next(item);
    }
  }

  getActionBy(item: any) {
    if (item.modifiedBy !== null && item.modifiedDate !== null) {
      return `Modified By ${item.modifiedBy} on ${moment(
        item.modifiedDate
      ).format("MMM Do YYYY, h:mm:ss a")}`;
    }
    return `Added By ${item.createdBy} on ${moment(item.createdDate).format(
      "MMM Do YYYY, h:mm:ss a"
    )}`;
  }
}
