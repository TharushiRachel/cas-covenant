import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-risk-tree-view",
  templateUrl: "./risk-tree-view.component.html",
  styleUrls: ["./risk-tree-view.component.scss"],
})
export class RiskTreeViewComponent implements OnInit {
  @Input() nodes: any[];
  @Output("addChildRisk") addChildRisk = new EventEmitter();
  @Output("editChildRisk") editChildRisk = new EventEmitter();
  @Output("approveRisk") approveRisk = new EventEmitter();
  @Output("rejectRisk") rejectRisk = new EventEmitter();
  @Output("viewPendingInfo") viewPendingInfo = new EventEmitter();
  @Output("removeRisk") removeRisk = new EventEmitter();

  constructor(private readonly applicationService: ApplicationService) {}

  ngOnInit() {}

  isParent(node: any) {
    return node.type == Constants.riskRecordTypeConst.P;
  }

  isChildExist(node: any) {
    return node.children && node.children.length > 0;
  }

  isDeleteEnabled(node: any) {
    return !node.children || node.children.length <= 0;
  }

  isPendingNode(node: any) {
    return node.approvedStatus == Constants.approveStatusConst.PENDING;
  }

  isInactiveNode(node: any) {
    if (node && node.pendingRec) {
      return node.pendingRec.status == Constants.statusConst.INA;
    }
    return node.status == Constants.statusConst.INA;
  }

  isAuthorizeEnabled(node: any) {
    if (this.isInactiveNode(node) && this.isValidUser(node)) {
      return true;
    } else if (
      this.isPendingNode(node) &&
      this.isValidUser(node) &&
      !node.pendingRec
    ) {
      return true;
    } else {
      return false;
    }
  }

  isValidUser(node: any): boolean {
    node = node.pendingRec ? node.pendingRec : node;
    var isValid: boolean = false;
    let loggedInUserName: string =
      this.applicationService.getLoggedInUserUserName();
    if (node.createdBy && node.modifiedBy) {
      isValid = node.modifiedBy != loggedInUserName;
    } else if (node.createdBy && !node.modifiedBy) {
      isValid = node.createdBy != loggedInUserName;
    }

    return isValid;
  }

  toggle(node: any) {
    node.expanded = !node.expanded;
  }

  handleAdd(node: any) {
    this.addChildRisk.emit(node);
  }

  handleEdit(node: any) {
    this.editChildRisk.emit(node);
  }

  handleApprove(node: any) {
    if (node.pendingRec) {
      this.approveRisk.emit(node.pendingRec);
    } else {
      this.approveRisk.emit(node);
    }
  }

  handleReject(node: any) {
    if (node.pendingRec) {
      this.rejectRisk.emit(node.pendingRec);
    } else {
      this.rejectRisk.emit(node);
    }
  }

  handlePendingInfo(node: any) {
    this.viewPendingInfo.next(node);
  }

  handleRemove(node: any) {
    this.removeRisk.emit(node);
  }
}
