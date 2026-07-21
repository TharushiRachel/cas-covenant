import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-add-edit-answer",
  templateUrl: "./add-edit-answer.component.html",
  styleUrls: ["./add-edit-answer.component.scss"],
})
export class AddEditAnswerComponent implements OnInit {
  action: Subject<any> = new Subject<any>();

  selectedAnswer: any = null;
  selectedQuestion: string = "";
  newAnswer: any = {
    answerId: 0,
    parentId: 0,
    questionId: 0,
    answer: "",
    displayOrder: 0,
  };
  errors: any = {
    answer: "",
  };
  isSubQuestion: boolean = false;
  constructor(private readonly mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    if (this.selectedAnswer) {
      this.newAnswer = {
        answerId: this.selectedAnswer.answerId
          ? this.selectedAnswer.answerId
          : 0,
        parentId: this.selectedAnswer.parentId
          ? this.selectedAnswer.parentId
          : this.selectedAnswer.answerId,
        questionId: this.selectedAnswer.questionId
          ? this.selectedAnswer.questionId
          : 0,
        answer: this.selectedAnswer.answer ? this.selectedAnswer.answer : "",
        displayOrder: this.selectedAnswer.displayOrder
          ? this.selectedAnswer.displayOrder
          : 0,
      };
    }
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  isSaveEnabled() {
    if (this.selectedAnswer) {
      return this.selectedAnswer.answer != this.newAnswer.answer;
    }

    return true;
  }

  addAnswer() {
    if (this.newAnswer.answer) {
      this.action.next(this.newAnswer);
      this.mdbModalRef.hide();
    } else {
      this.errors = {
        answer: !this.newAnswer.answer
          ? `${this.isSubQuestion ? "Sub Question" : "Answer"} is required!`
          : "",
      };

      setTimeout(() => {
        this.errors = {
          description: "",
        };
      }, 2500);
    }
  }
}
