import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FacilityPaperAddEditService} from "../../../../services/facility-paper-add-edit.service";
import {ApplicationService} from "../../../../../../../core/service/application/application.service";
import {AppUtils} from "../../../../../../../shared/app.utils";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-fp-comment',
  templateUrl: './fp-comment.component.html',
  styleUrls: ['./fp-comment.component.scss']
})
export class FpCommentComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any = {};
  onFormValueChangeSub = new Subscription();

  content: any = {};

  constructor(
    public  mdbModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private formBuilder: FormBuilder,
    private applicationService: ApplicationService
  ) {

    this.formErrors = {
      comment: {}
    };
  }

  ngOnInit() {
    this.componentForm = this.createCommentForm();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {

  }

  createCommentForm() {
    return this.formBuilder.group({
      comment: ['', [Validators.required, Validators.maxLength(3999)]],
    })
  }

  submitComment(fpCommentID) {
    let displayName = this.applicationService.getLoggedInUserDisplayName();
    let upmID = this.applicationService.getLoggedInUserUserID() ? this.applicationService.getLoggedInUserUserID() : null;


    let data = Object.assign({},
      {facilityPaperID: this.content.facilityPaper.facilityPaperID},
      {displayName: displayName},
      {upmID: upmID},
      {
        fpCommentDTOList: [Object.assign({}, {
            fpCommentID: fpCommentID ? fpCommentID : null,
            facilityPaperID: this.content.facilityPaper.facilityPaperID,
            comment: this.componentForm.controls.comment.value,
            status: 'ACT',
          }
        )]
      });

    this.facilityPaperAddEditService.addEditComment(data);
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.componentForm.valid
  }
}
