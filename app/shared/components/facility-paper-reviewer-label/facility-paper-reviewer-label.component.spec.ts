import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperReviewerLabelComponent } from './facility-paper-reviewer-label.component';

describe('FacilityPaperReviewerLabelComponent', () => {
  let component: FacilityPaperReviewerLabelComponent;
  let fixture: ComponentFixture<FacilityPaperReviewerLabelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperReviewerLabelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperReviewerLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
