import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DigitalApplicantPickerModalComponent } from './digital-applicant-picker-modal.component';

describe('DigitalApplicantPickerModalComponent', () => {
  let component: DigitalApplicantPickerModalComponent;
  let fixture: ComponentFixture<DigitalApplicantPickerModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DigitalApplicantPickerModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DigitalApplicantPickerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
