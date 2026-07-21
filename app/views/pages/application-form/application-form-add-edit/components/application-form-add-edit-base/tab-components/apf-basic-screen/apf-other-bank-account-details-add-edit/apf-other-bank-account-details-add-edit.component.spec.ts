import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfOtherBankAccountDetailsAddEditComponent} from './apf-other-bank-account-details-add-edit.component';

describe('ApfOtherBankAccountDetailsAddEditComponent', () => {
  let component: ApfOtherBankAccountDetailsAddEditComponent;
  let fixture: ComponentFixture<ApfOtherBankAccountDetailsAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfOtherBankAccountDetailsAddEditComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfOtherBankAccountDetailsAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
