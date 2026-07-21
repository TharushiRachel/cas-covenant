import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateHistoryCommentViewComponent } from './upc-template-history-comment-view.component';

describe('UpcTemplateHistoryCommentViewComponent', () => {
  let component: UpcTemplateHistoryCommentViewComponent;
  let fixture: ComponentFixture<UpcTemplateHistoryCommentViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateHistoryCommentViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateHistoryCommentViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
