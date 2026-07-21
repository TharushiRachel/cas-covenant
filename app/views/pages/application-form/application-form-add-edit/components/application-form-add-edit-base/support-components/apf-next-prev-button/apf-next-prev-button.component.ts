import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-apf-next-prev-button',
  templateUrl: './apf-next-prev-button.component.html',
  styleUrls: ['./apf-next-prev-button.component.scss']
})
export class ApfNextPrevButtonComponent implements OnInit {

  @Input("currentIndex") currentIndex;
  @Input("lastIndex") lastIndex;
  @Output("setTabIndex") setTabIndex: EventEmitter<number> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  showPreviousTab($event) {
    let index = this.currentIndex;
    if (index != 0) {
      this.setTabIndex.emit(index - 1)
    }
  }

  showNextTab($event) {
    let index = this.currentIndex;
    if (index != this.lastIndex) {
      this.setTabIndex.emit(index + 1)
    }
  }

}
