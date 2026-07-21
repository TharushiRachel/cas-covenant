import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovPendingDashboardComponent } from './cov-pending-dashboard.component';

describe('CovPendingDashboardComponent', () => {
  let component: CovPendingDashboardComponent;
  let fixture: ComponentFixture<CovPendingDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovPendingDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovPendingDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
