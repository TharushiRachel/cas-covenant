import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InternalLeadAcceptComponent } from './internal-lead-accept.component';

describe('InternalLeadAcceptComponent', () => {
  let component: InternalLeadAcceptComponent;
  let fixture: ComponentFixture<InternalLeadAcceptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InternalLeadAcceptComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InternalLeadAcceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
