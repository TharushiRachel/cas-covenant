import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcaePaperRangeInquiryDetailsComponent } from './acae-paper-range-inquiry-details.component';

describe('AcaePaperRangeInquiryDetailsComponent', () => {
  let component: AcaePaperRangeInquiryDetailsComponent;
  let fixture: ComponentFixture<AcaePaperRangeInquiryDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AcaePaperRangeInquiryDetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcaePaperRangeInquiryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
