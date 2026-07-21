import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import * as _ from "lodash";
import { AppUtils } from "../../../../../shared/app.utils";
import { Constants } from "../../../../../core/setting/constants";
import { UpcTemplateDto } from "../../dto/upc-template-dto";
import { UpcTemplateAddEditService } from "../../services/upc-template-add-edit.service";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { ApplicationService } from "../../../../../core/service/application/application.service";

@Component({
  selector: "app-upc-template-add-edit",
  templateUrl: "./upc-template-add-edit.component.html",
  styleUrls: ["./upc-template-add-edit.component.scss"],
})
export class UpcTemplateAddEditComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  formErrors: any;
  selectedUpdateDTO: UpcTemplateDto = new UpcTemplateDto({});
  onSelectedItemChange: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();
  upcLabel: string = "";
  upcLabelFontColor: string = "";
  upcLabelBackgroundColor: string = "";
  pageType: string = "new";

  optionsSelect = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];
  color = "#ffffff";
  selectedColor;
  approveStatus = Constants.approveStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  systemPrivileges: any = {};
  privilegeCategories: any = [];
  checkedPrivilegeIDs: any = [];

  allUpcSectionData: any = [];

  treeUpdatedContent: any = null;
  isTreeUpdated: boolean;
  initNodeData: any = [];
  alreadyAddedUpcSectionIDs = [];

  notifyEmails: string[] = [];
  constructor(
    private formBuilder: FormBuilder,
    private addEditService: UpcTemplateAddEditService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.formErrors = {
      templateName: {},
      description: {},
      status: {},
      upcLabel: {},
      upcLabelFontColor: {},
      upcLabelBackgroundColor: {},
      email: {},
    };

    this.onSelectedItemChange =
      this.addEditService.onSelectedItemChange.subscribe((item) => {
        if (_.isEmpty(item)) {
          this.pageType = "new";
          this.selectedUpdateDTO = new UpcTemplateDto({});
          this.selectedUpdateDTO.upcLabel = "LB";
          this.selectedUpdateDTO.upcLabelFontColor = "#FFFFFF";
          this.selectedUpdateDTO.upcLabelBackgroundColor = "#EB8A76";
        } else {
          this.pageType = "edit";
          this.selectedUpdateDTO = new UpcTemplateDto(item);
        }

        this.allUpcSectionData = this.addEditService.upcSectionData;
        this.isTreeUpdated = false;
        this.treeUpdatedContent = null;
        this.initNodeData = [];

        this.componentForm = this.createRoleForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges.subscribe(
          (form) => {
            this.formErrors = AppUtils.getFormErrors(
              this.componentForm,
              this.formErrors
            );
          }
        );

        this.generateTreeFeed();
        this.upcLabelChange(this.selectedUpdateDTO.upcLabel);
        this.upcLabelFontColorChange(this.selectedUpdateDTO.upcLabelFontColor);
        this.upcLabelBackgroundColorChange(
          this.selectedUpdateDTO.upcLabelBackgroundColor
        );
      });

    if (this.selectedUpdateDTO.emails !== "") {
      this.notifyEmails = this.selectedUpdateDTO.emails.split(",");
    }
  }

  public upcLabelChange(label: string): void {
    this.upcLabel = label.toUpperCase();
  }

  public upcLabelFontColorChange(fontColor: string): void {
    this.upcLabelFontColor = fontColor;
  }

  public upcLabelBackgroundColorChange(backgroundColor: string): void {
    this.upcLabelBackgroundColor = backgroundColor;
  }

  ngOnDestroy(): void {
    this.onSelectedItemChange.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  createRoleForm() {
    return this.formBuilder.group({
      templateName: [
        this.selectedUpdateDTO.templateName,
        [Validators.required, Validators.maxLength(50)],
      ],
      description: [
        this.selectedUpdateDTO.description,
        [Validators.maxLength(250)],
      ],
      status: [
        {
          value: this.selectedUpdateDTO.status,
          disabled: this.pageType == "new",
        },
        [Validators.required],
      ],
      upcLabel: [this.selectedUpdateDTO.upcLabel, [Validators.maxLength(3)]],
      upcLabelFontColor: [this.selectedUpdateDTO.upcLabelFontColor],
      upcLabelBackgroundColor: [this.selectedUpdateDTO.upcLabelBackgroundColor],
      email: ["", Validators.email],
    });
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty || this.isTreeUpdated;
  }

  isApproveOrRejectValid() {
    return (
      this.selectedUpdateDTO.approveStatus ==
        Constants.approveStatusConst.PENDING &&
      this.pageType == "edit" &&
      !this.isModifiedOrCreatedByLoggedInUser()
    );
  }

  search(data) {
    let result = [];
    this.addEditService.upcSectionData.forEach((item: any) => {
      if (_.includes(_.toUpper(item.upcSectionName), _.toUpper(data))) {
        result.push(item);
      }
    });
    this.allUpcSectionData = result;
  }

  saveUpdate() {
    let treeValues = this.composeTreeData();
    let saveData = Object.assign(
      {},
      this.selectedUpdateDTO,
      this.componentForm.getRawValue(),
      {
        approveStatus: this.selectedUpdateDTO.approveStatus,
        approvedBy: this.selectedUpdateDTO.approvedBy,
        approvedDateStr: this.selectedUpdateDTO.approvedDateStr,
      },
      { emails: this.notifyEmails.join(",") }
    );
    saveData.upcLabel = saveData.upcLabel.toUpperCase();
    if (treeValues) {
      saveData.upcTemplateDataDTOList = treeValues;
      for (var item of saveData.upcTemplateDataDTOList) {
        item["upcTemplateID"] = this.selectedUpdateDTO.upcTemplateID
          ? this.selectedUpdateDTO.upcTemplateID
          : null;
        if (!item.upcTemplateDataID) {
          item["upcTemplateDataID"] = null;
        }
      }
    }

    this.addEditService.saveUpdateItem(saveData);
  }

  approve() {
    let data = Object.assign(
      {},
      { approveRejectDataID: this.selectedUpdateDTO.upcTemplateID },
      { approveStatus: this.approveStatus.APPROVED }
    );
    this.addEditService.approveOrRejectUpcTemplate(data);
  }

  reject() {
    let data = Object.assign(
      {},
      { approveRejectDataID: this.selectedUpdateDTO.upcTemplateID },
      { approveStatus: this.approveStatus.REJECTED }
    );
    this.addEditService.approveOrRejectUpcTemplate(data);
  }

  getUPCSectionByID(upcSectionID) {
    return _.find(
      this.allUpcSectionData,
      (d: any) => d.upcSectionID == upcSectionID
    );
  }

  generateTreeFeed() {
    let data = _.sortBy(
      this.selectedUpdateDTO.upcTemplateDataDTOList,
      (i: any) => i.displayOrder
    );
    let nodeData = [];
    let addedIDs = [];

    let levelWiseParents = {};

    for (let i = 0; i < data.length; i++) {
      let dataItem = data[i];
      addedIDs.push(dataItem.upcSectionID);
      let upcSectionData = this.getUPCSectionByID(dataItem.upcSectionID);
      if (!dataItem.parentSectionID) {
        let mainParent = {
          name: upcSectionData.upcSectionName,
          item: upcSectionData,
          isExpanded: true,
          children: [],
        };

        nodeData.push(mainParent);
        levelWiseParents = {};
        levelWiseParents[0] = mainParent;
      } else {
        let upcSectionData = this.getUPCSectionByID(dataItem.upcSectionID);
        let itemData = {
          name: upcSectionData.upcSectionName,
          item: upcSectionData,
          isExpanded: true,
          children: [],
        };

        let currentLevel = dataItem.sectionLevel;
        let parentLevel = currentLevel - 1;

        if (parentLevel == 0) {
          let parent = levelWiseParents[parentLevel];
          parent.children.push(itemData);
        } else {
          let parents = levelWiseParents[parentLevel];
          let parent;
          for (let x = 0; x < parents.length; x++) {
            if (parents[x].item.upcSectionID == dataItem.parentSectionID) {
              parent = parents[x];
              break;
            }
          }

          parent.children.push(itemData);
        }

        if (!levelWiseParents[currentLevel]) {
          levelWiseParents[currentLevel] = [];
        }
        levelWiseParents[currentLevel].push(itemData);
      }
    }

    this.initNodeData = nodeData;
    this.alreadyAddedUpcSectionIDs = addedIDs;
  }

  onUpdateTree($event) {
    this.isTreeUpdated = true;
    this.treeUpdatedContent = $event;
  }

  composeTreeData() {
    let data = [];
    let tree = this.treeUpdatedContent;

    if (!tree) {
      return this.selectedUpdateDTO.upcTemplateDataDTOList;
    }

    for (let i = 0; i < tree.length; i++) {
      this.buildTree(data, tree[i], 0, null);
    }

    data.forEach((item: any, index) => {
      item.displayOrder = index + 1;

      let found = _.find(
        this.selectedUpdateDTO.upcTemplateDataDTOList,
        (i: any) => i.upcSectionID == item.upcSectionID
      );
      if (found) {
        item.upcTemplateDataID = found.upcTemplateDataID;
      }
    });

    return data;
  }

  buildTree(dataArray, node, level, parentSectionID) {
    dataArray.push(
      Object.assign({}, node.item, {
        parentSectionID: parentSectionID,
        sectionLevel: level,
      })
    );

    if (node.children && node.children.length > 0) {
      for (let i = 0; i < node.children.length; i++) {
        this.buildTree(
          dataArray,
          node.children[i],
          level + 1,
          node.item.upcSectionID
        );
      }
    }
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.selectedUpdateDTO.modifiedBy
      ? this.selectedUpdateDTO.modifiedBy ==
          this.applicationService.getLoggedInUserUserName()
      : this.selectedUpdateDTO.createdBy
      ? this.selectedUpdateDTO.createdBy ==
        this.applicationService.getLoggedInUserUserName()
      : false;
  }

  addNotifyEmail() {
    let rawData = this.componentForm.getRawValue();
    let email: any = rawData["email"];
    if (
      email !== null &&
      email !== "" &&
      this.componentForm.controls["email"].valid &&
      !this.notifyEmails.some((e: string) => e === email)
    ) {
      this.notifyEmails.push(email);
    }

    this.componentForm.controls["email"].patchValue("");
  }

  isInValidEmail() {
    return (
      this.componentForm.controls["email"].value === null ||
      this.componentForm.controls["email"].value === "" ||
      this.componentForm.controls["email"].invalid
    );
  }

  removeEmail(index: number) {
    this.notifyEmails = this.notifyEmails.filter(
      (d: any, i: number) => i !== index
    );
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }
}
