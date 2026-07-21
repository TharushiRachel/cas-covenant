import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Constants } from "src/app/core/setting/constants";
import { AddEditQuestionComponent } from "../add-edit-question/add-edit-question.component";
import { AddEditAnswerComponent } from "../add-edit-answer/add-edit-answer.component";
import { EnvironmentalRiskAnnexureService } from "../../services/environmental-risk-annexure.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { SortableModalComponent } from "../sortable-modal/sortable-modal.component";

@Component({
  selector: "app-add-edit-environmental-risk-annexure",
  templateUrl: "./add-edit-environmental-risk-annexure.component.html",
  styleUrls: ["./add-edit-environmental-risk-annexure.component.scss"],
})
export class AddEditEnvironmentalRiskAnnexureComponent
  implements OnInit, OnDestroy
{
  modalRef: MDBModalRef;
  onAnnexureChange = new Subscription();

  yesNoOptions: any[] = [
    { label: Constants.yesNo.Y, value: Constants.yesNoConst.Y },
    { label: Constants.yesNo.N, value: Constants.yesNoConst.N },
  ];

  answerType: any = Constants.answerType;

  formData: any = {
    annexureId: 0,
    parentId: 0,
    name: "",
    description: "",
    isMandatory: Constants.yesNoConst.N,
    allowRiskEdit: Constants.yesNoConst.N,
    status: Constants.statusConst.ACT,
  };

  selectedAnnexure: any = {
    annexureId: 0,
    parentId: 0,
    name: "",
    description: "",
    isMandatory: Constants.yesNoConst.N,
    status: Constants.statusConst.ACT,
  };

  prevQuestions: any[] = [];
  questions: any[] = [];

  selectedQuestion: any;
  selectedAnswer: any;

  errors: any = { name: "", description: "", questions: "" };

  selectedAnnexureId: number = 0;
  selectedActionStatus: string = "";

  constructor(
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute,
    private readonly mdbModalService: MDBModalService,
    private readonly environmentalRiskAnnexureService: EnvironmentalRiskAnnexureService
  ) {}

  ngOnInit() {
    this.handleClear();

    this.activatedRoute.queryParams.subscribe((params: any) => {
      this.selectedAnnexureId =
        params && params.annexureId ? params.annexureId : 0;
      this.selectedActionStatus = params && params.status ? params.status : "";
    });

    this.onAnnexureChange =
      this.environmentalRiskAnnexureService.onAnnexureChange.subscribe(
        (annex: any) => {
          if (annex !== null && Object.entries(annex).length > 0) {
            this.updateFormData(annex);
          } else {
            this.getAnnexeById();
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onAnnexureChange.unsubscribe();
  }

  getAnnexeById() {
    if (this.selectedAnnexureId > 0) {
      this.environmentalRiskAnnexureService
        .getAnnexeById(this.selectedAnnexureId, this.selectedActionStatus)
        .then((annex: any) => {
          if (annex) {
            this.updateFormData(annex);
          }
        });
    }
  }

  updateFormData(annex: any) {
    this.formData = {
      ...annex,
      annexureId: annex.annexureId,
      parentId: annex.parentId ? annex.parentId : annex.annexureId,
      name: annex.name,
      description: annex.description,
      isMandatory: annex.isMandatory,
      allowRiskEdit: annex.allowRiskEdit,
      status: annex.status,
      actionStatus: annex.actionStatus
        ? annex.actionStatus
        : Constants.annexStatusConst.SUBMITTED,
    };
    this.selectedAnnexure = {
      ...annex,
    };
    let questions: any[] = annex.questions.map((q: any) => ({
      ...q,
      status: Constants.statusConst.ACT,
      actionStatus: q.actionStatus
        ? q.actionStatus
        : Constants.annexStatusConst.SUBMITTED,
      answers: q.answers.map((qa: any) => ({
        ...qa,
        status: Constants.statusConst.ACT,
        actionStatus: qa.actionStatus
          ? qa.actionStatus
          : Constants.annexStatusConst.SUBMITTED,
      })),
    }));
    this.prevQuestions = questions;
    this.questions = questions;
  }

  handleAddEditQuestion(item?: any) {
    this.modalRef = this.mdbModalService.show(AddEditQuestionComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-md modal-width-50-p",
      containerClass: "",
      animated: false,
      data: {
        selectedQuestion: item ? item : null,
      },
    });
    this.modalRef.content.action.subscribe((result: any) => {
      if (result !== null) {
        this.saveQuestion(result);
      }
    });
  }

  handleRemoveQuestion(item: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "",
      animated: false,
      data: {
        heading: "Confirm Question Deletion",
        message: `Do you want to delete this question?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.removeQuestion(item);
      }
    });
  }

  handleAddEditAnswer(request: any) {
    let question: any = request.question;
    let item: any = request.item ? request.item : null;

    this.modalRef = this.mdbModalService.show(AddEditAnswerComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-md modal-width-50-p",
      containerClass: "",
      animated: false,
      data: {
        selectedQuestion: question.question,
        selectedAnswer: item,
        isSubQuestion:
          question.answerType ===
          Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION,
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result !== null) {
        this.saveAnswer(question, result);
      }
    });
  }

  handleRemoveAnswer(request: any) {
    let question: any = request.question;
    let item: any = request.item ? request.item : null;
    let isSubQuestion: boolean =
      question.answerType ===
      Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION;

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "",
      animated: false,
      data: {
        heading: `Confirm ${
          isSubQuestion ? "Sub Question" : "Answer"
        } Deletion`,
        message: `Do you want to delete this ${
          isSubQuestion ? "sub question" : "answer"
        }?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.removeAnswer(question, item);
      }
    });
  }

  saveQuestion(item: any) {
    if (this.questions.some((q: any) => q.questionId == item.questionId)) {
      this.questions = this.questions.map((q: any) =>
        q.questionId == item.questionId
          ? {
              ...item,
              answers: q.answers,
              status: Constants.statusConst.ACT,
              actionStatus: this.getQuestionStatus(item.questionId),
            }
          : q
      );
    } else {
      let emptyArr: any[] = [];
      this.questions.push({
        ...item,
        questionId: this.generateRandomNumber(),
        actionStatus: Constants.annexStatusConst.NEW,
        status: Constants.statusConst.ACT,
        displayOrder: this.getDisplayOrder(this.questions),
        answers: emptyArr,
        error: "",
      });
    }
  }

  removeQuestion(item: any) {

    if (this.prevQuestions.some((q: any) => q.questionId == item.questionId)) {
      this.questions = this.questions.map((q: any) =>
        q.questionId == item.questionId
          ? {
              ...q,
              status: Constants.statusConst.INA,
              actionStatus: Constants.annexStatusConst.DELETE,
              answers: q.answers
                ? q.answers.map((aq: any) => ({
                    ...aq,
                    status: Constants.statusConst.INA,
                    actionStatus: Constants.annexStatusConst.DELETE,
                  }))
                : [],
            }
          : q
      );
    } else {
      this.questions = this.questions.filter(
        (q: any) => q.questionId != item.questionId
      );
    }
  }

  saveAnswer(question: any, item: any) {
    let answers: any[] = question.answers;

    if (answers.some((q: any) => q.answerId == item.answerId)) {
      answers = answers.map((q: any) =>
        q.answerId == item.answerId
          ? {
              ...item,
              status: Constants.statusConst.ACT,
              actionStatus: this.getAnswerStatus(
                question.answers,
                item.answerId
              ),
            }
          : q
      );
    } else {
      answers.push({
        ...item,
        answerId: this.generateRandomNumber(),
        status: Constants.statusConst.ACT,
        actionStatus: Constants.annexStatusConst.NEW,
        displayOrder: this.getDisplayOrder(answers),
      });
    }

    this.questions = this.questions.map((q: any) =>
      q.questionId == question.questionId
        ? {
            ...q,
            answers: answers,
            actionStatus: this.getUpdatedQuestionStatus(question, answers),
          }
        : q
    );
  }

  getUpdatedQuestionStatus(question: any, answers: any[]) {
    let isPreviousQuestion: boolean = this.prevQuestions.some(
      (q: any) => q.questionId === question.questionId
    );
    return isPreviousQuestion &&
      answers.some(
        (a: any) => a.actionStatus !== Constants.annexStatusConst.SUBMITTED
      )
      ? Constants.annexStatusConst.UPDATE
      : question.actionStatus;
  }

  removeAnswer(question: any, item: any) {
    let prevQuestion: any = this.prevQuestions.find(
      (pq: any) => pq.questionId == question.questionId
    );

    let answers: any[] = question.answers;
    let prevAnswers: any[] = prevQuestion ? prevQuestion.answers : [];

    if (prevAnswers.some((a: any) => a.answerId == item.answerId)) {
      answers = answers.map((a: any) =>
        a.answerId == item.answerId
          ? {
              ...a,
              status: Constants.statusConst.INA,
              actionStatus: Constants.annexStatusConst.DELETE,
            }
          : a
      );
    } else {
      answers = answers.filter((a: any) => a.answerId != item.answerId);
    }

    this.questions = this.questions.map((q: any) =>
      q.questionId == question.questionId ? { ...q, answers: answers } : q
    );
  }

  getAnnexStatus() {
    if (
      this.formData.annexureId != 0 &&
      this.isAnnexureChange() &&
      this.formData.actionStatus !== Constants.annexStatusConst.DRAFT
    ) {
      return Constants.annexStatusConst.UPDATE;
    }
    if (
      this.formData.annexureId != 0 &&
      !this.isAnnexureChange() &&
      this.formData.actionStatus !== Constants.annexStatusConst.DRAFT
    ) {
      return Constants.annexStatusConst.SUBMITTED;
    }
    return Constants.annexStatusConst.NEW;
  }

  getQuestionStatus(id: number) {
    if (
      this.prevQuestions.some(
        (q: any) =>
          q.questionId == id &&
          q.actionStatus === Constants.annexStatusConst.SUBMITTED
      )
    ) {
      return Constants.annexStatusConst.UPDATE;
    }
    if (
      this.prevQuestions.some(
        (q: any) =>
          q.questionId == id &&
          q.actionStatus === Constants.annexStatusConst.DRAFT
      )
    ) {
      return Constants.annexStatusConst.DRAFT;
    }
    return Constants.annexStatusConst.NEW;
  }

  getAnswerStatus(prevList: any[], id: number) {
    if (
      prevList.some(
        (q: any) =>
          q.answerId == id &&
          q.actionStatus === Constants.annexStatusConst.SUBMITTED
      )
    ) {
      return Constants.annexStatusConst.UPDATE;
    }
    if (
      prevList.some(
        (q: any) =>
          q.answerId == id &&
          q.actionStatus === Constants.annexStatusConst.DRAFT
      )
    ) {
      return Constants.annexStatusConst.DRAFT;
    }
    return Constants.annexStatusConst.NEW;
  }

  isMCQ(item: any) {
    return item.answerType !== Constants.answerTypeConst.DESCRIPTIVE;
  }

  generateRandomNumber() {
    let epoch = new Date().getTime();
    return epoch % 100000;
  }

  getDisplayOrder(dataList: any) {
    let sortedList: any[] = dataList
      .filter((a: any) => a.actionStatus !== Constants.annexStatusConst.DELETE)
      .sort((a: any, b: any) => b.displayOrder - a.displayOrder);

    return sortedList.length != 0 ? sortedList[0].displayOrder + 1 : 1;
  }

  handleSortQuestions() {
    this.modalRef = this.mdbModalService.show(SortableModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-40-p",
      containerClass: "",
      animated: false,
      data: {
        heading: "Sort Questions",
        message: "",
        dataList: this.questions,
        listType: "Q",
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result !== null) {
        this.questions = result.sort(
          (a: any, b: any) => a.displayOrder - b.displayOrder
        );
      }
    });
  }

  handleSortAnswers(item: any) {
    this.modalRef = this.mdbModalService.show(SortableModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-40-p",
      containerClass: "",
      animated: false,
      data: {
        heading: "Sort Answers",
        message: "",
        dataList: item.answers ? item.answers : [],
        listType: "A",
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result !== null) {
        this.questions = this.questions.map((q: any) =>
          q.questionId == item.questionId
            ? {
                ...q,
                answers: result.sort(
                  (a: any, b: any) => a.displayOrder - b.displayOrder
                ),
              }
            : q
        );
      }
    });
  }

  handleSave() {
    this.validateQuestions();

    if (
      this.formData.name &&
      this.formData.description &&
      this.questions.length > 0 &&
      !this.questions.some((q: any) => q.error && q.error !== "")
    ) {
      let payload: any = {
        ...this.formData,
        status: Constants.statusConst.ACT,
        actionStatus: this.getAnnexStatus(),
        questions: this.questions.map((q: any) => ({
          ...q,
          questionId: this.getQuestionId(q),
          actionStatus:
            q.actionStatus == Constants.annexStatusConst.DRAFT
              ? Constants.annexStatusConst.NEW
              : q.actionStatus,
          answers: q.answers.map((qa: any) => ({
            ...qa,
            answerId: this.getAnswerId(qa, this.getQuestionId(q)),
            actionStatus:
              qa.actionStatus == Constants.annexStatusConst.DRAFT
                ? Constants.annexStatusConst.NEW
                : qa.actionStatus,
          })),
        })),
      };

      this.environmentalRiskAnnexureService
        .saveTempAnnex(payload)
        .then((res: any) => {
          if (res) {
            this.environmentalRiskAnnexureService.onAnnexureChange.next(null);
            this.router.navigate(["/environmental-risk-annexure"]);
          }
        });
    } else {
      this.errors = {
        name: !this.formData.name ? "Name is required!" : "",
        description: !this.formData.description
          ? "Description is required!"
          : "",
        questions: this.questions.length == 0 ? "Questions are required." : "",
      };

      setTimeout(() => {
        this.errors = { name: "", description: "", questions: "" };
        this.questions.forEach((element: any) => {
          element.error = "";
        });
      }, 2500);
    }
  }

  handleDraftSave() {
    if (this.formData.name && this.formData.description) {
      let payload: any = {
        ...this.formData,
        status: Constants.statusConst.ACT,
        actionStatus: Constants.annexStatusConst.DRAFT,
        questions: this.questions.map((q: any) => ({
          ...q,
          questionId: this.getQuestionId(q),
          actionStatus:
            q.actionStatus !== Constants.annexStatusConst.DELETE
              ? Constants.annexStatusConst.DRAFT
              : Constants.annexStatusConst.DELETE,
          answers: q.answers.map((qa: any) => ({
            ...qa,
            answerId: this.getAnswerId(qa, this.getQuestionId(q)),
            actionStatus:
              qa.actionStatus !== Constants.annexStatusConst.DELETE
                ? Constants.annexStatusConst.DRAFT
                : Constants.annexStatusConst.DELETE,
          })),
        })),
      };

      this.environmentalRiskAnnexureService
        .saveTempAnnex(payload)
        .then((res: any) => {
          if (res && res.tempAnnexes && res.tempAnnexes.length > 0) {
            this.environmentalRiskAnnexureService.onAnnexureChange.next(
              res.tempAnnexes[0]
            );
            this.router.navigate(
              ["/environmental-risk-annexure/add-edit-annexure"],
              {
                queryParams: {
                  annexureId: res.tempAnnexes[0].annexureId,
                  status: Constants.annexStatusConst.DRAFT,
                },
              }
            );
          }
        });
    } else {
      this.errors = {
        name: !this.formData.name ? "Name is required!" : "",
        description: !this.formData.description
          ? "Description is required!"
          : "",
      };

      setTimeout(() => {
        this.errors = { name: "", description: "", questions: "" };
        this.questions.forEach((element: any) => {
          element.error = "";
        });
      }, 2500);
    }
  }

  getQuestionId(question: any) {
    return (question.actionStatus == Constants.annexStatusConst.NEW ||
      question.actionStatus == Constants.annexStatusConst.DRAFT) &&
      !this.prevQuestions.some(
        (pq: any) => pq.questionId == question.questionId
      )
      ? 0
      : question.questionId;
  }

  getAnswerId(answer: any, questionId: number) {
    let prevQuestion: any = this.prevQuestions.find(
      (pq: any) => pq.questionId == questionId
    );
    let prevList: any[] =
      prevQuestion !== undefined &&
      prevQuestion !== null &&
      prevQuestion.answers
        ? prevQuestion.answers
        : [];

    return (answer.actionStatus == Constants.annexStatusConst.NEW ||
      answer.actionStatus == Constants.annexStatusConst.DRAFT) &&
      !prevList.some((pa: any) => pa.answerId == answer.answerId)
      ? 0
      : answer.answerId;
  }

  validateQuestions() {
    this.questions
      .filter((q: any) => q.actionStatus !== Constants.annexStatusConst.DELETE)
      .forEach((element: any) => {
        let answers: any[] = element.answers.filter(
          (a: any) => a.actionStatus !== Constants.annexStatusConst.DELETE
        );

        switch (element.answerType) {
          case Constants.answerTypeConst.MCQ_WITH_SINGLE:
            element.error =
              !answers || answers.length === 0 ? "Answers are required." : "";
            break;

          case Constants.answerTypeConst.DESCRIPTIVE_WITH_SUB_QUESTION:
            element.error =
              !answers || answers.length === 0
                ? "Sub Questions are required."
                : "";
            break;

          case Constants.answerTypeConst.MCQ_WITH_MULTIPLE:
            element.error =
              !answers || answers.length <= 1
                ? "Answers should be greater than two."
                : "";
            break;

          default:
            element.error = "";
        }
      });
  }

  isSortEnabled(dataList: any[]) {
    return (
      dataList.filter(
        (d: any) => d.actionStatus !== Constants.annexStatusConst.DELETE
      ).length > 1
    );
  }

  isAnnexureChange() {
    return (
      this.selectedAnnexure.name !== this.formData.name ||
      this.selectedAnnexure.description !== this.formData.description ||
      this.selectedAnnexure.isMandatory !== this.formData.isMandatory ||
      this.selectedAnnexure.allowRiskEdit !== this.formData.allowRiskEdit
    );
  }

  showDraft() {
    return this.formData.actionStatus !== Constants.annexStatusConst.SUBMITTED;
  }

  handleClear() {
    this.formData = {
      annexureId: 0,
      parentId: 0,
      name: "",
      description: "",
      isMandatory: Constants.yesNoConst.N,
      allowRiskEdit: Constants.yesNoConst.N,
      status: Constants.statusConst.ACT,
    };

    this.selectedAnnexure = {
      annexureId: 0,
      parentId: 0,
      name: "",
      description: "",
      isMandatory: Constants.yesNoConst.N,
      status: Constants.statusConst.ACT,
    };

    this.prevQuestions = [];
    this.questions = [];

    this.selectedQuestion = null;
    this.selectedAnswer = null;

    this.errors = { name: "", description: "", questions: "" };

    this.selectedAnnexureId = 0;
    this.selectedActionStatus = "";
  }
}
