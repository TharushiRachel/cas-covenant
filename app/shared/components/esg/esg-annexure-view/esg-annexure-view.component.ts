import { Component, Input, Output, OnInit, EventEmitter } from "@angular/core";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-esg-annexure-view",
  templateUrl: "./esg-annexure-view.component.html",
  styleUrls: ["./esg-annexure-view.component.scss"],
})
export class EsgAnnexureViewComponent implements OnInit {
  @Input() annexureData: any = {};
  @Output() handleSaveAnnexure = new EventEmitter<any>();
  @Output() handleActionCancel = new EventEmitter<any>();

  answerType: any = Constants.answerTypeConst;
  questions: any[] = [];
  questionErrors: any[] = [];

  constructor(private readonly alertService: AlertService) {}

  ngOnInit() {
    this.prepareQuestions();
  }

  prepareQuestions() {
    this.questions =
      this.annexureData !== null &&
      this.annexureData.questions !== null &&
      this.annexureData.questions.length > 0
        ? this.annexureData.questions
            .sort((a: any, b: any) => a.displayOrder - b.displayOrder)
            .map((ques: any) => ({
              ...ques,
              answers: this.prepareAnswers(ques),
              ...this.getPreviousAnswer(ques),
            }))
        : [];
  }

  getPreviousAnswer(question: any) {
    let emptyArray: any[] = [];
    let result: any = {
      userAnswer: "",
      userAnswerList: emptyArray,
    };
    let answers: any[] = question.answers;
    let selectedAnswers: any[] = answers.filter(
      (a: any) => a.isSelected === true
    );

    if (question.answerType === Constants.answerTypeConst.MCQ_WITH_SINGLE) {
      result = {
        ...result,
        userAnswer:
          selectedAnswers.length > 0 ? selectedAnswers[0].answerId : "",
      };
    } else if (question.answerType === Constants.answerTypeConst.DESCRIPTIVE) {
      result = {
        ...result,
        userAnswer:
          selectedAnswers.length > 0 ? selectedAnswers[0].userAnswer : "",
      };
    } else if (
      question.answerType === Constants.answerTypeConst.MCQ_WITH_MULTIPLE
    ) {
      result = {
        ...result,
        userAnswerList:
          selectedAnswers.length > 0
            ? selectedAnswers.map((sa: any) => sa.answerId)
            : emptyArray,
      };
    } else if (
      question.answerType ===
      Constants.answerTypeConst.DESCRIPTIVE_WITH_MCQ_QUESTION
    ) {
      result = {
        ...result,
      };
    }

    return result;
  }

  prepareAnswers(question: any): any[] {
    let result: any[] = [];
    if (question.answers && question.answers.length > 0) {
      result = question.answers
        .sort((a: any, b: any) => a.displayOrder - b.displayOrder)
        .map((a: any) => ({
          ...a,
          answerFormId: "q" + question.questionId + "-" + a.answerId,
        }));
    }
    return result;
  }

  handleMultipleAnswer(event: any, questionId: number, answerId: number) {
    this.questions = this.questions.map((q: any) => ({
      ...q,
      userAnswerList:
        q.questionId === questionId
          ? this.hanldeAnswers(q, answerId)
          : q.userAnswerList,
    }));
  }

  hanldeAnswers(question: any, answerId: number) {
    let answers: any[] =
      question.userAnswerList !== null ? question.userAnswerList : [];
    if (!answers.some((a: any) => a === answerId)) {
      answers.push(answerId);
    } else {
      answers = answers.filter((a: any) => a !== answerId);
    }

    return answers;
  }

  handleYesNoAnswer(question: any, answerId: any, selectedAnswer: string) {
    this.questions = this.questions.map((q: any) => ({
      ...q,
      answers:
        q.questionId === question.questionId
          ? q.answers.map((qa: any) => ({
              ...qa,
              userAnswer:
                qa.answerId === answerId ? selectedAnswer : qa.userAnswer,
            }))
          : q.answers,
    }));
  }

  isAnswerSelected(questionId: number, answerId: number) {
    return this.questions
      .filter((q: any) => q.questionId === questionId)
      .some((q: any) =>
        q.userAnswerList
          ? q.userAnswerList.some((ua: any) => ua === answerId)
          : false
      );
  }

  isYesNoSelected(answer: any, expectedAnswer: string) {
    let result: string = "";

    if (answer.userAnswer !== null && answer.userAnswer === expectedAnswer) {
      return "X";
    }

    return result;
  }

  onSubmit() {
    let answerDataList: any[] = [];

    this.questions.forEach((question: any) => {
      let errorMsg: string = this.validateQuestion(question);
      if (errorMsg == "") {
        let answerObj: any = {
          answerDataId: 0,
          annexureId: this.annexureData.annexureId,
          questionId: question.questionId,
          answerId: "",
          answerData: "",
          displayOrder: "",
        };

        if (question.answerType === Constants.answerTypeConst.MCQ_WITH_SINGLE) {
          answerObj = {
            ...answerObj,
            answerId: question.userAnswer,
            answerData: "",
            displayOrder: 0,
          };
          answerDataList.push(answerObj);
        } else if (
          question.answerType === Constants.answerTypeConst.DESCRIPTIVE
        ) {
          answerObj = {
            ...answerObj,
            answerId: "",
            answerData: question.userAnswer,
            displayOrder: 0,
          };
          answerDataList.push(answerObj);
        } else if (
          question.answerType === Constants.answerTypeConst.MCQ_WITH_MULTIPLE
        ) {
          if (question.userAnswerList.length > 0) {
            question.userAnswerList.forEach((answer: any) => {
              answerObj = {
                ...answerObj,
                answerId: answer,
                answerData: "",
                displayOrder: 0,
              };
              answerDataList.push(answerObj);
            });
          } else {
            answerObj = {
              ...answerObj,
              answerId: null,
              answerData: "",
              displayOrder: 0,
            };
            answerDataList.push(answerObj);
          }
        } else if (
          question.answerType ===
            Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION ||
          question.answerType ===
            Constants.answerTypeConst.DESCRIPTIVE_WITH_MCQ_QUESTION
        ) {
          question.answers.forEach((answer: any) => {
            answerObj = {
              ...answerObj,
              answerId: answer.answerId,
              answerData: answer.userAnswer,
              displayOrder:
                answer.displayOrder !== null ? answer.displayOrder : 0,
            };
            answerDataList.push(answerObj);
          });
        }
      } else {
        this.questionErrors.push({
          questionId: question.questionId,
          error: errorMsg,
        });
      }
    });

    if (this.questionErrors.length === 0) {
      let payload: any = {
        annexure: this.annexureData,
        answers: answerDataList,
      };

      this.handleSaveAnnexure.emit(payload);
    } else {
      setTimeout(() => {
        this.questionErrors = [];
      }, 2000);
      this.alertService.showToaster(
        "Please respond to the necessary questions.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  validateQuestion(question: any) {
    let msg: string = "";

    if (question.isMandatory === Constants.yesNoConst.Y) {
      switch (question.answerType) {
        case Constants.answerTypeConst.MCQ_WITH_SINGLE:
        case Constants.answerTypeConst.DESCRIPTIVE:
          msg =
            question.userAnswer === null || question.userAnswer === ""
              ? "Answer is required."
              : "";
          break;
        case Constants.answerTypeConst.DESCRIPTIVE_WITH_MCQ_QUESTION:
          msg =
            question.answers !== null &&
            question.answers.some(
              (a: any) => a.userAnswer === null || a.userAnswer === ""
            )
              ? "Answers is required."
              : "";
          break;
        case Constants.answerTypeConst.MCQ_WITH_MULTIPLE:
          msg =
            question.userAnswerList === null ||
            question.userAnswerList.length < 2
              ? "Multiple Answers are required."
              : "";
          break;
        case Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION:
          msg = question.answers.some(
            (a: any) => a.userAnswer === null || a.userAnswer === ""
          )
            ? "All Fields are required."
            : "";
          break;
        default:
          break;
      }
    }

    return msg;
  }

  getErrorMsg(questionId: any) {
    let error: any = this.questionErrors.find(
      (e: any) => e.questionId === questionId
    );
    return error !== undefined && error !== null ? error.error : "";
  }

  onCancel() {
    this.handleActionCancel.next(this.annexureData);
  }

  isNewAnnexure() {
    return this.annexureData.recordStatus === Constants.annexStatusConst.NEW;
  }
}
