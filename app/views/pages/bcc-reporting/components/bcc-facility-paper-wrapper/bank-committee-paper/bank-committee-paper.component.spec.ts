import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankCommitteePaperComponent } from './bank-committee-paper.component';

describe('BankCommitteePaperComponent', () => {
  let component: BankCommitteePaperComponent;
  let fixture: ComponentFixture<BankCommitteePaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankCommitteePaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankCommitteePaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
