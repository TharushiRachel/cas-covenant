import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentDetailedViewComponent } from './comment-detailed-view.component';

describe('CommentDetailedViewComponent', () => {
  let component: CommentDetailedViewComponent;
  let fixture: ComponentFixture<CommentDetailedViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentDetailedViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentDetailedViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
