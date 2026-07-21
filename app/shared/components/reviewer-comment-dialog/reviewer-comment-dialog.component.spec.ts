import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewerCommentDialogComponent} from './reviewer-comment-dialog.component';

describe('ReviewerCommentDialogComponent', () => {
  let component: ReviewerCommentDialogComponent;
  let fixture: ComponentFixture<ReviewerCommentDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ReviewerCommentDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
