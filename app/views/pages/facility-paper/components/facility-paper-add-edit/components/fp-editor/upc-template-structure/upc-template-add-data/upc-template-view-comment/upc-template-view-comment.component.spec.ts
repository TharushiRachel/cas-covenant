import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateViewCommentComponent } from './upc-template-view-comment.component';

describe('UpcTemplateViewCommentComponent', () => {
  let component: UpcTemplateViewCommentComponent;
  let fixture: ComponentFixture<UpcTemplateViewCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateViewCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateViewCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
