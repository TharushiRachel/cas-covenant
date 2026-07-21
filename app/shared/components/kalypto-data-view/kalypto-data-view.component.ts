import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-kalypto-data-view',
  templateUrl: './kalypto-data-view.component.html',
  styleUrls: ['./kalypto-data-view.component.scss']
})
export class KalyptoDataViewComponent implements OnInit {

  @Input() kalyptoData: any = {};

  constructor() {
  }

  ngOnInit() {
  }

  getParamValue(param) {
    if (param.value_text) {
      return param.value_text;
      /* } else if (param.value_percentage) {
         return param.value_percentage;*/
    } else {
      return param.value_numeric
    }
  }


}
