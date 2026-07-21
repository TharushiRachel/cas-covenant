import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskOpinionHistoryViewerComponent } from './risk-opinion-history-viewer.component';

describe('RiskOpinionHistoryViewerComponent', () => {
  let component: RiskOpinionHistoryViewerComponent;
  let fixture: ComponentFixture<RiskOpinionHistoryViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskOpinionHistoryViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskOpinionHistoryViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
