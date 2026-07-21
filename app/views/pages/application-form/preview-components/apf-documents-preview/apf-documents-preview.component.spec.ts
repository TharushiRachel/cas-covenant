import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfDocumentsPreviewComponent } from './apf-documents-preview.component';

describe('ApfDocumentsPreviewComponent', () => {
  let component: ApfDocumentsPreviewComponent;
  let fixture: ComponentFixture<ApfDocumentsPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfDocumentsPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfDocumentsPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
