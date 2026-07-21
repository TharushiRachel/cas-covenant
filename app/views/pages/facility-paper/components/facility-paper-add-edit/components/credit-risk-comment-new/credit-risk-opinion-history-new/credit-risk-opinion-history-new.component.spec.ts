import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditRiskOpinionHistoryNewComponent } from './credit-risk-opinion-history-new.component';

describe('CreditRiskOpinionHistoryNewComponent', () => {
  let component: CreditRiskOpinionHistoryNewComponent;
  let fixture: ComponentFixture<CreditRiskOpinionHistoryNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditRiskOpinionHistoryNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditRiskOpinionHistoryNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
