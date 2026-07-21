import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadComprehensiveFacilitieseComponent } from './lead-comprehensive-facilitiese.component';

describe('LeadComprehensiveFacilitieseComponent', () => {
  let component: LeadComprehensiveFacilitieseComponent;
  let fixture: ComponentFixture<LeadComprehensiveFacilitieseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadComprehensiveFacilitieseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadComprehensiveFacilitieseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
