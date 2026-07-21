import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaperReviewSummaryWrapperComponent } from './paper-review-summary-wrapper.component';

describe('PaperReviewSummaryWrapperComponent', () => {
  let component: PaperReviewSummaryWrapperComponent;
  let fixture: ComponentFixture<PaperReviewSummaryWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaperReviewSummaryWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaperReviewSummaryWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
