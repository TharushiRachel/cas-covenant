import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from "@angular/core";
import {PrivilegeService} from "../../core/service/authentication/privilege.service";

@Directive({
	selector: '[appHasAnyPrivilege]'
})
export class HasAnyPrivilegeDirective implements OnInit{

	constructor(private templateRef: TemplateRef<any>,
				private viewContainer: ViewContainerRef,
				private privilegeService: PrivilegeService) {
	}

	@Input('appHasAnyPrivilege') privileges;

	ngOnInit(): void {

		let privilegesArray = this.privileges.split(',').map((item) => {
			return item.trim();
		});

		if (this.privilegeService.hasAnyPrivilege(privilegesArray)) {
			this.viewContainer.createEmbeddedView(this.templateRef);
		} else {
			this.viewContainer.clear();
		}
	}

	/**
	 * Use -> *appHasAnyPrivilege="'TEST.PRIVILEGE.15, ...'"
	 *
	 * */
}
