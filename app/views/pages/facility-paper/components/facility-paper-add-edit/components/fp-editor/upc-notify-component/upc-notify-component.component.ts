import { Component, OnInit } from "@angular/core";
import { Subject } from "rxjs";

@Component({
  selector: "app-upc-notify-component",
  templateUrl: "./upc-notify-component.component.html",
  styleUrls: ["./upc-notify-component.component.scss"],
})
export class UpcNotifyComponentComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  message: string = "";
  modalAction: string = "";
  constructor() {}

  ngOnInit() {}

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  handleAction(action: number) {
    this.action.next(action);
  }
}
