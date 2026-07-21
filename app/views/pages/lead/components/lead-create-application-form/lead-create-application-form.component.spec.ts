import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadCreateApplicationForm } from './app-lead-create-application-form.component';

describe('LeadCreateApplicationForm', () => {
  let component: LeadCreateApplicationForm;
  let fixture: ComponentFixture<LeadCreateApplicationForm>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadCreateApplicationForm ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadCreateApplicationForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
