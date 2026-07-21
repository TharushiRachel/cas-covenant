import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CommitteeService } from "../../service/committee.service";
import { Constants } from "src/app/core/setting/constants";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { SaveStatusModalComponent } from "src/app/shared/components/save-status-modal/save-status-modal.component";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { PageSize } from "src/app/core/dto/page.size";

@Component({
  selector: "app-committee-pool",
  templateUrl: "./committee-pool.component.html",
  styleUrls: ["./committee-pool.component.scss"],
})
export class CommitteePoolComponent implements OnInit {
  tableColumns1: any = [
    "Name",
    "Designation",
    "Work Class",
    "Approval Status",
    "Status",
    "Activate/Deactivate",
    "Action",
  ];
  tableColumns2: any = [
    "Name",
    "Designation",
    "Approval Status",
    "Status",
    "Action",
  ];
  poolUsers: any[] = [];
  allPoolUsers: any[] = [];
  pendingPoolUsers: any[] = [];

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  modalRef: MDBModalRef;
  loggedInUserName: any = "";

  pageSize = new PageSize();

  constructor(
    private router: Router,
    private mdbModalService: MDBModalService,
    private committeeService: CommitteeService,
    private applicationService: ApplicationService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.loggedInUserName = this.applicationService.getLoggedInUserUserName();
    this.getPoolUserList();
  }

  loadAddPoolUsers() {
    this.router.navigate(["/committee/pool-add-edit"]);
  }

  getPoolUserList() {
    this.committeeService.getUserPool().then((data: any) => {
      if (data) {
        data.sort((a: any, b: any) => b.groupCode - a.groupCode);
        this.allPoolUsers = data;

        this.poolUsers = data.filter(
          (pu: any) => pu.approveStatus == this.approvedStatusConst.APPROVED
        );

        this.pendingPoolUsers = data
          .filter(
            (pu: any) =>
              pu.approveStatus == this.approvedStatusConst.PENDING ||
              pu.approveStatus == this.approvedStatusConst.PENDING_RMV
          )
          .map((pu: any) => ({
            ...pu,
            isEnabled:
              (pu.approveStatus == Constants.approveStatusConst.PENDING ||
                pu.approveStatus == Constants.approveStatusConst.PENDING_RMV) &&
              this.isValidUser(pu.createdBy, pu.modifiedBy),
          }));

        this.pageSize.length = this.poolUsers.length;
        this.pageSize.pageSizeOptions = [10];
      }
    });
  }

  getSliceUsers() {
    return this.poolUsers.slice(
      this.pageSize.pageIndex * this.pageSize.pageSize,
      (this.pageSize.pageIndex + 1) * this.pageSize.pageSize
    );
  }

  isValidUser(createdBy: any, modifiedBy: any): boolean {
    var isValid: boolean = false;

    if (createdBy && modifiedBy) {
      isValid = modifiedBy != this.loggedInUserName;
    } else if (createdBy && !modifiedBy) {
      isValid = createdBy != this.loggedInUserName;
    }

    return isValid;
  }

  handleStatusChange(e: any, item: any) {
    var currentStatus: any = e.target.checked
      ? this.statusConst.ACT
      : this.statusConst.INA;

    this.poolUsers = this.poolUsers.map((u: any) => ({
      ...u,
      userStatus: u.userId == item.userId ? currentStatus : u.userStatus,
    }));

    this.modalRef = this.mdbModalService.show(SaveStatusModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-md modal-width-50-p",
      containerClass: "",
      animated: false,
      data: {
        status: currentStatus,
        userDisplayName: item.userDisplayName,
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result == 1) {
        let data = {
          ...item,
          parentRecordID: item.userId,
          userStatus: currentStatus,
        };

        this.committeeService.savePoolUserStatus(data).then(
          (res: any) => {
            this.alertService.showToaster(
              "Pool user has been updated successfully.",
              SETTINGS.TOASTER_MESSAGES.success
            );

            setTimeout(() => {
              this.modalRef.hide();
              this.getPoolUserList();
            }, 1500);
          },
          (err: any) => {
            this.alertService.showToaster(
              "Failed to update pool user.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
      } else {
        this.poolUsers = this.allPoolUsers.filter(
          (pu: any) => pu.approveStatus == this.approvedStatusConst.APPROVED
        );

        this.pendingPoolUsers = this.allPoolUsers
          .filter(
            (pu: any) =>
              pu.approveStatus == this.approvedStatusConst.PENDING ||
              pu.approveStatus == this.approvedStatusConst.PENDING_RMV
          )
          .map((pu: any) => ({
            ...pu,
            isEnabled:
              (pu.approveStatus == Constants.approveStatusConst.PENDING ||
                pu.approveStatus == Constants.approveStatusConst.PENDING_RMV) &&
              this.isValidUser(pu.createdBy, pu.modifiedBy),
          }));
      }
    });
  }

  approveOrRejectUser(user: any, status: any) {
    var formData: any = {
      ...user,
      approveStatus: status,
    };
    this.committeeService.approveOrRejectPoolUser(formData).then((res: any) => {
      if (status == this.approvedStatusConst.APPROVED) {
        this.alertService.showToaster(
          "Pool user change has been approved successfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );
      } else {
        this.alertService.showToaster(
          "Pool user change has been rejected successfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );
      }

      this.getPoolUserList();
    });
  }

  deleteUser(user: any) {
    this.modalRef = this.mdbModalService.show(SaveStatusModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-md modal-width-50-p",
      containerClass: "",
      animated: false,
      data: {
        status: this.statusConst.RMV,
        userDisplayName: user.userDisplayName,
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result == 1) {
        let data = {
          ...user,
          parentRecordID: user.userId,
          userStatus: this.statusConst.RMV,
        };

        this.committeeService.savePoolUserStatus(data).then(
          (res: any) => {
            this.alertService.showToaster(
              "Pool user has been deleted successfully.",
              SETTINGS.TOASTER_MESSAGES.success
            );

            setTimeout(() => {
              this.modalRef.hide();
              this.getPoolUserList();
            }, 1500);
          },
          (err: any) => {
            this.alertService.showToaster(
              "Failed to delete pool user.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          }
        );
      }
    });
  }

  onPageEvent(event: any) {
    this.pageSize.pageIndex = event.pageIndex;
    this.pageSize.pageSize = event.pageSize;
  }
}
