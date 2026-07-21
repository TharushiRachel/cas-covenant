import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-basic-wrapper',
  templateUrl: './basic-wrapper.component.html',
  styleUrls: ['./basic-wrapper.component.scss']
})
export class BasicWrapperComponent implements OnInit {
  @Output('goToKalipto') goToKalipto = new EventEmitter<any>();

  constructor() {
  }

  ngOnInit() {
  }

  onKaliptoDataLoad(customerData: any) {
    this.goToKalipto.emit(customerData);
  }

}
