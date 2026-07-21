import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAECustomerLoanAccountComponentBkp } from './acae-customer-loan-accounts-details.component';

describe('CustomerStatOutstandingComponent', () => {
  let component: ACAECustomerLoanAccountComponentBkp;
  let fixture: ComponentFixture<ACAECustomerLoanAccountComponentBkp>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAECustomerLoanAccountComponentBkp]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAECustomerLoanAccountComponentBkp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
