import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpSupportingDocumentUploadComponent } from './fp-supporting-document-upload.component';

describe('FpSupportingDocumentUploadComponent', () => {
  let component: FpSupportingDocumentUploadComponent;
  let fixture: ComponentFixture<FpSupportingDocumentUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpSupportingDocumentUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpSupportingDocumentUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
