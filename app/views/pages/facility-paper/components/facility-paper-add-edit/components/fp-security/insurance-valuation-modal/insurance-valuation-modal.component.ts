import { Component, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-insurance-valuation-modal',
  templateUrl: './insurance-valuation-modal.component.html',
  styleUrls: ['./insurance-valuation-modal.component.scss']
})
export class InsuranceValuationModalComponent implements OnInit {

 
  insuranceRQ :any;
  action: Subject<any> = new Subject<any>();
  content: any = {};
  customerDTOList:any = []
  constructor(public mdbModalRef: MDBModalRef) { }

  ngOnInit() {
    this.insuranceRQ=  this.content.insuranceRQ 

    this.customerDTOList = this.content.customerDTOList
    // console.log("ad", this.customerDTOList.length)
  }

  getCustomerDetails(customer){
  //  console.log("adada",customer)
    if(customer.customerFinancialID){
      this.insuranceRQ.customerFinacleId = customer.customerFinancialID
    }
    else{
      this.insuranceRQ.customerFinacleId = null
    }
    return this.insuranceRQ
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }
}
