import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-apf-declaration-add-edit',
  templateUrl: './apf-declaration-add-edit.component.html',
  styleUrls: ['./apf-declaration-add-edit.component.scss']
})
export class ApfDeclarationAddEditComponent implements OnInit {
  declarationForm: FormGroup;
  isDisabled: boolean = false;

  selectUser: any = [
    {value: '', label: ''},
    {value: '', label: "user 1"},
    {value: '', label: "user 2"},
    {value: '', label: "user 3"},
  ];

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.declarationForm = this.createForm();
  }

  createForm() {
    this.declarationForm = this.formBuilder.group({
      declaration: [{value: '', disabled: this.isDisabled}, [Validators.required]],
      selectUser: [{value: '', disabled: this.isDisabled}, [Validators.required]],
    });
    return this.declarationForm
  }
}
