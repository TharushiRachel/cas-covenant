import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableCommentViewerComponent } from './table-comment-viewer.component';

describe('TableCommentViewerComponent', () => {
  let component: TableCommentViewerComponent;
  let fixture: ComponentFixture<TableCommentViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableCommentViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableCommentViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
