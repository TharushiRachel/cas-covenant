import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { FormGroup } from "@angular/forms";
import { MasterDataService } from "../../../core/service/data/master-data.service";
import { Constants } from "../../../core/setting/constants";

@Component({
  selector: "app-common-popup-with-tiny-mce-editor",
  templateUrl: "./common-popup-with-tiny-mce-editor.component.html",
  styleUrls: ["./common-popup-with-tiny-mce-editor.component.scss"],
})
export class CommonPopupWithTinyMceEditorComponent
  implements OnInit, OnDestroy
{
  componentForm: FormGroup;
  action: Subject<any> = new Subject<any>();
  content: any;
  givenData: any;
  header: any = "";
  isSaveAndCloseEnabled: boolean = false;
  actionName: string = "save";

  isTinyMCEEnabled: boolean = false;

  constructor(
    public mdbModalRef: MDBModalRef,
    private masterDataService: MasterDataService
  ) {}

  ngOnInit() {
    this.givenData = this.content.dataToEdit ? this.content.dataToEdit : "";
    this.header = this.content.header ? this.content.header : "";
    this.isTinyMCEEnabled = this.masterDataService.getSystemParameter(
      Constants.systemParamKey.TINYMCE_ENABLED
    );
  }

  save(event: any) {
    this.action.next(event);
  }

  saveAndClose(event: any) {
    this.action.next(event);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }
}
