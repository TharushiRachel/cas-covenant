import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerAdvanceDetailsComponent } from './customer-advance-details.component';

describe('CustomerAdvanceDetailsComponent', () => {
  let component: CustomerAdvanceDetailsComponent;
  let fixture: ComponentFixture<CustomerAdvanceDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerAdvanceDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerAdvanceDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
