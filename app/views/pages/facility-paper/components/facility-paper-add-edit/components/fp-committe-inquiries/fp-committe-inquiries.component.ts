import { Component, Input, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { FpAddCommitteeInquiryComponent } from "./fp-add-committee-inquiry/fp-add-committee-inquiry.component";
import { FpAddInquiryResponseComponent } from "./fp-add-inquiry-response/fp-add-inquiry-response.component";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Constants } from "src/app/core/setting/constants";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { Observable, Subject } from "rxjs";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Component({
  selector: "app-fp-committe-inquiries",
  templateUrl: "./fp-committe-inquiries.component.html",
  styleUrls: ["./fp-committe-inquiries.component.scss"],
})
export class FpCommitteInquiriesComponent implements OnInit {
  @Input("facilityPaper") facilityPaper: any;
  editMode: boolean = false;
  facilityPaperID: any;
  modalRef: MDBModalRef;
  inquiries: any[] = [];
  inquiryConst: any = Constants.inquiryStatusConst;
  inquiryStatus: any = Constants.inquiryStatus;
  statusConst: any = Constants.statusConst;
  isMember: boolean = null;
  action: Subject<any> = new Subject<any>();

  constructor(
    private mdbModalService: MDBModalService,
    private alertService: AlertService,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private mdbModalRef: MDBModalRef,
  ) { }

  ngOnInit(): void {
    this.facilityPaperID = this.facilityPaper.facilityPaperID;
    this.isCommitteeMember();
    this.loadInquiries();
  }


  private loadInquiries(): void {
    this.facilityPaperAddEditService
      .getCommitteeInquiryByFacilityPaperId(this.facilityPaperID)
      .subscribe({
        next: (res: any[]) => {
          if (res && res.length > 0) {
            const loggedInUser = this.applicationService.getLoggedInUserUserName();

            const mapped = res.map((r: any) => this.mapInquiries(r));

            this.inquiries = mapped
              .filter(function (r: any) {
                if (!r) { return false; }

                //inactive check
                if (r.status === this.statusConst.INA) { return false; }

                const logged = loggedInUser;

                //  hide inquiries still in draft unless you are the creator
                if (r.inquiryStatus === this.inquiryStatus.REQ_DRAFTED) {
                  return r.createdBy === logged;
                }

                //  hide drafted responses unless you are the creator
                if (r.response && r.response.inquiryStatus === this.inquiryStatus.RES_DRAFTED && r.response.createdBy !== logged) {
                  return false;
                }

                //confirmed responses → visible to everyone
                if (r.inquiryStatus === this.inquiryStatus.RES_CONFIRMED ||
                  (r.response && r.response.inquiryStatus === this.inquiryStatus.RES_CONFIRMED)) {
                  return true;
                }


                const topAssigned = r.assignUser === logged;
                const creator = r.createdBy === logged;
                const anyChildAssigned = (r.committeeInqReqResDTOList || [])
                  .some(function (dto: any) {
                    return dto &&
                      dto.assignUser === logged &&
                      dto.status !== this.statusConst.INA &&
                      dto.inquiryStatus !== this.inquiryStatus.RES_DRAFTED; // exclude drafted
                  }, this);

                return topAssigned || creator || anyChildAssigned || this.isMember;
              }, this)

              //latest on the top of inqiury list
              .sort(function (a: any, b: any) {
                function getLatestActivity(inq: any) {
                  const inquiryTime = (inq && inq.createdAt) ? new Date(inq.createdAt).getTime() : 0;
                  const responseTime = (inq && inq.response && inq.response.createdDate)
                    ? new Date(inq.response.createdDate).getTime()
                    : 0;

                  // also consider all child DTOs (responses/forwards)
                  const childTimes = (inq && inq.committeeInqReqResDTOList)
                    ? inq.committeeInqReqResDTOList
                      .filter(function (dto: any) { return dto && dto.createdDate; })
                      .map(function (dto: any) { return new Date(dto.createdDate).getTime(); })
                    : [];

                  return Math.max(inquiryTime, responseTime, ...childTimes);
                }

                return getLatestActivity(b) - getLatestActivity(a);
              });
          } else {
            this.inquiries = [];
            this.alertService.showToaster(SETTINGS.TOASTER_MESSAGES.warning, "No inquiries found for the given Facility Paper ID.");
          }
        },
        error: (err) => {
          this.alertService.showToaster(SETTINGS.TOASTER_MESSAGES.error, "Failed to load inquiries:");
          this.inquiries = [];
        },
      });
  }

  mapInquiries(data: any) {
    if (!data) { return data; }

    const dtoList = (data.committeeInqReqResDTOList || []).filter((d: any) => d && d.status !== this.statusConst.INA);

    const reqRow = dtoList.find((r: any) => r.inquiryRequestResponseType === 'REQ');
    data.comment = reqRow ? reqRow.inquiryRequestResponseText1 : '';
    data.createdAt = reqRow ? reqRow.createdDate : null;
    data.createdBy = reqRow ? reqRow.createdBy : null;

    const responses = dtoList
      .filter((r: any) => r.inquiryRequestResponseType === 'RES')
      .sort((a: any, b: any) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime());

    data.committeeInqReqResDTOList = dtoList;
    data.response = responses.length ? responses[0] : null;

    // latest forward info (only if not confirmed)
    data.latestForwardInfo = null;
    if (responses.length) {
      const latestRes = responses[0];
      if (latestRes.inquiryStatus !== this.inquiryStatus.RES_CONFIRMED) {
        data.latestForwardInfo = {
          createdBy: latestRes.createdUserDisplayName || latestRes.createdBy,
          assignedTo: latestRes.assignUserDisplayName || latestRes.assignUser,
          createdDate: latestRes.createdDate,
          displayLabel: `Responded By ${latestRes.createdUserDisplayName || latestRes.createdBy} assigned to ${latestRes.assignUserDisplayName || latestRes.assignUser}`
        };
      }
    }

    // Build audit: include RESPONDED audits + explicit CONFIRMED row as "Reviewed By"
    let auditTrail = (responses || [])
      .flatMap((r: any) => r.committeeInqReqResAuditDTOList || [])
      .filter((a: any) => a.inquiryRequestResponseType === 'RES')
      .sort((a: any, b: any) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime())
      .map((audit: any) => ({
        ...audit,
        displayLabel: `Responded by ${audit.createdUserDisplayName || audit.createdBy}`
      }));

    // If there’s a dedicated CONFIRMED response row, show it first as "Reviewed By"
    const confirmedRes = responses.find((r: any) => r.inquiryStatus === this.inquiryStatus.RES_CONFIRMED);
    if (confirmedRes) {
      //  Exclude confirmed response's own "Responded by" from audit trail
      auditTrail = auditTrail.filter(a => a.inqReqResId !== confirmedRes.inqReqResId);

      auditTrail = [
        {
          inqReqResAuditId: `confirm-${confirmedRes.inqReqResId}`,
          inqReqResId: confirmedRes.inqReqResId,
          inquiryRequestResponseType: 'RES',
          createdBy: confirmedRes.createdBy,
          createdUserDisplayName: confirmedRes.createdUserDisplayName,
          createdDate: confirmedRes.createdDate,
          displayLabel: `Reviewed By ${confirmedRes.createdUserDisplayName || confirmedRes.createdBy}`
        },
        ...auditTrail
      ];
    }

    data.committeeInqReqResAuditDTOList = auditTrail;
    return data;
  }

  openAddCommitteInquires($event: Event): void {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.editMode = false; // add mode
    this.modalRef = this.mdbModalService.show(FpAddCommitteeInquiryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable',
      containerClass: '',
      animated: false,

      data: {
        mode: "inquiry",
        facilityPaperID: this.facilityPaperID,
        facilityPaper: this.facilityPaper,
        isEdit: false,
      },
    });

    this.modalRef.content.action.subscribe((savedInquiry: any) => {
      if (savedInquiry) {
        this.loadInquiries();
      }
    });
  }

  editInquiry(inquiry: any): void {
    this.modalRef = this.mdbModalService.show(FpAddCommitteeInquiryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: false,
      class: 'modal-width-85-p modal-dialog-scrollable',
      data: {
        mode: "inquiry",
        inquiry,
        facilityPaperID: this.facilityPaper.facilityPaperID,
        comment: inquiry ? inquiry.comment : "",
        selectedUser: inquiry ? inquiry.assignUser : null,
        facilityPaper: this.facilityPaper,
        isEdit: true,
      },
    });

    this.modalRef.content.action.subscribe((updated: any) => {
      if (updated) {
        this.loadInquiries();
        this.editMode = false;
      }
    });
  }

  openAddResponse(inquiry: any, response: any): void {
    this.modalRef = this.mdbModalService.show(FpAddCommitteeInquiryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: false,
      class: 'modal-width-85-p modal-dialog-scrollable',
      data: {
        mode: "response",
        facilityPaperID: this.facilityPaperID,
        inquiry,
        facilityPaper: this.facilityPaper,
        isEdit: false,
        comment: response ? response.inquiryRequestResponseText1 : "",

      },
    });

    this.modalRef.content.action.subscribe((newResponse: any) => {
      if (newResponse) {
        this.loadInquiries();
      }
    });
  }

  editResponse(inquiry: any, response: any): void {
    this.modalRef = this.mdbModalService.show(FpAddCommitteeInquiryComponent, {
      class: 'modal-width-85-p modal-dialog-scrollable',
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,

      containerClass: "",
      animated: false,
      data: {
        mode: "response",
        facilityPaperID: this.facilityPaperID,
        comment: response ? response.inquiryRequestResponseText1 : "",
        inquiry,
        response,
        facilityPaper: this.facilityPaper,
        isEdit: true,
        selectedUser: response ? response.assignUser : null,
      },
    });

    this.modalRef.content.action.subscribe((updated: any) => {
      if (updated) {
        this.loadInquiries();
      }
    });
  }

  openConfirmResponse(inquiry: any): void {
    // pick latest active RES
    const activeResponses = (inquiry.committeeInqReqResDTOList || [])
      .filter((r: any) => r && r.inquiryRequestResponseType === 'RES' && r.status !== this.statusConst.INA)
      .sort((a: any, b: any) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime());

    const latestRes = activeResponses[0] || null;

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: false,
      class: "modal-md-50",
      data: {
        heading: "Confirm Response",
        message: `Are you sure you want to confirm the response?`,
      },
    });

    this.modalRef.content.action.subscribe((confirmed: any) => {
      if (!confirmed) return;

      // CREATE a new confirm row; DON'T overwrite/assign
      const request: any = {
        facilityPaperID: this.facilityPaperID,
        committeeInquiryId: 0, // force NEW row
        referenceInquiryId: inquiry.committeeInquiryId, // link to the inquiry thread
        inquiryStatus: Constants.inquiryStatus.RES_CONFIRMED,
        committeeInqReqResDTOList: [
          {
            inquiryRequestResponseType: 'RES',
            // keep last response text (or "")
            inquiryRequestResponseText1: latestRes.inquiryRequestResponseText1 || ''
          }
        ]
        //  do NOT spread getAssignUser() here – confirmation should not reassign
      };

      this.facilityPaperAddEditService
        .saveCommitteeInquiry(request)
        .then(() => this.loadInquiries())
        .catch((err) => console.error('Confirm failed', err));
    });
  }


  saveResponse(status: string, inquiry: any, response?: any) {
    const isConfirm = status === Constants.inquiryStatus.RES_CONFIRMED;

    const request: any = {
      facilityPaperID: this.facilityPaperID,
      committeeInquiryId: isConfirm ? 0 : (response ? response.committeeInquiryId : 0), // on confirm, force NEW row
      referenceInquiryId: inquiry ? inquiry.committeeInquiryId : 0,
      inquiryStatus: status,
      committeeInqReqResDTOList: [
        {
          inquiryRequestResponseType: 'RES',
          inquiryRequestResponseText1: response.inquiryRequestResponseText1 || ''
        }
      ]
    };

    // Only include assignment when drafting/submitting/forwarding. NOT on confirm.
    if (!isConfirm) {
      Object.assign(request, this.getAssignUser());
    }

    this.facilityPaperAddEditService
      .saveCommitteeInquiry(request)
      .then((res) => {
        this.action.next(res);
        this.mdbModalRef.hide();
      })
      .catch((err) => console.error('Save failed:', err));
  }

  //'modal-width-60-p modal-dialog-scrollable'

  getAssignUser() {
    let assignUserData = {
      assignUser: this.applicationService.getLoggedInUserUserName(),
      assignUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      assignUserUpmGroupCode:
        this.applicationService.getLoggedInUserUPMGroupCode(),
    };
    return assignUserData;
  }


  deleteInquiry(inquiry: any): void {
    let responses: any[] = inquiry.committeeInqReqResDTOList.sort(
      (a: any, b: any) => b.inqReqResId - a.inqReqResId
    );
    let response: any = responses && responses.length > 0 ? responses[0] : null;

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: false,
      class: "modal-md-50",
      data: {
        heading: "Delete Inquiry",
        message: `Are you sure you want to confirm the inquiry?`,
      },
    });

    this.modalRef.content.action.subscribe((confirmed: any) => {
      if (confirmed) {
        let request: any = {
          facilityPaperID: this.facilityPaperID,
          committeeInquiryId:
            response !== null ? response.committeeInquiryId : 0,
          status: Constants.statusConst.INA,
          inquiryStatus: Constants.inquiryStatus.REQ_DRAFTED,
        };

        this.facilityPaperAddEditService
          .statusUpdateCommitteeInquiry(request)
          .then((res) => {
            this.loadInquiries();
          })
          .catch((err) => { });
      }
    });
  }

  deleteResponse(inquiry: any, res: any): void {
    let responses: any[] = inquiry.committeeInqReqResDTOList.sort(
      (a: any, b: any) => b.inqReqResId - a.inqReqResId
    );
    let response: any = responses && responses.length > 0 ? responses[0] : null;

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: false,
      class: "modal-md-50",
      data: {
        heading: "Delete Response",
        message: `Are you sure you want to confirm the response?`,
      },
    });

    this.modalRef.content.action.subscribe((confirmed: any) => {
      if (confirmed) {
        let request: any = {
          facilityPaperID: this.facilityPaperID,
          committeeInquiryId:
            response !== null ? response.committeeInquiryId : 0,
          inquiryStatus: Constants.inquiryStatus.RES_DRAFTED,
          status: Constants.statusConst.INA
        };

        this.facilityPaperAddEditService
          .statusUpdateCommitteeInquiry(request)
          .then((res) => {
            this.loadInquiries();
          })
          .catch((err) => { });
      }
    });
  }
  showResponseButton(item: any): boolean {
    const logged = this.applicationService.getLoggedInUserUserName();
    if (!item) return false;

    // If the inquiry itself is still a draft, don't allow responses yet.
    if (item.inquiryStatus === this.inquiryStatus.REQ_DRAFTED) {
      return false;
    }

    // filter out inactive records
    const dtoList = (item.committeeInqReqResDTOList || [])
      .filter((r: any) => r && r.status !== this.statusConst.INA);

    // responses only
    const activeResponses = dtoList.filter(
      (r: any) => r.inquiryRequestResponseType === 'RES'
    );

    // find latest response (prefer createdDate, fallback to id)
    let latestRes: any = null;
    if (activeResponses.length > 0) {
      latestRes = activeResponses.sort((a: any, b: any) => {
        const ta = a.createdDate ? new Date(a.createdDate).getTime() : 0;
        const tb = b.createdDate ? new Date(b.createdDate).getTime() : 0;
        if (tb !== ta) return tb - ta;
        return (b.inqReqResId || 0) - (a.inqReqResId || 0);
      })[0];
    }

    // determine latestAssignedUser (latest response assignUser wins, otherwise top-level)
    let latestAssignedUser = item.assignUser;
    if (latestRes && latestRes.assignUser) {
      latestAssignedUser = latestRes.assignUser;
    }

    // don't show if inquiry closed/confirmed (top-level) OR latest response already confirmed
    if (item.inquiryStatus === this.inquiryStatus.REQ_CONFIRMED ||
      item.inquiryStatus === this.inquiryStatus.REQ_CLOSED) {
      return false;
    }
    if (latestRes && latestRes.inquiryStatus === this.inquiryStatus.RES_CONFIRMED) {
      return false;
    }

    // don't show if the latest response was already created by this user
    if (latestRes && latestRes.createdBy === logged) {
      return false;
    }

    // finally allow only the latest assigned user to add a response
    return latestAssignedUser === logged;
  }



  showConfirmButton(item: any): boolean {
    if (!item || !item.committeeInqReqResDTOList) {
      return false;
    }

    // Get active responses
    const activeResponses = (item.committeeInqReqResDTOList || [])
      .filter((res: any) =>
        res &&
        res.inquiryRequestResponseType === "RES" &&
        res.status !== this.statusConst.INA
      );

    if (activeResponses.length === 0) {
      return false;
    }

    // Find the latest response by createdDate or inqReqResId
    const latestResponse = activeResponses.sort(
      (a: any, b: any) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime()
    )[0];

    // Hide button if latest response is already confirmed and res drafted
    if (latestResponse.inquiryStatus === Constants.inquiryStatus.RES_CONFIRMED || latestResponse.inquiryStatus === Constants.inquiryStatus.RES_DRAFTED) {
      return false;
    }

    const loggedInUser = this.applicationService.getLoggedInUserUserName();
    const loggedInUserCode = this.applicationService.getLoggedInUserUPMGroupCode();


    const allAssignedUsers = activeResponses
      .map((res: any) => res.assignUser)
      .filter((u: any) => !!u);
    // Show only if user is authorized and inquiry is still in "submitted" stage
    return (
      loggedInUserCode >= 71 &&
      item.inquiryStatus === Constants.inquiryStatus.REQ_SUBMITTED &&
      allAssignedUsers.includes(loggedInUser)
    );
  }


  isCommitteeMember(): void {
    this.facilityPaperAddEditService.getCommitteeUsers(this.facilityPaper.facilityPaperID)
      .subscribe({
        next: (res: any[]) => {
          const loggedInUser = this.applicationService.getLoggedInUserUserName();
          this.isMember = (res && res.some(user => user.userName === loggedInUser)) || false;
        },
        error: (err) => {
          this.alertService.showToaster(SETTINGS.TOASTER_MESSAGES.error, "Failed to load committee users:");
          this.isMember = false;
        },
      });
  }


  showCommitteeButton() {
    return this.isMember === true;
  }

  getDraftedResponse(inquiry: any): any {
    if (!inquiry || !inquiry.committeeInqReqResDTOList) {
      return null;
    }

    const responses = inquiry.committeeInqReqResDTOList.filter(
      (res: any) => res.inquiryRequestResponseType === "RES"
    );

    const hasSubmitted = responses.some(
      (res: any) => res.inquiryStatus === Constants.inquiryStatus.RES_SUBMITTED
    );

    if (hasSubmitted) {
      return null;
    }

    return (
      responses.find((res: any) => res.inquiryStatus === Constants.inquiryStatus.RES_DRAFTED) || null
    );
  }

  canShowResponse(inquiry: any): boolean {

    if (!inquiry || !inquiry.response) return false;


    // never show inactive responses
    if (inquiry.response.status === this.statusConst.INA) return false;

    const logged = this.applicationService.getLoggedInUserUserName();

    //  hide drafted response unless current user is the creator
    if (inquiry.response.inquiryStatus === this.inquiryStatus.RES_DRAFTED &&
      inquiry.response.createdBy !== logged) {
      return false;
    }

    // Once confirmed → show to everyone
    if (inquiry.response.inquiryStatus === this.inquiryStatus.RES_CONFIRMED) {
      return true;
    }

    // Before confirmation → restrict visibility
    const resCreator = inquiry.response.createdBy;
    const resAssigned = inquiry.response.assignUser;

    // Allow response creator, assignee, and inquiry owner
    if (logged === resCreator || logged === resAssigned) {
      return true;
    }

    //  If inquiry creator → always sees their own inquiry
    if (inquiry.createdBy && inquiry.createdBy === logged) {
      return true;
    }

    // Committee members cannot see unconfirmed responses
    if (this.isMember === true) {
      return false;
    }


    return false;
  }


}

