import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExternalLeadRejectComponent } from './external-lead-reject.component';

describe('ExternalLeadRejectComponent', () => {
  let component: ExternalLeadRejectComponent;
  let fixture: ComponentFixture<ExternalLeadRejectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExternalLeadRejectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalLeadRejectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
