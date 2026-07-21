import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAddCommitteeInquiryComponent } from './fp-add-committee-inquiry.component';

describe('FpAddCommitteeInquiryComponent', () => {
  let component: FpAddCommitteeInquiryComponent;
  let fixture: ComponentFixture<FpAddCommitteeInquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAddCommitteeInquiryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAddCommitteeInquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
