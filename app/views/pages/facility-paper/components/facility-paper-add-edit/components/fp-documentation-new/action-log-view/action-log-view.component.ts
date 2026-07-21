import { Component, OnInit } from "@angular/core";
import * as _ from "lodash";
import * as moment from "moment";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { SDConstants } from "../utils";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-action-log-view",
  templateUrl: "./action-log-view.component.html",
  styleUrls: ["./action-log-view.component.scss"],
})
export class ActionLogViewComponent implements OnInit {
  modalRef: MDBModalRef;
  action: Subject<any> = new Subject<any>();
  content: any = {};

  selectedDocumentElement: any;
  securityDocumentHistoryActionList: any[] = [];
  constructor(public mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    if (this.content.securityDocumentHistoryDTOList !== null) {
      this.createActionHistoryLog(this.content.securityDocumentHistoryDTOList);
    }

    this.selectedDocumentElement = {
      ...this.content.selectedDocumentElement,
      documentName: this.content.selectedDocumentElement.documentFileName
        ? this.content.selectedDocumentElement.documentFileName.replaceAll(
            "-",
            " "
          )
        : "",
    };
  }

  createActionHistoryLog(securityDocumentHistoryDTOList: any[]) {
    let user: string = "";
    let branchDept: string = "";
    let actionDate: string = "";
    let action: string = "";
    let comment: string = "";
    let documentName: string = "";
    let documentStatus: string = "";

    _.forEach(securityDocumentHistoryDTOList, (ad) => {
      documentName = ad.documentName;
      documentStatus = ad.documentStatus;

      if (
        ad.documentStatus == SDConstants.documentStatusConst.APPROVE &&
        ad.printedBy != null
      ) {
        user = ad.printedByDisplayName;
        branchDept = ad.printedByDiv;
        actionDate = ad.printedDateStr;
        comment = "";
        action = "Printed by " + user;
        documentStatus = "PRINT";

        const actionTime = moment(actionDate, "DD/MM/YYYY HH:mm:ss").valueOf();

        this.securityDocumentHistoryActionList.push({
          documentName: documentName,
          user: user,
          branchDept: branchDept,
          actionDate: actionDate,
          action: action,
          comment: comment,
          documentStatus: documentStatus,
          actionTime: actionTime,
        });
      } else {
        switch (ad.documentStatus) {
          case SDConstants.documentStatusConst.DRAFT:
            user = ad.savedByDisplayName;
            branchDept = ad.savedByDiv;
            actionDate = ad.savedDateStr;
            comment = ad.actionComment ? ad.actionComment : "";
            action = "Drafted by " + user;
            break;
          case SDConstants.documentStatusConst.SUBMIT:
            user = ad.savedByDisplayName;
            branchDept = ad.savedByDiv;
            actionDate = ad.savedDateStr;
            comment = "";
            action = "Submitted by " + user;
            break;
          case "DELETE":
            user = ad.savedByDisplayName;
            branchDept = ad.savedByDiv;
            actionDate = ad.savedDateStr;
            comment = "";
            action = "Deleted by " + user;
            break;
          case SDConstants.documentStatusConst.APPROVE:
            user = ad.authByDisplayName;
            branchDept = ad.authByDiv;
            actionDate = ad.authDateStr;
            comment = "";
            action = "Recommended & Forwarded by " + user;
            break;
          case SDConstants.documentStatusConst.RETURN:
            user = this.isReccomandReturn(ad)
              ? ad.recommendedReturnDisplayName
              : ad.authByDisplayName;
            branchDept = this.isReccomandReturn(ad) ? "" : ad.authByDiv;
            actionDate = this.isReccomandReturn(ad)
              ? ad.recommendedReturnDateStr
              : ad.authDateStr;
            comment = ad.actionComment;
            action = "Returned by " + user;
            break;
        }

        const actionTime = moment(actionDate, "DD/MM/YYYY HH:mm:ss").valueOf();
        this.securityDocumentHistoryActionList.push({
          documentName: documentName,
          user: user,
          branchDept: branchDept,
          actionDate: actionDate,
          action: action,
          comment: comment,
          documentStatus: documentStatus,
          actionTime: actionTime,
        });
      }
    });

    this.securityDocumentHistoryActionList =
      this.securityDocumentHistoryActionList.sort(
        (a: any, b: any) => a.actionTime - b.actionTime
      );
  }

  isReccomandReturn(data: any) {
    return data.isRecommendedReturn === Constants.yesNoConst.Y;
  }
}
