import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-html-content-viewer',
  templateUrl: './html-content-viewer.component.html',
  styleUrls: ['./html-content-viewer.component.scss']
})
export class HtmlContentViewerComponent implements OnInit {

  htmlContent: string;
  header: any;
  action: Subject<any> = new Subject<any>();

  constructor(private mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }
}
