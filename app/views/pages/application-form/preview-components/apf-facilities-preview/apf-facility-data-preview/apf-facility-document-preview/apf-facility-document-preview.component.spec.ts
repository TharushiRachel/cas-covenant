import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilityDocumentPreviewComponent } from './apf-facility-document-preview.component';

describe('ApfFacilityDocumentPreviewComponent', () => {
  let component: ApfFacilityDocumentPreviewComponent;
  let fixture: ComponentFixture<ApfFacilityDocumentPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilityDocumentPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilityDocumentPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
