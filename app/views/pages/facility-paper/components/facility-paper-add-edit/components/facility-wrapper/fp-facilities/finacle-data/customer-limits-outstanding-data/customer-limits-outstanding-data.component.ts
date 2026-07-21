import { CurrencyPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { Subject } from 'rxjs';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-customer-limits-outstanding-data',
  templateUrl: './customer-limits-outstanding-data.component.html',
  styleUrls: ['./customer-limits-outstanding-data.component.scss']
})
export class CustomerLimitsOutstandingDataComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any = {};
  content: any;
  showTale: boolean;
  action: Subject<any> = new Subject<any>();
  loanData: any;
  loans: any;
  limits: any;
  showAccNotSelected: boolean = false;
  facilityId: any
  accountNumberOfFacility: any
  facilityDTOList: any
  tableTypes:any[] = ["Loans / ODs / Others", "Limits"]
  selectedType:any
  selectedIndex:any
  x:any
  initialSelectedId:any = null;

  constructor(
    private formBuilder: FormBuilder,
    public mdbModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
    private mdbModalService: MDBModalService,
    private currencyPipe: CurrencyPipe) {
  }
  rowData: any
  ngOnInit() {
    this.loanData = this.content.finacleData;
    this.facilityId = this.content.facilityId;
    this.selectedLoanId = this.content.accountNumberOfFacility;
    // console.log("facility id", this.facilityId)
    this.facilityDTOList = this.content.facilityDTOList
    // console.log()
    this.x = [1,2,3,4,5,6,7]
    
    const facilityMap = new Map<number, string>(
      this.facilityDTOList.map(facility => [facility.facilityID, this.getFacilityName(facility.facilityID)])
    );
   
    this.limits = this.loanData.limits.map(loan => ({
      ...loan,
      facilityDescription: facilityMap.get(loan.selectedBy) || 'N/A'
    }));
    // console.log("loand and limits", this.limits)
    this.loans = this.loanData.loans.map(loan => ({
      ...loan,
      facilityDescription: facilityMap.get(loan.selectedBy) || 'N/A'
    }));
    
    this.componentForm = this.formBuilder.group({
      cifId: ['']
    });

    //this.getFacilityName(this.facilityId)
   
    this.selectedIndex =0;
    this.selectedType = this.tableTypes[this.selectedIndex]

    if(this.selectedLoanId){
      this.initialSelectedId = this.selectedLoanId
    }
   
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }



  showTableLimitsAndOutstandingTable(): boolean {
    if (this.limits.length || this.loans.length) {
      return true;
    }
    return false;
  }

  selectedLoanId: number | null = null;

  onCheckboxChange(loanId: number) {
    if (this.selectedLoanId === loanId) {
      this.selectedLoanId = null;
    } else {
      this.selectedLoanId = loanId;
    }
  }
  getObjectById(id, array) {
    return array.find(item => item.id === id);
  }
  accountSelect() {
    let loanOrLimit = null
    this.initialSelectedId = null;
    if (this.selectedLoanId) {


      loanOrLimit = this.getObjectById(this.selectedLoanId, this.limits);

      if (!loanOrLimit) {
        loanOrLimit = this.getObjectById(this.selectedLoanId, this.loans);
      }
  //  console.log("selected loan or limit",loanOrLimit)
      
      this.action.next(loanOrLimit);
      this.mdbModalRef.hide();
    }
    else {

      this.showAccNotSelected = true;
      this.action.next(null);
      this.mdbModalRef.hide();
    }

  }



  isCheckboxDisabled(loan): boolean {
    // return false;
    return loan.selectedBy && loan.selectedBy !== this.facilityId;
  }

  getFacilityName(facilityId) {
    
    let facility =this.facilityDTOList.find(item => item.facilityID === facilityId)    
    return `${facility.creditFacilityTemplateDTO.description} - ${facility.facilityRefCode}`;
    
  }


 
  setTabIndex($event) {
    
    this.selectedIndex = $event;
    this.selectedType  = this.tableTypes[this.selectedIndex]
   
  }

  isSelectButtonShow(){
  let list ;
  if (this.selectedType == this.tableTypes[0]){
    return this.loans.length? true: false;
  }
  if (this.selectedType == this.tableTypes[1]){
    return this.limits.length? true: false;
  }

  return false;
  }


  setCurrencyFormatValue(value) {
    let amount =value? value.replace(/,/g, ""): "";
    
    amount = this.currencyPipe.transform(amount, "", "");
   return amount
  }
}
