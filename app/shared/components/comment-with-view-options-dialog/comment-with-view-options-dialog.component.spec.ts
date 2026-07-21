import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentWithViewOptionsDialogComponent } from './comment-with-view-options-dialog.component';

describe('CommentWithViewOptionsDialogComponent', () => {
  let component: CommentWithViewOptionsDialogComponent;
  let fixture: ComponentFixture<CommentWithViewOptionsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentWithViewOptionsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentWithViewOptionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
