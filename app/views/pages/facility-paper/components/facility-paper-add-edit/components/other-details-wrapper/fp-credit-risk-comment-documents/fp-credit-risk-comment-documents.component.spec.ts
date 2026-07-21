import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCreditRiskCommentDocumentsComponent } from './fp-credit-risk-comment-documents.component';

describe('FpCreditRiskCommentDocumentsComponent', () => {
  let component: FpCreditRiskCommentDocumentsComponent;
  let fixture: ComponentFixture<FpCreditRiskCommentDocumentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCreditRiskCommentDocumentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCreditRiskCommentDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
