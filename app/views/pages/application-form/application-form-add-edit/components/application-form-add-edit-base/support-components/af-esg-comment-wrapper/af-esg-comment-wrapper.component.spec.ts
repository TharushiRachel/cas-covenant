import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AfEsgCommentWrapperComponent } from './af-esg-comment-wrapper.component';

describe('AfEsgCommentWrapperComponent', () => {
  let component: AfEsgCommentWrapperComponent;
  let fixture: ComponentFixture<AfEsgCommentWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AfEsgCommentWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AfEsgCommentWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
