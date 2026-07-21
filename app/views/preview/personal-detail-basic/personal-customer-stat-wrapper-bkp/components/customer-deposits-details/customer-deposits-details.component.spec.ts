import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerDepositsDetailsComponent } from './customer-deposits-details.component';

describe('CustomerDepositsDetailsComponent', () => {
  let component: CustomerDepositsDetailsComponent;
  let fixture: ComponentFixture<CustomerDepositsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerDepositsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerDepositsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
