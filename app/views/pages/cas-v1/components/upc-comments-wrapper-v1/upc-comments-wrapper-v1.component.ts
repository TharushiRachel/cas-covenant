import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { CasV1Service } from "../../services/cas-v1.service";
@Component({
  selector: "app-upc-comments-wrapper-v1",
  templateUrl: "./upc-comments-wrapper-v1.component.html",
  styleUrls: ["./upc-comments-wrapper-v1.component.scss"],
})
export class UpcCommentsWrapperV1Component implements OnInit {
  upc: any[] = []; // Stores form data
  hasRecords: boolean = true;

  @Input("combinedData") combinedData: any;
  @Input("tabLoadedData") tabLoadedData: any;
  @Input("tabLoadingSetting") tabLoadingSetting: any;
  @Output() handleTabLoadingSetting = new EventEmitter<string>();
  @Output() onUPCDataChange = new EventEmitter<any[]>();

  constructor(private readonly casV1Service: CasV1Service) {}

  ngOnInit() {
    if (!this.combinedData.upcFormatNum) {
      this.hasRecords = false;
      return; // Prevent further API calls if upcFormatNum is missing
    }

    if (!this.tabLoadingSetting.isUPCLoaded) {
      this.loadAttachments();
    } else {
      this.upc = this.tabLoadedData.upc;
      this.hasRecords = this.upc.length > 0;
    }
  }

  decodeHTML(html: string): string {
    const txt = document.createElement('textarea');
    txt.innerHTML = html;
    return txt.value;
  }
  

  loadAttachments(): void {
    this.casV1Service
      .getAttachments(
        this.combinedData.refNo,
        this.combinedData.selectedDate,
        this.combinedData.upcFormatNum
      )
      .then((data) => {
        this.handleTabLoadingSetting.next("UPC");
        if (!data || data.length === 0) {
          this.upc = [];
          this.hasRecords = false;
          return;
        }
        this.upc = data;
        this.onUPCDataChange.next(this.upc);
        this.hasRecords = true;
      });
  }
}
