import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteePaperDashboardComponent } from './committee-paper-dashboard.component';

describe('CommitteePaperDashboardComponent', () => {
  let component: CommitteePaperDashboardComponent;
  let fixture: ComponentFixture<CommitteePaperDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommitteePaperDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommitteePaperDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
