import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfManualRiskRatesComponent } from './apf-manual-risk-rates.component';

describe('ApfManualRiskRatesComponent', () => {
  let component: ApfManualRiskRatesComponent;
  let fixture: ComponentFixture<ApfManualRiskRatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfManualRiskRatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfManualRiskRatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
