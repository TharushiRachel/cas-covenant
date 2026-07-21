import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditOtherBankDetailsComponent } from './apf-add-edit-other-bank-details.component';

describe('ApfAddEditOtherBankDetailsComponent', () => {
  let component: ApfAddEditOtherBankDetailsComponent;
  let fixture: ComponentFixture<ApfAddEditOtherBankDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditOtherBankDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditOtherBankDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
