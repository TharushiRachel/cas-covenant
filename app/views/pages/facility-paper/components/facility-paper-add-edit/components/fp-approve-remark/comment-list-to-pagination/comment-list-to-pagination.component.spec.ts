import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentListToPaginationComponent } from './comment-list-to-pagination.component';

describe('CommentListToPaginationComponent', () => {
  let component: CommentListToPaginationComponent;
  let fixture: ComponentFixture<CommentListToPaginationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentListToPaginationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentListToPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
