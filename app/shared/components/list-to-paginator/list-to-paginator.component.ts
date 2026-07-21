import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-list-to-paginator',
  templateUrl: './list-to-paginator.component.html',
  styleUrls: ['./list-to-paginator.component.scss']
})
export class ListToPaginatorComponent implements OnInit {

  @Input() inputArray = [];
  @Input() listType: string = '';
  @Output() resizedArray = new EventEmitter();

  @Input('pageSize') pageSize: number = 3;
  @Input('currentEndIndex') currentEndIndex: number = 3;
  currentBeginIndex: number = 0;

  constructor() {
  }

  ngOnInit() {
  }

  prvClick(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();

      if (this.currentBeginIndex >= this.pageSize && this.currentEndIndex > this.pageSize) {
        this.currentEndIndex = (this.currentEndIndex - this.currentBeginIndex) < this.pageSize ? this.currentEndIndex - (this.currentEndIndex - this.currentBeginIndex) : this.currentEndIndex - this.pageSize;
        this.currentBeginIndex = this.currentBeginIndex - this.pageSize;

        this.updateValue(this.currentBeginIndex, this.currentEndIndex)
      }
    }
  }

  nextClick(event) {

    if (event) {
      event.preventDefault();
      event.stopPropagation();

      if ((this.currentBeginIndex + this.pageSize) < (this.inputArray.length)) {
        this.currentBeginIndex = this.currentBeginIndex + this.pageSize;
        this.currentEndIndex = (this.currentEndIndex + this.pageSize) < this.inputArray.length ? this.currentEndIndex + this.pageSize : this.inputArray.length;
        this.updateValue(this.currentBeginIndex, this.currentEndIndex);
      }

    }
  }

  updateValue(prevIndex, nextIndex) {
    if (this.inputArray.length > 0) {
      let outputArray = this.inputArray.slice(prevIndex, nextIndex);
      let data: any = {
        outputArray: outputArray,
        outputArrayType: this.listType
      };
      this.resizedArray.emit(data);
    }
  }

}
