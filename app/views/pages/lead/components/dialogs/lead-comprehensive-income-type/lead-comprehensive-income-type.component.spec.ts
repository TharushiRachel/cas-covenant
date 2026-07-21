import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadComprehensiveIncomeTypeComponent } from './lead-comprehensive-income-type.component';

describe('LeadComprehensiveIncomeTypeComponent', () => {
  let component: LeadComprehensiveIncomeTypeComponent;
  let fixture: ComponentFixture<LeadComprehensiveIncomeTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadComprehensiveIncomeTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadComprehensiveIncomeTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
