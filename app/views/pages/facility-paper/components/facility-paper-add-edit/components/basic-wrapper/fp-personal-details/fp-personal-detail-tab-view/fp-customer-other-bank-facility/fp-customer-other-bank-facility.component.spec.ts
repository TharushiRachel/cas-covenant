import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCustomerOtherBankFacilityComponent } from './fp-customer-other-bank-facility.component';

describe('FpCustomerOtherBankFacilityComponent', () => {
  let component: FpCustomerOtherBankFacilityComponent;
  let fixture: ComponentFixture<FpCustomerOtherBankFacilityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCustomerOtherBankFacilityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCustomerOtherBankFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
