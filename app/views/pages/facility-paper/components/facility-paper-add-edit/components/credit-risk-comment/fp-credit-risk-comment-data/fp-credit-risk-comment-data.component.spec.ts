import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCreditRiskCommentDataComponent } from './fp-credit-risk-comment-data.component';

describe('FpCreditRiskCommentDataComponent', () => {
  let component: FpCreditRiskCommentDataComponent;
  let fixture: ComponentFixture<FpCreditRiskCommentDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCreditRiskCommentDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCreditRiskCommentDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
