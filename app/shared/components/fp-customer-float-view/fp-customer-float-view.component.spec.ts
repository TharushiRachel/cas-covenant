import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCustomerFloatViewComponent } from './fp-customer-float-view.component';

describe('FpCustomerFloatViewComponent', () => {
  let component: FpCustomerFloatViewComponent;
  let fixture: ComponentFixture<FpCustomerFloatViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCustomerFloatViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCustomerFloatViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
