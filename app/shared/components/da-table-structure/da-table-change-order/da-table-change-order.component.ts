import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { Subject } from 'rxjs';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { DaService } from 'src/app/views/pages/delegated-credit-authority/services/da.service';

@Component({
  selector: 'app-da-table-change-order',
  templateUrl: './da-table-change-order.component.html',
  styleUrls: ['./da-table-change-order.component.scss']
})
export class DaTableChangeOrderComponent implements OnInit {

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


  constructor(
    private mdbModalRef: MDBModalRef,
    private daService: DaService,
    private alertService : AlertService,
    private router: Router) {}

  ngOnInit() {
  }

  onNoClick(): void {
    
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  update(): void {
   
    this.saveUpdate()
    //this.action.next(this.dataList);
    this.mdbModalRef.hide();
  }

  listOrderChanged(event: any) {
    // console.log(event);
  }

  saveUpdate() {
    let saveData = Object.assign({},
      {
        daDisplayOrderDTOList : this.dataList.map((data: any, index) => {
          data.displayOrder = index;
          return data;
        })
      },
    );

    this.daService.changeDAOrder(saveData).then((res) => {
      this.alertService.showToaster("DA order saved successfully.", SETTINGS.TOASTER_MESSAGES.success);
      this.action.next(res);
    }).catch((err) => {
      this.alertService.showToaster("Please contact the administraion", "ERROR")
    });
  }

}
