import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpInquiryRichEditorComponent } from './fp-inquiry-rich-editor.component';

describe('FpInquiryRichEditorComponent', () => {
  let component: FpInquiryRichEditorComponent;
  let fixture: ComponentFixture<FpInquiryRichEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpInquiryRichEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpInquiryRichEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
