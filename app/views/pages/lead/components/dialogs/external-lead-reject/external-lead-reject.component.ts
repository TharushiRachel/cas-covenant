//Deprecated


// import {Component, OnDestroy, OnInit} from '@angular/core';
// import {FormBuilder, FormGroup, Validators} from "@angular/forms";
// import {MDBModalRef} from "ng-uikit-pro-standard";
// import {AppUtils} from "../../../../../../shared/app.utils";
// import {Subject, Subscription} from "rxjs";
// import {LeadAddEditService} from "../../../services/lead-add-edit.service";
// import {Constants} from "../../../../../../core/setting/constants";
//
// @Component({
//   selector: 'app-external-lead-reject',
//   templateUrl: './external-lead-reject.component.html',
//   styleUrls: ['./external-lead-reject.component.scss']
// })
//
// export class ExternalLeadRejectComponent implements OnInit, OnDestroy {
//
//   componentForm: FormGroup;
//   formErrors: any = {};
//   action: Subject<any> = new Subject<any>();
//
//   onFormValueChangeSub = new Subscription();
//   onLeadStatusUpdatedSub = new Subscription();
//
//   leadStatusConst = Constants.leadStatusConst;
//
//   leadID;
//   createdBy;
//
//   constructor(public  mdbModalRef: MDBModalRef,
//               private formBuilder: FormBuilder,
//               private leadAddEditService: LeadAddEditService,) {
//
//     this.formErrors = {
//       remark: {}
//     };
//   }
//
//   ngOnInit() {
//     this.componentForm = this.createCommentForm();
//
//     this.onLeadStatusUpdatedSub = this.leadAddEditService.onLeadStatusUpdated
//       .subscribe(() => {
//         this.mdbModalRef.hide();
//       });
//
//     this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
//       this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
//     });
//   }
//
//   ngOnDestroy(): void {
//     this.onFormValueChangeSub.unsubscribe();
//     this.onLeadStatusUpdatedSub.unsubscribe();
//     this.action.unsubscribe();
//   }
//
//   createCommentForm() {
//     return this.formBuilder.group({
//       remark: ['', [Validators.required, Validators.maxLength(4000)]],
//     })
//   }
//
//   // onReject() {
//   //
//   //   let data = {
//   //     leadID: this.leadID,
//   //     // leadStatus: this.leadStatusConst.REJECTED,
//   //     remark: this.componentForm.controls.remark.value,
//   //     assignUserID: this.createdBy
//   //   };
//   //
//   //   this.leadAddEditService.updateLeadStatusOrAssignee(data);
//   //   this.action.next(true);
//   //   this.mdbModalRef.hide();
//   // }
//
//   isValid() {
//     return this.componentForm.valid
//   }
//
// }
