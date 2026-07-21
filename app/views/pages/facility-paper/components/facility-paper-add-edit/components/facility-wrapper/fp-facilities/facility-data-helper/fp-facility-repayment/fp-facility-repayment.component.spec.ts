import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpFacilityRepaymentComponent } from './fp-facility-repayment.component';

describe('FpFacilityRepaymentComponent', () => {
  let component: FpFacilityRepaymentComponent;
  let fixture: ComponentFixture<FpFacilityRepaymentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpFacilityRepaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpFacilityRepaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
