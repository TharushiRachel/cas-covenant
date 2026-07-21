import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import * as _ from "lodash";
import {Constants} from "../../../core/setting/constants";
import {CustomerRatingsDto}          from "../../../views/pages/facility-paper/dto/customer-ratings-dto";

@Component({
  selector: 'app-preview-personal-customer-ratings',
  templateUrl: './preview-personal-customer-ratings.component.html',
  styleUrls: ['./preview-personal-customer-ratings.component.scss']
})
export class PreviewPersonalCustomerRatingsComponent implements OnInit {

  @Input('customer') customer: any = {};
  @Input('facilityPaper') facilityPaper: any = {};
  customerRatingsDTO: CustomerRatingsDto = new CustomerRatingsDto({});

  componentForm: FormGroup;
  isAbleToAddEdit = false;
  isDisableEdit = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  constructor(
    private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit() {

       if(this.customer.customerRatingsDTOList.length > 0) {
          this.customerRatingsDTO =  this.customer.customerRatingsDTOList[0];
       }
       this.getCustomerRatings();
  }

 loadInitialComponentForm(){
    this.componentForm.disable();
    this.isDisableEdit = true;
 }

 createCustomerRatingsForm() {
    let customerRatingsID = this.customerRatingsDTO.customerRatingsID || '';
    let customerID = this.customerRatingsDTO.customerID || '';
    let casCustomerID = this.customerRatingsDTO.customerID || '';
    let proposedFacilitiesROA = this.customerRatingsDTO.proposedFacilitiesROA || '';
    let existingFacilitiesROA = this.customerRatingsDTO.existingFacilitiesROA || '';
    let riskGrading = this.customerRatingsDTO.riskGrading || '';
    let riskScore= this.customerRatingsDTO.riskScore || '';

    return this.formBuilder.group({
                   customerRatingsID: [{value: customerRatingsID, disabled: this.isDisableEdit}, []],
                   customerID: [{value: customerID, disabled: this.isDisableEdit}, []],
                   casCustomerID: [{value: casCustomerID, disabled: this.isDisableEdit}, []],
                   proposedFacilitiesROA: [{value: proposedFacilitiesROA, disabled: this.isDisableEdit}, []],
                   existingFacilitiesROA: [{value: existingFacilitiesROA, disabled: this.isDisableEdit}, []],
                   riskGrading: [{value: riskGrading, disabled: this.isDisableEdit}, []],
                   riskScore: [{value: riskScore, disabled: this.isDisableEdit}, []],
               })
  }


  getCustomerRatings(){

     if (this.componentForm) {
       this.loadInitialComponentForm();
     }
     this.componentForm = this.createCustomerRatingsForm();
     this.loadInitialComponentForm();
  }


}
