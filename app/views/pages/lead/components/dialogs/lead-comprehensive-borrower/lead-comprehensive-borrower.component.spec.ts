import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadComprehensiveBorrowerComponent } from './lead-comprehensive-borrower.component';

describe('LeadComprehensiveBorrowerComponent', () => {
  let component: LeadComprehensiveBorrowerComponent;
  let fixture: ComponentFixture<LeadComprehensiveBorrowerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadComprehensiveBorrowerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadComprehensiveBorrowerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
