import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadComprehensiveCreateComponent } from './lead-comprehensive-create.component';

describe('LeadComprehensiveCreateComponent', () => {
  let component: LeadComprehensiveCreateComponent;
  let fixture: ComponentFixture<LeadComprehensiveCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadComprehensiveCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadComprehensiveCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
