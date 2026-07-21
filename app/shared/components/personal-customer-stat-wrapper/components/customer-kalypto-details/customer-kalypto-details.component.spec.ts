import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerKalyptoDetailsComponent } from './customer-kalypto-details.component';

describe('CustomerKalyptoDetailsComponent', () => {
  let component: CustomerKalyptoDetailsComponent;
  let fixture: ComponentFixture<CustomerKalyptoDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerKalyptoDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerKalyptoDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
