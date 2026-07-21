import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAddInquiryResponseComponent } from './fp-add-inquiry-response.component';

describe('FpAddInquiryResponseComponent', () => {
  let component: FpAddInquiryResponseComponent;
  let fixture: ComponentFixture<FpAddInquiryResponseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAddInquiryResponseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAddInquiryResponseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
