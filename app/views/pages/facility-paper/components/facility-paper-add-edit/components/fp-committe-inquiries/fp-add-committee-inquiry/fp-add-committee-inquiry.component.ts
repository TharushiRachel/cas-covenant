import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { FpInquiryRichEditorComponent } from "../fp-inquiry-rich-editor/fp-inquiry-rich-editor.component";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { Subject } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-fp-add-committee-inquiry",
  templateUrl: "./fp-add-committee-inquiry.component.html",
  styleUrls: ["./fp-add-committee-inquiry.component.scss"],
})
export class FpAddCommitteeInquiryComponent implements OnInit {
  mode: "inquiry" | "response" | "confirm" = "inquiry"; // decides if top-level or response
  isEdit: boolean = false;
  inquiry: any;
  selectedUser: any;
  response: any;
  facilityPaperID: any;
  facilityPaper: any;
  action: Subject<any> = new Subject<any>();
  @ViewChild(FpInquiryRichEditorComponent, { static: false })
  editor: FpInquiryRichEditorComponent;

  componentForm: FormGroup;
  editorContent: string = "";

  userOptions: Array<{ value: number; label: string; fullData: any }> = [];
  involvedUsers: any[] = [];
  comment: string = "";
  isChecked : Boolean = false;

  

  constructor(
    private mdbModalRef: MDBModalRef,
    private fb: FormBuilder,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.editorContent = this.comment || ""; 
    
    this.componentForm = this.fb.group({
      userId: [this.selectedUser ? this.selectedUser : ""],
      userComment: [this.comment ? this.comment : "", Validators.required],
    });

    this.facilityPaperAddEditService
      .getFPUsersInvolved(this.facilityPaperID)
      .then((users: any[]) => {
        this.involvedUsers = (users ? users : []).filter(
          (u: any) => u.assignUser !== this.applicationService.getLoggedInUserUserName()
        );
        this.userOptions = this.involvedUsers.map((u) => ({
          value: u.assignUserID,
          label: u.assignUserDisplayName,
          fullData: u, // Store full object
        }));

        if (this.selectedUser) {
          const matched = users.find(u => u.assignUser === this.selectedUser);
          if (matched) {
            this.componentForm.patchValue({ userId: matched.assignUserID });
          }
        }
  
      })
      .catch((err) => {
        this.alertService.showToaster(SETTINGS.TOASTER_MESSAGES.warning,"Failed to load users:");
        this.userOptions = [];
      });
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  onDraft() {
    if (this.mode === "response") {
      this.saveResponse(
        Constants.inquiryStatus.RES_DRAFTED,
        this.inquiry,
        this.response
      );
    } else {
      this.saveInquiry(Constants.inquiryStatus.REQ_DRAFTED, this.inquiry);
    }
  }

  onSave() {
    if (this.mode === "response") {
      this.saveResponse(
        Constants.inquiryStatus.RES_SUBMITTED,
        this.inquiry,
        this.response
      );
    } else {
      this.saveInquiry(Constants.inquiryStatus.REQ_SUBMITTED, this.inquiry);
    }
  }

  onConfirm() {
    if (this.mode === "response") {
      this.saveResponse(
        Constants.inquiryStatus.RES_CONFIRMED,
        this.inquiry,
        this.response
      );
    } 
  }


  getAssignUser() {
    let assignUserData = {
      assignUser: this.applicationService.getLoggedInUserUserName(),
      assignUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      assignUserUpmGroupCode:
        this.applicationService.getLoggedInUserUPMGroupCode(),
    };

    let selectedUserId: any = this.componentForm.get("userId").value;

    if (selectedUserId) {
      let user: any = this.involvedUsers.find(
        (u) => u.assignUserID === selectedUserId
      );
      if (user && user) {
        assignUserData = {
          assignUser: user.assignUser,
          assignUserDisplayName: user.assignUserDisplayName,
          assignUserUpmGroupCode: user.assignUserUpmGroupCode,
        };
      }
    }
    return assignUserData;
  }

  getEmailRequest(status: string): any {
    let selectedUserId: any = this.componentForm.get("userId").value;
    let user: any = null;
    if (selectedUserId) {
      user = this.involvedUsers.find(
        (u) => u.assignUserID === selectedUserId
      );
    }
 
    return{
      assignTo: user ? user.assignUser : '',
      facilityPaperId: this.facilityPaperID,
      committeeName: this.facilityPaper.assignUserDisplayName,
      comment:this.editorContent,
      commentedBy: this.applicationService.getLoggedInUserDisplayName(),
      commentType:status
    }
  }
 

  saveInquiry(status: string, inquiry?: any) {
    let request: any = {
      facilityPaperID: this.facilityPaperID,
      committeeInquiryId:
        inquiry !== undefined && inquiry !== null
          ? inquiry.committeeInquiryId
          : 0,
      referenceInquiryId: 0,
      inquiryStatus: status,
      committeeInqReqResDTOList: [
        {
          inquiryRequestResponseType: "REQ",
          inquiryRequestResponseText1: this.editorContent,
        },
      ],
      ...this.getAssignUser(),
    };

    if (status === Constants.inquiryStatus.REQ_SUBMITTED) {
      request.inquiryEmail = this.getEmailRequest('REQ');
    }

    this.facilityPaperAddEditService
      .saveCommitteeInquiry(request)
      .then((res) => {
        this.action.next(res);
        this.mdbModalRef.hide();
      })
      .catch((err) => {
        this.alertService.showToaster(SETTINGS.TOASTER_MESSAGES.warning,"Save failed:");
      });
  }

  saveResponse(status: string, inquiry: any, response?: any) {
    let request: any = {
      facilityPaperID: this.facilityPaperID,
      committeeInquiryId:
        response !== undefined && response !== null
          ? response.committeeInquiryId
          : 0,
      referenceInquiryId: inquiry !== null ? inquiry.committeeInquiryId : 0,
      inquiryStatus: status,
      committeeInqReqResDTOList: [
        {
          inquiryRequestResponseType: "RES",
          inquiryRequestResponseText1: this.editorContent,
        },
      ],
      ...this.getAssignUser(),
    };

    if (status === Constants.inquiryStatus.RES_SUBMITTED) {
      request.inquiryEmail = this.getEmailRequest('RES');
    }

    this.facilityPaperAddEditService
      .saveCommitteeInquiry(request)
      .then((res) => {
        this.action.next(res);
        this.mdbModalRef.hide();
      })
      .catch((err) => {
        console.error("Save failed:", err);
      });
  }

  onEditorSave(content: string) {
    this.editorContent = content; //  update local variable
    this.componentForm.patchValue({ userComment: content });
  }

  onEditorSaveAndClose(content: string) {
    this.componentForm.patchValue({ userComment: content });
    this.onSave();
  }

  showConfirmButton(): boolean {
    return (
      this.mode === "response" &&  // only in response mode
      this.applicationService.getLoggedInUserUPMGroupCode() >= 71 &&
      this.inquiry.inquiryStatus === Constants.inquiryStatus.REQ_SUBMITTED
    );
  }
}
