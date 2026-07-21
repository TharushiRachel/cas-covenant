import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerInsuranceValuationComponent } from './customer-insurance-valuation.component';

describe('CustomerInsuranceValuationComponent', () => {
  let component: CustomerInsuranceValuationComponent;
  let fixture: ComponentFixture<CustomerInsuranceValuationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerInsuranceValuationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerInsuranceValuationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
