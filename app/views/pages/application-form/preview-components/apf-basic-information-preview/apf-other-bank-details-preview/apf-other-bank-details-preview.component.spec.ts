import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfOtherBankDetailsPreviewComponent} from './apf-other-bank-details-preview.component';

describe('ApfOtherBankDetailsPreviewComponent', () => {
  let component: ApfOtherBankDetailsPreviewComponent;
  let fixture: ComponentFixture<ApfOtherBankDetailsPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfOtherBankDetailsPreviewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfOtherBankDetailsPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
