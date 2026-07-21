import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-add-edit-question",
  templateUrl: "./add-edit-question.component.html",
  styleUrls: ["./add-edit-question.component.scss"],
})
export class AddEditQuestionComponent implements OnInit {
  action: Subject<any> = new Subject<any>();

  yesNoOptions: any[] = [
    { label: Constants.yesNo.Y, value: Constants.yesNoConst.Y },
    { label: Constants.yesNo.N, value: Constants.yesNoConst.N },
  ];
  qTypeOptions: any[] = [
    {
      label: Constants.answerType.MCQ_WITH_SINGLE,
      value: Constants.answerTypeConst.MCQ_WITH_SINGLE,
    },
    {
      label: Constants.answerType.MCQ_WITH_MULTIPLE,
      value: Constants.answerTypeConst.MCQ_WITH_MULTIPLE,
    },
    {
      label: Constants.answerType.DESCRIPTIVE,
      value: Constants.answerTypeConst.DESCRIPTIVE,
    },
    {
      label: Constants.answerType.DESCRIPTIVE_WITH_SUB_QUESTION,
      value: Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION,
    },
    {
      label: Constants.answerType.DESCRIPTIVE_WITH_MCQ_QUESTION,
      value: Constants.answerTypeConst.DESCRIPTIVE_WITH_MCQ_QUESTION,
    },
  ];
  selectedQuestion: any = null;
  newQuestion: any = {
    questionId: 0,
    parentId: 0,
    annexureId: 0,
    answerType: Constants.answerTypeConst.MCQ_WITH_SINGLE,
    question: "",
    displayOrder: 0,
    isMandatory: Constants.yesNoConst.N,
  };
  errors: any = {
    question: "",
  };
  constructor(private readonly mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    if (this.selectedQuestion !== null) {
      this.newQuestion = {
        questionId: this.selectedQuestion.questionId
          ? this.selectedQuestion.questionId
          : 0,
        parentId: this.selectedQuestion.parentId
          ? this.selectedQuestion.parentId
          : this.selectedQuestion.questionId,
        annexureId: this.selectedQuestion.annexureId
          ? this.selectedQuestion.annexureId
          : 0,
        answerType: this.selectedQuestion.answerType
          ? this.selectedQuestion.answerType
          : Constants.answerTypeConst.MCQ_WITH_SINGLE,
        question: this.selectedQuestion.question
          ? this.selectedQuestion.question
          : "",
        isMandatory: this.selectedQuestion.isMandatory
          ? this.selectedQuestion.isMandatory
          : Constants.yesNoConst.N,
        displayOrder: this.selectedQuestion.displayOrder
          ? this.selectedQuestion.displayOrder
          : 0,
      };
    }
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  addQuestion() {
    if (this.newQuestion.question) {
      this.action.next(this.newQuestion);
      this.mdbModalRef.hide();
    } else {
      this.errors = {
        question: !this.newQuestion.question ? "Question is required!" : "",
      };

      setTimeout(() => {
        this.errors = {
          question: "",
        };
      }, 2500);
    }
  }

  isSavedEnabled() {
    if (this.selectedQuestion) {
      return (
        this.selectedQuestion.answerType != this.newQuestion.answerType ||
        this.selectedQuestion.question != this.newQuestion.question ||
        this.selectedQuestion.isMandatory != this.newQuestion.isMandatory
      );
    }
    return true;
  }
}
