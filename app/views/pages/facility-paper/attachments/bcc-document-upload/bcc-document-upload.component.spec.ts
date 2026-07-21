import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccDocumentUploadComponent } from './bcc-document-upload.component';

describe('BccDocumentUploadComponent', () => {
  let component: BccDocumentUploadComponent;
  let fixture: ComponentFixture<BccDocumentUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccDocumentUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccDocumentUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
