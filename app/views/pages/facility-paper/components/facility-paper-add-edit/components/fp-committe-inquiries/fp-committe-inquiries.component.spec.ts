import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCommitteInquiriesComponent } from './fp-committe-inquiries.component';

describe('FpCommitteInquiriesComponent', () => {
  let component: FpCommitteInquiriesComponent;
  let fixture: ComponentFixture<FpCommitteInquiriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCommitteInquiriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCommitteInquiriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
