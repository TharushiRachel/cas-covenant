import { Component, Input, OnDestroy, OnInit, SimpleChanges } from "@angular/core";
import { IMyDate, IMyOptions } from "ng-uikit-pro-standard";
import { MatDatepickerInputEvent } from "@angular/material/datepicker";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { Subscription } from "rxjs";
import { GuaranteeVolumeService } from "src/app/core/service/facility-paper/guarantee-volume.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { CurrencyPipe } from "@angular/common";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
@Component({
  selector: "app-customer-guarantee-volumes",
  templateUrl: "./customer-guarantee-volumes.component.html",
  styleUrls: ["./customer-guarantee-volumes.component.scss"],
})
export class CustomerGuaranteeVolumesComponent implements OnInit, OnDestroy{
  @Input("customerData") customerData: any;
  @Input('updateButtonIsEnabled') updateButtonIsEnabled: boolean = false;
  isShowSaveButton: any;
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
  noRecordsAvailableMsg:boolean
  userLevel:any = Constants.applicationSecurityWorkClass
  onYearChangeSub: Subscription = new Subscription();
  constructor(
    private formBuilder: FormBuilder,
    private guaranteeVolumeSerivce: GuaranteeVolumeService,
    private toastr: AlertService,
    private currencyPipe: CurrencyPipe,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
  ) {
    
    this.formErrors = {
      startDate: {},
    };
    this.volumes = null;

    this.years = [
      this.today.getFullYear(),
      this.today.getFullYear() - 1,
      this.today.getFullYear() - 2,
    ];
  }

  ngOnInit() {
    this.noRecordsAvailableMsg = false
    this.yearTypeMessage= this.yearTypeMsg.calender
    this.selectedStartDate = " ";
    this.componentForm = this.createDirectorDetailForm();
    this.finacleId = this.guaranteeVolumeSerivce.selectedFinacleID;
    this.isShowSaveButton = false;   
    // this.getGuaranteeVolumes();
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

  ngOnChanges(changes: SimpleChanges) {
    if (changes.customerData ) {   
      if (this.customerData ) {    
        if (this.volumes == null) {
          this.getGuaranteeVolumes();
        }
      }
    }
  }

  
  ngOnDestroy(): void {
    this.onYearChangeSub.unsubscribe();
  }

  createDirectorDetailForm() {
    return this.formBuilder.group({
      startDate: [null, [Validators.required]],
    });
  }

  getGuaranteeVolumes() {
    
    const requestBody = { facilityPaperId:this.customerData.facilityPaperID, cusId: this.customerData.customerFinacleId };

   // console.log("guaratnee req",requestBody )
    if(requestBody.facilityPaperId == null || requestBody.cusId == null){
      this.noRecordsAvailableMsg = true
    }
    else {
    this.noRecordsAvailableMsg = false
    this.guaranteeVolumeSerivce
      .getFinacaleGuaranteeVolumesDB(requestBody)
      .then((res) => {
        this.volumes = res.bgmVolumeSummary ? res.bgmVolumeSummary : [];
      //  console.log("volumes",this.volumes)
        if (this.volumes.length < 1) {   
          // console.log("volumes",this.volumes)     
          this.volumes = null;
          this.noRecordsAvailableMsg = true          
        }
        else{
          this.volumes = this.sortDataByYear(this.volumes)
          this.noRecordsAvailableMsg = false
        }
       // console.log(" sorted volumes",this.volumes)
      })
      .catch((err) => {
        this.noRecordsAvailableMsg = true
        this.toastr.showToaster("please contact administrator", "ERROR");
      });
    }
  }

  onSubmit(): void {
    this.selectedStartDate = this.componentForm.value.startDate;
    if (this.selectedStartDate != null) {
      const requestBody = Object.assign(
        {},
        {
          custId: this.customerData.customerFinacleId,
          startDate: this.selectedStartDate,
        }
      );

      if(requestBody.custId == null ){
        this.noRecordsAvailableMsg = true
      }else{
      if (this.isFinancialYear) {
        this.guaranteeVolumeSerivce
          .getFinacaleGuaranteeVolumesFinancial(requestBody)
          .then((res) => {
            
            this.volumes = res.bgmVolumeSummary ? res.bgmVolumeSummary : [];
            if (this.volumes.length < 1) {
              this.volumes = null
              this.noRecordsAvailableMsg = true;
              this.isShowSaveButton = false;
            }
            else{
            //  this.volumes =  this.sortDataByYear(this.volumes)
              this.isShowSaveButton = true;
              this.noRecordsAvailableMsg = false;
            }

            //isShowSaveButton this button also should true if the rtesult true
          })
          .catch((err) => {
            this.toastr.showToaster("please contact administrator", "ERROR");
          });
      } else {
        this.guaranteeVolumeSerivce
          .getFinacaleGuaranteeVolumes(requestBody)
          .then((res) => {
            // console.log("res",res)
            this.volumes = res.bgmVolumeSummary ? res.bgmVolumeSummary : [];
            if (this.volumes.length < 1) {
              this.volumes = null
              this.noRecordsAvailableMsg = true;
              this.isShowSaveButton = false;
            } else{
              this.volumes =  this.sortDataByYear(this.volumes)
             
              this.isShowSaveButton = true;
              this.noRecordsAvailableMsg = false;
            }

            //isShowSaveButton this button also should true if the rtesult true
          })
          .catch((err) => {
            this.toastr.showToaster("please contact administrator", "ERROR");
          });
      }
      }
    }
  }

  save() {
    this.isShowSaveButton = false;
    let requestBody = this.volumes;
    requestBody = Object.assign(
      {},
      {
        bgmVolumeSummary: requestBody,
        facilityPaperId: this.customerData.facilityPaperID,
        cusId : this.customerData.customerFinacleId
      }
    );
    
    this.guaranteeVolumeSerivce
      .saveGuaranteeVolumes(requestBody)
      .then((res) => {
        // if result give false this should change to error mesage this toast massage
        this.isShowSaveButton = false;
        this.toastr.showToaster("Save Success", "SUCCESS");
      })
      .catch((err) => {
        this.toastr.showToaster("please contact administrator", "ERROR");
      });
  }

  yearsMapCalenderYear() {
    this.yearOptions = this.years.map((year) => ({ label: year, value: year }));
  }

  yearsMapFinancialYear() {
    this.yearOptions = this.years.map((year) => ({
      label: `${year} - ${year + 1}`,
      value: year,
    }));
  }

  // onCheckboxChange() {
  //   if (this.IsFinancialYear) {
  //     this.yearTypeMessage= this.selectedYearTypeMsg(false)
  //     this.IsFinancialYear = false;
  //     this.yearsMapCalenderYear();
  //   } else {
  //     this.IsFinancialYear = true;
  //     this.yearTypeMessage= this.selectedYearTypeMsg(true)
  //     this.yearsMapFinancialYear();
  //   }
  // }


  setCurrencyFormatValue(value) {
    if(!isNaN(Number(value))){
    let amount =value? value.replace(/,/g, ""): "";
    
    amount = this.currencyPipe.transform(amount, "", "");
   return amount
    }
    return 0;
  }

  isUserHasAccess(){
    
    return  this.applicationService.getLoggedInUserUPMGroupCode() == this.userLevel.ENTERER ? true : false;
   }

   sortDataByYear(volumeList): any {
    return volumeList.sort((a, b) => parseInt(b.year) - parseInt(a.year));
  }
}
