import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadExistingMessageComponent } from './lead-existing-message.component';

describe('LeadExistingMessageComponent', () => {
  let component: LeadExistingMessageComponent;
  let fixture: ComponentFixture<LeadExistingMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadExistingMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadExistingMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
