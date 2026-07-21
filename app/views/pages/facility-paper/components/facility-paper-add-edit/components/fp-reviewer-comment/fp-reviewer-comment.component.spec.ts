import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpReviewerCommentComponent } from './fp-reviewer-comment.component';

describe('FpReviewerCommentComponent', () => {
  let component: FpReviewerCommentComponent;
  let fixture: ComponentFixture<FpReviewerCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpReviewerCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpReviewerCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
