import { Component, OnInit ,  Input, OnChanges, OnDestroy, SimpleChanges,} from '@angular/core';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { IMyOptions } from 'ng-uikit-pro-standard';
import {Subscription} from "rxjs";
import { PageSize } from 'src/app/core/dto/page.size';

@Component({
  selector: 'app-acae-details-monitor',
  templateUrl: './acae-details-monitor.component.html',
  styleUrls: ['./acae-details-monitor.component.scss']
})
export class ACAEDetailsMonitorComponent{
  searchedACAEList: any = [
    {
      "accountNumber":"000100003589",
      "customerName":"M N V DE SILVA",
      "recievedDate":"05-03-2022",
      "attended":"edit",
  }
  ];
  searchForm: FormGroup;
  pageSize = new PageSize();
  pageIndex=0;
  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };
  constructor( private formBuilder: FormBuilder) {
    
   }

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      afRefNumber: ['',Validators.required],
      assignUserDisplayName: [''],
      identificationNumber: '',
      currentApplicationFormStatus: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
    });
    // this.searchedACAEList  = new Subscription();
  }

  clearSearch(){
    this.searchForm.reset({
      afRefNumber: '',
      assignUserDisplayName: '',
      identificationNumber: '',
      currentApplicationFormStatus: '',
      createdFromDateStr:'',
      createdToDateStr:''
    }, {onlySelf: false, emitEvent: true});
  }

  onPageEvent(event){
    this.pageSize.pageSize = event.pageSize;
  }

  doSearch(){}

}
