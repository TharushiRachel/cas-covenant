import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAEDetailsDateRangeInquiryComponent } from './acae-details-date-range-inquiry.component';

describe('ACAEDetailsDateRangeInquiryComponent', () => {
  let component: ACAEDetailsDateRangeInquiryComponent;
  let fixture: ComponentFixture<ACAEDetailsDateRangeInquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAEDetailsDateRangeInquiryComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEDetailsDateRangeInquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
