import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteeApproveRejectModalComponent } from './committee-approve-reject-modal.component';

describe('CommitteeApproveRejectModalComponent', () => {
  let component: CommitteeApproveRejectModalComponent;
  let fixture: ComponentFixture<CommitteeApproveRejectModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitteeApproveRejectModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitteeApproveRejectModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
