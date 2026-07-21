import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { EnvironmentalRiskAnnexureRoutingModule } from "./environmental-risk-annexure-routing.module";
import { EnvironmentalRiskAnnexureComponent } from "./components/environmental-risk-annexure/environmental-risk-annexure.component";
import { AddEditEnvironmentalRiskAnnexureComponent } from "./components/add-edit-environmental-risk-annexure/add-edit-environmental-risk-annexure.component";
import { MDBBootstrapModule } from "ng-uikit-pro-standard";
import { SharedModule } from "src/app/shared/shared.module";
import { AddEditQuestionComponent } from "./components/add-edit-question/add-edit-question.component";
import { AddEditAnswerComponent } from "./components/add-edit-answer/add-edit-answer.component";
import { ViewQuestionsModalComponent } from "./components/view-questions-modal/view-questions-modal.component";
import { ViewQuestionWrapperComponent } from "./components/view-question-wrapper/view-question-wrapper.component";
import { CompareDataModalComponent } from "./components/compare-data-modal/compare-data-modal.component";
import { DragDropModule } from "@angular/cdk/drag-drop";
import { SortableModalComponent } from "./components/sortable-modal/sortable-modal.component";
import { NgxSortableModule } from "ngx-sortable";
@NgModule({
  declarations: [
    EnvironmentalRiskAnnexureComponent,
    AddEditEnvironmentalRiskAnnexureComponent,
    AddEditQuestionComponent,
    AddEditAnswerComponent,
    ViewQuestionsModalComponent,
    ViewQuestionWrapperComponent,
    CompareDataModalComponent,
    SortableModalComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    DragDropModule,
    NgxSortableModule,
    MDBBootstrapModule.forRoot(),
    EnvironmentalRiskAnnexureRoutingModule,
  ],
  entryComponents: [
    AddEditQuestionComponent,
    AddEditAnswerComponent,
    ViewQuestionsModalComponent,
    CompareDataModalComponent,
    SortableModalComponent,
  ],
})
export class EnvironmentalRiskAnnexureModule {}
