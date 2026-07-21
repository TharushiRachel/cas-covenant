import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FormGroup } from "@angular/forms";
import { Subject } from "rxjs";
import { MasterDataService } from "../../../../../../../../../core/service/data/master-data.service";
import { Constants } from "../../../../../../../../../core/setting/constants";
import * as moment from "moment";
import { UpcNotifyComponentComponent } from "../../upc-notify-component/upc-notify-component.component";

@Component({
  selector: "app-upc-template-add-data",
  templateUrl: "./upc-template-add-data.component.html",
  styleUrls: ["./upc-template-add-data.component.scss"],
})
export class UpcTemplateAddDataComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  action: Subject<any> = new Subject<any>();
  dataUpdateDTO: Data = new Data({});
  content: any;
  givenData: any = "";
  header: any = "";
  nodeData: any;
  isTinyMCEEnabled: boolean = false;

  isRefreshEnabled: boolean = false;
  templateSectionList: any[] = [];
  isCommittee: boolean = false;
  isNotifyModalOpen: boolean = false;
  currentAction: string = "";
  onCloseContent: string = "";

  messages: any = {
    reminder: "Please save your work and continue.",
    save: "Original template has been changed. Do you want to save this content?",
    close: "Content has been changed. Do you want to close the content?",
  };

  timer: any;

  constructor(
    private mdbModalService: MDBModalService,
    public mdbModalRef: MDBModalRef,
    public modalRef: MDBModalRef,
    private masterDataService: MasterDataService
  ) {}

  ngOnInit() {
    this.givenData = this.content.dataToEdit ? this.content.dataToEdit : "";
    this.header = this.content.header ? this.content.header : "";
    this.isTinyMCEEnabled = this.masterDataService.getSystemParameter(
      Constants.systemParamKey.TINYMCE_ENABLED
    );

    if (this.isCommittee) {
      this.handleOpenTime();
    }
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  handleOpenTime() {
    if (
      !this.isNotifyModalOpen &&
      this.getLocalDateTime() &&
      this.currentAction == ""
    ) {
      this.timer = setInterval(() => {
        this.openUpcNotifyModal(this.messages.reminder, "R", false);
      }, 300000);
    }
  }

  handleRefreshContent() {
    this.action.next(1);
    this.hideAllModal(true);
  }

  handleCloseModal() {
    this.handleClickSave("app-tiny-mce-editor-save-close");
    if (this.isCommittee) {
      if (this.givenData != this.onCloseContent) {
        this.openUpcNotifyModal(this.messages.close, "C", true);
      } else {
        this.hideAllModal(true);
      }
    } else {
      this.mdbModalRef.hide();
    }
  }

  setContentByClose(content: any) {
    this.onCloseContent = content;
  }

  save(event: any) {
    if (this.isCommittee) {
      if (this.validateContent(event).status == 1) {
        this.action.next(event);
        this.handleNotifyTimer();
      } else {
        this.openUpcNotifyModal(this.messages.save, "S", false);
      }
    } else {
      this.action.next(event);
    }
  }

  saveAndClose(event: any) {
    if (this.isCommittee) {
      if (this.validateContent(event).status == 1) {
        this.action.next(event);
        this.hideAllModal(true);
      } else {
        this.openUpcNotifyModal(this.messages.save, "S", true);
      }
    } else {
      this.action.next(event);
      this.hideAllModal(true);
    }
  }

  validateContent(data: string) {
    var response = { status: 1, message: "" };

    var headingCount: number = (
      data.match(new RegExp(`aria-label="important-heading"`, "g")) || []
    ).length;

    var lableCount: number = (
      data.match(new RegExp(`aria-label="important-label"`, "g")) || []
    ).length;

    var tableCount: number = (
      data.match(new RegExp(`aria-label="important-table"`, "g")) || []
    ).length;

    var templateSection: any = this.templateSectionList.find(
      (s: any) => s.sectionName == this.header
    );
    if (templateSection) {
      if (
        (templateSection.headingCount != 0 &&
          headingCount < templateSection.headingCount) ||
        (templateSection.lableCount != 0 &&
          lableCount < templateSection.lableCount) ||
        (templateSection.tableCount != 0 &&
          tableCount < templateSection.tableCount)
      ) {
        response = {
          status: 0,
          message:
            "Content template elements are removed. Please try to undo your changes or refresh the content template.",
        };
      }
    }

    return response;
  }

  notifySaveData(event: any) {
    this.action.next(event);
  }

  openUpcNotifyModal(message: any, action: any, isSaveClose: boolean) {
    this.currentAction = action;
    this.isNotifyModalOpen =
      this.isNotifyModalOpen && (action == "S" || action == "C")
        ? true
        : this.isNotifyModalOpen;

    if (!this.isNotifyModalOpen) {
      this.modalRef = this.mdbModalService.show(UpcNotifyComponentComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-35-p",
        containerClass: "",
        animated: false,
        data: {
          heading: "",
          message: message,
          modalAction: action,
          content: { header: "UPC Notification" },
        },
      });

      this.isNotifyModalOpen = true;

      this.modalRef.content.action.subscribe((result: any) => {
        //save and contine
        if (result == 1) {
          this.handleNotifyModal();
          this.handleClickSave("app-tiny-mce-editor-save");
        }

        //if close yes
        if (result == 2) {
          this.hideAllModal(isSaveClose);
        }

        //if close no / if save no
        if (result == 3 || result == 5) {
          this.handleNotifyModal();
          this.handleNotifyTimer();
        }

        //if save yes
        if (result == 4) {
          this.handleClickSave("app-tiny-mce-editor-save-notify");
          this.hideAllModal(isSaveClose);
        }
      });
    }
  }

  handleClickSave(btnId: string) {
    const button = document.getElementById(btnId) as HTMLButtonElement;
    button.click();
  }

  handleNotifyModal() {
    this.currentAction = "";
    this.isNotifyModalOpen = false;
    this.modalRef.hide();
  }

  handleNotifyTimer() {
    clearInterval(this.timer);
    localStorage.removeItem("mdbmlopn");
    localStorage.setItem(
      "mdbmlopn",
      JSON.stringify(moment().format("YYYY-MM-DD H:mm:ss"))
    );

    this.handleOpenTime();
  }

  getLocalDateTime() {
    var openedTime: any = localStorage.getItem("mdbmlopn")
      ? JSON.parse(localStorage.getItem("mdbmlopn"))
      : "";
    return openedTime;
  }

  hideAllModal(isSaveClose: boolean) {
    this.handleNotifyModal();

    if (isSaveClose) {
      this.mdbModalRef.hide();
      localStorage.removeItem("mdbmlopn");
      clearInterval(this.timer);
    } else {
      this.handleNotifyTimer();
    }
  }
}

export class Data {
  data;

  constructor(dto) {
    dto = dto || {};
    this.data = dto.data || "";
  }
}
