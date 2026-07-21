import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanCompStatementComponent } from './loan-comp-statement.component';

describe('LoanCompStatementComponent', () => {
  let component: LoanCompStatementComponent;
  let fixture: ComponentFixture<LoanCompStatementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanCompStatementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanCompStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
