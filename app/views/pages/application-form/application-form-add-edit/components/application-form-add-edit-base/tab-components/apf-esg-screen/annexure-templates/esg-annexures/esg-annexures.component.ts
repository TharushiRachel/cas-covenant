// import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
// import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
// import * as _ from 'lodash';
// import { Subscription } from 'rxjs';
// import { AlertService } from 'src/app/core/service/common/alert.service';
// import { SETTINGS } from 'src/app/core/setting/commons.settings';
// import { AFAnnexureAnswerDTO, AFAnnexureDTO, AFAnnexureQuestionDTO } from 'src/app/views/pages/application-form/application-form-add-edit/dtos/apf-annexure-dto';
// import { ApplicationFormAddEditService } from 'src/app/views/pages/application-form/application-form-add-edit/services/application-form-add-edit.service';

// @Component({
//   selector: 'app-esg-annexures',
//   templateUrl: './esg-annexures.component.html',
//   styleUrls: ['./esg-annexures.component.scss']
// })
// export class EsgAnnexuresComponent implements OnInit {

//   @Input() annexureId: number;
//   @Output() submitted = new EventEmitter<any>();
//   @Input() applicationForm: any = {};
//   @Input() existingData: any;
//   @Input() editMode: boolean;

//   form: FormGroup;
//   questions: AFAnnexureQuestionDTO[] = [];
//   answers: AFAnnexureAnswerDTO[] = [];
//   loading = true;
//   annexureData: any;
//   onApplicationFormChangeSub = new Subscription();

//   constructor(private applicationFormAddEditService: ApplicationFormAddEditService,
//     private fb: FormBuilder,
//     private alertService: AlertService) { }

//   ngOnInit() {
//     this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormChange.subscribe((data: any) => {
//       if (!_.isEmpty(data)) {
//         this.applicationForm = data;
//       }
//     });

//     const isNewAnnexure = !this.existingData;

//     if (this.editMode && this.existingData) {
//       this.loadEditedStructure();
//     } else if (isNewAnnexure) {
//       this.loadAnnexureStructure(true);  // pass true to force form enabled
//     } else {
//       this.loadAnnexureStructure(false);
//     }
//   }

//   ngOnChanges(changes: SimpleChanges): void {
//     if (changes.editMode && !changes.editMode.firstChange && this.form) {
//       if (this.editMode) {
//         this.enableFormControls();
//       } else {
//         this.disableFormControls();
//       }
//     }
//   }

//   loadAnnexureStructure(enableForm: boolean = false): void {
//     this.applicationFormAddEditService.getAnnexureById(this.annexureId).then(annexureData => {
//       if (!annexureData) {
//         this.alertService.showToaster("Failed to load Annexure Data", SETTINGS.TOASTER_MESSAGES.error)
//         return;
//       }
//       this.annexureData = annexureData;
//       this.questions = annexureData.questions
//         .sort((a, b) => a.displayOrder - b.displayOrder)
//         .map(q => ({
//           ...q,
//           answers: (q.answers || []).sort((a, b) => a.displayOrder - b.displayOrder)
//         }));

//       this.form = this.buildForm();

//       if (this.existingData) {
//         this.patchAnswers(this.existingData.afAnnexureQuestionDataDTOList);
//       }

//       if (this.existingData && !this.editMode) {
//         this.disableFormControls();
//       } else {
//         this.enableFormControls();
//       }


//       this.loading = false;

//     }).catch(err => {
//       this.alertService.showToaster("Failed to load Annexure Data", SETTINGS.TOASTER_MESSAGES.error)
//     });
//   }

//   loadEditedStructure(): void {
//     this.annexureData = this.existingData;

//     this.questions = (this.existingData.questions || [])
//       .sort((a, b) => a.displayOrder - b.displayOrder)
//       .map(q => ({
//         ...q,
//         answers: (q.answers || []).sort((a, b) => a.displayOrder - b.displayOrder)
//       }));

//     this.form = this.buildForm();

//     this.patchAnswers(this.existingData.questions);

//     if (this.existingData && !this.editMode) {
//       this.disableFormControls();
//     } else {
//       this.enableFormControls();
//     }

//     this.loading = false;
//   }

//   private buildForm(): FormGroup {
//     const group: any = {};

//     this.questions.forEach(q => {
//       const validators = q.isMandatory === 'Y' ? [Validators.required] : [];

//       if (q.answerType === 'MCQ_WITH_MULTIPLE') {
//         group[q.questionId] = new FormControl([]);
//       } else if (q.answerType === 'DESCRIPTIVE_WITH_SUB_QUESTION') {
//         q.answers.forEach(a => {
//           const subKey = `${q.questionId}_${a.answerId}`;
//           group[subKey] = new FormControl('', validators);
//         });
//       } else {
//         group[q.questionId] = new FormControl('', validators);
//       }
//     });

//     return this.fb.group(group);
//   }

//   patchExistingAnswers(): void {
//     if (!this.existingData.afAnnexureQuestionDataDTOList || !this.form) return;

//     this.patchAnswers(this.existingData.afAnnexureQuestionDataDTOList);

//   }

//   patchUserAnswers(): void {
//     if (!this.existingData.questions || !this.form) return;

//     this.patchAnswers(this.existingData.questions);

//   }

//   private patchAnswers(questions: any[]): void {
//     questions.forEach(q => {
//       if (q.answerType === 'DESCRIPTIVE_WITH_SUB_QUESTION') {
//         (q.afAnnexureAnswerDataDTOList || []).forEach(a => {
//           const control = this.form.get(`${q.questionId}_${a.answerId}`);
//           if (control) {
//             control.setValue(a.answer);
//           }
//         });
//       } else {
//         const control = this.form.get(q.questionId.toString());
//         if (!control) return;

//         if (q.answerType === 'MCQ_WITH_MULTIPLE') {
//           control.setValue((q.afAnnexureAnswerDataDTOList || []).map(a => a.answerId));
//         } else if (q.answerType === 'MCQ_WITH_SINGLE') {
//           control.setValue(q.afAnnexureAnswerDataDTOList[0].answerId || '');
//         } else if (q.answerType === 'DESCRIPTIVE') {
//           control.setValue(q.afAnnexureAnswerDataDTOList[0].answer || '');
//         }
//       }
//     });
//   }

//   onSubmit(): void {
//     if (this.form.invalid) return;
//     this.applicationFormAddEditService.saveAnnexureAnswer(this.buildPayload()).then(res => {
//       this.submitted.emit({ annexureId: this.annexureId, action: 'submit'});
//     }).catch(err => {
//       this.alertService.showToaster("Failed to save Annexure Data", SETTINGS.TOASTER_MESSAGES.error);
//     });
//   }

//   onUpdate(): void {
//     if (this.form.invalid) return;
//     const annexureDataId = this.existingData.annexureDataId;

//     this.applicationFormAddEditService.updateAnnexureAnswer(annexureDataId, this.buildPayload()).then(res => {
//       this.submitted.emit({ annexureId: this.annexureId });
//     }).catch(err => {
//       this.alertService.showToaster("Failed to Update Annexure Data", SETTINGS.TOASTER_MESSAGES.error);
//     });
//   }

//   onCancel(): void {
//     this.submitted.emit({ annexureId: this.annexureId, action: 'cancel'});
//   }

//   onCheckboxArrayChange(event: any, questionId: number, answerId: number): void {
//     const selectedAnswers = this.form.get(questionId.toString()).value || [];

//     if (event.target.checked) {
//       if (!selectedAnswers.includes(answerId)) {
//         selectedAnswers.push(answerId);
//       }
//     } else {
//       const index = selectedAnswers.indexOf(answerId);
//       if (index > -1) {
//         selectedAnswers.splice(index, 1);
//       }
//     }

//     this.form.get(questionId.toString()).setValue(selectedAnswers);
//   }

//   buildPayload(): any {
//     return {
//       applicationFormID: this.applicationForm.applicationFormID,
//       annexureId: this.annexureId,
//       afAnnexureQuestionDataDTOList: this.questions.map(q => {
//         let afAnnexureAnswerDataDTOList = [];

//         const control = this.form.get(q.questionId.toString());

//         if (q.answerType === 'MCQ_WITH_SINGLE') {
//           const selectedAnswer = q.answers.find(a => a.answerId === control.value);
//           if (selectedAnswer) {
//             afAnnexureAnswerDataDTOList.push({
//               answerId: selectedAnswer.answerId,
//               questionId: q.questionId,
//               answer: selectedAnswer.answer,
//               displayOrder: selectedAnswer.displayOrder
//             });
//           }
//         } else if (q.answerType === 'MCQ_WITH_MULTIPLE') {
//           afAnnexureAnswerDataDTOList = (control.value || []).map((id: number) => {
//             const answerObj = q.answers.find(a => a.answerId === id);
//             return {
//               answerId: answerObj.answerId,
//               questionId: q.questionId,
//               answer: answerObj.answer,
//               displayOrder: answerObj.displayOrder
//             };
//           });
//         } else if (q.answerType === 'DESCRIPTIVE') {
//           afAnnexureAnswerDataDTOList.push({
//             answerId: null,
//             questionId: q.questionId,
//             answer: control.value,
//             displayOrder: 0
//           });
//         } else if (q.answerType === 'DESCRIPTIVE_WITH_SUB_QUESTION') {
//           q.answers.forEach(a => {
//             const subControl = this.form.get(`${q.questionId}_${a.answerId}`);
//             afAnnexureAnswerDataDTOList.push({
//               answerId: a.answerId,
//               questionId: q.questionId,
//               answer: subControl.value,
//               displayOrder: a.displayOrder
//             });
//           });
//         }

//         return {
//           questionId: q.questionId,
//           annexureId: q.annexureId,
//           question: q.question,
//           answerType: q.answerType,
//           isMandatory: q.isMandatory,
//           displayOrder: q.displayOrder,
//           afAnnexureAnswerDataDTOList
//         };
//       })
//     };
//   }

//   enableFormControls(): void {
//     Object.keys(this.form.controls).forEach(controlName => {
//       this.form.get(controlName).enable();
//     });
//   }

//   disableFormControls(): void {
//     Object.keys(this.form.controls).forEach(controlName => {
//       this.form.get(controlName).disable();
//     });
//   }

// }
