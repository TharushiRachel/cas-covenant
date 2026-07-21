import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskOpinionHistoryComponent } from './risk-opinion-history.component';

describe('RiskOpinionHistoryComponent', () => {
  let component: RiskOpinionHistoryComponent;
  let fixture: ComponentFixture<RiskOpinionHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskOpinionHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskOpinionHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
