import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditContentComponent } from './audit-content.component';

describe('AuditContentComponent', () => {
  let component: AuditContentComponent;
  let fixture: ComponentFixture<AuditContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
