import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfRiskRatingPreviewComponent} from './apf-risk-rating-preview.component';

describe('ApfRiskRatingPreviewComponent', () => {
  let component: ApfRiskRatingPreviewComponent;
  let fixture: ComponentFixture<ApfRiskRatingPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfRiskRatingPreviewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfRiskRatingPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
