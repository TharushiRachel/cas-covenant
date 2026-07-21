import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../core/service/application/application.service";

@Component({
  selector: 'app-information-dialog',
  templateUrl: './information-dialog.component.html',
  styleUrls: ['./information-dialog.component.scss']
})
export class InformationDialogComponent implements OnInit {

  heading: string;
  message: string;
  showConfirm: boolean = true;
  action: Subject<any> = new Subject<any>();

  constructor(private mdbModalRef: MDBModalRef, public applicationService: ApplicationService,) {
  }

  ngOnInit() {
  }

  onNoClick(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    this.action.next(true);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

}
