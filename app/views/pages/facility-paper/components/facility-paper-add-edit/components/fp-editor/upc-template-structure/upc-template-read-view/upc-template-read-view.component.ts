import {Component, Input, OnInit} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'app-upc-template-read-view',
  templateUrl: './upc-template-read-view.component.html',
  styleUrls: ['./upc-template-read-view.component.scss']
})
export class UpcTemplateReadViewComponent implements OnInit {

  @Input('initNodeData') initNodeData: any = [];
  @Input('dataMap') dataMap:any ={};
  @Input('allUpcSectionData') allUpcSectionData: any = [];

  sortedData:any = [];


  constructor() { }

  ngOnInit() {

    this.sortedData = _.sortBy(this.allUpcSectionData, (i: any) => i.displayOrder);
  }

}
