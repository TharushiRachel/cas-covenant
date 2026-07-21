import {NgModule} from '@angular/core';


import {CreditFacilityTypeTemplateRoutingModule} from './credit-facility-type-template-routing.module';
import {CreditFacilityTypeTemplatesComponent} from './components/credit-facility-type-templates/credit-facility-type-templates.component';
import {SharedModule} from "../../../shared/shared.module";
import {CreditFacilityTypeTemplatesService} from "./services/credit-facility-type-templates.service";
import {CreditFacilityTemplateAddEditComponent} from './components/credit-facility-template-add-edit/credit-facility-template-add-edit.component';
import {CreditFacilityTemplateAddEditService} from "./services/credit-facility-template-add-edit.service";
import {CftSupportingDocComponent} from './components/cft-supporting-doc/cft-supporting-doc.component';
import {MDBBootstrapModulesPro, MDBSpinningPreloader} from "ng-uikit-pro-standard";
import {CftInterestRateComponent} from './components/cft-interest-rate/cft-interest-rate.component';
import {CftVitalQuestionComponent} from './components/cft-vital-question/cft-vital-question.component';
import { OtherFacilityInformationComponent } from './components/other-facility-information/other-facility-information.component';
import { CustomFacilityInformationComponent } from './components/custom-facility-information/custom-facility-information.component';


@NgModule({
  declarations: [CreditFacilityTypeTemplatesComponent, CreditFacilityTemplateAddEditComponent, CftSupportingDocComponent, CftInterestRateComponent, CftVitalQuestionComponent, OtherFacilityInformationComponent, CustomFacilityInformationComponent],
  imports: [
    SharedModule,
    CreditFacilityTypeTemplateRoutingModule,
    MDBBootstrapModulesPro.forRoot(),
  ],
  entryComponents: [
    CftSupportingDocComponent,
    CftInterestRateComponent,
    CftVitalQuestionComponent,
    OtherFacilityInformationComponent,
    CustomFacilityInformationComponent
  ],
  providers: [
    CreditFacilityTypeTemplatesService,
    CreditFacilityTemplateAddEditService,
    MDBSpinningPreloader
  ]
})
export class CreditFacilityTypeTemplateModule {
}
