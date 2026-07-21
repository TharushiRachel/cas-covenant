import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-view-question-wrapper",
  templateUrl: "./view-question-wrapper.component.html",
  styleUrls: ["./view-question-wrapper.component.scss"],
})
export class ViewQuestionWrapperComponent implements OnInit {
  @Input("questions") questions: any[] = [];
  @Input("isEditEnabled") isEditEnabled: boolean = false;
  @Input("isBadgeShow") isBadgeShow: boolean = false;

  @Output("addEditQuestion") addEditQuestion = new EventEmitter();
  @Output("removeQuestion") removeQuestion = new EventEmitter();
  @Output("addEditAnswer") addEditAnswer = new EventEmitter();
  @Output("removeAnswer") removeAnswer = new EventEmitter();
  @Output("sortAnswers") sortAnswers = new EventEmitter();

  answerType: any = Constants.answerType;
  constructor() {}

  ngOnInit() {}

  getSortedList(dataList: any[]) {
    if (this.isEditEnabled) {
      return dataList
        .filter(
          (a: any) => a.actionStatus !== Constants.annexStatusConst.DELETE
        )
        .sort((a: any, b: any) => a.displayOrder - b.displayOrder);
    }
    return dataList.sort((a: any, b: any) => a.displayOrder - b.displayOrder);
  }

  isMCQ(item: any) {
    return item.answerType !== Constants.answerTypeConst.DESCRIPTIVE;
  }

  isSubQuestion(item: any) {
    return (
      item.answerType ===
      Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION
    );
  }

  isSubMCQ(item: any) {
    return (
      item.answerType ===
      Constants.answerTypeConst.DESCRIPTIVE_WITH_MCQ_QUESTION
    );
  }

  handleAddEdit(item: any) {
    this.addEditQuestion.next(item);
  }

  handleRemoveQuestion(item: any) {
    this.removeQuestion.next(item);
  }

  handleAddEditAnswer(question: any, item?: any) {
    let request: any = {
      question: question,
      item: item ? item : null,
    };
    this.addEditAnswer.next(request);
  }

  handleRemoveAnswer(question: any, item?: any) {
    let request: any = {
      question: question,
      item: item ? item : null,
    };
    this.removeAnswer.next(request);
  }

  handleSortAnswers(item: any) {
    this.sortAnswers.next(item);
  }

  isActionShow(status: string) {
    return (
      status &&
      status !== Constants.annexStatusConst.SUBMITTED &&
      this.isBadgeShow
    );
  }

  getActionStatus(status: string) {
    switch (status) {
      case Constants.annexStatusConst.NEW:
        return "New";
      case Constants.annexStatusConst.UPDATE:
        return "Edited";
      case Constants.annexStatusConst.DELETE:
        return "Deleted";
    }
  }

  getActionColor(status: string) {
    switch (status) {
      case Constants.annexStatusConst.NEW:
        return "#2bbbad";
      case Constants.annexStatusConst.UPDATE:
        return "#007bff";
      case Constants.annexStatusConst.DELETE:
        return "#dc3545";
    }
  }

  isSortEnabled(dataList: any[]) {
    return (
      dataList.filter(
        (d: any) => d.actionStatus !== Constants.annexStatusConst.DELETE
      ).length > 1
    );
  }
}
