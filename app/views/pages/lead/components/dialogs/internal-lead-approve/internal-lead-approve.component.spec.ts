import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InternalLeadApproveComponent} from './internal-lead-approve.component';

describe('InternalLeadApproveComponent', () => {
  let component: InternalLeadApproveComponent;
  let fixture: ComponentFixture<InternalLeadApproveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InternalLeadApproveComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InternalLeadApproveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
