import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfExecutiveSummaryScreenComponent} from './apf-executive-summary-screen.component';

describe('ApfExecutiveSummaryScreenComponent', () => {
  let component: ApfExecutiveSummaryScreenComponent;
  let fixture: ComponentFixture<ApfExecutiveSummaryScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfExecutiveSummaryScreenComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfExecutiveSummaryScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
