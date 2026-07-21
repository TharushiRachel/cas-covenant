import {Component, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-show-html-modal',
  templateUrl: './show-html-modal.component.html',
  styleUrls: ['./show-html-modal.component.scss']
})
export class ShowHtmlModalComponent implements OnInit {

  content: any;

  htmlStr: any = '';

  constructor(public  mdbModalRef: MDBModalRef,) {
  }

  ngOnInit() {
    this.htmlStr = this.content.htmlStr ? this.content.htmlStr : '';
  }

}
