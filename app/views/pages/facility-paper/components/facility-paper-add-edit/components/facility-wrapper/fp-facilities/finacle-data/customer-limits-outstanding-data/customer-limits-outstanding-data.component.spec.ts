import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerLimitsOutstandingDataComponent } from './customer-limits-outstanding-data.component';

describe('CustomerLimitsOutstandingDataComponent', () => {
  let component: CustomerLimitsOutstandingDataComponent;
  let fixture: ComponentFixture<CustomerLimitsOutstandingDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerLimitsOutstandingDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerLimitsOutstandingDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
