import { PreloadAllModules, RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import { HomeComponent } from "./base/home/home.component";
import { AuthGuard } from "./core/guard/auth.guard";
import { MainComponent } from "./base/main/main.component";
import { RiskOpinionHistoryComponent } from "./views/pages/risk-opinion-history/risk-opinion-history.component";

const routes: Routes = [
  { path: "auth", loadChildren: "./views/pages/auth/auth.module#AuthModule" },
  {
    path: "",
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: "home",
        pathMatch: "full",
        component: HomeComponent,
      },
      {
        path: "roles",
        loadChildren: "./views/pages/role/role.module#RoleModule",
      },
      {
        path: "user-delegated-authorities",
        loadChildren:
          "./views/pages/user-delegated-authority/user-delegated-authority.module#UserDelegatedAuthorityModule",
      },
      {
        path: "support-documents",
        loadChildren:
          "./views/pages/support-document/support-document.module#SupportDocumentModule",
      },

      {
        path: "credit-facility-types",
        loadChildren:
          "./views/pages/credit-facility-type/credit-facility-type.module#CreditFacilityTypeModule",
      },

      {
        path: "credit-facility-type-templates",
        loadChildren:
          "./views/pages/credit-facility-type-template/credit-facility-type-template.module#CreditFacilityTypeTemplateModule",
      },
      {
        path: "section-sub-section",
        loadChildren:
          "./views/pages/section-sub-section/section-sub-section.module#SectionSubSectionModule",
      },
      {
        path: "upc-template",
        loadChildren:
          "./views/pages/upc-template/upc-template.module#UpcTemplateModule",
      },

      {
        path: "upm-group",
        loadChildren: "./views/pages/upm-group/upm-group.module#UpmGroupModule",
      },

      {
        path: "workflow-template",
        loadChildren:
          "./views/pages/workflow-template/workflow-template.module#WorkflowTemplateModule",
      },

      {
        path: "leads",
        loadChildren: "./views/pages/lead/lead.module#LeadModule",
      },

      {
        path: "my-branch-leads",
        loadChildren:
          "./views/pages/my-branch-leads/my-branch-leads.module#MyBranchLeadsModule",
      },

      {
        path: "audit",
        loadChildren: "./views/pages/audit/audit.module#AuditModule",
      },

      {
        path: "customer-360",
        loadChildren:
          "./views/pages/customer-360/customer-360.module#Customer360Module",
      },
      {
        path: "acae",
        loadChildren: "./views/pages/acae/acae.module#ACAEModule",
      },
      {
        path: "facility",
        loadChildren: "./views/pages/facility/facility.module#FacilityModule",
      },
      {
        path: "facility-paper",
        loadChildren:
          "./views/pages/facility-paper/facility-paper.module#FacilityPaperModule",
      },
      {
        path: "covenant",
        loadChildren:
          "./views/pages/covenant/covenant.module#CovenantModule",
      },
      {
        path: "my-facility-papers",
        loadChildren:
          "./views/pages/my-facility-papers/my-facility-papers.module#MyFacilityPapersModule",
      },

      {
        path: "facility-paper-transfer",
        loadChildren:
          "./views/pages/facility-paper-transfer/facility-paper-transfer.module#FacilityPaperTransferModule",
      },

      {
        path: "agents",
        loadChildren: "./views/pages/agent/agent.module#AgentModule",
      },

      {
        path: "facility-review",
        loadChildren:
          "./views/pages/facility-paper-review/facility-paper-review.module#FacilityPaperReviewModule",
      },

      {
        path: "bcc-reporting",
        loadChildren:
          "./views/pages/bcc-reporting/bcc-reporting.module#BccReportingModule",
      },

      {
        path: "application-form/add-edit",
        loadChildren:
          "./views/pages/application-form/application-form-add-edit/application-form-add-edit.module#ApplicationFormAddEditModule",
      },

      {
        path: "application-form/create",
        loadChildren:
          "./views/pages/application-form/application-form-create/application-form-create.module#ApplicationFormCreateModule",
      },

      {
        path: "application-form/inbox",
        loadChildren:
          "./views/pages/application-form/application-form-inbox/application-form-inbox.module#ApplicationFormInboxModule",
      },

      {
        path: "application-form/branch",
        loadChildren:
          "./views/pages/application-form/branch-application-form/branch-application-form.module#BranchApplicationFormModule",
      },

      {
        path: "application-form-transfer",
        loadChildren:
          "./views/pages/application-form/application-form-transfer/application-form-transfer.module#ApplicationFormTransferModule",
      },

      {
        path: "application-forms",
        loadChildren:
          "./views/pages/application-form/application-form/application-form.module#ApplicationFormModule",
      },

      {
        path: "application-form/copy",
        loadChildren:
          "./views/pages/application-form/application-form-copy/application-form-copy.module#ApplicationFormCopyModule",
      },

      {
        path: "application-form-topics",
        loadChildren:
          "./views/pages/application-topic/application-topic.module#ApplicationTopicModule",
      },

      {
        path: "application-form-topic/config",
        loadChildren:
          "./views/pages/application-topic-config/application-topic-config.module#ApplicationTopicConfigModule",
      },

      {
        path: "application-form/search",
        loadChildren:
          "./views/pages/application-form/application-form-search/application-form-search.module#ApplicationFormSearchModule",
      },

      {
        path: "committee-paper",
        loadChildren:
          "./views/pages/committee-paper/committee-paper.module#CommitteePaperModule",
      },
      {
        path: "committee",
        loadChildren: () =>
          import("./views/pages/committee/committee.module").then(
            (m) => m.CommitteeModule,
          ),
      },
      {
        path: "environmental-risk-tool",
        loadChildren: () =>
          import("./views/pages/environmental-risk/environmental-risk.module").then(
            (m) => m.EnvironmentalRiskModule,
          ),
      },
      {
        path: "environmental-risk-annexure",
        loadChildren: () =>
          import("./views/pages/environmental-risk-annexure/environmental-risk-annexure.module").then(
            (m) => m.EnvironmentalRiskAnnexureModule,
          ),
      },

      {
        path: "history",
        component: RiskOpinionHistoryComponent,
      },
      {
        path: "da",
        loadChildren:
          "./views/pages/delegated-credit-authority/delegated-credit-authority.module#DelegatedCreditAuthorityModule",
      },
      {
        path: "covering-approval",
        loadChildren:
          "./views/pages/covering-approval/covering-approval.module#CoveringApprovalModule",
      },
      {
        path: "covering-approval/ca-creation",
        loadChildren:
          "./views/pages/covering-approval/covering-approval.module#CoveringApprovalModule",
      },
      {
        path: "cas-v1",
        loadChildren: () =>
          import("./views/pages/cas-v1/cas-v1.module").then(
            (m) => m.CasV1Module,
          ),
      },
      {
        path: "diviation",
        loadChildren: () =>
          import("./views/pages/deviation/deviation.module").then(
            (m) => m.DeviationModule,
          ),
      },

      { path: "", redirectTo: "my-facility-papers", pathMatch: "full" },
      { path: "**", redirectTo: "my-facility-papers", pathMatch: "full" },
    ],
  },
  {
    path: "**",
    redirectTo: "my-facility-papers",
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),
  ],
  exports: [RouterModule],
})
export class AppRouter {}
