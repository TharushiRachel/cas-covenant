import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadAddEditComponent } from './lead-add-edit.component';

describe('LeadAddEditComponent', () => {
  let component: LeadAddEditComponent;
  let fixture: ComponentFixture<LeadAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
