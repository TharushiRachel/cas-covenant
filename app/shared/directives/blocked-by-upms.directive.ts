import {Directive, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {ApplicationService} from "../../core/service/application/application.service";

@Directive({
  selector: '[appBlockedByUPMs]'
})
export class BlockedByUPMsDirective implements OnInit {

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef,
              private applicationService: ApplicationService) {
  }

  ngOnInit(): void {
    //This is a request from sanka on 2022-03-11 to disable actions for upm levels without using privileges
    let blockedUPMs = [99]; // add upm classes here
    let disabled = blockedUPMs.includes(+this.applicationService.getLoggedInUserUPMGroupCode());
    if (disabled) {
      this.viewContainer.clear();
    } else {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }

}
