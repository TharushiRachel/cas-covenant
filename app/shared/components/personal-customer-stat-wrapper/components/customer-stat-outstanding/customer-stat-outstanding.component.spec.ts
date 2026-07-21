import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerStatOutstandingComponent } from './customer-stat-outstanding.component';

describe('CustomerStatOutstandingComponent', () => {
  let component: CustomerStatOutstandingComponent;
  let fixture: ComponentFixture<CustomerStatOutstandingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerStatOutstandingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerStatOutstandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
