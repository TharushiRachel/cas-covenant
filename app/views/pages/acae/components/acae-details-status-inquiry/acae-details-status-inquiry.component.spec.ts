import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAEDetailsStatusInquiryComponent } from './acae-details-status-inquiry.component';

describe('ACAEDetailsStatusInquiryComponent', () => {
  let component: ACAEDetailsStatusInquiryComponent;
  let fixture: ComponentFixture<ACAEDetailsStatusInquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ACAEDetailsStatusInquiryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEDetailsStatusInquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
