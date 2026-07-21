import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompLeadCommentViewComponent } from './comp-lead-comment-view.component';

describe('CompLeadCommentViewComponent', () => {
  let component: CompLeadCommentViewComponent;
  let fixture: ComponentFixture<CompLeadCommentViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompLeadCommentViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompLeadCommentViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
