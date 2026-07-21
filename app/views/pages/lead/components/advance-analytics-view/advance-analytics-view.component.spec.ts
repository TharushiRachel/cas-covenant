import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvanceAnalyticsViewComponent } from './advance-analytics-view.component';

describe('AdvanceAnalyticsViewComponent', () => {
  let component: AdvanceAnalyticsViewComponent;
  let fixture: ComponentFixture<AdvanceAnalyticsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvanceAnalyticsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvanceAnalyticsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
