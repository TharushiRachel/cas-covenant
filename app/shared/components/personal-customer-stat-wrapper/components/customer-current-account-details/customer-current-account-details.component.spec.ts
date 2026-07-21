import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerCurrentAccountDetailsComponent } from './customer-current-account-details.component';

describe('CustomerCurrentAccountDetailsComponent', () => {
  let component: CustomerCurrentAccountDetailsComponent;
  let fixture: ComponentFixture<CustomerCurrentAccountDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerCurrentAccountDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerCurrentAccountDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
