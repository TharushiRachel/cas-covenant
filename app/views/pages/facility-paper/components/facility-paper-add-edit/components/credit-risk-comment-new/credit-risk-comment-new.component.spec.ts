import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditRiskCommentNewComponent } from './credit-risk-comment-new.component';

describe('CreditRiskCommentNewComponent', () => {
  let component: CreditRiskCommentNewComponent;
  let fixture: ComponentFixture<CreditRiskCommentNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditRiskCommentNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditRiskCommentNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
