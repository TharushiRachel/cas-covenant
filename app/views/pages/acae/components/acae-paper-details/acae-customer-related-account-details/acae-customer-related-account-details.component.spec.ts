import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAECustomerRealatedAccountComponentBkp } from './acae-customer-related-account-details.component';

describe('CustomerStatOutstandingComponent', () => {
  let component: ACAECustomerRealatedAccountComponentBkp;
  let fixture: ComponentFixture<ACAECustomerRealatedAccountComponentBkp>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ACAECustomerRealatedAccountComponentBkp ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAECustomerRealatedAccountComponentBkp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
