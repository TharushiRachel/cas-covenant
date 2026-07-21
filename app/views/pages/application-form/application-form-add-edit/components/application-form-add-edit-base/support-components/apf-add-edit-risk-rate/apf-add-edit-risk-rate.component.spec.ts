import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfAddEditRiskRateComponent} from './apf-add-edit-risk-rate.component';

describe('ApfAddEditRiskRateComponent', () => {
  let component: ApfAddEditRiskRateComponent;
  let fixture: ComponentFixture<ApfAddEditRiskRateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfAddEditRiskRateComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditRiskRateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
