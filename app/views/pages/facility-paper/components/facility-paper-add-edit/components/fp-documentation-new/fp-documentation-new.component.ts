import { CurrencyPipe } from "@angular/common";
import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import * as _ from "lodash";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { AddEditContentFeildComponent } from "./add-edit-content-feild/add-edit-content-feild.component";
import { ApplicationService } from "src/app/core/service/application/application.service";
import {
  newFacilityTemplate,
  newDocumentElement,
  DocumentElement,
  Facility,
  FacilityTemplate,
  SDConstants,
  DocumentTemplateData,
  newFacility,
  DocumentTag,
} from "./utils";
import { CommentWithViewOptionsDialogComponent } from "src/app/shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import { ActionLogViewComponent } from "./action-log-view/action-log-view.component";
import { Constants } from "src/app/core/setting/constants";
import { SdInfoDialogComponent } from "./sd-info-dialog/sd-info-dialog.component";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { SdCovenantModalComponent } from "./sd-covenant-modal/sd-covenant-modal.component";

@Component({
  selector: "app-fp-documentation-new",
  templateUrl: "./fp-documentation-new.component.html",
  styleUrls: ["./fp-documentation-new.component.scss"],
})
export class FpDocumentationNewComponent implements OnInit {
  modalRef: MDBModalRef;

  @Input("facilityPaper") facilityPaper: any = {};
  facilities: any[] = [];
  documentTemplateDataList: DocumentTemplateData[] = [];
  fpFacilityTemplates: FacilityTemplate[] = [];
  savedDocuments: any[] = [];
  selectedTabIndex: number = 0;
  documentContent: string = "";
  selectedFacility: Facility;
  selectedDocumentTemplate: FacilityTemplate;
  selectedDocumentElement: DocumentElement;
  selectedDocumentStatus: string = "";

  documentStatusConst: any = SDConstants.documentStatusConst;
  covenantsList: any[] = [];
  selectedCovenantsList: any[] = [];
  documentTags: DocumentTag[] = [];
  isContentRefreshed:boolean = false;

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly currencyPipe: CurrencyPipe,
    private readonly applicationService: ApplicationService,
    public cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
      (facilityPaper: any) => {
        if (facilityPaper !== null && facilityPaper.facilityDTOList) {
          this.facilities = facilityPaper.facilityDTOList;
        }
      }
    );

    this.getSecurityDocumentationElementList();
    this.getDocumentTags();
  }

  getSecurityDocumentationElementList() {
    let request: any = {
      facilityPaperId: this.facilityPaper.facilityPaperID,
    };

    this.facilityPaperAddEditService
      .getFacilityTemplateDocumentElements(request)
      .then((data: any[]) => {
        if (!_.isEmpty(data)) {
          this.documentTemplateDataList = data;

          this.documentTemplateDataList.forEach(
            (element: DocumentTemplateData) => {
              if (element.securityDocuments) {
                this.savedDocuments = this.savedDocuments.concat(
                  element.securityDocuments
                );
              }
            }
          );

          this.prepareFacTemplates();
        }
      });
  }

  getDocumentTags() {
    this.facilityPaperAddEditService
      .getDocumentTags(this.facilityPaper.facilityPaperID)
      .then((data: any[]) => {
        this.documentTags = data !== null && data.length > 0 ? data : [];
      });
  }

  prepareFacTemplates() {
    this.fpFacilityTemplates = this.documentTemplateDataList
      .map((data: DocumentTemplateData, i: number) => ({
        ...data,
        securityDocumentID: 0,
        documentContent: "",
        documentName: "",
        selectedTabIndex: 0,
        expanded: i === 0,
        documentElements: this.getUpdatedTemplateDocuments(
          data.documentElements
        ),
        facilities: data.facilities.map((facility: Facility, i: number) => ({
          ...facility,
          tabTitle: `Facility ${facility.displayOrder} - ${
            facility.facilityCurrency
          } ${this.currencyPipe.transform(facility.facilityAmount, "", "")}`,
          ...this.prepareFacilityDocumnets(
            this.getUpdatedTemplateDocuments(data.documentElements, facility),
            facility
          ),
        })),
      }))
      .filter((template: FacilityTemplate) => this.isShowTemplate(template));
  }

  getUpdatedTemplateDocuments(
    documentElements: DocumentElement[],
    facility?: Facility
  ) {
    if (facility !== undefined && facility !== null) {
      return documentElements.map((d: DocumentElement) => ({
        ...d,
        isDisabled: this.isEmptyTagsExist(facility.facilityID, d.facilityTags),
      }));
    }
    return documentElements.map((d: DocumentElement) => ({
      ...d,
      isDisabled: false,
    }));
  }

  setTabIndex(index: number, creditFacilityName: string) {
    let fpFacilityTemplate: FacilityTemplate = this.fpFacilityTemplates.find(
      (d: FacilityTemplate) => d.creditFacilityName === creditFacilityName
    );

    if (
      fpFacilityTemplate !== undefined &&
      fpFacilityTemplate !== null &&
      fpFacilityTemplate.selectedTabIndex !== index
    ) {
      this.handleDocumentDisabled(creditFacilityName, 0);
      this.clearDocumentSelection();

      this.fpFacilityTemplates = this.fpFacilityTemplates.map(
        (d: FacilityTemplate) =>
          d.creditFacilityName === creditFacilityName
            ? {
                ...d,
                selectedTabIndex: index,
                expanded: true,
              }
            : d
      );
    }
  }

  getSecurityDocumentContent(
    documentElement: DocumentElement,
    facility: Facility,
    template: FacilityTemplate
  ) {
    this.selectedFacility = facility;
    this.selectedDocumentTemplate = template;
    this.selectedDocumentElement = documentElement;

    if (
      this.savedDocuments.some(
        (sd: any) =>
          sd.facilityID === facility.facilityID &&
          sd.elementID === documentElement.elementID &&
          sd.creditFacilityName === template.creditFacilityName
      )
    ) {
      let existingDocument: any = this.savedDocuments.find(
        (sd: any) =>
          sd.facilityID === facility.facilityID &&
          sd.elementID === documentElement.elementID &&
          sd.creditFacilityName === template.creditFacilityName
      );
      this.documentContent = existingDocument.documentContent;
      this.selectedDocumentTemplate = {
        ...this.selectedDocumentTemplate,
        securityDocumentID: existingDocument.securityDocumentID,
        documentContent: existingDocument.documentContent,
        documentName: existingDocument.documentName,
      };
      this.selectedDocumentStatus = existingDocument.documentStatus;

      this.handleTemplateExpand(template);

      this.handleDocumentDisabled(
        template.creditFacilityName,
        documentElement.elementID
      );
    } else {
      this.getInitialDocumentContent(template, documentElement, facility);
    }
  }

  getInitialDocumentContent(
    template: FacilityTemplate,
    documentElement: DocumentElement,
    facility: Facility
  ) {
    let request: any = {
      facilityPaperId: this.facilityPaper.facilityPaperID,
      folderName: documentElement.creditFacilityName.replace(/ /g, ""),
      documentName: documentElement.documentFileName,
      facilityId: facility.facilityID,
      elementName: documentElement.elementName.replace(/ /g, "_"),
      facilityDisplayOrder: facility.displayOrder,
    };

    this.facilityPaperAddEditService
      .getSecurityDocumentContent(request)
      .then((content: string) => {
        if (content !== null && content !== "") {
          this.documentContent = content;
          this.selectedDocumentTemplate = {
            ...this.selectedDocumentTemplate,
            documentContent: content,
          };
          this.selectedDocumentStatus = SDConstants.documentStatusConst.PENDING;
          this.handleDocumentDisabled(
            template.creditFacilityName,
            documentElement.elementID
          );
        } else {
          this.handleDocumentDisabled(
            this.selectedDocumentTemplate.creditFacilityName
          );
          this.clearDocumentSelection();
        }
        this.handleTemplateExpand(template);
      });
  }

  getElementsByDocumentStatus(
    templateDocuments: any[],
    facility: Facility,
    status: string
  ): DocumentElement[] {
    let result: DocumentElement[] = [];

    templateDocuments.forEach((element: any) => {
      if (status !== SDConstants.documentStatusConst.PENDING) {
        if (
          this.savedDocuments.some(
            (d: any) =>
              d.creditFacilityName === facility.creditFacilityName &&
              d.facilityID === facility.facilityID &&
              d.elementID === element.elementID &&
              d.documentStatus === status
          )
        ) {
          result.push(element);
        }
      } else {
        if (
          !this.savedDocuments.some(
            (d: any) =>
              d.creditFacilityName === facility.creditFacilityName &&
              d.facilityID === facility.facilityID &&
              d.elementID === element.elementID
          )
        ) {
          result.push(element);
        }
      }
    });

    return result;
  }

  openModalAddEdit(content?: string) {
    this.modalRef = this.mdbModalService.show(AddEditContentFeildComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "model-height-80-p model-width-55-p modal-dialog-scrollable",
      containerClass: "",
      animated: true,
      data: {
        content: {
          facilityPaper: this.facilityPaper,
          selectedDocumentTemplate:
            content !== undefined && content !== null && content !== ""
              ? {
                  ...this.selectedDocumentTemplate,
                  documentContent: content,
                }
              : this.selectedDocumentTemplate,
          selectedFacility: this.selectedFacility,
          selectedDocumentElement: this.selectedDocumentElement,
          selectedDocumentStatus: this.selectedDocumentStatus,
          documentTags: this.documentTags,
          isContentRefreshed: this.isContentRefreshed
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data !== null) {
        this.documentTags =
          data.documentTags !== undefined && data.documentTags !== null
            ? data.documentTags
            : this.documentTags;
        this.handleDocumentStatusChange(data);
      }
      this.isContentRefreshed = false;
    });
  }

  handleDocumentAction(status: string, comment?: string) {
    if (
      this.isBranchUser() &&
      status === SDConstants.documentStatusConst.PRINT
    ) {
      this.openModalAddEdit();
    } else {
      let securityDocumentData: any = {
        securityDocumentID: this.selectedDocumentTemplate.securityDocumentID,
        documentStatus: status,
        actionComment: comment !== undefined && comment !== null ? comment : "",
      };
      this.facilityPaperAddEditService
        .saveSecurityDocument(securityDocumentData)
        .then((data: any) => {
          if (data !== null) {
            this.handleDocumentStatusChange(data);

            if (status === SDConstants.documentStatusConst.PRINT) {
              this.previewDocument();
            }
          }
        });
    }
  }

  submitDocument() {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {
        heading: "Confirm Submit Document",
        message: "Do you want to submit this document?",
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (isYes) {
        this.handleDocumentAction(SDConstants.documentStatusConst.SUBMIT);
      }
    });
  }

  reccomandDocument() {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {
        heading: "Confirm Reccomand Document",
        message: "Do you want to reccomand this document?",
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (isYes) {
        this.handleDocumentAction(SDConstants.documentStatusConst.APPROVE);
      }
    });
  }

  returnDocument() {
    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          heading: "Add Return Comment",
          comment: "",
        },
      }
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let isApproved: boolean = this.isApprovedPaper();

        let status: string =
          (isApproved &&
            this.selectedDocumentStatus ===
              SDConstants.documentStatusConst.SUBMIT) ||
          this.selectedDocumentStatus ===
            SDConstants.documentStatusConst.APPROVE
            ? SDConstants.documentStatusConst.RECOMMEND_RETURN
            : SDConstants.documentStatusConst.RETURN;
        this.handleDocumentAction(status, data.comment);
      }
    });
  }

  prepareActionRequest() {
    return {
      securityDocumentID: this.selectedDocumentTemplate.securityDocumentID,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      creditFacilityTemplateID:
        this.selectedDocumentTemplate.creditFacilityTemplateID,
      creditFacilityName: this.selectedDocumentTemplate.creditFacilityName,
      elementID: this.selectedDocumentElement.elementID,
      facilityID: this.selectedFacility.facilityID,
      documentName: this.selectedDocumentTemplate.documentName,
      documentStatus: "",
      actionComment: "",
      documentContent: this.selectedDocumentTemplate.documentContent,
    };
  }

  viewActionLog() {
    this.facilityPaperAddEditService
      .getSecurityDocumentHistoryData(
        this.selectedDocumentTemplate.securityDocumentID
      )
      .then((data: any) => {
        this.modalRef = this.mdbModalService.show(ActionLogViewComponent, {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: "model-height-60-p model-width-100-p modal-dialog-scrollable",
          containerClass: "",
          animated: true,
          data: {
            content: {
              securityDocumentHistoryDTOList: data,
              selectedDocumentElement: this.selectedDocumentElement,
            },
          },
        });

        this.modalRef.content.action.subscribe((data: any) => {});
      });
  }

  previewDocument() {
    const templates = [
      "Offer Letter",
      "Samachara Loan With Facility",
      "Samachara Loan Without Facility",
    ];
    let fileName: string =
      this.selectedDocumentElement.documentFileName.replace(/-/g, " ");

    let documentName: string = this.selectedDocumentTemplate.documentName
      ? this.selectedDocumentTemplate.documentName
      : fileName;

    const matchDocName = templates.some((template) =>
      fileName.includes(template)
    );

    let department: string =
      this.selectedFacility.facilityTypeName === "Lease" ? "CCPU" : "CCDU";

    let printContents, popupWin;

    printContents = this.selectedDocumentTemplate.documentContent;
    popupWin = window.open("", "_blank", "top=0,left=0,height=80%,width=auto");
    popupWin.document.open();

    if (matchDocName) {
      popupWin.document.write(`
                 <html>
                    <head>
                      <script defer src="assets/js/polyfill.js"></script>
                      <script>
                        window.PagedConfig = {auto:false}
                        window.addEventListener('load', () => {window.PagedPolyfill.preview().then(() => {window.print();});});
                      </script>
                      <style type="text/css">
                           @page {size: A4;  margin-bottom: 1.5cm; margin-top: 3.5cm;
                           <!-- @bottom-right { content: "${department} | Page "counter(page)" of " counter(pages); }} -->
                           .template-footer {position: fixed; bottom:4cm; width: 100%; display:table;}
                           .template-footer p {page-break-before: always;font-size: 10pt;}
                           .template-footer p::before {content: "${department} | Page "counter(page)" of " counter(pages);display: block;text-align: right; margin-right: 2.5cm;}
                           .pagebreak {page-break-before: always; }
                      </style>
                       <title>${documentName}</title>
                    </head>
                    <body onafterprint="window.close()">${printContents}</body>
                  </html>`);
    } else {
      popupWin.document.write(`
                <html>
                  <head>
                    <title>${documentName}</title>
                  </head>
                  <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                </html>`);
    }

    popupWin.document.close();
  }

  handleDocumentStatusChange(data: any) {
    if (
      this.savedDocuments.some(
        (sd: any) => sd.securityDocumentID === data.securityDocumentID
      )
    ) {
      this.savedDocuments = this.savedDocuments.map((sd: any) =>
        sd.securityDocumentID === data.securityDocumentID
          ? {
              ...sd,
              documentContent: data.documentContent,
              documentStatus: data.documentStatus,
              authBy: data.authBy ? data.authBy : "",
              savedBy: data.savedBy ? data.savedBy : "",
            }
          : sd
      );
    } else {
      this.savedDocuments.push(data);
    }

    this.documentContent = data.documentContent;
    this.selectedDocumentTemplate = {
      ...this.selectedDocumentTemplate,
      securityDocumentID: data.securityDocumentID,
      documentContent: data.documentContent,
      documentName: data.documentName ? data.documentName : "",
    };
    this.selectedDocumentStatus = data.documentStatus;

    this.updateTemplateStructure();
    this.updateDocumentCount();

    this.cdr.detectChanges();
  }

  updateTemplateStructure() {
    this.fpFacilityTemplates = this.fpFacilityTemplates.map(
      (template: FacilityTemplate) =>
        template.creditFacilityName ===
        this.selectedDocumentTemplate.creditFacilityName
          ? {
              ...template,
              facilities: template.facilities.map((tf: Facility) =>
                tf.facilityID === this.selectedFacility.facilityID
                  ? {
                      ...tf,
                      ...this.prepareFacilityDocumnets(
                        template.documentElements,
                        tf
                      ),
                    }
                  : tf
              ),
            }
          : template
    );
    this.cdr.detectChanges();
  }

  prepareFacilityDocumnets(
    templateDocuments: DocumentElement[],
    facility: Facility
  ) {
    return {
      pendingDocuments: this.getElementsByDocumentStatus(
        templateDocuments,
        facility,
        SDConstants.documentStatusConst.PENDING
      ),
      draftedDocuments: this.getElementsByDocumentStatus(
        templateDocuments,
        facility,
        SDConstants.documentStatusConst.DRAFT
      ),
      returnedDocuments: this.getElementsByDocumentStatus(
        templateDocuments,
        facility,
        SDConstants.documentStatusConst.RETURN
      ),
      submittedDocuments: this.getElementsByDocumentStatus(
        templateDocuments,
        facility,
        SDConstants.documentStatusConst.SUBMIT
      ),
      recommandedDocuments: this.getElementsByDocumentStatus(
        templateDocuments,
        facility,
        SDConstants.documentStatusConst.APPROVE
      ),
    };
  }

  handleDocumentDisabled(creditFacilityName: string, elementID?: number) {
    if (elementID !== 0) {
      this.fpFacilityTemplates = this.fpFacilityTemplates.map(
        (d: FacilityTemplate) =>
          d.creditFacilityName === creditFacilityName
            ? {
                ...d,
                facilities: d.facilities.map((f: Facility) => ({
                  ...f,
                  pendingDocuments: f.pendingDocuments.map(
                    (doc: DocumentElement) =>
                      doc.elementID === elementID
                        ? { ...doc, isDisabled: true }
                        : {
                            ...doc,
                            isDisabled: this.isEmptyTagsExist(
                              f.facilityID,
                              doc.facilityTags
                            ),
                          }
                  ),
                })),
              }
            : {
                ...d,
                facilities: d.facilities.map((f: Facility) => ({
                  ...f,
                  pendingDocuments: f.pendingDocuments.map(
                    (pd: DocumentElement) => ({
                      ...pd,
                      isDisabled: this.isEmptyTagsExist(
                        f.facilityID,
                        pd.facilityTags
                      ),
                    })
                  ),
                })),
              }
      );
    } else {
      this.fpFacilityTemplates = this.fpFacilityTemplates.map(
        (d: FacilityTemplate) =>
          d.creditFacilityName === creditFacilityName
            ? {
                ...d,
                facilities: d.facilities.map((f: Facility) => ({
                  ...f,
                  pendingDocuments: f.pendingDocuments.map(
                    (doc: DocumentElement) => ({
                      ...doc,
                      isDisabled: this.isEmptyTagsExist(
                        f.facilityID,
                        doc.facilityTags
                      ),
                    })
                  ),
                })),
              }
            : {
                ...d,
                facilities: d.facilities.map((f: Facility) => ({
                  ...f,
                  pendingDocuments: f.pendingDocuments.map(
                    (pd: DocumentElement) => ({
                      ...pd,
                      isDisabled: this.isEmptyTagsExist(
                        f.facilityID,
                        pd.facilityTags
                      ),
                    })
                  ),
                })),
              }
      );
    }
  }

  clearDocumentSelection() {
    this.documentContent = "";
    this.selectedDocumentStatus = "";
    this.selectedFacility = newFacility();
    this.selectedDocumentTemplate = newFacilityTemplate();
    this.selectedDocumentElement = newDocumentElement();
  }

  showPendingList(facilityTypeName: string) {
    let loggedInUserDivCode: any =
      this.applicationService.getLoggedInUserDivCode();
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (loggedInUserDivCode === SDConstants.CCDU_DIV_CODE) {
      return (
        facilityTypeName !== "Lease" &&
        this.isApprovedPaper() &&
        parseInt(loggedInUserWorkClass) === 10
      );
    }

    if (loggedInUserDivCode === SDConstants.CCPU_DIV_CODE) {
      return (
        facilityTypeName === "Lease" &&
        this.isAllowedPaper() &&
        this.isAllowedUser() &&
        parseInt(loggedInUserWorkClass) <= 50
      );
    }

    return false;
  }

  showDraftedList(facility: Facility) {
    let loggedInUserDivCode: any =
      this.applicationService.getLoggedInUserDivCode();
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (loggedInUserDivCode === SDConstants.CCDU_DIV_CODE) {
      return (
        facility.facilityTypeName !== "Lease" &&
        this.isApprovedPaper() &&
        facility.draftedDocuments.length > 0 &&
        parseInt(loggedInUserWorkClass) === 10
      );
    }

    if (loggedInUserDivCode === SDConstants.CCPU_DIV_CODE) {
      return (
        facility.facilityTypeName === "Lease" &&
        facility.draftedDocuments.length > 0 &&
        parseInt(loggedInUserWorkClass) <= 50
      );
    }
    return false;
  }

  showReturnedList(facility: Facility) {
    let loggedInUserDivCode: any =
      this.applicationService.getLoggedInUserDivCode();
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (loggedInUserDivCode === SDConstants.CCDU_DIV_CODE) {
      return (
        facility.facilityTypeName !== "Lease" &&
        this.isApprovedPaper() &&
        facility.returnedDocuments.length > 0 &&
        parseInt(loggedInUserWorkClass) === 10
      );
    }

    if (loggedInUserDivCode === SDConstants.CCPU_DIV_CODE) {
      return (
        facility.facilityTypeName === "Lease" &&
        facility.returnedDocuments.length > 0 &&
        parseInt(loggedInUserWorkClass) <= 50
      );
    }
    return false;
  }

  showSubmittedList(facility: Facility) {
    let loggedInUserDivCode: any =
      this.applicationService.getLoggedInUserDivCode();
    return (
      facility.submittedDocuments.length > 0 &&
      (loggedInUserDivCode === SDConstants.CCPU_DIV_CODE ||
        loggedInUserDivCode === SDConstants.CCDU_DIV_CODE)
    );
  }

  showEditDocumentButton(): boolean {
    const status = this.selectedDocumentStatus;
    let loggedInUserUserName: any =
      this.applicationService.getLoggedInUserUserName();
    if (this.isCCPUEnter()) {
      if (status === SDConstants.documentStatusConst.RETURN) {
        return (
          (this.checkIsRecommendReturn() &&
            loggedInUserUserName !== this.getAuthBy()) ||
          (this.isAllowedPaper() &&
            this.isAllowedUser() &&
            !this.isCCPUAuthorizer())
        );
      }

      return (
        (this.checkIsRecommendReturn() &&
          status === SDConstants.documentStatusConst.DRAFT) ||
        (this.isAllowedPaper() &&
          this.isAllowedUser() &&
          [
            SDConstants.documentStatusConst.PENDING,
            SDConstants.documentStatusConst.DRAFT,
          ].includes(status))
      );
    }

    if (this.isCCDUEnter()) {
      const isApproved =
        this.isApprovedPaper();
      return (
        isApproved &&
        [
          SDConstants.documentStatusConst.RETURN,
          SDConstants.documentStatusConst.PENDING,
          SDConstants.documentStatusConst.DRAFT,
        ].includes(status)
      );
    }

    return false;
  }

  showSubmitDocumentButton() {
    if (this.isCCPUEnter()) {
      return (
        this.checkIsRecommendReturn() &&
        this.isApprovedPaper() &&
        this.selectedDocumentStatus === SDConstants.documentStatusConst.DRAFT
      );
    }

    return (
      this.isCCDUEnter() &&
      this.isApprovedPaper() &&
      this.selectedDocumentStatus === SDConstants.documentStatusConst.DRAFT
    );
  }

  showAuthorizeActionButton() {
    if (this.isCCPUAuthorizer()) {
      let isApproved: boolean = this.isApprovedPaper();
      if (isApproved) {
        return (
          this.selectedDocumentStatus === SDConstants.documentStatusConst.SUBMIT
        );
      }
      return (
        this.isAllowedPaper() &&
        this.isAllowedUser() &&
        this.selectedDocumentStatus === SDConstants.documentStatusConst.SUBMIT
      );
    }

    if (this.isNotCCDUEnter()) {
      return (
        this.isApprovedPaper() &&
        this.selectedDocumentStatus === SDConstants.documentStatusConst.SUBMIT
      );
    }

    return false;
  }

  showReccomandReturn(facility: Facility) {
    if (this.isCCPUAuthorizer()) {
      let loggedInUserUserName: any =
        this.applicationService.getLoggedInUserUserName();

      if (this.isAllowedPaper()) {
        return (
          this.isAllowedUser() &&
          loggedInUserUserName !== this.getAuthBy() &&
          [SDConstants.documentStatusConst.APPROVE].includes(
            this.selectedDocumentStatus
          )
        );
      }

      return (
        [SDConstants.documentStatusConst.APPROVE].includes(
          this.selectedDocumentStatus
        ) &&
        loggedInUserUserName !== this.getAuthBy() &&
        this.facilityPaper.currentFacilityPaperStatus !==
          Constants.facilityPaperStatusConst.REJECTED
      );
    }

    if (this.isBranchUser()) {
      return (
        facility.facilityTypeName !== "Lease" &&
        this.isApprovedPaper() &&
        [SDConstants.documentStatusConst.APPROVE].includes(
          this.selectedDocumentStatus
        )
      );
    }
    return false;
  }

  showReccomandApprove() {
    if (this.isCCPUAuthorizer()) {
      return (
        this.isApprovedPaper() &&
        this.selectedDocumentStatus === SDConstants.documentStatusConst.SUBMIT
      );
    }
    return false;
  }

  showActionLogButton() {
    return (
      this.selectedDocumentStatus !== SDConstants.documentStatusConst.PENDING
    );
  }

  showPrintButton() {
    return (
      this.selectedDocumentStatus === SDConstants.documentStatusConst.APPROVE &&
      this.isBranchUser()
    );
  }

  showRefreshButton() {
    let isAllowedDocument =
      this.selectedDocumentStatus === SDConstants.documentStatusConst.DRAFT ||
      this.selectedDocumentStatus === SDConstants.documentStatusConst.RETURN;

    if (this.isCCPUEnter() && !this.isCCPUAuthorizer()) {
      if (this.isApprovedPaper()) {
        return isAllowedDocument;
      }

      return this.isAllowedUser() && this.isAllowedPaper() && isAllowedDocument;
    }

    if (this.isCCDUEnter()) {
      return this.isApprovedPaper() && isAllowedDocument;
    }

    return false;
  }

  isNotCCDUEnter(): boolean {
    return (
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) > 10 &&
      this.applicationService.getLoggedInUserDivCode() ===
        SDConstants.CCDU_DIV_CODE
    );
  }

  isCCDUEnter(): boolean {
    return (
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) === 10 &&
      this.applicationService.getLoggedInUserDivCode() ===
        SDConstants.CCDU_DIV_CODE
    );
  }

  isCCPUEnter(): boolean {
    return (
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) <= 50 &&
      this.applicationService.getLoggedInUserDivCode() ===
        SDConstants.CCPU_DIV_CODE
    );
  }

  isBranchUser(): boolean {
    return (
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) <= 50 &&
      this.applicationService.getLoggedInUserDivCode() ===
        this.facilityPaper.branchCode
    );
  }

  handleCancel() {
    this.handleDocumentDisabled(
      this.selectedDocumentTemplate.creditFacilityName
    );
    this.clearDocumentSelection();
  }

  handleTemplateExpand(template: FacilityTemplate) {
    this.fpFacilityTemplates = this.fpFacilityTemplates.map(
      (temp: FacilityTemplate) => ({
        ...temp,
        expanded: temp.creditFacilityName === template.creditFacilityName,
      })
    );
  }

  isElementsExpand(documentStatus: string) {
    switch (documentStatus) {
      case SDConstants.documentStatusConst.PENDING:
        return (
          this.selectedDocumentStatus === "" ||
          this.selectedDocumentStatus ===
            SDConstants.documentStatusConst.PENDING
        );

      default:
        return this.selectedDocumentStatus === documentStatus;
    }
  }

  isEmptyTagsExist(facilityId: number, tags: any[]) {
    return tags !== null && tags.some((t: any) => t.facilityId === facilityId);
  }

  handleOpenTagInfo(facility: Facility, tags: any[]) {
    let facilityTag: any = tags.find(
      (t: any) => t.facilityId === facility.facilityID
    );

    let emptyEntries: any[] =
      facilityTag !== null ? facilityTag.emptyEntries : [];
    this.modalRef = this.mdbModalService.show(SdInfoDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Document Element Required Field",
        message: `The following information is not provided by the facility (${facility.creditFacilityName} - ${facility.tabTitle}),`,
        tags: emptyEntries,
        showConfirm: false,
      },
    });
  }

  isShowTemplate(template: FacilityTemplate) {
    return template.facilities.some(
      (facility: Facility) =>
        (this.showPendingList(facility.facilityTypeName) &&
          facility.pendingDocuments.length !== 0) ||
        facility.draftedDocuments.length !== 0 ||
        facility.submittedDocuments.length !== 0 ||
        facility.returnedDocuments.length !== 0 ||
        facility.recommandedDocuments.length !== 0
    );
  }

  isCCPUAuthorizer() {
    let loggedInUserDivCode: any =
      this.applicationService.getLoggedInUserDivCode();
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();
    let loggedInUserUserName: any =
      this.applicationService.getLoggedInUserUserName();
    let isCCPU = loggedInUserDivCode === SDConstants.CCPU_DIV_CODE;
    let documentSavedBy: string = this.getSavedBy();
    let documentAuthBy: string = this.getSavedBy();

    return (
      isCCPU &&
      parseInt(loggedInUserWorkClass) >= 50 &&
      documentSavedBy !== loggedInUserUserName &&
      documentAuthBy !== loggedInUserUserName
    );
  }

  getSavedBy() {
    let data: any = this.savedDocuments.find(
      (d: any) =>
        d.securityDocumentID ===
        this.selectedDocumentTemplate.securityDocumentID
    );
    return data !== undefined && data !== null ? data.savedBy : "";
  }

  getAuthBy() {
    let data: any = this.savedDocuments.find(
      (d: any) =>
        d.securityDocumentID ===
        this.selectedDocumentTemplate.securityDocumentID
    );
    return data !== undefined && data !== null ? data.authBy : "";
  }

  checkIsRecommendReturn(): boolean {
    let data: any = this.savedDocuments.find(
      (d: any) =>
        d.securityDocumentID ===
        this.selectedDocumentTemplate.securityDocumentID
    );
    return data !== undefined && data !== null
      ? data.isRecommendedReturn === Constants.yesNoConst.Y
      : false;
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
    return (
      this.facilityPaper.currentAssignUser ===
      this.applicationService.getLoggedInUserUserName()
    );
  }

  updateDocumentCount() {
    let counts: any = {
      draftedCount: this.savedDocuments.filter(
        (sd: any) => sd.documentStatus === SDConstants.documentStatusConst.DRAFT
      ).length,
      submittedCount: this.savedDocuments.filter(
        (sd: any) =>
          sd.documentStatus === SDConstants.documentStatusConst.SUBMIT
      ).length,
      returnedCount: this.savedDocuments.filter(
        (sd: any) =>
          sd.documentStatus === SDConstants.documentStatusConst.RETURN
      ).length,
    };

    this.facilityPaperAddEditService.onFPDocumnetCount.next(counts);
  }

  handleRefresh() {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {
        heading: "Confirm Refresh Document",
        message:
          "All user-entered data will be lost as a result of this action. Do you want this document to be refresh?",
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (isYes) {
        this.isContentRefreshed = true;
        let request: any = {
          facilityPaperId: this.facilityPaper.facilityPaperID,
          folderName: this.selectedDocumentElement.creditFacilityName.replace(
            / /g,
            ""
          ),
          documentName: this.selectedDocumentElement.documentFileName,
          facilityId: this.selectedFacility.facilityID,
          elementName: this.selectedDocumentElement.elementName.replace(
            / /g,
            "_"
          ),
          facilityDisplayOrder: this.selectedFacility.displayOrder,
        };

        this.facilityPaperAddEditService
          .getSecurityDocumentContent(request)
          .then((content: string) => {
            if (content !== null && content !== "") {
              this.openModalAddEdit(content);
            } else {
              this.handleDocumentDisabled(
                this.selectedDocumentTemplate.creditFacilityName
              );
              this.clearDocumentSelection();
            }
            this.handleTemplateExpand(this.selectedDocumentTemplate);
          });
      }
    });
  }

  showCovenantButton() {
    const status = this.selectedDocumentStatus;
    let loggedInUserUserName: any =
      this.applicationService.getLoggedInUserUserName();

    if (
      this.isApprovedPaper() &&
      this.isCCPUEnter() &&
      this.selectedDocumentElement.elementName === "Offer Letter" &&
      [
        SDConstants.documentStatusConst.DRAFT,
        SDConstants.documentStatusConst.RETURN,
      ].includes(status)
    ) {
      return (
        this.checkIsRecommendReturn() &&
        loggedInUserUserName !== this.getAuthBy() &&
        !this.isCCPUAuthorizer()
      );
    }

    return false;
  }

  openCovenantListModal() {
    this.modalRef = this.mdbModalService.show(SdCovenantModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-50-p modal-margin-center",
      animated: true,
      data: {
        fpRefNumber: this.facilityPaper.fpRefNumber,
        facilityId: this.selectedFacility.facilityID,
        selectedCovenantsList: this.getDocumentCovenants(),
      },
    });

    this.modalRef.content.action.subscribe((selectedCovenants: any[]) => {
      if (selectedCovenants !== null && selectedCovenants.length > 0) {
        this.selectedCovenantsList = selectedCovenants;

        let updateDocumentCount = this.bindCovenantToContent();
        this.documentContent = updateDocumentCount;
        this.selectedDocumentTemplate.documentContent = updateDocumentCount;

        this.handleSaveDocumentCovenant();
      }
    });
  }

  getDocumentCovenants() {
    let result: any[] = [];

    let elementId: string = "sd-covn-list";

    const parser = new DOMParser();
    const doc = parser.parseFromString(
      this.selectedDocumentTemplate.documentContent,
      "text/html"
    );

    const targetOLElement = doc.getElementById(elementId);

    if (!targetOLElement) return [];
    const lis = targetOLElement.querySelectorAll("li");
    lis.forEach((li, idx) => {
      if (idx !== lis.length - 1) {
        const span = li.querySelector("span");
        if (span) {
          result.push(span.innerHTML);
        }
      }
    });

    return result;
  }

  bindCovenantToContent(): any {
    let elementId: string = "sd-covn-list";

    const parser = new DOMParser();
    const doc = parser.parseFromString(
      this.selectedDocumentTemplate.documentContent,
      "text/html"
    );

    const targetOLElement = doc.getElementById(elementId);

    if (!targetOLElement) return this.selectedDocumentTemplate.documentContent;

    const lis = targetOLElement.querySelectorAll("li");
    lis.forEach((li, idx) => {
      if (idx !== lis.length - 1) li.remove();
    });

    const newItems: HTMLLIElement[] = this.selectedCovenantsList.map((cov) => {
      const li = document.createElement("li");
      li.style.paddingTop = "10pt";

      const span = document.createElement("span");
      span.textContent = cov.covenant_Description;
      li.append(span);
      return li;
    });

    this.toReversed(newItems).forEach((li: any) => {
      targetOLElement.insertBefore(li, targetOLElement.firstChild);
    });

    return doc.documentElement.outerHTML;
  }

  toReversed(data: any[]): any[] {
    return data.reverse();
  }

  handleSaveDocumentCovenant() {
    let securityDocumentData: any = {
      securityDocumentID: this.selectedDocumentTemplate.securityDocumentID,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      creditFacilityTemplateID:
        this.selectedDocumentTemplate.creditFacilityTemplateID,
      creditFacilityName: this.selectedDocumentTemplate.creditFacilityName,
      elementID: this.selectedDocumentElement.elementID,
      facilityID: this.selectedFacility.facilityID,
      documentName: this.selectedDocumentTemplate.documentName,
      documentStatus: SDConstants.documentStatusConst.DRAFT,
      actionComment: `Covenants updated by ${this.applicationService.getLoggedInUserDisplayName()}`,
      documentContent: this.documentContent,
      documentTags: [],
    };

    this.facilityPaperAddEditService
      .saveSecurityDocument(securityDocumentData)
      .then((data: any) => {
        if (data !== null) {
          this.handleDocumentStatusChange(data);
        }
      });
  }

  isApprovedPaper(): boolean {
    return (
      this.facilityPaper.currentFacilityPaperStatus ===
      Constants.facilityPaperStatusConst.APPROVED
    );
  }
}
