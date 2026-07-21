import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import { FacilityPaperAddEditService } from '../../pages/facility-paper/services/facility-paper-add-edit.service';
import * as _ from "lodash";

@Component({
  selector: 'app-preview-facility-exposure-data',
  templateUrl: './preview-facility-exposure-data.component.html',
  styleUrls: ['./preview-facility-exposure-data.component.scss']
})
export class PreviewFacilityExposureDataComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper;
  componentForm: FormGroup;
  onBaseFacilityPaperChangeSub = new Subscription();
  onCalculateFacilityPaperExposureChangeSub = new Subscription();
  exposureData: any = {};

  constructor(private formBuilder: FormBuilder, private currencyPipe: CurrencyPipe,private facilityPaperAddEditService: FacilityPaperAddEditService, ) {
  }

  ngOnInit() {
    this.componentForm = this.createForm();
    // console.log("this.facilityPaper2222222222",this.facilityPaper);


    this.onBaseFacilityPaperChangeSub = this.facilityPaperAddEditService.onBaseFacilityPaperChange
    .subscribe(baseFacility => {
      if (baseFacility) {
        this.facilityPaper = baseFacility;
        // console.log("this.facilityPaper..........", this.facilityPaper);
        
        this.exposureData = this.facilityPaper;

      }
    });

  }

  createForm() {
    // console.log("WWWWWWWWWWWWW",this.facilityPaper);
    return this.formBuilder.group({
      totalDirectExposurePrevious: [this.getCurrencyFormat(this.facilityPaper.totalDirectExposurePrevious)],
      totalDirectExposureNew: [this.getCurrencyFormat(this.facilityPaper.totalDirectExposureNew)],
      totalIndirectExposurePrevious: [this.getCurrencyFormat(this.facilityPaper.totalIndirectExposurePrevious)],
      totalIndirectExposureNew: [this.getCurrencyFormat(this.facilityPaper.totalIndirectExposureNew)],
      totalExposurePrevious: [this.getCurrencyFormat(this.facilityPaper.totalExposurePrevious)],
      totalExposureNew: [this.getCurrencyFormat(this.facilityPaper.totalExposureNew)],
      addTotalExposureToGroup: [this.facilityPaper.addTotalExposureToGroup, []],
      groupTotalDirectExposurePrevious: [this.getCurrencyFormat(this.facilityPaper.groupTotalDirectExposurePrevious)],
      groupTotalDirectExposureNew: [this.getCurrencyFormat(this.facilityPaper.groupTotalDirectExposureNew)],
      groupTotalIndirectExposurePrevious: [this.getCurrencyFormat(this.facilityPaper.groupTotalIndirectExposurePrevious)],
      groupTotalIndirectExposureNew: [this.getCurrencyFormat(this.facilityPaper.groupTotalIndirectExposureNew)],
      groupTotalExposurePrevious: [this.getCurrencyFormat(this.facilityPaper.groupTotalExposurePrevious)],
      groupTotalExposureNew: [this.getCurrencyFormat(this.facilityPaper.groupTotalExposureNew)]
    })
  }


  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '');
  }

  ngOnDestroy(): void {
    this.onBaseFacilityPaperChangeSub.unsubscribe();
  }
}
