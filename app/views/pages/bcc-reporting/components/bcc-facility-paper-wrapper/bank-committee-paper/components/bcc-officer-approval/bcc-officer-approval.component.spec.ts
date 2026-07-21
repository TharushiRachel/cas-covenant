import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BccOfficerApprovalComponent} from './bcc-officer-approval.component';

describe('BccOfficerApprovalComponent', () => {
  let component: BccOfficerApprovalComponent;
  let fixture: ComponentFixture<BccOfficerApprovalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BccOfficerApprovalComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccOfficerApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
