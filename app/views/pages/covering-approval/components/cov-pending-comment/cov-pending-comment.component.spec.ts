import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovPendingCommentComponent } from './cov-pending-comment.component';

describe('CovPendingCommentComponent', () => {
  let component: CovPendingCommentComponent;
  let fixture: ComponentFixture<CovPendingCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovPendingCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovPendingCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
