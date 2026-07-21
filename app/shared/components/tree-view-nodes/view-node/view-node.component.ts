import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import * as _ from "lodash";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { PrivilegeService } from "src/app/core/service/authentication/privilege.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { UPCTemplateComparisonComponent } from "src/app/views/pages/facility-paper/components/facility-paper-add-edit/components/fp-editor/upc-template-structure/upc-template-comparison/upc-template-comparison.component";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-view-node",
  templateUrl: "./view-node.component.html",
  styleUrls: [
    "./view-node.component.scss",
    "../tree-view-nodes.component.scss",
  ],
})
export class ViewNodeComponent implements OnInit, OnDestroy {
  @Input("nodeTitle") nodeTitle = "NodeTitle";
  @Input("nodeContent") nodeContent = "NodeContent";
  @Input("nodeId") nodeId = "-";
  @Input("node") node: any = {};
  @Input("isPreviewMode") isPreviewMode: boolean;
  @Input("isCommentEnable") isCommentEnable: boolean;
  @Input("facilityPaperId") facilityPaperId: any;
  @Input("currentAssignUser") currentAssignUser: any;
  @Input("currentFPStatus") currentFPStatus: any;
  @Input("currentFPWC") currentFPWC: any;
  @Input("assignDepartmentCode") assignDepartmentCode: any;
  upcSectionsFusTraces: any[] = [];

  @Output("onEditClick") onEditClick: EventEmitter<any> = new EventEmitter();
  @Output("onCommentClick") onCommentClick: EventEmitter<any> =
    new EventEmitter();

  onUPCFustaceChangeSub = new Subscription();

  upcTemplateComparisonModelRef: MDBModalRef;
  commentHistory: any[] = [];
  hasPrivilegeToEditUPCAsDefault: boolean = false;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  constructor(
    private mdbModalService: MDBModalService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.hasPrivilegeToEditUPCAsDefault = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_UPC_EDIT
    );

    this.onUPCFustaceChangeSub =
      this.facilityPaperAddEditService.onUPCFustaceChange.subscribe(
        (data: any) => {
          this.upcSectionsFusTraces = !_.isEmpty(data) ? data : [];

          if (this.node && this.upcSectionsFusTraces) {
            this.node = {
              ...this.node,
              commentCount: this.upcSectionsFusTraces.filter(
                (d: any) => d.mainKey == this.node.fpUpcSectionDataID
              ).length,
            };
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onUPCFustaceChangeSub.unsubscribe();
  }

  onEdit($event) {
    this.onEditClick.emit({ event: $event, node: this.node });
  }

  isShow(): boolean {
    var isShow: boolean = false;
    var wc: any = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    if (this.isPreviewMode) {
      isShow =
        this.currentFPStatus ==
          Constants.facilityPaperStatusConst.IN_PROGRESS ||
        this.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL ||
        this.currentFPStatus == Constants.facilityPaperStatusConst.APPROVED;
    } else {
      if (
        wc == Constants.applicationSecurityWorkClass.ENTERER ||
        wc == Constants.applicationSecurityWorkClass.SUPERVISOR ||
        wc == Constants.applicationSecurityWorkClass.MANAGER ||
        this.hasPrivilegeToEditUPCAsDefault
      ) {
        isShow =
          this.currentFPStatus ==
            Constants.facilityPaperStatusConst.IN_PROGRESS ||
          this.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL;
      } else {
        isShow = false;
      }
    }

    return isShow;
  }

  openModalUPCTemplateComment() {
    const initialState = {
      node: {
        ...this.node,
        facilityPaperID: this.facilityPaperId,
        currentAssignUser: this.currentAssignUser,
        currentFPStatus: this.currentFPStatus,
        currentFPWC: this.currentFPWC,
        assignDepartmentCode: this.assignDepartmentCode,
      },
    };

    this.upcTemplateComparisonModelRef = this.mdbModalService.show(
      UPCTemplateComparisonComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        ignoreBackdropClick: true,
        class: "modal-width-95-p",
        containerClass: "",
        animated: true,
        data: {
          heading: "12",
          content: {
            initialState: initialState,
            upcTemplateComparisonModelRef: this.upcTemplateComparisonModelRef,
          },
        },
      }
    );
  }
}
