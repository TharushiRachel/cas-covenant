import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcaeBulkCommentModalComponent } from './acae-bulk-comment-modal.component';

describe('AcaeBulkCommentModalComponent', () => {
  let component: AcaeBulkCommentModalComponent;
  let fixture: ComponentFixture<AcaeBulkCommentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcaeBulkCommentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcaeBulkCommentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
