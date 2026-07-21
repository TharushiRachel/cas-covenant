import {NgModule} from "@angular/core";
import {MDB_DATE_OPTIONS, MDBBootstrapModulesPro} from "ng-uikit-pro-standard";

@NgModule({
  exports: [
    MDBBootstrapModulesPro
  ],
  providers: [
    {
      provide: MDB_DATE_OPTIONS,
      useValue: {
        dateFormat: 'dd/mm/yyyy',
        minYear: 1990,
        maxYear: 2100
      }
    }
  ]
})

export class MdbImports {
}
