import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core";
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from "@angular/forms";
import * as _ from "lodash";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { EsgService } from "src/app/core/service/common/esg.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { AFAnnexureQuestionDTO } from "src/app/views/pages/application-form/application-form-add-edit/dtos/apf-annexure-dto";

@Component({
  selector: "app-esg-annexures-common",
  templateUrl: "./esg-annexures-common.component.html",
  styleUrls: ["./esg-annexures-common.component.scss"],
})
export class EsgAnnexuresCommonComponent implements OnInit {
  @Input() annexureId: number;
  @Input() facilityPaper: any;
  @Input() isFacilityPaper: boolean = false;
  @Output() submitted = new EventEmitter<any>();
  @Input() applicationForm: any = {};
  @Input() existingData: any;
  @Input() editMode: boolean;
  @Input() facilityPaperID: number;
  @Input() applicationFormID: number;

  anyAnswerFilled = false;

  form: FormGroup;
  questions: AFAnnexureQuestionDTO[] = [];
  loading = true;
  annexureData: any;
  onApplicationFormChangeSub = new Subscription();

  constructor(
    private readonly fb: FormBuilder,
    private readonly alertService: AlertService,
    private readonly esgService: EsgService,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {
    const isNewAnnexure = !this.existingData;
    if (this.editMode && this.existingData) {
      this.loadEditedStructure();
    } else if (isNewAnnexure) {
      this.loadAnnexureStructure(true); // pass true to force form enabled
    } else {
      this.loadAnnexureStructure(false);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.editMode && !changes.editMode.firstChange && this.form) {
      if (this.editMode) {
        this.enableFormControls();
      } else {
        this.disableFormControls();
      }
    }
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  private hasAnyAnswerFilled(): boolean {
    if (!this.form) return false;

    return Object.values(this.form.value).some((val) => {
      if (Array.isArray(val)) return val.length > 0;
      return val !== null && val !== undefined && val.toString().trim() !== "";
    });
  }

  loadAnnexureStructure(enableForm: boolean = false): void {
    this.esgService
      .getAnnexureByID(this.annexureId)
      .then((annexureData) => {
        if (!annexureData) {
          this.alertService.showToaster(
            "Annexure Data not found",
            SETTINGS.TOASTER_MESSAGES.error
          );
          return;
        }

        this.annexureData = annexureData;
        const rawQuestions = Array.isArray(annexureData.questions)
          ? annexureData.questions
          : [];
        this.questions = rawQuestions
          .filter((q: any) => q && q.questionId)
          .sort(
            (a: any, b: any) => (a.displayOrder || 0) - (b.displayOrder || 0)
          )
          .map((q: any) => ({
            ...q,
            answerType: this.isValidAnswerType(q.answerType)
              ? q.answerType
              : "DESCRIPTIVE",
            isMandatory: q.isMandatory || Constants.yesNoConst.N,
            answers: Array.isArray(q.answers)
              ? q.answers.map((a) => ({
                  ...a,
                  answerId: a.answerId != null ? a.answerId : -1,
                  answer: a.answer || "",
                  isSelected: !!a.isSelected,
                  userAnswer: a.userAnswer || "",
                }))
              : [],
              // ...this.getAnswerHistory(q.questionId)
          }));

        this.form = this.buildForm();

        this.form.valueChanges.subscribe(() => {
          this.anyAnswerFilled = this.hasAnyAnswerFilled();
        });

        if (this.existingData && Array.isArray(this.existingData.questions)) {
          this.patchAnswers(this.existingData.questions);

          if (this.editMode) {
            this.enableFormControls();
          } else {
            this.disableFormControls();
          }
        }

        // this.editMode && this.existingData ? this.disableFormControls() : this.enableFormControls();
        this.loading = false;
      })
      .catch((err) => {
        this.alertService.showToaster(
          "Failed to load Annexure Datax",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  private isValidAnswerType(
    type: string
  ): type is AFAnnexureQuestionDTO["answerType"] {
    return [
      "MCQ_WITH_SINGLE",
      "MCQ_WITH_MULTIPLE",
      "DESCRIPTIVE",
      "DESCRIPTIVE_WITH_SUB_QUESTION",
    ].includes(type);
  }

  loadEditedStructure(): void {
    this.annexureData = this.existingData;

    this.questions = (this.existingData.questions || [])
      .sort((a:any, b:any) => a.displayOrder - b.displayOrder)
      .map((q:any) => ({
        ...q,
        answers: (q.answers || []).sort(
          (a:any, b:any) => a.displayOrder - b.displayOrder
        ),
        // ...this.getAnswerHistory(q.questionId)
      }));

    this.form = this.buildForm();

    this.form.valueChanges.subscribe(() => {
      this.anyAnswerFilled = this.hasAnyAnswerFilled();
    });

    this.patchAnswers(this.existingData.questions);

    if (this.existingData && !this.editMode) {
      this.disableFormControls();
    } else {
      this.enableFormControls();
    }

    this.loading = false;
  }

  private buildForm(): FormGroup {
    const group: any = {};

    this.questions.forEach((q:any) => {
      const validators =
        q.isMandatory === Constants.yesNoConst.Y ? [Validators.required] : [];

      if (q.answerType === "MCQ_WITH_MULTIPLE") {
        const validators =
          q.isMandatory === Constants.yesNoConst.Y
            ? [this.minSelectedCheckboxes(1)]
            : [];
        group[q.questionId] = new FormControl([], validators);
      } else if (q.answerType === "DESCRIPTIVE_WITH_SUB_QUESTION") {
        q.answers.forEach((a:any) => {
          const subKey = `${q.questionId}_${a.answerId}`;
          group[subKey] = new FormControl("", validators);
        });
      } else {
        group[q.questionId] = new FormControl("", validators);
      }
    });

    return this.fb.group(group);
  }

  private minSelectedCheckboxes(min: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const selected = control.value;
      return Array.isArray(selected) && selected.length >= min
        ? null
        : { required: true };
    };
  }

  patchExistingAnswers(): void {
    if (!this.existingData.afAnnexureQuestionDataDTOList || !this.form) return;

    this.patchAnswers(this.existingData.afAnnexureQuestionDataDTOList);
  }

  patchUserAnswers(): void {
    if (!this.existingData.questions || !this.form) return;

    this.patchAnswers(this.existingData.questions);
  }

  private patchAnswers(questions: AFAnnexureQuestionDTO[]): void {
    questions.forEach((q: any) => {
      if (q.answerType === "DESCRIPTIVE_WITH_SUB_QUESTION") {
        (q.answers || []).forEach((a) => {
          const control = this.form.get(`${q.questionId}_${a.answerId}`);
          if (control) {
            control.setValue(a.userAnswer || "");
          }
        });
      } else {
        const control = this.form.get(q.questionId.toString());
        if (!control) return;

        if (q.answerType === "MCQ_WITH_MULTIPLE") {
          const selected = (q.answers || [])
            .filter((a) => a.isSelected)
            .map((a) => a.answerId);
          control.setValue(selected);
        } else if (q.answerType === "MCQ_WITH_SINGLE") {
          const single = (q.answers || []).find((a) => a.isSelected);
          control.setValue(single.answerId || "");
        } else if (q.answerType === "DESCRIPTIVE") {
          const control = this.form.get(q.questionId.toString());
          if (!control) return;

          const first = (q.answers || [])[0];

          if (first && (first.userAnswer || first.answer)) {
            control.setValue(first.userAnswer || first.answer);
          } else if (q.userAnswer) {
            control.setValue(q.userAnswer);
          } else if ((q as any).answerData) {
            control.setValue((q as any).answerData);
          } else {
            control.setValue("");
          }
        }
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const createdBy = this.applicationService.getLoggedInUserDisplayName();
    this.esgService
      .saveAnnexureAnswers(this.buildESGPayload(createdBy, ""))
      .then((res) => {
        this.submitted.emit({ annexureId: this.annexureId, action: "submit" });
      })
      .catch((err) => {
        this.alertService.showToaster(
          "Failed to save Annexure Data",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  onUpdate(): void {
    if (this.form.invalid) {
      this.alertService.showToaster(
        "Please complete all required feilds",
        SETTINGS.TOASTER_MESSAGES.warning
      );
      return;
    }

    const modifiedBy = this.applicationService.getLoggedInUserDisplayName();
    const createdBy =
      this.existingData.questions[0].answers[0].createdBy || modifiedBy;
    const payload = this.buildESGPayload(createdBy, modifiedBy);
    // this.esgService
    //   .updateAnnexureAnswers(
    //     this.annexureId,
    //     this.applicationForm.applicationFormID || null,
    //     this.facilityPaperID || null,
    //     payload
    //   )
    //   .then((res) => {
    //     this.alertService.showToaster(
    //       "Annexure answers updated successfully",
    //       SETTINGS.TOASTER_MESSAGES.success
    //     );
    //     this.disableFormControls();
    //     this.submitted.emit({
    //       annexureId: this.annexureId,
    //       action: "update-complete",
    //     });
    //   })
    //   .catch((err) => {
    //     this.alertService.showToaster(
    //       "Failed to Update Annexure Data",
    //       SETTINGS.TOASTER_MESSAGES.error
    //     );
    //   });
  }

  onCancel(): void {
    this.submitted.emit({ annexureId: this.annexureId, action: "cancel" });
  }

  onCheckboxArrayChange(
    event: any,
    questionId: number,
    answerId: number
  ): void {
    const selectedAnswers = this.form.get(questionId.toString()).value || [];

    if (event.target.checked) {
      if (!selectedAnswers.includes(answerId)) {
        selectedAnswers.push(answerId);
      }
    } else {
      const index = selectedAnswers.indexOf(answerId);
      if (index > -1) {
        selectedAnswers.splice(index, 1);
      }
    }

    this.form.get(questionId.toString()).setValue(selectedAnswers);
  }

  buildESGPayload(createdBy: string, modifiedBy: string): any[] {
    const payload = [];

    this.questions.forEach((q:any) => {
      const answerType = q.answerType;
      const formControl = this.form.get(q.questionId.toString());

      if (answerType === "MCQ_WITH_SINGLE" && formControl.value) {
        const answer = q.answers.find((a) => a.answerId === formControl.value);
        if (answer) {
          payload.push(
            this.createAnswerDTO(q, answer, null, createdBy, modifiedBy)
          );
        }
      }

      if (
        answerType === "MCQ_WITH_MULTIPLE" &&
        Array.isArray(formControl.value)
      ) {
        formControl.value.forEach((id) => {
          const answer = q.answers.find((a) => a.answerId === id);
          if (answer) {
            payload.push(
              this.createAnswerDTO(q, answer, null, createdBy, modifiedBy)
            );
          }
        });
      }

      if (answerType === "DESCRIPTIVE" && formControl.value) {
        payload.push(
          this.createAnswerDTO(
            q,
            null,
            formControl.value,
            createdBy,
            modifiedBy
          )
        );
      }

      if (answerType === "DESCRIPTIVE_WITH_SUB_QUESTION") {
        q.answers.forEach((a) => {
          const subControl = this.form.get(`${q.questionId}_${a.answerId}`);
          if (subControl.value) {
            payload.push(
              this.createAnswerDTO(
                q,
                a,
                subControl.value,
                createdBy,
                modifiedBy
              )
            );
          }
        });
      }
    });

    return payload;
  }

  createAnswerDTO(
    question: any,
    answer: any = null,
    answerData: string = null,
    createdBy: string,
    modifiedBy: string
  ): any {
    return {
      applicationFormID:
        this.applicationForm && this.applicationForm.applicationFormID
          ? this.applicationForm.applicationFormID
          : null,
      facilityPaperID:
        this.facilityPaper && this.facilityPaper.facilityPaperID
          ? this.facilityPaper.facilityPaperID
          : null,
      annexureId: question.annexureId,
      questionId: question.questionId,
      answerId: answer && answer.answerId ? answer.answerId : null,
      answerData: answerData || (answer && answer.answer) || "",
      displayOrder:
        answer && answer.displayOrder
          ? answer.displayOrder
          : question.displayOrder || 0,
      createdBy,
      modifiedBy,
    };
  }

  enableFormControls(): void {
    Object.keys(this.form.controls).forEach((controlName) => {
      this.form.get(controlName).enable();
    });
  }

  disableFormControls(): void {
    Object.keys(this.form.controls).forEach((controlName) => {
      this.form.get(controlName).disable();
    });
  }

  getAnswerHistory(questionId: any) {
    let result: any = {
      answeredBy: "",
      answeredDate: "",
    };

    let existingQuestions: any[] =
      this.existingData && this.existingData.questions
        ? this.existingData.questions
        : [];
    let existingQuestion: any = existingQuestions.find(
      (eq: any) => eq.questionId === questionId
    );

    if (existingQuestion) {
      result = {
        answeredBy: existingQuestion.answeredBy
          ? existingQuestion.answeredBy
          : "",
        answeredDate: existingQuestion.answeredDate
          ? existingQuestion.answeredDate
          : "",
      };
    }

    return result;
  }
}
