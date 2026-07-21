import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAnalyticsDecisionComponent } from './view-analytics-decision.component';

describe('ViewAnalyticsDecisionComponent', () => {
  let component: ViewAnalyticsDecisionComponent;
  let fixture: ComponentFixture<ViewAnalyticsDecisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAnalyticsDecisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAnalyticsDecisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
