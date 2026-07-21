import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfLpsExecutiveSummaryTopicsComponent } from './apf-lps-executive-summary-topics.component';

describe('ApfLpsExecutiveSummaryTopicsComponent', () => {
  let component: ApfLpsExecutiveSummaryTopicsComponent;
  let fixture: ComponentFixture<ApfLpsExecutiveSummaryTopicsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfLpsExecutiveSummaryTopicsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfLpsExecutiveSummaryTopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
