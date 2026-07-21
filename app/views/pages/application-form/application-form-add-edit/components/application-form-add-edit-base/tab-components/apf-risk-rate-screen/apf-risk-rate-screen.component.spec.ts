import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfRiskRateScreenComponent} from './apf-risk-rate-screen.component';

describe('ApfRiskRateScreenComponent', () => {
  let component: ApfRiskRateScreenComponent;
  let fixture: ComponentFixture<ApfRiskRateScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfRiskRateScreenComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfRiskRateScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
