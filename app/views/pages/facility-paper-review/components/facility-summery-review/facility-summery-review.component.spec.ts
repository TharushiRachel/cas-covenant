import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilitySummeryReviewComponent } from './facility-summery-review.component';

describe('FacilitySummeryReviewComponent', () => {
  let component: FacilitySummeryReviewComponent;
  let fixture: ComponentFixture<FacilitySummeryReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilitySummeryReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilitySummeryReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
