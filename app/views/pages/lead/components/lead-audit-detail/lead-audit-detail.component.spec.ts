import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadAuditDetailComponent } from './lead-audit-detail.component';

describe('LeadAuditDetailComponent', () => {
  let component: LeadAuditDetailComponent;
  let fixture: ComponentFixture<LeadAuditDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadAuditDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadAuditDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
