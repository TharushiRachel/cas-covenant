import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lead-existing-message',
  templateUrl: './lead-existing-message.component.html',
  styleUrls: ['./lead-existing-message.component.scss']
})
export class LeadExistingMessageComponent implements OnInit {

  lead: any = {};
  message: string;
  action: Subject<any> = new Subject<any>();

  constructor(private mdbModalRef: MDBModalRef, private router: Router,) {
  }

  ngOnInit() {

  }

  onNoClick(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
    this.router.navigate(['/leads/create']);
    //this.router.navigate(['/leads']);
  }

  onYesClick(): void {
    this.action.next(true);
    this.mdbModalRef.hide();
    this.router.navigate(['/leads']);
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

}
