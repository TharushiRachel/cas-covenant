import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject, Subscription } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-esg-annexure-selectors-common",
  templateUrl: "./esg-annexure-selectors-common.component.html",
  styleUrls: ["./esg-annexure-selectors-common.component.scss"],
})
export class EsgAnnexureSelectorsCommonComponent implements OnInit, OnDestroy {
  @Input() availableAnnexures: any[];
  @Output() action = new EventEmitter<number>();
  componentForm: FormGroup;
  onFormValueChangeSub: Subscription = new Subscription();
  formErrors: any;
  annexureOptions: any[] = [];

  constructor(
    private readonly mdbModalRef: MDBModalRef,
    private readonly formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.formErrors = { annexureId: {} };

    this.componentForm = this.formBuilder.group({
      annexureId: ["", Validators.required],
    });

    this.annexureOptions = (this.availableAnnexures || [])
      .map((a: any) => ({
        ...a,
        annexureIndex: this.extractRomanNumeral(a.name),
      }))
      .sort((a: any, b: any) => a.annexureIndex - b.annexureIndex)
      .map((a) => ({
        value: a.annexureId,
        label: a.isMandatory === "Y" ? `${a.name} *`.toString() : a.name,
      }));

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(
      () => {
        this.formErrors = AppUtils.getFormErrors(
          this.componentForm,
          this.formErrors
        );
      }
    );
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
  }

  isValid() {
    return this.componentForm.valid;
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  onCreate() {
    const selected = this.componentForm.get("annexureId").value;
    this.action.next(selected);
    this.mdbModalRef.hide();
  }

  extractRomanNumeral(text: string): number {
    let splitTxt: string[] = text.split(" ");
    let romanNumeral: any = splitTxt.length > 0 ? splitTxt[1] : "";

    return romanNumeral !== "" &&
      Constants.annexureNumberConst[romanNumeral] !== undefined &&
      Constants.annexureNumberConst[romanNumeral] !== null
      ? Constants.annexureNumberConst[romanNumeral]
      : 0;
  }
}
