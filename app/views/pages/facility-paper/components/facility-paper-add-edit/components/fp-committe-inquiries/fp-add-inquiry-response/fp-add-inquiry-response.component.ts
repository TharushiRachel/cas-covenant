import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-fp-add-inquiry-response',
  templateUrl: './fp-add-inquiry-response.component.html',
  styleUrls: ['./fp-add-inquiry-response.component.scss']
})
export class FpAddInquiryResponseComponent  {

  // @Input() inquiry: any;
  // @Output() action = new EventEmitter<any>();

  // componentForm: FormGroup;
  // editorContent: string = '';

  // constructor(private mdbModalRef: MDBModalRef, private fb: FormBuilder) { }

  // ngOnInit() {
  //   this.componentForm = this.fb.group({
  //     userId: ['', Validators.required],
  //     userComment: ['']
  //   });
  // }

  // onClose(): void {
  //   this.action.next(null);
  //   this.mdbModalRef.hide();
  // }

  // onSave(): void {
  //   if (this.editorContent && this.componentForm.valid) {
  //     const { userId } = this.componentForm.value;
  //     this.action.emit({
  //       id: Date.now(), // fake ID for dummy data
  //       userId,
  //       userName: this.getUserName(userId),
  //       comment: this.editorContent,
  //       createdAt: new Date()
  //     });
  //     this.mdbModalRef.hide();
  //   }
  // }

  // private getUserName(id: number): string {
  //   const options = [
  //     { value: 1, label: 'John Smith' },
  //     { value: 2, label: 'Jane Doe' }
  //   ];
  //   return options.find(u => u.value === id).label || '';
  // }

}
