import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadCustomerDetailComponent } from './lead-customer-detail.component';

describe('LeadCustomerDetailComponent', () => {
  let component: LeadCustomerDetailComponent;
  let fixture: ComponentFixture<LeadCustomerDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadCustomerDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadCustomerDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
