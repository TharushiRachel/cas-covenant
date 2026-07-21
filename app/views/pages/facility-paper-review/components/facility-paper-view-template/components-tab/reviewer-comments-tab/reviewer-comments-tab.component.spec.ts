import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewerCommentsTabComponent } from './reviewer-comments-tab.component';

describe('ReviewerCommentsTabComponent', () => {
  let component: ReviewerCommentsTabComponent;
  let fixture: ComponentFixture<ReviewerCommentsTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewerCommentsTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerCommentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
