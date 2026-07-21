import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-cov-submit',
  templateUrl: './cov-submit.component.html',
  styleUrls: ['./cov-submit.component.scss']
})
export class CovSubmitComponent implements OnInit {

  @Output() submitComment = new EventEmitter<string>(); // Event to emit comment
  componentForm: FormGroup;
  coveringApproval: any = {};

  constructor(private fb: FormBuilder,
    public mdbModalRef: MDBModalRef,
  ) {
    this.componentForm = this.fb.group({
      comment: new FormControl('') 
    });
  }

  ngOnInit() {

    //console.log("covrtin",this.coveringApproval);
  }

  onSubmit() {
    const comment = this.componentForm.get('comment') ? this.componentForm.get('comment').value : '';
    this.submitComment.emit(comment); 
    this.mdbModalRef.hide(); 
  }

}
