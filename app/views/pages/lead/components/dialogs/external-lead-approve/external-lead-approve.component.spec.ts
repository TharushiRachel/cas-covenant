import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExternalLeadApproveComponent } from './external-lead-approve.component';

describe('ExternalLeadApproveComponent', () => {
  let component: ExternalLeadApproveComponent;
  let fixture: ComponentFixture<ExternalLeadApproveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExternalLeadApproveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalLeadApproveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
