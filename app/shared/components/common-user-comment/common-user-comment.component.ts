import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ApplicationService } from "../../../core/service/application/application.service";
import { AppUtils } from "../../app.utils";
import * as _ from "lodash";
import { DateService } from "../../../core/service/application/date.service";
import { Constants } from "../../../core/setting/constants";
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { PrivilegeService } from 'src/app/core/service/authentication/privilege.service';

@Component({
  selector: 'app-common-user-comment',
  templateUrl: './common-user-comment.component.html',
  styleUrls: ['./common-user-comment.component.scss']
})
export class CommonUserCommentComponent implements OnInit {
  @Input() comments: any[] = [];
  @Input() isAbleToAddEdit = false;
  @Input() branchList = [];
  @Input() modifiedDateStr: any;
  @Output("updateComment") updateComment: EventEmitter<any> = new EventEmitter();
  tableColumns = ['User', 'Date', 'Action', 'Comment'];

  hasPrivilegeToViewBCCPapers: boolean = false;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  isLegalOfficer: boolean = false;

  constructor(private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private dateService: DateService) {
  }

  ngOnInit() {
    this.hasPrivilegeToViewBCCPapers = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_VIEW_BCC_PAPER
    );

    this.isLegalOfficer = this.applicationService.getLoggedInUserUPMGroupCode() && this.applicationService.getLoggedInUserUPMGroupCode() == Constants.committeeSignatureUsers.LO

  }

  isAbleToUpdate(comment) {
    return this.applicationService.getLoggedInUserUserID() == comment.createdUserID && this.dateService.isSameOrBeforeDateTime(this.modifiedDateStr, comment.createdDateStr);
  }

  viewComment(comment) {
    // console.log("this.applicationService",this.applicationService);
    // console.log("this.SDE",Constants.applicationSecurityWorkClass.SDE);
    //  console.log("this.SDA",Constants.applicationSecurityWorkClass.SDA);
    //  console.log("createdUserUpmCode",comment.createdUserUpmCode);
    //  console.log("comment",comment);


    if (comment.createdUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else if (comment.isUsersOnly === 'Y' && this.applicationService.getLoggedInUserUserID() == comment.assignedUserID) {
      return true;
    } else if (comment.isDivisionOnly === 'Y' && (this.applicationService.getLoggedInUserDivCode() == comment.createdUserDivCode || this.applicationService.getLoggedInUserDivCode() == comment.assignedUserDivCode)) {
      return true;
    } else {
      if (comment.createdUserUpmCode == Constants.applicationSecurityWorkClass.SDA ||
        comment.createdUserUpmCode == Constants.applicationSecurityWorkClass.SDE) {
        if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.SDA ||
          this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.SDE) {
          return true;
        } else {
          return false;
        }
      } else {
        return comment.isPublic === 'Y';
      }
    }
  }

  update(comment) {
    this.updateComment.emit(comment);
  }

  getBranchDepartmentName(branchCode) {
    let branch = AppUtils.getBranchFromBranchCode(this.branchList, branchCode);

    if (!_.isEmpty(branch)) {
      return branch.branchName;
    }
    return branchCode;
  }

  getActionMessage(comment) {
    if (!_.isEmpty(comment.actionMessage)) {
      if (comment.actionMessage.length > 120) {
        return comment.actionMessage.substring(0, 100).concat("...");
      } else {
        return comment.actionMessage;
      }
    }
    return ""
  }

  getActionMessageToolTip(comment) {
    if (!_.isEmpty(comment.actionMessage)) {
      if (comment.actionMessage.length > 120) {
        return comment.actionMessage;
      } else {
        return null;
      }
    }
    return null;
  }

}
