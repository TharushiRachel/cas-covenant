import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ChangeDetectorRef,
} from "@angular/core";
import { AdvanceAnalyticsService } from "../../services/advance-analytics.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { DigitalApplicationModalComponent } from "../digital-application-modal/digital-application-modal.component";
import { LeadComprehensiveService } from "../../services/lead-comprehensive.service";
import { combineAll } from "rxjs-compat/operator/combineAll";
import { Constants } from "src/app/core/setting/constants";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { AlertService } from "src/app/core/service/common/alert.service";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { DigitalApplicantPickerModalComponent } from "../digital-applicant-picker-modal/digital-applicant-picker-modal.component";

@Component({
  selector: "app-digital-application-view",
  templateUrl: "./digital-application-view.component.html",
  styleUrls: ["./digital-application-view.component.scss"],
})
export class DigitalApplicationViewComponent implements OnInit {
  modalRef: MDBModalRef;
  documentContent: string = "";
  leadId: number = 0;
  applicantCount = 1;
  isEditEnabled: boolean = false;

  @Input("leadData") leadData: any = {};
  @Input() leadStatus: any;
  @Input("compLeadId") compLeadId: any;
  @Input("digitalApplications") digitalApplications: any[] = [];
  @Output() refreshDigitalApplications = new EventEmitter<void>();
  @Output() digitalApplicationSaved = new EventEmitter<any>();
  selectedApplicantIds: number[] = [];
  private partiesLoadedAt: number = 0;
  private lastReloadPromise: Promise<void> | null = null;

  parties: any[] = [];


  constructor(
    private readonly leadComprehensiveService: LeadComprehensiveService,
    private readonly applicationService: ApplicationService,
    private readonly mdbModalService: MDBModalService,
    private cdr: ChangeDetectorRef,
    private readonly alertService: AlertService,
  ) { }

  ngOnInit() {
    console.log("lead data:: ", this.leadData);

    this.leadId = this.leadData !== null ? this.leadData.leadId : 0;
    if (this.leadData !== null && this.leadData.parties !== null) {
      this.parties = this.leadData.parties;
    }
  }


  editApplicationForm(selectedApplicationId?: number) {
    const html = this.documentContent || "";

    this.modalRef = this.mdbModalService.show(
      DigitalApplicationModalComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "model-height-80-p model-width-65-p modal-dialog-scrollable",
        containerClass: "",
        animated: true,
        data: {
          content: {
            selectedDocumentContent: html,
            facilityPaper: {},
            selectedDocumentStatus: "",
            leadId: this.leadId,
            compLeadId: this.compLeadId,
            selectedApplicantIds: Array.isArray(this.selectedApplicantIds) ? this.selectedApplicantIds : [],
            selectedApplicationId: selectedApplicationId
              ? selectedApplicationId
              : 0,
            applicantCount: this.applicantCount || 1,
          },
        },
      },
    );

    this.modalRef.content.action.subscribe((evt: any) => {
      const saved = evt.saved ? evt.saved : evt;

      const used = new Set<number>(this.selectedApplicantIds || []);
      (this.leadData.parties || []).forEach(p => {
        if (used.has(p.compPartyId)) p.isDigitalApplicationCreated = 'Y';
      });


      if (saved && saved.digitalApplicationID) {
        this.digitalApplicationSaved.emit(saved);
        this.refreshDigitalApplications.emit();
      }
      this.isEditEnabled = false;
    });
  }

  getDigitalApplications() {
    this.leadComprehensiveService
      .getDigitalApplications(this.leadId)
      .then((res: any[]) => {
        // Server should already filter, but add a defensive client filter:
        const safe = Array.isArray(res) ? res : [];
        this.digitalApplications = safe.filter(
          x => x && x.digitalApplicationID && x.documentStatus !== 'DELETE'
        );
        this.cdr.detectChanges();
      })
      .catch((err) => {
        console.warn('getDigitalApplications failed', err);
        this.digitalApplications = [];
      });
  }

  getDigitalApplicationsById(digitalApplicationID: number) {
    this.leadComprehensiveService
      .getDigitalApplicationById(digitalApplicationID)
      .then((res: any) => {
        if (res) {
          this.documentContent = res.documentContent;
          this.applicantCount = this.detectApplicantCountFromHtml(this.documentContent);
          this.editApplicationForm(digitalApplicationID);
        }
      });
  }

  detectApplicantCountFromHtml(html: string): number {
    if (!html) return 1;

    const doc = new DOMParser().parseFromString(html, "text/html");

    const tables = doc.querySelectorAll("#APPLICANTS_TABLE");
    if (tables && tables.length > 0) {
      const table = tables[0];
      const ths = table.querySelectorAll("thead tr th");
      const n = Math.max(0, ths.length - 1);
      if (n > 0) return n;
    }

    let max = 0;
    const allWithId = Array.from(doc.querySelectorAll("[id]")) as HTMLElement[];
    for (const el of allWithId) {
      const m = el.id.match(/Applicant-(\d+)/i);
      if (m) {
        const v = parseInt(m[1], 10);
        if (!isNaN(v)) max = Math.max(max, v);
      }
    }
    if (max > 0) return max;

    const text = doc.body.textContent || "";
    const matches = text.match(/Applicant\s*\[\s*(\d+)\s*\]/gi) || [];
    if (matches.length > 0) {
      const nums = matches
        .map((s) => parseInt((s.match(/(\d+)/) || [])[1], 10))
        .filter((n) => !isNaN(n));
      if (nums.length) return Math.max(...nums);
    }

    return 1;
  }

  applyPronouns(html: string, applicantCount: number): string {
    if (!html) return html;

    const IWe = applicantCount > 1 ? "We" : "I";
    const MeUs = applicantCount > 1 ? "us" : "me";
    const MyOur = applicantCount > 1 ? "our" : "my";

    const doc = new DOMParser().parseFromString(html, "text/html");

    const setText = (id: string, value: string) => {
      const el = doc.getElementById(id);
      if (el) el.innerHTML = value;
    };

    // --- Insurance consent ids  ---
    [
      "IS-DPL-Insurance-Consent-IWe-2",
      "IS-DPL-Insurance-Consent-IWe-3",
    ].forEach((id) => setText(id, IWe));

    [
      "IS-DPL-Insurance-Consent-MeUs-2",
      "IS-DPL-Insurance-Consent-MeUs-3",
    ].forEach((id) => setText(id, MeUs));

    setText("IS-DPL-Insurance-Consent-MyOur", MyOur);

    // --- Section 10 ids  ---
    [
      "IS-DPL-Section10-IWe",
      "IS-DPL-Section10-IWe-2",
      "IS-DPL-Section10-IWe-3",
      "IS-DPL-Section10-IWe-4",
      "IS-DPL-Section10-IWe-5",
      "IS-DPL-Section10-IWe-6",
      "IS-DPL-Section10-IWe-7",
      "IS-DPL-Section10-IWe-8",
      "IS-DPL-Section10-IWe-9",
      "IS-DPL-Section10-IWe-10",
      "IS-DPL-Section10-IWe-11",
      "IS-DPL-Section10-IWe-12",
    ].forEach((id) => setText(id, IWe));

    ["IS-DPL-Section10-MeUs", "IS-DPL-Section10-MeUs-1"].forEach((id) =>
      setText(id, MeUs),
    );

    [
      "IS-DPL-Section10-MyOur",
      "IS-DPL-Section10-MyOur-2",
      "IS-DPL-Section10-MyOur-3",
      "IS-DPL-Section10-MyOur-4",
      "IS-DPL-Section10-MyOur-5",
      "IS-DPL-Section10-MyOur-6",
      "IS-DPL-Section10-MyOur-7",
    ].forEach((id) => setText(id, MyOur));

    return doc.documentElement.outerHTML;
  }

  isActionEnabled() {
    return this.isAllowedUser() ;
  }

  isAllowedUser() {
    if (this.leadData !== null) {
      let upmGroupCode: number =
        this.applicationService.getLoggedInUserUPMGroupCode() !== null
          ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
          : 0;
      return (
        this.applicationService.getLoggedInUserUserName() ===
        this.leadData.assignUserId &&
        (upmGroupCode === 10 || upmGroupCode === SETTINGS.SALES_PERSON_WC)
      );
    }
    return false;
  }

  isAllowedLead() {
    if (this.leadStatus !== null) {
      return [
        Constants.leadStatusConst.PENDING,
        Constants.leadStatusConst.RETURNED,
      ].includes(this.leadStatus.status);
    }

    return false;
  }

  viewDocument(digitalApplicationID: any) {
    this.leadComprehensiveService
      .getDigitalApplicationById(digitalApplicationID)
      .then((res: any) => {
        if (res) {
          this.documentContent = res.documentContent;
        }
      });
  }

  downloadDocument(digitalApplicationID: any) {
    this.leadComprehensiveService
      .getDigitalApplicationById(digitalApplicationID)
      .then((res: any) => {
        if (res) {
          this.previewDocument(res.documentContent);
        }
      });
  }

  previewDocument(content: string) {
    let printContents, popupWin;
    let documentName: string = "E-Application";
    printContents = content;
    popupWin = window.open("", "_blank", "top=0,left=0,height=80%,width=auto");
    popupWin.document.open();

    popupWin.document.write(`
                <html>
                  <head>
                    <title>${documentName}</title>
                  </head>
                  <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                </html>`);

    popupWin.document.close();
  }

  cancelApplicationForm() {
    this.documentContent = "";
    this.isEditEnabled = false;
  }

  deleteDigitalApplication(appId: number): void {
    if (!appId) { return; }

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center',
      animated: true,
      data: {
        heading: 'Confirm Delete Application',
        message: 'Do you really want to delete this Digital application?',
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (!isYes) { return; }

      this.leadComprehensiveService
        .deleteDigitalApplication(appId)
        .then((updatedParties: any[]) => {
          this.digitalApplications = (this.digitalApplications || []).filter(
            r => r && r.digitalApplicationID !== appId
          );

          if (Array.isArray(updatedParties)) {
            this.leadData.parties = [...updatedParties];
            this.cdr.detectChanges();
          }

          this.alertService.showToaster(
            'Application deleted successfully',
            SETTINGS.TOASTER_MESSAGES.success
          );

          // (optional) still refresh DA list from backend
          this.getDigitalApplications();
        })
        .catch(() => {
          this.alertService.showToaster(
            'Failed to delete application',
            SETTINGS.TOASTER_MESSAGES.error
          );
        });

    });

  }

  async handleOpenApplicationForm(): Promise<void> {
    const compId = Number(this.compLeadId || 0);
    if (!compId) return;

    const source = Array.isArray(this.leadData.parties) ? this.leadData.parties : [];

    const allowedParties = source.filter(p =>
      String(p.isDigitalApplicationCreated || 'N').trim().toUpperCase() !== 'Y'
    );

    // Open the applicant picker first
    const modalRef = this.mdbModalService.show(DigitalApplicantPickerModalComponent, {
      backdrop: true,
      keyboard: true,
      ignoreBackdropClick: true,
      class: 'modal-width-40-p modal-margin-center',
      data: { compLeadId: compId, parties: allowedParties }
    });

    console.log('[Before picker] total=', (this.leadData.parties || []).length);
    console.log('[Before picker] allowed=',
      (this.leadData.parties || []).filter(p =>
        String(p.isDigitalApplicationCreated || 'N').trim().toUpperCase() !== 'Y'
      ).length
    );

    modalRef.content.action.subscribe(({ selectedIds }) => {
      this.selectedApplicantIds = Array.isArray(selectedIds) ? selectedIds : [];
      this.leadComprehensiveService
        .getDigitalApplicationContentWithApplicants(compId, selectedIds)
        .then((html: string) => {
          if (!html) { return; }
          this.applicantCount = this.detectApplicantCountFromHtml(html);
          this.documentContent = this.applyPronouns(html, this.applicantCount);
          this.isEditEnabled = true;
          this.editApplicationForm();
        });
    });
  }

  reloadLeadParties(): Promise<void> {
    const leadId = this.leadData.leadId;
    if (!leadId) return Promise.resolve();

    return this.leadComprehensiveService.getLeadById(leadId).then((res: any) => {
      const body = res.result || res;
      const lead = body.response || body;
      if (lead && Array.isArray(lead.parties)) {
        this.leadData.parties = lead.parties;
        this.cdr.detectChanges();
      }
    });
  }

  ensureFreshParties(): Promise<void> {
    if (!this.lastReloadPromise) {
      this.lastReloadPromise = this.reloadLeadParties().finally(() => {
        this.partiesLoadedAt = Date.now();
        this.lastReloadPromise = null;
      });
    }
    return this.lastReloadPromise;
  }

  isAllowedParties(): boolean {
    const source = Array.isArray(this.leadData.parties) ? this.leadData.parties : [];

    const allowedParties = source.filter(p => String(p.isDigitalApplicationCreated || 'N').trim().toUpperCase() !== 'Y');

    return allowedParties.length > 0;
  }



}
