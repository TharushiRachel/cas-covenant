import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-comment-list-to-pagination',
  templateUrl: './comment-list-to-pagination.component.html',
  styleUrls: ['./comment-list-to-pagination.component.scss']
})
export class CommentListToPaginationComponent implements OnInit {

  @Input() inputArray = [];
  @Input() listType: string = '';
  @Output() resizedArray = new EventEmitter();

  pageSize: number = 10;
  currentEndIndex: number = 10;
  currentBeginIndex: number = 0;

  constructor() {
  }

  ngOnInit() {
  }

  prvClick(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();

      if (this.currentBeginIndex >= 10 && this.currentEndIndex > 10) {
        this.currentEndIndex = (this.currentEndIndex - this.currentBeginIndex) < 10 ? this.currentEndIndex - (this.currentEndIndex - this.currentBeginIndex) : this.currentEndIndex - 10;
        this.currentBeginIndex = this.currentBeginIndex - 10;

        this.updateValue(this.currentBeginIndex, this.currentEndIndex)
      }
    }
  }

  nextClick(event) {

    if (event) {
      event.preventDefault();
      event.stopPropagation();

      if ((this.currentBeginIndex + 10) < (this.inputArray.length)) {
        this.currentBeginIndex = this.currentBeginIndex + 10;
        this.currentEndIndex = (this.currentEndIndex + 10) < this.inputArray.length ? this.currentEndIndex + 10 : this.inputArray.length;
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
