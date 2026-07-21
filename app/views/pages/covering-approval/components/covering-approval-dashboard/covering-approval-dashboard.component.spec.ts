import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoveringApprovalDashboardComponent } from './covering-approval-dashboard.component';

describe('CoveringApprovalDetailsComponent', () => {
  let component: CoveringApprovalDashboardComponent;
  let fixture: ComponentFixture<CoveringApprovalDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoveringApprovalDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoveringApprovalDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
