import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Subject} from "rxjs";

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent implements OnInit, OnDestroy {

  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  isCribConfirmation:boolean = false;
  
  constructor(private mdbModalRef: MDBModalRef) {
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

  /*
  *
  *

  this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: false,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Confirm for something",
        message: "Are you sure to do something ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        TODO: your action here
      }
    });

  *
  *
  * */

}
