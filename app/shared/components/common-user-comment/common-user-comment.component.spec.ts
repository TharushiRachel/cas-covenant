import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonUserCommentComponent } from './common-user-comment.component';

describe('CommonUserCommentComponent', () => {
  let component: CommonUserCommentComponent;
  let fixture: ComponentFixture<CommonUserCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommonUserCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommonUserCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
