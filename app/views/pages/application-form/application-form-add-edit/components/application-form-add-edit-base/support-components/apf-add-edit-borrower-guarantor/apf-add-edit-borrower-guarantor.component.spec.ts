import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditBorrowerGuarantorComponent } from './apf-add-edit-borrower-guarantor.component';

describe('ApfAddEditBorrowerGuarantorComponent', () => {
  let component: ApfAddEditBorrowerGuarantorComponent;
  let fixture: ComponentFixture<ApfAddEditBorrowerGuarantorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditBorrowerGuarantorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditBorrowerGuarantorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
