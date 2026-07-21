import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAECustomerOutstandingComponentBkp } from './acae-customer-outstanding-details.component';

describe('CustomerCurrentAccountDetailsComponent', () => {
  let component: ACAECustomerOutstandingComponentBkp;
  let fixture: ComponentFixture<ACAECustomerOutstandingComponentBkp>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAECustomerOutstandingComponentBkp]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAECustomerOutstandingComponentBkp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
