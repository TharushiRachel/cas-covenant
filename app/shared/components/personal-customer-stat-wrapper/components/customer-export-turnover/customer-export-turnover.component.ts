import { ChangeDetectorRef, Component, Input, OnDestroy, OnInit, SimpleChanges } from "@angular/core";
import { IMyDate, IMyOptions } from "ng-uikit-pro-standard";
import { MatDatepickerInputEvent } from "@angular/material/datepicker";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} 
from "@angular/forms";
import { Subscription } from "rxjs";
import { GuaranteeVolumeService } from "src/app/core/service/facility-paper/guarantee-volume.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { CurrencyPipe } from "@angular/common";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { CasCustomerService } from "src/app/core/service/data/cas-customer.service";
import { FinacleService } from "src/app/core/service/facility-paper/finacle.service";
import { request } from "http";
@Component({
  selector: "app-customer-export-turnover",
  templateUrl: "./customer-export-turnover.component.html",
  styleUrls: ["./customer-export-turnover.component.scss"],
})
export class CustomerExportTurnoverComponent implements OnInit, OnDestroy{
  @Input("customerData") customerData: any;
  @Input('updateButtonIsEnabled') updateButtonIsEnabled: boolean = false;
  isShowSaveButton: boolean = false
  today = new Date();
  facilityPaperId: any;
  formErrors: any = {};
  onFormStartDateChangeSub: Subscription = new Subscription();
  componentForm: FormGroup;
  selectedStartDate: any;
  finacleId: any;
  selectedDate: string | null;
  volumes: any = null;
  yearOptions: any;
  isFinancialYear: any = null;
  years: any;
  yearTypeMsg :any = {financial:"Select Financial Year", calender:"Select Calender Year"}
  yearTypeMessage:any 
  noRecordsAvailableMsg:boolean= true
  userLevel:any = Constants.applicationSecurityWorkClass
  onYearChangeSub: Subscription = new Subscription();
  turnoverAccounts :any [] = []
  turnoverAccountsWithValue:any
  selectedYearArray: any[]
  exportDataArray : { [key: number]: any } = {};
  loadingStatus:any
  selectedAccount:any
  loading: { [key: number]: boolean } = {};
  yearData = {};
  selectedYearArrayWithLable :any[] = []
  isDisablesGetButton : boolean = false;
  isTableShow :boolean = false;

exportTypes = { exportBill : "EXPORT_BILL", exportDc : "EXPORT_DC", exportInward: "EXPORT_INWARD"}
accountNumberDB = "-"
createdDate = "-"
currencyArray : any[]=[]
  constructor(
    private formBuilder: FormBuilder,
    private guaranteeVolumeSerivce: GuaranteeVolumeService,
    private toastr: AlertService,
    private currencyPipe: CurrencyPipe,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private casCustomerService: CasCustomerService,
    private fincacleService: FinacleService,
    private cdr: ChangeDetectorRef
  ) {
    
    this.formErrors = {
      startDate: {},
      account: {},
    };
    this.volumes = null;

    this.years = [     
      this.today.getFullYear() - 2,
      this.today.getFullYear() - 1,
      this.today.getFullYear(),
    ];

    this.exportDataArray = {}
  }

  ngOnInit() {

    this.yearTypeMessage= this.yearTypeMsg.calender
    this.selectedStartDate = " ";
    this.componentForm = this.createDirectorDetailForm();
   
    this.finacleId = this.guaranteeVolumeSerivce.selectedFinacleID;
    this.isShowSaveButton = false;   
  
    this.onYearChangeSub = this.facilityPaperAddEditService.onYearTypeChange.subscribe( (data:any)=>{
      
      this.isFinancialYear = data?(data ==Constants.yesNoConst.N?false : true) : false
      if(this.isFinancialYear){
        this.yearsMapFinancialYear();
      }
      else{
        this.yearsMapCalenderYear();
        
      }
    })
   
   
  }

  getCurrencyArray( year:any){
    
   let data = this.exportDataArray[year]
   if(data.length>0)
    return [...new Set(data.map(item => item.billCurrencyCode))];
  else
    return []
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.customerData || changes.updateButtonIsEnabled) { 
      
      if ( Object.keys(this.exportDataArray).length === 0) {
        this.getExportData()}
        
        
      
    }
  }

  
  ngOnDestroy(): void {
    this.onYearChangeSub.unsubscribe();
  }

  createDirectorDetailForm() {
    return this.formBuilder.group({
      startDate: [null, [Validators.required]]
    });
  }


  getExportData(){
  
    let data = null;
    this.noRecordsAvailableMsg = false 
    const requestBody = { facilityPaperId:this.customerData.facilityPaperID, cusId: this.customerData.customerFinacleId };
  
    if(requestBody.facilityPaperId == null || requestBody.cusId == null){
      this.noRecordsAvailableMsg = true
    }
    else {
      
      this.fincacleService.getExportDataDB(requestBody).then((res)=>{
      data = res? res: [];
      
      if (data.length < 1) {        
        this.exportDataArray = null
        this.noRecordsAvailableMsg = true 
        this.isTableShow = false;         
      }
      else{
        this.arrangeExportData(data)
        this.noRecordsAvailableMsg = false  
        this.isTableShow = true;
        
        this.getCreatedDateAndAccount()
      }

     
     
    }).catch((err) => {
     
          this.noRecordsAvailableMsg = true
          this.isTableShow = false;
          this.toastr.showToaster("please contact administrator", "ERROR");
        });
  
    }
   
  }

  arrangeExportData(data:any){
   
    const groupedData = data.reduce((acc, item) => {
      const { year } = item;
      if (!acc[year]) {
        acc[year] = [];
      }
      acc[year].push(item);
      return acc;
    }, {});



    const numberOfYears = Object.keys(groupedData);
    
    if (!Array.isArray(this.yearOptions)) {
      throw new Error('Expected yearOptions to be an array');
    }
   const filteredArray = this.yearOptions.filter(item => numberOfYears.includes(item.label)) || [];
    this.selectedYearArray = filteredArray || []

      this.selectedYearArray.forEach(year => {
      this.loading[year.value] = false;
    });
   
      this.selectedYearArray.forEach(year => {
      this.exportDataArray[year.value] = groupedData[year.label] || []
    });
  
  }

 async onSubmit(){
    
    this.selectedStartDate = this.componentForm.value.startDate;
    this.selectedAccount = this.customerData.bankAccountNumber || (this.customerData.defaultAccount|| null)
    this.createdDate = this.getCurrentDate()
    this.accountNumberDB = this.selectedAccount
    if (this.selectedAccount){
    if (this.selectedStartDate != null) {
      this.isDisablesGetButton = true;
      this.isShowSaveButton = false;
      this.noRecordsAvailableMsg = false
      this.selectedYearArray = this.yearOptions.filter(year=> year.value >= this.selectedStartDate)
      this.exportDataArray ={}

      this.selectedYearArray.forEach(year => {
        this.loading[year.value] = true
        });
    
        this.isTableShow = true;
      for(let year of this.selectedYearArray) {
         
          const reqestBody = {accountId: this.selectedAccount,startDate : year.value  }

    
          try{
            let data = []
            if(this.isFinancialYear){
           
             data = await this.fincacleService.getExportDataFinancial(reqestBody);
            }else{
              data = await this.fincacleService.getExportDataCalender(reqestBody);
            }

           if( Array.isArray(data)){
             this.exportDataArray[year.value] = data.length>0? data :[]
            if(data.length>0){
            // this.isShowSaveButton = true
             this.noRecordsAvailableMsg = false  
             this.getCreatedDateAndAccount()
            }
          }
          else {
          
            this.toastr.showToaster("Unable to Load Data", "ERROR");
            this.isDisablesGetButton = false;
            break;
          }
          

        }
          catch (error){
            this.isDisablesGetButton = false;
            this.toastr.showToaster("please contact administrator", "ERROR");
            break;
          }

          
          this.loading[year.value] = false;
        
          this.cdr.detectChanges();
          
        }
       
        if(!this.hasNonEmptyArrays(this.exportDataArray)){
         
          this.isTableShow = false;
          this.isShowSaveButton = false
          this.noRecordsAvailableMsg = true        
          
        }
        else {
          this.isShowSaveButton = true;
        }
        this.isDisablesGetButton = false;
        
        this.cdr.detectChanges();
       
    }
  }
  else {
    this.toastr.showToaster("Customer Account Number Not Found", "ERROR");
  }
  }



  save() {
    // this.isShowSaveButton = false;

    const exportArray = Object.values(this.exportDataArray) || [];
    let exportDto=[];
    exportArray.forEach(element => {
      exportDto = [...exportDto, ...element]
    });

    const requestBody = Object.assign(
      {},
      {
        exportTurnOverData: exportDto,
        facilityPaperId: this.customerData.facilityPaperID,
        cusId : this.customerData.customerFinacleId,    
      }
    );
    
    this.fincacleService
      .saveExportData(requestBody)
      .then((res) => {
        // if result give false this should change to error mesage this toast massage
        if(typeof res === 'boolean' && res){
        this.isShowSaveButton = false;
        this.toastr.showToaster("Save Success", "SUCCESS");
      }
        else
        {
          this.isShowSaveButton = true;
          this.toastr.showToaster("Save failed", "ERROR");
        }
      })
      .catch((err) => {
        this.toastr.showToaster("please contact administrator", "ERROR");
      });
  }

  yearsMapCalenderYear() {

    this.yearOptions = this.years.map((year) => ({ label: year.toString(), value: year }));
    
  }

  yearsMapFinancialYear() {
    this.yearOptions = this.years.map((year) => ({
      label: `${year}-${year + 1}`,
      value: year,
    }));
    
  }





  isUserHasAccess(){
    
    return  this.applicationService.getLoggedInUserUPMGroupCode() == this.userLevel.ENTERER ? true : false;
   }

   sortDataByYear(volumeList): any {
    return volumeList.sort((a, b) => parseInt(b.year) - parseInt(a.year));
  }


  getTTTurnoverAccounts() {
   
    // let requestBody =  this.customerData.bankAccountNumber 
     let requestBody =  this.customerData.bankAccountNumber || (this.customerData.defaultAccount|| null)
 
    if (requestBody) {
      this.casCustomerService
        .getTTTunroverAccounts(requestBody)
        .then((res) => {         
          this.turnoverAccounts = res ? res : [];
          if (this.turnoverAccounts.length<1){
            this.isDisablesGetButton = true;
            this.toastr.showToaster(
              "Customer Accounts Unavailable",
              "ERROR"
            );
          }
          
          this.ttTurnoverAccountMap()
        })
        .catch((err) => {
          this.isDisablesGetButton = true;
          this.toastr.showToaster(
            "please contact administrator",
            "ERROR"
          );
        });
    }
    
  }

  ttTurnoverAccountMap(){
    this.turnoverAccountsWithValue = this.turnoverAccounts.map(account => ({ label:account, value: account }));
  }


  getExportValue(year:any,currencyCode:any,exportType:any, billType:any){ 
  
    
   return this.exportDataArray[year]?this.setCurrencyFormatValue(( this.exportDataArray[year].find(data=> data.turnOverType === exportType && data.billCurrencyCode === currencyCode )),billType,currencyCode):null
 
  }

  setCurrencyFormatValue(object: Record<string, any>, fieldName: string , currencyCode) {
  
    let value = ''
    if(object){
      value = object[fieldName]
      
    }
    
    if(value && !isNaN(Number(value))){
      
    
      value =fieldName !="convertedAmount"? this.currencyPipe.transform(value, `${currencyCode} `, "code"):this.currencyPipe.transform(value, "","");
    return value
    }
    return undefined;
  }
 
  
  getTotalValueFC(year:any,currencyCode: any){
    
      let value = this.exportDataArray[year]? this.exportDataArray[year]
      .filter(transaction => transaction.billCurrencyCode === currencyCode)  
      .reduce((acc, transaction) => acc + transaction.billAmount, 0):null

      return value? this.currencyPipe.transform(value, `${currencyCode} `, "code") : null
  }

  getTotalValueLKR(year:any){

  let value = this.exportDataArray[year]? this.exportDataArray[year].reduce((acc, transaction) => {
      return acc + transaction.convertedAmount; }, 0):null

      return value? this.currencyPipe.transform(value, "", "") : null
  }


  


  sortYearsDescending(years: string[]): string[] {
    return years.sort((a, b) => { 
      return Number(b) - Number(a);  
    });
  }


  disableGetButton(){
   return !this.componentForm.valid || this.selectedStartDate == null || this.isDisablesGetButton
  }

 hasNonEmptyArrays(obj: { [key: number]: any }): boolean {
    return Object.values(obj).some(value =>  Array.isArray(value)? value.length>0 : false);
}

getCreatedDateAndAccount(){

 
  const exportArray = Object.values(this.exportDataArray);
    let exportDto=[];
    exportArray.forEach(element => { if(element){
      exportDto = [...exportDto, ...element]}
    });
    this.createdDate = exportDto.find(obj => obj.createdDate)?exportDto.find(obj => obj.createdDate).createdDate : "-"
    this.accountNumberDB = exportDto.find(obj => obj.foracid)?exportDto.find(obj => obj.foracid).foracid : "-"
}

 getCurrentDate(): any {
  const today = new Date();
  const day = String(today.getDate()).padStart(2, '0');
  const month = String(today.getMonth() + 1).padStart(2, '0'); // Months are zero-based
  const year = today.getFullYear();
  
  return `${day}-${month}-${year}`;
}
}
