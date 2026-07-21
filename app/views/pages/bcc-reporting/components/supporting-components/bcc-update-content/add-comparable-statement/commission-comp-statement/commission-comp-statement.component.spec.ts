import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommissionCompStatementComponent } from './commission-comp-statement.component';

describe('CommissionCompStatementComponent', () => {
  let component: CommissionCompStatementComponent;
  let fixture: ComponentFixture<CommissionCompStatementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommissionCompStatementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionCompStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
