import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoveringApprovalCountBoxComponent } from './covering-approval-count-box.component';

describe('CoveringApprovalCountBoxComponent', () => {
  let component: CoveringApprovalCountBoxComponent;
  let fixture: ComponentFixture<CoveringApprovalCountBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoveringApprovalCountBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoveringApprovalCountBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
