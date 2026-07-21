import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBorrowerGuarantorComponent } from './apf-borrower-guarantor.component';

describe('ApfBorrowerGuarantorComponent', () => {
  let component: ApfBorrowerGuarantorComponent;
  let fixture: ComponentFixture<ApfBorrowerGuarantorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBorrowerGuarantorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBorrowerGuarantorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
