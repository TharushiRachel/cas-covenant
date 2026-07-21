import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperReviewWrapperComponent } from './facility-paper-review-wrapper.component';

describe('FacilityPaperReviewWrapperComponent', () => {
  let component: FacilityPaperReviewWrapperComponent;
  let fixture: ComponentFixture<FacilityPaperReviewWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperReviewWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperReviewWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
