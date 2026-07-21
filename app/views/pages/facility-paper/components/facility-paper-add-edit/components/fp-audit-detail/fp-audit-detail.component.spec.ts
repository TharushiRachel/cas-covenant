import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAuditDetailComponent } from './fp-audit-detail.component';

describe('FpAuditDetailComponent', () => {
  let component: FpAuditDetailComponent;
  let fixture: ComponentFixture<FpAuditDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAuditDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAuditDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
