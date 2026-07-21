import { Component, Input, OnInit } from "@angular/core";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-customer-clarification-view",
  templateUrl: "./customer-clarification-view.component.html",
  styleUrls: ["./customer-clarification-view.component.scss"],
})
export class CustomerClarificationViewComponent implements OnInit {
  @Input() facilityPaper: any;
  @Input() customer: any;
  @Input() isEditEnabled: boolean = false;

  clarificationList: any[] = [];
  selectedIds: any[] = [];
  previewClarificationList: any[] = [];
  constructor(
    private readonly applicationService: ApplicationService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
  ) {}

  ngOnInit() {
    this.getCustomerClassifications();
  }

  getCustomerClassifications() {
    let payload: any = {
      facilityPaperId: this.facilityPaper.facilityPaperID,
      customerId: this.customer.casCustomerID,
    };
    this.facilityPaperAddEditService
      .getCustomerClassification(payload)
      .then((data: any[]) => {
        this.clarificationList = data.map((d: any) => ({
          ...d,
          isSelected: d.selected === Constants.yesNoConst.Y,
          comment: d.comment || "",
          children: d.children.map((c: any) => ({
            ...c,
            isSelected: c.selected === Constants.yesNoConst.Y,
            comment: c.comment || "",
            children: c.children.map((cd: any) => ({
              ...cd,
              isSelected: cd.selected === Constants.yesNoConst.Y,
              comment: cd.comment || "",
            })),
          })),
        }));

        this.previewClarificationList = this.mapSelectedItems();
      });
  }

  handleSave() {
    let selectedIds: any[] = this.mapSelectedItems();

    let payload: any = {
      facilityPaperId: this.facilityPaper.facilityPaperID,
      customerId: this.customer.casCustomerID,
      classificationDTOList: selectedIds,
    };
    this.facilityPaperAddEditService.saveCustomerClassification(payload);
  }

  prepareSelectedReq(element: any) {
    return {
      id: element.id,
      description: element.description,
      selected: element.isSelected
        ? Constants.yesNoConst.Y
        : Constants.yesNoConst.N,
      comment: element.comment || "",
    };
  }

  isActionEnabled() {
    return this.isEditEnabled && this.isAllowedPaper() && this.isAllowedUser();
  }

  isAllowedPaper() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.REJECTED
    );
  }

  isAllowedUser() {
    let loggedInUserWC: number = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode(),
    );
    return (
      [10, 20, 50].includes(loggedInUserWC) &&
      this.facilityPaper.currentAssignUser ===
        this.applicationService.getLoggedInUserUserName()
    );
  }

  mapSelectedItems(): any[] {
    let selectedIds: any[] = [];

    this.clarificationList.forEach((element: any) => {
      if (element.isSelected) {
        selectedIds.push(this.prepareSelectedReq(element));
      }

      if (element.children) {
        element.children.forEach((childEle: any) => {
          if (childEle.isSelected) {
            selectedIds.push(this.prepareSelectedReq(childEle));

            if (childEle.children) {
              childEle.children.forEach((grandChildEle: any) => {
                if (grandChildEle.isSelected) {
                  selectedIds.push(this.prepareSelectedReq(grandChildEle));
                }
              });
            }
          }
        });
      }
    });

    return selectedIds;
  }

  getFilteredData(dataList: any[]) {
    if (!this.isEditEnabled) {
      return dataList.filter((d: any) => d.isSelected);
    }
    return dataList;
  }

  isNotEmptyPreview() {
    return (
      (!this.isEditEnabled && this.previewClarificationList.length !== 0) ||
      this.isEditEnabled
    );
  }
}
