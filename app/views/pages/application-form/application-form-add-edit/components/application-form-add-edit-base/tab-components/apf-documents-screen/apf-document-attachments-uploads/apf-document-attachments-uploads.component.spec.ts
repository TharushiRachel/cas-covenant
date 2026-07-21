import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfDocumentAttachmentsUploadsComponent } from './apf-document-attachments-uploads.component';

describe('ApfDocumentAttachmentsUploadsComponent', () => {
  let component: ApfDocumentAttachmentsUploadsComponent;
  let fixture: ComponentFixture<ApfDocumentAttachmentsUploadsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfDocumentAttachmentsUploadsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfDocumentAttachmentsUploadsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
