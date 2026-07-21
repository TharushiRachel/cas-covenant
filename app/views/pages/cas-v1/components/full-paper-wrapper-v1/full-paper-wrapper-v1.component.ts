import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

@Component({
  selector: "app-full-paper-wrapper-v1",
  templateUrl: "./full-paper-wrapper-v1.component.html",
  styleUrls: ["./full-paper-wrapper-v1.component.scss"],
})
export class FullPaperWrapperV1Component implements OnInit {
  @Input("facilities") facilities: any[] = [];
  @Input("basicData") basicData: any;
  @Input("combinedData") combinedData: any;
  @Input("tabLoadedData") tabLoadedData: any;
  @Input("tabLoadingSetting") tabLoadingSetting: any;

  @Output() handleTabLoadingSetting = new EventEmitter<string>();
  @Output() onCmntDataChange = new EventEmitter<any[]>();
  @Output() onUPCDataChange = new EventEmitter<any[]>();

  constructor() {}

  ngOnInit() {}

  setTabLoadingSetting(type: string) {
    this.handleTabLoadingSetting.next(type);
  }

  setComments(data: any[]) {
    this.onCmntDataChange.next(data);
  }

  setUpcData(data: any[]) {
    this.onUPCDataChange.next(data);
  }
}
