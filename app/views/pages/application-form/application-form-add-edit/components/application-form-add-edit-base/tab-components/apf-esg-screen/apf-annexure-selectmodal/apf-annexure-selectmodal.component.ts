// import { Component, Input, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { MDBModalRef } from 'ng-uikit-pro-standard';
// import { Subject, Subscription } from 'rxjs';
// import { Constants } from 'src/app/core/setting/constants';
// import { AppUtils } from 'src/app/shared/app.utils';

// @Component({
//   selector: 'app-apf-annexure-selectmodal',
//   templateUrl: './apf-annexure-selectmodal.component.html',
//   styleUrls: ['./apf-annexure-selectmodal.component.scss']
// })
// export class ApfAnnexureSelectmodalComponent implements OnInit {

//   @Input() availableAnnexures: any[];
//   action: Subject<any> = new Subject<any>();
//   componentForm: FormGroup;
//   onFormValueChangeSub: Subscription = new Subscription();
//   formErrors: any;
//   annexureOptions: any[] = [];

//   constructor(private mdbModalRef: MDBModalRef, private formBuilder: FormBuilder) { }

//   ngOnInit() {
//     this.formErrors = { annexureId: {} };
  
//     this.componentForm = this.formBuilder.group({
//       annexureId: ['', Validators.required],
//     });
  
//     this.annexureOptions = this.availableAnnexures || [];

//     this.onFormValueChangeSub.unsubscribe();
//     this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(() => {
//       this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
//     });
//   }

//   isValid() {
//     return this.componentForm.valid;
//   }

//   onClose(): void {
//     this.action.next(null);
//     this.mdbModalRef.hide();
//   }

//   onCreate() {
//     const selected = this.componentForm.get('annexureId').value;
//     this.action.next(selected);
//     this.mdbModalRef.hide();
//   }


// }
