import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-change-order',
  templateUrl: './change-order.component.html',
  styleUrls: ['./change-order.component.scss']
})
export class ChangeOrderComponent implements OnInit {

  keyData: any;
  dataList: any[] = [];
  heading: string;
  message: string;
  actionName: string;
  action: Subject<any> = new Subject<any>();

  listStyle = {
    width: '100%', //width of the list defaults to 300,
    height: '100%', //height of the list defaults to 250,
    dropZoneHeight: '45px' // height of the dropzone indicator defaults to 50
  };


  constructor(private mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  update(): void {
    this.action.next(this.dataList);
    this.mdbModalRef.hide();
  }

  listOrderChanged(event: any) {
    // console.log(event);
  }
}
