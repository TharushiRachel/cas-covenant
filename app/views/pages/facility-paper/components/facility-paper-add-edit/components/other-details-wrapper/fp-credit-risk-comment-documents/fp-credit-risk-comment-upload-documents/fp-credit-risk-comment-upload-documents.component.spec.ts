import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCreditRiskCommentUploadDocumentsComponent } from './fp-credit-risk-comment-upload-documents.component';

describe('FpCreditRiskCommentUploadDocumentsComponent', () => {
  let component: FpCreditRiskCommentUploadDocumentsComponent;
  let fixture: ComponentFixture<FpCreditRiskCommentUploadDocumentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCreditRiskCommentUploadDocumentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCreditRiskCommentUploadDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
