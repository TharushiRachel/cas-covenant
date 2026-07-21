import { CurrencyPipe } from '@angular/common';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { FinacleService } from 'src/app/core/service/facility-paper/finacle.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';

@Component({
  selector: 'app-customer-insurance-valuation',
  templateUrl: './customer-insurance-valuation.component.html',
  styleUrls: ['./customer-insurance-valuation.component.scss']
})
export class CustomerInsuranceValuationComponent implements OnInit {

  @Input("insuranceParams") insuranceParams: any ;
  @Input("mode") mode: any = '' ;
  @Input('updateButtonIsEnabled') updateButtonIsEnabled: boolean = false;
  showNoRecordsMsg:boolean = false
  insuranceValuationDetails:any = null
  userLevel = Constants.applicationSecurityWorkClass;
  constructor(private toastr: AlertService, private insuranceService : FinacleService, private currencyPipe: CurrencyPipe,private applicationService: ApplicationService) { 
    this.insuranceValuationDetails = null
  }

  ngOnInit() {  
  }

  ngOnChanges(changes: SimpleChanges) {
    if ((changes.insuranceParams && this.insuranceParams) || this.updateButtonIsEnabled ) {
     
        if(this.insuranceValuationDetails == null ){
         
        this.getInsuranceDetails()
      
      }
      
    }
  }

  getInsuranceDetails(){
    const requestBody = {cusId:this.insuranceParams.customerFinacleId , facilityPaperId:this.insuranceParams.facilityPaperID}
    if(requestBody.cusId == null || requestBody.facilityPaperId == null){
      this.showNoRecordsMsg= true
      this.insuranceValuationDetails=null
    }
    else{
 
       if(this.mode == "edit" && this.updateButtonIsEnabled){
    this.insuranceService.getInsuranceDetails(requestBody).then((res)=>{  
        this.insuranceValuationDetails=res?res:[];
        if(this.insuranceValuationDetails.length<1){
          this.showNoRecordsMsg= true
          this.insuranceValuationDetails=null
        }

    }).catch((err)=>{
      this.toastr.showToaster("please contact administrator", "ERROR")
    })

    }
    else {
      this.insuranceService.getInsuranceDetailsDB(requestBody).then((res)=>{  
        this.insuranceValuationDetails=res?res:[];
        if(this.insuranceValuationDetails.length<1){
          this.showNoRecordsMsg= true
          this.insuranceValuationDetails=null
        }

    }).catch((err)=>{
      this.toastr.showToaster("please contact administrator", "ERROR")
    })
    }
  }


  }

  refresh(){
    const requestBody = {cusId:this.insuranceParams.customerFinacleId , facilityPaperId:this.insuranceParams.facilityPaperID}
    if(requestBody.cusId == null || requestBody.facilityPaperId == null){
      this.showNoRecordsMsg= true
      this.insuranceValuationDetails=null
    }
    else{
    this.insuranceService.refreshInsuranceDetails(requestBody).then((res)=>{
      
        this.insuranceValuationDetails=res?res:[];
        this.showNoRecordsMsg= false
        if(this.insuranceValuationDetails.length<1){
          this.insuranceValuationDetails=null
          this.showNoRecordsMsg= true
        }
        
    }).catch((err)=>{
      this.showNoRecordsMsg= true
      this.toastr.showToaster("please contact administrator", "ERROR")
    })
  }
  }

  setCurrencyFormatValue(value) {
    if(!isNaN(Number(value))){
    let amount =value? value.replace(/,/g, ""): "";
    
    amount = this.currencyPipe.transform(amount, "", "");
   return amount
    }
    return 0;
  }

}
