import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, FormControl, Validators } from "@angular/forms";
import { SmeServiceService } from "../../service/sme-service.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { SessionStorage } from "ngx-webstorage";
import { ApplicationService } from "src/app/core/service/application/application.service";

@Component({
  selector: "app-sme-questions",
  templateUrl: "./sme-questions.component.html",
  styleUrls: ["./sme-questions.component.scss"],
})
export class SmeQuestionsComponent implements OnInit {
  @Input() questionsAndAnswers: any[] = []; // Input property to receive questions and answers
  @Output() formSubmitted = new EventEmitter<boolean>(); // Event emitter to notify parent
  questionsForm: FormGroup;
  selectedDescriptions: { [key: number]: any[] } = {}; // Store descriptions for selected answers
  facilityPaperID: string | null = sessionStorage.getItem("facilityPaperID");

  constructor(
    private smeService: SmeServiceService,
    private fb: FormBuilder,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.questionsForm = this.fb.group({});
    this.loadQuestions();

    // // Restore saved answers from localStorage
    // const savedAnswers = this.getSavedAnswers();
    // if (savedAnswers) {
    //   this.questionsForm.patchValue(savedAnswers);
    // }

    // Try to load answers from backend first
    this.getAnswers().then((hasBackendAnswers) => {
      if (!hasBackendAnswers) {
        // If no backend answers, restore from localStorage
        const savedAnswers = this.getSavedAnswers();
        if (savedAnswers) {
          this.questionsForm.patchValue(savedAnswers);
        }
      }
    });

    // Save answers to localStorage on value changes
    this.questionsForm.valueChanges.subscribe((formValues) => {
      this.updateDescriptions(formValues);
      this.saveAnswersToLocalStorage(formValues);
    });
  }

  loadQuestions() {
    if (!this.questionsAndAnswers || this.questionsAndAnswers.length === 0) {
      console.warn("No questions provided via @Input().");
      return;
    }

    // Initialize form controls for each question
    this.questionsAndAnswers.forEach((question) => {
      this.questionsForm.addControl(
        "question_" + question.smeQuestionId,
        new FormControl(null, Validators.required)
      );
    });
  }

  updateDescriptions(formValues: any) {
    this.selectedDescriptions = {}; // Reset descriptions

    Object.keys(formValues).forEach((key) => {
      const questionId = parseInt(key.replace("question_", ""), 10);
      const selectedAnswerId = formValues[key];

      const question = this.questionsAndAnswers.find(
        (q) => q.smeQuestionId === questionId
      );

      if (question) {
        const selectedAnswer = question.smeQuestionAnswerDTOList.find(
          (answer) => answer.smeAnswerId === selectedAnswerId
        );

        if (selectedAnswer && selectedAnswer.smeAnswerDescriptionDTOList) {
          this.selectedDescriptions[questionId] =
            selectedAnswer.smeAnswerDescriptionDTOList;
        }
      }
    });
  }

  // Save answers and descriptions to localStorage
  // private saveAnswersToLocalStorage(formValues: any) {
  //   const dataToSave = Object.keys(formValues).reduce((acc, key) => {
  //     const questionId = parseInt(key.replace('question_', ''), 10);
  //     const selectedAnswerId = formValues[key];
  //     const facilityPaperID = this.facilityPaperID;

  //     const question = this.questionsAndAnswers.find(
  //       (q) => q.smeQuestionId === questionId
  //     );

  //     if (question) {
  //       const selectedAnswer = question.smeQuestionAnswerDTOList.find(
  //         (answer) => answer.smeAnswerId === selectedAnswerId
  //       );

  //       acc[key] = {
  //         selectedAnswerId,
  //         descriptions: selectedAnswer && selectedAnswer.smeAnswerDescriptionDTOList
  //           ? selectedAnswer.smeAnswerDescriptionDTOList
  //           : [],
  //           facilityPaperID
  //       };
  //     }

  //     return acc;
  //   }, {});

  //   localStorage.setItem('smeQuestionsAnswers', JSON.stringify(dataToSave));
  // }

  // // Get saved answers and descriptions from localStorage
  // private getSavedAnswers(): any {
  //   const savedAnswers = localStorage.getItem('smeQuestionsAnswers');
  //   if (savedAnswers) {
  //     const parsedAnswers = JSON.parse(savedAnswers);

  //     // Restore only the selected answer IDs to the form
  //     const formValues = Object.keys(parsedAnswers).reduce((acc, key) => {
  //       acc[key] = parsedAnswers[key].selectedAnswerId;
  //       return acc;
  //     }, {});

  //     // Restore descriptions to `selectedDescriptions`
  //     this.selectedDescriptions = Object.keys(parsedAnswers).reduce((acc, key) => {
  //       const questionId = parseInt(key.replace('question_', ''), 10);
  //       acc[questionId] = parsedAnswers[key].descriptions;
  //       return acc;
  //     }, {});

  //     return formValues;
  //   }

  //   return null;
  // }

  // Save answers and descriptions to localStorage, grouped by facilityPaperID
  private saveAnswersToLocalStorage(formValues: any) {
    const facilityPaperID = this.facilityPaperID;
    let allData = {};

    // Get existing data if any
    const existing = localStorage.getItem("smeQuestionsAnswers");
    if (existing) {
      allData = JSON.parse(existing);
    }

    // Prepare answers for this facilityPaperID
    const answersForThisPaper = Object.keys(formValues).reduce((acc, key) => {
      const questionId = parseInt(key.replace("question_", ""), 10);
      const selectedAnswerId = formValues[key];

      const question = this.questionsAndAnswers.find(
        (q) => q.smeQuestionId === questionId
      );

      if (question) {
        const selectedAnswer = question.smeQuestionAnswerDTOList.find(
          (answer) => answer.smeAnswerId === selectedAnswerId
        );

        acc[key] = {
          selectedAnswerId,
          descriptions:
            selectedAnswer && selectedAnswer.smeAnswerDescriptionDTOList
              ? selectedAnswer.smeAnswerDescriptionDTOList
              : [],
        };
      }
      return acc;
    }, {});

    // Store/overwrite for this facilityPaperID
    allData[facilityPaperID] = answersForThisPaper;
    localStorage.setItem("smeQuestionsAnswers", JSON.stringify(allData));
  }

  // Get saved answers and descriptions from localStorage for the current facilityPaperID
  private getSavedAnswers(): any {
    const facilityPaperID = this.facilityPaperID;
    const savedAnswers = localStorage.getItem("smeQuestionsAnswers");
    if (savedAnswers) {
      const allData = JSON.parse(savedAnswers);
      const answersForThisPaper = allData[facilityPaperID];

      if (answersForThisPaper) {
        // Restore only the selected answer IDs to the form
        const formValues = Object.keys(answersForThisPaper).reduce(
          (acc, key) => {
            acc[key] = answersForThisPaper[key].selectedAnswerId;
            return acc;
          },
          {}
        );

        // Restore descriptions to `selectedDescriptions`
        this.selectedDescriptions = Object.keys(answersForThisPaper).reduce(
          (acc, key) => {
            const questionId = parseInt(key.replace("question_", ""), 10);
            acc[questionId] = answersForThisPaper[key].descriptions;
            return acc;
          },
          {}
        );

        return formValues;
      }
    }
    return null;
  }

  onSubmit() {
    if (this.questionsForm.valid) {
      const formValues = this.questionsForm.value;

      // Construct the payload dynamically
      const payload = this.questionsAndAnswers.map((question) => {
        const smeAnswerId = formValues["question_" + question.smeQuestionId];
        const selectedAnswer = question.smeQuestionAnswerDTOList.find(
          (answer) => answer.smeAnswerId === smeAnswerId
        );

        return {
          answerId: null, // Set to null or dynamically if available
          smeQuestionId: question.smeQuestionId,
          smeAnswerId: smeAnswerId,
          facilityPaperID: this.facilityPaperID, // Replace with the actual facilityPaperID if dynamic
          answer:
            selectedAnswer && selectedAnswer.answer
              ? selectedAnswer.answer
              : "",
          answerDescription: "", // Add description if needed
          createdUserWorkClass:
            this.applicationService.getLoggedInUserUPMGroupCode(),
        };
      });
      // Call the saveOrUpdateAnswer() method in the service
      this.smeService
        .saveOrUpdateAnswer(payload)
        .then((response: any) => {
          console.log("Save/Update Response:", response);
          this.formSubmitted.emit(true); // Notify parent that the form is submitted
        })
        .catch((error: any) => {
          console.error("Error saving answers:", error);
        });
    } else {
      console.log("Please answer all questions.");
    }
  }

  getAnswers(): Promise<boolean> {
    return new Promise((resolve) => {
      this.smeService
        .getAnswerList(this.facilityPaperID)
        .then((response: any) => {
          const loggedInUser =
            this.applicationService.getLoggedInUserDisplayName();
          const userAnswers = response && response[loggedInUser];

          if (
            userAnswers &&
            Array.isArray(userAnswers) &&
            userAnswers.length > 0
          ) {
            // Patch answers to form
            const formValues = {};
            this.selectedDescriptions = {};
            userAnswers.forEach((ans) => {
              const key = "question_" + ans.smeQuestionId;
              formValues[key] = ans.smeAnswerId;
              this.selectedDescriptions[ans.smeQuestionId] =
                ans.answerDescription ? [ans.answerDescription] : [];
            });
            this.questionsForm.patchValue(formValues);
            resolve(true);
          } else {
            resolve(false);
          }
        })
        .catch((error: any) => {
          console.error("Error fetching SME answers:", error);
          resolve(false);
        });
    });
  }
}
