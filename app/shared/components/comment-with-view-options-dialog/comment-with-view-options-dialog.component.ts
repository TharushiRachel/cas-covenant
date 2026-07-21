import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subject, Subscription } from "rxjs";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import {
  IMyOptions,
  MDBModalRef,
} from "ng-uikit-pro-standard";
import { AppUtils } from "../../app.utils";
import { CommentCacheService } from "../../../core/service/data/comment-cache.service";
import { ApplicationService } from "../../../core/service/application/application.service";
import { Constants } from "../../../core/setting/constants";
import * as moment from "moment";
import { SmeServiceService } from "src/app/views/pages/sme/service/sme-service.service";

@Component({
  selector: "app-comment-with-view-options-dialog",
  templateUrl: "./comment-with-view-options-dialog.component.html",
  styleUrls: ["./comment-with-view-options-dialog.component.scss"],
})
export class CommentWithViewOptionsDialogComponent
  implements OnInit, OnDestroy
{
  heading: string;
  commentCacheKey: string;
  message: string;
  comment: any = {};
  fpUsersInvolvedList: any[] = [];
  showUsersOnlyOption = true;
  showDivisionOnlyOption = true;
  actionName: string;
  action: Subject<any> = new Subject<any>();
  componentForm: FormGroup;
  onFormValueChangeSub = new Subscription();
  formErrors: any;
  isMeetingDateEnable: boolean = false;
  meetingDate: any;

  today: any = moment().add(1, "day").format("YYYY-MM-DD");
  public myDatePickerOptions: IMyOptions = {
    dateFormat: "yyyy-mm-dd",
    minYear: new Date().getFullYear() - 5,
    maxYear: new Date().getFullYear(),
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: "mo",
    editableDateField: false,
    disableSince: {
      year: this.today ? this.today.split("-")[0] : 0,
      month: this.today ? this.today.split("-")[1] : 0,
      day: this.today ? this.today.split("-")[2] : 0,
    },
  };

  isQuestionsSubmitted: boolean = false; // State to track if questions are submitted
  isQuestionsVisible: boolean = false; // Flag to control visibility of <app-sme-questions>
  questionsAndAnswers: any[] = []; // Array to store filtered questions and answers

  constructor(
    private mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private commentCacheService: CommentCacheService,
    private applicationService: ApplicationService,
    private smeService: SmeServiceService
  ) {}

  ngOnInit() {
    this.formErrors = {
      comment: {},
    };

    this.createForm();
    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formErrors = AppUtils.getFormErrors(
          this.componentForm,
          this.formErrors
        );
      }
    );

    this.onFormValueChangeSub =
      this.componentForm.controls.isUsersOnly.valueChanges.subscribe((data) => {
        if (data) {
          this.componentForm.get("isDivisionOnly").setValue(false);
          this.componentForm.get("isPublic").setValue(false);
        } else {
          this.componentForm.get("isPublic").setValue(true);
        }
      });

    this.onFormValueChangeSub =
      this.componentForm.controls.isDivisionOnly.valueChanges.subscribe(
        (data) => {
          if (data) {
            this.componentForm.get("isUsersOnly").setValue(false);
            this.componentForm.get("isPublic").setValue(false);
          } else {
            this.componentForm.get("isPublic").setValue(true);
          }
        }
      );

    this.onFormValueChangeSub =
      this.componentForm.controls.comment.valueChanges.subscribe((data) => {
        this.commentCacheService.cacheComment(this.commentCacheKey, data);
      });

    this.checkQuestionsVisibility();
  }

  handleUserSelect(event: any, userId: any) {
    var checked: boolean = event ? event.checked : false;

    this.fpUsersInvolvedList = this.fpUsersInvolvedList.map((user: any) => ({
      ...user,
      isSelected: user.assignUserID == userId ? checked : user.isSelected,
    }));
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  submitComment(): void {
    var formData: any = Object.assign({}, this.componentForm.getRawValue(), {
      fpUsersInvolvedList: this.fpUsersInvolvedList,
      meetingDate:
        this.isMeetingDateEnable && this.meetingDate
          ? moment(this.meetingDate).format("YYYY-MM-DD")
          : "",
    });
    this.action.next(formData);
    this.commentCacheService.expireCommentCacheData(this.commentCacheKey);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  isValid() {
    return this.componentForm && this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm && this.componentForm.dirty;
  }

  createForm() {
    this.componentForm = this.formBuilder.group({
      comment: [
        this.comment.comment
          ? this.comment.comment
          : this.commentCacheService.commentDataCache[this.commentCacheKey]
          ? this.commentCacheService.commentDataCache[this.commentCacheKey]
          : "",
        [Validators.required],
      ],
      isUsersOnly: [
        this.comment.isUsersOnly ? this.comment.isUsersOnly === "Y" : false,
      ],
      isDivisionOnly: [
        this.comment.isDivisionOnly
          ? this.comment.isDivisionOnly === "Y"
          : false,
      ],
      isPublic: [this.comment.isPublic ? this.comment.isPublic === "Y" : true],
    });
    let loggedInUserGroupUPMGroupCode =
      this.applicationService.getLoggedInUserUPMGroupCode();
    //if (loggedInUserGroupUPMGroupCode == Constants.defaultWorkflowUpmGroupCode.ASSISTANT || loggedInUserGroupUPMGroupCode == Constants.defaultWorkflowUpmGroupCode.MD) {
    if (
      loggedInUserGroupUPMGroupCode ==
      Constants.defaultWorkflowUpmGroupCode.ASSISTANT
    ) {
      this.componentForm.get("isDivisionOnly").setValue(true);
      this.componentForm.get("isUsersOnly").setValue(false);
      this.componentForm.get("isPublic").setValue(false);
    } else {
      this.componentForm.get("isDivisionOnly").setValue(false);
      this.componentForm.get("isUsersOnly").setValue(false);
      this.componentForm.get("isPublic").setValue(true);
    }
  }

  checkUserSelected() {
    if (this.actionName != "Reject") {
      if (
        this.fpUsersInvolvedList != null &&
        this.fpUsersInvolvedList.length > 0
      ) {
        let bb =
          this.fpUsersInvolvedList.find((user) => user.isSelected) !==
          undefined;
        //console.log("bb", bb)
        return bb;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  isValidMeetingDate() {
    if (this.isMeetingDateEnable) {
      if (
        this.meetingDate &&
        moment(this.today).format("YYYY-MM-DD") >
          moment(this.meetingDate).format("YYYY-MM-DD")
      ) {
        return true;
      }
      return false;
    }

    return true;
  }

  onQuestionsSubmitted(submitted: boolean) {
    this.isQuestionsSubmitted = submitted; // Update state when questions are submitted
  }

  private checkQuestionsVisibility() {
    const userWorkClass = this.applicationService.getLoggedInUserUPMGroupCode();
    console.log('Logged-in User Work Class:', userWorkClass);

    this.smeService.getAllQuestionsAndAnswers().then((response: any) => {
      if (!response || !Array.isArray(response)) {
        console.error('Invalid response from getAllQuestionsAndAnswers:', response);
        this.isQuestionsVisible = false;
        return;
      }

      // Log the entire response for debugging
      console.log('Response from getAllQuestionsAndAnswers:', response);

      // Filter questions based on userWorkClass
      this.questionsAndAnswers = response.filter((question: any) => {
        // Ensure smeQuestionConfigDTOList exists and is an array
        if (!question.smeQuestionConfigDTOList || !Array.isArray(question.smeQuestionConfigDTOList)) {
          console.warn(`Question ${question.smeQuestionId} has no valid smeQuestionConfigDTOList.`);
          return false; // Exclude questions without valid configs
        }

        // Check if any config matches the userWorkClass
        const hasPermission = question.smeQuestionConfigDTOList.some((config: any) => {
          const matchesWorkClass = String(config.workClass) === String(userWorkClass);
          const isVisible = config.isShow === 'Y';

          return matchesWorkClass && isVisible;
        });

        return hasPermission;
      });

      // Set visibility based on filtered questions
      this.isQuestionsVisible = this.questionsAndAnswers.length > 0;

      console.log('Filtered Questions:', this.questionsAndAnswers);
      console.log('Is Questions Visible:', this.isQuestionsVisible);
    }).catch((error: any) => {
      console.error('Error fetching questions:', error);
      this.isQuestionsVisible = false;
    });
  }

  smeQuestionShow(): boolean {
    if(this.heading == "Forward Facility Paper" || this.heading == "Approve Facility Paper"){
        return this.isQuestionsVisible && !this.isQuestionsSubmitted;
    }
    
    if(this.applicationService.getLoggedInUserDivCode() == '874'){
      this.isQuestionsVisible = false;
  
    }
  }

  smeQuestionSkip(): boolean {
    if(this.heading == "Approve Facility Paper"){
      return this.isQuestionsSubmitted;
    }
    else {
      return true;
    }
  }
}
