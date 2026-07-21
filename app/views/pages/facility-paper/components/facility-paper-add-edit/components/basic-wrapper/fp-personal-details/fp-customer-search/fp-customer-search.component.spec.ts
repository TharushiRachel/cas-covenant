import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCustomerSearchComponent } from './fp-customer-search.component';

describe('FpCustomerSearchComponent', () => {
  let component: FpCustomerSearchComponent;
  let fixture: ComponentFixture<FpCustomerSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCustomerSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCustomerSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
